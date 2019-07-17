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
public class MUNSPointSchemaLine extends X_UNS_PointSchema_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6378806301775704442L;

	/**
	 * @param ctx
	 * @param UNS_PointSchema_Line_ID
	 * @param trxName
	 */
	public MUNSPointSchemaLine(Properties ctx, int UNS_PointSchema_Line_ID,
			String trxName) {
		super(ctx, UNS_PointSchema_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPointSchemaLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
