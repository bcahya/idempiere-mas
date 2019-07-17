/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCurrency;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko, Modified 13/08/13 by ITD - Andy
 */
public class MUNSLoanInstallment extends X_UNS_Loan_Installment implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_reversal = false;

	/**
	 * @param ctx
	 * @param UNS_Loan_Installment_ID
	 * @param trxName
	 */
	public MUNSLoanInstallment(Properties ctx, int UNS_Loan_Installment_ID,
			String trxName) {
		super(ctx, UNS_Loan_Installment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLoanInstallment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (null == getPaidAmt() || getPaidAmt().compareTo(BigDecimal.ZERO) <= 0)
			setPaidAmt(getParent().getInstallment());
		
		MUNSEmployeeLoan parent = getParent();
		if(getLineNetAmt().compareTo(parent.getLoanAmt())>0)
			setPaidAmt(parent.getLoanAmtLeft());
		
		if (newRecord)
			setInstallmentPeriod(generateInstallmentPeriod());
		
		if (isCashPayment())
		{
			if (getC_BankAccount_ID() == 0)
			{
				log.saveError("MandatoryFieldBankAccount", "");
				return false;	
			}
			else if (getC_Charge_ID() == 0)
			{
				log.saveError("MandatoryFieldCharge", "");
			}
		}
		
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess)
	{
		return super.afterSave(newRecord, sucess);
	}
	
	private int generateInstallmentPeriod()
	{
		return DB.getSQLValue(
				get_TrxName(), "SELECT COALESCE(MAX(" + COLUMNNAME_InstallmentPeriod + ") + 1, 1) FROM " 
		+ Table_Name + " WHERE " + COLUMNNAME_UNS_Employee_Loan_ID + " = "
		+ getUNS_Employee_Loan_ID());
	}
	
	private String updateHeader()
	{
		MUNSEmployeeLoan parent = getParent();
		
		parent.setPaidPeriod(getTotalPaidPeriod());
		parent.setTotalPaid(getLineNetAmt());
		parent.setLoanAmtLeft(parent.getLoanAmt().subtract(parent.getTotalPaid()));
		
		if (!parent.save())
			return "Failed to update employee loan ";
		
		return null;
	}
	
	private BigDecimal getLineNetAmt()
	{
		BigDecimal lineNetAmt = BigDecimal.ZERO;
		MUNSLoanInstallment[] loanInstallments = getParent().getLines(true);
		for(MUNSLoanInstallment loanInstallment : loanInstallments)
		{
			if (loanInstallment.getDocStatus().equals("CO"))
				lineNetAmt = lineNetAmt.add(loanInstallment.getPaidAmt());
		}
		lineNetAmt = lineNetAmt.add(getPaidAmt());
		return lineNetAmt;
	}
	
	private int getTotalPaidPeriod()
	{
		return Query.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				COLUMNNAME_UNS_Employee_Loan_ID + " = " + getUNS_Employee_Loan_ID()
				+ " AND DocStatus='CO'", get_TrxName()).list().size()+1;
	}
	
	public MUNSEmployeeLoan getParent()
	{
		return (MUNSEmployeeLoan)getUNS_Employee_Loan();
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
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
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
		setProcessed(false);
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
	
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
			
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);

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
		
		if (isCashPayment())
		{
			m_processMsg = doCreateStatementLine();
			if (null != m_processMsg)
				return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = updateHeader();
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		
		log.info(getSummary());
		
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		else if (DOCSTATUS_Completed.equals(getDocStatus()))
		{
			boolean accrual = false;
			try 
			{
				MPeriod.testPeriodOpen(getCtx(), getPaidDate(), 
						getC_DocType_ID(), getAD_Org_ID());
			}
			catch (PeriodClosedException e) 
			{
				accrual = true;
			}
			
			if (accrual)
				return reverseAccrualIt();
			else
				return reverseCorrectIt();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
			return false;
		
		addRemarks("**VOIDED " + getPaidAmt() + "**");
		setPaidAmt(Env.ZERO);
		saveEx();
				
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
			return false;
		
		setDocStatus(DOCSTATUS_Voided);
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (null != m_processMsg)
			return false;
		
		MUNSLoanInstallment reversal = reverse(false);
		if (null == reversal)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (null != m_processMsg)
			return false;
		
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		setProcessed(true);
		setReversal_ID(reversal.get_ID());
		saveEx();
		return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (null != m_processMsg)
			return false;
		
		MUNSLoanInstallment reversal = reverse(true);
		if (null == reversal)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (null != m_processMsg)
			return false;
		
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		setProcessed(true);
		setReversal_ID(reversal.get_ID());
		saveEx();
		return true;
	}

	@Override
	public boolean reActivateIt() {
		m_processMsg = "Invalid action [Reactivate-it]";
		return false;
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder(getDocumentNo()).append(", ")
				.append(getUNS_Employee_Loan().getUNS_Employee().getName())
				.append(", Loan Amount ").append(getPaidAmt());
		if (getRemarks() != null)
		{
			sb.append(", ").append(getRemarks());
		}
		
		String summary = sb.toString();
		return summary;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return getSummary();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
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
		return MCurrency.get(getCtx(), "IDR").get_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getPaidAmt();
	}
	
	/**
	 * Create statement 
	 * @param stmt
	 * @return null when success or error message on failed to create statement line.
	 */
	private String doCreateStatementLine()
	{
		if(getC_BankStatementLine_ID() > 0)
			return null;
		
		Timestamp dateTrx = getPaidDate();
		MBankStatement stmt = MBankStatement.getOpen(
				getC_BankAccount_ID(), get_TrxName(), true);
		MBankStatementLine stmtLine = new MBankStatementLine(stmt);
		
		try
		{
			int C_BPartner_ID = getUNS_Employee_Loan().getUNS_Employee()
					.getC_BPartner_ID();
			
			if (C_BPartner_ID == 0)
			{
				C_BPartner_ID = getParent().get_ValueAsInt("C_BPartner_ID");
			}
			stmtLine.setC_BPartner_ID(C_BPartner_ID);
			int C_Charge_ID = getC_Charge_ID();			
			BigDecimal amtSrc = getPaidAmt();
			
			String transactionType = 
					MBankStatementLine.TRANSACTIONTYPE_ARTransaction;
			if (amtSrc.signum() == -1)
			{
				transactionType = 
						MBankStatementLine.TRANSACTIONTYPE_APTransaction;
			}
			
			stmtLine.setTransactionType(transactionType);
			stmtLine.setAmount(amtSrc.abs());
			stmtLine.setChargeAmt(amtSrc);
			stmtLine.setStmtAmt(amtSrc);
			stmtLine.setDateAcct(dateTrx);
			stmtLine.setEftValutaDate(dateTrx);
			stmtLine.setStatementLineDate(dateTrx);
			stmtLine.setEftStatementLineDate(dateTrx);
			stmtLine.setC_Currency_ID(getC_Currency_ID());
			stmtLine.setC_Charge_ID(C_Charge_ID);
			stmtLine.setDescription(getSummary());
			stmtLine.saveEx();
			
			setC_BankStatementLine_ID(stmtLine.get_ID());
			saveEx();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		
		return null;
	}
	
	private MUNSLoanInstallment reverse (boolean accrual)
	{
		MUNSLoanInstallment reversal = new MUNSLoanInstallment(
				getCtx(), 0, get_TrxName());
		PO.copyValues(this, reversal);
		reversal.setClientOrg(this);
		reversal.setC_BankStatementLine_ID(-1);
		reversal.setReversal_ID(this.get_ID());
		reversal.setPaidAmt(reversal.getPaidAmt().negate());
		reversal.setReversal(true);
		if (accrual)
		{
			Timestamp date = new Timestamp(System.currentTimeMillis());
			reversal.setPaidDate(date);
		}
		try
		{
			reversal.saveEx();
			boolean ok = reversal.processIt(DocAction.ACTION_Complete);
			if (!ok)
			{
				m_processMsg = reversal.getProcessMsg();
				return null;
			}
			
			reversal.setDocStatus(DOCSTATUS_Reversed);
			reversal.setDocAction(DocAction.ACTION_None);
			reversal.saveEx();
		}
		catch (Exception e)
		{
			m_processMsg = e.getMessage();
			return null;
		}
		
		return reversal;
	}
	
	public void setReversal (boolean reversal)
	{
		this.m_reversal = reversal;
	}
	
	public boolean isReversal ()
	{
		return this.m_reversal;
	}
	
	public void addRemarks(String remarks)
	{
		String mark = getRemarks();
		if (mark == null)
			setRemarks(remarks);
		else
			setRemarks(mark + " | " + remarks);
	}
	
}
