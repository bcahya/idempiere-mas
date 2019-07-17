package com.uns.model.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSContainerArrival;

public class ContainerArrivalCreateLines extends SvrProcess {

	private MUNSContainerArrival m_record;

	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){

			log.fine("prepare - " + para[i]);
		}
		m_record = new MUNSContainerArrival(getCtx(), getRecord_ID(), get_TrxName());
		}

	@Override
	protected String doIt() throws Exception {
		int count = m_record.createLinesFrom();
		
		if (count<=0)
			throw new IllegalArgumentException("Cant Create Lines");
		
		return "Created " + count + " lines.";
	}

}
