/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeAllowanceRecord;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author Inohtaf, Modified by ITD-Andy 14-08-2013, 28-08-2013
 * 
 */
public class ChangeDailyPresence extends SvrProcess 
{
	private String m_newPresenceStatus;
	//private int m_RecordID;
	private MUNSDailyPresence m_DailyPresence;
	private BigDecimal m_Overtime;
	//private BigDecimal m_LD1;
	//private BigDecimal m_LD2;
	//private BigDecimal m_LD3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		
		for (ProcessInfoParameter param : getParameter()) 
		{
			String paramName = param.getParameterName();
			if (paramName.equalsIgnoreCase("PresenceStatus")) 
				m_newPresenceStatus = (String) param.getParameter();
			else if (paramName.equalsIgnoreCase("Overtime"))
				m_Overtime = (BigDecimal) param.getParameter();
			/*
			else if (paramName.equalsIgnoreCase("LD1"))
				m_LD1 = (BigDecimal) param.getParameter();
			else if (paramName.equalsIgnoreCase("LD2"))
				m_LD2 = (BigDecimal) param.getParameter();
			else if (paramName.equalsIgnoreCase("LD3"))
				m_LD3 = (BigDecimal) param.getParameter();
			*/
		}
		
		//int m_RecordID = getRecord_ID();
		m_DailyPresence = new MUNSDailyPresence(getCtx(), getRecord_ID(), get_TrxName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		//String msg = null;
		m_DailyPresence.setPresenceStatus(m_newPresenceStatus);
		
		MUNSPayrollConfiguration payConfig = 
				MUNSPayrollConfiguration.get(getCtx(), m_DailyPresence.getPresenceDate(), get_TrxName());
		MUNSEmployee employee = (MUNSEmployee) m_DailyPresence.getUNS_MonthlyPresenceSummary().getUNS_Employee();
				
		if (MUNSDailyPresence.PRESENCESTATUS_FullDay.equals(m_newPresenceStatus))
			if (employee.getShift().equals(MUNSEmployee.SHIFT_Shift))
				m_DailyPresence.setWorkHours(payConfig.getNormalDayWorkHoursShift());
			else
				m_DailyPresence.setWorkHours(payConfig.getNormalDayWorkHours());
		else if (MUNSDailyPresence.PRESENCESTATUS_HalfDay.equals(m_newPresenceStatus))
			if (employee.getShift().equals(MUNSEmployee.SHIFT_Shift))
				m_DailyPresence.setWorkHours(
						payConfig.getNormalDayWorkHoursShift()
						.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_DOWN));
			else
				m_DailyPresence.setWorkHours(
						payConfig.getNormalDayWorkHours()
						.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_DOWN));
		
		String prevPermissionType = m_DailyPresence.getPermissionType();
		
		m_DailyPresence.setPermissionType(null);
		m_DailyPresence.setOvertime(m_Overtime);
		
		if (MUNSDailyPresence.PERMISSIONTYPE_LeaveCuti.equals(prevPermissionType))
		{
			MUNSEmployeeAllowanceRecord empAllowance = 
					MUNSEmployeeAllowanceRecord.get(
							getCtx(), employee.get_ID(), m_DailyPresence.getPresenceDate(), get_TrxName());
			empAllowance.setLeaveReservedUsed(empAllowance.getLeaveReservedUsed().subtract(Env.ONE));
			empAllowance.saveEx();
		}
		String userName = MUser.getNameOfUser(getAD_User_ID());
		Timestamp currTS = new Timestamp(System.currentTimeMillis());
		
		String description = m_DailyPresence.getDescription() == null? "" : m_DailyPresence.getDescription() + "\n";
		m_DailyPresence.setDescription(description + "***Changed by " + userName + " @" + currTS + "***");
		m_DailyPresence.saveEx();
		addLog("Presence status changed.");
		return "Presence status changed.";
	}
}
