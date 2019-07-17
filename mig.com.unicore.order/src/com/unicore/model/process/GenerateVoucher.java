/**
 * 
 */
package com.unicore.model.process;

import java.util.Hashtable;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.model.MUNSVoucher;
import com.uns.model.MUNSVoucherCode;

/**
 * @author root
 *
 */
public class GenerateVoucher extends SvrProcess {
	
	private boolean m_deleteExsisting = false;
	private Hashtable<String, MUNSVoucherCode> m_existing = new Hashtable<>();

	/**
	 * 
	 */
	public GenerateVoucher() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] pis = getParameter();
		for(ProcessInfoParameter pi : pis)
		{
			String name = pi.getParameterName();
			if(name.equals("DelteExisting"))
				m_deleteExsisting = pi.getParameterAsBoolean();
			else
				log.warning("Unknown parameter " .concat(name));
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MUNSVoucher voucher = new MUNSVoucher(getCtx(), getRecord_ID(), get_TrxName());
		IProcessUI ui = Env.getProcessUI(Env.getCtx());
		if(m_deleteExsisting)
		{
			ui.statusUpdate("Deleting existing record.");
			String sql = "DELETE FROM " .concat(MUNSVoucherCode.Table_Name).concat(" WHERE ")
					.concat(MUNSVoucherCode.COLUMNNAME_UNS_Voucher_ID).concat(" = ?");
			boolean ok = DB.executeUpdate(sql, voucher.get_ID(), get_TrxName()) != -1;
			if(!ok)
			{
				throw new AdempiereException(Msg.getMsg("", "Failed when delete previous record"));
			}
		}
		else
		{
			ui.statusUpdate("Loading existing record.");
			loadExisting(voucher);
		}
		int startNo = voucher.getStartNo();
		int endNo = voucher.getEndNo();
		String prefix = voucher.getPrefix() == null ? "" : voucher.getPrefix();
		String suffix = voucher.getSuffix() == null ? "" : voucher.getSuffix();
		int totalRecordToCreate = endNo - startNo + 1;
		int successCount = 0;
		
		for(int i=startNo; i<=endNo; i++)
		{
			String name = prefix + i + suffix;
			MUNSVoucherCode voucherCode = m_existing.get(name);
			if(null != voucherCode)
			{
				ui.statusUpdate("Ignored already exists data with code " .concat(name));
				continue;
			}
			
			voucherCode = new MUNSVoucherCode(voucher);
			voucherCode.setDescription("::Auto Generate By System::");
			voucherCode.setIsUsed(false);
			voucherCode.setName(name);
			
			try
			{
				voucherCode.saveEx();
				m_existing.put(name, voucherCode);
			}
			catch (Exception e)
			{
				throw new AdempiereException(e.getMessage());
			}
			
			successCount++;
			ui.statusUpdate("Created Record " + successCount + " Of " + totalRecordToCreate + " || Voucher[" + name + "]");
		}
		return successCount + " record success fully created.";
	}
	
	private void loadExisting(MUNSVoucher voucher)
	{
		MUNSVoucherCode[] existings = voucher.getVoucherCodes(false);
		for(MUNSVoucherCode code : existings)
		{
			String msg = "Loading Voucher Code " .concat(code.getName());
			log.log(Level.INFO, msg);
			
			m_existing.put(code.getName(), code);
		}
	}
}
