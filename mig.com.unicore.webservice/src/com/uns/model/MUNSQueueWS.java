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
public class MUNSQueueWS extends X_UNS_Queue_WS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -156657464946648606L;

	/**
	 * @param ctx
	 * @param UNS_Queue_WS_ID
	 * @param trxName
	 */
	public MUNSQueueWS(Properties ctx, int UNS_Queue_WS_ID, String trxName) {
		super(ctx, UNS_Queue_WS_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQueueWS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static List<MUNSQueueWS> getOfTable(Properties ctx, int Table_ID, String trxName)
	{
		List<MUNSQueueWS> list = new Query(ctx, Table_Name, COLUMNNAME_Referenced_Table_ID + "=? AND "
									+ COLUMNNAME_Status + "<>?", trxName).setParameters(Table_ID, "S")
										.list();
		return list;
	}

}
