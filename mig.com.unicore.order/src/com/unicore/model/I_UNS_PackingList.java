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

/** Generated Interface for UNS_PackingList
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PackingList 
{

    /** TableName=UNS_PackingList */
    public static final String Table_Name = "UNS_PackingList";

    /** AD_Table_ID=1000181 */
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

    /** Column name ConsolidateConfirmation */
    public static final String COLUMNNAME_ConsolidateConfirmation = "ConsolidateConfirmation";

	/** Set Consolidate Confirmation	  */
	public void setConsolidateConfirmation (boolean ConsolidateConfirmation);

	/** Get Consolidate Confirmation	  */
	public boolean isConsolidateConfirmation();

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

    /** Column name CreateFromSalesRep */
    public static final String COLUMNNAME_CreateFromSalesRep = "CreateFromSalesRep";

	/** Set Create From Sales Rep	  */
	public void setCreateFromSalesRep (String CreateFromSalesRep);

	/** Get Create From Sales Rep	  */
	public String getCreateFromSalesRep();

    /** Column name CreatePLFromRayon */
    public static final String COLUMNNAME_CreatePLFromRayon = "CreatePLFromRayon";

	/** Set Create PL From Rayon.
	  * Create Packing List based Rayon Customer
	  */
	public void setCreatePLFromRayon (String CreatePLFromRayon);

	/** Get Create PL From Rayon.
	  * Create Packing List based Rayon Customer
	  */
	public String getCreatePLFromRayon();

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

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name IsCashOrder */
    public static final String COLUMNNAME_IsCashOrder = "IsCashOrder";

	/** Set Cash Order	  */
	public void setIsCashOrder (boolean IsCashOrder);

	/** Get Cash Order	  */
	public boolean isCashOrder();

    /** Column name IsPickup */
    public static final String COLUMNNAME_IsPickup = "IsPickup";

	/** Set Pickup	  */
	public void setIsPickup (boolean IsPickup);

	/** Get Pickup	  */
	public boolean isPickup();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name PPPFPS */
    public static final String COLUMNNAME_PPPFPS = "PPPFPS";

	/** Set Print Penjualan Per Faktur Per Sales	  */
	public void setPPPFPS (String PPPFPS);

	/** Get Print Penjualan Per Faktur Per Sales	  */
	public String getPPPFPS();

    /** Column name PrintBAPengiriman */
    public static final String COLUMNNAME_PrintBAPengiriman = "PrintBAPengiriman";

	/** Set Print Berita Acara Pengiriman	  */
	public void setPrintBAPengiriman (String PrintBAPengiriman);

	/** Get Print Berita Acara Pengiriman	  */
	public String getPrintBAPengiriman();

    /** Column name printBATTF */
    public static final String COLUMNNAME_printBATTF = "printBATTF";

	/** Set Berita Acara Tanda Terima Faktur	  */
	public void setprintBATTF (String printBATTF);

	/** Get Berita Acara Tanda Terima Faktur	  */
	public String getprintBATTF();

    /** Column name PrintBATTRekapBarang */
    public static final String COLUMNNAME_PrintBATTRekapBarang = "PrintBATTRekapBarang";

	/** Set Print Berita Acara Tanda Terima Rekap Barang	  */
	public void setPrintBATTRekapBarang (String PrintBATTRekapBarang);

	/** Get Print Berita Acara Tanda Terima Rekap Barang	  */
	public String getPrintBATTRekapBarang();

    /** Column name PrintDeliveryOrder */
    public static final String COLUMNNAME_PrintDeliveryOrder = "PrintDeliveryOrder";

	/** Set Print Delivery Order	  */
	public void setPrintDeliveryOrder (String PrintDeliveryOrder);

	/** Get Print Delivery Order	  */
	public String getPrintDeliveryOrder();

    /** Column name PrintFPP */
    public static final String COLUMNNAME_PrintFPP = "PrintFPP";

	/** Set Print Faktur Penjualan Per Packing List	  */
	public void setPrintFPP (String PrintFPP);

	/** Get Print Faktur Penjualan Per Packing List	  */
	public String getPrintFPP();

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

    /** Column name Reference_ID */
    public static final String COLUMNNAME_Reference_ID = "Reference_ID";

	/** Set Refrerence Record	  */
	public void setReference_ID (int Reference_ID);

	/** Get Refrerence Record	  */
	public int getReference_ID();

	public com.unicore.model.I_UNS_PackingList getReference() throws RuntimeException;

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name ReOpenDocument */
    public static final String COLUMNNAME_ReOpenDocument = "ReOpenDocument";

	/** Set Re-Open Document	  */
	public void setReOpenDocument (String ReOpenDocument);

	/** Get Re-Open Document	  */
	public String getReOpenDocument();

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

    /** Column name Tonase */
    public static final String COLUMNNAME_Tonase = "Tonase";

	/** Set Tonase.
	  * Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase);

	/** Get Tonase.
	  * Indicate total tonase
	  */
	public BigDecimal getTonase();

    /** Column name UNS_PackingList_ID */
    public static final String COLUMNNAME_UNS_PackingList_ID = "UNS_PackingList_ID";

	/** Set Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID);

	/** Get Packing List	  */
	public int getUNS_PackingList_ID();

    /** Column name UNS_PackingList_UU */
    public static final String COLUMNNAME_UNS_PackingList_UU = "UNS_PackingList_UU";

	/** Set UNS_PackingList_UU	  */
	public void setUNS_PackingList_UU (String UNS_PackingList_UU);

	/** Get UNS_PackingList_UU	  */
	public String getUNS_PackingList_UU();

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

    /** Column name VoidIt */
    public static final String COLUMNNAME_VoidIt = "VoidIt";

	/** Set Void It	  */
	public void setVoidIt (String VoidIt);

	/** Get Void It	  */
	public String getVoidIt();

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();
}
