/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Menjangan
 *
 */
public class MUNSEmpStationLine extends X_UNS_EmpStationLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8486866391095356436L;

	/**
	 * @param ctx
	 * @param UNS_EmpStationLine_ID
	 * @param trxName
	 */
	public MUNSEmpStationLine(Properties ctx, int UNS_EmpStationLine_ID,
			String trxName) {
		super(ctx, UNS_EmpStationLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSEmpStationLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSEmpStationLine getByParentEmp(Properties ctx, int Parent_ID, int Emp_ID, String trxName)
	{
		MUNSEmpStationLine line = null;
		
		line = new Query(ctx, Table_Name, COLUMNNAME_UNS_EmpStation_ID + "=? AND "
										  + COLUMNNAME_UNS_Employee_ID + "=?", 
										  trxName).setParameters(Parent_ID, Emp_ID).first();
		
		return line;
	}

	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if(newRecord && getValidFrom() != null)
		{
			if(dateNow().after(getValidFrom()) || dateNow().equals(getValidFrom()))
				throw new AdempiereException("Youd Should can create with valid from more than today");
		}
		
		StringBuilder sql = new StringBuilder("SELECT 1 FROM UNS_EmpStationLine WHERE UNS_EmpStationLine_ID <> ? ")
			.append("AND UNS_EmpStation_ID = ? AND UNS_Employee_ID = ?");
		boolean exists = DB.getSQLValue(
				get_TrxName(), sql.toString(), getUNS_EmpStationLine_ID(), getUNS_EmpStation_ID(), getUNS_Employee_ID()) > 0;
		
		if(exists)
		{
			StringBuilder sql2 = new StringBuilder(sql).append(" AND ValidFrom <= ? ");
			exists = DB.getSQLValue(
					get_TrxName(), sql2.toString(), getUNS_EmpStationLine_ID(), getUNS_EmpStation_ID(), getUNS_Employee_ID()
					, getValidFrom()) > 0;
			if(exists)
			{
				StringBuilder sql3 = new StringBuilder(sql).append(" AND ValidFrom = ?");	
				exists = DB.getSQLValue(
						get_TrxName(), sql3.toString(), getUNS_EmpStationLine_ID(), getUNS_EmpStation_ID(), getUNS_Employee_ID()
						, getValidFrom()) > 0;
				if(exists)
					{
					log.saveError("Cannot Save", "Duplicate slot");
					return false;
					}
			}
		}	
		
		if (getLine() == 0)
		{
			String sqql = "SELECT COALESCE(MAX(Line), 0) FROM UNS_EmpStationLine "
					+ " WHERE UNS_EmpStation_ID = ? ";
			int value = DB.getSQLValue(get_TrxName(), sqql, 
					getUNS_EmpStation_ID());
			value += 10;
			setLine(value);
		}
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean beforeDelete() {
		
		if(get_Value("ValidFrom") == null || get_Value("ValidTo") == null)
			return super.beforeDelete();
		
		if(dateNow().after(getValidFrom()) || dateNow().equals(getValidFrom()))
			throw new AdempiereException("You can only delete data which valid from more than today");
		
		MUNSEmpStationLine prevLine = null;
		MUNSEmpStationLine nextLine = null;
		
		//get previous Line
		String sql = "SELECT UNS_EmpStationLine_ID FROM UNS_EmpStationLine WHERE UNS_Employee_ID = ?"
				+ " AND ValidFrom < ? AND UNS_EmpStationLine_ID <> ? AND isActive = ?"
				+ " ORDER BY ValidFrom DESC";
		int id = DB.getSQLValue(
				get_TrxName(), sql, getUNS_Employee_ID(), getValidFrom(), getUNS_EmpStationLine_ID(), "Y");
		if(id > 0)
		{
			prevLine = new MUNSEmpStationLine(getCtx(), id, get_TrxName());
		}
		
		//getAfter Line
		if(getValidTo() != null)
		{
			sql = "SELECT UNS_EmpStationLine_ID FROM UNS_EmpStationLine WHERE UNS_Employee_ID = ?"
					+ " AND ValidFrom > ? AND UNS_EmpStationLine_ID <> ? AND isActive = ?"
					+ " ORDER BY ValidFrom ASC";
			id = DB.getSQLValue(
					get_TrxName(), sql, getUNS_Employee_ID(), getValidTo() , getUNS_EmpStationLine_ID(), "Y");
			if(id > 0)
			{
				nextLine = new MUNSEmpStationLine(getCtx(), id, get_TrxName());
			}
		}
		
		Timestamp validTo = null;
		if(prevLine != null && nextLine != null)
		{
			if(prevLine.getUNS_EmpStation_ID() == nextLine.getUNS_EmpStation_ID())
			{
				validTo = nextLine.getValidTo();
				String sqql = "DELETE FROM UNS_EmpStationLine WHERE UNS_EmpStationLine_ID = ?";
				boolean ok = DB.executeUpdate(sqql, nextLine.getUNS_EmpStationLine_ID(), get_TrxName()) != -1;
				if(!ok)
					throw new AdempiereException("Error when try to delete line");
			}
			else
			{
				validTo = getValidTo();
			}
			
			prevLine.setValidTo(validTo);
			prevLine.saveEx();
		}
		else if(prevLine != null)
		{
			prevLine.setValidTo(getValidTo());
			prevLine.saveEx();
		}
		else if(nextLine != null)
		{
			nextLine.setValidFrom(getValidFrom());
			nextLine.saveEx();
		}
		
		return super.beforeDelete();
	}
	
	public Timestamp dateNow() {
		
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		return Timestamp.valueOf(dateStr+" 00:00:00");
	}
}
