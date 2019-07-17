/**
 * 
 */
package com.uns.model.utilities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MPeriod;
import org.compiere.util.TimeUtil;

import com.uns.model.MUNSAttConfiguration;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSOTRequest;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSSlotType;
import com.uns.model.MUNSWorkHoursConfig;

/**
 * @author Burhani Adam
 *
 */
public class HRMUtils
{
	private MUNSEmployee m_employee;
	private Timestamp m_date;
	private Timestamp m_presenceDate;
	private MPeriod m_period;
	private Properties m_ctx;
	private String m_trxName;
	private MUNSPayrollConfiguration m_config;
	private MUNSAttConfiguration m_attConfig;
	private MUNSResource m_resource;
	private Timestamp m_start;
	private Timestamp m_end;
	private Timestamp m_timeInRule;
	private Timestamp m_maxLateTimeInRule;
	private Timestamp m_maxEarlierTimeInRule;
	private Timestamp m_timeOutRule;
	private Timestamp m_maxLateTimeOutRule;
	private Timestamp m_maxEarlierTimeOutRule;
	private MUNSSlotType m_slotType;
	private MUNSWorkHoursConfig m_workHoursConfig;
	private int m_defaultHourIn;
	private int m_defaultHourOut;
	private int m_defaultMinuteIn;
	private int m_defaultMinuteOut;
	private double m_defaultBreakTime = 0;
	private double m_breakTimeOnOT = 0;
	private double m_normalWorkHours;
	private double m_overTimeEvtHours;
	private double m_maxWorkHours;
	private double m_addWorkHours;
	private Timestamp m_startOT;
	private Timestamp m_endOT;
	private boolean m_isOverTime = false;
	private boolean m_isNationalHoliday = false;
	private boolean m_isWeeklyHoliday = false;
	private String m_day = null;

	/**
	 * 
	 */
	public HRMUtils(MUNSEmployee employee, Timestamp date)
	{
		super();
		m_employee = employee;
		m_date = date;
		m_ctx = employee.getCtx();
		m_trxName = employee.get_TrxName();
		m_config = MUNSPayrollConfiguration.get(
				getCtx(), getDate(), get_TrxName());
		if(m_attConfig == null)
		{
			m_attConfig = MUNSAttConfiguration.get(
					getCtx(), date, employee.getAD_Org_ID(), 
					employee.getEmploymentType(), 
					employee.getC_BPartner_ID(), 
					employee.getC_Job().getC_JobCategory_ID(), 
					get_TrxName());
		}
		m_resource = MUNSResource.getByEmployee(getCtx(), 
				employee.get_ID(), employee.get_TrxName());
		if(m_resource == null)
			return;
//		initSlotType();
		initWorkHoursConfig();
		initDefaultTime();
	}
	
	public HRMUtils(MUNSEmployee employee, Timestamp date, MUNSDailyPresence daily,
			MUNSSlotType st, MUNSAttConfiguration attConfig)
	{
		m_date = date;
		m_attConfig = attConfig;
		Calendar cal = Calendar.getInstance();
		cal.setTime(m_date);
		Calendar calIn = Calendar.getInstance();
		calIn.setTime(daily.getTimeInRules());
		Calendar calOut = Calendar.getInstance();
		calOut.setTime(daily.getTimeOutRules());

		Calendar calDay = Calendar.getInstance();
		calDay.setTime(daily.getPresenceDate());
		int day = calDay.get(Calendar.DAY_OF_WEEK);
		m_day = "" + day;
		m_slotType = st;
		m_isNationalHoliday = MNonBusinessDay.isNonBusinessDay(
				getCtx(), daily.getPresenceDate(), employee.getAD_Org_ID(), get_TrxName());
		m_isWeeklyHoliday = null != m_slotType && 
				!m_slotType.IsWorkDay(day); 
		walkOnOverTime(MUNSOTRequest.getValidof(get_TrxName(), m_date, employee.get_ID()));
		if(isOverTime())
		{
			long milisecond = m_endOT.getTime() - m_startOT.getTime();
			m_overTimeEvtHours = (double) milisecond /1000/60/60;
		}
		m_timeInRule = daily.getTimeInRules();
		m_timeOutRule = daily.getTimeOutRules();
		calIn.add(Calendar.MINUTE, m_attConfig.getMaxLateFSIn());
		m_maxLateTimeInRule = new Timestamp(calIn.getTimeInMillis());
		calOut.add(Calendar.MINUTE, -m_attConfig.getMaxEarLierFSOut());
		m_maxEarlierTimeOutRule  = new Timestamp(calOut.getTimeInMillis());
		
		if((cal.getTimeInMillis() <= m_maxLateTimeInRule.getTime()
				&& cal.getTimeInMillis() >= daily.getMinTimeInRule().getTime())
				|| (cal.getTimeInMillis() <= daily.getMaxTimeOutRule().getTime()
				&& cal.getTimeInMillis() >= m_maxEarlierTimeOutRule.getTime()))
		{
			m_presenceDate = daily.getPresenceDate();
		}
	}
	
