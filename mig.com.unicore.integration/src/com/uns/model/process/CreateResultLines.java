package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.qad.model.MUNSQAMLabResult;
import com.uns.qad.model.MUNSQAMLabResultInfo;
import com.uns.qad.model.MUNSQAMLabResultLine;
import com.uns.qad.model.MUNSQAMonitoringLab;
import com.uns.qad.model.MUNSQAMonitoringLabLine;

public class CreateResultLines extends SvrProcess {

	private MUNSQAMLabResult record;
	private String RECREATE = "IsCreated";
	private boolean m_recreate;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if(RECREATE.equals(name))
				m_recreate = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		if (getTable_ID()==MUNSQAMLabResult.Table_ID)
			record = new MUNSQAMLabResult(getCtx(), getRecord_ID(), get_TrxName());
		else {
			record = MUNSQAMLabResultInfo.getHeader(getCtx(), getRecord_ID(), get_TrxName());
		}

	}

	@Override
	protected String doIt() throws Exception {
		if (m_recreate)
			record.deleteAllLines();
		
		MUNSQAMonitoringLab monitoring = new MUNSQAMonitoringLab(getCtx(), record.getUNS_QAMonitoringLab_ID(), get_TrxName());
		int count = 0;
		if (!record.isInfo()){
			for (MUNSQAMonitoringLabLine mlLine : monitoring.getLine()){
				MUNSQAMLabResultLine.createLines(getCtx(), record, null, mlLine, get_TrxName());
				count++;
			}
		} else {
			for (MUNSQAMLabResultInfo info : record.getInfo()){
				for (MUNSQAMonitoringLabLine mlLine : monitoring.getLine()){
					MUNSQAMLabResultLine.createLines(getCtx(), record, info, mlLine, get_TrxName());
					count++;
				}
			}
		}
		return "Created "+count+" lines.";
	}

}
