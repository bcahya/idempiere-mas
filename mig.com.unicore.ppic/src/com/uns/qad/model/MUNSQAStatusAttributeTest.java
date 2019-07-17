package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSQAStatusAttributeTest extends X_UNS_QAStatus_AttributeTest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6150739133289626435L;

	public MUNSQAStatusAttributeTest(Properties ctx,
			int UNS_QAStatus_AttributeTest_ID, String trxName) {
		super(ctx, UNS_QAStatus_AttributeTest_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAStatusAttributeTest(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean beforeSave(boolean newRecord){
		if(getUNS_QAMLab_Result_ID()!=0){
			if(getUNS_QAMLab_Result().getDocStatus().equals(MUNSQAMLabResult.DOCSTATUS_Drafted)
					|| getUNS_QAMLab_Result().getDocStatus().equals(MUNSQAMLabResult.DOCSTATUS_InProgress)){
				setDescription("Need Confirmation Lab Result.");
			} 
			else if (getUNS_QAMLab_Result().getDocStatus().equals(MUNSQAMLabResult.DOCSTATUS_Completed)){
				I_UNS_QAMLab_Result labResult = getUNS_QAMLab_Result();
				if(null != labResult.getRelease() && !"".equals(labResult.getRelease())
						&& "-".equals(labResult.getRelease()))
					setName("LULUS");
				else
					setName("GAGAL");
//				if(getUNS_QAMLab_Result().getQAStatus().equals(MUNSQAMLabResult.QASTATUS_Release)){
//					setName("LULUS");
//				} else if(!getUNS_QAMLab_Result().getQAStatus().equals(MUNSQAMLabResult.QASTATUS_PendingInspectionLabTes)){
//					setName("GAGAL");
//				}
			}
				
		}
		
		return super.beforeSave(newRecord);
	}
}
