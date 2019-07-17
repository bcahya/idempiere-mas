package com.uns.model.validator;

import org.compiere.model.MClient;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Msg;

import com.uns.model.I_UNS_Chequebook;
import com.uns.model.MUNSChequeChangelog;
import com.uns.model.MUNSChequebook;
import com.uns.model.X_UNS_Chequebook;

public class ChequebookValidator implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instantiated when logging in and client is selected/known
	 */
	public ChequebookValidator ()
	{
		super ();
	}	//	MyValidator
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ChequebookValidator.class);
	/** Client			*/
	private int	m_AD_Client_ID = -1;
	
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null) 
		{	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  
		{
			log.info("Initializing global validator: "+this.toString());
		}
		
		engine.addModelChange(MPayment.Table_Name, this);
		engine.addModelChange(MUNSChequeChangelog.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID() 
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) 
	{
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception 
	{
		log.info(po.get_TableName() + " Type: "+type);
		String msg = null;

		if(po instanceof MPayment)
		{
			MPayment pay = (MPayment) po;
			if (!pay.getDocStatus().equals("DR"))
			{
				return msg;
			}
			if(pay.isReceipt())
			{
				return msg;	
			}
			else if (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE || 
					type == TYPE_BEFORE_CHANGE)
			{
				MPayment payment = (MPayment) po;
				msg = checkChequeNo(payment, type);
			}
		}
		
		return msg;				
	}

	@Override
	public String docValidate(PO po, int timing) {
		log.info(po.get_TableName() + " Timing: "+timing);
		
		String msg = null;
		if(po instanceof MPayment)
		{
			MPayment pay = (MPayment) po;
			if(pay.isReceipt())
			{
				return msg;	
			}
			
			if (timing == TIMING_BEFORE_COMPLETE) 
			{
				msg = checkAndTrySaveChequeReconciliation((MPayment) po);
			}
		}
		else if (po instanceof MUNSChequeChangelog)
		{
			if (timing == TIMING_AFTER_COMPLETE)
			{
				I_UNS_Chequebook cb = ((MUNSChequeChangelog)po).
						getUNS_Cheque_Reconciliation().getUNS_Chequebook();
				cb.setlast_used(((MUNSChequeChangelog)po).getChequeNo());
				((X_UNS_Chequebook) cb).save();
			}
		}
		return msg;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	private synchronized String checkAndTrySaveChequeReconciliation(MPayment po)
	{
		String msg = null;
		if (po.getTenderType().equals(MPayment.TENDERTYPE_Check)) 
		{	
			String chequeNo = po.getCheckNo();
			MUNSChequebook cb = (chequeNo == null || "".equals(chequeNo))? null :  
					MUNSChequebook.isRegistered(po.getCtx(), 
							po.get_TrxName(), po.getCheckNo()); 
			if ((cb == null))
			{
				return Msg.getMsg(po.getCtx(), "ChequeNotRegistered");
			}
			
			String properNumber = MUNSChequebook.getProperChequeNumberFormat(
					chequeNo, cb);
			
			if (cb.isUsed(properNumber, po.get_ID())) 
			{
				msg = Msg.getMsg(po.getCtx(), "ChequeHasBeenUsed");
			}
			else  
			{
				msg = cb.getCreateReconciliation(po);
				if (msg == null) 
				{
					cb.setlast_used(properNumber);
					po.set_ValueNoCheck(MPayment.COLUMNNAME_CheckNo, 
							properNumber);
					cb.save();
				}
			}
		}
		return msg;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	private String checkChequeNo(MPayment po, int type)
	{
		String msg = null;
		if (po.getTenderType().equals(MPayment.TENDERTYPE_Check) 
			&& (po.is_ValueChanged(MPayment.COLUMNNAME_CheckNo)
					|| type == TYPE_AFTER_NEW)) 
		{	
			String chequeNo = po.getCheckNo();
			MUNSChequebook cb = (chequeNo == null || "".equals(chequeNo))? null 
					: MUNSChequebook.isRegistered(po.getCtx(), 
							po.get_TrxName(), po.getCheckNo()); 
			if ((cb == null))
			{
				return Msg.getMsg(po.getCtx(), "ChequeNotRegistered");
			}
			
			String cekNum = MUNSChequebook.getProperChequeNumberFormat(
					chequeNo, cb);
			
			if (cb.isUsed(cekNum, po.get_ID())) 
			{
				msg = Msg.getMsg(po.getCtx(), "ChequeHasBeenUsed");
			}
			else if (type == TYPE_BEFORE_CHANGE || type == TYPE_AFTER_NEW)
			{
				po.set_ValueNoCheck(MPayment.COLUMNNAME_CheckNo, cekNum);
			}
		}
		return msg;
	}
}
