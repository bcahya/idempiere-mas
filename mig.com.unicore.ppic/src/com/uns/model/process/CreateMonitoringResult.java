package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import com.uns.model.I_C_DocType;
import com.uns.qad.model.MUNSQAMLabResult;
import com.uns.qad.model.MUNSQAMonitoringLab;
import com.uns.qad.model.MUNSQAStatusPRCLine;

public class CreateMonitoringResult extends SvrProcess {


	private String m_reff;
	private int m_emploID;
	private MUNSQAMonitoringLab m_monitoring;
	private MUNSQAStatusPRCLine m_record;

	@Override
	protected void prepare() {
		int monitoring = 0;
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if (MUNSQAMLabResult.COLUMNNAME_ReferenceNo.equals(name))
				m_reff = (String) para[i].getParameter();
			else if (MUNSQAMLabResult.COLUMNNAME_UNS_Employee_ID.equals(name))
				m_emploID = para[i].getParameterAsInt();
			else if (MUNSQAMLabResult.COLUMNNAME_UNS_QAMonitoringLab_ID.equals(name))
				monitoring = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		m_record = new MUNSQAStatusPRCLine(getCtx(), getRecord_ID(), get_TrxName());
		
		if (monitoring>0)
			m_monitoring = new MUNSQAMonitoringLab(getCtx(), monitoring, get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		if (!MUNSQAMLabResult.isExist(get_TrxName(), m_monitoring.get_ID(), getRecord_ID())){
			MUNSQAMLabResult result = new MUNSQAMLabResult(getCtx(), get_TrxName(), m_record);
			result.setReferenceNo(m_reff);
			result.setUNS_Employee_ID(m_emploID);

			result.setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
			result.setInspectionType(m_monitoring.getTestType());
			result.setM_Locator_ID(m_record.getM_Locator_ID());
			result.setM_Product_ID(m_record.getM_Product_ID());
			result.setM_AttributeSetInstance_ID(m_record.getM_AttributeSetInstance_ID());
			result.setUNS_QAMonitoringLab_ID(m_monitoring.get_ID());
			result.setUNS_QAStatus_PRCLine_ID(m_record.get_ID());
			
			result.setCodeNo(m_record.getCodeNo());
//			result.setRelease(m_record.getRelease());
//			result.setReleaseQty(m_record.getReleaseQty());
//			result.setOnHold(m_record.getOnHold());
//			result.setOnHoldQty(m_record.getOnHoldQty());
//			result.setNC(m_record.getNC());
//			result.setNCQty(m_record.getNCQty());
//			result.setQty(m_record.getQty());
			result.setRelease("-");
			result.setReleaseQty(Env.ZERO);
			result.setOnHold("-");
			result.setOnHoldQty(Env.ZERO);
			result.setNC("-");
			result.setNCQty(Env.ZERO);
			result.setQty(m_record.getQty());
			result.setQAStatus(MUNSQAStatusPRCLine.QASTATUS_OnHold);
			
			if (!result.save())
				throw new AdempiereException("Can't create result.");
			
			m_record.setQAStatus(MUNSQAStatusPRCLine.QASTATUS_OnHold);
			m_record.saveEx();
			
			return "Result reff no "+result.getReferenceNo()+" has created";
		} else
			return "Record with monitoring "+m_monitoring.getName()+" exists.";
	}

}