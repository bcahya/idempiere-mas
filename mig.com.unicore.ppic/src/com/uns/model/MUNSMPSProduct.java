/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMPSProduct extends X_UNS_MPSProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2151829031522527460L;
	
	//lines product resource
	private MUNSMPSProductResource[] m_LinesProductRsc = null;
	//lines product weekly
	private MUNSMPSProductWeekly[] m_linesProductWeekly = null;
	private MUNSMPSProductWeekly[] m_linesProductDayly  = null;
	private MUNSMPSHeader m_Parent = null;

	public static final String[] NUMBERTYPE_COLUMNS = new String[] {
		COLUMNNAME_ActualProduced,
		COLUMNNAME_ActualScheduledMT,
		COLUMNNAME_ActualScheduledUOM,
		COLUMNNAME_ActualToOrderJanMT,
		COLUMNNAME_ActualToOrderJanUOM,
		COLUMNNAME_ActualToOrderFebMT,
		COLUMNNAME_ActualToOrderFebUOM,
		COLUMNNAME_ActualToOrderMarMT,
		COLUMNNAME_ActualToOrderMarUOM,
		COLUMNNAME_ActualToOrderAprMT,
		COLUMNNAME_ActualToOrderAprUOM,
		COLUMNNAME_ActualToOrderMayMT,
		COLUMNNAME_ActualToOrderMayUOM,
		COLUMNNAME_ActualToOrderJunMT,
		COLUMNNAME_ActualToOrderJunUOM,
		COLUMNNAME_ActualToOrderJulMT,
		COLUMNNAME_ActualToOrderJulUOM,
		COLUMNNAME_ActualToOrderAugMT,
		COLUMNNAME_ActualToOrderAugUOM,
		COLUMNNAME_ActualToOrderSepMT,
		COLUMNNAME_ActualToOrderSepUOM,
		COLUMNNAME_ActualToOrderOctMT,
		COLUMNNAME_ActualToOrderOctUOM,
		COLUMNNAME_ActualToOrderNovMT,
		COLUMNNAME_ActualToOrderNovUOM,
		COLUMNNAME_ActualToOrderDecMT,
		COLUMNNAME_ActualToOrderDecUOM,
		COLUMNNAME_InitialProjectedStock_OnHand,
		COLUMNNAME_QtyMT_Jan,
		COLUMNNAME_QtyUOM_Jan,
		COLUMNNAME_QtyMT_Feb,
		COLUMNNAME_QtyUOM_Feb,
		COLUMNNAME_QtyMT_Mar,
		COLUMNNAME_QtyUOM_Mar,
		COLUMNNAME_QtyMT_Apr,
		COLUMNNAME_QtyUOM_Apr,
		COLUMNNAME_QtyMT_May,
		COLUMNNAME_QtyUOM_May,
		COLUMNNAME_QtyMT_Jun,
		COLUMNNAME_QtyUOM_Jun,
		COLUMNNAME_QtyMT_Jul,
		COLUMNNAME_QtyUOM_Jul,
		COLUMNNAME_QtyMT_Agt,
		COLUMNNAME_QtyUOM_Agt,
		COLUMNNAME_QtyMT_Sep,
		COLUMNNAME_QtyUOM_Sep,
		COLUMNNAME_QtyMT_Oct,
		COLUMNNAME_QtyUOM_Oct,
		COLUMNNAME_QtyMT_Nov,
		COLUMNNAME_QtyUOM_Nov,
		COLUMNNAME_QtyMT_Dec,
		COLUMNNAME_QtyUOM_Dec,
		COLUMNNAME_QtyOnHand,
		COLUMNNAME_TotalActualToOrderMT,
		COLUMNNAME_TotalActualToOrderUOM,
		COLUMNNAME_TotalActualToStockMT,
		COLUMNNAME_TotalActualToStockUOM
	};
	
	
	/**
	 * 
	 */
	public void rescaleAllNumbers() {
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, 4, BigDecimal.ROUND_HALF_UP, false);
	}
	
	/**
	 * @param ctx
	 * @param UNS_MPSProduct_ID
	 * @param trxName
	 */
	public MUNSMPSProduct(Properties ctx, int UNS_MPSProduct_ID, String trxName) {
		super(ctx, UNS_MPSProduct_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMPSProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Get or create MPS Product of a MPS Header.
	 * @param ctx
	 * @param UNS_MPSHeader_ID
	 * @param M_Product_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMPSProduct getCreate(Properties ctx, int UNS_MPSHeader_ID, int M_Product_ID, String trxName) 
	{
		MUNSMPSProduct mpsProduct = getNoCreate(ctx, UNS_MPSHeader_ID, M_Product_ID, trxName);
		if (mpsProduct != null)
			return mpsProduct;
		
		// Create new one.
		mpsProduct = new MUNSMPSProduct(ctx, 0, trxName);
		mpsProduct.setUNS_MPSHeader_ID(UNS_MPSHeader_ID);
		mpsProduct.setM_Product_ID(M_Product_ID);
		
		return mpsProduct;
	}
	
	/**
	 * Get or create MPS Product of a MPS Header.
	 * @param ctx
	 * @param UNS_MPSHeader_ID
	 * @param M_Product_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMPSProduct getNoCreate(Properties ctx, int UNS_MPSHeader_ID, int M_Product_ID, String trxName) 
	{
		MUNSMPSProduct mpsProduct = 
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "UNS_MPSHeader_ID=? AND M_Product_ID=?", 
						  trxName)
				.setParameters(UNS_MPSHeader_ID, M_Product_ID)
				.first();
		
		return mpsProduct;
	}
	
	/**
	 * Get MPSProduct for a product from any possible MPS Header which the given date of theDate is between 
	 * MPS Header period start and period end date.
	 *  
	 * @param ctx
	 * @param theDate
	 * @param M_Product_ID
	 * @param trxName
	 * @return an array of MPSProduct from any different MPS Header which the given date of theDate is between 
	 * MPS Header period start and period end date. 
	 */
	public static MUNSMPSProduct[] get(Properties ctx, Timestamp theDate, int M_Product_ID, String trxName) 
	{
		List<MUNSMPSProduct> mpsProductList = 
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "AND M_Product_ID=? AND UNS_MPSHeader_ID IN (" +
						  "		SELECT header.UNS_MPSHeader_ID FROM UNS_MPSHeader header " +
						  "		INNER JOIN C_Perod periodStart ON header.PeriodStart_ID=periodStart.C_Period_ID " +
						  "		INNER JOIN C_Perod periodEnd ON header.PeriodEnd_ID=periodEnd.C_Period_ID " +
						  "		WHERE ? BETWEEN periodStart.StartDate AND periodEnd.EndDate)", 
						  trxName)
				.setParameters(M_Product_ID, theDate)
				.list();
		
		MUNSMPSProduct[] mpsProducts = new MUNSMPSProduct[mpsProductList.size()];
		mpsProductList.toArray(mpsProducts);
		
		return mpsProducts;
	}
	
	/**
	 * get Dayly of MPS
	 * @param requery
	 * @return MUNSMPSProductWeekly[]
	 */
	public MUNSMPSProductWeekly[] getLinesDayly(boolean requery)
	{
		if (m_linesProductDayly != null && !requery)
		{
			set_TrxName(m_linesProductDayly, get_TrxName());
			return m_linesProductDayly;
		}
		
		final String whereClause = X_UNS_MPSProduct_Weekly.COLUMNNAME_UNS_MPSProduct_ID 
				+ "=? AND MPSDate IS NOT NULL";
		List<X_UNS_MPSProduct_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProduct_Weekly.Table_Name, 
				whereClause, get_TrxName())
				.setParameters(getUNS_MPSProduct_ID())
				.setOrderBy(
						X_UNS_MPSProduct_Weekly.COLUMNNAME_MPSDate)
						.list();
		
		m_linesProductDayly = new MUNSMPSProductWeekly[list.size()];
		list.toArray(m_linesProductDayly);
		
		return m_linesProductDayly;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		return deleteLines();
	}
	
	private boolean deleteLines()
	{
		for (MUNSMPSProductResource mpsProductRsc : getLineProductRsc())
		{
			try{
				mpsProductRsc.delete(true);
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		}
		for (MUNSMPSProductWeekly weeklyMPS : getLinesWeekly())
		{
			try{
				weeklyMPS.delete(true);
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		}
		for (MUNSMPSProductWeekly dailyMps : getLinesDayly())
		{
			try{
				return dailyMps.delete(true);
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		}
		return true;
	}
	
	/**
	 * get Dayly of MPS
	 * @return MUNSMPSProductWeekly[]
	 */
	public MUNSMPSProductWeekly[] getLinesDayly()
	{
		return getLinesDayly(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return lines Product Resource
	 */
	public MUNSMPSProductResource[] getLinesProductRsc(boolean requery)
	{
		if (null != m_LinesProductRsc && !requery)
		{
			set_TrxName(m_LinesProductRsc, get_TrxName());
			return m_LinesProductRsc;
		}
		
		final String whereClause = X_UNS_MPSProduct_Resource.COLUMNNAME_UNS_MPSProduct_ID + " =?";
		List<X_UNS_MPSProduct_Resource> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProduct_Resource.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSProduct_ID())
				.list();
		
		m_LinesProductRsc = new MUNSMPSProductResource[list.size()];
		list.toArray(m_LinesProductRsc);
		
		return m_LinesProductRsc;
	}
	
	/**
	 * Order by week no.
	 * @param requery
	 * @return
	 */
	public MUNSMPSProductWeekly[] getLinesWeekly(boolean requery)
	{
		if (null != m_linesProductWeekly
				&& !requery)
		{
			set_TrxName(m_linesProductWeekly, get_TrxName());
			return m_linesProductWeekly;
		}
		
		final String whereClause = X_UNS_MPSProduct_Weekly.COLUMNNAME_UNS_MPSProduct_ID 
				+ " =? AND weekly_ID IS null AND StartDate IS NOT NULL AND EndDate IS NOT NULL";
		List<X_UNS_MPSProduct_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProduct_Weekly.Table_Name
				, whereClause, get_TrxName()).setParameters(getUNS_MPSProduct_ID())
				.setOrderBy(X_UNS_MPSProduct_Weekly.COLUMNNAME_WeekNo)
				.list();
		
		m_linesProductWeekly = new MUNSMPSProductWeekly[list.size()];
		list.toArray(m_linesProductWeekly);
		
		return m_linesProductWeekly;
	}
	

	/**
	 * 
	 * @return
	 */
	public MUNSMPSProductWeekly[] getLinesWeekly()
	{
		return getLinesWeekly(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSMPSProductWeekly[] getLinesFromWeek(int weekNo)
	{
		final String whereClause = X_UNS_MPSProduct_Weekly.COLUMNNAME_UNS_MPSProduct_ID 
				+ " =? AND weekly_ID IS null AND StartDate IS NOT NULL AND EndDate IS NOT NULL"
				+ " AND WeekNo >=?";
		List<X_UNS_MPSProduct_Weekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProduct_Weekly.Table_Name
				, whereClause, get_TrxName()).setParameters(getUNS_MPSProduct_ID())
				.setOrderBy(X_UNS_MPSProduct_Weekly.COLUMNNAME_WeekNo)
				.list();
		
		MUNSMPSProductWeekly[] linesProductWeekly = new MUNSMPSProductWeekly[list.size()];
		list.toArray(linesProductWeekly);
		
		return linesProductWeekly;
	}
	
	public MUNSMPSHeader getParent()
	{
		if (m_Parent == null)
			m_Parent = new MUNSMPSHeader(getCtx(), getUNS_MPSHeader_ID(), get_TrxName());
		return m_Parent;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSMPSProductResource[] getLineProductRsc()
	{
		return getLinesProductRsc(false);
	}
	
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyUOM(int month)
	{
		switch(month) {
		case 0 :
			return getQtyUOM_Jan();
		case 1 :
			return getQtyUOM_Feb();
		case 2 :
			return getQtyUOM_Mar();
		case 3 :
			return getQtyUOM_Apr();
		case 4 :
			return getQtyUOM_May();
		case 5 :
			return getQtyUOM_Jun();
		case 6 :
			return getQtyUOM_Jul();
		case 7 :
			return getQtyUOM_Agt();
		case 8 :
			return getQtyUOM_Sep();
		case 9 :
			return getQtyUOM_Oct();
		case 10 :
			return getQtyUOM_Nov();
		case 11 :
			return getQtyUOM_Dec();
			default :
				return BigDecimal.ZERO;
		}
	}

	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getQtyMT(int month)
	{
		switch(month) {
		case 0 :
			return getQtyMT_Jan();
		case 1 :
			return getQtyMT_Feb();
		case 2 :
			return getQtyMT_Mar();
		case 3 :
			return getQtyMT_Apr();
		case 4 :
			return getQtyMT_May();
		case 5 :
			return getQtyMT_Jun();
		case 6 :
			return getQtyMT_Jul();
		case 7 :
			return getQtyMT_Agt();
		case 8 :
			return getQtyMT_Sep();
		case 9 :
			return getQtyMT_Oct();
		case 10 :
			return getQtyMT_Nov();
		case 11 :
			return getQtyMT_Dec();
			default :
				return BigDecimal.ZERO;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param qty
	 */
	public void setQtyMT(int month, BigDecimal qty)
	{
		switch(month) {
		case 0 :
			setQtyMT_Jan(qty);
		case 1 :
			setQtyMT_Feb(qty);
		case 2 :
			setQtyMT_Mar(qty);
		case 3 :
			setQtyMT_Apr(qty);
		case 4 :
			setQtyMT_May(qty);
		case 5 :
			setQtyMT_Jun(qty);
		case 6 :
			setQtyMT_Jul(qty);
		case 7 :
			setQtyMT_Agt(qty);
		case 8 :
			setQtyMT_Sep(qty);
		case 9 :
			setQtyMT_Oct(qty);
		case 10 :
			setQtyMT_Nov(qty);
		case 11 :
			setQtyMT_Dec(qty);
		}
	}

	/**
	 * 
	 * @param month
	 * @param qty
	 */
	public void setQtyUOM(int month, BigDecimal qty)
	{
		switch(month) {
		case 0 :
			setQtyUOM_Jan(qty);
		case 1 :
			setQtyUOM_Feb(qty);
		case 2 :
			setQtyUOM_Mar(qty);
		case 3 :
			setQtyUOM_Apr(qty);
		case 4 :
			setQtyUOM_May(qty);
		case 5 :
			setQtyUOM_Jun(qty);
		case 6 :
			setQtyUOM_Jul(qty);
		case 7 :
			setQtyUOM_Agt(qty);
		case 8 :
			setQtyUOM_Sep(qty);
		case 9 :
			setQtyUOM_Oct(qty);
		case 10 :
			setQtyUOM_Nov(qty);
		case 11 :
			setQtyUOM_Dec(qty);
		}
	}

	
	/**
	 * 
	 * @param yearlyForecast
	 */
	public void copyValues(MUNSForecastDate yearlyForecast)
	{
		setM_Product_ID(yearlyForecast.getM_Product_ID());
		setC_UOM_ID(yearlyForecast.getC_UOM_ID());
		//setTotalActualToStockUOM(yearlyForecast.getQtyUom());
		//setTotalActualToStockMT(yearlyForecast.getQtyMT());
		setTotalActualToStockUOM(yearlyForecast.getDecidedQty());
		setTotalActualToStockMT(yearlyForecast.getDecidedQtyMT());
		//setQtyUOM_Jan(yearlyForecast.get)
		String sql = "SELECT period.PeriodNo, fd.DecidedQty, fd.DecidedQtyMT FROM UNS_Forecast_Date fd " +
				"INNER JOIN C_Period period ON fd.C_Period_ID=period.C_Period_ID " +
				"WHERE fd.Yearly_ID=? AND fd.Monthly_ID IS NULL AND fd.Weekly_ID IS NULL AND M_Product_ID=?";
		PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
		try {
			stmt.setInt(1, yearlyForecast.get_ID());
			stmt.setInt(2, yearlyForecast.getM_Product_ID());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int month = rs.getInt(1) - 1;
				setQtyUOM(month, rs.getBigDecimal(2) == null? Env.ZERO : rs.getBigDecimal(2));
				setQtyMT(month, rs.getBigDecimal(3) == null? Env.ZERO : rs.getBigDecimal(3));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw new AdempiereException ("Failed when copying forecast monthly target quantity.");
		}
	}
	
	/**
	 * 
	 * @param yearlyForecast
	 */
	public void calculate(MUNSForecastDate yearlyForecast)
	{
		setTotalActualToStockMT(getTotalActualToStockMT().add(yearlyForecast.getDecidedQty()));
		setTotalActualToStockUOM(getTotalActualToStockUOM().add(yearlyForecast.getDecidedQtyMT()));
	}
	
	/**
	 * 
	 * @return Hashtable of MPSProductWeekly
	 */
	public Hashtable<Integer, MUNSMPSProductWeekly> getMapOfWeeklyMPSproduct()
	{
		Hashtable<Integer, MUNSMPSProductWeekly> mapOfWeeklyMPSProduct
								= new Hashtable<Integer, MUNSMPSProductWeekly>();
		for (MUNSMPSProductWeekly weeklyMPS : getLinesWeekly(true))
		{
			mapOfWeeklyMPSProduct.put(weeklyMPS.getWeekNo(), weeklyMPS);
		}
		
		return mapOfWeeklyMPSProduct;
	}
	
	
	/**
	 * Key this hashtable (Day Of Year)
	 * @return Hashtable of MPSPRoductDaily
	 */
	public Hashtable<Integer, MUNSMPSProductWeekly> getMapOfDailyMPSProduct()
	{
		Hashtable<Integer, MUNSMPSProductWeekly> mapMPSPRoductDaily = 
						new Hashtable<Integer, MUNSMPSProductWeekly>();
		
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		for (MUNSMPSProductWeekly dailyMPS : getLinesDayly(true))
		{
			cal.setTimeInMillis(dailyMPS.getMPSDate().getTime());
			mapMPSPRoductDaily.put(cal.get(Calendar.DAY_OF_YEAR), dailyMPS);
		}
		
		return mapMPSPRoductDaily;
	}
	
	/**
	 * 
	 * @param UNS_Resource_ID
	 * @return
	 */
	public MUNSMPSProductResource getMPSProductRscOf(int UNS_Resource_ID)
	{
		for (MUNSMPSProductResource mpsRsc : getLineProductRsc())
		{
			if(mpsRsc.getUNS_Resource_ID() == UNS_Resource_ID)
				return mpsRsc;
		}
		return null;
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getActualToOrderUOM(int month)
	{
		switch (month) {
		case 0 :
			return getActualToOrderJanUOM();
		case 1 :
			return getActualToOrderFebUOM();
		case 2 :
			return getActualToOrderMarUOM();
		case 3 :
			return getActualToOrderAprUOM();
		case 4 :
			return getActualToOrderMayUOM();
		case 5 :
			return getActualToOrderJunUOM();
		case 6 :
			return getActualToOrderJulUOM();
		case 7 :
			return getActualToOrderAugUOM();
		case 8 :
			return getActualToOrderSepUOM();
		case 9 :
			return getActualToOrderOctUOM();
		case 10 :
			return getActualToOrderNovUOM();
		case 11 : 
			return getActualToOrderDecUOM();
			default :
				return BigDecimal.ZERO;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getActualToOrderMT(int month)
	{
		switch (month) {
		case 0 :
			return getActualToOrderJanMT();
		case 1 :
			return getActualToOrderFebMT();
		case 2 :
			return getActualToOrderMarMT();
		case 3 :
			return getActualToOrderAprMT();
		case 4 :
			return getActualToOrderMayMT();
		case 5 :
			return getActualToOrderJunMT();
		case 6 :
			return getActualToOrderJulMT();
		case 7 :
			return getActualToOrderAugMT();
		case 8 :
			return getActualToOrderSepMT();
		case 9 :
			return getActualToOrderOctMT();
		case 10 :
			return getActualToOrderNovMT();
		case 11 : 
			return getActualToOrderDecMT();
			default :
				return BigDecimal.ZERO;
		}
	}
	
	
	/**
	 * 
	 * @param month
	 * @param qty
	 */
	public void setActualToOrderMT(int month, BigDecimal qty)
	{
		switch (month) {
		case 0 :
			setActualToOrderJanMT(qty);
			break;
		case 1 :
			setActualToOrderFebMT(qty);
			break;
		case 2 :
			setActualToOrderMarMT(qty);
			break;
		case 3 :
			setActualToOrderAprMT(qty);
			break;
		case 4 :
			setActualToOrderMayMT(qty);
			break;
		case 5 :
			setActualToOrderJunMT(qty);
			break;
		case 6 :
			setActualToOrderJulMT(qty);
			break;
		case 7 :
			setActualToOrderAugMT(qty);
			break;
		case 8 :
			setActualToOrderSepMT(qty);
			break;
		case 9 :
			setActualToOrderOctMT(qty);
			break;
		case 10 :
			setActualToOrderNovMT(qty);
			break;
		case 11 :
			setActualToOrderDecMT(qty);
			break;
		}
	}

	/**
	 * 
	 * @param month
	 * @param qty
	 */
	public void setActualToOrderUOM(int month, BigDecimal qty)
	{
		switch (month) {
		case 0 :
			setActualToOrderJanUOM(qty);
			break;
		case 1 :
			setActualToOrderFebUOM(qty);
			break;
		case 2 :
			setActualToOrderMarUOM(qty);
			break;
		case 3 :
			setActualToOrderAprUOM(qty);
			break;
		case 4 :
			setActualToOrderMayUOM(qty);
			break;
		case 5 :
			setActualToOrderJunUOM(qty);
			break;
		case 6 :
			setActualToOrderJulUOM(qty);
			break;
		case 7 :
			setActualToOrderAugUOM(qty);
			break;
		case 8 :
			setActualToOrderSepUOM(qty);
			break;
		case 9 :
			setActualToOrderOctUOM(qty);
			break;
		case 10 :
			setActualToOrderNovUOM(qty);
			break;
		case 11 :
			setActualToOrderDecUOM(qty);
			break;
		}
	}
	
	/**
	 * 
	 */
	void settotalActual()
	{
		double totalActualStockMT = 0.0;
		double totalActualStocUOM = 0.0;
		double totalActualToOrderUom = 0.0;
		double totalActualToOrderMT = 0.0;
		for(int i=0; i<12; i++)
		{
			totalActualToOrderMT += getActualToOrderMT(i).doubleValue();
			totalActualToOrderUom += getActualToOrderUOM(i).doubleValue();
			totalActualStockMT += getQtyMT(i).doubleValue();
			totalActualStocUOM += getQtyUOM(i).doubleValue();
		}
		setTotalActualToOrderMT(new BigDecimal(totalActualToOrderMT));
		setTotalActualToOrderUOM(new BigDecimal(totalActualToOrderUom));
		setTotalActualToStockMT(new BigDecimal(totalActualStockMT));
		setTotalActualToStockUOM(new BigDecimal(totalActualStocUOM));
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//settotalActual();
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param cal
	 *
	private void generateMPSProductPeriodic(Calendar cal)
	{
		Hashtable<String, MUNSMPSProductWeekly> mapOfMPSProductWeekly =
				new Hashtable<String, MUNSMPSProductWeekly>();
		
		long endLoop = getParent().getEndDate().getTime();
		BigDecimal z = BigDecimal.ZERO;
		
		while(cal.getTimeInMillis() <= endLoop)
		{
			int C_PeriodStart_ID = getParent().getPeriodStart_ID();
			int C_PeriodEnd_ID = getParent().getPeriodEnd_ID();
			
			int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
			if(weekNo ==1 && cal.get(Calendar.DAY_OF_YEAR) > 350)
				weekNo = 53;
			MUNSMPSProductWeekly mpsProductDaily =
					new MUNSMPSProductWeekly(getCtx(), 0, get_TrxName());
			mpsProductDaily.setAcc_ATP(z);
			mpsProductDaily.setActualToOrderMT(z);
			mpsProductDaily.setActualToOrderUOM(z);
			mpsProductDaily.setActualToStockMT(z);
			mpsProductDaily.setActualToStockUOM(z);
			mpsProductDaily.setAD_Org_ID(getAD_Org_ID());
			mpsProductDaily.setATP(z);
			mpsProductDaily.setIncubationDays(z);
			mpsProductDaily.setEndOfIncubation(null);
			mpsProductDaily.setMPSDate(new Timestamp(cal.getTimeInMillis()));
			mpsProductDaily.setProjectedActualBalance(z);
			mpsProductDaily.setQtyDelivered(z);
			mpsProductDaily.setWeekNo(weekNo);
			mpsProductDaily.setUNS_MPSProduct_ID(get_ID());
			mpsProductDaily.setStock(z);
			mpsProductDaily.setQtyUom(z);
			mpsProductDaily.save();
			MUNSMPSProductWeekly weeklyMPS = mapOfMPSProductWeekly.get(
					"" + weekNo + "_" + getM_Product_ID());
			if(null == weeklyMPS)
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
					weekcal.setTimeInMillis(getParent().getStartDate().getTime());
				else
					weekcal.set(Calendar.DAY_OF_WEEK, 1);
				
				Timestamp startDateOfWeek = new Timestamp(weekcal.getTimeInMillis());
				
				if(thisWeekInMonth == weekcal.getActualMaximum(Calendar.WEEK_OF_MONTH)
						&& C_Period_ID == C_PeriodEnd_ID)
					weekcal.setTimeInMillis(getParent().getEndDate().getTime());
				else
					weekcal.set(Calendar.DAY_OF_WEEK, 7);
				
				Timestamp endDateOfWeek = new Timestamp(weekcal.getTimeInMillis());
					
				weeklyMPS = new MUNSMPSProductWeekly(getCtx(), 0, get_TrxName());
				weeklyMPS.setUNS_MPSProduct_ID(get_ID());
				weeklyMPS.setAD_Org_ID(getAD_Org_ID());
				weeklyMPS.setEndDate(endDateOfWeek);
				weeklyMPS.setStartDate(startDateOfWeek);
				weeklyMPS.setMPSDate(null);
				weeklyMPS.setWeekNo(weekNo);
				weeklyMPS.calculate(mpsProductDaily);
				weeklyMPS.save();
				mapOfMPSProductWeekly.put("" + weekNo + "_" + getM_Product_ID(), weeklyMPS);
			}
			cal.add(Calendar.DATE, 1);
			
		}
	}
	
	private void moreAction()
	{
		I_C_Period periodStart = getParent().getPeriodStart();
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		cal.setTimeInMillis(periodStart.getStartDate().getTime());
		generateMPSProductPeriodic(cal);
	}*/
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//if(newRecord && success && !isGenerate())
			//moreAction();
		
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 * @param month
	 * @param newSchedule
	 */
	public void addActualScheduled(int month, BigDecimal newSchedule)
	{
		switch(month) {
		case 0 :
			if (null == getActualScheduledJanUOM())
				setActualScheduledJanUOM(newSchedule);
			else
				setActualScheduledJanUOM(getActualScheduledJanUOM().add(newSchedule));
		case 1 :
			if (null == getActualScheduledFebUOM())
				setActualScheduledFebUOM(newSchedule);
			else
				setActualScheduledFebUOM(getActualScheduledFebUOM().add(newSchedule));
		case 2 :
			if (null == getActualScheduledMarUOM())
				setActualScheduledMarUOM(newSchedule);
			else
				setActualScheduledMarUOM(getActualScheduledMarUOM().add(newSchedule));
		case 3 :
			if (null == getActualScheduledAprUOM())
				setActualScheduledAprUOM(newSchedule);
			else
				setActualScheduledAprUOM(getActualScheduledAprUOM().add(newSchedule));
		case 4 :
			if (null == getActualScheduledMayUOM())
				setActualScheduledMayUOM(newSchedule);
			else
				setActualScheduledMayUOM(getActualScheduledMayUOM().add(newSchedule));
		case 5 :
			if (null == getActualScheduledJunUOM())
				setActualScheduledJunUOM(newSchedule);
			else
				setActualScheduledJunUOM(getActualScheduledJunUOM().add(newSchedule));
		case 6 :
			if (null == getActualScheduledJulUOM())
				setActualScheduledJulUOM(newSchedule);
			else
				setActualScheduledJulUOM(getActualScheduledJulUOM().add(newSchedule));
		case 7 :
			if (null == getActualScheduledAugUOM())
				setActualScheduledAugUOM(newSchedule);
			else
				setActualScheduledAugUOM(getActualScheduledAugUOM().add(newSchedule));
		case 8 :
			if (null == getActualScheduledSepUOM())
				setActualScheduledSepUOM(newSchedule);
			else
				setActualScheduledSepUOM(getActualScheduledSepUOM().add(newSchedule));
		case 9 :
			if (null == getActualScheduledOctUOM())
				setActualScheduledOctUOM(newSchedule);
			else
				setActualScheduledOctUOM(getActualScheduledOctUOM().add(newSchedule));
		case 10 :
			if (null == getActualScheduledNovUOM())
				setActualScheduledNovUOM(newSchedule);
			else
				setActualScheduledNovUOM(getActualScheduledNovUOM().add(newSchedule));
		case 11 :
			if (null == getActualScheduledDecUOM())
				setActualScheduledDecUOM(newSchedule);
			else
				setActualScheduledDecUOM(getActualScheduledDecUOM().add(newSchedule));
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param newSchedule
	 */
	public void addActualScheduledMT(int month, BigDecimal newSchedule)
	{
		switch(month) {
		case 0 :
			if (null == getActualScheduledJanMT())
				setActualScheduledJanMT(newSchedule);
			else
				setActualScheduledJanMT(getActualScheduledJanMT().add(newSchedule));
		case 1 :
			if (null == getActualScheduledFebMT())
				setActualScheduledFebMT(newSchedule);
			else
				setActualScheduledFebMT(getActualScheduledFebMT().add(newSchedule));
		case 2 :
			if (null == getActualScheduledMarMT())
				setActualScheduledMarMT(newSchedule);
			else
				setActualScheduledMarMT(getActualScheduledMarMT().add(newSchedule));
		case 3 :
			if (null == getActualScheduledAprMT())
				setActualScheduledAprMT(newSchedule);
			else
				setActualScheduledAprMT(getActualScheduledAprMT().add(newSchedule));
		case 4 :
			if (null == getActualScheduledMayMT())
				setActualScheduledMayMT(newSchedule);
			else
				setActualScheduledMayMT(getActualScheduledMayMT().add(newSchedule));
		case 5 :
			if (null == getActualScheduledJunMT())
				setActualScheduledJunMT(newSchedule);
			else
				setActualScheduledJunMT(getActualScheduledJunMT().add(newSchedule));
		case 6 :
			if (null == getActualScheduledJulMT())
				setActualScheduledJulMT(newSchedule);
			else
				setActualScheduledJulMT(getActualScheduledJulMT().add(newSchedule));
		case 7 :
			if (null == getActualScheduledAugMT())
				setActualScheduledAugMT(newSchedule);
			else
				setActualScheduledAugMT(getActualScheduledAugMT().add(newSchedule));
		case 8 :
			if (null == getActualScheduledSepMT())
				setActualScheduledSepMT(newSchedule);
			else
				setActualScheduledSepMT(getActualScheduledSepMT().add(newSchedule));
		case 9 :
			if (null == getActualScheduledOctMT())
				setActualScheduledOctMT(newSchedule);
			else
				setActualScheduledOctMT(getActualScheduledOctMT().add(newSchedule));
		case 10 :
			if (null == getActualScheduledNovMT())
				setActualScheduledNovMT(newSchedule);
			else
				setActualScheduledNovMT(getActualScheduledNovMT().add(newSchedule));
		case 11 :
			if (null == getActualScheduledDecMT())
				setActualScheduledDecMT(newSchedule);
			else
				setActualScheduledDecMT(getActualScheduledDecMT().add(newSchedule));
		}
	}

//======== settings to actual manufactured.
	
	/**
	 * 
	 * @param month
	 * @param newSchedule
	 */
	public void addActualManufactured(int month, BigDecimal newManufacture)
	{
		switch(month) {
		case 0 :
			if (null == getActualManufacturedJanUOM())
				setActualManufacturedJanUOM(newManufacture);
			else
				setActualManufacturedJanUOM(getActualManufacturedJanUOM().add(newManufacture));
		case 1 :
			if (null == getActualManufacturedFebUOM())
				setActualManufacturedFebUOM(newManufacture);
			else
				setActualManufacturedFebUOM(getActualManufacturedFebUOM().add(newManufacture));
		case 2 :
			if (null == getActualManufacturedMarUOM())
				setActualManufacturedMarUOM(newManufacture);
			else
				setActualManufacturedMarUOM(getActualManufacturedMarUOM().add(newManufacture));
		case 3 :
			if (null == getActualManufacturedAprUOM())
				setActualManufacturedAprUOM(newManufacture);
			else
				setActualManufacturedAprUOM(getActualManufacturedAprUOM().add(newManufacture));
		case 4 :
			if (null == getActualManufacturedMayUOM())
				setActualManufacturedMayUOM(newManufacture);
			else
				setActualManufacturedMayUOM(getActualManufacturedMayUOM().add(newManufacture));
		case 5 :
			if (null == getActualManufacturedJunUOM())
				setActualManufacturedJunUOM(newManufacture);
			else
				setActualManufacturedJunUOM(getActualManufacturedJunUOM().add(newManufacture));
		case 6 :
			if (null == getActualManufacturedJulUOM())
				setActualManufacturedJulUOM(newManufacture);
			else
				setActualManufacturedJulUOM(getActualManufacturedJulUOM().add(newManufacture));
		case 7 :
			if (null == getActualManufacturedAugUOM())
				setActualManufacturedAugUOM(newManufacture);
			else
				setActualManufacturedAugUOM(getActualManufacturedAugUOM().add(newManufacture));
		case 8 :
			if (null == getActualManufacturedSepUOM())
				setActualManufacturedSepUOM(newManufacture);
			else
				setActualManufacturedSepUOM(getActualManufacturedSepUOM().add(newManufacture));
		case 9 :
			if (null == getActualManufacturedOctUOM())
				setActualManufacturedOctUOM(newManufacture);
			else
				setActualManufacturedOctUOM(getActualManufacturedOctUOM().add(newManufacture));
		case 10 :
			if (null == getActualManufacturedNovUOM())
				setActualManufacturedNovUOM(newManufacture);
			else
				setActualManufacturedNovUOM(getActualManufacturedNovUOM().add(newManufacture));
		case 11 :
			if (null == getActualManufacturedDecUOM())
				setActualManufacturedDecUOM(newManufacture);
			else
				setActualManufacturedDecUOM(getActualManufacturedDecUOM().add(newManufacture));
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param newManufacture
	 */
	public void addActualManufacturedMT(int month, BigDecimal newManufacture)
	{
		switch(month) {
		case 0 :
			if (null == getActualManufacturedJanMT())
				setActualManufacturedJanMT(newManufacture);
			else
				setActualManufacturedJanMT(getActualManufacturedJanMT().add(newManufacture));
		case 1 :
			if (null == getActualManufacturedFebMT())
				setActualManufacturedFebMT(newManufacture);
			else
				setActualManufacturedFebMT(getActualManufacturedFebMT().add(newManufacture));
		case 2 :
			if (null == getActualManufacturedMarMT())
				setActualManufacturedMarMT(newManufacture);
			else
				setActualManufacturedMarMT(getActualManufacturedMarMT().add(newManufacture));
		case 3 :
			if (null == getActualManufacturedAprMT())
				setActualManufacturedAprMT(newManufacture);
			else
				setActualManufacturedAprMT(getActualManufacturedAprMT().add(newManufacture));
		case 4 :
			if (null == getActualManufacturedMayMT())
				setActualManufacturedMayMT(newManufacture);
			else
				setActualManufacturedMayMT(getActualManufacturedMayMT().add(newManufacture));
		case 5 :
			if (null == getActualManufacturedJunMT())
				setActualManufacturedJunMT(newManufacture);
			else
				setActualManufacturedJunMT(getActualManufacturedJunMT().add(newManufacture));
		case 6 :
			if (null == getActualManufacturedJulMT())
				setActualManufacturedJulMT(newManufacture);
			else
				setActualManufacturedJulMT(getActualManufacturedJulMT().add(newManufacture));
		case 7 :
			if (null == getActualManufacturedAugMT())
				setActualManufacturedAugMT(newManufacture);
			else
				setActualManufacturedAugMT(getActualManufacturedAugMT().add(newManufacture));
		case 8 :
			if (null == getActualManufacturedSepMT())
				setActualManufacturedSepMT(newManufacture);
			else
				setActualManufacturedSepMT(getActualManufacturedSepMT().add(newManufacture));
		case 9 :
			if (null == getActualManufacturedOctMT())
				setActualManufacturedOctMT(newManufacture);
			else
				setActualManufacturedOctMT(getActualManufacturedOctMT().add(newManufacture));
		case 10 :
			if (null == getActualManufacturedNovMT())
				setActualManufacturedNovMT(newManufacture);
			else
				setActualManufacturedNovMT(getActualManufacturedNovMT().add(newManufacture));
		case 11 :
			if (null == getActualManufacturedDecMT())
				setActualManufacturedDecMT(newManufacture);
			else
				setActualManufacturedDecMT(getActualManufacturedDecMT().add(newManufacture));
		}
	}
}
