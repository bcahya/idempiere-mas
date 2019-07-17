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
public class MUNSHODLine extends X_UNS_HOD_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1094387995408747750L;

	/**
	 * @param ctx
	 * @param UNS_HOD_Line_ID
	 * @param trxName
	 */
	public MUNSHODLine(Properties ctx, int UNS_HOD_Line_ID, String trxName) {
		super(ctx, UNS_HOD_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSHODLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeDelete()
	{	
		return false;
	}
}
