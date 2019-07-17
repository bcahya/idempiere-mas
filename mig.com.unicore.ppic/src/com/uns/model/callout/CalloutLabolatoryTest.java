package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.AdempiereUserError;

import com.uns.model.GeneralCustomization;
import com.uns.qad.model.MUNSQAStatusNutLabTest;
import com.uns.qad.model.MUNSQAStatusType;

public class CalloutLabolatoryTest extends CalloutEngine implements
		IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		
		if (value==null || value.equals(""))
			return retValue;
		
		if (mField.getColumnName().equals(MUNSQAStatusNutLabTest.COLUMNNAME_CoconutType))
			retValue = getQAType(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return retValue;
	}

	private String getQAType(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String strValue = ((String) value).substring(0, 1);
		int type_id = 0;
		if(strValue.equals("A"))
			type_id = GeneralCustomization.get_ID(null, MUNSQAStatusType.Table_Name, 
					MUNSQAStatusType.COLUMNNAME_Name, "LIKE", "KB A");
		else
			type_id = GeneralCustomization.get_ID(null, MUNSQAStatusType.Table_Name, 
					MUNSQAStatusType.COLUMNNAME_Name, "LIKE", "KB B");
		
		if (type_id<1)
			throw new AdempiereUserError("Please make configuration at Type of QA Attribute.");
		
		mTab.setValue(MUNSQAStatusNutLabTest.COLUMNNAME_UNS_QAStatus_Type_ID, type_id);
		return null;
	}

}
