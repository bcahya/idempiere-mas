/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineMA;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA_Andy
 *
 */
public class MUNSPLConfirmLine extends X_UNS_PL_ConfirmLine
{
		
	/*
	 * 
	 */
	private static final long serialVersionUID = 3844404755693468053L;
	private MUNSPLConfirmProduct m_parent;

	/**
	 * @param ctx
	 * @param UNS_PL_ConfirmLine_ID
	 * @param trxName
	 */
	public MUNSPLConfirmLine(Properties ctx, int UNS_PL_ConfirmLine_ID, String trxName)
	{
		super(ctx, UNS_PL_ConfirmLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLConfirmLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	public MUNSPLConfirmLine (MUNSPLConfirmProduct parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setHeader(parent);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		String sql = "SELECT plConfirm.ConfirmType FROM UNS_PL_Confirm plConfirm "
				+ "	INNER JOIN UNS_PL_ConfirmProduct cProduct ON plConfirm.UNS_PL_Confirm_ID=cProduct.UNS_PL_Confirm_ID "
				+ " WHERE cProduct.UNS_PL_ConfirmProduct_ID=?";
		String confirmType = DB.getSQLValueStringEx(get_TrxName(), sql, getUNS_PL_ConfirmProduct_ID());
		
		if (confirmType.equals(MUNSPLConfirm.CONFIRMTYPE_ShipmentReturnConfirmation)
				&& getQtyEntered().abs().compareTo(getTargetQty().abs()) > 0)
		{
			log.saveError("ConfirmQtyMustLessThanTargetQty", 
					"Confirmed quantity must be equals or less than target qty.");
			return false;
		}
		return true;
	}
	
	protected boolean isSavingProductList = false;
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean sucess)
	{
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyEntered))
		{
			if (!isSavingProductList)
			{				
				//update PL Confirm Product
				String sql = "SELECT SUM(QtyEntered) FROM UNS_PL_ConfirmLine WHERE UNS_PL_ConfirmProduct_ID=?";
				BigDecimal sumQty = DB.getSQLValueBDEx(get_TrxName(), sql, getUNS_PL_ConfirmProduct_ID());
				
				if (sumQty == null)
					sumQty = Env.ZERO;

				/** enhancement -- tune up the process. */
				sql = "UPDATE UNS_PL_ConfirmProduct SET ConfirmedQty = ?"// + sumQty
						//+ ", MovementQty = ?" + sumQty +", DifferenceQty = (? - TargetQty)"// + sumQty.subtract(getUNS_PL_ConfirmProduct().getTargetQty())
						+ ", MovementQty = ?, DifferenceQty = (? - TargetQty) "
						+ " WHERE UNS_PL_ConfirmProduct_ID = " + getUNS_PL_ConfirmProduct_ID();
				int count = DB.executeUpdateEx(sql, new Object[]{sumQty, sumQty, sumQty}, get_TrxName());
				if (count <= 0) {
					log.saveError("CannotUpdateProductList", "Cannot update product list confirmed quantity.");
				}
			}

			MUNSPackingListLine pLine = new MUNSPackingListLine(getCtx(), 
					getUNS_PackingList_Line_ID(), get_TrxName());
		
			String sql = "SELECT plConfirm.ConfirmType FROM UNS_PL_Confirm plConfirm "
					+ "	INNER JOIN UNS_PL_ConfirmProduct cProduct ON plConfirm.UNS_PL_Confirm_ID=cProduct.UNS_PL_Confirm_ID "
					+ " WHERE cProduct.UNS_PL_ConfirmProduct_ID=?";
			String confirmType = DB.getSQLValueStringEx(get_TrxName(), sql, getUNS_PL_ConfirmProduct_ID());
			BigDecimal qty = getQtyEntered();

			if(confirmType.equals(MUNSPLConfirm.CONFIRMTYPE_ShipmentReturnConfirmation))
			{
				qty = getTargetQty().subtract(getQtyEntered());
				pLine.setQtyShipping(qty);
			}
			else
			{
				pLine.setMovementQty(getQtyEntered());
				pLine.setQtyEntered(getQtyEntered());
				pLine.setQtyShipping(getQtyEntered());
			}
			pLine.saveEx();
			
			if(newRecord && !getParent().getUNS_PL_Confirm().getConfirmType()
					.equals(X_UNS_PL_Confirm.CONFIRMTYPE_PackingListConfirmation)
					&& !isReversal())
			{
				MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
				BigDecimal movementQty = getQtyEntered();
				if(line.getM_AttributeSetInstance_ID() > 0)
				{
					MUNSPLConfirmMA ma = MUNSPLConfirmMA.getCreate(getParent(),
							line.getM_AttributeSetInstance_ID(), line.getM_InOut().getMovementDate());
					ma.setMovementQty(ma.getMovementQty().add(movementQty));
					ma.saveEx();
					if(isPartialCancel())
					{
						line.setMovementQty(line.getMovementQty().subtract(movementQty));
						line.saveEx();
						movementQty = Env.ZERO;
					}
				}
				else
				{
					MInOutLineMA[] ma = MInOutLineMA.get(getCtx(), getM_InOutLine_ID(), get_TrxName());
					for(int i = 0; i < ma.length; i++)
					{
						if(movementQty.compareTo(Env.ZERO) == 0)
							break;
						
						MUNSPLConfirmMA confirmMA = MUNSPLConfirmMA.getCreate(getParent(), ma[i].getM_AttributeSetInstance_ID(),
								ma[i].getDateMaterialPolicy());
						if(movementQty.compareTo(ma[i].getMovementQty()) == 1)
						{
							confirmMA.setMovementQty(confirmMA.getMovementQty().add(ma[i].getMovementQty()));
							movementQty = movementQty.subtract(ma[i].getMovementQty());
							if(isPartialCancel())
								ma[i].deleteEx(false);
						}
						else if(movementQty.compareTo(ma[i].getMovementQty()) <= 0)
						{
							confirmMA.setMovementQty(confirmMA.getMovementQty().add(movementQty));
							if(isPartialCancel())
							{
								ma[i].setMovementQty(ma[i].getMovementQty().subtract(movementQty));
								ma[i].saveEx();
							}
							movementQty = Env.ZERO;
						}
						confirmMA.saveEx();
					}
				}
				if(movementQty.compareTo(Env.ZERO) == 1)
				{
					line.setMovementQty(line.getMovementQty().subtract(getQtyEntered()));
					line.setQtyEntered(line.getQtyEntered().subtract(getQtyEntered()));
					line.saveEx();
				}
			}
		}

