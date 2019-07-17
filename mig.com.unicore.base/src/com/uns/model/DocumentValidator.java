/**
 * 
 */
package com.uns.model;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * @author menjangan
 *
 */
public class DocumentValidator implements ModelValidator {
	
	private static CLogger log = CLogger.getCLogger(DocumentValidator.class);
	private int m_AD_Client_ID = -1;
	
	/**
	 * 
	 */
	public DocumentValidator() {
		super();
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if(client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else
		{
			log.info("Initializing global validator : " + this.toString());
		}
		
		MUNSDocumentValidator[] docs = MUNSDocumentValidator.getAll(Env.getCtx(), Trx.createTrxName());
		
		for(MUNSDocumentValidator doc : docs)
		{
			if(doc.getAD_Table_ID() <= 0)
				continue;
			else if (!doc.isActive())
				continue;
			
			String tableName = doc.getAD_Table().getTableName();
			engine.addModelChange(tableName, this);
			engine.addDocValidate(tableName, this);
		}
		
	}

	@Override
	public int getAD_Client_ID() {
		// TODO Auto-generated method stub
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " type: " +type);
		String msg = null;
		
		MUNSDocumentValidator[] docs = MUNSDocumentValidator.getAll(po.getCtx(),po.get_TrxName());
		for(MUNSDocumentValidator doc : docs)
		{
			if(doc.getAD_Table_ID() <= 0)
				continue;
			
			String tableName = doc.getAD_Table().getTableName();
			if(!po.get_TableName().equals(tableName))
				continue;

			if(type != doc.getTiming(doc.getOnCondition(), true))
				continue;
			
			doc.setValidationPO(po);
			
			if(doc.isAlwaysError())
				return doc.getErrorMsg();
			if(doc.isMandatoryAttachment() && po.getAttachment() == null)
				return doc.getErrorMsg();
			if(doc.isMandatoryDescription() && (null == po.get_Value("Description") || po.get_Value("Description").equals("")))
				return doc.getErrorMsg();
		}
		return msg;
	}

	@Override
	public String docValidate(PO po, int timing) {
		log.info(po.get_TableName() + " timing: "+timing);
		String msg = null;
	
		MUNSDocumentValidator[] docs = MUNSDocumentValidator.getAll(po.getCtx(),po.get_TrxName());

		for(MUNSDocumentValidator doc : docs)
		{
			if(doc.getAD_Table_ID() <= 0)
				continue;
			
			String tableName = doc.getAD_Table().getTableName();
			if(!po.get_TableName().equals(tableName))
				continue;
			if(timing != doc.getTiming(doc.getOnCondition(), false))
				continue;
				
			doc.setValidationPO(po);
			
			if(doc.isAlwaysError())
				return doc.getErrorMsg();
			if(doc.isMandatoryAttachment() && po.getAttachment() == null)
				return doc.getErrorMsg();
			if(doc.isMandatoryDescription() && (null == po.get_Value("Description") || po.get_Value("Description").equals("")))
				return doc.getErrorMsg();
		}
		
		return msg;
	}
}