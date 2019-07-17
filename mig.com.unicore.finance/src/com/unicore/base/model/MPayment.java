/**
 * 
 */
package com.unicore.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author setyaka
 *
 */
public class MPayment extends org.compiere.model.MPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2185046750335145854L;

	public MPayment(Properties ctx, int C_Payment_ID, String trxName) {
		super(ctx, C_Payment_ID, trxName);
	}

	public MPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
}
