/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

import com.uns.model.MUNSEmployeeAtt;

/**
 * @author MuhAmin
 *
 */
public class CalloutEmployeeAtt implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String msg = null;
		if (mField.getColumnName().equals(MUNSEmployeeAtt.COLUMNNAME_UNS_Employee_ID))
		{
			msg = OnEmployee(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return msg;
	
	}
	private String OnEmployee(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String SQL = "SELECT name From UNS_Employee where"
				+ " UNS_Employee_ID=?";
		String name = DB.getSQLValueString(null, SQL, value);
		mTab.setValue(MUNSEmployeeAtt.COLUMNNAME_Name, name);
		
		return null;
	}

}