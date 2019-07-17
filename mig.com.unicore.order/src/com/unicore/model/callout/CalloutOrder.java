/**
 * 
 */
package com.unicore.model.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.MSysConfig;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.util.MessageBox;

import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.MUNSOrderGroup;

/**
 * @author setyaka
 *
 */
public class CalloutOrder extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutOrder() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		String retVal = null;
		if(mTab.getTableName().equals(MOrderLine.Table_Name) 
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_TargetLineNetAmt))
			retVal  = TargetLineNetAmt(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrderLine.Table_Name) 
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_DiscountAmt))
			retVal  = DiscountAmt(ctx, WindowNo, mTab, mField, value, oldValue);
		else if((mTab.getTableName().equals(MOrder.Table_Name) && mField.getColumnName().equals(MOrder.COLUMNNAME_Discount)) 
				|| (mTab.getTableName().equals(MOrder.Table_Name) && mField.getColumnName().equals(MOrder.COLUMNNAME_DiscountAmt)))
			retVal  = setAdditionalDiscount(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrderLine.Table_Name)
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_ProductValue))
			retVal = productKey(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrderLine.Table_Name)
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_Discount))
			retVal = Discount(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrderLine.Table_Name)
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_QtyBonuses))
			retVal = QtyBonuses(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrderLine.Table_Name)
				&& mField.getColumnName().equals(MOrderLine.COLUMNNAME_M_Product_ID))
			retVal = product(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals(MOrder.Table_Name) 
				&& mField.getColumnName().equals(MOrder.COLUMNNAME_UNS_OrderGroup_ID))
			retVal = OrderGroup(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return retVal;
	}

	private String setAdditionalDiscount(
			Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue) 
	{
		if (value == null)
			value = Env.ZERO;
		
		BigDecimal discountPersen = Env.ZERO;
		BigDecimal dValue = Env.ZERO;
		BigDecimal discountAmt = Env.ZERO;
		Object totalLinesObjt = mTab.getValue(MOrder.COLUMNNAME_TotalLines);
		BigDecimal totalLines = (totalLinesObjt == null)? Env.ZERO : (BigDecimal) totalLinesObjt;
		String columnToChange = null;
		
		if (mField.getColumnName().equals(MOrder.COLUMNNAME_Discount)) {
			discountPersen = (BigDecimal) value;
			discountAmt = totalLines.multiply(discountPersen.divide(Env.ONEHUNDRED, 10, BigDecimal.ROUND_HALF_DOWN));
			dValue = discountAmt;
			columnToChange = MOrder.COLUMNNAME_DiscountAmt;
		}
		else if (mField.getColumnName().equals(MOrder.COLUMNNAME_DiscountAmt) 
				&& totalLines.signum() != 0) {
			discountAmt = (BigDecimal) value;
			discountPersen = discountAmt.divide(totalLines, 10, BigDecimal.ROUND_HALF_DOWN);
			dValue = discountPersen.multiply(Env.ONEHUNDRED);
			columnToChange = MOrder.COLUMNNAME_Discount;
		}
		
		mTab.setValue(columnToChange, dValue);
		
		return null;
	}

	private String DiscountAmt(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue) {
		BigDecimal dicountAmt = (BigDecimal) value;
		BigDecimal qtyOrdered = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_QtyOrdered);
		BigDecimal priceList = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_PriceList);

		BigDecimal lineNetAmt = priceList.multiply(qtyOrdered);
		BigDecimal newLineNetAmt = lineNetAmt.subtract(dicountAmt);
		BigDecimal targetPrice = newLineNetAmt.divide(qtyOrdered, 2, RoundingMode.HALF_UP);
		BigDecimal discount = dicountAmt.divide(lineNetAmt, 2, RoundingMode.HALF_UP);
		discount = discount.multiply(Env.ONEHUNDRED);
		
		BigDecimal targetLineNetAmt = lineNetAmt.multiply(new BigDecimal(1.1)).setScale(2, RoundingMode.UP);
		
		mTab.setValue(MOrderLine.COLUMNNAME_Discount, discount);
		mTab.setValue(MOrderLine.COLUMNNAME_PriceEntered, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_PriceActual, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_TargetLineNetAmt, targetLineNetAmt);
		
		return null;
	}
	
