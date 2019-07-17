/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSBillingTrfValidation;

/**
 * @author Burhani Adam
 *
 */
public class VoidTrfAllocation extends SvrProcess{

	/**
	 * 
	 */
	public VoidTrfAllocation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception
	{
		if(getRecord_ID() <= 0)
			return null;
		
		MUNSBillingTrfValidation tfv = new MUNSBillingTrfValidation(getCtx(), getRecord_ID(), get_TrxName());
		tfv.processIt(DocAction.ACTION_ReActivate);
		tfv.saveEx();

		return null;
	}

}
