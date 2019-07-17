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

/** Generated Interface for UNS_Loan_Installment
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Loan_Installment 
{

    /** TableName=UNS_Loan_Installment */
    public static final String Table_Name = "UNS_Loan_Installment";

    /** AD_Table_ID=1000097 */
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

    /** Column name IsCashPayment */
    public static final String COLUMNNAME_IsCashPayment = "IsCashPayment";

	/** Set Cash Payment	  */
	public void setIsCashPayment (boolean IsCashPayment);

	/** Get Cash Payment	  */
	public boolean isCashPayment();

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount	  */
	public BigDecimal getPaidAmt();

    /** Column name PaidDate */
    public static final String COLUMNNAME_PaidDate = "PaidDate";

	/** Set Paid Date	  */
	public void setPaidDate (Timestamp PaidDate);

	/** Get Paid Date	  */
	public Timestamp getPaidDate();

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

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

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

	public com.uns.model.I_UNS_Loan_Installment getReversal() throws RuntimeException;

    /** Column name UNS_Employee_Loan_ID */
    public static final String COLUMNNAME_UNS_Employee_Loan_ID = "UNS_Employee_Loan_ID";

	/** Set Employee Loan	  */
	public void setUNS_Employee_Loan_ID (int UNS_Employee_Loan_ID);

	/** Get Employee Loan	  */
	public int getUNS_Employee_Loan_ID();

	public com.uns.model.I_UNS_Employee_Loan getUNS_Employee_Loan() throws RuntimeException;

    /** Column name UNS_Loan_Installment_ID */
    public static final String COLUMNNAME_UNS_Loan_Installment_ID = "UNS_Loan_Installment_ID";

	/** Set Loan Installment	  */
	public void setUNS_Loan_Installment_ID (int UNS_Loan_Installment_ID);

	/** Get Loan Installment	  */
	public int getUNS_Loan_Installment_ID();

    /** Column name UNS_Loan_Installment_UU */
    public static final String COLUMNNAME_UNS_Loan_Installment_UU = "UNS_Loan_Installment_UU";

	/** Set UNS_Loan_Installment_UU	  */
	public void setUNS_Loan_Installment_UU (String UNS_Loan_Installment_UU);

	/** Get UNS_Loan_Installment_UU	  */
	public String getUNS_Loan_Installment_UU();

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
