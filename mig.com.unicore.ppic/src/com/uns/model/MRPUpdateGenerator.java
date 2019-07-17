package com.uns.model;
/**
 * 
 *
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;

import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MProductBOM;
import org.compiere.model.PO;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 *
public class MRPUpdateGenerator {
	
	private MUNSMPSHeader m_MPSHeader = null;
	private Hashtable<Integer, MUNSMRP> m_MapOfMRP = new Hashtable<Integer, MUNSMRP>();
	private Hashtable<String, MUNSMRPWeekly> m_MapOfMRPWeekly = new Hashtable<String, MUNSMRPWeekly>();
	private static final String TYPE_SCHEDULE_RECEIPT = "ScheduleReceipt";
	private static final String TYPE_PRODUCTION = "Production";
	private static final String TYPE_MPS = "MPS";
	private int m_OrderLine_ID = 0;
	private int m_WeekStart = 0;
	private Properties m_Ctx = null;
	private String m_TrxName = null;

	/**
	 * 
	 *
	public MRPUpdateGenerator(MUNSMPSHeader mpsHeader) {
		// TODO Auto-generated constructor stub
		this.m_MPSHeader = mpsHeader;
		this.m_WeekStart = mpsHeader.getWeekStart();
		this.m_Ctx = mpsHeader.getCtx();
		this.m_TrxName = mpsHeader.get_TrxName();
		prepare();
	}
	/*
	private void initMRP()
	{
		for (MUNSMRP listMRP : m_MPSHeader.getListMRP(false))
		{
			m_MapOfMRP.put(listMRP.getM_Product_ID(), listMRP);
		}
	}
	
	/**
	 * Initial all requirement for generate update MRP
	 *
	private void prepare()
	{
		initMRP();
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param UNS_MRP_ID
	 * @param WeekNo
	 * @return
	 *
	private String generateKeyOfMap(int M_Product_ID, int UNS_MRP_ID, int WeekNo)
	{
		return M_Product_ID +"_"+ UNS_MRP_ID +"_"+ WeekNo;
	}
	
	/**
	 * 
	 * @param mrp
	 *
	private void initMRPWeeklyOf(MUNSMRP mrp)
	{
		m_MapOfMRPWeekly.clear();
		for (MUNSMRPWeekly mrpWeekly : mrp.getWeeklysLines())
		{
			String key = generateKeyOfMap(mrp.getM_Product_ID(), mrp.getUNS_MRP_ID(), mrpWeekly.getWeekNo());
			m_MapOfMRPWeekly.put(key, mrpWeekly);
		}
	}
	
	public String updateByMPS(int UNS_MPSProduct_ID, int M_Product_ID, Timestamp date, BigDecimal qty,
			 BigDecimal oldQty)
	{
		for (MProductBOM bom : MProductBOM.getBOMLines(
				Env.getCtx(), M_Product_ID, null))
		{
			MUNSMRP mrp = m_MapOfMRP.get(bom.getM_ProductBOM_ID());
			if (null == mrp)
				continue;
			
			BigDecimal newQty = qty.multiply(bom.getBOMQty());
			BigDecimal prevQty = oldQty.multiply(bom.getBOMQty());
			prevQty.setScale(2, RoundingMode.HALF_UP);
			
			for(MUNSMRP mrpDetail : mrp.getChilds())
			{
				if(mrpDetail.getUNS_MPSProduct_ID() != UNS_MPSProduct_ID)
					continue;
				
				initMRPWeeklyOf(mrpDetail);
				updateMRPWeeklyDetail(mrpDetail, newQty, prevQty, date, TYPE_MPS);
				
				initMRPWeeklyOf(mrp);
				updateMRPWeekly(mrp, newQty, prevQty, date, TYPE_MPS);
			}
			
			MUNSMPSHeader nextMPS = m_MPSHeader.getNextMPS();
			if(null == nextMPS)
				continue;
			
			nextMPS.updateTerhadapPerubahanMRPSebelumnya(M_Product_ID);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @param summary
	 * @param newQty
	 * @param oldQty
	 * @param date
	 * @param type
	 *
	private void updateMRPWeeklyDetail(
			MUNSMRP mrp, BigDecimal newQty, BigDecimal oldQty, Timestamp date, String type)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int weekToUpdate = calendar.get(Calendar.WEEK_OF_YEAR);
		if(weekToUpdate == 1 && calendar.get(Calendar.DAY_OF_YEAR) > 350)
			weekToUpdate = 53;
		
		for (MUNSMRPWeekly mrpWeekly : mrp.getWeeklysLines())
		{
			if(mrpWeekly.getWeekNo() != weekToUpdate)
				continue;
			
			if (type.equals(TYPE_SCHEDULE_RECEIPT))
			{
				I_M_RequisitionLine reqLine = mrpWeekly.getRequisitionRef();
				if (reqLine.getC_OrderLine_ID() != m_OrderLine_ID)
					continue;
				
				BigDecimal prevQty = mrpWeekly.getGRManufacturing();
				if(!mrpWeekly.isUpdated())
				{
					prevQty = BigDecimal.ZERO;
					mrpWeekly.setIsUpdated(true);
				}
				newQty = prevQty.add(newQty);
				mrpWeekly.setScheduleReceipt(newQty);
			}
			else if (type.equals(TYPE_PRODUCTION))
			{
				BigDecimal prevQty = mrpWeekly.getGRManufacturing();
				if(!mrpWeekly.isUpdated())
				{
					prevQty = BigDecimal.ZERO;
					mrpWeekly.setIsUpdated(true);
				}
				newQty = prevQty.add(newQty);
				mrpWeekly.setGRManufacturing(newQty);
			}
			else if (type.equals(TYPE_MPS))
			{
				BigDecimal QtyManufacturing = mrpWeekly.getgrosrequirement().subtract(oldQty);
				newQty = QtyManufacturing.add(newQty);
				mrpWeekly.setgrosrequirement(newQty.setScale(2, RoundingMode.HALF_UP));
			}
			mrpWeekly.save();
		}
	}

	
	/**
	 * 
	 * @param po
	 *
	public void generateUpdateMRP(PO po)
	{
		
		if (po instanceof MInOut)
		{
			if (!((MInOut)po).getDocStatus().equals(X_M_InOut.DOCSTATUS_Completed))
				return;
			
			for (MInOutLine inoutLine: ((MInOut) po).getLines())
			{
				m_OrderLine_ID = inoutLine.getC_OrderLine_ID();
				if (m_OrderLine_ID <= 0)
					continue;
				
				BigDecimal scheduleReceipt = inoutLine.getConfirmedQty(); // inoutLine.getConfirmedQty();
				if (scheduleReceipt.compareTo(BigDecimal.ZERO) <=0)
					continue;
				
				MUNSMRP mrp = m_MapOfMRP.get(inoutLine.getM_Product_ID());
				if (null == mrp)
					continue;
				
				for(MUNSMRP mrpChild : mrp.getChilds())
				{
					initMRPWeeklyOf(mrpChild);
					updateMRPWeeklyDetail(
							mrpChild, scheduleReceipt, null
							, ((MInOut)po).getMovementDate(), TYPE_SCHEDULE_RECEIPT);
					
					initMRPWeeklyOf(mrp);
					updateMRPWeekly(mrp, scheduleReceipt, null
							, ((MInOut)po).getMovementDate(), TYPE_SCHEDULE_RECEIPT );
				}
			}
		}
		
		else if (po instanceof MUNSProduction)
		{
			MUNSProduction production = (MUNSProduction)po;
			if (!production.getIsComplete().equals(MUNSProduction.ISCOMPLETE_YES))
				return;
			
			if (production.getUNS_ProductionSchedule_ID() <= 0)
				return;
			
			X_UNS_ProductionSchedule ps = new X_UNS_ProductionSchedule(
					Env.getCtx(), production.getUNS_ProductionSchedule_ID(), m_TrxName);
			
			if (ps.getUNS_MPSProductRsc_Weekly_ID() <= 0)
				return;
			
			MUNSMPSProductRscWeekly mpsRscWeek = new MUNSMPSProductRscWeekly(
					m_Ctx, ps.getUNS_MPSProductRsc_Weekly_ID(), m_TrxName);
			
			int mpsProduct_ID = mpsRscWeek.getUNS_MPSProduct_Resource().getUNS_MPSProduct_ID();
			
			for (MUNSProductionDetail productionDetail : production.getLines())
			{
				if (!productionDetail.getM_Product().isPurchased())
					continue;
				if(productionDetail.isEndProduct())
					continue;
			
				MUNSMRP mrp = m_MapOfMRP.get(productionDetail.getM_Product_ID());
				if (null == mrp)
					continue;
				
				BigDecimal grosRequirement = 
						productionDetail.getMovementQty();
				
				if (grosRequirement.compareTo(BigDecimal.ZERO) <= 0)
					continue;
				
				for (MUNSMRP mrpDetail : mrp.getChilds())
				{
					if(mrpDetail.getUNS_MPSProduct_ID() != mpsProduct_ID)
						continue;
					
					initMRPWeeklyOf(mrpDetail);
					updateMRPWeeklyDetail(mrpDetail, grosRequirement, null
							, production.getMovementDate(), TYPE_PRODUCTION);
					
					initMRPWeeklyOf(mrp);
					updateMRPWeekly(mrp, grosRequirement, null, 
							production.getMovementDate(), TYPE_PRODUCTION);
				}
			}
		}
	}



	/**
	 * 
	 * @param mrp
	 * @param qtyToUpdate
	 * @param oldQty
	 * @param date
	 * @param type
	 *
	private void updateMRPWeekly(
			MUNSMRP mrp, BigDecimal qtyToUpdate, BigDecimal oldQty, Timestamp date, String type)
	{
		MUNSMRPWeekly mrpWeeklyPORE = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		BigDecimal prevProjectedOnHand = BigDecimal.ZERO;
		BigDecimal MOQ = mrp.getMOQ();
		BigDecimal onHand = mrp.getExpectedStorageOnHand();
		BigDecimal safetyStock = mrp.getSafetyStock();
		int leadTime = mrp.getLeadTime();
		int weekToUpdate = calendar.get(Calendar.WEEK_OF_YEAR);
		
		for (MUNSMRPWeekly mrpWeekly : mrp.getWeeklysLines())
		{
			if (mrpWeekly.getWeekNo() < weekToUpdate)
			{
				prevProjectedOnHand = mrpWeekly.getProjectedOnHand();
				continue;
			}
			if (mrpWeekly.getWeekNo() == calendar.get(Calendar.WEEK_OF_YEAR))
			{
				if (type.equals(TYPE_SCHEDULE_RECEIPT))
				{	
					BigDecimal prevQty = mrpWeekly.getGRManufacturing();
					if(!mrpWeekly.isUpdated())
					{
						prevQty = BigDecimal.ZERO;
						mrpWeekly.setIsUpdated(true);
					}
					
					qtyToUpdate = qtyToUpdate.add(prevQty);
					mrpWeekly.setScheduleReceipt(qtyToUpdate);
				}
				else if (type.equals(TYPE_PRODUCTION))
				{
					BigDecimal prevQty = mrpWeekly.getGRManufacturing();
					if(!mrpWeekly.isUpdated())
					{
						prevQty = BigDecimal.ZERO;
						mrpWeekly.setIsUpdated(true);
					}
					qtyToUpdate = qtyToUpdate.add(prevQty);
					mrpWeekly.setGRManufacturing(qtyToUpdate);
				}
				else if (type.equals(TYPE_MPS))
				{
					BigDecimal grossReq = mrpWeekly.getgrosrequirement().subtract(oldQty);
					qtyToUpdate = grossReq.add(qtyToUpdate);
					mrpWeekly.setgrosrequirement(qtyToUpdate);
				}
			}
			
			if (mrpWeekly.getWeekNo() == m_WeekStart)
			{
				prevProjectedOnHand = onHand;
			}
			

			BigDecimal PORE = BigDecimal.ZERO;
			int weekNo = mrpWeekly.getWeekNo() - leadTime;
			if (weekNo > 0)
			{
				String keyMRPPORE = generateKeyOfMap(mrp.getM_Product_ID(), mrp.getUNS_MRP_ID(), weekNo);
				mrpWeeklyPORE = m_MapOfMRPWeekly.get(keyMRPPORE);
				PORE = mrpWeeklyPORE.getPORE();
				BigDecimal currOnHand = prevProjectedOnHand;
				BigDecimal GR = mrpWeekly.getgrosrequirement();
				
				if (mrpWeekly.getGRManufacturing().compareTo(BigDecimal.ZERO) > 0)
				{
					GR = mrpWeekly.getGRManufacturing();
				}
				
				BigDecimal kekurangan = currOnHand.subtract(GR);
				/**
				 *  jika projected onHand masih kurang dari safety stock maka akan looping terus
				 *  sampai projected onHand lebih dari safety stock
				 *
				while (kekurangan.compareTo(safetyStock) < 0)
				{
					if (mrpWeekly.getWeekNo() == calendar.get(Calendar.WEEK_OF_YEAR)
							&& type.equals(TYPE_SCHEDULE_RECEIPT))
					{
						PORE = mrpWeekly.getScheduleReceipt();
						break;
					}
					PORE = PORE.add(MOQ);
					currOnHand = currOnHand.add(MOQ);
					kekurangan = currOnHand.subtract(GR);
				}
				
				mrpWeeklyPORE.setPORE(PORE);
				mrpWeeklyPORE.setAORE(PORE);
				mrpWeeklyPORE.save();
			}
			
			mrpWeekly.setPOR(PORE);
			mrpWeekly.setProjectedAvailable(prevProjectedOnHand.add(PORE));
			mrpWeekly.setProjectedOnHand(
					mrpWeekly.getProjectedAvailable().subtract(mrpWeekly.getgrosrequirement()));
			mrpWeekly.setAROH(mrpWeekly.getProjectedOnHand());
			mrpWeekly.setNetRequirement(mrpWeekly.getgrosrequirement().add(
					safetyStock).subtract(mrpWeekly.getScheduleReceipt().add(prevProjectedOnHand)));
			mrpWeekly.save();
			prevProjectedOnHand = mrpWeekly.getProjectedOnHand();
		}
	}
}
*/
