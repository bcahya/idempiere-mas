package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.qad.model.MUNSQAStatusPRCLine;

public class CalloutProductionInspection extends CalloutEngine implements IColumnCallout {

	//FIXME remove pallet

	public String getQtyRelease(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-"))
			return "";
		
		//BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
		/**
		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(ctx, null, 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Product_ID), 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Locator_ID),
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_AttributeSetInstance_ID),
				strParser);
				*/
		//mTab.setValue(MUNSQAStatusPRCLine.COLUMNNAME_ReleaseQty, qty);
		
		return "";
	}
	
	public String getQtyOnHold(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-"))
			return "";
		
		//BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
		/**
		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(ctx, null, 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Product_ID), 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Locator_ID),
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_AttributeSetInstance_ID),
				strParser);
				*/
		//mTab.setValue(MUNSQAStatusPRCLine.COLUMNNAME_OnHoldQty, qty);
		
		return "";
	}
	
	public String getQtyNC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-"))
			return "";
		
		//BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
		/**
		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(ctx, null, 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Product_ID), 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Locator_ID),
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_AttributeSetInstance_ID),
				strParser);
				*/
		//mTab.setValue(MUNSQAStatusPRCLine.COLUMNNAME_NCQty, qty);
		
		return "";
	}
	
	public String getQty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-"))
			return "";
		
		//BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
		/**
		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(ctx, null, 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Product_ID), 
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_Locator_ID),
				(Integer) mTab.getValue(MUNSQAStatusPRCLine.COLUMNNAME_M_AttributeSetInstance_ID),
				strParser);
				*/		
		//mTab.setValue(MUNSQAStatusPRCLine.COLUMNNAME_Qty, qty);
		
		return "";
	}
	
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = "";
		
		if (MUNSQAStatusPRCLine.COLUMNNAME_Release.equals(mField.getColumnName()))
		{
			retValue = getQtyRelease(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAStatusPRCLine.COLUMNNAME_OnHold.equals(mField.getColumnName()))
		{
			retValue = getQtyOnHold(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAStatusPRCLine.COLUMNNAME_NC.equals(mField.getColumnName()))
		{
			retValue = getQtyNC(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAStatusPRCLine.COLUMNNAME_CodeNo.equals(mField.getColumnName()))
		{
			retValue = getQty(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retValue;
	}
}
