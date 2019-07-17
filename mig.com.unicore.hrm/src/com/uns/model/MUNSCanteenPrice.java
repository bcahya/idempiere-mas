/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSCanteenPrice extends X_UNS_CanteenPrice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6109968616932958742L;

	/**
	 * @param ctx
	 * @param UNS_CanteenPrice_ID
	 * @param trxName
	 */
	public MUNSCanteenPrice(Properties ctx, int UNS_CanteenPrice_ID,
			String trxName) {
		super(ctx, UNS_CanteenPrice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCanteenPrice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static MUNSCanteenPrice getByDate (int orgID, Timestamp dateTrx, String trxName) {
		String wc = "AD_Org_ID = ? OR AD_Org_ID = ? AND ValidFrom <= ?";
		return Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, trxName)
				.setParameters(orgID, 0, dateTrx).setOrderBy("AD_Org_ID DESC, ValidFrom DESC")
				.first();
	}
}
