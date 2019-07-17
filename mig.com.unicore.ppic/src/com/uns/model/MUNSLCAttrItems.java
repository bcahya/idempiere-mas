/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class MUNSLCAttrItems extends X_UNS_LC_AttrItems {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4235841383150884528L;

	/**
	 * @param ctx
	 * @param UNS_LC_AttrItems_ID
	 * @param trxName
	 */
	public MUNSLCAttrItems(Properties ctx, int UNS_LC_AttrItems_ID,
			String trxName) {
		super(ctx, UNS_LC_AttrItems_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAttrItems(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSLCAttributes getPrent()
	{
		return (MUNSLCAttributes)getUNS_LC_Attributes();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			int lineNo = DB.getSQLValue(
					get_TrxName()
					, "SELECT COALESCE(MAX(" + COLUMNNAME_ItemNo + "),0) + 1 FROM " + Table_Name 
					+ " WHERE " + COLUMNNAME_UNS_LC_Attributes_ID + " = " 
							+ getUNS_LC_Attributes_ID()); 
			setItemNo(lineNo);
		}
		return super.beforeSave(newRecord);
	}
}
