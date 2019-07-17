package org.globalqss.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MLCOWithholdingType extends X_LCO_WithholdingType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -919983984441323543L;

	public MLCOWithholdingType(Properties ctx, int LCO_WithholdingType_ID, String trxName) {
		super(ctx, LCO_WithholdingType_ID, trxName);
	}

	public MLCOWithholdingType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
