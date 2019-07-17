package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSJobRole extends X_UNS_Job_Role {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1663369897230518229L;

	public MUNSJobRole(Properties ctx, int UNS_Job_Role_ID, String trxName) {
		super(ctx, UNS_Job_Role_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSJobRole(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
}
