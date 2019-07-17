package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MUNSResourceLocator extends X_UNS_Resource_Locator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9060325470891832687L;

	public MUNSResourceLocator(Properties ctx, int UNS_Resource_Locator_ID,
			String trxName) {
		super(ctx, UNS_Resource_Locator_ID, trxName);
	}

	public MUNSResourceLocator(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Get the designated M_Locator_ID for the product input, or get the default if not specified.
	 *  
	 * @param resource_ID
	 * @param input_ID
	 * @param trxName
	 * @return
	 */
	public static int getInputLocatorOf (int resource_ID, int input_ID, String trxName)
	{
		int retLoc;
		
		String sql = "SELECT M_Locator_ID FROM UNS_Resource_Locator WHERE M_Product_ID=? AND UNS_Resource_ID=?";
		
		retLoc = DB.getSQLValueEx(trxName, sql, input_ID, resource_ID);
		
		if (retLoc <= 0) {
			sql = "SELECT M_Locator_ID FROM UNS_Resource_Locator WHERE IsDefault='Y' AND UNS_Resource_ID=?";
			retLoc = DB.getSQLValueEx(trxName, sql, resource_ID);
		}
		
		return retLoc;
	}
}
