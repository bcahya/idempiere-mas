/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Util;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UntaSoft
 * 
 */
public class MUNSPLReturnOrder extends X_UNS_PL_ReturnOrder
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3450908208905121437L;

	/**
	 * @param ctx
	 * @param UNS_PL_ReturnOrder_ID
	 * @param trxName
	 */
	public MUNSPLReturnOrder(Properties ctx, int UNS_PL_ReturnOrder_ID, String trxName)
	{
		super(ctx, UNS_PL_ReturnOrder_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLReturnOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSPLReturnLine[] getLines()
	{
		return getLines(null, null);
	}
	
	/**
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @return
	 */
	public MUNSPLReturnLine[] getLines(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLReturnOrder.COLUMNNAME_UNS_PL_ReturnOrder_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (Util.isEmpty(orderClause, true))
			orderClause = MUNSPLReturnLine.COLUMNNAME_UNS_PL_ReturnLine_ID;
		//
		List<MUNSPLReturnLine> list = Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLReturnLine.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPLReturnLine[list.size()]);
	} // getLines

	public static BigDecimal getQtyProduct(int m_Product_ID, int UNS_PackingList_Order_ID,
			String trxName)
	{
		StringBuilder sql =
				new StringBuilder("SELECT ").append(MUNSPackingListLine.COLUMNNAME_MovementQty)
						.append(" FROM ").append(MUNSPackingListLine.Table_Name).append(" WHERE ")
						.append(MUNSPackingListLine.COLUMNNAME_M_Product_ID).append("=").append(m_Product_ID)
						.append(" AND ").append(MUNSPackingListLine.COLUMNNAME_UNS_PackingList_Order_ID)
						.append("=").append(UNS_PackingList_Order_ID);

		return new BigDecimal(DB.getSQLValue(trxName, sql.toString()));
	}
	
	public void setPLOrder(MUNSPLReturn plReturn)
	{
		setClientOrg(plReturn);

		setUNS_PL_Return_ID(plReturn.get_ID());
		setIsCancelled(false);
	}
	
}
