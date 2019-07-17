/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSWarehouseConfig extends X_UNS_WarehouseConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2089904751771048415L;

	/**
	 * @param ctx
	 * @param UNS_WarehouseConfig_ID
	 * @param trxName
	 */
	public MUNSWarehouseConfig(Properties ctx, int UNS_WarehouseConfig_ID,
			String trxName) {
		super(ctx, UNS_WarehouseConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWarehouseConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public List<MUNSWarehouseConfigLine> getLines()
	{		
		List<MUNSWarehouseConfigLine> line = new Query(getCtx(), MUNSWarehouseConfigLine.Table_Name,
				COLUMNNAME_M_Warehouse_ID + "=?", get_TrxName()).setParameters(getM_Warehouse_ID()).list();
		
		return line;
	}
}
