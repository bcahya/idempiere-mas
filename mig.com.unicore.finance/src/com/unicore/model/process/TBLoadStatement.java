/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;

import com.unicore.base.model.MBankStatementLine;
import com.unicore.model.MUNSTransferBalanceRequest;

/**
 * @author menjangan
 *
 */
public class TBLoadStatement extends SvrProcess {

	/**
	 * 
	 */
	public TBLoadStatement() 
	{
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		//Do nothing
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		MUNSTransferBalanceRequest req = new MUNSTransferBalanceRequest(
				getCtx(), getRecord_ID(), get_TrxName());
		
		if(req.isProcessed())
			return "";
		
		MBankStatementLine[] sLines = MBankStatementLine.getLastOf(
				req.getAccountTo_ID(), req.getDateRequired(), get_TrxName());

		for(MBankStatementLine sLine : sLines)
		{
			if(!sLine.isProcessed())
				continue;
			
			sLine.setUNS_TransferBalance_Request_ID(req.get_ID());
			sLine.save();
		}
		
		return null;
	}
}
