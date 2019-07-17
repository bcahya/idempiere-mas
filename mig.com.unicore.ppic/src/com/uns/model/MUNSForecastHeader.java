/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MPeriod;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MYear;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.DBUtils;
import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSForecastHeader extends X_UNS_Forecast_Header implements DocAction, DocOptions{
	
	private MUNSForecastDate[] m_linesYearly = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private int m_Cur_IDR = MCurrency.get(getCtx(), "IDR").getC_Currency_ID();
	private int m_Cur_SGD = MCurrency.get(getCtx(), "SGD").getC_Currency_ID();
	private int m_Cur_USD = MCurrency.get(getCtx(), "USD").getC_Currency_ID();
	private Hashtable<String, MUNSForecastDate> m_ListDailyForecast = 
			new Hashtable <String, MUNSForecastDate>();
	private Hashtable<Integer, Hashtable<Integer, MUNSForecastDate>> m_ListWeeklyForecast =
			new Hashtable<Integer, Hashtable<Integer,MUNSForecastDate>>();
	private Hashtable<Integer, MUNSForecastDate> m_ListYearlyForecast = 
			new Hashtable<Integer, MUNSForecastDate>();
	private Hashtable<Integer, Hashtable<Integer, MUNSForecastDate>> m_ListMonthlyForecast = 
			new Hashtable<Integer, Hashtable <Integer, MUNSForecastDate>>();
	private MUNSResource m_Resource = null;
	private MUNSForecastInput[] m_InputAllocations = null;
	private BigDecimal m_ToIDRRate = Env.ONE;
		/*MConversionRate.getRate(
			m_Cur_IDR, m_Cur_USD, Env.getContextAsDate(
					getCtx(), "#Date"), 0, getAD_Client_ID(), getAD_Org_ID());*/
	private  BigDecimal m_ToSGDRate = Env.ONE; 
		/*MConversionRate.getRate(
			m_Cur_IDR, m_Cur_SGD, Env.getContextAsDate(
					getCtx(), "#Date"), 0, getAD_Client_ID(), getAD_Org_ID());*/
	private BigDecimal m_ToUSDRate = Env.ONE;
	private MPriceList m_PriceList;
	private MPeriod m_PeriodStart = null;
	private MPeriod m_PeriodEnd = null;
	private MYear m_Year = null;
	private Hashtable<Integer, double[]> m_AllDayProductProductionHours = new Hashtable<Integer, double[]>();
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_Forecast_Header_ID
	 * @param trxName
	 */
	public MUNSForecastHeader(Properties ctx, int UNS_Forecast_Header_ID,
			String trxName) {
		super(ctx, UNS_Forecast_Header_ID, trxName);
		m_PriceList = (MPriceList) getM_PriceList();
		initCurrencyConversionRate();
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSForecastHeader(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		m_PriceList = (MPriceList) getM_PriceList();
		initCurrencyConversionRate();
	}
	
	/**
	 * initialize all used currencies's conversion rate based on the based currency defined in the selected 
	 * price list.
	 */
	private void initCurrencyConversionRate()
	{
		m_ToIDRRate = MConversionRate.getRate(
				m_PriceList.getC_Currency_ID(), m_Cur_IDR, 
				Env.getContextAsDate(getCtx(), "#Date"), 0, getAD_Client_ID(), getAD_Org_ID());
		m_ToUSDRate = MConversionRate.getRate(
				m_PriceList.getC_Currency_ID(), m_Cur_USD, 
				Env.getContextAsDate(getCtx(), "#Date"), 0, getAD_Client_ID(), getAD_Org_ID());
		m_ToSGDRate = MConversionRate.getRate(
				m_PriceList.getC_Currency_ID(), m_Cur_SGD, 
				Env.getContextAsDate(getCtx(), "#Date"), 0, getAD_Client_ID(), getAD_Org_ID());
	}
	
	/**
	 * 
	 * @param value
	 * @param currToID
	 * @return
	 */
	private BigDecimal convertTo(BigDecimal value, int currToID)
	{
		BigDecimal convertedValue = Env.ZERO;
		if (currToID == m_Cur_IDR)
			convertedValue = value.multiply(m_ToIDRRate);
		else if (currToID == m_Cur_USD)
			convertedValue = value.multiply(m_ToUSDRate);
		else if (currToID == m_Cur_SGD)
			convertedValue = value.multiply(m_ToSGDRate);
		return convertedValue;
	}
	
	/**
	 * 
	 * @param M_Product_Input_ID
	 * @return
	 */
	public MUNSForecastInput getForecastInput(int M_Product_Input_ID) 
	{
		for (MUNSForecastInput forecastInput : getInputAllocations()) 
		{
			if (forecastInput.getM_Product_ID() == M_Product_Input_ID){
				return forecastInput;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastInput[] getInputAllocations(boolean requery)
	{
		if (m_InputAllocations != null && !requery)
		{
			set_TrxName(m_InputAllocations, get_TrxName());
			return m_InputAllocations;
		}
		
		final String whereClause = X_UNS_Forecast_Input.COLUMNNAME_UNS_Forecast_Header_ID + "=?";
		List<X_UNS_Forecast_Input> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID()
						, X_UNS_Forecast_Input.Table_Name, whereClause, get_TrxName())
						.setParameters(getUNS_Forecast_Header_ID())
						.setOrderBy(X_UNS_Forecast_Input.COLUMNNAME_UNS_Forecast_Input_ID)
						.list();
		m_InputAllocations = new MUNSForecastInput[list.size()];
		list.toArray(m_InputAllocations);
		
		return m_InputAllocations;
	}

	/**
	 * 
	 * @return
	 */
	public MUNSForecastInput[] getInputAllocations()
	{
		return getInputAllocations(false);
	}

	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastDate[] getLinesYearly(boolean requery)
	{
		if (null != m_linesYearly && !requery)
		{
			set_TrxName(m_linesYearly, get_TrxName());
			return m_linesYearly;
		}
		
		final String whereClause = X_UNS_Forecast_Date.COLUMNNAME_UNS_Forecast_Header_ID + "=?";
		
		List<X_UNS_Forecast_Date> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_Forecast_Date.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_Forecast_Header_ID())
				.setOrderBy(X_UNS_Forecast_Date.COLUMNNAME_UNS_Forecast_Date_ID)
				.list();
		
		m_linesYearly = new MUNSForecastDate[list.size()];
		list.toArray(m_linesYearly);
		
		return m_linesYearly;
	}
	
	@Override
	public boolean beforeDelete()
	{
		if (!deleteForecastInput())
			return false;
		
		String sql = "DELETE UNS_ProductAllocation WHERE UNS_Forecast_Header_ID=" + get_ID();
		int count = DB.executeUpdate(sql, get_TrxName());
		if (count < 0)
			return false;
		
		return deleteForecasts();
	}
	
	private MUNSProductAllocation[] m_OutputAllocation = null;
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSProductAllocation[] getOutputsAllocation(boolean requery)
	{
		if (m_OutputAllocation != null && !requery)
		{
			set_TrxName(m_OutputAllocation, get_TrxName());
			return m_OutputAllocation;
		}
		
		final String whereClause = X_UNS_ProductAllocation.COLUMNNAME_UNS_Forecast_Header_ID + "=?";
		List<X_UNS_Forecast_Input> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID()
						, X_UNS_ProductAllocation.Table_Name, whereClause, get_TrxName())
						.setParameters(getUNS_Forecast_Header_ID())
						.setOrderBy(X_UNS_ProductAllocation.COLUMNNAME_UNS_ProductAllocation_ID)
						.list();
		m_OutputAllocation = new MUNSProductAllocation[list.size()];
		list.toArray(m_OutputAllocation);
		
		return m_OutputAllocation;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteForecastInput()
	{
		MUNSForecastInput[] inputs = getInputAllocations();
		for (MUNSForecastInput input : inputs)
		{
			if (!input.delete(true))
				return false;
		}
		
		return true;
	}

	/**
	 * Delete all forecasts data (yearly, monthly, weekly and daily) of this forecast header.
	 * 
	 * @return
	 */
	public boolean deleteForecasts() 
	{
		MUNSForecastDate[] yearlyList = getLinesYearly(true);
		for (MUNSForecastDate yearly : yearlyList)
		{
			MUNSForecastDate[] monthlyList = yearly.getLinesMonthOfYear(true);
			for (MUNSForecastDate monthly : monthlyList)
			{
				DB.executeUpdateEx(
						"DELETE FROM UNS_Forecast_Date WHERE Monthly_ID=" + monthly.get_ID(), 
						get_TrxName());
				/*
				MUNSForecastDate[] dailyList = monthly.getLinesDayOfMonth(true);
				for (MUNSForecastDate daily : dailyList)
				{
					if (!daily.delete(true))
						return false;
				}
				if (!monthly.delete(true))
					return false;
				*/
			}
			DB.executeUpdateEx(
					"DELETE FROM UNS_Forecast_Date WHERE Yearly_ID=" + yearly.get_ID(), 
					get_TrxName());
			/*
			MUNSForecastDate[] weeklyList = yearly.getLinesWeekOfYear(true);
			for (MUNSForecastDate weekly : weeklyList)
			{
				MUNSForecastDate[] dailyList = weekly.getLinesDayOfWeek(true);
				for (MUNSForecastDate daily : dailyList)
				{
					if (!daily.delete(true))
						return false;
				}
				if (!weekly.delete(true))
					return false;
			}
			
			if (!yearly.delete(true))
				return false;
			*/
		}
		DB.executeUpdateEx(
				"DELETE FROM UNS_Forecast_Date WHERE UNS_Forecast_Header_ID=" + get_ID(), 
				get_TrxName());
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate[] getLinesYearly()
	{
		return getLinesYearly(false);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		 
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    		
    	return index;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		 
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		 
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		 
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		 
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSForecastInput[] lines = getInputAllocations();
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
			
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		 
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		 
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		 
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
//		Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		 
		return false;
	}

	@Override
	public boolean closeIt() {
		 
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		 
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		 
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reActivateIt() {
		 
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		 
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getInputAllocations().length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	@Override
	public String getDocumentNo() {
		 
		return null;
	}

	@Override
	public String getDocumentInfo() {
		 
		return null;
	}

	@Override
	public File createPDF() {
		 
		return null;
	}

	@Override
	public String getProcessMsg() {
		 
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		 
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		 
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		 
		return null;
	}
	
	/*************************************************************************************
	**							Process Generate Forecast Descending					**
	*************************************************************************************/
	/**
	private generateInputOutputAllocation()
	{
		MUNSForecastAllocation forecastAllocation = new MUNSForecastAllocation, forecastHeader)
	}
	*/
	

	public void updateForecastByOutput_Old()
	{
		prepare();
		Hashtable<String, MUNSForecastDate> mapWeeklyForecast = new Hashtable<String, MUNSForecastDate>();
		m_AllDayProductProductionHours = m_Resource.getAllProductProductionHours();
		
		resetTargetedForecast();
		
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);

		for (MUNSProductAllocation output : getOutputsAllocation(false))
		{
			if (!output.isPrimary())
				continue;
			
			for (MUNSForecastDate yearlyForecast : getLinesYearly())
			{
				BigDecimal qtyBOM = BigDecimal.ONE;
				if (yearlyForecast.getM_Product_ID() != output.getM_Product_ID())
				{
					qtyBOM = MUNSResourceInOut.getBOMQty(
							output.getM_Product_ID(), yearlyForecast.getM_Product_ID(), get_TrxName());
					if (qtyBOM.compareTo(BigDecimal.ZERO) <= 0)
						continue;
				}
				
				//total jam kerja perhari dalam satu minggu berdasarkan bulan saat ini dan product output saat ini
				double[] currentProductDailyProdHours = 
						(double[]) m_AllDayProductProductionHours.get(yearlyForecast.getM_Product_ID());
				
				double totalMForcastMonthlyQty = 0.0;
				double totalTargetForecastMonthlyQty = 0.0;
				BigDecimal productWeight = yearlyForecast.getM_Product().getWeight();

				for (MUNSForecastDate monthlyForecast : yearlyForecast.getLinesMonthOfYear(false))
				{
					
					cal.setTimeInMillis(monthlyForecast.getStartDate().getTime());
					
					double salesFInputMonthlyQty = output.getForecastMarketingQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					double targetFInputMonthlyQty = output.getForecastByTargetedQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					double productionHoursInMonth = 
							MUNSResourceInOut.getTotalProductionHoursInMonthOf(
									cal, 
									currentProductDailyProdHours,
									monthlyForecast.getAD_Org_ID(),
									get_TrxName());
					monthlyForecast.setQtyMForecast(
							monthlyForecast.getQtyMForecast().add(new BigDecimal(salesFInputMonthlyQty)));
					
					monthlyForecast.setDecidedQty(
							monthlyForecast.getDecidedQty().add(new BigDecimal(targetFInputMonthlyQty)));
					
					totalMForcastMonthlyQty += monthlyForecast.getQtyMForecast().doubleValue();
					
					totalTargetForecastMonthlyQty += monthlyForecast.getDecidedQty().doubleValue();

					double avgSalesFInputQtyPerHours = //salesFInputMonthlyQty / productionHoursInMonth;
							monthlyForecast.getQtyMForecast().doubleValue() / productionHoursInMonth;

					double avgTargetFInputQtyPerHours = //targetFInputMonthlyQty / productionHoursInMonth;
							monthlyForecast.getDecidedQty().doubleValue() / productionHoursInMonth;
					
					BigDecimal qtyMForecastMT = 
							BigDecimal.valueOf(salesFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setQtyMForecastMT(monthlyForecast.getQtyMForecastMT().add(qtyMForecastMT));
					
					BigDecimal decidedQtyMT = 
							new BigDecimal(targetFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setDecidedQtyMT(monthlyForecast.getDecidedQtyMT().add(decidedQtyMT));

					monthlyForecast.setTotalExpectedRevenueIDR(
							monthlyForecast.getExpectedRevenueIDR().multiply(monthlyForecast.getDecidedQty()));
					monthlyForecast.setTotalExpectedRevenueSGD(
							monthlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
					monthlyForecast.setTotalExpectedRevenueUSD(
							monthlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
					monthlyForecast.save();

					for (MUNSForecastDate dailyForecast : monthlyForecast.getLinesDayOfMonth())
					{
						Calendar todayCal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
						todayCal.setTimeInMillis(dailyForecast.getForcastedDate().getTime());
						int today = todayCal.get(Calendar.DAY_OF_WEEK);
						
						if (MNonBusinessDay.isNonBusinessDay(getCtx(), new Timestamp(todayCal.getTimeInMillis()),
								monthlyForecast.getAD_Org_ID(), get_TrxName()))
							continue;
							
						double totalDayProductionHours = currentProductDailyProdHours[today-1];
						
						double totalSalesFInputPerDay = avgSalesFInputQtyPerHours * totalDayProductionHours;
						double totalTargetedFInputPerDay = avgTargetFInputQtyPerHours * totalDayProductionHours;
						dailyForecast.setQtyMForecast(new BigDecimal(totalSalesFInputPerDay));
						dailyForecast.setDecidedQty(new BigDecimal(totalTargetedFInputPerDay));
						
						dailyForecast.setTotalExpectedRevenueIDR(
								dailyForecast.getExpectedRevenueIDR().multiply(dailyForecast.getDecidedQty()));
						
						dailyForecast.setTotalExpectedRevenueSGD(
								dailyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
						dailyForecast.setTotalExpectedRevenueUSD(
								dailyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
						
//						dailyForecast.setQtyMForecastMT(
//								new BigDecimal(totalSalesFInputPerDay)
//								.multiply(dailyForecast.getM_Product().getWeight())
//								.multiply(new BigDecimal(0.001)));
//						
//						dailyForecast.setDecidedQtyMT(
//								new BigDecimal(totalTargetedFInputPerDay)
//								.multiply(dailyForecast.getM_Product().getWeight())
//								.multiply(new BigDecimal(0.001)));
						
						dailyForecast.setQtyMForecastMT(
								new BigDecimal(totalSalesFInputPerDay)
								.multiply(productWeight)
								.multiply(new BigDecimal(0.001)));
						
						dailyForecast.setDecidedQtyMT(
								new BigDecimal(totalTargetedFInputPerDay)
								.multiply(productWeight)
								.multiply(new BigDecimal(0.001)));
						
						for(MUNSForecastDate weeklyForecast : yearlyForecast.getLinesWeekOfYear())
						{
							String key = weeklyForecast.getWeekNo() +"_"+weeklyForecast.getM_Product_ID();
							weeklyForecast.setQtyMForecast(BigDecimal.ZERO);
							weeklyForecast.setDecidedQty(BigDecimal.ZERO);
							weeklyForecast.setQtyMForecastMT(BigDecimal.ZERO);
							weeklyForecast.setDecidedQtyMT(BigDecimal.ZERO);
							weeklyForecast.setTotalExpectedRevenueIDR(BigDecimal.ZERO);
							weeklyForecast.setTotalExpectedRevenueSGD(BigDecimal.ZERO);
							weeklyForecast.setTotalExpectedRevenueUSD(BigDecimal.ZERO);
							
							mapWeeklyForecast.put(key, weeklyForecast);
						}
						
						//Ambil weeklyForecast utk minggu pertama.
						int weekNo = todayCal.get(Calendar.WEEK_OF_YEAR);
						if (todayCal.get(Calendar.DAY_OF_YEAR) > 350 && weekNo == 1)
						{
							weekNo = 53;
						}
						String key = weekNo +"_"+monthlyForecast.getM_Product_ID();
						MUNSForecastDate weeklyTmp = mapWeeklyForecast.get(key);

						weeklyTmp.setQtyMForecast(weeklyTmp.getQtyMForecast()
								.add(dailyForecast.getQtyMForecast()));
						
						weeklyTmp.setDecidedQty(weeklyTmp.getDecidedQty()
								.add(dailyForecast.getDecidedQty()));
						
						weeklyTmp.setTotalExpectedRevenueIDR(weeklyTmp.getTotalExpectedRevenueIDR()
								.add(dailyForecast.getTotalExpectedRevenueIDR()));
						
						weeklyTmp.setTotalExpectedRevenueSGD(weeklyTmp.getTotalExpectedRevenueSGD()
								.add(dailyForecast.getTotalExpectedRevenueSGD()));
						
						weeklyTmp.setTotalExpectedRevenueUSD(weeklyTmp.getTotalExpectedRevenueUSD()
								.add(dailyForecast.getTotalExpectedRevenueUSD()));
						
						weeklyTmp.setQtyMForecastMT(weeklyTmp.getQtyMForecastMT()
								.add(dailyForecast.getQtyMForecastMT()));
						
						weeklyTmp.setDecidedQtyMT(weeklyTmp.getDecidedQtyMT()
								.add(dailyForecast.getDecidedQtyMT()));
					}
				}
				
				for (MUNSForecastDate weeklyForecast : mapWeeklyForecast.values())
				{
					weeklyForecast.save();
				}
				
				yearlyForecast.setQtyMForecast(new BigDecimal(totalMForcastMonthlyQty));
				yearlyForecast.setDecidedQty(new BigDecimal(totalTargetForecastMonthlyQty));
			
				yearlyForecast.setTotalExpectedRevenueIDR(
						yearlyForecast.getExpectedRevenueIDR().multiply(yearlyForecast.getDecidedQty()));
				yearlyForecast.setTotalExpectedRevenueSGD(
						yearlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
				yearlyForecast.setTotalExpectedRevenueUSD(
						yearlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
				
				yearlyForecast.setQtyMForecastMT(
						new BigDecimal(totalMForcastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				
//				yearlyProductForecast.setDecidedQtyMT(
//						new BigDecimal(totalMonthlyQtyTargetForecast)
//						.multiply(yearlyProductForecast.getM_Product().getWeight())
//						.multiply(new BigDecimal(0.001)));
				
				yearlyForecast.setDecidedQtyMT(
						new BigDecimal(totalTargetForecastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				
				yearlyForecast.save();
			}
		}
		updateForecastInputQty();
	}
	
	Hashtable<String, MUNSForecastDate> m_mapWeeklyForecast = new Hashtable<String, MUNSForecastDate>();
	List<MUNSForecastDate> m_yearlyForecastList = new ArrayList<MUNSForecastDate>();
	List<MUNSForecastDate> m_monthlyForecastList = new ArrayList<MUNSForecastDate>();
	List<MUNSForecastDate> m_dailyForecastList = new ArrayList<MUNSForecastDate>();
	
	/**
	 * 
	 */
	public void updateForecastByOutput()
	{
		prepare();
		m_AllDayProductProductionHours = m_Resource.getAllProductProductionHours();
		log.warning("Resetting Targeted Forecast ....");
		resetTargetedForecast();
		log.warning("Resetting Targeted Forecast DONE.");

		log.warning("Updating Primary Output Forecast ....");
		updateForecastByOutput(true);
		log.warning("Updating Primary Output Forecast DONE.");

		log.warning("Updating Forecast Input ....");
		updateForecastInputQty();
		log.warning("Updating Forecast Input DONE.");

		log.warning("Updating Non-Primary Output Forecast ....");
		updateNonPrimaryOutputQty();
		log.warning("Updating Non-Primary Output Forecast DONE.");
		
		log.warning("Execute Update of Output Forecast ....");
		executeBatchUpdateForecastByOutput();
		log.warning("Execute Update of Output Forecast DONE.");
	}
	
	/**
	 * 
	 */
	public void updateForecastByOutput(boolean isPrimaryUpdate)
	{
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);

		MUNSProductAllocation[] outputList = getOutputsAllocation(false);
		MUNSForecastDate[] yearlyForecastList = getLinesYearly();
		
		for (MUNSProductAllocation output : outputList)
		{
			if (isPrimaryUpdate != output.isPrimary())
				continue;
			for (MUNSForecastDate yearlyForecast : yearlyForecastList)
			{
				BigDecimal qtyBOM = BigDecimal.ONE;
				
				if (yearlyForecast.getM_Product_ID() != output.getM_Product_ID())
				{
					qtyBOM = MUNSResourceInOut.getBOMQty(
							output.getM_Product_ID(), yearlyForecast.getM_Product_ID(), get_TrxName());
					if (qtyBOM.compareTo(BigDecimal.ZERO) <= 0)
						continue;
				}
				
				double totalMForcastMonthlyQty = 0.0;
				double totalTargetForecastMonthlyQty = 0.0;
				BigDecimal productWeight = MProduct.get(getCtx(), yearlyForecast.getM_Product_ID()).getWeight();
						//yearlyForecast.getM_Product().getWeight();

				MUNSForecastDate[] monthlyForecastList = yearlyForecast.getLinesMonthOfYear(false);
				for (MUNSForecastDate monthlyForecast : monthlyForecastList)
				{
					
					cal.setTimeInMillis(monthlyForecast.getStartDate().getTime());
					
					double salesFInputMonthlyQty = output.getForecastMarketingQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					double targetFInputMonthlyQty = output.getForecastByTargetedQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					monthlyForecast.setQtyMForecast(
							monthlyForecast.getQtyMForecast().add(new BigDecimal(salesFInputMonthlyQty)));
					
					monthlyForecast.setDecidedQty(
							monthlyForecast.getDecidedQty().add(new BigDecimal(targetFInputMonthlyQty)));
					
					totalMForcastMonthlyQty += monthlyForecast.getQtyMForecast().doubleValue();
					
					totalTargetForecastMonthlyQty += monthlyForecast.getDecidedQty().doubleValue();

					BigDecimal qtyMForecastMT = 
							BigDecimal.valueOf(salesFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setQtyMForecastMT(monthlyForecast.getQtyMForecastMT().add(qtyMForecastMT));
					
					BigDecimal decidedQtyMT = 
							new BigDecimal(targetFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setDecidedQtyMT(monthlyForecast.getDecidedQtyMT().add(decidedQtyMT));

					monthlyForecast.setTotalExpectedRevenueIDR(
							monthlyForecast.getExpectedRevenueIDR().multiply(monthlyForecast.getDecidedQty()));
					monthlyForecast.setTotalExpectedRevenueSGD(
							monthlyForecast.getExpectedRevenueSGD().multiply(monthlyForecast.getDecidedQty()));
					monthlyForecast.setTotalExpectedRevenueUSD(
							monthlyForecast.getExpectedRevenueUSD().multiply(monthlyForecast.getDecidedQty()));
					
					m_monthlyForecastList.add(monthlyForecast);
					//monthlyForecast.save();
				}
				
				yearlyForecast.setQtyMForecast(new BigDecimal(totalMForcastMonthlyQty));
				yearlyForecast.setDecidedQty(new BigDecimal(totalTargetForecastMonthlyQty));
			
				yearlyForecast.setTotalExpectedRevenueIDR(
						yearlyForecast.getExpectedRevenueIDR().multiply(yearlyForecast.getDecidedQty()));
				yearlyForecast.setTotalExpectedRevenueSGD(
						yearlyForecast.getExpectedRevenueSGD().multiply(yearlyForecast.getDecidedQty()));
				yearlyForecast.setTotalExpectedRevenueUSD(
						yearlyForecast.getExpectedRevenueUSD().multiply(yearlyForecast.getDecidedQty()));
				
				yearlyForecast.setQtyMForecastMT(
						new BigDecimal(totalMForcastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				
				yearlyForecast.setDecidedQtyMT(
						new BigDecimal(totalTargetForecastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				m_yearlyForecastList.add(yearlyForecast);
				//yearlyForecast.save();
			}
		}
		/*
		Hashtable<String, MUNSForecastDate> mapWeeklyForecast = new Hashtable<String, MUNSForecastDate>();
		List<MUNSForecastDate> yearlyForecastList = new ArrayList<MUNSForecastDate>();
		List<MUNSForecastDate> monthlyForecastList = new ArrayList<MUNSForecastDate>();
		List<MUNSForecastDate> dailyForecastList = new ArrayList<MUNSForecastDate>();
		*/

		for (MUNSForecastDate yearlyForecast : getLinesYearly())
		{
			if (isPrimaryUpdate != yearlyForecast.isPrimary()) 
				continue;
			
			MUNSForecastDate[] weeklyForecastList = yearlyForecast.getLinesWeekOfYear();
			for(MUNSForecastDate weeklyForecast : weeklyForecastList)
			{
				String key = weeklyForecast.getWeekNo() +"_"+weeklyForecast.getM_Product_ID();

				m_mapWeeklyForecast.put(key, weeklyForecast);
			}
					
			//total jam kerja perhari dalam satu minggu berdasarkan bulan saat ini dan product output saat ini
			double[] currentProductDailyProdHours = 
					(double[]) m_AllDayProductProductionHours.get(yearlyForecast.getM_Product_ID());
			
			BigDecimal productWeight = yearlyForecast.getM_Product().getWeight();

			MUNSForecastDate[] monthlyForecastList = yearlyForecast.getLinesMonthOfYear(false);
			for (MUNSForecastDate monthlyForecast : monthlyForecastList)
			{
				
				cal.setTimeInMillis(monthlyForecast.getStartDate().getTime());
				
				double productionHoursInMonth = MUNSResourceInOut
						.getTotalProductionHoursInMonthOf(cal, currentProductDailyProdHours, 
								yearlyForecast.getAD_Org_ID(), get_TrxName());
				
				double avgSalesFInputQtyPerHours = //salesFInputMonthlyQty / productionHoursInMonth;
						monthlyForecast.getQtyMForecast().doubleValue() / productionHoursInMonth;
				
				double avgTargetFInputQtyPerHours = //targetFInputMonthlyQty / productionHoursInMonth;
						monthlyForecast.getDecidedQty().doubleValue() / productionHoursInMonth;
				
				MUNSForecastDate[] dailyForecastList = monthlyForecast.getLinesDayOfMonth();
				for (MUNSForecastDate dailyForecast : dailyForecastList)
				{
					Calendar todayCal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
					todayCal.setTimeInMillis(dailyForecast.getForcastedDate().getTime());
					int today = todayCal.get(Calendar.DAY_OF_WEEK);
					
					if (MNonBusinessDay.isNonBusinessDay(getCtx(), new Timestamp(todayCal.getTimeInMillis()), 
							monthlyForecast.getAD_Org_ID(), get_TrxName()))
						continue;
						
					double totalDayProductionHours = currentProductDailyProdHours[today-1];
					
					double totalSalesFInputPerDay = avgSalesFInputQtyPerHours * totalDayProductionHours;
					double totalTargetedFInputPerDay = avgTargetFInputQtyPerHours * totalDayProductionHours;
					dailyForecast.setQtyMForecast(new BigDecimal(totalSalesFInputPerDay));
					dailyForecast.setDecidedQty(new BigDecimal(totalTargetedFInputPerDay));
					
					dailyForecast.setTotalExpectedRevenueIDR(
							dailyForecast.getExpectedRevenueIDR().multiply(dailyForecast.getDecidedQty()));
					dailyForecast.setTotalExpectedRevenueSGD(
							dailyForecast.getExpectedRevenueSGD().multiply(dailyForecast.getDecidedQty()));
					dailyForecast.setTotalExpectedRevenueUSD(
							dailyForecast.getExpectedRevenueUSD().multiply(dailyForecast.getDecidedQty()));
					
					dailyForecast.setQtyMForecastMT(
							new BigDecimal(totalSalesFInputPerDay)
							.multiply(productWeight)
							.multiply(new BigDecimal(0.001)));
					
					dailyForecast.setDecidedQtyMT(
							new BigDecimal(totalTargetedFInputPerDay)
							.multiply(productWeight)
							.multiply(new BigDecimal(0.001)));
					
					m_dailyForecastList.add(dailyForecast);

					//Ambil weeklyForecast utk minggu pertama.
					int weekNo = todayCal.get(Calendar.WEEK_OF_YEAR);
					if (todayCal.get(Calendar.DAY_OF_YEAR) > 350 && weekNo == 1)
					{
						weekNo = 53;
					}
					String key = weekNo +"_"+monthlyForecast.getM_Product_ID();
					MUNSForecastDate weeklyTmp = m_mapWeeklyForecast.get(key);

					weeklyTmp.setQtyMForecast(weeklyTmp.getQtyMForecast()
							.add(dailyForecast.getQtyMForecast()));
					
					weeklyTmp.setDecidedQty(weeklyTmp.getDecidedQty()
							.add(dailyForecast.getDecidedQty()));
					
					weeklyTmp.setTotalExpectedRevenueIDR(weeklyTmp.getTotalExpectedRevenueIDR()
							.add(dailyForecast.getTotalExpectedRevenueIDR()));
					
					weeklyTmp.setTotalExpectedRevenueSGD(weeklyTmp.getTotalExpectedRevenueSGD()
							.add(dailyForecast.getTotalExpectedRevenueSGD()));
					
					weeklyTmp.setTotalExpectedRevenueUSD(weeklyTmp.getTotalExpectedRevenueUSD()
							.add(dailyForecast.getTotalExpectedRevenueUSD()));
					
					weeklyTmp.setQtyMForecastMT(weeklyTmp.getQtyMForecastMT()
							.add(dailyForecast.getQtyMForecastMT()));
					
					weeklyTmp.setDecidedQtyMT(weeklyTmp.getDecidedQtyMT()
							.add(dailyForecast.getDecidedQtyMT()));
				}
			}
		}
		/*
		for (MUNSForecastDate weeklyForecast : m_mapWeeklyForecast.values()) {
			weeklyForecast.rescaleAllNumbers();
			//weeklyForecast.save();
		}
		
		DBUtils.executeBatchUpdateOfPO(m_mapWeeklyForecast, get_TrxName());
		
		for (MUNSForecastDate yearlyForecast : getLinesYearly())
		{
			if (isPrimaryUpdate != yearlyForecast.isPrimary()) 
				continue;
			yearlyForecast.rescaleAllNumbers();
			m_yearlyForecastList.add(yearlyForecast);
			//if (!yearlyForecast.save())
			//	throw new AdempiereException("Failed when saving Yearly-Forecast");
			
			for (MUNSForecastDate monthlyForecast : yearlyForecast.getLinesMonthOfYear(false))
			{
				monthlyForecast.rescaleAllNumbers();
				m_monthlyForecastList.add(monthlyForecast);
				//if (!monthlyForecast.save())
				//	throw new AdempiereException("Failed when saving Monthly-Forecast");
				
				for (MUNSForecastDate dailyForecast : monthlyForecast.getLinesDayOfMonth())
				{
					dailyForecast.rescaleAllNumbers();
					m_dailyForecastList.add(dailyForecast);
					//if (!dailyForecast.save())
					//	throw new AdempiereException("Failed when saving Daily-Forecast");
				}
			}
		}
		MUNSForecastDate[] yearList = new MUNSForecastDate[m_yearlyForecastList.size()];
		m_yearlyForecastList.toArray(yearList);
		MUNSForecastDate[] monthList = new MUNSForecastDate[m_monthlyForecastList.size()];
		m_monthlyForecastList.toArray(monthList);
		MUNSForecastDate[] dailyList = new MUNSForecastDate[m_dailyForecastList.size()];
		m_dailyForecastList.toArray(dailyList);
		DBUtils.executeBatchUpdateOfPO(yearList, get_TrxName());
		DBUtils.executeBatchUpdateOfPO(monthList, get_TrxName());
		DBUtils.executeBatchUpdateOfPO(dailyList, get_TrxName());
		*/
	}
	
	private void executeBatchUpdateForecastByOutput()
	{
		
		for (MUNSForecastDate weeklyForecast : m_mapWeeklyForecast.values()) {
			weeklyForecast.rescaleAllNumbers();
			//weeklyForecast.save();
		}
		
		DBUtils.executeBatchUpdateOfPO(m_mapWeeklyForecast, get_TrxName());
		
		for (MUNSForecastDate yearlyForecast : m_yearlyForecastList)
			yearlyForecast.rescaleAllNumbers();
		for (MUNSForecastDate monthlyForecast : m_monthlyForecastList)
			monthlyForecast.rescaleAllNumbers();
		for (MUNSForecastDate dailyForecast : m_dailyForecastList)
			dailyForecast.rescaleAllNumbers();
		
		MUNSForecastDate[] yearList = new MUNSForecastDate[m_yearlyForecastList.size()];
		m_yearlyForecastList.toArray(yearList);
		MUNSForecastDate[] monthList = new MUNSForecastDate[m_monthlyForecastList.size()];
		m_monthlyForecastList.toArray(monthList);
		MUNSForecastDate[] dailyList = new MUNSForecastDate[m_dailyForecastList.size()];
		m_dailyForecastList.toArray(dailyList);
		DBUtils.executeBatchUpdateOfPO(yearList, get_TrxName());
		DBUtils.executeBatchUpdateOfPO(monthList, get_TrxName());
		DBUtils.executeBatchUpdateOfPO(dailyList, get_TrxName());
	}
	
	/**
	 * 
	 */
	public void updateForecastByOutput_Old(boolean isPrimaryUpdate)
	{
		prepare();
		m_AllDayProductProductionHours = m_Resource.getAllProductProductionHours();
		
		resetTargetedForecast();
		
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);

		for (MUNSProductAllocation output : getOutputsAllocation(false))
		{
			if (!output.isPrimary())
				continue;
			
			for (MUNSForecastDate yearlyForecast : getLinesYearly())
			{
				BigDecimal qtyBOM = BigDecimal.ONE;
				
				if (yearlyForecast.getM_Product_ID() != output.getM_Product_ID())
				{
					qtyBOM = MUNSResourceInOut.getBOMQty(
							output.getM_Product_ID(), yearlyForecast.getM_Product_ID(), get_TrxName());
					if (qtyBOM.compareTo(BigDecimal.ZERO) <= 0)
						continue;
				}
				
				double totalMForcastMonthlyQty = 0.0;
				double totalTargetForecastMonthlyQty = 0.0;
				BigDecimal productWeight = yearlyForecast.getM_Product().getWeight();

				for (MUNSForecastDate monthlyForecast : yearlyForecast.getLinesMonthOfYear(false))
				{
					
					cal.setTimeInMillis(monthlyForecast.getStartDate().getTime());
					
					double salesFInputMonthlyQty = output.getForecastMarketingQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					double targetFInputMonthlyQty = output.getForecastByTargetedQty(
							cal.get(Calendar.MONTH)).doubleValue() * qtyBOM.doubleValue();
					
					monthlyForecast.setQtyMForecast(
							monthlyForecast.getQtyMForecast().add(new BigDecimal(salesFInputMonthlyQty)));
					
					monthlyForecast.setDecidedQty(
							monthlyForecast.getDecidedQty().add(new BigDecimal(targetFInputMonthlyQty)));
					
					totalMForcastMonthlyQty += monthlyForecast.getQtyMForecast().doubleValue();
					
					totalTargetForecastMonthlyQty += monthlyForecast.getDecidedQty().doubleValue();

					BigDecimal qtyMForecastMT = 
							BigDecimal.valueOf(salesFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setQtyMForecastMT(monthlyForecast.getQtyMForecastMT().add(qtyMForecastMT));
					
					BigDecimal decidedQtyMT = 
							new BigDecimal(targetFInputMonthlyQty)
							.multiply(productWeight).multiply(new BigDecimal(0.001));
					monthlyForecast.setDecidedQtyMT(monthlyForecast.getDecidedQtyMT().add(decidedQtyMT));

					monthlyForecast.setTotalExpectedRevenueIDR(
							monthlyForecast.getExpectedRevenueIDR().multiply(monthlyForecast.getDecidedQty()));
					monthlyForecast.setTotalExpectedRevenueSGD(
							monthlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
					monthlyForecast.setTotalExpectedRevenueUSD(
							monthlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
					//monthlyForecast.save();
				}
				
				yearlyForecast.setQtyMForecast(new BigDecimal(totalMForcastMonthlyQty));
				yearlyForecast.setDecidedQty(new BigDecimal(totalTargetForecastMonthlyQty));
			
				yearlyForecast.setTotalExpectedRevenueIDR(
						yearlyForecast.getExpectedRevenueIDR().multiply(yearlyForecast.getDecidedQty()));
				yearlyForecast.setTotalExpectedRevenueSGD(
						yearlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
				yearlyForecast.setTotalExpectedRevenueUSD(
						yearlyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
				
				yearlyForecast.setQtyMForecastMT(
						new BigDecimal(totalMForcastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				
				yearlyForecast.setDecidedQtyMT(
						new BigDecimal(totalTargetForecastMonthlyQty)
						.multiply(productWeight).multiply(new BigDecimal(0.001)));
				
				yearlyForecast.save();
			}
		}
		
		Hashtable<String, MUNSForecastDate> mapWeeklyForecast = new Hashtable<String, MUNSForecastDate>();

		for (MUNSForecastDate yearlyForecast : getLinesYearly())
		{
			if (!yearlyForecast.isPrimary()) continue;
			
			for(MUNSForecastDate weeklyForecast : yearlyForecast.getLinesWeekOfYear())
			{
				String key = weeklyForecast.getWeekNo() +"_"+weeklyForecast.getM_Product_ID();

				mapWeeklyForecast.put(key, weeklyForecast);
			}
					
			//total jam kerja perhari dalam satu minggu berdasarkan bulan saat ini dan product output saat ini
			double[] currentProductDailyProdHours = 
					(double[]) m_AllDayProductProductionHours.get(yearlyForecast.getM_Product_ID());
			
			BigDecimal productWeight = yearlyForecast.getM_Product().getWeight();

			for (MUNSForecastDate monthlyForecast : yearlyForecast.getLinesMonthOfYear(false))
			{
				
				cal.setTimeInMillis(monthlyForecast.getStartDate().getTime());
				
				double productionHoursInMonth = MUNSResourceInOut
						.getTotalProductionHoursInMonthOf(cal, currentProductDailyProdHours,
								monthlyForecast.getAD_Org_ID(), get_TrxName());
				
				double avgSalesFInputQtyPerHours = //salesFInputMonthlyQty / productionHoursInMonth;
						monthlyForecast.getQtyMForecast().doubleValue() / productionHoursInMonth;
				
				double avgTargetFInputQtyPerHours = //targetFInputMonthlyQty / productionHoursInMonth;
						monthlyForecast.getDecidedQty().doubleValue() / productionHoursInMonth;
				
				for (MUNSForecastDate dailyForecast : monthlyForecast.getLinesDayOfMonth())
				{
					Calendar todayCal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
					todayCal.setTimeInMillis(dailyForecast.getForcastedDate().getTime());
					int today = todayCal.get(Calendar.DAY_OF_WEEK);
					
					if (MNonBusinessDay.isNonBusinessDay(getCtx(), new Timestamp(todayCal.getTimeInMillis()), 
							monthlyForecast.getAD_Org_ID(), get_TrxName()))
						continue;
						
					double totalDayProductionHours = currentProductDailyProdHours[today-1];
					
					double totalSalesFInputPerDay = avgSalesFInputQtyPerHours * totalDayProductionHours;
					double totalTargetedFInputPerDay = avgTargetFInputQtyPerHours * totalDayProductionHours;
					dailyForecast.setQtyMForecast(new BigDecimal(totalSalesFInputPerDay));
					dailyForecast.setDecidedQty(new BigDecimal(totalTargetedFInputPerDay));
					
					dailyForecast.setTotalExpectedRevenueIDR(
							dailyForecast.getExpectedRevenueIDR().multiply(dailyForecast.getDecidedQty()));
					
					dailyForecast.setTotalExpectedRevenueSGD(
							dailyForecast.getTotalExpectedRevenueIDR().multiply(m_ToSGDRate));
					dailyForecast.setTotalExpectedRevenueUSD(
							dailyForecast.getTotalExpectedRevenueIDR().multiply(m_ToIDRRate));
					
					dailyForecast.setQtyMForecastMT(
							new BigDecimal(totalSalesFInputPerDay)
							.multiply(productWeight)
							.multiply(new BigDecimal(0.001)));
					
					dailyForecast.setDecidedQtyMT(
							new BigDecimal(totalTargetedFInputPerDay)
							.multiply(productWeight)
							.multiply(new BigDecimal(0.001)));						

					//Ambil weeklyForecast utk minggu pertama.
					int weekNo = todayCal.get(Calendar.WEEK_OF_YEAR);
					if (todayCal.get(Calendar.DAY_OF_YEAR) > 350 && weekNo == 1)
					{
						weekNo = 53;
					}
					String key = weekNo +"_"+monthlyForecast.getM_Product_ID();
					MUNSForecastDate weeklyTmp = mapWeeklyForecast.get(key);

					weeklyTmp.setQtyMForecast(weeklyTmp.getQtyMForecast()
							.add(dailyForecast.getQtyMForecast()));
					
					weeklyTmp.setDecidedQty(weeklyTmp.getDecidedQty()
							.add(dailyForecast.getDecidedQty()));
					
					weeklyTmp.setTotalExpectedRevenueIDR(weeklyTmp.getTotalExpectedRevenueIDR()
							.add(dailyForecast.getTotalExpectedRevenueIDR()));
					
					weeklyTmp.setTotalExpectedRevenueSGD(weeklyTmp.getTotalExpectedRevenueSGD()
							.add(dailyForecast.getTotalExpectedRevenueSGD()));
					
					weeklyTmp.setTotalExpectedRevenueUSD(weeklyTmp.getTotalExpectedRevenueUSD()
							.add(dailyForecast.getTotalExpectedRevenueUSD()));
					
					weeklyTmp.setQtyMForecastMT(weeklyTmp.getQtyMForecastMT()
							.add(dailyForecast.getQtyMForecastMT()));
					
					weeklyTmp.setDecidedQtyMT(weeklyTmp.getDecidedQtyMT()
							.add(dailyForecast.getDecidedQtyMT()));
				}
			}
		}

		for (MUNSForecastDate weeklyForecast : mapWeeklyForecast.values())
			weeklyForecast.save();
		
		for (MUNSForecastDate yearlyForecast : getLinesYearly())
		{
			if (!yearlyForecast.isPrimary()) continue;
			
			if (!yearlyForecast.save())
				throw new AdempiereException("Failed when saving Yearly-Forecast");
			
			for (MUNSForecastDate monthlyForecast : yearlyForecast.getLinesMonthOfYear(false))
			{
				if (!monthlyForecast.save())
					throw new AdempiereException("Failed when saving Monthly-Forecast");
				
				for (MUNSForecastDate dailyForecast : monthlyForecast.getLinesDayOfMonth())
				{
					if (!dailyForecast.save())
						throw new AdempiereException("Failed when saving Daily-Forecast");
				}
			}
		}
		
		updateForecastInputQty();
	}
	
	/**
	 * Update forecast input for its quantities of Marketing & Targeted forecast allocation in which defined by
	 * it's Output Allocation.
	 */
	private void updateForecastInputQty()
	{
		int periodStart = getPeriodStart().getPeriodNo();
		int periodEnd = getPeriodEnd().getPeriodNo();
		
		// Reset the input qty of Marketing and Targeted forecasting data if any to Zero.
		for (MUNSForecastInput forecastInput : getInputAllocations())
		{
			for (int i = periodStart-1; i <= periodEnd-1; i++)
			{
				forecastInput.setQtyForecastMarketing(i, BigDecimal.ZERO);
				forecastInput.setQtyForecastMarketingMT(i, BigDecimal.ZERO);
				forecastInput.setQtyTargetedForecast(i, BigDecimal.ZERO);
				forecastInput.setQtyTargetedForecastMT(i, BigDecimal.ZERO);
			}
		}

		for (MUNSProductAllocation outputAllocation : getOutputsAllocation(true))
		{
			if (!outputAllocation.isPrimary()) 
				continue;
			
			for (MUNSForecastInput forecastInput : getInputAllocations())
			{	
				if (forecastInput.isPredictedRejectMaterialTo(outputAllocation.getM_Product_ID()))
					continue;
				
				BigDecimal bomQty = MUNSResourceInOut.getBOMQty(
						outputAllocation.getM_Product_ID(), forecastInput.getM_Product_ID(), get_TrxName());
				
				if (bomQty.compareTo(BigDecimal.ZERO) <= 0)
					continue;
				
				BigDecimal inputWeight = forecastInput.getM_Product().getWeight();
				
				for (int i = periodStart-1; i <= periodEnd-1; i++)
				{
					BigDecimal outputMarketingQty = outputAllocation.getForecastByMarketingQty(i);
					BigDecimal outputTargetedQty = outputAllocation.getForecastByTargetedQty(i);
					
					if (outputMarketingQty.signum() <=0 && outputTargetedQty.signum() <=0)
						continue;
					BigDecimal totalRMPInput = Env.ZERO;
					for (MUNSForecastInput rmpInput : getInputAllocations()) 
					{
						if (rmpInput.getM_Product_ID() == forecastInput.getM_Product_ID()) {
							totalRMPInput = totalRMPInput.add(rmpInput.getQtyForecastRMP(i));
						}
						else if (!rmpInput.isPredictedRejectMaterialTo(outputAllocation.getM_Product_ID())) 
						{
							BigDecimal bomQtyRMP = MUNSResourceInOut.getBOMQty(
									outputAllocation.getM_Product_ID(), rmpInput.getM_Product_ID(), get_TrxName());
							BigDecimal rmpInputQty = rmpInput.getQtyForecastRMP(i);
							if (bomQtyRMP.compareTo(BigDecimal.ZERO) > 0 
								&& rmpInputQty != null && rmpInputQty.signum() >0) 
							{
								BigDecimal outputTmpQty = rmpInputQty.divide(bomQtyRMP, 10, BigDecimal.ROUND_HALF_UP);
								BigDecimal inputTmpQty = outputTmpQty.multiply(bomQty);
								totalRMPInput = totalRMPInput.add(inputTmpQty);
							}
						}
					}
					BigDecimal outputProportion = forecastInput.getQtyForecastRMP(i)
							.divide(totalRMPInput, 15, BigDecimal.ROUND_HALF_UP);
					
					BigDecimal fInputQty = forecastInput.getQtyMarketingForecast(i);
					BigDecimal inputToOutputAllocation = outputAllocation.getForecastMarketingQty(i)
							.multiply(outputProportion).multiply(bomQty);
					BigDecimal totalCurrentInputQty = fInputQty.add(inputToOutputAllocation);
					
					forecastInput.setQtyForecastMarketing(i, totalCurrentInputQty);
					/*
					forecastInput.setQtyForecastMarketing(i, 
							forecastInput.getQtyMarketingForecast(i).add(
									outputAllocation.getForecastMarketingQty(i)
									.multiply(outputProportion).multiply(bomQty)));
					*/
					forecastInput.setQtyForecastMarketingMT(i, 
							forecastInput.getQtyMarketingForecast(i)
							.multiply(inputWeight).multiply(new BigDecimal(0.001)));
					
					forecastInput.setQtyTargetedForecast(i, 
							forecastInput.getQtyTargetedForecast(i).add(
									outputAllocation.getForecastByTargetedQty(i)
									.multiply(outputProportion).multiply(bomQty)));
					
					forecastInput.setQtyTargetedForecastMT(i, 
							forecastInput.getQtyTargetedForecast(i)
							.multiply(inputWeight).multiply(new BigDecimal(0.001)));
				}
			}
		}
		for (MUNSForecastInput forecastInput : getInputAllocations()) {
			forecastInput.rescaleAllNumbers();
			//if (!forecastInput.save())
			//	throw new AdempiereException("Failed when updating Forecast Input Allocation.");
		}
		DBUtils.executeBatchUpdateOfPO(getInputAllocations(), get_TrxName());
	}

	/**
	 * 
	 */
	void updateNonPrimaryOutputQty()
	{
		// initialize the non primary output proportion.
		// The first key is the Product ID defined as the output of m_Resource's start nodes.
		// The second Hashtable will contain Output's product-id and the Total Allocation's of the output represent by
		// MUNSForecastInput class.
		Hashtable<Integer, MUNSForecastInput> MapOfTotalGroupOutputAllocation = 
				new Hashtable<Integer, MUNSForecastInput>();
		
		initNonPrimaryStartNodesTotalOuput(MapOfTotalGroupOutputAllocation);
		
		int periodStart = getPeriodStart().getPeriodNo();
		int periodEnd = getPeriodEnd().getPeriodNo();
		BigDecimal productWeight = Env.ZERO;
		
		List<MUNSProductAllocation> outputListToUpdate = new ArrayList<MUNSProductAllocation>();
		// Update the output allocation based on the updated input quantity for the Sales & Targeted forecast.
		for (MUNSProductAllocation output : getOutputsAllocation(false))
		{
			if (output.isPrimary()) continue;
			
			MUNSForecastInput totalOutputAllocation = null;
			for (MUNSForecastInput inputAllocation : MapOfTotalGroupOutputAllocation.values())
			{
				if (inputAllocation.getPossibleOutputBOMOf(output.getM_Product_ID()) != null){
					totalOutputAllocation = inputAllocation;
					break;
				}
			}
			if (totalOutputAllocation == null)
				continue;
			
			BigDecimal outputAllocationBOMQty = totalOutputAllocation.getPossibleOutputBOMOf(output.getM_Product_ID());
			
			BigDecimal qtyBOM = BigDecimal.ONE;
			productWeight = output.getM_Product().getWeight();
			
			int counter = 0;
			Hashtable<Integer, BigDecimal> marketingMap = new Hashtable<Integer, BigDecimal>();
			Hashtable<Integer, BigDecimal> targetedMap = new Hashtable<Integer, BigDecimal>();
			
			for (MUNSForecastInput input : getInputAllocations()) 
			{
				qtyBOM = MUNSResourceInOut.getBOMQty(
						output.getM_Product_ID(), input.getM_Product_ID(), get_TrxName());
				if (qtyBOM.compareTo(BigDecimal.ZERO) <= 0)
					continue;
				
				counter++;
				BigDecimal prevMarketingOutput = Env.ZERO;
				BigDecimal prevTargetedOutput = Env.ZERO;
				for (int i = periodStart-1; i <= periodEnd-1; i++)
				{
					if (counter > 1) {
						prevMarketingOutput = output.getForecastByMarketingQty(i);
						prevTargetedOutput = output.getForecastByTargetedQty(i);
					}
					BigDecimal marketingAllocation = marketingMap.get(i);
					BigDecimal targetedAllocation = targetedMap.get(i);
					if (marketingAllocation == null)
					{
						marketingAllocation = output.getForecastByMarketingQty(i).multiply(outputAllocationBOMQty);
						targetedAllocation = output.getForecastByTargetedQty(i).multiply(outputAllocationBOMQty);
						marketingMap.put(i, marketingAllocation);
						targetedMap.put(i, targetedAllocation);
					}
					if (totalOutputAllocation.getQtyMarketingForecast(i).compareTo(Env.ZERO) <= 0 
						|| totalOutputAllocation.getQtyTargetedForecast(i).compareTo(Env.ZERO) <= 0) 
					{
						continue;
						//throw new AdempiereException("You have to set at least one of secondary end-product into non-zero quantity.");
					}
					
					BigDecimal marketingProportion = marketingAllocation.divide(
							totalOutputAllocation.getQtyMarketingForecast(i), 10, BigDecimal.ROUND_HALF_UP);
					
					output.setQtyMarketingForecast(i, 
							prevMarketingOutput.add(
									input.getQtyMarketingForecast(i).multiply(marketingProportion)
									.divide(qtyBOM, 10, BigDecimal.ROUND_HALF_UP)));
					output.setQtyMarketingForecastMT(i, 
							output.getForecastByMarketingQty(i)
							.multiply(productWeight).multiply(BigDecimal.valueOf(0.001)));
					
					BigDecimal targetedProportion = targetedAllocation.divide(
							totalOutputAllocation.getQtyTargetedForecast(i), 10, BigDecimal.ROUND_HALF_UP);

					output.setQtyTargetedForecast(i, 
							prevTargetedOutput.add(
									input.getQtyTargetedForecast(i).multiply(targetedProportion)
									.divide(qtyBOM, 10, BigDecimal.ROUND_HALF_UP)));
					output.setQtyTargetedForecastMT(i, 
							output.getForecastByTargetedQty(i)
							.multiply(productWeight).multiply(BigDecimal.valueOf(0.001)));
				}
			}
			output.rescaleAllNumbers();
			outputListToUpdate.add(output);
			//if (!output.save())
			//	throw new AdempiereException("Failed when updating Non-Primary Output Allocation.");
		}
		MUNSProductAllocation[] outputList = new MUNSProductAllocation[outputListToUpdate.size()];
		outputListToUpdate.toArray(outputList);
		DBUtils.executeBatchUpdateOfPO(outputList, get_TrxName());
		updateForecastByOutput(false);
	}
	
	/**
	 * Initialize the given mapping of total group output allocation.
	 * @param MapOfTotalGroupOutputAllocation
	 */
	private void initNonPrimaryStartNodesTotalOuput(
			Hashtable<Integer, MUNSForecastInput> MapOfTotalGroupOutputAllocation)
	{ 
		String sql = "SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio " +
				" WHERE rio.InOutType=? " + // AND rio.IsPrimary='N'" +
				" 		AND rio.UNS_Resource_ID IN (SELECT rsc.UNS_Resource_ID FROM UNS_Resource rsc " +
				"									WHERE rsc.ResourceParent_ID=? AND rsc.IsParentStartNode='Y')";
		PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		try {
			stmt.setString(1, MUNSResourceInOut.INOUTTYPE_Output);
			stmt.setInt(2, m_Resource.get_ID());
			
			rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int productID = rs.getInt("M_Product_ID");
				if (MapOfTotalGroupOutputAllocation.get(productID) == null)
					MapOfTotalGroupOutputAllocation.put(productID, new MUNSForecastInput(getCtx(), 0, get_TrxName()));
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException("Failed when initilize Output of Resource's start nodes");
		}
		
		int periodStart = getPeriodStart().getPeriodNo();
		int periodEnd = getPeriodEnd().getPeriodNo();
		
		for (MUNSProductAllocation output : getOutputsAllocation(false))
		{
			if (output.isPrimary()) continue;
			
			for (int startNodeProduct : MapOfTotalGroupOutputAllocation.keySet()) 
			{
				BigDecimal qtyBOM = BigDecimal.ONE;
				if (output.getM_Product_ID() != startNodeProduct) {
					qtyBOM = MUNSResourceInOut.getBOMQty(
							output.getM_Product_ID(), startNodeProduct, get_TrxName());					
					if (qtyBOM.compareTo(BigDecimal.ZERO) <= 0) 
						continue;
				}
				
				MUNSForecastInput inputAllocation = MapOfTotalGroupOutputAllocation.get(startNodeProduct);
				
				if (inputAllocation == null) {
					inputAllocation = new MUNSForecastInput(getCtx(), 0, get_TrxName());
					MapOfTotalGroupOutputAllocation.put(startNodeProduct, inputAllocation);
				}
				inputAllocation.addPossibleOutputs(output.getM_Product_ID(), qtyBOM);
				
				for (int i = periodStart-1; i <= periodEnd-1; i++)
				{
					inputAllocation.setQtyForecastMarketing(i, 
							inputAllocation.getQtyMarketingForecast(i).add(
									output.getForecastByMarketingQty(i).multiply(qtyBOM)));
					inputAllocation.setQtyTargetedForecast(i, 
							inputAllocation.getQtyTargetedForecast(i).add(
									output.getForecastByTargetedQty(i).multiply(qtyBOM)));
				}
				//break;
			}
		}
	}
	
	void updateForecastInputQty_Old()
	{
		//ini akan bernilai true jika output multi dan sudah teralokasikan ke input
		for (MUNSForecastInput forecastInput : getInputAllocations())
		{	
			forecastInput.setQtyToZero();
			boolean isMultiAndAllocated = false;
			for (MUNSProductAllocation outputAllocation : getOutputsAllocation(true))
			{
				if (isMultiAndAllocated) 
					continue;
				
				MUNSResourceInOut output = m_Resource.getResourceOutput(outputAllocation.getM_Product_ID());
				if (null == output)
					continue;
				
				MUNSResource childrscOfOutput = m_Resource.getResourceChildOf(outputAllocation.getM_Product_ID());

				if(childrscOfOutput == null || !childrscOfOutput.isParentStartNode())
					continue;
				
				BigDecimal bomQty = MUNSResourceInOut.getBOMQty(
						outputAllocation.getM_Product_ID(), forecastInput.getM_Product_ID(), get_TrxName());
				
				if (bomQty.compareTo(BigDecimal.ZERO) <= 0)
					continue;
				
				for (int i=0; i<12; i++)
				{
					forecastInput.setQtyForecastMarketing(i, 
							forecastInput.getQtyMarketingForecast(i)
							.add(outputAllocation.getForecastMarketingQty(i).multiply(bomQty)));
					
					forecastInput.setQtyForecastMarketingMT(i, 
							forecastInput.getQtyMarketingForecastMT(i)
							.add(outputAllocation.getForecastMarketingQty(i).multiply(bomQty)
							.multiply(forecastInput.getM_Product()
									.getWeight()).multiply(new BigDecimal(0.001))));
					
					forecastInput.setQtyTargetedForecast(
							i, forecastInput.getQtyTargetedForecast(i)
							.add(outputAllocation.getForecastByTargetedQty(i).multiply(bomQty)));
					
					forecastInput.setQtyTargetedForecastMT(i, 
							forecastInput.getQtyTargetedForecastMT(i)
							.add(outputAllocation.getForecastByTargetedQty(i)
							.multiply(bomQty).multiply(forecastInput.getM_Product()
									.getWeight()).multiply(new BigDecimal(0.001))));
				}
				
				if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi))
					isMultiAndAllocated = true;
			}
			
			forecastInput.save();
		}
	}

	
	/************************************************************************************
	 * 								Process Generate Forecast							*
	 ************************************************************************************/
//	private String m_allocationMethod = GenerateForecast.INPUTALLOCATION_AllPlantOutput;
	
	public String generateIt(String allocationMethod)
	{
//		m_allocationMethod = allocationMethod;
		prepare();
		generateForecast();
		m_processMsg = "Completed.";
		return m_processMsg;
	}
	
	/**
	 * init all requirement Data
	 */
	private void prepare()
	{
		m_Resource = (MUNSResource)getUNS_Resource();
		m_Year = (MYear)getC_Year();
		m_PeriodStart = (MPeriod) getPeriodStart();
		m_PeriodEnd = (MPeriod) getPeriodEnd();
	}

	private Hashtable<Integer, ArrayList<ResourceInputRequirement>>
				createResourceInputRequirementMap(MUNSResource[] listOfRscTransition,
						MUNSForecastInput[] forecastInputList)
	{
		Hashtable<Integer, ArrayList<ResourceInputRequirement>> mapOfRscInputReq = 
				new Hashtable<Integer, ArrayList<ResourceInputRequirement>>(); 
		//String test = "";
		for (MUNSForecastInput input : forecastInputList)
		{
			ArrayList<ResourceInputRequirement> listOfRIR = new ArrayList<ResourceInputRequirement>();
			for (int i=0; i<listOfRscTransition.length; i++)
			{
				if(!listOfRscTransition[i].isRequiredInput(input.getM_Product_ID()))
					continue;
				//
				double totalMaxReqOfRsc = listOfRscTransition[i].getMaxRequirementPerJamOf(input.getM_Product_ID());
				double totalMaxReqOfRscInMTPerJam = totalMaxReqOfRsc * input.getM_Product().getWeight().doubleValue();
				if (totalMaxReqOfRsc <= 0)
					continue;
				ResourceInputRequirement rir = new ResourceInputRequirement(
						listOfRscTransition[i], input, totalMaxReqOfRscInMTPerJam, 0);
				
				listOfRIR.add(rir);
			}
			if (listOfRIR.size()<=0)
				continue;
			if (getAllocationMethod().equals(ALLOCATIONMETHOD_MaximumToMinimum))
				Collections.sort(listOfRIR, Collections.reverseOrder());
			else if (getAllocationMethod().equals(ALLOCATIONMETHOD_MinimumToMaximum))
				Collections.sort(listOfRIR);
			//else
			ResourceInputRequirement.setInputProportion(input, listOfRIR, getAllocationMethod());
			
			mapOfRscInputReq.put(input.getM_Product_ID(), listOfRIR);
		}
		
		return mapOfRscInputReq;
	}
	
	/**
	 * generate Forecast base on daily
	 */
	private void generateForecast()
	{		
		MUNSResource[] startNodes = m_Resource.getStartNodes();
		
		Hashtable<Integer, ArrayList<ResourceInputRequirement>> listOfRscInputReq = 
				createResourceInputRequirementMap(startNodes, getInputAllocations());
		
		for (MUNSResource node : startNodes)
		{	
			generateForecast(node, listOfRscInputReq);
		}
		log.warning("Generating Periodic Forecast values ....");
		generatePeriodicForecastFromDaily();
		log.warning("Generating Periodic Forecast values status DONE.");
		
		log.warning("Saving Forecast values ....");
		//saveForecast();
		saveBatchForecast();
		log.warning("Saving Forecast values DONE.");
	}
	
	
	/**
	 * 
	 * @throws AdempiereException
	 */
	private void saveBatchForecast() throws AdempiereException
	{
		for (MUNSForecastDate yearlyForecast : m_ListYearlyForecast.values())
		{
			yearlyForecast.setUNS_Forecast_Header_ID(getUNS_Forecast_Header_ID());
			yearlyForecast.rescaleAllNumbers();
		}			
		Hashtable<Object, Integer> yearlyIDs =
				DBUtils.executeBatchUpdateOfPO(m_ListYearlyForecast, get_TrxName());
		
		for (Hashtable<Integer, MUNSForecastDate> mapMonthlyForecast : m_ListMonthlyForecast.values())
		{
			for (MUNSForecastDate monthlyForecast : mapMonthlyForecast.values())
			{
				int yearlyId = yearlyIDs.get(monthlyForecast.getM_Product_ID());
				monthlyForecast.setYearly_ID(yearlyId);
				monthlyForecast.rescaleAllNumbers();
			}
		}
		for (Hashtable<Integer, MUNSForecastDate> mapWeeklyForecast : m_ListWeeklyForecast.values())
		{
			for (MUNSForecastDate weeklyForecast : mapWeeklyForecast.values())
			{
				int yearlyId = yearlyIDs.get(weeklyForecast.getM_Product_ID());
				weeklyForecast.setYearly_ID(yearlyId);
				weeklyForecast.rescaleAllNumbers();
			}
		}

		Hashtable<String, Integer> monthlyKeys = executeBatchUpdateOfForecastMap(m_ListMonthlyForecast);
		Hashtable<String, Integer> weeklyKeys = executeBatchUpdateOfForecastMap(m_ListWeeklyForecast);
		
		Calendar today = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		for (MUNSForecastDate dailyForecastDate : m_ListDailyForecast.values())
		{
			int productID = dailyForecastDate.getM_Product_ID();
			today.setTimeInMillis(dailyForecastDate.getForcastedDate().getTime());
			int month = today.get(Calendar.MONTH);
			int weekNo = today.get(Calendar.WEEK_OF_YEAR);
			//int yearlyID = m_ListYearlyForecast.get(productID).get_ID();
			if (today.get(Calendar.DAY_OF_YEAR) >= 350 && weekNo == 1)
				weekNo = 53;
			int monthlyID = monthlyKeys.get(month + "-" + productID);
			int weeklyID = weeklyKeys.get(weekNo + "-" + productID);

			dailyForecastDate.setWeekly_ID(weeklyID);
			dailyForecastDate.setMonthly_ID(monthlyID);
			dailyForecastDate.rescaleAllNumbers();
		}
		DBUtils.executeBatchUpdateOfPO(m_ListDailyForecast, get_TrxName());
	}

	/**
	 * 
	 * @param forecastMap
	 * @return
	 */
	private Hashtable<String, Integer>
		executeBatchUpdateOfForecastMap (Hashtable<Integer, Hashtable<Integer, MUNSForecastDate>> forecastMap)
	{
		String[] colsArray = null;
		String[] typesArray = null;
		String[][] valuesArray = null;
		List<String[]> valuesList = new ArrayList<String[]>();
		String tableName = null;
		
		List<String> keysTmp = new ArrayList<String>();
		Iterator<Integer> firstKeys = forecastMap.keySet().iterator();
		while (firstKeys.hasNext())
		{
			Integer firstKey = firstKeys.next();
			
			String keyTmp = null;
			
			Hashtable<Integer, MUNSForecastDate> periodForecastMap = forecastMap.get(firstKey);
			
			Iterator<Integer> secondKeys = periodForecastMap.keySet().iterator();
			while (secondKeys.hasNext())
			{
				int secondKey = secondKeys.next();
				keyTmp = firstKey + "-" + secondKey;
				
				keysTmp.add(keyTmp);
				
				PO thePO = (PO) periodForecastMap.get(secondKey);
				
				if (colsArray == null || typesArray == null)
				{
					tableName = thePO.get_TableName();
					colsArray = DBUtils.getPOColumnsAsArrayOfString(thePO);
					typesArray = DBUtils.getPOTypesAsArrayOfString(thePO);
				}
				valuesList.add(DBUtils.getPOValuesAsArrayOfString(thePO));
			}
		}
		valuesArray = new String[valuesList.size()][];
		valuesList.toArray(valuesArray);
		
		Hashtable<String, Integer> idMapOfKeys = new Hashtable<String, Integer>();
		
		String sql = "SELECT * FROM executeBatchUpdatePO(?, ?, ?, ?)";
	
		PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		try {
			Connection conn = stmt.getConnection();
			/*
			Array pgColsArray = conn.createArrayOf("varchar", colsArray);
			Array pgTypesArray = conn.createArrayOf("varchar", typesArray);
			Array pgValuesArray = conn.createArrayOf("varchar", valuesArray);
			*/
			Array pgColsArray = DBUtils.getSqlArray(colsArray, "varchar", conn);
			Array pgTypesArray = DBUtils.getSqlArray(typesArray, "varchar", conn);
			Array pgValuesArray = DBUtils.getSqlArray(valuesArray, "varchar", conn);
			stmt.setString(1, tableName);
			stmt.setArray(2, pgColsArray);
			stmt.setArray(3, pgTypesArray);
			stmt.setArray(4, pgValuesArray);
			
			rs = stmt.executeQuery();
			//int i = 0;
			while (rs.next())
			{
				int theRowIx = rs.getInt(DBUtils.COLUMN_ROW_INDEX);
				int theNewId = rs.getInt(DBUtils.COLUMN_ROW_ID);
				
				String theKey = keysTmp.get(theRowIx);
				
				idMapOfKeys.put(theKey, theNewId);
				//i++;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new AdempiereException(ex.getMessage());
		}
		return idMapOfKeys;
	}
	
	/**
	 * 
	 * @throws AdempiereException
	 * @Deprecated
	 */
	void saveForecast() throws AdempiereException
	{
		for (MUNSForecastDate yearlyForecast : m_ListYearlyForecast.values())
		{
			try {
				yearlyForecast.setUNS_Forecast_Header_ID(getUNS_Forecast_Header_ID());
				yearlyForecast.rescaleAllNumbers();
				yearlyForecast.save();
			} catch (AdempiereException e)
			{
				e.printStackTrace();
				throw new AdempiereException(e.getMessage());
			}
			
			for (Hashtable<Integer, MUNSForecastDate> mapMonthlyForecast : m_ListMonthlyForecast.values())
			{
				MUNSForecastDate monthlyForecast = mapMonthlyForecast.get(yearlyForecast.getM_Product_ID());
				if (null == monthlyForecast)
				continue;
				
				monthlyForecast.setYearly_ID(yearlyForecast.getUNS_Forecast_Date_ID());
				try {
					monthlyForecast.rescaleAllNumbers();
					monthlyForecast.save();
				} catch (AdempiereException e)
				{
					e.printStackTrace();
					throw new AdempiereException(e.getMessage());
				}
			}
			for (Hashtable<Integer, MUNSForecastDate> mapWeeklyForecast : m_ListWeeklyForecast.values())
			{
				for (MUNSForecastDate weeklyForecast : mapWeeklyForecast.values())
				{
					if (weeklyForecast.getM_Product_ID() != yearlyForecast.getM_Product_ID())
						continue;
					
					weeklyForecast.setYearly_ID(yearlyForecast.getUNS_Forecast_Date_ID());
					try {
						weeklyForecast.rescaleAllNumbers();
						weeklyForecast.save();
					} catch (AdempiereException e)
					{
						e.printStackTrace();
						throw new AdempiereException(e.getMessage());
					}
				}
			}
		}
		Calendar today = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		for (MUNSForecastDate dailyForecastDate : m_ListDailyForecast.values())
		{
			int productID = dailyForecastDate.getM_Product_ID();
			today.setTimeInMillis(dailyForecastDate.getForcastedDate().getTime());
			int month = today.get(Calendar.MONTH);
			int weekNo = today.get(Calendar.WEEK_OF_YEAR);
			//int yearlyID = m_ListYearlyForecast.get(productID).get_ID();
			if (today.get(Calendar.DAY_OF_YEAR) >= 350 && weekNo == 1)
				weekNo = 53;
			int monthlyID = m_ListMonthlyForecast.get(month).get(productID).get_ID();
			int weeklyID = m_ListWeeklyForecast.get(weekNo).get(productID).get_ID();
			try {
				dailyForecastDate.setWeekly_ID(weeklyID);
				dailyForecastDate.setMonthly_ID(monthlyID);
				dailyForecastDate.rescaleAllNumbers();
				dailyForecastDate.save();
			} catch (AdempiereException e)
			{
				e.printStackTrace();
				throw new AdempiereException(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 
	 * @param dailyForecast
	 * @return
	 */
	private void generateMonthlyRawInputForecast(MUNSForecastDate dailyForecast)
	{
		Calendar calendar = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		calendar.setTimeInMillis(dailyForecast.getForcastedDate().getTime());
		MUNSForecastDate monthlyForecast = null;
		Hashtable<Integer, MUNSForecastDate> mapMonthlyForecast = m_ListMonthlyForecast.get(
				calendar.get(Calendar.MONTH));
		if (null == mapMonthlyForecast)
		{
			mapMonthlyForecast = new Hashtable<Integer, MUNSForecastDate>();
			m_ListMonthlyForecast.put(calendar.get(Calendar.MONTH), mapMonthlyForecast);
		}
		
		monthlyForecast = mapMonthlyForecast.get(dailyForecast.getM_Product_ID());
		
		if (null == monthlyForecast)
		{
			monthlyForecast = new MUNSForecastDate(getCtx(), 0, get_TrxName());
			monthlyForecast.copyValues(dailyForecast);
			@SuppressWarnings("deprecation")
			MPeriod period = MPeriod.get(getCtx(), dailyForecast.getForcastedDate(), getAD_Org_ID());
			monthlyForecast.setC_Period_ID(period.getC_Period_ID());
			monthlyForecast.setStartDate(period.getStartDate());
			monthlyForecast.setEndDate(period.getEndDate());
			mapMonthlyForecast.put(monthlyForecast.getM_Product_ID(), monthlyForecast);
		}
		else
		{
			monthlyForecast.calculate(dailyForecast);
		}
	}
	
	
	/**
	 * 
	 * @param dailyForecast
	 */
	private void generateWeeklyForecast(MUNSForecastDate dailyForecast)
	{
		Calendar forecastCalendar = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		forecastCalendar.setTimeInMillis(dailyForecast.getForcastedDate().getTime());
		int weekNo = UNSTimeUtil.getProductionWeekNo(dailyForecast.getForcastedDate());
		/*if (forecastCalendar.get(Calendar.DAY_OF_YEAR) > 350 && 
				forecastCalendar.get(Calendar.WEEK_OF_YEAR) == 1)
		{
			weekNo = 53;
		}*/

		Hashtable<Integer, MUNSForecastDate> mapWeeklyForecast = m_ListWeeklyForecast.get(weekNo);
		if (null == mapWeeklyForecast)
		{
			mapWeeklyForecast = new Hashtable<Integer, MUNSForecastDate>();
			m_ListWeeklyForecast.put(weekNo, mapWeeklyForecast);
		}
		MUNSForecastDate weeklyForecast = mapWeeklyForecast.get(dailyForecast.getM_Product_ID());
		if (null == weeklyForecast)
		{
			weeklyForecast = new MUNSForecastDate(getCtx(), 0, get_TrxName());
			weeklyForecast.copyValues(dailyForecast);
			mapWeeklyForecast.put(dailyForecast.getM_Product_ID(), weeklyForecast);
			/*
			Calendar weeklyCalendar = (Calendar) forecastCalendar.clone();
			int thisWeekOfMonth = weeklyCalendar.get(Calendar.WEEK_OF_MONTH);
			
			int C_Period_ID = MPeriod.getC_Period_ID(
					getCtx(), dailyForecast.getForcastedDate(), getAD_Org_ID());
			
			if (C_Period_ID == getPeriodStart_ID()
							&& thisWeekOfMonth == 1)
				weeklyCalendar.setTimeInMillis(m_PeriodStart.getStartDate().getTime());
			
			else
				weeklyCalendar.set(Calendar.DAY_OF_WEEK, 1);
			*/
			Timestamp startOfWeek = 
					UNSTimeUtil.getProductionWeekStartDate(((MYear)getC_Year()).getYearAsInt(), weekNo);
			/*
			if (C_Period_ID == getPeriodEnd_ID()
							&& thisWeekOfMonth == forecastCalendar
										.getActualMaximum(Calendar.WEEK_OF_MONTH))
				weeklyCalendar.setTimeInMillis(m_PeriodEnd.getEndDate().getTime());	
			
			else
				weeklyCalendar.set(Calendar.DAY_OF_WEEK, 7);
			*/
			Timestamp endOfWeek = 
					UNSTimeUtil.getProductionWeekEndDate(((MYear)getC_Year()).getYearAsInt(), weekNo);
			weeklyForecast.setWeekNo(weekNo);
			weeklyForecast.setStartDate(startOfWeek);
			weeklyForecast.setEndDate(endOfWeek);
			weeklyForecast.setForcastedDate(null);		
		}
		else
		{
			weeklyForecast.calculate(dailyForecast);
		}		
	}
	
	/**
	 * 
	 * @param dailyForecast
	 */
	private void generateYearlyForecast(MUNSForecastDate dailyForecast)
	{
		MUNSForecastDate yearlyForecast = m_ListYearlyForecast.get(dailyForecast.getM_Product_ID());
		if (null == yearlyForecast)
		{
			yearlyForecast = new MUNSForecastDate(getCtx(), 0, get_TrxName());
			yearlyForecast.copyValues(dailyForecast);
			yearlyForecast.setUNS_Forecast_Header_ID(getUNS_Forecast_Header_ID());
			m_ListYearlyForecast.put(dailyForecast.getM_Product_ID(), yearlyForecast);
		}
		else
		{
			yearlyForecast.calculate(dailyForecast);
		}
	}
	
	/**
	 * 
	 */
	private void generatePeriodicForecastFromDaily()
	{
		for (MUNSForecastDate dailyForecast : m_ListDailyForecast.values())
		{
			generateMonthlyRawInputForecast(dailyForecast);
			generateWeeklyForecast(dailyForecast);
			generateYearlyForecast(dailyForecast);
		}
		
		Enumeration<Integer> months = m_ListMonthlyForecast.keys();
		while (months.hasMoreElements())
		{
			Integer month = months.nextElement();
			Hashtable<Integer, MUNSForecastDate> monthlyForecast = m_ListMonthlyForecast.get(month);
			if (monthlyForecast == null)
				continue;
			for (MUNSProductAllocation productAllocation : getOutputsAllocation(false))
			{
				MUNSForecastDate currentMonthFD = monthlyForecast.get(productAllocation.getM_Product_ID());
				if (null == currentMonthFD)
					continue;
				productAllocation.setIsPrimary(currentMonthFD.isPrimary());
				productAllocation.setQtyRMPForecast(month, currentMonthFD.getQtyUom());
				productAllocation.setQtyRMPForecastMT(month, currentMonthFD.getQtyMT());
			}			
		}
		//save product Allocation;
		for (MUNSProductAllocation productAllocation : getOutputsAllocation(false))
		{
			productAllocation.rescaleAllNumbers();
			//productAllocation.save();
		}
		DBUtils.executeBatchUpdateOfPO(getOutputsAllocation(false), get_TrxName());
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	private MUNSForecastInput createForecastInput(int M_Product_ID)
	{
		MUNSForecastInput forecastInput = new MUNSForecastInput(getCtx(), 0, get_TrxName());
		forecastInput.setM_Product_ID(M_Product_ID);
		return forecastInput;
	}
	
	private Hashtable<Integer, Integer> m_hasAllocation = new Hashtable<Integer, Integer>();
	private boolean isHasAllocation(int outputID)
	{
		Integer output = m_hasAllocation.get(outputID);
		if(null != output)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param rsc
	 * @param inputList
	 */
	private void generateForecast(MUNSResource rsc, Hashtable<Integer, ArrayList<ResourceInputRequirement>> inputList)
	{
		log.warning("Generating Forecast values of Resource " + rsc.getName() + "....");
		ArrayList<MUNSForecastInput> newInputList = new ArrayList<MUNSForecastInput>();
		//TODO possible improvement dgn menyaring outputLines yang hanya merupakan output dari inputlist dg
		//1 kali koneksi ke database.
		for (MUNSResourceInOut output : rsc.getOutputLines())
		{
			BigDecimal price = Env.ZERO;
			for (Integer inputID : inputList.keySet())
			{
				if (!rsc.isRequiredInput(inputID))
					continue;
				BigDecimal bomQty = output.getBOMQty(inputID, BigDecimal.ONE, get_TrxName());
				if (bomQty.compareTo(BigDecimal.ZERO) <= 0)
					continue;
				
				boolean isPrimaryOutput = output.isPrimaryOutput();
				// pada posisi ini rscInReq tidak akan null karena inputID sudah pasti merupakan bomnya output.
				ResourceInputRequirement  rscInReq = ResourceInputRequirement.getResource(
						inputList, rsc.get_ID(), inputID);
				if (!rscInReq.hasInputAllocation())
					return;
				
				//Calendar periodStart = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
				//Calendar PeriodEnd = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
				//periodStart.setTimeInMillis(m_PeriodStart.getStartDate().getTime());
				//PeriodEnd.setTimeInMillis(m_PeriodEnd.getEndDate().getTime());
				int monthOfPeriodStart = m_PeriodStart.getPeriodNo() - 1;//periodStart.get(Calendar.MONTH);
				int monthOfPeriodEnd = m_PeriodEnd.getPeriodNo() - 1;//PeriodEnd.get(Calendar.MONTH);
				
				Calendar calendar = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
				calendar.set(Calendar.YEAR, m_Year.getYearAsInt());
				int totalMaxDayOfPeriodStartToPeriodEnd = 0;
				int currentMonth = monthOfPeriodStart-1;
				double monthlyQtyInputPerHourForRsc = 0.0;
				double totalProductionHoursInMonth = 0.0;
				double totalOutputQtyPerHour = 0.0;
				double maxCapsUOMPerHour = output.getMaxCaps().doubleValue();
				double optimumCapsUOMPerHour = output.getOptimumCaps().doubleValue();
				double maxCapsDay = 0.0;
				double optimumCapsDay = 0.0;
				
				MProduct outputPrd = MProduct.get(getCtx(), output.getM_Product_ID());
				
				double outputWeight = outputPrd.getWeight().doubleValue();
				
				//Ambil rata2 alokasi produksi perjam untuk output saat ini berdasarkan input saat ini.
				//Ini dipakai untuk mengalokasikan total input kesetiap output dari input yang sudah dialokasikan 
				//utk resource (rsc) saat ini. 
				double avgProdAllocation = 0.0;
				//TODO FORECAST
//				if (m_allocationMethod.equals(GenerateForecast.INPUTALLOCATION_AllPlantOutput))
//				{
//					avgProdAllocation = rsc.getAvgProportionOf(
//							m_allocationMethod, outputPrd.get_ID(), inputID);
//				}
//				else if (outputPrd.isGeneralProduct()
//						 && m_allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludePerBuyer))
//				{
//					avgProdAllocation = rsc.getAvgProportionOf(
//							m_allocationMethod, outputPrd.get_ID(), inputID);
//				}
//				else if (!outputPrd.isGeneralProduct()
//						&& m_allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludeGeneral))
//				{
//					avgProdAllocation = rsc.getAvgProportionOf(
//							m_allocationMethod, outputPrd.get_ID(), inputID);
//				}
				/*
				else if (outputPrd.isGeneralProduct())
				{
					if (!outputPrd.isSold() 
						|| (output.isOptional()
							&& m_allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludePerBuyer))
						|| (output.isMulti()))
						avgProdAllocation = rsc.getAvgProportionOf(
								m_allocationMethod, outputPrd.get_ID(), inputID);
				}
				else if (output.isOptional()
						&& m_allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludeGeneral))
				{
					avgProdAllocation = rsc.getAvgProportionOf(
							m_allocationMethod, outputPrd.get_ID(), inputID);
				}
				*/
				MUNSForecastInput forecastInput = null;
				
				if (!rsc.isParentEndNode() && rsc.isOutputSetAsTransitionOutIn(output.getM_Product_ID()))
				{
					forecastInput = createForecastInput(output.getM_Product_ID());
					newInputList.add(forecastInput);
				}
				
				if(isHasAllocation(output.getM_Product_ID()))
					continue;
				
				m_hasAllocation.put(output.getM_Product_ID(), output.getM_Product_ID());
				
				for (int month=monthOfPeriodStart; month<=monthOfPeriodEnd; month++)
				{
					calendar.set(Calendar.MONTH, month);
					calendar.set(Calendar.DATE, 1);
					totalMaxDayOfPeriodStartToPeriodEnd += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}

				calendar.set(Calendar.MONTH, monthOfPeriodStart);
				
				for (int i=1; i<=totalMaxDayOfPeriodStartToPeriodEnd; i++)
				{
					if (currentMonth < calendar.get(Calendar.MONTH))
					{
						currentMonth = calendar.get(Calendar.MONTH);
						double qtyInMonthForRscInMT = 
								rscInReq.getActualInput(currentMonth, getAllocationMethod()) 
								* avgProdAllocation;
						totalProductionHoursInMonth = output.getTotalProductionHoursInMonth(calendar, forecastInput.getAD_Org_ID())
								.doubleValue();
						
						monthlyQtyInputPerHourForRsc = qtyInMonthForRscInMT / totalProductionHoursInMonth;
						I_M_Product inputProduct = MProduct.get(getCtx(), inputID);
						double inputWeight = inputProduct.getWeight().doubleValue();
						
						//totalOutputQtyPerHour = monthlyQtyInputPerHourForRsc / bomQty.doubleValue()
						//		/ inputProduct.getWeight().doubleValue();
						totalOutputQtyPerHour = 
								((monthlyQtyInputPerHourForRsc * 1000)/inputWeight) / bomQty.doubleValue();
						if (null != forecastInput)
						{
							//double qtyMonth = qtyInMonthForRscInMT/bomQty.doubleValue()
							//		/inputProduct.getWeight().doubleValue();
							double qtyMonth = 
									((qtyInMonthForRscInMT*1000)/inputWeight)/bomQty.doubleValue();
							
							forecastInput.setQtyForecastRMPMT(currentMonth, new BigDecimal(qtyMonth * inputWeight / 1000));
						}
					}
					double totalDayProductionHours = output.getDayProductionHours(
							calendar.get(Calendar.DAY_OF_WEEK)).doubleValue();
					double dayOutputQtyMT = totalDayProductionHours * totalOutputQtyPerHour * outputWeight / 1000;
					maxCapsDay = maxCapsUOMPerHour * totalDayProductionHours;
					optimumCapsDay = optimumCapsUOMPerHour  * totalDayProductionHours;
					
					BigDecimal dayOutputQtyUOM = convertMTToUOM(outputPrd, new BigDecimal(dayOutputQtyMT));
					
					MUNSForecastDate dailyForecast = null;
					String dailyKey = null;
				
					dailyKey = generateDailyKey(calendar, output.getM_Product_ID());
					dailyForecast = m_ListDailyForecast.get(dailyKey);;
				
					if (null == dailyForecast)
					{
						dailyForecast = new MUNSForecastDate(getCtx(), 0, get_TrxName());
						dailyForecast.setIsPrimary(isPrimaryOutput);
						dailyForecast.setM_Product_ID(output.getM_Product_ID());
						dailyForecast.setC_UOM_ID(output.getUOMCaps_ID());
						dailyForecast.setAD_Org_ID(getAD_Org_ID());
						Timestamp currentDate = new Timestamp(calendar.getTimeInMillis());
						dailyForecast.setForcastedDate(currentDate);
						dailyForecast.setQtyMT(new BigDecimal(dayOutputQtyMT));
						dailyForecast.setQtyUom(dayOutputQtyUOM);
						dailyForecast.setMaxCaps(new BigDecimal(maxCapsDay));
						dailyForecast.setOptimumCaps(new BigDecimal(optimumCapsDay));
						
						if (price.signum() == 0)
							price = getPrice(output.getM_Product_ID());
						if (price.signum() == 0 && outputPrd.isSold())
							throw new AdempiereException ("Price of Product [" + outputPrd.getName() + 
														  "] undefined yet in the price list." );

						dailyForecast.setExpectedRevenueIDR(convertTo(price, m_Cur_IDR));
						dailyForecast.setExpectedRevenueSGD(convertTo(price, m_Cur_SGD));
						dailyForecast.setExpectedRevenueUSD(convertTo(price, m_Cur_USD));
						
						dailyForecast.setTotalExpectedRevenueIDR(
								dailyForecast.getExpectedRevenueIDR().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedRevenueUSD(
								dailyForecast.getExpectedRevenueUSD().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedRevenueSGD(
								dailyForecast.getExpectedRevenueSGD().multiply(dailyForecast.getQtyUom()));
						
						dailyForecast.setExpectedCostIDR(output.getExpectedProductionCost());
						dailyForecast.setExpectedCostSGD(convertTo(dailyForecast.getExpectedCostIDR(), m_Cur_SGD));
						dailyForecast.setExpectedCostUSD(convertTo(dailyForecast.getExpectedCostIDR(), m_Cur_USD));
						
//						dailyForecast.setMaxCaps(output.getMaxCaps());
//						dailyForecast.setOptimumCaps(output.getOptimumCaps());
						
						dailyForecast.setTotalExpectedCostIDR(
								dailyForecast.getExpectedCostIDR().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedCostSGD(
								dailyForecast.getExpectedCostSGD().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedCostUSD(
								dailyForecast.getExpectedCostUSD().multiply(dailyForecast.getQtyUom()));
						
						m_ListDailyForecast.put(dailyKey, dailyForecast);
					}
					else
					{
						dailyForecast.setQtyMT(dailyForecast.getQtyMT().add(new BigDecimal(dayOutputQtyMT)));
						dailyForecast.setQtyUom(dailyForecast.getQtyUom().add(dayOutputQtyUOM));
						dailyForecast.setMaxCaps(dailyForecast.getMaxCaps().add(new BigDecimal(maxCapsDay)));
						dailyForecast.setOptimumCaps(dailyForecast.getOptimumCaps().add(
								new BigDecimal(optimumCapsDay)));
						
						dailyForecast.setTotalExpectedRevenueIDR(
								dailyForecast.getExpectedRevenueIDR().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedRevenueSGD(
								dailyForecast.getExpectedRevenueSGD().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedRevenueUSD(
								dailyForecast.getExpectedRevenueUSD().multiply(dailyForecast.getQtyUom()));
						
						dailyForecast.setTotalExpectedCostIDR(
								dailyForecast.getExpectedCostIDR().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedCostSGD(
								dailyForecast.getExpectedCostSGD().multiply(dailyForecast.getQtyUom()));
						dailyForecast.setTotalExpectedCostUSD(
								dailyForecast.getExpectedCostUSD().multiply(dailyForecast.getQtyUom()));
						
					}
					calendar.add(Calendar.DATE, 1);
				}
			}
		}
		log.warning("Generating Forecast values of Resource " + rsc.getName() + " DONE.");
		if (newInputList.isEmpty())
			return;
		
		MUNSForecastInput[] forecastInputList = new MUNSForecastInput[newInputList.size()];
		newInputList.toArray(forecastInputList);
		
		for (MUNSResourceTransition transition : rsc.getTransitionLines())
		{
			for (MUNSResourceInOut outputTransition : transition.getLines())
			{
				//TODO ada kemungkinan utk dapat meningkatkan kecepatan proses disini, dg menyeleksi forecastInputList
				//yang sesuai dengan outputTransition saja (tapi masih meragukan apakah akan memicu bugs atau tidak).
				//Please consider this possible improvement.
				Hashtable<Integer, ArrayList<ResourceInputRequirement>> mapOfRIR =
						createResourceInputRequirementMap(rsc.getNextResourcesOf(outputTransition.getM_Product_ID()), 
														  forecastInputList);
				
				generateForecast((MUNSResource)transition.getNextResource(), mapOfRIR);
			}
		}
	}

	/**
	 * 
	 * @param cal
	 * @param M_Product_ID
	 * @return
	 */
	private String generateDailyKey(Calendar cal, int M_Product_ID)
	{
		String key = cal.get(Calendar.DATE) + "-" + cal.get(Calendar.MONTH) +  
				"-" + cal.get(Calendar.YEAR) + "_" + M_Product_ID;
		return key;
	}

	/**
	 * 
	 * @param M_Product_ID
	 * @param M_PriceList_ID
	 * @return
	 */
	private BigDecimal getPrice(int M_Product_ID)
	{
		BigDecimal price = BigDecimal.ZERO;
		Timestamp date = Env.getContextAsDate(getCtx(), "#Date");
		int M_PriceList_Version_ID =0;
		if ( M_PriceList_Version_ID == 0 && getM_PriceList_ID() > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			M_PriceList_Version_ID = DB.getSQLValueEx(
					null, sql, getM_PriceList_ID(), date);
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(getCtx(), "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}
		MProductPrice pp = MProductPrice.get(getCtx(), M_PriceList_Version_ID, M_Product_ID, get_TrxName());
		if (null == pp)
		{
			//MProduct product = MProduct.get(getCtx(), M_Product_ID);
			//throw new AdempiereUserError("Product [" + product.getName() + "] is not in Price List");
			return Env.ZERO;
		}
		price = pp.getPriceList();
		if (null == price)
			price = BigDecimal.ZERO;
		return price;
	}

	/**
	 * 
	 * @param product
	 * @param qtyMT
	 * @return
	 */
	private BigDecimal convertMTToUOM(MProduct product, BigDecimal qtyMT)
	{
		double qtyUOM = 0.0;
		BigDecimal weight = product.getWeight();
		if (null == weight || weight.compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereUserError(
					"Please define weight for " + product.getValue() + product.getName());
		}
		qtyUOM = qtyMT.doubleValue() * 1000 / weight.doubleValue();
//		qtyUOM = qtyUOM / weight.doubleValue();
		return new BigDecimal(qtyUOM);
	}
	
	public int getYear()
	{
		return ((MYear)getC_Year()).getYearAsInt();
	}
	
	/**
	 * Reset the targeted and marketing forecast quantity. NOTE: All are not saved yet.
	 */
	void resetTargetedForecast()
	{
		for (MUNSForecastDate yearly : getLinesYearly())
		{
			yearly.setQtyMForecast(BigDecimal.ZERO);
			yearly.setQtyMForecastMT(BigDecimal.ZERO);
			yearly.setDecidedQty(BigDecimal.ZERO);
			yearly.setDecidedQtyMT(BigDecimal.ZERO);
			for (MUNSForecastDate monthly : yearly.getLinesMonthOfYear(false))
			{
				monthly.setQtyMForecast(BigDecimal.ZERO);
				monthly.setQtyMForecastMT(BigDecimal.ZERO);
				monthly.setDecidedQty(BigDecimal.ZERO);
				monthly.setDecidedQtyMT(BigDecimal.ZERO);
				for (MUNSForecastDate daily : monthly.getLinesDayOfMonth())
				{
					daily.setQtyMForecast(BigDecimal.ZERO);
					daily.setQtyMForecastMT(BigDecimal.ZERO);
					daily.setDecidedQty(BigDecimal.ZERO);
					daily.setDecidedQtyMT(BigDecimal.ZERO);
				}
			}
			for (MUNSForecastDate weekly : yearly.getLinesWeekOfYear())
			{
				weekly.setQtyMForecast(BigDecimal.ZERO);
				weekly.setQtyMForecastMT(BigDecimal.ZERO);
				weekly.setDecidedQty(BigDecimal.ZERO);
				weekly.setDecidedQtyMT(BigDecimal.ZERO);
			}
		}
	}
}


class ResourceInputRequirement implements Comparable<ResourceInputRequirement>
{
	int m_InputID;
	int m_ResourceID;
	MUNSResource m_Resource;
	double m_MaxRequirementInMTPerJam;
	double m_ProportionalRequirement = 0.0;
	MUNSForecastInput m_ForecastInput;
	double[] m_MonthlyActualInput = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	
	
	ResourceInputRequirement(MUNSResource resource, MUNSForecastInput forecastInput) {
		m_InputID = forecastInput.getM_Product_ID();
		m_Resource = resource;
		m_ResourceID = resource.get_ID();
		m_ForecastInput = forecastInput;
	}

	ResourceInputRequirement(MUNSResource resource, MUNSForecastInput forecastInput,
									double maxRequirement, double proportionalReqirement) {
		this(resource,forecastInput);
		m_MaxRequirementInMTPerJam = maxRequirement;
		m_ProportionalRequirement = proportionalReqirement;
	}
	
	String getKey()
	{
		return m_InputID+"_"+m_ResourceID;
	}
	
	/**
	 * 
	 * @param listRscInputReq
	 */
	static void setInputProportion(MUNSForecastInput input, ArrayList<ResourceInputRequirement> listRscInputReq, String allocationMethod)
	{
		if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_Proportion))
		{
			setInputProportion(listRscInputReq);
			return;
		}

		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		
		for (int i=0; i < 12; i++)
		{
			cal.set(Calendar.MONTH, i);
			double maxOfCurrentMonthInput = input.getQtyForecastRMPMT(i).doubleValue();
			if (maxOfCurrentMonthInput == 0)
				continue;
			for (ResourceInputRequirement rscInputReq : listRscInputReq)
			{
				Hashtable<MUNSResourceInOut, double[]> allOutputPH = 
						rscInputReq.m_Resource.getAllOutputProductionHours();
				double totalAllOuputPHInMo = MUNSResourceInOut.getTotalAllOutputProductionHoursInMonth(
						cal, allOutputPH, input.get_TrxName()); 
				double maxRequirementByRscInMonth = totalAllOuputPHInMo * rscInputReq.m_MaxRequirementInMTPerJam;
				if (maxOfCurrentMonthInput >= maxRequirementByRscInMonth)
					rscInputReq.m_MonthlyActualInput[i] = maxRequirementByRscInMonth;
				else
					rscInputReq.m_MonthlyActualInput[i] = maxOfCurrentMonthInput;
				
				maxOfCurrentMonthInput -= rscInputReq.m_MonthlyActualInput[i];
			}
		}
	}

	/**
	 * 
	 * @param listRscInputReq
	 */
	static void setInputProportion(ArrayList<ResourceInputRequirement> listRscInputReq)
	{
		double totalMaxReq = 0.0;
		for (ResourceInputRequirement rscInputReq : listRscInputReq)
		{
			totalMaxReq += rscInputReq.m_MaxRequirementInMTPerJam;
		}
		for (ResourceInputRequirement rscInputReq : listRscInputReq)
		{
			rscInputReq.m_ProportionalRequirement = rscInputReq.m_MaxRequirementInMTPerJam/totalMaxReq;
		}
	}
	
	/**
	 * Get the resource input requirement id from the list.
	 * @param listRscInputReq
	 * @param rscID
	 * @return
	 */
	static ResourceInputRequirement getResource(Hashtable<Integer, ArrayList<ResourceInputRequirement>> inputList, 
												int rscID, int inputID)
	{
		ResourceInputRequirement rscInReq = null;
		ArrayList<ResourceInputRequirement> listRscInputReq = inputList.get(inputID);
		for (ResourceInputRequirement rscInputReq : listRscInputReq)
		{
			if (rscID == rscInputReq.m_ResourceID)
				return rscInputReq;
		}
		return rscInReq;
	}
	

	/**
	 * 
	 * @param inputID
	 * @param resourceID
	 * @return
	 */
	static String formatKey(int inputID, int resourceID)
	{
		return inputID+"_"+resourceID;
	}

	@Override
	public int compareTo(ResourceInputRequirement o) {
		if (m_MaxRequirementInMTPerJam < o.m_MaxRequirementInMTPerJam)
			return -1;
		else if (m_MaxRequirementInMTPerJam > o.m_MaxRequirementInMTPerJam)
			return 1;
		else 
			return 0;
	}
	
	/**
	 * Get actual input in MT for the given month from this forecastInput.
	 * @param month
	 * @return actual input in MT for the given month from this forecastInput.
	 */
	double getActualInput(int month, String allocationMethod)
	{
		if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_Proportion))
		{
			return m_ProportionalRequirement * m_ForecastInput.getQtyInputMT(month).doubleValue();
		}		
		return m_MonthlyActualInput[month];
	}
	
	boolean isNullMonthlyActualInput()
	{
		for (double monthlyInput : m_MonthlyActualInput)
		{
			if(monthlyInput > 0.0)
				return false;
		}
		return true;
	}
	
	/**
	 * Memeriksa apakah resource ini memiliki alokasi input atau tidak.
	 * Method ini utk memeriksa akibat dari method setInputProportion thd method ini.
	 * @return
	 */
	boolean hasInputAllocation()
	{
		if (m_ProportionalRequirement == 0.0 && isNullMonthlyActualInput())
			return false;
		
		return true;
	}	
}