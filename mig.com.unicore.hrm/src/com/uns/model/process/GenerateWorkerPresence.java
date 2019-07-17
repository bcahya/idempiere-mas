/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSHalfPeriodPresence;

/**
 * @author menjangan
 *
 */
public class GenerateWorkerPresence extends SvrProcess {
	
	private int m_Record_ID = 0;

	/**
	 * 
	 */
	public GenerateWorkerPresence() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		String msg = null;
		MUNSHalfPeriodPresence halfPeriodPresence = new MUNSHalfPeriodPresence(
								getCtx(), m_Record_ID, get_TrxName());
		msg = halfPeriodPresence.generateWorkerPresence();
		return msg;
	}

}
