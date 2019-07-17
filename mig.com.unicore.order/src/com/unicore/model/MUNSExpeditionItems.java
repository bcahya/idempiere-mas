/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Util;

/**
 * @author Burhani Adam
 *
 */
public class MUNSExpeditionItems extends X_UNS_ExpeditionItems {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6757925305323487036L;

	/**
	 * @param ctx
	 * @param UNS_ExpeditionItems_ID
	 * @param trxName
	 */
	public MUNSExpeditionItems(Properties ctx, int UNS_ExpeditionItems_ID,
			String trxName) {
		super(ctx, UNS_ExpeditionItems_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSExpeditionItems(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(getM_Product_ID() > 0 && Util.isEmpty(getProductName(), true))
		{
			setProductName(getM_Product().getName());
		}
		
		return true;
	}

}
