/**
 * 
 */
package com.uns.model.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMatchInv;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import org.compiere.model.MInvoice;

/**
 * @author nurse
 *
 */
public class NotInvoiceReceiptProcessor extends SvrProcess {

	private Timestamp m_from = null;
	private Timestamp m_to = null;
	
	/**
	 * 
	 */
	public NotInvoiceReceiptProcessor() {
		super ();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++) {
			ProcessInfoParameter param = params[i];
			if ("DateFrom".equals(param.getParameterName()))
				m_from = param.getParameterAsTimestamp();
			else if ("DateTo".equals(param.getParameterName()))
				m_to = param.getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "Unknown parameter " + param.getParameterName());
		}
		
		if (m_from == null)
			m_from = Timestamp.valueOf("1988-10-10 00:00:00");
		if (m_to == null)
			m_to = new Timestamp(System.currentTimeMillis());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		StringBuilder unresolvedLogBuilder = new StringBuilder();
		String sql = "DELETE FROM C_InvoiceLine WHERE C_Invoice_ID IN (SELECT C_Invoice_ID "
				+ " FROM C_Invoice WHERE Description = ? AND DocStatus IN (?,?,?))";
		int result = DB.executeUpdate(sql, new Object[]{"***Auto create from process not"
								+ " invoiced receipt processor***", "DR", "IP", "IN"}, 
								false, get_TrxName());
		if (result == -1)
			throw new AdempiereException(CLogger.retrieveErrorString("failed when try to remove invoice line"));
		sql = "DELETE FROM C_Invoice WHERE Description = ? AND DocStatus IN (?,?,?)";
		result = DB.executeUpdate(sql, new Object[]{"***Auto create from process not"
								+ " invoiced receipt processor***", "DR", "IP", "IN"},
								false, get_TrxName());
		if (result == -1)
			throw new AdempiereException(CLogger.retrieveErrorString("failed when try to remove invoice"));
		
		MInOut[] inOuts = getErrorPostInOuts();
		Hashtable<String, MInvoice> mapInvoice = new Hashtable<>();
		for (int i=0; i<inOuts.length; i++)
		{
			int displayIdx = i+1;
			processUI.statusUpdate("Processing Document " + inOuts[i].getDocumentNo() 
					+ " - [" + displayIdx + " of " + inOuts.length + "]");
			MInOutLine[] lines = inOuts[i].getLines();
			for (int j=0; j<lines.length; j++)
			{
				if (inOuts[i].getMovementType().equals("V-"))
				{
					unresolvedLogBuilder.append(inOuts[i].getDocumentNo()).append(";RETURN TO VENDOR&");
					break;
				}
				if (lines[j].getC_OrderLine_ID() == 0 && inOuts[i].getMovementType().equals("V+"))
				{
					unresolvedLogBuilder.append(inOuts[i].getDocumentNo()).append(";NO ORDER&");
					break;
				}
				
				sql = "SELECT SUM(Qty) FROM M_MatchInv WHERE M_InOutLine_ID = ? AND Processed = ?";
				BigDecimal matchedQty = DB.getSQLValueBD(get_TrxName(), sql, lines[j].get_ID(), "Y");
				if (matchedQty == null)
					matchedQty = Env.ZERO;
				BigDecimal notMatchedQty = lines[j].getMovementQty().subtract(matchedQty);
				if (notMatchedQty.signum() == 0)
					continue;
				boolean isMatchedGreaterThanInvQty = false;
				if (lines[j].getC_OrderLine_ID() > 0)
				{
					List<MInvoiceLine> invLines = new Query(
							getCtx(), MInvoiceLine.Table_Name, "C_OrderLine_ID = ? AND Processed = 'Y'", 
							get_TrxName()).
							setParameters(lines[j].getC_OrderLine_ID()).list();
					for (int k=0; k<invLines.size(); k++)
					{
						BigDecimal matchedQtyInv = invLines.get(k).getMatchedQty();
						BigDecimal notMatchedQtyInv = invLines.get(k).getQtyInvoiced().subtract(matchedQtyInv);
						if (notMatchedQtyInv.signum() == -1)
						{
							isMatchedGreaterThanInvQty = true;
							continue;
						}
						if (notMatchedQtyInv.signum() == 0)
							continue;
						BigDecimal tobeMathced = notMatchedQty;
						if (tobeMathced.compareTo(notMatchedQtyInv) == 1)
							tobeMathced = notMatchedQtyInv;
						MMatchInv match = new MMatchInv(invLines.get(k), inOuts[i].getMovementDate(), notMatchedQty);
						match.setM_InOutLine_ID(lines[j].getM_InOutLine_ID());
						match.setQty(tobeMathced);
						if (!match.save())
							return CLogger.retrieveErrorString("Could not save match invoice");
						notMatchedQty = notMatchedQty.subtract(tobeMathced);
					}
				}
				if (isMatchedGreaterThanInvQty)
				{
					unresolvedLogBuilder.append(inOuts[i].getDocumentNo()).append(";MATCHED QYT GREATER THAN INVOICED QTY&");
					break;
				}
				if (notMatchedQty.signum() != 0)
				{
					log.log(Level.INFO, "not matched qty = " + notMatchedQty);
					Env.setContext(Env.getCtx(), "ON_ProcessNotInvoiceReceiptProcessor", true);
					String key = mapInvKeyGen(inOuts[i].getAD_Org_ID(),
							inOuts[i].getC_BPartner_ID(), inOuts[i].getMovementType(), 
							inOuts[i].getMovementDate());
					MInvoice inv = mapInvoice.get(key);
					if (inv == null)
					{
						inv = new MInvoice(inOuts[i], inOuts[i].getMovementDate());
						inv.setDescription("***Auto create from process not"
								+ " invoiced receipt processor***");
						mapInvoice.put(key, inv);
					}
					if (!inv.save())
						return CLogger.retrieveErrorString("Could not create invoice");
					
					MInvoiceLine newInvLine = new MInvoiceLine(inv);
					newInvLine.setShipLine(lines[j]);
					newInvLine.setQty(notMatchedQty);
					
					if (!newInvLine.save())
						return CLogger.retrieveErrorString("Could not save invoice line");
					notMatchedQty = Env.ZERO;
					Env.setContext(Env.getCtx(), "ON_ProcessNotInvoiceReceiptProcessor", true);
				}
			}
		}
		
		Enumeration<MInvoice> enumeration = mapInvoice.elements();
		Env.setContext(Env.getCtx(), "ON_ProcessNotInvoiceReceiptProcessor", true);
		while (enumeration.hasMoreElements())
		{
			MInvoice inv = enumeration.nextElement();
			if (!inv.processIt(DocAction.ACTION_Complete))
				return inv.getProcessMsg();
			if (!inv.save())
				return CLogger.retrieveErrorString("Could not complete invoice");
		}
		Env.setContext(Env.getCtx(), "ON_ProcessNotInvoiceReceiptProcessor", false);

		String dir = System.getProperty("user.home");
		String fileName = "UnresolvedNotInvoiceReceipt.csv";
		if (!dir.endsWith("\\"))
			dir += "\\";
		fileName = dir + fileName;
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		String unresolvedToStr = unresolvedLogBuilder.toString();
		String[] unresolveds = unresolvedToStr.split("&");
		for (String unresolved : unresolveds)
		{
			writer.write(unresolved);
			writer.write("\n");
		}
		writer.close();
		fileWriter.close();
		return null;
	}
	
	private String mapInvKeyGen (int orgID, int partnerID, String movementType, Timestamp date)
	{
		int year = DB.getSQLValue(get_TrxName(), "SELECT EXTRACT (YEAR FROM ?::TIMESTAMP)", date);
		StringBuilder sb = new StringBuilder("INV_");
		sb.append(movementType).append(year).append(partnerID);
		String retKey = sb.toString();
		return retKey;
	}
	
	private MInOut[] getErrorPostInOuts ()
	{
		log.log(Level.INFO, "Collect all error posted receipt and vendor return..");
		processUI.statusUpdate("Collect all error posted receipt and vendor return..");
		String where = "DocStatus IN ('CO','CL') AND Posted = 'E' AND MovementType "
				+ " IN ('V+','V-') AND MovementDate BETWEEN ? AND ? ";
		List<MInOut> list = new Query (getCtx(), MInOut.Table_Name, where, get_TrxName()).setOrderBy(
				MInOut.COLUMNNAME_MovementDate).setParameters(m_from, m_to).list();
		return list.toArray(new MInOut[list.size()]);
	}
}
