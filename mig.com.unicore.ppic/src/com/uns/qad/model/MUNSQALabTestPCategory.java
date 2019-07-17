package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSQALabTestPCategory extends X_UNS_QALabTest_PCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 590207145324592941L;

	public MUNSQALabTestPCategory(Properties ctx,
			int UNS_QALabTest_PCategory_ID, String trxName) {
		super(ctx, UNS_QALabTest_PCategory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQALabTestPCategory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
