/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;

/**
 * @author ALBURHANY
 *
 */
public class MUNSOrgWarehouseLine extends X_UNS_OrgWarehouse_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6763582789807896648L;

	/**
	 * @param ctx
	 * @param UNS_OrgWarehouse_Line_ID
	 * @param trxName
	 */
	public MUNSOrgWarehouseLine(Properties ctx, int UNS_OrgWarehouse_Line_ID,
			String trxName) {
		super(ctx, UNS_OrgWarehouse_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOrgWarehouseLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord)
	{
		StringBuffer sql = new StringBuffer
				("SELECT M_Warehouse_ID FROM UNS_OrgWarehouse_Line"
						+ " WHERE M_Warehouse_ID = " + getM_Warehouse_ID());
		int idWarehouse = DB.getSQLValue(get_TrxName(), sql.toString());
		
		MWarehouse warehouse = new MWarehouse(getCtx(), idWarehouse, get_TrxName());
		
		if(idWarehouse >= 1)
		{
			throw new AdempiereException("Duplicate for Warehouse \"" + warehouse.getName() + "\"");
		}
		return true;
	}
}
