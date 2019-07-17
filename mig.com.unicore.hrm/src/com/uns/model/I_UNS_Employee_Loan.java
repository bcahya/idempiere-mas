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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_Employee_Loan
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Employee_Loan 
{

    /** TableName=UNS_Employee_Loan */
    public static final String Table_Name = "UNS_Employee_Loan";

    /** AD_Table_ID=1000081 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AllowancePayment */
    public static final String COLUMNNAME_AllowancePayment = "AllowancePayment";

	/** Set Allowance Payment.
	  * The amount of payment using employee's medical allowance
	  */
	public void setAllowancePayment (BigDecimal AllowancePayment);

	/** Get Allowance Payment.
	  * The amount of payment using employee's medical allowance
	  */
	public BigDecimal getAllowancePayment();

    /** Column name CashPayment */
    public static final String COLUMNNAME_CashPayment = "CashPayment";

	/** Set Cash Payment.
	  * The amount of payment using cash
	  */
	public void setCashPayment (BigDecimal CashPayment);

	/** Get Cash Payment.
	  * The amount of payment using cash
	  */
	public BigDecimal getCashPayment();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/** Set Bank statement line.
	  * Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/** Get Bank statement line.
	  * Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID();

	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine() throws RuntimeException;

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name GeneratePay */
    public static final String COLUMNNAME_GeneratePay = "GeneratePay";

	/** Set Generate Pay	  */
	public void setGeneratePay (String GeneratePay);

	/** Get Generate Pay	  */
	public String getGeneratePay();

    /** Column name Installment */
    public static final String COLUMNNAME_Installment = "Installment";

	/** Set Installment	  */
	public void setInstallment (BigDecimal Installment);

	/** Get Installment	  */
	public BigDecimal getInstallment();

    /** Column name InstallmentPeriod */
    public static final String COLUMNNAME_InstallmentPeriod = "InstallmentPeriod";

	/** Set Installment Period	  */
	public void setInstallmentPeriod (int InstallmentPeriod);

	/** Get Installment Period	  */
	public int getInstallmentPeriod();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name LoanAmt */
    public static final String COLUMNNAME_LoanAmt = "LoanAmt";

	/** Set Loan Amt	  */
	public void setLoanAmt (BigDecimal LoanAmt);

	/** Get Loan Amt	  */
	public BigDecimal getLoanAmt();

    /** Column name LoanAmtLeft */
    public static final String COLUMNNAME_LoanAmtLeft = "LoanAmtLeft";

	/** Set Loan Amt Left	  */
	public void setLoanAmtLeft (BigDecimal LoanAmtLeft);

	/** Get Loan Amt Left	  */
	public BigDecimal getLoanAmtLeft();

    /** Column name LoanType */
    public static final String COLUMNNAME_LoanType = "LoanType";

	/** Set Loan Type	  */
	public void setLoanType (String LoanType);

	/** Get Loan Type	  */
	public String getLoanType();

    /** Column name MedicalAllowance */
    public static final String COLUMNNAME_MedicalAllowance = "MedicalAllowance";

	/** Set Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance);

	/** Get Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance();

    /** Column name MedicalCosts */
    public static final String COLUMNNAME_MedicalCosts = "MedicalCosts";

	/** Set Medical Costs.
	  * The cost of medical being charged to patient
	  */
	public void setMedicalCosts (BigDecimal MedicalCosts);

	/** Get Medical Costs.
	  * The cost of medical being charged to patient
	  */
	public BigDecimal getMedicalCosts();

    /** Column name PaidPeriod */
    public static final String COLUMNNAME_PaidPeriod = "PaidPeriod";

	/** Set Paid Period	  */
	public void setPaidPeriod (int PaidPeriod);

	/** Get Paid Period	  */
	public int getPaidPeriod();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/** Set Reason	  */
	public void setReason (String Reason);

	/** Get Reason	  */
	public String getReason();

    /** Column name RemainingAllowance */
    public static final String COLUMNNAME_RemainingAllowance = "RemainingAllowance";

	/** Set Remaining Allowance Amt.
	  * Remaining yearly employee's medical allowance amt
	  */
	public void setRemainingAllowance (BigDecimal RemainingAllowance);

	/** Get Remaining Allowance Amt.
	  * Remaining yearly employee's medical allowance amt
	  */
	public BigDecimal getRemainingAllowance();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name RequestDate */
    public static final String COLUMNNAME_RequestDate = "RequestDate";

	/** Set Request Date	  */
	public void setRequestDate (Timestamp RequestDate);

	/** Get Request Date	  */
	public Timestamp getRequestDate();

    /** Column name Reversal_ID */
    public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/** Set Reversal ID.
	  * ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID);

	/** Get Reversal ID.
	  * ID of document reversal
	  */
	public int getReversal_ID();

	public com.uns.model.I_UNS_Employee_Loan getReversal() throws RuntimeException;

    /** Column name TotalPaid */
    public static final String COLUMNNAME_TotalPaid = "TotalPaid";

	/** Set Total Paid	  */
	public void setTotalPaid (BigDecimal TotalPaid);

	/** Get Total Paid	  */
	public BigDecimal getTotalPaid();

    /** Column name TrxDate */
    public static final String COLUMNNAME_TrxDate = "TrxDate";

	/** Set Trx Date	  */
	public void setTrxDate (Timestamp TrxDate);

	/** Get Trx Date	  */
	public Timestamp getTrxDate();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_Employee_Loan_ID */
    public static final String COLUMNNAME_UNS_Employee_Loan_ID = "UNS_Employee_Loan_ID";

	/** Set Employee Loan	  */
	public void setUNS_Employee_Loan_ID (int UNS_Employee_Loan_ID);

	/** Get Employee Loan	  */
	public int getUNS_Employee_Loan_ID();

    /** Column name UNS_Employee_Loan_UU */
    public static final String COLUMNNAME_UNS_Employee_Loan_UU = "UNS_Employee_Loan_UU";

	/** Set UNS_Employee_Loan_UU	  */
	public void setUNS_Employee_Loan_UU (String UNS_Employee_Loan_UU);

	/** Get UNS_Employee_Loan_UU	  */
	public String getUNS_Employee_Loan_UU();

    /** Column name UNS_MedicalRecord_ID */
    public static final String COLUMNNAME_UNS_MedicalRecord_ID = "UNS_MedicalRecord_ID";

	/** Set Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID);

	/** Get Medical Record	  */
	public int getUNS_MedicalRecord_ID();

	public com.uns.model.I_UNS_MedicalRecord getUNS_MedicalRecord() throws RuntimeException;

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
