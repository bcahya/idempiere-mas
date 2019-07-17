package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.UNSTimeUtil;

import com.uns.model.I_UNS_Manufacturing_Order;
import com.uns.model.MUNSMPSProductRscWeekly;
import com.uns.model.X_UNS_Manufacturing_Order;
import com.uns.model.X_UNS_ProductionSchedule;
import com.uns.model.X_UNS_SrcProduct_Detail;

public class CalloutManufacturingOrder extends CalloutEngine implements IColumnCallout {


	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue =null;
		if(X_UNS_ProductionSchedule.COLUMNNAME_UNS_MPSProductRsc_Weekly_ID.equalsIgnoreCase(mField.getColumnName()))
		{
			retValue = qtyMPS(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		if (X_UNS_Manufacturing_Order.COLUMNNAME_C_Year_ID.equalsIgnoreCase(mField.getColumnName()) ||
				X_UNS_Manufacturing_Order.COLUMNNAME_WeekNo.equalsIgnoreCase(mField.getColumnName())) 
		{
			retValue = setPeriod(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		if (X_UNS_ProductionSchedule.COLUMNNAME_M_Product_ID.equalsIgnoreCase(mField.getColumnName())) 
		{
			retValue = setProductionStickerInfo(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		// The attribute instance of the source product to be selected.
		if (X_UNS_SrcProduct_Detail.COLUMNNAME_M_AttributeSetInstance_ID.equalsIgnoreCase(mField.getColumnName())) 
		{
			retValue = setASIQty(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setPeriod (Properties ctx, int windowNo, GridTab mTab,
							  GridField mField, Object value, Object oldValue) 
	{
		String retMsg = null;
		
		I_UNS_Manufacturing_Order mo = GridTabWrapper.create(mTab, I_UNS_Manufacturing_Order.class);
		
		if (null == mo.getWeekNo() || mo.getC_Year_ID() <=0 )
			return retMsg;
		int year = Integer.valueOf(mo.getC_Year().getFiscalYear());

		int weekNo = Integer.valueOf(mo.getWeekNo());

		Timestamp periodStart = UNSTimeUtil.getProductionWeekStartDate(year, weekNo);
		
		Timestamp periodEnd = UNSTimeUtil.getProductionWeekEndDate(year, weekNo);
		
		mTab.setValue(I_UNS_Manufacturing_Order.COLUMNNAME_Target_PD_Start, periodStart);
		mTab.setValue(I_UNS_Manufacturing_Order.COLUMNNAME_Target_PD_End, periodEnd);
		
		return retMsg;
	}

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String qtyMPS(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (null == value)
			return null;
		Integer UNS_MPSProductRsc_Weekly_ID = (Integer)value;
		if (UNS_MPSProductRsc_Weekly_ID <= 0 )
			return "";
		
		if (isCalloutActive())
			return "";
		
		MUNSMPSProductRscWeekly MPSRscProduct =
				new MUNSMPSProductRscWeekly(Env.getCtx(), UNS_MPSProductRsc_Weekly_ID, null);
		mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyUom, MPSRscProduct.getActualToStockUOM());
		if(MPSRscProduct.getActualToOrderUOM().compareTo(BigDecimal.ZERO) > 0)
		{
			mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyMT, MPSRscProduct.getActualToOrderMT());
			mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyMPS, MPSRscProduct.getActualToOrderUOM());
		}
		else
		{
			mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyMT, MPSRscProduct.getActualToStockMT());
			mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyMPS, MPSRscProduct.getActualToStockUOM());
		}
		//mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_Target_PD_Start, MPSRscProduct.getMPSDate());
		//mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_Target_PD_End, MPSRscProduct.getMPSDate());
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setProductionStickerInfo(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		int countSticker = DB.getSQLValue(null, 
				"SELECT count(*) FROM UNS_Product_StickerInfo WHERE M_Product_ID=?", 
				value);
		
		boolean hasSticker = (countSticker > 0);
		
		mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_HasStickerInfo, hasSticker);
		
		return null;
	}
	
	/**
	 * Set the ASI qty. Get the sum of available qty (QtyOnHand - NCQty - OnHoldQty) from all storages.
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setASIQty(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		BigDecimal availableQty = DB.getSQLValueBDEx(null, 
				"SELECT sum(QtyOnHand - NCQty - OnHoldQty) FROM M_StorageOnHand WHERE M_AttributeSetInstance_ID=?", 
				value);
		
		mTab.setValue(X_UNS_SrcProduct_Detail.COLUMNNAME_AvailableQty, availableQty);
		//Env.getContext(ctx, "QtyOrdered");
		mTab.setValue(X_UNS_SrcProduct_Detail.COLUMNNAME_AvailableQty, availableQty);
		return null;
	}

}
