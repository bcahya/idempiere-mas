/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSLoadingCost extends X_UNS_LoadingCost implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSLoadingCostLine[] m_Lines = null;

	/**
	 * @param ctx
	 * @param UNS_LoadingCost_ID
	 * @param trxName
	 */
	public MUNSLoadingCost(Properties ctx, int UNS_LoadingCost_ID,
			String trxName) {
		super(ctx, UNS_LoadingCost_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLoadingCost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSLoadingCostLine[] getLines(boolean requery) {
		if (null != m_Lines && !requery) {
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		final String whereClause = X_UNS_LoadingCost_Line.COLUMNNAME_UNS_LoadingCost_ID + "=?";
		List<X_UNS_LoadingCost_Line> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_LoadingCost_Line.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_LoadingCost_ID())
				.setOrderBy(X_UNS_LoadingCost_Line.COLUMNNAME_Line)
				.list();
		
		m_Lines = new MUNSLoadingCostLine[list.size()];
		list.toArray(m_Lines);
		return m_Lines;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSLoadingCostLine[] getLines() {
		return getLines(false);
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
		
		MUNSLoadingCostLine[] lines = getLines();
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		for (MUNSLoadingCostLine line : lines) {
			line.setProcessed(true);
			line.save();
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = createReceivableOfWorker(getUNS_Resource_ID());
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = createWorkerAbsence();
		if(null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
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

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
			{
				//	Set lines to 0
				MUNSLoadingCostLine[] lines = getLines(false);
				for (int i = 0; i < lines.length; i++)
				{
					MUNSLoadingCostLine line = lines[i];
					BigDecimal old = line.getQty();
					if (old.signum() != 0)
					{
						line.setQty(Env.ZERO);
						line.addDescription("Void (" + old + ")");
						line.save(get_TrxName());
					}
				}
				//
				// Void Confirmations
				setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
				saveEx();
			}
			else
			{
				return reverseCorrectIt();
			}

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
		return false;
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
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getLines().length).append(")");
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
	
	public String createLinesFromShipment() {
		MUNSShipmentSchedule shipmentSchedule = new MUNSShipmentSchedule(
				getCtx(), getUNS_ShipmentSchedule_ID(), get_TrxName());
		MUNSContainerDeparture[] containerDepartures = shipmentSchedule.getContainerDeparture();
		if(!deleteLines())
			throw new AdempiereException("Failed to delete old lines ");
		if (null == containerDepartures) {
			m_processMsg = "NO LINE CONTAINER DEPARTURE FROM SHIPMENT SCHEDULE"
					+ shipmentSchedule.getDocumentNo() + "_" +shipmentSchedule.getName();
			return m_processMsg;
		}
		for (MUNSContainerDeparture containerDeparture : containerDepartures) {
			MUNSSOShipment[] sOShipments = containerDeparture.getSOShipments();
			if (null == sOShipments) {
				m_processMsg = "NO LINE SHIPMENT ALLOCATION";
				return m_processMsg;
			}
			for (MUNSSOShipment sOShipment : sOShipments) {
				MUNSLoadingCostLine lcLine = new MUNSLoadingCostLine(getCtx(), 0, get_TrxName());
				lcLine.setAD_Org_ID(getAD_Org_ID());
				lcLine.setC_BPartner_ID(shipmentSchedule.getC_BPartner_ID());
				lcLine.setC_Order_ID(sOShipment.getC_OrderLine().getC_Order_ID());
				lcLine.setC_OrderLine_ID(sOShipment.getC_OrderLine_ID());
				lcLine.setM_Product_ID(sOShipment.getM_Product_ID());
				lcLine.setC_UOM();
				lcLine.setPriceCost(BigDecimal.ZERO);
				lcLine.setLineNetAmt(BigDecimal.ZERO);
//				lcLine.setPriceCost();
				lcLine.setQty(sOShipment.getQtyUom());
				lcLine.setUNS_SOShipment_ID(sOShipment.getUNS_SOShipment_ID());
				lcLine.setUNS_LoadingCost_ID(getUNS_LoadingCost_ID());
				if (!lcLine.save()) {
					throw new IllegalArgumentException("Can't create Lines loading cost");
				}
			}
		}
		generateWorker();
		return m_processMsg;
	}
	
	private boolean deleteLines() {
		MUNSLoadingCostLine[] lines = getLines();
		for (MUNSLoadingCostLine line : lines) {
			if (!line.delete(true)) {
				return false;
			}
		}
		for(MUNSLoadingCostWorker worker : getWorkers())
		{
			if(!worker.delete(true))
				return false;
		}
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		if (!deleteLines()) {
			ErrorMsg.setErrorMsg(getCtx(), "DeleteError", "Can't Delete Lines");
			return false;
		}
		return true;
	}
	
	public void generateWorker()
	{
		MUNSResource workStation = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		List<MUNSResourceWorkerLine> listOfWorkerLines = workStation.getListOfWorker();
		for(MUNSResourceWorkerLine worker : listOfWorkerLines)
		{
			MUNSEmployee employee = new MUNSEmployee(getCtx(), worker.getLabor_ID(), get_TrxName());
			MUNSLoadingCostWorker bm_Worker = new MUNSLoadingCostWorker(getCtx(), 0, get_TrxName());
			bm_Worker.setUNS_LoadingCost_ID(get_ID());
			bm_Worker.setLabor_ID(worker.getLabor_ID());
			bm_Worker.setAD_Org_ID(getAD_Org_ID());
			bm_Worker.setReplacementLabor_ID(worker.getLabor_ID());
			bm_Worker.setC_BPartner_ID(employee.getC_BPartner_ID());
			bm_Worker.setReceivableAmt(BigDecimal.ZERO);
			bm_Worker.setUNS_Job_Role_ID(worker.getUNS_Job_Role_ID());
			bm_Worker.setPayrollTerm(worker.getPayrollTerm());
			if (!bm_Worker.save())
			{
				m_processMsg =  "can't create worker " + employee.getName();
				throw new IllegalArgumentException(m_processMsg);
			}
		}
	}
	
	/**
	 * Get Worker loading
	 * @return
	 */
	public List<MUNSLoadingCostWorker> getWorkers()
	{
		return Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSLoadingCostWorker.Table_Name
				, COLUMNNAME_UNS_LoadingCost_ID + " = " + getUNS_LoadingCost_ID()
				, get_TrxName())
				.list();
	}
	
	/**
	 * 
	 * @param listOfWorker
	 * @return
	 */
	private String createPresence(List<MUNSLoadingCostWorker> listOfWorker)
	{
		String msg = null;
		Calendar calendarBongkarMuat = Calendar.getInstance();
		calendarBongkarMuat.setTimeInMillis(getDateAcct().getTime());
		boolean isNonBusinessDay = MNonBusinessDay.isNonBusinessDay(getCtx(), getDateAcct(), getAD_Org_ID(), get_TrxName());
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		
		for (MUNSLoadingCostWorker worker : listOfWorker)
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					getCtx(), labor_ID, getDateAcct(), 
					getAD_Org_ID(), rsc.getResourceParent_ID(), get_TrxName());
			

			X_UNS_Employee employee = new X_UNS_Employee(getCtx(), worker.getLabor_ID(), get_TrxName());
			
			if (null == halfPeriodPresence)
			{
				msg = "Please create worker periodic presence for " + employee.getName();
				return msg;
			}
			
//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//					getCtx(), getAD_Org_ID(), worker.getLabor_ID(), get_TrxName());
			
			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
					getCtx(), getUNS_Resource_ID(), worker.getLabor_ID(), get_TrxName());
			
			if (null == workerOfResource)
				continue;

			//MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSSlotType shift = rsc.getSlotType();
			MUNSWorkerPresence wp = halfPeriodPresence.getWorkerPresence(
										getDateAcct(),rsc.getUNS_SlotType_ID() );
			if (null == wp)
				wp = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			
			wp.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			wp.setAD_Org_ID(getAD_Org_ID());
			wp.setWorkDate(getDateAcct());
			wp.setUNS_Resource_ID(getUNS_Resource_ID()); 
			wp.setReceivableAmt(wp.getReceivableAmt().add(worker.getReceivableAmt()));
			if (isNonBusinessDay)
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
			else if (!shift.IsWorkDay(calendarBongkarMuat.get(Calendar.DAY_OF_WEEK)))
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			else
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			
			wp.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			if(isOverTime())
			{
				wp.setOvertime(getHoursOverTime());
				wp.setOT_Loading_ID(getUNS_LoadingCost_ID());
			}
			else
			{
				wp.setUNS_LoadingCost_ID(getUNS_LoadingCost_ID());
			}
			wp.save();
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isNoWorkDay()
	{
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		if(rsc.getUNS_SlotType_ID() <= 0)
			return true;
		MUNSSlotType slotType = new MUNSSlotType(getCtx(), rsc.getUNS_SlotType_ID(), get_TrxName());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getDateAcct().getTime());
		return !slotType.IsWorkDay(cal.get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createDailyReceivable()
	{
		m_processMsg = generateCostOfProductLines();
		if(null != m_processMsg)
			return m_processMsg;
		return createPresence(getWorkers());
	}
	
	/**
	 * Buat pembayaran untuk worker utama, dan kemudian return sisanya untuk worker non primary
	 * @param amt
	 * @param listOfWorker
	 * @return
	 */
	protected double createReceivableForPrimPortion(double amt, List<MUNSLoadingCostWorker> listOfWorker)
	{
		double sisaAmt = amt;
		for (MUNSLoadingCostWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSResourceWorkerLine rscWorker  = rsc.getWorker(labor_ID);
//					MUNSResourceWorkerLine.getWorker(getCtx(), getAD_Org_ID(), labor_ID, get_TrxName());
			if(null == rscWorker)
				continue;
			if(!rscWorker.isPrimePortion())
				continue;
			
			double proportion = rscWorker.getResultProportion().doubleValue();
			proportion /= 100;
			double pay = sisaAmt * proportion;
			sisaAmt -= pay;
			worker.setReceivableAmt(new BigDecimal(pay));
			worker.save();
			listOfWorker.add(worker);
		}
		return sisaAmt;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createGroupResultReceivable()
	{
		m_processMsg = generateCostOfProductLines();
		if(null != m_processMsg)
			return m_processMsg;
		
		List<MUNSLoadingCostWorker> listOfWorker = new ArrayList<MUNSLoadingCostWorker>();
		double totalAmtForThisGroup = 
				createReceivableForPrimPortion(getTotalAmt().doubleValue(), listOfWorker);

		for(MUNSLoadingCostWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSResourceWorkerLine rscWorker = rsc.getWorker(labor_ID);
//					MUNSResourceWorkerLine.getWorker(getCtx(), getAD_Org_ID(), labor_ID, get_TrxName());
			if(null == rscWorker)
				continue;
			if(rscWorker.isPrimePortion())
				continue;
			double proportion = rscWorker.getResultProportion().doubleValue();
			proportion /= 100;
			double pay = totalAmtForThisGroup * proportion;
			worker.setReceivableAmt(new BigDecimal(pay));
			worker.save();
			listOfWorker.add(worker);
		}
		return createPresence(listOfWorker);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createPersonalResultReceivable()
	{
		List<MUNSLoadingCostWorker> listOfWorker = new ArrayList<MUNSLoadingCostWorker>();
		double totalCost = 0.0;
		for(MUNSLoadingCostWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//				getCtx(), getAD_Org_ID(), getLabor_ID(), get_TrxName());
	
			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
					getCtx(), getUNS_Resource_ID(), labor_ID, get_TrxName());
	
			if(null == workerOfResource)
				continue;
			
			totalCost += worker.getReceivableAmt().doubleValue();
			listOfWorker.add(worker);
		}
		String msg = createCostOfProductLine(totalCost);
		if(null != msg)
			return msg;
		return createPresence(listOfWorker);
	}
	
	protected String createReceivableOfWorker(int UNS_Resource_ID)
	{
		String payMethod = getWorkerResultType();
		if(MUNSResource.PAYMENTMETHOD_Daily.equals(payMethod))
			m_processMsg = createDailyReceivable();
		else if(MUNSResource.PAYMENTMETHOD_GroupResult.equals(payMethod))
			m_processMsg = createGroupResultReceivable();
		else if(MUNSResource.PAYMENTMETHOD_PersonalResult.equals(payMethod))
			m_processMsg = createPersonalResultReceivable();
		else
			m_processMsg = "Unknown worker result type " + payMethod;
		return m_processMsg;
	}
	
	protected String createWorkerAbsence()
	{
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		for(MUNSResourceWorkerLine rscWorker : rsc.getListOfWorker())
		{
			if(rscWorker.isAdditional())
				continue;
			
			MUNSLoadingCostWorker worker  = getWorker(rscWorker.getLabor_ID());
			if(null != worker)
				continue;
			
			MUNSHalfPeriodPresence halfPresence =
					MUNSHalfPeriodPresence.get(
							getCtx(), rscWorker.getLabor_ID(), getDateAcct(), 
							rscWorker.getAD_Org_ID(), rsc.getResourceParent_ID(), get_TrxName());
			
			if(null == halfPresence)
				throw new AdempiereUserError("Not found worker periodic presence");
			
			if(!halfPresence.createAbsence(getDateAcct(), getUNS_Resource_ID()))
				return "Failed to create presence of worker";
		}
		return null;
	}
	
	public MUNSLoadingCostWorker getWorker(int laborID)
	{
		for(MUNSLoadingCostWorker lcWorker : getWorkers())
		{
			int labor_ID = lcWorker.getLabor_ID();
			if(lcWorker.getReplacementLabor_ID() > 0)
				labor_ID = lcWorker.getReplacementLabor_ID();
			if(labor_ID == laborID)
				return lcWorker;
		}
		return null;
	}
	
	public double getTotalQty()
	{
		double totalQty = 0.0;
		for(MUNSLoadingCostLine mLine : getLines())
		{
			totalQty += mLine.getQty().doubleValue();
		}
		return totalQty;
	}
	
	public MUNSResource getWorkstation() {
		
		return new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
	}
	
	private BigDecimal getCostProduct(String handlingType, int M_Product_ID, int C_UOM_ID, int AD_Org_ID)
	{
		BigDecimal biayaBongkar = MUNSBiayaBongkarMuat.getBiayaBongkarMuat(
				handlingType, true, M_Product_ID, C_UOM_ID,AD_Org_ID, get_TrxName());
		if (null == biayaBongkar || biayaBongkar.compareTo(BigDecimal.ZERO)<=0)
		{
			MProduct product = MProduct.get(getCtx(), M_Product_ID);
			throw new IllegalArgumentException(
					"NO COST BONGKAR MUAT FOR "+ product.getName()
			+ " Departement " + getNameOfDepartement(AD_Org_ID));
		}
		return biayaBongkar;
	}
	
	
	private MOrg m_AD_Org_ID = null;
	private String getNameOfDepartement(int AD_Org_ID)
	{
		if (null != m_AD_Org_ID && m_AD_Org_ID.getAD_Org_ID() == AD_Org_ID)
			return m_AD_Org_ID.getName();
		
		return MOrg.get(getCtx(), AD_Org_ID).getName();
	}
	
	private String generateCostOfProductLines()
	{
		double totalQty = getTotalQty();
		String payMethod = getWorkerResultType();
		double totalBiayaBongkar = getCostOfProductByDailyPay();
		double totalCost = 0.0;
		double biayaBongkar = 0.0;
		double cost = 0.0;
		for(MUNSLoadingCostLine mLine : getLines())
		{			
			if(MUNSResource.PAYMENTMETHOD_GroupResult.equals(payMethod))
			{
				biayaBongkar = getCostProduct(mLine.getTypeBongkar(),
											  mLine.getM_Product_ID(), 
											  mLine.getC_UOM_ID(), 
											  mLine.getAD_Org_ID())
								.doubleValue();
				cost = biayaBongkar;
				biayaBongkar *= mLine.getQty().doubleValue();
			}
			else if(MUNSResource.PAYMENTMETHOD_Daily.equals(payMethod))
			{
				double qty = mLine.getQty().doubleValue();
				double proportion = qty / totalQty;
				biayaBongkar = totalBiayaBongkar * proportion;
				cost = biayaBongkar / mLine.getQty().doubleValue();
			}
			else
			{
				m_processMsg = "Unknown Resource Payment Method " + payMethod;
				return m_processMsg;
			}
			mLine.setLineNetAmt(new BigDecimal(biayaBongkar));
			mLine.setPriceCost(new BigDecimal(cost));
			if(!mLine.save())
			{
				m_processMsg = "Failed to update line "
							+ mLine.getLine() + mLine.getM_Product().getName();
				return m_processMsg;
			}
			totalCost += biayaBongkar;
		}
		setTotalAmt(new BigDecimal(totalCost));
		return m_processMsg;
	}
	
	private double getCostOfProductByDailyPay()
	{
		double cost = 0.0;
		for(MUNSLoadingCostWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//				getCtx(), getAD_Org_ID(), getLabor_ID(), get_TrxName());

			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
				getCtx(), getUNS_Resource_ID(), labor_ID, get_TrxName());
			
			if(null == workerOfResource)
				continue;
			
			MUNSProductionPayConfig payConfig =
					MUNSProductionPayConfig.get(
							getCtx(), 
							worker.getAD_Org_ID(),  
							getDateAcct(), 
							get_TrxName());
			if(null == payConfig)
				throw new AdempiereUserError("Not found production pay config");
			
			BigDecimal pay = BigDecimal.ZERO;
			
			if(MNonBusinessDay.isNonBusinessDay(getCtx(), getDateAcct(), getAD_Org_ID(), get_TrxName()))
			{
				pay = payConfig.getPayFullNationalHoliday(true);
				if(isOverTime())
					pay = payConfig.getOverTimeNationalHoliday(true);
			}
			else if(isNoWorkDay())
			{
				pay = payConfig.getPayFullHoliday(true);
				if(isOverTime())
					pay = payConfig.getOverTimeHoliday(true);
			}
			else
			{
				pay = payConfig.getPayFullDay(true);
				if(isOverTime())
					pay = payConfig.getPayOverTimePerHours(true);
			}
			
			worker.setReceivableAmt(pay);
			worker.save();
			cost += worker.getReceivableAmt().doubleValue();
		}
		return cost;
	}

	protected String createCostOfProductLine(double totalCost)
	{
		double totalQty = getTotalQty();
		for(MUNSLoadingCostLine mLine : getLines())
		{
			double proportion = mLine.getQty().doubleValue() / totalQty;
			double lineNet = totalCost * proportion;
			double cost = lineNet / mLine.getQty().doubleValue();
			mLine.setLineNetAmt(new BigDecimal(lineNet));
			mLine.setPriceCost(new BigDecimal(cost));
			if(!mLine.save())
			{
				return "Failed to update line " + mLine.getLine() + " " + mLine.getM_Product().getName();
			}
		}
		return null;
	}
}
