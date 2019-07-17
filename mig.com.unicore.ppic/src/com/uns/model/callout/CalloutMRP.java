/**
 * 
 */
package com.uns.model.callout;


import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import com.uns.model.MUNSMPSProduct;
import com.uns.model.X_UNS_MRP;

/**
 * @author menjangan
 *
 */
public class CalloutMRP extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutMRP() {
		// TODO Auto-generated constructor stub
	}
	
	private String MPSProduct(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField
			, Object value, Object oldValue)
	{
		
		Integer UNS_MPSProduct_ID = (Integer)value;
		if (UNS_MPSProduct_ID <=0 )
			return "";
		
		if (isCalloutActive())
			return "";
		
		MUNSMPSProduct MPSPRoduct = new MUNSMPSProduct(Env.getCtx(), UNS_MPSProduct_ID, null);
		mTab.setValue(X_UNS_MRP.COLUMNNAME_M_Product_ID, MPSPRoduct.getM_Product_ID());
		mTab.setValue(X_UNS_MRP.COLUMNNAME_C_UOM_ID, MPSPRoduct.getC_UOM_ID());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue =null;
		if(mField.getColumnName() == X_UNS_MRP.COLUMNNAME_UNS_MRP_ID)
		{
			retValue = MPSProduct(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		// TODO Auto-generated method stub
		return retValue;
	}

}
