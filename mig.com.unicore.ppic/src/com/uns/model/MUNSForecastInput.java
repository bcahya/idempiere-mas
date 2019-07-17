/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSForecastInput extends X_UNS_Forecast_Input {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String[] NUMBERTYPE_COLUMNS = new String[] {
		COLUMNNAME_ForecastMarketingAgt,
		COLUMNNAME_ForecastMarketingAgtMT,
		COLUMNNAME_ForecastMarketingApr,
		COLUMNNAME_ForecastMarketingAprMT,
		COLUMNNAME_ForecastMarketingDec,
		COLUMNNAME_ForecastMarketingDecMT,
		COLUMNNAME_ForecastMarketingFeb,
		COLUMNNAME_ForecastMarketingFebMT,
		COLUMNNAME_ForecastMarketingJan,
		COLUMNNAME_ForecastMarketingJanMT,
		COLUMNNAME_ForecastMarketingJul,
		COLUMNNAME_ForecastMarketingJulMT,
		COLUMNNAME_ForecastMarketingJun,
		COLUMNNAME_ForecastMarketingJunMT,
		COLUMNNAME_ForecastMarketingMar,
		COLUMNNAME_ForecastMarketingMarMT,
		COLUMNNAME_ForecastMarketingMay,
		COLUMNNAME_ForecastMarketingMayMT,
		COLUMNNAME_ForecastMarketingNov,
		COLUMNNAME_ForecastMarketingNovMT,
		COLUMNNAME_ForecastMarketingOct,
		COLUMNNAME_ForecastMarketingOctMT,
		COLUMNNAME_ForecastMarketingSep,
		COLUMNNAME_ForecastMarketingSepMT,
		COLUMNNAME_ForecastRMPAgt,
		COLUMNNAME_ForecastRMPAgtMT,
		COLUMNNAME_ForecastRMPApr,
		COLUMNNAME_ForecastRMPAprMT,
		COLUMNNAME_ForecastRMPDec,
		COLUMNNAME_ForecastRMPDecMT,
		COLUMNNAME_ForecastRMPFeb,
		COLUMNNAME_ForecastRMPFebMT,
		COLUMNNAME_ForecastRMPJan,
		COLUMNNAME_ForecastRMPJanMT,
		COLUMNNAME_ForecastRMPJul,
		COLUMNNAME_ForecastRMPJulMT,
		COLUMNNAME_ForecastRMPJun,
		COLUMNNAME_ForecastRMPJunMT,
		COLUMNNAME_ForecastRMPMar,
		COLUMNNAME_ForecastRMPMarMT,
		COLUMNNAME_ForecastRMPMay,
		COLUMNNAME_ForecastRMPMayMT,
		COLUMNNAME_ForecastRMPNov,
		COLUMNNAME_ForecastRMPNovMT,
		COLUMNNAME_ForecastRMPOct,
		COLUMNNAME_ForecastRMPOctMT,
		COLUMNNAME_ForecastRMPSep,
		COLUMNNAME_ForecastRMPSepMT,
		COLUMNNAME_ForecastTargetedAgt,
		COLUMNNAME_ForecastTargetedAgtMT,
		COLUMNNAME_ForecastTargetedApr,
		COLUMNNAME_ForecastTargetedAprMT,
		COLUMNNAME_ForecastTargetedDec,
		COLUMNNAME_ForecastTargetedDecMT,
		COLUMNNAME_ForecastTargetedFeb,
		COLUMNNAME_ForecastTargetedFebMT,
		COLUMNNAME_ForecastTargetedJan,
		COLUMNNAME_ForecastTargetedJanMT,
		COLUMNNAME_ForecastTargetedJul,
		COLUMNNAME_ForecastTargetedJulMT,
		COLUMNNAME_ForecastTargetedJun,
		COLUMNNAME_ForecastTargetedJunMT,
		COLUMNNAME_ForecastTargetedMar,
		COLUMNNAME_ForecastTargetedMarMT,
		COLUMNNAME_ForecastTargetedMay,
		COLUMNNAME_ForecastTargetedMayMT,
		COLUMNNAME_ForecastTargetedNov,
		COLUMNNAME_ForecastTargetedNovMT,
		COLUMNNAME_ForecastTargetedOct,
		COLUMNNAME_ForecastTargetedOctMT,
		COLUMNNAME_ForecastTargetedSep,
		COLUMNNAME_ForecastTargetedSepMT,
	};
	
	
	/**
	 * 
	 */
	public void rescaleAllNumbers() {
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, 4, BigDecimal.ROUND_HALF_UP, false);
	}
	
	/**
	 * @param ctx
	 * @param UNS_Forecast_Input_ID
	 * @param trxName
	 */
	public MUNSForecastInput(Properties ctx, int UNS_Forecast_Input_ID,
			String trxName) {
		super(ctx, UNS_Forecast_Input_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSForecastInput(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyInputMT(int month)
	{
		return getQtyForecastRMPMT(month);
	}
	
	/**
	 * 
	 * @param QtyUOM
	 * @return
	 *
	public BigDecimal getQtyInputMT(BigDecimal QtyUOM)
	{
		BigDecimal Qty = QtyUOM;
		MProduct Product = MProduct.get(getCtx(), getM_Product_ID());
		BigDecimal weight = Product.getWeight();
		weight = weight.multiply(new BigDecimal(0.001));
		Qty = Qty.multiply(weight);
		return Qty;
	}
	*/
	
	/**
	 * Update input Quantity Added input with new quantity
	 * @param month
	 * @param quantity
	 */
	public void setQtyForecastRMP(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastRMPJan(quantity);
			break;
		case 1 :
			setForecastRMPFeb(quantity);
			break;
		case 2 :
			setForecastRMPMar(quantity);
			break;
		case 3 :
			setForecastRMPApr(quantity);
			break;
		case 4 :
			setForecastRMPMay(quantity);
			break;
		case 5 :
			setForecastRMPJun(quantity);
			break;
		case 6 :
			setForecastRMPJul(quantity);
			break;
		case 7 :
			setForecastRMPAgt(quantity);
			break;
		case 8 :
			setForecastRMPSep(quantity);
			break;
		case 9 :
			setForecastRMPOct(quantity);
			break;
		case 10 :
			setForecastRMPNov(quantity);
			break;
		case 11 :
			setForecastRMPDec(quantity);
			break;
		}
	}

	public void setQtyTargetedForecast(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastTargetedJan(quantity);
			break;
		case 1 :
			setForecastTargetedFeb(quantity);
			break;
		case 2 :
			setForecastTargetedMar(quantity);
			break;
		case 3 :
			setForecastTargetedApr(quantity);
			break;
		case 4 :
			setForecastTargetedMay(quantity);
			break;
		case 5 :
			setForecastTargetedJun(quantity);
			break;
		case 6 :
			setForecastTargetedJul(quantity);
			break;
		case 7 :
			setForecastTargetedAgt(quantity);
			break;
		case 8 :
			setForecastTargetedSep(quantity);
			break;
		case 9 :
			setForecastTargetedOct(quantity);
			break;
		case 10 :
			setForecastTargetedNov(quantity);
			break;
		case 11 :
			setForecastTargetedDec(quantity);
			break;
		}		
	}

	public void setQtyForecastMarketing(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastMarketingJan(quantity);
			break;
		case 1 :
			setForecastMarketingFeb(quantity);
			break;
		case 2 :
			setForecastMarketingMar(quantity);
			break;
		case 3 :
			setForecastMarketingApr(quantity);
			break;
		case 4 :
			setForecastMarketingMay(quantity);
			break;
		case 5 :
			setForecastMarketingJun(quantity);
			break;
		case 6 :
			setForecastMarketingJul(quantity);
			break;
		case 7 :
			setForecastMarketingAgt(quantity);
			break;
		case 8 :
			setForecastMarketingSep(quantity);
			break;
		case 9 :
			setForecastMarketingOct(quantity);
			break;
		case 10 :
			setForecastMarketingNov(quantity);
			break;
		case 11 :
			setForecastMarketingDec(quantity);
			break;
		}
	}
	
	
	public void setQtyTargetedForecastMT(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastTargetedJanMT(quantity);
			break;
		case 1 :
			setForecastTargetedFebMT(quantity);
			break;
		case 2 :
			setForecastTargetedMarMT(quantity);
			break;
		case 3 :
			setForecastTargetedAprMT(quantity);
			break;
		case 4 :
			setForecastTargetedMayMT(quantity);
			break;
		case 5 :
			setForecastTargetedJunMT(quantity);
			break;
		case 6 :
			setForecastTargetedJulMT(quantity);
			break;
		case 7 :
			setForecastTargetedAgtMT(quantity);
			break;
		case 8 :
			setForecastTargetedSepMT(quantity);
			break;
		case 9 :
			setForecastTargetedOctMT(quantity);
			break;
		case 10 :
			setForecastTargetedNovMT(quantity);
			break;
		case 11 :
			setForecastTargetedDecMT(quantity);
			break;
		}		
	}

	public void setQtyForecastMarketingMT(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastMarketingJanMT(quantity);
			break;
		case 1 :
			setForecastMarketingFebMT(quantity);
			break;
		case 2 :
			setForecastMarketingMarMT(quantity);
			break;
		case 3 :
			setForecastMarketingAprMT(quantity);
			break;
		case 4 :
			setForecastMarketingMayMT(quantity);
			break;
		case 5 :
			setForecastMarketingJunMT(quantity);
			break;
		case 6 :
			setForecastMarketingJulMT(quantity);
			break;
		case 7 :
			setForecastMarketingAgtMT(quantity);
			break;
		case 8 :
			setForecastMarketingSepMT(quantity);
			break;
		case 9 :
			setForecastMarketingOctMT(quantity);
			break;
		case 10 :
			setForecastMarketingNovMT(quantity);
			break;
		case 11 :
			setForecastMarketingDecMT(quantity);
			break;
		}
	}
	
	
	public void setQtyForecastRMPMT(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setForecastRMPJanMT(quantity);
			break;
		case 1 :
			setForecastRMPFebMT(quantity);
			break;
		case 2 :
			setForecastRMPMarMT(quantity);
			break;
		case 3 :
			setForecastRMPAprMT(quantity);
			break;
		case 4 :
			setForecastRMPMayMT(quantity);
			break;
		case 5 :
			setForecastRMPJunMT(quantity);
			break;
		case 6 :
			setForecastRMPJulMT(quantity);
			break;
		case 7 :
			setForecastRMPAgtMT(quantity);
			break;
		case 8 :
			setForecastRMPSepMT(quantity);
			break;
		case 9 :
			setForecastRMPOctMT(quantity);
			break;
		case 10 :
			setForecastRMPNovMT(quantity);
			break;
		case 11 :
			setForecastRMPDecMT(quantity);
			break;
		}
	}
	
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyForecastRMP(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastRMPJan();
		case 1 :
			return getForecastRMPFeb();
		case 2 :
			return getForecastRMPMar();
		case 3 :
			return getForecastRMPApr();
		case 4 :
			return getForecastRMPMay();
		case 5 :
			return getForecastRMPJun();
		case 6 :
			return getForecastRMPJul();
		case 7 :
			return getForecastRMPAgt();
		case 8 :
			return getForecastRMPSep();
		case 9 :
			return getForecastRMPOct();
		case 10 :
			return getForecastRMPNov();
		case 11 :
			return getForecastRMPDec();
			default : return null;
		}
	}
	
	/**
	 * Get quantity input MT
	 * @param month
	 * @return BigDecimal quantity
	 */
	public BigDecimal getQtyForecastRMPMT(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastRMPJanMT();
		case 1 :
			return getForecastRMPFebMT();
		case 2 :
			return getForecastRMPMarMT();
		case 3 :
			return getForecastRMPAprMT();
		case 4 :
			return getForecastRMPMayMT();
		case 5 :
			return getForecastRMPJunMT();
		case 6 :
			return getForecastRMPJulMT();
		case 7 :
			return getForecastRMPAgtMT();
		case 8 :
			return getForecastRMPSepMT();
		case 9 :
			return getForecastRMPOctMT();
		case 10 :
			return getForecastRMPNovMT();
		case 11 :
			return getForecastRMPDecMT();
			default : return null;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyMarketingForecast(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastMarketingJan();
		case 1 :
			return getForecastMarketingFeb();
		case 2 :
			return getForecastMarketingMar();
		case 3 :
			return getForecastMarketingApr();
		case 4 :
			return getForecastMarketingMay();
		case 5 :
			return getForecastMarketingJun();
		case 6 :
			return getForecastMarketingJul();
		case 7 :
			return getForecastMarketingAgt();
		case 8 :
			return getForecastMarketingSep();
		case 9 :
			return getForecastMarketingOct();
		case 10 :
			return getForecastMarketingNov();
		case 11 :
			return getForecastMarketingDec();
			default : return null;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyTargetedForecast(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastTargetedJan();
		case 1 :
			return getForecastTargetedFeb();
		case 2 :
			return getForecastTargetedMar();
		case 3 :
			return getForecastTargetedApr();
		case 4 :
			return getForecastTargetedMay();
		case 5 :
			return getForecastTargetedJun();
		case 6 :
			return getForecastTargetedJul();
		case 7 :
			return getForecastTargetedAgt();
		case 8 :
			return getForecastTargetedSep();
		case 9 :
			return getForecastTargetedOct();
		case 10 :
			return getForecastTargetedNov();
		case 11 :
			return getForecastTargetedDec();
			default : return null;
		}
	}
	
	
	public BigDecimal getQtyMarketingForecastMT(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastMarketingJanMT();
		case 1 :
			return getForecastMarketingFebMT();
		case 2 :
			return getForecastMarketingMarMT();
		case 3 :
			return getForecastMarketingAprMT();
		case 4 :
			return getForecastMarketingMayMT();
		case 5 :
			return getForecastMarketingJunMT();
		case 6 :
			return getForecastMarketingJulMT();
		case 7 :
			return getForecastMarketingAgtMT();
		case 8 :
			return getForecastMarketingSepMT();
		case 9 :
			return getForecastMarketingOctMT();
		case 10 :
			return getForecastMarketingNovMT();
		case 11 :
			return getForecastMarketingDecMT();
			default : return null;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyTargetedForecastMT(int month)
	{
		switch (month)
		{
		case 0 :
			return getForecastTargetedJanMT();
		case 1 :
			return getForecastTargetedFebMT();
		case 2 :
			return getForecastTargetedMarMT();
		case 3 :
			return getForecastTargetedAprMT();
		case 4 :
			return getForecastTargetedMayMT();
		case 5 :
			return getForecastTargetedJunMT();
		case 6 :
			return getForecastTargetedJulMT();
		case 7 :
			return getForecastTargetedAgtMT();
		case 8 :
			return getForecastTargetedSepMT();
		case 9 :
			return getForecastTargetedOctMT();
		case 10 :
			return getForecastTargetedNovMT();
		case 11 :
			return getForecastTargetedDecMT();
			default : return null;
		}
	}
	
	/**
	 * 
	 * @param output
	 */
	public void initQtyAllocation(MUNSResourceInOut output)
	{
		BigDecimal avgWorkTimeInDay = output.getDailyAvgProductionHours();
		BigDecimal actualMaxCaps = output.getActualMaxCaps().multiply(new BigDecimal(0.001));
		int year = 0;//((MUNSForecastHeader)getUNS_Forecast_Header()).getYear();
		MUNSSlotType slotType = new MUNSSlotType(getCtx(), 
				output.getUNS_Resource().getUNS_SlotType_ID(), get_TrxName());
		BigDecimal BOMQty = MUNSResource.getBOMQty(
				output.getM_Product_ID(), getM_Product_ID()
				, get_TrxName());
		
		if (null == BOMQty
				|| BOMQty.compareTo(new BigDecimal(0)) <= 0)
			return;
		actualMaxCaps = actualMaxCaps.multiply(BOMQty);
		
		for (int i=0; i<12; i++)
		{
			int WorkDayInMonth = slotType.getWorkDay(year, i);
			BigDecimal monthlyTotalProductionTime = avgWorkTimeInDay.multiply(
					new BigDecimal(WorkDayInMonth));
			BigDecimal monthlyMaxCaps = actualMaxCaps.multiply(monthlyTotalProductionTime);
			setQtyForecastRMP(i, monthlyMaxCaps);
		}
	}
	
	public void setQtyToZero()
	{
		for (int i=0; i<12; i++)
		{
			setQtyForecastMarketing(i, BigDecimal.ZERO);
			setQtyForecastMarketingMT(i, BigDecimal.ZERO);
			setQtyTargetedForecast(i, BigDecimal.ZERO);
			setQtyTargetedForecastMT(i, BigDecimal.ZERO);
		}
	}
	
	// Contains of Output product-id and its BOMQty to this input product.
	Hashtable<Integer, BigDecimal> m_mapOfPossibleOutputs = new Hashtable<Integer, BigDecimal>();
	
	/**
	 * 
	 * @param productID
	 * @param BOMQty
	 */
	public void addPossibleOutputs(int productID, BigDecimal BOMQty)
	{
		m_mapOfPossibleOutputs.put(productID, BOMQty);
	}
	
	/**
	 * 
	 * @param productID
	 * @return
	 */
	public BigDecimal getPossibleOutputBOMOf(int productID)
	{
		return m_mapOfPossibleOutputs.get(productID);
	}
	
	public String toString()
	{
		return super.toString() + "[" + getM_Product() + "]";
	}

	/**
	 * To check if the input is the Output's BOM and at least one of the BOM's chain is coming from the Predicted
	 * Reject Material.
	 * @param outputID
	 * @return
	 */
	public boolean isPredictedRejectMaterialTo(int outputID)
	{
		return false;
		//TODO FORECAST
//		String strResult = DB.getSQLValueString(get_TrxName(), "SELECT isBOMTypePartOf(?, ?, ?)", 
//												outputID,
//											    getM_Product_ID(),
//											    MProductBOM.BOMTYPE_PredictedRejectMaterial);
//		
//		return strResult != null && strResult.equalsIgnoreCase("T");
	}
}
