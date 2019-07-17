package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMPSProductResource extends X_UNS_MPSProduct_Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** */
	private MUNSMPSProductRscWeekly[] m_lines_weekly = null;
	/** */
	private MUNSMPSProductRscWeekly[] m_lines_dayly = null;

	public static final String[] NUMBERTYPE_COLUMNS = new String[] {
		COLUMNNAME_ActualProduced,
		COLUMNNAME_ActualScheduledMT,
		COLUMNNAME_ActualScheduledUOM,
		COLUMNNAME_ActualToOrderMT,
		COLUMNNAME_ActualToOrderUOM,
		COLUMNNAME_ActualToStockMT,
		COLUMNNAME_ActualToStockUOM
	};
	
	
	/**
	 * 
	 */
	public void rescaleAllNumbers() {
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, 4, BigDecimal.ROUND_HALF_UP, false);
	}
	
	/**
	 * @param ctx
	 * @param UNS_MPSProduct_Resource_ID
	 * @param trxName
	 */
	public MUNSMPSProductResource(Properties ctx,
			int UNS_MPSProduct_Resource_ID, String trxName) {
		super(ctx, UNS_MPSProduct_Resource_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMPSProductResource(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_MPSProduct_ID
	 * @param UNS_Resource_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMPSProductResource get(
			Properties ctx, int UNS_MPSProduct_ID, int UNS_Resource_ID, String trxName)
	{
		MUNSMPSProductResource mpsProductRsc =  
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "UNS_MPSProduct_ID=? AND UNS_Resource_ID=?", 
						  trxName)
				.setParameters(UNS_MPSProduct_ID, UNS_Resource_ID)
				.first();
		
		if (mpsProductRsc != null)
			return mpsProductRsc;
		
		// Create it.
		mpsProductRsc = new MUNSMPSProductResource(ctx, 0, trxName);
		
		mpsProductRsc.setUNS_MPSProduct_ID(UNS_MPSProduct_ID);
		mpsProductRsc.setUNS_Resource_ID(UNS_Resource_ID);
		
		return mpsProductRsc;
	}	

	/**
	 * @param boolean requery
	 * @return {@link MUNSMPSProductRscWeekly}
	 */
	public MUNSMPSProductRscWeekly[] getLinesWeekly(boolean requery)
	{
		if (null != m_lines_weekly && !requery)
		{
			set_TrxName(m_lines_weekly, get_TrxName());
			return m_lines_weekly;
		}
		
		final String whereClause = X_UNS_MPSProductRsc_Weekly.COLUMNNAME_UNS_MPSProduct_Resource_ID + "=? AND "
				+ X_UNS_MPSProductRsc_Weekly.COLUMNNAME_StartDate + " IS NOT NULL AND "
				+ X_UNS_MPSProductRsc_Weekly.COLUMNNAME_EndDate + " IS NOT NULL";
		
		List<X_UNS_MPSProductRsc_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProductRsc_Weekly.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSProduct_Resource_ID())
				.setOrderBy(X_UNS_MPSProductRsc_Weekly.COLUMNNAME_WeekNo)
				.list();
		
		m_lines_weekly = new MUNSMPSProductRscWeekly[list.size()];
		list.toArray(m_lines_weekly);
		
		return m_lines_weekly;
	}
	
	/**
	 * 
	 * @return {@link MUNSMPSProductRscWeekly}
	 */
	public MUNSMPSProductRscWeekly[] getLinesWeekly()
	{
		return getLinesWeekly(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return {@link MUNSMPSProductRscWeekly}
	 */
	public MUNSMPSProductRscWeekly[] getLinesDayly(boolean requery)
	{
		if (m_lines_dayly != null && !requery)
		{
			set_TrxName(m_lines_dayly, get_TrxName());
			return m_lines_dayly;
		}
		
		final String whereClause = X_UNS_MPSProductRsc_Weekly.COLUMNNAME_UNS_MPSProduct_Resource_ID + "=? AND "
				+ X_UNS_MPSProductRsc_Weekly.COLUMNNAME_MPSDate + "  IS NOT NULL";
		
		List<X_UNS_MPSProductRsc_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProductRsc_Weekly.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSProduct_Resource_ID())
				.setOrderBy(X_UNS_MPSProductRsc_Weekly.COLUMNNAME_MPSDate)
				.list();
		
		m_lines_dayly = new MUNSMPSProductRscWeekly[list.size()];
		list.toArray(m_lines_dayly);
		
		return m_lines_dayly;
	}
	
	/**
	 * 
	 * @param forecast
	 */
	public void copyValues(MUNSForecastDate forecast)
	{
		setActualToStockMT(forecast.getDecidedQtyMT());
		setActualToStockUOM(forecast.getDecidedQty());
	}
	
	/**
	 * 
	 * @param forecast
	 */
	public void calculate(MUNSForecastDate forecast)
	{
//		setActualToStockMT(getActualToStockMT().add(forecast.getQtyMT()));
//		setActualToStockUOM(getActualToStockUOM().add(forecast.getQtyUom()));
		setActualToStockMT(getActualToStockMT().add(forecast.getDecidedQtyMT()));
		setActualToStockUOM(getActualToStockUOM().add(forecast.getDecidedQty()));
	}
	
	/**
	 * 
	 * @return {@link MUNSMPSProductRscWeekly}
	 */
	public MUNSMPSProductRscWeekly[] getLinesDayly()
	{
		return getLinesDayly(false);
	}
	
	private boolean deleteLines()
	{
		for (MUNSMPSProductRscWeekly dailyMPS : getLinesDayly())
		{
			try{
				dailyMPS.delete(true);
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		}
		
		for (MUNSMPSProductRscWeekly weeklyMPS : getLinesWeekly())
		{
			try{
				weeklyMPS.delete(true);
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		}
		return true;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		return deleteLines();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSMPSProductRscWeekly> getMapOfWeeklyMPSRsc() 
	{
		Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfWeeklyMPSRsc =
				new Hashtable<Integer, MUNSMPSProductRscWeekly>();
		Calendar cal = Calendar.getInstance();
		for(MUNSMPSProductRscWeekly mpsRscWeekly : getLinesWeekly(false))
		{
			cal.setTimeInMillis(mpsRscWeekly.getStartDate().getTime());
			int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
			mapOfWeeklyMPSRsc.put(weekOfYear, mpsRscWeekly);
		}
		return mapOfWeeklyMPSRsc;
	}
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSMPSProductRscWeekly> getMapOfDailyMPSRsc() 
	{
		Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfWeeklyMPSRsc =
				new Hashtable<Integer, MUNSMPSProductRscWeekly>();
		Calendar cal = Calendar.getInstance();
		for(MUNSMPSProductRscWeekly mpsRscWeekly : getLinesDayly(false))
		{
			cal.setTimeInMillis(mpsRscWeekly.getMPSDate().getTime());
			int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
			mapOfWeeklyMPSRsc.put(dayOfYear, mpsRscWeekly);
		}
		return mapOfWeeklyMPSRsc;
	}
	/*
	private void moreAction()
	{
		Hashtable<String, MUNSMPSProductRscWeekly> mapOfMPSProductWeekly =
				new Hashtable<String, MUNSMPSProductRscWeekly>();
		MUNSMPSProduct mpsProduct = getParent();
		MUNSMPSHeader mpsHeader = mpsProduct.getParent();
		long endLoop = mpsHeader.getEndDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mpsHeader.getStartDate().getTime());
		while(cal.getTimeInMillis() <= endLoop)
		{
			int C_PeriodStart_ID = mpsHeader.getPeriodStart_ID();
			int C_PeriodEnd_ID = mpsHeader.getPeriodEnd_ID();
			
			int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
			if(weekNo ==1 && cal.get(Calendar.DAY_OF_YEAR) > 350)
				weekNo = 53;
			
			MUNSMPSProductRscWeekly mpsProductRscDay = 
					new MUNSMPSProductRscWeekly(getCtx(), 0, get_TrxName());
			mpsProductRscDay.setUNS_MPSProduct_Resource_ID(get_ID());
			mpsProductRscDay.setAD_Org_ID(getAD_Org_ID());
			mpsProductRscDay.setMPSDate(new Timestamp(cal.getTimeInMillis()));
			mpsProductRscDay.setWeekNo(weekNo);
			
			MUNSMPSProductRscWeekly mpsProductRscWeek = mapOfMPSProductWeekly.get(
					"" + weekNo + "_" + mpsProduct.getM_Product_ID());
			
			if(null == mpsProductRscWeek)
			{
				Calendar weekcal = (Calendar) cal.clone();
				int thisWeekInMonth = weekcal.get(Calendar.WEEK_OF_MONTH);
				int weekOfYear = weekcal.get(Calendar.WEEK_OF_YEAR);
				if(weekOfYear ==1 && weekcal.get(Calendar.DAY_OF_YEAR) > 350)
					weekNo = 53;
				int C_Period_ID = MPeriod.getC_Period_ID(getCtx(), 
						new Timestamp(cal.getTimeInMillis()), 
						getAD_Org_ID());
				
				if(thisWeekInMonth == 1 && C_Period_ID == C_PeriodStart_ID)
					weekcal.setTimeInMillis(mpsHeader.getStartDate().getTime());
				else
					weekcal.set(Calendar.DAY_OF_WEEK, 1);
				
				Timestamp startDateOfWeek = new Timestamp(weekcal.getTimeInMillis());
				
				if(thisWeekInMonth == weekcal.getActualMaximum(Calendar.WEEK_OF_MONTH)
						&& C_Period_ID == C_PeriodEnd_ID)
					weekcal.setTimeInMillis(mpsHeader.getEndDate().getTime());
				else
					weekcal.set(Calendar.DAY_OF_WEEK, 7);
				
				Timestamp endDateOfWeek = new Timestamp(weekcal.getTimeInMillis());
					
				mpsProductRscWeek = new MUNSMPSProductRscWeekly(getCtx(), 0, get_TrxName());
				mpsProductRscWeek.setUNS_MPSProduct_Resource_ID(get_ID());
				mpsProductRscWeek.setAD_Org_ID(getAD_Org_ID());
				mpsProductRscWeek.setEndDate(endDateOfWeek);
				mpsProductRscWeek.setStartDate(startDateOfWeek);
				mpsProductRscWeek.setMPSDate(null);
				mpsProductRscWeek.setWeekNo(weekNo);
				mpsProductRscWeek.save();
				mapOfMPSProductWeekly.put("" + weekNo + "_" + mpsProduct.getM_Product_ID(), mpsProductRscWeek);
			}
			mpsProductRscDay.setWeekly_ID(mpsProductRscWeek.get_ID());
			if(!mpsProductRscDay.save())
				throw new AdempiereException("Can't create daily MPS Product Rsc");
				
			cal.add(Calendar.DATE, 1);
			
		}
	}
	*/
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//if(newRecord && success && !isGenerate())
			//moreAction();
		return super.afterSave(newRecord, success);
	}
	
	public boolean isGenerate()
	{
		return getUNS_MPSProduct().isGenerate();
	}
	
	public MUNSMPSProduct getParent()
	{
		return (MUNSMPSProduct)getUNS_MPSProduct();
	}
}
