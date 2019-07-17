/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Burhani Adam
 *
 */
public class MUNSDocSchemaCondition extends X_UNS_DocSchema_Condition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5197990467318652848L;

	/**
	 * @param ctx
	 * @param UNS_DocSchema_Condition_ID
	 * @param trxName
	 */
	public MUNSDocSchemaCondition(Properties ctx,
			int UNS_DocSchema_Condition_ID, String trxName) {
		super(ctx, UNS_DocSchema_Condition_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDocSchemaCondition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
