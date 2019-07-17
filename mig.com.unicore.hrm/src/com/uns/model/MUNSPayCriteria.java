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
public class MUNSPayCriteria extends X_UNS_PayCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529590290231714486L;

	/**
	 * @param ctx
	 * @param UNS_PayCriteria_ID
	 * @param trxName
	 */
	public MUNSPayCriteria(Properties ctx, int UNS_PayCriteria_ID,
			String trxName) {
		super(ctx, UNS_PayCriteria_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayCriteria(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSProductionPayConfig getParent()
	{
		return (MUNSProductionPayConfig)getUNS_ProductionPayConfig();
	}

}
