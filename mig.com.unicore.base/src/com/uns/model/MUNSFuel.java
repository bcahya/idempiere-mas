/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class MUNSFuel extends X_UNS_Fuel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -571784963692912045L;

	/**
	 * @param ctx
	 * @param UNS_Fuel_ID
	 * @param trxName
	 */
	public MUNSFuel(Properties ctx, int UNS_Fuel_ID, String trxName) {
		super(ctx, UNS_Fuel_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSFuel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Get fuel by value
	 * @param trxName
	 * @param value
	 * @return
	 */
	public static MUNSFuel get(String trxName, String value)
	{
		String sql = "SELECT * FROM ".concat(Table_Name).concat(" WHERE ")
				.concat(COLUMNNAME_Value).concat(" = ? ");
		
		MUNSFuel fuel = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setString(1, value);
			rs = st.executeQuery();
			if (rs.next())
			{
				fuel = new MUNSFuel(Env.getCtx(), rs, trxName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		return fuel;
	}
}
