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
public class MUNSItemRewardSelection extends X_UNS_ItemReward_Selection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3949760906778973008L;

	/**
	 * @param ctx
	 * @param UNS_ItemReward_Selection_ID
	 * @param trxName
	 */
	public MUNSItemRewardSelection(Properties ctx,
			int UNS_ItemReward_Selection_ID, String trxName) {
		super(ctx, UNS_ItemReward_Selection_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSItemRewardSelection(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
