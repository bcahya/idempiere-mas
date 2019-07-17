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
public class MUNSHandoverDocLine extends X_UNS_HandoverDoc_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6292680678360373608L;

	/**
	 * @param ctx
	 * @param UNS_HandoverDoc_Line_ID
	 * @param trxName
	 */
	public MUNSHandoverDocLine(Properties ctx, int UNS_HandoverDoc_Line_ID,
			String trxName) {
		super(ctx, UNS_HandoverDoc_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSHandoverDocLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
