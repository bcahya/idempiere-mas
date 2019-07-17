/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.acct.DocManager;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInOut;
//import org.compiere.model.MInventory;
import org.compiere.model.MInvoice;
import org.compiere.model.MMatchInv;
//import org.compiere.model.MMovement;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MTable;
//import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

/**
 * @author root
 *
 */
public class UNSCompletingCoreDocument extends SvrProcess {
	
	private Timestamp m_dateFrom = null;
	private Timestamp m_dateTo = null;
	private String m_errorLog = "";
	private final String INITIAL_IMPORT_CTX = "ON_IMPORT";
	private MAcctSchema[] m_ass = null;

	private IProcessUI m_processMonitor;
	
	/**
	 * 
	 */
	public UNSCompletingCoreDocument() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				continue;
			if(param.getParameterName().equals("DateFrom"))
				m_dateFrom = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("DateTo"))
				m_dateTo = param.getParameterAsTimestamp();
			else
				log.log(Level.WARNING, "UNKNOWN PARAMETER " .concat(param.getParameterName()));
		}
		
		m_ass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		m_processMonitor = Env.getProcessUI(getCtx());
		StringBuilder specRegion = new StringBuilder(" AD_Org_ID IN (SELECT AD_Org_ID FROM AD_Org WHERE ")
		.append(MOrg.COLUMNNAME_C_SalesRegion_ID).append("=1000000)"); // Only Depo Pusat.
		StringBuilder specRegionManufacture = new StringBuilder(" AD_Org_ID IN (SELECT AD_Org_ID FROM AD_Org WHERE ")
		.append(MOrg.COLUMNNAME_C_SalesRegion_ID).append("=1000005)"); //Manufacturing region
		StringBuilder includeSpecificRegion = new StringBuilder("(").append(specRegion).append(" OR ").append(specRegionManufacture).append(")");
//		StringBuilder specRegionSQLOnly = specRegion;
		
		List<CompleteHelper> cHelpers = new ArrayList<>();
		cHelpers.add(new CompleteHelper(MInvoice.Table_Name, MInvoice.COLUMNNAME_DateInvoiced,"UNSOrderModelFactory", 
				MInvoice.COLUMNNAME_IsSOTrx.concat("='N' AND ").concat(includeSpecificRegion.toString())));
//		cHelpers.add(new CompleteHelper(MInOut.Table_Name, MInOut.COLUMNNAME_MovementDate,"UNSOrderModelFactory",
//				"((".concat(MInOut.COLUMNNAME_IsSOTrx).concat("='N' OR C_DocType_ID = 1000015))")/**.concat(includeSpecificRegion.toString())*/));
//		cHelpers.add(new CompleteHelper(MMovement.Table_Name, MMovement.COLUMNNAME_MovementDate, "UniCoreMaterialManagementModelFactory",
//				/**specRegionSQLOnly.toString()*/ null));
//		cHelpers.add(new CompleteHelper("UNS_Production", "MovementDate", null, null));
		cHelpers.add(new CompleteHelper(MInvoice.Table_Name, MInvoice.COLUMNNAME_DateInvoiced,"UNSOrderModelFactory",  
				MInvoice.COLUMNNAME_IsSOTrx.concat("='Y' AND ") .concat(includeSpecificRegion.toString())));
//		cHelpers.add(new CompleteHelper(MInOut.Table_Name, MInOut.COLUMNNAME_MovementDate,"UNSOrderModelFactory", 
//				MInOut.COLUMNNAME_IsSOTrx.concat("='Y'").concat(
//						" AND AD_Org_ID NOT IN (1000008,1000009,1000010) AND CreatedBy = (SELECT AD_User_ID FROM AD_User WHERE Name = 'FLGAdmin')")
//						/**.concat(includeSpecificRegion.toString())*/));	
//		cHelpers.add(new CompleteHelper(MDiscountSchema.Table_Name, "ValidTo","UNSOrderModelFactory", null));
		cHelpers.add(new CompleteHelper(MPayment.Table_Name, MPayment.COLUMNNAME_DateTrx,"UNSFinancialModelFactory", includeSpecificRegion.toString()));
