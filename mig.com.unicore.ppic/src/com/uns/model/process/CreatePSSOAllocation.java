/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.base.model.MOrderLine;
import com.uns.model.MUNSPSSOAllocation;
import com.uns.model.MUNSProductionSchedule;

/**
 * @author YAKA
 *
 */
public class CreatePSSOAllocation extends SvrProcess implements ProcessCall {

	/**
	 * 
	 */
	int c_orderline_id = 0;
	int uns_ps = 0;
	MOrderLine m_orderline = null;
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
			if ("C_OrderLine_ID".equals(name))
				c_orderline_id = para[i].getParameterAsInt();
			else if ("UNS_ProductionSchedule_ID".equals(name))
				uns_ps = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_orderline = new MOrderLine(getCtx(), c_orderline_id, get_TrxName());
		m_ps = new MUNSProductionSchedule(getCtx(), uns_ps, get_TrxName());

	}	//prepare

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String retVal = MUNSPSSOAllocation.createSOAllocation(m_orderline, m_ps, getCtx(), get_TrxName());
		
		return retVal;
	}

}
