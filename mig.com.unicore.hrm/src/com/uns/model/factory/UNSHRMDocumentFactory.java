/**
 * 
 */
package com.uns.model.factory;

import java.sql.ResultSet;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;

import com.uns.base.UNSAbstractDocumentFactory;

/**
 * @author menjangan
 *
 */
public class UNSHRMDocumentFactory extends UNSAbstractDocumentFactory {

	public static final String EXTENSION_ID = "UNSHRMDocumentFactory";
	/**
	 * 
	 */
	public UNSHRMDocumentFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName) 
	{
			return super.getDocument(as, AD_Table_ID, rs, trxName);
	}
	
	

	/* (non-Javadoc)
	 * @see com.uns.base.UNSAbstractDocumentFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		return clazz;
	}

}
