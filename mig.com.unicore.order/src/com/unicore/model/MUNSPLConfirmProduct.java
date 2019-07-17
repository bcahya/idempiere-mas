/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInOutLineMA;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.util.ErrorMsg;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSPLConfirmProduct extends X_UNS_PL_ConfirmProduct
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3808189968306020691L;
	private MUNSPLConfirmLine[] m_Lines;
	private MUNSPLConfirmMA[] m_MALines;
	private MUNSPLConfirm m_parent;

	/**
	 * @param ctx
	 * @param UNS_PL_ConfirmProduct_ID
	 * @param trxName
	 */
	public MUNSPLConfirmProduct(Properties ctx, int UNS_PL_ConfirmProduct_ID, String trxName)
	{
		super(ctx, UNS_PL_ConfirmProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLConfirmProduct(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPLConfirmProduct(MUNSPLConfirm confirm, int UNS_PL_ConfimProduct_ID)
	{
		this(confirm.getCtx(), UNS_PL_ConfimProduct_ID, confirm.get_TrxName());

		setClientOrg(confirm);
		setUNS_PL_Confirm_ID(confirm.get_ID());

		m_parent = confirm;
	}

	public MUNSPLConfirm getParent()
	{
		if (m_parent == null)
			m_parent = (MUNSPLConfirm) getUNS_PL_Confirm();

		return m_parent;

	}


	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPLConfirmLine[] getLines(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmLine.COLUMNNAME_UNS_PL_ConfirmProduct_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPLConfirmLine.COLUMNNAME_UNS_PL_ConfirmLine_ID;
		//
		List<MUNSPLConfirmLine> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmLine.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPLConfirmLine[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPLConfirmLine[] getLines(boolean requery, String orderBy)
	{
		if (m_Lines != null && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_Lines = getLines(null, orderClause);
		return m_Lines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPLConfirmLine[] getLines()
	{
		return getLines(false, null);
	} // getLines



	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPLConfirmMA[] getMAs(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmMA.COLUMNNAME_UNS_PL_ConfirmProduct_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPLConfirmMA.COLUMNNAME_UNS_PL_ConfirmMA_UU;
		//
		List<MUNSPLConfirmMA> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmMA.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPLConfirmMA[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPLConfirmMA[] getMAs(boolean requery, String orderBy)
	{
		if (m_MALines != null && !requery)
		{
			set_TrxName(m_MALines, get_TrxName());
			return m_MALines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_MALines = getMAs(null, orderClause);
		return m_MALines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPLConfirmMA[] getMAs()
	{
		return getMAs(false, null);
	} // getLines

	/* ************************************************************************ */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete()
	{
		for (MUNSPLConfirmLine line : getLines())
		{
			if (!line.delete(true))
				return false;
		}

		for (MUNSPLConfirmMA ma : getMAs())
		{
			if (!ma.delete(true))
				return false;
		}

		return super.beforeDelete();
	}

	public void setProduct(PO po)
	{
		setM_Product_ID(po.get_ValueAsInt("M_Product_ID"));
		setC_UOM_ID(po.get_ValueAsInt("C_UOM_ID"));
	}

	public void setQty(BigDecimal qty)
	{
		setConfirmedQty(getConfirmedQty().add(qty));
		setTargetQty(getTargetQty().add(qty));
		setMovementQty(getTargetQty());
		setDifferenceQty(getTargetQty().subtract(getConfirmedQty()));
	}

	public static MUNSPLConfirmProduct getNewConfirmProduct(PO model, MUNSPLConfirm parent)
	{
		int product_id = 0;
		int whs_id = 0;
		int loc_id = 0;
		if(model instanceof MUNSPackingListLine)
		{
			MUNSPackingListLine line = (MUNSPackingListLine) model;
			product_id = line.getM_Product_ID();
			whs_id = line.getParent().getM_Warehouse_ID();
			loc_id = line.getM_Locator_ID();
		}
		else if(model instanceof MUNSPLReturnLine)
		{
			MUNSPLReturnLine line = (MUNSPLReturnLine) model;
			product_id = line.getM_Product_ID();
			whs_id = line.getM_Locator().getM_Warehouse_ID();
			loc_id = line.getM_Locator_ID();
		}
		MUNSPLConfirmProduct confirm = null;
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmProduct.COLUMNNAME_M_Product_ID).append("=? AND ")
						.append(MUNSPLConfirmProduct.COLUMNNAME_M_Warehouse_ID).append("=? AND ")
						.append(MUNSPLConfirmProduct.COLUMNNAME_M_Locator_ID).append("=? AND ")
						.append(MUNSPLConfirmProduct.COLUMNNAME_UNS_PL_Confirm_ID).append("=?");

		confirm =
				Query
						.get(model.getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmProduct.Table_Name,
								whereClauseFinal.toString(), model.get_TrxName())
						.setParameters(product_id, whs_id, loc_id, parent.getUNS_PL_Confirm_ID())
						.firstOnly();

		if (confirm != null)
			return confirm;
		
		confirm = new MUNSPLConfirmProduct(parent, 0);
		confirm.setProduct(model);
		confirm.setM_Locator_ID(loc_id);
		confirm.setM_Warehouse_ID(whs_id);
		
		return confirm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		getParent().updateTonaseVolume();
		
		if(is_ValueChanged(COLUMNNAME_ConfirmedQty))
		{
			BigDecimal newAllocatedQty = getConfirmedQty();//getDifferenceQty();
			
			for(MUNSPLConfirmLine line : getLines())
			{
				if(newAllocatedQty.abs().compareTo(line.getTargetQty().abs()) > 0)
				{
					line.setQtyEntered(line.getTargetQty());
					newAllocatedQty = newAllocatedQty.subtract(line.getTargetQty());
				}
				else //if(newAllocatedQty.compareTo(line.getTargetQty()) > 0 )
				{
					line.setQtyEntered(newAllocatedQty);
					newAllocatedQty = Env.ZERO;
				}
				
				line.isSavingProductList = true;
				line.saveEx();
				line.isSavingProductList = false;
			}
		}
		
		if (getM_AttributeSetInstance_ID() > 0)
		{
			String sql = "UPDATE M_InOutLine SET M_AttributeSetInstance_ID = ? "
					+ " WHERE M_InOutLine_ID IN (SELECT M_InOutLine_ID FROM "
					+ " UNS_PackingList_Line WHERE UNS_PackingList_Line_ID "
					+ " IN (SELECT UNS_PackingList_Line_ID FROM "
					+ " UNS_PL_ConfirmLine WHERE UNS_PL_ConfirmProduct_ID = ?))"
					;
			int ret = DB.executeUpdateEx(sql, new Object[] {get_ID()}, 
					get_TrxName());
			if (ret < 0)
			{
				ErrorMsg.setErrorMsg(getCtx(), "ErrorOnUpdateShipmentLine", 
						"Can't update Attribute Set Instance of Shipment Line");
				return false;
			}
		}
		
		return super.afterSave(newRecord, success);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getConfirmedQty().abs().compareTo(getTargetQty().abs()) > 0)
			throw new AdempiereUserError("Confirmed Qty cannot greater than Targeted Qty.");

		setDifferenceQty(getTargetQty().subtract(getConfirmedQty()));
		
		return super.beforeSave(newRecord);
	}
	

	public boolean deleteLinesMA()
	{
		String sql = "DELETE FROM UNS_PL_ConfirmMA WHERE UNS_PL_ConfirmProduct_ID = ? AND IsAutoGenerated='Y'";
		int retVal = DB.executeUpdate(sql, getUNS_PL_ConfirmProduct_ID(), get_TrxName());
		if (log.isLoggable(Level.INFO)) log.log(Level.INFO, 
				retVal + " UNS_PLConfirmMA deleted ");
		
		return retVal != -1;
	}

	public void createReversalLine (MUNSPLConfirmProduct ori)
	{
		MUNSPLConfirmLine oLine[] = ori.getLines();
		for (int i=0; i<oLine.length; i++)
		{
			MUNSPLConfirmLine rLine = new MUNSPLConfirmLine(this);
			rLine.setDescription(oLine[i].getDescription());
			rLine.setQtyEntered(oLine[i].getQtyEntered().negate());
			rLine.setTargetQty(oLine[i].getTargetQty().negate());
			rLine.setM_Product_ID(oLine[i].getM_Product_ID());
			rLine.setUNS_PackingList_Line_ID(oLine[i].getUNS_PackingList_Line_ID());
			rLine.setReversal(true);
			rLine.saveEx();
		}
	}
	
	public String createShipInvLine(MInOut io, MInvoice inv)
	{
		String m_MsgErr = null;
		
		if(getDifferenceQty().compareTo(Env.ZERO) <= 0)
			return m_MsgErr;
		
		if(io == null || io.get_ID() <= 0)
			return m_MsgErr;
		
		String sql = "SELECT M_Locator_ID FROM M_Locator WHERE UPPER(Value) like UPPER('%Intransit%Customer%') AND M_Warehouse_ID=?";
		int idLoc = DB.getSQLValue(get_TrxName(), sql, getM_Warehouse_ID());
		MInOutLine iol = new MInOutLine(io);
		iol.setM_Product_ID(getM_Product_ID(), getC_UOM_ID());
		iol.setQty(getDifferenceQty());
		iol.setM_Locator_ID(idLoc);
		iol.setM_AttributeSetInstance_ID(0);
		if(!iol.save())
			m_MsgErr = "Failed when trying create shipment lines";
		
		MInvoiceLine il = new MInvoiceLine(inv);
		il.setM_InOutLine_ID(iol.get_ID());
		il.setM_Product_ID(getM_Product_ID());
		il.setC_UOM_ID(getC_UOM_ID());
		il.setQty(getDifferenceQty());
		if(!il.save())
			m_MsgErr = "Failed when trying create invoice lines";
		
		String a = "SELECT UNS_PL_ConfirmProduct_ID FROM UNS_PL_ConfirmProduct WHERE M_Product_ID=? AND M_Warehouse_ID=?"
				+ " AND UNS_PL_Confirm_ID IN (SELECT UNS_PL_Confirm_ID FROM UNS_PL_Confirm WHERE ConfirmType='PC'"
				+ " AND UNS_PackingList_ID=?)";
		int m_PL_ConfirmProduct_ID = DB.getSQLValue(get_TrxName(), a, new Object[]{getM_Product_ID(), getM_Warehouse_ID(),
											getUNS_PL_Confirm().getUNS_PL_Return().getUNS_PackingList_ID()});
		
		MUNSPLConfirmProduct plcp = new MUNSPLConfirmProduct(getCtx(), m_PL_ConfirmProduct_ID, get_TrxName());
		
		for(MUNSPLConfirmMA ma : getMAs(true, null))
		{
			for(MUNSPLConfirmMA maOld : plcp.getMAs(true, null))
			{
				if(ma.getDateMaterialPolicy().compareTo(maOld.getDateMaterialPolicy()) == 0
						&& ma.getM_AttributeSetInstance_ID() == maOld.getM_AttributeSetInstance_ID()
							&& ma.getMovementQty() != maOld.getMovementQty())
				{
					MInOutLineMA lineMA = new MInOutLineMA(getCtx(), 0, get_TrxName());
					lineMA.setAD_Org_ID(iol.getAD_Org_ID());
					lineMA.setM_InOutLine_ID(iol.get_ID());
					lineMA.setM_AttributeSetInstance_ID(ma.getM_AttributeSetInstance_ID());
					lineMA.setMovementQty(maOld.getMovementQty().subtract(ma.getMovementQty()));
					lineMA.setDateMaterialPolicy(ma.getDateMaterialPolicy());
					if(!lineMA.save())
						m_MsgErr = "Failed when trying create MA Lines";
				}
			}
		}
			
		return m_MsgErr;
	}
}