	public MUNSAttConfiguration getAttConfig()
	{
		if(m_attConfig != null)
			return m_attConfig;
		
		return null;
	}
	
	public MUNSPayrollConfiguration getPayConfig()
	{
		if(m_config == null)
			m_config = MUNSPayrollConfiguration.get(
					getCtx(), getDate(), get_TrxName());
		
		return m_config;
	}
	
	/**
	 * 
	 * @param employee
	 * @param period
	 */
	public HRMUtils (MUNSEmployee employee, MPeriod period)
	{
		this(employee, period.getStartDate());
		m_period = period;
	}
	
	/**
	 * 
	 * @param employee
	 * @param C_Period_ID
	 */
	public HRMUtils (MUNSEmployee employee, int C_Period_ID)
	{
		this(employee, MPeriod.get(employee.getCtx(), C_Period_ID));
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param C_Period_ID
	 * @param trxName
	 */
	public HRMUtils (Properties ctx, int UNS_Employee_ID, 
			int C_Period_ID, String trxName)
	{
		this (new MUNSEmployee(ctx, UNS_Employee_ID, trxName), C_Period_ID);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param date
	 * @param trxName
	 */
	public HRMUtils (Properties ctx, int UNS_Employee_ID, 
			Timestamp date, String trxName)
	{
		this(new MUNSEmployee(ctx, UNS_Employee_ID, trxName), date);
	}
	
	private void initDefaultTime()
	{
		Calendar calIn = Calendar.getInstance();
		Calendar calOut = Calendar.getInstance();
		if(m_workHoursConfig != null && m_workHoursConfig.getUNS_SlotType_ID() <= 0)
		{
			calIn.setTime(m_workHoursConfig.getStartTime());
			calOut.setTime(m_workHoursConfig.getEndTime());
		}
		else if(m_slotType != null)
		{
			calIn.setTime(m_slotType.getTimeSlotStart());
			calOut.setTime(m_slotType.getTimeSlotEnd());
		}
		
		m_defaultHourIn = calIn.get(Calendar.HOUR_OF_DAY);
		m_defaultMinuteIn = calIn.get(Calendar.MINUTE);
		m_defaultHourOut = calOut.get(Calendar.HOUR_OF_DAY);
		m_defaultMinuteOut = calOut.get(Calendar.MINUTE);
	}
	
	/**
	 * 
	 * @return
	 */
	public String init ()
	{
		return init(false);
	}
	
	/** 
	 * @param forPresence
	 * @return
	 */
	public String init (boolean forPresence)
	{
		String msg = null;
		
		if (null == m_config)
		{
			msg = "Can't find payroll configuration.";
		}
		else if (null == m_employee)
		{
			msg = "Missing parameter employee.";
		}
		else if (null == m_date && null == m_period)
		{
			msg = "Required parameter date or period.";
		}
		else if (m_resource == null)
		{
			msg = "Can't find resource for " + m_employee.toString();
		}
		else if (m_slotType == null)
		{
			msg = "Can't find slot type configuration for " + m_employee.toString();
		}
		else if (getC_Period() != null)
		{
			msg = initByPeriod(forPresence);
		}
		else 
		{
			msg = initByDate(forPresence);
		}
		
		if (msg == null) 
		{
			validatePresenceDate();
			boolean isOverTimeOnHoliday = isOverTime() && (isNationalHoliday() || isWeeklyHoliday());
			if(!isOverTimeOnHoliday)
			{
				if(m_workHoursConfig != null && m_workHoursConfig.getUNS_SlotType_ID() <= 0
						&& m_workHoursConfig.isOverwriteBreakTime())
				{
					BigDecimal bd = m_workHoursConfig.getBreakTime();
					m_defaultBreakTime = bd.doubleValue();
				}
				else if (m_slotType != null && m_slotType.isDaySlot())
				{
					BigDecimal bd = m_slotType.getBreakTime(Integer.valueOf(getDay()));
					if (bd != null)
						m_defaultBreakTime = bd.doubleValue();
				}
			}
			initWorkHours();
		}
		
		return msg;
	}
	
	private String initByPeriod (boolean forPresence)
	{
		m_config.initPayrollPeriodOf(getC_Period().get_ID());
		m_start = m_config.getStartDate();
		m_end = m_config.getEndDate();
		
		if (!forPresence)
			return null;
		
		iniTimeSlotComponent();
		
		return null;
	}
	
	/**
	 * 
	 */
	private String initByDate (boolean forPresence)
	{
		m_config.initPayrollPeriodOf(getDate());
		m_period = MPeriod.get(getCtx(), m_config.getC_Period_ID ());
		m_start = m_config.getStartDate();
		m_end = m_config.getEndDate();
		
		if (!forPresence)
			return null;
		
		iniTimeSlotComponent();
		
		return null;
	}
	
	private void initWorkHours ()
	{
		initNormalWorkHours();
		if (m_isOverTime)
		{
			long milisecond = m_endOT.getTime() - m_startOT.getTime();
			m_overTimeEvtHours = (double) milisecond /1000/60/60;
		}
		else
			m_overTimeEvtHours = 0;
		m_maxWorkHours = m_normalWorkHours;
	}
	
	private void initNormalWorkHours()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(m_date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		m_day = "" + day;
		if(m_day.equals(m_config.getShortDay()))
			m_normalWorkHours =  (double) m_slotType.getShortDayWorkHours().longValue(); // - m_defaultBreakTime;
		else
			m_normalWorkHours =  (double) m_slotType.getNormalDayWorkHours().longValue();// - m_defaultBreakTime;

		initAddWorkHours();
	}
	
	private void initAddWorkHours()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(m_date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		m_day = "" + day;
		long workHours = m_defaultHourOut - m_defaultHourIn;
//		double workHours = (double) milisecond /1000/60/60;
		if(m_day.equals(m_config.getShortDay()))
			m_addWorkHours = workHours - (double) m_config.getShortDayWorkHours().longValue();
		else
			m_addWorkHours = workHours - (double) m_config.getNormalDayWorkHours().longValue();
	}
	
	/**
	 * Get C_Period model.
	 * @return
	 */
	public MPeriod getC_Period ()
	{
		return m_period;
	}
	
	private void iniTimeSlotComponent()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(m_workHoursConfig != null && m_workHoursConfig.getUNS_SlotType_ID() <= 0 ?
								m_workHoursConfig.getStartTime().getTime() 
									: m_slotType.getTimeSlotStart().getTime());
		cal.setTimeInMillis(m_date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, m_defaultHourIn);
		cal.set(Calendar.MINUTE, m_defaultMinuteIn);
		cal.set(Calendar.SECOND, 0);
		m_timeInRule = new Timestamp(cal.getTimeInMillis());
		
		cal.add(Calendar.MINUTE, m_attConfig.getMaxLateFSIn());
		m_maxLateTimeInRule = new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.MINUTE, -m_attConfig.getMaxLateFSIn());
		cal.add(Calendar.MINUTE, -m_attConfig.getMaxEarlierFSIn());
		m_maxEarlierTimeInRule = new Timestamp(cal.getTimeInMillis());
		
		cal.setTimeInMillis(m_date.getTime());
		if (m_defaultHourOut < m_defaultHourIn)
		{
			cal.add(Calendar.DATE, 1);
		}
		
		cal.set(Calendar.HOUR_OF_DAY, m_defaultHourOut);
		cal.set(Calendar.MINUTE, m_defaultMinuteOut);
		m_timeOutRule = new Timestamp(cal.getTimeInMillis());

		cal.add(Calendar.MINUTE, -m_attConfig.getMaxEarLierFSOut());
		m_maxEarlierTimeOutRule  = new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.MINUTE, m_attConfig.getMaxEarLierFSOut());
		cal.add(Calendar.MINUTE, m_attConfig.getMaxLateFSOut());
		m_maxLateTimeOutRule = new Timestamp(cal.getTimeInMillis());
	}
	
	private void initWorkHoursConfig()
	{
		MUNSWorkHoursConfig config = MUNSWorkHoursConfig.getByEmployee(getCtx(), m_resource.get_ID(), 
													m_employee.get_ID(), m_date, get_TrxName());
		if(config != null)
		{
			if(config.getUNS_SlotType_ID() > 0)
				m_slotType = new MUNSSlotType(getCtx(), config.getUNS_SlotType_ID(), get_TrxName());
			else
			{
				m_workHoursConfig = config;
			}
		}
		if(m_slotType == null)
			initSlotType();
		
		if(m_slotType != null && m_slotType.getUNS_AttConfiguration_ID() > 0)
		{
			m_attConfig = MUNSAttConfiguration.get(getCtx(), 
					m_slotType.getUNS_AttConfiguration_ID(), get_TrxName());
		}
	}
	
	private void initSlotType()
	{
		MUNSSlotType st = MUNSSlotType.getByEmployee(getCtx(), m_employee.get_ID(), get_TrxName());
		if(st != null)
			m_slotType = st;
	}
	
	/**
	 * Validate presence date by check date, if check date is not between time 
	 * in rule and time out rule the presence date will be set to null.
	 * @return
	 */
	private Timestamp validatePresenceDate ()
	{
		Calendar cal = Calendar.getInstance();
		walkOnOverTime(MUNSOTRequest.getValidof(
				get_TrxName(), getDate(), m_employee.get_ID()));
		//minimum waktu masuk
		cal.setTimeInMillis(m_maxEarlierTimeInRule.getTime());
		int hourIn = cal.get(Calendar.HOUR_OF_DAY);
		int minuteIn = cal.get(Calendar.MINUTE);
		
		//tanggal dan waktu absen
		cal.setTimeInMillis(m_date.getTime());
		int checkHour = cal.get(Calendar.HOUR_OF_DAY);
		int checkMinute = cal.get(Calendar.MINUTE);
		
		//maximum waktu keluar
		cal.setTimeInMillis(m_maxLateTimeOutRule.getTime());
		int hourOut = cal.get(Calendar.HOUR_OF_DAY);
		int minuteOut = cal.get(Calendar.MINUTE);
	
		if (checkHour < hourIn || (checkHour == hourIn 
				&& checkMinute < minuteIn))
		{
			cal.add(Calendar.DATE, -1);
		}
		else if (checkHour > hourOut || (checkHour == hourOut
				&& checkMinute > minuteOut))
		{
			cal.add(Calendar.DATE, 1);
			hourOut = hourOut + 24;
		}
		
		if ((checkHour > hourIn && checkHour < hourOut)
				|| ((checkHour == hourIn && checkMinute >= minuteIn)
				|| (checkHour == hourOut&& checkHour <= minuteOut)))
		{
			m_presenceDate = TimeUtil.trunc(
					new Timestamp(cal.getTimeInMillis()), 
					TimeUtil.TRUNC_DAY);
		}
		else if (isOverTime())
		{
			if (getDate().equals(getStartOverTime()) 
					|| getDate().equals(getEndOverTime())
					|| (getDate().before(getEndOverTime()) 
							&& getDate().after(getStartOverTime())))
			{
				m_presenceDate = TimeUtil.trunc(m_date, TimeUtil.TRUNC_DAY);
			}
		}
		
		cal.setTime(m_date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		m_day = "" + day;
//		if (MUNSEmployee.SHIFT_Shift.equals(m_employee.getShift()))
//		{
			m_isWeeklyHoliday = null != m_slotType && 
					!m_slotType.IsWorkDay(day);
//		}
//		else
//		{
//			m_isWeeklyHoliday = !m_employee.isWorkDay(getDay ());
//		}
			
		m_isNationalHoliday = MNonBusinessDay.isNonBusinessDay(
				getCtx(), m_presenceDate == null ? m_date : m_presenceDate, m_employee.getAD_Org_ID(), get_TrxName());
		if (null == getPresenceDate())
		{
			return null;
		}
		
		return m_presenceDate;
	}
	
	private void walkOnOverTime (MUNSOTRequest request)
	{
		if (null == request)
		{
			return;
		}
		m_startOT = request.getStartTime();
		m_endOT = request.getEndTime();
		m_isOverTime = true;
		m_breakTimeOnOT = request.getBreakTime().doubleValue();
	}
	
	public Timestamp getStartOverTime ()
	{
		return m_startOT;
	}
	
	public Timestamp getEndOverTime ()
	{
		return m_endOT;
	}
	
	public boolean isOverTime ()
	{
		return m_isOverTime;
	}
	
	public boolean isNationalHoliday ()
	{
		return m_isNationalHoliday;
	}
	
	public boolean isWeeklyHoliday ()
	{
		return m_isWeeklyHoliday;
	}
	
	/**
	 * Get presence date after validation
	 * @return
	 */
	public Timestamp getPresenceDate ()
	{
		return m_presenceDate;
	}
	
	public String getDay ()
	{
		return m_day;
	}
	
	public double getMaxWorkHours ()
	{
		return m_maxWorkHours;
	}
	
	/**
	 * Get contexts
	 * @return
	 */
	public Properties getCtx ()
	{
		return m_ctx;
	}
	
	/**
	 * Get transaction name.
	 * @return
	 */
	public String get_TrxName ()
	{
		return m_trxName;
	}
	
	/**
	 * @return
	 */
	public Timestamp getDate ()
	{
		return m_date;
	}
	
	 /**
	 * Get start date of payroll period
	 * @return
	 */
	public Timestamp getStartDate ()
	{
		return m_start;
	}
	
	/**
	 * Get end date of payroll period
	 * @return
	 */
	public Timestamp getEndDate ()
	{
		return m_end;
	}
	
	/**
	 * Get time in rule
	 * @return
	 */
	public Timestamp getTimeInRules ()
	{
		return m_timeInRule;
	}
	
	/**
	 * Get time out rule
	 * @return
	 */
	public Timestamp getTimeOutRules ()
	{
		return m_timeOutRule;
	}
	
	/**
	 * Get maximum earlier allowed time in to finger scan
	 * @return
	 */
	public Timestamp getMaxEarlierTimeInRules ()
	{
		return m_maxEarlierTimeInRule;
	}
	
	/**
	 * Get maximum late allowed time in to finger scan
	 * @return
	 */
	public Timestamp getMaxLateTimeInRules ()
	{
		return m_maxLateTimeInRule;
	}
	
	/**
	 * Get maximum earlier  allowed time out to finger scan
	 * @return
	 */
	public Timestamp getMaxEarlierTimeOutRules ()
	{
		return m_maxEarlierTimeOutRule;
	}
	
	/**
	 * Get maximum late allowed time out t finger scan
	 * @return
	 */
	public Timestamp getMaxLateTimeOutRules ()
	{
		return m_maxLateTimeOutRule;
	}
	
	/**
	 * Get Maximum hours over time
	 * @return
	 */
	public double getMaxOTHours ()
	{
		return m_overTimeEvtHours;
	}
	
	public boolean isAutoSynchronize()
	{
		if(m_slotType != null)
		{
			return m_slotType.isAutoSynchronize();
		}
		
		return true;
	}
	
	public double getAddWorkHours()
	{
		return m_addWorkHours;
	}
	
	public double getNormalWorkHours()
	{
		return m_normalWorkHours;
	}
	
	public double getDefaultBreakTime()
	{
		return m_defaultBreakTime;
	}
	
	public MUNSSlotType getSlotType()
	{
		return m_slotType;
	}
	
	public double getBreakTimeOnOT()
	{
		return m_breakTimeOnOT;
	}
}