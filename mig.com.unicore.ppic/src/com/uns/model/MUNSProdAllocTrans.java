/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSProdAllocTrans extends X_UNS_ProdAllocTrans {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSForecastHeader m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_ProdAllocTrans_ID
	 * @param trxName
	 */
	public MUNSProdAllocTrans(Properties ctx, int UNS_ProdAllocTrans_ID,
			String trxName) {
		super(ctx, UNS_ProdAllocTrans_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProdAllocTrans(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastHeader getParen()
	{
		if (m_parent != null)
			return m_parent;
		
		m_parent = new MUNSForecastHeader(getCtx(), getUNS_Forecast_Header_ID(), get_TrxName());
		return m_parent;
	}
	
	/**
	 * 
	 * @param month
	 * @param WorkDay
	 * @return
	 */
	public BigDecimal getProportionResultDay(int month, int WorkDay)
	{
		BigDecimal proportionMonth = getProportionResultMonth(month);
		BigDecimal proportionDay = proportionMonth.divide(new BigDecimal(WorkDay), 16, RoundingMode.HALF_UP);
		return proportionDay;
	}
	
	/**
	 * 
	 * @param month
	 * @param workDay
	 * @param BOMQty
	 * @param pAInput
	 * @param pAOutput
	 * @return
	 */
	public BigDecimal getProportionDay(
			int month, int workDay,BigDecimal BOMQty, MUNSProductAllocation pAInput
			, MUNSProductAllocation pAOutput) {
		BigDecimal proportion = BigDecimal.ZERO;
		BigDecimal qtyInput = pAInput.getForecastMarketingQty(month);
		BigDecimal qtyOutput = pAOutput.getForecastMarketingQty(month);
		proportion = qtyOutput.multiply(BOMQty).divide(qtyInput, 16, RoundingMode.HALF_UP);
		proportion = proportion.divide(
				new BigDecimal(workDay), 16, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
		return proportion;
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getProportionResultMonth(int month)
	{
		BigDecimal preparation = BigDecimal.ZERO;
		if (month == 0)
		{
			preparation = getJan();
		}
		else if (month == 1)
		{
			preparation = getFeb();
		}
		else if (month == 2)
		{
			preparation = getMar();
		}
		else if (month == 3)
		{
			preparation = getApr();
		}
		else if (month == 4)
		{
			preparation = getMay();
		}
		else if (month == 5)
		{
			preparation = getJun();
		}
		else if (month == 6)
		{
			preparation = getJul();
		}
		else if (month == 7)
		{
			preparation = getAgt();
		}
		else if (month == 8)
		{
			preparation = getSep();
		}
		else if (month == 9)
		{
			preparation = getOct();
		}
		else if (month == 10)
		{
			preparation = getNov();
		}
		else if (month == 11)
		{
			preparation = getDec();
		}
		
		return preparation;
	}
	
	/**
	 * 
	 * @param month
	 * @param quantity
	 */
	public void setQtyAllocation(int month, BigDecimal quantity)
	{
		switch (month)
		{
		case 0 :
			setJan(quantity);
			break;
		case 1 :
			setFeb(quantity);
			break;
		case 2 :
			setMar(quantity);
			break;
		case 3 :
			setApr(quantity);
			break;
		case 4 :
			setMay(quantity);
			break;
		case 5 :
			setJun(quantity);
			break;
		case 6 :
			setJul(quantity);
			break;
		case 7 :
			setAgt(quantity);
			break;
		case 8 :
			setSep(quantity);
			break;
		case 9 :
			setOct(quantity);
			break;
		case 10 :
			setNov(quantity);
			break;
		case 11 :
			setDec(quantity);
			break;
			default :;
		}
	}
	
	
	/**
	 * 
	 * @param input
	 * @param BOMQty
	 */
	private void setQtyByInput(MUNSProdAllocTrans input, BigDecimal BOMQty)
	{
		setJan(getJan().add(input.getJan()));
		setJan(getJan().add(input.getFeb()));
		setJan(getJan().add(input.getMar()));
		setJan(getJan().add(input.getApr()));
		setJan(getJan().add(input.getMay()));
		setJan(getJan().add(input.getJun()));
		setJan(getJan().add(input.getJul()));
		setJan(getJan().add(input.getAgt()));
		setJan(getJan().add(input.getSep()));
		setJan(getJan().add(input.getOct()));
		setJan(getJan().add(input.getNov()));
		setJan(getJan().add(input.getDec()));
	}
	
	/**
	 * 
	 * @param input
	 */
	public void initQtyAllocation(MUNSProdAllocTrans input)
	{
		BigDecimal BOMQty =  MUNSResource.getBOMQty(getM_Product_ID(), input.getM_Product_ID(), get_TrxName());
		setQtyByInput(input,BOMQty);
	}
	
	/**
	 * 
	 * @param output
	 * @param transitionAsInput
	 */
	public void initQtyAllocation(MUNSResourceInOut output, MUNSProdAllocTrans transitionAsInput)
	{
		BigDecimal avgWorkTimeInDay = output.getDailyAvgProductionHours();
		BigDecimal actualMaxCapsMT = output.getActualMaxCapsMT();
		int year = ((MUNSForecastHeader)getUNS_Forecast_Header()).getYear();
		MUNSSlotType slotType = ((MUNSResource)output.getUNS_Resource()).getSlotType();
		
		BigDecimal BOMQty = MUNSResource.getBOMQty(
				output.getM_Product_ID(), transitionAsInput.getM_Product_ID()
				, get_TrxName());
		
		if (null == BOMQty
				|| BOMQty.compareTo(new BigDecimal(0)) <= 0)
			return;
		
		for (int i=0; i<12; i++)
		{
			BigDecimal qtyToInput = transitionAsInput.getProportionResultMonth(i).multiply(BOMQty);
			
			int WorkDayInMonth = slotType.getWorkDay(year, i);
			BigDecimal monthlyTotalProductionTime = avgWorkTimeInDay.multiply(
					new BigDecimal(WorkDayInMonth));
			BigDecimal monthlyMaxCaps = actualMaxCapsMT.multiply(monthlyTotalProductionTime);
			
			if (qtyToInput.compareTo(actualMaxCapsMT) > 0)
			{
				qtyToInput = monthlyMaxCaps;
			}
			
			setQtyAllocation(i, qtyToInput);
		}
	}
	
	/**
	 * 
	 * @param outIn
	 */
	public void initQtyAllocation(MUNSResourceInOut outIn)
	{
		BigDecimal avgWorkTimeInDay = outIn.getDailyAvgProductionHours();
		BigDecimal actualMaxCaps = outIn.getActualMaxCaps();
		int year = ((MUNSForecastHeader)getUNS_Forecast_Header()).getYear();
		MUNSSlotType slotType = ((MUNSResource) outIn.getUNS_Resource()).getSlotType();
		
		for (int i=0; i<12; i++)
		{
			int WorkDayInMonth = slotType.getWorkDay(year, i);
			BigDecimal monthlyTotalProductionTime = avgWorkTimeInDay.multiply(
					new BigDecimal(WorkDayInMonth));
			BigDecimal monthlyMaxCaps = actualMaxCaps.multiply(monthlyTotalProductionTime);
			setQtyAllocation(i, monthlyMaxCaps);
		}
	}

}
