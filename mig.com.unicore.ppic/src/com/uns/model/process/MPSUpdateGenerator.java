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
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Period;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSOtherProductConf;
import com.uns.util.MessageBox;

import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;
import com.uns.model.I_UNS_Forecast_Header;
import com.uns.model.MUNSContainerDeparture;
import com.uns.model.MUNSForecastHeader;
import com.uns.model.MUNSMOScheduler;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductResource;
import com.uns.model.MUNSMPSProductRscWeekly;
import com.uns.model.MUNSMPSProductWeekly;
import com.uns.model.MUNSProductionSchedule;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.MUNSSOAmendment;
import com.uns.model.MUNSSOAmendmentLine;
import com.uns.model.MUNSSOShipment;
import com.uns.model.MUNSShipmentSchedule;

/**
 * @author menjangan
 *
 */
public class MPSUpdateGenerator extends SvrProcess {
	
	private String m_ProcessMsg = null;
	private static final String TYPE_ORDER = "O";
	private static final String TYPE_SHIPMENT_SCHEDULE = "SS";
	private MUNSMPSHeader m_MPSHeader = null;
	private MOrder m_Order = null;
	private MUNSShipmentSchedule m_ShipmentSchedule = null;
	private ArrayList<ResourceProportion> m_rscProportion = new ArrayList<ResourceProportion>();
	private String m_TrxName = null;
	private Properties m_Ctx = null;
	private MUNSMPSHeader m_PrevMPSHeader = null;
	
