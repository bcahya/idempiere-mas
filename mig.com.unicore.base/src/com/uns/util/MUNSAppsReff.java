package com.uns.util;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSAppsReff extends X_UNS_AppsReff {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSAppsReff(Properties ctx, int UNS_AppsReff_ID, String trxName) {
		super(ctx, UNS_AppsReff_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSAppsReff(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
}
