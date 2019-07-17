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
public class MUNSVoucherArmada extends X_UNS_VoucherArmada {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8150155430512643402L;

	/**
	 * @param ctx
	 * @param UNS_VoucherArmada_ID
	 * @param trxName
	 */
	public MUNSVoucherArmada(Properties ctx, int UNS_VoucherArmada_ID,
			String trxName) {
		super(ctx, UNS_VoucherArmada_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVoucherArmada(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
