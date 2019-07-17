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

/** Generated Interface for UNS_VATDocInOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VATDocInOut 
{

    /** TableName=UNS_VATDocInOut */
    public static final String Table_Name = "UNS_VATDocInOut";

    /** AD_Table_ID=1000209 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name DeliveredBy */
    public static final String COLUMNNAME_DeliveredBy = "DeliveredBy";

	/** Set DeliveredBy.
	  * DeliveredBy
	  */
	public void setDeliveredBy (String DeliveredBy);

	/** Get DeliveredBy.
	  * DeliveredBy
	  */
	public String getDeliveredBy();

    /** Column name DeliveredBy_ID */
    public static final String COLUMNNAME_DeliveredBy_ID = "DeliveredBy_ID";

	/** Set Delivered By	  */
	public void setDeliveredBy_ID (int DeliveredBy_ID);

	/** Get Delivered By	  */
	public int getDeliveredBy_ID();

    /** Column name DeliveredDate */
    public static final String COLUMNNAME_DeliveredDate = "DeliveredDate";

	/** Set DeliveredDate.
	  * Date Of Delivered
	  */
	public void setDeliveredDate (Timestamp DeliveredDate);

	/** Get DeliveredDate.
	  * Date Of Delivered
	  */
	public Timestamp getDeliveredDate();

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

    /** Column name GenerateInvoicePaid */
    public static final String COLUMNNAME_GenerateInvoicePaid = "GenerateInvoicePaid";

	/** Set GenerateInvoicePaid	  */
	public void setGenerateInvoicePaid (String GenerateInvoicePaid);

	/** Get GenerateInvoicePaid	  */
	public String getGenerateInvoicePaid();

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

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name isReplacement */
    public static final String COLUMNNAME_isReplacement = "isReplacement";

	/** Set isReplacement	  */
	public void setisReplacement (boolean isReplacement);

	/** Get isReplacement	  */
	public boolean isReplacement();

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

    /** Column name Notes */
    public static final String COLUMNNAME_Notes = "Notes";

	/** Set Notes	  */
	public void setNotes (String Notes);

	/** Get Notes	  */
	public String getNotes();

    /** Column name PrintedBy */
    public static final String COLUMNNAME_PrintedBy = "PrintedBy";

	/** Set PrintedBy	  */
	public void setPrintedBy (int PrintedBy);

	/** Get PrintedBy	  */
	public int getPrintedBy();

    /** Column name PrintedDate */
    public static final String COLUMNNAME_PrintedDate = "PrintedDate";

	/** Set PrintedDate	  */
	public void setPrintedDate (Timestamp PrintedDate);

	/** Get PrintedDate	  */
	public Timestamp getPrintedDate();

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

    /** Column name ReceiptDate */
    public static final String COLUMNNAME_ReceiptDate = "ReceiptDate";

	/** Set Receipt Date.
	  * Receipt Date
	  */
	public void setReceiptDate (Timestamp ReceiptDate);

	/** Get Receipt Date.
	  * Receipt Date
	  */
	public Timestamp getReceiptDate();

    /** Column name ReceivedBy */
    public static final String COLUMNNAME_ReceivedBy = "ReceivedBy";

	/** Set ReceivedBy	  */
	public void setReceivedBy (String ReceivedBy);

	/** Get ReceivedBy	  */
	public String getReceivedBy();

    /** Column name ReceivedBy_ID */
    public static final String COLUMNNAME_ReceivedBy_ID = "ReceivedBy_ID";

	/** Set Received By	  */
	public void setReceivedBy_ID (int ReceivedBy_ID);

	/** Get Received By	  */
	public int getReceivedBy_ID();

    /** Column name StdVATDocNo */
    public static final String COLUMNNAME_StdVATDocNo = "StdVATDocNo";

	/** Set StdVATDocNo	  */
	public void setStdVATDocNo (String StdVATDocNo);

	/** Get StdVATDocNo	  */
	public String getStdVATDocNo();

    /** Column name UNS_VATDocInOut_ID */
    public static final String COLUMNNAME_UNS_VATDocInOut_ID = "UNS_VATDocInOut_ID";

	/** Set UNS_VATDocInOut	  */
	public void setUNS_VATDocInOut_ID (int UNS_VATDocInOut_ID);

	/** Get UNS_VATDocInOut	  */
	public int getUNS_VATDocInOut_ID();

    /** Column name UNS_VATDocInOut_UU */
    public static final String COLUMNNAME_UNS_VATDocInOut_UU = "UNS_VATDocInOut_UU";

	/** Set UNS_VATDocInOut_UU	  */
	public void setUNS_VATDocInOut_UU (String UNS_VATDocInOut_UU);

	/** Get UNS_VATDocInOut_UU	  */
	public String getUNS_VATDocInOut_UU();

    /** Column name UNS_VATDocNo_ID */
    public static final String COLUMNNAME_UNS_VATDocNo_ID = "UNS_VATDocNo_ID";

	/** Set UNS_VATDocNo_ID	  */
	public void setUNS_VATDocNo_ID (int UNS_VATDocNo_ID);

	/** Get UNS_VATDocNo_ID	  */
	public int getUNS_VATDocNo_ID();

	public com.unicore.model.I_UNS_VATDocNo getUNS_VATDocNo() throws RuntimeException;

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
