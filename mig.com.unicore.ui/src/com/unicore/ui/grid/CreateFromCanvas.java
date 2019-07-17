/**
 * 
 */
package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import com.unicore.model.MUNSCvsRptCustomer;
import com.unicore.model.MUNSCvsRptProduct;
import com.uns.model.MProduct;

/**
 * @author Burhani Adam
 *
 */
public class CreateFromCanvas extends CreateFrom {
	
	/**
	 * @param gridTab
	 */
	public CreateFromCanvas(GridTab gridTab) {
		super(gridTab);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.ICreateFrom#getWindow()
	 */
	@Override
	public Object getWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info(org.compiere.minigrid.IMiniTable, org.compiere.apps.IStatusBar)
	 */
	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName)
	{
		int UNS_Cvs_RptCustomer_ID = ((Integer) getGridTab().getValue("UNS_Cvs_RptCustomer_ID")).intValue();
		MUNSCvsRptCustomer customer = new MUNSCvsRptCustomer(Env.getCtx(), UNS_Cvs_RptCustomer_ID, trxName);
		
		int M_Locator_ID = DB.getSQLValue(
				null, "SELECT M_Locator_ID FROM UNS_BP_Canvasser WHERE C_BPartner_ID = ?"
				, customer.getUNS_Cvs_Rpt().getC_BPartner_ID());
		
		/**
		 *  Selected		- 0
		 *  QtySold L1		- 1
		 *  QtySold L2		- 2
		 *  QtySold L3		- 3
		 *  QtySold L4		- 4
		 *  QtyAvalibale	- 5
		 *  PriceActual		- 6
		 *  Product			- 7
		 *  Total			- 8
		 */
		
		// Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				// variable values
				BigDecimal qtyL1 = (BigDecimal) miniTable.getValueAt(i, 1); // QtyL1
				BigDecimal qtyL2 = (BigDecimal) miniTable.getValueAt(i, 2); // QtyL2
				BigDecimal qtyL3 = (BigDecimal) miniTable.getValueAt(i, 3); // QtyL3
				BigDecimal qtyL4 = (BigDecimal) miniTable.getValueAt(i, 4); // QtyL4
				BigDecimal price = (BigDecimal) miniTable.getValueAt(i, 6); // Price
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 7); // Product
				int M_Product_ID = pp.getKey();
				
				//	Create New OR Check Existing Canvas Customer Product
				MUNSCvsRptProduct rp = null;
				
