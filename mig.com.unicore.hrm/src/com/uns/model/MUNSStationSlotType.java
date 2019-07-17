package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

public class MUNSStationSlotType extends X_UNS_StationSlotType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9195458083098975167L;

	public MUNSStationSlotType(Properties ctx, int UNS_StationSlotType_ID,
			String trxName) {
		super(ctx, UNS_StationSlotType_ID, trxName);
	}

	public MUNSStationSlotType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public static int getSlotTypeID (String trxName, int UNS_Employee_ID, Timestamp date)
	{
		int empStationID = MUNSEmpStation.getIDByDate(trxName, UNS_Employee_ID, date);
		if (empStationID <= 0)
			return -1;
		int slotTypeID = DB.getSQLValue(trxName, "SELECT UNS_SlotType_ID FROM UNS_StationSlotType "
				+ " WHERE UNS_EmpStation_ID = ? AND ValidFrom <= ? AND "
				+ " (ValidTo Is NULL OR ValidTo >= ?) ORDER BY ValidFrom", empStationID, date, date);
		return slotTypeID;
	}
	
	public static MUNSStationSlotType getSlotTypeByDate(
			Properties ctx, int UNS_Employee_ID , Timestamp date, String trxName) {
		
		MUNSEmpStation empStation = MUNSEmpStation.getbyDate(ctx, UNS_Employee_ID, date, trxName);
		
		Query query = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				" UNS_EmpStation_ID = ? AND ValidFrom <= ? AND isActive = ?", trxName);
		query.setOrderBy(" ValidFrom DESC");
		query.setParameters(empStation.getUNS_EmpStation_ID(), date, 'Y');
		
		MUNSStationSlotType slot = query.first();
		String sqql = "SELECT 1 FROM UNS_StationSlotType WHERE UNS_EmpStation_ID = ?"
				+ " AND UNS_SlotType_ID = ? AND ValidTo <= ?";
		boolean exists = DB.getSQLValue(
				trxName, sqql, slot.getUNS_EmpStation_ID(), slot.getUNS_SlotType_ID(), date) > 0;
		if(exists)
			return null;
		
		return slot; 
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord)
		{
			if(dateNow().after(getValidFrom()) || dateNow().equals(getValidFrom()))
				throw new AdempiereException("Youd Should can create with valid from more than today");
		}
		
		StringBuilder sql = new StringBuilder("SELECT 1 FROM UNS_StationSlotType WHERE UNS_StationSlotType_ID <> ? ")
				.append("AND UNS_EmpStation_ID = ? ");
		boolean exists = DB.getSQLValue(get_TrxName(), sql.toString(), getUNS_StationSlotType_ID(), getUNS_EmpStation_ID()) > 0;
		if(exists)
		{
			StringBuilder sql2 = new StringBuilder(sql).append(" AND ValidFrom <= ? ");
			exists = DB.getSQLValue(get_TrxName(), sql2.toString(), getUNS_StationSlotType_ID(), getUNS_EmpStation_ID()
					, getValidFrom()) > 0;
			if(exists)
			{
				StringBuilder sql3 = new StringBuilder(sql).append(" AND ValidFrom = ?");	
				exists = DB.getSQLValue(get_TrxName(), sql3.toString(), getUNS_StationSlotType_ID(), getUNS_EmpStation_ID()
						, getValidFrom()) > 0;
				if(exists)
				{
					log.saveError("Cannot Save", "Duplicate slot");
					return false;
				}
			}
		}
		
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean beforeDelete() {
		
		if(dateNow().after(getValidFrom()) || dateNow().equals(getValidFrom()))
			throw new AdempiereException("You can only delete data which valid from more than today");
		
		MUNSStationSlotType prevSlot = null;
		MUNSStationSlotType nextSlot = null;
		
		//get Previous slot
		String sql = "SELECT UNS_StationSlotType_ID FROM UNS_StationSlotType WHERE UNS_EmpStation_ID = ?"
				+ " AND ValidFrom < ? AND UNS_StationSlotType_ID <> ? AND isActive = ?"
				+ " ORDER BY ValidFrom DESC";
		int id = DB.getSQLValue(
				get_TrxName(), sql, getUNS_EmpStation_ID(), getValidFrom(), getUNS_StationSlotType_ID(), "Y");
		if(id > 0)
		{
			prevSlot = new MUNSStationSlotType(getCtx(), id, get_TrxName());
		}
		
		//getAfter Line
		if(getValidTo() != null)
		{
			sql = "SELECT UNS_StationSlotType_ID FROM UNS_StationSlotType WHERE UNS_EmpStation_ID = ?"
					+ " AND ValidFrom > ? AND UNS_StationSlotType_ID <> ? AND isActive = ?"
					+ " ORDER BY ValidFrom ASC";
			id = DB.getSQLValue(
					get_TrxName(), sql, getUNS_EmpStation_ID(), getValidTo() , getUNS_StationSlotType_ID(), "Y");
			if(id > 0)
			{
				nextSlot = new MUNSStationSlotType(getCtx(), id, get_TrxName());
			}
		}
		
		Timestamp validTo = null;
		if(prevSlot != null && nextSlot != null)
		{
			if(prevSlot.getUNS_SlotType_ID() == nextSlot.getUNS_SlotType_ID())
			{
				validTo = nextSlot.getValidTo();
				String sqql = "DELETE FROM UNS_StationSlotType WHERE UNS_StationSlotType_ID = ?";
				boolean ok = DB.executeUpdate(sqql, nextSlot.getUNS_StationSlotType_ID(), get_TrxName()) != -1;
				if(!ok)
					throw new AdempiereException("Error when try to delete nextline");
			}
			else
			{
				validTo = getValidTo();
			}
			
			prevSlot.setValidTo(validTo);
			prevSlot.saveEx();
		}
		else if(prevSlot != null)
		{
			prevSlot.setValidTo(getValidTo());
			prevSlot.saveEx();
		}
		else if(nextSlot != null)
		{
			nextSlot.setValidFrom(getValidFrom());
			nextSlot.saveEx();
		}
		
		return super.beforeDelete();
	}
	
	public Timestamp dateNow() {
		
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		return Timestamp.valueOf(dateStr+" 00:00:00");
	}
}
