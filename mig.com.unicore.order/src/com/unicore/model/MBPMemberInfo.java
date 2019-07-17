package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MBPMemberInfo extends X_C_BP_MemberInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7871053444048299859L;

	public MBPMemberInfo(Properties ctx, int C_BP_MemberInfo_ID, String trxName) {
		super(ctx, C_BP_MemberInfo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPMemberInfo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
