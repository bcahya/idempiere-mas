/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Employee_Loan
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Employee_Loan extends PO implements I_UNS_Employee_Loan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160302L;

    /** Standard Constructor */
    public X_UNS_Employee_Loan (Properties ctx, int UNS_Employee_Loan_ID, String trxName)
    {
      super (ctx, UNS_Employee_Loan_ID, trxName);
      /** if (UNS_Employee_Loan_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_Charge_ID (0);
// 1000001
			setC_DocType_ID (0);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setInstallmentPeriod (0);
			setIsApproved (false);
// N
			setLoanAmt (Env.ZERO);
			setLoanType (null);
// CMP
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setReason (null);
			setRequestDate (new Timestamp( System.currentTimeMillis() ));
			setTrxDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_Employee_ID (0);
			setUNS_Employee_Loan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Employee_Loan (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_Employee_Loan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Allowance Payment.
		@param AllowancePayment 
		The amount of payment using employee's medical allowance
	  */
	public void setAllowancePayment (BigDecimal AllowancePayment)
	{
		set_Value (COLUMNNAME_AllowancePayment, AllowancePayment);
	}

	/** Get Allowance Payment.
		@return The amount of payment using employee's medical allowance
	  */
	public BigDecimal getAllowancePayment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllowancePayment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Cash Payment.
		@param CashPayment 
		The amount of payment using cash
	  */
	public void setCashPayment (BigDecimal CashPayment)
	{
		set_Value (COLUMNNAME_CashPayment, CashPayment);
	}

	/** Get Cash Payment.
		@return The amount of payment using cash
	  */
	public BigDecimal getCashPayment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CashPayment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Cash / Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Cash / Bank Account.
		@return Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankStatementLine)MTable.get(getCtx(), org.compiere.model.I_C_BankStatementLine.Table_Name)
			.getPO(getC_BankStatementLine_ID(), get_TrxName());	}

	/** Set Bank statement line.
		@param C_BankStatementLine_ID 
		Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Bank statement line.
		@return Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Confirmed = CF */
	public static final String DOCACTION_Confirmed = "CF";
	/** Finished = FN */
	public static final String DOCACTION_Finished = "FN";
	/** Cancelled = CN */
	public static final String DOCACTION_Cancelled = "CN";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Generate Pay.
		@param GeneratePay Generate Pay	  */
	public void setGeneratePay (String GeneratePay)
	{
		set_Value (COLUMNNAME_GeneratePay, GeneratePay);
	}

	/** Get Generate Pay.
		@return Generate Pay	  */
	public String getGeneratePay () 
	{
		return (String)get_Value(COLUMNNAME_GeneratePay);
	}

	/** Set Installment.
		@param Installment Installment	  */
	public void setInstallment (BigDecimal Installment)
	{
		set_Value (COLUMNNAME_Installment, Installment);
	}

	/** Get Installment.
		@return Installment	  */
	public BigDecimal getInstallment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Installment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Installment Period.
		@param InstallmentPeriod Installment Period	  */
	public void setInstallmentPeriod (int InstallmentPeriod)
	{
		set_Value (COLUMNNAME_InstallmentPeriod, Integer.valueOf(InstallmentPeriod));
	}

	/** Get Installment Period.
		@return Installment Period	  */
	public int getInstallmentPeriod () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InstallmentPeriod);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Loan Amt.
		@param LoanAmt Loan Amt	  */
	public void setLoanAmt (BigDecimal LoanAmt)
	{
		set_Value (COLUMNNAME_LoanAmt, LoanAmt);
	}

	/** Get Loan Amt.
		@return Loan Amt	  */
	public BigDecimal getLoanAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoanAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Loan Amt Left.
		@param LoanAmtLeft Loan Amt Left	  */
	public void setLoanAmtLeft (BigDecimal LoanAmtLeft)
	{
		set_Value (COLUMNNAME_LoanAmtLeft, LoanAmtLeft);
	}

	/** Get Loan Amt Left.
		@return Loan Amt Left	  */
	public BigDecimal getLoanAmtLeft () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoanAmtLeft);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Company = CMP */
	public static final String LOANTYPE_Company = "CMP";
	/** Koperasi = KPR */
	public static final String LOANTYPE_Koperasi = "KPR";
	/** Medical = MED */
	public static final String LOANTYPE_Medical = "MED";
	/** Set Loan Type.
		@param LoanType Loan Type	  */
	public void setLoanType (String LoanType)
	{

		set_Value (COLUMNNAME_LoanType, LoanType);
	}

	/** Get Loan Type.
		@return Loan Type	  */
	public String getLoanType () 
	{
		return (String)get_Value(COLUMNNAME_LoanType);
	}

	/** Set Medical Allowance.
		@param MedicalAllowance 
		The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance)
	{
		set_Value (COLUMNNAME_MedicalAllowance, MedicalAllowance);
	}

	/** Get Medical Allowance.
		@return The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Medical Costs.
		@param MedicalCosts 
		The cost of medical being charged to patient
	  */
	public void setMedicalCosts (BigDecimal MedicalCosts)
	{
		set_Value (COLUMNNAME_MedicalCosts, MedicalCosts);
	}

	/** Get Medical Costs.
		@return The cost of medical being charged to patient
	  */
	public BigDecimal getMedicalCosts () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalCosts);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Paid Period.
		@param PaidPeriod Paid Period	  */
	public void setPaidPeriod (int PaidPeriod)
	{
		set_Value (COLUMNNAME_PaidPeriod, Integer.valueOf(PaidPeriod));
	}

	/** Get Paid Period.
		@return Paid Period	  */
	public int getPaidPeriod () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PaidPeriod);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

	/** Set Remaining Allowance Amt.
		@param RemainingAllowance 
		Remaining yearly employee's medical allowance amt
	  */
	public void setRemainingAllowance (BigDecimal RemainingAllowance)
	{
		set_Value (COLUMNNAME_RemainingAllowance, RemainingAllowance);
	}

	/** Get Remaining Allowance Amt.
		@return Remaining yearly employee's medical allowance amt
	  */
	public BigDecimal getRemainingAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainingAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Set Request Date.
		@param RequestDate Request Date	  */
	public void setRequestDate (Timestamp RequestDate)
	{
		set_Value (COLUMNNAME_RequestDate, RequestDate);
	}

	/** Get Request Date.
		@return Request Date	  */
	public Timestamp getRequestDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_RequestDate);
	}

	public com.uns.model.I_UNS_Employee_Loan getReversal() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee_Loan)MTable.get(getCtx(), com.uns.model.I_UNS_Employee_Loan.Table_Name)
			.getPO(getReversal_ID(), get_TrxName());	}

	/** Set Reversal ID.
		@param Reversal_ID 
		ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Reversal ID.
		@return ID of document reversal
	  */
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Paid.
		@param TotalPaid Total Paid	  */
	public void setTotalPaid (BigDecimal TotalPaid)
	{
		set_Value (COLUMNNAME_TotalPaid, TotalPaid);
	}

	/** Get Total Paid.
		@return Total Paid	  */
	public BigDecimal getTotalPaid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalPaid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Trx Date.
		@param TrxDate Trx Date	  */
	public void setTrxDate (Timestamp TrxDate)
	{
		set_Value (COLUMNNAME_TrxDate, TrxDate);
	}

	/** Get Trx Date.
		@return Trx Date	  */
	public Timestamp getTrxDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TrxDate);
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Employee_ID()));
    }

	/** Set Employee Loan.
		@param UNS_Employee_Loan_ID Employee Loan	  */
	public void setUNS_Employee_Loan_ID (int UNS_Employee_Loan_ID)
	{
		if (UNS_Employee_Loan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_Loan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_Loan_ID, Integer.valueOf(UNS_Employee_Loan_ID));
	}

	/** Get Employee Loan.
		@return Employee Loan	  */
	public int getUNS_Employee_Loan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_Loan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Employee_Loan_UU.
		@param UNS_Employee_Loan_UU UNS_Employee_Loan_UU	  */
	public void setUNS_Employee_Loan_UU (String UNS_Employee_Loan_UU)
	{
		set_Value (COLUMNNAME_UNS_Employee_Loan_UU, UNS_Employee_Loan_UU);
	}

	/** Get UNS_Employee_Loan_UU.
		@return UNS_Employee_Loan_UU	  */
	public String getUNS_Employee_Loan_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Employee_Loan_UU);
	}

	public com.uns.model.I_UNS_MedicalRecord getUNS_MedicalRecord() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MedicalRecord)MTable.get(getCtx(), com.uns.model.I_UNS_MedicalRecord.Table_Name)
			.getPO(getUNS_MedicalRecord_ID(), get_TrxName());	}

	/** Set Medical Record.
		@param UNS_MedicalRecord_ID Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID)
	{
		if (UNS_MedicalRecord_ID < 1) 
			set_Value (COLUMNNAME_UNS_MedicalRecord_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MedicalRecord_ID, Integer.valueOf(UNS_MedicalRecord_ID));
	}

	/** Get Medical Record.
		@return Medical Record	  */
	public int getUNS_MedicalRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MedicalRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}