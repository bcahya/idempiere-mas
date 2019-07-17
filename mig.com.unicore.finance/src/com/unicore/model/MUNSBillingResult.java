/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author setyaka
 * 
 */
public class MUNSBillingResult extends X_UNS_Billing_Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4092460198142085656L;

	/**
	 * @param ctx
	 * @param UNS_Billing_Confirm_ID
	 * @param trxName
	 */
	public MUNSBillingResult(Properties ctx, int UNS_Billing_Confirm_ID, String trxName) {
		super(ctx, UNS_Billing_Confirm_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBillingResult(MUNSBillingGroupResult parent) {
		this(parent.getCtx(), 0, parent.get_TrxName());

		setClientOrg(parent);

		setIsApproved(false);
		setTotalAmt(Env.ZERO);
		setDifferenceAmt(Env.ZERO);
		setPaidAmt(Env.ZERO);
		setUNS_BillingGroup_Result_ID(parent.get_ID());
	}

	MUNSBillingLineResult[] m_lines = null;

	public MUNSBillingLineResult[] getLines(boolean query) {
		if (m_lines == null || query)
			m_lines = getLines(null);

		return m_lines;
	}

	public MUNSBillingLineResult[] getLines(String whereClause) {
		String whereClauseFinal = "UNS_Billing_Result_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;

		List<MUNSBillingLineResult> list =
				Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSBillingLineResult.Table_Name,
						whereClauseFinal, get_TrxName()).setParameters(new Object[] {getUNS_Billing_Result_ID()})
						.setOrderBy(MUNSBillingLineResult.COLUMNNAME_UNS_BillingLine_Result_ID).list();

		return list.toArray(new MUNSBillingLineResult[list.size()]);
	}
	
	protected boolean updateHeader()
	{
		MUNSBillingGroupResult bg = (MUNSBillingGroupResult) getUNS_BillingGroup_Result();

		String sql =
				"SELECT SUM(TotalAmt) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive='Y' ";
		BigDecimal grandtotal = DB.getSQLValueBD(get_TrxName(), sql, bg.getUNS_BillingGroup_Result_ID());

		sql = "SELECT SUM(PaidAmt) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive='Y' ";
		BigDecimal paidAmt = DB.getSQLValueBD(get_TrxName(), sql, bg.getUNS_BillingGroup_Result_ID());
		
		sql = "SELECT SUM(PaidAmtGiro) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive = 'Y'";
		BigDecimal paidAmtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_BillingGroup_Result_ID());
		
		sql = "SELECT SUM(ReceiptAmt) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive='Y'";
		BigDecimal receiptAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_BillingGroup_Result_ID());
		
		sql = "SELECT SUM(ReceiptAmtGiro) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive='Y'";
		BigDecimal receiptAmtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_BillingGroup_Result_ID());
		
		sql = "SELECT SUM(AmountTrf) FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = ? AND IsActive='Y'";
		BigDecimal amountTrf = DB.getSQLValueBD(get_TrxName(), sql, getUNS_BillingGroup_Result_ID());
		
		BigDecimal totalPaid = paidAmt.add(paidAmtGiro).add(amountTrf);
		BigDecimal differenceAmt = grandtotal.subtract(totalPaid);
		
		sql =
				"UPDATE UNS_BillingGroup_Result SET GrandTotal = " + grandtotal + ", paidAmt=" + paidAmt
						+ ", DifferenceAmt=" + differenceAmt + ", ReceiptAmt = "  + receiptAmt 
						+ ", ReceiptAmtGiro = " + receiptAmtGiro + ", PaidAmtGiro = " + paidAmtGiro 
						+ ", AmountTrf = " + amountTrf
						+ " WHERE UNS_BillingGroup_Result_ID =" + bg.getUNS_BillingGroup_Result_ID();
		int ok = DB.executeUpdate(sql, get_TrxName());

		return (ok != -1);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		return updateHeader();
	}
	
	@Override 
	protected boolean afterDelete(boolean success)
	{
		return updateHeader();
	}

}
