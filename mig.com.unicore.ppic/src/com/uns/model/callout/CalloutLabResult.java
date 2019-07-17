package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import com.uns.qad.model.MUNSQAMLabResult;

public class CalloutLabResult extends CalloutEngine implements IColumnCallout {

	public CalloutLabResult() {
		// TODO Auto-generated constructor stub
	}
	
	//FIXME Remove pallet Calculation

	public String getQtyRelease(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-")){
			mTab.setValue(MUNSQAMLabResult.COLUMNNAME_ReleaseQty, Env.ZERO);
		}

//		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
//
//		mTab.setValue(MUNSQAMLabResult.COLUMNNAME_ReleaseQty, qty);
		
		return "";
	}
	
	public String getQtyOnHold(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-")){
			mTab.setValue(MUNSQAMLabResult.COLUMNNAME_OnHoldQty, Env.ZERO);
		}
		
//		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
//
//		mTab.setValue(MUNSQAMLabResult.COLUMNNAME_OnHoldQty, qty);
		
		return "";
	}
	
	public String getQtyNC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-")){
			mTab.setValue(MUNSQAMLabResult.COLUMNNAME_NCQty, Env.ZERO);
		}
		
//		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
//		
//		mTab.setValue(MUNSQAMLabResult.COLUMNNAME_NCQty, qty);
		
		return "";
	}
	
	public String getQty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
			Object value, Object oldValue){
		String strParser = (String)value;
		if (strParser == null || strParser.equals("") || strParser.equals("-")){
			mTab.setValue(MUNSQAMLabResult.COLUMNNAME_Qty, Env.ZERO);
		}
		
//		BigDecimal qty = PalletHelper.getQuantityOfPackageSequence(strParser);
//
//		mTab.setValue(MUNSQAMLabResult.COLUMNNAME_Qty, qty);
		
		return "";
	}
	
	
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = "";
		
		if (MUNSQAMLabResult.COLUMNNAME_Release.equals(mField.getColumnName()))
		{
			retValue = getQtyRelease(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAMLabResult.COLUMNNAME_OnHold.equals(mField.getColumnName()))
		{
			retValue = getQtyOnHold(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAMLabResult.COLUMNNAME_NC.equals(mField.getColumnName()))
		{
			retValue = getQtyNC(ctx, WindowNo, mTab, mField, value, oldValue);
		} else if (MUNSQAMLabResult.COLUMNNAME_CodeNo.equals(mField.getColumnName()))
		{
			retValue = getQty(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retValue;
	}

}
