/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;

import com.uns.base.UNSAbstractDocumentFactory;
import com.uns.model.acct.Doc_UNSChargeRS;

import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSShipping;
import com.unicore.model.acct.Doc_UNSBonusClaim;

/**
 * @author user
 *
 */
public class UNSOrderDocumentFactory extends UNSAbstractDocumentFactory
{

	/**
	 * 
	 */
	public UNSOrderDocumentFactory()
	{
		// TODO Auto-generated constructor stub
	}


	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName)
	{
		Doc doc = null;
		if(MUNSBonusClaim.Table_ID == AD_Table_ID)
			doc = new Doc_UNSBonusClaim(as, rs, trxName);
		else if(MUNSShipping.Table_ID == AD_Table_ID)
			doc = new Doc_UNSChargeRS(as, MUNSShipping.class, rs, null, trxName);
		else
			doc = super.getDocument(as, AD_Table_ID, rs, trxName);
		
		return doc;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.base.UNSAbstractDocumentFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className) throws ClassNotFoundException
	{
		Class<?> clazz = Class.forName(className);
		return clazz;
	}

}
