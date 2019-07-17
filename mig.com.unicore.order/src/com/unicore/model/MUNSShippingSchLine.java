package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSShippingSchLine extends X_UNS_ShippingIncentiveSch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940804075964263699L;

	public MUNSShippingSchLine(Properties ctx, int UNS_ShippingIncentiveSch_ID,
			String trxName) {
		super(ctx, UNS_ShippingIncentiveSch_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSShippingSchLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
