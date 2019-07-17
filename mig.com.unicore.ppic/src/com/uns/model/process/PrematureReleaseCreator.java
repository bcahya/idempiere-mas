/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSPrematureRequest;

/**
 * @author AzHaidar
 *
 */
public class PrematureReleaseCreator extends SvrProcess {

	private int m_Record;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			para[i].getParameterName();
		}
		m_Record = getRecord_ID();
	}


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		return MUNSPrematureRequest.createFGPRR(getCtx(), m_Record, get_TrxName());
	}

}
