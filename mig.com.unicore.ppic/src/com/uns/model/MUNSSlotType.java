/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author menjangan
 *
 */
public class MUNSSlotType extends X_UNS_SlotType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SlotType_ID
	 * @param trxName
	 */
	public MUNSSlotType(Properties ctx, int UNS_SlotType_ID, String trxName) {
		super(ctx, UNS_SlotType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSlotType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public int getMaxDay(int year, int month)
	{
		Calendar calendar = Calendar.getInstance();
		int maxDay = 0;
		calendar.set(year, month, 1);
		maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public boolean IsWorkDay(int day)
	{
		if (!isDaySlot())
			return true;
		switch (day)
		{
		case 1 :
			return isOnSunday();
		case 2 :
			return isOnMonday();
		case 3 :
			return isOnTuesday();
		case 4 :
			return isOnWednesday();
		case 5 :
			return isOnThursday();
		case 6 :
			return isOnFriday();
		case 7 :
			return isOnSaturday();
			default :
				return false;
		}
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public BigDecimal getBreakTime(int day)
	{
		BigDecimal preparation = BigDecimal.ZERO;
		switch (day)
		{
		case 1 :
			preparation = getBreakTimeSunday();
			break;
		case 2 :
			preparation = getBreakTimeMonday();
			break;
		case 3 :
			preparation = getBreakTimeTuesday();
			break;
		case 4 :
			preparation = getBreakTimeWednesday();
			break;
		case 5 :
			preparation = getBreakTimeThursday();
			break;
		case 6 :
			preparation = getBreakTimeFriday();
			break;
		case 7 :
			preparation = getBreakTimeSaturday();
			break;

		default : preparation = BigDecimal.ZERO;
		}
		return preparation;
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public BigDecimal getPreparationTime(int day)
	{
		BigDecimal preparation = BigDecimal.ZERO;
		switch (day)
		{
		case 1 :
			preparation = getPreparationTimeSunday();
			break;
		case 2 :
			preparation = getPreparationTimeMonday();
			break;
		case 3 :
			preparation = getPreparationTimeTuesday();
			break;
		case 4 :
			preparation = getPreparationTimeWednesday();
			break;
		case 5 :
			preparation = getPreparationTimeThursday();
			break;
		case 6 :
			preparation = getPreparationTimeFriday();
			break;
		case 7 :
			preparation = getPreparationTimeSaturday();
			break;
		default : preparation = BigDecimal.ZERO;
		}
		return preparation;
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public Integer getWorkDay(Calendar calendar)
	{
		Integer WorkDay = 0;
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int MaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i=1; i<=MaxDay; i++)
		{
			int DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (IsWorkDay(DayOfWeek))
				WorkDay++;
			calendar.add(Calendar.DATE, 1);
		}
		return WorkDay;
	}
	
	/**
	 * 
	 * @param yearNo
	 * @param month
	 * @return Amount of Work day in month
	 */
	public Integer getWorkDay(int yearNo, int month)
	{
		Integer WorkDay = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearNo,month,1);
		int MaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i=1; i<=MaxDay; i++)
		{
			int DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (IsWorkDay(DayOfWeek))
				WorkDay++;
			calendar.add(Calendar.DATE, 1);
		}
		return WorkDay;
	}
	
	/**
	 * 
	 * @param day
	 * @return BigDecimal workt time in hour
	 */
	public BigDecimal getWorkTime(int day)
	{
		BigDecimal WorkTime = new BigDecimal(24);
		if (isDaySlot())
		{
			if (IsWorkDay(day))
			{
				if (isTimeSlot())
					WorkTime = getRangeWorkTime();
				BigDecimal PT = getPreparationTime(day).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
				BigDecimal BT = getBreakTime(day).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
				WorkTime = WorkTime.subtract(PT.add(BT));
				/*
				if (isTimeSlot())
				{
					WorkTime = getRangeWorkTime();
					BigDecimal PT = getPreparationTime(day).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
					BigDecimal BT = getBreakTime(day).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
					WorkTime = WorkTime.subtract(PT.add(BT));
				}
				*/
			}
			else
			{
				WorkTime = BigDecimal.ZERO;
			}
		}
		
		return WorkTime;
	}
	
	/**
	 * 
	 * @return BigDecimal WorkTime in hour
	 */
	public BigDecimal getRangeWorkTime()
	{
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
//		if (isTimeSlot())
//		{
	
		calStart.setTimeInMillis(getTimeSlotStart().getTime());
		calEnd.setTimeInMillis(getTimeSlotEnd().getTime());
		int startHour = calStart.get(Calendar.HOUR_OF_DAY);
		int startMinute = calStart.get(Calendar.MINUTE);
		
		int endHour = calEnd.get(Calendar.HOUR_OF_DAY);
		int endMinute = calEnd.get(Calendar.MINUTE);
		float workTime = 0;
		if (calStart.get(Calendar.AM_PM) == Calendar.PM && calEnd.get(Calendar.AM_PM) == Calendar.AM)
		{
			workTime = (60 - startMinute)/60;
			workTime += endMinute/60;
			workTime += (24 - startHour - 1);
			workTime += endHour;
		}
		else 
		{
			workTime = (60 - startMinute)/60;
			workTime += endMinute/60;
			workTime += (endHour - startHour - 1);
		}
//		}
		
		return new BigDecimal(workTime).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static MUNSSlotType getByEmployee(Properties ctx, int UNS_Employee_ID, String trxName)
	{
		MUNSSlotType st = null;
		
		String whereClause = "UNS_SlotType_ID ="
				+ " (SELECT rs.UNS_SlotType_ID FROM UNS_Resource rs"
				+ " WHERE rs.C_DocType_ID IN (SELECT dt.C_DocType_ID FROM C_DocType dt WHERE dt.DocBaseType = 'EMP')"
				+ " AND rs.UNS_Resource_ID IN (SELECT wl.UNS_Resource_ID FROM UNS_Resource_WorkerLine wl"
				+ " WHERE wl.Labor_ID = ? AND wl.IsActive = 'Y'))";
		
		st = new Query(ctx, Table_Name, whereClause, trxName).setParameters(UNS_Employee_ID)
						.first();
		
		return st;
	}
}