	/**
	 * 
	 * @param amendment
	 */
	public MPSUpdateGenerator(MUNSSOAmendment amendment)
	{	
		this.m_TrxName = amendment.get_TrxName();
		this.m_Ctx = amendment.getCtx();
		this.m_Order = (MOrder)amendment.getC_Order();
		this.m_MPSHeader = MUNSMPSHeader.get(
				m_Order.getDateOrdered(), m_TrxName, m_Order.getAD_Org_ID());
		if(m_MPSHeader.getPrevMPS_ID() > 0)
			m_PrevMPSHeader = (MUNSMPSHeader) m_MPSHeader.getPrevMPS();
	}
	
	
	/**
	 * 
	 * @param mapOfAmendmentLine
	 */
	public void updateMPS(Hashtable<Integer, MUNSSOAmendmentLine> mapOfAmendmentLine)
	{
		
		for(MUNSMPSProduct mpsProduct : m_MPSHeader.getLines())
		{
			MUNSSOAmendmentLine amendmentLineTmp =
					mapOfAmendmentLine.get(mpsProduct.getM_Product_ID());
			if(null == amendmentLineTmp)
				continue;
			
			double oldQty = amendmentLineTmp.getPrevQuantity().doubleValue();
			double newQty = amendmentLineTmp.getQuantity().doubleValue();
			
			updateMPSProductRsc(mpsProduct, newQty, oldQty, 0.00);
		}
	}
	
	
	/**
	 * 
	 * @param mpsProductRsc
	 * @param mapOfDailyMPSRsc
	 * @param orderQty
	 * @param dayOfYear
	 */
	private void updateMPSProductRscDaily(
			MUNSMPSProductResource mpsProductRsc,
			Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfDailyMPSRsc,
			double orderQty,double oldOrderQty, double shipmentQty, Calendar cal)
	{
		int M_Product_ID = mpsProductRsc.getUNS_MPSProduct().getM_Product_ID();
		double Weight = mpsProductRsc.getUNS_MPSProduct().getM_Product().getWeight().doubleValue();
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		MUNSMPSProduct mpsProduct = (MUNSMPSProduct)mpsProductRsc.getUNS_MPSProduct();
		
		MUNSMPSProductRscWeekly mpsProductRscDaily = mapOfDailyMPSRsc.get(dayOfYear);
		
		if(null == mpsProductRscDaily)
			return ;
		
		List<MUNSProductionSchedule> listOfProductionSchedule = 
				MUNSProductionSchedule.getByMPS(m_Ctx, mpsProductRscDaily.get_ID(), m_TrxName);
		
		for(MUNSProductionSchedule ps : listOfProductionSchedule)
		{
			boolean isScheduled = MUNSMOScheduler.isScheduledToProduction(m_Ctx, ps.get_ID(), m_TrxName);
			if(isScheduled)
				throw new AdempiereUserError(
						"Tidak dapat mengubah mps yang sudah digunakan " +
						" oleh manufacturing order yang sudah dijadwalkan.");
			if(ps.hasRealization())
				throw new AdempiereUserError(
						"Tidak dapat mengubah mps yang sudah direalisasikan");
			else
			{
				try {
					ps.processIt(DocAction.ACTION_Void);
				}catch(Exception ex) {
					throw new AdempiereUserError(ex.getMessage());
				}
			}
		}
		
		double qtyMTToAllocate = orderQty * Weight / 1000;
		double qtyUOMToAllocate = orderQty;
		double totalRealAllocatedUOM = 0.0;
		for(ResourceProportion rscProportion : m_rscProportion)
		{
			if(mpsProductRsc.getUNS_Resource_ID() != rscProportion.getResource_ID())
				continue;
			
			if(qtyUOMToAllocate <= 0.0 || qtyMTToAllocate <= 0.0)
				continue;
			
			double capacitiesOfResourcePerHours = rscProportion.getActualMaxCaps();
			double capacitiesMTOfResourcePerHours = rscProportion.getActualMaxCapsMT();
			
			
			MUNSResource rsc = (MUNSResource)mpsProductRsc.getUNS_Resource();
			MUNSResourceInOut output = rsc.getResourceOutput(M_Product_ID);
			
			if(null == output)
				continue;
			
			double totalProductionTimeInDay = output.getDayProductionHours(
					cal.get(Calendar.DAY_OF_WEEK)).doubleValue();
			
			double totalProductionInDay = totalProductionTimeInDay * capacitiesOfResourcePerHours;
			double totalProductionMTInDay = totalProductionTimeInDay *  capacitiesMTOfResourcePerHours;
			double realQtyUOMToAllocate = 0.0;
			double realQtyMTToAllocate = 0.0;
			double oldOrderQtyMT = oldOrderQty * Weight /1000;
			double oldQtyUOM = mpsProductRscDaily.getActualToOrderUOM().doubleValue();
			oldQtyUOM -= oldOrderQty;
			double oldQtyMT = mpsProductRscDaily.getActualToOrderMT().doubleValue();
			oldQtyMT -= oldOrderQtyMT;
			double newActualToOrderMT = oldQtyMT + qtyMTToAllocate;
			double newActualToOrderUOM = oldQtyUOM + qtyUOMToAllocate;
			
			if(newActualToOrderMT-totalProductionMTInDay > 0.0
					|| newActualToOrderUOM - totalProductionInDay > 0.0)
			{
				realQtyMTToAllocate = totalProductionMTInDay;
				realQtyUOMToAllocate = totalProductionInDay;
				qtyMTToAllocate -= totalProductionMTInDay;
				qtyUOMToAllocate -= totalProductionInDay;
			}
			else
			{
				realQtyMTToAllocate = newActualToOrderMT;
				realQtyUOMToAllocate = newActualToOrderUOM;
				qtyMTToAllocate = 0.0;
				qtyUOMToAllocate = 0.0;
			}
			
			mpsProductRscDaily.setActualToOrderMT(new BigDecimal(realQtyMTToAllocate));
			mpsProductRscDaily.setActualToOrderUOM(new BigDecimal(realQtyUOMToAllocate));
			
			if(!mpsProductRscDaily.save())
				throw new AdempiereException("Failed to update daily MPS Product Resource");
			
			if(!updateMPSProductRscWeekly(mpsProductRscDaily))
				throw new AdempiereException("Failed to update weekly MPS Product Resource");
			
			//update MPSProductRsc
			oldQtyMT = mpsProductRsc.getActualToOrderMT().doubleValue();
			oldQtyUOM = mpsProductRsc.getActualToOrderUOM().doubleValue();
			oldQtyMT -= oldOrderQtyMT;
			oldQtyUOM -= oldOrderQty;
			newActualToOrderMT = oldQtyMT + realQtyMTToAllocate;
			newActualToOrderUOM = oldQtyUOM + realQtyUOMToAllocate;
			mpsProductRsc.setActualToOrderMT(new BigDecimal(newActualToOrderMT));
			mpsProductRsc.setActualToOrderUOM(new BigDecimal(newActualToOrderUOM));
			if(!mpsProductRsc.save())
				throw new AdempiereException("Failed to update MPS Product Resource");
			
			oldQtyMT = mpsProduct.getActualToOrderMT(cal.get(Calendar.MONTH)).doubleValue();
			oldQtyUOM = mpsProduct.getActualToOrderUOM(cal.get(Calendar.MONTH)).doubleValue();
			oldQtyMT -= oldOrderQtyMT;
			oldQtyUOM -= oldOrderQty;
			newActualToOrderMT = oldQtyMT + realQtyMTToAllocate;
			newActualToOrderUOM = oldQtyUOM + realQtyUOMToAllocate;
			mpsProduct.setActualToOrderMT(cal.get(Calendar.MONTH), new BigDecimal(newActualToOrderMT));
			mpsProduct.setActualToOrderUOM(cal.get(Calendar.MONTH), new BigDecimal(newActualToOrderUOM));
			if(!mpsProduct.save())
				throw new AdempiereException("Failed to update MPS Product Resource");
			
			totalRealAllocatedUOM += realQtyUOMToAllocate;
		}
		
		if(m_Order.getDatePromised().equals(new Timestamp(cal.getTimeInMillis()))
				&& qtyUOMToAllocate > 0.0)
		{
			int retValue =	MessageBox.showMsg(m_Ctx, getProcessInfo(), "Quantity " +
									qtyUOMToAllocate + " " + mpsProduct.getC_UOM().getName() + " " 
									+ mpsProduct.getM_Product().getName() 
									+ " tidak terpenuhi, Apakah anda tetap ingin melanjutkan?", 
									"Peringatan", 
									MessageBox.YESNO, 
									MessageBox.ICONINFORMATION);
			
			if(MessageBox.RETURN_NO == retValue)
				throw new AdempiereException("Order Allocation aborted.");
		}
		
		updateDailyMPS(mpsProduct, cal, totalRealAllocatedUOM, oldOrderQty, shipmentQty);
		updateWeeklyMPS(mpsProduct, cal, totalRealAllocatedUOM, oldOrderQty, shipmentQty);
		
		// juka produksi tidak terpenuhi di tanggal ini makan akan dialokasikan ke hari berikutnya
		if(qtyUOMToAllocate > 0.0)
		{
			cal.add(Calendar.DATE, 1);
			updateMPSProductRscDaily(
					mpsProductRsc, mapOfDailyMPSRsc, qtyUOMToAllocate, 0.00, shipmentQty, cal);
		}
	}
	
