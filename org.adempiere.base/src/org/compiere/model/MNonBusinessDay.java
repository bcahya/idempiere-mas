package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

public class MNonBusinessDay extends X_C_NonBusinessDay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5062263244896650725L;

	public MNonBusinessDay(Properties ctx, int C_NonBusinessDay_ID,
			String trxName) {
		super(ctx, C_NonBusinessDay_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MNonBusinessDay(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param ctx
	 * @param date
	 * @param trxName
	 * @return true if the date set as one of non business day
	 */
	public static boolean isNonBusinessDay(Properties ctx, Timestamp date, int AD_Org_ID, String trxName)
	{
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		String sql = "SELECT C_NonBusinessDay_ID FROM C_NonBusinessDay WHERE Date1 = ?"
				+ " AND (AD_Org_ID = ? OR AD_Org_ID = 0)";
		
		int record = DB.getSQLValue(trxName, sql, date, AD_Org_ID);
//		I_C_NonBusinessDay nbd = (
//				I_C_NonBusinessDay)MTable.get(ctx, I_C_NonBusinessDay.Table_Name).getPO(
//						I_C_NonBusinessDay.COLUMNNAME_Date1+"=?", new Object[]{date}, trxName);
//		return (null != nbd);
		return record > 0;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		int count = 0;
		String sql = "SELECT COUNT(*) FROM C_NonBusinessDay WHERE Date1 = ?"
				+ " AND C_NonBusinessDay_ID <> ?";
		
		if(getAD_Org_ID() > 0)
		{
			sql += " AND (AD_Org_ID = ? OR AD_Org_ID = 0)";
			count = DB.getSQLValue(get_TrxName(), sql, getDate1(), get_ID(), getAD_Org_ID());
		}
		else
			count = DB.getSQLValue(get_TrxName(), sql, getDate1(), get_ID());
		
		if(count > 0)
		{
			log.saveError("Error", "Duplicate record with same date");
			return false;
		}
	
		return true;
	}
}
