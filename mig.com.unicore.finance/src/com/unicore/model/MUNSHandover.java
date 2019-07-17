/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;

/**
 * @author setyaka
 *
 */
public class MUNSHandover extends X_UNS_Handover implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700251288551891772L;

	/**
	 * @param ctx
	 * @param UNS_Handover_ID
	 * @param trxName
	 */
	public MUNSHandover(Properties ctx, int UNS_Handover_ID, String trxName) {
		super(ctx, UNS_Handover_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSHandover(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSHandover(MUNSBillingGroupResult result) {
		this(result.getCtx(), 0, result.get_TrxName());
		
		setClientOrg(result);
		
		setIsApproved(false);
		setUNS_BillingGroup_ID (result.getUNS_BillingGroup_ID());
		setUNS_BillingGroup_Result_ID(result.get_ID());
		setDateDoc(result.getDateDoc());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.compiere.process.DocOptions#customizeValidActions(java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.String, int,
	 * java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {
		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid))
		{
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Completed, add "Reactivte" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			options[index++] = DocumentEngine.ACTION_ReActivate;
			options[index++] = DocumentEngine.ACTION_Void;
		}

		return index;
	}

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction
	 *            document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	private MUNSHandoverInv[] m_lines;

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**************************************************************************
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt() {
		// Lines
		MUNSHandoverInv[] lines = getLines(true, null);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_InProgress;
		}

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg = ModelValidationEngine.get()
				.fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		setProcessed(true);
		saveEx();

		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO))
			log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO))
			log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	@Override
	public String completeIt() {
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		StringBuilder info = new StringBuilder();

		setProcessed(true);
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	@Override
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		addDescription(Msg.getMsg(getCtx(), "Voided"));

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description
	 *            text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Close

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	}

	/**
	 * Reverse Correction - same void
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt() {
		if (log.isLoggable(Level.INFO))
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
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	public boolean reverseAccrualIt() {
		if (log.isLoggable(Level.INFO))
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
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// TODO palce coding reActivateIt here

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		
		sb.append(": Status=").append(getDocStatus());

		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	} // getSummary

	/**************************************************************************
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getName()).append(get_ID()).append("-").append(
				getDocumentNo()).append(", Status=").append(getDocStatus());

		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo() {
		return "Packing List :" + getDocumentNo();
	} // getDocumentInfo

	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		} catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;

	}

	/**
	 * Create PDF file
	 * 
	 * @param file
	 *            output file
	 * @return file if success
	 */
	public File createPDF(File file) {
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, get_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if (format.getJasperProcess_ID() > 0)
		{
			ProcessInfo pi = new ProcessInfo("", format.getJasperProcess_ID());
			pi.setRecord_ID(get_ID());
			pi.setIsBatch(true);

			ServerProcessCtl.process(pi, null);

			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	} // createPDF

	@Override
	public String getProcessMsg() {

		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {

		return getUpdatedBy();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	/**************************************************************************
	 * Get Lines
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @return orders
//	 */
//	public MUNSHandoverInv[] getLines(String whereClause, String orderClause) {
//		// red1 - using new Query class from Teo / Victor's MDDOrder.java
//		// implementation
//		StringBuilder whereClauseFinal = new StringBuilder(
//				MUNSHandoverInv.COLUMNNAME_UNS_Handover_ID + "=? ");
//		if (!Util.isEmpty(whereClause, true))
//			whereClauseFinal.append(whereClause);
//		if (orderClause.length() == 0)
//			orderClause = MUNSHandoverInv.COLUMNNAME_UNS_Handover_Inv_ID;
//		//
//		List<MUNSHandoverInv> list = Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
//				MUNSPaymentReceiptBP.Table_Name, whereClauseFinal.toString(), get_TrxName()).setParameters(
//				get_ID()).setOrderBy(orderClause).list();
//
//		return list.toArray(new MUNSHandoverInv[list.size()]);
//	} // getLines

	/**
	 * Get Lines
	 * 
	 * @param requery
	 * @param orderBy
	 * @return lines
	 */
	public MUNSHandoverInv[] getLines(boolean requery, String orderBy) {
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
//		String orderClause = "";
//		if (orderBy != null && orderBy.length() > 0)
//			orderClause += orderBy;

//		m_lines = getLines(null, orderClause);
		return m_lines;
	} // getLines

	/**
	 * Get Lines
	 * 
	 * @return lines
	 */
	public MUNSHandoverInv[] getLines() {
		return getLines(false, null);
	} // getLines

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static MUNSHandover get(MUNSBillingGroupResult bgr) {
		MUNSHandover handover = Query.get(bgr.getCtx(), UNSFinanceModelFactory.EXTENSION_ID, Table_Name,
				"UNS_BillingGroup_Result_ID=?", bgr.get_TrxName()).setParameters(bgr.get_ID()).setOrderBy(
				COLUMNNAME_UNS_Handover_ID).first();

		return handover;
	}


}
