package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSOtherCondition;
import com.uns.model.X_UNS_OrderCondition;

public class CalloutSOCondition extends CalloutEngine implements IColumnCallout{

	public String orderCondition(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer Other_ID = (Integer)value;
		if (Other_ID == null || Other_ID.intValue() == 0)
			return "";
		
		int id = Other_ID.intValue();
		mTab.setValue(X_UNS_OrderCondition.COLUMNNAME_Name, "n Copies");
		mTab.setValue(X_UNS_OrderCondition.COLUMNNAME_Description, MUNSOtherCondition.getDescription(ctx, id));

		return "";
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (X_UNS_OrderCondition.COLUMNNAME_UNS_OtherCondition_ID.equals(mField.getColumnName()))
		{
			retValue = orderCondition(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}
}
