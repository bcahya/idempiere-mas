/**
 * 
 */
package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.GeneralCustomization;
import com.uns.model.I_C_DocType;
import com.uns.model.MUNSContainerArrival;
import com.uns.model.MUNSContainerDeparture;
import com.uns.model.MUNSPackingSlip;
import com.uns.model.MUNSShipmentSchedule;


/**
 * @author YAKA
 * 
 */
public class MUNSQAContainerInspection extends X_UNS_QAContainerInspection
		implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6730624356362105886L;
	public static final String EXTENSION_ID = "com.uns.model.factory.UNSPPICModelFactory";
	private String m_processMsg;
	private boolean m_justPrepared;
	private boolean m_reActive = false;
	private MUNSQAStatusContainer[] m_lines;

	/**
	 * @param ctx
	 * @param UNS_QAContainerInspection_ID
	 * @param trxName
	 */
	public MUNSQAContainerInspection(Properties ctx,
			int UNS_QAContainerInspection_ID, String trxName) {
		super(ctx, UNS_QAContainerInspection_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAContainerInspection(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {

		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}
		
		// If status = Completed, add "Reactive" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Close;
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

	@Override
	public boolean processIt(String processAction) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
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
		
		if (getLines().length<=0){
			m_processMsg  = "@NoLines@"; 
			return DocAction.STATUS_Drafted;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!MPeriod.isOpen(getCtx(), getDateDoc(),
				com.uns.model.I_C_DocType.DOCBASETYPE_QAD,
				getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(checkQAStatus(X_UNS_QAStatusContainer.QASTATUS_Release, true)){
			setDocAction(DOCACTION_Prepare);
			return DocAction.STATUS_InProgress;
		}

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public String completeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		//Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		if (!checkStatusContainer() && (m_reActive 
				|| checkQAStatus(X_UNS_QAStatusContainer.QASTATUS_OnHold, false))){
			m_reActive = false;
			return DocAction.STATUS_InProgress;
		}
		m_processMsg = null;
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		for(MUNSQAStatusContainer sc : getLines()){
			sc.updateContainerStatus();
			sc.updateContainerManagement();
			sc.setProcessed(true);
			sc.saveEx();
		}
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {

		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
				
		//Input code to void here
		
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
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//input code to close here
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;		
		
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		//not implement yet
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	@Override
	public boolean reActivateIt() {
		
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		if (checkQAStatus(X_UNS_QAStatusContainer.QASTATUS_Reject, false)){
			for(MUNSQAStatusContainer sc : getLines()){
				if(sc.isNC()){
					if (sc.getQAStatus().equals(X_UNS_QAStatusContainer.QASTATUS_Reject)){
						MUNSQAStatusNC snc = new MUNSQAStatusNC(getCtx(), 0, get_TrxName());
						if (!snc.createStatusNC(sc))
							throw new AdempiereException("Error when create Non Conformance");
					}
				}
			}
		};
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;			
		
		m_reActive=true;
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(STATUS_InProgress);
		return true;
	}

	
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// not used yet
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public int getC_Currency_ID() {
		// not use currency
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// don't have approval amount
		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		if (isProcessed())
			return false;
		
		for (MUNSQAStatusContainer sc : getLines()){
			sc.deleteEx(false, get_TrxName());
		}
		
		return super.beforeDelete();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return super.afterDelete(success);
	}

	/**
	 * 
	 * @param requery
	 * @return MUNSQAStatusContainer[]
	 */
	protected MUNSQAStatusContainer[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusContainer> mList =
				new Query(getCtx(), MUNSQAStatusContainer.Table_Name
						, MUNSQAStatusContainer.COLUMNNAME_UNS_QAContainerInspection_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAContainerInspection_ID())
		.setOrderBy(MUNSQAStatusContainer.COLUMNNAME_UNS_QAStatusContainer_ID).list();
		
		m_lines = new MUNSQAStatusContainer[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusContainer[]
	 */
	public MUNSQAStatusContainer[] getLines()
	{
		return getLines(false);
	}
	
	public String createContainerInspection(PO po){
		setAD_Org_ID(GeneralCustomization.getDept_ID(get_TrxName(), "QAD"));
		setDocAction(ACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
		if (po.get_TableName().equals(MUNSShipmentSchedule.Table_Name)){
			MUNSShipmentSchedule sc = (MUNSShipmentSchedule) po;
			setDateDoc(sc.getETD());
			setInspectionDate(sc.getETD());
			setInspectionType(INSPECTIONTYPE_DepartureLoading);			
			if(!save(get_TrxName()))
				throw new AdempiereException("Can't create Container Inspection");
			for(MUNSContainerDeparture cd : sc.getContainerDeparture()){
				MUNSQAStatusContainer statusC = new MUNSQAStatusContainer(getCtx(), 0, get_TrxName());
				statusC.createContainerStatus(this, cd, false);
				if (!statusC.save(get_TrxName()))
					throw new AdempiereException("Can't create status Container Inspection");
			}
		} else {
			MUNSPackingSlip ps = (MUNSPackingSlip) po;
			setDateDoc(ps.getArriveDate());
			setInspectionDate(ps.getArriveDate());
			setInspectionType(INSPECTIONTYPE_ArrivalUnloading);			
			if(!save(get_TrxName()))
				throw new AdempiereException("Can't create Container Inspection");
			for(MUNSContainerArrival ca : ps.getArrivedContainer()){
				MUNSQAStatusContainer statusC = new MUNSQAStatusContainer(getCtx(), 0, get_TrxName());
				statusC.createContainerStatus(this, ca, true);
				if (!statusC.save(get_TrxName()))
					throw new AdempiereException("Can't create status Container Inspection");
			}
		}
		return getDocumentInfo();
	}
	
	public boolean checkQAStatus(String QAStatus, boolean negasi){
		for(MUNSQAStatusContainer sc : getLines()){
			if (negasi){
				if (!sc.getQAStatus().equals(QAStatus))
					return true;
			} else {
				if (sc.getQAStatus().equals(QAStatus))
					return true;
			}
		}
		return false;
	}
	
	public boolean checkStatusContainer(){
		m_processMsg = "Please conform non conformance container: ";
		if (checkQAStatus(X_UNS_QAStatusContainer.QASTATUS_Release, true)){
			for(MUNSQAStatusContainer sc : getLines()){
				if(sc.isNC()){
					if (sc.getQAStatus().equals(X_UNS_QAStatusContainer.QASTATUS_OnHold))
						if (sc.getLines().length == 0){
							MUNSQAStatusNC snc = new MUNSQAStatusNC(getCtx(), 0, get_TrxName());
							if (!snc.createStatusNC(sc))
								throw new AdempiereException("Error when create Non Conformance");
							m_processMsg = m_processMsg + snc.get_ID() + " ";
						} else
							m_processMsg = m_processMsg + sc.getLastNC().get_ID() + " ";
				} else {
					sc.setQAStatus(X_UNS_QAStatusContainer.QASTATUS_Release);
				}
				sc.saveEx(get_TrxName());
			}

			for(MUNSQAStatusContainer sc : getLines()){
				if (!sc.getQAStatus().equals(X_UNS_QAStatusContainer.QASTATUS_Release)){
					return false;
				}
			}
		}
		return true;
	}
	
}
