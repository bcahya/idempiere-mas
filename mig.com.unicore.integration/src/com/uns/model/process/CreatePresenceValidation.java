/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMonthlyPresenceVal;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author Burhani Adam
 *
 */
public class CreatePresenceValidation extends SvrProcess {

	private int p_AD_Org_ID = 0;
	private int p_SectionOfDept_ID = 0;
	private int p_C_Period_ID = 0;
	private Timestamp p_DateTo = null;
	private int p_Resource_ID = 0;
	private int p_Employee_ID = 0;
	private Timestamp m_startDate = null;
	private Timestamp m_endDate = null;
	private boolean p_isIncludedMangkir = false;
	
	/**
	 * 
	 */
	public CreatePresenceValidation() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			String name = params[i].getParameterName();
			if ("AD_Org_ID".equals(name))
			{
				p_AD_Org_ID = params[i].getParameterAsInt();
			}
			else if ("C_BPartner_ID".equals(name))
			{
				p_SectionOfDept_ID = params[i].getParameterAsInt();
			}
			else if ("C_Period_ID".equals(name))
			{
				p_C_Period_ID = params[i].getParameterAsInt();
			}
			else if("DateTo".equals(name))
			{
				p_DateTo = params[i].getParameterAsTimestamp();
			}
			else if("UNS_Employee_ID".equals(name))
			{
				p_Employee_ID = params[i].getParameterAsInt();
			}
			else if("UNS_Resource_ID".equals(name))
			{
				p_Resource_ID = params[i].getParameterAsInt();
			}
			else if("IsIncludedMangkir".equals(name))
			{
				p_isIncludedMangkir = params[i].getParameterAsBoolean();
			}
			else
			{
				log.log(Level.SEVERE, "Unknown parameter " + name);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		analyzeStartEndDate();
		String sql = "SELECT d.UNS_DailyPresence_ID, emp.C_BPartner_ID FROM UNS_DailyPresence d"
				+ " INNER JOIN UNS_MonthlyPresenceSummary m ON "
				+ " m.UNS_MonthlyPresenceSummary_ID = d.UNS_MonthlyPresenceSummary_ID"
				+ " INNER JOIN UNS_Employee emp ON emp.UNS_Employee_ID = m.UNS_Employee_ID"
				+ " INNER JOIN UNS_Resource_WorkerLine rw ON"
				+ " rw.Labor_ID = emp.UNS_Employee_ID"
				+ " WHERE d.PresenceDate BETWEEN ? AND ?"
				+ " AND (d.UNS_MonthlyPresenceVal_ID <= 0 OR d.UNS_MonthlyPresenceVal_ID IS NULL)";
		
		if(p_AD_Org_ID > 0)
			sql += " AND emp.AD_Org_ID = " + p_AD_Org_ID;
		if(p_SectionOfDept_ID > 0)
			sql += " AND emp.C_BPartner_ID = " + p_SectionOfDept_ID;
		if(p_Resource_ID > 0)
			sql += " AND rw.UNS_Resource_ID = " + p_Resource_ID;
		if(p_Employee_ID > 0)
			sql += " AND emp.UNS_Employee_ID = " + p_Employee_ID;

		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, m_startDate);
			stmt.setTimestamp(2, m_endDate);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				MUNSDailyPresence daily = new MUNSDailyPresence(getCtx(), rs.getInt(1), get_TrxName());
				if(daily.getUNS_MonthlyPresenceVal_ID() > 0)
					continue;

