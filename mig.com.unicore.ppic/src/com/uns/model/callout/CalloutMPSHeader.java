/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

import com.uns.model.MUNSForecastHeader;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.X_UNS_MPSHeader;

/**
 * @author menjangan
 *
 */
public class CalloutMPSHeader extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutMPSHeader() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		return forecastHeader(ctx, WindowNo, mTab, mField, value, oldValue);
	}
	
	private String forecastHeader(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return "";
		
		int UNS_Forecast_Header_ID = (Integer)value;
		
		if (UNS_Forecast_Header_ID <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MUNSForecastHeader forecastHeader = new MUNSForecastHeader(ctx, UNS_Forecast_Header_ID, null);
		Integer prevMPSHeader_ID = DB.getSQLValue(
				null, "SELECT " + MUNSMPSHeader.COLUMNNAME_UNS_MPSHeader_ID 
				+ " FROM " + MUNSMPSHeader.Table_Name 
				+ " WHERE " 
				+ MUNSMPSHeader.COLUMNNAME_UNS_Forecast_Header_ID + " = " + forecastHeader.getPrevForecast_ID()
				+ " AND " + MUNSMPSHeader.COLUMNNAME_IsActive + " = 'Y'");
		
		if(prevMPSHeader_ID > 0)
			mTab.setValue(MUNSMPSHeader.COLUMNNAME_PrevMPS_ID, prevMPSHeader_ID);
		else
			mTab.setValue(MUNSMPSHeader.COLUMNNAME_PrevMPS_ID, null);
		
		mTab.setValue(X_UNS_MPSHeader.COLUMNNAME_PeriodStart_ID, forecastHeader.getPeriodStart_ID());
		mTab.setValue(X_UNS_MPSHeader.COLUMNNAME_PeriodEnd_ID, forecastHeader.getPeriodEnd_ID());
		return "";
	}
}
