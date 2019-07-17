/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import com.uns.base.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSWarehouseConfigLine extends X_UNS_WarehouseConfigLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259075699345341996L;

	/**
	 * @param ctx
	 * @param UNS_WarehouseConfigLine_ID
	 * @param trxName
	 */
	public MUNSWarehouseConfigLine(Properties ctx,
			int UNS_WarehouseConfigLine_ID, String trxName) {
		super(ctx, UNS_WarehouseConfigLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWarehouseConfigLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSWarehouseConfigLine getByWhsDocType(Properties ctx, int M_Warehouse_ID, int C_DocType_ID, String trxName)
	{
		MUNSWarehouseConfigLine line = null;
		line = new Query(ctx, Table_Name, COLUMNNAME_M_Warehouse_ID + "=? AND "
				+ COLUMNNAME_C_DocType_ID + "=?", trxName).setParameters(M_Warehouse_ID, C_DocType_ID).first();
		
		return line;
	}
}
