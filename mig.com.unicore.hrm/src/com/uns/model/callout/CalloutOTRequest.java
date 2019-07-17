package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSOTRequest;

public class CalloutOTRequest implements IColumnCallout {
	
	public CalloutOTRequest() 
	{
		super();
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String msg = null;
		if (mField.getColumnName().equals(
				MUNSOTRequest.COLUMNNAME_DateDoOT))
		{
			msg = onDateDoOT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(
				MUNSOTRequest.COLUMNNAME_StartTime))
		{
			msg = onStartTime(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(
				MUNSOTRequest.COLUMNNAME_EndTime))
		{
			msg = onEndTime(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(
				MUNSOTRequest.COLUMNNAME_RequestedHours))
		{
			msg = onRequestHours(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return msg;
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
	private String onDateDoOT (Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			mTab.setValue(MUNSOTRequest.COLUMNNAME_StartTime, null);
			mTab.setValue(MUNSOTRequest.COLUMNNAME_EndTime, null);
			mTab.setValue(MUNSOTRequest.COLUMNNAME_RequestedHours, null);
			return null;
		}
		
		mTab.setValue(MUNSOTRequest.COLUMNNAME_StartTime, value);
		
		return onStartTime(ctx, WindowNo, mTab, mField, value, oldValue);
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
	private String onStartTime (Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			return null;
		}
		Timestamp start = (Timestamp) value;
		Object ooEnd = mTab.getValue(MUNSOTRequest.COLUMNNAME_EndTime);
		if (null == ooEnd)
		{
			return null;
		}
		
		Timestamp end = (Timestamp) ooEnd;
		long startTime = start.getTime();
		long endTime = end.getTime();
		double range = (double) endTime - (double) startTime;
		range = range / 1000 / 60 / 60;
		double breaktime = ((BigDecimal) mTab.getValue(MUNSOTRequest.COLUMNNAME_BreakTime)).doubleValue();
		range = range - breaktime;
		mTab.setValue(MUNSOTRequest.COLUMNNAME_RequestedHours, 
				new BigDecimal(range));
		
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
	private String onEndTime (Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			return null;
		}
		Timestamp end = (Timestamp) value;
		Object ooStart = mTab.getValue(MUNSOTRequest.COLUMNNAME_StartTime);
		if (null == ooStart)
		{
			return null;
		}
		
		Timestamp start = (Timestamp) ooStart;
		long startTime = start.getTime();
		long endTime = end.getTime();
		double range = (double) endTime - (double) startTime;
		range = range / 1000 / 60 / 60;
		double breaktime = ((BigDecimal) mTab.getValue(MUNSOTRequest.COLUMNNAME_BreakTime)).doubleValue();
		range = range - breaktime;
		
		mTab.setValue(MUNSOTRequest.COLUMNNAME_RequestedHours, 
				new BigDecimal (range));
		
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
	private String onRequestHours (Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			mTab.setValue(MUNSOTRequest.COLUMNNAME_EndTime, null);
			return null;
		}
		
		Object ooStart = mTab.getValue(MUNSOTRequest.COLUMNNAME_StartTime);
		if (null == ooStart)
		{
			return null;
		}
		int breaktime = ((BigDecimal) mTab.getValue(MUNSOTRequest.COLUMNNAME_BreakTime)).intValue();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(((Timestamp) ooStart).getTime());
		cal.add(Calendar.HOUR_OF_DAY, ((BigDecimal)value).intValue() - breaktime);
		mTab.setValue(MUNSOTRequest.COLUMNNAME_EndTime, 
				new Timestamp(cal.getTimeInMillis()));
		
		return null;
	}
}