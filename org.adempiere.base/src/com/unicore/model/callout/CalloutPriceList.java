/**
 * 
 */
package com.unicore.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.X_M_PriceList;
import org.compiere.model.X_M_ProductPrice;
import org.compiere.util.Env;

import com.unicore.util.UNSCustomCalcConvertion;

/**
 * @author Toriq
 *
 */
public class CalloutPriceList implements IColumnCallout {
		
	/**
	 * 
	 */
	public CalloutPriceList() {
		// TODO Auto-generated constructor stub
	}
	
	public String priceList(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		mTab.setValue(X_M_ProductPrice.COLUMNNAME_PriceStd, (BigDecimal) value );
		mTab.setValue(X_M_ProductPrice.COLUMNNAME_PriceLimit,(BigDecimal) value);
		return "";
	}
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String retValue = null;
		
		if (X_M_ProductPrice.COLUMNNAME_PriceList.equals(mField.getColumnName()))
		{
			retValue = priceList(ctx, WindowNo, mTab, mField, value);
		}
		return retValue;
	}

}
