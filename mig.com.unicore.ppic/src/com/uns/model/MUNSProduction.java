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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MProductCategory;
//import org.compiere.model.MProduction;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSImportContent;
import com.uns.util.MessageBox;
import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 * 
 */
public class MUNSProduction extends X_UNS_Production implements DocAction, DocOptions {

	private String m_processMsg = null;
	
	private boolean m_justPrepared = false;
	public boolean m_isFormModification = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258391499304840964L;
	public static final String ISCOMPLETE_NO ="N";
	public static final String ISCOMPLETE_YES ="Y";
	/** Reversal Indicator			*/
	public static String	REVERSE_INDICATOR = "^";
	
	private MUNSResource m_Resource = null;
	private MUNSProductionDetail[] m_ProductionDetails = null;
	private MUNSProductionDetail[] m_EndProducts = null;

	/** A flag to indicate if this is the reversal document. **/
	private boolean m_isReversal = false;
	
	protected boolean isGeneratingOutPlan = false;

	/**
	 * 
	 */
	/** Log */
	 
	//private static CLogger m_log = CLogger.getCLogger(MUNSProduction.class);
	private int lineno;
	private int count;
	private int lineOtherProduct;
	

	/**
	 * @param ctx
	 * @param UNS_Production_ID
	 * @param trxName
	 */
	public MUNSProduction(Properties ctx, int UNS_Production_ID, String trxName) {
		super(ctx, UNS_Production_ID, trxName);
		getResource();
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProduction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		getResource();
	}
	
