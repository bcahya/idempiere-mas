package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSMedicalDiseaseRecord extends X_UNS_MedicalDisease_Record {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336177382228409778L;

	public MUNSMedicalDiseaseRecord(Properties ctx,
			int UNS_MedicalDisease_Record_ID, String trxName) {
		super(ctx, UNS_MedicalDisease_Record_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSMedicalDiseaseRecord(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
