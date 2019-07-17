/**
 * 
 */
package com.untacore.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSalesRegion;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author AzHaidar
 *
 */
public class ValidateIntransitStock extends SvrProcess {

	/**
	 * 
	 */
	public ValidateIntransitStock() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
	} //prepare

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{		
		//return recountNotCountedIntransit();
		return validateIntransit();
	} //doIt
	
	
	String recountNotCountedIntransit()
	{
		int IntransitInventory_ID = 1024565;
		
		MInventory inv = new MInventory(getCtx(), getRecord_ID(), get_TrxName());
		
		String description = "**Revisi stock-opname #" + inv.getDocumentNo() + "**";
		MInventory revision = 
				new Query(getCtx(), MInventory.Table_Name, "Description LIKE '%" + description + "%'" , get_TrxName())
				.firstOnly();
		if (revision == null)
		{
			revision = new MInventory(getCtx(), 0, get_TrxName());
			revision.setC_DocType_ID(inv.getC_DocType_ID());
			revision.setClientOrg(inv.getAD_Client_ID(), inv.getAD_Org_ID());
			revision.setM_Warehouse_ID(inv.getM_Warehouse_ID());
			revision.setDescription(description);
			revision.saveEx();
		}
		
		int count = 0;
		
		MInventoryLine[] iLines = inv.getLines(true);
		for (MInventoryLine iline : iLines)
		{
			int M_Locator_ID = iline.getM_Locator_ID();
			int M_Product_ID = iline.getM_Product_ID();
			//int AD_Org_ID = iline.getAD_Org_ID();
			
			String sql = "SELECT il.QtyCount FROM M_InventoryLine il "
					+ "		INNER JOIN M_Locator loc ON il.M_Locator_ID=loc.M_Locator_ID "
					+ "	  WHERE il.M_Inventory_ID=? AND il.M_Product_ID=? "
					+ "		AND loc.M_Warehouse_ID=? AND loc.IsIntransit='Y'";
			
			BigDecimal qtyIntransitCount = 
					DB.getSQLValueBDEx(get_TrxName(), sql, 
							IntransitInventory_ID, M_Product_ID, inv.getM_Warehouse_ID());
			
			if (qtyIntransitCount == null || qtyIntransitCount.signum() == 0)
					continue;
			
			BigDecimal currentQtyBook = 
					MStorageOnHand.getQtyOnHandForLocator(M_Product_ID, M_Locator_ID, 0, get_TrxName());
			
			MWarehouse whs = MWarehouse.get(getCtx(), revision.getM_Warehouse_ID());
			int locIntransitCustomer_ID = whs.getIntransitCustomerLocator_ID(true);
			
			BigDecimal currentIntransit = 
					MStorageOnHand.getQtyOnHandForLocator(M_Product_ID, locIntransitCustomer_ID, 0, get_TrxName());
			
			BigDecimal revCount = currentQtyBook.subtract(qtyIntransitCount);
			
			if (revCount.signum() < 0)
			{
				if (currentIntransit.add(currentQtyBook).compareTo(qtyIntransitCount) < 0)
					continue;
				else {
				//return "Fatal: quantity on hand less than intransit-revision. Line No#" + iline.getLine();
					revCount = currentQtyBook;
				}
			}
			
			String descLine = "**Line revision for line-no#" + iline.getLine() + "**";
			
			MInventoryLine revLine = 
					new Query(getCtx(), MInventoryLine.Table_Name, 
							"M_Inventory_ID=? AND Description LIKE '%" + descLine + "%'", get_TrxName())
					.setParameters(revision.get_ID())
					.firstOnly();
			
			if (revLine == null)
			{
				revLine = new MInventoryLine(revision, M_Locator_ID, M_Product_ID, 
						iline.getM_AttributeSetInstance_ID(), Env.ZERO, revCount);
			}
			
			revLine.setDescription(descLine + " -- " + qtyIntransitCount);
			revLine.setQtyCount(revCount);
			if (!revLine.save())
				return "error while saving new inventory-line";
			
			count++;
		}
		
		return "Completed #" + count + " lines processed.";
	} //recountNotCountedIntransit
	
	String validateIntransit()
	{
		MInventory inv = new MInventory(getCtx(), getRecord_ID(), get_TrxName());
		
		MOrg org = MOrg.get(getCtx(), inv.getAD_Org_ID());
		MSalesRegion region = MSalesRegion.get(getCtx(), org.getC_SalesRegion_ID());
		String regionValue = region.getValue();
		
		Hashtable<String, MInventoryLine> StockOfUnprocessedPL = new Hashtable<String, MInventoryLine>();
		
		String sql = "SELECT p.M_Product_ID, p.Value, p.Name, whs.Value, whs.M_Warehouse_ID, SUM(pll.MovementQty)" 
				+ " FROM UNS_PackingList_Line pll " 
				+ " INNER JOIN UNS_PackingList_Order plo ON pll.UNS_PackingList_Order_ID=plo.UNS_PackingList_Order_ID "
				+ " INNER JOIN UNS_PackingList pl ON pl.UNS_PackingList_ID=plo.UNS_PackingList_ID "
				+ " INNER JOIN M_Locator loc ON loc.M_Locator_ID=pll.M_Locator_ID "
				+ " INNER JOIN M_Warehouse whs ON whs.M_Warehouse_ID=loc.M_Warehouse_ID "
				+ " INNER JOIN M_Product p ON pll.M_Product_ID=p.M_Product_ID "
				+ " WHERE pl.DocStatus IN ('CO', 'CL') "
				+ "			AND whs.Name LIKE '%" + regionValue + "%' AND pl.DocStatus IN ('CO', 'CL') "
				+ "			AND EXISTS (SELECT 1 FROM UNS_PL_Return plr "
				+ "					WHERE plr.UNS_PackingList_ID=pl.UNS_PackingList_ID "
				+ "					AND plr.DocStatus IN ('DR', 'IP', 'IN')) "
				+ " GROUP BY p.M_Product_ID, p.Value, p.Name, whs.Value, whs.M_Warehouse_ID";
		
		int count= 0;
		
		PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int M_Product_ID = rs.getInt(1);
				int M_Warehouse_ID = rs.getInt(5);
				BigDecimal totalPLMovementQty = rs.getBigDecimal(6);
				
				int M_Locator_ID = MWarehouse.get(getCtx(), M_Warehouse_ID).getIntransitCustomerLocator_ID(true);
				
				MInventoryLine iLine = 
						new Query(getCtx(), MInventoryLine.Table_Name, 
								"M_Inventory_ID=? AND M_Locator_ID=? AND M_Product_ID=?", get_TrxName())
						.setParameters(inv.get_ID(), M_Locator_ID, M_Product_ID)
						.firstOnly();
				
				if (iLine == null) {
					iLine = new MInventoryLine(inv, M_Locator_ID, M_Product_ID, 0, Env.ZERO, totalPLMovementQty);
				}
				
				iLine.setQtyCount(totalPLMovementQty);
				iLine.saveEx();
				
				String key = M_Locator_ID + "_" + M_Product_ID;
				MInventoryLine duplicateLine = StockOfUnprocessedPL.get(key);
				if (duplicateLine != null)
				{
					return "Error duplicate results for product of " + MProduct.get(getCtx(), M_Product_ID) + 
							" at locator " + MLocator.get(getCtx(), M_Locator_ID);
				}
				else {
					StockOfUnprocessedPL.put(key, iLine);
				}
				count++;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT soh.M_Product_ID, soh.M_Locator_ID, SUM(soh.QtyOnHand) FROM M_StorageOnHand soh "
				+ " INNER JOIN M_Locator loc ON loc.M_Locator_ID=soh.M_Locator_ID "
				+ " INNER JOIN M_Warehouse whs ON whs.M_Warehouse_ID=loc.M_Warehouse_ID "
				+ " WHERE loc.IsIntransit='Y' AND loc.Value LIKE '%Customer%' "
				+ "		AND whs.Name LIKE '%" + regionValue + "%' "
				+ " GROUP By soh.M_Product_ID, soh.M_Locator_ID";
		
		stmt = DB.prepareStatement(sql, get_TrxName());
		rs = null;
		try {
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int M_Product_ID = rs.getInt(1);
				int M_Locator_ID = rs.getInt(2);
				BigDecimal totalIntransitQty = rs.getBigDecimal(3);
				
				if (totalIntransitQty.signum() == 0)
					continue;
				
				String key = M_Locator_ID + "_" + M_Product_ID;
				MInventoryLine iLine = StockOfUnprocessedPL.get(key);
				
				if (iLine != null)
					continue;
				
				iLine = new Query(
								getCtx(), MInventoryLine.Table_Name, 
								"M_Inventory_ID=? AND M_Locator_ID=? AND M_Product_ID=?", get_TrxName())
						.setParameters(inv.get_ID(), M_Locator_ID, M_Product_ID)
						.firstOnly();
				
				BigDecimal qtyBook = MStorageOnHand.getQtyOnHandForLocator(M_Product_ID, M_Locator_ID, 0, get_TrxName());
				
				if (iLine == null)
				{
					iLine = new MInventoryLine(inv, M_Locator_ID, M_Product_ID, 0, totalIntransitQty, Env.ZERO);
				}
//				else {
//					BigDecimal diffKoma = iLine.getQtyBook().subtract(qtyBook);
//					if (diffKoma.compareTo(BigDecimal.valueOf(0.99)) <= 0)
//						qtyBook = qtyBook.add(diffKoma);
//				}
				
				iLine.setQtyCount(Env.ZERO);
				iLine.setQtyBook(qtyBook);
				iLine.saveEx();
				count++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			rs = null;
			stmt = null;
		}
		
		return "Completed. #" + count + " of lines processed.";
	} //validateIntransit
	
} //ValidateIntransitStock
