/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;

import com.uns.model.MUNSMonthlyPayrollEmployee;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author Menjangan
 *
 */
public class CalloutMonthlyPayrollEmployee implements IColumnCallout 
{

	/**
	 * 
	 */
	public CalloutMonthlyPayrollEmployee() 
	{
		super();
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int,
	 *  org.compiere.model.GridTab, org.compiere.model.GridField, 
	 *  java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		String columnName = mField.getColumnName();
		if (MUNSMonthlyPayrollEmployee.COLUMNNAME_C_Period_ID.
				equals(columnName)) 
		{
			return onC_PeriodChanged(ctx, WindowNo, mTab, mField, 
					value, oldValue);
		} 
		else if (MUNSMonthlyPayrollEmployee.COLUMNNAME_PeriodType.
				equals(columnName))
		{
			return onPeriodTypeChanged(ctx, WindowNo, mTab, mField, 
					value, oldValue);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onPeriodTypeChanged (Properties ctx, int WindowNo, 
			GridTab mTab, GridField mField, Object value, Object oldValue) 
	{
		if (null == value)
		{
			return null;
		}
		
		Integer C_Period_ID = (Integer) mTab.getValue("C_Period_ID");
		if (null == C_Period_ID)
		{
			return null;
		}
		else if (MUNSMonthlyPayrollEmployee.PERIODTYPE_1stWeek.equals(value))
		{
			return walkInWeekly(ctx, mTab, 1);
		}
		else if (!MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month.equals(value))
		{
			return "Period Type you are choosed is not impletemented";
		}
		
		return walkInMonthly(ctx, mTab);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onC_PeriodChanged (Properties ctx, int WindowNo, 
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			return null;
		}
		
		String periodType = (String) mTab.get_ValueAsString("PeriodType");
		if (periodType == null)
		{
			return null;
		}
		else if (MUNSMonthlyPayrollEmployee.PERIODTYPE_1stWeek.equals(periodType))
		{
			return walkInWeekly(ctx, mTab, 1);
		}
		else if (!MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month.
				equals(periodType))
		{
			return "Period Type you are choosed is not impletemented";
		}
		
		return walkInMonthly(ctx, mTab);
	}
	
	private String walkInWeekly (Properties ctx, GridTab mTab, int weekNo)
	{
		Integer C_Period_ID = (Integer) mTab.getValue("C_Period_ID");
		MPeriod period = MPeriod.get(ctx, C_Period_ID);
		int month = period.getPeriodNo();
		String fiscalyear = DB.getSQLValueString(
				null, 
				"SELECT FiscalYear FROM C_Year WHERE C_Year_ID = ? ",
				period.getC_Year_ID());
		if (null == fiscalyear)
		{
			return null;
		}
		
		Integer year = new Integer(fiscalyear);
		Calendar cal = Calendar.getInstance(Locale.UK);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		int maxWeekInMonth = cal.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
		Timestamp start, end;
		if (weekNo > maxWeekInMonth)
		{
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.WEEK_OF_MONTH, 1);
			cal.set(Calendar.DAY_OF_WEEK, 2);
		}
		else
		{
			cal.set(Calendar.WEEK_OF_MONTH, weekNo);
			cal.set(Calendar.DAY_OF_WEEK, 2);
		}
		
		start = new Timestamp(cal.getTimeInMillis());
		cal.set(Calendar.DAY_OF_WEEK, 1);
		end = new Timestamp(cal.getTimeInMillis());
		
		mTab.setValue("StartDate", start);
		mTab.setValue("EndDate", end);
		
		return null;
	}
	
	private String walkInMonthly (Properties ctx, GridTab mTab)
	{
		Integer C_Period_ID = (Integer) mTab.getValue("C_Period_ID");
		MPeriod period = MPeriod.get(ctx, C_Period_ID);
		MUNSPayrollConfiguration config = MUNSPayrollConfiguration.get(
				ctx, period, null);
		if (null == config)
		{
			return null;
		}
		
		config.initPayrollPeriodOf(C_Period_ID);
		mTab.setValue("StartDate", config.getStartDate());
		mTab.setValue("EndDate", config.getEndDate());
		
		return null;
	}
	
	public static void main (String[] args)
	{
		Calendar cal = Calendar.getInstance(Locale.UK);
//		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		System.out.println(cal.get(Calendar.DATE));
	}
}