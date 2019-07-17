/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;


/**
 * @author menjangan
 *
 */
public class MUNSMPSProductWeekly extends X_UNS_MPSProduct_Weekly {
	
	/** */
	private MUNSMPSProduct m_Parent = null;
	public static final String m_TypeSo = "SO";
	public static final String m_TypePI = "PI";

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
		COLUMNNAME_ActualToStockUOM,
		COLUMNNAME_AATP,
		COLUMNNAME_Acc_ATP,
		COLUMNNAME_ActualAvailableBalances,
		COLUMNNAME_ATP,
		COLUMNNAME_ProjectedActualBalance,
		COLUMNNAME_QtyDelivered,
		COLUMNNAME_QtyMT,
		COLUMNNAME_QtyUom,
		COLUMNNAME_Stock
	};
	
	
	/**
	 * 
	 */
	public void rescaleAllNumbers() {
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, 4, BigDecimal.ROUND_HALF_UP, false);
	}
	
	/**
	 * @param ctx
	 * @param UNS_MPSProduct_Weekly_ID
	 * @param trxName
	 */
	public MUNSMPSProductWeekly(Properties ctx, int UNS_MPSProduct_Weekly_ID,
			String trxName) {
		super(ctx, UNS_MPSProduct_Weekly_ID, trxName);
	}
	
	
	/**
	 * 
	 * @return MUNSMPSProduct
	 */
	public MUNSMPSProduct getParent()
	{
		if (m_Parent == null)
		{
			m_Parent = new MUNSMPSProduct(
					getCtx(), getUNS_MPSProduct_ID(), get_TrxName());
		}
		return m_Parent;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMPSProductWeekly(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * If it can be found, it will create new MPS Product Weekly and you should set other 
	 * MPS Product Weekly data (here only UNS_MPSProduct_ID and weekNo are set).
	 * 
	 * @param ctx
	 * @param UNS_MPSProduct
	 * @param weekNo
	 * @param trxName
	 * @return
	 */
	public static MUNSMPSProductWeekly get (Properties ctx, MUNSMPSProduct UNS_MPSProduct, int weekNo, String trxName)
	{
		MUNSMPSProductWeekly mpsProductWeekly =  
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "UNS_MPSProduct_ID=? AND Weekly_ID IS NULL AND MPSDate IS NULL AND WeekNo=?", 
						  trxName)
				.setParameters(UNS_MPSProduct.get_ID(), weekNo)
				.first();
		
		if (mpsProductWeekly != null)
			return mpsProductWeekly;
		
		// Create it.
		mpsProductWeekly = new MUNSMPSProductWeekly(ctx, 0, trxName);
		
		mpsProductWeekly.setUNS_MPSProduct_ID(UNS_MPSProduct.get_ID());
		mpsProductWeekly.setWeekNo(weekNo);
		mpsProductWeekly.initBasicNecessaryInfo(null, UNS_MPSProduct, UNS_MPSProduct.getM_Product_ID());
		//mpsProductWeekly.setStartDate(UNSTimeUtil.getProductionWeekStartDate(new MUNSMPSProduct(ctx, UNS_MPSProduct_ID, trxName).getUNS_MPSHeader(), weekNo))
		
		return mpsProductWeekly;
	}
	
	/**
	 * If it can be found, it will create new MPS Product Weekly and you should set other 
	 * MPS Product Weekly data (here only UNS_MPSProduct_ID and weekNo are set).
	 * 
	 * @param ctx
	 * @param UNS_MPSProduct_ID
	 * @param weekNo
	 * @param trxName
	 * @return the appropriate MPS Product Weekly, or null if not found.
	 */
	public static MUNSMPSProductWeekly get (
			Properties ctx, int UNS_MPSHeader_ID, int M_Product_ID, int weekNo, String trxName)
	{
		MUNSMPSProductWeekly mpsProductWeekly =  
				Query.get(ctx, 
						  UNSPPICModelFactory.getExtensionID(), 
						  Table_Name, 
						  "(Weekly_ID IS NULL OR Weekly_ID=0) AND MPSDate IS NULL AND WeekNo=? AND " +
						  "UNS_MPSProduct_ID = (SELECT mpsPrd.UNS_MPSProduct_ID FROM UNS_MPSProduct mpsPrd " +
						  "		WHERE mpsPrd.UNS_MPSHeader_ID=? AND mpsPrd.M_Product_ID=?)", 
						  trxName)
				.setParameters(weekNo, UNS_MPSHeader_ID, M_Product_ID)
				.first();
		
		if (mpsProductWeekly != null)
			return mpsProductWeekly;
		
		// Create it.
		MUNSMPSProduct mpsProduct = MUNSMPSProduct.getNoCreate(ctx, UNS_MPSHeader_ID, M_Product_ID, trxName);
		if (mpsProduct == null)
			return null;
		
		mpsProductWeekly = new MUNSMPSProductWeekly(ctx, 0, trxName);
		
		mpsProductWeekly.setUNS_MPSProduct_ID(mpsProduct.get_ID());
		mpsProductWeekly.setWeekNo(weekNo);
		mpsProductWeekly.initBasicNecessaryInfo(null, mpsProduct, mpsProduct.getM_Product_ID());
		
		return mpsProductWeekly;
	}
	
	/**
	 * This is a method to be called after calling getCreate method which is only set MPSProductID and week-no.
	 * Note: it is not calling save action, you need to call it after calling this method.
	 * Either mpsHeader or mpsProduct cannot be null.
	 * 
	 * @param mpsHeader
	 * @param mpsProduct
	 */
	public void initBasicNecessaryInfo(MUNSMPSHeader mpsHeader, MUNSMPSProduct mpsProduct, int M_Product_ID)
	{
		int year;
		if (mpsHeader != null)
			year = Integer.valueOf(mpsHeader.getPeriodStart().getC_Year().getFiscalYear());
		else if (mpsProduct != null)
			year = Integer.valueOf(mpsProduct.getUNS_MPSHeader().getPeriodStart().getC_Year().getFiscalYear());
		else
			throw new AdempiereException("Cannot initialize MPS Product Weekly data without MPS Header or MPS Product.");

		if (mpsProduct == null)
			mpsProduct = MUNSMPSProduct.getNoCreate(getCtx(), mpsHeader.get_ID(), M_Product_ID, get_TrxName());
		
		setStartDate(UNSTimeUtil.getProductionWeekStartDate(year, getWeekNo()));
		setEndDate(UNSTimeUtil.getProductionWeekEndDate(year, getWeekNo()));
		setIncubationDays(mpsProduct.getIncubationDays());
		setSafetyStock(mpsProduct.getSafetyStock());
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return true;
	}
	
	/**
	 * 
	 * @param toCalculate
	 */
	public void calculate(MUNSMPSProductWeekly toCalculate)
	{
		setAcc_ATP(getAcc_ATP().add(toCalculate.getAcc_ATP()));
		setActualToOrderMT(getActualToOrderMT().add(toCalculate.getActualToOrderMT()));
		setActualToOrderUOM(getActualToOrderUOM().add(toCalculate.getActualToOrderUOM()));
		setATP(getATP().add(toCalculate.getATP()));
		setQtyMT(getQtyMT().add(toCalculate.getQtyMT()));
		setQtyUom(getQtyUom().add(toCalculate.getQtyUom()));
		setActualToStockMT(getActualToStockMT().add(toCalculate.getActualToStockMT()));
		setActualToStockUOM(getActualToStockUOM().add(toCalculate.getActualToStockUOM()));
		setProjectedActualBalance(getProjectedActualBalance().add(toCalculate.getProjectedActualBalance()));
		setQtyDelivered(getQtyDelivered().add(toCalculate.getQtyDelivered()));
	}

	/**
	 * 
	 * @param listOfMPSWeekly Pretend the list is all MPSProduct weekly list from start to end period.
	 * @param currentIx
	 * @return
	 */
	public MUNSMPSProductWeekly getPreviousReleasedMPSProductWeekly(
			MUNSMPSHeader prevMPS, MUNSMPSProductWeekly[] listOfMPSWeekly, int currentIx, int M_Product_ID)
	{
		MUNSMPSProductWeekly lastReleased = null;
		
		Timestamp cutOffIncubationDate = UNSTimeUtil.addDays(getStartDate(), getIncubationDays());
		for (int i=currentIx-1; i >= 0; i--)
		{
			if (cutOffIncubationDate.compareTo(listOfMPSWeekly[i].getEndDate()) >= 0) {
				lastReleased = listOfMPSWeekly[i];
				break;
			}
		}
		// if not exist in the list, then try to get it from previous MPSHeader.
		if (prevMPS == null)
			return null;
		
		String sqlWhere = 
				" UNS_MPSProduct_ID = (SELECT prev.UNS_MPSProduct_ID FROM UNS_MPSProduct prev " +
				"						WHERE prev.UNS_MPSHeader_ID=? AND prev.M_Product_ID=?) " +
				" AND EndDate <= ?";
		lastReleased = Query
				.get(getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, sqlWhere, get_TrxName())
				.setParameters(prevMPS.getUNS_MPSHeader_ID(), M_Product_ID, cutOffIncubationDate)
				.setOrderBy(COLUMNNAME_WeekNo)
				.first();
		if (lastReleased == null)
			return null;
		
		if (currentIx == 0)
			return lastReleased;
		
		// test apakah lastReleased yg didapatkan layak utk mjd data RSFS minggu sebelumnya. 
		// jika layak maka itu artinya lastReleased yg didapatkan sudah digunakan oleh 
		// MPSPRoductWeekly minggu sebelum currentIx.
		Timestamp prevIndexCutOffDate = 
				UNSTimeUtil.addDays(listOfMPSWeekly[currentIx-1].getStartDate(), getIncubationDays());
		if (prevIndexCutOffDate.compareTo(lastReleased.getEndDate()) >= 0)
			return null;

		return lastReleased;
	}
}
