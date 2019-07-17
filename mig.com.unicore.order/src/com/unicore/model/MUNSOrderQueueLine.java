/**
 * 
 */
package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author ALBURHANY
 *
 */
public class MUNSOrderQueueLine extends X_UNS_OrderQueue_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5900359932212460859L;

	/**
	 * @param ctx
	 * @param UNS_OrderQueue_Line_ID
	 * @param trxName
	 */
	public MUNSOrderQueueLine(Properties ctx, int UNS_OrderQueue_Line_ID,
			String trxName) {
		super(ctx, UNS_OrderQueue_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOrderQueueLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param C_OrderLine_ID
	 * @param M_Product_ID
	 * @param AD_Org_ID
	 * @param trxName
	 * @return record
	 */
	public static MUNSOrderQueueLine get(int C_OrderLine_ID, int M_Product_ID, int AD_Org_ID, String trxName)
	{
		MUNSOrderQueueLine record = null;
		String sql = "SELECT * FROM " + Table_Name + " WHERE " + COLUMNNAME_C_OrderLine_ID + " = ?"
				+ " AND " + COLUMNNAME_UNS_OrderQueue_ID + " = (SELECT UNS_OrderQueue_ID FROM UNS_OrderQueue"
						+ " WHERE M_Product_ID " + " = ?" + " AND AD_Org_ID " + " = ? " + " )";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, C_OrderLine_ID);
			st.setInt(2, M_Product_ID);
			st.setInt(3, AD_Org_ID);
			rs = st.executeQuery();
			if(rs.next())
			{
				record = new MUNSOrderQueueLine(Env.getCtx(), rs, trxName);
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
		return record;
	}
}
