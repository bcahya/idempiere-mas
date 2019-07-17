/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MMovementConfirm;

import com.uns.base.UNSAbstractDocumentFactory;

import com.unicore.base.doc.Doc_Invoice;
import com.unicore.base.doc.Doc_MovementConfirm;
import com.unicore.base.model.MInvoice;

/**
 * @author MENJANGAN
 *
 */
public class UniCoreMaterialManagementDocumentFactory extends
		UNSAbstractDocumentFactory {


	/**
	 * 
	 */
	public UniCoreMaterialManagementDocumentFactory() {
		super();
	}
	
	
	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName)
	{
		Doc doc = null;
		if(MInvoice.Table_ID == AD_Table_ID)
			doc = new Doc_Invoice(as, rs, trxName);
		else if(MMovementConfirm.Table_ID ==  AD_Table_ID)
			doc = new Doc_MovementConfirm(as, rs, trxName);
		else
			doc = super.getDocument(as, AD_Table_ID, rs, trxName);
		return doc;
	}

	/* (non-Javadoc)
	 * @see com.uns.base.UNSAbstractDocumentFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException 
	{
		Class<?> Clazz = Class.forName(className);
		return Clazz;
	}

}
