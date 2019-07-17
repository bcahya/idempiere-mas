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

/** Generated Interface for UNS_VAT
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VAT 
{

    /** TableName=UNS_VAT */
    public static final String Table_Name = "UNS_VAT";

    /** AD_Table_ID=1000343 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name AssignedBy_ID */
    public static final String COLUMNNAME_AssignedBy_ID = "AssignedBy_ID";

	/** Set Assigned By	  */
	public void setAssignedBy_ID (int AssignedBy_ID);

	/** Get Assigned By	  */
	public int getAssignedBy_ID();

	public org.compiere.model.I_AD_User getAssignedBy() throws RuntimeException;

    /** Column name BeforeTaxAmt */
    public static final String COLUMNNAME_BeforeTaxAmt = "BeforeTaxAmt";

	/** Set Before Tax Amount	  */
	public void setBeforeTaxAmt (BigDecimal BeforeTaxAmt);

	/** Get Before Tax Amount	  */
	public BigDecimal getBeforeTaxAmt();

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

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

    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/** Set Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom);

	/** Get Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateReport */
    public static final String COLUMNNAME_DateReport = "DateReport";

	/** Set Report Date.
	  * Expense/Time Report Date
	  */
	public void setDateReport (Timestamp DateReport);

	/** Get Report Date.
	  * Expense/Time Report Date
	  */
	public Timestamp getDateReport();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

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

    /** Column name DifferenceTaxAmt */
    public static final String COLUMNNAME_DifferenceTaxAmt = "DifferenceTaxAmt";

	/** Set Difference Tax Amt	  */
	public void setDifferenceTaxAmt (BigDecimal DifferenceTaxAmt);

	/** Get Difference Tax Amt	  */
	public BigDecimal getDifferenceTaxAmt();

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

    /** Column name ExpFormatExcel */
    public static final String COLUMNNAME_ExpFormatExcel = "ExpFormatExcel";

	/** Set Export Format Excel	  */
	public void setExpFormatExcel (String ExpFormatExcel);

	/** Get Export Format Excel	  */
	public String getExpFormatExcel();

    /** Column name ExpFormatImporter */
    public static final String COLUMNNAME_ExpFormatImporter = "ExpFormatImporter";

	/** Set Export Format Importer	  */
	public void setExpFormatImporter (String ExpFormatImporter);

	/** Get Export Format Importer	  */
	public String getExpFormatImporter();

    /** Column name GenerateTaxInvoiceNo */
    public static final String COLUMNNAME_GenerateTaxInvoiceNo = "GenerateTaxInvoiceNo";

	/** Set Generate Tax Invoice No	  */
	public void setGenerateTaxInvoiceNo (String GenerateTaxInvoiceNo);

	/** Get Generate Tax Invoice No	  */
	public String getGenerateTaxInvoiceNo();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name isCreditMemo */
    public static final String COLUMNNAME_isCreditMemo = "isCreditMemo";

	/** Set Credit Memo.
	  * identification return transaction
	  */
	public void setisCreditMemo (boolean isCreditMemo);

	/** Get Credit Memo.
	  * identification return transaction
	  */
	public boolean isCreditMemo();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

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

    /** Column name RevisionAmt */
    public static final String COLUMNNAME_RevisionAmt = "RevisionAmt";

	/** Set Revision Amount	  */
	public void setRevisionAmt (BigDecimal RevisionAmt);

	/** Get Revision Amount	  */
	public BigDecimal getRevisionAmt();

    /** Column name RevisionBeforeTaxAmt */
    public static final String COLUMNNAME_RevisionBeforeTaxAmt = "RevisionBeforeTaxAmt";

	/** Set Revision Before Tax Amt	  */
	public void setRevisionBeforeTaxAmt (BigDecimal RevisionBeforeTaxAmt);

	/** Get Revision Before Tax Amt	  */
	public BigDecimal getRevisionBeforeTaxAmt();

    /** Column name RevisionTaxAmt */
    public static final String COLUMNNAME_RevisionTaxAmt = "RevisionTaxAmt";

	/** Set Revision Tax Amount	  */
	public void setRevisionTaxAmt (BigDecimal RevisionTaxAmt);

	/** Get Revision Tax Amount	  */
	public BigDecimal getRevisionTaxAmt();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

    /** Column name TotalInvoice */
    public static final String COLUMNNAME_TotalInvoice = "TotalInvoice";

	/** Set Total Invoice	  */
	public void setTotalInvoice (int TotalInvoice);

	/** Get Total Invoice	  */
	public int getTotalInvoice();

    /** Column name UNS_VAT_ID */
    public static final String COLUMNNAME_UNS_VAT_ID = "UNS_VAT_ID";

	/** Set UNS_VAT_ID	  */
	public void setUNS_VAT_ID (int UNS_VAT_ID);

	/** Get UNS_VAT_ID	  */
	public int getUNS_VAT_ID();

    /** Column name UNS_VAT_UU */
    public static final String COLUMNNAME_UNS_VAT_UU = "UNS_VAT_UU";

	/** Set UNS_VAT_UU	  */
	public void setUNS_VAT_UU (String UNS_VAT_UU);

	/** Get UNS_VAT_UU	  */
	public String getUNS_VAT_UU();

    /** Column name UNS_VATPayment_ID */
    public static final String COLUMNNAME_UNS_VATPayment_ID = "UNS_VATPayment_ID";

	/** Set VAT Payment	  */
	public void setUNS_VATPayment_ID (int UNS_VATPayment_ID);

	/** Get VAT Payment	  */
	public int getUNS_VATPayment_ID();

	public I_UNS_VATPayment getUNS_VATPayment() throws RuntimeException;

    /** Column name UpdateAmount */
    public static final String COLUMNNAME_UpdateAmount = "UpdateAmount";

	/** Set Update Amount	  */
	public void setUpdateAmount (String UpdateAmount);

	/** Get Update Amount	  */
	public String getUpdateAmount();

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

    /** Column name UpdateRecord */
    public static final String COLUMNNAME_UpdateRecord = "UpdateRecord";

	/** Set Update Record	  */
	public void setUpdateRecord (String UpdateRecord);

	/** Get Update Record	  */
	public String getUpdateRecord();
}
