/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSBillingTrfInvoice extends X_UNS_BillingTrf_Invoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3392325799450457625L;

	/**
	 * @param ctx
	 * @param UNS_BillingTrf_Invoice_ID
	 * @param trxName
	 */
	public MUNSBillingTrfInvoice(Properties ctx, int UNS_BillingTrf_Invoice_ID,
			String trxName) {
		super(ctx, UNS_BillingTrf_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingTrfInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSBillingTrfInvoice getCreate(Properties ctx, int UNS_BillingTrf_Detail_ID, 
													int C_Invoice_ID, BigDecimal Amt, String trxName)
	{
		MUNSBillingTrfInvoice trfInv = null;
		
		String whereClause = "UNS_BillingTrf_Detail_ID = ? AND C_Invoice_ID = ?";
		
		trfInv = new Query(ctx, Table_Name, whereClause, trxName).
						setParameters(UNS_BillingTrf_Detail_ID, C_Invoice_ID).first();
		
		if(trfInv == null)
		{
			trfInv = new MUNSBillingTrfInvoice(ctx, 0, trxName);
			MInvoice inv = MInvoice.get(ctx, C_Invoice_ID);
			trfInv.setUNS_BillingTrf_Detail_ID(UNS_BillingTrf_Detail_ID);
			trfInv.setInvoice(inv);
			trfInv.setAmount(Amt);
			trfInv.setAmountTrf(Amt);
			trfInv.saveEx();
		}
		else
		{
			if(trfInv.getAmountTrf().compareTo(Amt) != 0)
			{
				trfInv.setAmountTrf(Amt);;
				trfInv.saveEx();
			}
		}
		
		return trfInv;
	}
	
	public void setInvoice(MInvoice inv)
	{
		setClientOrg(inv);
		setC_Invoice_ID(inv.get_ID());
		setC_BPartner_ID(inv.getC_BPartner_ID());
		setGrandTotal(inv.getGrandTotal());
		setDateInvoiced(inv.getDateInvoiced());
		setDescription(inv.getDescription());
		setTotalAmt(getTotalTrf());
	}
	
	public BigDecimal getTotalTrf()
	{
		BigDecimal TotalTransfer = Env.ZERO;
		
		String sql = "SELECT COALESCE(SUM(bti.AmountTrf),0) FROM UNS_BillingTrf_Invoice bti WHERE bti.C_Invoice_ID=?"
				+ " AND EXISTS (SELECT 1 FROM UNS_BillingTrf_Detail btd WHERE btd.UNS_BillingTrf_Detail_ID"
				+ " = bti.UNS_BillingTrf_Detail_ID AND EXISTS (SELECT 1 FROM UNS_BillingTrfValidation btv WHERE"
				+ " btv.UNS_BillingTrfValidation_ID = btd.UNS_BillingTrfValidation_ID AND btv.DocStatus NOT IN ('VO', 'RE')))"
				+ " AND bti.UNS_BillingTrf_Invoice_ID <> ?";
		TotalTransfer = DB.getSQLValueBD(get_TrxName(), sql, getC_Invoice_ID(), get_ID());
		
		return TotalTransfer;
	}
	
	public boolean beforeSave(boolean newRecord)
	{ 
		if(newRecord || is_ValueChanged(COLUMNNAME_C_Invoice_ID))
			setTotalAmt(getTotalTrf());
		return true;
	}
}