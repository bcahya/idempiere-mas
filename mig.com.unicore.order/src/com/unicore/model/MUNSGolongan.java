package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSGolongan extends X_UNS_Golongan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8341603919691617235L;

	public MUNSGolongan(Properties ctx, int UNS_Golongan_ID, String trxName) {
		super(ctx, UNS_Golongan_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSGolongan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
