/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MOrg;

/**
 * @author AzHaidar
 *
 */
public class MUNSActualCostConfigItem extends X_UNS_ActualCostConfigItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7817984787871912898L;

	/**
	 * @param ctx
	 * @param UNS_ActualCostConfigItem_ID
	 * @param trxName
	 */
	public MUNSActualCostConfigItem(Properties ctx,
			int UNS_ActualCostConfigItem_ID, String trxName) {
		super(ctx, UNS_ActualCostConfigItem_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSActualCostConfigItem(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSActualCostConfigItem[")
			.append (get_ID()).append("-").append(MOrg.get(getCtx(), getAD_Org_ID()).getValue())
			.append(",").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString

}
