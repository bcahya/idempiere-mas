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

/** Generated Interface for UNS_VATRegisteredSequences
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VATRegisteredSequences 
{

    /** TableName=UNS_VATRegisteredSequences */
    public static final String Table_Name = "UNS_VATRegisteredSequences";

    /** AD_Table_ID=1000214 */
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

    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

	/** Set Date received.
	  * Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived);

	/** Get Date received.
	  * Date a product was received
	  */
	public Timestamp getDateReceived();

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

    /** Column name PrefixNo */
    public static final String COLUMNNAME_PrefixNo = "PrefixNo";

	/** Set PrefixNo	  */
	public void setPrefixNo (String PrefixNo);

	/** Get PrefixNo	  */
	public String getPrefixNo();

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

    /** Column name ReplacementPrefix */
    public static final String COLUMNNAME_ReplacementPrefix = "ReplacementPrefix";

	/** Set Replacement Prefix	  */
	public void setReplacementPrefix (String ReplacementPrefix);

	/** Get Replacement Prefix	  */
	public String getReplacementPrefix();

    /** Column name Requested_By */
    public static final String COLUMNNAME_Requested_By = "Requested_By";

	/** Set Requested_By	  */
	public void setRequested_By (String Requested_By);

	/** Get Requested_By	  */
	public String getRequested_By();

    /** Column name RequestedBy_ID */
    public static final String COLUMNNAME_RequestedBy_ID = "RequestedBy_ID";

	/** Set RequestedBy_ID	  */
	public void setRequestedBy_ID (int RequestedBy_ID);

	/** Get RequestedBy_ID	  */
	public int getRequestedBy_ID();

    /** Column name SequenceEndNo */
    public static final String COLUMNNAME_SequenceEndNo = "SequenceEndNo";

	/** Set SequenceEndNo	  */
	public void setSequenceEndNo (int SequenceEndNo);

	/** Get SequenceEndNo	  */
	public int getSequenceEndNo();

    /** Column name SequenceStartNo */
    public static final String COLUMNNAME_SequenceStartNo = "SequenceStartNo";

	/** Set SequenceStartNo	  */
	public void setSequenceStartNo (int SequenceStartNo);

	/** Get SequenceStartNo	  */
	public int getSequenceStartNo();

    /** Column name TotalNumbers */
    public static final String COLUMNNAME_TotalNumbers = "TotalNumbers";

	/** Set TotalNumbers	  */
	public void setTotalNumbers (int TotalNumbers);

	/** Get TotalNumbers	  */
	public int getTotalNumbers();

    /** Column name UNS_VATRegisteredSequences_ID */
    public static final String COLUMNNAME_UNS_VATRegisteredSequences_ID = "UNS_VATRegisteredSequences_ID";

	/** Set VAT Registered Sequences	  */
	public void setUNS_VATRegisteredSequences_ID (int UNS_VATRegisteredSequences_ID);

	/** Get VAT Registered Sequences	  */
	public int getUNS_VATRegisteredSequences_ID();

    /** Column name UNS_VATRegisteredSequences_UU */
    public static final String COLUMNNAME_UNS_VATRegisteredSequences_UU = "UNS_VATRegisteredSequences_UU";

	/** Set UNS_VATRegisteredSequences_UU	  */
	public void setUNS_VATRegisteredSequences_UU (String UNS_VATRegisteredSequences_UU);

	/** Get UNS_VATRegisteredSequences_UU	  */
	public String getUNS_VATRegisteredSequences_UU();

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

    /** Column name UsedNumber */
    public static final String COLUMNNAME_UsedNumber = "UsedNumber";

	/** Set UsedNumber	  */
	public void setUsedNumber (int UsedNumber);

	/** Get UsedNumber	  */
	public int getUsedNumber();
}
