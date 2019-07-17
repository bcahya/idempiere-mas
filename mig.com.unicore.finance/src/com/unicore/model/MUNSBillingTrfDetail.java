/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author ALBURHANY
 *
 */
public class MUNSBillingTrfDetail extends X_UNS_BillingTrf_Detail {

	private String m_message = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -845297903963983103L;

	/**
	 * @param ctx
	 * @param UNS_BillingTrf_Detail_ID
	 * @param trxName
	 */
	public MUNSBillingTrfDetail(Properties ctx, int UNS_BillingTrf_Detail_ID,
			String trxName) {
		super(ctx, UNS_BillingTrf_Detail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingTrfDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(is_ValueChanged(COLUMNNAME_UNS_PaymentReceipt_ID) || getUNS_PaymentReceipt_ID() > 0)
		{
			String sql = "SELECT SalesRep_ID FROM UNS_BillingGroup WHERE UNS_BillingGroup_ID IN "
					+ " (SELECT UNS_BillingGroup_ID FROM UNS_BillingGroup_Result WHERE UNS_BillingGroup_Result_ID IN"
					+ " (SELECT UNS_BillingGroup_Result_ID FROM UNS_PaymentReceipt WHERE UNS_PaymentReceipt_ID = "
					+ getUNS_PaymentReceipt_ID() + "))";
			int SalesRep_ID = DB.getSQLValue(get_TrxName(), sql);
			
			if(SalesRep_ID <= 0)
				throw new AdempiereException("Cannot get Sales Repsentative for this Payment Receipt");
			else
				setSalesRep_ID(SalesRep_ID);
			
			MUNSPaymentReceipt pr = new MUNSPaymentReceipt(getCtx(), getUNS_PaymentReceipt_ID(), get_TrxName());
			String sqlAmtTrf = "SELECT COALESCE(SUM(AmountTrf),0) FROM UNS_BillingTrf_Detail WHERE UNS_PaymentReceipt_ID=?"
					+ " AND UNS_BillingTrfValidation_ID IN (SELECT UNS_BillingTrfValidation_ID FROM UNS_BillingTrfValidation"
					+ " WHERE DocStatus <> 'VO') AND UNS_BillingTrf_Detail_ID <> " + get_ID();
			BigDecimal TotalAmtTrf = DB.getSQLValueBD(get_TrxName(), sqlAmtTrf, pr.get_ID());
			setAD_Org_ID(pr.getAD_Org_ID());
			setAmount(pr.getReceiptAmt());
			setTotalAmt(TotalAmtTrf); // total has transferred Y
			if(getAmountTrf().compareTo(Env.ZERO) == 0)
				setAmountTrf(pr.getReceiptAmt().subtract(TotalAmtTrf)); //total do transferred Z
			setDifferenceAmt(getAmount().subtract(TotalAmtTrf).subtract(getAmountTrf())); //Difference X-(Y+Z)
		}
		
		if(is_ValueChanged(COLUMNNAME_C_Payment_ID) || getC_Payment_ID() > 0)
		{
			MPayment payment = (MPayment) getC_Payment();
			String sql = "SELECT COALESCE(SUM(AmountTrf),0) FROM UNS_BillingTrf_Detail WHERE C_Payment_ID=?"
					+ " AND UNS_BillingTrfValidation_ID IN (SELECT UNS_BillingTrfValidation_ID FROM UNS_BillingTrfValidation"
					+ " WHERE DocStatus <> 'VO') AND UNS_BillingTrf_Detail_ID <> " + get_ID();
			BigDecimal TotalAmtTrf = DB.getSQLValueBD(get_TrxName(), sql, payment.get_ID());
			setAD_Org_ID(payment.getAD_Org_ID());
			setAmount(payment.getPayAmt()); // total payment X
			setTotalAmt(TotalAmtTrf); // total has transferred Y
			if(getAmountTrf().compareTo(Env.ZERO) == 0)
				setAmountTrf(payment.getPayAmt().subtract(TotalAmtTrf)); //total do transferred Z
			setDifferenceAmt(getAmount().subtract(TotalAmtTrf).subtract(getAmountTrf())); //Difference X-(Y+Z)
			setSalesRep_ID(payment.getSalesRep_ID());
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_AmountTrf))
		{
			if(getAmountTrf().compareTo(getAmount()) == 1)
			{
				log.saveError("Error", "Amount transferred cant bigger than grand total invoice");
				return false;
			}
			BigDecimal diffNow = ((BigDecimal) get_ValueOld(COLUMNNAME_AmountTrf)).subtract(getAmountTrf());
			for(MUNSBillingTrfInvoice tfi : getInvoices())
			{
				if(tfi.getAmountTrf().compareTo(diffNow) == 0)
				{
					tfi.delete(true);
					break;
				}
				
				if(tfi.getAmountTrf().compareTo(diffNow) == -1)
				{
					diffNow = diffNow.subtract(tfi.getAmountTrf());
					tfi.delete(true);
					continue;
				}
				
				if(tfi.getAmountTrf().compareTo(diffNow) == 1)
				{
					tfi.setAmountTrf(tfi.getAmountTrf().subtract(diffNow));
					tfi.setDescription(tfi.getDescription() + " ::Auto subtract amount transfer:: by System");
					tfi.saveEx();
					break;
				}
			}
		}
		
		return true;
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		if(newRecord || is_ValueChanged(COLUMNNAME_AmountTrf))
		{
			generateInvoices();
		}
		
		if(null != updateHeader())
		{
			log.saveError("Error", m_message);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean beforeDelete()
	{		
		DB.executeUpdate("DELETE FROM UNS_BillingTrf_Invoice WHERE UNS_BillingTrf_Detail_ID = ?", get_ID(), get_TrxName());
		return true;
	}
	
	@Override
	public boolean afterDelete(boolean success)
	{
		if(null != updateHeader())
		{
			log.saveError("Error", m_message);
			return false;
		}

		return true;
	}
	
	public String updateHeader()
	{
		m_message = null;
		MUNSBillingTrfValidation tv = new MUNSBillingTrfValidation(getCtx(), getUNS_BillingTrfValidation_ID(), get_TrxName());
		String sql = "UPDATE UNS_BillingTrfValidation SET AmountTrf = " + tv.TotalAmt()
				+ " WHERE UNS_BillingTrfValidation_ID=?";
		int count = DB.executeUpdate(sql, tv.get_ID(), get_TrxName());
		if(count < 0)
			m_message= "Failed when trying update amount header";
		
		return m_message;
	}
	
	public static BigDecimal getTotalTrf(String TableName, int Record_ID, String trxName)
	{
		BigDecimal TotalTransfer = Env.ZERO;
		
		String sql = "SELECT COALESCE(SUM(AmountTrf),0) FROM UNS_BillingTrf_Detail WHERE UNS_BillingTrfValidation_ID IN"
				+ " (SELECT UNS_BillingTrfValidation_ID FROM UNS_BillingTrfValidation WHERE DocStatus <> 'VO') AND "
				+ TableName + "_ID = ?";
		TotalTransfer = DB.getSQLValueBD(trxName, sql, Record_ID);
		
		return TotalTransfer;
	}
	
	private String generateInvoices()
	{
		String err = null;
		String sql = null;
		BigDecimal amountTrf = getAmountTrf();
		if(getUNS_PaymentReceipt_ID() > 0)
		{
			sql = "SELECT blr.C_Invoice_ID, blr.PaidAmt FROM UNS_BillingLine_Result blr WHERE EXISTS"
					+ " (SELECT 1 FROM UNS_Billing_Result br WHERE br.UNS_Billing_Result_ID = blr.UNS_Billing_Result_ID"
					+ " AND EXISTS (SELECT 1 FROM UNS_PaymentReceipt pr"
					+ " WHERE pr.UNS_BillingGroup_Result_ID = br.UNS_BillingGroup_Result_ID"
					+ " AND pr.UNS_PaymentReceipt_ID = ? AND pr.AD_Org_ID = " + getAD_Org_ID() + "))"
					+ " AND blr.PaymentStatus IN ('CG', 'PD')";
		}
		else if(getC_Payment_ID() > 0)
			sql = "SELECT pa.C_Invoice_ID, pa.Amount FROM C_PaymentAllocate pa"
					+ " WHERE pa.C_Payment_ID = ?";
		else
			return "Mandatory fill Payment OR Payment Receipt";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getUNS_PaymentReceipt_ID() > 0 ? getUNS_PaymentReceipt_ID() : getC_Payment_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int C_Invoice_ID = rs.getInt(1);
				BigDecimal paidAmt = rs.getBigDecimal(2);
				
				if(paidAmt.compareTo(amountTrf) >= 0)
					paidAmt = amountTrf;
				
				MUNSBillingTrfInvoice.getCreate(getCtx(), 
						get_ID(), C_Invoice_ID, paidAmt, get_TrxName());
				
				amountTrf = amountTrf.subtract(paidAmt);
				if(amountTrf.compareTo(Env.ZERO) == 0)
					break;
			}
		} catch (SQLException e) {
			return e.getMessage();
		}		
		
		return err;
		
	}
	
