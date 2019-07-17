/**
 * 
 */
package com.unicore.model.validator;

import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMovementLine;
import org.compiere.model.MMovementLineConfirm;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRMALine;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.util.UNSCustomCalcConvertion;

import com.unicore.model.MUNSCvsRptProduct;
import com.unicore.model.MUNSPLReturnLine;
import com.unicore.model.MUNSPackingListLine;

/**
 * @author root
 *
 */
public class UNSUOMConvertionValidator implements ModelValidator {
	
	private int m_AD_Client_ID = 0;
	private CLogger log = CLogger.getCLogger(getClass());
	private final String[] m_list_Table_Name = {
		MOrderLine.Table_Name,
		MInvoiceLine.Table_Name,
		MInOutLine.Table_Name,
		MInventoryLine.Table_Name,
		MMovementLine.Table_Name,
		MRMALine.Table_Name,
		MUNSPackingListLine.Table_Name,
		MInOutLineConfirm.Table_Name,
		MMovementLineConfirm.Table_Name,
		MStorageOnHand.Table_Name,
		"UNS_Cvs_PhysicalProduct",
		MUNSCvsRptProduct.Table_Name,
		"UNS_DailyStockHistory",
		MRequisitionLine.Table_Name,
		MUNSPLReturnLine.Table_Name,
		"UNS_Production_Detail",
	};
	
	/**
	 * 
	 */
	public UNSUOMConvertionValidator() {
		super();
//		m_mapColumn = new Hashtable<String, QtyUOMMapping[]>();
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		
		if(client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.log(Level.INFO, client.toString());
		}
		else
		{
			log.log(Level.INFO, "Initializing global validator -" + this.toString());
			log.log(Level.INFO, "Table affected : ");
		}
		
		for(String tableName : m_list_Table_Name)
		{
			log.log(Level.INFO, "- " + tableName);
			engine.addModelChange(tableName, this);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception
	{	
		String event = Env.getContext(Env.getCtx(), "ON_IMPORT");
		boolean onImportEvent = !Util.isEmpty(event, true) && event.equals("Y");
		
		if(onImportEvent)
			return null;

		if(type != TYPE_BEFORE_NEW && type != TYPE_BEFORE_CHANGE)
			return null;
		
		UNSCustomCalcConvertion customConvertion = new UNSCustomCalcConvertion();
		customConvertion.start(po);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) {
		// NO Action
		return null;
	}
}