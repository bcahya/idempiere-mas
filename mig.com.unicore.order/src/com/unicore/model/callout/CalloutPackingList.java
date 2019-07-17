/**
 * 
 */
package com.unicore.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.util.DB;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.X_UNS_PL_ConfirmProduct;
import com.unicore.model.X_UNS_PackingList_Line;
import com.unicore.model.X_UNS_PackingList_Order;

/**
 * @author UNTA-Andy
 * 
 */
public class CalloutPackingList implements IColumnCallout
{

	/**
	 * 
	 */
	public CalloutPackingList()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int,
	 * org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue)
	{
		String retValue = null;

		if (mField.getColumnName().equals(X_UNS_PackingList_Order.COLUMNNAME_C_Order_ID))
		{
			retValue = this.calloutOrder(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_PackingList_Line.COLUMNNAME_C_OrderLine_ID))
		{
			retValue = this.calloutOrderLine(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered))
		{
			retValue = this.calloutQtyEntered(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_PL_ConfirmProduct.COLUMNNAME_ConfirmedQty))
		{
			retValue = this.calloutConfirmedQty(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_PackingList_Order.COLUMNNAME_C_Invoice_ID))
		{
			retValue = this.calloutInvoice(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_PackingList_Line.COLUMNNAME_C_InvoiceLine_ID))
		{
			retValue = this.calloutInvoiceLine(ctx, WindowNo, mTab, mField, value);
		}
		return retValue;
	}

	private String calloutOrder(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value || (Integer) value <= 0)
		{
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_SalesRep_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_UNS_Rayon_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_M_Warehouse_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_AD_Org_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_Description, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_C_Invoice_ID, null);
			return "";
		}
		
		MOrder order = new MOrder(ctx, (Integer) value, null);
		MBPartnerLocation bpLocation = new MBPartnerLocation(ctx, order.getC_BPartner_Location_ID(), null);
		
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_SalesRep_ID, order.getSalesRep_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_UNS_Rayon_ID, bpLocation.getUNS_Rayon_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_M_Warehouse_ID, order.getM_Warehouse_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_AD_Org_ID, order.getAD_Org_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_Description, order.getDescription());
		if(order.getC_DocTypeTarget().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_CashOrder))
		{
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_C_Invoice_ID, order.getC_Invoice_ID() <= 0 ?
					null : order.getC_Invoice_ID());
		}
		else if(order.getC_DocTypeTarget().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_PrepayOrder))
		{
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_C_Invoice_ID, order.getC_Invoice_ID() <= 0 ?
					null : order.getC_Invoice_ID());
		}

		return null;
	}

	private String calloutQtyEntered(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value)
			return "";

		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, (BigDecimal) value);
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, (BigDecimal) value);

		return null;
	}

	private String calloutOrderLine(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value)
		{
			mTab.setValue("AD_Org_ID", null);
			mTab.setValue(MUNSPackingListLine.COLUMNNAME_C_InvoiceLine_ID, null);	
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_M_Product_ID, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_C_UOM_ID, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, null);
			return "";
		}

		MOrderLine line = new MOrderLine(ctx, (Integer) value, null);
		
		int C_InvoiceLine_ID = DB.getSQLValue(null, "SELECT C_InvoiceLine_ID FROM C_InvoiceLine WHERE C_OrderLine_ID = ?", line.get_ID());
		if(line.getParent().getC_DocTypeTarget().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_CashOrder))
		{
			mTab.setValue(MUNSPackingListLine.COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);	
		}
		
		mTab.setValue("AD_Org_ID", line.getAD_Org_ID());
		String sql = "SELECT UNS_PackingList_Line_ID FROM UNS_PackingList_Line WHERE C_OrderLine_ID = " + line.get_ID();
		
		if (DB.getSQLValue(null, sql) >= 1)
		{
			MUNSPackingListLine lineOld = new MUNSPackingListLine(ctx, DB.getSQLValue(null, sql), null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_M_Product_ID, line.getM_Product_ID());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_C_UOM_ID, line.getC_UOM_ID());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered, line.getQtyEntered().subtract(lineOld.getQtyEntered()));
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, line.getQtyEntered().subtract(lineOld.getQtyEntered()));
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, line.getQtyEntered().subtract(lineOld.getQtyEntered()));
		}
		
		if (DB.getSQLValue(null, sql) <= -1)
		{
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_M_Product_ID, line.getM_Product_ID());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_C_UOM_ID, line.getC_UOM_ID());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered, line.getQtyOrdered());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, line.getQtyOrdered());
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, line.getQtyOrdered());
		}

		return "";
	}

	private String calloutConfirmedQty(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value)
			return "";

		mTab.setValue(X_UNS_PL_ConfirmProduct.COLUMNNAME_DifferenceQty, ((BigDecimal) mTab
				.getValue(X_UNS_PL_ConfirmProduct.COLUMNNAME_TargetQty)).subtract((BigDecimal) value));
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, (BigDecimal) value);

		return "";
	}
	
	
	private String calloutInvoice(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value || (Integer) value <= 0)
		{
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_SalesRep_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_UNS_Rayon_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_M_Warehouse_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_AD_Org_ID, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_Description, null);
			mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_C_Invoice_ID, null);
			return "";
		}
		
		MInvoice invoice = new MInvoice(ctx, (Integer) value, null);
		MBPartnerLocation bpLocation = new MBPartnerLocation(ctx, invoice.getC_BPartner_Location_ID(), null);
		
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_SalesRep_ID, invoice.getSalesRep_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_UNS_Rayon_ID, bpLocation.getUNS_Rayon_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_AD_Org_ID, invoice.getAD_Org_ID());
		mTab.setValue(X_UNS_PackingList_Order.COLUMNNAME_Description, invoice.getDescription());
		return null;
	}
	
	
	private String calloutInvoiceLine(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value)
	{
		if (null == value)
		{
			mTab.setValue("AD_Org_ID", null);
			mTab.setValue(MUNSPackingListLine.COLUMNNAME_C_OrderLine_ID, null);	
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_M_Product_ID, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_C_UOM_ID, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, null);
			mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, null);
			return "";
		}

		MInvoiceLine line = new MInvoiceLine(ctx, (Integer) value, null);
		
		int C_OrderLine_ID = DB.getSQLValue(null, "SELECT C_OrderLine_ID FROM C_InvoiceLine WHERE C_InvoiceLine_ID = ?", line.get_ID());
		if(C_OrderLine_ID > 0)
		{
			mTab.setValue("C_OrderLine_ID", C_OrderLine_ID);
		}
		
		mTab.setValue("AD_Org_ID", line.getAD_Org_ID());
		String sql = "SELECT COALESCE(SUM(MovementQty), 0) FROM M_InOutLine WHERE M_Product_ID = ? AND M_InOut_ID IN "
				+ " ( SELECT M_InOut_ID FROM M_InOut WHERE C_Invoice_ID = ?)";
		BigDecimal totalShipQty = DB.getSQLValueBD(null, sql, line.getM_Product_ID(), line.getC_Invoice_ID());
		BigDecimal currentQty = line.getQtyInvoiced().subtract(totalShipQty);
	
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_M_Product_ID, line.getM_Product_ID());
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_C_UOM_ID, line.getC_UOM_ID());
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_QtyEntered, currentQty);
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_TargetQty, currentQty);
		mTab.setValue(X_UNS_PackingList_Line.COLUMNNAME_MovementQty, currentQty);

		return "";
	}

}