	public MUNSProduction(MUNSProductionSchedule ps, int UNS_Resource_ID, BigDecimal qtyToManufacture)
	{
		this(ps.getCtx(), 0, ps.get_TrxName());
		setClientOrg(ps.getAD_Client_ID(), ps.getUNS_Manufacturing_Order().getProductionDepartment_ID());
		qtyToManufacture = (qtyToManufacture == null || Env.ZERO.compareTo(qtyToManufacture) >= 0) ?
				ps.getQtyOrdered() : qtyToManufacture;
		setProduction(ps, UNS_Resource_ID, qtyToManufacture);
	}

//	private void setDate(Timestamp productionDate) 
//	{
//		setProductionDate(productionDate);
//		setDatePromised(productionDate);
//		setMovementDate(productionDate);
//	}
//
//	private void setNameDescrip(MUNSMP1FormDetail detail, boolean overtime) 
//	{
//		MUNSResource workCenter = new MUNSResource(getCtx(), detail.getMP1Form().getUNS_Resource_ID(), get_TrxName());
//		m_Resource = new MUNSResource(getCtx(), detail.getUNS_Resource_ID(), get_TrxName());
//		String sDate = new SimpleDateFormat("dd/MM/yyyy").format(detail.getMP1Form().getProductionDate());
//		
//		setName("Production "+sDate+" Work Center "+workCenter.getValue()); 
//		setDescription("This production generated from MPD Production.");
//		
//		if (overtime){
//			setDescription(getDescription() + " (Overtime Production)");
//			setIsOverTime(overtime);
//			setHoursOverTime(BigDecimal.ZERO);
//		}
//	}

	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Invalid, add "Prepare & Void" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Void;
		}

		// If status = In Progress, add "Void" in the list
		if (docStatus.equals(DocumentEngine.STATUS_InProgress)) {
			options[index++] = DocumentEngine.ACTION_Void;
		}

		// If status = Completed, add "Reverse Correct" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			options[index++] = DocumentEngine.ACTION_Reverse_Accrual;
		}

		return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() 
	{
		log.info(toString());
		setDocAction(DocAction.ACTION_Invalidate);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() 
	{
		if (isReversal())
		{
			m_justPrepared = true;
			setProcessed(true);
			return DocAction.STATUS_InProgress;
		}
		
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getM_Product_ID() > 0 && getProductionQty().signum() <= 0)
			throw new AdempiereUserError("Please update production quantity!");
		
		MUNSProductionDetail[] pds = getLines();
		for(MUNSProductionDetail pd : pds)
		{
			if(pd.isEndProduct() && pd.getMovementQty().signum() <= 0)
			{
				throw new AdempiereException("Please update movement qty for output detail of " 
						+ pd.getM_Product().getName() + " or remove it if not produced.");
			}
			else if (!pd.isEndProduct() && pd.getQtyUsed().signum() <= 0) {
				throw new AdempiereException("Please update movement/used qty for input detail of " 
						+ pd.getM_Product().getName() + " or remove it if not used.");
			}
			
			if(pd.isEndProduct())
				continue;
			
			BigDecimal availableQty = MStorageOnHand.getQtyOnHandForLocator(
					pd.getM_Product_ID(), pd.getM_Locator_ID()
					, pd.getM_AttributeSetInstance_ID(), pd.get_TrxName());
			if(availableQty.compareTo(pd.getMovementQty().negate()) < 0)
			{
				throw new AdempiereException("Stok " + pd.getM_Product().getName() 
						+ " tidak cukup untuk melakukan produksi pada locator " 
						+ pd.getM_Locator().getValue());
			}
		}
		
		if(isWorkerBase())
		{
			MUNSProductionWorker[] workers = getWorkers();
			if(workers == null || workers.length == 0)
				throw new AdempiereException("Production worker base must have at least one worker");
			
			for(MUNSProductionWorker worker : workers)
			{
				if(!isPersonalResult())
					continue;
				MUNSProductionWorkerResult[] workerResults = worker.getResults();
				if(null == workerResults || workerResults.length == 0)
				{
					throw new AdempiereException("Result of worker " + worker.getLabor().getName() + " is not set");
				}
				
				for(MUNSProductionWorkerResult workerResult : workerResults)
				{
					if(workerResult.getProductionQty().signum() == 0)
					{
						throw new AdempiereException("Please update production qty of worker result. Worker : " 
									+ worker.getLabor().getName() + " for product : " 
								+ workerResult.getM_Product().getName());
					}
				}
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
//		if (!DocAction.ACTION_Complete.equals(getDocAction()))
//			setDocAction(DocAction.ACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() 
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		if(getDocAction().equals(DocAction.ACTION_Prepare))
		{
			return DocAction.STATUS_InProgress;
		}
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = processLines();

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		//setDateDoc(new Timestamp(Calendar.getInstance().getTimeInMillis())); // Reset the request timestamp to today.

		
		setProcessed(true);
		setDocAction(DocAction.ACTION_Close);
		m_processMsg = "Completed.";
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() 
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		setDescription("<<Voided>>\n " + getDescription());
		//setProductionQty(Env.ZERO);
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DocAction.ACTION_None);

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		setDocAction(DocAction.ACTION_Close);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() 
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		MUNSProduction reversal = reverse(false);
		if (reversal == null)
			return false;
		
		m_processMsg = "Reversed with created reversal document no : " + reversal.getDocumentNo();

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() 
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		MUNSProduction reversal = reverse(true);
		if (reversal == null)
			return false;
		
		m_processMsg = "Reversed with created reversal document no : " + reversal.getDocumentNo();

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}
	
	/**
	 * Create the reversal document for this production.
	 * 
	 * @param accrual
	 * @return
	 */
	private MUNSProduction reverse(boolean accrual)
	{
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getMovementDate();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
//		else
//			throw new AdempiereException();
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return null;
		}
		
		MUNSProduction reversal = new MUNSProduction(getCtx(), 0, get_TrxName());
		copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setIsApproved (false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		reversal.setMovementDate(reversalDate);
		reversal.setProductionDate(Env.getContextAsDate(getCtx(), "#Date"));
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	//	indicate reversals
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//FR [ 1948157  ]
		reversal.setReversal_ID(getUNS_Production_ID());
		reversal.setReversal(true);
		if (!reversal.save()) {
			m_processMsg = "Could not create Production Reversal Document.";
			return null;
		}
		
		// Reverse all of the production details.
		MUNSProductionDetail[] pdLines = getLines();
		
		for (int i=0; i < pdLines.length; i++)
		{
			MUNSProductionDetail pd = pdLines[i];
			
			if (!pd.isEndProduct() || !pd.isPrimary())
				continue;
			
			MUNSProductionDetail reversalPD = new MUNSProductionDetail(getCtx(), 0, get_TrxName());
			String errMsg = reversalPD.initReversalDetail(reversal, pd);
			boolean saveSuccess = reversalPD.save();
			if (errMsg != null || !saveSuccess) {
				m_processMsg = "Could not create Reversal of Production Detail (Primary Output) of " + pd.getM_Product()
						+ (errMsg != null ? " Caused: " + errMsg : "");
				return null;
			}
			
			// Create all of the primary's non-primary.
			MUNSProductionDetail[] nonPrimaryLines = pd.getNonPrimaryOutputs();
			
			for (MUNSProductionDetail nonPrimaryPD : nonPrimaryLines)
			{
				MUNSProductionDetail reversalNonPrimary = new MUNSProductionDetail(getCtx(), 0, get_TrxName());
				errMsg = reversalNonPrimary.initReversalDetail(reversal, nonPrimaryPD);
				reversalNonPrimary.setPrimaryOutput_ID(reversalPD.getUNS_Production_Detail_ID());
				saveSuccess = reversalNonPrimary.save();
				if (errMsg != null || !saveSuccess) {
					m_processMsg = "Could not create Reversal of Production Detail (Non-Primary Output) of " 
								+ nonPrimaryPD.getM_Product() + (errMsg != null ? " Caused: " + errMsg : "");
					return null;
				}
			}

			// Create all of the primary's inputs.
			MUNSProductionDetail[] inputLines = pd.getInputs();
			
			for (MUNSProductionDetail inputPD : inputLines)
			{
				MUNSProductionDetail reversalInput = new MUNSProductionDetail(getCtx(), 0, get_TrxName());
				errMsg = reversalInput.initReversalDetail(reversal, inputPD);
				reversalInput.setPrimaryOutput_ID(reversalPD.getUNS_Production_Detail_ID());
				saveSuccess = reversalInput.save();
				if (errMsg != null || !saveSuccess) {
					m_processMsg = "Could not create Reversal of Production Detail (Input) of " 
								+ inputPD.getM_Product() + (errMsg != null ? " Caused: " + errMsg : "");
					return null;
				}
			}
		}

		//Reverse all of the production worker.
		if (isWorkerBase())
		{
			String cutOffWeekDayStr = 
					DB.getSQLValueString(get_TrxName(), 
							"SELECT CutOffWeekDay FROM UNS_ProductionPayConfig WHERE UNS_ProductionPayConfig_ID=?", 
							getUNS_ProductionPayConfig_ID());
			int cutOffWeekDay = Integer.valueOf(cutOffWeekDayStr);
			int workCenter_ID = getUNS_Resource().getResourceParent_ID();

			MUNSProductionWorker[] workerLines = getWorkers();
			for (MUNSProductionWorker worker : workerLines)
			{
				MUNSProductionWorker reversalWO = new MUNSProductionWorker(getCtx(), 0, get_TrxName());
				copyValues(worker, reversalWO, worker.getAD_Client_ID(), worker.getAD_Org_ID());
				reversalWO.setUNS_Production_ID(getUNS_Production_ID());
				reversalWO.setReversalLine_ID(worker.getUNS_Production_Worker_ID());
				reversalWO.setReceivableAmt(reversalWO.getReceivableAmt().negate());
				reversalWO.setOvertimeReceivable(reversalWO.getOvertimeReceivable().negate());
				reversalWO.setInsentifPemborong(reversalWO.getInsentifPemborong().negate());
				reversalWO.setNoWorkDayIncentive(reversalWO.getNoWorkDayIncentive().negate());
				reversalWO.setTotalReceivableAmt(reversalWO.getTotalReceivableAmt().negate());
				if (!reversalWO.save()) {
					m_processMsg = "Could not create Reversal for Production Worker of " 
							+ reversalWO.getLabor().getValue() 
							+ " (" + reversalWO.getLabor().getName() + ")";
					return null;
				}
				
				MUNSWorkerPresence woPresence = 
						MUNSWorkerPresence.getOfProductionWorker(getCtx(), worker.get_ID(), get_TrxName());
				
				boolean isPaid = woPresence.getParent().getDocStatus().equals(DOCSTATUS_Completed) || 
						woPresence.getParent().getDocStatus().equals(DOCSTATUS_Closed);
				
				if (!isPaid)
				{
					woPresence.setUNS_Production_Worker_ID(reversalWO.getUNS_Production_Worker_ID());
					woPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Reversed);
					woPresence.setReceivableAmt(Env.ZERO);
					woPresence.setNoWorkDayIncentive(Env.ZERO);
					woPresence.setOvertime(Env.ZERO);
					woPresence.setOvertimeReceivable(Env.ZERO);
					woPresence.setSubsidyAmt(Env.ZERO);
					woPresence.setTotalReceivableAmt(Env.ZERO);
					
					if (!woPresence.save()) {
						m_processMsg = "Could not create Reversal for Worker Presence of " 
								+ woPresence.getUNS_HalfPeriod_Presence().getUNS_Employee().getValue() 
								+ " (" + woPresence.getUNS_HalfPeriod_Presence().getUNS_Employee().getName() + ")";
						return null;
					}
				}
				else 
				{
					MUNSHalfPeriodPresence theHPPresenceToAdjust = null;
					Timestamp theNextPeriodPresenceDate = 
							UNSTimeUtil.addDays(woPresence.getParent().getEndDate(), 1);
					
					while (true)
					{
						theHPPresenceToAdjust =
								MUNSHalfPeriodPresence.getCreate(
										getCtx(), worker.getAD_Org_ID(), worker.getReplacementLabor_ID(), 
										worker.getUNS_Job_Role_ID(), true, cutOffWeekDay, 
										theNextPeriodPresenceDate, workCenter_ID, 
										worker.getPayrollTerm(), get_TrxName());
						
						if (theHPPresenceToAdjust.getDocStatus().equals(DOCSTATUS_Completed)
							|| theHPPresenceToAdjust.getDocStatus().equals(DOCSTATUS_Closed)) 
						{
							theNextPeriodPresenceDate = 
									UNSTimeUtil.addDays(woPresence.getParent().getEndDate(), 1);
							continue;
						}
						
						break;
					}
					
					MUNSWorkerPresence reversalWOPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
					copyValues(woPresence, reversalWOPresence, 
							woPresence.getAD_Client_ID(), woPresence.getAD_Org_ID());
					reversalWOPresence.setUNS_HalfPeriod_Presence_ID(theHPPresenceToAdjust.get_ID());
					reversalWOPresence.setUNS_Production_Worker_ID(reversalWO.get_ID());
					reversalWOPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Reversal);
					reversalWOPresence.setReceivableAmt(woPresence.getReceivableAmt().negate());
					reversalWOPresence.setNoWorkDayIncentive(woPresence.getNoWorkDayIncentive().negate());
					reversalWOPresence.setOvertimeReceivable(woPresence.getOvertimeReceivable().negate());
					reversalWOPresence.setSubsidyAmt(woPresence.getSubsidyAmt().negate());
					reversalWOPresence.setTotalReceivableAmt(woPresence.getTotalReceivableAmt().negate());
					if (!reversalWOPresence.save()) {
						m_processMsg = "Could not create Reversal for Worker Presence of " 
								+ woPresence.getUNS_HalfPeriod_Presence().getUNS_Employee().getValue() 
								+ " (" + woPresence.getUNS_HalfPeriod_Presence().getUNS_Employee().getName() + ")";
						return null;
					}
				}
			}
		}

		try {
			//m_justPrepared = true;
			//MUNSProductionDetail[] detailLines = reversal.getLines();
			reversal.setReversal(true);
			if (!reversal.processIt(DocAction.ACTION_Complete))
			{
				m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
				return null;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			m_processMsg = "Reversal ERROR: " + ex.getMessage();
			return null;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx();
		
		//	Update Reversed (this)
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		//FR [ 1948157  ]
		setReversal_ID(reversal.getUNS_Production_ID());
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
			
		return reversal;
	}

	void setReversal(boolean reversal)
	{
		m_isReversal = reversal;
	}
	
	/**
	 * To indicate if this production is for reversal.
	 * @return
	 */
	public boolean isReversal()
	{
		return m_isReversal;
	} // isReversalLine
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reActivateIt() {

		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		StringBuilder msgreturn = new StringBuilder().append(dt.getName()).append(" ").append(getDocumentNo());
		return msgreturn.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		try
		{
			StringBuilder msgfile = new StringBuilder().append(get_TableName()).append(get_ID()).append("_");
			File temp = File.createTempFile(msgfile.toString(), ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.MANUFACTURING_ORDER, getUNS_Production_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if(format.getJasperProcess_ID() > 0)	
		{
			ProcessInfo pi = new ProcessInfo ("", format.getJasperProcess_ID());
			pi.setRecord_ID ( getUNS_Production_ID() );
			pi.setIsBatch(true);
			
			ServerProcessCtl.process(pi, null);
			
			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	}	//	createPDF

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() 
	{
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		return Env.getContextAsInt(getCtx(),"$C_Currency_ID");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}
	
	/**
	 * Indicates if this output is optional.
	 * 
	 * @return
	 */
	public boolean isOptional() {
		return (getOutputType().equals(OUTPUTTYPE_Optional));
	}

	/**
	 * Indicates if this output is single.
	 * 
	 * @return
	 */
	public boolean isSingle() {
		return (getOutputType().equals(OUTPUTTYPE_Single));
	}

	/**
	 * Indicates if this output is multi.
	 * 
	 * @return
	 */
	public boolean isMulti() {
		return (getOutputType().equals(OUTPUTTYPE_Multi));
	}

	/**
	 * Indicates if this output is multioptional.
	 * @return
	 */
	public boolean isMultiOptional() {
		return (getOutputType().equals(OUTPUTTYPE_MultiOptional));
	}

	// not used yet.
	// public MUNSProduction( MOrderLine line ) {
	// super( line.getCtx(), 0, line.get_TrxName());
	// setAD_Client_ID(line.getAD_Client_ID());
	// setAD_Org_ID(line.getAD_Org_ID());
	// setMovementDate( line.getDatePromised() );
	// }

	public MUNSProductionOutPlan getPrimaryOutput() 
	{
		String sql = "SELECT UNS_Production_OutPlan_ID FROM UNS_Production_OutPlan "
				+ "WHERE UNS_Production_ID = ? AND isPrimary='Y'";

		int productionOP = DB.getSQLValue(get_TrxName(), sql,
				getUNS_Production_ID());
		if (productionOP<0)
			throw new AdempiereException("Primary product output not set.");

		return new MUNSProductionOutPlan(getCtx(), productionOP, get_TrxName());
	}

	public MUNSProductionOutPlan[] getOtherOutput() {
		ArrayList<MUNSProductionOutPlan> list = new ArrayList<MUNSProductionOutPlan>();

		String sql = "SELECT UNS_Production_OutPlan_ID FROM UNS_Production_OutPlan "
				+ "WHERE UNS_Production_ID = ? AND isPrimary='N'";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getUNS_Production_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSProductionOutPlan(getCtx(), rs.getInt(1),
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

		MUNSProductionOutPlan[] retValue = new MUNSProductionOutPlan[list
				.size()];
		list.toArray(retValue);
		return retValue;
	}
	
	public MUNSImportContent[] getImportContent() {
		ArrayList<MUNSImportContent> list = new ArrayList<MUNSImportContent>();

		String sql = "SELECT UNS_ImportContent_ID FROM UNS_ImportContent "
				+ "WHERE UNS_Production_ID = ?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			
			while (rs.next())
				list.add(new MUNSImportContent(getCtx(), rs.getInt(1), get_TrxName()));
			
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load Import Content",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSImportContent[] retValue = new MUNSImportContent[list.size()];
		list.toArray(retValue);
		return retValue;
	}

	/**
	 * 
	 * @return
	 */
	public MUNSProductionOutPlan[] getOutputs() 
	{
		ArrayList<MUNSProductionOutPlan> list = new ArrayList<MUNSProductionOutPlan>();

		String sql = "SELECT UNS_Production_OutPlan_ID FROM UNS_Production_OutPlan "
				+ "WHERE UNS_Production_ID = " + getUNS_Production_ID();
		
		//if (inputID != null && inputID.length > 0)
		//{
		//	sql += " AND M_Product_ID IN (SELECT bom.M_Product_ID FROM M_Product_BOM bom WHERE bom.M_ProductBOM_ID=?)";
		//}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSProductionOutPlan(getCtx(), rs.getInt(1),
						get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load production output",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSProductionOutPlan[] retValue = new MUNSProductionOutPlan[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSProductionWorker[] getWorkers() 
	{
		List<MUNSProductionWorker> list = null;

		/*
		String sql = "SELECT UNS_Production_Worker_ID FROM UNS_Production_Worker "
				+ "WHERE UNS_Production_ID = " + getUNS_Production_ID();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSProductionWorker(getCtx(), rs.getInt(1),
						get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load production output",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}
		*/
		
		list = Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						 MUNSProductionWorker.Table_Name, "UNS_Production_ID=?", get_TrxName())
					.setParameters(get_ID())
					.list();

		MUNSProductionWorker[] retValue = new MUNSProductionWorker[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	
	/**
	 * 
	 * @param ps
	 * @param AD_Org_ID
	 */
	public void setProduction(MUNSProductionSchedule ps, int UNS_Resource_ID, BigDecimal qtyToManufacture)
	{
//		if (!DOCSTATUS_Completed.equals(ps.getDocStatus()))
//			throw new AdempiereUserError("Cannot create production form uncompleted schedule.");
		
		setUNS_ProductionSchedule_ID(ps.get_ID());
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_Production));
		setDatePromised(ps.getUNS_Manufacturing_Order().getTarget_PD_Start());
		/*
		if (ps.getUNS_MPSProductRsc_Weekly_ID()!=0){
			MUNSMPSProductRscWeekly mpsprscw = new MUNSMPSProductRscWeekly(getCtx(), ps.getUNS_MPSProductRsc_Weekly_ID(), get_TrxName());
			MUNSMPSProductResource prsc = new MUNSMPSProductResource(getCtx(), mpsprscw.getUNS_MPSProduct_Resource_ID(), get_TrxName());
			setResource(new MUNSResource(getCtx(), prsc.getUNS_Resource_ID(), get_TrxName()), AD_Org_ID); 
		} else {
			MUNSSERLineProduct lp = new MUNSSERLineProduct(getCtx(), ps.getUNS_SERLineProduct_ID(), get_TrxName());
			setResource(MUNSResource.getWSFromProduct(getCtx(), get_TrxName(), lp.getM_Product_ID()), AD_Org_ID);
		}
		*/
		setUNS_Resource_ID(UNS_Resource_ID);
		setProductionQty(qtyToManufacture);
		setProductionDate(new Timestamp( System.currentTimeMillis() ));
		setMovementDate(new Timestamp( System.currentTimeMillis() ));
		setM_Product_ID(ps.getM_Product_ID());
		
		//setOutputType(MUNSProduction.OUTPUTTYPE_Single);

		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		MUNSResourceInOut[] rscOutput = rsc.getOutputLines();
		
		setIsWorkerBase(rsc.isWorkerBase());
		if (isWorkerBase())
			setWorkerResultType(rsc.getPaymentMethod());
		
		for (MUNSResourceInOut rio : rscOutput)
		{
			if (rio.getM_Product_ID() == ps.getM_Product_ID())
			{
				setOutputType(rio.getOutputType());
				if (rio.getOutputType().equals(OUTPUTTYPE_Optional))
					setOutputType(OUTPUTTYPE_Single);
				if (!rio.getOutputType().equals(OUTPUTTYPE_Multi))
				{
					setM_Locator_ID(rio.getM_Locator_ID());
				}
				//if (rio.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi) && !rio.isPrimary())
				//	throw new AdempiereException("")	
			}		
		}
		
//		check if locator getting from resource locator
		/*
		if (rscOutput.length>0){
			setM_Locator_ID(rscOutput[0].getM_Locator_ID());
		} else
			throw new AdempiereException("Please Set locator at resource " + rsc.getName());
		*/
		//if(!ps.getPSType().equalsIgnoreCase(MUNSProductionSchedule.PSTYPE_MasterProductionSchedule))
		//{
			/*
			if (ps.getPSType().equalsIgnoreCase(MUNSProductionSchedule.PSTYPE_Rebundle))
				setM_Product_ID(ps.getM_Product_ID());
			else 
				setM_Product_ID(ps.getSourceProduct_ID());
			*/
//			check if locator getting from resource locator
		/*
			if (rscOutput.length>0){
				setM_Locator_ID(rscOutput[0].getM_Locator_ID());
			} else
				throw new AdempiereException("Please Set locator at resource " + rsc.getName());
				
			//setProductionQty(ps.getQtyUom());
		}
		*/
		setPSType(ps.getPSType());
		setDescription(ps.getDescription() + "\n " + ps.getProductionRemarks());
		
		//if(getPSType().equals(PSTYPE_Restickering))
		if (null != ps.getStickerRemarks() 
			&& !"".equals(ps.getStickerRemarks()) 
			&& !"-".equals(ps.getStickerRemarks()))
		{
			setHasStickerInfo(true);
			//setProductSticker_ID(ps.getProductSticker_ID());
			setStickerInfo(ps.getStickerRemarks());
		}
		setMustSyncWithMPS(true);
		setIsComplete(ISCOMPLETE_NO);
		setName("Production "+MOrg.get(getCtx(), getAD_Org_ID()).getName()+" "+MProduct.get(getCtx(), ps.getM_Product_ID()).getName());
	}
	
	/**
	 * MultiOptional is not Implement
	 * 
	 * @param rsc
	 * @param AD_Org_ID
	 */
	public void setResource(MUNSResource rsc)
	{
		setUNS_Resource_ID(rsc.get_ID());
		setIsEndingStockBase(rsc.isEndingStockBase());
		setIsWorkerBase(rsc.isWorkerBase());
		setWorkerResultType(rsc.getPaymentMethod());
		MUNSResourceInOut rscOutput = rsc.getOutputLines()[0];
		if(rsc.getOutputLines().length<2)
		{
			setOutputType(MUNSProduction.OUTPUTTYPE_Single);
			setM_Product_ID(rscOutput.getM_Product_ID());
			setM_Locator_ID(rscOutput.getM_Locator_ID());
		} 
		else if (rscOutput.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional))
		{
			setOutputType(MUNSProduction.OUTPUTTYPE_Optional);
			setM_Product_ID(rscOutput.getM_Product_ID());
			setM_Locator_ID(rscOutput.getM_Locator_ID());
		} 
		else 
		{
			rscOutput = rsc.getPrimaryOutput();
			setOutputType(MUNSProduction.OUTPUTTYPE_Multi);
			setM_Product_ID(rscOutput.getM_Product_ID());
			setM_Locator_ID(rscOutput.getM_Locator_ID());
		}
		
//		if (rsc.getSupervisor_ID()!=0)
//			setSupervisor_ID(rsc.getSupervisor_ID());
//		else if (rsc.getParent().getSupervisor_ID()!=0)
//			setSupervisor_ID(rsc.getParent().getSupervisor_ID());
//		else
//			throw new AdempiereException("Not found Supervisor, " +
//					"please check Resource Configuration.");
	}

	public void deleteLines(String trxName) {

		for (MUNSProductionDetail line : getLines()) {
			line.deleteEx(true);
		}
		
		m_ProductionDetails = null;
	}// deleteLines
	
	public void deleteOutputs(String trxName) {

		for (MUNSProductionOutPlan output : getOutputs()) {
			output.deleteEx(true);
		}

	}// deleteOutputs

	public void deleteWorkers(String trxName) {

		for (MUNSProductionWorker worker : getWorkers()) {
			worker.deleteEx(true);
		}

	}// deleteWorkers
	
	// main function for production
	/**
	 * Create lines based on production header's definition.
	 * 
	 * @param mustBeStocked
	 * @param FGsAsi
	 * @return
	 */
	public int createLines(
			boolean mustBeStocked, String bomType, Hashtable<Integer, MAttributeSetInstance> FGsAsi) 
	{
		lineno = 100;
		count = 0;
		
		if (getIsCreated().equals(ISCREATED_Yes) && FGsAsi != null && FGsAsi.size()==0)
			throw new AdempiereException("Error when searching Attribute Set Instance");

		MProduct finishedProduct = null;
		MUNSProductionSchedule ps = new MUNSProductionSchedule(getCtx(), getUNS_ProductionSchedule_ID(), get_TrxName());
		BigDecimal productionQty;
		//int locatorInput_ID = getLocator(getCtx(), getUNS_Resource_ID(), get_TrxName());
		int locatorID = 0;

		if ("MLT".equals(getOutputType())) 
		{
			// primaryProduct to be produced
			finishedProduct = new MProduct(getCtx(), getPrimaryOutput().getM_Product_ID(), get_TrxName());
			MUNSProductionOutPlan productionOP = getPrimaryOutput();
			productionQty = productionOP.getQtyPlan();
			locatorID = productionOP.getM_Locator_ID();
		} 
		else 
		{
			finishedProduct = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
			productionQty = getProductionQty();
			locatorID = getM_Locator_ID();
		}

		MUNSProductionDetail line = new MUNSProductionDetail(this);
		line.setLine(lineno);
		line.setM_Product_ID(finishedProduct.getM_Product_ID());
		line.setM_Locator_ID(locatorID);
		line.setMovementQty(productionQty);
		line.setPlannedQty(productionQty);
		line.m_isProcess = true;
		
		if (FGsAsi != null && FGsAsi.size() > 0)
		{
			line.setM_AttributeSetInstance_ID(FGsAsi.get(line.getM_Product_ID()).get_ID());
		}

		line.saveEx();
		count++;

		if (OUTPUTTYPE_Multi.equals(getOutputType()) 
				|| OUTPUTTYPE_MultiOptional.equals(getOutputType())) 
		{
			MUNSProductionOutPlan[] otherOPs = getOtherOutput();
			
			for (MUNSProductionOutPlan otherOP : otherOPs) 
			{
				lineno = lineno + 10;
				MUNSProductionDetail detail = new MUNSProductionDetail(this);
				detail.setLine(lineno);
				detail.setIsEndProduct(true);
				detail.setM_Product_ID(otherOP.getM_Product_ID());
				detail.setM_Locator_ID(otherOP.getM_Locator_ID());
				detail.setMovementQty(Env.ZERO);
				detail.setPlannedQty(Env.ZERO);
				detail.m_isProcess = true;
				
				if (FGsAsi != null && FGsAsi.size() > 0)
				{
					detail.setM_AttributeSetInstance_ID(FGsAsi.get(detail.getM_Product_ID()).get_ID());
				}
				
				detail.saveEx();
				count++;
			}
			lineOtherProduct = lineno;
		}
		
		if(!MUNSProduction.PSTYPE_MasterProductionSchedule.equalsIgnoreCase(getPSType()))
		{
			MProduct sourceProduct = new MProduct(getCtx(), ps.getSourceProduct_ID(), get_TrxName());
			createLines(finishedProduct, sourceProduct, FGsAsi);
		} 
		else
			createLines(mustBeStocked, bomType, finishedProduct, line.getPlannedQty(), FGsAsi);
			//createLines(mustBeStocked, finishedProduct, line.getPlannedQty(), FGsAsi, locatorInput_ID);
		
		if (OUTPUTTYPE_Multi.equals(getOutputType()) 
				|| OUTPUTTYPE_MultiOptional.equals(getOutputType()))
		{
			for (int ln = 110; ln <= lineOtherProduct; ln = ln + 10) 
			{
				String sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail"
						+ " WHERE line=? AND UNS_Production_ID=?";
				int pd_id = DB.getSQLValue(get_TrxName(), sql, ln, getUNS_Production_ID());
				MUNSProductionDetail pd = new MUNSProductionDetail(getCtx(), pd_id, get_TrxName());
				pd.m_isProcess = true;
				
				int[] pdbom_ids = MUNSProductionDetail.getAllIDs(MUNSProductionDetail.Table_Name, 
						"UNS_Production_ID="+getUNS_Production_ID(), get_TrxName());
				for (int pdbom_id : pdbom_ids)
				{
					MUNSProductionDetail pdbom = new MUNSProductionDetail(getCtx(), pdbom_id, get_TrxName());	
					if (pdbom.getLine() <= lineOtherProduct)
						continue;
					
					pdbom.m_isProcess = true;
					addOutputLines(pdbom, pd);
				}
				
				//Comment at 2014/10/7 
//				String sqlbom = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail"
//						+ " WHERE line=? AND UNS_Production_ID=?";
//				int pdbom_id = 
//						DB.getSQLValue(get_TrxName(), sqlbom, lineOtherProduct+10, getUNS_Production_ID());
//				MUNSProductionDetail pdbom = new MUNSProductionDetail(getCtx(), pdbom_id, get_TrxName());					
//				pdbom.m_isProcess = true;
//				
				//addOutputLines(mustBeStocked, pdbom, pd);
			}
		}

		return count;
	}

	/**
	 * 
	 * @param finishedProduct
	 * @param sourceProduct
	 * @param FGsAsi
	 */
	private void createLines (
			MProduct finishedProduct, MProduct sourceProduct, 
			Hashtable<Integer, MAttributeSetInstance> FGsAsi) 
	{
		MUNSProductionDetail pdDetailEnd = new MUNSProductionDetail(this);
		
		if (MUNSProduction.PSTYPE_Rebundle.equalsIgnoreCase(getPSType())
			|| MUNSProduction.PSTYPE_Reprocess.equals(getPSType()))
			pdDetailEnd.setM_Product_ID(finishedProduct.get_ID());
		else
			pdDetailEnd.setM_Product_ID(sourceProduct.get_ID());
		
		if (MUNSProduction.OUTPUTTYPE_Single.equalsIgnoreCase(getOutputType()))
			pdDetailEnd.setLine(0);
		
		pdDetailEnd.setIsEndProduct(true);
		pdDetailEnd.setMovementQty(getProductionQty());
		pdDetailEnd.setM_Locator_ID(getM_Locator_ID());
		
		if (getIsCreated().equals(ISCREATED_Yes))	
			pdDetailEnd.setM_AttributeSetInstance_ID(FGsAsi.get(getM_Product_ID()).get_ID());
		else
			pdDetailEnd.setM_AttributeSetInstance_ID(0);
		pdDetailEnd.m_isProcess=true;
		
		pdDetailEnd.saveEx();
			//throw new AdempiereException("Can't save production detail");
		
		if (MUNSProduction.PSTYPE_Reprocess.equals(getPSType()))
			return;
		
		MUNSProductionDetail pdDetailSrc = new MUNSProductionDetail(this);
		pdDetailSrc.setIsEndProduct(false);
		pdDetailSrc.setMovementQty(getProductionQty());
		pdDetailSrc.setM_Locator_ID(MLocator.getDefault(
				MWarehouse.getForOrg(getCtx(), getAD_Org_ID())[0]).get_ID());
		pdDetailSrc.setM_Locator_ID(getM_Locator_ID());
		pdDetailSrc.setM_AttributeSetInstance_ID(0);
		pdDetailSrc.m_isProcess=true;
		
		pdDetailSrc.saveEx();
			//throw new AdempiereException("Can't save production detail");
		
	}

	/**
	 * 
	 * @param mustBeStocked
	 * @param finishedProduct
	 * @param requiredQty
	 * @param FGsAsi
	 * @return
	 */
	private int createLines(
			boolean mustBeStocked, String bomTypeToUse, MProduct finishedProduct,
			BigDecimal requiredQty, Hashtable<Integer, MAttributeSetInstance> FGsAsi) //, int M_Locator_ID) {
	{
		// products used in production
		/*
		String sql = "SELECT M_ProductBom_ID, BOMQty, M_Product_BOM_ID FROM M_Product_BOM"
				+ " WHERE IsActive='Y' AND M_Product_ID=" + finishedProduct.getM_Product_ID()
				+ " ORDER BY Line";
		*/
		String whereClause = "M_Product_ID=" + finishedProduct.getM_Product_ID();
		
		List<MProductBOM> bomList = 
				new Query(getCtx(), MProductBOM.Table_Name, whereClause, get_TrxName())
				.setOnlyActiveRecords(true)
				.setOrderBy(MProductBOM.COLUMNNAME_Line)
				.list();

		int defaultInputLocator = getDefaultInputLocatorOf(getCtx(), getUNS_Resource_ID(), get_TrxName());

		lineno = lineno + 10;
		
		for (MProductBOM bom : bomList) 
		{
			if (bomTypeToUse != null && !bom.getBOMType().equals(bomTypeToUse))
				continue;
			
			int BOMProduct_ID = bom.getM_ProductBOM_ID();//rs.getInt(1);
			BigDecimal BOMQty = bom.getBOMQty();//rs.getBigDecimal(2);
			//int ProductBOM_ID = bom.getM_Product_BOM_ID();//rs.getInt(3);
			BigDecimal BOMMovementQty = BOMQty.multiply(requiredQty);

			int inputLocator = getLocator(getCtx(), getUNS_Resource_ID(), BOMProduct_ID, get_TrxName());
			if (inputLocator <= 0)
			{
				if (defaultInputLocator <= 0)
					throw new AdempiereException("You don't have any resource's input locator define yet.");
				else 
					inputLocator = defaultInputLocator;
			}
			
			MLocator productionLocator = MLocator.get(getCtx(), inputLocator);
			int M_Warehouse_ID = productionLocator.getM_Warehouse_ID();

			MProduct bomproduct = MProduct.get(Env.getCtx(), BOMProduct_ID);
			//MProductBOM productBOM = new MProductBOM(getCtx(), ProductBOM_ID, get_TrxName());

			if (bomproduct.isBOM() && bomproduct.isPhantom()) 
			{
				//createLines(mustBeStocked, bomproduct, BOMMovementQty, FGsAsi, M_Locator_ID);
				createLines(mustBeStocked, bomTypeToUse, bomproduct, BOMMovementQty, FGsAsi);
				continue;
			} 
			//else 
			//{
			// It is not a phantom, so create it.

			if (!bomproduct.isStocked() || !mustBeStocked) 
			{
				MUNSProductionDetail BOMLine = null;
				BOMLine = new MUNSProductionDetail(this);
				BOMLine.setLine(lineno);
				BOMLine.setM_Product_ID(BOMProduct_ID);
				BOMLine.setM_Locator_ID(inputLocator);
				BOMLine.setQtyUsed(BOMMovementQty);
				BOMLine.setPlannedQty(BOMMovementQty);
				BOMLine.saveEx(get_TrxName());
				BOMLine.m_isProcess=true;

				lineno = lineno + 10;
				count++;
			} 
			else if (BOMMovementQty.signum() == 0) 
			{
				MUNSProductionDetail BOMLine = null;
				BOMLine = new MUNSProductionDetail(this);
				BOMLine.setLine(lineno);
				BOMLine.setM_Product_ID(BOMProduct_ID);
				BOMLine.setM_Locator_ID(inputLocator);
				BOMLine.setQtyUsed(BOMMovementQty);
				BOMLine.setPlannedQty(BOMMovementQty);
				BOMLine.saveEx(get_TrxName());
				BOMLine.m_isProcess=true;

				lineno = lineno + 10;
				count++;
			} 
			else 
			{
				// BOM stock info
				MStorageOnHand[] storages = null;
				MProduct usedProduct = bomproduct;//MProduct.get(getCtx(), BOMProduct_ID);
				/*
				if (inputLocator == 0)
					throw new AdempiereException("Not found Production Locator");
				*/
				if (usedProduct == null || usedProduct.get_ID() == 0)
					return 0;

				MClient client = MClient.get(getCtx());
				MProductCategory pc = 
						MProductCategory.get(getCtx(), usedProduct.getM_Product_Category_ID());
				String MMPolicy = pc.getMMPolicy();
				
				if (MMPolicy == null || MMPolicy.length() == 0) 
				{
					MMPolicy = client.getMMPolicy();
				}
				
				int locToGet = inputLocator;
				int wsToGet = M_Warehouse_ID;
				/* Remark this.
				if (PSTYPE_Rebundle.equals(getPSType()) 
					|| PSTYPE_Restickering.equals(getPSType()) 
					|| PSTYPE_Rework.equals(getPSType())) {
					locToGet = 0;
					wsToGet = 0;
				}
				*/
				storages = MStorageOnHand
						.getWarehouse(getCtx(), wsToGet, BOMProduct_ID, 0, null,
								MProductCategory.MMPOLICY_FiFo.equals(MMPolicy), true, locToGet,
								get_TrxName());
				if (storages.length == 0)
				{
					/** skip creating lines when optional product not found in locator */
					if (MProductBOM.BOMTYPE_OptionalPart.equals(bom.getBOMType()))
					{
						continue;
					} 
					else
						throw new AdempiereException ("Can not find any stock available of Product : " + 
												  usedProduct.getValue() + " at Locator : " + 
												  productionLocator.getValue() + ".");
					//storages = getWHProduct(getCtx(), get_TrxName(), BOMProduct_ID);
				}
				MUNSProductionDetail BOMLine = null;
				int prevLoc = -1;
				int previousAttribSet = -1;
				// Create lines from storage until qty is reached
				for (int sl = 0; sl < storages.length; sl++) 
				{
					BigDecimal lineQty = storages[sl].getQtyOnHand();
					if (lineQty.signum() != 0) 
					{
						if (lineQty.compareTo(BOMMovementQty) > 0)
							lineQty = BOMMovementQty;

						int loc = storages[sl].getM_Locator_ID();
						int slASI = storages[sl].getM_AttributeSetInstance_ID();
						int locAttribSet = new MAttributeSetInstance(getCtx(), slASI, get_TrxName())
								.getM_AttributeSet_ID();

						// roll up costing attributes if in the same
						// locator (default idempiere)
						//if (locAttribSet == 0 && previousAttribSet == 0
						//		&& prevLoc == loc) {
						
						/**
						 * @author YAKA
						 * roll up if in the same locator
						 * hard code when search old value QytUsed
						 */
						if(prevLoc == loc) {
							BOMLine.setQtyUsed(((BigDecimal) BOMLine.get_ValueOld(13)).add(lineQty));
							BOMLine.setPlannedQty(BOMLine.getQtyUsed());
							BOMLine.m_isProcess = true;
							BOMLine.saveEx(get_TrxName());
						}
						// otherwise create new line
						else {
							BOMLine = new MUNSProductionDetail(this);
							BOMLine.setLine(lineno);
							BOMLine.setM_Product_ID(BOMProduct_ID);
							BOMLine.setM_Locator_ID(loc);
							BOMLine.setQtyUsed(lineQty);
							BOMLine.setPlannedQty(lineQty);
							BOMLine.m_isProcess = true;
//									if (slASI != 0 && locAttribSet != 0) // ie non costing attribute
//										BOMLine.setM_AttributeSetInstance_ID(slASI);
							BOMLine.saveEx(get_TrxName());
							lineno = lineno+10;
							count++;
						}
						prevLoc = loc;
						previousAttribSet = locAttribSet;
						// enough ?
						BOMMovementQty = BOMMovementQty.subtract(lineQty);
						if (BOMMovementQty.signum() == 0)
							break;
					}
				} // for available storages

				// fallback
				if (BOMMovementQty.signum() != 0) 
				{
					if (!mustBeStocked) 
					{
						// roll up costing attributes if in the same
						// locator
						if (previousAttribSet == 0 && prevLoc == inputLocator) 
						{
							BOMLine.setQtyUsed(BOMLine.getQtyUsed().add(BOMMovementQty));
							BOMLine.setPlannedQty(BOMLine.getQtyUsed());
							BOMLine.m_isProcess = true;
							BOMLine.saveEx(get_TrxName());
						}
						// otherwise create new line
						else 
						{
							BOMLine = new MUNSProductionDetail(this);
							BOMLine.setLine(lineno);
							BOMLine.setM_Product_ID(BOMProduct_ID);
							BOMLine.setM_Locator_ID(inputLocator);
							BOMLine.setQtyUsed(BOMMovementQty);
							BOMLine.setPlannedQty(BOMMovementQty);
							BOMLine.m_isProcess = true;
							BOMLine.saveEx(get_TrxName());

							lineno = lineno + 10;
							count++;
						}

					} else {
						throw new AdempiereException("Insufficient stock of " + bomproduct.getName() + 
								" at locator " + productionLocator.getValue());
					}
				}
			}
		} // for all bom products

		return count;
	}
	
	/**
	 * 
	 * @param mustBeStocked
	 * @param pdbom
	 * @param pd
	 * @return
	 */
	private int addOutputLines(MUNSProductionDetail pdbom, MUNSProductionDetail pd) {

		// products used in production
		String sql = "SELECT BOMQty FROM M_Product_BOM"
				+ " WHERE M_Product_ID=" + pd.getM_Product_ID()
				+ " AND M_ProductBOM_ID=" + pdbom.getM_Product_ID()
				+ " ORDER BY Line";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				BigDecimal BOMQty = rs.getBigDecimal(1);
				BigDecimal BOMPlanQty = pdbom.getPlannedQty();
				BigDecimal BOMMovementQty = BigDecimal.ZERO;
				if (BOMQty.compareTo(Env.ZERO) > 0)
					BOMMovementQty = BOMPlanQty.multiply(BOMQty);

				pd.setMovementQty(pd.getMovementQty().add(BOMMovementQty));
				pd.setPlannedQty(pd.getMovementQty().add(BOMMovementQty));
				pd.m_isProcess = true;
				pd.saveEx(get_TrxName());
				
			} // for all bom products
		} catch (Exception e) {
			throw new AdempiereException("Failed to add output production", e);
		} finally {
			DB.close(rs, pstmt);
		}
		
		return count;		
	}
	
//	/**
//	 * 
//	 * @param mustBeStocked
//	 * @param pdbom
//	 * @param pd
//	 * @return
//	 */
//	private int addOutputLines(boolean mustBeStocked, MUNSProductionDetail pdbom,
//			MUNSProductionDetail pd) {
//
//		// products used in production
//		String sql = "SELECT BOMQty FROM M_Product_BOM"
//				+ " WHERE M_Product_ID=" + pd.getM_Product_ID()
//				+ " ORDER BY Line";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			pstmt = DB.prepareStatement(sql, get_TrxName());
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				BigDecimal BOMQty = rs.getBigDecimal(1);
//				BigDecimal BOMPlanQty = pdbom.getPlannedQty();
//				BigDecimal BOMMovementQty = BigDecimal.ZERO;
//				if (BOMQty.compareTo(Env.ZERO) > 0)
//					BOMMovementQty = BOMPlanQty.divide(BOMQty, 4, RoundingMode.HALF_DOWN);
//
//				pd.setMovementQty(BOMMovementQty);
//				pd.setPlannedQty(BOMMovementQty);
//				pd.m_isProcess = true;
//				pd.saveEx(get_TrxName());
//				
//			} // for all bom products
//		} catch (Exception e) {
//			throw new AdempiereException("Failed to add output production", e);
//		} finally {
//			DB.close(rs, pstmt);
//		}
//		
//		return count;		
//	}
	
	public static MUNSProduction getFromOrder(
			Properties ctx, String trxName, org.compiere.model.MOrderLine orderLine) 
	{
		String sql = "SELECT UNS_Production_ID FROM UNS_SOShipment_V WHERE C_OrderLine_ID=?";
		int p_id = DB.getSQLValue(trxName, sql, orderLine.get_ID());
		if (p_id==-1)
			return null; 
		return new MUNSProduction(ctx, p_id, trxName);
	}

	public static String getOutputType(Properties ctx, int production_id, String trxName) {
		MUNSProduction p = new MUNSProduction(ctx, production_id, trxName);

		return p.getOutputType();
	}

	/**
	 * 
	 * @param ctx
	 * @param resource_id
	 * @param trxName
	 * @return
	 */
	public static int getLocator(Properties ctx, int resource_id, int M_Product_ID, String trxName) 
	{
		String sql="SELECT M_Locator_ID FROM UNS_Resource_Locator Where UNS_Resource_ID=? AND M_Product_ID=?";
		int id = DB.getSQLValue(trxName, sql, resource_id, M_Product_ID);
		return id;
	}

	/**
	 * Get default production's input locator of a Resource.
	 * 
	 * @param ctx
	 * @param resource_id
	 * @param trxName
     * @return first value or -1 if not found or error
	 */
	public static int getDefaultInputLocatorOf(Properties ctx, int resource_id, String trxName) 
	{
		String sql="SELECT M_Locator_ID FROM UNS_Resource_Locator Where UNS_Resource_ID=? AND IsDefault=?";
		int id = DB.getSQLValue(trxName, sql, resource_id, "Y");
		return id;
	}
	
	/**
	 * get warehouse id from storage on hand with quantity > 0
	 */
	public static MStorageOnHand[] getWHProduct(Properties ctx, String trxName, int product_id)
	{
		String whereClauseFinal = COLUMNNAME_M_Product_ID + "=? "+"AND QtyOnHand > 0 ";
		
		List<MStorageOnHand> list =
				Query.get(ctx, UNSPPICModelFactory.getExtensionID(), 
						MStorageOnHand.Table_Name, whereClauseFinal, trxName)
						.setParameters(product_id).setOrderBy(
								MStorageOnHand.COLUMNNAME_QtyOnHand)
								.list();

		MStorageOnHand[] soh = new MStorageOnHand[list.size()];
		
		return list.toArray(soh);
	}
	
	/**
	 * 
	 * @param MP1Form
	 * @return
	 */
//	public static MUNSProduction[] CreateUpdateProduction(MUNSMP1Form MP1Form){
//		Properties ctx = MP1Form.getCtx();
//		String trxName = MP1Form.get_TrxName();
//		
//		List<MUNSProduction> productionList = new ArrayList<MUNSProduction>();
//		
//		MUNSMP1FormDetail[] details = MP1Form.getLines(true);
//		
//		for (MUNSMP1FormDetail detail : details)
//		{
//			//SKIP create production if WM or CW is not 0 (MPD_ORG)
//			if (MP1Form.getAD_OrgTo_ID() == UNSApps.getRefAsInt(UNSApps.MPD_ORG)
//					&& detail.getWhiteMeatBW().signum() <= 0 
//					&& detail.getCoconutWaterBS().signum() <= 0)
//				continue;
//			
//			//SKIP create production if KC is not 0 (LKU_ORG)
//			if (MP1Form.getAD_OrgTo_ID() == UNSApps.getRefAsInt(UNSApps.LKU_ORG)
//					&& detail.getKC().signum() <= 0 
//					&& detail.getKCExtra().signum() <= 0)
//				continue;
//			
//			
//			if (detail.getWhiteMeatBW().signum() > 0
//					|| detail.getCoconutWaterBS().signum() > 0
//					|| detail.getKC().signum() > 0) 
//			{
//				MUNSProduction production = new MUNSProduction(ctx,
//						detail.getUNS_Production_ID(), trxName);
//
//				production.setProduction(detail, false);
//				if (!production.save())
//					throw new AdempiereException(
//							"Data has not valid to create/update Production. "
//									+ "Please Contact System Administrator.");
//
//				if (detail.getUNS_Production_ID() == 0) {
//					MUNSProductionDetail.createDetails(production, detail, false);
//					MUNSProductionWorker.createWorkers(production, detail);
//
//					detail.setUNS_Production_ID(production.get_ID());
//					detail.saveEx();
//
//					productionList.add(production);
//				} else {
//					for (MUNSProductionDetail pd : production.getLines()) {
//						pd.updateProductionDetail(detail, false);
//						pd.saveEx();
//					}
//
//					// delete UnSycn Worker
//					MUNSProductionWorker[] workers = production.getWorkers();
//					for (int count = 0; count < workers.length; count++) {
//						if (workers[count].updateProductionWorker(detail, count) == 0)
//							workers[count].delete(false);
//						else
//							workers[count].saveEx();
//					}
//
//					productionList.add(production);
//				}
//			}
//			
//			//isOvertime LKU
//			if (detail.getKCExtra().signum() > 0)
//			{
//				MUNSProduction overtimeProduction = new MUNSProduction(ctx, 
//						detail.getOVT_Production_ID(), trxName);
//				
//				overtimeProduction.setProduction(detail, true);
//				if (!overtimeProduction.save())
//					throw new AdempiereException(
//							"Data has not valid to create/update Production. "
//							+ "Please Contact System Administrator.");
//				
//				if(detail.getOVT_Production_ID() == 0)
//				{
//					MUNSProductionDetail.createDetails(overtimeProduction, detail, true);
//					MUNSProductionWorker.createWorkers(overtimeProduction, detail);
//					
//					detail.setOVT_Production_ID(overtimeProduction.get_ID());
//					detail.saveEx();
//					
//					productionList.add(overtimeProduction);
//				}
//				else 
//				{
//					for (MUNSProductionDetail pd :  overtimeProduction.getLines()){
//						pd.updateProductionDetail(detail, true);
//						pd.saveEx();
//					}
//
//					//delete UnSycn Worker
//					MUNSProductionWorker[] workers = overtimeProduction.getWorkers();
//					for(int count = 0 ; count < workers.length; count++){
//						if (workers[count].updateProductionWorker(detail, count)==0)
//							workers[count].delete(false);
//						else
//							workers[count].saveEx();
//					}
//					
//					
//					productionList.add(overtimeProduction);
//				}
//			}
//		}
//		
//		MUNSProduction[] retval = new MUNSProduction[productionList.size()];
//		productionList.toArray(retval);
//		
//		return retval;
//	}

	/**
	 * 
	 * @param detail
	 * @param overtime
	 */
//	private void setProduction(MUNSMP1FormDetail detail, boolean overtime) {
//		MUNSMP1Form form =  detail.getMP1Form();
//		
//		if (detail.getNomer()<10)
//			setDocumentNo(form.getDocumentNo()+"/0"+detail.getNomer());
//		else
//			setDocumentNo(form.getDocumentNo()+"/"+detail.getNomer());
//		
//		if (form.getAD_OrgTo_ID() == UNSApps.getRefAsInt(UNSApps.MPD_ORG))
//		{
//			if (form.isKBPecahSegar())
//				setWorkerResultType(WORKERRESULTTYPE_PersonalResult);
//			else
//				setWorkerResultType(WORKERRESULTTYPE_GroupResult);
//			
//			setProductionQty(detail.getWhiteMeatBW());
//		}
//		else if (form.getAD_OrgTo_ID() == UNSApps.getRefAsInt(UNSApps.LKU_ORG))
//		{
//			setWorkerResultType(WORKERRESULTTYPE_PersonalResult);
//			if (!overtime)
//				setProductionQty(detail.getKC());
//			else
//				setProductionQty(detail.getKCExtra());
//		}
//		else
//			setProductionQty(Env.ZERO);
//		
//		setAD_Org_ID(form.getAD_OrgTo_ID());
//		setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_Production));
//		setDocumentNo(form.getDocumentNo()+"/"+detail.getNomer());
//		setPSType(PSTYPE_MasterProductionSchedule);
//		setNameDescrip(detail, overtime);
//		setDate(form.getProductionDate());
//		
//		setResource(detail.getResource());
//		setIsEndingStockBase(true);
//		setUNS_ProductionPayConfig_ID(form.getUNS_ProductionPayConfig_ID());
//		
//		if (getDocStatus()!=null && !getDocStatus().equals(STATUS_Drafted))
//			throw new AdempiereException(
//					"Can't change processed Production. Please Contact System Administrator.");
//		
//		setDocStatus(STATUS_Drafted);
//		setDocAction(DOCACTION_Prepare);
//	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		//if("MOP".equals(getOutputType()))
		//	throw new AdempiereException("Multi Optional Output is not implemented yet.");
		if(!PSTYPE_MasterProductionSchedule.equals(getPSType())){
			if (!PSTYPE_Reprocess.equals(getPSType()) && OUTPUTTYPE_Multi.equals(getOutputType()))
				throw new AdempiereException("Production Type except MPS must be Single/Optional Output");
		}
		//if(getUNS_ProductionSchedule_ID()==0)
		//	if(isMustSyncWithMPS())
		//		throw new AdempiereException("This resource must be using manufacturing order");
		
		if (!newRecord && is_ValueChanged(COLUMNNAME_MovementDate))
		{
			// just try to update the output's asi's creation date to this movement date.
			String sql = "UPDATE M_AttributeSetInstance SET Created=? "
					+ "WHERE M_AttributeSetInstance_ID IN "
					+ "		(SELECT pd.M_AttributeSetInstance_ID FROM UNS_Production_Detail pd "
					+ "		 WHERE pd.UNS_Production_ID=? AND pd.IsEndProduct='Y')";
			
			DB.executeUpdateEx(sql, new Object[]{getMovementDate(), getUNS_Production_ID()}, get_TrxName());
		}
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
		if (isReversal() || isGeneratingOutPlan)
			return true;
		
		if (getProductionQty().compareTo(Env.ZERO) <= 0 && getM_Product_ID() > 0)
			throw new FillMandatoryException(
					"(Production Quantity) :: If default product is set, you have to set "
					+ "production quantity to greater than zero value.");
		
		BigDecimal oldValue = (BigDecimal) get_ValueOld(COLUMNNAME_ProductionQty);
		BigDecimal newValue = (BigDecimal) get_Value(COLUMNNAME_ProductionQty);
		boolean isWSChanged = is_ValueChanged(COLUMNNAME_UNS_Resource_ID);
		
		if (!success)
			return success;
		else if (!newRecord && oldValue.compareTo(newValue)==0 && !isWSChanged)
			;//	return success;
		else if ((newRecord && getM_Product_ID() > 0) || isWSChanged)
		{
			//if (newRecord)
			if (!MUNSProductionOutPlan.generateOutPlan(getCtx(), get_TrxName(), this, getOutputType()))
				return false;
			
			//MUNSProductionOutPlan plan = getPrimaryOutput();
			//plan.setQtyPlan(getProductionQty());
			
			//return plan.save();
		}
		
		if (newRecord & isWorkerBase()) {
			MUNSProductionWorker.generateWorker(getCtx(), get_TrxName(), this);
		}
		else if (!newRecord)
		{
			boolean prevWorkerBase = (Boolean) get_ValueOld(COLUMNNAME_IsWorkerBase);
					//((prevWorkerBaseStr != null) && prevWorkerBaseStr.equals("Y"))? true : false;
			boolean isRscChanged = is_ValueChanged(COLUMNNAME_UNS_Resource_ID);
			
			if (prevWorkerBase && !this.isWorkerBase()) 
			{
				String msg = "You are about to change this production from worker base to not involving worker. "
						+ "\nDo you want to automatically remove all of the worker list you have defined?";
				String title = "Automatically remove defined worker list?";				
				int answer = 
						MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
				
				if (answer == MessageBox.RETURN_YES)
					deleteWorkers(get_TrxName());
			}
			else if ((this.isWorkerBase() && (prevWorkerBase != this.isWorkerBase())) 
					|| (isRscChanged && this.isWorkerBase())) 
			{
				boolean isWorkerListed = false;
				if (!prevWorkerBase) {
					int count = DB.getSQLValue(get_TrxName(), 
							"SELECT COUNT(*) FROM UNS_Production_Worker WHERE UNS_Production_ID=?",
							this.get_ID());
					isWorkerListed = count > 0 ? true : false;
				}
				
				if (isWorkerListed || prevWorkerBase)
				{
					String msg = "You are about to change the resource of this production. "
							+ "Do you want to automatically reset all of the current worker list you have defined?";
					String title = "Automatically reset current defined worker list?";
					
					int answer = 
							MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
					
					if (answer == MessageBox.RETURN_YES)
						deleteWorkers(get_TrxName());
				}
				MUNSProductionWorker.generateWorker(getCtx(), get_TrxName(), this);
			}
		}

		return success;
	}
	
	
	
	@Override
	protected boolean beforeDelete() {
		if(!STATUS_Drafted.equals(getDocStatus()))
			return false;
		
		deleteLines(get_TrxName());
		deleteOutputs(get_TrxName());
		deleteWorkers(get_TrxName());
		return true;
	}

	public static MUNSImportContent[] getPrevImportList(
			Properties ctx, String trxName, int AttributeSetInstance_ID, int productBom_ID){
		ArrayList<MUNSImportContent> icList = new ArrayList<MUNSImportContent>();
		MAttributeInstance[] listAI = MAttributeInstance.getAttributeInstance(
				ctx, trxName, AttributeSetInstance_ID);
		for(MAttributeInstance ai : listAI){
			MAttribute att =  new MAttribute(ctx, ai.getM_Attribute_ID(), trxName);
			if (!att.getName().equalsIgnoreCase("Production No"))
				continue;
			if(null != ai.getValue()){
				String p_no = ai.getValue();
				MUNSProduction p = MUNSProduction.getProduction(ctx, trxName, p_no);
				if (p == null)
					return null;
				for (MUNSImportContent ic : p.getImportContent()){
						icList.add(ic);
				}
				return icList
						.toArray(new MUNSImportContent[icList.size()]);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param DocumentNo
	 * @return
	 */
	public static MUNSProduction getProduction(Properties ctx, String trxName, String DocumentNo){
		String whereClause="DocumentNo='"+DocumentNo+"'";
		
		MUNSProduction retval = Query.get(ctx, UNSPPICModelFactory.getExtensionID(),
				MUNSProduction.Table_Name, whereClause, trxName).first();
		
		return retval;
	}
	
	/**
	 * method to get All Attribute Set Instance for FGs.
	 * use after 
	 * @param Context 
	 * @param TrxName
	 * @param MUNSProduction
	 * @return
	 */
	public static Hashtable<Integer, MAttributeSetInstance> 
		getFGsAsi (Properties ctx, String trxName, MUNSProduction p)
	{
		Hashtable<Integer, MAttributeSetInstance> FGsASI = new Hashtable<Integer, MAttributeSetInstance>();
		
		MUNSProductionDetail[] pdLines = p.getLines();
		if (pdLines.length==0)
			throw new AdempiereException("Please create production plan first!");
		
		for (MUNSProductionDetail pd : pdLines)
		{
			if(pd.isEndProduct())
			{
				if(pd.getM_AttributeSetInstance_ID()== 0)
					throw new AdempiereException(pd.getM_Product().getName() + " doesn't have Attribute Instance.");
				else 
				{
					MAttributeSetInstance asi = new MAttributeSetInstance(
							ctx, pd.getM_AttributeSetInstance_ID(), trxName);
					FGsASI.put(pd.getM_Product_ID(), asi);
				}
			}
		}
		return FGsASI;
	}
	
	/**
	 * Create daily payment for worker
	 * @return
	 *
	public String createDailyReceivableAmtOfWorker()
	{
		String msg = "";
		Calendar calendarProduction = Calendar.getInstance();
		calendarProduction.setTimeInMillis(getMovementDate().getTime());
		
		MUNSResource rsc = (MUNSResource) getUNS_Resource();
		MUNSSlotType slotType = rsc.getSlotType();
		MUNSProductionPayConfig productionPayConfig = 
				new MUNSProductionPayConfig (getCtx(), getUNS_ProductionPayConfig_ID(), get_TrxName());
		
		for (MUNSProductionWorker productionWorker : getWorkers())
		{
			int labor_ID = productionWorker.getLabor_ID();
			if(productionWorker.getReplacementLabor_ID() > 0)
				labor_ID = productionWorker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(labor_ID);
			if(null == workerOfResource)
				continue;
			/*
			MUNSProductionPayConfig productionPayConfig = 
					MUNSProductionPayConfig.get(
					getCtx(), getAD_Org_ID(), productionWorker.getEmploymentStatus(), 
					getMovementDate(), get_TrxName());
			*
			
			MUNSHalfPeriodPresence halfPeriodPresence = 
					MUNSHalfPeriodPresence.get(
							getCtx(), labor_ID, getMovementDate(), 
							getAD_Org_ID(), getUNS_Resource().getResourceParent_ID(), get_TrxName());
			
			if (null == halfPeriodPresence)
			{
				msg = "No Period Presence Record of worker " 
						+ productionWorker.getLabor().getName() + " for date of " + getMovementDate()
						+ ", please create that.";
				return msg;
			}
			
			BigDecimal ReceivableAmt = BigDecimal.ZERO;
			double insentifPemborong = productionPayConfig.getSupervisedInsentifPemborong().doubleValue();
			
			MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
					getMovementDate(), getUNS_Resource_ID());
			
			if (null == workerPresence)
			{
				workerPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
				workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
				workerPresence.setWorkDate(getMovementDate());
				workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			}
			
			if(isOverTime())
			{
				workerPresence.setOT_ProductionWOrker_ID(productionWorker.get_ID());
				workerPresence.setOvertime(getHoursOverTime());
			}
			else
			{
				workerPresence.setUNS_Production_Worker_ID(productionWorker.get_ID());
			}
			
			workerPresence.setUNS_Resource_ID(getUNS_Resource_ID());
			workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			
			boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));

			if (!isWorkDay)
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguanLiburNasional);
				ReceivableAmt = productionPayConfig.getSupervisedPayFullHoliday();
				
				if(isOverTime())
				{
					ReceivableAmt = productionPayConfig.getSupervisedPayHolidayOTPerHour()
										.multiply(getHoursOverTime());
					workerPresence.setOvertime(getHoursOverTime());
					workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Lembur);
				}
			}
			else if (MNonBusinessDay.isNonBusinessDay(getCtx(), getMovementDate(), get_TrxName()))
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguanLiburNasional);
				ReceivableAmt = productionPayConfig.getSupervisedPayNHolidayOTPerHour();
				
				if(isOverTime())
				{
					ReceivableAmt = productionPayConfig.getSupervisedPayNHolidayOTPerHour()
							.multiply(getHoursOverTime());
					workerPresence.setOvertime(getHoursOverTime());
					workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Lembur);
				}
			}
			else
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
				ReceivableAmt = productionPayConfig.getSupervisedPayFullDay();
				
				if(isOverTime())
				{
					ReceivableAmt = productionPayConfig.getSupervisedPayOTPerHour()
										.multiply(getHoursOverTime());
					workerPresence.setOvertime(getHoursOverTime());
					workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Lembur);
				}
			}
			workerPresence.setProductionQty(BigDecimal.ZERO);
			workerPresence.setReceivableAmt(
					workerPresence.getReceivableAmt().add(ReceivableAmt));
			if (!workerPresence.save())
			{
				msg = "Failed to create Worker Presence ";
				return msg;
			}
			
			productionWorker.setReceivableAmt(ReceivableAmt);
			productionWorker.setInsentifPemborong(new BigDecimal(insentifPemborong));
			productionWorker.saveEx();

			Hashtable<Integer, productionProduct> mapOfProductionProduct =
					new Hashtable<Integer, productionProduct>();
			
			for(MUNSProductionDetail pd : getLines())
			{
				MUNSMOutCostingConfig moutCosting =
						MUNSMOutCostingConfig.get(getCtx(), pd.getM_Product_ID(), get_TrxName());
				if(null == moutCosting)
					continue;
				productionProduct pp = mapOfProductionProduct.get(pd.getM_Product_ID());
			
				if(null == pp)
				{
					pp = new productionProduct(pd.getM_Product_ID());
					mapOfProductionProduct.put(pd.getM_Product_ID(), pp);
				}
				pp.setQtyProduction(pp.getQtyProduction().add(pd.getMovementQty()));
			}
			
			for(productionProduct pp : mapOfProductionProduct.values())
			{
				MUNSProductionWorkerResult wr = new MUNSProductionWorkerResult(getCtx(), 0, get_TrxName());
				wr.setAD_Org_ID(getAD_Org_ID());
				wr.setDescription("");
				wr.setInsentifPemborong(new BigDecimal(
						insentifPemborong / mapOfProductionProduct.values().size()));
				wr.setM_Product_ID(pp.getM_Product_ID());
				wr.setProductionQty(pp.getQtyProduction().divide(
						new BigDecimal(mapOfProductionProduct.values().size()), 4, RoundingMode.HALF_UP));
				
				wr.setUNS_Production_Worker_ID(productionWorker.get_ID());
				wr.saveEx();
			}
		}
		return msg;
	}
	*/

	/**
	 * Create Worker Presence Group result
	 * @return
	 * @deprecated
	 */
	public String createGroupResultReceivableAmtOfWorker()
	{
		String msg= "";
		
		Calendar calendarProduction = Calendar.getInstance();
		calendarProduction.setTimeInMillis(getMovementDate().getTime());
		
		Hashtable<Integer, Double>  mapOfQtyProduction = new Hashtable<Integer, Double>();
		
		for (MUNSProductionWorker primaryWorker : getPrimaryWorker())
		{	
			double totalPayForWorker = 0.0;
			double totalInsentifPemborong = 0.0;

			int labor_ID = primaryWorker.getLabor_ID();
			if(primaryWorker.getReplacementLabor_ID() > 0)
				labor_ID = primaryWorker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(
					labor_ID);
			MUNSSlotType slotType = m_Resource.getSlotType();
			boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));
			boolean isNonBusinesDay = MNonBusinessDay.isNonBusinessDay(
					getCtx(), getMovementDate(), getAD_Org_ID(), get_TrxName());
			
			MUNSProductionPayConfig productionPayConfig = MUNSProductionPayConfig.get(
					getCtx(), getAD_Org_ID(), getMovementDate(), get_TrxName());
			
			for (MUNSProductionDetail pd : getLines())
			{
				Double qtyProduction = mapOfQtyProduction.get(pd.getM_Product_ID());
				if (null == qtyProduction)
					qtyProduction = pd.getMovementQty().doubleValue();
				
				double pay = 0.0;
				
				MUNSPayRollLine payRollLine = productionPayConfig.getCriteriaLine(
						primaryWorker.getUNS_Job_Role_ID(), 
						pd.getM_Product_ID(), 
						0, 
						MUNSPayRollLine.CRITERIATYPE_Product
						, null);
				if (null == payRollLine)
					continue;
				
				double proportionOfWorker = workerOfResource.getResultProportion().doubleValue();
				double insentifPemborong = payRollLine.getInsentifPemborong().doubleValue();
				double qtyProductionOfWorker = pd.getMovementQty().doubleValue() * proportionOfWorker /100;
				double totalPay = 0.0;
				qtyProduction -= qtyProductionOfWorker;
				
				if(payRollLine.isTargetBased())
				{
					MUNSPayRollPremiTarget criteriaProductTarget =
							payRollLine.getCriteriaTarget(new BigDecimal(qtyProduction));
					if(null != criteriaProductTarget)
					{
						if (isNonBusinesDay)
						{
							pay = payRollLine.getPayNationalHoliday().doubleValue();
							if(isOverTime())
								pay = payRollLine.getPayOverTime().doubleValue();
						}
						else if (!isWorkDay)
						{
							pay = payRollLine.getPaySunday().doubleValue();
							if(isOverTime())
								pay = payRollLine.getPayOverTime().doubleValue();
						}
						else
						{
							pay = criteriaProductTarget.getPay().doubleValue();
							if(isOverTime())
								pay = payRollLine.getPayOverTime().doubleValue();
						}
					}
				}
				else
				{
					if (isNonBusinesDay)
					{
						pay = payRollLine.getPayNationalHoliday().doubleValue();
						if(isOverTime())
							pay = payRollLine.getPayOverTime().doubleValue();
					}
					else if (!isWorkDay)
					{
						pay = payRollLine.getPaySunday().doubleValue();
						if(isOverTime())
							pay = payRollLine.getPayOverTime().doubleValue();
					}
					else
					{
						pay = payRollLine.getPay().doubleValue();
						if(isOverTime())
							pay = payRollLine.getPayOverTime().doubleValue();
					}
				}
					
				mapOfQtyProduction.put(pd.getM_Product_ID(), qtyProduction);
				
				totalPay = pay * qtyProductionOfWorker;
				totalPayForWorker += totalPay;
				totalInsentifPemborong += insentifPemborong;
				
				MUNSProductionWorkerResult workerResult = 
						new MUNSProductionWorkerResult(getCtx(), 0, get_TrxName());
				workerResult.setUNS_Production_Worker_ID(primaryWorker.get_ID());
				workerResult.setAD_Org_ID(primaryWorker.getAD_Org_ID());
				workerResult.setDescription("Auto Generate");
				workerResult.setInsentifPemborong(new BigDecimal(insentifPemborong));
				workerResult.setM_Product_ID(pd.getM_Product_ID());
				workerResult.setProductionQty(new BigDecimal(qtyProductionOfWorker));
				workerResult.setReceivableAmt(new BigDecimal(totalPay));
				workerResult.save();
			}

			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					getCtx(), labor_ID, getMovementDate(), 
					getAD_Org_ID(), getUNS_Resource().getResourceParent_ID(), get_TrxName());
			
			if ( null == halfPeriodPresence)
			{
				msg = "Please create Half Period First for this date " + getMovementDate();
				return msg;
			}
			
			MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
					getMovementDate(), m_Resource.getUNS_Resource_ID());
			
			if (null == workerPresence)
			{
				workerPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
				workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
				workerPresence.setWorkDate(getMovementDate());
				workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			}
			
			if (!isWorkDay || isNonBusinesDay)
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
			}
			else
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			}
			workerPresence.setUNS_Resource_ID(getUNS_Resource_ID());
			workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);

			if(isOverTime())
			{
				workerPresence.setOT_ProductionWOrker_ID(primaryWorker.get_ID());
				workerPresence.setOvertime(getHoursOverTime());
			}
			else
			{
				workerPresence.setUNS_Production_Worker_ID(primaryWorker.get_ID());
			}
			
			workerPresence.setReceivableAmt(
					workerPresence.getReceivableAmt().add(new BigDecimal(totalPayForWorker)));
			workerPresence.save();
			
			primaryWorker.setReceivableAmt(new BigDecimal(totalPayForWorker));
			primaryWorker.setInsentifPemborong(new BigDecimal(totalInsentifPemborong));
			primaryWorker.save();
		}
		
		for (MUNSProductionWorker worker : getWorkers())
		{
			double totalPayForWorker = 0.0;
			double totalInsentifPemborong = 0.0;

			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(
					labor_ID);
			if(null == workerOfResource)
				continue;
			if (workerOfResource.isPrimePortion())
				continue;
			
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSSlotType slotType = rsc.getSlotType();
			boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));
			boolean isNonBusinesDay = MNonBusinessDay.isNonBusinessDay(
					getCtx(), getMovementDate(), getAD_Org_ID(), get_TrxName());
			
			MUNSProductionPayConfig productionPayConfig = MUNSProductionPayConfig.get(
					getCtx(), getAD_Org_ID(), getMovementDate(), get_TrxName());
			
			for (MUNSProductionDetail pd : getLines())
			{
				Double qtyProduction = mapOfQtyProduction.get(pd.getM_Product_ID());
				if (null == qtyProduction)
					qtyProduction = pd.getMovementQty().doubleValue();
				
				double pay = 0.0;
				
				MUNSPayRollLine payRollLine = productionPayConfig.getCriteriaLine(
						worker.getUNS_Job_Role_ID(), 
						pd.getM_Product_ID(), 
						0, 
						MUNSPayRollLine.CRITERIATYPE_Product
						, null);
				
				if (null == payRollLine)
					continue;
				
				double proportionOfWorker = workerOfResource.getResultProportion().doubleValue();
				double insentifPemborong = payRollLine.getInsentifPemborong().doubleValue();

				double qtyProductionOfWorker = qtyProduction * proportionOfWorker /100;
				double totalPay = 0.0;
				
				if(payRollLine.isTargetBased())
				{
					MUNSPayRollPremiTarget criteriaProductTarget =
							payRollLine.getCriteriaTarget(new BigDecimal(qtyProduction));
					if(null != criteriaProductTarget)
					{
						if(isOverTime())
							pay = payRollLine.getPayOverTime().doubleValue();
						else if (isNonBusinesDay)
							pay = payRollLine.getPayNationalHoliday().doubleValue();
						else if (!isWorkDay)
							pay = payRollLine.getPaySunday().doubleValue();
						else
							pay = criteriaProductTarget.getPay().doubleValue();
					}
				}
				else
				{
					if(isOverTime())
						pay = payRollLine.getPayOverTime().doubleValue();
					else if (isNonBusinesDay)
						pay = payRollLine.getPayNationalHoliday().doubleValue();
					else if (!isWorkDay)
						pay = payRollLine.getPaySunday().doubleValue();
					else
						pay = payRollLine.getPay().doubleValue();
				}
				
				totalPay = pay * qtyProductionOfWorker;
				totalPayForWorker += totalPay;
				totalInsentifPemborong += insentifPemborong;
				
				MUNSProductionWorkerResult workerResult = 
						new MUNSProductionWorkerResult(getCtx(), 0, get_TrxName());
				workerResult.setUNS_Production_Worker_ID(worker.get_ID());
				workerResult.setAD_Org_ID(worker.getAD_Org_ID());
				workerResult.setDescription("Auto Generate");
				workerResult.setInsentifPemborong(new BigDecimal(insentifPemborong));
				workerResult.setM_Product_ID(pd.getM_Product_ID());
				workerResult.setProductionQty(new BigDecimal(qtyProductionOfWorker));
				workerResult.setReceivableAmt(new BigDecimal(totalPay));
				workerResult.save();
			}

			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					getCtx(), labor_ID, getMovementDate(), 
					getAD_Org_ID(), getUNS_Resource().getResourceParent_ID(), get_TrxName());
			if ( null == halfPeriodPresence)
			{
				msg = "Please create Half Period First for this date " + getMovementDate();
				return msg;
			}
			
			MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
					getMovementDate(), rsc.getUNS_Resource_ID());
			if (null == workerPresence)
			{
				workerPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
				workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
				workerPresence.setWorkDate(getMovementDate());
				workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			}
			
			if (!isWorkDay || isNonBusinesDay)
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			}
			else
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			}
			workerPresence.setUNS_Resource_ID(getUNS_Resource_ID());
			workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			
			if(isOverTime())
			{
				workerPresence.setOT_ProductionWOrker_ID(worker.get_ID());
				workerPresence.setOvertime(getHoursOverTime());
			}
			else
			{
				workerPresence.setUNS_Production_Worker_ID(worker.get_ID());
			}
			
			workerPresence.setReceivableAmt(
					workerPresence.getReceivableAmt().add(new BigDecimal(totalPayForWorker)));
			workerPresence.save();
			
			worker.setInsentifPemborong(new BigDecimal(totalInsentifPemborong));
			worker.setReceivableAmt(new BigDecimal(totalPayForWorker));
			worker.save();
		}
		return msg;
	}
	
	/**
	 * Create Worker presence personal result
	 * @return
	 * @deprecated
	 */
	public String createPersonalResultReceivableAmtOfWorker()
	{
		String msg = "";
		
		Calendar calendarProduction = Calendar.getInstance();
		calendarProduction.setTimeInMillis(getMovementDate().getTime());
		
		for (MUNSProductionWorker worker : getWorkers())
		{

			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(
					labor_ID);
			
			if(null == workerOfResource)
				continue;
			
			MUNSProductionPayConfig productionPayConfig = MUNSProductionPayConfig.get(
					getCtx(), getAD_Org_ID(), getMovementDate(), get_TrxName());
			
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSSlotType slotType = rsc.getSlotType();
			boolean isWorkDay = slotType.IsWorkDay(calendarProduction.get(Calendar.DAY_OF_WEEK));
			boolean isNonBusinesDay = MNonBusinessDay.isNonBusinessDay(
					getCtx(), getMovementDate(), getAD_Org_ID(), get_TrxName());
			
			double totalPayForWorker = 0.0;
			double totalInsentifPemborong = 0.0;
			
			for (MUNSProductionWorkerResult workerResult : worker.getResults())
			{
				MUNSPayRollLine payRollLine = productionPayConfig.getCriteriaLine(
						worker.getUNS_Job_Role_ID(), 
						workerResult.getM_Product_ID(), 
						0, 
						MUNSPayRollLine.CRITERIATYPE_Product
						, null);
				
				if (null == payRollLine)
					continue;
				
				double pay = 0.0;
				double insentifPemborong = payRollLine.getInsentifPemborong().doubleValue();
				
				if(payRollLine.isTargetBased())
				{
					MUNSPayRollPremiTarget criteriaProductTarget =
							payRollLine.getCriteriaTarget(workerResult.getProductionQty());
					if(null != criteriaProductTarget)
					{
						if(isOverTime())
							pay = payRollLine.getPayOverTime().doubleValue();
						else if(isNonBusinesDay)
							pay = payRollLine.getPayNationalHoliday().doubleValue();
						else if (!isWorkDay)
							pay = payRollLine.getPaySunday().doubleValue();
						else
							pay = criteriaProductTarget.getPay().doubleValue();
					}
				}
				else
				{
					if(isOverTime())
						pay = payRollLine.getPayOverTime().doubleValue();
					else if(isNonBusinesDay)
						pay = payRollLine.getPayNationalHoliday().doubleValue();
					else if (!isWorkDay)
						pay = payRollLine.getPaySunday().doubleValue();
					else
						pay = payRollLine.getPay().doubleValue();
				}
				
				double payForWorker = pay * workerResult.getProductionQty().doubleValue();
				totalPayForWorker += payForWorker;
				totalInsentifPemborong += insentifPemborong;
				
				workerResult.setInsentifPemborong(new BigDecimal(insentifPemborong));
				workerResult.setReceivableAmt(new BigDecimal(payForWorker));
				workerResult.save();
			}
			
			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					getCtx(), labor_ID, getMovementDate(), 
					getAD_Org_ID(), getUNS_Resource().getResourceParent_ID(), get_TrxName());
			
			if ( null == halfPeriodPresence)
			{
				msg = "Please create Half Period First for this date " + getMovementDate();
				return msg;
			}
			
			MUNSWorkerPresence workerPresence = halfPeriodPresence.getWorkerPresence(
					getMovementDate(), rsc.getUNS_Resource_ID());
			if (null == workerPresence)
			{
				workerPresence = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
				workerPresence.setAD_Org_ID(halfPeriodPresence.getAD_Org_ID());
				workerPresence.setWorkDate(getMovementDate());
				workerPresence.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			}
			
			if (!isWorkDay || isNonBusinesDay)
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			}
			else
			{
				workerPresence.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			}
			workerPresence.setUNS_Resource_ID(getUNS_Resource_ID());
			workerPresence.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			
			if(isOverTime())
			{
				workerPresence.setOT_ProductionWOrker_ID(worker.get_ID());
				workerPresence.setOvertime(getHoursOverTime());
			}
			else
			{
				workerPresence.setUNS_Production_Worker_ID(worker.get_ID());
			}
			
			
			workerPresence.setReceivableAmt(
					workerPresence.getReceivableAmt().add(new BigDecimal(totalPayForWorker)));
			workerPresence.save();
			
			worker.setReceivableAmt(new BigDecimal(totalPayForWorker));
			worker.setInsentifPemborong(new BigDecimal(totalInsentifPemborong));
			worker.save();
		}
		return msg;
	}
	
	public boolean isProductionOnSunday()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getMovementDate().getTime());
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
			return true;
		return false;
	}
	
	public boolean isProductionOnNonBusinessDay()
	{
		return MNonBusinessDay.isNonBusinessDay(getCtx(), getMovementDate(), getAD_Org_ID(), get_TrxName());
	}
	
	/**
	 * Get production workers those are set as prime-portion in the worker configuration.
	 * @return
	 */
	public MUNSProductionWorker[] getPrimaryWorker()
	{
		List<MUNSProductionWorker> list = new ArrayList<MUNSProductionWorker>();
		for (MUNSProductionWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(
					labor_ID);
			if (null == workerOfResource)
				continue;
			if (!workerOfResource.isPrimePortion())
				continue;
			
			list.add(worker);
		}
		
		MUNSProductionWorker[] primaryWorker = new MUNSProductionWorker[list.size()];
		return list.toArray(primaryWorker);
	}
	
	
	/**
	 * Get production-workers those are set as non-prime-portion in the worker configuration and set the
	 * employment status as borongan.
	 * @return
	 */
	public MUNSProductionWorker[] getNonPrimaryWorkerBorongan()
	{
		List<MUNSProductionWorker> list = new ArrayList<MUNSProductionWorker>();
		for (MUNSProductionWorker worker : getWorkers())
		{
//			I_UNS_Contract_Recommendation contract = worker.getLabor().getUNS_Contract_Recommendation();
			
//			if (!contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan)
//					&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV)
//					&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian)
//					&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV))
//				continue;
			
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResourceWorkerLine workerOfResource = m_Resource.getWorker(labor_ID);
			if (workerOfResource != null && workerOfResource.isPrimePortion())
				continue;
			
			list.add(worker);
		}
		
		MUNSProductionWorker[] primaryWorker = new MUNSProductionWorker[list.size()];
		return list.toArray(primaryWorker);
	}
	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public MUNSProductionDetail getProductionDetailOf(int M_Product_ID)
	{
		return Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
				COLUMNNAME_UNS_Production_ID + "=? AND " + COLUMNNAME_M_Product_ID + "=?", get_TrxName())
				.setParameters(getUNS_Production_ID(), M_Product_ID)
				.setOrderBy(MUNSProductionDetail.COLUMNNAME_Line)
				.first();
				//.firstOnly();
	}
	
	/**
	 * @author YAKA
	 * @param ctx
	 * @param prod
	 * @return {@link MUNSResource}
	 */
	public static MUNSResource getResource(Properties ctx, MUNSProduction prod) {
		return new MUNSResource(ctx, prod.getUNS_Resource_ID(), prod.get_TrxName());
	}
	
	/**
	 * Get this production's resource detail.
	 * @return
	 */
	public MUNSResource getResource()
	{
		if (m_Resource != null)
			return m_Resource;
		
		m_Resource = getResource(getCtx(), this);
		return m_Resource;
	}
	
	/**
	 * @author YAKA
	 * @param ctx
	 * @param prod
	 * @return true if resource need QA Monitoring
	 */
	public static boolean getQAMonitoring(Properties ctx, MUNSProduction prod) 
	{
		MUNSResource resc = prod.getResource();//MUNSProduction.getResource(ctx, prod);
		for(MUNSProductionDetail pd : prod.getOutputLines())
		{
			MUNSResourceInOut rio = resc.getResourceOutput(pd.getM_Product_ID());
			if (rio != null && rio.isQAMonitoring())
				return true;
			continue;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSProductionDetail[] getLines() 
	{
		if (m_ProductionDetails != null)
			return m_ProductionDetails;
		
		List<MUNSProductionDetail> list = null;
		
		String orderBy = MUNSProductionDetail.COLUMNNAME_IsPrimary + " DESC, "
				+ MUNSProductionDetail.COLUMNNAME_IsEndProduct + " DESC, "
				+ MUNSProductionDetail.COLUMNNAME_Line + " ASC";
		
		list = Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
						 "UNS_Production_ID=" + get_ID(), get_TrxName())
					.setOrderBy(orderBy)
					.list();
		MUNSProductionDetail[] retValue = new MUNSProductionDetail[list.size()];
		list.toArray(retValue);
		m_ProductionDetails = retValue;
		
		return retValue;
	}
	

	/**
	 * 
	 * @param isOutput
	 * @return
	 */
	public MUNSProductionDetail[] getOutputLines() 
	{
		if (m_EndProducts != null)
			return m_EndProducts;
		
		List<MUNSProductionDetail> list = null;
		list = Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
						 "UNS_Production_ID=? AND isEndProduct=?", get_TrxName())
					.setParameters(get_ID(), true)
					.list();
		MUNSProductionDetail[] retValue = new MUNSProductionDetail[list.size()];
		list.toArray(retValue);
		m_EndProducts = retValue;
		
		return retValue;
	}
	
	/**
	 * 
	 */
	public boolean isPersonalResult()
	{
		return WORKERRESULTTYPE_PersonalResult.equals(getWorkerResultType());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isGroupResult()
	{
		return WORKERRESULTTYPE_GroupResult.equals(getWorkerResultType());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDailyResult()
	{
		return WORKERRESULTTYPE_Daily.equals(getWorkerResultType());
	}
	
	@SuppressWarnings("deprecation")
	public boolean createWorkerAbsence()
	{
		MUNSResource rsc = (MUNSResource)getUNS_Resource();
		for(MUNSResourceWorkerLine worker : rsc.getListOfWorker())
		{
			if(worker.isAdditional())
				continue;
			
			MUNSProductionWorker productionWorker = getProductionWorkerOf(worker.getLabor_ID());
			if(null != productionWorker)
				continue;
			MUNSHalfPeriodPresence halfPresence =
					MUNSHalfPeriodPresence.get(getCtx(), worker.getLabor_ID(), getMovementDate(), 
							worker.getAD_Org_ID(), getUNS_Resource().getResourceParent_ID(), get_TrxName());
			
			MUNSEmployee employee = new MUNSEmployee(getCtx(), worker.getLabor_ID(), get_TrxName());
			if(null == halfPresence)
			{
				MPeriod period = MPeriod.get(getCtx(), getMovementDate(), worker.getAD_Org_ID());
				throw new AdempiereUserError(
						"Cannot find worker presence for" + employee.getName() 
						+ " in period " + period.getName());
			}
			if(!halfPresence.createAbsence(getMovementDate(), getUNS_Resource_ID()))
				throw new AdempiereUserError("Cannot find Production Pay Config for : Dept.:" + 
						MOrg.get(getCtx(), getAD_Org_ID()).getName());
		}
		return true;
	}
	
	/**
	 * 
	 * @param laborID
	 * @return
	 */
	MUNSProductionWorker getProductionWorkerOf(int laborID)
	{
		for(MUNSProductionWorker pw : getWorkers())
		{
			int labor_ID = pw.getLabor_ID();
			if(pw.getReplacementLabor_ID() > 0)
				labor_ID = pw.getReplacementLabor_ID();
			
			if(labor_ID != laborID)
				continue;
			
			return pw;
		}
		return null;
	}

	/**
	 * Move this method from Process class of ProductionProcess.java into here as it does need workflow approval.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String processLines()
	{	
		int processed = 0;
		BigDecimal sumOutput = Env.ZERO;
		BigDecimal sumQtyUsed = Env.ZERO; 
		MUNSProductionDetail[] detailLines = getLines();

		//Checking for multi output configuration
		if(!isReversal() && 
				(getOutputType().equals(OUTPUTTYPE_Multi) || getOutputType().equals(OUTPUTTYPE_MultiOptional)))
		{
			int count = 0;
			for(MUNSProductionDetail pd : getLines())
			{
				for(MUNSProductionOutPlan op : getOutputs())
				{
					if (op.getM_Product_ID()==pd.getM_Product_ID())
						count++;
				}
			}
			if (count<getOutputs().length)
				throw new AdempiereException("Number of end product less then output plan.");
		}
		//if (true)
		//	throw new AdempiereException();
		//check quantity production detail
		MUNSProductionSchedule m_ps = getRelatedPPICOrder(getUNS_ProductionSchedule_ID(), 0, null);
		StringBuilder errors = new StringBuilder();

		for (MUNSProductionDetail pd : detailLines)
		{
			if (pd.isEndProduct())
			{
				sumOutput = sumOutput.add(pd.getMovementQty());
				MUNSProductionSchedule ps = null;
				
				if (getUNS_ProductionSchedule_ID() > 0 && m_ps.getM_Product_ID() == pd.getM_Product_ID())
					ps = m_ps;
				else 
					ps = getRelatedPPICOrder(0, pd.getM_Product_ID(), pd.getMovementQty());
				
				//if(null != m_ps && m_ps.getSERLineProduct().getM_Product_ID()==getM_Product_ID())
//				MUNSMPSProductRscWeekly mpsRscWeekly = null;
				if(null != ps) 
				{
					MUNSPSRealization.createRealization(ps, pd, getCtx(), get_TrxName());
					
//					mpsRscWeekly = (MUNSMPSProductRscWeekly) ps.getUNS_MPSProductRsc_Weekly();
				}
//				else {
//					mpsRscWeekly = MUNSMPSProductRscWeekly.get(getCtx(), getAD_Org_ID(), 
//															   line.getM_Product_ID(), getMovementDate(),
//															   get_TrxName());
//				}
//				if (null != mpsRscWeekly) 
//				{
//					boolean mpsUpdated = mpsRscWeekly.updateFromProduction(getCtx(), line, get_TrxName());	
//					if (!mpsUpdated)
//						errors.append("Failed when updating MPS of Product " + line.getM_Product().getValue());
//				}
			} 
			else 
			{
				if (pd.getMovementQty().compareTo(Env.ZERO) != 0)
					sumQtyUsed = sumQtyUsed.add(pd.getMovementQty().negate());
				else
					throw new AdempiereException("Please update movement Qty for product "
								+ pd.getM_Product().getName());
			}

			//if (sumOutput.signum() <= 0 && sumQtyUsed.signum() <= 0)
			//	throw new AdempiereException("Please update production detail before complete.");
		}
		
		for ( int i = 0; i < detailLines.length; i++) 
		{
			MUNSSrcProductDetail[] sourceList = null;
			
			if (!getPSType().equalsIgnoreCase(MUNSProduction.PSTYPE_MasterProductionSchedule)
				&& !getPSType().equalsIgnoreCase(MUNSProduction.PSTYPE_Reprocess)
				&& !detailLines[i].isEndProduct())
				sourceList = m_ps.getLinesSrcProductDetail(false);
			
			errors.append( detailLines[i].createTransactions(getMovementDate(), true, sourceList) );
			
			detailLines[i].setProcessed( true );
			detailLines[i].saveEx(get_TrxName());
			/*
			 * No need!!! (by Harry)
			if (getUNS_ProductionSchedule_ID()==0 && lines[i].isEndProduct())
				if(!MUNSProductionSchedule.createManufacturingOrder(getCtx(), get_TrxName(), lines[i]))
					throw new AdempiereException("Error when create Manufacturing Order");
			*/
			processed++;
		}
		
		if(isWorkerBase() && null != getWorkerResultType()) 
		{
			if (getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_Daily))
			{
				errors.append(MUNSProductionWorker.createDailyReceivables(getCtx(), this, get_TrxName()));
			}
			else if (getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_GroupResult))
			{	
				errors.append(MUNSProductionWorker
						.createGroupResultReceivable(getCtx(), this, get_TrxName()));
			}
			else if (getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_PersonalResult))
			{
				errors.append(MUNSProductionWorker
						.createPersonalResultReceivable(getCtx(), this, get_TrxName()));
			}
			/*
			if(!createWorkerAbsence())
				errors.append("Failed to create absence of worker");
			*/
			
		}
		
		//TODO QA STATUS not use yet
