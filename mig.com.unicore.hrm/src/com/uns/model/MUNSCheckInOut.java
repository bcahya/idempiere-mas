/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;

import com.uns.base.model.Query;

/**
 * @author Menjangan
 *
 */
public class MUNSCheckInOut extends X_UNS_CheckInOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 316026972796094443L;
	private MUNSMonthlyPresenceVal m_presenceVal = null;

	/**
	 * @param ctx
	 * @param UNS_CheckInOut_ID
	 * @param trxName
	 */
	public MUNSCheckInOut(Properties ctx, int UNS_CheckInOut_ID, String trxName) 
	{
		super(ctx, UNS_CheckInOut_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckInOut(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param whereClause
	 * @param params
	 * @return
	 */
	public static MUNSCheckInOut[] gets (
			Properties ctx, String trxName, String whereClause, 
			List<Object> params, String orderBy)
	{
		Query query = new Query(ctx, Table_Name, whereClause, trxName);
		if (params != null && params.size() > 0)
		{
			query.setParameters(params);
		}
		if (orderBy != null)
		{
			query.setOrderBy(orderBy);
		}
		
		List<MUNSCheckInOut> list = query.list();
		MUNSCheckInOut[] inOuts = new MUNSCheckInOut[list.size()];
		list.toArray(inOuts);
		
		return inOuts;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (is_ValueChanged("UNS_DailyPresence_ID"))
		{
			Timestamp presenceDate = DB.getSQLValueTS(
					get_TrxName(), 
					"SELECT PresenceDate FROM UNS_DailyPresence "
					+ " WHERE UNS_DailyPresence_ID = ? ", 
					get_Value("UNS_DailyPresence_ID"));
			set_Value("PresenceDate", presenceDate);
		}
		
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{		
		return true;
	}
	
	public String toString(){
		return "NAME :" + "[" + getAttendanceName() + "]";
	}
	
	public MUNSDailyPresence getCreateDaily ()
	{
		if (getUNS_DailyPresence_ID() > 0)
		{
			return (MUNSDailyPresence) getUNS_DailyPresence();
		}
		
		MUNSDailyPresence day = new MUNSDailyPresence(
				(Timestamp)get_Value("PresenceDate"), 
				MUNSEmployee.getByAttName(get_TrxName(), getAttendanceName()));
		
		setUNS_DailyPresence_ID(day.get_ID());
		saveEx();
		
		return day;
	}
	
	public MUNSMonthlyPresenceVal getPresenceValidation ()
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