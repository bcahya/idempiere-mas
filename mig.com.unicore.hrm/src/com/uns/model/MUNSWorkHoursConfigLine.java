/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Burhani Adam
 *
 */
public class MUNSWorkHoursConfigLine extends X_UNS_WorkHoursConfig_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8085711270793752372L;

	/**
	 * @param ctx
	 * @param UNS_WorkHoursConfig_Line_ID
	 * @param trxName
	 */
	public MUNSWorkHoursConfigLine(Properties ctx,
			int UNS_WorkHoursConfig_Line_ID, String trxName) {
		super(ctx, UNS_WorkHoursConfig_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWorkHoursConfigLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(getUNS_Resource_ID() > 0)
			setIsSummary(true);
		else
			setIsSummary(false);
		if(newRecord || is_ValueChanged(COLUMNNAME_UNS_Resource_ID) || is_ValueChanged(COLUMNNAME_UNS_Employee_ID))
		{
			if(isDuplicateRecord())
			{
				log.saveError("Error", "Duplicated record Resource / Employee one configuration.");
				return false;
			}
			if(isDuplicateWithOtherConfiguration())
			{
				log.saveError("Error", "Duplicated record with other configuration for this Resource / Employee.");
				return false;
			}
		}
		
		if(newRecord && getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0) + 10 FROM UNS_WorkHoursConfig_Line"
					+ " WHERE UNS_WorkHoursConfig_ID = ? AND UNS_WorkHoursConfig_Line_ID <> ?";
			int line = DB.getSQLValue(get_TrxName(), sql, getUNS_WorkHoursConfig_ID(), get_ID());
			if(line <= 0)
				setLine(line + 10);
			else
				setLine(line);
		}
		
		return true;
	}
	
	public static MUNSWorkHoursConfigLine getByWorkerGroup(Properties ctx, int Parent_ID, 
															int UNS_Resource_ID, String trxName)
	{
		MUNSWorkHoursConfigLine line = null;
		
		line = new Query(ctx, Table_Name, COLUMNNAME_UNS_Resource_ID + "=? AND " 
								+ COLUMNNAME_UNS_WorkHoursConfig_ID + "=?", trxName)
										.setParameters(UNS_Resource_ID, Parent_ID).first();
		
		return line;			
	}
	
	public boolean isDuplicateRecord()
	{
		String sql = "";
		int idCriteria = 0;
		if(getUNS_Resource_ID() > 0)
		{
			sql = "SELECT COUNT(whc.*) FROM UNS_WorkHoursConfig_Line whc"
					+ " WHERE (CASE WHEN whc.UNS_Resource_ID > 0 THEN whc.UNS_Resource_ID = ?"
					+ " ELSE whc.UNS_Employee_ID IN (SELECT rw.Labor_ID FROM UNS_Resource_WorkerLine rw"
					+ " WHERE rw.UNS_Resource_ID = ?) END)"
					+ " AND whc.UNS_WorkHoursConfig_Line_ID <> ?"
					+ " AND whc.UNS_WorkHoursConfig_ID = ?";
			idCriteria = getUNS_Resource_ID();
		}
		else if(getUNS_Employee_ID() > 0)
		{
			sql = "SELECT COUNT(whc.*) FROM UNS_WorkHoursConfig_Line whc"
					+ " WHERE (CASE WHEN whc.UNS_Employee_ID > 0 THEN whc.UNS_Employee_ID = ?"
					+ " ELSE whc.UNS_Resource_ID = (SELECT rw.Labor_ID FROM UNS_Resource_WorkerLine rw"
					+ " WHERE rw.UNS_Resource_ID = whc.UNS_Resource_ID) END)"
					+ " AND whc.UNS_WorkHoursConfig_Line_ID <> ?"
					+ " AND whc.UNS_WorkHoursConfig_ID = ?";
			idCriteria = getUNS_Employee_ID();
		}
		else
			return false;
		
		return DB.getSQLValue(get_TrxName(), sql, idCriteria, idCriteria, get_ID(), 
				getUNS_WorkHoursConfig_ID()) > 0 ? true : false;
	}
	
	public boolean isDuplicateWithOtherConfiguration()
	{
		String sql = "";
		int idCriteria = 0;
		if(getUNS_Resource_ID() > 0)
		{
			sql = "SELECT COUNT(cl.*) FROM UNS_WorkHoursConfig_Line cl"
					+ " INNER JOIN UNS_WorkHoursConfig c ON c.UNS_WorkHoursConfig_ID = cl.UNS_WorkHoursConfig_ID"
					+ " WHERE (CASE WHEN cl.UNS_Resource_ID > 0 THEN cl.UNS_Resource_ID = ?"
					+ " ELSE UNS_Employee_ID IN (SELECT rw.Labor_ID FROM UNS_Resource_WorkerLine rw"
					+ " WHERE rw.UNS_Resource_ID = ?) END)"
					+ " AND cl.UNS_WorkHoursConfig_Line_ID <> ?"
					+ " AND (? BETWEEN c.ValidFrom AND c.ValidTo OR ? BETWEEN c.ValidFrom AND c.ValidTo)"
					+ " AND c.DocStatus NOT IN ('VO', 'RE')"
					+ " AND c.UNS_WorkHoursConfig_ID <> ?";
			idCriteria = getUNS_Resource_ID(); 
		}
		else if(getUNS_Employee_ID() > 0)
		{
			sql = "SELECT COUNT(cl.*) FROM UNS_WorkHoursConfig_Line cl"
					+ " INNER JOIN UNS_WorkHoursConfig c ON c.UNS_WorkHoursConfig_ID = cl.UNS_WorkHoursConfig_ID"
					+ " WHERE (CASE WHEN cl.UNS_Employee_ID > 0 THEN cl.UNS_Employee_ID = ?"
					+ " ELSE UNS_Resource_ID = (SELECT rw.UNS_Resource_ID FROM UNS_Resource_WorkerLine rw"
					+ " WHERE rw.Labor_ID = ?) END)"
					+ " AND cl.UNS_WorkHoursConfig_Line_ID <> ?"
					+ " AND (? BETWEEN c.ValidFrom AND c.ValidTo OR ? BETWEEN c.ValidFrom AND c.ValidTo)"
					+ " AND c.DocStatus NOT IN ('VO', 'RE')"
					+ " AND c.UNS_WorkHoursConfig_ID <> ?";
			idCriteria = getUNS_Employee_ID();
		}
		else
			return false;
		
		return DB.getSQLValue(get_TrxName(), sql, idCriteria, idCriteria, get_ID(), 
				getUNS_WorkHoursConfig().getValidFrom(), getUNS_WorkHoursConfig().getValidTo(),
					getUNS_WorkHoursConfig_ID()) > 0 ? true : false;
	}
	
	public static Timestamp getStartTimeEmployee(Properties ctx, int Employee_ID, 
													Timestamp date, String trxName)
	{
		String sql = "SELECT UNS_Resource_ID FROM UNS_Resource_WorkerLine"
				+ " WHERE Labor_ID = ? AND IsActive = 'Y'";
		int Resource_ID = DB.getSQLValue(trxName, sql, Employee_ID);
		
		if(Resource_ID > 0)
		{
			MUNSWorkHoursConfig config = MUNSWorkHoursConfig.getByDate(ctx, date, 0, trxName);
			if(config != null)
				return config.getStartTime();
		}
		
		return null;
	}
	
	public static Timestamp getEndTimeEmployee(Properties ctx, int Employee_ID, 
			Timestamp date, String trxName)
	{
		String sql = "SELECT UNS_Resource_ID FROM UNS_Resource_WorkerLine"
				+ " WHERE Labor_ID = ? AND IsActive = 'Y'";
		int Resource_ID = DB.getSQLValue(trxName, sql, Employee_ID);
		
		if(Resource_ID > 0)
		{
			MUNSWorkHoursConfig config = MUNSWorkHoursConfig.getByDate(ctx, date, 0, trxName);
			if(config != null)
				return config.getEndTime();
		}
		
		return null;
	}
}
