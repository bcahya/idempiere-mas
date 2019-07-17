/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCurrency;
import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko, Menjangan
 * @see www.untasoft.com
 */
public class MUNSEmployeeLoan extends X_UNS_Employee_Loan implements DocAction, DocOptions {

	private MUNSLoanInstallment[] m_Lines = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_isProcessing = false;
	private boolean m_reversal = false;

	/**
	 * @param ctx
	 * @param UNS_Employee_Loan_ID
	 * @param trxName
	 */
	public MUNSEmployeeLoan(Properties ctx, int UNS_Employee_Loan_ID,
			String trxName) {
		super(ctx, UNS_Employee_Loan_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSEmployeeLoan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSLoanInstallment[]
	 */
	public MUNSLoanInstallment[] getLines(boolean requery)
	{
		if (null != m_Lines
				&& !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String whereClause = MUNSLoanInstallment.COLUMNNAME_UNS_Employee_Loan_ID + " = " + getUNS_Employee_Loan_ID();
		List<MUNSLoanInstallment> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSLoanInstallment.Table_Name
				, whereClause, get_TrxName())
				.list();
		
		m_Lines = new MUNSLoanInstallment[list.size()];
		return list.toArray(m_Lines);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{	
		if (LOANTYPE_Medical.equals(getLoanType()) && !m_isProcessing)
		{
			MUNSEmployee employee = (MUNSEmployee) getUNS_Employee();
			
			MUNSEmployeeAllowanceRecord employeeAllowance = MUNSEmployeeAllowanceRecord.getCreateMedical(
					getCtx(), employee, getTrxDate(), get_TrxName());
			
			if (employeeAllowance != null)
			{
				setMedicalAllowance(employeeAllowance.getMedicalAllowance());
				setRemainingAllowance(employeeAllowance.getRemainingAmt());

				if (getAllowancePayment().compareTo(getRemainingAllowance()) > 0)
					setAllowancePayment(getRemainingAllowance());
			}
			BigDecimal totalInitialPayment = getAllowancePayment().add(getCashPayment());
			if (totalInitialPayment.compareTo(getMedicalCosts()) > 0)
				throw new AdempiereUserError("Total Allowance + Cash Payment greater then Total Medical Costs.", 
						"Please enter the right amount for allowance and cash payment!");
			
			setLoanAmt(getMedicalCosts().subtract(totalInitialPayment));
			setLoanAmtLeft(getLoanAmt());
		}
		
		if(newRecord || DOCSTATUS_Drafted.equals(getDocStatus()))
		{
			if(getInstallmentPeriod() <= 0)
				throw new AdempiereException("Instalment period must be greater than 0");
			
			double installment = 0.0;
			setLoanAmtLeft(getLoanAmt());
			installment = getLoanAmtLeft().doubleValue() / getInstallmentPeriod();
			setInstallment (new BigDecimal(installment).setScale(2, RoundingMode.HALF_UP));
		}
		
		return super.beforeSave(newRecord);
	}

	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static List<MUNSEmployeeLoan> gets(Properties ctx, int UNS_Employee_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name
				, COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID 
				+ " AND " + COLUMNNAME_LoanAmtLeft + " > 0 AND " 
						+ COLUMNNAME_IsActive + " = 'Y'"
						, trxName)
						.list();
	}
	
	public static BigDecimal getLoanToCompany(Properties ctx, int UNS_Employee_ID, String trxName){
		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
				ctx, UNS_Employee_ID, trxName);
		double paidToCompany = 0;
		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan){
			if(MUNSEmployeeLoan.LOANTYPE_Company.equals(employeeLoan.getLoanType()))
				paidToCompany = paidToCompany + employeeLoan.getLoanAmtLeft().doubleValue();
		}
		
		return new BigDecimal(paidToCompany);
	}
	