//	private String secondDiscount(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value,
//			Object oldValue) {
//		BigDecimal totalLines = (BigDecimal) mTab.getValue(MOrder.COLUMNNAME_TotalLines);
//		BigDecimal divide = ((BigDecimal) value).divide(totalLines, 5, RoundingMode.HALF_UP);
//		BigDecimal inPerc = divide.multiply(Env.ONEHUNDRED);
//		
//		String sql = "SELECT Limit_Discount FROM C_BPartner WHERE C_BPartner_ID = ?";
//		BigDecimal limitDiscPerc = DB.getSQLValueBD(null, sql, (Integer) mTab.getValue(MOrder.COLUMNNAME_C_BPartner_ID));
//		if(inPerc.compareTo(limitDiscPerc) == 1)
//		{
//			mTab.fireDataStatusEEvent("Error",
//					"Over limit discount. Limit discount partner " + limitDiscPerc.setScale(2, RoundingMode.HALF_UP) + "%",
//					true);
//			BigDecimal secondDisc = ((BigDecimal) value).subtract((BigDecimal) oldValue);
//			BigDecimal oriDisc = (BigDecimal) mTab.getValue(MOrder.COLUMNNAME_DiscountAmt);
//			BigDecimal totalDisc = oriDisc.subtract(secondDisc);
//			mTab.setValue(MOrder.COLUMNNAME_DiscountAmt, totalDisc);
//			mTab.setValue(MOrder.COLUMNNAME_DiscountAmt2, Env.ZERO);
//		}
//		else
//		{
//			BigDecimal secondDisc = ((BigDecimal) value).subtract((BigDecimal) oldValue);
//			BigDecimal oriDisc = (BigDecimal) mTab.getValue(MOrder.COLUMNNAME_DiscountAmt);
//			BigDecimal totalDisc = secondDisc.add(oriDisc);
//			mTab.setValue(MOrder.COLUMNNAME_DiscountAmt, totalDisc);
//		}
//			
//		return null;
//	}

	private String TargetLineNetAmt(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue) {
		if(value == null)
			return "";
		
		BigDecimal targetLineNetAmt = (BigDecimal) value;
		BigDecimal qtyOrdered = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_QtyOrdered);
		BigDecimal targetAmtNonPPN = targetLineNetAmt.divide(new BigDecimal(1.1), 10, RoundingMode.HALF_UP);
		BigDecimal targetPrice = targetAmtNonPPN.divide(qtyOrdered, 2, RoundingMode.HALF_UP);
		
		BigDecimal priceList = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_PriceList);
		BigDecimal lineNetAmt = priceList.multiply(qtyOrdered);
		BigDecimal dicountAmt = lineNetAmt.subtract(targetLineNetAmt.divide(new BigDecimal(1.1), 2, RoundingMode.UP));
		
		mTab.setValue(MOrderLine.COLUMNNAME_PriceEntered, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_PriceActual, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_DiscountAmt, dicountAmt);
		
		return null;
	}
	
	//Adding By BurhaniAdam
	private String productKey(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(value == null)
			return "";
				
		int org = (Integer) mTab.getValue(X_C_OrderLine.COLUMNNAME_AD_Org_ID);
		if(org <= 0)
			return "";
		
		String sql = "SELECT p.M_Product_ID FROM M_Product p WHERE TRIM(UPPER(p.Value)) = TRIM(UPPER(?))"
				+ " AND EXISTS (SELECT 1 FROM UNS_Product_Division div WHERE div.M_Product_ID = p.M_Product_ID"
				+ " AND EXISTS (SELECT 1 FROM AD_OrgInfo oi WHERE oi.AD_Org_ID=? AND oi.Parent_Org_ID=div.Division_ID))";
		int idProduct = DB.getSQLValue(null, sql, value.toString(), org);
		if(idProduct > 0)
			mTab.setValue(MOrderLine.COLUMNNAME_M_Product_ID, idProduct);
		else
			return null;
		
		MOrder order = new MOrder(ctx, Env.getContextAsInt(ctx, windowNo, "C_Order_ID"), null);
		int day = MSysConfig.getIntValue(MSysConfig.CHECK_DUPLICATE_ORDER, 7);
		String check = "SELECT ARRAY_TO_STRING(ARRAY_AGG(o.DocumentNo), ',') FROM C_Order o"
				+ " WHERE o.C_BPartner_ID = ? AND o.C_BPartner_Location_ID = ?"
				+ " AND o.AD_Org_ID = ? AND o.DateOrdered >= ?::timestamp - ? AND DocStatus NOT IN ('VO', 'RE') AND EXISTS"
				+ " (SELECT 1 FROM C_OrderLine ol WHERE ol.C_Order_ID = o.C_Order_ID AND ol.M_Product_ID=?"
				+ " AND QtyPacked < QtyOrdered) AND o.C_Order_ID <> ?";
		String docNo = DB.getSQLValueString(null, check,
				new Object[]{order.getC_BPartner_ID(), order.getC_BPartner_Location_ID(), order.getAD_Org_ID(),
								order.getDateOrdered(), day, idProduct, order.get_ID()});
		if(!Util.isEmpty(docNo, true))
		{
			String msg = "Duplicate order has detected with no (" + docNo + ").";
			String title = Msg.getMsg(Env.getCtx(), "Notification");
			MessageBox.showMsg(order,
					Env.getCtx()
					, msg
					, title
					, MessageBox.ICONINFORMATION
					, MessageBox.ICONQUESTION);
		}
		
		check = "SELECT count(*) FROM C_OrderLine WHERE M_Product_ID = ? AND C_Order_ID = ?";
		int count = DB.getSQLValue(null, check, idProduct, order.get_ID());
		if(count > 1)
		{
			String msg = "Duplicate product in one order.";
			String title = Msg.getMsg(Env.getCtx(), "Notification");
			MessageBox.showMsg(order,
					Env.getCtx()
					, msg
					, title
					, MessageBox.ICONINFORMATION
					, MessageBox.ICONQUESTION);
		}
		
		return null;
	}
	
	private String Discount(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue) {
		BigDecimal discount = (BigDecimal) value;
		BigDecimal qtyOrdered = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_QtyOrdered);
		BigDecimal priceList = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_PriceList);

		BigDecimal lineNetAmt = priceList.multiply(qtyOrdered);
		BigDecimal discountAmt = lineNetAmt.multiply(discount);
		discountAmt = discountAmt.divide(Env.ONEHUNDRED);
		BigDecimal newLineNetAmt = lineNetAmt.subtract(discountAmt);
		BigDecimal targetPrice = newLineNetAmt.divide(qtyOrdered, 2, RoundingMode.HALF_UP);
		
		BigDecimal targetLineNetAmt = lineNetAmt.multiply(new BigDecimal(1.1)).setScale(2, RoundingMode.UP);
		
		mTab.setValue(MOrderLine.COLUMNNAME_DiscountAmt, discountAmt);
		mTab.setValue(MOrderLine.COLUMNNAME_PriceEntered, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_PriceActual, targetPrice);
		mTab.setValue(MOrderLine.COLUMNNAME_TargetLineNetAmt, targetLineNetAmt);
		
		return null;
	}
	
	private String QtyBonuses(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value,
			Object oldValue)
	{
		BigDecimal qtyBonus = (BigDecimal) value;
		if(value == null)
			return null;
		
		BigDecimal discountAmt = qtyBonus.multiply((BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_PriceActual));
		BigDecimal oldQtyOrdered = (BigDecimal) mTab.getValue(MOrderLine.COLUMNNAME_QtyOrdered);
		BigDecimal qtyOrdered = qtyBonus.add(oldQtyOrdered).subtract((BigDecimal) oldValue);
		mTab.setValue(MOrderLine.COLUMNNAME_QtyOrdered, qtyOrdered);
		
		return DiscountAmt(ctx, windowNo, mTab, mField, discountAmt, oldValue);
	}
	
	private String product(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return null;
		
		MOrder order = new MOrder(ctx, Env.getContextAsInt(ctx, windowNo, "C_Order_ID"), null);
		int day = MSysConfig.getIntValue(MSysConfig.CHECK_DUPLICATE_ORDER, 7);
		String check = "SELECT ARRAY_TO_STRING(ARRAY_AGG(o.DocumentNo), ',') FROM C_Order o"
				+ " WHERE o.C_BPartner_ID = ? AND o.C_BPartner_Location_ID = ?"
				+ " AND o.AD_Org_ID = ? AND o.DateOrdered >= ?::timestamp - ? AND DocStatus NOT IN ('VO', 'RE') AND EXISTS"
				+ " (SELECT 1 FROM C_OrderLine ol WHERE ol.C_Order_ID = o.C_Order_ID AND ol.M_Product_ID=?"
				+ " AND QtyPacked < QtyOrdered) AND o.C_Order_ID <> ?";
		String docNo = DB.getSQLValueString(null, check,
				new Object[]{order.getC_BPartner_ID(), order.getC_BPartner_Location_ID(), order.getAD_Org_ID(),
								order.getDateOrdered(), day, M_Product_ID, order.get_ID()});
		if(!Util.isEmpty(docNo, true))
		{
			String msg = "Duplicate order has detected with no (" + docNo + ").";
			String title = Msg.getMsg(Env.getCtx(), "Notification");
			MessageBox.showMsg(order,
					Env.getCtx()
					, msg
					, title
					, MessageBox.ICONINFORMATION
					, MessageBox.ICONQUESTION);
		}
		
		check = "SELECT count(*) FROM C_OrderLine WHERE M_Product_ID = ? AND C_Order_ID = ?";
		int count = DB.getSQLValue(null, check, M_Product_ID, order.get_ID());
		if(count > 1)
		{
			String msg = "Duplicate product in one order.";
			String title = Msg.getMsg(Env.getCtx(), "Notification");
			MessageBox.showMsg(order,
					Env.getCtx()
					, msg
					, title
					, MessageBox.ICONINFORMATION
					, MessageBox.ICONQUESTION);
		}
		
		return null;
	}
	
	private String OrderGroup(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(value == null || (Integer) value == 0)
			return null;
		
		MUNSOrderGroup group = new MUNSOrderGroup(ctx, (Integer) value, null);
		I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		order.setC_BPartner_ID(group.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(group.getC_BPartner_Location_ID());
		order.setDateOrdered(group.getDateOrdered());
		order.setDatePromised(group.getDateOrdered());
		order.setPaymentRule(group.getPaymentRule());
		order.setC_DocType_ID(order.getC_DocType_ID());
		order.setC_DocTypeTarget_ID(order.getC_DocType_ID());
		order.setSalesRep_ID(group.getSalesRep_ID());
		
		return null;
	}
}