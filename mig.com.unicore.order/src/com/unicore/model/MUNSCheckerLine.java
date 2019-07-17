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
public class MUNSCheckerLine extends X_UNS_CheckerLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7474753207589461670L;

	/**
	 * @param ctx
	 * @param UNS_CheckerLine_ID
	 * @param trxName
	 */
	public MUNSCheckerLine(Properties ctx, int UNS_CheckerLine_ID,
			String trxName) {
		super(ctx, UNS_CheckerLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckerLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		return true;
	}
}
