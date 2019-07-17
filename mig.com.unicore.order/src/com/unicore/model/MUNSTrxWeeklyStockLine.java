/**
 * 
 */
package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.compiere.model.MBPartnerProduct;
import org.compiere.util.DB;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSTrxWeeklyStockLine extends X_UNS_TrxWeeklyStock_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8196711981087640214L;

	/**
	 * @param ctx
	 * @param UNS_TrxWeeklyStock_Line_ID
	 * @param trxName
	 */
	public MUNSTrxWeeklyStockLine(Properties ctx,
			int UNS_TrxWeeklyStock_Line_ID, String trxName) {
		super(ctx, UNS_TrxWeeklyStock_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSTrxWeeklyStockLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		MBPartnerProduct[] partner = MBPartnerProduct.getOfProduct(getCtx(), getM_Product_ID(), get_TrxName());
		if(partner.length > 0)
			setC_BPartner_ID(partner[0].getC_BPartner_ID());
		
		return true;
	}
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(newRecord)
		{			
			String sql = "SELECT SUM(MovementQty), MovementType, MovementDate,"
					+ " M_InventoryLine_ID, M_MovementLine_ID, M_InOutLine_ID,"
					+ " UNS_Production_Detail_ID, UNS_PL_ConfirmProduct_ID, AD_Org_ID"
					+ " FROM M_Transaction"
					+ " WHERE M_Locator_ID=? AND M_Product_ID=? AND (Created BETWEEN ? AND ?)"
					+ " GROUP BY MovementType, MovementDate, M_AttributeSetInstance_ID, M_Locator_ID, M_Product_ID,"
					+ " M_InventoryLine_ID, M_MovementLine_ID, M_InOutLine_ID,"
					+ " UNS_Production_Detail_ID, UNS_PL_ConfirmProduct_ID, AD_Org_ID";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = DB.prepareStatement(sql, get_TrxName());
				ps.setInt(1, getM_Locator_ID());
				ps.setInt(2, getM_Product_ID());
				ps.setTimestamp(3, getParent().getDateFrom());
				ps.setTimestamp(4, getParent().getDateTo());
				rs = ps.executeQuery();
				while (rs.next())
				{	
					MUNSTrxWeeklyStockDetail detail = new MUNSTrxWeeklyStockDetail(getCtx(), 0, get_TrxName());
					detail.setUNS_TrxWeeklyStock_Line_ID(get_ID());
					detail.setAD_Org_ID(rs.getInt(9));
					detail.setMovementQty(rs.getBigDecimal(1));
					detail.setMovementType(rs.getString(2));
					detail.setMovementDate(rs.getTimestamp(3));
					detail.setM_InventoryLine_ID(rs.getInt(4));
					detail.setM_MovementLine_ID(rs.getInt(5));
					detail.setM_InOutLine_ID(rs.getInt(6));
					detail.setUNS_Production_Detail_ID(rs.getInt(7));
					detail.setUNS_PL_ConfirmProduct_ID(rs.getInt(8));
					detail.saveEx();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
//			finally {
//				DB.close(rs);
//			}
		}
		
		return true;
	}

	public static MUNSTrxWeeklyStockLine getByParentProduct(Properties ctx, int Parent_ID, int Product_ID, String trxName)
	{
		String whereClause = "UNS_TrxWeeklyStock_ID=? AND M_Product_ID=?";
		MUNSTrxWeeklyStockLine line = Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name,
					whereClause, trxName).setParameters(Parent_ID, Product_ID).first();
		
		if(null!=line)
			return line;
		
		return null;
	}
	
	public static MUNSTrxWeeklyStockLine getPreviousTrx(Properties ctx, int weekNo, int Locator_ID, int Product_ID)
	{
		
		return null;
	}
	
	public MUNSTrxWeeklyStock m_parent = null;
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MUNSTrxWeeklyStock getParent()
	{
		if (m_parent == null)
			m_parent = new MUNSTrxWeeklyStock(getCtx(), getUNS_TrxWeeklyStock_ID(), get_TrxName());
		
		return m_parent;
	}	//	getParent
}
