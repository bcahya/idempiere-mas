/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author root
 *
 */
public class MUNSCvsRptProductMA extends X_UNS_Cvs_RptProductMA {

	/**
	 * 
	 */
	private static final long serialVersionUID = -403193911874730905L;

	/**
	 * @param ctx
	 * @param UNS_Cvs_RptProductMA_ID
	 * @param trxName
	 */
	public MUNSCvsRptProductMA(Properties ctx, int UNS_Cvs_RptProductMA_ID,
			String trxName) {
		super(ctx, UNS_Cvs_RptProductMA_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsRptProductMA(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