	public MUNSBillingTrfInvoice[] getInvoices() {
		List<MUNSBillingTrfInvoice> trfInvoice = new Query(getCtx(),
				MUNSBillingTrfInvoice.Table_Name,
				COLUMNNAME_UNS_BillingTrf_Detail_ID + "=?", get_TrxName())
				.setParameters(get_ID()).setOrderBy(X_UNS_BillingTrf_Invoice.COLUMNNAME_AmountTrf + " DESC").list();

		return trfInvoice.toArray(new MUNSBillingTrfInvoice[trfInvoice.size()]);
	}
	
	public static MUNSBillingTrfDetail getCreate(Properties ctx, int UNS_BillingTrfValidation_ID, 
													int C_Payment_ID, BigDecimal amt, String trxName)
	{
		MPayment pay = new MPayment(ctx, C_Payment_ID, trxName);
		MUNSBillingTrfDetail detail = null;
		detail = new Query(ctx, Table_Name, COLUMNNAME_UNS_BillingTrfValidation_ID + "=? AND " 
							+ COLUMNNAME_C_Payment_ID + "=?", trxName)
								.setParameters(UNS_BillingTrfValidation_ID, C_Payment_ID).first();
		BigDecimal amtTrf = getTotalTrf("C_Payment", C_Payment_ID, trxName);
		if(amtTrf == null)
			amtTrf = Env.ZERO;
		BigDecimal remaining = pay.getPayAmt().subtract(amtTrf);
		
		if(remaining.compareTo(Env.ZERO) == 0)
			return null;
		
		if(remaining.compareTo(amt) == 1)
			remaining = amt;
		
		if(detail == null)
		{
			detail = new MUNSBillingTrfDetail(ctx, 0, trxName);
			detail.setUNS_BillingTrfValidation_ID(UNS_BillingTrfValidation_ID);
			detail.setAD_Org_ID(pay.getAD_Org_ID());
			detail.setC_Payment_ID(pay.get_ID());
			detail.setAmount(pay.getPayAmt());
			detail.setAmountTrf(remaining);
			detail.saveEx();
		}
		else if(detail.getAmountTrf().compareTo(amt) != 0)
		{
			if(amt.compareTo(remaining) == 1)
				amt = remaining;
			detail.setAmountTrf(amt);
			detail.saveEx();
		}
		
		return detail;
	}
}