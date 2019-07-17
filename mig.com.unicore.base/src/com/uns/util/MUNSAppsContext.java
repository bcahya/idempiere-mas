package com.uns.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MUNSAppsContext extends X_UNS_AppsContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSAppsContext(Properties ctx, int UNS_AppsContext_ID,
			String trxName) {
		super(ctx, UNS_AppsContext_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ctx
	 * @param context
	 * @param trxName
	 * @return
	 */
	public static MUNSAppsContext get(int context, Properties ctx,
			String trxName) {
		MUNSAppsContext mAppsCtx = null;
		String sql = "SELECT * FROM " + Table_Name + " WHERE "
				+ COLUMNNAME_UNS_AppsReff_ID + " =?";
		PreparedStatement stm = DB.prepareStatement(sql, null);
		ResultSet rs = null;
		try {
			//PreparedStatement stm = DB.prepareStatement(sql, trxName);
			stm.setInt(1, context);
			rs = stm.executeQuery();
			if (rs.next())
				mAppsCtx = new MUNSAppsContext(ctx, rs, null);

			rs.close();
			stm.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return mAppsCtx;
	}

	public MUNSAppsContext(Properties ctx, ResultSet rs, String trxName) {

		super(ctx, rs, trxName);
	}

	protected boolean beforeDelete(boolean newRecord) {
		// The record can't be remove
		return false;
	}

	/**
	 * Get Product.
	 * 
	 * @return Product, Service, Item
	 */
	public int getM_Product_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null || ii.intValue() == 0)
			throw new AdempiereException(
					"Fatal Error: Product has not set. Check at Apps Context.");
		return ii.intValue();
	}

	/**
	 * Get DB Table Name.
	 * 
	 * @return Name of the table in the database
	 */
	public int getAD_Table_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null || ii.intValue() == 0)
			throw new AdempiereException(
					"Fatal Error: Table has not set. Check at Apps Context.");
		return ii.intValue();
	}

	/**
	 * Get Cost Element.
	 * 
	 * @return Product Cost Element
	 */
	public int getM_CostElement_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null || ii.intValue() == 0)
			throw new AdempiereException(
					"Fatal Error: Cost Element has not set. Check at Apps Context.");
		return ii.intValue();
	}

	/**
	 * Get Charge.
	 * 
	 * @return Additional document charges
	 */
	public int getC_Charge_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null || ii.intValue() == 0)
			throw new AdempiereException(
					"Fatal Error: Charge has not set. Check at Apps Context.");
		return ii.intValue();
	}
	
	/**
	 * get UOM
	 * 
	 * @return UOM
	 */
	public int getC_UOM_ID()
	{
		Integer ii = (Integer) get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null || ii.intValue() == 0)
			throw new AdempiereException(
					"Fatal Error: UOM has not set. Check at Apps Context.");
		
		return ii.intValue();
	}
	
	/**
	 * get Account_ID 
	 * @return C_ValidCombination_ID
	 */
	public int getC_ValidCombination_ID()
	{
		Integer ii = (Integer) get_Value("C_ValidCombination_ID");
		if(ii == null || ii.intValue() <= 0)
			throw new AdempiereException("Fatal Error : Account has not set. Check at Apps Context.");
		
		return ii.intValue();
	}
}
