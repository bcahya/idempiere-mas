/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;

import com.uns.model.I_UNS_LC_AmendmentLine;
import com.uns.model.MUNSLCAmendmentLine;
import com.uns.model.MUNSLCLines;

/**
 * @author menjangan
 *
 */
public class CalloutLCAmendmentLine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutLCAmendmentLine() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		String retValue = null;
		if(mField.getColumnName().equals(MUNSLCAmendmentLine.COLUMNNAME_UNS_LC_Lines_ID))
			retValue = LCLine(ctx, WindowNo, mTab, mField, value, oldValue);
		if(mField.getColumnName().equals(MUNSLCAmendmentLine.COLUMNNAME_M_Product_Category_ID))
			retValue = Product(ctx, WindowNo, mTab, mField, value, oldValue);
		return retValue;
	}
	
	protected String Product(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer M_Product_ID = (Integer)value;
		I_UNS_LC_AmendmentLine lcAmdLine = GridTabWrapper.create(mTab, I_UNS_LC_AmendmentLine.class);
		MProduct product = MProduct.get(ctx, M_Product_ID);
		lcAmdLine.setC_UOM_ID(product.getC_UOM_ID());
		return null;
	}
	
	protected String LCLine(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer lcLine_ID = (Integer)value;
		MUNSLCLines lcLine = new MUNSLCLines(ctx, lcLine_ID, null);
		I_UNS_LC_AmendmentLine lcAmdLine = GridTabWrapper.create(mTab, I_UNS_LC_AmendmentLine.class);
		MProduct product = MProduct.get(ctx, lcLine.getM_Product_Category_ID());
		lcAmdLine.setC_UOM_ID(product.getC_UOM_ID());
		lcAmdLine.setM_Product_Category_ID(lcLine.getM_Product_Category_ID());
		lcAmdLine.setPrevLCQuantity(lcLine.getLCQuantity());
		return null;
	}

}
