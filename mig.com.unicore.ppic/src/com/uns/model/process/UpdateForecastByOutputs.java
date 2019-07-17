package com.uns.model.process;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSForecastHeader;

public class UpdateForecastByOutputs extends SvrProcess {
	
	private int m_Record_ID =0;

	public UpdateForecastByOutputs() {
	}

	@Override
	protected void prepare() 
	{
		m_Record_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception 
	{
		MUNSForecastHeader fh = new MUNSForecastHeader(getCtx(), m_Record_ID, get_TrxName());
		fh.updateForecastByOutput();		
		return null;
	}

}
