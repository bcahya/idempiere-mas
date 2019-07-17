/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.unicore.model.X_UNS_PaymentReceipt;

/**
 * @author setyaka
 * 
 */
public class CalloutPaymentReceipt extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutPaymentReceipt() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int,
	 * org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue) {
		String retValue = null;

		if (mField.getColumnName().equals(X_UNS_PaymentReceipt.COLUMNNAME_ReceiptAmt))
		{
			retValue = this.calloutReceiptAmt(ctx, WindowNo, mTab, mField, value);
		}

		return retValue;
	}

	private String calloutReceiptAmt(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value) {
		if (null == value)
			return null;

		mTab.setValue(X_UNS_PaymentReceipt.COLUMNNAME_GrandTotal, (BigDecimal) value);
		BigDecimal diffValue = ((BigDecimal) value).subtract(
				(BigDecimal) mTab.getValue(X_UNS_PaymentReceipt.COLUMNNAME_PaidAmt));
		mTab.setValue(X_UNS_PaymentReceipt.COLUMNNAME_DifferenceAmt, diffValue);

		return null;
	}

}
