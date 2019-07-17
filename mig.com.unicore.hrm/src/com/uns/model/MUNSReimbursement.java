/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
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
import org.compiere.util.Env;

/**
 * @author Haryadi
 *
 */
public class MUNSReimbursement extends X_UNS_Reimbursement implements
		DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1483443327140305426L;

	private String m_processMsg = null;
	
	private boolean m_justPrepared = false;
	
	private MUNSEmployee m_employee = null;
	
	private boolean m_isProcessing = false;
	
	/**
	 * @param ctx
	 * @param UNS_Reimbursement_ID
	 * @param trxName
	 */
	public MUNSReimbursement(Properties ctx, int UNS_Reimbursement_ID,
			String trxName) {
		super(ctx, UNS_Reimbursement_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSReimbursement(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Get instance of MUNSEmployee of this reimburse document.
	 * @return
	 */
	public MUNSEmployee getEmployee()
	{
		return this.getUNS_Employee();
	}

	/**
	 * Get instance of MUNSEmployee of this reimburse document.
	 * @return
	 */
	public MUNSEmployee getUNS_Employee()
	{
		if (m_employee == null)
			m_employee = (MUNSEmployee) super.getUNS_Employee();
		return m_employee;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(java.lang.String, java.lang.Object, java.lang.String, java.lang.String, int, java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Completed, add "Reverse Correct" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			options[index++] = DocumentEngine.ACTION_Void;
		}

		return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.PO#beforeSave(java.lang.Boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) throws AdempiereUserError 
	{
		if (m_isProcessing)
			return true;
		
		if (getSubjectCosts().compareTo(getTotalPaid()) < 0)
			throw new AdempiereException("Total to reimburse amount must be less or equals to medical cost.");
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = 
				MUNSEmployeeAllowanceRecord.getCreateMedical(getCtx(), getUNS_Employee(), getRequestDate(), get_TrxName());
		
		if (medAllowanceRec == null)
			m_processMsg = "Employee doesn't have medical allowance.";
		else if (medAllowanceRec.getRemainingAmt().compareTo(getTotalPaid()) < 0)
			m_processMsg = "Employee's remaining medical allowance amount less than total reimbursement amount.";
		
		if (m_processMsg != null)
			throw new AdempiereException (m_processMsg);
		
		setMedicalAllowance(medAllowanceRec.getMedicalAllowance());
		setRemainingAllowance(medAllowanceRec.getRemainingAmt());
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception 
	{
		m_isProcessing = true;
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		boolean retProcess = engine.processIt(action, getDocAction());
		
		m_isProcessing = false;
		
		return retProcess;
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
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (getTotalPaid() == null || getTotalPaid().signum() <= 0)
		{
			m_processMsg = "Cannot process zero reimbursement amount.";
			setProcessed(false);
			return DocAction.STATUS_Invalid;
		}
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = 
				MUNSEmployeeAllowanceRecord.getCreateMedical(getCtx(), getUNS_Employee(), getRequestDate(), get_TrxName());
		
		if (medAllowanceRec == null)
			m_processMsg = "Employee doesn't have medical allowance.";
		else if (medAllowanceRec.getRemainingAmt().compareTo(getTotalPaid()) < 0)
			m_processMsg = "Employee's remaining medical allowance amount less than total reimbursement amount.";
		
		if (m_processMsg != null)
		{
			setProcessed(false);
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
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

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = 
				MUNSEmployeeAllowanceRecord.get(getCtx(), getUNS_Employee_ID(), getRequestDate(), get_TrxName());
		
		medAllowanceRec.setMedicalAllowanceUsed(
				medAllowanceRec.getMedicalAllowanceUsed().add(getTotalPaid()));
		
		if (!medAllowanceRec.save()) {
			m_processMsg = "Failed when updating medical allowance ";
			return DocAction.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
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

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

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
		sb.append(":")
			.append("_").append(getUNS_Employee().getName()).append("_")
			.append(getDateAcct()).append(getTotalPaid());
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
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.MANUFACTURING_ORDER, get_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if(format.getJasperProcess_ID() > 0)	
		{
			ProcessInfo pi = new ProcessInfo ("", format.getJasperProcess_ID());
			pi.setRecord_ID ( get_ID() );
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
		return getTotalPaid();
	}
}
