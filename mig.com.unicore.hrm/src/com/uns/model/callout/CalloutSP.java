/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.AdempiereUserError;

import com.uns.model.I_UNS_Employee;
import com.uns.model.I_UNS_SP;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSSP;

/**
 * @author menjangan
 *
 */
public class CalloutSP implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutSP() {
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
		if(mField.getColumnName().equals(MUNSSP.COLUMNNAME_ValidFrom))
			valoidFrom(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSSP.COLUMNNAME_UNS_Employee_ID))
			employee(ctx, WindowNo, mTab, mField, value, oldValue);
		return null;
	}
	
	private String employee(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		I_UNS_SP sp =  GridTabWrapper.create(mTab, I_UNS_SP.class);
		I_UNS_Employee employee = sp.getUNS_Employee();
		sp.setC_Job_ID(employee.getC_Job_ID());
		//sp.setName(sp.getName() + " For " + employee.getName());
		return null;
	}

	private String valoidFrom(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		I_UNS_SP sp = GridTabWrapper.create(mTab, I_UNS_SP.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(sp.getValidFrom().getTime());
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(ctx, sp.getValidFrom(), null);
		if(null == payConfig)
			throw new AdempiereUserError("Not found payroll configuration for date " + sp.getValidFrom());
		
		int spPeriod = payConfig.getSP_Period();
		cal.add(Calendar.MONTH, spPeriod);
		sp.setValidTo(new Timestamp(cal.getTimeInMillis()));
		
		return null;
	}
}
