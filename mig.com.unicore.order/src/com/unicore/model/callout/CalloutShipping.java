/**
 * 
 */
package com.unicore.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.uns.model.MUNSArmada;

import com.unicore.model.I_UNS_Shipping;
import com.unicore.model.MUNSShipping;
import com.unicore.model.MUNSShippingFreight;
import com.unicore.model.X_UNS_Shipping;

/**
 * @author ***********
 *
 */
public class CalloutShipping implements IColumnCallout
{

	/**
	 * 
	 */
	public CalloutShipping()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue)
	{		
		switch (mField.getColumnName()) {
			case
				MUNSShipping.COLUMNNAME_UNS_Armada_ID :
				return
				calloutArmada(ctx, WindowNo, mTab, mField, value);
			case
				MUNSShipping.COLUMNNAME_UNS_Employee_ID :
				return
				onDriver_ID(ctx, WindowNo, mTab, mField, value, oldValue);
			case
				MUNSShipping.COLUMNNAME_Helper1_ID :
				return
				onHelper1_ID(ctx, WindowNo, mTab, mField, value, oldValue);
			case
				MUNSShipping.COLUMNNAME_Helper2_ID :
				return
				onHelper2_ID(ctx, WindowNo, mTab, mField, value, oldValue);
			case
				MUNSShipping.COLUMNNAME_Helper3_ID :
				return
				onHelper3_ID(ctx, WindowNo, mTab, mField, value, oldValue);
			case
				MUNSShipping.COLUMNNAME_UNS_Shipping_Reff_ID :
				return
				onShippingReferenceChange(ctx, WindowNo, mTab, mField, value, oldValue);
			case
				MUNSShippingFreight.COLUMNNAME_UNS_PackingList_ID :
				return
				UNS_PackingList_ID(ctx, WindowNo, mTab, mField, value, oldValue);
			default : 
				return null;
		}
	}
	
	private String onShippingReferenceChange(Properties ctx, int WindowNo, GridTab mTab
			, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			return null;
		}
		
		MUNSShipping shipReff = new MUNSShipping(ctx, (Integer) value, null);
		I_UNS_Shipping shipping = GridTabWrapper.create(mTab, I_UNS_Shipping.class);
		String status = mTab.get_ValueAsString("Status");
		
		shipping.setAD_Org_ID(shipReff.getAD_Org_ID());
		shipping.setName(shipReff.getName());
		shipping.setUNS_Armada_ID(shipReff.getUNS_Armada_ID());
		if(shipReff.getUNS_Employee_ID() > 0)
			shipping.setUNS_Employee_ID(shipReff.getUNS_Employee_ID());
		shipping.setC_BPartner_ID(shipReff.getC_BPartner_ID());
		shipping.setShippingType(shipReff.getShippingType());
		shipping.setPaymentTerm(shipReff.getPaymentTerm());
		shipping.setC_DocType_ID(shipReff.getC_DocType_ID());
		shipping.setUseCity(shipReff.isUseCity());
		
		if(status.equals(X_UNS_Shipping.STATUS_AdditionalShipping))
		{
			if(shipReff.getDestination_ID() > 0)
				shipping.setOrigin_ID(shipReff.getDestination_ID());
			shipping.setDestination_ID(-1);
		}
		else
		{
			if (shipReff.getDestination_ID() > 0)
				shipping.setOrigin_ID(shipReff.getDestination_ID());
			if (shipReff.getOrigin_ID() > 0)
				shipping.setDestination_ID(shipReff.getOrigin_ID());
			if(shipReff.getUNS_Rayon_ID() > 0)
				shipping.setUNS_Rayon_ID(shipReff.getUNS_Rayon_ID());
		}
		
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	private String calloutArmada(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if(null == value)
			return "";
		
		MUNSArmada ar = new MUNSArmada(ctx, (Integer) value, null);

		mTab.setValue(X_UNS_Shipping.COLUMNNAME_AD_Org_ID, ar.getAD_Org_ID());
		mTab.setValue(X_UNS_Shipping.COLUMNNAME_PrevOM, ar.getLastOM());
		mTab.setValue(X_UNS_Shipping.COLUMNNAME_UNS_Employee_ID, ar.getUNS_Employee_ID());
		
		return onDriver_ID(ctx, windowNo, mTab, mField, ar.getUNS_Employee_ID(), null);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onDriver_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(MUNSShipping.COLUMNNAME_Driver, null);
			return null;
		}
		
		String name = DB.getSQLValueString(
				null, "SELECT Name FROM UNS_Employee WHERE UNS_Employee_ID = ?"
				, (Integer) value);
		int c_BPartner_ID = DB.getSQLValue(
				null, "SELECT C_BPartner_ID FROM AD_User WHERE UNS_Employee_ID = ?"
				, (Integer) value);

		mTab.setValue(MUNSShipping.COLUMNNAME_Driver, name);
		if(c_BPartner_ID <= 0)
		{
			mTab.fireDataStatusEEvent("EmployeeNotLinkedToBP",
					"Employee has not linked to Business Partner",
					false);
		}
		
		mTab.setValue(MUNSShipping.COLUMNNAME_C_BPartner_ID, c_BPartner_ID);
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onHelper1_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(MUNSShipping.COLUMNNAME_Helper1, null);
			return null;
		}
		
		String name = DB.getSQLValueString(
				null, "SELECT Name FROM UNS_Employee WHERE UNS_Employee_ID = ?"
				, (Integer) value);
		mTab.setValue(MUNSShipping.COLUMNNAME_Helper1, name);
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onHelper2_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(MUNSShipping.COLUMNNAME_Helper2, null);
			return null;
		}
		
		String name = DB.getSQLValueString(
				null, "SELECT Name FROM UNS_Employee WHERE UNS_Employee_ID = ?"
				, (Integer) value);
		mTab.setValue(MUNSShipping.COLUMNNAME_Helper2, name);
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String onHelper3_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(MUNSShipping.COLUMNNAME_Helper3, null);
			return null;
		}
		
		String name = DB.getSQLValueString(
				null, "SELECT Name FROM UNS_Employee WHERE UNS_Employee_ID = ?"
				, (Integer) value);
		mTab.setValue(MUNSShipping.COLUMNNAME_Helper3, name);
		return null;
	}
	
	private String UNS_PackingList_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value || (Integer) value <= 0)
			return null;
		
		int UNS_PackingList_ID = (Integer) value;
		
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(DocumentNo), ',') FROM UNS_Shipping sh"
				+ " WHERE sh.DocStatus NOT IN ('VO', 'RE') AND EXISTS"
				+ " (SELECT 1 FROM UNS_ShippingFreight sf WHERE sf.UNS_Shipping_ID = sh.UNS_Shipping_ID"
				+ " AND sf.UNS_PackingList_ID=?)";
		String values = DB.getSQLValueString(null, sql, UNS_PackingList_ID);
		if(!Util.isEmpty(values, true))
			mTab.fireDataStatusEEvent("", "Packing List already exists in shipping (" + values + ")", false);
		
		return null;
	}
}
