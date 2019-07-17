/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSHalfPeriodPresence;

/**
 * @author menjangan
 *
 */
public class CreateCriteriaResult extends SvrProcess {

	/**
	 * 
	 */
	public CreateCriteriaResult() {
	}

	private MUNSHalfPeriodPresence m_HalfPresence = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {		
		m_HalfPresence = new MUNSHalfPeriodPresence(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(m_HalfPresence.isCreateCriteriraResult())
			if(!m_HalfPresence.deleteCriteriaResult())
				throw new AdempiereUserError("Failed to delete old criteria result");
		return m_HalfPresence.createCriteriaResults();
	}

}
