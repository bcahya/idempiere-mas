/**
 * 
 */
package com.unicore.model;
//129 00 1107602 9
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MLocator;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSTrxWeeklyStock extends X_UNS_TrxWeeklyStock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6724868872041173027L;

	/**
	 * @param ctx
	 * @param UNS_TrxWeeklyStock_ID
	 * @param trxName
	 */
	public MUNSTrxWeeklyStock(Properties ctx, int UNS_TrxWeeklyStock_ID,
			String trxName) {
		super(ctx, UNS_TrxWeeklyStock_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSTrxWeeklyStock(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(newRecord)
		{
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			for(MLocator loc : wh.getLocators(true))
			{
				analyzeProduct(loc.get_ID(), getDateFrom(), getDateTo());
				if(value == null)
					continue;
				
				for(int i=0; i < value.length; i++)
				{
					int product_ID = new Integer(value[i]);
					
//					String sql = "SELECT COUNT(*) FROM M_StorageOnHand WHERE M_Locator_ID = ? AND M_Product_ID = ?";
//					int exists = DB.getSQLValue(get_TrxName(), sql, loc.get_ID(), product_ID);
//					if(exists == 0)
//					{
//						sql = "SELECT COUNT(*) FROM M_Transaction WHERE M_Locator_ID = ? AND M_Product_ID = ?";
//						exists = DB.getSQLValue(get_TrxName(), sql, loc.get_ID(), product_ID);
//						if(exists == 0)
//							continue;
//					}
							
					if(MUNSTrxWeeklyStockLine.getByParentProduct(getCtx(), getUNS_TrxWeeklyStock_ID(), product_ID, get_TrxName())
							!= null)
						continue;
					
					MUNSTrxWeeklyStockLine line = new MUNSTrxWeeklyStockLine(getCtx(), 0, get_TrxName());
					line.setUNS_TrxWeeklyStock_ID(get_ID());
					line.setAD_Org_ID(wh.getAD_Org_ID());
					line.setM_Locator_ID(loc.get_ID());
					line.setIsInTransit(loc.isInTransit());
					line.setM_Product_ID(product_ID);
					line.setC_UOM_ID(line.getM_Product().getC_UOM_ID());
					
					String trx = "SELECT COALESCE(SUM(MovementQty),0) FROM M_Transaction WHERE"
							+ " M_Locator_ID=? AND M_Product_ID=? AND Created > ?";
					BigDecimal trxQty = DB.getSQLValueBD(get_TrxName(), trx, 
							new Object[]{loc.get_ID(), product_ID, getDateTo()});
					
					String trxWeek = "SELECT COALESCE(SUM(MovementQty),0) FROM M_Transaction WHERE"
							+ " M_Locator_ID=? AND M_Product_ID=? AND Created BETWEEN ? AND ?";
					BigDecimal trxWeekQty = DB.getSQLValueBD(get_TrxName(), trxWeek, 
							new Object[]{loc.get_ID(), product_ID, getDateFrom(), getDateTo()});
					
					String OnHand = "SELECT COALESCE(SUM(QtyOnHand),0) FROM M_StorageOnHand WHERE M_Product_ID=?"
							+ " AND M_Locator_ID=?";
					BigDecimal qtyOnHand = DB.getSQLValueBD(get_TrxName(), OnHand,
							new Object[]{product_ID, loc.get_ID()});
					
					line.setTransactionQty(trxWeekQty);
					line.setQtyOnHand(qtyOnHand.subtract(trxQty));
					line.setQtyOnDate(line.getQtyOnHand().subtract(trxWeekQty));
					log.log(Level.INFO, "Create :: " + getWeekNo() + " - " + wh.getName() + " - " + loc.getValue() + " - " 
							+ line.getM_Product().getName());
					line.saveEx();
				}				
			}
		}
		
		return true;
	}
	
	public static MUNSTrxWeeklyStock getMaxPeriod(Properties ctx, int M_Warehouse_ID, int C_Year_ID, String trxName)
	{
		String whereClause = " M_Warehouse_ID= ? AND C_Year_ID = ?";
		MUNSTrxWeeklyStock trx = Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(M_Warehouse_ID, C_Year_ID).setOrderBy(" WeekNo DESC").first();
		
		if(trx != null)
			return trx;
		
		return null;
	}
	
	private String values = null;
	private String[] value = null;
	
	private void analyzeProduct(int Locator_ID, Timestamp DateFrom, Timestamp DateTo)
	{
		value = null;
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(DISTINCT(list.ProductID)), ';') FROM (SELECT M_Product_ID AS ProductID FROM M_Transaction"
				+ " WHERE M_Locator_ID = ? AND Created BETWEEN ? AND ? AND MovementQty > 0 "
						+ "UNION ALL"
					+ " SELECT p.M_Product_ID AS ProductID FROM M_Product p WHERE"
					+ " (SELECT COALESCE(SUM(QtyOnHand),0) FROM M_StorageOnHand WHERE"
					+ " M_Locator_ID = ? AND M_Product_ID = p.M_Product_ID) -"
					+ " (SELECT COALESCE(SUM(MovementQty),0) FROM M_Transaction WHERE"
					+ " M_Locator_ID = ? AND M_Product_ID = p.M_Product_ID AND Created > ?) > 0) AS list";
		values = DB.getSQLValueString(get_TrxName(), sql, new Object[]{Locator_ID, DateFrom, DateTo, Locator_ID, Locator_ID, DateTo});
		
		if(!Util.isEmpty(values, true))
			value = values.split(";");
	}
}