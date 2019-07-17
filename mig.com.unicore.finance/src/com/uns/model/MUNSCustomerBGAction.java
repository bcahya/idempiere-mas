/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
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
import org.compiere.wf.MWorkflow;

import com.unicore.model.MUNSBillingLineGiro;
import com.unicore.model.MUNSBillingLineResult;
import com.unicore.model.MUNSHandoverInv;
import com.unicore.model.MUNSPaymentReceiptBP;

import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;

/**
 * @author UntaSoft.
 * 
 */
public class MUNSCustomerBGAction extends X_UNS_CustomerBG_Action implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -884929068725688459L;
	
	/**
	 * @param ctx
	 * @param UNS_CustomerBG_Action_ID
	 * @param trxName
	 */
	public MUNSCustomerBGAction(Properties ctx, int UNS_CustomerBG_Action_ID, String trxName) {
		super(ctx, UNS_CustomerBG_Action_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerBGAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSCustomerBGAction(MUNSCustomerBG giro) {
		this(giro.getCtx(), 0, giro.get_TrxName());

		setUNS_CustomerBG_ID(giro.get_ID());
		setAD_Org_ID(giro.getAD_Org_ID());
		setDateDoc(new Timestamp(System.currentTimeMillis()));

		setDocAction(ACTION_Prepare);
		setDocStatus(STATUS_Drafted);

		setIsApproved(false);
		setProcessed(false);
		setProcessing(false);

		setAction(X_UNS_CustomerBG_Action.ACTION_None);
		setTotalPaid(Env.ZERO);
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
	private MUNSCustomerBG m_parent = null;

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
		
		MUNSCustomerBGInvList[] invList = getParent().getLinesBGInv(false);
		if (null == invList || invList.length == 0)
		{
			throw new AdempiereException("@No Giro Invoice List On @" + getParent().getName());
		}

		if (!getValidAction(getAction()))
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
//		if (DOCACTION_Prepare.equals(getDocAction()))
//		{
//			setProcessed(false);
//			return DocAction.STATUS_InProgress;
//		}

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

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		StringBuilder info = new StringBuilder();

		if (ACTION_Disbursment.equals(getAction()))
		{
			MPayment payment = null;
			BigDecimal unallocated = getTotalPaid();
			MUNSCustomerBGInvList[] invList = getParent().getLinesBGInv(true);
			
			BigDecimal unallocatedCharge = (BigDecimal)
					getParent().get_Value("ChargeAmt");
			int C_Charge_ID = getParent().get_ValueAsInt("C_Charge_ID");
			
			if (unallocatedCharge == null)
				unallocatedCharge = Env.ZERO;
			if (null == invList || invList.length == 0)
			{
				throw new AdempiereUserError("Please defaine Giro Invoice List first.");
			}
			for (int x=0; x<invList.length; x++)
			{
				MUNSCustomerBGInvList inv = invList[x];
				BigDecimal allocateTothisInv = inv.getPaidAmt();
				if (inv.getC_Invoice_ID() > 0)
				{
					//update current open invoice
					BigDecimal invoiceOpen = DB.getSQLValueBD(
							get_TrxName(), "SELECT InvoiceOpen(?, ?)", inv.getC_Invoice_ID(), 0);
					inv.setNetAmtToInvoice(invoiceOpen);
					inv.saveEx();
				}
				if(allocateTothisInv.compareTo(unallocated) == 1 || invList.length-1 == x)
				{
					allocateTothisInv = unallocated;
					inv.setPaidAmt(allocateTothisInv);
					inv.saveEx();
				}
				
				unallocated = unallocated.subtract(allocateTothisInv);
				if(allocateTothisInv.signum() <= 0)
					break;
				
				if(payment == null || inv.getC_Order_ID() > 0)
				{
					payment = (MPayment) inv.getC_Payment();
				}
				
				if(payment.get_ID() == 0)
				{
					payment.setDisbursementDate(getParent().getDisbursementDate());
					payment.setAD_Org_ID(getAD_Org_ID());
					payment.setC_Currency_ID(getC_Currency_ID());
					payment.setUNS_PR_Allocation_ID(0);
					payment.setC_DocType_ID(true);
					payment.setC_BPartner_ID(getParent().getC_BPartner_ID());
					payment.setBankCash(getC_BankAccount_ID(), true, MPayment.TENDERTYPE_Check);
					payment.setDescription("Generated from Customer billed Giro, for payment with Giro NO" + getParent().getName());
					payment.setCheckNo(getParent().getName());
					payment.setDateTrx(getDateDoc());
					payment.setDateAcct(getDateDoc());
					payment.setDisbursementDate(getDateDoc());
					payment.setC_Charge_ID(C_Charge_ID);
					payment.setChargeAmt(unallocatedCharge);
					payment.setSalesRep_ID(getParent().getSalesRep_ID());
					payment.saveEx();
					setC_Payment_ID(payment.get_ID());
				}

				inv.setC_Payment_ID(payment.get_ID());
				MPaymentAllocate allocate = (MPaymentAllocate) inv.getC_PaymentAllocate();
				if(allocate.get_ID() == 0)
				{
					allocate.setC_Payment_ID(payment.get_ID());
				}
				
				allocate.setAD_Org_ID(payment.getAD_Org_ID());
				
				if(inv.getC_Order_ID() > 0)
				{
					allocate.setC_Order_ID(inv.getC_Order_ID());
				}
				else if(inv.getC_Invoice_ID() > 0)
				{
					allocate.setC_Invoice_ID(inv.getC_Invoice_ID());
				}
				
				BigDecimal overUnder = inv.getNetAmtToInvoice().
						subtract(allocateTothisInv);
				BigDecimal distributeCharge = Env.ZERO;
				if (overUnder.signum() == 1 && unallocatedCharge.signum() == 1)
				{
					if (overUnder.compareTo(unallocatedCharge) >= 0)
					{
						distributeCharge = unallocatedCharge;
						unallocatedCharge = Env.ZERO;
					}
					else
					{
						distributeCharge = overUnder;
						unallocatedCharge = unallocatedCharge.
								subtract(distributeCharge);
					}
				}
				
				if (x == invList.length - 1 && unallocatedCharge.signum() == 1)
				{
					distributeCharge = distributeCharge.add(unallocatedCharge);
					unallocatedCharge = Env.ZERO;
				}
				
				BigDecimal writeOff = Env.ZERO;
				if(inv.getUNS_BillingLine_Result_ID() > 0)
				{
					String sql = "SELECT WriteOffAmt FROM UNS_BillingLine_Result"
							+ " WHERE UNS_BillingLine_Result_ID = ? AND PaymentStatus = 'PG'";
					writeOff = DB.getSQLValueBD(get_TrxName(), sql, inv.getUNS_BillingLine_Result_ID());
					if(writeOff == null)
						writeOff = Env.ZERO;
				}
				allocateTothisInv = allocateTothisInv.add(distributeCharge);
				overUnder = inv.getNetAmtToInvoice().subtract(allocateTothisInv).subtract(writeOff);
				
				allocate.setAmount(allocateTothisInv);
				allocate.setWriteOffAmt(writeOff);
				allocate.setPayToOverUnderAmount(allocateTothisInv);
				allocate.setOverUnderAmt(overUnder);
				allocate.setInvoiceAmt(inv.getNetAmtToInvoice());
				
				if (!allocate.save())
					throw new AdempiereException("Error while tray create Payment Allocate, Inv : "+ inv.getC_Invoice().toString());
				if(inv.getUNS_BillingLine_Result_ID() > 0)
				{
					String sql = "SELECT UNS_BillingLine_ID FROM UNS_BillingLine_Result WHERE UNS_BillingLine_Result_ID = ?";
					int retVal = DB.getSQLValue(get_TrxName(), sql, inv.getUNS_BillingLine_Result_ID());
					if(retVal <= 0)
						continue;
					
					int retVal2 = DB.executeUpdate(
							(new StringBuilder("UPDATE UNS_BillingLine SET PaidAmt = COALESCE(PaidAmt, 0) + ")
							.append(inv.getPaidAmt()).append(" WHERE UNS_BillingLine_ID = ")
							.append(retVal)).toString()
							, false
							, get_TrxName());
					if(retVal2 <= 0)
						log.log(Level.SEVERE, "Failed on update Billing Line");
					
					retVal2 = DB.executeUpdate(
							(new StringBuilder("UPDATE UNS_BillingGroup SET PaidAmt = COALESCE(PaidAmt, 0) + ")
							.append(inv.getPaidAmt()).append(" WHERE UNS_BillingGroup_ID = (")
							.append("SELECT UNS_BillingGroup_ID FROM UNS_Billing WHERE UNS_Billing_ID =(")
							.append("SELECT UNS_Billing_ID FROM UNS_BillingLine WHERE UNS_BillingLine_ID = ")
							.append(retVal).append("))")).toString(), false, get_TrxName());
					
					if(retVal2 <= 0)
						log.log(Level.SEVERE, "Failed on update Billing Group");
					
					sql = "Select UNS_PR_Allocation_ID FROM UNS_Handover_Inv WHERE UNS_BillingLine_Result_ID = ?";
					retVal2 = DB.getSQLValue(get_TrxName(), sql, inv.getUNS_BillingLine_Result_ID());
					if(retVal2 <=0)
						continue;
					
					if(payment.getUNS_PR_Allocation_ID() <= 0)
					{
						payment.setUNS_PR_Allocation_ID(retVal2);
						payment.saveEx();
					}
					
					retVal2 = DB.executeUpdate(
							(new StringBuilder("UPDATE UNS_PR_Allocation set ReceiptAmt = COALESCE(ReceiptAmt, 0) + ")
							.append(inv.getPaidAmt()).append(", PaidAmt = COALESCE(PaidAmt, 0) + ")
							.append(inv.getPaidAmt()).append(", GrandTotal = COALESCE(GrandTotal, 0) + ")
							.append(inv.getPaidAmt()).append(" WHERE UNS_PR_Allocation_ID = ")
							.append(retVal2)).toString()
							, false, get_TrxName());
					if(retVal2 <= 0)
						log.info("Failed on update Payment Receipt Allocation");
				}
				
				inv.setC_PaymentAllocate_ID(allocate.get_ID());
			
				inv.setProcessed(true);
				inv.saveEx();
				
				MUNSBillingLineResult result = (MUNSBillingLineResult) inv.getUNS_BillingLine_Result();
				if(result.getUNS_CustomerBG_ID() == 0)
				{
					MUNSBillingLineGiro[] giroList = result.getLines(true);
					for (int i=0; i<giroList.length; i++)
					{
						if(giroList[i].getUNS_CustomerBG_ID() != getUNS_CustomerBG_ID())
						{
							continue;
						}
						
						giroList[i].setReceiptAmtGiro(inv.getPaidAmt());
						giroList[i].saveEx();
					}
				}
				else
				{
					result.setReceiptAmtGiro(inv.getPaidAmt());
					result.saveEx();
				}
				
				if(inv.getUNS_BillingLine_Result_ID() > 0)
				{
					MUNSHandoverInv ho = MUNSHandoverInv.get(
							inv.getUNS_BillingLine_Result_ID(), get_TrxName());
					ho.setInvoiceCollectionType(MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverFinancePaid);
					ho.setProcessed(true);
					ho.save();
				}
			}
			
			try
			{
				ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(payment, 
						DocAction.ACTION_Complete);
				if (pi.isError())
				{
					throw new AdempiereException(pi.getSummary());
				}
			}
			catch (Exception ex)
			{
				throw new AdempiereException(ex.getMessage());
			}

			getParent().setDisbursementDate(getDateDoc());
			getParent().setPaymentStatus(MUNSCustomerBG.PAYMENTSTATUS_Paid);
			getParent().setDocStatus(DOCSTATUS_Completed);
			getParent().saveEx();
		}
		
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

		boolean reverseAllocate = getAction().equals(ACTION_Disbursment);
		if (reverseAllocate)
		{
			m_processMsg = reverseAllocate();
			if (null != m_processMsg)
				return false;
		}
		
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
		String desc = getRemarks();
		if (desc == null)
			setRemarks(description);
		else
			setRemarks(desc + " | " + description);
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


		boolean reverseAllocate = getAction().equals(ACTION_Disbursment);
		if (reverseAllocate)
		{
			m_processMsg = reverseAllocate();
			if (null != m_processMsg)
				return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
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
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "Action")).append("=").append(getAction());

		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getRemarks() != null && getRemarks().length() > 0)
			sb.append(" - ").append(getRemarks());
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
						.append(", Action =").append(getAction());

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
		
		return getTotalPaid();
	}

	@Override
	public int getC_Currency_ID() {
		
		return MCurrency.get(getCtx(), "IDR").get_ID();
	}

	@Override
	public MUNSCustomerBG getParent() {
		if (m_parent == null)
			m_parent = new MUNSCustomerBG(getCtx(), getUNS_CustomerBG_ID(), get_TrxName());

		return m_parent;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
//		if (!getAction().equals(X_UNS_CustomerBG_Action.ACTION_None)
//				&& !getValidAction(getAction()))
//		{
//			throw new AdempiereUserError("Invalid Giro Action");
//		}
		
		return super.beforeSave(newRecord);
	}

	private boolean getValidAction(String thisAction) {
		MUNSCustomerBGAction[] listOfAction = getParent().getLinesBGAction(true);
		String lastAction = X_UNS_CustomerBG_Action.ACTION_None;
		

		for (MUNSCustomerBGAction action : listOfAction)
		{
			if(!action.getDocStatus().equals(DOCSTATUS_Completed)
					&& !action.getDocStatus().equals(DOCSTATUS_Closed))
				continue;
			
			if(getC_BankAccount_ID() == 0)
				setC_BankAccount_ID(action.getC_BankAccount_ID());
			
			if (action.getAction().equals(ACTION_HandoverBank))
				lastAction = ACTION_HandoverBank;
			else if (action.getAction().equals(ACTION_Disbursment))
				lastAction = ACTION_Disbursment;
			else if (action.getAction().equals(ACTION_Refusal))
				lastAction = ACTION_Refusal;
			else if (action.getAction().equals(ACTION_ReturnGiro))
				lastAction = ACTION_ReturnGiro;
		}
		
		if(!thisAction.equals(X_UNS_CustomerBG_Action.ACTION_None)
				&& getC_BankAccount_ID() == 0)
			throw new AdempiereUserError("Bank account is mandatory");
		if(thisAction.equals(ACTION_Disbursment))
		{
			if(getTotalPaid().compareTo(getParent().getLimitAmt()) != 0)
			{
				setTotalPaid(getParent().getLimitAmt());
			}
		}

		if (lastAction.equals(X_UNS_CustomerBG_Action.ACTION_None))
			return (thisAction.equals(ACTION_Disbursment) || thisAction.equals(ACTION_Refusal) 
					|| thisAction.equals(ACTION_HandoverBank));
		else if (lastAction.equals(ACTION_HandoverBank))
			return (thisAction.equals(ACTION_Disbursment) || thisAction.equals(ACTION_Refusal));
		else if (lastAction.equals(ACTION_Disbursment) || lastAction.equals(ACTION_Refusal))
			return thisAction.equals(ACTION_ReturnGiro);

		return false;
	}
	
	private String reverseAllocate ()
	{
		MPayment payment = null;
		if (getC_Payment_ID() > 0)
		{
			payment = new MPayment(getCtx(), 
					getC_Payment_ID(), get_TrxName());
		}
		
		MUNSCustomerBGInvList[] invList = getParent().getLinesBGInv(true);
		
		for (int x=0; x<invList.length; x++)
		{
			MUNSCustomerBGInvList inv = invList[x];
			if (payment == null && inv.getC_Payment_ID() > 0)
			{
				payment = new MPayment(getCtx(), inv.getC_Payment_ID(), 
						get_TrxName());
			}
			if (payment == null)
			{
				return null;
			}
			
			inv.setC_Payment_ID(-1);
			
			if(inv.getUNS_BillingLine_Result_ID() > 0)
			{
				String sql = "SELECT UNS_BillingLine_ID FROM UNS_BillingLine_Result WHERE UNS_BillingLine_Result_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, inv.getUNS_BillingLine_Result_ID());
				if(retVal <= 0)
					continue;
				
				int retVal2 = DB.executeUpdate(
						(new StringBuilder("UPDATE UNS_BillingLine SET PaidAmt = COALESCE(PaidAmt, 0) - ")
						.append(inv.getPaidAmt()).append(" WHERE UNS_BillingLine_ID = ")
						.append(retVal)).toString()
						, false
						, get_TrxName());
				if(retVal2 <= 0)
					log.log(Level.SEVERE, "Failed on update Billing Line");
				
				retVal2 = DB.executeUpdate(
						(new StringBuilder("UPDATE UNS_BillingGroup SET PaidAmt = COALESCE(PaidAmt, 0) - ")
						.append(inv.getPaidAmt()).append(" WHERE UNS_BillingGroup_ID = (")
						.append("SELECT UNS_BillingGroup_ID FROM UNS_Billing WHERE UNS_Billing_ID =(")
						.append("SELECT UNS_Billing_ID FROM UNS_BillingLine WHERE UNS_BillingLine_ID = ")
						.append(retVal).append("))")).toString(), false, get_TrxName());
				
				if(retVal2 <= 0)
					log.log(Level.SEVERE, "Failed on update Billing Group");
				
				sql = "Select UNS_PR_Allocation_ID FROM UNS_Handover_Inv WHERE UNS_BillingLine_Result_ID = ?";
				retVal2 = DB.getSQLValue(get_TrxName(), sql, inv.getUNS_BillingLine_Result_ID());
				if(retVal2 <=0)
					continue;
				
				if(payment.getUNS_PR_Allocation_ID() <= 0)
				{
					payment.setUNS_PR_Allocation_ID(retVal2);
					payment.saveEx();
				}
				
				retVal2 = DB.executeUpdate(
						(new StringBuilder("UPDATE UNS_PR_Allocation set ReceiptAmt = COALESCE(ReceiptAmt, 0) - ")
						.append(inv.getPaidAmt()).append(", PaidAmt = COALESCE(PaidAmt, 0) - ")
						.append(inv.getPaidAmt()).append(", GrandTotal = COALESCE(GrandTotal, 0) - ")
						.append(inv.getPaidAmt()).append(" WHERE UNS_PR_Allocation_ID = ")
						.append(retVal2)).toString()
						, false, get_TrxName());
				if(retVal2 <= 0)
					log.info("Failed on update Payment Receipt Allocation");
			}
			
			inv.setC_PaymentAllocate_ID(-1);
			inv.setProcessed(false);
			inv.saveEx();
			
			MUNSBillingLineResult result = (MUNSBillingLineResult) inv.getUNS_BillingLine_Result();
			if(result.getUNS_CustomerBG_ID() == 0)
			{
				MUNSBillingLineGiro[] giroList = result.getLines(true);
				for (int i=0; i<giroList.length; i++)
				{
					if(giroList[i].getUNS_CustomerBG_ID() != getUNS_CustomerBG_ID())
					{
						continue;
					}
					
					giroList[i].setReceiptAmtGiro(Env.ZERO);
					giroList[i].saveEx();
				}
			}
			else
			{
				result.setReceiptAmtGiro(Env.ZERO);
				result.saveEx();
			}
			
			if(inv.getUNS_BillingLine_Result_ID() > 0)
			{
				MUNSHandoverInv ho = MUNSHandoverInv.get(
						inv.getUNS_BillingLine_Result_ID(), get_TrxName());
				if (ho.getInvoiceCollectionType().equals(MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverFinancePaid))
				{
					ho.setInvoiceCollectionType(MUNSHandoverInv.INVOICECOLLECTIONTYPE_HandoverFinanceNotPaid);
					ho.setProcessed(false);
					ho.save();
				}
			}
		}
		
		try
		{
			ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(payment, 
					DocAction.ACTION_Void);
			if (pi.isError())
			{
				throw new AdempiereException(pi.getSummary());
			}
		}
		catch (Exception ex)
		{
			throw new AdempiereException(ex.getMessage());
		}

		getParent().setDisbursementDate(null);
		getParent().setPaymentStatus(MUNSCustomerBG.PAYMENTSTATUS_OnProcess);
		getParent().setDocStatus(DOCSTATUS_InProgress);
		getParent().saveEx();
		setC_Payment_ID(-1);
		
		return null;
	}
}
