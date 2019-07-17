/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCheckerLineResult extends X_UNS_CheckerLine_Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1679135296629773434L;

	/**
	 * @param ctx
	 * @param UNS_CheckerLine_Result_ID
	 * @param trxName
	 */
	public MUNSCheckerLineResult(Properties ctx, int UNS_CheckerLine_Result_ID,
			String trxName) {
		super(ctx, UNS_CheckerLine_Result_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckerLineResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
