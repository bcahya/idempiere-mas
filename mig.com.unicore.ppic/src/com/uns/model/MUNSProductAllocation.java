/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Properties;

import org.compiere.model.MUOM;

/**
 * @author menjangan
 *
 */
public class MUNSProductAllocation extends X_UNS_ProductAllocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSForecastHeader m_parent = null;
	private Hashtable<String, MUNSProdAllocTrans> m_Transition = new Hashtable<String, MUNSProdAllocTrans>();

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
		int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, precision, BigDecimal.ROUND_HALF_UP, false);
	}
	
	
	/**
	 * @param ctx
	 * @param UNS_ProductAllocation_ID
	 * @param trxName
	 */
	public MUNSProductAllocation(Properties ctx, int UNS_ProductAllocation_ID,
			String trxName) {
		super(ctx, UNS_ProductAllocation_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductAllocation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastHeader getParent()
	{
		if (null != m_parent)
		{
			return m_parent;
		}
		m_parent = new MUNSForecastHeader(getCtx(),getUNS_Forecast_Header_ID(), get_TrxName());
		return m_parent;
	}
	
	/**
	 * 
	 * @param UNS_ProductAllocation_ID
	 * @param UNS_Resource_Transition_ID
	 * @return
	 */
	public String formatTransitionKey(int UNS_ProductAllocation_ID, int UNS_Resource_Transition_ID)
	{
		return UNS_ProductAllocation_ID + "_" + UNS_Resource_Transition_ID;
	}
	
	/**
	 * 
	 * @param resource
	 */
	public void initInputTransition(MUNSResource resource, MUNSForecastHeader forecastHeader)
	{
		String key = formatTransitionKey(
				getUNS_ProductAllocation_ID(), 0);
		MUNSProdAllocTrans allocationTransition = m_Transition.get(key);
		if (null == allocationTransition)
		{
			allocationTransition =  new MUNSProdAllocTrans(getCtx(), 0, get_TrxName());
			allocationTransition.setUNS_Forecast_Header_ID(forecastHeader.getUNS_Forecast_Header_ID());
			allocationTransition.setUNS_Resource_ID(resource.get_ID());
			allocationTransition.setUNS_Resource_Transition_ID(0);
			allocationTransition.setNextResource_ID(resource.get_ID());			
			m_Transition.put(key, allocationTransition);
		}
	}
	
	
	/**
	 * 
	 * @param outputs
	 */
	public void initQtyAllocation(Collection<MUNSResourceInOut> outputs)
	{
		BigDecimal totalMaxCapsDay = BigDecimal.ZERO;
		for (MUNSResourceInOut output : outputs)
		{
			if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi))
			{
				totalMaxCapsDay = output.getMaxCapsMT();
				if (output.getMaxCapsMT().compareTo(totalMaxCapsDay) > 0) 
				{
					totalMaxCapsDay = output.getMaxCapsMT();
				}
			}
			else
			{
				totalMaxCapsDay.add(output.getMaxCapsMT().multiply(output.getDailyAvgProductionHours()));
			}
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastMarketingQty(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastMarketingJan();
			break;
		case 1 :
			qty = getForecastMarketingFeb();
			break;
		case 2 :
			qty = getForecastMarketingMar();
			break;
		case 3 :
			qty = getForecastMarketingApr();
			break;
		case 4 :
			qty = getForecastMarketingMay();
			break;
		case 5 :
			qty = getForecastMarketingJun();
			break;
		case 6 :
			qty = getForecastMarketingJul();
			break;
		case 7 :
			qty = getForecastMarketingAgt();
			break;
		case 8 :
			qty = getForecastMarketingSep();
			break;
		case 9 :
			qty = getForecastMarketingOct();
			break;
		case 10 :
			qty = getForecastMarketingNov();
			break;
		case 11 :
			qty = getForecastMarketingDec();
			break;
		}			
		return qty;
	}
	

	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastByMarketingQtyMT(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastMarketingJanMT();
			break;
		case 1 :
			qty = getForecastMarketingFebMT();
			break;
		case 2 :
			qty = getForecastMarketingMarMT();
			break;
		case 3 :
			qty = getForecastMarketingAprMT();
			break;
		case 4 :
			qty = getForecastMarketingMayMT();
			break;
		case 5 :
			qty = getForecastMarketingJunMT();
			break;
		case 6 :
			qty = getForecastMarketingJulMT();
			break;
		case 7 :
			qty = getForecastMarketingAgtMT();
			break;
		case 8 :
			qty = getForecastMarketingSepMT();
			break;
		case 9 :
			qty = getForecastMarketingOctMT();
			break;
		case 10 :
			qty = getForecastMarketingNovMT();
			break;
		case 11 :
			qty = getForecastMarketingDecMT();
			break;
		}			
		return qty;
	}
	

	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastByRawInputQty(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastRMPJan();
			break;
		case 1 :
			qty = getForecastRMPFeb();
			break;
		case 2 :
			qty = getForecastRMPMar();
			break;
		case 3 :
			qty = getForecastRMPApr();
			break;
		case 4 :
			qty = getForecastRMPMay();
			break;
		case 5 :
			qty = getForecastRMPJun();
			break;
		case 6 :
			qty = getForecastRMPJul();
			break;
		case 7 :
			qty = getForecastRMPAgt();
			break;
		case 8 :
			qty = getForecastRMPSep();
			break;
		case 9 :
			qty = getForecastRMPOct();
			break;
		case 10 :
			qty = getForecastRMPNov();
			break;
		case 11 :
			qty = getForecastRMPDec();
			break;
		}			
		return qty;
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastByTargetedQty(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastTargetedJan();
			break;
		case 1 :
			qty = getForecastTargetedFeb();
			break;
		case 2 :
			qty = getForecastTargetedMar();
			break;
		case 3 :
			qty = getForecastTargetedApr();
			break;
		case 4 :
			qty = getForecastTargetedMay();
			break;
		case 5 :
			qty = getForecastTargetedJun();
			break;
		case 6 :
			qty = getForecastTargetedJul();
			break;
		case 7 :
			qty = getForecastTargetedAgt();
			break;
		case 8 :
			qty = getForecastTargetedSep();
			break;
		case 9 :
			qty = getForecastTargetedOct();
			break;
		case 10 :
			qty = getForecastTargetedNov();
			break;
		case 11 :
			qty = getForecastTargetedDec();
			break;
		}			
		return qty;
	}
	

	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastByTargetedQtyMT(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastTargetedJanMT();
			break;
		case 1 :
			qty = getForecastTargetedFebMT();
			break;
		case 2 :
			qty = getForecastTargetedMarMT();
			break;
		case 3 :
			qty = getForecastTargetedAprMT();
			break;
		case 4 :
			qty = getForecastTargetedMayMT();
			break;
		case 5 :
			qty = getForecastTargetedJunMT();
			break;
		case 6 :
			qty = getForecastTargetedJulMT();
			break;
		case 7 :
			qty = getForecastTargetedAgtMT();
			break;
		case 8 :
			qty = getForecastTargetedSepMT();
			break;
		case 9 :
			qty = getForecastTargetedOctMT();
			break;
		case 10 :
			qty = getForecastTargetedNovMT();
			break;
		case 11 :
			qty = getForecastTargetedDecMT();
			break;
		}			
		return qty;
	}
	

	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getForecastByMarketingQty(int month)
	{
		BigDecimal qty = BigDecimal.ZERO;
		switch (month) {
		case 0 :
			qty = getForecastMarketingJan();
			break;
		case 1 :
			qty = getForecastMarketingFeb();
			break;
		case 2 :
			qty = getForecastMarketingMar();
			break;
		case 3 :
			qty = getForecastMarketingApr();
			break;
		case 4 :
			qty = getForecastMarketingMay();
			break;
		case 5 :
			qty = getForecastMarketingJun();
			break;
		case 6 :
			qty = getForecastMarketingJul();
			break;
		case 7 :
			qty = getForecastMarketingAgt();
			break;
		case 8 :
			qty = getForecastMarketingSep();
			break;
		case 9 :
			qty = getForecastMarketingOct();
			break;
		case 10 :
			qty = getForecastMarketingNov();
			break;
		case 11 :
			qty = getForecastMarketingDec();
			break;
		}			
		return qty;
	}
	
	
	/**
	 * 
	 * @param month
	 * @param quantity
	 */
	public void setQtyRMPForecast(int month, BigDecimal quantity)
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
			default :;
		}
	}
	
	
	public void setQtyRMPForecastMT(int month, BigDecimal quantity)
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
			default :;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param quantity
	 */
	public void setQtyMarketingForecast(int month, BigDecimal quantity)
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
			default :;
		}
	}
	
	
	public void setQtyMarketingForecastMT(int month, BigDecimal quantity)
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
			default :;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param quantity
	 */
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
			default :;
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
			default :;
		}
	}
	
	/**
	 * 
	 * @param output
	 */
	public void initQtyAllocation(MUNSResourceInOut output)
	{
		BigDecimal avgWorkTimeInDay = output.getDailyAvgProductionHours();
		BigDecimal actualMaxCaps = output.getActualMaxCapsMT();
		int year = ((MUNSForecastHeader)getUNS_Forecast_Header()).getYear();
		MUNSSlotType slotType = ((MUNSResource)output.getUNS_Resource()).getSlotType();
		
		for (int i=0; i<12; i++)
		{
			int WorkDayInMonth = slotType.getWorkDay(year, i);
			BigDecimal monthlyTotalProductionTime = avgWorkTimeInDay.multiply(
					new BigDecimal(WorkDayInMonth));
			BigDecimal monthlyMaxCaps = actualMaxCaps.multiply(monthlyTotalProductionTime);
			setQtyRMPForecast(i, monthlyMaxCaps);
		}
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		setIsSold(getM_Product().isSold());
		BigDecimal pWeight = getM_Product().getWeight();
		for (int i=0; i<=11; i++)
		{
			//BigDecimal rmpQty = getForecastByRawInputQty(i);
			BigDecimal marketingQty = getForecastByMarketingQty(i);
			BigDecimal targetedQty = getForecastByTargetedQty(i);
			
			//BigDecimal rmpQtyMT = getForecastByRawInputQty(i);
			BigDecimal marketingQtyMT = getForecastByMarketingQtyMT(i);
			BigDecimal targetedQtyMT = getForecastByTargetedQtyMT(i);
			
			if (marketingQty != null && marketingQty.signum() > 0)
				setQtyMarketingForecastMT(i, 
						marketingQty.multiply(pWeight).multiply(BigDecimal.valueOf(0.001)));				
			else if ((marketingQtyMT != null && marketingQtyMT.signum() > 0))
				setQtyMarketingForecast(i, 
						marketingQtyMT.multiply(BigDecimal.valueOf(1000))
						.divide(pWeight, 0, BigDecimal.ROUND_HALF_UP));
			
			if((targetedQty != null && targetedQty.signum() > 0))
					setQtyTargetedForecastMT(i, 
							targetedQty.multiply(pWeight).multiply(BigDecimal.valueOf(0.001)));
			else if ((targetedQtyMT != null && targetedQtyMT.signum() > 0))
				setQtyTargetedForecast(i, 
						targetedQtyMT.multiply(BigDecimal.valueOf(1000))
						.divide(pWeight, 0, BigDecimal.ROUND_HALF_UP));				
		}
		return true;
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		return super.toString() + "[" + getM_Product().getValue() + "]";
	}
	
	/**
	 * To check if the input is this Output's BOM and at least one of the BOM's chain is coming from the Predicted
	 * Reject Material.
	 * @param inputID
	 * @return
	 */
	public boolean isPredictedRejectMaterial(int inputID)
	{
		return false;
		//TODO Product Alocation MPS FORECAST
//		String strResult = DB.getSQLValueString(get_TrxName(), "SELECT isBOMTypePartOf(?, ?, ?)", 
//											    getM_Product_ID(),
//											    inputID,
//											    MProductBOM.BOMTYPE_PredictedRejectMaterial);
//		
//		return strResult != null && strResult.equalsIgnoreCase("T");
	}
}
