/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCustomerPoint extends X_UNS_CustomerPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898140316267335056L;

	/**
	 * @param ctx
	 * @param UNS_CustomerPoint_ID
	 * @param trxName
	 */
	public MUNSCustomerPoint(Properties ctx, int UNS_CustomerPoint_ID,
			String trxName) {
		super(ctx, UNS_CustomerPoint_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerPoint(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get SUM Point Valid
	 * @param C_BPartner_ID
	 * @return Total Point
	 */
	public int getTotalValidPoint (int C_BPartner_ID)
	{
		int totalPoint = 0;
		
		String sql = "SELECT SUM (Point) FROM UNS_CustomerPoint_Line WHERE isValid = 'Y'"
				+ " AND UNS_CustomerPoint_ID = (SELECT UNS_CustomerPoint_ID FROM UNS_CustomerPoint"
				+ " WHERE C_BPartner_ID = " + C_BPartner_ID  + ")";
		totalPoint = DB.getSQLValue(get_TrxName(), sql);
		
		return totalPoint;
	}
	
	/**
	 * get SUM Point Not Yet Valid
	 * @param C_BPartner_ID
	 * @return Total Point
	 */
	public int getTotalNotYetValidPoint (int C_BPartner_ID)
	{
		int totalPoint = 0;
		
		String sql = "SELECT SUM (Point) FROM UNS_CustomerPoint_Line WHERE isValid = 'N'"
				+ " AND UNS_CustomerPoint_ID = (SELECT UNS_CustomerPoint_ID FROM UNS_CustomerPoint"
				+ " WHERE C_BPartner_ID = " + C_BPartner_ID + ")";
		totalPoint = DB.getSQLValue(get_TrxName(), sql);
		
		return totalPoint;
	}
}

