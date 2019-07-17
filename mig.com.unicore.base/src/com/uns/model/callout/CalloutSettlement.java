/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;

import com.uns.model.I_UNS_Charge_RS;
import com.uns.model.MUNSChargeRS;

/**
 * @author root
 *
 */
public class CalloutSettlement implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutSettlement() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals(MUNSChargeRS.COLUMNNAME_Reference_ID))
			return onReferenceCange(ctx, WindowNo, mTab, mField, value, oldValue);
		if(mField.getColumnName().equals(MUNSChargeRS.COLUMNNAME_UNS_Shipping_ID))
			return onShipping(ctx, WindowNo, mTab, mField, value, oldValue);
		if(mField.getColumnName().equals(MUNSChargeRS.COLUMNNAME_C_BPartner_ID))
			return onBPartner(ctx, WindowNo, mTab, mField, value, oldValue);
		return null;
	}
	
	private String onReferenceCange(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		I_UNS_Charge_RS settlement = GridTabWrapper.create(mTab, I_UNS_Charge_RS.class);
		if(null == value)
		{
			settlement.setC_BPartner_ID(-1);
			settlement.setDateTrx(null);
			return null;
		}
		
		MUNSChargeRS cs = (MUNSChargeRS) settlement.getReference();
		settlement.setC_BPartner_ID(cs.getC_BPartner_ID());
		return null;
	}
	
	public String onShipping(Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if(value == null)
			return null;
	
		int ship_ID = 0;
		try {
			ship_ID = (Integer) value;
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		if(ship_ID > 0)
		{
			mTab.setValue(MUNSChargeRS.COLUMNNAME_C_BPartner_ID, DB.getSQLValue(null, "SELECT C_BPartner_ID FROM UNS_Shipping"
					+ " WHERE UNS_Shipping_ID = ?", ship_ID));
			mTab.setValue(MUNSChargeRS.COLUMNNAME_Name, DB.getSQLValueString(null, "SELECT COALESCE(Name,'') FROM UNS_Shipping"
					+ " WHERE UNS_Shipping_ID = ?", ship_ID));
//			mTab.setValue(MUNSChargeRS.COLUMNNAME_AD_Org_ID, DB.getSQLValue(null, "SELECT AD_Org_ID FROM UNS_Shipping"
//					+ " WHERE UNS_Shipping_ID = ?", ship_ID));
			mTab.setValue(MUNSChargeRS.COLUMNNAME_DateTrx, DB.getSQLValueTSEx(null, "SELECT DateDoc FROM UNS_Shipping"
					+ " WHERE UNS_Shipping_ID = ?", ship_ID));
		}
		return null;
	}
	
	public String onBPartner(Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		boolean isMustSame = MSysConfig.getBooleanValue(MSysConfig.SET_ORG_WITH_ORG_EMPLOYEE_ON_SETTLEMENT, false);
		if(!isMustSame)
			return null;
		
		if(value == null || (Integer) value == 0)
			return null;
		
		String sql = "SELECT emp.AD_Org_ID FROM UNS_Employee emp"
				+ " INNER JOIN AD_User us ON us.UNS_Employee_ID = emp.UNS_Employee_ID"
				+ " WHERE us.C_BPartner_ID = ? AND us.IsActive = 'Y' AND emp.IsActive = 'Y'";
		int org = DB.getSQLValue(null, sql, (Integer) value);
		
		if(org <= 0)
			return null;
		else
			mTab.setValue(MUNSChargeRS.COLUMNNAME_AD_Org_ID, org);
		
		return null;
	}
}