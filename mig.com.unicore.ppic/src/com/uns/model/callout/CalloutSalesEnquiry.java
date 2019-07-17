/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MStorageOnHand;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.I_UNS_SERLineProduct;
import com.uns.model.MUNSSERLineProduct;

/**
 * @author menjangan
 *
 */
public class CalloutSalesEnquiry extends CalloutEngine implements
		IColumnCallout {
	
	/**
	 * 
	 */
	private static final BigDecimal MULTIPLY_KG_TO_MT = new BigDecimal(0.001);

	/**
	 * 
	 */
	public CalloutSalesEnquiry() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String Product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer) value;
		
		if (isCalloutActive())
			return "";
		
		if (null == M_Product_ID ||
				M_Product_ID.intValue() <= 0)
			return "";
		
		//TODO Add C_B_PartnerLocation_ID on the SER
		int C_BPartner_Location_ID = 0;
		int BpartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartnerSR_ID");
		MProductPricing pp = new MProductPricing(M_Product_ID, BpartnerID
				, (BigDecimal)mTab.getParentTab().getValue("Qty"), true, C_BPartner_Location_ID);
		int priceList = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(priceList);
		int priceListVersion = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		BigDecimal qtyOnHand = BigDecimal.ZERO;
		
		Timestamp orderDate = (Timestamp)Env.getContextAsDate(ctx, WindowNo, "EnquiryDate");
		
		if ( priceListVersion == 0 && priceList > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? ";
			if (pp.isIgnoredUnprocessedPrice())
			{
				sql += " AND plv.IsApproved = 'Y' AND plv.DocStatus IN ('CO')";
			}
			sql 
				+= "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			priceListVersion = DB.getSQLValueEx(null, sql, priceList, orderDate);
			if ( priceListVersion > 0 )
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", priceListVersion );
			else
				return "You do not have any pricelist version defined which is valid before " + orderDate;
		}
		
		pp.setM_PriceList_Version_ID(priceListVersion);
		pp.setPriceDate(orderDate);
		
		MStorageOnHand onHands[] = MStorageOnHand.getOfProduct(ctx, M_Product_ID, null);
		
		for (int i=0; i<onHands.length; i++)
		{
			MStorageOnHand Onhand = onHands[i];
			qtyOnHand = qtyOnHand.add(Onhand.getQtyOnHand());
		}
		
		//set value
		mTab.setValue(MUNSSERLineProduct.COLUMNNAME_QtyAvailable, qtyOnHand);
		mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_Price, pp.getPriceList());
		mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_C_UOM_ID, pp.getC_UOM_ID());
		
		BigDecimal qty = (BigDecimal)mTab.getValue(MUNSSERLineProduct.COLUMNNAME_Qty);
		
		if (null != qty
				&& qty.signum() != 0)
			set(ctx, mTab, qty, M_Product_ID.intValue());
		
		return "";
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String Qty(Properties ctx, int WindowNo,GridTab mTab, GridField mField, Object value)
	{
		BigDecimal qty = (BigDecimal) value;
		if (isCalloutActive())
			return "";
		
		if (null == qty || qty.signum() <= 0)
			return "";
		
		Integer M_Product_ID = ((Integer) mTab.getValue("M_Product_ID"));
		Integer C_UOM_ID = ((Integer)mTab.getValue(I_UNS_SERLineProduct.COLUMNNAME_C_UOM_ID));
		
		if (null != M_Product_ID && M_Product_ID.intValue() >= 0
				&& null != C_UOM_ID && C_UOM_ID.intValue() >= 0)
		{
			set(ctx, mTab, qty, M_Product_ID);
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String UOM(Properties ctx, int WindowNo, GridTab mTab, GridField mField,  Object value)
	{
		Integer C_UOM_ID = (Integer)value;
		
		if (null == C_UOM_ID || C_UOM_ID.intValue() < 0)
			return "";
		if (isCalloutActive())
			return "";
		
		Integer M_Product_ID = ((Integer)mTab.getValue(
				I_UNS_SERLineProduct.COLUMNNAME_M_Product_ID));
		BigDecimal qty = (BigDecimal)mTab.getValue(I_UNS_SERLineProduct.COLUMNNAME_Qty);
		/**
		BigDecimal newQty =
				MUOMConversion.convertProductTo(ctx, M_Product_ID.intValue(), C_UOM_ID.intValue(), qty);
		
		mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Qty, newQty);
		*/
		if (null != M_Product_ID && M_Product_ID.intValue() >= 0
				&& null != qty && qty.intValue() >=0)
		{
			set(ctx, mTab, qty, M_Product_ID);
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param ctx
	 * @param mTab
	 * @param qty
	 * @param M_Product_ID
	 */
	private void set(Properties ctx, GridTab mTab, BigDecimal qty,int M_Product_ID)
	{
		MProduct product = MProduct.get(ctx, M_Product_ID);
		mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_C_UOM_ID, product.getC_UOM_ID());
		BigDecimal prodWeight = product.getWeight();
		BigDecimal prodVolume = product.getVolume();
		BigDecimal price = (BigDecimal) mTab.getValue(MUNSSERLineProduct.COLUMNNAME_Price);
		//BigDecimal priceMT =  MUOMConversion.convertProductTo(ctx, M_Product_ID, UOM_MT, price);
		//BigDecimal priceM3 = MUOMConversion.convertProductTo(ctx, M_Product_ID, UOM_M3, price);

		BigDecimal totalAmt = qty.multiply(price);
		mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_LineNetAmt, totalAmt);
		mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_SalesTotal, totalAmt);

		BigDecimal ConversionMT = (prodWeight.multiply(qty)).multiply(MULTIPLY_KG_TO_MT);
		prodWeight = prodWeight.multiply(qty);
		BigDecimal ConversionM3 = prodVolume.multiply(qty);
		
		if (null != ConversionMT && ConversionMT.compareTo(BigDecimal.ZERO) > 0)
		{
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Weight, ConversionMT);
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToMT, 
						  (totalAmt.divide(prodWeight, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(1000)));//.divide(ConversionMT, BigDecimal.ROUND_HALF_UP));
		}
		
		if (null != ConversionM3 && ConversionM3.compareTo(BigDecimal.ZERO) > 0)
		{
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Volume, ConversionM3);
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToM3, totalAmt.divide(ConversionM3, BigDecimal.ROUND_HALF_UP));
		}
		/**
		boolean isVolumeOrWeight = mTab.getValueAsBoolean(I_UNS_SERLineProduct.COLUMNNAME_IsVolumeOrWeight);
		if (isVolumeOrWeight)
		{
			BigDecimal ConversionMT = product.getWeight().multiply(qty).multiply(new BigDecimal(0.001));
			BigDecimal ConversionM3 = product.getVolume().multiply(qty);
					//MUOMConversion.convertProductTo(ctx, M_Product_ID, 0, qty);
			
			if (null != ConversionMT)
			{
				mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Weight, ConversionMT);
			}
			else 
			{
				prodWeight = prodWeight.multiply(qty);
				prodWeight = prodWeight.multiply(MULTIPLY_KG_TO_MT);
				mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Weight, prodWeight);
			}
			
			if (null != ConversionM3)
			{
				mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Volume, ConversionM3);
			}
			else
			{
				prodVolume = prodVolume.multiply(qty);
				mTab.setValue(MUNSSERLineProduct.COLUMNNAME_Volume, prodVolume);
			}
		}
		else
		{
			if (null != prodWeight
					&& prodWeight.intValue() != 0)
			{
				BigDecimal weight = qty.multiply(prodWeight);
				BigDecimal WeightMT = weight.multiply(MULTIPLY_KG_TO_MT);					
				mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_Weight, WeightMT);
			}
			
			if (null != prodVolume
					&& prodVolume.intValue() != 0)
			{			
				BigDecimal volume = qty.multiply(prodVolume);
				
				mTab.setValue(I_UNS_SERLineProduct.COLUMNNAME_Volume, volume);
				
			}
		}
		
		if (null == priceMT || priceMT.intValue() <= 0) {
			priceMT = price.multiply(prodWeight);
			priceMT = priceMT.divide(MULTIPLY_KG_TO_MT,2,RoundingMode.HALF_UP);
		}
		
		if (null != priceMT)
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToMT, priceMT);
		else
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToMT, BigDecimal.ZERO);
		
		if (null != priceM3)
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToM3, priceM3);
		else
			mTab.setValue(MUNSSERLineProduct.COLUMNNAME_FOBGtnToM3, BigDecimal.ZERO);
		*/
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		String retValue = null;
		
		if (I_UNS_SERLineProduct.COLUMNNAME_M_Product_ID.equals(mField.getColumnName()))
		{
			retValue = Product(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (I_UNS_SERLineProduct.COLUMNNAME_Qty.equals(mField.getColumnName()))
		{
			retValue = Qty(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_SERLineProduct.COLUMNNAME_C_UOM_ID.equals(mField.getColumnName()))
		{
			retValue = UOM(ctx, WindowNo, mTab, mField, value);
		}
		/**
		else if (I_UNS_SERLineProduct.COLUMNNAME_IsVolumeOrWeight.equals(mField.getColumnName()))
		{
			int productID = (Integer) mTab.getValue(I_UNS_SERLineProduct.COLUMNNAME_M_Product_ID);
			if (productID > 0)
				set(ctx, mTab, 
					(BigDecimal) mTab.getValue(I_UNS_SERLineProduct.COLUMNNAME_Qty), 
					(Integer) mTab.getValue(I_UNS_SERLineProduct.COLUMNNAME_M_Product_ID));
		}
		*/
		return retValue;
	}

}
