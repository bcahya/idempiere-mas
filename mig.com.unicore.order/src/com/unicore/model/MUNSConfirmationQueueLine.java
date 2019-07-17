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
public class MUNSConfirmationQueueLine extends X_UNS_ConfirmationQueue_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4953206728121479590L;

	/**
	 * @param ctx
	 * @param UNS_ConfirmationQueue_Line_ID
	 * @param trxName
	 */
	public MUNSConfirmationQueueLine(Properties ctx,
			int UNS_ConfirmationQueue_Line_ID, String trxName) {
		super(ctx, UNS_ConfirmationQueue_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSConfirmationQueueLine(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
