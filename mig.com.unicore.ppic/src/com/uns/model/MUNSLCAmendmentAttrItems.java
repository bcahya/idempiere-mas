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
public class MUNSLCAmendmentAttrItems extends X_UNS_LC_AmendmentAttrItems {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4400036084353943555L;

	/**
	 * @param ctx
	 * @param UNS_LC_AmendmentAttrItems_ID
	 * @param trxName
	 */
	public MUNSLCAmendmentAttrItems(Properties ctx,
			int UNS_LC_AmendmentAttrItems_ID, String trxName) {
		super(ctx, UNS_LC_AmendmentAttrItems_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAmendmentAttrItems(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
