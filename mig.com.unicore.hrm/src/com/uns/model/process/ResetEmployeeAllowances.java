/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.base.model.Query;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeAllowanceRecord;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Haryadi
 *
 */
public class ResetEmployeeAllowances extends SvrProcess 
{
	private int m_Org_ID = 0;
	
	/**
	 * 
	 */
	public ResetEmployeeAllowances() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{
			String paramName = param.getParameterName();
			if (paramName.equals("AD_Org_ID"))
				m_Org_ID = param.getParameterAsInt();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String whereClause = "EmploymentType='COM' AND IsActive='Y' " +
				"AND IsBlacklist='N' AND IsTerminate='N' AND UNS_Employee.UNS_Contract_Recommendation_ID > 0 " +
				"AND ? < (SELECT ctr.DateContractEnd FROM UNS_Contract_Recommendation ctr" +
				"		  WHERE ctr.UNS_Contract_Recommendation_ID = UNS_Employee.UNS_Contract_Recommendation_ID)";
		if (m_Org_ID > 0)
			whereClause += " AND AD_Org_ID=" + m_Org_ID;
		
		Timestamp currDate = new Timestamp(System.currentTimeMillis());
		
		List<MUNSEmployee> employeeList = 
				Query.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
						  MUNSEmployee.Table_Name, whereClause, get_TrxName())
					 .setParameters(currDate)
					 .setOrderBy(MUNSEmployee.COLUMNNAME_Value)
					 .list();
		
		if (employeeList.isEmpty())
			return "No Employee at department of " + MOrg.get(getCtx(), m_Org_ID).getName();
		
		String msg = "";
		//addLog ("Allowance records created for employee of :");
		
		for (MUNSEmployee employee : employeeList)
		{
			MUNSEmployeeAllowanceRecord empAllowance =
					MUNSEmployeeAllowanceRecord.getCreate(getCtx(), employee, currDate, get_TrxName());
			
			if (empAllowance != null)
			{
				String createdMsg = "[" + employee.getValue() + "-" + employee.getName() + "]" + 
						(msg.isEmpty()? "," : "");
				
				addLog(createdMsg);
				msg += createdMsg;
			}
		}
		
		if (!msg.isEmpty())
			msg = "Allowance records created for employee of :\n";
		
		return msg;
	}

}
