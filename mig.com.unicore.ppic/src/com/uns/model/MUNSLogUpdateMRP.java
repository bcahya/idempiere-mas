/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class MUNSLogUpdateMRP extends X_UNS_LogUpdate_MRP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_LogUpdate_MRP_ID
	 * @param trxName
	 */
	public MUNSLogUpdateMRP(Properties ctx, int UNS_LogUpdate_MRP_ID,
			String trxName) {
		super(ctx, UNS_LogUpdate_MRP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLogUpdateMRP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param recordID
	 * @param tableName
	 * @return
	 */
	public static MUNSLogUpdateMRP get(int recordID, String tableName, String trxName)
	{
		MUNSLogUpdateMRP logUpdateMrp = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM UNS_LogUpdate_MRP WHERE Record_ID =? AND tableName = ?";
		try {

			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, recordID);
			st.setString(2, tableName);
			rs = st.executeQuery();
			if (rs.next())
			{
				logUpdateMrp = new MUNSLogUpdateMRP(Env.getCtx(), rs, trxName);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally {
			DB.close(rs, st);;
		}
		
		return logUpdateMrp;
	}
}