	public static BigDecimal getLoanToKoperasi(Properties ctx, int UNS_Employee_ID, String trxName){
		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
				ctx, UNS_Employee_ID, trxName);
		double paidToKoperasi = 0;
		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan){
			if(MUNSEmployeeLoan.LOANTYPE_Koperasi.equals(employeeLoan.getLoanType()))
				paidToKoperasi = paidToKoperasi + employeeLoan.getLoanAmtLeft().doubleValue();
		}
		
		return new BigDecimal(paidToKoperasi);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// 
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Reverse_Accrual;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    		
    	return index;
	}

	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	@Override
	public boolean processIt(String action) throws Exception {
		// 
		m_isProcessing = true;
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		boolean processReturn = engine.processIt (action, getDocAction());
		m_isProcessing = false;
		return processReturn;
	}

	@Override
	public boolean unlockIt() {
		// 
		log.info(toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// 
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// 
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		if (LOANTYPE_Medical.equals(getLoanType()))
		{
			MUNSEmployee employee = (MUNSEmployee) getUNS_Employee();
			
			MUNSEmployeeAllowanceRecord employeeAllowance = MUNSEmployeeAllowanceRecord.getCreateMedical(
					getCtx(), employee, getTrxDate(), get_TrxName());
			
			if (employeeAllowance == null && getAllowancePayment().signum() > 0)
				m_processMsg = "Employee doesn't have medical allowance.";
			else if (employeeAllowance.getRemainingAmt().compareTo(getAllowancePayment()) < 0)
				m_processMsg = "Employee's allowance record has been changed. Please revise it to re-complete it.";
			
			//saveEx();
			
			if (m_processMsg != null) {
				setRemainingAllowance(employeeAllowance.getRemainingAmt());
				//setProcessed(false);
				return DOCSTATUS_Invalid;
			}
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
		// 
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// 
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// 
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
		
		if (getUNS_Employee_ID() > 0 && getAllowancePayment().signum() > 0)
		{
			
			MUNSEmployeeAllowanceRecord medAllowanceRec = MUNSEmployeeAllowanceRecord.getCreateMedical(
					getCtx(), (MUNSEmployee) getUNS_Employee(), getTrxDate(), get_TrxName());
			
			medAllowanceRec.setMedicalAllowanceUsed(
					medAllowanceRec.getMedicalAllowanceUsed().add(getAllowancePayment()));
			
			if (!medAllowanceRec.save()) {
				m_processMsg = "Failed when updating medical allowance ";
				return DocAction.STATUS_Invalid;
			}
		}
		
		if (LOANTYPE_Company.equals(getLoanType()))
		{
			m_processMsg = doCreateStatementLine();
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;
		}
		else if (LOANTYPE_Koperasi.equals(getLoanType()))
		{
			
		}
		else if (LOANTYPE_Medical.equals(getLoanType()))
		{
			
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);	
		//m_processMsg = info.toString();
		//
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
				MPeriod.testPeriodOpen(getCtx(), getTrxDate(), 
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
		
		addRemarks("**VOIDED " + getLoanAmt() + "**");
		setLoanAmt(Env.ZERO);
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
		// 
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
		
		MUNSEmployeeLoan reversal = reverse(false);
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
		
		MUNSEmployeeLoan reversal = reverse(true);
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
		m_processMsg = "Invalid action [Reactivate-It]";
		return false;
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder(getDocumentNo()).append(", ")
				.append(getUNS_Employee().getName())
				.append(", Loan Amount ").append(getLoanAmt());
		if (getReason() != null)
		{
			sb.append(", ").append(getReason());
		}
		if (getRemarks() != null)
		{
			sb.append(", ").append(getRemarks());
		}
		
		String summary = sb.toString();
		return summary;
	}

	@Override
	public String getDocumentInfo() {
		return getSummary();
	}

	@Override
	public File createPDF() {
		// 
		return null;
	}

	@Override
	public String getProcessMsg() {
		// 
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// 
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// 
		return MCurrency.get(getCtx(), "IDR").get_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getLoanAmt();
	}	
	
	/**
	 * Create statement 
	 * @param stmt
	 * @return null when success or error message on failed to create statement line.
	 */
	public String doCreateStatementLine()
	{
		if(getC_BankStatementLine_ID() > 0)
			return null;
		
		Timestamp dateTrx = getTrxDate();
		MBankStatement stmt = MBankStatement.getOpen(
				getC_BankAccount_ID(), get_TrxName(), true);
		MBankStatementLine stmtLine = new MBankStatementLine(stmt);
		
		try
		{
			MUser[] user = MUser.getOfEmployee(getCtx(), getUNS_Employee_ID(), get_TrxName());
			int C_BPartner_ID = 0;
			for(int i=0;i<user.length;i++)
			{
				if(user[i].getC_BPartner_ID() > 0)
				{
					C_BPartner_ID = user[i].getC_BPartner_ID();
					break;
				}
			}
			
			if(C_BPartner_ID == 0)
				return "Employee not have been business partner";
			
			stmtLine.setC_BPartner_ID(C_BPartner_ID);
			int C_Charge_ID = getC_Charge_ID();
			BigDecimal amtSrc = getLoanAmt();
			
			String transactionType = 
					MBankStatementLine.TRANSACTIONTYPE_APTransaction;
			if (amtSrc.signum() == -1)
			{
				transactionType = 
						MBankStatementLine.TRANSACTIONTYPE_ARTransaction;
			}
			
			stmtLine.setChargeAmt(amtSrc.negate());
			stmtLine.setStmtAmt(amtSrc.negate());
			stmtLine.setTransactionType(transactionType);
			stmtLine.setAmount(amtSrc.abs());
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
	
	public void addRemarks(String remarks)
	{
		String mark = getRemarks();
		if (mark == null)
			setRemarks(remarks);
		else
			setRemarks(mark + " | " + remarks);
	}
	
	private MUNSEmployeeLoan reverse (boolean accrual)
	{
		MUNSEmployeeLoan reversal = new MUNSEmployeeLoan(
				getCtx(), 0, get_TrxName());
		PO.copyValues(this, reversal);
		reversal.setClientOrg(this);
		reversal.setC_BankStatementLine_ID(-1);
		reversal.setReversal_ID(this.get_ID());
		reversal.setLoanAmt(reversal.getLoanAmt().negate());
		reversal.setLoanAmtLeft(reversal.getLoanAmtLeft().negate());
		reversal.setCashPayment(reversal.getCashPayment().negate());
		reversal.setMedicalAllowance(reversal.getMedicalAllowance().negate());
		reversal.setMedicalCosts(reversal.getMedicalCosts().negate());
		reversal.setReversal(true);
		if (accrual)
		{
			Timestamp date = new Timestamp(System.currentTimeMillis());
			reversal.setTrxDate(date);
			reversal.setRequestDate(date);
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
		return m_reversal;
	}
}
