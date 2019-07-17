/**
 * 
 */
package com.uns.model.callout;


import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Employee;
import com.uns.model.I_UNS_MonthlyPresenceSummary;
import com.uns.model.MUNSPayrollTermConfig;
import com.uns.model.X_UNS_Employee;


/*
 * @author Az's
 */
public class CalloutMonthlyPresenceSummary implements IColumnCallout {

	/* */
	public CalloutMonthlyPresenceSummary() {
		
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		I_UNS_MonthlyPresenceSummary monthlyPresenceSummary = GridTabWrapper.create(mTab, I_UNS_MonthlyPresenceSummary.class);
		
		I_UNS_Employee employee = new X_UNS_Employee(ctx, monthlyPresenceSummary.getUNS_Employee_ID(), null);

		
		int C_BPartner_ID = 0;
		int C_Job_ID = 0; 
		String JobStatus = null;
		String payrollTerm = null;
//		String MaritalStatus = null;
//		int HourPerDay = 0;
		String NoWorkDay = null; 
		String Shift = null;
		
		
		if(mField.getColumnName().equals(I_UNS_MonthlyPresenceSummary.COLUMNNAME_UNS_Employee_ID));
			{

			
				C_BPartner_ID = employee.getC_BPartner_ID();
				C_Job_ID = employee.getC_Job_ID();
		//		JobStatus = employee.getJobStatus();
				payrollTerm = employee.getPayrollTerm();
//				MaritalStatus = employee.getMaritalStatus();
//				HourPerDay = employee.getHourPerDay();
				NoWorkDay = employee.getNoWorkDay();
				Shift = employee.getShift();
				
				String payterm = MUNSPayrollTermConfig.getPayrollTermOf(
						monthlyPresenceSummary.getAD_Org_ID()
						, employee.getC_BPartner_ID()
						, employee.getUNS_Contract_Recommendation()
						.getNextContractType()
						, Env.getContextAsDate(ctx, "Date")
						, null);
				
				if(null != payterm && !payterm.equals(payrollTerm))
					payrollTerm = payterm;
				
			}
			
			monthlyPresenceSummary.setC_BPartner_ID(C_BPartner_ID);
			monthlyPresenceSummary.setC_Job_ID(C_Job_ID);
			monthlyPresenceSummary.setJobStatus(JobStatus);
			monthlyPresenceSummary.setPayrollTerm(payrollTerm);
//			monthlyPresenceSummary.setMaritalStatus(MaritalStatus);
//			monthlyPresenceSummary.setHourPerDay(HourPerDay);
			monthlyPresenceSummary.setNoWorkDay(NoWorkDay);
			monthlyPresenceSummary.setShift(Shift);
			

			
		// TODO Auto-generated method stub
		return null;
	}
}
