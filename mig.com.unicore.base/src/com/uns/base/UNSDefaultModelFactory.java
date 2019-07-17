/**
 * 
 */
package com.uns.base;

import java.sql.ResultSet;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.util.MUNSAppsContext;
import com.uns.util.MUNSAppsReff;

/**
 * @author AzHaidar
 *
 */
public class UNSDefaultModelFactory extends UNSAbstractModelFactory 
{

	public static final String EXTENSION_ID = "UNSDefaultModelFactory";
	
	/**
	 * 
	 */
	public UNSDefaultModelFactory() {
		super();
	}
	
	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) 
	{
		PO po = null;
		
		if (MUNSAppsReff.Table_Name.equals(tableName)) {
			po = new MUNSAppsReff(Env.getCtx(), Record_ID, trxName);
		} 
		else if (MUNSAppsContext.Table_Name.equals(tableName)) {
			po = new MUNSAppsContext(Env.getCtx(), Record_ID, trxName);
		} 
		else {
			po = super.getPO(tableName, Record_ID, trxName);
		}
		
		return po;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) 
	{
		PO po = null;

		if (MUNSAppsReff.Table_Name.equals(tableName)) {
			po = new MUNSAppsReff(Env.getCtx(), rs, trxName);
		} 
		else if (MUNSAppsContext.Table_Name.equals(tableName)) {
			po = new MUNSAppsContext(Env.getCtx(), rs, trxName);
		} 
		else {
			po = super.getPO(tableName, rs, trxName);
		}
		
		return po;
	}

	@Override
	protected Class<?> getInternalClass(String className) throws ClassNotFoundException
	{
		Class<?> clazz = Class.forName(className);
		return clazz;
	}
}
