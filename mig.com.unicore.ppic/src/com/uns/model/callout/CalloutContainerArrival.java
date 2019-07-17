/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.DB;

import com.uns.model.MUNSContainerArrival;
import com.uns.model.MUNSContainerArrivalLine;
import com.uns.model.MUNSPackingSlip;
import com.uns.model.MUNSPackingSlipLine;
import com.uns.model.MUNSPackingSlipSupplier;

/**
 * @author menjangan
 *
 */
public class CalloutContainerArrival extends CalloutEngine implements
		IColumnCallout {

	/**
	 * 
	 */
	public CalloutContainerArrival() {
		// TODO Auto-generated constructor stub
	}
	
	public String product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		Integer UNS_PackingSlip_ID = 
				(Integer) mTab.getParentTab().getValue(MUNSPackingSlip.COLUMNNAME_UNS_PackingSlip_ID);
		
		if (isCalloutActive())
			return "";
		
		if (null == M_Product_ID || M_Product_ID.intValue() <=0)
			return "";
		
		MProduct product = MProduct.get(ctx, M_Product_ID.intValue());
		
		String Sql = "SELECT psl." + MUNSPackingSlipLine.COLUMNNAME_C_OrderLine_ID 
				+ " FROM " + MUNSPackingSlipLine.Table_Name + " psl INNER JOIN " 
				+ MUNSPackingSlipSupplier.Table_Name
				+ " pss ON pss." + MUNSPackingSlipSupplier.COLUMNNAME_UNS_PackingSlip_ID + " =? AND pss."
				+ MUNSPackingSlipLine.COLUMNNAME_UNS_PackingSlipSupplier_ID
				+ " = psl." + MUNSPackingSlipSupplier.COLUMNNAME_UNS_PackingSlipSupplier_ID
				+ " WHERE psl." + MUNSPackingSlipLine.COLUMNNAME_M_Product_ID + " =?";
		
		int C_OrderLine_ID = DB.getSQLValue(
				product.get_TrxName(), Sql, UNS_PackingSlip_ID.intValue(), M_Product_ID.intValue());
		
		mTab.setValue(MUNSContainerArrivalLine.COLUMNNAME_C_UOM_ID, product.getC_UOM_ID());
		mTab.setValue(MUNSContainerArrivalLine.COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
		
		return "";
	}
	
	public String getDetail(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer UNS_PackingSlip_ID = (Integer)value;
		
		if (isCalloutActive())
			return "";
		
		if (null == UNS_PackingSlip_ID || UNS_PackingSlip_ID.intValue() <=0)
			return "";
		
		MUNSPackingSlip ps = new MUNSPackingSlip(ctx, UNS_PackingSlip_ID.intValue(), null);
		
		mTab.setValue(MUNSContainerArrival.COLUMNNAME_ArriveDate, ps.getArriveDate());
		mTab.setValue(MUNSContainerArrival.COLUMNNAME_ETA, ps.getArriveDate());
		mTab.setValue(MUNSContainerArrival.COLUMNNAME_ETD_Factory, ps.getShipDate());
		
		return "";
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		String retValue = null;
		if (MUNSContainerArrivalLine.COLUMNNAME_M_Product_ID.equals(mField.getColumnName()))
		{
			return product(ctx, WindowNo, mTab, mField, value);
		} else if (MUNSContainerArrival.COLUMNNAME_UNS_PackingSlip_ID.equals(mField.getColumnName()))
		{
			return getDetail(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}

}
