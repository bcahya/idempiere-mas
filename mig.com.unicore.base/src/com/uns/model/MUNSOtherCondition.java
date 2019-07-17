package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.compiere.util.DB;

public class MUNSOtherCondition extends X_UNS_OtherCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSOtherCondition(Properties ctx, int UNS_OtherCondition_ID,
			String trxName) {
		super(ctx, UNS_OtherCondition_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSOtherCondition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static String getName(Properties ctx, int UNS_OtherCondition_ID) {

		String retVal = "";
		String sql = "SELECT " + MUNSOtherCondition.COLUMNNAME_Name // 9..10
				+ " FROM " + MUNSOtherCondition.Table_Name
				+ " WHERE UNS_OtherCondition_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, UNS_OtherCondition_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				retVal = rs.getString(1);
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static String getDescription(Properties ctx, int UNS_OtherCondition_ID) {

		String retVal = "";
		String sql = "SELECT "
				+ MUNSOtherCondition.COLUMNNAME_Description // 9..10
				+ " FROM " + MUNSOtherCondition.Table_Name
				+ " WHERE UNS_OtherCondition_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, UNS_OtherCondition_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				retVal = rs.getString(1);
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
