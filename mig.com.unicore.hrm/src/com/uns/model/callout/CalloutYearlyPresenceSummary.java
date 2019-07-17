package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Employee;
import com.uns.model.I_UNS_YearlyPresenceSummary;
import com.uns.model.MUNSPayrollTermConfig;
import com.uns.model.X_UNS_Employee;

public class CalloutYearlyPresenceSummary implements IColumnCallout {
	
	public CalloutYearlyPresenceSummary(){
		
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		I_UNS_YearlyPresenceSummary yps = GridTabWrapper.create(mTab, I_UNS_YearlyPresenceSummary.class);
		
		I_UNS_Employee unse = new X_UNS_Employee(ctx, yps.getUNS_Employee_ID(), null);
		
	
		int bp = 0;
		int job = 0;
//		String jobst = "null";
		String payrollTerm = "null";
//		String mrtlst = "null";
		//int positionCategory = 0;
		//String nationality = "null";
		if(mField.getColumnName().equals(I_UNS_YearlyPresenceSummary.COLUMNNAME_UNS_Employee_ID))
		{
			
			bp = unse.getC_BPartner_ID();
			job = unse.getC_Job_ID();
		//	jobst = unse.getJobStatus();
			payrollTerm = unse.getPayrollTerm();
		//	mrtlst = unse.getMaritalStatus();
			//positionCategory=unse.getC_Job().getC_JobCategory_ID();
			//nationality = unse.getNationality();
			String defaultPayrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
					yps.getAD_Org_ID()
					, unse.getC_BPartner_ID()
					, unse.getUNS_Contract_Recommendation().getNextContractType()
					, Env.getContextAsDate(ctx, "Date")
					, null);
			if(null != defaultPayrollTerm && defaultPayrollTerm != payrollTerm)
				payrollTerm = defaultPayrollTerm;
		}
		
		yps.setC_BPartner_ID(bp);
		yps.setC_Job_ID(job);
//		yps.setJobStatus(jobst);
		yps.setPayrollTerm(payrollTerm);
//		yps.setMaritalStatus(mrtlst);
		/*
		if (job != 0){
			MUNSLeaveReservedConfig leveConfig = MUNSLeaveReservedConfig.get(ctx, positionCategory, nationality, null);
			if (null == leveConfig)
				return "";
			
			yps.setTotalLeaveClaimReserved(leveConfig.getLeaveClaimReserved());
		}
		*/
		return null;
	}

}
