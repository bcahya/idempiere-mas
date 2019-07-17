/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;

import com.uns.model.X_UNS_Production_WRAttributes;

/**
 * @author ALBURHANY
 *
 */
public class CalloutProductionWRAttributes implements IColumnCallout{

	/**
	 * 
	 */
	public CalloutProductionWRAttributes() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
	
		String retValue = null;
		
		if (X_UNS_Production_WRAttributes.COLUMNNAME_UPC.equals(mField.getColumnName()))
		{
			retValue = upc(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}
	
	public String upc (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String UPC = (String)value;
		
		if (UPC == null || UPC.equals(0))
			return "";
		
		String upcCodeParts = UPC.replaceAll("[a-zA-Z]+[0-9]+", "");
		int M_Product_ID = MProduct.getIDByUPC(null, upcCodeParts, true, null);
		
		mTab.setValue("M_Product_ID", M_Product_ID);
		mTab.setValue("ProductionQty", 1);
		
		return "";
	}
	
	public String clear (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		mTab.setValue(mField.getColumnName(), null);
		return "";
	}

}
