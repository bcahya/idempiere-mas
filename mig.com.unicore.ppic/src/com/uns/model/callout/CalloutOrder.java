/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;

import com.uns.model.MUNSSER;

/**
 * @author menjangan
 *
 */
public class CalloutOrder extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutOrder() {
		// TODO Auto-generated constructor stub
	}
	
	public String SER_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer UNS_SER_ID = (Integer) value;
		
		if (isCalloutActive())
			return "";
		
		if (null == UNS_SER_ID
				|| UNS_SER_ID.intValue() == 0)
			return "";
		
		MUNSSER mSER = new MUNSSER(ctx, UNS_SER_ID, null);
		mTab.setValue(I_C_Order.COLUMNNAME_C_BPartner_ID, mSER.getC_BPartnerSR_ID());
		
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
		//FIXME remove Callout for SER
//		if (I_C_Order.COLUMNNAME_UNS_SER_ID.equals(mField.getColumnName()))
//		{
//			retValue = SER_ID(ctx, WindowNo, mTab, mField, value);
//		}
		return retValue;
	}

}
