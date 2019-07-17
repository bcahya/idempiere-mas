/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import com.uns.util.ErrorMsg;

/**
 * @author menjangan
 *
 */
public class MUNSPayRollPremiTarget extends X_UNS_PayRollPremiTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1490628905839892586L;

	/**
	 * @param ctx
	 * @param UNS_PayRollPremiTarget_ID
	 * @param trxName
	 */
	public MUNSPayRollPremiTarget(Properties ctx, int UNS_PayRollPremiTarget_ID, String trxName) {
		super(ctx, UNS_PayRollPremiTarget_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayRollPremiTarget(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public void setName()
	{
		MUNSPayRollLine parent = getParent();
		String parentName = parent.getName();
		String name = "Target For " + parentName + " (" + getValue() + ")";
		setName(name);
	}
	
	public void setValue()
	{
		String value = getTargetMin() + "_" + getTargetMax();
		setValue(value);
	}
	
	@Override
	protected boolean beforeSave(boolean newRrecord)
	{
		if (getTargetMin().compareTo(getTargetMax()) > 0 )
			throw new AdempiereUserError(" Target Maximum must be greater than the Target Minimum");
		if (isMultipleTargetQuantity())
			return false;
		if(null == getValue())
			setValue();
		if(null == getName() || "".equals(getName()))
			setName();
		return true;
	}
	
	private boolean isMultipleTargetQuantity()
	{
		String sql = "SELECT * FROM " + Table_Name + " WHERE ((('" + getTargetMax() 
				+ "' BETWEEN " + COLUMNNAME_TargetMin + " AND " + COLUMNNAME_TargetMax 
				+ ") OR ('" + getTargetMin() + "' BETWEEN " + COLUMNNAME_TargetMin + " AND " 
				+ COLUMNNAME_TargetMax + ")) AND (" + COLUMNNAME_UNS_PayRollLine_ID + " = " 
				+ getUNS_PayRollLine_ID() 
				+ " AND (" + COLUMNNAME_AbsenType + " IS NULL OR "
				+ COLUMNNAME_AbsenType + " = '" + getAbsenType()
				+ "') AND " + COLUMNNAME_IsActive + " ='Y'" + " AND " 
				+ COLUMNNAME_UNS_PayRollPremiTarget_ID + " <> " + getUNS_PayRollPremiTarget_ID() + "))";
		
		int count = DB.getSQLValue(get_TrxName(), sql);
		if (count == -1)
			return false;
		
		ErrorMsg.setErrorMsg(
				getCtx(), "SaveError", "Multiple target Quantity" +
						", target min or target max has already exists");
		return true;
	}
	
	public MUNSPayRollLine getParent()
	{
		return (MUNSPayRollLine)getUNS_PayRollLine();
	}

}
