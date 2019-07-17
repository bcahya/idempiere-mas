/**
 * 
 */
package com.uns.model;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;


/*
 * @author Az's
 */
public class MUNSDailyPresence extends X_UNS_DailyPresence 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSMonthlyPresenceSummary m_monthlySummary = null;
	private MUNSMonthlyPresenceVal m_presenceVal = null;

	/**
	 * @param ctx
	 * @param UNS_DailyPresence_ID
	 * @param trxName
	 */
	public MUNSDailyPresence(Properties ctx, int UNS_DailyPresence_ID, 
			String trxName) 
	{
		super(ctx, UNS_DailyPresence_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param
	 * @param trxName
	 */
	public MUNSDailyPresence(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public MUNSMonthlyPresenceSummary getParent() 
	{
		if (m_monthlySummary != null)
			return m_monthlySummary;
		m_monthlySummary = (MUNSMonthlyPresenceSummary)
				getUNS_MonthlyPresenceSummary();
		return m_monthlySummary;
	}
	
	public MUNSDailyPresence(MUNSMonthlyPresenceSummary parent) 
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_MonthlyPresenceSummary_ID(parent.get_ID());
	}
	

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getTotalWorkHours()
	{
		double totalWorkHours = 0.0;
		MUNSMonthlyPresenceSummary monthlyPresence = getParent();
		for(MUNSDailyPresence dayPresence : monthlyPresence.getLines(null))
		{
			totalWorkHours += dayPresence.getWorkHours().doubleValue();
		}
		return new BigDecimal(totalWorkHours);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess)
	{
		MUNSMonthlyPresenceSummary monthly = getParent();
		
		String sql = "SELECT COUNT(*) FROM UNS_DailyPresence WHERE UNS_DailyPresence_ID <> ?"
				+ " AND PresenceDate = ? AND UNS_MonthlyPresenceSummary_ID = ?";
		int count = DB.getSQLValue(get_TrxName(), sql, get_ID(), getPresenceDate(), monthly.get_ID());
		if(count > 0)
		{
			log.saveError("Error", monthly.getUNS_Employee().getName() + " - " + getPresenceDate());
			return false;
		}
		
		if (!monthly.updateData())
		{
			ErrorMsg.setErrorMsg(getCtx(), "Could not update monthly record.", 
					"Failed when updating the monthly summary.");
			return false;
		}
		
		return super.afterSave(newRecord, sucess);
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public String getDay(int day)
	{		
		switch(day) {
		case 1 :
			return DAY_Sunday;
		case 2 :
			return DAY_Monday;
		case 3 :
			return DAY_Tuesday;
		case 4 :
			return DAY_Wednesday;
		case 5 :
			return DAY_Thursday;
		case 6 :
			return DAY_Friday;
		case 7 :
			return DAY_Saturday;
		default : 
			return null;
		}
	}	

	/**
	 * Get it or create it if not exist.
	 * 
	 * @param ctx
	 * @param date
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSDailyPresence getCreate(
			Timestamp date, MUNSEmployee employee)
	{
		MUNSDailyPresence dailyPresence = getByDate(
				employee.getCtx(), date, employee.get_ID(), 
				employee.get_TrxName());
		
		if (dailyPresence != null)
		{
			return dailyPresence;
		}
		
		dailyPresence = new MUNSDailyPresence(date, employee);
		dailyPresence.saveEx();
		
		return dailyPresence;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param date
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSDailyPresence getByDate(Properties ctx, Timestamp date,
			int UNS_Employee_ID, String trxName)
	{
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		String whereClause = "PresenceDate=? AND UNS_MonthlyPresenceSummary_ID "
				+ "IN (SELECT UNS_MonthlyPresenceSummary_ID FROM "
				+ " UNS_MonthlyPresenceSummary WHERE UNS_Employee_ID=?)";
		
		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID,
				Table_Name, whereClause, trxName)
				.setParameters(date, UNS_Employee_ID)
				.first();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param leave
	 * @param trxName
	 * @return
	 */
  public static BigDecimal updateDailyPresence(MUNSLeavePermissionTrx leave)
  {
		BigDecimal countCuti = Env.ZERO;
		Timestamp dateStart = leave.getLeaveDateStart();
		Timestamp dateEnd = leave.getLeaveDateEnd();
		Timestamp loopDate = dateStart;
		boolean startIsHalfDay = leave.getLeavePeriodType().equals(
				MUNSLeavePermissionTrx.
				LEAVEPERIODTYPE_StartDateIsHalfDay);
		boolean endIsHalfDay = leave.getLeavePeriodType().equals(
				MUNSLeavePermissionTrx.LEAVEPERIODTYPE_EndDateIsHalfDay);
		boolean startEndIsHalfDay = leave.getLeavePeriodType().equals(
				MUNSLeavePermissionTrx.
				LEAVEPERIODTYPE_StartAndEndDateIsHalfDay);
		
		while (!loopDate.after(dateEnd))
		{						
			MUNSDailyPresence currDay = MUNSDailyPresence.getCreate(
					loopDate, leave.getEmployee());
			if (loopDate.equals(dateStart) && (startEndIsHalfDay
					|| startIsHalfDay))
			{
				countCuti = countCuti.add(new BigDecimal(0.5));
			}
			else if (loopDate.equals(dateEnd) && (startEndIsHalfDay 
					|| endIsHalfDay))
			{
				countCuti = countCuti.add(new BigDecimal(0.5));
			}
			else
			{
				countCuti = countCuti.add(Env.ONE);
			}			
			
			currDay.setDayPresence(leave);
			String description = "";
			
			if (!MUNSLeavePermissionTrx.ACTION_Void.equals(
					leave.getDocAction()))
			{
				description = currDay.getDescription() == null?
						"***Set by leave permission request***"
						: currDay.getDescription() + 
						"\n***Set by leave permission request***";
			}	
			else 
			{
				String userName = MUser.getNameOfUser(
						Env.getAD_User_ID(leave.getCtx()));
				Timestamp currTS = new Timestamp(System.currentTimeMillis());
				
				description = currDay.getDescription() == null? "" : 
					currDay.getDescription() + "\n";
				currDay.setDescription(description + 
						"***Void leave permission by  " + userName + 
						" @" + currTS + "***");
			}
			
			currDay.setDescription(description);
			currDay.setPresenceStatus(X_UNS_DailyPresence.PRESENCESTATUS_Izin);
			currDay.saveEx();
			loopDate = TimeUtil.addDays(loopDate, 1);
		}
		
		return countCuti;
  }
	
	/**
	 * Set daily presence permission type to the leave requested type.
	 * Jika presence status awal adalah mangkir atau libur ubah menjadi Izin.
	 * Jika halfday pastikan Overtime nya adalah Env.Zero.
	 * @param leave
	 */
	public void setDayPresence(MUNSLeavePermissionTrx leave) 
	{
		setPermissionType(null);
		
		if (DocAction.ACTION_Void.equals(leave.getDocAction()))
		{
			setPresenceStatus(PRESENCESTATUS_FullDay);
			return;
		}
		// Cek apakah presence date merupakan ijin setengah hari?
		boolean isHalfDay = false;
		int daysDiff = TimeUtil.getDaysBetween(leave.getLeaveDateStart(),
				getPresenceDate());
		if (daysDiff == 0)
		{
			if (MUNSLeavePermissionTrx.LEAVEPERIODTYPE_StartAndEndDateIsHalfDay.
					equals(leave.getLeavePeriodType())
				|| MUNSLeavePermissionTrx.LEAVEPERIODTYPE_StartDateIsHalfDay.
				equals(leave.getLeavePeriodType()))
			{
				isHalfDay = true;
			}
		}
			
		if (!isHalfDay)
		{
			daysDiff = TimeUtil.getDaysBetween(getPresenceDate(), 
					leave.getLeaveDateEnd());
			if (daysDiff == 0)
			{
				if (MUNSLeavePermissionTrx.
						LEAVEPERIODTYPE_StartAndEndDateIsHalfDay.equals(
								leave.getLeavePeriodType())
						|| MUNSLeavePermissionTrx.
						LEAVEPERIODTYPE_EndDateIsHalfDay.equals(
								leave.getLeavePeriodType()))
				{
					isHalfDay = true;
				}
			}
		}
		
		if (isHalfDay) 
		{
			setPresenceStatus(PRESENCESTATUS_HalfDay);
		}
		else
		{
			setPresenceStatus(PRESENCESTATUS_Izin);
			setPermissionType(leave.getLeaveType());
		}
		
		setOvertime(Env.ZERO);
	}
	
	public String toString()
	{
		return "[" + get_ID() + "-" + getDay() + "-" + getDayType() + "-" 
				+ getPresenceDate() + "]";
	}
	
	public MUNSDailyPresence (Timestamp date, MUNSEmployee employee)
	{
		this(employee.getCtx(), 0, employee.get_TrxName());
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		
		setClientOrg(employee);
		setPresenceDate(date);
		setPresenceStatus(PRESENCESTATUS_FullDay);
		MPeriod period = MPeriod.get(getCtx(), date, employee.getAD_Org_ID(), get_TrxName());
		MUNSPayrollConfiguration config =
				MUNSPayrollConfiguration.get(
						getCtx(), period, get_TrxName());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		if(calendar.get(Calendar.MONTH) == 11)
		{
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if(day >= config.getPayrollDateStart())
				calendar.add(Calendar.YEAR, 1);
		}
		Integer year = calendar.get(Calendar.YEAR);
		String fiscalYear = year.toString();
		
		int C_Year_ID = DB.getSQLValue(
				get_TrxName(), 
				"SELECT C_Year_ID FROM C_Year WHERE FiscalYear = ?", 
				"" + year);
		
		if (C_Year_ID == -1)
		{
			throw new AdempiereUserError("Undefined Year " + year);
		}
		
		MUNSYearlyPresenceSummary yearly = MUNSYearlyPresenceSummary.get(
				getCtx(), employee.get_ID(), fiscalYear, get_TrxName());
		
		if (null == yearly)
		{
			yearly = new MUNSYearlyPresenceSummary(
					getCtx(), employee, C_Year_ID, get_TrxName());
			yearly.saveEx();
		}
		
		MUNSMonthlyPresenceSummary monthly = yearly.getMonth(date);
		
		if (null == monthly)
		{
			monthly = new MUNSMonthlyPresenceSummary(yearly, date);
			monthly.saveEx();
		}
		
		setUNS_MonthlyPresenceSummary_ID(monthly.get_ID());
	}
	
	@Override
	public String getDay ()
	{
		String day = super.getDay();
		if (null == day)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getPresenceDate().getTime());
			Integer dayInt = cal.get(Calendar.DAY_OF_WEEK);
			day = dayInt.toString();
			super.setDay(day);
		}
		
		return day;
	}
	
	public MUNSMonthlyPresenceVal getValidation ()
	{
		if (null != m_presenceVal)
		{
			return m_presenceVal;
		}
		
		int presenceVal_ID = get_ValueAsInt("UNS_MonthlyPresenceVal_ID");
		if (presenceVal_ID <= 0)
		{
			return null;
		}
		
		m_presenceVal = new MUNSMonthlyPresenceVal(
				getCtx(), presenceVal_ID, get_TrxName());
		return m_presenceVal;
	}
}