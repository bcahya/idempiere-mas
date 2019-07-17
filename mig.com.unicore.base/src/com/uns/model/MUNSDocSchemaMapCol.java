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
public class MUNSDocSchemaMapCol extends X_UNS_DocSchema_MapCol {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7284094506694116124L;

	/**
	 * @param ctx
	 * @param UNS_DocSchema_MapCol_ID
	 * @param trxName
	 */
	public MUNSDocSchemaMapCol(Properties ctx, int UNS_DocSchema_MapCol_ID,
			String trxName) {
		super(ctx, UNS_DocSchema_MapCol_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDocSchemaMapCol(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	protected boolean beforeSave(boolean newRecord)
	{
		String sql = "SELECT 1 FROM UNS_DocSchema_MapCol WHERE UNS_DocumentSchema_ID=?"
				+ " AND (AD_Column_ID=? OR ColumnReferer_ID=?)";
		int count = DB.getSQLValue(get_TrxName(), sql, new Object[]{getUNS_DocumentSchema_ID(),
						getAD_Column_ID(), getColumnReferer_ID()});
		if(count > 0)
		{
			log.saveError("Error", "Duplicate mapping in one schema");
			return false;
		}
		return true;
	}
}