				if ((daily.getFSTimeIn() == null || daily.getFSTimeOut() == null)
						&& !MUNSDailyPresence.PRESENCESTATUS_Izin.
							equals(daily.getPresenceStatus())
						&& !MUNSDailyPresence.PRESENCESTATUS_Libur.
							equals(daily.getPresenceStatus())
						&& (p_isIncludedMangkir ? true : !MUNSDailyPresence.PRESENCESTATUS_Mangkir.
							equals(daily.getPresenceStatus()))
						&& !MUNSDailyPresence.DAYTYPE_HariLiburNasional.
							equals(daily.getDayType()))
				{
					MUNSEmployee emp = MUNSEmployee.get(getCtx(), daily.getUNS_MonthlyPresenceSummary().getUNS_Employee_ID());
					boolean isWorkDay = true;
					String sql2 = "SELECT hc.UNS_SlotType_ID FROM UNS_WorkHoursConfig hc"
							+ " INNER JOIN UNS_WorkHoursConfig_Line cl ON cl.UNS_WorkHoursConfig_ID"
							+ " = hc.UNS_WorkHoursConfig_ID"
							+ " WHERE (? BETWEEN hc.ValidFrom AND hc.ValidTo)"
							+ " AND hc.DocStatus IN ('CO', 'CL') AND"
							+ " (CASE WHEN cl.UNS_Resource_ID > 0 THEN cl.UNS_Resource_ID ="
							+ " (SELECT UNS_Resource_ID FROM UNS_Resource_WorkerLine WHERE Labor_ID = ?)"
							+ " ELSE cl.UNS_Employee_ID = ? END)";
					int slotType = DB.getSQLValue(get_TrxName(), sql2, 
										new Object[]{daily.getPresenceDate(), emp.get_ID(), emp.get_ID()});
					if(slotType <= 0)
					{
						String sql3 = "SELECT r.UNS_SlotType_ID FROM UNS_Resource r"
								+ " INNER JOIN UNS_Resource_WorkerLine wl ON wl.UNS_Resource_ID = r.UNS_Resource_ID"
								+ " WHERE wl.Labor_ID = ?";
						slotType = DB.getSQLValue(get_TrxName(), sql3, emp.get_ID());
					}
					if(slotType <= 0)
					{
						continue;
					}
					sql = "SELECT COUNT(*) FROM UNS_SlotType st WHERE st.UNS_SlotType_ID = ?"
							+ " AND CASE WHEN IsDaySlot = 'Y' THEN (CASE WHEN "
							+ " ? = '1' THEN st.OnSunday = 'Y' WHEN"
							+ " ? = '2' THEN st.OnMonday = 'Y' WHEN"
							+ " ? = '3' THEN st.OnTuesday = 'Y' WHEN"
							+ " ? = '4' THEN st.OnWednesday = 'Y' WHEN"
							+ " ? = '5' THEN st.OnThursday = 'Y' WHEN"
							+ " ? = '6' THEN st.OnFriday = 'Y' WHEN"
							+ " ? = '7' THEN st.OnSaturday = 'Y' ELSE"
							+ " 1=1 END) ELSE 1=1 END";
					isWorkDay = DB.getSQLValue(get_TrxName(), sql, 
									new Object[]{slotType, daily.getDay(), daily.getDay(), daily.getDay(), 
											 daily.getDay(), daily.getDay(), daily.getDay(), daily.getDay()}) 
											 	> 0 ? true : false;
				   	if(isWorkDay)
			    	{
				   		MUNSMonthlyPresenceVal validation = null;
				    	validation = MUNSMonthlyPresenceVal.createValidation(
									daily, "PresenceDate", m_startDate, 
									m_endDate, rs.getInt(2), 
									p_C_Period_ID);
				    	daily.setUNS_MonthlyPresenceVal_ID(validation.get_ID());
				    	daily.saveEx();
			    	}
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		return "Success";
	}
	
	private void analyzeStartEndDate()
	{
		MPeriod period = MPeriod.get(getCtx(), p_C_Period_ID);
		Calendar cal = TimeUtil.getCalendar(period.getStartDate());
		
		MUNSPayrollConfiguration config =
				MUNSPayrollConfiguration.get(
						getCtx(), period, get_TrxName());
		
		if (null == config)
		{
			throw new AdempiereException(
					"Can't find payroll configuration.");
		}
		
		int startConfig = config.getPayrollDateStart();
		int endConfig = config.getPayrollDateEnd();
		
		cal.set(Calendar.DATE, startConfig);
		
		int daysOfMonth = cal.getActualMaximum(Calendar.DATE);
		int median = daysOfMonth / 2;
		
		if (startConfig > median)
		{
			cal.add(Calendar.MONTH, -1);
		}
		
		m_startDate = new Timestamp(cal.getTimeInMillis());
		
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, endConfig);
		
		m_endDate = new Timestamp(cal.getTimeInMillis());
		
		if(p_DateTo != null && p_DateTo.before(m_endDate) && p_DateTo.after(m_startDate))
			m_endDate = p_DateTo;
	}
}