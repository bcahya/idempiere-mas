/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;

import com.uns.base.UNSAbstractModelFactory;

/**
 * @author menjangan
 *
 */
public class UNSFinanceModelFactory extends UNSAbstractModelFactory {
	
	public static final String EXTENSION_ID = "UNSFinancialModelFactory";

	/**
	 * 
	 */
	public UNSFinanceModelFactory() {
		super();
	}
	
	@Override
	public PO getPO(String tableName, int id, String trxName)
	{
		PO po;
		//define your custom
		po = super.getPO(tableName, id, trxName);
		return po;
	}
	
	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName)
	{
		PO po;
		//define your custom
		po = super.getPO(tableName, rs, trxName);
		return po;
	}

	/* (non-Javadoc)
	 * @see com.uns.base.UNSAbstractModelFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		return clazz;
	}

}
