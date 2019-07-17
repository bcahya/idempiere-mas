/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;

import com.unicore.model.I_UNS_ImportSimpleColumn;

/**
 * @author AzHaidar
 *
 */
public class SimpleImportXLSCallout extends CalloutEngine implements
		IColumnCallout {

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		String retVal = null;
		
		if (I_UNS_ImportSimpleColumn.COLUMNNAME_AD_Column_ID.equals(mField.getColumnName()))
		{
			retVal = setColumnReference(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retVal;
	}
	
	private String setColumnReference(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		String retVal = null;
		
		I_UNS_ImportSimpleColumn column = GridTabWrapper.create(mTab, I_UNS_ImportSimpleColumn.class);
		I_AD_Column ADColumn = column.getAD_Column();
		if (ADColumn != null)
			column.setAD_Reference_ID(ADColumn.getAD_Reference_ID());
		
		return retVal;
	}

}