//		/**
//		 * @author YAKA
//		 * create Product Release Certificate
//		 */
//		if (getQAMonitoring(getCtx(), this))
//		{
//			MUNSQAStatusPRC prc = new MUNSQAStatusPRC(getCtx(), 0, get_TrxName());
//			errors.append(prc.createQAMonitoring(this));
//		} 
//		else 
//		{
//			for (MUNSProductionDetail pd : getOutputLines())
//			{
////				MProductCategory prodCat = (MProductCategory) pd.getM_Product().getM_Product_Category();
////				if(prodCat.getInitialQAStatus().equals(MStorageOnHand.QASTATUS_Incubation))
////					continue;
//				MStorageOnHand.setReleaseQty(pd.getM_Product_ID(),
//											 pd.getM_Locator_ID(),
//											 pd.getM_AttributeSetInstance_ID(), 
//											 pd.getMovementQty(), pd.get_TrxName());
//			}
//		}
	
		if ( errors.toString().compareTo("") != 0 ) 
		{
			log.log(Level.SEVERE, errors.toString() );
			throw new AdempiereException(errors.toString());
		}
		
		setIsComplete("Y");
		setProcessed(true);
		
		saveEx(get_TrxName());
		StringBuilder msgreturn = new StringBuilder().append(processed).append(" production lines were processed");
		//addLog(msgreturn.toString());
		return msgreturn.toString();
	}
	
	/**
	 * 
	 * @return
	 *
	private String setWorkerReceivables()
	{
		String errMsg = null;
		
		//BigDecimal totalProportion = Env.O
		
		return errMsg;
	}
	*/

	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSProduction[")
			.append(get_ID())
			.append(" - ").append(getDocumentNo())
			.append(", DocStatus:").append(getDocStatus())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param movementQyt
	 * @return
	 */
	private MUNSProductionSchedule getRelatedPPICOrder(int PSID, int M_Product_ID, BigDecimal movementQyt)
	{
		MUNSProductionSchedule ps = null;
		//if(null != m_ps && m_ps.getSERLineProduct().getM_Product_ID()==getM_Product_ID())
		if (PSID > 0) {
			ps = new MUNSProductionSchedule(getCtx(), PSID, get_TrxName());			
		}
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getMovementDate().getTime());
			int movementWeekNo = cal.get(Calendar.WEEK_OF_YEAR);
			int month = cal.get(Calendar.MONTH);
			if (month == 11 && movementWeekNo == 1)
				movementWeekNo = 53;
			
			String sql = "SELECT ps.UNS_ProductionSchedule_ID, mo.WeekNo, (ps.QtyOrdered-ps.QtyManufactured) FROM UNS_ProductionSchedule ps " +
					" INNER JOIN UNS_Manufacturing_Order mo ON ps.UNS_Manufacturing_Order_ID=mo.UNS_Manufacturing_Order_ID " +
					" INNER JOIN C_Year year ON mo.C_Year_ID=year.C_Year_ID " +
					" WHERE ps.M_Product_ID=? AND year.FiscalYear=? AND mo.ProductionDepartment_ID=? " +
					"	AND ps.QtyManufactured < ps.QtyOrdered " +
					" ORDER BY mo.Target_PD_Start";
			PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
			try {
				stmt.setInt(1, M_Product_ID);
				stmt.setString(2, String.valueOf(cal.get(Calendar.YEAR)));
				stmt.setInt(3, getAD_Org_ID());
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					int psIDTmp = rs.getInt(1);
					int psWeekNo = rs.getInt(2);
					BigDecimal unrealizedQty = rs.getBigDecimal(3);
					
					if (psWeekNo != movementWeekNo && unrealizedQty.compareTo(movementQyt) < 0)
						continue;
					else if (rs.isLast() || psWeekNo == movementWeekNo)
						PSID = psIDTmp;
				}
			}
			catch (SQLException ex){
				throw new AdempiereException(ex.getMessage());
			}
			if (PSID > 0)
				ps = new MUNSProductionSchedule(getCtx(), PSID, get_TrxName());			
		}
		return ps;
	}
	
	public MUNSProductionDetail getProductionDetail(int M_Product_ID)
	{
		MUNSProductionDetail retVal = null;
		for (MUNSProductionDetail lines : getLines()){
			if (lines.getM_Product_ID()==M_Product_ID)
				return lines;
		}
		return retVal;
	}	//	toString
}