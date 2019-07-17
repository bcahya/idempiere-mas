/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Period;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSOtherProductConf;
import com.uns.util.UNSTimeUtil;

import com.uns.model.MUNSForecastDate;
import com.uns.model.MUNSForecastHeader;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductResource;
import com.uns.model.MUNSMPSProductRscWeekly;
import com.uns.model.MUNSMPSProductWeekly;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;

/**
 * @author menjangan
 *
 */
public class MPSGenerator extends SvrProcess {

	private int m_Record_ID = 0;
	private int m_SessionProduct = 0;
	private double m_Weight = 0.0;
	private double m_OnHand = 0.0;
	private BigDecimal m_OnHandUOM = Env.ZERO;
	private MUNSMPSHeader m_MPSHeader = null;
	private MUNSResource m_Resource = null;
	private MUNSForecastHeader m_ForecastHeader = null;
	private Hashtable<String, MUNSMPSProductResource> m_ListProductResource = 
			new Hashtable<String, MUNSMPSProductResource>();
	private Hashtable<String, MUNSMPSProductRscWeekly> m_ListProductResourceWeekly =
			new Hashtable<String, MUNSMPSProductRscWeekly>();
	/*
	private Hashtable<String, MUNSMPSProductRscWeekly>  m_ListProductResourceDaily = 
			new Hashtable<String, MUNSMPSProductRscWeekly>();
	*/
	private Hashtable<Integer, MUNSMPSProduct> m_ListMPSProduct =
			new Hashtable<Integer, MUNSMPSProduct>();
	private Hashtable<String, MUNSMPSProductWeekly> m_ListMPSProductWeekly =
			new Hashtable<String, MUNSMPSProductWeekly>();
	/*
	private int m_ThisYear = 0;
	private Hashtable<String, MUNSMPSProductWeekly> m_ListMPSProductDaily = 
			new Hashtable<String, MUNSMPSProductWeekly>();
	private Hashtable<String, Double> m_UnAllocatedQty = new Hashtable<String, Double>();
	private Hashtable<String, Double> m_UnAllocatedQtyMT = new Hashtable<String, Double>();
	private Hashtable<Integer, MUNSForecastDate> m_MapOfWeeklyForecast = 
			new Hashtable<Integer, MUNSForecastDate>();
	*/
	private Hashtable<Integer, Double> m_ExpectedOnHans = new Hashtable<Integer, Double>();
	private MUNSMPSHeader m_prevMPSHeader = null;
	private boolean m_isDeleteOld = false;
	private int m_startWeekNo = 1;
	private int m_endWeekNo = 53;

	private ArrayList<ResourceProportion> m_rscProportion = new ArrayList<ResourceProportion>();
	
	
	/**
	 * 
	 */
	public MPSGenerator() 
	{
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String paramName = para[i].getParameterName();
			if (paramName.equals("DeleteOld"))
				m_isDeleteOld = para[i].getParameterAsBoolean();
		}
		
		m_Record_ID = getRecord_ID();
		
		if (m_Record_ID <= 0)
			throw new AdempiereException("NO RECORD ID");
		
		m_MPSHeader = new MUNSMPSHeader(getCtx(), m_Record_ID, get_TrxName());
		m_ForecastHeader = new MUNSForecastHeader(
				getCtx(), m_MPSHeader.getUNS_Forecast_Header_ID(), get_TrxName());
		m_ExpectedOnHans = m_MPSHeader.getExpectedOnHands();
		//m_MPSHeader.deleteLines(true);
		m_Resource = (MUNSResource) m_ForecastHeader.getUNS_Resource();

		m_startWeekNo = UNSTimeUtil.getProductionWeekNo(m_MPSHeader.getPeriodStart().getStartDate());

		m_endWeekNo = UNSTimeUtil.getProductionWeekNo(m_MPSHeader.getPeriodEnd().getEndDate());
		
		//if (1==1)
		//	throw new AdempiereException();

