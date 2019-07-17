package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;

import com.uns.model.I_UNS_PeriodicCostBenefit;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPeriodicCostBenefit;

public class CalloutPeriodicCostBenefit implements IColumnCallout {
	
	public CalloutPeriodicCostBenefit() {
		
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(value == null)
			return null;
		
		String retVal = null;
		
		if(mTab.getTableName().equals(MUNSPeriodicCostBenefit.Table_Name))
		{
			retVal = setDate(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return retVal;
	}
	
	@SuppressWarnings("unused")
	public String setDate(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		int yearID = (Integer) mTab.getValue(I_UNS_PeriodicCostBenefit.COLUMNNAME_C_Year_ID);
		
		String sql = "SELECT FiscalYear FROM C_Year WHERE C_Year_ID = ?";
		int year = (Integer) DB.getSQLValue(null, sql, yearID);
		
		if(mField.getColumnName().equals(MUNSPeriodicCostBenefit.COLUMNNAME_C_Period_ID))
		{
			int periodID = Integer.valueOf(value.toString());
			
			MPeriod period = new MPeriod(ctx, periodID, null);
			if(period == null)
				return null;
			
			MUNSPayrollConfiguration payrollConfig = MUNSPayrollConfiguration.get(ctx, period, null);
			if(payrollConfig == null)
				return null;
			
			payrollConfig.initPayrollPeriodOf(periodID);
			dateFrom = payrollConfig.getStartDate();
			dateTo = payrollConfig.getEndDate();
			
		}
		else if(mField.getColumnName().equals(MUNSPeriodicCostBenefit.COLUMNNAME_WeekNo))
		{
			int week = Integer.valueOf(value.toString());
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.WEEK_OF_YEAR, week);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			dateFrom = new Timestamp(cal.getTimeInMillis());
			
			cal.add(Calendar.DATE, 6);
			dateTo = new Timestamp(cal.getTimeInMillis());
		}
		
		mTab.setValue(I_UNS_PeriodicCostBenefit.COLUMNNAME_DateFrom, dateFrom);
		
		mTab.setValue(I_UNS_PeriodicCostBenefit.COLUMNNAME_DateTo, dateTo);
		
		return null;
	}
}
