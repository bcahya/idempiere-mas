/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSForecastAllocation;
import com.uns.model.MUNSForecastHeader;

/**
 * @author menjangan
 *
 */
public class ForecastAllocationGenerator extends SvrProcess {

	private int m_Record_ID = 0;
	
	/**
	 * 
	 */
	public ForecastAllocationGenerator() {

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
	
	protected String doIt() throws Exception 
	{
		String msg = null;
		
		MUNSForecastHeader forecastHeader = new MUNSForecastHeader(getCtx(), m_Record_ID, get_TrxName());
		MUNSForecastAllocation inoutForecast = new MUNSForecastAllocation(forecastHeader);
		msg = inoutForecast.initInOutAllocations();
		
		return msg;
	}
}
