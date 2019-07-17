/**
 * 
 */
package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLocator;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import com.unicore.base.model.MOrder;

/**
 * @author ALBURHANY
 *
 */
public class CreateFromOrder extends CreateFrom {

	private int defaultLocator_ID=0;
	
	/** Loaded Order            */
	protected MRequisition p_requisition = null;
	
	/**
	 * 
	 */
	public CreateFromOrder(GridTab gridTab) {
		super(gridTab);
		if (log.isLoggable(Level.INFO)) log.info(gridTab.toString());
	}

	@Override
	public Object getWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_Order", false) 
				+ " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return false;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName)
	{	
		int M_Locator_ID = 0;

		// Get Movement
		int C_Order_ID = ((Integer) getGridTab().getValue("C_Order_ID")).intValue();
		MOrder order = new MOrder(Env.getCtx(), C_Order_ID, trxName);
		if (log.isLoggable(Level.CONFIG)) log.config(order + ", M_Locator_ID=" + M_Locator_ID);

		/**
		 *  Selected        - 0
		 *  Qty             - 1
		 *  C_UOM_ID        - 2
		 *  M_Locator_ID    - 3
		 *  M_Product_ID    - 4
		 *  RequisitionLine - 5
		 */
		
		// Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue()) {
				// variable values
				BigDecimal QtyEntered = (BigDecimal) miniTable.getValueAt(i, 1); // Qty
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 3); // UOM
				int C_UOM_ID = pp.getKey();
				pp = (KeyNamePair) miniTable.getValueAt(i, 4); // Product
				int M_Product_ID = pp.getKey();
//				pp = (KeyNamePair) miniTable.getValueAt(i, 5); // Locator
//				M_Locator_ID = pp.getKey();
				int M_RequisitionLine_ID = 0;
				pp = (KeyNamePair) miniTable.getValueAt(i, 5); // ReqLine
				if (pp != null)
					M_RequisitionLine_ID = pp.getKey();
		
				int precision = 2;
				if (M_Product_ID != 0)
				{
					MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
					precision = product.getUOMPrecision();
				}
				QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
				//
				if (log.isLoggable(Level.FINE)) log.fine("Line QtyEntered=" + QtyEntered
						+ ", Product=" + M_Product_ID 
						+ ", RequisitionLine=" + M_RequisitionLine_ID);

				//	Create new Order Line
				MOrderLine orderLine = new MOrderLine(Env.getCtx(), 0, null);
				orderLine.setAD_Org_ID(order.getAD_Org_ID());
				orderLine.setC_Order_ID(order.get_ID());
				orderLine.setM_Product_ID(M_Product_ID);	//	Line UOM
				orderLine.setC_UOM_ID(C_UOM_ID);
				orderLine.setQtyEntered(QtyEntered);
				orderLine.set_ValueOfColumn("M_RequisitionLine_ID", M_RequisitionLine_ID);
				orderLine.saveEx();
				
