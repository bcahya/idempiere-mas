/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MPayment;
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
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.util.MessageBox;

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;

/**
 * @author UNTA_YAKA
 * 
 */
public class MUNSPRAllocation extends X_UNS_PR_Allocation implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4420947862332860761L;
	public boolean m_force = false;

	/**
	 * @param ctx
	 * @param UNS_PR_Allocation_ID
	 * @param trxName
	 */
	public MUNSPRAllocation(Properties ctx, int UNS_PR_Allocation_ID, String trxName) {
		super(ctx, UNS_PR_Allocation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPRAllocation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPRAllocation(MUNSBillingGroupResult result) {
		this(result.getCtx(), 0, result.get_TrxName());
		
		setPaidAmt (Env.ZERO);
		setReceiptAmt (Env.ZERO);
		setDifferenceAmt (Env.ZERO);
		setGrandTotal (Env.ZERO);
		
		setIsApproved (false);
		setProcessed (false);

		setDocAction (ACTION_Prepare);
		setDocStatus (STATUS_Drafted);
		
		setUNS_BillingGroup_Result_ID(result.get_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocOptions#customizeValidActions(java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.String, int, java.lang.String[],
	 * java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {
		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted) || docStatus.equals(DocumentEngine.STATUS_Invalid))
		{
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Completed, add "ReActivate" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction document action
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

	private MUNSBillingResult[] m_billLines;
	private MUNSHandoverInv[] m_Invlines;
	private MPayment[] m_lines;

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
		
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
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

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSHandoverInv[] hois = getInvLines();
		for (int i=0; i<hois.length; i++)
		{
			if (!hois[i].getInvoiceCollectionType().equals(
					MUNSHandoverInv.INVOICECOLLECTIONTYPE_Lost)
					&& hois[i].getAcceptedBy() == null)
			{
				throw new AdempiereUserError("Please define Accepted By on Handover Invoice.");
			}
			else if (!hois[i].isReceipt() 
					&& !hois[i].getInvoiceCollectionType().equals(
							MUNSHandoverInv.INVOICECOLLECTIONTYPE_Lost)
							&& !hois[i].getInvoiceCollectionType().equals(
									MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverCustomer)
									&& !hois[i].getInvoiceCollectionType().equals(
											MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverFinancePaid))
			{
				throw new AdempiereUserError("Some Handover Invoice record is invalid");
			}
			else if (hois[i].isReceipt()
					&& (hois[i].getInvoiceCollectionType().equals(
							MUNSHandoverInv.INVOICECOLLECTIONTYPE_Lost)
							|| hois[i].getInvoiceCollectionType().equals(
									MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverCustomer)))
			{
				throw new AdempiereUserError("Some Handover Invoice record is invalid");
			}
			
			hois[i].setProcessed(true);
			hois[i].saveEx();
		}

		//complete lines (Payment)
		try
		{
			if(!m_force)
			{
				MPayment[] myLines = getLines(false, "");
				for (MPayment line : myLines)
				{
					if(line.isComplete())
					{
						continue;
					}
					
//					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(line, DocAction.ACTION_Complete);
//					if(pi.isError())
//					{
//						throw new AdempiereUserError("Can't complete payment.." + line.getDocumentNo() + " ." + pi.getSummary());
//					}
					
					if(!line.processIt(DocAction.ACTION_Complete) || !line.save())
					{
						m_processMsg = line.getProcessMsg() + "-" + line.getC_Invoice().getDocumentNo();
						setProcessed(false);
						return DocAction.STATUS_Invalid;
					}
				}
			}
		}
		
		catch (Exception e)
		{
			setProcessed(false);
			throw new AdempiereException("Failed on complete lines.. " + e.getMessage());
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
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
		
		m_processMsg = "Disallowed action void";
		return false;
	}

	/**
	 * Add to Description
	 * 
	 * @param description text
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		String msg = Msg.getMsg(getCtx(), "Do you want to reverse payment also ?");
		String title = Msg.getMsg(getCtx(), "Confirm action");
		int retVal = MessageBox.showMsg(this,
				getCtx()
				, msg
				, title
				, MessageBox.YESNO
				, MessageBox.ICONQUESTION);
		if(retVal == MessageBox.RETURN_NO || retVal == MessageBox.RETURN_NONE)
		{
			String sql = "UPDATE UNS_HandOver_Inv SET Processed = 'N' "
					+ " WHERE UNS_PR_Allocation_ID = ?";
			int updated = DB.executeUpdate(sql, get_ID(), get_TrxName());
			if(log.isLoggable(Level.INFO))
				log.info(updated + " record updated");
		}
		
		if(retVal == MessageBox.RETURN_OK || retVal == MessageBox.RETURN_YES)
		{			
			String m_User = Env.getContext(getCtx(), "#AD_User_Name");
			String m_addDescription = "Reversed By " + m_User + " from Billing Payment Allocation";
			for(MPayment pay : getLines())
			{
				if(pay.isComplete() && !pay.getDocStatus().equals(DOCSTATUS_Reversed))
				{
					pay.addDescription(m_addDescription);
					pay.processIt(DOCACTION_Reverse_Correct);
					if(!pay.save())
						m_processMsg = pay.getProcessMsg();
				}
			}
		}
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(STATUS_Drafted);
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

		if (m_billLines != null)
			sb.append(" (#").append(m_billLines.length).append(")");
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
		StringBuffer sb =
				new StringBuffer(getClass().getName()).append(" [ID:").append(get_ID()).append(" - DocNo:").append(getDocumentNo())
						.append(", Status=").append(getDocStatus()).append("]");

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
	 * @param file output file
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

		return getReceiptAmt();
	}

	@Override
	public int getC_Currency_ID() {

		return MCurrency.get(getCtx(), "IDR").get_ID();
	}
	
	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MPayment[] getLines(String whereClause, String orderClause) {
		// red1 - using new Query class from Teo / Victor's MDDOrder.java
		// implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MPayment.COLUMNNAME_UNS_PR_Allocation_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MPayment.COLUMNNAME_C_Payment_ID;
		//
		List<MPayment> list =
				new Query(getCtx(), MPayment.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID()).setOrderBy(orderClause)
						.list();

		return list.toArray(new MPayment[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MPayment[] getLines(boolean requery, String orderBy) {
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_lines = getLines(null, orderClause);
		return m_lines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MPayment[] getLines() {
		return getLines(false, null);
	} // getLines
	
	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSBillingResult[] getBLines(String whereClause, String orderClause) {
		// red1 - using new Query class from Teo / Victor's MDDOrder.java
		// implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSBillingResult.COLUMNNAME_UNS_BillingGroup_Result_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSBillingResult.COLUMNNAME_UNS_Billing_Result_ID;
		//
		List<MUNSBillingResult> list =
				Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSBillingResult.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID()).setOrderBy(orderClause)
						.list();

		return list.toArray(new MUNSBillingResult[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSBillingResult[] getBLines(boolean requery, String orderBy) {
		if (m_billLines != null && !requery)
		{
			set_TrxName(m_billLines, get_TrxName());
			return m_billLines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_billLines = getBLines(null, orderClause);
		return m_billLines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSBillingResult[] getBLines() {
		return getBLines(false, null);
	} // getLines

	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSHandoverInv[] getInvLines(String whereClause, String orderClause) {
		// red1 - using new Query class from Teo / Victor's MDDOrder.java
		// implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(COLUMNNAME_UNS_PR_Allocation_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSHandoverInv.COLUMNNAME_UNS_PR_Allocation_ID;
		//
		List<MUNSHandoverInv> list =
				Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSHandoverInv.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID()).setOrderBy(orderClause)
						.list();

		return list.toArray(new MUNSHandoverInv[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSHandoverInv[] getInvLines(boolean requery, String orderBy) {
		if (m_Invlines != null && !requery)
		{
			set_TrxName(m_billLines, get_TrxName());
			return m_Invlines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_Invlines = getInvLines(null, orderClause);
		return m_Invlines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSHandoverInv[] getInvLines() {
		return getInvLines(false, null);
	} // getLines
	

	@Override
	protected boolean beforeDelete() {		
		for (MUNSHandoverInv hinv : getInvLines()){
			hinv.deleteEx(true);
		}
		
		for (MPayment payment : getLines()){
			payment.deleteEx(true);
		}
		
		return super.beforeDelete();
	}
	
	public void addAmount(BigDecimal paidAmt, BigDecimal receiptAmt, BigDecimal amountTrf)
	{
		paidAmt = paidAmt.add(getPaidAmt());
		receiptAmt = receiptAmt.add(getReceiptAmt());
		amountTrf = amountTrf.add(getAmountTrf());
		setAmount(paidAmt, receiptAmt, amountTrf);
	}

	public void setAmount(BigDecimal paidAmt, BigDecimal receiptAmt, BigDecimal amountTrf) {
		setPaidAmt(paidAmt);
		setReceiptAmt(receiptAmt);
		setGrandTotal(receiptAmt.add(amountTrf));
		setAmountTrf(amountTrf);
		setDifferenceAmt(receiptAmt.subtract(paidAmt).subtract(amountTrf));
	}	
}