//		cHelpers.add(new CompleteHelper(MInventory.Table_Name, MInventory.COLUMNNAME_MovementDate,null, 
//				/**specRegionSQLOnly.toString()*/ null));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(m_dateTo.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		m_dateTo = new Timestamp(calendar.getTimeInMillis());
		
		completeSession(cHelpers, m_dateFrom);
		
		if(!Util.isEmpty(m_errorLog, true))
		{
			m_errorLog = "Process stoped ".concat(m_errorLog);
		}
		
		return m_errorLog;
	}

	/**
	 * 
	 * @param helpers
	 * @param date
	 */
	private void completeSession(List<CompleteHelper> cHelpers, Timestamp date)
	{
		while (date.compareTo(m_dateTo) <= 0) 
		{	
			String mainStatusMsg = new StringBuilder("Date: ").append(date).toString(); 
			m_processMonitor.statusUpdate("================= Processing " + mainStatusMsg + " =================");
			for(int i=0; i< cHelpers.size(); i++)
			{
				StringBuilder docStatusMsg = 
						new StringBuilder(mainStatusMsg).append(" Document of ")
						.append(cHelpers.get(i).TABLE_NAME);
				
				m_processMonitor.statusUpdate(docStatusMsg.toString());
				
				doComplete(cHelpers.get(i), date);
				
				if(cHelpers.get(i).TABLE_NAME.equals(MInOut.Table_Name) && Util.isEmpty(m_errorLog, true))
					doMatch(date);
				
				if(!Util.isEmpty(m_errorLog, true))
				{
					m_errorLog = m_errorLog.concat(" \n Last processed date ") + date;
					return;
				}
			}
			
			date = DB.getSQLValueTS(get_TrxName(), (new StringBuilder("SELECT '").append(date)
					.append("'::TIMESTAMP + INTERVAL '1 DAYS'")).toString());
		}
	}
	
	
	/**
	 * Post Document
	 * @param helper
	 * @param date
	 */
	private void doComplete(CompleteHelper helper, Timestamp date)
	{
		String completeTrx = Trx.createTrxName("COD");
		Trx coTrx = Trx.get(completeTrx, true);
		
		MTable table = MTable.get(getCtx(), helper.TABLE_NAME);
		String msg = "Date: " + date + ":" + helper.TABLE_NAME;
		log.info(msg);
		m_processMonitor.statusUpdate(msg);
		if(!Util.isEmpty(helper.EXTENSION_HANDLER, true))
		{
			table.setExtensionHandler(helper.EXTENSION_HANDLER);
		}
		else
		{
			table.setExtensionHandler(null);
		}

		Env.setContext(Env.getCtx(), INITIAL_IMPORT_CTX, true);
		
		StringBuilder sb = new StringBuilder("SELECT ").append(helper.TABLE_NAME).append("_ID")
				.append(" FROM ").append(helper.TABLE_NAME)
				.append(" WHERE (").append(helper.DATE_COLUMN).append("::TIMESTAMP)::DATE = ('")
				.append(date).append("'::TIMESTAMP)::DATE AND DocStatus IN ('DR','IP','IN')");
		
		if (!Util.isEmpty(helper.WHERECLAUSE, true))
			sb.append(" AND ").append(helper.WHERECLAUSE);
		
		PreparedStatement st = null;
		ResultSet rs  = null;
		try
		{
			st = DB.prepareStatement(sb.toString(), completeTrx);
			rs = st.executeQuery();
			String errorMsg = msg;
			while ( rs.next())
			{
				PO po = table.getPO(rs.getInt(1), completeTrx);
				
				int docNoIx = po.get_ColumnIndex("DocumentNo");
				if (docNoIx > -1) {
					String docNo = po.get_ValueAsString("DocumentNo");
					errorMsg = msg + ":" + docNo;
					m_processMonitor.statusUpdate(msg + ":" + docNo);
				}
				
				if (po instanceof MPayment) {
					completePayment((MPayment) po);
				}
				else if(po instanceof MInOut)
				{
					MInOut inout = (MInOut) po;
					boolean ok = inout.processIt(DocAction.ACTION_Complete);
					if(!ok)
					{
						m_errorLog = inout.getProcessMsg();
						break;
					}
				}
				else {
					
					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(po, DocAction.ACTION_Complete);
					if(pi.isError())
					{
						m_errorLog = m_errorLog.concat(errorMsg).concat(pi.getSummary());
						break;
					}
				}
			}
		}
		catch ( SQLException e)
		{
			e.printStackTrace();
			m_errorLog = m_errorLog.concat(e.getMessage());
		}
		finally
		{
			DB.close(rs, st);
			coTrx.commit();
			coTrx.close();
			coTrx = null;			
			Env.setContext(Env.getCtx(), INITIAL_IMPORT_CTX, false);
		}
	}
	
	private void doMatch(Timestamp date)
	{
		//dont include vendor return.
		String dontIncludeReturn = " AND M_InoutLine_ID NOT IN (SELECT M_InoutLine_ID FROM M_Inout "
				+ "WHERE M_InOut_ID IN (SELECT M_Inout_ID FROM M_Inout WHERE C_DocType_ID = 1000013))";
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(MMatchInv.Table_Name)
				.append(" WHERE ").append("DateTrx").append("::TIMESTAMP::DATE = '").append(date)
				.append("'::TIMESTAMP::DATE AND Posted <> 'Y'")
				.append(dontIncludeReturn);
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String trxName = Trx.createTrxName("POST");
		Trx myTrx = Trx.get(trxName, true);
		
		try
		{
			st = DB.prepareStatement(sb.toString(), trxName);
			rs = st.executeQuery();
			while (rs.next())
			{
				String posted = rs.getString("Posted");
				boolean repost = !posted.equals("N");
				String error = DocManager.postDocument(m_ass, MMatchInv.Table_ID, rs, true, repost, trxName);
				if(!Util.isEmpty(error, true))
				{
					m_errorLog = m_errorLog.concat(error).concat("\n ********************** \n");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
			try
			{
				myTrx.commit();
				myTrx.close();
				myTrx = null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	String completePayment(MPayment payment)
	{
		if (payment.isComplete())
			return null;
		
		MPaymentAllocate paList[] = MPaymentAllocate.get(payment);
		
		if (paList.length == 0)
			return null;
		
//		payment.setDocStatus(DocAction.STATUS_Completed);
//		payment.setDocAction(DocAction.ACTION_Close);
//		payment.setProcessed(true);
//		payment.disableModelValidation();
//		payment.saveEx();
//		
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, 
				payment.getDateTrx(), payment.getC_Currency_ID(), 
					Msg.translate(getCtx(), "C_Payment_ID")	+ ": " + payment.getDocumentNo(), 
					payment.get_TrxName());
		alloc.setAD_Org_ID(payment.getAD_Org_ID());
		alloc.setDateAcct(payment.getDateAcct());
		alloc.disableModelValidation();
		if (!alloc.save()) {
			return "Payment Allocation header not created.";
		}
			
		MBPartner bpartner = new MBPartner (getCtx(), payment.getC_BPartner_ID(), payment.get_TrxName());
		
		for (MPaymentAllocate pa : paList)
		{
			MAllocationLine aLine = null;
			if (payment.isReceipt())
				aLine = new MAllocationLine (alloc, pa.getAmount(), 
					pa.getDiscountAmt(), pa.getWriteOffAmt(), pa.getOverUnderAmt());
			else
				aLine = new MAllocationLine (alloc, pa.getAmount().negate(), 
					pa.getDiscountAmt().negate(), pa.getWriteOffAmt().negate(), pa.getOverUnderAmt().negate());
			
			MInvoice invoice = (MInvoice) pa.getC_Invoice();
		
			aLine.setDocInfo(invoice.getC_BPartner_ID(), 0, pa.getC_Invoice_ID());
			aLine.setPaymentInfo(payment.getC_Payment_ID(), 0);
			aLine.disableModelValidation();
			if (!aLine.save())
				log.warning("P.Allocations - line not saved");
			else {
				pa.setC_AllocationLine_ID(aLine.getC_AllocationLine_ID());
				pa.disableModelValidation();
				pa.saveEx();
			}
			
			invoice.testAllocation();
			invoice.disableModelValidation();
			invoice.saveEx();
			
			//Update BPartner Balance.
			int C_Payment_ID = aLine.getC_Payment_ID();
			int C_BPartner_ID = aLine.getC_BPartner_ID();
			int M_Invoice_ID = aLine.getC_Invoice_ID();

			if ((C_BPartner_ID == 0) || ((M_Invoice_ID == 0) && (C_Payment_ID == 0)))
				continue;

			boolean isSOTrxInvoice = false;
			//MInvoice invoice = M_Invoice_ID > 0 ? new MInvoice (getCtx(), M_Invoice_ID, get_TrxName()) : null;
			if (M_Invoice_ID > 0)
				isSOTrxInvoice = invoice.isSOTrx();
			
			DB.getDatabase().forUpdate(bpartner, 0);

			BigDecimal openBalanceDiff = Env.ZERO;
			
			boolean paymentProcessed = false;
			boolean paymentIsReceipt = false;
			
			int convTypeID = 0;
			Timestamp paymentDate = null;
			
			convTypeID = payment.getC_ConversionType_ID();
			paymentDate = payment.getDateAcct();
			//paymentProcessed = payment.isProcessed();
			paymentIsReceipt = payment.isReceipt();
					
			// Adjust open amount with allocated amount. 
			if (invoice != null)
			{
				// If payment is already processed, only adjust open balance by discount and write off amounts.
				BigDecimal amt = MConversionRate.convertBase(getCtx(), aLine.getWriteOffAmt().add(aLine.getDiscountAmt()),
						alloc.getC_Currency_ID(), paymentDate, convTypeID, getAD_Client_ID(), alloc.getAD_Org_ID());
				if (amt == null)
				{
					return "Could not convert allocation C_Currency_ID=" + alloc.getC_Currency_ID()
					+ " to base C_Currency_ID=" + MClient.get(getCtx()).getC_Currency_ID() + ", C_ConversionType_ID=" + convTypeID
					+ ", conversion date= " + paymentDate;
				}
				openBalanceDiff = openBalanceDiff.add(amt);
			}
			
			//	Total Balance
			BigDecimal newBalance = bpartner.getTotalOpenBalance();
			if (newBalance == null)
				newBalance = Env.ZERO;
			
			BigDecimal originalBalance = new BigDecimal(newBalance.toString());

			if (openBalanceDiff.signum() != 0) {
				newBalance = newBalance.subtract(openBalanceDiff);
			}

			// Update BP Credit Used only for Customer Invoices and for payment-to-payment allocations.
			BigDecimal newCreditAmt = Env.ZERO;
			if (isSOTrxInvoice || (invoice == null && paymentIsReceipt && paymentProcessed))
			{
				if (invoice == null)
					openBalanceDiff = openBalanceDiff.negate();

				newCreditAmt = bpartner.getSO_CreditUsed();

				if (newCreditAmt == null)
					newCreditAmt = openBalanceDiff.negate();
				else
					newCreditAmt = newCreditAmt.subtract(openBalanceDiff);

				bpartner.setSO_CreditUsed(newCreditAmt);
			}
			else
			{
				if (log.isLoggable(Level.FINE))
				{
					log.fine("TotalOpenBalance=" + bpartner.getTotalOpenBalance() + "(" + openBalanceDiff
							+ ", Balance=" + bpartner.getTotalOpenBalance() + " -> " + newBalance);				
				}
			}

			if (newBalance.compareTo(originalBalance) != 0)
				bpartner.setTotalOpenBalance(newBalance);
			
			bpartner.setSOCreditStatus();
		}

		payment.setDocStatus(DocAction.STATUS_Completed);
		payment.setDocAction(DocAction.ACTION_Close);
		payment.setProcessed(true);
		payment.disableModelValidation();
		payment.saveEx();
		
		alloc.disableModelValidation();
		alloc.setDocStatus(DocAction.STATUS_Completed);
		alloc.setDocAction(DocAction.ACTION_Close);
		alloc.setProcessed(true);
		if (!alloc.save()) {
			return "Payment Allocation header not created.";
		}
			
		
		if (!bpartner.save())
		{
			return "Could not update Business Partner";
		}
 
//		String error = DocManager.postDocument(m_ass, MPayment.Table_ID, payment.get_ID(), true, true, payment.get_TrxName());
//		if(!Util.isEmpty(error, true)) {
//			return m_errorLog.concat(error).concat("\n ********************** \n");
//		}
//		
//		error = DocManager.postDocument(m_ass, MAllocationHdr.Table_ID, alloc.get_ID(), true, true, payment.get_TrxName());
//		if(!Util.isEmpty(error, true)) {
//			return m_errorLog.concat(error).concat("\n ********************** \n");
//		}
		
		return null;
	}
	
	public String completePAllocation()
	{
		String sql = "SELECT * FROM UNS_PR_Allocation WHERE UNS_PR_Allocation_ID IN"
				+ " (SELECT UNS_PR_Allocation_ID FROM C_Payment WHERE C_Payment_ID IN"
				+ " (SELECT C_Payment_ID FROM C_Payment WHERE AD_Org_ID IN"
				+ " (SELECT AD_Org_ID FROM AD_Org WHERE C_SalesRegion_ID IN"
				+ " (SELECT C_SalesRegion_ID FROM C_SalesRegion WHERE Value = 'MAN' OR Value = 'PST')))"
				+ " AND DateTrx BETWEEN ? AND ?)";
		
		ResultSet rs=null;
		PreparedStatement pstmt= null;
//		int count = 0;
		
		
		try {
			pstmt = DB.prepareStatement(sql,get_TrxName());
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
//				count ++;
			}
		} catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
		}		
						
		return m_errorLog;
	}
}

class CompleteHelper
{
	String TABLE_NAME = null;
	String DATE_COLUMN = null;
	String WHERECLAUSE = null;
	String EXTENSION_HANDLER = null;

	/**
	 * 
	 * @param tableName
	 * @param tableID
	 * @param dateColumn
	 * @param whereClause
	 */
	public CompleteHelper(String tableName,String dateColumn, String extensionHandler, String whereClause)
	{
		this.TABLE_NAME = tableName; 
		this.DATE_COLUMN = dateColumn; 
		this.EXTENSION_HANDLER = extensionHandler;
		this.WHERECLAUSE = whereClause;
	}
}