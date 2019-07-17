/**
 * 
 */
package com.uns.model;

import java.io.File;
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
import org.compiere.model.I_C_Period;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSOtherProductConf;
import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMPSHeader extends X_UNS_MPSHeader implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * MPS Product
	 */
	private MUNSMPSProduct[] m_line = null;
	private MUNSMRP[] m_ListMRP = null;
	private MUNSMPSHeader m_PrevMPSHeader = null;
	private int m_firstWeekNo = -1;
	private int m_lastWeekNo = -1;
	private int m_year = 0;

	/**
	 * @param ctx
	 * @param UNS_MPSHeader_ID
	 * @param trxName
	 */
	public MUNSMPSHeader(Properties ctx, int UNS_MPSHeader_ID, String trxName) {
		super(ctx, UNS_MPSHeader_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMPSHeader(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getYear()
	{
		if (m_year == 0)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getPeriodStart().getStartDate().getTime());
			
			m_year = cal.get(Calendar.YEAR);
		}
		return m_year;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFirstWeekNo()
	{
		if (m_firstWeekNo == -1)
		{
			Timestamp ts = getPeriodStart().getStartDate();
			m_firstWeekNo = UNSTimeUtil.getProductionWeekNo(ts);
		}
		return m_firstWeekNo;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLastWeekNo()
	{
		if (m_lastWeekNo == -1)
		{
			Timestamp ts = getPeriodEnd().getEndDate();
			m_firstWeekNo = UNSTimeUtil.getProductionWeekNo(ts);
		}
		return m_lastWeekNo;
	}
	
	/**
	 * 
	 * @param requery
	 * @return lines MPS Product
	 */
	public MUNSMPSProduct[] getLines(boolean requery)
	{
		if (null != m_line && !requery)
		{
			set_TrxName(m_line, get_TrxName());
			return m_line;
		}
		
		final String whereClause = X_UNS_MPSProduct.COLUMNNAME_UNS_MPSHeader_ID + "=?";
		List<X_UNS_MPSProduct> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MPSProduct.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSHeader_ID())
				.setOrderBy(X_UNS_MPSProduct.COLUMNNAME_UNS_MPSProduct_ID)
				.list();
		
		m_line = new MUNSMPSProduct[list.size()];
		list.toArray(m_line);
		
		return m_line;
	}
	
	
	/**
	 * 
	 * @return getLines(false)
	 */
	public MUNSMPSProduct[] getLines()
	{
		return getLines(false);
	}
	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param dateReceived
	 * @param trxName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static MUNSMPSHeader get(Timestamp dateMPS, String trxName, int AD_Org_ID)
	{
		Calendar calendar = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		calendar.setTimeInMillis(dateMPS.getTime());
		MUNSMPSHeader mpsHeader = null;

		MPeriod period = MPeriod.get(Env.getCtx(), dateMPS, AD_Org_ID);
		int periodNo = period.getPeriodNo();
		int C_Year_ID = period.getC_Year_ID();
		
		String sqlGetMPS = "SELECT * FROM " + Table_Name + " mps INNER JOIN " 
				+ MUNSForecastHeader.Table_Name + " f ON f." 
				+ MUNSForecastHeader.COLUMNNAME_UNS_Forecast_Header_ID 
				+ " = mps." + COLUMNNAME_UNS_Forecast_Header_ID + " AND f." 
				+ MUNSForecastHeader.COLUMNNAME_C_Year_ID + " =? AND f." 
				+ MUNSForecastHeader.COLUMNNAME_IsActive + " ='Y' "
				+ " INNER JOIN " + MPeriod.Table_Name + " ps ON ps."
				+ MPeriod.COLUMNNAME_C_Period_ID + " = mps." 
				+ COLUMNNAME_PeriodStart_ID + " "
				+ " INNER JOIN " + MPeriod.Table_Name + " pe  ON pe."
				+ MPeriod.COLUMNNAME_C_Period_ID + " = mps."
				+ COLUMNNAME_PeriodEnd_ID
				+ " WHERE mps." + COLUMNNAME_IsActive +  "='Y' AND "
				+ periodNo + " BETWEEN " + " ps." + MPeriod.COLUMNNAME_PeriodNo + " AND pe."
				+ MPeriod.COLUMNNAME_PeriodNo;
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sqlGetMPS, trxName);
			stm.setInt(1, C_Year_ID);
			rs = stm.executeQuery();
			if  (rs.next())
				mpsHeader = new MUNSMPSHeader(Env.getCtx(), rs, trxName);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return mpsHeader;
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

	private String m_processMsg = null;
	
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
		
		MUNSMPSProduct[] lines = getLines();
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
	
	private boolean m_justPrepared = true;
	@Override
	public boolean approveIt() {
		
		log.info(toString());
//		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		
		log.info(toString());
//		setIsApproved(false);
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
		
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		return false;
	}

	@Override
	public String getSummary() {

		return null;
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

	
	/**
	 * getResource
	 * @return
	 */
	public MUNSResource getResource()
	{
		return (MUNSResource) getUNS_Forecast_Header().getUNS_Resource();
	}
	
	public boolean deleteLines(boolean force)
	{
		for (MUNSMPSProduct mpsProduct : getLines())
			try{
				if (!mpsProduct.delete(true))
					throw new IllegalStateException("can't delete mps product");
			}catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());
			}
		
		return false;
	}
	
	/**
	 * Get ExpectedOnHand for update MPS
	 * @return
	 */
	public Hashtable<Integer, Double> getExpectedOnHands() {
		Hashtable<Integer, Double> expectedOnHands = new Hashtable<Integer, Double>();
		for (MUNSMPSProduct mpsProduct : getLines())
		{
			double expectedOnHand = 0.0;
			if (mpsProduct.getInitialProjectedStock_OnHand().compareTo(BigDecimal.ZERO) > 0)
			{
				expectedOnHand = mpsProduct.getInitialProjectedStock_OnHand().doubleValue();
				expectedOnHands.put(mpsProduct.getM_Product_ID(), expectedOnHand);
			}
		}
		return expectedOnHands;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMRP[] getListMRP(boolean requery)
	{
		if (null != m_ListMRP && !requery)
		{
			set_TrxName(m_ListMRP, get_TrxName());
			return m_ListMRP;
		}
		
		final String whereClause = X_UNS_MRP.COLUMNNAME_UNS_MPSHeader_ID + "=?";
		List<X_UNS_MRP> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MRP.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_MPSHeader_ID())
				.list();
		
		m_ListMRP = new MUNSMRP[list.size()];
		list.toArray(m_ListMRP);
		
		return m_ListMRP;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//setName(getName()+ " - "  + getUNS_Forecast_Header().getName());
		//setDescription(getName());
		return super.beforeSave(newRecord);
	}
	
	public boolean isGeneratedMRP()
	{
		return getGenerate_MRP().equals("Y");
	}
	
	public MUNSMPSHeader getNextMPS()
	{
		MUNSMPSHeader mps = null;
		String sql = "SELECT * FROM " + Table_Name + " mps INNER JOIN " + MUNSForecastHeader.Table_Name
				+ " f ON f." + COLUMNNAME_UNS_Forecast_Header_ID + " = mps."
				+ COLUMNNAME_UNS_Forecast_Header_ID + " AND f." + MUNSForecastHeader.COLUMNNAME_PrevForecast_ID
				+ " = " + getUNS_Forecast_Header_ID();
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			rs = st.executeQuery();
			if(rs.next())
				mps = new MUNSMPSHeader(getCtx(), rs, get_TrxName());
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			DB.close(rs,st);
		}
		return mps;
	}
	
	public int getWeekStart()
	{
		I_C_Period period = getPeriodStart();
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		cal.setTimeInMillis(period.getStartDate().getTime());
		int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
		if(weekNo == 1 && cal.get(Calendar.DAY_OF_YEAR) >= 350 )
			weekNo = 53;
		return weekNo;
	}
	
	public int getEndDay()
	{
		I_C_Period period = getPeriodEnd();
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		cal.setTimeInMillis(period.getEndDate().getTime());
		int endDay = cal.get(Calendar.DAY_OF_YEAR);
		return endDay;
	}
	
	public Timestamp getStartDate()
	{
		I_C_Period period = getPeriodStart();
		return period.getStartDate();
	}
	
	public Timestamp getEndDate()
	{
		I_C_Period period = getPeriodEnd();
		return period.getEndDate();
	}
	
	public int getStartDay()
	{
		I_C_Period period = getPeriodStart();
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		cal.setTimeInMillis(period.getStartDate().getTime());
		int StartDay = cal.get(Calendar.DAY_OF_YEAR);
		return StartDay;
	}
	
	public int getWeekEnd()
	{
		I_C_Period period = getPeriodEnd();
		Calendar cal = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
		cal.setTimeInMillis(period.getEndDate().getTime());
		int weekEnd = cal.get(Calendar.WEEK_OF_YEAR);
		if(weekEnd == 1 && cal.get(Calendar.DAY_OF_YEAR) >= 350 )
			weekEnd = 53;
		
		return weekEnd;
	}
	
	/**
	 * Get latest week of MPSProductWeekly record for the given M_Product_ID.
	 * @param M_Product_ID
	 * @return the latest week of MPSProductWeekly record for the given M_Product_ID.
	 */
	public MUNSMPSProductWeekly getLatestWeeklyProductOf(int M_Product_ID)
	{
		MUNSMPSProductWeekly latest = null;
		
		String whereClause = 
				" UNS_MPSProduct_ID = (SELECT mpsProduct.UNS_MPSProduct_ID FROM UNS_MPSProduct mpsProduct " +
				" 						WHERE mpsProduct.UNS_MPSHeader_ID=? AND mpsProduct.M_Product_ID=?)" +
				" AND weekly_ID IS NULL";
		latest = Query
				.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSMPSProductWeekly.Table_Name, whereClause, get_TrxName())
				.setParameters(get_ID(), M_Product_ID)
				.setOrderBy(MUNSMPSProductWeekly.COLUMNNAME_WeekNo)
				.first();
		
		return latest;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param weekNo
	 * @param date
	 * @return
	 */
	public MUNSMPSProductWeekly getMPSProductPeriodic(
			int M_Product_ID, 
			int weekNo, 
			Timestamp date)
	{
		MUNSMPSProductWeekly prevProductWeekly = null;
		String WHERECLAUSE = "";
		
		if(weekNo > 0)
			WHERECLAUSE += MUNSMPSProductWeekly.COLUMNNAME_WeekNo + " = " + weekNo
						+ " AND " + MUNSMPSProductWeekly.COLUMNNAME_MPSDate + " IS NULL ";
		if(null != date)
			WHERECLAUSE += MUNSMPSProductWeekly.COLUMNNAME_MPSDate + " = '" + date + "'";
		
		String sql = "SELECT mpd.*"
				+ " FROM " + MUNSMPSProductWeekly.Table_Name
				+ " mpd INNER JOIN " + MUNSMPSProduct.Table_Name
				+ " mp ON mpd." + MUNSMPSProduct.COLUMNNAME_UNS_MPSProduct_ID + " = mp." 
				+ MUNSMPSProductWeekly.COLUMNNAME_UNS_MPSProduct_ID
				+ " AND mp." + MUNSMPSProduct.COLUMNNAME_M_Product_ID + " = "
				+ M_Product_ID
				+ " INNER JOIN " + MUNSMPSHeader.Table_Name
				+ " mh ON mh." + MUNSMPSHeader.COLUMNNAME_UNS_MPSHeader_ID + " = mp."
				+ MUNSMPSProduct.COLUMNNAME_UNS_MPSHeader_ID
				+ " AND mh." + MUNSMPSHeader.COLUMNNAME_UNS_MPSHeader_ID + " = "
				+ get_ID()
				+ " WHERE mpd." + WHERECLAUSE;
		
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			rs = st.executeQuery();
			if(rs.next())
				prevProductWeekly = new MUNSMPSProductWeekly(getCtx(), rs, get_TrxName());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return prevProductWeekly;
	}
	
	public void updateTerhadapPerubahanMPSSebelumnnya(MUNSMPSHeader prevMPS)
	{
		this.m_PrevMPSHeader = prevMPS;
		for(MUNSMPSProduct mpsProduct : getLines())
		{
			updateDailyMPS(mpsProduct);
			updateWeeklyMPS(mpsProduct);
		}
	}
	
	private void updateWeeklyMPS(MUNSMPSProduct mpsProduct)
	{
		Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSWeekly = 
				new Hashtable<Integer, MUNSMPSProductWeekly>();
		mapOfMPSWeekly = mpsProduct.getMapOfWeeklyMPSproduct();
		
		double weight = mpsProduct.getM_Product().getWeight().doubleValue();
		double onHandMT = mpsProduct.getInitialProjectedStock_OnHand().doubleValue() * weight;
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), mpsProduct.getM_Product_ID(), get_TrxName());
		
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " +mpsProduct.getM_Product_ID());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + mpsProduct.getM_Product_ID());
		}
		
		double SafetyStockMT = OtherProductConf.getSafetyStock().doubleValue() * weight * 0.001;
		

		MUNSMPSProductWeekly prevMPSProductWeekly = null;
		double prevPAB = 0.0;
		double prevStock = 0.0;
		double prevAccAtp = 0.0;
		
		if(null != m_PrevMPSHeader)
		{
			I_C_Period period = m_PrevMPSHeader.getPeriodEnd();
			Calendar calPrevWeekly = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
			calPrevWeekly.setTimeInMillis(period.getEndDate().getTime());
			int prevWeekNo = calPrevWeekly.get(Calendar.WEEK_OF_YEAR);
			if(prevWeekNo == 1 && calPrevWeekly.get(Calendar.DAY_OF_YEAR) > 150)
				prevWeekNo = 53;
			
			prevMPSProductWeekly = 
					m_PrevMPSHeader.getMPSProductPeriodic(
							mpsProduct.getM_Product_ID(),
							prevWeekNo,
							null);
			
			prevPAB = prevMPSProductWeekly.getProjectedActualBalance().doubleValue();
			prevStock = prevMPSProductWeekly.getStock().doubleValue();
			prevAccAtp = prevMPSProductWeekly.getAcc_ATP().doubleValue();
		}
		for (MUNSMPSProductWeekly mpsWeekly : mpsProduct.getLinesWeekly(true))
		{
			double PAB = mpsWeekly.getProjectedActualBalance().doubleValue();
			double stock = mpsWeekly.getStock().doubleValue();
			double atp = mpsWeekly.getATP().doubleValue();
			double Acc_ATP = mpsWeekly.getAcc_ATP().doubleValue();
			int weekNo = mpsWeekly.getWeekNo();
		
			
			if (weekNo == getWeekStart()
					&& null == prevMPSProductWeekly)
			{
				prevPAB = onHandMT;
				prevStock = onHandMT;
			}
			
			//generate MPS Product Day OF weekly And Day OF Year

			if (mpsWeekly.getActualToOrderMT().compareTo(BigDecimal.ZERO)>0)
			{
				PAB = prevPAB + mpsWeekly.getQtyMT().doubleValue() 
						- mpsWeekly.getActualToOrderMT().doubleValue();
				if(weekNo == getWeekStart()
						&& null == m_PrevMPSHeader) 
				{
					atp = onHandMT + mpsWeekly.getQtyMT().doubleValue() - SafetyStockMT 
						- mpsWeekly.getActualToOrderMT().doubleValue();
				}
				else
				{
					atp = mpsWeekly.getQtyMT().doubleValue() - SafetyStockMT 
							- mpsWeekly.getActualToOrderMT().doubleValue();
				}
			}
			else
			{
				PAB = prevPAB + mpsWeekly.getQtyMT().doubleValue()
						- mpsWeekly.getActualToStockMT().doubleValue();
				if (weekNo == getWeekStart()
						&& null == m_PrevMPSHeader)
				{
					atp = onHandMT + mpsWeekly.getQtyMT().doubleValue()
							- SafetyStockMT - mpsWeekly.getActualToStockMT().doubleValue();
				}
				else
				{
					atp = mpsWeekly.getQtyMT().doubleValue() - SafetyStockMT
							 - mpsWeekly.getActualToStockMT().doubleValue();
				}
			}
			
			
			MUNSMPSProductWeekly nextWeeklyMPS = mapOfMPSWeekly.get(weekNo+1);

			if (null != nextWeeklyMPS)
			{
				if(nextWeeklyMPS.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
					atp -= nextWeeklyMPS.getActualToOrderMT().doubleValue();
				else
					atp -= nextWeeklyMPS.getActualToStockMT().doubleValue();
			}
			
			mpsWeekly.setProjectedActualBalance(new BigDecimal(PAB));
			mpsWeekly.setATP(new BigDecimal(atp));
			
			stock = prevStock + mpsWeekly.getQtyMT().doubleValue() 
					- mpsWeekly.getQtyDelivered().doubleValue();
			
			Acc_ATP = atp;
			Acc_ATP += prevAccAtp;
			
			mpsWeekly.setAcc_ATP(new BigDecimal(Acc_ATP));
			mpsWeekly.save();
			prevPAB = PAB;
			prevStock = stock;
			prevAccAtp = Acc_ATP;
		}
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param calendar
	 * @param orderQty
	 * @param shipmentQty
	 */
	private void updateDailyMPS(MUNSMPSProduct mpsProduct)
	{		
		Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSDaily = 
									new Hashtable<Integer, MUNSMPSProductWeekly>();
		mapOfMPSDaily = mpsProduct.getMapOfDailyMPSProduct();
		double weight = mpsProduct.getM_Product().getWeight().doubleValue();
		double onHandMT = mpsProduct.getInitialProjectedStock_OnHand().doubleValue()
				* weight * 0.001;
	
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), mpsProduct.getM_Product_ID(), get_TrxName());
		
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " +mpsProduct.getM_Product_ID());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + mpsProduct.getM_Product_ID());
		}
		
		double SafetyStockMT = OtherProductConf.getSafetyStock().doubleValue() 
				* weight * 0.001;
		int incubationDay = OtherProductConf.getIncubationDays();
		

		MUNSMPSProductWeekly prevMPSproductDaily = null;
		double prevPAB = 0.0;
		double prevStock = 0.0;
		double prevAccAtp = 0.0;
		
		if(null != m_PrevMPSHeader)
		{
			I_C_Period period = m_PrevMPSHeader.getPeriodEnd();
			Calendar calPrevWeekly = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
			calPrevWeekly.setTimeInMillis(period.getEndDate().getTime());
			Timestamp date = new Timestamp(calPrevWeekly.getTimeInMillis());
			
			prevMPSproductDaily = 
					m_PrevMPSHeader.getMPSProductPeriodic(
							mpsProduct.getM_Product_ID(),
							0,
							date);
			
			prevPAB = prevMPSproductDaily.getProjectedActualBalance().doubleValue();
			prevStock = prevMPSproductDaily.getStock().doubleValue();
			prevAccAtp = prevMPSproductDaily.getAcc_ATP().doubleValue();
		}
		
		for (MUNSMPSProductWeekly mpsProductDaily : mpsProduct.getLinesDayly(true))
		{
			Calendar mpsCalendar = Calendar.getInstance(UNSTimeUtil.DEFAULT_LOCALE);
			mpsCalendar.setTimeInMillis(mpsProductDaily.getMPSDate().getTime());
			int dayOfMPS = mpsCalendar.get(Calendar.DAY_OF_YEAR);
			double PAB = mpsProductDaily.getProjectedActualBalance().doubleValue();
			double stock = mpsProductDaily.getStock().doubleValue();
			double atp = mpsProductDaily.getATP().doubleValue();
			double Acc_ATP = mpsProductDaily.getAcc_ATP().doubleValue();
	
			
			if (dayOfMPS == getStartDay()
					&& null == prevMPSproductDaily)
			{
				prevPAB = onHandMT;
				prevStock = onHandMT;
			}
			
			if (mpsProductDaily.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
			{
				PAB = prevPAB + mpsProductDaily.getQtyMT().doubleValue()
						- mpsProductDaily.getActualToOrderMT().doubleValue();
				if(dayOfMPS == getStartDay()
						&& null == m_PrevMPSHeader)
				{
					atp = onHandMT + mpsProductDaily.getQtyMT().doubleValue() 
							- SafetyStockMT
							- mpsProductDaily.getActualToOrderMT().doubleValue();
				}
				else
				{
					atp = mpsProductDaily.getQtyMT().doubleValue() 
							- SafetyStockMT
							- mpsProductDaily.getActualToOrderMT().doubleValue();
				}
				
			}
			
			else
			{
				PAB = prevPAB + mpsProductDaily.getQtyMT().doubleValue() 
						 - mpsProductDaily.getActualToStockMT().doubleValue();
				if (dayOfMPS == getStartDay()
						&& null == m_PrevMPSHeader)
				{
					atp = onHandMT + mpsProductDaily.getQtyMT().doubleValue()
							- SafetyStockMT - mpsProductDaily.getActualToStockMT()
							.doubleValue();
				}
				else
				{
					atp = mpsProductDaily.getQtyMT().doubleValue() - SafetyStockMT
							- mpsProductDaily.getActualToStockMT().doubleValue();
				}
				
			}
			
			MUNSMPSProductWeekly nextDailyMPSProduct = mapOfMPSDaily.get(dayOfMPS + 1);
			if (null != nextDailyMPSProduct)
			{
				if(nextDailyMPSProduct.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
					atp -= nextDailyMPSProduct.getActualToOrderMT().doubleValue();
				else
					atp -= nextDailyMPSProduct.getActualToStockMT().doubleValue();
			}
			
			mpsProductDaily.setProjectedActualBalance(new BigDecimal(PAB));
			mpsProductDaily.setAD_Org_ID(mpsProduct.getAD_Org_ID());
			mpsProductDaily.setATP(new BigDecimal(atp));
			mpsProductDaily.setIncubationDays(incubationDay);
			
			long MiliSecondIncubDays = (24 * 60 * 60 * 1000);
			
			long StartIncubation = mpsProductDaily.getMPSDate().getTime();
			
			mpsProductDaily.setEndOfIncubation(new Timestamp(StartIncubation+MiliSecondIncubDays));
			stock = prevStock + mpsProductDaily.getQtyMT().doubleValue()
					- mpsProductDaily.getQtyDelivered().doubleValue();
			mpsProductDaily.setStock(new BigDecimal(stock));
			
			Acc_ATP = atp;
			Acc_ATP += prevAccAtp;
			
			mpsProductDaily.setAcc_ATP(new BigDecimal(Acc_ATP));
			//ini pasti mps tanggal sebelumnya karena pada saat mengambil daily forecast sudah di sortir
			// berdasarkan tanggal
			mpsProductDaily.save();
		}

	}	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public MUNSMRP getMRPSummary(int M_Product_ID)
	{
		MUNSMRP mrp = null;
		
		String sql = "SELECT * FROM " +MUNSMRP.Table_Name
				+ " WHERE " + MUNSMRP.COLUMNNAME_M_Product_ID
				+ " = " + M_Product_ID;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			rs = st.executeQuery();
			if(rs.next())
				mrp = new MUNSMRP(getCtx(), rs, get_TrxName());
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally{
			DB.close(rs, st);
		}
		return mrp;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 *
	public void updateTerhadapPerubahanMRPSebelumnya(int M_Product_ID)
	{
		MUNSMRP mrp = getMRPSummary(M_Product_ID);
		if(null == mrp)
			return;
		Hashtable<Integer, MUNSMRPWeekly> mapOfMRPWeekly =
				mrp.getMapOfMRPWeekly();
		BigDecimal onHand = mrp.getLastPrevOnHand(getWeekEnd());
		MUNSMRPWeekly mrpWeeklyPORE = null;
		mrp.setExpectedStorageOnHand(onHand);
		double MOQ = mrp.getMOQ().doubleValue();
		double safetyStock = mrp.getSafetyStock().doubleValue();
		double prevProjectedOnhand = 0.0;
		double grossRequirement = 0.0;
		double grossManufacture = 0.0;
		double netRequirement = 0.0;
		for(MUNSMRPWeekly mrpWeekly : mrp.getWeeklysLines())
		{
			int weekNo = mrpWeekly.getWeekNo();
			if(weekNo == getWeekStart())
			{
				prevProjectedOnhand = onHand.doubleValue();
			}
			grossRequirement = mrpWeekly.getgrosrequirement().doubleValue();
			grossManufacture = mrpWeekly.getGRManufacturing().doubleValue();
			netRequirement = mrpWeekly.getNetRequirement().doubleValue();
			int weekPore = weekNo - mrp.getLeadTime();
			double pore = 0.0;
			double aore = 0.0;
			if(weekPore > 0)
			{
				mrpWeeklyPORE = mapOfMRPWeekly.get(weekPore);
				pore = mrpWeeklyPORE.getPORE().doubleValue();
				aore = mrpWeeklyPORE.getPORE().doubleValue();
				double curentOnHandEstimasi = prevProjectedOnhand;
				double curentOnHandReal = prevProjectedOnhand;
				double kurangEstimasi = curentOnHandReal - grossRequirement;
				double kurangReal = curentOnHandReal - grossManufacture;
				
				while (kurangEstimasi < safetyStock)
				{
					pore += MOQ;
					curentOnHandEstimasi += MOQ;
					kurangEstimasi = curentOnHandEstimasi - grossRequirement;
				}
				
				while (kurangReal < safetyStock)
				{
					aore += MOQ;
					curentOnHandReal += MOQ;
					kurangReal = curentOnHandReal - grossManufacture;
				}
				mrpWeeklyPORE.setPORE(new BigDecimal(pore));
				mrpWeeklyPORE.setAORE(new BigDecimal(aore));
				mrpWeeklyPORE.save();
			}
			
			double projectedAvailable = prevProjectedOnhand + pore;
			mrpWeekly.setPOR(new BigDecimal(pore));
			mrpWeekly.setScheduleReceipt(new BigDecimal(aore));
			mrpWeekly.setProjectedAvailable(new BigDecimal(projectedAvailable));
			mrpWeekly.setProjectedOnHand(
					mrpWeekly.getProjectedAvailable().subtract(mrpWeekly.getgrosrequirement()));
			mrpWeekly.setAROH(mrpWeekly.getProjectedOnHand());
			mrpWeekly.setAATP(mrpWeekly.getProjectedAvailable());
			netRequirement = grossRequirement + safetyStock - aore + prevProjectedOnhand;
					
			mrpWeekly.setNetRequirement(new BigDecimal(netRequirement));
			prevProjectedOnhand = mrpWeekly.getProjectedOnHand().doubleValue();
			mrpWeekly.save();
			
		}
	}
	*/
}

