/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSMOSchedulerLines;

/**
 * @author menjangan
 *
 */
public class MOSchedulerCreateProduction extends SvrProcess {

	/**
	 * 
	 */
	public MOSchedulerCreateProduction() {
		// TODO Auto-generated constructor stub
	}
	
	private MUNSMOSchedulerLines m_SchedulerLine = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		m_SchedulerLine = new MUNSMOSchedulerLines(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		CreateProduction createProduction = new CreateProduction(
				getCtx(), m_SchedulerLine.getUNS_ProductionSchedule_ID(), get_TrxName());
		try {
			m_SchedulerLine.setCreateProduction("Y");
			m_SchedulerLine.save(get_TrxName());
			return createProduction.doIt();
		}catch(Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
	}

}