				MRequisitionLine reqLine = new MRequisitionLine(Env.getCtx(), M_RequisitionLine_ID, orderLine.get_TrxName());
				reqLine.setQtyOrdered(reqLine.getQtyOrdered().add(QtyEntered));
				reqLine.saveEx();
			}   //   if selected
		}   //  for all rows

		return true;
	}
	
	protected ArrayList<KeyNamePair> loadRequisitionData (int M_Warehouse_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		
		StringBuffer display = new StringBuffer("r.DocumentNo||' - '||")
		.append(DB.TO_CHAR("r.DateRequired", DisplayType.Date, Env.getAD_Language(Env.getCtx())))
		.append("|| ' - ' ||")
		.append(DB.TO_CHAR("us.Name", DisplayType.String, Env.getAD_Language(Env.getCtx())));
		//
		StringBuffer sql = new StringBuffer("SELECT r.M_Requisition_ID, ").append(display)
		.append(" FROM M_Requisition r "
				+ " INNER JOIN AD_User us ON us.AD_User_ID = r.AD_User_ID"
				+ " WHERE r.M_Warehouse_ID=? AND r.DocStatus = 'CO'"
				+ " AND r.AD_Org_ID = ?"
				+ " AND r.M_Requisition_ID IN (SELECT M_Requisition_ID FROM M_RequisitionLine WHERE"
				+ " Qty > (COALESCE(MovementQty,0) + COALESCE(QtyOrdered,0)))");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setInt(2, ((Integer) getGridTab().getValue("AD_Org_ID")).intValue());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return list;
	}
	
	/**
	 *  Load Data - Order
	 *  @param M_Requisition_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 */
	protected Vector<Vector<Object>> getRequisitionData (int M_Requisition_ID, int DestinationWarehouse_ID)
	{
//		MLocator loc = new MLocator(Env.getCtx(), M_Locator_ID, null);
//		if(M_Locator_ID > 0)
			
		
		/**
		 *  Selected        - 0
		 *  Quantity        - 1
		 *  AvalibaleStock	- 2
		 *  C_UOM_ID        - 3
		 *  M_Product_ID    - 4
		 *  Locator			- 5
		 *  ReqLine	        - 6
		 */
		if (log.isLoggable(Level.CONFIG)) log.config("M_Requisition_ID=" + M_Requisition_ID);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT "
				+ " rl.Qty- COALESCE(rl.QtyOrdered,0) - COALESCE(rl.MovementQty,0),"	//	1
				+ " rl.C_UOM_ID, COALESCE(uom.UOMSymbol,uom.Name),"			//	2..3
				+ " rl.M_Product_ID, p.Name, " //	4..5
				+ " rl.M_RequisitionLine_ID,rl.Line "	//	6..7
				+ "	FROM M_RequisitionLine rl"
				+ " INNER JOIN M_Requisition r ON r.M_Requisition_ID = rl.M_Requisition_ID"
				+ " INNER JOIN M_Product p ON rl.M_Product_ID=p.M_Product_ID");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (rl.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (rl.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
			.append(Env.getAD_Language(Env.getCtx())).append("')");
		sql.append(" WHERE (rl.Qty- COALESCE(rl.QtyOrdered,0) - COALESCE(rl.MovementQty,0)) > 0")
		.append(" AND r.AD_Org_ID=?");
		if(M_Requisition_ID > 0)
			sql.append(" AND r.M_Requisition_ID=?");
		
		//
		if (log.isLoggable(Level.FINER)) log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			MWarehouse wh = new MWarehouse(Env.getCtx(), DestinationWarehouse_ID, null);
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, wh.getAD_Org_ID());
			if(M_Requisition_ID > 0) pstmt.setInt(2, M_Requisition_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				BigDecimal qtyOnHand = Env.ZERO;
				qtyOnHand = MStorageOnHand.getQtyOnHand(rs.getInt(4), wh.get_ID(), 0, wh.get_TrxName());
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));           //  0-Selection
				BigDecimal qtyOrdered = rs.getBigDecimal(1);
				line.add(qtyOrdered);  //  1-Quantity
				line.add(qtyOnHand); // 2-avaliableStock
				KeyNamePair pp = new KeyNamePair(rs.getInt(2), rs.getString(3).trim());
				line.add(pp);                           //  3-UOM
				// Add product
				pp = new KeyNamePair(rs.getInt(4), rs.getString(5));
				line.add(pp);                           //  4-Product
				pp = new KeyNamePair(rs.getInt(6), rs.getString(7));
				line.add(pp);                           //  6-RequisitionLine
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			//throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return data;
	}   //  LoadOrder
		
	/**
	 * Get KeyNamePair for Locator.
	 * If no locator specified or the specified locator is not valid (e.g. warehouse not match),
	 * a default one will be used.
	 * @param M_Locator_ID
	 * @return KeyNamePair
	 */
	protected KeyNamePair getLocatorKeyNamePair(int M_Locator_ID)
	{
		MLocator locator = null;
		
		// Load desired Locator
		if (M_Locator_ID > 0)
		{
			locator = MLocator.get(Env.getCtx(), M_Locator_ID);
			// Validate warehouse
			if (locator != null && locator.getM_Warehouse_ID() != getM_Warehouse_ID())
			{
				locator = null;
			}
		}
		
		// Try to use default locator from Order Warehouse
		if (locator == null && p_order != null && p_order.getM_Warehouse_ID() == getM_Warehouse_ID())
		{
			MWarehouse wh = MWarehouse.get(Env.getCtx(), p_order.getM_Warehouse_ID());
			if (wh != null)
			{
				locator = wh.getDefaultLocator();
			}
		}
		// Try to get from locator field
		if (locator == null)
		{
			if (defaultLocator_ID > 0)
			{
				locator = MLocator.get(Env.getCtx(), defaultLocator_ID);
			}
		}
		// Validate Warehouse
		if (locator == null || locator.getM_Warehouse_ID() != getM_Warehouse_ID())
		{
			locator = MWarehouse.get(Env.getCtx(), getM_Warehouse_ID()).getDefaultLocator();
		}
		
		KeyNamePair pp = null ;
		if (locator != null)
		{
			pp = new KeyNamePair(locator.get_ID(), locator.getValue());
		}
		return pp;
	}
	
	protected Vector<String> getOISColumnNames()
	{
		Vector<String> columnNames = new Vector<String>(6);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Stock"));
	    columnNames.add(Msg.translate(Env.getCtx(), "C_UOM_ID"));
	    columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
	    columnNames.add(Msg.getElement(Env.getCtx(), "M_RequisitionLine_ID", false));
	    
	    return columnNames;
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);     //  Selection
		miniTable.setColumnClass(1, BigDecimal.class, false);      //  Qty
		miniTable.setColumnClass(2, BigDecimal.class, true);	// AvaliableStock
		miniTable.setColumnClass(3, String.class, true);          //  UOM
		miniTable.setColumnClass(4, String.class, true);   //  Product
		miniTable.setColumnClass(5, String.class, true);     //  Order
		
		//  Table UI
		miniTable.autoSize();
	}
}