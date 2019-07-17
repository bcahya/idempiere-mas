/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Burhani Adam
 *
 */
public class MUNSIncentiveConfigLine extends X_UNS_IncentiveConfigLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -902501583167821353L;

	/**
	 * @param ctx
	 * @param UNS_IncentiveConfigLine_ID
	 * @param trxName
	 */
	public MUNSIncentiveConfigLine(Properties ctx,
			int UNS_IncentiveConfigLine_ID, String trxName) {
		super(ctx, UNS_IncentiveConfigLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveConfigLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
