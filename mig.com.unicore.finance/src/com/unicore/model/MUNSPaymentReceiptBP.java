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
import org.compiere.util.Util;

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;

/**
 * @author setyaka
 *
 */
public class MUNSPaymentReceiptBP extends X_UNS_PaymentReceipt_BP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2991578735343600716L;
	
	private MUNSPaymentReceiptInv[] m_lines;

	/**
	 * @param ctx
	 * @param UNS_PaymentReceipt_BP_ID
	 * @param trxName
	 */
	public MUNSPaymentReceiptBP(Properties ctx, int UNS_PaymentReceipt_BP_ID, String trxName) {
		super(ctx, UNS_PaymentReceipt_BP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPaymentReceiptBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPaymentReceiptBP(MUNSBillingResult br) {
		this(br.getCtx(), 0, br.get_TrxName());
		
		setClientOrg(br);
		setUNS_Billing_Result_ID(br.get_ID());

		setDifferenceAmt (Env.ZERO);
		setIsApproved (false);
		setPaidAmt (Env.ZERO);
		setReceiptAmt (Env.ZERO);
		setTotalAmt (Env.ZERO);		
	}

	/**************************************************************************
	 * Get Lines
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @return orders
	 */
	public MUNSPaymentReceiptInv[] getLines(String whereClause, String orderClause) {
		// red1 - using new Query class from Teo / Victor's MDDOrder.java
		// implementation
		StringBuilder whereClauseFinal = new StringBuilder(MUNSPaymentReceiptInv.COLUMNNAME_UNS_PaymentReceipt_BP_ID
				+ "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPaymentReceiptInv.COLUMNNAME_UNS_PaymentReceipt_Inv_ID;
		//
		List<MUNSPaymentReceiptInv> list = Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
				MUNSPaymentReceiptInv.Table_Name, whereClauseFinal.toString(), get_TrxName()).setParameters(
				get_ID()).setOrderBy(orderClause).list();

		return list.toArray(new MUNSPaymentReceiptInv[list.size()]);
	} // getLines

	/**
	 * Get Lines
	 * 
	 * @param requery
	 * @param orderBy
	 * @return lines
	 */
	public MUNSPaymentReceiptInv[] getLines(boolean requery, String orderBy) {
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_lines = getLines(null, orderClause);
		return m_lines;
	} // getLines

	/**
	 * Get Lines 
	 * 
	 * @return lines
	 */
	public MUNSPaymentReceiptInv[] getLines() {
		return getLines(false, null);
	} // getLines

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return updateHeaderAmount();
	}

	private boolean updateHeaderAmount() {

		MUNSPaymentReceipt pr = new MUNSPaymentReceipt(getCtx(), getUNS_PaymentReceipt_ID(),
				get_TrxName());

		// Find Total Bill Amount
		String sql = "SELECT SUM(ReceiptAmt) FROM UNS_PaymentReceipt WHERE UNS_PaymentReceipt_ID = ? AND IsActive='Y' ";
		BigDecimal receiptAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PaymentReceipt_ID());

		pr.setReceiptAmt(receiptAmt);
		pr.setDifferenceAmt(pr.getPaidAmt().subtract(receiptAmt));

		if (!pr.save(get_TrxName()))
		{
			log.saveError("Cannot Update Header", "Cannot Save Header");
			return false;
		}

		return true;
	}
}
