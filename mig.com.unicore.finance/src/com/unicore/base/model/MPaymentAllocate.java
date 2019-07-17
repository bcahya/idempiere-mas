/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author setyaka
 * 
 */
public class MPaymentAllocate extends org.compiere.model.MPaymentAllocate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370649836879867950L;
	
	public MPaymentAllocate(Properties ctx, int C_PaymentAllocate_ID, String trxName) {
		super(ctx, C_PaymentAllocate_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MPaymentAllocate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MPaymentAllocate[] get(MPayment payment) {
		final String whereClause = "C_Payment_ID=?";
		Query query =
				Query.get(payment.getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MPaymentAllocate.Table_Name,
						whereClause, payment.get_TrxName());
		query.setParameters(payment.getC_Payment_ID()).setOnlyActiveRecords(true);
		List<MPaymentAllocate> list = query.list();
		return list.toArray(new MPaymentAllocate[list.size()]);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		return super.beforeSave(newRecord);
	}

	public BigDecimal getTotalAmt() {
		
		return getAmount().add(getDiscountAmt()).add(getWriteOffAmt()).add(getOverUnderAmt());
	}
	
}