		if (m_MPSHeader.getPrevMPS_ID() > 0)
			m_prevMPSHeader = (MUNSMPSHeader) m_MPSHeader.getPrevMPS();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		if(m_MPSHeader.getDocStatus().equals(MUNSMPSHeader.DOCSTATUS_Completed))
			throw new AdempiereUserError("Can't generate or regenerate completed MPS.");
		if (m_isDeleteOld)
		{
			String sqlDel = "DELETE FROM UNS_MPSProductRsc_Weekly " +
					" WHERE UNS_MPSProduct_Resource_ID IN (SELECT prdRsc.UNS_MPSProduct_Resource_ID " +
					"	FROM UNS_MPSProduct_Resource prdRsc WHERE prdRsc.UNS_MPSProduct_ID IN " +
					"		(SELECT prd.UNS_MPSProduct_ID FROM UNS_MPSProduct prd WHERE prd.UNS_MPSHeader_ID=" +
					m_MPSHeader.get_ID() + "))";
			int count = DB.executeUpdate(sqlDel, get_TrxName());
			if (count < 0)
				throw new AdempiereException ("Error while clearing previous MPS Resource Weekly data.");

			sqlDel = "DELETE FROM UNS_MPSProduct_Resource WHERE UNS_MPSProduct_ID IN " +
					"		(SELECT prd.UNS_MPSProduct_ID FROM UNS_MPSProduct prd WHERE prd.UNS_MPSHeader_ID=" +
					m_MPSHeader.get_ID() + ")";
			count = DB.executeUpdate(sqlDel, get_TrxName());			
			if (count < 0)
				throw new AdempiereException ("Error while clearing previous MPS Product Resource data.");
			
			sqlDel = "DELETE FROM UNS_MPSProduct_Weekly WHERE UNS_MPSProduct_ID IN " +
					"		(SELECT prd.UNS_MPSProduct_ID FROM UNS_MPSProduct prd WHERE prd.UNS_MPSHeader_ID=" +
					m_MPSHeader.get_ID() + ")";
			count = DB.executeUpdate(sqlDel, get_TrxName());
			if (count < 0)
				throw new AdempiereException ("Error while clearing previous MPS Product Weekly data.");

			sqlDel = "DELETE FROM UNS_MPSProduct WHERE UNS_MPSHeader_ID=" + m_MPSHeader.get_ID();
			count = DB.executeUpdate(sqlDel, get_TrxName());
			if (count < 0)
				throw new AdempiereException ("Error while clearing previous MPS Product data.");
		}
		generateIt();
		return null;
	}
	
	
	/**
	 * generate MPS detail.
	 */
	private void generateIt()
	{	
		generateMPSProduct();
		initProportion();
		generateMPSProductResource();
		saveMPS();
	}

	/**
	 * 
	 * @param forecastHeader
	 */
	private void generateMPSProduct()
	{
		
		log.warning("Generating MPS product data ...");
		for (MUNSForecastDate yearlyForecast : m_ForecastHeader.getLinesYearly())
		{
			MUNSMPSProduct mpsProduct = getMPSProduct(yearlyForecast.getM_Product_ID(), yearlyForecast);
			
			log.warning("Generating MPS for product " + mpsProduct.getM_Product());
			
			//mpsProduct.calculate(yearlyForecast);
			//generateMPSProductDaily(yearlyForecast, mpsProduct);
	
			//log.warning("Generating Weekly details ...");
			generateMPSProductWeekly(yearlyForecast, mpsProduct);
			//log.warning("Generating Weekly details DONE");
		}
		log.warning("Generating MPS product data DONE.");
	}

	/**
	 * 
	 * @param M_Product_ID
	 * @param yearlyForecast
	 * @return
	 */
	private MUNSMPSProduct getMPSProduct(int M_Product_ID, MUNSForecastDate yearlyForecast)
	{
		MUNSMPSProduct mpsProduct = m_ListMPSProduct.get(M_Product_ID);
		if (null == mpsProduct)
		{
			mpsProduct = new MUNSMPSProduct(getCtx(), 0, get_TrxName());
			mpsProduct.setUNS_MPSHeader_ID(m_MPSHeader.getUNS_MPSHeader_ID());
			mpsProduct.setAD_Org_ID(m_MPSHeader.getAD_Org_ID());
			mpsProduct.copyValues(yearlyForecast);
			mpsProduct.setIsGenerate(true);

			double projectedOnHand = getOnHandMTOfProduct(yearlyForecast.getM_Product_ID());
			projectedOnHand /= getWeightOf(mpsProduct.getM_Product_ID());
			projectedOnHand *=  1000;
			mpsProduct.setInitialProjectedStock_OnHand(new BigDecimal(projectedOnHand));

			MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
					getCtx(), yearlyForecast.getM_Product_ID(), get_TrxName());
			
			mpsProduct.setSafetyStock((OtherProductConf != null)? OtherProductConf.getSafetyStock() : Env.ZERO);
			mpsProduct.setIncubationDays((OtherProductConf != null)? OtherProductConf.getIncubationDays() : 0);
			m_ListMPSProduct.put(M_Product_ID, mpsProduct);
		}
		
		return mpsProduct;
	}
	
		
	/**
	 * 
	 * @param yearlyForecast
	 * @param mpsProduct
	 */
	private void generateMPSProductWeekly( MUNSForecastDate yearlyForecast, MUNSMPSProduct mpsProduct)
	{
		BigDecimal weight = yearlyForecast.getM_Product().getWeight();
		//double onHandMT = getOnHandMTOfProduct(mpsProduct.getM_Product_ID());
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), yearlyForecast.getM_Product_ID(), get_TrxName());
		/*
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " +yearlyForecast.getM_Product_ID());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + yearlyForecast.getM_Product_ID());
		}
		*/
		BigDecimal safetyStock = (OtherProductConf != null)? OtherProductConf.getSafetyStock() : Env.ZERO;
		
		MUNSMPSProductWeekly prevWeeklMPS = null;
		BigDecimal prevPAB = Env.ZERO;
		BigDecimal prevAccATP = Env.ZERO;
		if(null != m_prevMPSHeader)
		{
			I_C_Period period = m_prevMPSHeader.getPeriodEnd();
			Calendar calPrevWeekly = Calendar.getInstance();
			calPrevWeekly.setTimeInMillis(period.getEndDate().getTime());
			int prevWeekNo = calPrevWeekly.get(Calendar.WEEK_OF_YEAR);
			if(prevWeekNo == 1 && calPrevWeekly.get(Calendar.MONTH) == 11)//calPrevWeekly.get(Calendar.DAY_OF_YEAR) > 150)
				prevWeekNo = 53;
			
			prevWeeklMPS = 
					m_prevMPSHeader.getMPSProductPeriodic(
							mpsProduct.getM_Product_ID(),
							prevWeekNo,
							null);
			prevPAB = prevWeeklMPS.getProjectedActualBalance();
			prevAccATP = prevWeeklMPS.getAcc_ATP();
		}
		else {
			prevPAB = getOnHandOf(yearlyForecast.getM_Product_ID());
		}
	
		MUNSForecastDate[] weeklyForecastList = yearlyForecast.getLinesWeekOfYear();
		for (int i=0; i < weeklyForecastList.length; i++)
		{
			MUNSForecastDate weeklyForecast = weeklyForecastList[i];
			BigDecimal atp = Env.ZERO;
			MUNSMPSProductWeekly mpsProductWeekly = new MUNSMPSProductWeekly(getCtx(), 0, get_TrxName());
	
			mpsProductWeekly.setIncubationDays(mpsProduct.getIncubationDays());
			mpsProductWeekly.setSafetyStock(mpsProduct.getSafetyStock());
			mpsProductWeekly.setQtyUom(weeklyForecast.getDecidedQty());
			mpsProductWeekly.setQtyMT(weeklyForecast.getDecidedQtyMT());
			mpsProductWeekly.setActualToOrderMT(BigDecimal.ZERO); //Nanti diisikan dari PO
			mpsProductWeekly.setActualToOrderUOM(BigDecimal.ZERO);
			mpsProductWeekly.setStock(BigDecimal.ZERO); //Nanti Diisi dari PO
			mpsProductWeekly.setQtyDelivered(BigDecimal.ZERO);
			mpsProductWeekly.setWeekNo(weeklyForecast.getWeekNo());
			mpsProductWeekly.setStartDate(weeklyForecast.getStartDate());
			mpsProductWeekly.setEndDate(weeklyForecast.getEndDate());
	
			BigDecimal masterSchedules = mpsProductWeekly.getQtyUom().add(safetyStock).subtract(prevPAB);

			BigDecimal PAB = prevPAB.add(masterSchedules).subtract(mpsProductWeekly.getQtyUom());
			prevPAB = PAB;
			if (i < weeklyForecastList.length - 1)
				atp = prevPAB.subtract(weeklyForecastList[i+1].getDecidedQty());
				//atp = prevPAB.subtract(weeklyForecastList[i+1].getDecidedQty());
			prevAccATP = prevAccATP.add(atp);
	
			mpsProductWeekly.setActualToStockUOM(masterSchedules);			
			mpsProductWeekly.setActualToStockMT(
					masterSchedules.multiply(weight).multiply(BigDecimal.valueOf(0.001)));
			mpsProductWeekly.setProjectedActualBalance(PAB);
			mpsProductWeekly.setAD_Org_ID(mpsProduct.getAD_Org_ID());
			mpsProductWeekly.setATP(atp);
			mpsProductWeekly.setAcc_ATP(prevAccATP);
			
			// It is now used to put value of Master Scheduled based on the forecasted qty and initial on hand.
			String key = generateKeyMPSProduct(
					0, weeklyForecast.getWeekNo(), 0, weeklyForecast.getM_Product_ID());
			m_ListMPSProductWeekly.put(key, mpsProductWeekly);
		}
	}

	/**
	 * 
	 */
	private void generateMPSProductResource()
	{
		log.warning("Generating MPS product data per resource ...");
		//if (true)
		//	throw new AdempiereException();
		Map<String, BigDecimal> mapOfAllocation = new Hashtable<String, BigDecimal>();
		
		for (ResourceProportion rscProportion : m_rscProportion)
		{
			for (MUNSForecastDate yearlyForecast : m_ForecastHeader.getLinesYearly())
			{
				if (yearlyForecast.getM_Product_ID() != rscProportion.getM_product_ID())
					continue;
				BigDecimal weight = yearlyForecast.getM_Product().getWeight();

				MUNSResource rsc = new MUNSResource(getCtx(), rscProportion.getResource_ID(), get_TrxName());
				MUNSResourceInOut output = rsc.getResourceOutput(yearlyForecast.getM_Product_ID());
				
				if (null == output)
					continue;
				
				MUNSMPSProductResource yearlyMPSProductRsc = new MUNSMPSProductResource(
						getCtx(), 0, get_TrxName());
				yearlyMPSProductRsc.setAD_Org_ID(m_MPSHeader.getAD_Org_ID());
				yearlyMPSProductRsc.setUNS_Resource_ID(rscProportion.getResource_ID());
				
				BigDecimal totalRscSchedule = Env.ZERO;
				BigDecimal totalRscScheduleMT = Env.ZERO;
				
				for (MUNSForecastDate weeklyForecast : yearlyForecast.getLinesWeekOfYear())
				{
					String key = generateKeyMPSProduct(
							0, weeklyForecast.getWeekNo(), 0, weeklyForecast.getM_Product_ID());
					
					MUNSMPSProductWeekly weeklyMPSProduct = m_ListMPSProductWeekly.get(key);
					
					BigDecimal totalUnallocatedSchedule = mapOfAllocation.get(key);
					if (null == totalUnallocatedSchedule) {
						totalUnallocatedSchedule = weeklyMPSProduct.getActualToStockUOM();
						mapOfAllocation.put(key, totalUnallocatedSchedule);
					}
					else if (totalUnallocatedSchedule.signum() <= 0) {
						continue;
					}
					
					BigDecimal currentAllocationSchedule = totalUnallocatedSchedule.multiply(
							BigDecimal.valueOf(rscProportion.getProportion()));
					
					// Reset the unallocated schedule
					mapOfAllocation.put(key, totalUnallocatedSchedule.subtract(currentAllocationSchedule));
					
					MUNSMPSProductRscWeekly weeklyMPSProductRsc = 
							new MUNSMPSProductRscWeekly(getCtx(), 0, get_TrxName());					

					weeklyMPSProductRsc.setStartDate(weeklyMPSProduct.getStartDate());
					weeklyMPSProductRsc.setEndDate(weeklyMPSProduct.getEndDate());
					weeklyMPSProductRsc.setWeekNo(weeklyForecast.getWeekNo());
					weeklyMPSProductRsc.setAD_Org_ID(m_MPSHeader.getAD_Org_ID());
					weeklyMPSProductRsc.setActualToStockUOM(currentAllocationSchedule);
					weeklyMPSProductRsc.setActualToStockMT(
							currentAllocationSchedule.multiply(weight).divide(BigDecimal.valueOf(0.001)));
					
					String weeklyKey = generateKeyMPSProduct(
							0, weeklyForecast.getWeekNo(), rscProportion.getResource_ID()
							, weeklyForecast.getM_Product_ID());					
					m_ListProductResourceWeekly.put(weeklyKey, weeklyMPSProductRsc);
					
					totalRscSchedule = totalRscSchedule.add(currentAllocationSchedule);
					totalRscScheduleMT = totalRscScheduleMT.add(weeklyMPSProductRsc.getActualToStockMT());
				}
				
				yearlyMPSProductRsc.setActualToStockUOM(totalRscSchedule);
				yearlyMPSProductRsc.setActualToStockMT(totalRscScheduleMT);

				String yearlyKey = generateKeyMPSProduct(
						0, 0, rscProportion.getResource_ID(), yearlyForecast.getM_Product_ID());
				m_ListProductResource.put(yearlyKey, yearlyMPSProductRsc);
			}
		}		
		log.warning("Generating MPS product data per resource DONE.");
	}

	/**
	 * 
	 * @param resource
	 */
	private void saveMPS()
	{
		log.warning("Saving MPS product data ...");
		Iterator<MUNSMPSProduct> listMPSProduct = m_ListMPSProduct.values().iterator();
		while (listMPSProduct.hasNext())
		{
			MUNSMPSProduct mpsProduct = listMPSProduct.next();
			try{
				mpsProduct.setUNS_MPSHeader_ID(m_MPSHeader.getUNS_MPSHeader_ID());
				mpsProduct.rescaleAllNumbers();
				mpsProduct.save();
				saveChild(mpsProduct, m_Resource);
				saveChild(mpsProduct);
			} catch (Exception ex){
				log.log(Level.SEVERE, ex.toString());
				throw new AdempiereException(ex.getMessage().toString());
			}
		}	
		log.warning("Saving MPS product data DONE.");
	}

	/**
	 * 
	 * @param dayOfYear
	 * @param weekNo
	 * @param UNS_Resource_ID
	 * @param M_Product_ID
	 * @return
	 */
	public String generateKeyMPSProduct(
			int dayOfYear, int weekNo, int UNS_Resource_ID, int M_Product_ID)
	{
		String key = ""+ M_Product_ID;
		if (dayOfYear > 0)
			key += "_" + dayOfYear;
		if (weekNo > 0)
			key += "_" + weekNo;
		if (UNS_Resource_ID > 0)
			key += "_"+UNS_Resource_ID ;
		return key;
	}
	
	/**
	 * 
	 * @param yearlyForecast
	 * @param mpsProduct
	 *
	private void generateMPSProductDaily( MUNSForecastDate yearlyForecast, MUNSMPSProduct mpsProduct)
	{
		double weight = getWeightOf(yearlyForecast.getM_Product_ID());
		double onHandMT = getOnHandMTOfProduct(mpsProduct.getM_Product_ID());
		Hashtable<Timestamp, MUNSForecastDate> listDailyForecast = 
				MUNSForecastDate.getDailyForecast(yearlyForecast.getUNS_Forecast_Date_ID(), get_TrxName());
		
		
		double totalActualToStockMT = 0.0;
		double totalActualToStockUOM = 0.0;
		
		//variable untuk simpan nilai qtyMt sementara
		double actualToStockJanMT = 0.0;
		double actualToStockFebMT = 0.0;
		double actualToStockMarMT = 0.0;
		double actualToStockAprMT = 0.0;
		double actualToStockMayMT = 0.0;
		double actualToStockJunMT = 0.0;
		double actualToStockJulMT = 0.0;
		double actualToStockAugMT = 0.0;
		double actualToStockSepMT = 0.0;
		double actualToStockOctMT = 0.0;
		double actualToStockNovMT = 0.0;
		double actualToStockDecMT = 0.0;
		
		//variabel untuk simpan nilai qtyuom sementara
		double actualToStockJanUOM = 0.0;
		double actualToStockFebUOM = 0.0;
		double actualToStockMarUOM = 0.0;
		double actualToStockAprUOM = 0.0;
		double actualToStockMayUOM = 0.0;
		double actualToStockJunUOM = 0.0;
		double actualToStockJulUOM = 0.0;
		double actualToStockAugUOM = 0.0;
		double actualToStockSepUOM = 0.0;
		double actualToStockOctUOM = 0.0;
		double actualToStockNovUOM = 0.0;
		double actualToStockDecUOM = 0.0;
		
//		int M_product_ID = yearlyForecast.getM_Product_ID();
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), yearlyForecast.getM_Product_ID(), get_TrxName());
		
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " +yearlyForecast.getM_Product().getName());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + yearlyForecast.getM_Product().getName());
		}
		
		double SafetyStockMT = OtherProductConf.getSafetyStock().doubleValue() * weight / 1000;
		double incubationDay = OtherProductConf.getIncubationDays().doubleValue();
		//end create MPS product
		
	
		MUNSMPSProductWeekly prevDailyMPS = null;
		double prevPAB = 0.0;
		double prevStock = 0.0;
		double prevAccAtp = 0.0;
		if(null != m_prevMPSHeader)
		{
			I_C_Period period = m_prevMPSHeader.getPeriodEnd();
			Timestamp lastPrevDate = period.getEndDate();
			prevDailyMPS = m_prevMPSHeader.getMPSProductPeriodic(
								mpsProduct.getM_Product_ID(), 0, lastPrevDate);
			
			prevPAB = prevDailyMPS.getProjectedActualBalance().doubleValue();
			prevStock = prevDailyMPS.getStock().doubleValue();
			prevAccAtp = prevDailyMPS.getAcc_ATP().doubleValue();
		}
		MUNSForecastDate[] ForecastWeeklyOFYears = yearlyForecast.getLinesWeekOfYear();
		for (MUNSForecastDate ForecastWeeklyOFYear : ForecastWeeklyOFYears)
		{
			m_MapOfWeeklyForecast.put(ForecastWeeklyOFYear.getWeekNo(), ForecastWeeklyOFYear);
			
			Calendar cal = Calendar.getInstance();			
			MUNSForecastDate[] ForecastDayOfWeeks =ForecastWeeklyOFYear.getLinesDayOfWeek(false);


			for (MUNSForecastDate ForecastDayOfWeek : ForecastDayOfWeeks)
			{
				cal.setTimeInMillis(ForecastDayOfWeek.getForcastedDate().getTime());
				int dayMPS = cal.get(Calendar.DAY_OF_YEAR);
				if (dayMPS == m_MPSHeader.getStartDay()
						&& null == prevDailyMPS)
				{
					prevPAB = onHandMT;
					prevStock = onHandMT;
				}
				
				//generate MPS Product Day OF weekly And Day OF Year
				double PAB = 0.0;
				double stock = 0.0;
				double atp = 0.0;
				MUNSMPSProductWeekly MPSProductDayly = new MUNSMPSProductWeekly(getCtx(), 0, get_TrxName());

				MPSProductDayly.setActualToStockMT(ForecastDayOfWeek.getDecidedQtyMT());
				MPSProductDayly.setActualToStockUOM(ForecastDayOfWeek.getDecidedQty());
				
				MPSProductDayly.setActualToOrderMT(BigDecimal.ZERO); //Nanti diisikan dari PO
				MPSProductDayly.setActualToOrderUOM(BigDecimal.ZERO);
				
				if (MPSProductDayly.getActualToOrderMT().compareTo(BigDecimal.ZERO)>0)
				{
					PAB = prevPAB + ForecastDayOfWeek.getDecidedQtyMT().doubleValue() 
							- MPSProductDayly.getActualToOrderMT().doubleValue();
					if (dayMPS == m_MPSHeader.getStartDay())
					{
						atp = onHandMT + ForecastDayOfWeek.getDecidedQtyMT().doubleValue()
								 - SafetyStockMT 
								 - MPSProductDayly.getActualToOrderMT().doubleValue();
					}
					else
					{
						atp = ForecastDayOfWeek.getDecidedQtyMT().doubleValue() + SafetyStockMT
								- MPSProductDayly.getActualToOrderMT().doubleValue();
					}
			
					if(null != prevDailyMPS)
					{
						double prevDailyAtp = prevDailyMPS.getATP().doubleValue();
						prevDailyAtp -= MPSProductDayly.getActualToOrderMT().doubleValue();
						prevDailyMPS.setATP(new BigDecimal(prevDailyAtp));
						Calendar calPrevMPS = Calendar.getInstance();
						calPrevMPS.setTimeInMillis(prevDailyMPS.getMPSDate().getTime());
						calPrevMPS.add(Calendar.DATE, -1);
						Timestamp dateMPS2HariSebelumnya = new Timestamp(calPrevMPS.getTimeInMillis());
						MUNSMPSProductWeekly mps2HariSebelumnya =
								m_prevMPSHeader.getMPSProductPeriodic(mpsProduct.getM_Product_ID()
										, 0
										, dateMPS2HariSebelumnya);
						if(null != mps2HariSebelumnya)
						{
							double accAtp2HariYangLalu = mps2HariSebelumnya.getAcc_ATP().doubleValue();
							prevAccAtp = accAtp2HariYangLalu + prevDailyAtp;
							prevDailyMPS.setAcc_ATP(new BigDecimal(prevAccAtp));
						}
						prevDailyMPS.save();
						prevDailyMPS = null;
					}
				}
				else
				{
					PAB = prevPAB + ForecastDayOfWeek.getDecidedQtyMT().doubleValue()
							- MPSProductDayly.getActualToStockMT().doubleValue();
					if (dayMPS == m_MPSHeader.getStartDay())
					{
						atp = onHandMT + ForecastDayOfWeek.getDecidedQtyMT().doubleValue()
								 - SafetyStockMT - ForecastDayOfWeek.getDecidedQtyMT().doubleValue();
					}
					else
					{
						atp = ForecastDayOfWeek.getDecidedQtyMT().doubleValue() - SafetyStockMT
								 - ForecastDayOfWeek.getDecidedQtyMT().doubleValue();
					}
					if(null != prevDailyMPS)
					{
						double prevDailyAtp = prevDailyMPS.getATP().doubleValue();
						prevDailyAtp -= MPSProductDayly.getActualToStockMT().doubleValue();
						prevDailyMPS.setATP(new BigDecimal(prevDailyAtp));
						Calendar calPrevMPS = Calendar.getInstance();
						calPrevMPS.setTimeInMillis(prevDailyMPS.getMPSDate().getTime());
						calPrevMPS.add(Calendar.DATE, -1);
						Timestamp dateMPS2HariSebelumnya = new Timestamp(calPrevMPS.getTimeInMillis());
						MUNSMPSProductWeekly mps2HariSebelumnya =
								m_prevMPSHeader.getMPSProductPeriodic(mpsProduct.getM_Product_ID()
										, 0
										, dateMPS2HariSebelumnya);
						if(null != mps2HariSebelumnya)
						{
							double accAtp2HariYangLalu = mps2HariSebelumnya.getAcc_ATP().doubleValue();
							prevAccAtp = accAtp2HariYangLalu + prevDailyAtp;
							prevDailyMPS.setAcc_ATP(new BigDecimal(prevAccAtp));
						}
						prevDailyMPS.save();
						prevDailyMPS = null;
					}
				}
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(ForecastDayOfWeek.getForcastedDate().getTime());
				calendar.add(Calendar.DATE, 1);
				MUNSForecastDate nextDailyForecast =
						listDailyForecast.get(new Timestamp(calendar.getTimeInMillis()));
				if (null != nextDailyForecast)
				{
					atp -= nextDailyForecast.getDecidedQtyMT().doubleValue();
				}
				
				MPSProductDayly.setProjectedActualBalance(new BigDecimal(PAB));
				MPSProductDayly.setAD_Org_ID(mpsProduct.getAD_Org_ID());
				MPSProductDayly.setATP(new BigDecimal(atp));
				MPSProductDayly.setMPSDate(ForecastDayOfWeek.getForcastedDate());
				MPSProductDayly.setIncubationDays(new BigDecimal(incubationDay));
				MPSProductDayly.setWeekNo(ForecastWeeklyOFYear.getWeekNo());
				
				long MiliSecondIncubDays = (long) (incubationDay * 24 * 60 * 60 * 1000) ;
						
				
				long StartIncubation = MPSProductDayly.getMPSDate().getTime();
				
				MPSProductDayly.setEndOfIncubation(new Timestamp(StartIncubation+MiliSecondIncubDays));
				MPSProductDayly.setStock(BigDecimal.ZERO); //Nanti Diisi dari PO
				MPSProductDayly.setQtyUom(ForecastDayOfWeek.getDecidedQty());
				MPSProductDayly.setQtyMT(ForecastDayOfWeek.getDecidedQtyMT());
				MPSProductDayly.setQtyDelivered(BigDecimal.ZERO);
				stock = prevStock + ForecastDayOfWeek.getDecidedQtyMT().doubleValue()
							- MPSProductDayly.getQtyDelivered().doubleValue();
				MPSProductDayly.setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
				prevPAB = PAB;
				prevStock = stock;
				
				double Acc_ATP = atp;
				Acc_ATP += prevAccAtp;

				MPSProductDayly.setAcc_ATP(new BigDecimal(Acc_ATP));
				//ini pasti mps tanggal sebelumnya karena pada saat mengambil daily forecast sudah di sortir
				// berdasarkan tanggal
				prevAccAtp = Acc_ATP;
				
				int month = cal.get(Calendar.MONTH);
				
				switch (month) {
				case 0 :
					actualToStockJanMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockJanUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 1 :
					actualToStockFebMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockFebUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 2 :
					actualToStockMarMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockMarUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 3 :
					actualToStockAprMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockAprUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 4 :
					actualToStockMayMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockMayUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 5 :
					actualToStockJunMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockJunUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 6 :
					actualToStockJulMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockJulUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 7 :
					actualToStockAugMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockAugUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 8 :
					actualToStockSepMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockSepUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 9 :
					actualToStockOctMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockOctUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 10 :
					actualToStockNovMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockNovUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				case 11 :
					actualToStockDecMT += MPSProductDayly.getActualToStockMT().doubleValue();
					actualToStockDecUOM += MPSProductDayly.getActualToStockUOM().doubleValue();
					break;
				default :;
				}
				//generate MPS Product Weekly
				String key = generateKeyMPSProduct(
						cal.get(Calendar.DAY_OF_YEAR), 0,
						0, ForecastDayOfWeek.getM_Product_ID());
				
				m_ListMPSProductDaily.put(key, MPSProductDayly);
			}
			//end for of ForecastDayOfWeek
		}
		//end FOr OF forecastDay Week of year
		
		totalActualToStockMT = actualToStockJanMT + 
				actualToStockFebMT + actualToStockMarMT + actualToStockAprMT + 
						actualToStockMayMT + actualToStockJunMT + actualToStockJulMT + 
								actualToStockAugMT + actualToStockSepMT + actualToStockOctMT + 
										actualToStockNovMT + actualToStockDecMT;
		totalActualToStockUOM = actualToStockJanUOM + actualToStockFebUOM+ actualToStockMarUOM + actualToStockAprUOM 
				+ actualToStockMayUOM + actualToStockJunUOM + actualToStockJulUOM + 
								actualToStockAugUOM + actualToStockSepUOM + actualToStockOctUOM + 
										actualToStockNovUOM + actualToStockDecUOM;
		
		//SetQty MT And UOM MPS Product
		mpsProduct.setQtyMT_Jan(new BigDecimal(actualToStockJanMT));
		mpsProduct.setQtyMT_Feb(new BigDecimal(actualToStockFebMT));
		mpsProduct.setQtyMT_Mar(new BigDecimal(actualToStockMarMT));
		mpsProduct.setQtyMT_Apr(new BigDecimal(actualToStockAprMT));
		mpsProduct.setQtyMT_May(new BigDecimal(actualToStockMayMT));
		mpsProduct.setQtyMT_Jun(new BigDecimal(actualToStockJunMT));
		mpsProduct.setQtyMT_Jul(new BigDecimal(actualToStockJulMT));
		mpsProduct.setQtyMT_Agt(new BigDecimal(actualToStockAugMT));
		mpsProduct.setQtyMT_Sep(new BigDecimal(actualToStockSepMT));
		mpsProduct.setQtyMT_Oct(new BigDecimal(actualToStockOctMT));
		mpsProduct.setQtyMT_Nov(new BigDecimal(actualToStockNovMT));
		mpsProduct.setQtyMT_Dec(new BigDecimal(actualToStockDecMT));
		
		mpsProduct.setQtyUOM_Jan(new BigDecimal(actualToStockJanUOM));
		mpsProduct.setQtyUOM_Feb(new BigDecimal(actualToStockFebUOM));
		mpsProduct.setQtyUOM_Mar(new BigDecimal(actualToStockMarUOM));
		mpsProduct.setQtyUOM_Apr(new BigDecimal(actualToStockAprUOM));
		mpsProduct.setQtyUOM_May(new BigDecimal(actualToStockMayUOM));
		mpsProduct.setQtyUOM_Jun(new BigDecimal(actualToStockJunUOM));
		mpsProduct.setQtyUOM_Jul(new BigDecimal(actualToStockJulUOM));
		mpsProduct.setQtyUOM_Agt(new BigDecimal(actualToStockAugUOM));
		mpsProduct.setQtyUOM_Sep(new BigDecimal(actualToStockSepUOM));
		mpsProduct.setQtyUOM_Oct(new BigDecimal(actualToStockOctUOM));
		mpsProduct.setQtyUOM_Nov(new BigDecimal(actualToStockNovUOM));
		mpsProduct.setQtyUOM_Dec(new BigDecimal(actualToStockDecUOM));
		mpsProduct.setTotalActualToStockMT(new BigDecimal(totalActualToStockMT));
		mpsProduct.setTotalActualToStockUOM(new BigDecimal(totalActualToStockUOM));
	}
	*/
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	private double getWeightOf(int M_Product_ID)
	{
		if (m_SessionProduct == M_Product_ID
				&& m_Weight > 0)
		{
			return m_Weight;
		}
		MProduct product = MProduct.get(getCtx(), M_Product_ID);
		m_Weight = product.getWeight().doubleValue();
		return m_Weight;
	}
	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	private double getOnHandMTOfProduct(int M_Product_ID)
	{
		if (m_SessionProduct == M_Product_ID
				&& m_OnHand >0)
		{
			return m_OnHand;
		}
		
		Double expectedOnHand = m_ExpectedOnHans.get(M_Product_ID);
		if (null != expectedOnHand)
		{
			m_OnHand = expectedOnHand;
			double weight = getWeightOf(M_Product_ID);
			m_OnHand *= weight / 1000;
			return m_OnHand;
		}
		else if (null != m_prevMPSHeader)
		{
			m_OnHand = getPrevOnHand(M_Product_ID);
			double weight = getWeightOf(M_Product_ID);
			m_OnHand *= weight / 1000;
			return m_OnHand;
		}
		else 
		{
			MStorageOnHand[] storageOnHand = MStorageOnHand.getOfProduct(getCtx(), M_Product_ID, get_TrxName());
			m_OnHand = 0.0;
			for (MStorageOnHand soh : storageOnHand)
			{
				m_OnHand += soh.getQtyOnHand().doubleValue();
			}
			double weight = getWeightOf(M_Product_ID);
			m_OnHand *= weight / 1000;
			return m_OnHand;
		}
	}
	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	private BigDecimal getOnHandOf(int M_Product_ID)
	{
		if (m_SessionProduct == M_Product_ID
				&& m_OnHandUOM.signum() >0)
		{
			return m_OnHandUOM;
		}
		
		Double expectedOnHand = m_ExpectedOnHans.get(M_Product_ID);
		if (null != expectedOnHand)
		{
			m_OnHandUOM = BigDecimal.valueOf(expectedOnHand);
			return m_OnHandUOM;
		}
		else if (null != m_prevMPSHeader)
		{
			m_OnHandUOM = BigDecimal.valueOf(getPrevOnHand(M_Product_ID));
			return m_OnHandUOM;
		}
		else 
		{
			MStorageOnHand[] storageOnHand = MStorageOnHand.getOfProduct(getCtx(), M_Product_ID, get_TrxName());
			m_OnHandUOM = Env.ZERO;
			for (MStorageOnHand soh : storageOnHand)
			{
				m_OnHandUOM = m_OnHandUOM.add(soh.getQtyOnHand());
			}
			return m_OnHandUOM;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private double getPrevOnHand(int M_Product_ID)
	{
		I_C_Period period = m_prevMPSHeader.getPeriodEnd();
		Timestamp prevEndDate = period.getEndDate();
		String sql = "SELECT " + MUNSMPSProductWeekly.COLUMNNAME_ProjectedActualBalance
				+ " FROM " + MUNSMPSProductWeekly.Table_Name
				+ " mpd INNER JOIN " + MUNSMPSProduct.Table_Name
				+ " mp ON mpd." + MUNSMPSProduct.COLUMNNAME_UNS_MPSProduct_ID + " = mp." 
				+ MUNSMPSProductWeekly.COLUMNNAME_UNS_MPSProduct_ID
				+ " AND mp." + MUNSMPSProduct.COLUMNNAME_M_Product_ID + " = ?"
				+ " INNER JOIN " + MUNSMPSHeader.Table_Name
				+ " mh ON mh." + MUNSMPSHeader.COLUMNNAME_UNS_MPSHeader_ID + " = mp."
				+ MUNSMPSProduct.COLUMNNAME_UNS_MPSHeader_ID
				+ " AND mh." + MUNSMPSHeader.COLUMNNAME_UNS_MPSHeader_ID + " = ?"
				+ " WHERE mpd." + MUNSMPSProductWeekly.COLUMNNAME_MPSDate + " = ?";
		BigDecimal onHand = DB.getSQLValueBD(get_TrxName(), sql, M_Product_ID,
				m_MPSHeader.getPrevMPS_ID(), prevEndDate);
		if (null == onHand)
			onHand = BigDecimal.ZERO;
		return onHand.doubleValue();
	}

	
	/**
	 * initial proportion of product in resources
	 * @param yearlyForecast
	 */
	private void initProportion()
	{
		for (MUNSForecastDate yearlyForecast : m_ForecastHeader.getLinesYearly())
		{
			MUNSResource[] resourceOfProduct = m_Resource.getResourcesOf(yearlyForecast.getM_Product_ID());
			BigDecimal totalProductInAllResource = BigDecimal.ZERO;
			for (MUNSResource resource : resourceOfProduct)
			{
				BigDecimal maximumQuantity = resource.getTotalActualMaxCapsOf(yearlyForecast.getM_Product_ID());
				BigDecimal maximumQuantityMT = resource.getTotalActualMaxCapsMTOf(yearlyForecast.getM_Product_ID());
				totalProductInAllResource = totalProductInAllResource.add(maximumQuantity);
				ResourceProportion rscProportion = new ResourceProportion(
						resource.getUNS_Resource_ID(), yearlyForecast.getM_Product_ID());
				rscProportion.setActualMaxCaps(maximumQuantity);
				rscProportion.setActualMaxCapsMT(maximumQuantityMT);
				m_rscProportion.add(rscProportion);
			}
			
			String allocationMethod = m_ForecastHeader.getAllocationMethod();
			if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_Proportion))
			{
				for (ResourceProportion rscProportion : m_rscProportion)
				{
					if (rscProportion.getM_product_ID() == yearlyForecast.getM_Product_ID())
						rscProportion.initProportion(totalProductInAllResource);
				}
			}
			else if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_MaximumToMinimum))
			{
				Collections.sort(m_rscProportion, Collections.reverseOrder());
			}
			else if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_MinimumToMaximum))
			{
				Collections.sort(m_rscProportion);
			}
		}
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param resource
	 */
	private void saveChild(MUNSMPSProduct mpsProduct, MUNSResource resource) 
	{
		for (ResourceProportion rscProportion : m_rscProportion)
		{
			String keyMPSProductResource = generateKeyMPSProduct(
					0, 0, rscProportion.getResource_ID(), mpsProduct.getM_Product_ID());
			MUNSMPSProductResource mpsProductResource = m_ListProductResource.get(keyMPSProductResource);
			if (null != mpsProductResource)
			{
				mpsProductResource.setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
				mpsProductResource.setUNS_Resource_ID(rscProportion.getResource_ID());
				mpsProductResource.rescaleAllNumbers();
				mpsProductResource.save();
				saveChild(mpsProduct, mpsProductResource, rscProportion);
			}
		}
	}

	/**
	 * 
	 * @param mpsProduct
	 * @param mpsProductResource
	 * @param resource
	 */
	private void saveChild(MUNSMPSProduct mpsProduct, 
						   MUNSMPSProductResource mpsProductResource,
						   ResourceProportion rscProportion)
	{
		for (int weekNo=m_startWeekNo; weekNo <= m_endWeekNo; weekNo++)
		{
			String keyMPSProductRscWeekly = generateKeyMPSProduct(
					0, weekNo, rscProportion.getResource_ID(), mpsProduct.getM_Product_ID());
			
			MUNSMPSProductRscWeekly mpsProductRscWeekly = 
					m_ListProductResourceWeekly.get(keyMPSProductRscWeekly);
			
			if (null == mpsProductRscWeekly)
				continue;
		
			mpsProductRscWeekly.setUNS_MPSProduct_Resource_ID(
					mpsProductResource.getUNS_MPSProduct_Resource_ID());
			mpsProductRscWeekly.rescaleAllNumbers();
			mpsProductRscWeekly.save();
			/*
			int maxDayInWeek = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
			for (int j=1; j<=maxDayInWeek; j++)
			{
				String keyProductRscDaily = generateKeyMPSProduct(
						j, mpsProductRscWeekly.getWeekNo(), rscProportion.getResource_ID()
						, mpsProduct.getM_Product_ID());
				
				MUNSMPSProductRscWeekly mpsProductRscDaily = 
						m_ListProductResourceDaily.get(keyProductRscDaily);
				
				if (null == mpsProductRscDaily)
					continue;
			
				mpsProductRscDaily.setWeekNo(i);
				mpsProductRscDaily.setUNS_MPSProduct_Resource_ID(
						mpsProductResource.getUNS_MPSProduct_Resource_ID());
				mpsProductRscDaily.setWeekly_ID(mpsProductRscWeekly.getUNS_MPSProductRsc_Weekly_ID());
				mpsProductRscDaily.rescaleAllNumbers();
				mpsProductRscDaily.save();
			}
			*/	
		}
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @throws Exception
	 */
	private void saveChild(MUNSMPSProduct mpsProduct) throws Exception
	{
		for (int weekNo=m_startWeekNo; weekNo <= m_endWeekNo; weekNo++)
		{
			String key = generateKeyMPSProduct(
					0, weekNo,0, mpsProduct.getM_Product_ID());
			MUNSMPSProductWeekly mpsProductWeekly = m_ListMPSProductWeekly.get(key);
			if (null == mpsProductWeekly)
				continue;
			
			mpsProductWeekly.setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
			try {
				mpsProductWeekly.rescaleAllNumbers();
				mpsProductWeekly.save();
			}catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
		/*
		for (int i=1; i<=calendar.getActualMaximum(Calendar.DAY_OF_YEAR); i++)
		{
			String key = generateKeyMPSProduct(i, 0, 0, mpsProduct.getM_Product_ID());
			
			MUNSMPSProductWeekly mpsProductDaily =  m_ListMPSProductDaily.get(key);
			if (null == mpsProductDaily)
				continue;
			
			mpsProductDaily.setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
			mpsProductDaily.rescaleAllNumbers();
			mpsProductDaily.save();
		}
		*/
	}
}

class ResourceProportion implements Comparable<ResourceProportion>{
	double m_Proportion = 1.0;
	int m_Resource_ID = 0;
	int m_Product_ID = 0;
	double m_ActualMaxCaps = 0.0;
	double m_ActualMaxCapsMT = 0.0;
	ResourceProportion(int UNS_Resource_ID, int M_Product_ID)
	{
		this.m_Resource_ID = UNS_Resource_ID;
		this.m_Product_ID = M_Product_ID;
	}
	double getProportion(int UNS_Resource_ID, int M_Product_ID)
	{
		return m_Proportion;
	}
	int getResource_ID()
	{
		return m_Resource_ID;
	}
	double getProportion()
	{
		return m_Proportion;
	}
	void setProportion(double proportion)
	{
		this.m_Proportion = proportion;
	}
	double getActualMaxCaps()
	{
		return m_ActualMaxCaps * m_Proportion;
	}
	double getActualMaxCapsMT()
	{
		return m_ActualMaxCapsMT * m_Proportion;
	}
	void setActualMaxCaps(BigDecimal actualMaxCaps)
	{
		this.m_ActualMaxCaps = actualMaxCaps.doubleValue();
	}
	void setActualMaxCapsMT( BigDecimal actualMaxCapsMT)
	{
		this.m_ActualMaxCapsMT = actualMaxCapsMT.doubleValue();
	}
	void initProportion(BigDecimal maximumToAllocate)
	{
		setProportion(m_ActualMaxCaps / maximumToAllocate.doubleValue());
	}
	int getM_product_ID()
	{
		return m_Product_ID;
	}
	
	
	@Override
	public int compareTo(ResourceProportion o) 
	{
		if (getActualMaxCaps() < o.getActualMaxCaps())
			return -1;
		else if (getActualMaxCaps() > o.getActualMaxCaps())
			return 1;
		else
			return 0;
	}
}