				int idCstProduct = DB.getSQLValue(
						trxName, "SELECT UNS_Cvs_RptProduct_ID FROM UNS_Cvs_RptProduct WHERE"
						+ " UNS_Cvs_RptCustomer_ID=? AND M_Product_ID=?", customer.get_ID(), M_Product_ID);
				MProduct p = new MProduct(Env.getCtx(), M_Product_ID, trxName);
				if(idCstProduct > 0)
				{
					rp = new MUNSCvsRptProduct(Env.getCtx(), idCstProduct, trxName);
					rp.setUOMQtySoldL1(rp.getUOMQtySoldL1().add(qtyL1));
					rp.setUOMQtySoldL2(rp.getUOMQtySoldL2().add(qtyL2));
					rp.setUOMQtySoldL3(rp.getUOMQtySoldL3().add(qtyL3));
					rp.setUOMQtySoldL4(rp.getUOMQtySoldL4().add(qtyL4));
					rp.setisManual(false);
				}
				else
				{
					rp = new MUNSCvsRptProduct(Env.getCtx(), 0, trxName);
					rp.setAD_Org_ID(customer.getAD_Org_ID());
					rp.setUNS_Cvs_RptCustomer_ID(customer.get_ID());
					rp.setM_Product_ID(M_Product_ID);
					rp.setC_UOM_ID(p.getC_UOM_ID());
					rp.setM_Locator_ID(M_Locator_ID);
					rp.setUOMQtySoldL1(qtyL1);
					rp.setUOMQtySoldL2(qtyL2);
					rp.setUOMQtySoldL3(qtyL3);
					rp.setUOMQtySoldL4(qtyL4);
					rp.setPriceActual(price);
					rp.setisManual(false);
				}
				rp.saveEx();
			}
		} // if selected	
		return true;
	}
	
	/**
	 *  Load Data - Stock On Hand
	 *  @param UNS_Cvs_Rpt_ID Canvas Report
	 */
	protected Vector<Vector<Object>> getOnHandLocator (int UNS_Cvs_Rpt_ID)
	{
		/**
		 *  Selected		- 0
		 *  QtySold L1		- 1
		 *  QtySold L2		- 2
		 *  QtySold L3		- 3
		 *  QtySold L4		- 4
		 *  QtyAvalibale	- 5
		 *  PriceActual		- 6
		 *  Product			- 7
		 */

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT 0, 0, 0, 0," //1..2..3..4
				+ " UOMConversionToSTR(p.M_Product_ID,"
						+ "	(COALESCE(SUM(soh.QtyOnHand),0) -"
						+ " COALESCE((SELECT SUM(QtySold) FROM UNS_Cvs_RptProduct WHERE M_Product_ID = p.M_Product_ID"
						+ " AND UNS_Cvs_RptCustomer_ID IN (SELECT UNS_Cvs_RptCustomer_ID FROM UNS_Cvs_RptCustomer"
						+ " WHERE UNS_Cvs_Rpt_ID = rpt.UNS_Cvs_Rpt_ID)),0))) AS QtyAvaliable," //5
				+ " getpriceactualcanvas(rpt.C_BPartner_ID, p.M_Product_ID)," //6
				+ " p.M_Product_ID, p.Name FROM UNS_Cvs_Rpt rpt" //7
				+ " INNER JOIN UNS_BP_Canvasser cvs ON cvs.C_BPartner_ID = rpt.C_BPartner_ID"
				+ " INNER JOIN M_Locator loc ON loc.M_Locator_ID = cvs.M_Locator_ID"
				+ " INNER JOIN M_StorageOnHand soh ON soh.M_Locator_ID = loc.M_Locator_ID"
				+ " INNER JOIN M_Product p ON p.M_Product_ID = soh.M_Product_ID"
				+ " INNER JOIN UNS_Cvs_RptCustomer rc ON rc.UNS_Cvs_Rpt_ID = rpt.UNS_Cvs_Rpt_ID"
				+ " WHERE rpt.UNS_Cvs_Rpt_ID = ? AND soh.QtyOnHand <> 0"
				+ " GROUP BY rpt.UNS_Cvs_Rpt_ID, p.M_Product_ID, loc.M_Locator_ID"
				+ " ORDER BY p.Name");
		
		if (log.isLoggable(Level.FINER)) log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, UNS_Cvs_Rpt_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));           //  0-Selection
				BigDecimal qtyL1 = rs.getBigDecimal(1);
				line.add(qtyL1); // 1-QtyL1
				BigDecimal qtyL2 = rs.getBigDecimal(2);
				line.add(qtyL2); // 2-QtyL2
				BigDecimal qtyL3 = rs.getBigDecimal(3);
				line.add(qtyL3); // 3-QtyL3
				BigDecimal qtyL4 = rs.getBigDecimal(4);
				line.add(qtyL4); // 4-QtyL4
				String qtyAvaliable = rs.getString(5);
				line.add(qtyAvaliable); //5-qtyAvaliable
				BigDecimal price  = rs.getBigDecimal(6);
				line.add(price); // 6-price
				
				KeyNamePair pp = new KeyNamePair(rs.getInt(7), rs.getString(8).trim());
				line.add(pp); // 7-Product
				
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
	}   //  LoadStockOnHand
	
	protected Vector<String> getOISColumnNames()
	{
		Vector<String> columnNames = new Vector<String>(8);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "UOMQtySoldL1"));
	    columnNames.add(Msg.translate(Env.getCtx(), "UOMQtySoldL2"));
	    columnNames.add(Msg.translate(Env.getCtx(), "UOMQtySoldL3"));
	    columnNames.add(Msg.translate(Env.getCtx(), "UOMQtySoldL4"));
	    columnNames.add(Msg.translate(Env.getCtx(), "QtyAvailable"));
	    columnNames.add(Msg.translate(Env.getCtx(), "PriceActual"));
	    columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
	    
	    return columnNames;	
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);     //  Selection
		miniTable.setColumnClass(1, BigDecimal.class, false);      //  QtyL1
		miniTable.setColumnClass(2, BigDecimal.class, false);      //  QtyL2
		miniTable.setColumnClass(3, BigDecimal.class, false);      //  QtyL3
		miniTable.setColumnClass(4, BigDecimal.class, false);      //  QtyL4
		miniTable.setColumnClass(5, String.class, true);          //  QtyAvaliable
		miniTable.setColumnClass(6, BigDecimal.class, false);  //  Price
		miniTable.setColumnClass(7, String.class, true);   //  Product
		
		//  Table UI
		miniTable.autoSize();
		
	}
}
