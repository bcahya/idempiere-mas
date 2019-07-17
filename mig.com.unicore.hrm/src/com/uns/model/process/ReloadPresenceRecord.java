package com.uns.model.process;
import java.util.Properties;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSYearlyPresenceSummary;


public class ReloadPresenceRecord extends SvrProcess {
	
	private int m_Record_ID = 0;
	private Properties m_Ctx = null;
	private String m_trxName = null;
	
	@Override
	protected void prepare() {
		m_Record_ID = getRecord_ID();
		m_Ctx = getCtx();
		m_trxName = get_TrxName();
	}

	@Override
	protected String doIt() throws Exception {
		String msg = null;
		if (m_Record_ID <= 0)
			return "No Yearly Presence ID";
		
		MUNSYearlyPresenceSummary Yearly = new MUNSYearlyPresenceSummary(
				m_Ctx, m_Record_ID, m_trxName);
		
		for(MUNSMonthlyPresenceSummary monthly : Yearly.getLines(null)){
			if (!monthly.isProcessed())
				monthly.updateData();
		}
		
		return msg;
	}

}
