/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;

import com.uns.model.X_UNS_UnloadingCostLine;

/**
 * @author Burhani Adam
 *
 */
public class CalloutUnloadingCost implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals(X_UNS_UnloadingCostLine.COLUMNNAME_M_InOut_ID))
			return onInOutChange(ctx, WindowNo, mTab, mField, value, oldValue);
					
		return null;
	}
	
	public String onInOutChange(Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if(value == null || (Integer) value <=0)
			return null;
		int M_InOut_ID = (Integer) value;
		MInOut io = new MInOut(ctx, M_InOut_ID, null);
		if(io.getC_Order_ID() <=0)
			throw new AdempiereException("Receipt document not linked with anything PO");
		
		mTab.setValue(X_UNS_UnloadingCostLine.COLUMNNAME_C_Order_ID, io.getC_Order_ID());
		
		return null;
	}
}