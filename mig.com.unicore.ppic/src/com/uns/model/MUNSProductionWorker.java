/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MNonBusinessDay;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 * @author YAKA
 * 
 */
public class MUNSProductionWorker extends X_UNS_Production_Worker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8039558314013764515L;
	
	private MUNSEmployee m_labor = null;
	private MUNSEmployee m_replacementLabor = null;
	private MUNSProduction m_parent = null;

	public boolean m_isFormModification = false;

	/**
	 * @param ctx
	 * @param UNS_Production_Worker_ID
	 * @param trxName
	 */
	public MUNSProductionWorker(Properties ctx, int UNS_Production_Worker_ID,
			String trxName) {
		super(ctx, UNS_Production_Worker_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionWorker(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSProductionWorker(MUNSProduction production) {
		this(production.getCtx(), 0, production.get_TrxName());
		
		setClientOrg(production);
		setUNS_Production_ID(production.get_ID());
		
		m_parent = production;
	}

	/**
	 * 
	 * @return
	 */
	public MUNSProductionWorkerResult[] getResults() 
	{
		ArrayList<MUNSProductionWorkerResult> list = new ArrayList<MUNSProductionWorkerResult>();

		String sql = "SELECT UNS_Production_WorkerResult_ID FROM UNS_Production_WorkerResult "
				+ "WHERE UNS_Production_Worker_ID ="
				+ getUNS_Production_Worker_ID();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSProductionWorkerResult(getCtx(), rs.getInt(1),
						get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load production details",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSProductionWorkerResult[] retValue = new MUNSProductionWorkerResult[list
				.size()];
		list.toArray(retValue);

		return retValue;
	}

	/**
	 * Get and generate worker based on resource defined in the production record.
	 * 
	 * @param ctx
	 * @param trxName
	 * @param production
	 * @return false if nothing to be created (no workers found for the production).
	 */
	public static boolean generateWorker(Properties ctx, String trxName, MUNSProduction production) 
	{
		MUNSResource rsc = (MUNSResource) production.getUNS_Resource();
		
		List<MUNSResourceWorkerLine> listWorkers = rsc.getListOfWorker();
		
		if (listWorkers == null || listWorkers.size() == 0)
			return false;
		
		for (MUNSResourceWorkerLine rscWorker : listWorkers) 
		{
			MUNSProductionWorker pWorker = new MUNSProductionWorker(ctx, 0, trxName);
			MUNSEmployee employee = MUNSEmployee.get(ctx, rscWorker.getLabor_ID());
			
			pWorker.setUNS_Production_ID(production.getUNS_Production_ID());
			pWorker.setAD_Client_ID(production.getAD_Client_ID());
			pWorker.setAD_Org_ID(production.getAD_Org_ID());
			pWorker.setIsSubcontracting(rsc.isSubcontracting());
			pWorker.setC_BPartner_ID(employee.getVendor_ID());
			pWorker.setLabor_ID(rscWorker.getLabor_ID());
			pWorker.setReplacementLabor_ID(rscWorker.getLabor_ID());
			pWorker.setUNS_Job_Role_ID(rscWorker.getUNS_Job_Role_ID());
			pWorker.setPayrollTerm(rscWorker.getPayrollTerm());
			pWorker.setPresenceStatus(PRESENCESTATUS_FullDay);
			pWorker.setIsAdditional(rscWorker.isAdditional());
			
			if((employee.getVendor_ID()!=0))
			{
				pWorker.setIsSubcontracting(true);
				pWorker.setC_BPartner_ID(employee.getVendor_ID());
			}

			if (!pWorker.save())
				throw new AdempiereException("Error when create Worker, please check Resource Worker for Resource "+rsc.getName());
					
			//pWorker.deleteResult(production.get_TrxName());
			
			/*
			if(!production.isPersonalResult())
				continue;
				
			BigDecimal resultProportion = rscWorker.getResultProportion();
			boolean isPrimePortion = rscWorker.isPrimePortion();
			
			if(production.isWorkerBase() 
					&& (MUNSProduction.OUTPUTTYPE_Multi.equals(production.getOutputType()) 
							|| MUNSProduction.OUTPUTTYPE_MultiOptional.equals(production.getOutputType())))
			{
				MUNSProductionWorkerResult.generateWorkerResultMULTI(ctx, trxName, pWorker, resultProportion, isPrimePortion);
			} 
			else if (production.isWorkerBase() && (MUNSProduction.OUTPUTTYPE_Single.equals(production.getOutputType()) 
				|| MUNSProduction.OUTPUTTYPE_Optional.equals(production.getOutputType())))
			{
				MUNSProductionWorkerResult.generateWorkerResult(ctx, trxName, pWorker, resultProportion);
			}
			*/
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param trxName
	 */
	public void deleteResult(String trxName) {

		for (MUNSProductionWorkerResult output : getResults()) {
			output.deleteEx(true);
		}

	}// deleteResults
	
	/**
	 * 
	 * @param resultProportion
	 * @return
	 *
	private boolean createResult(BigDecimal resultProportion, boolean isPrimePortion)
	{
		String sql = "SELECT COUNT(*) FROM UNS_Production_WorkerResult "
				+ "WHERE UNS_Production_Worker_ID = " + getUNS_Production_Worker_ID();
		
		int count = DB.getSQLValue(get_TrxName(), sql);
		
		if(count==0)
		{
			MUNSProduction p = new MUNSProduction(getCtx(), getUNS_Production_ID(), get_TrxName());
			if (MUNSProduction.OUTPUTTYPE_Multi.equals(p.getOutputType()) 
					|| MUNSProduction.OUTPUTTYPE_MultiOptional.equals(p.getOutputType()))
				MUNSProductionWorkerResult.generateWorkerResultMULTI(getCtx(), get_TrxName(), this, resultProportion, isPrimePortion);
			else
				MUNSProductionWorkerResult.generateWorkerResult(getCtx(), get_TrxName(), this, resultProportion);
		}
		
		if (count<0)
			throw new AdempiereException("Error when create result list");
		return true;
	}
	*/
	
	@Override
	protected boolean beforeDelete() 
	{
		deleteResult(get_TrxName());
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newrecord) 
	{
		//String workerResultType = getParent().getWorkerResultType();
		//if (workerResultType.equals(MUNSProduction.WORKERRESULTTYPE_Daily))
		//	setPayrollTerm(PAYROLLTERM_Weekly);
		
		// Jika full day, set normal work hours nya sesuai dg slot-type.
		if (PRESENCESTATUS_FullDay.equals(getPresenceStatus()))
		{
			MUNSResource wc = (MUNSResource) getParent().getResource().getResourceParent();
			MUNSSlotType slotType = wc.getSlotType();
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getParent().getMovementDate().getTime());
			int workDay = cal.get(Calendar.DAY_OF_WEEK);
			
			if (!slotType.IsWorkDay(workDay))
			{
				workDay++;
				// Jika jumat, maka samakan dg rabu.
				if (workDay == Calendar.FRIDAY)
					workDay = workDay - 2;
			}
							
			BigDecimal  workHours = slotType.getWorkTime(workDay);
			
			setWorkHours(workHours);
			
			if (newrecord) {
				setHoursOverTime(getParent().getHoursOverTime());
			}
		}
		else if (PRESENCESTATUS_HalfDay.equals(getPresenceStatus()))
		{
			MUNSResource wc = (MUNSResource) getParent().getResource().getResourceParent();
			MUNSSlotType slotType = wc.getSlotType();
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getParent().getMovementDate().getTime());
			int workDay = cal.get(Calendar.DAY_OF_WEEK);

			if (!slotType.IsWorkDay(workDay))
			{
				workDay++;
				// Jika jumat, maka samakan dg rabu.
				if (workDay == Calendar.FRIDAY)
					workDay = workDay - 2;
			}
				
			BigDecimal  workHours = slotType.getWorkTime(workDay);
			
			if (workHours.compareTo(getWorkHours()) >= 0)
				setWorkHours(workHours.multiply(new BigDecimal(0.5)));
			
			setHoursOverTime(Env.ZERO);
		}
		else 
		{
			setHoursOverTime(Env.ZERO);
			if (PRESENCESTATUS_Mangkir.equals(getPresenceStatus()))
				setWorkHours(Env.ZERO);
		}
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newrecord, boolean success) 
	{
		MUNSResource rsc = (MUNSResource) ((X_UNS_Production) getUNS_Production()).getUNS_Resource();
		MUNSResourceWorkerLine workerOfRsc = rsc.getWorker(getReplacementLabor_ID());
		
		if(null == workerOfRsc)
		{
			workerOfRsc = new MUNSResourceWorkerLine(getCtx(), 0, get_TrxName());
			workerOfRsc.setAD_Org_ID(getAD_Org_ID());
			workerOfRsc.setPayrollTerm(getPayrollTerm());
			workerOfRsc.setIsPrimePortion(false);
			workerOfRsc.setLabor_ID(getLabor_ID());
			workerOfRsc.setResultProportion(Env.ZERO);
			workerOfRsc.setUNS_Resource_ID(rsc.getUNS_Resource_ID());
			workerOfRsc.setIsAdditional(true);
			workerOfRsc.setUNS_Job_Role_ID(getUNS_Job_Role_ID());
			workerOfRsc.save();
		}
//			createResult(Env.ZERO, false);
//		} else {
//			createResult(workerOfRsc.getResultProportion(), workerOfRsc.isPrimePortion());
//		}
		return true;
	}

	/**
	 * 
	 */
	public MUNSProduction getParent()
	{
		if (m_parent == null || m_parent.get_ID() == 0)
			m_parent = (MUNSProduction) getUNS_Production();
		
		return m_parent;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSEmployee getLabor()
	{
		if (null == m_labor)
			m_labor = MUNSEmployee.get(getCtx(), getLabor_ID());
		return m_labor;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSEmployee getReplacementLabor()
	{
		if (null == m_replacementLabor)
			m_replacementLabor = MUNSEmployee.get(getCtx(), getReplacementLabor_ID());
		return m_replacementLabor;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param p
	 * @param trxName
	 * @return
	 */
	public static String createDailyReceivables(Properties ctx, MUNSProduction p, String trxName)
	{
		String msg = "";
		Calendar calendarProduction = Calendar.getInstance();
		calendarProduction.setTimeInMillis(p.getMovementDate().getTime());
		
		MUNSResource rsc = (MUNSResource) p.getUNS_Resource();
		MUNSProductionPayConfig payConfig = 
				new MUNSProductionPayConfig (ctx, p.getUNS_ProductionPayConfig_ID(), trxName);

		MUNSSlotType slotType = rsc.getSlotType();
		boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));
		
		MUNSProductionWorker[] productionWorkerList = p.getWorkers();
		
		// Total seluruh jam kerja.
		double totalWorkHours = 0.0;
		for (MUNSProductionWorker worker : productionWorkerList)
		{			
			if (worker.getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
				continue;
			
			totalWorkHours += worker.getWorkHours().doubleValue();
			
			if (worker.getPresenceStatus().equals(PRESENCESTATUS_FullDay) && p.isOverTime())
				totalWorkHours += worker.getHoursOverTime().doubleValue();
		}
		
		//Create output mapping to sum of the same production-output' quantity.
		Hashtable<Integer, BigDecimal> availableOutputPortionMap = new Hashtable<Integer, BigDecimal>();
		MUNSProductionDetail[] outputLines = p.getOutputLines();
		
		for (MUNSProductionDetail output : outputLines) 
		{
			BigDecimal currOutputQty = availableOutputPortionMap.get(output.getM_Product_ID());
			
			currOutputQty = (currOutputQty != null)? 
					currOutputQty.add(output.getMovementQty().abs()) : output.getMovementQty().abs();
			
			availableOutputPortionMap.put(output.getM_Product_ID(), currOutputQty);
		}
		
		//Create the production personal records.
		for (MUNSProductionWorker worker : productionWorkerList)
		{
			if (worker.getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
				continue;
			
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = rsc.getWorker(labor_ID);
			if(null == workerOfResource)
				continue;
			
			msg = worker.setDailyReceivables(rsc, payConfig, p, isWorkDay);

			// Calculate worker proportion.
			double proportion = worker.getWorkHours().doubleValue();
			
			if (worker.getPresenceStatus().equals(PRESENCESTATUS_FullDay) && p.isOverTime())
				proportion += worker.getHoursOverTime().doubleValue();
			
			proportion = proportion / totalWorkHours;
			
			for(int M_Product_ID : availableOutputPortionMap.keySet())
			{
				BigDecimal theOutputQty = availableOutputPortionMap.get(M_Product_ID);
				
				MUNSProductionWorkerResult wr = new MUNSProductionWorkerResult(ctx, 0, trxName);
				wr.setAD_Org_ID(p.getAD_Org_ID());
				wr.setDescription("**Auto generated**");
				wr.setInsentifPemborong(Env.ZERO);
				wr.setM_Product_ID(M_Product_ID);
				wr.setProductionQty(theOutputQty.multiply(new BigDecimal(proportion))
									.setScale(3, BigDecimal.ROUND_HALF_DOWN));
				wr.setUNS_Production_Worker_ID(worker.get_ID());
				wr.saveEx();
			}
		}
		return msg;
	}
	
	/**
	 * 
	 * @param workerPresence
	 * @param payConfig
	 * @param p
	 * @param isWorkDay
	 */
	private String setDailyReceivables(
			MUNSResource rsc, MUNSProductionPayConfig payConfig, MUNSProduction p, boolean isWorkDay)
	{
		MUNSHalfPeriodPresence halfPeriodPresence = 
				MUNSHalfPeriodPresence.getCreate(
						getCtx(), getAD_Org_ID(), getReplacementLabor_ID(), getUNS_Job_Role_ID(), true, 
						Integer.valueOf(payConfig.getCutOffWeekDay()), p.getMovementDate(), 
						rsc.getResourceParent_ID(), getPayrollTerm(), get_TrxName());
		
		if (null == halfPeriodPresence)
		{
			return "Cannot find nor create of Period Presence Record for worker " 
					+ getLabor().getName() + " date of " 
					+ p.getMovementDate() + ", please create that.";
		}
		
		MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
				p.getMovementDate(), p.getUNS_Resource_ID());
		boolean presenceExist = true;
		if (null == workerPresence)
		{
			presenceExist = false;
			workerPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			workerPresence.setPresenceStatus(getPresenceStatus());
			workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
			workerPresence.setWorkDate(p.getMovementDate());
			workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
		}
		
		if (getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
		{
			if (presenceExist && 	
					workerPresence.getPresenceStatus().equals(PRESENCESTATUS_Izin))
//				(workerPresence.getPresenceStatus().equals(PRESENCESTATUS_IstirahatSakit)
//				 || workerPresence.getPresenceStatus().equals(PRESENCESTATUS_SakitKecelakaanKerja)))
				return "";
			
			workerPresence.setPayrollTerm(getPayrollTerm());
			workerPresence.setUNS_Job_Role_ID(getUNS_Job_Role_ID());
			workerPresence.setPresenceStatus(getPresenceStatus());
			workerPresence.setOvertimeReceivable(Env.ZERO);
			workerPresence.setNoWorkDayIncentive(Env.ZERO);
			workerPresence.setReceivableAmt(Env.ZERO);
			workerPresence.setTotalReceivableAmt(Env.ZERO);
			workerPresence.setSubsidyAmt(Env.ZERO);
			if (!isWorkDay)
			{
				if (MNonBusinessDay.isNonBusinessDay(getCtx(), p.getMovementDate(), getAD_Org_ID(), get_TrxName()))
					workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
				else 
					workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			}
			else
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			
			workerPresence.saveEx();
			
			setOvertimeReceivable(Env.ZERO);
			setNoWorkDayIncentive(Env.ZERO);
			setReceivableAmt(Env.ZERO);
			setTotalReceivableAmt(Env.ZERO);
			saveEx();
			
			return "";
		}
		
		if(p.isOverTime())
		{
			workerPresence.setOT_ProductionWOrker_ID(get_ID());
			workerPresence.setOvertime(p.getHoursOverTime());
		}
		
		workerPresence.setPayrollTerm(getPayrollTerm());
		workerPresence.setUNS_Job_Role_ID(getUNS_Job_Role_ID());
		workerPresence.setUNS_Production_Worker_ID(get_ID());
		workerPresence.setUNS_Resource_ID(rsc.get_ID());
		workerPresence.setPresenceStatus(getPresenceStatus());
		
		MUNSPayRollLine payrollLine = payConfig.getDailyPayrollLineOf(
				getUNS_Job_Role_ID(), getPayrollTerm());
		
		BigDecimal basicReceivable = Env.ZERO;
		BigDecimal OTReceivable = Env.ZERO;
		
		if (!isWorkDay)
		{
			if (MNonBusinessDay.isNonBusinessDay(getCtx(), p.getMovementDate(), getAD_Org_ID(), get_TrxName()))
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
				
				if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
					basicReceivable = (payrollLine != null)? payrollLine.getPayNHolidayFullDay() 
							: payConfig.getSupervisedPayFullNHoliday();
				else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
					basicReceivable = (payrollLine != null)? payrollLine.getPayNHolidayHalfDay() 
							: payConfig.getSupervisedPayHalfNHoliday();
					
				if(p.isOverTime() && PRESENCESTATUS_FullDay.equals(getPresenceStatus()))
				{
					BigDecimal OT = (payrollLine != null)? payrollLine.getPayNHolidayOTPerHour()
							: payConfig.getSupervisedPayNHolidayOTPerHour();
					
					OTReceivable = OT.multiply(getHoursOverTime());
				}
			}
			else 
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
				
				if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
					basicReceivable = (payrollLine != null)? payrollLine.getPayHolidayFullDay() 
							: payConfig.getSupervisedPayFullHoliday();
				else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
					basicReceivable = (payrollLine != null)? payrollLine.getPayHolidayHalfDay() 
							: payConfig.getSupervisedPayHalfHoliday();
					
				if(p.isOverTime() && PRESENCESTATUS_FullDay.equals(getPresenceStatus()))
				{
					BigDecimal OT = (payrollLine != null)? payrollLine.getPayHolidayOTPerHour()
							: payConfig.getSupervisedPayHolidayOTPerHour();
					
					OTReceivable = OT.multiply(getHoursOverTime());
				}
			}
		}
		else
		{
			workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);

			if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
				basicReceivable = (payrollLine != null)? payrollLine.getPayFullDay() 
						: payConfig.getSupervisedPayFullDay();
			else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
				basicReceivable = (payrollLine != null)? payrollLine.getPayHalfDay() 
						: payConfig.getSupervisedPayHalfDay();
				
			if(p.isOverTime())
			{
				BigDecimal OT = (payrollLine != null)? payrollLine.getPayOverTime()
						: payConfig.getSupervisedPayOTPerHour();
				
				OTReceivable = OT.multiply(p.getHoursOverTime());
			}
		}
		
		double insentifPemborong = (payrollLine != null)? payrollLine.getInsentifPemborong().doubleValue() 
				 : payConfig.getSupervisedInsentifPemborong().doubleValue();
		
		workerPresence.setProductionQty(BigDecimal.ZERO);
		workerPresence.setReceivableAmt(basicReceivable);
		workerPresence.setOvertimeReceivable(OTReceivable);
		workerPresence.setTotalReceivableAmt(basicReceivable.add(OTReceivable));
		workerPresence.saveEx();
		
		setInsentifPemborong(new BigDecimal(insentifPemborong));
		setReceivableAmt(basicReceivable);
		setOvertimeReceivable(OTReceivable);
		setTotalReceivableAmt(basicReceivable.add(OTReceivable));
		saveEx();
		
		return "";
	}
	
	/**
	 * Create Worker presence personal result
	 * @return
	 */
	public static String createPersonalResultReceivable(Properties ctx, MUNSProduction p, String trxName)
	{
		String msg = "";
		
		Calendar calendarProduction = Calendar.getInstance();
		calendarProduction.setTimeInMillis(p.getMovementDate().getTime());
		
		MUNSResource rsc = (MUNSResource) p.getUNS_Resource();
		MUNSProductionPayConfig payConfig = 
				new MUNSProductionPayConfig (ctx, p.getUNS_ProductionPayConfig_ID(), trxName);

		MUNSSlotType slotType = rsc.getSlotType();
		boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));
		boolean isNonBusinesDay = MNonBusinessDay.isNonBusinessDay(ctx, p.getMovementDate(), 
				p.getAD_Org_ID(), trxName);		
		
		MUNSProductionWorker[] productionWorkerList = p.getWorkers();

		for (MUNSProductionWorker worker : productionWorkerList)
		{
			//worker atau employee tetap bisa dapet group result apapun kontraknya.
//			I_UNS_Contract_Recommendation contract = worker.getLabor().getUNS_Contract_Recommendation();
//			
//			if (!MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(contract.getNextContractType()))
//			{
//				msg = worker.setDailyReceivables(rsc, payConfig, p, isWorkDay);
//				if (msg != null)
//					return msg;
//				continue;
//			}
			
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = rsc.getWorker(labor_ID);
			
			if(null == workerOfResource)
				continue;
			
			/*
			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					ctx, labor_ID, p.getMovementDate(), 
					worker.getAD_Org_ID(), rsc.getResourceParent_ID(), trxName);
			*/
			MUNSHalfPeriodPresence halfPeriodPresence = 
					MUNSHalfPeriodPresence.getCreate(
							ctx, worker.getAD_Org_ID(), worker.getReplacementLabor_ID(), 
							worker.getUNS_Job_Role_ID(), true, 
							Integer.valueOf(payConfig.getCutOffWeekDay()), p.getMovementDate(), 
							rsc.getResourceParent_ID(), worker.getPayrollTerm(), trxName);
			
			if (null == halfPeriodPresence)
			{
				I_UNS_Employee labor = worker.getLabor();
				return "Cannot create or find Period Presence Record of worker " 
						+ labor.getName() + "(" + labor.getValue() + ") for date of " 
						+ p.getMovementDate() + ", please create it first manually.";
			}
			
			MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
					p.getMovementDate(), rsc.getUNS_Resource_ID());
			boolean presenceExist = true;
			if (null == workerPresence)
			{
				presenceExist = false;
				workerPresence = new MUNSWorkerPresence(ctx, 0, trxName);
				workerPresence.setPresenceStatus(worker.getPresenceStatus());
				workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
				workerPresence.setWorkDate(p.getMovementDate());
				workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			}
			if (worker.getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
			{
				if (presenceExist && 
						workerPresence.getPresenceStatus().equals(PRESENCESTATUS_Izin))
//					(workerPresence.getPresenceStatus().equals(PRESENCESTATUS_IstirahatSakit)
//					 || workerPresence.getPresenceStatus().equals(PRESENCESTATUS_SakitKecelakaanKerja)))
					continue;
				
				workerPresence.setUNS_Production_Worker_ID(worker.get_ID());
				workerPresence.setPayrollTerm(worker.getPayrollTerm());
				workerPresence.setUNS_Job_Role_ID(worker.getUNS_Job_Role_ID());
				workerPresence.setPresenceStatus(worker.getPresenceStatus());
				workerPresence.setOvertimeReceivable(Env.ZERO);
				workerPresence.setNoWorkDayIncentive(Env.ZERO);
				workerPresence.setReceivableAmt(Env.ZERO);
				workerPresence.setTotalReceivableAmt(Env.ZERO);
				workerPresence.setSubsidyAmt(Env.ZERO);
				if (!isWorkDay)
				{
					if (MNonBusinessDay.isNonBusinessDay(ctx, p.getMovementDate(), p.getAD_Org_ID(), trxName))
						workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
					else 
						workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
				}
				else
					workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
				
				workerPresence.saveEx();
				
				worker.setOvertimeReceivable(Env.ZERO);
				worker.setNoWorkDayIncentive(Env.ZERO);
				worker.setReceivableAmt(Env.ZERO);
				worker.setTotalReceivableAmt(Env.ZERO);
				worker.saveEx();
				continue;//return null;
			}
						
			double totalPayForWorker = 0.0;
			double totalInsentifPemborong = 0.0;
			BigDecimal noWorkDayIncentive = Env.ZERO;
			
			for (MUNSProductionWorkerResult workerResult : worker.getResults())
			{
				MUNSPayRollLine payRollLine = payConfig.getCriteriaLine(
						worker.getUNS_Job_Role_ID(), 
						workerResult.getM_Product_ID(), 
						0, 
						MUNSPayRollLine.CRITERIATYPE_Product, 
						null);
				
				if (null == payRollLine)
					continue;
				
				if(noWorkDayIncentive.signum() == 0 && (isNonBusinesDay || !isWorkDay)) 
					noWorkDayIncentive = payRollLine.getNoWorkDayIncentive();
				
				double productionQty = workerResult.getProductionQty().doubleValue();
				
				double insentifPemborong = payRollLine.getInsentifPemborong().doubleValue() * productionQty;
				
				double payForWorker = 0.0;
				double pay = 0.0;
				
				if(payRollLine.isTargetBased())
				{
					double targetDivider = (payRollLine.isTotalResultTarget())?
							payRollLine.getStdLaborNumber() : 1;
							
					if (!payRollLine.isGradualCalculation()) 
					{
						productionQty = productionQty * targetDivider;
						MUNSPayRollPremiTarget criteriaProductTarget = 
								payRollLine.getCriteriaTarget(BigDecimal.valueOf(productionQty));
						
						if(null != criteriaProductTarget) {
							pay = criteriaProductTarget.getPay().doubleValue();
						}
						payForWorker = pay * productionQty;
					}
					else // Calculate gradually based on target achievement.
					{
						MUNSPayRollPremiTarget[] ppTargetList = payRollLine.getLines(false);
						int i = 0;
						double prevMaxQty = 0;
						
						while(true)
						{
							MUNSPayRollPremiTarget theTarget = ppTargetList[i];
							
							double targetMaxQty = theTarget.getTargetMax().doubleValue();
							targetMaxQty = targetMaxQty / targetDivider;
							
							double calculatedQty = (productionQty > targetMaxQty) ? 
									targetMaxQty - prevMaxQty : productionQty - prevMaxQty;
							
							pay = theTarget.getPay().doubleValue();
							payForWorker += pay * calculatedQty;
							
							if (productionQty <= targetMaxQty)
								break;
							
							prevMaxQty = targetMaxQty;
							i++;
						}
					}
					totalPayForWorker += payForWorker;
					totalInsentifPemborong += insentifPemborong;					
				}
				else
				{
					if(isNonBusinesDay)
						pay = payRollLine.getPayNationalHoliday().doubleValue();
					else if (!isWorkDay)
						pay = payRollLine.getPaySunday().doubleValue();
					else
						pay = payRollLine.getPay().doubleValue();
					
					payForWorker = pay * productionQty;
					totalPayForWorker += payForWorker;
					totalInsentifPemborong += insentifPemborong;
				}
				
				workerResult.setInsentifPemborong(new BigDecimal(insentifPemborong));
				workerResult.setReceivableAmt(new BigDecimal(payForWorker));
				workerResult.saveEx();
			}
			
//			if (noWorkDayIncentive.signum() == 0) 
//				noWorkDayIncentive = payConfig.getNoWorkDayIncentive();
			
			if (!isWorkDay)
				if (isNonBusinesDay)
					workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
				else 
					workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			else
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			
			workerPresence.setPayrollTerm(worker.getPayrollTerm());
			workerPresence.setUNS_Job_Role_ID(worker.getUNS_Job_Role_ID());
			workerPresence.setUNS_Resource_ID(rsc.getUNS_Resource_ID());
			workerPresence.setPresenceStatus(worker.getPresenceStatus());
			
			workerPresence.setUNS_Production_Worker_ID(worker.get_ID());
			
			workerPresence.setProductionQty(BigDecimal.ZERO);
			workerPresence.setReceivableAmt(new BigDecimal(totalPayForWorker));
			workerPresence.setNoWorkDayIncentive(noWorkDayIncentive);
			workerPresence.setTotalReceivableAmt(
					new BigDecimal (totalPayForWorker + noWorkDayIncentive.doubleValue()));
			workerPresence.saveEx();
			
			// TODO Put code in here to set Contractor's incentives.
			
			worker.setInsentifPemborong(new BigDecimal(totalInsentifPemborong));
			worker.setReceivableAmt(new BigDecimal(totalPayForWorker));
			worker.setNoWorkDayIncentive(noWorkDayIncentive);
			worker.setTotalReceivableAmt(
					new BigDecimal (totalPayForWorker + noWorkDayIncentive.doubleValue()));
			worker.saveEx();			
		}
		return msg;
	}	

	/**
	 * 
	 * @param ctx
	 * @param p
	 * @param trxName
	 * @return
	 */
	public static String createGroupResultReceivable(Properties ctx, MUNSProduction p, String trxName)
	{
		Hashtable<Integer, BigDecimal> availableOutputPortionMap = new Hashtable<Integer, BigDecimal>();
		MUNSProductionDetail[] outputLines = p.getOutputLines();
		
		for (MUNSProductionDetail output : outputLines) 
		{
			BigDecimal currOutputQty = availableOutputPortionMap.get(output.getM_Product_ID());
			
			currOutputQty = (currOutputQty != null)? 
					currOutputQty.add(output.getMovementQty().abs()) : output.getMovementQty().abs();
			
			availableOutputPortionMap.put(output.getM_Product_ID(), currOutputQty);
		}
		
		// Create Primary Worker Result based on its result proportion defined in the worker-configuration.
		boolean hasPrimeWorker = false;
		for (MUNSProductionWorker primeWorker : p.getPrimaryWorker())
		{
//			I_UNS_Contract_Recommendation contract = 
//					primeWorker.getLabor().getUNS_Contract_Recommendation();
			
//			if (!MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_SquenceContract.equals(contract.getNextContractType()))
			
//			if (!primeWorker.getEmploymentStatus().equals(EMPLOYMENTSTATUS_BoronganCV))
//				continue; // don't include worker that is not borongan.
			
			hasPrimeWorker = true;
			
			for (MUNSProductionDetail output : outputLines)
			{
				BigDecimal availableQty = availableOutputPortionMap.get(output.getM_Product_ID());
				
				BigDecimal workerPortion = 
						p.getResource().getWorker(primeWorker.getReplacementLabor_ID()).getResultProportion();
				
				int stdPrecision = output.getM_Product().getC_UOM().getStdPrecision();
				
				BigDecimal workerOutputPortion = 
						output.getMovementQty().abs().multiply(workerPortion.divide(Env.ONEHUNDRED))
						.setScale(stdPrecision, BigDecimal.ROUND_HALF_EVEN);
				
				availableQty = availableQty.subtract(workerOutputPortion);
				availableOutputPortionMap.put(output.getM_Product_ID(), availableQty);
				
				MUNSProductionWorkerResult wr = new MUNSProductionWorkerResult(ctx, 0, trxName);
				wr.setAD_Org_ID(p.getAD_Org_ID());
				wr.setDescription("**Auto generated**");
				wr.setInsentifPemborong(Env.ZERO);
				wr.setM_Product_ID(output.getM_Product_ID());
				wr.setProductionQty(workerOutputPortion);
				wr.setUNS_Production_Worker_ID(primeWorker.get_ID());
				wr.saveEx();
			}
		}
		
		// Create Non-Primary Worker Result based on the rest of available qty for each of output.
		MUNSProductionWorker[] nonPrimeWorkerBoronganList = p.getNonPrimaryWorkerBorongan();
		
		// Total seluruh jam kerja.
		double totalWorkHours = 0.0;
		for (MUNSProductionWorker nonPrimeWorker : nonPrimeWorkerBoronganList)
		{			
			
			if (nonPrimeWorker.getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
				continue;
			
//			I_UNS_Contract_Recommendation contract = nonPrimeWorker.getLabor().getUNS_Contract_Recommendation();
//			
//			if (!MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(contract.getNextContractType())
//					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_SquenceContract.equals(contract.getNextContractType()))
//				continue;
			
			totalWorkHours += nonPrimeWorker.getWorkHours().doubleValue();
			
			if (nonPrimeWorker.getPresenceStatus().equals(PRESENCESTATUS_FullDay) && p.isOverTime())
				totalWorkHours += nonPrimeWorker.getHoursOverTime().doubleValue();
		}
		
		if (totalWorkHours == 0.0 && !hasPrimeWorker && !p.isReversal())
			throw new AdempiereException("Cannot process Group-Result type without any Borongan Status.\n " +
					"You better select 'Daily' result type for non-borongan production.");
		
		for (MUNSProductionWorker nonPrimeWorker : nonPrimeWorkerBoronganList)
		{
			nonPrimeWorker.deleteResult(trxName);
			
			double proportion = nonPrimeWorker.getWorkHours().doubleValue();
			
			if (nonPrimeWorker.getPresenceStatus().equals(PRESENCESTATUS_FullDay) && p.isOverTime())
				proportion += nonPrimeWorker.getHoursOverTime().doubleValue();
			
			proportion = proportion / totalWorkHours;
			
			for (MUNSProductionDetail output : outputLines)
			{
				BigDecimal availableQty = availableOutputPortionMap.get(output.getM_Product_ID());
				
				int stdPrecision = output.getM_Product().getC_UOM().getStdPrecision();
				
				BigDecimal workerOutputPortion = 
						availableQty.multiply(new BigDecimal(proportion))
								    .setScale(stdPrecision, BigDecimal.ROUND_HALF_EVEN);
				
				MUNSProductionWorkerResult wr = new MUNSProductionWorkerResult(ctx, 0, trxName);
				wr.setAD_Org_ID(p.getAD_Org_ID());
				wr.setDescription("**Auto generated**");
				wr.setInsentifPemborong(Env.ZERO);
				wr.setM_Product_ID(output.getM_Product_ID());
				wr.setProductionQty(workerOutputPortion);
				wr.setUNS_Production_Worker_ID(nonPrimeWorker.get_ID());
				wr.saveEx();
			}
		}
		return createPersonalResultReceivable(ctx, p, trxName);
	}

	public static void createWorkers(MUNSProduction production,
			MUNSMP1FormDetail detail) {	
		for (int i=0; i<4; i++){
			MUNSProductionWorker pWorker = new MUNSProductionWorker(production);
			
			pWorker.updateProductionWorker(detail, i);
		}
	}

	public int updateProductionWorker(MUNSMP1FormDetail detail, int count) {
		Properties ctx = detail.getCtx();
		String trxName  = detail.get_TrxName();
		
		MUNSEmployee employee = null;
		if (count==0){ // SHELLER
			employee = MUNSEmployee.get(ctx, detail.getUNS_Sheller_ID());
			
			if (detail.getResource().getMaxMachineSuprv1() == 0 
					|| detail.getResource().getMaxMachineSuprv1() <= detail.getNomer())
				setSupervisor_ID(detail.getResource().getSupervisor_ID());
			else
				setSupervisor_ID(detail.getResource().getSupervisor1_ID());
		}
		else if (count==1){ // PARRER 1
			employee = MUNSEmployee.get(ctx, detail.getUNS_Parrer1_ID());

			if (detail.getResource().getMaxMachineSuprv2() == 0 
					|| detail.getResource().getMaxMachineSuprv2() <= detail.getNomer())
				setSupervisor_ID(detail.getResource().getSupervisor2a_ID());
			else
				setSupervisor_ID(detail.getResource().getSupervisor2b_ID());
		}
		else if (count==2){ // PARRER 2
			employee = MUNSEmployee.get(ctx, detail.getUNS_Parrer2_ID());

			if (detail.getResource().getMaxMachineSuprv2() == 0 
					|| detail.getResource().getMaxMachineSuprv2() <= detail.getNomer())
				setSupervisor_ID(detail.getResource().getSupervisor2a_ID());
			else
				setSupervisor_ID(detail.getResource().getSupervisor2b_ID());
		}
		else if (count==3){ // PARRER 3
			employee = MUNSEmployee.get(ctx, detail.getUNS_Parrer3_ID());

			if (detail.getResource().getMaxMachineSuprv2() == 0 
					|| detail.getResource().getMaxMachineSuprv2() <= detail.getNomer())
				setSupervisor_ID(detail.getResource().getSupervisor2a_ID());
			else
				setSupervisor_ID(detail.getResource().getSupervisor2b_ID());
		}
		else
			// SKIP WHEN NULL
			return 0;
		
		//SKIP WHEN EMPLOYEE IS NOT FOUND OR NULL
		if (employee==null || employee.get_ID()==0)
			return 0;

		MUNSResource ws = ((MUNSProduction) getUNS_Production()).getResource();
		MUNSResourceWorkerLine rscWorker = ws.getWorker(employee.get_ID());

		setLabor_ID(employee.get_ID());
		setReplacementLabor_ID(employee.get_ID());
		setUNS_Job_Role_ID(rscWorker.getUNS_Job_Role_ID());
		setPayrollTerm(rscWorker.getPayrollTerm());

		if (employee.getVendor_ID() != 0) {
			setIsSubcontracting(true);
			setC_BPartner_ID(employee.getVendor_ID());
		}

		if (!save())
			throw new AdempiereException(
					"Error at Worker Configuration, unable to create Production Data."
							+ "Please contact System Administrator.");

		deleteResult(trxName);

//		BigDecimal resultProportion = rscWorker.getResultProportion();
//		MUNSProductionWorkerResult.generateWorkerResultFROM(ctx, trxName, this, resultProportion, detail);
		
		return employee.get_ID();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param prodWorker
	 * @param date
	 * @param workCenter_ID
	 * @param halfPeriodType
	 * @param trxName
	 * @return
	 *
	@SuppressWarnings("deprecation")
	public static MUNSHalfPeriodPresence getCreate(
			Properties ctx, MUNSProductionWorker prodWorker, 
			Timestamp date, int workCenter_ID, 
			String halfPeriodType, String trxName)
	{
		MUNSHalfPeriodPresence retPresence = 
				MUNSHalfPeriodPresence.get(ctx, prodWorker.getLabor_ID(), date, 
						prodWorker.getAD_Org_ID(), workCenter_ID, trxName);
		
		if (retPresence != null)
			return retPresence;
		
		MUNSEmployee worker = (MUNSEmployee) prodWorker.getLabor();
		MPeriod period = MPeriod.get(ctx, date, prodWorker.getAD_Org_ID());
		
		retPresence = new MUNSHalfPeriodPresence(ctx, 0, trxName);
		retPresence.setAD_Org_ID(prodWorker.getAD_Org_ID());
		retPresence.setC_DocType_ID(1000089);
		retPresence.setC_Period_ID(period.getC_Period_ID());
		retPresence.setUNS_Employee_ID(prodWorker.getLabor_ID());
		retPresence.setIsSupervised(true);
		retPresence.setC_BPartner_ID(worker.getC_BPartner_ID());
		retPresence.setUNS_Resource_ID(workCenter_ID);
		retPresence.setUNS_Job_Role_ID(prodWorker.getUNS_Job_Role_ID());
		retPresence.setVendor_ID(worker.getVendor_ID());
		if (halfPeriodType.equals(MUNSProductionPayConfig.TARGETPERIOD_1Month))
		{
			retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_FullMonth);
			retPresence.setStartDate(period.getStartDate());
			retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
		}
		else {
			Timestamp halfPeriodDate = TimeUtil.addDays(period.getStartDate(), 14);
			if (halfPeriodDate.after(date))
			{
				retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2);
				retPresence.setStartDate(TimeUtil.addDays(period.getStartDate(), 15));
				retPresence.setEndDate(period.getEndDate());
			}
			else {
				retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1);
				retPresence.setStartDate(period.getStartDate());
				retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
			}
		}
		/*
		
		if (halfPeriodType.equals(MUNSProductionPayConfig.TARGETPERIOD_1Month) 
			|| halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1))
		{
			retPresence.setStartDate(period.getStartDate());
			if (halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_FullMonth))
				retPresence.setEndDate(period.getEndDate());
			else
			retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
		}
		else if (halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2)) {
			retPresence.setStartDate(TimeUtil.addDays(period.getStartDate(), 14));
			retPresence.setEndDate(period.getEndDate());
		}
		//*
		retPresence.saveEx();
		
		return retPresence;
	} // getCreate
	*/
	
	/**
	 * To indicate if this worker line is for reversal.
	 * @return
	 */
	public boolean isReversalLine()
	{
		return getReversalLine_ID() > 0;
	} // isReversalLine
}