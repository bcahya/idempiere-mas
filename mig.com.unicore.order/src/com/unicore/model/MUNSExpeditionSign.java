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
public class MUNSExpeditionSign extends X_UNS_ExpeditionSign {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8551106246587047114L;

	/**
	 * @param ctx
	 * @param UNS_ExpeditionSign_ID
	 * @param trxName
	 */
	public MUNSExpeditionSign(Properties ctx, int UNS_ExpeditionSign_ID,
			String trxName) {
		super(ctx, UNS_ExpeditionSign_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSExpeditionSign(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(getC_BPartner_ID() > 0 && Util.isEmpty(getBPartnerName(), true))
		{
			setBPartnerName(getC_BPartner().getName());
		}
		
		if(getC_BPartner_Location_ID() > 0 && Util.isEmpty(getAddress(), true))	
		{
			setAddress(getC_BPartner_Location().getName());
		}
		
		return true;
	}
}
