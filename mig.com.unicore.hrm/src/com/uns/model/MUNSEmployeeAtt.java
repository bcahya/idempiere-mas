/**
 * 
 */
package com.uns.model;

import java.util.Properties;

import org.compiere.util.DB;

import com.uns.base.model.Query;

/**
 * @author MuhAmin
 *
 */
public class MUNSEmployeeAtt extends X_UNS_EmployeeAtt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4789011642296281839L;

	public MUNSEmployeeAtt(Properties ctx, int UNS_EmployeeAtt_ID,
			String trxName) {
		super(ctx, UNS_EmployeeAtt_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSEmployeeAtt getByEmployeeAtt (Properties ctx, String serialNo, int badgeNumber, String trxName)
	{
		MUNSRegisteredAtt regAtt = MUNSRegisteredAtt.getBySerialNo(ctx, serialNo, trxName);
	
		MUNSEmployeeAtt emp = new Query(ctx, MUNSEmployeeAtt.Table_Name, "bagdeNumber=? AND UNS_RegisteredAtt_ID=?", trxName)
							.setOnlyActiveRecords(true).setParameters(badgeNumber, regAtt.get_ID()).first();
		return emp;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (null == getValue())
		{
			setValue(getBadgeNumber() + "-" + getName());
		}
		if (getUNS_Employee_ID() > 0)
		{
			String sql = " SELECT AD_Org_ID FROM UNS_Employee WHERE "
					+ " UNS_Employee_ID = ? ";
			int org_ID = DB.getSQLValue(
					get_TrxName(), sql, getUNS_Employee_ID());
			setAD_Org_ID(org_ID);
		}
		
		return super.beforeSave(newRecord);
	}
}
