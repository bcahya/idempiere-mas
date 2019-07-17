/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSLCSO extends X_UNS_LC_SO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_LC_SO_ID
	 * @param trxName
	 */
	public MUNSLCSO(Properties ctx, int UNS_LC_SO_ID, String trxName) {
		super(ctx, UNS_LC_SO_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCSO(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
