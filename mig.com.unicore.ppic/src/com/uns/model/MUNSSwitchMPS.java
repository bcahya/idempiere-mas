/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

import com.uns.model.MUNSOtherProductConf;

/**
 * @author menjangan
 *
 */
public class MUNSSwitchMPS extends X_UNS_SwitchMPS implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * @param ctx
	 * @param UNS_SwitchMPS_ID
	 * @param trxName
	 */
	public MUNSSwitchMPS(Properties ctx, int UNS_SwitchMPS_ID, String trxName) {
		super(ctx, UNS_SwitchMPS_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSwitchMPS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

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
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
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
		
		generateMPSProductPeriodic();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		
			// After Void
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
			if (m_processMsg != null)
				return false;

			setProcessed(true);
			setDocAction(DOCACTION_None);
			return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		
			setProcessed(true);
			setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
			setDocAction(DOCACTION_None);
			return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":");
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
//			.append(" (#").append(getLines().length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return Env.ZERO;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void generateDailyMPSproduct(
			double onHand,
			I_M_Product product,
			MUNSMPSHeader mpsHeader,
			MUNSMPSProductResource mpsProductRsc,
			Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSDaily)
	{
		
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
		
		int M_Product_ID = product.getM_Product_ID();
		double weight = product.getWeight().doubleValue();
		double onHandMT = onHand * weight /1000;
		Calendar cal = Calendar.getInstance();
		int startDay = mpsHeader.getStartDay();
		MUNSMPSHeader prevMPSHeader = (MUNSMPSHeader)mpsHeader.getPrevMPS();

		Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfMPSproductRscDay =
				mpsProductRsc.getMapOfDailyMPSRsc();
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), M_Product_ID, get_TrxName());
		
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " + product.getName());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + product.getName());
		}
		
		double SafetyStockMT = OtherProductConf.getSafetyStock().doubleValue() * weight / 1000;
		int incubationDay = OtherProductConf.getIncubationDays();
		MUNSMPSProduct mpsProduct = (MUNSMPSProduct)mpsProductRsc.getUNS_MPSProduct();
		MUNSMPSProductWeekly prevDailyMPS = null;
		double prevPAB = 0.0;
		double prevStock = 0.0;
		double prevAccAtp = 0.0;
		if(null != prevMPSHeader)
		{
			Timestamp lastPrevDate = mpsHeader.getEndDate();
			prevDailyMPS = prevMPSHeader.getMPSProductPeriodic(
								M_Product_ID, 0, lastPrevDate);
			
			prevPAB = prevDailyMPS.getProjectedActualBalance().doubleValue();
			prevStock = prevDailyMPS.getStock().doubleValue();
			prevAccAtp = prevDailyMPS.getAcc_ATP().doubleValue();
		}
		for(MUNSMPSProductRscWeekly mpsProductRscDaily : mpsProductRsc.getLinesDayly(false))
		{
			cal.setTimeInMillis(mpsProductRscDaily.getMPSDate().getTime());
			int thisDay = cal.get(Calendar.DAY_OF_YEAR);
			MUNSMPSProductWeekly mpsProductDaily = mapOfMPSDaily.get(thisDay);
			if(null == mpsProductDaily)
				continue;
			
			if (thisDay == startDay
					&& null == prevDailyMPS)
			{
				prevPAB = onHandMT;
				prevStock = onHandMT;
			}
			
			double PAB = 0.0;
			double stock = 0.0;
			double atp = 0.0;
			mpsProductDaily.setActualToOrderMT(mpsProductRsc.getActualToOrderMT());
			mpsProductDaily.setActualToOrderUOM(mpsProductRsc.getActualToOrderUOM());
			mpsProductDaily.setActualToStockMT(mpsProductRsc.getActualToStockMT());
			mpsProductDaily.setActualToStockUOM(mpsProductRsc.getActualToStockUOM());
			mpsProductDaily.setQtyMT(mpsProductRsc.getActualToStockMT());
			mpsProductDaily.setQtyUom(mpsProductRsc.getActualToStockUOM());
			double qtyMT = mpsProductDaily.getQtyMT().doubleValue();
			double actualTOOrderMT = mpsProductDaily.getActualToOrderMT().doubleValue();
			double actualToStockMT = mpsProductDaily.getActualToStockMT().doubleValue();
			
			if (mpsProductDaily.getActualToOrderMT().compareTo(BigDecimal.ZERO)>0)
			{
				PAB = prevPAB + qtyMT - actualTOOrderMT;
				if (thisDay == startDay)
				{
					atp = onHandMT + qtyMT - SafetyStockMT 
							 - actualTOOrderMT;
				}
				else
				{
					atp = qtyMT - SafetyStockMT - actualTOOrderMT;
				}
		
				if(null != prevDailyMPS)
				{
					double prevDailyAtp = prevDailyMPS.getATP().doubleValue();
					prevDailyAtp -= actualTOOrderMT;
					prevDailyMPS.setATP(new BigDecimal(prevDailyAtp));
					Calendar calPrevMPS = Calendar.getInstance();
					calPrevMPS.setTimeInMillis(prevDailyMPS.getMPSDate().getTime());
					calPrevMPS.add(Calendar.DATE, -1);
					Timestamp dateMPS2HariSebelumnya = new Timestamp(calPrevMPS.getTimeInMillis());
					MUNSMPSProductWeekly mps2HariSebelumnya =
							prevMPSHeader.getMPSProductPeriodic(M_Product_ID
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
				PAB = prevPAB + qtyMT - actualToStockMT;
				if (thisDay == startDay)
				{
					atp = onHandMT + qtyMT - SafetyStockMT - actualToStockMT;
				}
				else
				{
					atp = qtyMT - SafetyStockMT
							 - actualToStockMT;
				}
				if(null != prevDailyMPS)
				{
					double prevDailyAtp = prevDailyMPS.getATP().doubleValue();
					prevDailyAtp -= actualToStockMT;
					prevDailyMPS.setATP(new BigDecimal(prevDailyAtp));
					Calendar calPrevMPS = Calendar.getInstance();
					calPrevMPS.setTimeInMillis(prevDailyMPS.getMPSDate().getTime());
					calPrevMPS.add(Calendar.DATE, -1);
					Timestamp dateMPS2HariSebelumnya = new Timestamp(calPrevMPS.getTimeInMillis());
					MUNSMPSProductWeekly mps2HariSebelumnya =
							prevMPSHeader.getMPSProductPeriodic(M_Product_ID
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
			
			
			Calendar calendar = (Calendar)cal.clone();
			calendar.add(Calendar.DATE, 1);
			MUNSMPSProductRscWeekly nextDailyMPSProductRsc =
					mapOfMPSproductRscDay.get(calendar.get(Calendar.DAY_OF_YEAR));
			if (null != nextDailyMPSProductRsc)
			{
				if(nextDailyMPSProductRsc.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
					atp -= nextDailyMPSProductRsc.getActualToOrderMT().doubleValue();
				else
					atp -= nextDailyMPSProductRsc.getActualToStockMT().doubleValue();
				
			}
			
			mpsProductDaily.setProjectedActualBalance(new BigDecimal(PAB));
			mpsProductDaily.setATP(new BigDecimal(atp));
			mpsProductDaily.setIncubationDays(incubationDay);
			
			long MiliSecondIncubDays = (long) (incubationDay * 24 * 60 * 60 * 1000) ;
					
			
			long StartIncubation = mpsProductDaily.getMPSDate().getTime();
			
			mpsProductDaily.setEndOfIncubation(new Timestamp(StartIncubation+MiliSecondIncubDays));
			mpsProductDaily.setStock(BigDecimal.ZERO); //Nanti Diisi dari PO
			mpsProductDaily.setQtyDelivered(BigDecimal.ZERO);
			stock = prevStock + qtyMT
						- mpsProductDaily.getQtyDelivered().doubleValue();
			prevPAB = PAB;
			prevStock = stock;
			
			double Acc_ATP = atp;
			Acc_ATP += prevAccAtp;

			mpsProductDaily.setAcc_ATP(new BigDecimal(Acc_ATP));
			mpsProductDaily.save();
			//ini pasti mps tanggal sebelumnya karena pada saat mengambil daily forecast sudah di sortir
			// berdasarkan tanggal
			prevAccAtp = Acc_ATP;
			
			int month = calendar.get(Calendar.MONTH);
			switch (month) {
			case 0 :
				actualToStockJanMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockJanUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 1 :
				actualToStockFebMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockFebUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 2 :
				actualToStockMarMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockMarUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 3 :
				actualToStockAprMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockAprUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 4 :
				actualToStockMayMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockMayUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 5 :
				actualToStockJunMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockJunUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 6 :
				actualToStockJulMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockJulUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 7 :
				actualToStockAugMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockAugUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 8 :
				actualToStockSepMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockSepUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 9 :
				actualToStockOctMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockOctUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 10 :
				actualToStockNovMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockNovUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			case 11 :
				actualToStockDecMT += mpsProductDaily.getActualToStockMT().doubleValue();
				actualToStockDecUOM += mpsProductDaily.getActualToStockUOM().doubleValue();
				break;
			default :;
			}
		}
		
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
		mpsProduct.save();
	}
	
	private void generateWeeklyMPSProduct(
			double onHand, 
			I_M_Product product,
			MUNSMPSHeader mpsHeader,
			MUNSMPSProductResource mpsProductRsc,
			Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSWeekly)
	{
		int weekStart = mpsHeader.getWeekStart();
		
		int M_Product_ID = product.getM_Product_ID();
		double weight = product.getWeight().doubleValue();
		double onHandMT = onHand * weight /1000;
		MUNSMPSHeader prevMPSHeader = (MUNSMPSHeader)mpsHeader.getPrevMPS();

		Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfMPSproductRscWeek =
				mpsProductRsc.getMapOfDailyMPSRsc();
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				getCtx(), M_Product_ID, get_TrxName());
		
		if (null == OtherProductConf)
		{
			throw new AdempiereException(
					"NO PRODUCT CONFIGURATION " + product.getName());
		}
		if (null == OtherProductConf.getSafetyStock())
		{
			throw new AdempiereException(
					"NO SAFETY STOCK PRODUCT CONFIGURATION " + product.getName());
		}
		
		double SafetyStockMT = OtherProductConf.getSafetyStock().doubleValue() * weight / 1000;
		

		MUNSMPSProductWeekly prevWeeklyMPS = null;
		double prevPAB = 0.0;
		double prevStock = 0.0;
		double prevAccAtp = 0.0;
		if(null != prevMPSHeader)
		{
			Timestamp lastPrevDate = mpsHeader.getEndDate();
			prevWeeklyMPS = prevMPSHeader.getMPSProductPeriodic(
								M_Product_ID, 0, lastPrevDate);
			
			prevPAB = prevWeeklyMPS.getProjectedActualBalance().doubleValue();
			prevStock = prevWeeklyMPS.getStock().doubleValue();
			prevAccAtp = prevWeeklyMPS.getAcc_ATP().doubleValue();
		}
		
		for(MUNSMPSProductRscWeekly productRscWeekly : mpsProductRsc.getLinesWeekly())
		{
			int weekNo = productRscWeekly.getWeekNo();
			MUNSMPSProductWeekly mpsProductWeekly = mapOfMPSWeekly.get(weekNo);
			if(null == mpsProductWeekly)
				continue;
			
			if(weekNo == weekStart
					&& null != prevWeeklyMPS)
			{
				prevPAB = onHand;
				prevStock = onHand;
			}
			
			double PAB = 0.0;
			double stock = 0.0;
			double atp = 0.0;
			mpsProductWeekly.setActualToOrderMT(mpsProductRsc.getActualToOrderMT());
			mpsProductWeekly.setActualToOrderUOM(mpsProductRsc.getActualToOrderUOM());
			mpsProductWeekly.setActualToStockMT(mpsProductRsc.getActualToStockMT());
			mpsProductWeekly.setActualToStockUOM(mpsProductRsc.getActualToStockUOM());
			mpsProductWeekly.setQtyMT(mpsProductRsc.getActualToStockMT());
			mpsProductWeekly.setQtyUom(mpsProductRsc.getActualToStockUOM());
			double qtyMT = mpsProductWeekly.getQtyMT().doubleValue();
			double actualTOOrderMT = mpsProductWeekly.getActualToOrderMT().doubleValue();
			double actualToStockMT = mpsProductWeekly.getActualToStockMT().doubleValue();
			
			if (mpsProductWeekly.getActualToOrderMT().compareTo(BigDecimal.ZERO)>0)
			{
				PAB = prevPAB + qtyMT - actualTOOrderMT;
				if (weekNo == weekStart)
				{
					atp = onHandMT + qtyMT - SafetyStockMT 
							 - actualTOOrderMT;
				}
				else
				{
					atp = qtyMT - SafetyStockMT - actualTOOrderMT;
				}
		
				if(null != prevWeeklyMPS)
				{
					double prevWeeklyAtp = prevWeeklyMPS.getATP().doubleValue();
					prevWeeklyAtp -= actualTOOrderMT;
					prevWeeklyMPS.setATP(new BigDecimal(prevWeeklyAtp));
					int prevWeek = prevWeeklyMPS.getWeekNo();
					int weekMPS2mingguYangLalu = prevWeek - 1;
					if(prevWeek == 1)
						weekMPS2mingguYangLalu = 53;
					MUNSMPSProductWeekly mps2MingguSebelumnya =
							prevMPSHeader.getMPSProductPeriodic(M_Product_ID
									, weekMPS2mingguYangLalu
									, null);
					if(null != mps2MingguSebelumnya)
					{
						double accAtp2MingguYangLalu = mps2MingguSebelumnya.getAcc_ATP().doubleValue();
						prevAccAtp = accAtp2MingguYangLalu + prevWeeklyAtp;
						prevWeeklyMPS.setAcc_ATP(new BigDecimal(prevAccAtp));
					}
					prevWeeklyMPS.save();
					prevWeeklyMPS = null;
				}
			}
			else
			{
				PAB = prevPAB + qtyMT - actualToStockMT;
				if (weekNo == weekStart)
				{
					atp = onHandMT + qtyMT - SafetyStockMT - actualToStockMT;
				}
				else
				{
					atp = qtyMT - SafetyStockMT
							 - actualToStockMT;
				}
				if(null != prevWeeklyMPS)
				{
					double prevDailyAtp = prevWeeklyMPS.getATP().doubleValue();
					prevDailyAtp -= actualToStockMT;
					prevWeeklyMPS.setATP(new BigDecimal(prevDailyAtp));
					int prevWeek = prevWeeklyMPS.getWeekNo();
					int weekMPS2mingguYangLalu = prevWeek - 1;
					if(prevWeek == 1)
						weekMPS2mingguYangLalu = 53;
					MUNSMPSProductWeekly mps2MingguSebelumnya =
							prevMPSHeader.getMPSProductPeriodic(M_Product_ID
									, weekMPS2mingguYangLalu
									, null);
					if(null != mps2MingguSebelumnya)
					{
						double accAtp2HariYangLalu = mps2MingguSebelumnya.getAcc_ATP().doubleValue();
						prevAccAtp = accAtp2HariYangLalu + prevDailyAtp;
						prevWeeklyMPS.setAcc_ATP(new BigDecimal(prevAccAtp));
					}
					prevWeeklyMPS.save();
					prevWeeklyMPS= null;
				}
			}
			
			
			MUNSMPSProductRscWeekly nextWeeklyMPSproductRsc =
					mapOfMPSproductRscWeek.get(weekNo+1);
			
			if (null != nextWeeklyMPSproductRsc)
			{
				if(nextWeeklyMPSproductRsc.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
					atp -= nextWeeklyMPSproductRsc.getActualToOrderMT().doubleValue();
				else
					atp -= nextWeeklyMPSproductRsc.getActualToStockMT().doubleValue();
			}

			
			mpsProductWeekly.setProjectedActualBalance(new BigDecimal(PAB));
			mpsProductWeekly.setATP(new BigDecimal(atp));
			
			mpsProductWeekly.setStock(BigDecimal.ZERO); //Nanti Diisi dari PO
			mpsProductWeekly.setQtyDelivered(BigDecimal.ZERO);
			mpsProductWeekly.setWeekNo(weekNo);
			stock = prevStock + mpsProductWeekly.getQtyMT().doubleValue() 
					- mpsProductWeekly.getQtyDelivered().doubleValue();
			prevPAB = PAB;
			prevStock = stock;
			
			double Acc_ATP = atp;
			Acc_ATP += prevAccAtp;
			
			mpsProductWeekly.setAcc_ATP(new BigDecimal(Acc_ATP));
			prevAccAtp = Acc_ATP;
			mpsProductWeekly.save();
			
		}
	}
	
	private void generateMPSProductPeriodic()
	{
		MUNSMPSHeader mpsHeader = new MUNSMPSHeader(
				getCtx(), getUNS_MPSHeader_ID(), get_TrxName());
		for(MUNSMPSProduct mpsProduct : mpsHeader.getLines())
		{
			Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSWeekly =
					mpsProduct.getMapOfWeeklyMPSproduct();
			
			Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSDaily =
					mpsProduct.getMapOfDailyMPSProduct();
			
			double onHand = mpsProduct.getInitialProjectedStock_OnHand().doubleValue();
			I_M_Product product = mpsProduct.getM_Product();
			
			if(mpsProduct.isGenerate())
				continue;
			
			for(MUNSMPSProductResource mpsProductRsc : mpsProduct.getLineProductRsc())
			{
				generateDailyMPSproduct(
						onHand, product, mpsHeader, mpsProductRsc, mapOfMPSDaily);
				generateWeeklyMPSProduct(
						onHand, product, mpsHeader, mpsProductRsc, mapOfMPSWeekly);
			}
		}
	}
}
