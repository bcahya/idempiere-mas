/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSResourceWorkerLine;
import com.uns.model.X_UNS_Employee;

/**
 * @author menjangan
 *
 */
public class CalloutResourceWorkerLine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutResourceWorkerLine() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		if(null == value)
			return null;
		
		if(mField.getColumnName().equals(MUNSResourceWorkerLine.COLUMNNAME_Labor_ID))
		{
			X_UNS_Employee employee = new X_UNS_Employee(ctx, new Integer(value.toString()), null);
			mTab.setValue(MUNSResourceWorkerLine.COLUMNNAME_PayrollTerm, employee.getPayrollTerm());
		}
		return null;
	}

}
