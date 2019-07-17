/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;

import com.uns.base.UNSAbstractDocumentFactory;

/**
 * @author menjangan
 *
 */
public class UNSFinanceDocumentFactory extends UNSAbstractDocumentFactory {

	/**
	 * 
	 */
	public UNSFinanceDocumentFactory() {
		super();
	}
	
	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName) 
	{
		Doc doc;
		//custom your code here
		doc = super.getDocument(as, AD_Table_ID, rs, trxName);
		
		return doc;
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
