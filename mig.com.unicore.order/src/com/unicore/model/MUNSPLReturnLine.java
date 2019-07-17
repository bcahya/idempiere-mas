package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.unicore.ui.ISortTabRecord;
import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MUNSPLReturnLine extends X_UNS_PL_ReturnLine implements ISortTabRecord 
{

	private MUNSPLReturnOrder m_parent = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4380666607260103662L;

	public MUNSPLReturnLine(Properties ctx, int UNS_PL_ReturnLine_ID,
			String trxName) {
		super(ctx, UNS_PL_ReturnLine_ID, trxName);
	}

	public MUNSPLReturnLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	public PO getParent()
	{
		if (m_parent == null)
			m_parent = new MUNSPLReturnOrder(getCtx(), getUNS_PL_ReturnOrder_ID(), get_TrxName());
		
		return m_parent;
	}
	
	//private boolean isSavingTabRecordSelection = false;
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (!newRecord && 
				(getCancelledQty().compareTo(getMovementQty()) > 0 || getCancelledQty().signum() <= 0))
		{
			log.saveError("InvalidCancelledQty", 
					"Quantity to be cancelled must greater than zero and less than movement quantity.");
			return false;
		}
		
		return true;
	}

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) 
	{
		this.setUNS_PL_ReturnOrder_ID(parentRecord_ID);
		
		MUNSPLReturnOrder returnOrder = (MUNSPLReturnOrder) getParent();
		
		String whereClause = "C_OrderLine_ID=? AND UNS_PackingList_Order_ID=?";
		
		MUNSPackingListLine pll = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, 
				MUNSPackingListLine.Table_Name, whereClause, get_TrxName())
				.setParameters(getC_OrderLine_ID(), returnOrder.getUNS_PackingList_Order_ID())
				.firstOnly();
		
		this.setClientOrg(pll);
		this.setC_InvoiceLine_ID(pll.getC_InvoiceLine_ID());
		this.setM_InOutLine_ID(pll.getM_InOutLine_ID());
		this.setC_UOM_ID(pll.getC_UOM_ID());
		this.setM_Locator_ID(pll.getM_Locator_ID());
		this.setUNS_PackingList_Order_ID(returnOrder.getUNS_PackingList_Order_ID());
		this.setM_Product_ID(pll.getM_Product_ID());
		this.setReason(returnOrder.getReason());
		//this.isSavingTabRecordSelection = true;
		this.setCancelledQty(Env.ZERO);
		this.setMovementQty(pll.getMovementQty());
		
		return null;
	}

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}

}
