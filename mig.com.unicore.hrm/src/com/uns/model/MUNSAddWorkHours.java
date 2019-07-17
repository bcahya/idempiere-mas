/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.DB;

/**
 * @author MUHAMIN
 *
 */

public class MUNSAddWorkHours extends X_UNS_AddWorkHours {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5108935537787251843L;
	/**
	 * @param ctx
	 * @param UNS_AddWorkHours_ID
	 * @param trxName
	 */	
	
	public MUNSAddWorkHours(Properties ctx, int UNS_AddWorkHours_ID,
			String trxName) 
	{
		super(ctx, UNS_AddWorkHours_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAddWorkHours(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public boolean beforeSave (boolean newRecord)
	{
		if(getAddWorkHours() < 1 || getEarlierAddWorkHours() < 1)
		{
			log.saveError("Save Error", "Can not be negative or zero");
			return false;
		}
		if (getValidFrom().after(getValidTo()))
		{
			log.saveError("Save Error","Valid to must be greather than valid from");
			return false;
		}
		if(!IsValidDate())
		{
			log.saveError("Save Error", "ValidFrom or ValidTo date can not be inserted with "
					+ "the previous document number");
			return false;
		}	
		return true;
	}
	
	public boolean IsValidDate()
	{
		String sql = "SELECT DocumentNo FROM UNS_AddWorkHours WHERE UNS_EmpStation_ID = ? AND"
				+ " (((? >= ValidFrom AND ? <= ValidTo) OR (? >= ValidFrom AND ?"
				+ " <= ValidTo)) OR ((ValidFrom >= ? AND ValidFrom <= ?) OR"
				+ " (ValidTo >= ? AND ValidTo <= ? ))) AND UNS_AddWorkHours_ID <> ?"
				+ " AND DocStatus NOT IN ('CO','CL')";
		boolean ambil = DB.getSQLValue(get_TrxName(), sql, getUNS_EmpStation_ID(), getValidFrom(), getValidFrom(), getValidTo(), getValidTo()
				, getValidFrom(), getValidTo(), getValidFrom(), getValidTo(), getUNS_AddWorkHours_ID()) > 0;
				
		return !ambil ;
	}
	
	public static int getAddWorkHours(String trxName, int employeeID, Timestamp date)
	{
		int empStationID = MUNSEmpStation.getIDByDate(trxName, employeeID, date);
		String sql = "SELECT AddWorkHours FROM UNS_AddWorkHours WHERE UNS_EmpStation_ID = ?"
				+ " AND IsActive = ? AND ? BETWEEN ValidFrom AND ValidTo";
		int addWorkHours = DB.getSQLValue(trxName, sql, empStationID, "Y", date);
		if (addWorkHours < 0)
			addWorkHours = 0;
		return addWorkHours;
	}
	
	public static int getEarlierAddWorkHours(String trxName, int employeeID, Timestamp date)
	{
		int empStationID = MUNSEmpStation.getIDByDate(trxName, employeeID, date);
		String sql = "SELECT EarlierAddWorkHours FROM UNS_AddWorkHours WHERE UNS_EmpStation_ID = ?"
				+ " AND IsActive = ? AND ? BETWEEN ValidFrom AND ValidTo";
		int addWorkHours = DB.getSQLValue(trxName, sql, empStationID, "Y", date);
		if (addWorkHours < 0)
			addWorkHours = 0;
		return addWorkHours;
	}
}