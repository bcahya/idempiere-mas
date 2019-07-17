/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSPSRealization;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionSchedule;

/**
 * @author YAKA
 *
 */
public class CreatePSRealization extends SvrProcess implements ProcessCall {

	/**
	 * 
	 */
	int uns_pd = 0;
	int uns_ps = 0;
	MUNSProductionDetail m_pd = null;
	MUNSProductionSchedule m_ps = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if ("UNS_Production_Detail_ID".equals(name))
				uns_pd = para[i].getParameterAsInt();
			else if ("UNS_ProductionSchedule_ID".equals(name))
				uns_ps = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_pd = new MUNSProductionDetail(getCtx(), uns_pd, get_TrxName());
		m_ps = new MUNSProductionSchedule(getCtx(), uns_ps, get_TrxName());

	}	//prepare


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String retVal = MUNSPSRealization.createRealization(m_ps, m_pd, getCtx(), get_TrxName());
		
		return retVal;
	}

}

