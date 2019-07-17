/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSOTConfirmation;

/**
 * @author Menjangan
 *
 */
public class CalloutOTConfirmation implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutOTConfirmation() 
	{
		super ();
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
		String colName = mField.getColumnName();
		if (colName.equals(MUNSOTConfirmation.COLUMNNAME_EndTime))
		{
			return onEndTime(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (colName.equals(MUNSOTConfirmation.COLUMNNAME_ConfirmedHours))
		{
			return onHours(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else 
		{
			return null;
		}
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
		Object ooStart = mTab.getValue(MUNSOTConfirmation.COLUMNNAME_StartTime);
		if (null == ooStart)
		{
			return null;
		}
		
		Timestamp start = (Timestamp) ooStart;
		long startTime = start.getTime();
		long endTime = end.getTime();
		double range = (double) endTime - (double) startTime;
		range = range / 1000 / 60 / 60;
		
		mTab.setValue(MUNSOTConfirmation.COLUMNNAME_ConfirmedHours, 
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
	private String onHours (Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
		{
			mTab.setValue(MUNSOTConfirmation.COLUMNNAME_EndTime, null);
			return null;
		}
		
		Object ooStart = mTab.getValue(MUNSOTConfirmation.COLUMNNAME_StartTime);
		if (null == ooStart)
		{
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(((Timestamp) ooStart).getTime());
		cal.add(Calendar.HOUR_OF_DAY, ((BigDecimal) value).intValue());
		mTab.setValue(MUNSOTConfirmation.COLUMNNAME_EndTime, 
				new Timestamp(cal.getTimeInMillis()));
		
		return null;
	}

}
