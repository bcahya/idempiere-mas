package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MUNSShippingCrewIncentive extends X_UNS_ShippingCrewIncentive {

	/**
	 * 
	 */
	private static final long serialVersionUID = 468395139715595845L;

	public MUNSShippingCrewIncentive(Properties ctx,
			int UNS_ShippingCrewIncentive_ID, String trxName) {
		super(ctx, UNS_ShippingCrewIncentive_ID, trxName);
	}

	public MUNSShippingCrewIncentive(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}
	
	public static MUNSShippingCrewIncentive getOfEmployee(Properties ctx, int UNS_Employee_ID, int AD_Org_ID, String trxName)
	{
		MUNSShippingCrewIncentive ci = null;
		String whereClause = "UNS_Employee_ID = ? AND CreateSettlement='N' AND AD_Org_ID=?";
		ci = new org.compiere.model.Query(ctx, Table_Name, whereClause, trxName).
											setParameters(UNS_Employee_ID, AD_Org_ID).first();
		return ci;
	}
	
	public BigDecimal getAmount()
	{
		BigDecimal amount = Env.ZERO;
		
		String sql = "SELECT SUM(Amount) FROM UNS_ShippingCrewInctv_Line WHERE UNS_ShippingCrewIncentive_ID=?";
		amount = DB.getSQLValueBD(get_TrxName(), sql, get_ID());
		
		if(amount == null)
			amount = Env.ZERO;
		
		return amount; 
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(getAmountBonuses().signum() != 0)
		{
			setGrandTotal(getAmount().add(getAmountBonuses()));
		}
		else if(getAmountBonuses().signum() == 0 && is_ValueChanged(COLUMNNAME_AmountBonuses) && !newRecord)
		{
			setGrandTotal(getAmount());
		}
		
		return true;
	}
	
	protected boolean beforeDelete()
	{
		if(getUNS_Charge_RS_ID() > 0)
		{
			log.saveError("Error", "Charge RS has created.");
			return false;
		}
		else
		{
			String sql = "DELETE FROM UNS_ShippingCrewInctv_Line WHERE UNS_ShippingCrewIncentive_ID = ?";
			DB.executeUpdate(sql, get_ID(), get_TrxName());
		}
		
		return true;
	}
}
