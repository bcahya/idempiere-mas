package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MUNSQAMonitoringLabLine extends X_UNS_QAMonitoringLabLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8777241308732384530L;

	public MUNSQAMonitoringLabLine(Properties ctx,
			int UNS_QAMonitoringLabLine_ID, String trxName) {
		super(ctx, UNS_QAMonitoringLabLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAMonitoringLabLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
