/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

import com.uns.model.I_UNS_Loan_Installment;
import com.uns.model.X_UNS_Loan_Installment;

/**
 * @author menjangan
 *
 */
public class CalloutLoanInstallment implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutLoanInstallment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retVal = null;
		
		if(null == value && null == oldValue)
			return retVal;
		
		if(mField.getColumnName().equals(I_UNS_Loan_Installment.COLUMNNAME_UNS_Employee_Loan_ID))
			retVal = UNS_Employee_Loan(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return retVal;
	}
	
	public String UNS_Employee_Loan(
			Properties ctx, int WindowNo, GridTab mTab
			, GridField mField, Object val, Object oldVal)
	{
		
		String msg = null;
		String sql = "SELECT Installment FROM UNS_Employee_Loan WHERE UNS_Employee_Loan_ID = ?";
		BigDecimal installment = DB.getSQLValueBD(null, sql, val);
		mTab.setValue(X_UNS_Loan_Installment.COLUMNNAME_PaidAmt, installment);		
		return msg;
	}

}
