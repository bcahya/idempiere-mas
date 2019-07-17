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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_TransferBalance_Confirm
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_TransferBalance_Confirm 
{

    /** TableName=UNS_TransferBalance_Confirm */
    public static final String Table_Name = "UNS_TransferBalance_Confirm";

    /** AD_Table_ID=1000089 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountFrom_ID */
    public static final String COLUMNNAME_AccountFrom_ID = "AccountFrom_ID";

	/** Set Account From.
	  * The account the transfer requested from
	  */
	public void setAccountFrom_ID (int AccountFrom_ID);

	/** Get Account From.
	  * The account the transfer requested from
	  */
	public int getAccountFrom_ID();

	public org.compiere.model.I_C_BankAccount getAccountFrom() throws RuntimeException;

    /** Column name AccountTo_ID */
    public static final String COLUMNNAME_AccountTo_ID = "AccountTo_ID";

	/** Set Account To.
	  * The account the balance will be transfered to
	  */
	public void setAccountTo_ID (int AccountTo_ID);

	/** Get Account To.
	  * The account the balance will be transfered to
	  */
	public int getAccountTo_ID();

	public org.compiere.model.I_C_BankAccount getAccountTo() throws RuntimeException;

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_OrgFrom_ID */
    public static final String COLUMNNAME_AD_OrgFrom_ID = "AD_OrgFrom_ID";

	/** Set Requested Organization.
	  * The organization/branch/sub-ordinate to be requested
	  */
	public void setAD_OrgFrom_ID (int AD_OrgFrom_ID);

	/** Get Requested Organization.
	  * The organization/branch/sub-ordinate to be requested
	  */
	public int getAD_OrgFrom_ID();

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

    /** Column name AmountConfirmed */
    public static final String COLUMNNAME_AmountConfirmed = "AmountConfirmed";

	/** Set Confirmed Amount	  */
	public void setAmountConfirmed (BigDecimal AmountConfirmed);

	/** Get Confirmed Amount	  */
	public BigDecimal getAmountConfirmed();

    /** Column name AmountRequested */
    public static final String COLUMNNAME_AmountRequested = "AmountRequested";

	/** Set Requested Amount	  */
	public void setAmountRequested (BigDecimal AmountRequested);

	/** Get Requested Amount	  */
	public BigDecimal getAmountRequested();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name CheckNo */
    public static final String COLUMNNAME_CheckNo = "CheckNo";

	/** Set Check No.
	  * Check Number
	  */
	public void setCheckNo (String CheckNo);

	/** Get Check No.
	  * Check Number
	  */
	public String getCheckNo();

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

    /** Column name DateConfirm */
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";

	/** Set Date Confirm.
	  * Date Confirm of this Order
	  */
	public void setDateConfirm (Timestamp DateConfirm);

	/** Get Date Confirm.
	  * Date Confirm of this Order
	  */
	public Timestamp getDateConfirm();

    /** Column name DateRequired */
    public static final String COLUMNNAME_DateRequired = "DateRequired";

	/** Set Date Required.
	  * Date when required
	  */
	public void setDateRequired (Timestamp DateRequired);

	/** Get Date Required.
	  * Date when required
	  */
	public Timestamp getDateRequired();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name PaymentFrom_ID */
    public static final String COLUMNNAME_PaymentFrom_ID = "PaymentFrom_ID";

	/** Set Payment From	  */
	public void setPaymentFrom_ID (int PaymentFrom_ID);

	/** Get Payment From	  */
	public int getPaymentFrom_ID();

	public org.compiere.model.I_C_Payment getPaymentFrom() throws RuntimeException;

    /** Column name PaymentTo_ID */
    public static final String COLUMNNAME_PaymentTo_ID = "PaymentTo_ID";

	/** Set Payment To	  */
	public void setPaymentTo_ID (int PaymentTo_ID);

	/** Get Payment To	  */
	public int getPaymentTo_ID();

	public org.compiere.model.I_C_Payment getPaymentTo() throws RuntimeException;

    /** Column name PrevBalanceAmt */
    public static final String COLUMNNAME_PrevBalanceAmt = "PrevBalanceAmt";

	/** Set Previous Balance	  */
	public void setPrevBalanceAmt (BigDecimal PrevBalanceAmt);

	/** Get Previous Balance	  */
	public BigDecimal getPrevBalanceAmt();

    /** Column name PrintDocument */
    public static final String COLUMNNAME_PrintDocument = "PrintDocument";

	/** Set Print Document	  */
	public void setPrintDocument (String PrintDocument);

	/** Get Print Document	  */
	public String getPrintDocument();

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

    /** Column name RequestType */
    public static final String COLUMNNAME_RequestType = "RequestType";

	/** Set Request Type	  */
	public void setRequestType (String RequestType);

	/** Get Request Type	  */
	public String getRequestType();

    /** Column name UNS_TransferBalance_Confirm_ID */
    public static final String COLUMNNAME_UNS_TransferBalance_Confirm_ID = "UNS_TransferBalance_Confirm_ID";

	/** Set Transfer Balance Confirmation	  */
	public void setUNS_TransferBalance_Confirm_ID (int UNS_TransferBalance_Confirm_ID);

	/** Get Transfer Balance Confirmation	  */
	public int getUNS_TransferBalance_Confirm_ID();

    /** Column name UNS_TransferBalance_Confirm_UU */
    public static final String COLUMNNAME_UNS_TransferBalance_Confirm_UU = "UNS_TransferBalance_Confirm_UU";

	/** Set UNS_TransferBalance_Confirm_UU	  */
	public void setUNS_TransferBalance_Confirm_UU (String UNS_TransferBalance_Confirm_UU);

	/** Get UNS_TransferBalance_Confirm_UU	  */
	public String getUNS_TransferBalance_Confirm_UU();

    /** Column name UNS_TransferBalance_Request_ID */
    public static final String COLUMNNAME_UNS_TransferBalance_Request_ID = "UNS_TransferBalance_Request_ID";

	/** Set Transfer Balance Request	  */
	public void setUNS_TransferBalance_Request_ID (int UNS_TransferBalance_Request_ID);

	/** Get Transfer Balance Request	  */
	public int getUNS_TransferBalance_Request_ID();

	public com.unicore.model.I_UNS_TransferBalance_Request getUNS_TransferBalance_Request() throws RuntimeException;

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
