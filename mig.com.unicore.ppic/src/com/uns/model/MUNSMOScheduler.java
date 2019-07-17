/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMOScheduler extends X_UNS_MO_Scheduler implements DocAction, DocOptions 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5174051566790415025L;
	private MUNSMOSchedulerLines[] m_Lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	
	/**
	 * @param ctx
	 * @param UNS_MO_Scheduler_ID
	 * @param trxName
	 */
	public MUNSMOScheduler(Properties ctx, int UNS_MO_Scheduler_ID,
			String trxName) {
		super(ctx, UNS_MO_Scheduler_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMOScheduler(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isScheduledToProduction(
			Properties ctx, int UNS_ProductionSchedule_ID, String trxName)
	{
		return Query.get(
				ctx, UNSPPICModelFactory.getExtensionID(), MUNSMOSchedulerLines.Table_Name
				, MUNSMOSchedulerLines.COLUMNNAME_UNS_ProductionSchedule_ID 
				+ " = " + UNS_ProductionSchedule_ID 
				, trxName).firstOnly() != null;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMOSchedulerLines[] getLines(boolean requery)
	{
		if(null != m_Lines && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String whereClause = COLUMNNAME_UNS_MO_Scheduler_ID + " =?";
		List<MUNSMOScheduler> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSMOSchedulerLines.Table_Name
				, whereClause
				, get_TrxName())
				.setParameters(getUNS_MO_Scheduler_ID())
				.list();
		m_Lines = new MUNSMOSchedulerLines[list.size()];
		return list.toArray(m_Lines);
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
		
		MUNSMOSchedulerLines[] lines = getLines(false);
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
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
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}
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
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getLines(false).length).append(")");
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
}
