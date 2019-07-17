/**
 * UntaCore Customization
 * @Untasoft www.untasoft.com
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
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.MUNSPackingListOrder;

public class CreateFromPackingListOrder extends CreateFrom {

	/**  Loaded Invoice             */
	private MInvoice		m_invoice = null;
	private int defaultLocator_ID=0;
	
	public CreateFromPackingListOrder(GridTab gridTab) {
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
		setTitle(Msg.getElement(Env.getCtx(), "UNS_PackingList_Order_ID", false) 
				+ " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		int M_Locator_ID = defaultLocator_ID;
		if (M_Locator_ID == 0) {
			return false;
		}
		// Get Shipment
		int UNS_PackingListOrder_ID = ((Integer) getGridTab().getValue("UNS_PackingList_Order_ID")).intValue();
		MUNSPackingListOrder orderList = new MUNSPackingListOrder(Env.getCtx(), UNS_PackingListOrder_ID, trxName);
		if (log.isLoggable(Level.CONFIG)) log.config(orderList + ", M_Locator_ID=" + M_Locator_ID);

		/**
		 *  Selected        - 0
		 *  Qty             - 1
		 *  C_UOM_ID        - 2
		 *  M_Locator_ID    - 3
		 *  M_Product_ID    - 4
		 *  OrderLine       - 5
		 *  InvoiceLine     - 6
		 */
		// Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue()) {
				// variable values
				BigDecimal QtyEntered = (BigDecimal) miniTable.getValueAt(i, 1); // Qty
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 2); // UOM
				int C_UOM_ID = pp.getKey();
				pp = (KeyNamePair) miniTable.getValueAt(i, 3); // Locator
				// If a locator is specified on the product, choose that otherwise default locator
				M_Locator_ID = pp!=null && pp.getKey()!=0 ? pp.getKey() : defaultLocator_ID;

				pp = (KeyNamePair) miniTable.getValueAt(i, 4); // Product
				int M_Product_ID = pp.getKey();
				int C_OrderLine_ID = 0;
				pp = (KeyNamePair) miniTable.getValueAt(i, 5); // OrderLine
				if (pp != null)
					C_OrderLine_ID = pp.getKey();
				int C_InvoiceLine_ID = 0;
				MInvoiceLine il = null;
				pp = (KeyNamePair) miniTable.getValueAt(i, 6); // InvoiceLine
				if (pp != null)
					C_InvoiceLine_ID = pp.getKey();
				if (C_InvoiceLine_ID != 0)
					il = new MInvoiceLine (Env.getCtx(), C_InvoiceLine_ID, trxName);
		
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
						+ ", OrderLine=" + C_OrderLine_ID + ", InvoiceLine=" + C_InvoiceLine_ID);

				//	Credit Memo - negative Qty
				if (m_invoice != null && m_invoice.isCreditMemo() )
					QtyEntered = QtyEntered.negate();

				//	Create new InOut Line
				MUNSPackingListLine pll = new MUNSPackingListLine(Env.getCtx(), 0, trxName);
				pll.setUNS_PackingList_Order_ID(orderList.get_ID());
				pll.setM_Product_ID(M_Product_ID);	//	Line UOM
				pll.setQty(QtyEntered);
				pll.setC_UOM_ID(C_UOM_ID);//	Movement/Entered
				//
				MOrderLine ol = null;
				if (C_OrderLine_ID != 0)
				{
					pll.setC_OrderLine_ID(C_OrderLine_ID);
					ol = new MOrderLine (Env.getCtx(), C_OrderLine_ID, trxName);
					if (ol.getQtyEntered().compareTo(ol.getQtyOrdered()) != 0)
					{
						pll.setMovementQty(QtyEntered
								.multiply(ol.getQtyOrdered())
								.divide(ol.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP));
						pll.setC_UOM_ID(ol.getC_UOM_ID());
					}
					pll.setDescription(ol.getDescription());
					orderList.setC_Order_ID(ol.getC_Order_ID());
				}
				else if (il != null)
				{
					if (il.getQtyEntered().compareTo(il.getQtyInvoiced()) != 0)
					{
						pll.setQtyEntered(QtyEntered
								.multiply(il.getQtyInvoiced())
								.divide(il.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP));
						pll.setC_UOM_ID(il.getC_UOM_ID());
					}
					orderList.setC_Invoice_ID(il.getC_Invoice_ID());
				}
				// Set locator
				pll.setM_Locator_ID(M_Locator_ID);
				pll.setAD_Org_ID(orderList.getAD_Org_ID());
				pll.saveEx();
				orderList.saveEx();
			}   //   if selected
		}   //  for all rows
		return true;	
	}
	
	/**
	 * Load PBartner dependent Order/Invoice/Shipment Field.
	 * @param C_BPartner_ID
	 */
	protected ArrayList<KeyNamePair> loadInvoiceData (int C_BPartner_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		
		StringBuffer display = new StringBuffer("i.DocumentNo||' - '||")
		.append(DB.TO_CHAR("DateInvoiced", DisplayType.Date, Env.getAD_Language(Env.getCtx())))
		.append("|| ' - ' ||")
		.append(DB.TO_CHAR("GrandTotal", DisplayType.Amount, Env.getAD_Language(Env.getCtx())));
		//
		StringBuffer sql = new StringBuffer("SELECT i.C_Invoice_ID,").append(display)
		.append(" FROM C_Invoice i "
				+ "WHERE i.C_BPartner_ID=? AND i.IsSOTrx=? AND i.DocStatus IN ('CL','CO')"
				+ " AND i.C_Invoice_ID IN "
				+ "(SELECT il.C_Invoice_ID FROM C_InvoiceLine il"
				+ " LEFT OUTER JOIN M_MatchInv mi ON (il.C_InvoiceLine_ID=mi.C_InvoiceLine_ID) "
				+ " JOIN C_Invoice i2 ON (il.C_Invoice_ID = i2.C_Invoice_ID) "
				+ " WHERE i2.C_BPartner_ID=? AND i2.IsSOTrx=? AND i2.DocStatus IN ('CL','CO') "
				+ "GROUP BY il.C_Invoice_ID,mi.C_InvoiceLine_ID,il.QtyInvoiced "
				+ "HAVING (il.QtyInvoiced<>SUM(mi.Qty) AND mi.C_InvoiceLine_ID IS NOT NULL)"
				+ " OR mi.C_InvoiceLine_ID IS NULL) "
				+ "ORDER BY i.DateInvoiced");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_BPartner_ID);
			pstmt.setString(2, isSOTrx ? "Y" : "N");
			pstmt.setInt(3, C_BPartner_ID);
			pstmt.setString(4, isSOTrx ? "Y" : "N");
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
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 */
	protected Vector<Vector<Object>> getOrderData (int C_Order_ID, boolean forInvoice)
	{
		/**
		 *  Selected        - 0
		 *  Qty             - 1
		 *  C_UOM_ID        - 2
		 *  M_Locator_ID    - 3
		 *  M_Product_ID    - 4
		 *  OrderLine       - 5
		 *  InvoiceLine     - 6
		 */
		if (log.isLoggable(Level.CONFIG)) log.config("C_Order_ID=" + C_Order_ID);
		p_order = new MOrder (Env.getCtx(), C_Order_ID, null);      //  save

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT "
				+ "l.QtyOrdered-COALESCE(l.QtyPacked,0),"					//	1
				+ "CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END,"	//	2
				+ " l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),"			//	3..4
				+ " p.M_Locator_ID, loc.Value, " // 5..6
				+ " COALESCE(l.M_Product_ID,0),COALESCE(p.Name,c.Name), " //	7..8
				+ " l.C_OrderLine_ID,l.Line "	//	9..10
				+ "	FROM C_OrderLine l"
				+ " LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)"
				+ " LEFT OUTER JOIN M_Locator loc on (p.M_Locator_ID=loc.M_Locator_ID)"
				+ " LEFT OUTER JOIN C_Charge c ON (l.C_Charge_ID=c.C_Charge_ID)");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
			.append(Env.getAD_Language(Env.getCtx())).append("')");
		//
		sql.append(" WHERE l.C_Order_ID=? AND l.QtyOrdered > l.QtyPacked "			//	#1
				+ "GROUP BY l.QtyOrdered,CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END, "
				+ "l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name), p.M_Locator_ID, loc.Value, "
				+ "l.M_Product_ID,COALESCE(p.Name,c.Name), l.Line,l.C_OrderLine_ID "
				+ "ORDER BY l.Line");
		//
		if (log.isLoggable(Level.FINER)) log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));           //  0-Selection
				BigDecimal qtyOrdered = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyOrdered.multiply(multiplier);
				line.add(qtyEntered);  //  1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp);                           //  2-UOM
				// Add locator
				line.add(getLocatorKeyNamePair(rs.getInt(5)));// 3-Locator
				// Add product
				pp = new KeyNamePair(rs.getInt(7), rs.getString(8));
				line.add(pp);                           //  4-Product
				pp = new KeyNamePair(rs.getInt(9), rs.getString(10));
				line.add(pp);                           //  6-OrderLine
				line.add(null);                         //  7-Invoice
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
	 * Load Invoice details
	 * @param C_Invoice_ID Invoice
	 */
	protected Vector<Vector<Object>> getInvoiceData(int C_Invoice_ID)
	{
		m_invoice = new MInvoice(Env.getCtx(), C_Invoice_ID, null); // save
		p_order = null;
		m_rma = null;
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT " // Entered UOM
				+ "l.QtyInvoiced-COALESCE(SUM(iol.movementqty), 0),l.QtyEntered/l.QtyInvoiced,"
				+ " l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name)," // 3..4
				+ " p.M_Locator_ID, loc.Value, " // 5..6
				+ " l.M_Product_ID,p.Name, l.C_InvoiceLine_ID,l.Line," // 7..10
				+ " l.C_OrderLine_ID, ol.line " // 11..12
				+ " FROM C_InvoiceLine l "); 
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
			.append(Env.getAD_Language(Env.getCtx())).append("')");

		sql.append(" LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)")
		.append(" LEFT OUTER JOIN M_Locator loc on (p.M_Locator_ID=loc.M_Locator_ID)")
		.append(" INNER JOIN C_Invoice inv ON (l.C_Invoice_ID=inv.C_Invoice_ID)")
		.append(" LEFT OUTER JOIN M_InOut io ON io.C_Invoice_ID = inv.C_Invoice_ID ")
		.append(" LEFT OUTER JOIN M_InOutLine iol ON iol.M_Inout_ID = io.M_InOut_ID AND iol.M_Product_ID = l.M_Product_ID")
		.append(" LEFT OUTER JOIN C_OrderLine ol on ol.C_OrderLine_ID = l.C_OrderLine_ID ")
		.append(" WHERE l.C_Invoice_ID=? AND l.QtyInvoiced<>0 ")
		.append("GROUP BY l.QtyInvoiced,l.QtyEntered/l.QtyInvoiced,"
				+ "l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),"
				+ "p.M_Locator_ID, loc.Value, ol.line, "
				+ "l.M_Product_ID,p.Name, l.C_InvoiceLine_ID,l.Line,l.C_OrderLine_ID ")
				.append("ORDER BY l.Line");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(7);
				line.add(new Boolean(false)); // 0-Selection
				BigDecimal qtyInvoiced = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyInvoiced.multiply(multiplier);
				line.add(qtyEntered); // 1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp); // 2-UOM
				// Add locator
				line.add(getLocatorKeyNamePair(rs.getInt(5))); // 3-Locator
				pp = new KeyNamePair(rs.getInt(7), rs.getString(8));
				line.add(pp); // 4-Product
				int C_OrderLine_ID = rs.getInt(11);
				if (rs.wasNull())
					line.add(null); // 6-Order
				else
					line.add(new KeyNamePair(C_OrderLine_ID, rs.getString(12)));
				pp = new KeyNamePair(rs.getInt(9), rs.getString(10));
				line.add(pp); // 7-Invoice Line
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			//throw new DBException(e, sql);
		}
	    finally
	    {
	    	DB.close(rs, pstmt);
	    	rs = null; pstmt = null;
	    }
		return data;
	}
	
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
		Vector<String> columnNames = new Vector<String>(7);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
	    columnNames.add(Msg.translate(Env.getCtx(), "C_UOM_ID"));
	    columnNames.add(Msg.translate(Env.getCtx(), "M_Locator_ID"));
	    columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
	    columnNames.add(Msg.getElement(Env.getCtx(), "C_OrderLine_ID", false));
	    columnNames.add(Msg.getElement(Env.getCtx(), "C_InvoiceLine_ID", false));
	    
	    return columnNames;	
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);     //  Selection
		miniTable.setColumnClass(1, BigDecimal.class, false);      //  Qty
		miniTable.setColumnClass(2, String.class, true);          //  UOM
		miniTable.setColumnClass(3, String.class, false);  //  Locator
		miniTable.setColumnClass(4, String.class, true);   //  Product
		miniTable.setColumnClass(5, String.class, true);     //  Order
		miniTable.setColumnClass(6, String.class, true);   //  Invoice
		
		//  Table UI
		miniTable.autoSize();
		
	}
	
	protected Vector<Vector<Object>> getOrderData (int C_Order_ID, boolean forInvoice, int M_Locator_ID)
	{
		defaultLocator_ID = M_Locator_ID;
		return getOrderData (C_Order_ID, forInvoice);
	}
	
	protected Vector<Vector<Object>> getInvoiceData (int C_Invoice_ID, int M_Locator_ID)
	{
		defaultLocator_ID = M_Locator_ID;
		return getInvoiceData (C_Invoice_ID);
	}
}
