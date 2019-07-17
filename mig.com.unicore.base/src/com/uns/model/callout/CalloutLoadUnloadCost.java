package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Product;

import com.uns.model.I_UNS_Biaya_Bongkar_Muat;

public class CalloutLoadUnloadCost extends CalloutEngine implements
		IColumnCallout {

	public CalloutLoadUnloadCost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retVal = null;
		
		if (I_UNS_Biaya_Bongkar_Muat.COLUMNNAME_M_Product_ID.equals(mField.getColumnName()))
		{
			retVal = setUOM(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retVal;
	}

	private String setUOM(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retVal = null;
		
		I_UNS_Biaya_Bongkar_Muat bongkarMuat = GridTabWrapper.create(mTab, I_UNS_Biaya_Bongkar_Muat.class);
		I_M_Product product = bongkarMuat.getM_Product();
		if (product != null)
			bongkarMuat.setC_UOM_ID(product.getC_UOM_ID());
		
		return retVal;
	}

}
