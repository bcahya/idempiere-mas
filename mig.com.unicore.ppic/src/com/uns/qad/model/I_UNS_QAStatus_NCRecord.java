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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QAStatus_NCRecord
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_QAStatus_NCRecord 
{

    /** TableName=UNS_QAStatus_NCRecord */
    public static final String Table_Name = "UNS_QAStatus_NCRecord";

    /** AD_Table_ID=1000243 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name ActByDT */
    public static final String COLUMNNAME_ActByDT = "ActByDT";

	/** Set Time	  */
	public void setActByDT (Timestamp ActByDT);

	/** Get Time	  */
	public Timestamp getActByDT();

    /** Column name ActByName */
    public static final String COLUMNNAME_ActByName = "ActByName";

	/** Set Name	  */
	public void setActByName (String ActByName);

	/** Get Name	  */
	public String getActByName();

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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Department.
	  * Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Department.
	  * Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException;

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set Notification to.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get Notification to.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

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

    /** Column name CartonSequence */
    public static final String COLUMNNAME_CartonSequence = "CartonSequence";

	/** Set Package Sequence.
	  * Carton or Package Number Sequence
	  */
	public void setCartonSequence (String CartonSequence);

	/** Get Package Sequence.
	  * Carton or Package Number Sequence
	  */
	public String getCartonSequence();

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

    /** Column name CreateMovement */
    public static final String COLUMNNAME_CreateMovement = "CreateMovement";

	/** Set Create Movement.
	  * Automatic created movemet to Trx Department.
	  */
	public void setCreateMovement (boolean CreateMovement);

	/** Get Create Movement.
	  * Automatic created movemet to Trx Department.
	  */
	public boolean isCreateMovement();

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

    /** Column name EndTime */
    public static final String COLUMNNAME_EndTime = "EndTime";

	/** Set End Time.
	  * End of the time span
	  */
	public void setEndTime (Timestamp EndTime);

	/** Get End Time.
	  * End of the time span
	  */
	public Timestamp getEndTime();

    /** Column name FromPRC */
    public static final String COLUMNNAME_FromPRC = "FromPRC";

	/** Set PRC.
	  * This identification from PRC or not
	  */
	public void setFromPRC (boolean FromPRC);

	/** Get PRC.
	  * This identification from PRC or not
	  */
	public boolean isFromPRC();

    /** Column name InformedBy */
    public static final String COLUMNNAME_InformedBy = "InformedBy";

	/** Set Informed By	  */
	public void setInformedBy (String InformedBy);

	/** Get Informed By	  */
	public String getInformedBy();

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

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name PDAStatus */
    public static final String COLUMNNAME_PDAStatus = "PDAStatus";

	/** Set PDA Status	  */
	public void setPDAStatus (String PDAStatus);

	/** Get PDA Status	  */
	public String getPDAStatus();

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

    /** Column name ProposedDispositionAction */
    public static final String COLUMNNAME_ProposedDispositionAction = "ProposedDispositionAction";

	/** Set PDA Description	  */
	public void setProposedDispositionAction (String ProposedDispositionAction);

	/** Get PDA Description	  */
	public String getProposedDispositionAction();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name ReceivedBy */
    public static final String COLUMNNAME_ReceivedBy = "ReceivedBy";

	/** Set Received By	  */
	public void setReceivedBy (String ReceivedBy);

	/** Get Received By	  */
	public String getReceivedBy();

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (String Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public String getResult();

    /** Column name StartTime */
    public static final String COLUMNNAME_StartTime = "StartTime";

	/** Set Start Time.
	  * Time started
	  */
	public void setStartTime (Timestamp StartTime);

	/** Get Start Time.
	  * Time started
	  */
	public Timestamp getStartTime();

    /** Column name UNS_QAStatus_InOutLine_ID */
    public static final String COLUMNNAME_UNS_QAStatus_InOutLine_ID = "UNS_QAStatus_InOutLine_ID";

	/** Set Ship/Receipt Inspection Line	  */
	public void setUNS_QAStatus_InOutLine_ID (int UNS_QAStatus_InOutLine_ID);

	/** Get Ship/Receipt Inspection Line	  */
	public int getUNS_QAStatus_InOutLine_ID();

	public com.uns.qad.model.I_UNS_QAStatus_InOutLine getUNS_QAStatus_InOutLine() throws RuntimeException;

    /** Column name UNS_QAStatus_NCRecord_ID */
    public static final String COLUMNNAME_UNS_QAStatus_NCRecord_ID = "UNS_QAStatus_NCRecord_ID";

	/** Set Non-Conformance Record	  */
	public void setUNS_QAStatus_NCRecord_ID (int UNS_QAStatus_NCRecord_ID);

	/** Get Non-Conformance Record	  */
	public int getUNS_QAStatus_NCRecord_ID();

    /** Column name UNS_QAStatus_NCRecord_UU */
    public static final String COLUMNNAME_UNS_QAStatus_NCRecord_UU = "UNS_QAStatus_NCRecord_UU";

	/** Set UNS_QAStatus_NCRecord_UU	  */
	public void setUNS_QAStatus_NCRecord_UU (String UNS_QAStatus_NCRecord_UU);

	/** Get UNS_QAStatus_NCRecord_UU	  */
	public String getUNS_QAStatus_NCRecord_UU();

    /** Column name UNS_QAStatus_PRCLine_ID */
    public static final String COLUMNNAME_UNS_QAStatus_PRCLine_ID = "UNS_QAStatus_PRCLine_ID";

	/** Set Production Inspection Line	  */
	public void setUNS_QAStatus_PRCLine_ID (int UNS_QAStatus_PRCLine_ID);

	/** Get Production Inspection Line	  */
	public int getUNS_QAStatus_PRCLine_ID();

	public com.uns.qad.model.I_UNS_QAStatus_PRCLine getUNS_QAStatus_PRCLine() throws RuntimeException;

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

    /** Column name UseMaterials */
    public static final String COLUMNNAME_UseMaterials = "UseMaterials";

	/** Set Use Materials.
	  * Currently used material with created internal material use.
	  */
	public void setUseMaterials (boolean UseMaterials);

	/** Get Use Materials.
	  * Currently used material with created internal material use.
	  */
	public boolean isUseMaterials();

    /** Column name VrfByDT */
    public static final String COLUMNNAME_VrfByDT = "VrfByDT";

	/** Set Time	  */
	public void setVrfByDT (Timestamp VrfByDT);

	/** Get Time	  */
	public Timestamp getVrfByDT();

    /** Column name VrfByName */
    public static final String COLUMNNAME_VrfByName = "VrfByName";

	/** Set Name	  */
	public void setVrfByName (String VrfByName);

	/** Get Name	  */
	public String getVrfByName();
}
