/**
 * 
 */
package com.uns.base;

import java.sql.ResultSet;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;

import com.uns.model.MUNSChargeRS;
import com.uns.model.acct.Doc_UNSChargeRS;

/**
 * @author root
 *
 */
public class UNSDefaultDocumentFactory extends UNSAbstractDocumentFactory {

	/**
	 * 
	 */
	public UNSDefaultDocumentFactory() {
		super();
	}
	
	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName)
	{
		if(AD_Table_ID == MUNSChargeRS.Table_ID)
			return new Doc_UNSChargeRS(as, MUNSChargeRS.class, rs, null, trxName);
		else 
			return null;
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
