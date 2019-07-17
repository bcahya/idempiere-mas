/**
 * 
 */
package com.uns.model.process;

import org.compiere.model.MMessage;
import org.compiere.model.MNote;
import org.compiere.process.SvrProcess;

import com.uns.model.X_UNS_LogBook;

/**
 * @author ITD-Andy
 *
 */
public class SentNotice extends SvrProcess {

	private int m_RecordID;
	private X_UNS_LogBook m_logBook;

	/**
	 * 
	 */
	public SentNotice() {
		
	}
	
	@Override
	protected void prepare() {
		
		m_RecordID = getRecord_ID();
		m_logBook = new X_UNS_LogBook(getCtx(), m_RecordID, get_TrxName());
	}
	
	@Override
	protected String doIt() throws Exception {
		
		MNote note = new MNote(getCtx(), MMessage.getAD_Message_ID(getCtx(), "Updated"), 
				m_logBook.getReportedBy_ID(), X_UNS_LogBook.Table_ID, m_RecordID, 
				"Data has been updated by ERP-Team", m_logBook.getHelp(), get_TrxName());
		note.setAD_Org_ID(m_logBook.getAD_Org_ID());
		
		note.saveEx();
		
		return "Notice has sent to " + 
			m_logBook.getReportedBy().getName() + 
			" : " + m_logBook.getReportedBy().getRealName();
	}

}
