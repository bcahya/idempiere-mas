/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSBonusClaimLineConfirm extends X_UNS_BonusClaimLineConfirm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_BonusClaimLineConfirm_ID
	 * @param trxName
	 */
	public MUNSBonusClaimLineConfirm(Properties ctx,
			int UNS_BonusClaimLineConfirm_ID, String trxName) {
		super(ctx, UNS_BonusClaimLineConfirm_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBonusClaimLineConfirm(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSBonusClaimLineConfirm(MUNSBonusClaimConfirm confirm)
	{
		super(confirm.getCtx(), 0, confirm.get_TrxName());
		setClientOrg(confirm);
		confirm.setUNS_BonusClaimConfirm_ID(confirm.get_ID());
	}

}
