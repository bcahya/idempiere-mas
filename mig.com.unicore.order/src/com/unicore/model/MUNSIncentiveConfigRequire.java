/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Burhani Adam
 *
 */
public class MUNSIncentiveConfigRequire extends X_UNS_IncentiveConfigRequire {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067509006944668320L;

	/**
	 * @param ctx
	 * @param UNS_IncentiveConfigRequire_ID
	 * @param trxName
	 */
	public MUNSIncentiveConfigRequire(Properties ctx,
			int UNS_IncentiveConfigRequire_ID, String trxName) {
		super(ctx, UNS_IncentiveConfigRequire_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveConfigRequire(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
