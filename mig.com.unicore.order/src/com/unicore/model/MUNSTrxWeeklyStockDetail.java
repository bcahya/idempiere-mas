/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_M_Transaction;
import org.compiere.util.DB;
import org.compiere.util.Util;

/**
 * @author Burhani Adam
 *
 */
public class MUNSTrxWeeklyStockDetail extends X_UNS_TrxWeeklyStock_Detail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1353412654708510748L;

	/**
	 * @param ctx
	 * @param UNS_TrxWeeklyStock_Detail_ID
	 * @param trxName
	 */
	public MUNSTrxWeeklyStockDetail(Properties ctx,
			int UNS_TrxWeeklyStock_Detail_ID, String trxName) {
		super(ctx, UNS_TrxWeeklyStock_Detail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSTrxWeeklyStockDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		String sql = null;
		int id = 0;
		
		if(getM_MovementLine_ID() > 0)
		{
			setM_Movement_ID(getM_MovementLine().getM_Movement_ID());
			if(getM_MovementLine().getM_Movement().getC_Order_ID() > 0)
			{
				column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyDelivered;
			}
			else if(getM_MovementLine().getM_Movement().getC_BPartner_ID() > 0)
			{
				column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyDelivered;
				setMovementQty(getMovementQty().negate());
			}
		}
		else if(getM_InOutLine_ID() > 0)
			setM_InOut_ID(getM_InOutLine().getM_InOut_ID());
		else if(getM_InventoryLine_ID() > 0)
			setM_Inventory_ID(getM_InventoryLine().getM_Inventory_ID());
		else if(getUNS_Production_Detail_ID() > 0)
		{
			sql = "SELECT UNS_Production_ID FROM UNS_Production_Detail WHERE UNS_Production_Detail_ID=?";
			id = DB.getSQLValue(get_TrxName(), sql, getUNS_Production_Detail_ID());
			setUNS_Production_ID(id);
		}
		else if(getUNS_PL_ConfirmProduct_ID() > 0)
		{
			sql = "SELECT UNS_PackingList_ID FROM UNS_PL_Confirm WHERE UNS_PL_Confirm_ID ="
					+ " (SELECT UNS_PL_Confirm_ID FROM UNS_PL_ConfirmProduct WHERE UNS_PL_ConfirmProduct_ID=?)";
			id = DB.getSQLValue(get_TrxName(), sql, getUNS_Production_Detail_ID());
			setUNS_PackingList_ID(id);
		}
		
		return upHeaderQty();
	}
	
	protected boolean upHeaderQty()
	{
		analyzeUpdateColumn();
		MUNSTrxWeeklyStockLine line = (MUNSTrxWeeklyStockLine) getParent();
		line.set_ValueOfColumn(column, ((BigDecimal) line.get_Value(column)).add(getMovementQty()));
		
		return line.save();
	}
	
	public MUNSTrxWeeklyStockLine m_parent = null;
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MUNSTrxWeeklyStockLine getParent()
	{
		if (m_parent == null)
			m_parent = new MUNSTrxWeeklyStockLine(getCtx(), getUNS_TrxWeeklyStock_Line_ID(), get_TrxName());
		
		return m_parent;
	}	//	getParent
	
	String column = null;
	private void analyzeUpdateColumn()
	{	
		if(!Util.isEmpty(column, true))
			return;
		switch (getMovementType()) {
		case X_M_Transaction.MOVEMENTTYPE_CustomerReturns :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyReturnC;
				break;
		case X_M_Transaction.MOVEMENTTYPE_CustomerShipment :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyDelivered;
				break;
		case X_M_Transaction.MOVEMENTTYPE_InventoryIn :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_OtherQtyIn;
				break;
		case X_M_Transaction.MOVEMENTTYPE_InventoryOut :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_OtherQtyOut;
				break;
		case X_M_Transaction.MOVEMENTTYPE_MovementFrom :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_MovementQtyOut;
				break;
		case X_M_Transaction.MOVEMENTTYPE_MovementTo :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_MovementQtyIn;
				break;
		case X_M_Transaction.MOVEMENTTYPE_PackedIn :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyPacked;
				break;
		case X_M_Transaction.MOVEMENTTYPE_PackedOut :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyPacked;
				break;
		case X_M_Transaction.MOVEMENTTYPE_VendorReceipts :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyReceipt;
				break;
		case X_M_Transaction.MOVEMENTTYPE_VendorReturns :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_QtyReturnC;
				break;
		case X_M_Transaction.MOVEMENTTYPE_Production_ :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_ProductionQtyOut;
				break;
		case X_M_Transaction.MOVEMENTTYPE_ProductionPlus :
			column = X_UNS_TrxWeeklyStock_Line.COLUMNNAME_ProductionQtyIn;
				break;
		default: column = null;
			break;
		}
	}
}