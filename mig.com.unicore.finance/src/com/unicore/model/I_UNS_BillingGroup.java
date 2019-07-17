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

/** Generated Interface for UNS_BillingGroup
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_BillingGroup 
{

    /** TableName=UNS_BillingGroup */
    public static final String Table_Name = "UNS_BillingGroup";

    /** AD_Table_ID=1000180 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name ConsolidateDocument */
    public static final String COLUMNNAME_ConsolidateDocument = "ConsolidateDocument";

	/** Set Consolidate to one Document.
	  * Consolidate Lines into one Document
	  */
	public void setConsolidateDocument (boolean ConsolidateDocument);

	/** Get Consolidate to one Document.
	  * Consolidate Lines into one Document
	  */
	public boolean isConsolidateDocument();

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

    /** Column name GenerateBillingLine */
    public static final String COLUMNNAME_GenerateBillingLine = "GenerateBillingLine";

	/** Set Create Invoice Billing	  */
	public void setGenerateBillingLine (String GenerateBillingLine);

	/** Get Create Invoice Billing	  */
	public String getGenerateBillingLine();

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

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount.
	  * Total Paid Amount of Document
	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount.
	  * Total Paid Amount of Document
	  */
	public BigDecimal getPaidAmt();

    /** Column name PrintDaftarFakturPotongReDis */
    public static final String COLUMNNAME_PrintDaftarFakturPotongReDis = "PrintDaftarFakturPotongReDis";

	/** Set Print Daftar Faktur Potong Retur/Diskon	  */
	public void setPrintDaftarFakturPotongReDis (String PrintDaftarFakturPotongReDis);

	/** Get Print Daftar Faktur Potong Retur/Diskon	  */
	public String getPrintDaftarFakturPotongReDis();

    /** Column name PrintDaftarTagihTukarGuling */
    public static final String COLUMNNAME_PrintDaftarTagihTukarGuling = "PrintDaftarTagihTukarGuling";

	/** Set Print Daftar Tagihan Tukar Guling	  */
	public void setPrintDaftarTagihTukarGuling (String PrintDaftarTagihTukarGuling);

	/** Get Print Daftar Tagihan Tukar Guling	  */
	public String getPrintDaftarTagihTukarGuling();

    /** Column name PrintFakturPajak */
    public static final String COLUMNNAME_PrintFakturPajak = "PrintFakturPajak";

	/** Set Print Faktur Pajak	  */
	public void setPrintFakturPajak (String PrintFakturPajak);

	/** Get Print Faktur Pajak	  */
	public String getPrintFakturPajak();

    /** Column name PrintSJPenagihan */
    public static final String COLUMNNAME_PrintSJPenagihan = "PrintSJPenagihan";

	/** Set Print Surat Jalan Penagihan	  */
	public void setPrintSJPenagihan (String PrintSJPenagihan);

	/** Get Print Surat Jalan Penagihan	  */
	public String getPrintSJPenagihan();

    /** Column name PrintTTFaktur */
    public static final String COLUMNNAME_PrintTTFaktur = "PrintTTFaktur";

	/** Set Print Tanda Titip Faktur	  */
	public void setPrintTTFaktur (String PrintTTFaktur);

	/** Get Print Tanda Titip Faktur	  */
	public String getPrintTTFaktur();

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

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name TotalInvoice */
    public static final String COLUMNNAME_TotalInvoice = "TotalInvoice";

	/** Set Total Invoice	  */
	public void setTotalInvoice (int TotalInvoice);

	/** Get Total Invoice	  */
	public int getTotalInvoice();

    /** Column name UNS_BillingGroup_ID */
    public static final String COLUMNNAME_UNS_BillingGroup_ID = "UNS_BillingGroup_ID";

	/** Set Grouping Billing.
	  * Grouping Billing by parameter
	  */
	public void setUNS_BillingGroup_ID (int UNS_BillingGroup_ID);

	/** Get Grouping Billing.
	  * Grouping Billing by parameter
	  */
	public int getUNS_BillingGroup_ID();

    /** Column name UNS_BillingGroup_UU */
    public static final String COLUMNNAME_UNS_BillingGroup_UU = "UNS_BillingGroup_UU";

	/** Set UNS_BillingGroup_UU	  */
	public void setUNS_BillingGroup_UU (String UNS_BillingGroup_UU);

	/** Get UNS_BillingGroup_UU	  */
	public String getUNS_BillingGroup_UU();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

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
