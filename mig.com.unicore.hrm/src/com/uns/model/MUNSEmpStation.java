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
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Menjangan
 *
 */
public class MUNSEmpStation extends X_UNS_EmpStation {
	
	private MUNSEmpStationLine[] m_lines = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -125339640447973214L;

	/**
	 * @param ctx
	 * @param UNS_EmpStation_ID
	 * @param trxName
	 */
	public MUNSEmpStation(Properties ctx, int UNS_EmpStation_ID, String trxName) 
	{
		super(ctx, UNS_EmpStation_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSEmpStation(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	public MUNSEmpStationLine[] getLines (boolean requery)
	{
		if (null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		String wc = Table_Name + "_ID = ?";
		
		List<MUNSEmpStationLine> list = Query.get(getCtx(), 
				UNSHRMModelFactory.EXTENSION_ID, MUNSEmpStationLine.Table_Name, 
				wc, get_TrxName()).setParameters(get_ID()).list();
		
		m_lines = new MUNSEmpStationLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public MUNSEmpStationLine[] getLines ()
	{
		return getLines(false);
	}
	
	public static int getEmployeeSlotType_ID (
			String trxName, int UNS_Employee_ID)
	{
		StringBuilder sb = new StringBuilder("SELECT ")
		.append(COLUMNNAME_UNS_SlotType_ID).append(" FROM ")
		.append(Table_Name).append(" WHERE ").append(Table_Name)
		.append("_ID IN (SELECT ").append(Table_Name).append("_ID FROM ")
		.append(MUNSEmpStationLine.Table_Name)
		.append(" WHERE ").append(MUNSEmpStationLine.COLUMNNAME_UNS_Employee_ID)
		.append(" = ? AND IsActive = ?) AND IsActive = ?");
		String sql = sb.toString();
		int slotType = DB.getSQLValue(trxName, sql, UNS_Employee_ID, "Y","Y");
		return slotType;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSEmpStation get (
			Properties ctx, int UNS_Employee_ID, String trxName)
	{
		Query query = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				Table_Name + "." + COLUMNNAME_IsActive + " = ? AND es." 
				+ MUNSEmpStationLine.COLUMNNAME_UNS_Employee_ID + " = ? "
				+ " AND es." + COLUMNNAME_IsActive + " = ?" ,
				trxName);
		
		query.addJoinClause("INNER JOIN " + MUNSEmpStationLine.Table_Name
		+ " es ON es.UNS_EmpStation_ID = UNS_EmpStation.UNS_EmpStation_ID ");
		query.setParameters("Y", UNS_Employee_ID, "Y");
		MUNSEmpStation station = query.first();
		
		return station;
	}
	
	/**
	 * 
	 * @param trxName
	 * @param employeeID
	 * @param date
	 * @return int ID of Employee Station
	 */
	public static int getIDByDate (String trxName, int employeeID, Timestamp date)
	{
		String sql = "SELECT UNS_EmpStation_ID FROM UNS_EmpStationLine WHERE "
				+ " IsActive = ? AND UNS_Employee_ID = ? AND ValidFrom <= ? "
				+ " AND (ValidTo IS NULL OR ValidTo >= ?) ORDER BY ValidFrom";
		return DB.getSQLValue(trxName, sql, "Y", employeeID, date, date);
	}
	
	public static MUNSEmpStation getbyDate(
			Properties ctx, int UNS_Employee_ID, Timestamp date, String trxName)
	{
		
		String whereClause = " UNS_EmpStation.isActive = ? AND sl.UNS_Employee_ID = ?"
				+ " AND sl.ValidFrom <= ?";
		Query query = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName);
		query.addJoinClause(" INNER JOIN UNS_EmpStationLine sl ON sl.UNS_EmpStation_ID = UNS_EmpStation.UNS_EmpStation_ID ");
		query.setOrderBy(" sl.ValidFrom DESC");
		query.setParameters("Y", UNS_Employee_ID ,date);
		
		MUNSEmpStation stations = query.first();
		if(stations != null)
		{
			String sqql = "SELECT 1 FROM UNS_EmpStationLine WHERE UNS_EmpStation_ID = ?"
					+ " AND UNS_Employee_ID = ? AND ValidTo < ?";
			boolean exists = DB.getSQLValue(trxName, sqql, stations.getUNS_EmpStation_ID(), UNS_Employee_ID , date) > 0;
			if(exists)
				return null;
		}
		
		return stations;
	}
}
