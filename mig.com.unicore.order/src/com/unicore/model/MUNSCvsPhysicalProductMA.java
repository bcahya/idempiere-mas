/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author root
 *
 */
public class MUNSCvsPhysicalProductMA extends X_UNS_Cvs_PhysicalProductMA {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1102168761115557245L;

	/**
	 * @param ctx
	 * @param UNS_Cvs_PhysicalProductMA_ID
	 * @param trxName
	 */
	public MUNSCvsPhysicalProductMA(Properties ctx,
			int UNS_Cvs_PhysicalProductMA_ID, String trxName) {
		super(ctx, UNS_Cvs_PhysicalProductMA_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsPhysicalProductMA(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
