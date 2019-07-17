/**
 * 
 */
package com.uns.importer;

import java.util.Properties;

import org.compiere.util.CLogger;
import org.compiere.util.DB;

import jxl.Sheet;

/**
 * @author AzHaidar
 *
 */
public abstract class AbstractDataImporter {

	protected Properties 	m_ctx = null;
	protected Sheet 		m_sheet = null;
	protected String 		m_trxName = null;
	
	protected CLogger			log = CLogger.getCLogger (getClass());

	/**
	 * Default constructor.
	 * 
	 * @param ctx
	 * @param sheet
	 * @param trxName
	 */
	public AbstractDataImporter(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx;
		this.m_sheet = sheet;
		this.m_trxName = trxName;
	}
	
	/**
	 * Just do the import processes.
	 * 
	 * @param m_sheet
	 * 
	 * @return true if succeed, false otherwise.
	 */
	public abstract boolean doImport();
	
	/**
	 * 
	 * @param trxName
	 * @param tableName
	 * @param whereClause
	 * @return
	 */
	public static int getTableID(String trxName, String tableName, String whereClause)
	{
		return DB.getSQLValue(trxName, 
							  "SELECT " + tableName + "_ID FROM " + tableName + 
							  " WHERE " + whereClause);
	}

	public static int getTableID(String trxName, String tableName, String whereClause, Object... params)
	{
		return DB.getSQLValue(trxName, 
				"SELECT " + tableName + "_ID FROM " + tableName + " WHERE " + whereClause, 
				params);
	}
}
