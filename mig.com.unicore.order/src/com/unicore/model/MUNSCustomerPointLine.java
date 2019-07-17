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
public class MUNSCustomerPointLine extends X_UNS_CustomerPoint_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 223013700303074186L;

	/**
	 * @param ctx
	 * @param UNS_CustomerPoint_Line_ID
	 * @param trxName
	 */
	public MUNSCustomerPointLine(Properties ctx, int UNS_CustomerPoint_Line_ID,
			String trxName) {
		super(ctx, UNS_CustomerPoint_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerPointLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
}
