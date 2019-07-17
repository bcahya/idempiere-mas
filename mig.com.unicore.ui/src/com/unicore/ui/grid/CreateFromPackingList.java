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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.X_M_Requisition;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.wf.MWorkflow;

import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSPackingListLine;

/**
 * @author Burhani Adam
 *
 */
public class CreateFromPackingList extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromPackingList(GridTab gridTab) {
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
		return false;
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
		int UNS_PackingList_ID = (Integer) getGridTab().getValue("UNS_PackingList_ID");
		MUNSPackingList pl = new MUNSPackingList(Env.getCtx(), UNS_PackingList_ID, trxName);
		MRequisition req = null;
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				int M_LocatorTo_ID = (Integer) miniTable.getValueAt(i, 8);
				BigDecimal qtyPacked = (BigDecimal) miniTable.getValueAt(i, 3);
				BigDecimal qtyRequest = (BigDecimal) miniTable.getValueAt(i, 7);
				BigDecimal qtyLocatorTo = (BigDecimal) miniTable.getValueAt(i, 7);
				if(qtyRequest.compareTo(qtyPacked) == 1 || qtyRequest.compareTo(qtyLocatorTo) == 1)
					throw new AdempiereException("Over Qty Request");
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 1);
				int M_Locator_ID = pp.getKey();
				MLocator loc = MLocator.get(Env.getCtx(), M_Locator_ID);
				req = MRequisition.getByPL(Env.getCtx(), pl.get_ID(),
						loc.getM_Warehouse_ID(), trxName);
				if(req == null)
				{
					req = new MRequisition(Env.getCtx(), 0, trxName);
					req.setAD_Org_ID(loc.getAD_Org_ID());
					req.setM_Warehouse_ID(loc.getM_Warehouse_ID());
					req.setDateDoc(pl.getDateDoc());
					req.setDateRequired(pl.getDateDoc());
					req.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_PackingListRequisition));
					req.setPriorityRule(X_M_Requisition.PRIORITYRULE_High);
					req.setAD_User_ID(Env.getContextAsInt(Env.getCtx(), 0, "#AD_User_ID"));
					req.setUNS_PackingList_ID(pl.get_ID());
					if(!req.save())
					{
						throw new AdempiereException("Requisition not created");
					}
				}	
				
				MRequisitionLine line = new MRequisitionLine(req);
				pp = (KeyNamePair) miniTable.getValueAt(i, 2);
				int M_Product_ID = pp.getKey();
				line.setM_Locator_ID(loc.get_ID());
				line.setM_LocatorTo_ID(M_LocatorTo_ID);
				line.setM_Product_ID(M_Product_ID);
				line.setQty(qtyRequest);
				if(!line.save())
				{
					throw new AdempiereException("Requisition Line not created");
				}
				
				String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(UNS_PackingList_Line_ID), ';') FROM"
						+ " UNS_PackingList_Line pll"
						+ " INNER JOIN UNS_PackingList_Order plo ON plo.UNS_PackingList_Order_ID = pll.UNS_PackingList_Order_ID"
						+ " WHERE plo.UNS_PackingList_ID = ? AND pll.M_Locator_ID = ? AND pll.M_Product_ID = ?";
				String listID = DB.getSQLValueString(trxName, sql, new Object[]{UNS_PackingList_ID, M_Locator_ID, M_Product_ID});
				String[] id = listID.split(";");
				BigDecimal qty = qtyRequest;
				for(int y = 0; y < id.length; y++)
				{
					int idInt = Integer.parseInt(id[y]);
					MUNSPackingListLine pll = new MUNSPackingListLine(Env.getCtx(), idInt, trxName);
					BigDecimal qtyNeed = pll.getMovementQty().subtract(pll.getQtyRequest());
					if(qtyNeed.compareTo(qty) >= 0)
					{
						pll.setQtyRequest(pll.getQtyRequest().add(qty));
						qty = Env.ZERO;
					}
					else if(qtyNeed.compareTo(qty) < 0)
					{
						pll.setQtyRequest(pll.getQtyRequest().add(qtyNeed));
						qty = qty.subtract(qtyNeed);
					}
					
					pll.saveEx();
					if(qty.compareTo(Env.ZERO) == 0)
						break;
				}
			}
		}
		
		if(req != null)
		{
			try
			{				
				ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(req, DocAction.ACTION_Complete);
				if(pi.isError())
				{
					throw new AdempiereUserError("Can't complete requisition " + pi.getSummary());
				}
			} catch (Exception e) {
				throw new AdempiereException(req.getProcessMsg());
			}
		}
		
		return true;
	}
	
	protected Vector<Vector<Object>> getLines(int UNS_PackingList_ID, int M_Locator_ID)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT Master.LocatorID, Master.Locator, Master.ProductID, Master.Product,"
				+ " Master.QtyPacked, Master.QtyLocatorTo,"
				+ " (Master.QtyLocatorTo - Master.QtyPacked) AS Difference, Master.QtyLocatorFrom,"
				+ " 0 AS QtyRequest FROM"
				+ " (SELECT ml.M_Locator_ID AS LocatorID, ml.Value AS Locator,"
				+ " mp.M_Product_ID AS ProductID, mp.Name AS Product, SUM(pll.QtyEntered - pll.QtyRequest) AS QtyPacked,"
				+ " TotalStockByLocator(mp.M_Product_ID, ml.M_Locator_ID, null) AS QtyLocatorTo,"
				+ " TotalStockByLocator(mp.M_Product_ID, ?, null) AS QtyLocatorFrom"
				+ " FROM UNS_PackingList_Line pll"
				+ " INNER JOIN UNS_PackingList_Order plo ON plo.UNS_PackingList_Order_ID = pll.UNS_PackingList_Order_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = pll.M_Product_ID"
				+ " INNER JOIN M_Locator ml ON ml.M_Locator_ID = pll.M_Locator_ID"
				+ " WHERE plo.UNS_PackingList_ID=?"
				+ " GROUP BY mp.M_Product_ID, ml.M_Locator_ID) AS Master");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DB.prepareStatement(sql.toString(), null);
			stmt.setInt(1, M_Locator_ID);
			stmt.setInt(2, UNS_PackingList_ID);
			rs = stmt.executeQuery();
			
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				line.add(pp);
				KeyNamePair p = new KeyNamePair(rs.getInt(3), rs.getString(4));
				line.add(p);
				line.add(rs.getBigDecimal(5));
				line.add(rs.getBigDecimal(6));
				line.add(rs.getBigDecimal(7));
				line.add(rs.getBigDecimal(8));
				line.add(rs.getBigDecimal(9));
				line.add(M_Locator_ID);
				data.add(line);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null; stmt = null;
		}
		return data;
	}
	
	protected Vector<String> getOISColumnNames()
	{
		Vector<String> columnNames = new Vector<String>(9);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add("Locator");
	    columnNames.add("Product");
	    columnNames.add("Qty Packing");
	    columnNames.add("Qty Avaliable");
	    columnNames.add("Qty Difference");
	    columnNames.add("Qty Loc Request");
	    columnNames.add("Qty Request");
	    columnNames.add("Locator From");
	    
	    return columnNames;	
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);		//Select
		miniTable.setColumnClass(1, String.class, true);		
		miniTable.setColumnClass(2, String.class, true);		
		miniTable.setColumnClass(3, BigDecimal.class, true);	
		miniTable.setColumnClass(4, BigDecimal.class, true);	
		miniTable.setColumnClass(5, BigDecimal.class, true);	
		miniTable.setColumnClass(6, BigDecimal.class, true);	
		miniTable.setColumnClass(7, BigDecimal.class, false);	
		miniTable.setColumnClass(8, String.class, false);
		
		//  Table UI
		miniTable.autoSize();
	}
}
