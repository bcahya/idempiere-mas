/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author eko
 *
 */
public class MUNSJobDescription extends X_UNS_JobDescription {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_JobDescription_ID
	 * @param trxName
	 */
	public MUNSJobDescription(Properties ctx, int UNS_JobDescription_ID,
			String trxName) {
		super(ctx, UNS_JobDescription_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSJobDescription(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
