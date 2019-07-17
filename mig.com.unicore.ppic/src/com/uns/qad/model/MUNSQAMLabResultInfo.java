package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.GeneralCustomization;

public class MUNSQAMLabResultInfo extends X_UNS_QAMLab_ResultInfo implements DocOptions, DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 121521650580694502L;
	private MUNSQAMLabResult m_header;
	private MUNSQAMLabResultLine[] m_lines;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	public MUNSQAMLabResultInfo(Properties ctx, int UNS_QAMLab_ResultInfo_ID,
			String trxName) {
		super(ctx, UNS_QAMLab_ResultInfo_ID, trxName);
		
	}

	public MUNSQAMLabResultInfo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

	/**
	* @param requery
	* @return MUNSQAMLabResultLine[]
	*/
	protected MUNSQAMLabResultLine[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAMLabResultLine> mList = new Query(getCtx(), MUNSQAMLabResultLine.Table_Name
			, MUNSQAMLabResultLine.COLUMNNAME_UNS_QAMLab_ResultInfo_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQAMLabResultLine.COLUMNNAME_UNS_QAMLab_ResultLine_ID).list();
			
		m_lines = new MUNSQAMLabResultLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
		
	/**
	 * 
	 * @return MUNSQAMLabResultLine[]
	 */
	public MUNSQAMLabResultLine[] getLines(){
		return getLines(false);
	}
	
	/**
	 * 
	 * @return MUNSQAMLabResult
	 */
	public MUNSQAMLabResult getHeader(){
		if (m_header !=null)
			return m_header;
		
		m_header = new MUNSQAMLabResult(getCtx(), getUNS_QAMLab_Result_ID(), get_TrxName());
		
		return m_header;
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
		
		if (getHeader().getInspectionDate()==null)
			throw new AdempiereException("Fill Mandantory \"Inspection Date\" at Lab Result.");
		
		if(getLines().length<=0)
			throw new AdempiereException("No Lines. Please create lines before complete.");
			
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
				
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
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		getHeader().setDocStatus(DOCSTATUS_InProgress);
		getHeader().saveEx();
		
		setProcessed(true);
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		setProcessed(true);
		setDocAction(DOCACTION_None);
		setDocStatus(DOCSTATUS_Drafted);
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		return "Laboratorium Result : " +getDocumentNo();
	}

	@Override
	public File createPDF() {
		// not used yet
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg ;
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
	public String getDocumentNo() {

		return "QA Result Info ["+ getLineMachine() + "]";
	}

	public static MUNSQAMLabResult getHeader(Properties ctx, int info_ID, String trxName) {
		int result_ID = (Integer) GeneralCustomization.getColumn_ID(trxName, 
				MUNSQAMLabResultInfo.Table_Name, 
				MUNSQAMLabResultInfo.COLUMNNAME_UNS_QAMLab_Result_ID, 
				MUNSQAMLabResultInfo.COLUMNNAME_UNS_QAMLab_ResultInfo_ID, 
				"=", info_ID);
		
		MUNSQAMLabResult retval = new MUNSQAMLabResult(ctx, result_ID, trxName);
		
		return retval;
	}
}
