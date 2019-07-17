/**
 * 
 */
package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Toriq
 *
 */
public class MUNSDailyStockHistory extends X_UNS_DailyStockHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5341881243199345732L;

	/**
	 * @param ctx
	 * @param UNS_DailyStockHistory_ID
	 * @param trxName
	 */
	public MUNSDailyStockHistory(Properties ctx, int UNS_DailyStockHistory_ID,
			String trxName) {
		super(ctx, UNS_DailyStockHistory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDailyStockHistory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MUNSDailyStockHistory getToDate(String trxName,Timestamp date,int Product,int Whs){
		String sql ="select * from uns_dailystockhistory as a where a.m_product_id=? "
				+ "and a.m_warehouse_id=? and a.datetrx=(select max(b.datetrx) from uns_dailystockhistory as b "
				+ "where b.datetrx<=? and  b.m_product_id=a.m_product_id and b.m_warehouse_id=a.m_warehouse_id)";
		MUNSDailyStockHistory record = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt= DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, Product);
			pstmt.setInt(2, Whs);
			pstmt.setTimestamp(3, date);
			rs= pstmt.executeQuery();
			if (rs.next()){
				record= new MUNSDailyStockHistory(Env.getCtx(), rs, trxName);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
		}
		return record;
	}
	protected boolean beforeSave(boolean newRecord){
		if (getAD_Org_ID()!=getM_Warehouse().getAD_Org_ID()){
			setAD_Org_ID(getM_Warehouse().getAD_Org_ID());
		}
		return super.beforeSave(newRecord);
		
		
	}
}
