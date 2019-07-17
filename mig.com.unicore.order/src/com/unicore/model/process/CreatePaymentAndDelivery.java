/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import com.unicore.model.MUNSOrderGroup;

/**
 * @author Burhani Adam
 *
 */
public class CreatePaymentAndDelivery extends SvrProcess {

	/**
	 * 
	 */
	public CreatePaymentAndDelivery() {
		// TODO Auto-generated constructor stub
	}
	
	private MUNSOrderGroup group = null;
	public boolean isPickup = false;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
//		ProcessInfoParameter[] params = getParameter();
//		for(ProcessInfoParameter param : params)
//		{
//			if(param.getParameterName() == null)
//				;
//			else if(param.getParameterName().equals("IsPickup"))
//				isPickup = param.getParameterAsBoolean();
//			else
//				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
//		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		String msg = null;
		
		group = new MUNSOrderGroup(getCtx(), getRecord_ID(), get_TrxName());
		
		if(group.isVoided())
			return "Document has been void, cannot run this action.";
		
		if(!group.isComplete())
			msg = createPayment();
		else
			msg =  createPackingList();
		
		return msg;
	}
	
	private String createPayment()
	{
		if(!group.createPayment())
			return CLogger.retrieveErrorString("Error while trying create payment");
		
		return null;
	}
	
	private String createPackingList()
	{
		if(!group.createPackingList())
			return CLogger.retrieveErrorString("Error while trying create packing list");
		
		return null;
	}
}