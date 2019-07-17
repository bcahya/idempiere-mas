/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPartnerLocation;

import com.unicore.model.X_UNS_Rayon;
import com.uns.model.GeneralCustomization;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSRayon extends X_UNS_Rayon {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2483061910461063467L;

	/**
	 * @param ctx
	 * @param UNS_Rayon_ID
	 * @param trxName
	 */
	public MUNSRayon(Properties ctx, int UNS_Rayon_ID, String trxName) {
		super(ctx, UNS_Rayon_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSRayon(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MBPartnerLocation getBPLocation(Properties ctx, int UNS_Rayon_ID, String trxName) {
		int bpl = GeneralCustomization.get_ID(trxName, MBPartnerLocation.Table_Name,
				MBPartnerLocation.COLUMNNAME_UNS_Rayon_ID, "=", UNS_Rayon_ID);
		
		return new MBPartnerLocation(ctx, bpl, trxName);
	}

}
