package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MPeriod;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

public class MUNSCanteenActivity extends X_UNS_CanteenActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7191468373511617858L;

	public MUNSCanteenActivity(Properties ctx, int UNS_CanteenActivity_ID,
			String trxName) {
		super(ctx, UNS_CanteenActivity_ID, trxName);
	}

	public MUNSCanteenActivity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	public static MUNSCanteenActivity[] getActivityByPeriod (
			int periodID, int employeeID, String trxName) {
		MUNSPayrollConfiguration conf = MUNSPayrollConfiguration.get(Env.getCtx(), MPeriod.get(Env.getCtx(), periodID), trxName);
		Timestamp from  = conf.getStartDate();
		Timestamp to = conf.getEndDate();
		String wc = "UNS_Employee_ID = ? AND DateTrx BETWEEN ? AND ?";
		List< MUNSCanteenActivity> list = Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, trxName).setParameters(employeeID, from, to).list();
		MUNSCanteenActivity[] acts = new MUNSCanteenActivity[list.size()];
		list.toArray(acts);
		return acts;
	}
	
	public static MUNSCanteenActivity[] getActivityBetween (int employeeID, Timestamp from, Timestamp to, String trxName) {
		String wc = "UNS_Employee_ID = ? AND DateTrx BETWEEN ? AND ?";
		List< MUNSCanteenActivity> list = Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, trxName).setParameters(employeeID, from, to).list();
		MUNSCanteenActivity[] acts = new MUNSCanteenActivity[list.size()];
		list.toArray(acts);
		return acts;
	}
	
	public MUNSCanteenActivity (MUNSMonthlyPresenceSummary monthly) {
		this (monthly.getCtx(), 0, monthly.get_TrxName());
		setClientOrg(monthly);
		setUNS_MonthlyPresenceSummary_ID(monthly.get_ID());
		setUNS_Employee_ID(monthly.getUNS_Employee_ID());
	}
	
	public static MUNSCanteenActivity get (int monthlyPresenceID, Timestamp date, String trxName) {
		return Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				"UNS_MonthlyPresenceSummary_ID = ? AND DateTrx = ?", trxName).setParameters(monthlyPresenceID, date).
				firstOnly();
	}
}
