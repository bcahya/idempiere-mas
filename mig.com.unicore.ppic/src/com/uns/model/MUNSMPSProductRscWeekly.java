/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMPSProductRscWeekly extends X_UNS_MPSProductRsc_Weekly {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * @param UNS_MPSProductRsc_Weekly_ID
	 * @param trxName
	 */
	public MUNSMPSProductRscWeekly(Properties ctx,
			int UNS_MPSProductRsc_Weekly_ID, String trxName) {
		super(ctx, UNS_MPSProductRsc_Weekly_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMPSProductRscWeekly(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_MPSProduct_Resource_ID
	 * @param weekNo
	 * @param trxName
	 * @return
	 */
	public static MUNSMPSProductRscWeekly get(
			Properties ctx, int UNS_MPSProduct_Resource_ID, int weekNo, String trxName)
	{
		MUNSMPSProductRscWeekly mpsProductRscWeekly =  
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "UNS_MPSProduct_Resource_ID=? AND Weekly_ID IS NULL AND MPSDate IS NULL AND WeekNo=?", 
						  trxName)
				.setParameters(UNS_MPSProduct_Resource_ID, weekNo)
				.first();
		
		if (mpsProductRscWeekly != null)
			return mpsProductRscWeekly;
		
		// Create it.
		mpsProductRscWeekly = new MUNSMPSProductRscWeekly(ctx, 0, trxName);
		
		mpsProductRscWeekly.setUNS_MPSProduct_Resource_ID(UNS_MPSProduct_Resource_ID);
		mpsProductRscWeekly.setWeekNo(weekNo);
		
		return mpsProductRscWeekly;
	}
	
	public static MUNSMPSProductRscWeekly get(
			Properties ctx, int Rsc_Org_ID, int M_Product_ID, Timestamp specificDate, String trxName)
	{
		MUNSMPSProductRscWeekly retMPSRscWeek = null;
		String sql = 
				"SELECT prodRscWeekly.UNS_MPSProductRsc_Weekly_ID FROM UNS_MPSProductRsc_Weekly prodRscWeekly " +
				" 	INNER JOIN UNS_MPSProduct_Resource prodRsc ON prodRscWeekly.UNS_MPSProduct_Resource_ID=prodRsc.UNS_MPSProduct_Resource_ID " +
				" 	INNER JOIN UNS_MPSProduct mpsProd ON prodRsc.UNS_MPSProduct_ID=mpsProd.UNS_MPSProduct_ID " +
				"	INNER JOIN UNS_MPSHeader mps ON mpsProd.UNS_MPSHeader_ID=mps.UNS_MPSHeader_ID " +
				"	INNER JOIN C_Period periodStart ON mps.PeriodStart_ID=periodStart.C_Period_ID " +
				"									   	AND periodStart.StartDate<=?" +
				"	INNER JOIN C_Period periodEnd ON mps.PeriodEnd_ID=periodEnd.C_Period_ID " +
				"									 	AND periodEnd.EndDate>=?" +
				" WHERE mpsProd.M_Product_ID=? AND prodRscWeekly.WeekNo=? AND prodRscWeekly.MPSDate is null " +
				"	AND prodRscWeekly.Weekly_ID is null AND prodRsc.UNS_Resource_ID IN (" +
				"		SELECT rsc.UNS_Resource_ID FROM UNS_Resource rsc WHERE rsc.AD_Org_ID=?)";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(specificDate.getTime());
		int mpsPrdRscWeeklyID = DB.getSQLValue(trxName, sql, 
											   specificDate,
											   specificDate,
											   M_Product_ID,
											   cal.get(Calendar.WEEK_OF_YEAR),
											   Rsc_Org_ID);
		
		if (mpsPrdRscWeeklyID > 0) 
		{
			retMPSRscWeek = new MUNSMPSProductRscWeekly(ctx, mpsPrdRscWeeklyID, trxName);
		}
		return retMPSRscWeek;
	}

	/** */
	private MUNSMPSProductRscWeekly[] m_lines_dayOfWeek = null;
	
	/**
	 * 
	 * @param requery
	 * @return {@link MUNSMPSProductRscWeekly} Day of week
	 */
	public MUNSMPSProductRscWeekly[] getLinesDayOfWeek(boolean requery)
	{
		if (m_lines_dayOfWeek != null && !requery)
		{
			set_TrxName(m_lines_dayOfWeek, get_TrxName());
			return m_lines_dayOfWeek;
		}
		
		final String whereClause = COLUMNNAME_Weekly_ID + "=? AND ";
		
		List<X_UNS_MPSProductRsc_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSProductRsc_Weekly_ID())
				.setOrderBy(COLUMNNAME_WeekNo)
				.list();
		
		m_lines_dayOfWeek = new MUNSMPSProductRscWeekly[list.size()];
		list.toArray(m_lines_dayOfWeek);
		
		return m_lines_dayOfWeek;
	}
	
	/**
	 * 
	 * @return {@link MUNSMPSProductRscWeekly} Day Of Week
	 */
	public MUNSMPSProductRscWeekly[] getLinesDayOfWeek()
	{
		return getLinesDayOfWeek(false);
	}
	
	/**
	 * 
	 * @param forecast
	 */
	public void calculate(MUNSForecastDate forecast)
	{
		setActualToStockMT(getActualToStockMT().add(forecast.getDecidedQtyMT()));
		setActualToStockUOM(getActualToStockUOM().add(forecast.getDecidedQty()));
	}
	
	public void calculate(MUNSMPSProductRscWeekly dailyProductRsc)
	{
		setActualToStockMT(getActualToStockMT().add(dailyProductRsc.getActualToStockMT()));
		setActualToStockUOM(getActualToStockUOM().add(dailyProductRsc.getActualToStockUOM()));
		setActualToOrderMT(getActualToOrderMT().add(dailyProductRsc.getActualToOrderMT()));
		setActualToOrderUOM(getActualToOrderUOM().add(dailyProductRsc.getActualToOrderUOM()));
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResource getResource(){
		MUNSMPSProductResource mpspr = new MUNSMPSProductResource(getCtx(), getUNS_MPSProduct_Resource_ID(), get_TrxName());
		MUNSResource rsc = new MUNSResource(getCtx(), mpspr.getUNS_Resource_ID(), get_TrxName());
		
		return rsc;
	}

	/**
	 *  
	 * @param ctx
	 * @param ps
	 * @param trxName
	 * @return
	 */
	public boolean updateFromMO(Properties ctx, MUNSProductionSchedule ps, String trxName) 
	{	
		//MUNSMPSProductRscWeekly mpsRscWeekly = (MUNSMPSProductRscWeekly) ps.getUNS_MPSProductRsc_Weekly();
		
		BigDecimal actualScheduled = ps.getQtyOrdered();
		BigDecimal actualScheduledMT = 
				actualScheduled.multiply(ps.getM_Product().getWeight()).multiply(new BigDecimal(0.001));
		
		setActualScheduledUOM(actualScheduled);
		setActualScheduledMT(actualScheduledMT);
		//setActualProduced(ps.getQtyManufactured());
		
		if (!save())
			throw new AdempiereException("Failed when updating MPS based on production scheduled.");
		
		// Update the MPS Product Resource.
		MUNSMPSProductResource mpsRsc = (MUNSMPSProductResource) getUNS_MPSProduct_Resource();
		
		mpsRsc.setActualScheduledUOM(mpsRsc.getActualScheduledUOM().add(actualScheduled));
		mpsRsc.setActualScheduledMT(mpsRsc.getActualScheduledMT().add(actualScheduledMT));
		//mpsRsc.setActualProduced(mpsRsc.getActualProduced().add(ps.getQtyManufactured()));

		if (!mpsRsc.save())
			throw new AdempiereException("Failed when updating MPS based on production scheduled.");
		
		// Update the MPS Product.
		MUNSMPSProduct mpsProduct = (MUNSMPSProduct) mpsRsc.getUNS_MPSProduct();
		
		mpsProduct.setActualScheduledUOM(mpsProduct.getActualScheduledUOM().add(actualScheduled));
		mpsProduct.setActualScheduledMT(mpsProduct.getActualScheduledMT().add(actualScheduledMT));
		allocateToMonthlyMPSProduct(mpsProduct, actualScheduled, actualScheduledMT);
		//mpsProduct.setActualProduced(mpsProduct.getActualProduced().add(ps.getQtyManufactured()));
		
		if (mpsProduct.getWeekToBeUpdated() == 0 || mpsProduct.getWeekToBeUpdated() > getWeekNo())
			mpsProduct.setWeekToBeUpdated(getWeekNo());

		if (!mpsProduct.save())
			throw new AdempiereException("Failed when updating MPS based on production scheduled.");
		
		// Update the MPS Product Weekly.
		X_UNS_MPSProduct_Weekly mpsProductWeekly = 
				MUNSMPSProductWeekly.get(ctx, mpsProduct, getWeekNo(), trxName);

		if (mpsProductWeekly == null || mpsProductWeekly.get_ID() == 0)
			throw new AdempiereException("MPS is unsync.");
		
		mpsProductWeekly.setActualScheduledUOM(mpsProductWeekly.getActualScheduledUOM().add(actualScheduled));
		mpsProductWeekly.setActualScheduledMT(mpsProductWeekly.getActualScheduledMT().add(actualScheduledMT));
		//mpsProductWeekly.setActualProduced(mpsProductWeekly.getActualProduced().add(ps.getQtyManufactured()));

		if (!mpsProductWeekly.save())
			throw new AdempiereException("Failed when updating MPS based on production scheduled.");

		// Update MPS Header.
		MUNSMPSHeader mpsHeader = (MUNSMPSHeader) mpsProduct.getUNS_MPSHeader();

		if (!mpsHeader.isSyncDatabase())
		{
			mpsHeader.setIsSyncDatabase(true);

			if (!mpsHeader.save())
				throw new AdempiereException("Failed when updating MPS based on production scheduled.");
		}
		
		return true;
	}
	
	/**
	 *  
	 * @param ctx
	 * @param ps
	 * @param trxName
	 * @return
	 */
	public boolean updateFromProduction (Properties ctx, MUNSProductionDetail pd, String trxName) 
	{	
		//MUNSMPSProductRscWeekly mpsRscWeekly = (MUNSMPSProductRscWeekly) ps.getUNS_MPSProductRsc_Weekly();
		
		setActualProduced(getActualProduced().add(pd.getMovementQty()));
		BigDecimal actualProducedMT = 
				pd.getMovementQty().multiply(pd.getM_Product().getWeight()).multiply(BigDecimal.valueOf(0.001));
		saveEx();
		//if (!save())
		//	throw new AdempiereException("Failed when updating MPS based on production detail.");
		
		// Update the MPS Product Resource.
		MUNSMPSProductResource mpsRsc = (MUNSMPSProductResource) getUNS_MPSProduct_Resource();
		
		mpsRsc.setActualProduced(mpsRsc.getActualProduced().add(pd.getMovementQty()));
		//mpsRsc.setActualProduced(mpsRsc.getActualProducedMT().add(actualProducedMT));
		mpsRsc.saveEx();
		//if (!mpsRsc.save())
		//	throw new AdempiereException("Failed when updating MPS based on production detail.");
		
		// Update the MPS Product.
		MUNSMPSProduct mpsProduct = (MUNSMPSProduct) mpsRsc.getUNS_MPSProduct();
		
		mpsProduct.setActualProduced(mpsProduct.getActualProduced().add(pd.getMovementQty()));
		mpsProduct.setActualProducedMT(mpsProduct.getActualProducedMT().add(actualProducedMT));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(pd.getUNS_Production().getMovementDate().getTime());
		int month = cal.get(Calendar.MONTH);
		mpsProduct.addActualManufactured(month, pd.getMovementQty());
		mpsProduct.addActualManufacturedMT(month, pd.getMovementQty());
		
		if (mpsProduct.getWeekToBeUpdated() == 0 || mpsProduct.getWeekToBeUpdated() > getWeekNo())
			mpsProduct.setWeekToBeUpdated(getWeekNo());
		mpsProduct.saveEx();
		//if (!mpsProduct.save())
		//	throw new AdempiereException("Failed when updating MPS based on production detail.");
		
		// Update the MPS Product Weekly.
		X_UNS_MPSProduct_Weekly mpsProductWeekly = 
				MUNSMPSProductWeekly.get(ctx, mpsProduct, getWeekNo(), trxName);

		if (mpsProductWeekly == null || mpsProductWeekly.get_ID() == 0)
			throw new AdempiereException("MPS is unsync.");
		
		mpsProductWeekly.setActualProduced(mpsProductWeekly.getActualProduced().add(pd.getMovementQty()));

		mpsProductWeekly.saveEx();
		//if (!mpsProductWeekly.save())
		//	throw new AdempiereException("Failed when updating MPS based on production detail.");

		// Update MPS Header.
		MUNSMPSHeader mpsHeader = (MUNSMPSHeader) mpsProduct.getUNS_MPSHeader();

		if (!mpsHeader.isSyncDatabase())
		{
			mpsHeader.setIsSyncDatabase(true);
			mpsHeader.saveEx();
			//if (!mpsHeader.save())
			//	throw new AdempiereException("Failed when updating MPS based on production detail.");
		}
		
		return true;
	}
	
	public void allocateToMonthlyMPSProduct(
			MUNSMPSProduct mpsProduct, BigDecimal actualScheduled, BigDecimal actualScheduledMT) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getStartDate().getTime());
		int monthOfStartDate = cal.get(Calendar.MONTH);
		
		cal.setTimeInMillis(getEndDate().getTime());
		int monthOfEndDate = cal.get(Calendar.MONTH);
		
		if (monthOfStartDate == monthOfEndDate)
		{
			mpsProduct.addActualScheduled(monthOfStartDate, actualScheduled);
			mpsProduct.addActualScheduledMT(monthOfStartDate, actualScheduledMT);
			return;
		}
		//else
		
			
		MUNSResourceInOut rio = 
				((MUNSResource) getUNS_MPSProduct_Resource().getUNS_Resource())
				.getResourceOutput(mpsProduct.getM_Product_ID());
		BigDecimal prevMonthProductionHours = Env.ZERO;
		BigDecimal nextMonthProductionHours = Env.ZERO;
		cal.setTimeInMillis(getStartDate().getTime());
		
		for (int i=1; i <= 7; i++)
		{
			int monthTmp = cal.get(Calendar.MONTH);
			
			if(monthTmp == monthOfStartDate)
				prevMonthProductionHours.add((BigDecimal)rio.get_Value("Day"+i+"ProductionHours"));
			else 
				nextMonthProductionHours.add((BigDecimal)rio.get_Value("Day"+i+"ProductionHours"));
			
			cal.add(Calendar.DATE, 1);
		}
		BigDecimal totalProductionHours = prevMonthProductionHours.add(nextMonthProductionHours);
		BigDecimal prevMonthPortion = 
				prevMonthProductionHours.divide(totalProductionHours, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal nextMonthPortion = 
				nextMonthProductionHours.divide(totalProductionHours, 10, BigDecimal.ROUND_HALF_UP);
		
		mpsProduct.addActualScheduled(monthOfStartDate, actualScheduled.multiply(prevMonthPortion));
		mpsProduct.addActualScheduledMT(monthOfStartDate, actualScheduledMT.multiply(prevMonthPortion));
		
		mpsProduct.addActualScheduled(monthOfEndDate, actualScheduled.multiply(nextMonthPortion));
		mpsProduct.addActualScheduledMT(monthOfEndDate, actualScheduledMT.multiply(nextMonthPortion));
	}
}
