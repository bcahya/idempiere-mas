/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.X_UNS_MCU_Info;

/**
 * @author ITD-Andy
 *
 */
public class CalloutMCUInfo implements IColumnCallout {

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals(X_UNS_MCU_Info.COLUMNNAME_ValidFrom))
			return generateDateTo(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals(X_UNS_MCU_Info.COLUMNNAME_ValidTo))
			return checkDateTo(ctx, WindowNo, mTab, mField, value);
		
		return null;
	}

	private String generateDateTo(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		String retval = null;
		
		if(value==null)
			return "";
		
		Timestamp startDate = (Timestamp) value;
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(startDate.getTime());
		calendar.add(Calendar.MONTH, 6);
		mTab.setValue(X_UNS_MCU_Info.COLUMNNAME_ValidTo, 
				new Timestamp(calendar.getTimeInMillis()));
		return retval;
	}
	
	private String checkDateTo(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		String retval = null;
		
		if(value==null || 
				mTab.getValue(X_UNS_MCU_Info.COLUMNNAME_ValidFrom)==null)
			return "";
		
		Timestamp dateFrom = (Timestamp) mTab.getValue(X_UNS_MCU_Info.COLUMNNAME_ValidFrom);
		Timestamp dateTo = (Timestamp) mTab.getValue(X_UNS_MCU_Info.COLUMNNAME_ValidTo);
				
		if (dateFrom.after(dateTo))
			retval = "invalid date, DateFrom must be greater than DateTo!";
			
		return retval;
	}
}
