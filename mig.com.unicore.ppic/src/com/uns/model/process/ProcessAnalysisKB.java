package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.qad.model.MUNSQAStatusNutLabTest;

public class ProcessAnalysisKB extends SvrProcess implements ProcessCall {

	private int m_nutLabTest_ID;
	private MUNSQAStatusNutLabTest m_nutLabTest;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_nutLabTest_ID = getRecord_ID();
		m_nutLabTest = new MUNSQAStatusNutLabTest(getCtx(), m_nutLabTest_ID, get_TrxName());

	}

	@Override
	protected String doIt() throws Exception {
		String msg = null;
		
		try {
			m_nutLabTest.completeIt();
			m_nutLabTest.saveEx(get_TrxName());
		}
		catch (AdempiereException ex) {
			msg = ex.getMessage();
		}
		
		return msg;
	}

}
