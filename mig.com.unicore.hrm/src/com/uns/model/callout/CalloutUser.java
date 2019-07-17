/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_User;

import com.uns.model.I_UNS_Employee;
import com.uns.model.X_UNS_Employee;

/**
 * @author eko
 *
 */
public class CalloutUser implements IColumnCallout {
	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		I_AD_User au = GridTabWrapper.create(mTab, I_AD_User.class);
		I_UNS_Employee unse = new X_UNS_Employee(ctx, au.getUNS_Employee_ID(), null);
		
		
		String  mphone = "Null";
		String hphone ="Null";
		int job = 0;
		
		if(mField.getColumnName().equals(I_AD_User.COLUMNNAME_UNS_Employee_ID))
		{
			mphone = unse.getMobilePhone();
			job = unse.getC_Job_ID();
			hphone = unse.getHomePhone();
		} 
		
		au.setPhone(mphone);
		au.setC_Job_ID(job);
		au.setPhone2(hphone);
		
		return null;
	}

}
