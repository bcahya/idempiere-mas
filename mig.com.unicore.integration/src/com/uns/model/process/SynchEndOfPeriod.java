package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import com.uns.model.MUNSAttConfiguration;
import com.uns.model.MUNSCheckInOut;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmpStation;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSLeavePermissionTrx;
import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSMonthlyPresenceVal;
import com.uns.model.MUNSOTRequest;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSSlotType;
import com.uns.model.MUNSWorkHoursConfig;
import com.uns.model.utilities.HRMUtils;

public class SynchEndOfPeriod extends SvrProcess {
	
	private int p_AD_Org_ID = 0;
	private int p_SectionOfDept_ID = 0;
	private int p_C_Period_ID = 0;
	private Timestamp p_DateTo = null;
	private int p_Resource_ID = 0;
	private int p_Employee_ID = 0;
	private Timestamp m_startDate = null;
	private Timestamp m_endDate = null;
	private MUNSAttConfiguration m_AttConfig = null;
	private MUNSSlotType m_SlotType = null;
//	private StringBuilder m_logUpdate = new StringBuilder();
//	private IProcessUI m_process;

	public SynchEndOfPeriod() 
	{
		super ();
	}

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
			else
			{
				log.log(Level.SEVERE, "Unknown parameter " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception 
	{
//		m_process = Env.getProcessUI(getCtx());
		createMonthlyRecord();
		MUNSMonthlyPresenceSummary[] monthly = getMonthlyRecord();
//		int length = monthly.length;
		for (int i=0; i<monthly.length; i++)
		{
//			m_logUpdate.append("Employee ").append(monthly[i].getUNS_Employee().getName())
//			.append(" for ").append(length).append(" Employees");
//			m_process.statusUpdate(m_logUpdate.toString());
			processMonthlyRecord(monthly[i]);
		}
		
		return "Success";
	}
	
	private void createMonthlyRecord()
	{
		analyzeStartEndDate();
		String sql = "SELECT DISTINCT(emp.UNS_Employee_ID) FROM UNS_CheckInOut io"
				+ " INNER JOIN UNS_Employee emp ON emp.AttendanceName = io.AttendanceName"
				+ " INNER JOIN UNS_Contract_Recommendation cr "
				+ " ON cr.UNS_Contract_Recommendation_ID = emp.UNS_Contract_Recommendation_ID"
				+ " INNER JOIN UNS_Resource_WorkerLine rs ON rs.Labor_ID = emp.UNS_Employee_ID"
				+ " WHERE (CASE WHEN ? < cr.DateContractStart THEN ? >= cr.DateContractStart"
				+ " WHEN ? > cr.DateContractEnd THEN ? <= cr.DateContractEnd ELSE 1=1 END) AND emp.IsActive='Y'"
				+ " AND emp.IsBlacklist='N' AND emp.IsTerminate='N'"
				+ " AND emp.AD_Org_ID = ? AND io.CheckTime BETWEEN ? AND ?"
				+ " AND NOT EXISTS (SELECT 1 FROM UNS_MonthlyPresenceSummary mps"
				+ " WHERE mps.UNS_Employee_ID = emp.UNS_Employee_ID AND mps.C_Period_ID = ?)";
		
		if(p_Employee_ID > 0)
			sql += " AND emp.UNS_Employee_ID = " + p_Employee_ID;
		if(p_Resource_ID > 0)
			sql += " AND rs.UNS_Resource_ID = " + p_Resource_ID;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, m_startDate);
			stmt.setTimestamp(2, m_endDate);
			stmt.setTimestamp(3, m_endDate);
			stmt.setTimestamp(4, m_startDate);
			stmt.setInt(5, p_AD_Org_ID);
			stmt.setTimestamp(6, m_startDate);
			stmt.setTimestamp(7, m_endDate);
			stmt.setInt(8, p_C_Period_ID);
			rs = stmt.executeQuery();
			
			while (rs.next())
			{
				MUNSMonthlyPresenceSummary.getCreate(getCtx(), 
					MUNSEmployee.get(getCtx(), rs.getInt(1)), p_C_Period_ID, m_startDate, m_endDate, get_TrxName());
			}
			
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void processMonthlyRecord (MUNSMonthlyPresenceSummary monthly)
	{
		Timestamp start = monthly.getStartDate();
		
		Timestamp end = p_DateTo != null && p_DateTo.before(monthly.getEndDate())
						&& p_DateTo.after(start) ? 
							p_DateTo : monthly.getEndDate();

		MUNSEmployee employee = (MUNSEmployee) 
				monthly.getUNS_Employee();
		
		MUNSResource resource = MUNSResource.getByEmployee(getCtx(), 
				employee.get_ID(), employee.get_TrxName());
		if(resource == null)
			return;
		
		while (!(start.after(end)))
		{
//			if(!start.equals(Timestamp.valueOf("2018-03-16 00:00:00.00")))
//			{
//				start = TimeUtil.addDays(start, 1); 
//				continue;
//			}
			m_AttConfig = MUNSAttConfiguration.get(
					getCtx(), start, employee.getAD_Org_ID(), 
					employee.getEmploymentType(), 
					employee.getC_BPartner_ID(), 
					employee.getC_Job().getC_JobCategory_ID(), 
					get_TrxName());
			m_SlotType = MUNSSlotType.getByEmployee(getCtx(), employee.get_ID(), get_TrxName());
			MUNSWorkHoursConfig adjusment = MUNSWorkHoursConfig.getByEmployee(getCtx(), 
					resource.get_ID(), employee.get_ID(), start, get_TrxName());
			if(adjusment != null && adjusment.getUNS_SlotType_ID() > 0)
				m_SlotType = new MUNSSlotType(getCtx(), adjusment.getUNS_SlotType_ID(), get_TrxName());
				
//			m_logUpdate.append("\n").append("Day ").append(start);
//			m_process.statusUpdate(m_logUpdate.toString());
			MUNSDailyPresence daily = monthly.getDaily(start);
			if(daily == null)
			{
				daily = new MUNSDailyPresence(monthly);
				daily.setPresenceDate(start);
				daily.setIsNeedAdjustRule(true);
			}
			else
			{
				if(daily.getUNS_MonthlyPresenceVal_ID() > 0)
				{
					MUNSMonthlyPresenceVal val = new MUNSMonthlyPresenceVal(getCtx(), 
							daily.getUNS_MonthlyPresenceVal_ID(), get_TrxName());
					if(!val.getDocStatus().equals("CO")
							&& !val.getDocStatus().equals("CL"))
					{
						daily.setFSTimeIn(null);
						daily.setFSTimeOut(null);
					}
					else
					{
						start = TimeUtil.addDays(start, 1);
						continue;
					}
				}
				else
				{
					daily.setFSTimeIn(null);
					daily.setFSTimeOut(null);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			if(daily.isNeedAdjustRule())
			{
				Calendar calIn = Calendar.getInstance();
				Calendar calOut = Calendar.getInstance();
				calIn.setTime(start);
				calOut.setTime(start);
				
				if(adjusment != null)
				{
					if(adjusment.getUNS_SlotType_ID() > 0)
					{
						m_SlotType = new MUNSSlotType(getCtx(), adjusment.getUNS_SlotType_ID(), get_TrxName());
						calIn.setTime(m_SlotType.getTimeSlotStart());
						calIn.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
						calOut.setTime(m_SlotType.getTimeSlotEnd());
						calOut.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
					}
					else
					{
						calIn.setTime(adjusment.getStartTime());
						calIn.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
						calOut.setTime(adjusment.getEndTime());
						calOut.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
					}
				}
				else
				{
					calIn.setTime(m_SlotType.getTimeSlotStart());
					calIn.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
					calOut.setTime(m_SlotType.getTimeSlotEnd());
					calOut.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
				}
				
				if(calIn.get(Calendar.HOUR_OF_DAY) >= calOut.get(Calendar.HOUR_OF_DAY))
					calOut.add(Calendar.DATE, 1);
				
				MUNSOTRequest OTrequest = MUNSOTRequest.getValidof(
						get_TrxName(), start, employee.get_ID());

				if(OTrequest != null)
				{
					if(m_SlotType.IsWorkDay(new Integer(daily.getDay()))
						&& !MNonBusinessDay.isNonBusinessDay(getCtx(), start, daily.getAD_Org_ID(), get_TrxName()))
					{
						if(calIn.getTimeInMillis() >= OTrequest.getEndTime().getTime())
							calIn.setTimeInMillis(OTrequest.getStartTime().getTime());
						else if(calOut.getTimeInMillis() >= OTrequest.getStartTime().getTime())
							calOut.setTimeInMillis(OTrequest.getEndTime().getTime());
					}
					else
					{
						calIn.setTimeInMillis(OTrequest.getStartTime().getTime());
						calOut.setTimeInMillis(OTrequest.getEndTime().getTime());
					}
				}
				
				daily.setTimeInRules(new Timestamp(calIn.getTimeInMillis()));
				daily.setTimeOutRules(new Timestamp(calOut.getTimeInMillis()));
				
				int minLateFSIn = m_AttConfig.getMaxEarlierFSIn();
				int maxLateFSOut = m_AttConfig.getMaxLateFSOut();
				
				if(minLateFSIn > 0)
					calIn.add(Calendar.MINUTE, -minLateFSIn);
				if(maxLateFSOut > 0)
					calOut.add(Calendar.MINUTE, maxLateFSOut);
				
				daily.setMinTimeInRule(new Timestamp(calIn.getTimeInMillis()));
				daily.setMaxTimeOutRule(new Timestamp(calOut.getTimeInMillis()));
				daily.setIsNeedAdjustRule(false);
			}
			
//			MUNSSlotType slot = getShift(employee);
//			MUNSWorkHoursConfig config = MUNSWorkHoursConfig.getByEmployee(
//					getCtx(), rs != null ? rs.get_ID() : 0, employee.get_ID(), start, get_TrxName());
//			String sql = "SELECT UNS_WorkHoursConfig.UNS_SlotType_ID FROM UNS_WorkHoursConfig "
//					+ " WHERE ? BETWEEN UNS_WorkHoursConfig.ValidFrom AND UNS_WorkHoursConfig.ValidTo"
//					+ " AND EXISTS (SELECT 1 FROM UNS_WorkHoursConfig_Line wl WHERE"
//					+ " UNS_WorkHoursConfig.UNS_WorkHoursConfig_ID = wl.UNS_WorkHoursConfig_ID"
//					+ " AND wl.UNS_Employee_ID = ? AND wl.IsActive = 'Y')"
//					+ " AND UNS_WorkHoursConfig.DocStatus IN ('CO', 'CL')";
//			int slotType_ID = DB.getSQLValue(get_TrxName(), sql, new Object[]{start, employee.get_ID()});
//			
//			if(slotType_ID > 0)
//				slot = new MUNSSlotType(getCtx(), slotType_ID, get_TrxName());
//			
//			if(slot == null)
//				return;
			
			boolean isWorkDay = m_SlotType.IsWorkDay(cal.get(Calendar.DAY_OF_WEEK));
			MUNSLeavePermissionTrx leave = MUNSLeavePermissionTrx.get(getCtx(), employee.get_ID(), start, get_TrxName());
			if(isNonBusinessDay(start, employee.getAD_Org_ID()) && leave == null)
			{
				daily.setDayType(MUNSDailyPresence.DAYTYPE_HariLiburNasional);	
				daily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Libur);
			}
			else if ((daily.getDay().equals(monthly.getNoWorkDay()) || !isWorkDay) && leave == null)
			{
				daily.setDayType(MUNSDailyPresence.DAYTYPE_HariLiburMingguan);
				daily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Libur);
			}
			else if(leave == null)
			{
				daily.setDayType(MUNSDailyPresence.DAYTYPE_HariKerjaBiasa);
				daily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Mangkir);
			}
			
			daily.saveEx();
				
			InOutToDaily(getInOuts(daily.getMinTimeInRule(), daily.getMaxTimeOutRule(),
					employee.getAttendanceName()), daily, employee);
			
			start = TimeUtil.addDays(start, 1);
		}
	}
	
	public MUNSSlotType getWorkerShift (MUNSEmployee employee)
	{
		return MUNSResource.getSlotType(employee)[0];
	}

	public MUNSSlotType getShift (MUNSEmployee employee)
	{
		if (employee.getEmploymentType().equals("SUB"))
		{
			return getWorkerShift(employee);
		}
		
		int shift = MUNSEmpStation.getEmployeeSlotType_ID(
				get_TrxName(), employee.get_ID());
		if (shift <= 0)
		{
			return null;
		}
		
		MUNSSlotType slotType = new MUNSSlotType(
				getCtx(), shift, get_TrxName());
		return slotType;
	}
	
	
	private boolean isNonBusinessDay(Timestamp timestamp, int AD_Org_ID) {
		return MNonBusinessDay.isNonBusinessDay(getCtx(), timestamp, AD_Org_ID, get_TrxName());
	}

	private MUNSMonthlyPresenceSummary[] getMonthlyRecord ()
	{
		List<Object> params = new ArrayList<>();
		String whereClause = "C_Period_ID = ? ";
		params.add(p_C_Period_ID);
		if (p_AD_Org_ID > 0)
		{
			whereClause += " AND AD_Org_ID = ?";
			params.add(p_AD_Org_ID);
		}
		if (p_SectionOfDept_ID > 0)
		{
			whereClause += " AND C_BPartner_ID = ?";
			params.add(p_SectionOfDept_ID);
		}
		if(p_Resource_ID > 0)
		{
			whereClause += " AND EXISTS (SELECT 1 FROM UNS_Resource_WorkerLine WHERE"
					+ " UNS_Resource_WorkerLine.Labor_ID = UNS_MonthlyPresenceSummary.UNS_Employee_ID"
					+ " AND UNS_Resource_WorkerLine.UNS_Resource_ID = ?)";
			params.add(p_Resource_ID);
		}
		if(p_Employee_ID > 0)
		{
			whereClause += " AND UNS_Employee_ID = ?";
			params.add(p_Employee_ID);
		}
		
		whereClause += " AND DocStatus NOT IN ('VO', 'RE', 'CL', 'CO')";
		
		MUNSMonthlyPresenceSummary[] monthly = MUNSMonthlyPresenceSummary.get(
				getCtx(), whereClause, params, null, get_TrxName());
		
		return monthly;
	}

	private MUNSCheckInOut[] getInOuts(Timestamp dateFrom, Timestamp dateTo, String attName)
	{
		List<Object> params = new ArrayList<>();
		dateFrom = TimeUtil.addDays(dateFrom, -1);
		dateTo = TimeUtil.addDays(dateTo, 1);
		params.add(dateFrom);
		params.add(dateTo);
		
		String whereClause = "CheckTime BETWEEN ? AND ?";
		if(!Util.isEmpty(attName, true))
		{
			whereClause += " AND AttendanceName = ?";
			params.add(attName);
		}
		
		MUNSCheckInOut[] ios = MUNSCheckInOut.gets(getCtx(), get_TrxName(), whereClause, params, "CheckTime ASC");
		
		return ios;
	}
	
	private String InOutToDaily(MUNSCheckInOut[] ios, MUNSDailyPresence day, MUNSEmployee emp)
	{
		String msg = null;
		
		for(int i = 0; i < ios.length; i++)
		{
			MUNSCheckInOut io = ios[i];
			HRMUtils util = new HRMUtils(emp, io.getCheckTime(), day, m_SlotType, m_AttConfig);
			
			if(util.getPresenceDate() == null)
				continue;
			
			linkedToDaily(emp, util, io, day);
		}
		
		return msg;
	}
	
	/**
	 * 
	 * @param presenceDate
	 * @param employee
	 * @param util
	 * @param inOut
	 * @return
	 */
	private String linkedToDaily (MUNSEmployee employee, 
			HRMUtils util, MUNSCheckInOut inOut, MUNSDailyPresence presence)
	{
		Timestamp presenceDate = util.getPresenceDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(presenceDate.getTime());
		String presenceStatus = MUNSDailyPresence.PRESENCESTATUS_FullDay;
		String dayType = MUNSDailyPresence.DAYTYPE_HariKerjaBiasa;
		if (util.isNationalHoliday())
		{
			dayType = MUNSDailyPresence.DAYTYPE_HariLiburNasional;
		}
		else if (util.isWeeklyHoliday())
		{
			dayType = MUNSDailyPresence.DAYTYPE_HariLiburMingguan;
		}
		if (util.isOverTime() && (util.isWeeklyHoliday() 
				|| util.isNationalHoliday()))
		{
			presenceStatus = MUNSDailyPresence.PRESENCESTATUS_Lembur;
		}
		
		try
		{
//			MUNSDailyPresence presence = MUNSDailyPresence.getByDate(
//					getCtx(), presenceDate, employee.get_ID(), get_TrxName());
//			if (null == presence)
//			{
//				presence = new MUNSDailyPresence(presenceDate, employee);
//			}
//			else
//			{
//			if(presence.getPresenceStatus().equals(X_UNS_DailyPresence.PRESENCESTATUS_Izin))
//				return "";
//			}
			
//			String sql = "SELECT DocStatus FROM " + MUNSMonthlyPresenceSummary
//					.Table_Name	+ " WHERE " + MUNSMonthlyPresenceSummary
//					.COLUMNNAME_UNS_MonthlyPresenceSummary_ID + " = ? ";
//			
//			String monthlyDocStatus = DB.getSQLValueString(
//					get_TrxName(), sql, 
//					presence.getUNS_MonthlyPresenceSummary_ID());
//			
//			if (MUNSMonthlyPresenceSummary.DOCSTATUS_Completed.
//					equals(monthlyDocStatus))
//			{
//				String errorMsg = "The monthly presence of day record [" 
//							+ presence.toString() + "] is completed.";
//				log.log(Level.WARNING, errorMsg);
//				
//				return errorMsg;
//			}
			
			Timestamp timeIn = presence.getTimeInRules() == null ? util.getTimeInRules() : presence.getTimeInRules();
			Timestamp maxFSIn = util.getMaxLateTimeInRules();
			Timestamp timeOut = presence.getTimeOutRules() == null ? util.getTimeOutRules() : presence.getTimeOutRules();
			Timestamp minFSOut = util.getMaxEarlierTimeOutRules();
			long maxStart = maxFSIn.getTime();
			long minEnd = minFSOut.getTime();
			long check = util.getDate().getTime();
			
			if (maxStart >= check)
			{
				Timestamp lastIn = presence.getFSTimeIn();
				if (null == lastIn)
				{
					lastIn = util.getDate();
				}
				else if (timeIn.after(lastIn))
				{
					lastIn = util.getDate();
				}
				
				presence.setFSTimeIn(lastIn);
			}
			else if (minEnd <= check)
			{
				Timestamp lastOut = presence.getFSTimeOut();
				if (null == lastOut)
				{
					lastOut = util.getDate();
				}
				else if (util.getDate().after(lastOut))
				{
					lastOut = util.getDate();
				}
				
				presence.setFSTimeOut(lastOut);
			}
			
			presence.setTimeInRules(timeIn);
			presence.setTimeOutRules(timeOut);
			presence.setPresenceStatus(presenceStatus);
			presence.setDayType(dayType);
			presence.setDay(util.getDay());
			if (inOut.getAD_Org_ID() != presence.getAD_Org_ID())
			{
				inOut.setAD_Org_ID(presence.getAD_Org_ID());
			}
//			presence.setWorkHours(BigDecimal.valueOf(util.getMaxWorkHours()));
//			presence.setAddWorkHours((int) util.getAddWorkHours());
			presence.setOvertime(BigDecimal.valueOf(util.getMaxOTHours()));
		
			presence.saveEx();
			inOut.setUNS_DailyPresence_ID(presence.get_ID());
			inOut.saveEx();
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
		
		return null;
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
		
		if (startConfig > median)
		{
			cal.add(Calendar.MONTH, 1);
		}
		int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(maxDayOfMonth < endConfig)
			endConfig = maxDayOfMonth;
		cal.set(Calendar.DATE, endConfig);
		
		m_endDate = new Timestamp(cal.getTimeInMillis());
	}
}