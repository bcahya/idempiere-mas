/**
 * 
 */
package com.uns.base.model;

import java.util.Properties;

import org.compiere.model.MTable;

/**
 * @author menjangan
 *
 */
public class Query extends org.compiere.model.Query 
{
	
	/**
	 * 
	 * @param ctx
	 * @param table
	 * @param whereClause
	 * @param trxName
	 */
	public Query (Properties ctx, MTable table, String whereClause, String trxName)
	{
		super (ctx, table, whereClause, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param table
	 * @param whereClause
	 * @param trxName
	 */
	public Query (Properties ctx, String tableName, String whereClause, String trxName)
	{
		super (ctx, tableName, whereClause, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param extensionHandler
	 * @param tableName
	 * @param whereClause
	 * @param trxName
	 * @return
	 */
	public static Query get(Properties ctx, String extensionHandler, String tableName, String whereClause, String trxName)
	{
		MTable table = MTable.get(ctx, tableName);
		table.setExtensionHandler(extensionHandler);
		
		return new Query(ctx, table, whereClause, trxName);
	}
}