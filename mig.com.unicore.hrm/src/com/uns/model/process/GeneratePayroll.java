/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.util.MessageBox;

import com.uns.model.MUNSMonthlyPayrollEmployee;
import com.uns.model.MUNSPayrollEmployee;

/**
 * @author menjangan
 *
 */
public class GeneratePayroll extends SvrProcess {

	/**
	 * 
	 */
	public GeneratePayroll() {
		super();
	}
	
	private String m_generateBy = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			String paramName = param.getParameterName();
			if(null == paramName)
				continue;
			
			if(MUNSMonthlyPayrollEmployee.COLUMNNAME_GenerateList.equals(paramName))
				m_generateBy = param.getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown parameter : " + paramName);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(getRecord_ID() <= 0)
			return null;
		
		MUNSMonthlyPayrollEmployee monthlyPayroll = new MUNSMonthlyPayrollEmployee(
				getCtx(), getRecord_ID(), get_TrxName());
		
		monthlyPayroll.setProcessInfo(getProcessInfo());

		if(!MUNSMonthlyPayrollEmployee.DOCSTATUS_Drafted.equals(monthlyPayroll.getDocStatus())
				&& !MUNSMonthlyPayrollEmployee.DOCSTATUS_InProgress.equals(monthlyPayroll.getDocStatus())
				&& !MUNSMonthlyPayrollEmployee.DOCSTATUS_Invalid.equals(monthlyPayroll.getDocStatus()))
			return "";
		
		monthlyPayroll.setGenerateList(m_generateBy);
		
		String deleteMsg = "";
		if(monthlyPayroll.isGenerated())
		{
			int retVal = MessageBox.showMsg(getCtx()
						, getProcessInfo()
						, "Payroll has ben generated, do you want to delete previus record?"
						, "Delete Previous?"
						, MessageBox.YESNO
						, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_OK)
			{
				String sql = "DELETE FROM "
						+ MUNSPayrollEmployee.Table_Name
						+ " WHERE " + MUNSPayrollEmployee
						.COLUMNNAME_UNS_MonthlyPayroll_Employee_ID
						+ "=?";
				int result = DB.executeUpdate(sql, monthlyPayroll.get_ID(), get_TrxName());
				if(result < 0)
					throw new AdempiereException("Failed to remove previous record!!!");
				
				deleteMsg = "Deleted previous payroll record : " + result + "\n";
			}
		}
		
		String erroMsg = deleteMsg + monthlyPayroll.generatePayroll();
		
		monthlyPayroll.setErrorMsg(erroMsg);
		monthlyPayroll.setGeneratePay("Y");
		monthlyPayroll.saveEx();
		
		return "SUCCESS";
	}

}
