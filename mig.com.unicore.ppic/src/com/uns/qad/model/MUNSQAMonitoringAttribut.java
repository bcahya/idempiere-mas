/**
 * 
 */
package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author YAKA
 *
 */
public class MUNSQAMonitoringAttribut extends X_UNS_QAMonitoringAttribut {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2560590603822711128L;

	/**
	 * @param ctx
	 * @param UNS_QAMonitoringAttribut_ID
	 * @param trxName
	 */
	public MUNSQAMonitoringAttribut(Properties ctx,
			int UNS_QAMonitoringAttribut_ID, String trxName) {
		super(ctx, UNS_QAMonitoringAttribut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAMonitoringAttribut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