		return super.afterSave(newRecord, sucess);
	} // afterSave

	public static MUNSPLConfirmLine getNewConfirmLine(MUNSPackingListLine PLLine, MUNSPLConfirmProduct header)
	{
		MUNSPLConfirmLine confirm = null;
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmLine.COLUMNNAME_UNS_PackingList_Line_ID).append("=?")
				.append(" AND UNS_PL_ConfirmProduct_ID = ? ");

		confirm =
				Query
						.get(PLLine.getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmLine.Table_Name,
								whereClauseFinal.toString(), PLLine.get_TrxName())
						.setParameters(PLLine.get_ID(), header.get_ID()).firstOnly();

		if (confirm == null)
			confirm = new MUNSPLConfirmLine(header);
		
		return confirm;
	}

	public static MUNSPLConfirmLine getNewReturnConfirmLine(MUNSPLReturnLine returnLine, MUNSPLConfirmProduct header)
	{
		String sql = "SELECT pll.UNS_PackingList_Line_ID FROM UNS_PackingList_Line pll "
				+ "	WHERE pll.UNS_PackingList_Order_ID=? AND pll.M_Product_ID=? AND pll.M_Locator_ID=?"
				+ " AND pll.M_InOutLine_ID = ?";
		int pll_ID = DB.getSQLValueEx(header.get_TrxName(), sql, returnLine.getUNS_PackingList_Order_ID(), 
				returnLine.getM_Product_ID(), returnLine.getM_Locator_ID(), returnLine.getM_InOutLine_ID());
		
		MUNSPLConfirmLine confirm = null;
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmLine.COLUMNNAME_UNS_PL_ConfirmProduct_ID).append("=?")
				//.append(" AND ").append(MUNSPLConfirmLine.COLUMNNAME_M_InOutLine_ID).append("=?")
				.append(" AND ").append(MUNSPLConfirmLine.COLUMNNAME_M_Product_ID).append("=?")
				.append(" AND UNS_PackingList_Line_ID=?");

		confirm = Query.get(
				returnLine.getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmLine.Table_Name,
				whereClauseFinal.toString(), returnLine.get_TrxName())
				.setParameters(header.getUNS_PL_ConfirmProduct_ID(), 
						returnLine.getM_Product_ID(), pll_ID)
				.firstOnly();

		if (confirm == null) {
			confirm = new MUNSPLConfirmLine(returnLine.getCtx(), 0, returnLine.get_TrxName());
			confirm.setUNS_PackingList_Line_ID(pll_ID);
		}
		
		return confirm;
	}

	public void setHeader(MUNSPLConfirmProduct plCProduct)
	{
		setClientOrg(plCProduct);
		setM_Product_ID(plCProduct.getM_Product_ID());
		setUNS_PL_ConfirmProduct_ID(plCProduct.get_ID());
		
		m_parent = plCProduct;
	}
	
	public MUNSPLConfirmProduct getParent(){
		if (m_parent == null || m_parent.get_ID() <= 0)
			m_parent = (MUNSPLConfirmProduct) getUNS_PL_ConfirmProduct();
		
		return m_parent;
		
	}

	public void setLine(MUNSPackingListLine line)
	{
		setUNS_PackingList_Line_ID(line.get_ID());
		setQtyEntered(line.getQtyEntered());
		setTargetQty(line.getTargetQty());
	}
	
	private boolean m_reversal = false;
	
	public boolean isReversal()
	{
		return m_reversal;
	}
	
	public void setReversal (boolean reverse)
	{
		m_reversal = reverse;
	}
	
	private boolean m_partialCancel = false;
	private boolean isPartialCancel()
	{
		return m_partialCancel;
	}
	public void setPartialCancel(boolean partialCancel)
	{
		m_partialCancel = partialCancel;
	}
}
