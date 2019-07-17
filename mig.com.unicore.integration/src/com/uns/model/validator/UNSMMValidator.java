/**
 * 
 */
package com.uns.model.validator;

import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.model.MMovement;
import org.compiere.process.DocAction;

import com.unicore.base.model.MOrder;
import com.unicore.model.MUNSShipping;

/**
 * @author root
 *
 */
public class UNSMMValidator implements ModelValidator 
{

	/**
	 * 
	 */
	public UNSMMValidator() 
	{
		super();
	}
	
	private int m_AD_Client_ID = 0;
	private CLogger log = CLogger.getCLogger(getClass());

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) 
	{
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
	
		log.log(Level.INFO, "- " + MMovement.Table_Name);
		engine.addDocValidate(MMovement.Table_Name, this);
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() 
	{
		return m_AD_Client_ID;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception 
	{
		//No action
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) 
	{
		if(!po.get_TableName().equals(MMovement.Table_Name))
		{
			return null;
		}
		
		MMovement move = (MMovement) po;
		String msg = null;
		if (timing == TIMING_BEFORE_COMPLETE && move.isInTransit())
		{
			MUNSShipping shipping = MUNSShipping.getShipping(move);
			if (null == shipping)
			{
				msg = "Please create shipping document first.";
			}
		}
		
		if(timing != TIMING_AFTER_COMPLETE)
		{
			return null;
		}
		
		if(move.getC_Order_ID() > 0)
		{
			MOrder order = new MOrder(
					move.getCtx(), move.getC_Order_ID(), move.get_TrxName());
			if(!order.isComplete())
			{
				String docAction = move.isReversal() ? DocAction.ACTION_Void :
					DocAction.ACTION_Complete;
				//complete canvas order document.
				order.setForce(true);
				order.processIt(docAction);
			}
			msg = order.getProcessMsg();
		}
	
		return msg;
	}
}
