package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MUNSQAMLabResultLine extends X_UNS_QAMLab_ResultLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1323438137146043629L;

	public MUNSQAMLabResultLine(Properties ctx, int UNS_QAMLab_ResultLine_ID,
			String trxName) {
		super(ctx, UNS_QAMLab_ResultLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAMLabResultLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static void createLines(Properties ctx, MUNSQAMLabResult mlr, MUNSQAMLabResultInfo info, MUNSQAMonitoringLabLine mll, String trxName){
		MUNSQAMLabResultLine line = new MUNSQAMLabResultLine(ctx, 0, trxName);
		if (info!=null){
			line.setUNS_QAMLab_ResultInfo_ID(info.get_ID());
			line.setAD_Org_ID(info.getAD_Org_ID());
		} else {
			line.setAD_Org_ID(mlr.getAD_Org_ID());
			line.setUNS_QAMLab_Result_ID(mlr.get_ID());
		}
		line.setUNS_QAMonitoringLabLine_ID(mll.get_ID());
		line.setUNS_QALabTest_ID(mll.getUNS_QALabTest_ID());
		if (!line.save())
			throw new AdempiereException("Can't create Lines.");
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!newRecord)
			getParent().updateQAStatus();		
		
		return super.afterSave(newRecord, success);
	}
	
	@Override
	public MUNSQAMLabResult getParent() {
		if (getUNS_QAMLab_Result_ID()!=0)
			return new MUNSQAMLabResult(getCtx(), getUNS_QAMLab_Result_ID(), get_TrxName());
		else 
			return new MUNSQAMLabResult(getCtx(), 
					getUNS_QAMLab_ResultInfo().getUNS_QAMLab_Result_ID(), get_TrxName());
	}

}
