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
public class UNSFinanceDocumentFactory extends UNSAbstractDocumentFactory {
	
	private static final String EXTENSION_HANDLER = "UNSFinanceDocumentFactory";
	
	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName) 
	{
			return super.getDocument(as, AD_Table_ID, rs, trxName);
	}

	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		Class<?> clazz = Class.forName(className);
		return clazz;
	}
	
	public static String getExtensionID()
	{
		String extensionID = EXTENSION_HANDLER;
		return extensionID;
	}
}
