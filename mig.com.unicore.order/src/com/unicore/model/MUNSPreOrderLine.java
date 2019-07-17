/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Burhani Adam
 *
 */
public class MUNSPreOrderLine extends X_UNS_PreOrder_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451565112722116825L;

	/**
	 * @param ctx
	 * @param UNS_PreOrder_Line_ID
	 * @param trxName
	 */
	public MUNSPreOrderLine(Properties ctx, int UNS_PreOrder_Line_ID,
			String trxName) {
		super(ctx, UNS_PreOrder_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPreOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			String sql = "SELECT COALESCE(MAX(LineNo),0) + 10 FROM UNS_PreOrder_Line WHERE UNS_PreOrder_ID=? AND UNS_PreOrder_Line_ID<>?";
			int line = DB.getSQLValue(get_TrxName(), sql, getUNS_PreOrder_ID(), get_ID());
			setLineNo(line);
		}
		
		return true;
	}
}
