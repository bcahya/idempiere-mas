/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPricing;
import org.compiere.model.X_C_Order;

import com.uns.model.MProduct;

import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;
import com.uns.model.I_UNS_SO_Amendment;
import com.uns.model.I_UNS_SO_AmendmentLine;
import com.uns.model.MUNSSOAmendment;
import com.uns.model.MUNSSOAmendmentLine;

/**
 * @author menjangan
 *
 */
public class CalloutSOAmendment implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutSOAmendment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		String retValue = null;
		
		if (mField.getColumnName().equals(MUNSSOAmendment.COLUMNNAME_C_Order_ID))
			C_Order_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSSOAmendmentLine.COLUMNNAME_C_OrderLine_ID))
			C_OrderLine_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSSOAmendmentLine.COLUMNNAME_M_Product_ID))
			M_Product_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSSOAmendmentLine.COLUMNNAME_NewPrice))
			newPrice(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSSOAmendmentLine.COLUMNNAME_Quantity))
			newQty(ctx, WindowNo, mTab, mField, value, oldValue);
		return retValue;
	}
	
	private String newQty(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return null;
		BigDecimal qty = (BigDecimal)value;
		I_UNS_SO_AmendmentLine thisTab = GridTabWrapper.create(mTab, I_UNS_SO_AmendmentLine.class);
		BigDecimal price = thisTab.getNewPrice();
		BigDecimal newAmt = qty.multiply(price);
		thisTab.setNewAmt(newAmt);
		return null;
	} 
	
	private String newPrice(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return null;
		BigDecimal price = (BigDecimal)value;
		I_UNS_SO_AmendmentLine thisTab = GridTabWrapper.create(mTab, I_UNS_SO_AmendmentLine.class);
		BigDecimal qty = thisTab.getQuantity();
		BigDecimal newAmt = qty.multiply(price);
		thisTab.setNewAmt(newAmt);
		return null;
	}
	
	private String M_Product_ID(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer M_Product_ID = (Integer)value;
		MProduct product = new MProduct(ctx, M_Product_ID, null);
		GridTab parenTab = mTab.getParentTab();
		I_UNS_SO_Amendment parent = GridTabWrapper.create(parenTab, I_UNS_SO_Amendment.class);
		I_UNS_SO_AmendmentLine thisTab = GridTabWrapper.create(mTab, I_UNS_SO_AmendmentLine.class); 
		MOrder order = new MOrder(ctx, parent.getC_Order_ID(), null);
		MPriceList priceList = (MPriceList) order.getM_PriceList();
		MProductPricing pp = new MProductPricing(
				M_Product_ID, order.getC_BPartner_ID(), thisTab.getQuantity(),true, order.getC_BPartner_Location_ID());
		pp.setM_PriceList_ID(priceList.get_ID());
		pp.setM_PriceList_Version_ID(priceList.getPriceListVersion(order.getDateOrdered()).get_ID());
		BigDecimal newPrice = pp.getPriceList();
		thisTab.setNewPrice(newPrice);
		thisTab.setC_UOM_ID(product.getC_UOM_ID());
		return null;
	}
	
	private String C_OrderLine_ID(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer C_OrderLine_ID = (Integer)value;
		MOrderLine orderLine = new MOrderLine(ctx, C_OrderLine_ID, null);
		I_UNS_SO_AmendmentLine amendmentLine = GridTabWrapper.create(mTab, I_UNS_SO_AmendmentLine.class);
		amendmentLine.setPrevQuantity(orderLine.getQtyOrdered());
		amendmentLine.setPrevPrice(orderLine.getPriceActual());
		amendmentLine.setPrevAmt(orderLine.getLineNetAmt());
		amendmentLine.setM_Product_ID(orderLine.getM_Product_ID());
		amendmentLine.setC_UOM_ID(orderLine.getC_UOM_ID());
		return null;
	}

	private String C_Order_ID(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		
		Integer C_Order_ID = (Integer)value;
		X_C_Order order = new X_C_Order(ctx, C_Order_ID, null);
		mTab.setValue(MUNSSOAmendment.COLUMNNAME_DateOrdered, order.getDateOrdered());
		
		return null;
	}
}
