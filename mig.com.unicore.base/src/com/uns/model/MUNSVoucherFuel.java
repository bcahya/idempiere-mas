/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author root
 *
 */
public class MUNSVoucherFuel extends X_UNS_VoucherFuel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1977984597452819701L;

	/**
	 * @param ctx
	 * @param UNS_VoucherFuel_ID
	 * @param trxName
	 */
	public MUNSVoucherFuel(Properties ctx, int UNS_VoucherFuel_ID,
			String trxName) {
		super(ctx, UNS_VoucherFuel_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVoucherFuel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
