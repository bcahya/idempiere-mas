package com.unicore.model.validator;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;

import com.unicore.model.process.CalculateIncentive;

public class UNSIncentiveValidator implements ModelValidator {

	public UNSIncentiveValidator() {
		super();
	}

	private int m_AD_Client_ID = 0;
	CLogger log = CLogger.getCLogger(UNSIncentiveValidator.class);
	
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
		
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) 
	{		
		if(timing != TIMING_AFTER_REVERSEACCRUAL
				&& timing != TIMING_AFTER_REACTIVATE
				&& timing != TIMING_AFTER_REVERSECORRECT
				&& timing != TIMING_AFTER_VOID)
			return null;
		
		CalculateIncentive calc = new CalculateIncentive(po);
		calc.calculate();
		
		return null;
	}

}