	/**
	 * 
	 * @param mpsProductRsc
	 * @param Qty
	 * @param shipmentQty
	 */
	protected void updateMPSProductRscDaily(
			MUNSMPSProductResource mpsProductRsc, double Qty, 
			double oldQty, double shipmentQty)
	{
		Hashtable<Integer, MUNSMPSProductRscWeekly> mapOfWeeklyMPSRsc = mpsProductRsc.getMapOfDailyMPSRsc();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(m_Order.getDateOrdered().getTime());
		
		updateMPSProductRscDaily(mpsProductRsc, mapOfWeeklyMPSRsc, Qty, oldQty, shipmentQty, cal);
	}
	
	
	/**
	 * 
	 * @param dailyProductRscMPS
	 * @return
	 */
	protected boolean updateMPSProductRscWeekly(MUNSMPSProductRscWeekly dailyProductRscMPS)
	{
		MUNSMPSProductRscWeekly mpsProductRscWeekly =
				new MUNSMPSProductRscWeekly(
						m_Ctx, dailyProductRscMPS.getWeekly_ID(), m_TrxName);
		double oldQtyUOM = mpsProductRscWeekly.getActualToOrderUOM().doubleValue();
		double oldQtyMT = mpsProductRscWeekly.getActualToOrderMT().doubleValue();
		double newActualToOrderMT = oldQtyMT + dailyProductRscMPS.getActualToOrderMT().doubleValue();
		double newActualToOrderUOM = oldQtyUOM + dailyProductRscMPS.getActualToOrderUOM().doubleValue();
		mpsProductRscWeekly.setActualToOrderMT(new BigDecimal(newActualToOrderMT));
		mpsProductRscWeekly.setActualToOrderUOM(new BigDecimal(newActualToOrderUOM));
		if(!mpsProductRscWeekly.save())
			return false;
		
		return true;
	}
	
	
	/**
	 * 
	 * @param mpsProduct
	 * @param newQty
	 * @param shipmentQty
	 */
	protected void updateMPSProductRsc(MUNSMPSProduct mpsProduct, double newQty, double oldQty, double shipmentQty)
	{
		I_UNS_Forecast_Header forecastHeader = m_MPSHeader.getUNS_Forecast_Header();
		String AllocationMethod = forecastHeader.getAllocationMethod();
		initProportion(mpsProduct, AllocationMethod);
		
		for(MUNSMPSProductResource mpsProductRsc : mpsProduct.getLineProductRsc())
		{
			updateMPSProductRscDaily(mpsProductRsc, newQty, oldQty, shipmentQty);
		}
	}
	
	
	/**
	 * 
	 * @param mapOfQty
	 */
	protected void updateMPSproduct(Hashtable<String, Double> mapOfQty)
	{
		for(MUNSMPSProduct mpsProduct : m_MPSHeader.getLines(false))
		{
			Double orderQty = mapOfQty.get(
					generateKey(TYPE_ORDER, mpsProduct.getM_Product_ID()));
			Double shipmentQty = mapOfQty.get(
					generateKey(TYPE_SHIPMENT_SCHEDULE, mpsProduct.getM_Product_ID()));
			if(null == orderQty && null == shipmentQty)
				continue;
			
			if(null == orderQty)
				orderQty = 0.0;
			if(null == shipmentQty)
				shipmentQty = 0.0;
			
			updateMPSProductRsc(mpsProduct, orderQty, 0.00, shipmentQty);
		}
	}

	
	/**
	 * 
	 */
	public MPSUpdateGenerator() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		this.m_TrxName = get_TrxName();
		this.m_Ctx = getCtx();
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i<param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals("C_Order_ID"))
			{
				m_Order = new MOrder(m_Ctx, param[i].getParameterAsInt(), m_TrxName);
			}
			else if (paramName.equals("UNS_ShipmentSchedule_ID"))
			{
				m_ShipmentSchedule = 
						new MUNSShipmentSchedule (m_Ctx, param[i].getParameterAsInt(), m_TrxName);
			}
		}
		
		m_MPSHeader = new MUNSMPSHeader(m_Ctx, getRecord_ID(), m_TrxName);
	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		if (null == m_Order && null == m_ShipmentSchedule)
		{
			m_ProcessMsg = "Please choise production or receipt at the list";
			throw new IllegalArgumentException(m_ProcessMsg);
		}
		
		Hashtable<String, Double> mapQuantityToUpdate = new Hashtable<String, Double>();
		
		for (MOrderLine orderLine : m_Order.getLines())
		{
			String key = generateKey(TYPE_ORDER, orderLine.getM_Product_ID());
			Double qty = mapQuantityToUpdate.get(key);
			if (null == qty)
			{
				qty = orderLine.getQtyEntered().doubleValue();
				mapQuantityToUpdate.put(key, qty);
			}
			else
				qty += orderLine.getQtyEntered().doubleValue();				
		}
	
		for (MUNSContainerDeparture cd : m_ShipmentSchedule.getContainerDeparture())
		{
			for (MUNSSOShipment soShipment : cd.getSOShipments())
			{
				String key = generateKey(TYPE_SHIPMENT_SCHEDULE, soShipment.getM_Product_ID());
				Double qty = mapQuantityToUpdate.get(key);
				if (null == qty)
				{
					qty = soShipment.getQtyUom().doubleValue();
					mapQuantityToUpdate.put(key, qty);
				}
				else
					qty += soShipment.getQtyUom().doubleValue();
			}
		}
		
		updateMPSproduct(mapQuantityToUpdate);
		
		MUNSMPSHeader nextMPSHeader = m_MPSHeader.getNextMPS();
		if(null != nextMPSHeader)
		{
			nextMPSHeader.updateTerhadapPerubahanMPSSebelumnnya(m_MPSHeader);
		}
		return m_ProcessMsg;
	}
	
	
	/**
	 * 
	 * @param type
	 * @param M_Product_ID
	 * @return
	 */
	private String generateKey(String type, int M_Product_ID)
	{
		return type+"_"+M_Product_ID;
	}	
	

	/**
	 * 
	 * @param mpsProduct
	 * @param calendar
	 * @param orderQty
	 * @param shipmentQty
	 */
	private void updateWeeklyMPS(MUNSMPSProduct mpsProduct, Calendar calendar,
			Double orderQty, double oldOrderQty, Double shipmentQty)
	{
		Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSWeekly = 
				new Hashtable<Integer, MUNSMPSProductWeekly>();
		mapOfMPSWeekly = mpsProduct.getMapOfWeeklyMPSproduct();
		
		double weight = mpsProduct.getM_Product().getWeight().doubleValue();
		double onHandMT = mpsProduct.getInitialProjectedStock_OnHand().doubleValue() * weight;
		double oldOrderQtyMT = oldOrderQty * weight / 1000;
		
		calendar = Calendar.getInstance();
		int weekOfYearOrder = 100;
		int weekOfYearShipment = 100;
		double actualToOrderUOM = 0.0;
		double actualToOrderMT = 0.0;
		double qtyDeliveledMT = 0.0;
		
		if (null != orderQty && orderQty > 0.0)
		{
			actualToOrderUOM = orderQty;
			actualToOrderMT = actualToOrderUOM * weight * 0.001;
			weekOfYearOrder = calendar.get(Calendar.WEEK_OF_YEAR);
		}
		if (null != shipmentQty && shipmentQty > 0)
		{
			calendar.setTimeInMillis(m_ShipmentSchedule.getETA().getTime());
			qtyDeliveledMT = shipmentQty * weight * 0.001;
			weekOfYearShipment = calendar.get(Calendar.WEEK_OF_YEAR);
		}
		
		if (null == orderQty && null == shipmentQty)
			return;
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				m_Ctx, mpsProduct.getM_Product_ID(), m_TrxName);
		
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
			Calendar calPrevWeekly = Calendar.getInstance();
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
			if (weekNo >= weekOfYearOrder || weekNo >= weekOfYearShipment)
			{				
				if (weekNo == weekOfYearOrder)
				{
					if (actualToOrderMT > 0.0)
					{
						double oldQtyMT = 0.0;
						double oldQtyUOM = 0.0;
						double newQtyUOM = 0.0;
						double newQtyMT = 0.0;
						
						newQtyUOM = actualToOrderUOM;
						newQtyMT = actualToOrderMT;
						if(mpsWeekly.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
						{
							oldQtyUOM = mpsWeekly.getActualToOrderUOM().doubleValue();
							oldQtyMT = mpsWeekly.getActualToOrderMT().doubleValue();
							oldQtyUOM -= oldOrderQty;
							oldQtyMT -= oldOrderQtyMT;
							newQtyUOM = oldQtyUOM + actualToOrderUOM;
							newQtyMT = oldQtyMT + actualToOrderMT;
						}
						else
						{
							oldQtyUOM = mpsWeekly.getActualToStockUOM().doubleValue();
							oldQtyMT = mpsWeekly.getActualToStockMT().doubleValue();
						}
						
						
						mpsWeekly.setActualToOrderUOM(new BigDecimal(newQtyUOM));
						mpsWeekly.setActualToOrderMT(new BigDecimal(newQtyMT));
						
						//update mrp
						/*
						MRPUpdateGenerator mrpUpdate = new MRPUpdateGenerator(m_MPSHeader);		
						mrpUpdate.updateByMPS(mpsProduct.get_ID(),mpsProduct.getM_Product_ID()
								, m_Order.getDateOrdered(),mpsWeekly.getActualToOrderUOM()
								, new BigDecimal(oldQtyUOM));
						*/
						MUNSMPSProductWeekly mps2MingguSebelumnya = null;
						if(weekNo-1 == m_MPSHeader.getWeekStart())
							mps2MingguSebelumnya = prevMPSProductWeekly;
						
						if(weekNo > m_MPSHeader.getWeekStart())
							prevMPSProductWeekly = mapOfMPSWeekly.get(weekNo - 1);
						
						if(null != prevMPSProductWeekly)
						{
							//mengubah atp seminggu sebelumnya
							double prevATP = prevMPSProductWeekly.getATP().doubleValue();
							prevATP += mpsWeekly.getActualToStockMT().doubleValue();
							double newPrevAtp = prevATP - mpsWeekly.getActualToOrderMT().doubleValue();
							prevMPSProductWeekly.setATP(new BigDecimal(newPrevAtp));
							prevATP = newPrevAtp;
							
							if(null == mps2MingguSebelumnya)
								mps2MingguSebelumnya = mapOfMPSWeekly.get(weekNo -2);
							
							if(null != mps2MingguSebelumnya)
							{
								prevAccAtp = mps2MingguSebelumnya.getAcc_ATP().doubleValue()
										+ newPrevAtp;
								prevMPSProductWeekly.setAcc_ATP(new BigDecimal(prevAccAtp));
							}
							prevMPSProductWeekly.save();
						}
					}
				}
				if (weekNo == weekOfYearShipment)
				{
					if (qtyDeliveledMT > 0.0)
						mpsWeekly.setQtyDelivered(mpsWeekly.getQtyDelivered().add(new BigDecimal(qtyDeliveledMT)));
				}
				
				if (weekNo == m_MPSHeader.getWeekStart()
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
					if(weekNo == m_MPSHeader.getWeekStart()
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
					if (weekNo == m_MPSHeader.getWeekStart()
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
			}
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
	private void updateDailyMPS(
			MUNSMPSProduct mpsProduct, 
			Calendar calendar, 
			Double orderQty, 
			double oldOrderQty,
			Double shipmentQty)
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
		
		Hashtable<Integer, MUNSMPSProductWeekly> mapOfMPSDaily = 
									new Hashtable<Integer, MUNSMPSProductWeekly>();
		mapOfMPSDaily = mpsProduct.getMapOfDailyMPSProduct();
		
		int DayOfYearOrder = 1000;
		int DayOfYearShipment = 1000;
		double actualOrderMT = 0.0;
		double actualOrderUOM = 0.0;
		double QtyDeliveredUOM = 0.0;
		double qtyDeliveledMT = 0.0;
		double weight = mpsProduct.getM_Product().getWeight().doubleValue();
		double onHandMT = mpsProduct.getInitialProjectedStock_OnHand().doubleValue()
				* weight * 0.001;
		double oldOrderQtyMT = oldOrderQty * weight / 1000;
		
		if (null != orderQty && orderQty > 0.0)
		{
			actualOrderUOM = orderQty;
			actualOrderMT = actualOrderUOM * weight * 0.001;
			DayOfYearOrder = calendar.get(Calendar.DAY_OF_YEAR);
		}
		
		if (null != shipmentQty && shipmentQty > 0.0 )
		{
			calendar.setTimeInMillis(m_ShipmentSchedule.getETA().getTime());
			QtyDeliveredUOM = shipmentQty;
			qtyDeliveledMT = QtyDeliveredUOM * weight * 0.001;
			DayOfYearShipment = calendar.get(Calendar.DAY_OF_YEAR);
		}
		
		if (null == orderQty && null == shipmentQty)
			return;
		
		MUNSOtherProductConf OtherProductConf = MUNSOtherProductConf.get(
				m_Ctx, mpsProduct.getM_Product_ID(), m_TrxName);
		
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
			Calendar calPrevWeekly = Calendar.getInstance();
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
			Calendar mpsCalendar = Calendar.getInstance();
			mpsCalendar.setTimeInMillis(mpsProductDaily.getMPSDate().getTime());
			int dayStart =  mpsCalendar.get(Calendar.DAY_OF_YEAR);
			int dayOfMPS = mpsCalendar.get(Calendar.DAY_OF_YEAR);
			double PAB = mpsProductDaily.getProjectedActualBalance().doubleValue();
			double stock = mpsProductDaily.getStock().doubleValue();
			double atp = mpsProductDaily.getATP().doubleValue();
			double Acc_ATP = mpsProductDaily.getAcc_ATP().doubleValue();
			if (dayOfMPS >= DayOfYearOrder || dayOfMPS >= DayOfYearShipment)
			{
				
				if (dayOfMPS == DayOfYearOrder
						&& actualOrderMT > 0.0)
				{							
					double oldQtyMT = 0.0;
					double oldQtyUOM = 0.0;
					double newQtyUOM = 0.0;
					double newQtyMT = 0.0;
					
					newQtyUOM = actualOrderUOM;
					newQtyMT = actualOrderMT;
					if(mpsProductDaily.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
					{
						oldQtyUOM = mpsProductDaily.getActualToOrderUOM().doubleValue();
						oldQtyMT = mpsProductDaily.getActualToOrderMT().doubleValue();
						oldQtyUOM -= oldOrderQty;
						oldQtyMT -= oldOrderQtyMT;
						newQtyUOM = oldQtyUOM + actualOrderUOM;
						newQtyMT = oldQtyMT + actualOrderMT;
					}
					else
					{
						oldQtyUOM = mpsProductDaily.getActualToStockUOM().doubleValue();
						oldQtyMT = mpsProductDaily.getActualToStockMT().doubleValue();
					}
					
					mpsProductDaily.setActualToOrderMT(new BigDecimal(newQtyMT));
					mpsProductDaily.setActualToOrderUOM(new BigDecimal(newQtyUOM));
					
					MUNSMPSProductWeekly mps2HariSebelumnya = null;
					
					if(dayOfMPS-1 == dayStart)
						mps2HariSebelumnya = prevMPSproductDaily;
					if(dayOfMPS > dayStart)
						prevMPSproductDaily = mapOfMPSDaily.get(dayOfMPS - 1);
					
					if(null != prevMPSproductDaily)
					{
						//mengubah atp sehari sebelumnya
						double prevATP = prevMPSproductDaily.getATP().doubleValue();
						prevATP += mpsProductDaily.getActualToStockMT().doubleValue();
						double newPrevAtp = prevATP - mpsProductDaily.getActualToOrderMT().doubleValue();
						prevMPSproductDaily.setATP(new BigDecimal(newPrevAtp));
						prevATP = newPrevAtp;
						
						if(null == mps2HariSebelumnya)
							mps2HariSebelumnya = mapOfMPSDaily.get(dayOfMPS - 2);
						
						if(null != mps2HariSebelumnya)
						{
							prevAccAtp = mps2HariSebelumnya.getAcc_ATP().doubleValue()
									+ newPrevAtp;
							prevMPSproductDaily.setAcc_ATP(new BigDecimal(prevAccAtp));
						}
						prevMPSproductDaily.save();
					}
				}
				
				if (dayOfMPS == DayOfYearShipment)
				{
					if (qtyDeliveledMT > 0.0)
					{
						double oldQtyDeliv = mpsProductDaily.getQtyDelivered().doubleValue();
						double newQtydeliv = oldQtyDeliv + qtyDeliveledMT;
						mpsProductDaily.setQtyDelivered(new BigDecimal(newQtydeliv));	
					}
				}
				
				
				if (dayOfMPS == m_MPSHeader.getStartDay()
						&& null == prevMPSproductDaily)
				{
					prevPAB = onHandMT;
					prevStock = onHandMT;
				}
				
				if (mpsProductDaily.getActualToOrderMT().compareTo(BigDecimal.ZERO) > 0)
				{
					PAB = prevPAB + mpsProductDaily.getQtyMT().doubleValue()
							- mpsProductDaily.getActualToOrderMT().doubleValue();
					if(dayOfMPS == m_MPSHeader.getStartDay())
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
					if (dayOfMPS == m_MPSHeader.getStartDay())
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
				
				long MiliSecondIncubDays = (incubationDay* 24* 60* 60 * 1000);
				
				long StartIncubation = mpsProductDaily.getMPSDate().getTime();
				
				mpsProductDaily.setEndOfIncubation(new Timestamp(StartIncubation+MiliSecondIncubDays));
				stock = prevStock + mpsProductDaily.getQtyMT().doubleValue()
						- mpsProductDaily.getQtyDelivered().doubleValue();
//				MPSProductDayly.setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
				
				Acc_ATP = atp;
				Acc_ATP += prevAccAtp;
				
				mpsProductDaily.setAcc_ATP(new BigDecimal(Acc_ATP));
				//ini pasti mps tanggal sebelumnya karena pada saat mengambil daily forecast sudah di sortir
				// berdasarkan tanggal
				mpsProductDaily.save();
			}
			prevPAB = PAB;
			prevStock = stock;
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
		
	}	
	
	
	/**
	 * 
	 * @param mpsProduct
	 * @param allocationMethod
	 */
	private void initProportion(MUNSMPSProduct mpsProduct, String allocationMethod)
	{
		MUNSMPSProductResource[] mpsProductRscs = mpsProduct.getLineProductRsc();
		BigDecimal totalProductInAllResource = BigDecimal.ZERO;
		for (MUNSMPSProductResource mpsPrdRsc : mpsProductRscs)
		{
			MUNSResource resourceOfProduct = (MUNSResource)mpsPrdRsc.getUNS_Resource();
			BigDecimal maximumQuantity = resourceOfProduct.getTotalActualMaxCapsOf(mpsProduct.getM_Product_ID());
			BigDecimal maximumQuantityMT = resourceOfProduct.getTotalActualMaxCapsMTOf(mpsProduct.getM_Product_ID());
			totalProductInAllResource = totalProductInAllResource.add(maximumQuantity);
			ResourceProportion rscProportion = new ResourceProportion(
					mpsPrdRsc.getUNS_Resource_ID(), mpsProduct.getM_Product_ID());
			rscProportion.setActualMaxCaps(maximumQuantity);
			rscProportion.setActualMaxCapsMT(maximumQuantityMT);
			m_rscProportion.add(rscProportion);
		}
			
		if (allocationMethod.equals(MUNSForecastHeader.ALLOCATIONMETHOD_Proportion))
		{
			for (ResourceProportion rscProportion : m_rscProportion)
			{
				if (rscProportion.getM_product_ID() == mpsProduct.getM_Product_ID())
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
