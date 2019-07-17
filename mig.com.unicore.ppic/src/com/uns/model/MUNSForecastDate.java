/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MUOM;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSForecastDate extends X_UNS_Forecast_Date {
	
	private MUNSForecastDate[] m_linesMonth = null;
	private MUNSForecastDate[] m_linesWeek = null;
	private MUNSForecastDate[] m_linesDayOFMonth = null;
	private MUNSForecastDate[] m_linesDayOfWeek = null;
	private MUNSForecastDate m_Parent_Month = null;
	private MUNSForecastDate m_Parent_Week = null;
	private MUNSForecastHeader m_Parent = null;
	private MUNSForecastDate m_Parent_Day_Of_Week = null;
	private MUNSForecastDate m_Parent_Day_Of_Month = null;
	
	public static final String[] NUMBERTYPE_COLUMNS = new String[] {
		COLUMNNAME_QtyMForecast,
		COLUMNNAME_QtyMForecastMT,
		COLUMNNAME_QtyMT,
		COLUMNNAME_QtyUom,
		COLUMNNAME_DecidedQty,
		COLUMNNAME_DecidedQtyMT,
		COLUMNNAME_ExpectedCostIDR,
		COLUMNNAME_ExpectedCostSGD,
		COLUMNNAME_ExpectedCostUSD,
		COLUMNNAME_ExpectedRevenueIDR,
		COLUMNNAME_ExpectedRevenueSGD,
		COLUMNNAME_ExpectedRevenueUSD,
		COLUMNNAME_MaxCaps,
		COLUMNNAME_OptimumCaps,
		COLUMNNAME_TotalExpectedCostIDR,
		COLUMNNAME_TotalExpectedCostSGD,
		COLUMNNAME_TotalExpectedCostUSD,
		COLUMNNAME_TotalExpectedRevenueIDR,
		COLUMNNAME_TotalExpectedRevenueSGD,
		COLUMNNAME_TotalExpectedRevenueUSD
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_Forecast_Date_ID
	 * @param trxName
	 */
	public MUNSForecastDate(Properties ctx, int UNS_Forecast_Date_ID,
			String trxName) {
		super(ctx, UNS_Forecast_Date_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSForecastDate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean beforeDelete()
	{
		//return deleteLines();
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastHeader getParent()
	{
		if (m_Parent == null)
			m_Parent = new MUNSForecastHeader(
					getCtx(), getUNS_Forecast_Header_ID(), get_TrxName());
		
		return m_Parent;
	}
	
	/**
	 * 
	 * @param yearlyForecast_ID
	 * @return
	 */
	public static Hashtable<Timestamp, MUNSForecastDate> getDailyForecast(int yearlyForecast_ID, String trxName)
	{
		Hashtable<Timestamp, MUNSForecastDate> listDailyForecast = 
				new Hashtable<Timestamp, MUNSForecastDate>();
		String sql = "SELECT * FROM " + Table_Name + " fd, UNS_ForecastWeek_V fw "
				+ " WHERE fw.Yearly_ID = ? AND fd.Weekly_ID = fw.UNS_ForecastWeek_V_ID";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1,yearlyForecast_ID);
			rs = st.executeQuery();
			while (rs.next()){
				MUNSForecastDate fd = new MUNSForecastDate(Env.getCtx(), rs, trxName);
				listDailyForecast.put(fd.getForcastedDate(), fd);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		return listDailyForecast;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate getParentOFMonth()
	{
		if (m_Parent_Month == null)
			m_Parent_Month = new MUNSForecastDate(getCtx(), getYearly_ID(), get_TrxName());
		
		return m_Parent_Month;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate getParentOFWeek()
	{
		if (m_Parent_Week == null)
			m_Parent_Week = new MUNSForecastDate(getCtx(), getYearly_ID(), get_TrxName());
		
		return m_Parent_Week;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate getParentDayOfWeek()
	{
		if (m_Parent_Day_Of_Week == null)
			m_Parent_Day_Of_Week = new MUNSForecastDate(getCtx(), getWeekly_ID(), get_TrxName());
		
		return m_Parent_Day_Of_Week;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate getParentDayOfMonth()
	{
		if (m_Parent_Day_Of_Month == null)
			m_Parent_Day_Of_Month = new MUNSForecastDate(getCtx(), getMonthly_ID(), get_TrxName());
		
		return m_Parent_Day_Of_Month;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastDate[] getLinesMonthOfYear(boolean requery)
	{
		if (m_linesMonth != null && !requery)
		{
			set_TrxName(m_linesMonth, get_TrxName());
			return m_linesMonth;
		}
		
		final String whereClause = COLUMNNAME_Yearly_ID + "=? AND C_Period_ID != 0";
		
		List<X_UNS_Forecast_Date> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Forecast_Date_ID())
				.setOrderBy(COLUMNNAME_StartDate)
				.list();
		
		m_linesMonth = new MUNSForecastDate[list.size()];
		list.toArray(m_linesMonth);
		
		return m_linesMonth;
	}

	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastDate[] getLinesWeekOfYear(boolean requery)
	{
		if (m_linesWeek != null && !requery)
		{
			set_TrxName(m_linesWeek, get_TrxName());
			return m_linesWeek;
		}
		
		final String whereClause = COLUMNNAME_Yearly_ID + "=? AND WeekNo != 0";
		
		List<X_UNS_Forecast_Date> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Forecast_Date_ID())
				.setOrderBy(COLUMNNAME_WeekNo)
				.list();
		
		m_linesWeek = new MUNSForecastDate[list.size()];
		list.toArray(m_linesWeek);
		
		return m_linesWeek;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate[] getLinesWeekOfYear()
	{
		return getLinesWeekOfYear(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastDate[] getLinesDayOfMonth(boolean requery)
	{
		if (m_linesDayOFMonth != null && !requery)
		{
			set_TrxName(m_linesDayOFMonth, get_TrxName());
			return m_linesDayOFMonth;
		}
		
		final String whereClause = COLUMNNAME_Monthly_ID + "=?";
		
		List<X_UNS_Forecast_Date> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Forecast_Date_ID())
				.setOrderBy(COLUMNNAME_ForcastedDate)
				.list();
		
		m_linesDayOFMonth = new MUNSForecastDate[list.size()];
		list.toArray(m_linesDayOFMonth);
		
		return m_linesDayOFMonth;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastDate[] getLinesDayOfMonth()
	{
		return getLinesDayOfMonth(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSForecastDate[] getLinesDayOfWeek(boolean requery)
	{
		if (m_linesDayOfWeek != null && !requery)
		{
			set_TrxName(m_linesDayOfWeek, get_TrxName());
			return m_linesDayOfWeek;
		}
		
		final String whereClause = COLUMNNAME_Weekly_ID + "=?";
		
		List<X_UNS_Forecast_Date> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Forecast_Date_ID())
				.setOrderBy(COLUMNNAME_ForcastedDate)
				.list();
		
		m_linesDayOfWeek = new MUNSForecastDate[list.size()];
		list.toArray(m_linesDayOfWeek);
		
		return m_linesDayOfWeek;
	}

	
	public void calculate(MUNSForecastDate toCalculate)
	{
		setQtyMT(getQtyMT().add(toCalculate.getQtyMT()));
		setQtyMForecast(getQtyMForecast().add(toCalculate.getQtyMForecast()));
		setQtyUom(getQtyUom().add(toCalculate.getQtyUom()));
		setMaxCaps(getMaxCaps().add(toCalculate.getMaxCaps()));
		setOptimumCaps(getOptimumCaps().add(toCalculate.getOptimumCaps()));
		setTotalExpectedCostIDR(getTotalExpectedCostIDR().add(toCalculate.getTotalExpectedCostIDR()));
		setTotalExpectedCostSGD(getTotalExpectedCostSGD().add(toCalculate.getTotalExpectedCostSGD()));
		setTotalExpectedCostUSD(getTotalExpectedCostUSD().add(toCalculate.getTotalExpectedCostUSD()));
		setTotalExpectedRevenueIDR(getTotalExpectedRevenueIDR().add(toCalculate.getTotalExpectedRevenueIDR()));
		setTotalExpectedRevenueSGD(getTotalExpectedRevenueSGD().add(toCalculate.getTotalExpectedRevenueSGD()));
		setTotalExpectedRevenueUSD(getTotalExpectedRevenueUSD().add(toCalculate.getTotalExpectedRevenueUSD()));
	}
	
	public void copyValues(MUNSForecastDate from)
	{
		setAD_Org_ID(from.getAD_Org_ID());
		setM_Product_ID(from.getM_Product_ID());
		setC_UOM_ID(from.getC_UOM_ID());
		setIsPrimary(from.isPrimary());
		setExpectedCostIDR(from.getExpectedCostIDR());
		setExpectedCostSGD(from.getExpectedCostSGD());
		setExpectedCostUSD(from.getExpectedCostUSD());
		setExpectedRevenueIDR(from.getExpectedRevenueIDR());
		setExpectedRevenueSGD(from.getExpectedRevenueSGD());
		setExpectedRevenueUSD(from.getExpectedRevenueUSD());
		setQtyMT(from.getQtyMT());
		setQtyMForecast(from.getQtyMForecast());
		setQtyUom(from.getQtyUom());
		setMaxCaps(from.getMaxCaps());
		setOptimumCaps(from.getOptimumCaps());
		setTotalExpectedCostIDR(from.getTotalExpectedCostIDR());
		setTotalExpectedCostSGD(from.getTotalExpectedCostSGD());
		setTotalExpectedCostUSD(from.getTotalExpectedCostUSD());
		setTotalExpectedRevenueIDR(from.getTotalExpectedRevenueIDR());
		setTotalExpectedRevenueSGD(from.getTotalExpectedRevenueSGD());
		setTotalExpectedRevenueUSD(from.getTotalExpectedRevenueUSD());
	}
	
	/**
	 * 
	 */
	public void rescaleAllNumbers() {
		int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, precision, BigDecimal.ROUND_HALF_UP, false);
	}
}
