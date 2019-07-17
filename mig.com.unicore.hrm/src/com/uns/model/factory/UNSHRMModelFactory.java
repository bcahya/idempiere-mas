/**
 * 
 */
package com.uns.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;

import com.uns.base.UNSAbstractModelFactory;

/**
 * @author eko
 *
 */
public final class UNSHRMModelFactory extends UNSAbstractModelFactory {

	public static final String EXTENSION_ID = "UNSHRMModelFactory";
	/**
	 * 
	 */
	public UNSHRMModelFactory() {
		super();
	}
	
	public PO getPO(String tableName, int recordID, String trxName)
	{
		PO po = null;
		
		po = super.getPO(tableName, recordID, trxName);
		
		return po;
	}
	
	public PO getPO(String tableName, ResultSet rs, String trxName)
	{
		PO po = null;
		
		po = super.getPO(tableName, rs, trxName);
		
		return po;
	}
	 
	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException {
		
		Class<?> clazz = Class.forName(className);
		// TODO Auto-generated method stub
		return clazz;
	}

	public static String getExtensionID() {
		return EXTENSION_ID;
	}

}
