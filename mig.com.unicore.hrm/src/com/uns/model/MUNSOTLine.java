/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Burhani Adam
 *
 */
public class MUNSOTLine extends X_UNS_OTLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5680316630035875408L;

	/**
	 * @param ctx
	 * @param UNS_OTLine_ID
	 * @param trxName
	 */
	public MUNSOTLine(Properties ctx, int UNS_OTLine_ID, String trxName) {
		super(ctx, UNS_OTLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOTLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			String sql = "SELECT COALESCE(MAX(Line),0) FROM UNS_OTLine WHERE UNS_OTRequest_ID = ?"
					+ " AND UNS_OTLine_ID <> ?";
			int line = DB.getSQLValue(get_TrxName(), sql, getUNS_OTRequest_ID(), get_ID());
			setLine(line + 10);
		}
		if(getUNS_Resource_ID() > 0)
			setIsSummary(true);
		else
			setIsSummary(false);
		return true;
	}
}
