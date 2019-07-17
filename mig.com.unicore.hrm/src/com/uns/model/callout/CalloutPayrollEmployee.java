/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.I_UNS_Employee;
import com.uns.model.I_UNS_Payroll_Employee;
import com.uns.model.MUNSPayrollBaseEmployee;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollEmployee;
import com.uns.model.MUNSPayrollTermConfig;

/**
 * @author menjangan
 *
 */
public class CalloutPayrollEmployee implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutPayrollEmployee() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		if(MUNSPayrollEmployee.COLUMNNAME_UNS_Employee_ID.equals(mField.getColumnName()))
			return employee_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(MUNSPayrollEmployee.COLUMNNAME_C_Period_ID.equals(mField.getColumnName()))
			return setDate(ctx, WindowNo, mTab, mField, value, oldValue);
		return null;
	}
	
	protected String employee_ID(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		// TODO Auto-generated method stub
		if(null == value)
			return null;
		I_UNS_Payroll_Employee payEmployee =
				GridTabWrapper.create(mTab, I_UNS_Payroll_Employee.class);
		
		I_UNS_Employee employee = payEmployee.getUNS_Employee();
		
		String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				payEmployee.getAD_Org_ID()
				, employee.getC_BPartner_ID()
				, employee.getUNS_Contract_Recommendation().getNextContractType()
				, Env.getContextAsDate(ctx, "Date")
				, null);
		
		if(null == payrollTerm)
			payrollTerm = employee.getPayrollTerm();
		
		payEmployee.setC_Job_ID(employee.getC_Job_ID());
		payEmployee.setShift(employee.getShift());
		payEmployee.setPayrollTerm(payrollTerm);
		
		I_UNS_Contract_Recommendation cr = employee.getUNS_Contract_Recommendation();
		
		MUNSPayrollBaseEmployee pbEmployee = 
				MUNSPayrollBaseEmployee.get(ctx, cr.getUNS_Contract_Recommendation_ID(), null);
		payEmployee.setUNS_Contract_Recommendation_ID(cr.getUNS_Contract_Recommendation_ID());
		payEmployee.setUNS_PayrollBase_Employee_ID(pbEmployee.get_ID());
		if(employee.getVendor_ID()==0)
			payEmployee.setC_DocType_ID(MUNSPayrollBaseEmployee.getDocType(ctx, false).get_ID());
		else 
			payEmployee.setC_DocType_ID(MUNSPayrollBaseEmployee.getDocType(ctx, true).get_ID()); 
		return null;
	}
	
	@SuppressWarnings("unused")
	public String setDate(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		Timestamp startDate = null;
		Timestamp endDate = null;
		
		if(mField.getColumnName().equals("C_Period_ID"))
		{
			int periodID = Integer.valueOf(value.toString());
			
			MPeriod period = new MPeriod(ctx, periodID, null);
			if(period == null)
				return null;
			
			MUNSPayrollConfiguration payrollConfig = MUNSPayrollConfiguration.get(ctx, period, null);
			if(payrollConfig == null)
				return null;
			
			payrollConfig.initPayrollPeriodOf(periodID);
			startDate = payrollConfig.getStartDate();
			endDate = payrollConfig.getEndDate();
			
		}
		
		mTab.setValue("StartDate", startDate);
		
		mTab.setValue("EndDate", endDate);
		
		return null;
	}

}
