/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
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

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;
import com.uns.model.GeneralCustomization;
import com.uns.model.MUNSBilling;
import com.uns.model.MUNSBillingLine;

/**
 * @author setyaka
 * 
 */
public class MUNSPaymentReceipt extends X_UNS_PaymentReceipt implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3239973637766721605L;

	/**
	 * @param ctx
	 * @param UNS_PaymentReceipt_ID
	 * @param trxName
	 */
	public MUNSPaymentReceipt(Properties ctx, int UNS_PaymentReceipt_ID, String trxName) {
		super(ctx, UNS_PaymentReceipt_ID, trxName);
		if(UNS_PaymentReceipt_ID <= 0)
		{
			setToZero();
		}
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPaymentReceipt(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPaymentReceipt(MUNSBillingGroupResult BGResult) {
		this(BGResult.getCtx(), 0, BGResult.get_TrxName());

		setClientOrg(BGResult);
		setIsApproved(false);;
		setProcessed(false);

		setUNS_BillingGroup_Result_ID(BGResult.get_ID());
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

		// If status = Completed, add "Reactivate" in the list
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

	private MUNSPaymentReceiptBP[] m_lines;

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
		
		if(getC_BankAccount_ID() <= 0 && getReceiptAmt().signum() == 1)
		{
			throw new AdempiereUserError("Please define Bank Account before process this document.");
		}
		
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

		createPRAllocation();

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (log.isLoggable(Level.INFO))
			log.info(toString());

		// Implicit Approval
		if (!isApproved())
			approveIt();

		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt
	
	private void createPRAllocation() {
		MUNSBillingGroupResult result =
				new MUNSBillingGroupResult(getCtx(), getUNS_BillingGroup_Result_ID(), get_TrxName());

		int allocation_ID =
				GeneralCustomization.get_ID(get_TrxName(), MUNSPRAllocation.Table_Name,
						COLUMNNAME_UNS_BillingGroup_Result_ID, "=", (Integer) result.get_ID());
		
		MUNSPRAllocation allocation =
				new MUNSPRAllocation(getCtx(), allocation_ID == -1 ? 0 : allocation_ID, get_TrxName());
		
		allocation.addAmount(getPaidAmt(), getReceiptAmt(), Env.ZERO);
		allocation.setUNS_PaymentReceipt_ID(get_ID());
		allocation.setUNS_BillingGroup_Result_ID(getUNS_BillingGroup_Result_ID());
		allocation.setAD_Org_ID(result.getAD_Org_ID());

		if (!allocation.save())
			throw new AdempiereException("Error while tray create Billing Payment Allocation");

		for (MUNSBillingResult br : result.getLines())
		{
			MPayment payment = null;
			for (MUNSBillingLineResult blr : br.getLines(true))
			{
				if(blr.isHasReceipt() && blr.getC_PaymentAllocate_ID() > 0)
					continue;
				if(blr.getAD_Org_ID() != getAD_Org_ID())
					continue;
				
				if (blr.isPaid() && blr.getReceiptAmt().signum() != 0)
				{
					BigDecimal openInvoice = DB.getSQLValueBD(
							get_TrxName(), "SELECT InvoiceOpen(?, ?)", blr.getC_Invoice_ID(), 0);
					
					if (payment == null || payment.get_ID() == 0)
					{
						payment = new MPayment(getCtx(), 0, get_TrxName());
						payment.setAD_Org_ID(blr.getAD_Org_ID());
						payment.setUNS_PR_Allocation_ID(allocation.get_ID());
						payment.setAmount(getC_Currency_ID(), blr.getReceiptAmt());
						payment.setC_DocType_ID(true);
						payment.setC_BPartner_ID((Integer) GeneralCustomization.getColumn_ID(get_TrxName(),
								MUNSBilling.Table_Name, MUNSBilling.COLUMNNAME_C_BPartner_ID,
								MUNSBillingResult.COLUMNNAME_UNS_Billing_ID, "=", br.getUNS_Billing_ID()));
						payment.setBankCash(getC_BankAccount_ID(), true, MPayment.TENDERTYPE_Cash);
						
						int salesID = DB.getSQLValue(get_TrxName(), "SELECT bg.SalesRep_ID FROM UNS_BillingGroup_Result bgr"
								+ " INNER JOIN UNS_BillingGroup bg ON bgr.UNS_BillingGroup_ID = bg.UNS_BillingGroup_ID "
								+ " WHERE bgr.UNS_BillingGroup_Result_ID =  "+getUNS_BillingGroup_Result_ID());
						payment.setSalesRep_ID(salesID);
						
						Timestamp dateReceipt = DB.getSQLValueTS(get_TrxName(), 
								"SELECT DateReceived FROM UNS_PReceipt_Group "
								+ " WHERE UNS_PReceipt_Group_ID = ?", 
								getUNS_PReceipt_Group_ID());
						if (null != dateReceipt)
						{
							payment.setDateTrx(dateReceipt);
							payment.setDateAcct(dateReceipt);
						}
						
						if (!payment.save())
							throw new AdempiereException("Error while tray create Payment");					
					}
			
					BigDecimal receipt = blr.getReceiptAmt();
					MPaymentAllocate pa = new MPaymentAllocate(getCtx(), 0, get_TrxName());
					pa.setAD_Org_ID(blr.getAD_Org_ID());
					pa.setC_Payment_ID(payment.get_ID());
					pa.setC_Invoice_ID(blr.getC_Invoice_ID());
					pa.setAmount(receipt);
					pa.setPayToOverUnderAmount(receipt);
					pa.setWriteOffAmt(blr.getWriteOffAmt());
					pa.setOverUnderAmt(openInvoice.subtract(receipt.add(blr.getWriteOffAmt())));
					pa.setInvoiceAmt(openInvoice);

					if (!pa.save())
//						throw new AdempiereException("Error while tray create Payment Allocate");
						throw new AdempiereException("Error while tray create Payment Allocate (" 
									+ blr.getC_Invoice().getDocumentNo() + ")");
					
					//recreate model for get new update of pay amount.
					payment = new MPayment(getCtx(), payment.getC_Payment_ID(), get_TrxName());
					
					MUNSBillingLine bl = (MUNSBillingLine) blr.getUNS_BillingLine();
					bl.setPaidAmt(blr.getReceiptAmt());
					bl.saveEx();
					
					if(blr.getC_Invoice_ID() > 0)
					{
						blr.setNetAmtToInvoice(openInvoice);
						blr.setC_PaymentAllocate_ID(pa.get_ID());
						blr.setIsHasReceipt(true);
						blr.saveEx();
					}
				}
				
				MUNSHandoverInv inv = MUNSHandoverInv.getCreateByBillingGroup(
						blr, allocation.get_ID());
				inv.saveEx();
			}
		}
		
		String sql = "SELECT UNS_BillingGroup_ID FROM UNS_BillingGroup_Result WHERE UNS_BillingGroup_Result_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getUNS_BillingGroup_Result_ID());
		if(retVal <= 0)
			return;
		
		retVal = DB.executeUpdate(
				(new StringBuffer("UPDATE UNS_BillingGroup SET PaidAmt = (")
				.append(allocation.getReceiptAmt()).append(" + COALESCE(PaidAmt,0) )WHERE UNS_BillingGroup_ID = ")
				.append(retVal)).toString()
				, get_TrxName());
		if(retVal <= 0)
			log.info("Failed on update Grouping Billing");
	}

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
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
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
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
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

		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(pr.DocumentNo), ';') FROM UNS_PR_Allocation pr WHERE pr.UNS_PaymentReceipt_ID=?"
				+ " AND EXISTS (SELECT 1 FROM C_Payment cp WHERE cp.DocStatus IN ('CO', 'CL')"
				+ " AND cp.UNS_PR_Allocation_ID=pr.UNS_PR_Allocation_ID)";
		String list = DB.getSQLValueString(get_TrxName(), sql, get_ID());
		if(!Util.isEmpty(list, true))
		{
			m_processMsg = "Cannot reactivate payment receipt, document allocation has processed (" + list + ").";
			return false;
		}
		
		MUNSBillingGroupResult result =
				new MUNSBillingGroupResult(getCtx(), getUNS_BillingGroup_Result_ID(), get_TrxName());

		int allocation_ID =
				GeneralCustomization.get_ID(get_TrxName(), MUNSPRAllocation.Table_Name,
						COLUMNNAME_UNS_BillingGroup_Result_ID, "=", (Integer) result.get_ID());
		
		MUNSPRAllocation allocation =
				new MUNSPRAllocation(getCtx(), allocation_ID == -1 ? 0 : allocation_ID, get_TrxName());
		
		int retVal = DB.executeUpdate(
				(new StringBuffer("UPDATE UNS_BillingGroup SET PaidAmt = COALESCE(PaidAmt,0) - ")
				.append(allocation.getReceiptAmt()).append(" WHERE UNS_BillingGroup_ID = ")
				.append(getUNS_BillingGroup_ID()).toString())
				, get_TrxName());
		if(retVal <= 0)
			log.info("Failed on update Grouping Billing");

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
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "Grand Total")).append("=").append(getGrandTotal());

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
		StringBuffer sb =
				new StringBuffer(getClass().getName()).append(get_ID()).append("-").append(getDocumentNo())
						.append(", Grand Total=").append(getGrandTotal());

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
		return Env.ZERO;
	}

	/**************************************************************************
	 * Get Lines
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @return orders
	 */
	public MUNSPaymentReceiptBP[] getLines(String whereClause, String orderClause) {
		// red1 - using new Query class from Teo / Victor's MDDOrder.java
		// implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPaymentReceiptBP.COLUMNNAME_UNS_PaymentReceipt_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPaymentReceiptBP.COLUMNNAME_UNS_PaymentReceipt_BP_ID;
		//
		List<MUNSPaymentReceiptBP> list =
				Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSPaymentReceiptBP.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPaymentReceiptBP[list.size()]);
	} // getLines

	/**
	 * Get Lines
	 * 
	 * @param requery
	 * @param orderBy
	 * @return lines
	 */
	public MUNSPaymentReceiptBP[] getLines(boolean requery, String orderBy) {
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
	 * Get Lines
	 * 
	 * @return lines
	 */
	public MUNSPaymentReceiptBP[] getLines() {
		return getLines(false, null);
	} // getLines

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return MCurrency.get(getCtx(), "IDR").get_ID();
	}
	
	/**
	 * 
	 * @param trxName
	 * @param UNS_BillingGroup_Result_ID
	 * @return
	 */
	public static MUNSPaymentReceipt[] gets(String trxName, int UNS_BillingGroup_Result_ID) {
		List<MUNSPaymentReceipt> list =
				Query.get(Env.getCtx(), UNSFinanceModelFactory.EXTENSION_ID, Table_Name,
						"UNS_BillingGroup_Result_ID=?", trxName)
						.setParameters(UNS_BillingGroup_Result_ID)
						.setOrderBy(COLUMNNAME_DocumentNo).list();
		MUNSPaymentReceipt[] records = new MUNSPaymentReceipt[list.size()];
		list.toArray(records);
		return records;
	}

	/**
	 * 
	 * @param trxName
	 * @param UNS_BillingGroup_Result_ID
	 * @param AD_Org_ID
	 * @return
	 */
	public static MUNSPaymentReceipt get(String trxName, int UNS_BillingGroup_Result_ID, int AD_Org_ID) {
		MUNSPaymentReceipt pr =
				Query.get(Env.getCtx(), UNSFinanceModelFactory.EXTENSION_ID, Table_Name,
						"UNS_BillingGroup_Result_ID=? AND AD_Org_ID = ?", trxName)
						.setParameters(UNS_BillingGroup_Result_ID, AD_Org_ID)
						.setOrderBy(COLUMNNAME_DocumentNo).first();

		return pr;
	}

	public void setAmount() {
		setGrandTotal(getReceiptAmt().add(getReceiptAmtGiro()));
		setDifferenceAmt(getGrandTotal().subtract(getPaidAmt().add(getPaidAmtGiro())));
	}
	
	public void upHeader()
	{
		String sql = "SELECT COALESCE(SUM(PaidAmt),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal paidAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "SELECT COALESCE(SUM(paidAmtGiro),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal paidAmtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "SELECT COALESCE(SUM(ReceiptAmt),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal receiptAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "SELECT COALESCE(SUM(ReceiptAmtGiro),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal receiptAmtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "SELECT COALESCE(SUM(GrandTotal),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal grandTotal = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "SELECT COALESCE(SUM(DifferenceAmt),0) FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?";
		BigDecimal diffAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_PReceipt_Group_ID());
		
		sql = "UPDATE UNS_PReceipt_Group SET PaidAmt = " + paidAmt
				+ ", PaidAmtGiro = " + paidAmtGiro + ", ReceiptAmt = " + receiptAmt
				+ ", ReceiptAmtGiro = " + receiptAmtGiro + ", GrandTotal = " + grandTotal
				+ ", DifferenceAmt = " + diffAmt + " WHERE UNS_PReceipt_Group_ID = ?";
		if(DB.executeUpdate(sql, getUNS_PReceipt_Group_ID(), get_TrxName()) < 0)
			throw new AdempiereException("Failed when traying to update Receipt Group");
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		setAmount();
		return super.beforeSave(newRecord);
	}
	
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		upHeader();
		
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		MUNSBillingGroupResult result =
				new MUNSBillingGroupResult(getCtx(), getUNS_BillingGroup_Result_ID(), get_TrxName());
		for (MUNSBillingResult br : result.getLines())
		{
			br.setReceiptAmt(Env.ZERO);

			if (br.save())
			{
				for (MUNSBillingLineResult line : br.getLines(false))
				{
					line.setReceiptAmt(Env.ZERO);

					line.saveEx();
				}
			}
		}
		
		upHeader();
		return super.beforeDelete();
	}
	
	public void setToZero()
	{
		setPaidAmt(Env.ZERO);
		setReceiptAmt(Env.ZERO);
		setPaidAmtGiro(Env.ZERO);
		setReceiptAmtGiro(Env.ZERO);
		setGrandTotal(Env.ZERO);
		setDifferenceAmt(Env.ZERO);
	}
	
	public BigDecimal getAmtTrf()
	{
		BigDecimal AmtTrf = Env.ZERO; 
		
		String sql = "SELECT COALESCE(SUM(AmtTrf),0) FROM UNS_BillingTrf_Detail WHERE UNS_PaymentReceipt_ID=?"
				+ " AND UNS_BillingTrf_Validation_ID IN (SELECT UNS_BillingTrf_Validation_ID FROM UNS_BillingTrf_Validation"
				+ " WHERE DocStatus <> 'VO')";
		AmtTrf = DB.getSQLValueBD(get_TrxName(), sql, get_ID());
		
		return AmtTrf;
	}
}
