package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MUNSPaymentReceiptInv extends X_UNS_PaymentReceipt_Inv {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9188345054818477303L;

	/**
	 * @param ctx
	 * @param UNS_PaymentReceipt_Inv_ID
	 * @param trxName
	 */
	public MUNSPaymentReceiptInv(Properties ctx, int UNS_PaymentReceipt_Inv_ID, String trxName) {
		super(ctx, UNS_PaymentReceipt_Inv_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPaymentReceiptInv(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPaymentReceiptInv(MUNSBillingLineResult blr) {
		this(blr.getCtx(), 0, blr.get_TrxName());

		setClientOrg(blr);

		setDifferenceAmt(Env.ZERO);
		setNetAmtToInvoice(blr.getPaidAmt());
		setPaidAmt(blr.getPaidAmt());
		setPaymentStatus(blr.getPaymentStatus());
		setReceiptAmt(Env.ZERO);
		setUNS_CustomerBG_ID(blr.getUNS_CustomerBG_ID());
		setUNS_BillingLine_Result_ID(blr.get_ID());
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return updateHeaderAmount();
	}

	private boolean updateHeaderAmount() {

		MUNSPaymentReceiptBP bp = new MUNSPaymentReceiptBP(getCtx(), getUNS_PaymentReceipt_BP_ID(),
				get_TrxName());

		// Find Total Bill Amount
		String sql = "SELECT SUM(ReceiptAmt) FROM UNS_PaymentReceipt_Inv WHERE UNS_PaymentReceipt_BP_ID = ? AND IsActive='Y' ";
		BigDecimal receiptAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PaymentReceipt_BP_ID());

		bp.setReceiptAmt(receiptAmt);
		bp.setDifferenceAmt(bp.getPaidAmt().subtract(receiptAmt));

		if (!bp.save(get_TrxName()))
		{
			log.saveError("Cannot Update Header", "Cannot Save Header");
			return false;
		}

		return true;
	}

}
