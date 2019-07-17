package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.qad.model.MUNSQAMLabResult;
import com.uns.qad.model.MUNSQAMLabSummary;

public class SummitLabResult extends SvrProcess {

	private MUNSQAMLabResult m_result;
	private MUNSQAMLabSummary m_record;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		int result = 0;
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if(MUNSQAMLabResult.COLUMNNAME_UNS_QAMLab_Result_ID.equals(name))
				result = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_result = new MUNSQAMLabResult(getCtx(), result, get_TrxName());
		m_record = new MUNSQAMLabSummary(getCtx(), getRecord_ID(), get_TrxName());

	}

	@Override
	protected String doIt() throws Exception {
		m_record.setCumulatedQty(m_record.getCumulatedQty().add(m_result.getQty()));
		if(!m_record.save())
			throw new AdempiereException("Can't update quantity.");
		
		if (m_result.updateSummary(getRecord_ID()))
			return "Summit "+m_result.getReferenceNo()+", Succes!";
		return "error when update laboratory result.";
	}

}
