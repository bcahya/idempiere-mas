/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;

import com.uns.base.model.MOrderLine;
import com.uns.model.X_UNS_ContainerDeparture;
import com.uns.model.X_UNS_ContainerManagement;
import com.uns.model.X_UNS_SOShipment;
import com.uns.model.X_UNS_ShipmentSchedule;

/**
 * @author YAKA
 *
 */
public class CalloutShipmentSchedule extends CalloutEngine implements
		IColumnCallout {

	/**
	 * 
	 */
	public String bPartner(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_BPartner_ID = (Integer) value;
		
		if (isCalloutActive())
			return "";
		
		if (null == C_BPartner_ID || C_BPartner_ID.intValue() <= 0)
			return "";
		
		MBPartner bp = new MBPartner(ctx, C_BPartner_ID, null);
		MBPartnerLocation[] bpLoc = MBPartnerLocation.getForBPartner(ctx, C_BPartner_ID, null);
		
		//set value
		mTab.setValue(X_UNS_ShipmentSchedule.COLUMNNAME_DropShip_BPartner_ID, bp.getC_BPartner_ID());
		mTab.setValue(X_UNS_ShipmentSchedule.COLUMNNAME_DropShip_Location_ID, bpLoc[0].getC_BPartner_Location_ID());

		return "";
	}
	
	/**
	 * 
	 */
	public String salesOrderLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_OrderLine_ID = (Integer) value;
		
		if (isCalloutActive())
			return "";
		
		if (null == C_OrderLine_ID || C_OrderLine_ID.intValue() <= 0)
		{
			mTab.setValue(X_UNS_SOShipment.COLUMNNAME_M_Product_ID, 0);
			mTab.setValue(X_UNS_SOShipment.COLUMNNAME_QtyUom, 0);
			mTab.setValue(X_UNS_SOShipment.COLUMNNAME_QtyMT, 0);
			mTab.setValue(X_UNS_SOShipment.COLUMNNAME_RemarkSO, "");
			mTab.setValue(X_UNS_SOShipment.COLUMNNAME_RemarkProduct, "");
			return "";
		}
		
		MOrderLine ol = new MOrderLine(ctx, C_OrderLine_ID, null);
		MAttributeSetInstance asi = MAttributeSetInstance.get(ctx, ol.getM_AttributeSetInstance_ID(), ol.getM_Product_ID());
		//set value
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_M_Product_ID, ol.getM_Product_ID());
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_C_UOM_ID, ol.getC_UOM_ID());
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_QtyUom, ol.getQtyEntered());
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_QtyMT, 
					  ol.getQtyEntered().multiply(ol.getM_Product().getWeight().multiply(new BigDecimal(0.001))));
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_RemarkSO, ol.getDescription());
		mTab.setValue(X_UNS_SOShipment.COLUMNNAME_RemarkProduct, asi.getDescription());

		return "";
	}
	
	/**
	 * 
	 */
	public String containerManagement(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer UNS_ContainerManagement_ID = (Integer) value;
		
		if (isCalloutActive())
			return "";
		
		if (null == UNS_ContainerManagement_ID || UNS_ContainerManagement_ID.intValue() <= 0)
			return "";
		
		X_UNS_ContainerManagement cm = new X_UNS_ContainerManagement(ctx, UNS_ContainerManagement_ID, null);
		
		//set value
		mTab.setValue(X_UNS_ContainerDeparture.COLUMNNAME_QAStatus, cm.getLastQAStatus());
		
		return "";
	}


	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (X_UNS_ShipmentSchedule.COLUMNNAME_C_BPartner_ID.equals(mField.getColumnName()))
		{
			return bPartner(ctx, WindowNo, mTab, mField, value);
		} else if (X_UNS_SOShipment.COLUMNNAME_C_OrderLine_ID.equals(mField.getColumnName())){
			return salesOrderLine(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}

}
