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

/** Generated Interface for UNS_Production
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Production 
{

    /** TableName=UNS_Production */
    public static final String Table_Name = "UNS_Production";

    /** AD_Table_ID=1000058 */
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

    /** Column name BOMType */
    public static final String COLUMNNAME_BOMType = "BOMType";

	/** Set BOM Type.
	  * Type of BOM
	  */
	public void setBOMType (String BOMType);

	/** Get BOM Type.
	  * Type of BOM
	  */
	public String getBOMType();

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

    /** Column name CreatePSRealization */
    public static final String COLUMNNAME_CreatePSRealization = "CreatePSRealization";

	/** Set Create Realization	  */
	public void setCreatePSRealization (String CreatePSRealization);

	/** Get Create Realization	  */
	public String getCreatePSRealization();

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

    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/** Set Date Promised.
	  * Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised);

	/** Get Date Promised.
	  * Date Order was promised
	  */
	public Timestamp getDatePromised();

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

    /** Column name GenerateOutPlan */
    public static final String COLUMNNAME_GenerateOutPlan = "GenerateOutPlan";

	/** Set Generate Output Plan	  */
	public void setGenerateOutPlan (String GenerateOutPlan);

	/** Get Generate Output Plan	  */
	public String getGenerateOutPlan();

    /** Column name HasStickerInfo */
    public static final String COLUMNNAME_HasStickerInfo = "HasStickerInfo";

	/** Set Has Sticker Info	  */
	public void setHasStickerInfo (boolean HasStickerInfo);

	/** Get Has Sticker Info	  */
	public boolean isHasStickerInfo();

    /** Column name HoursOverTime */
    public static final String COLUMNNAME_HoursOverTime = "HoursOverTime";

	/** Set Overtime (Hours).
	  * The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public void setHoursOverTime (BigDecimal HoursOverTime);

	/** Get Overtime (Hours).
	  * The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public BigDecimal getHoursOverTime();

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

    /** Column name IsComplete */
    public static final String COLUMNNAME_IsComplete = "IsComplete";

	/** Set Complete.
	  * It is complete
	  */
	public void setIsComplete (String IsComplete);

	/** Get Complete.
	  * It is complete
	  */
	public String getIsComplete();

    /** Column name IsCreated */
    public static final String COLUMNNAME_IsCreated = "IsCreated";

	/** Set Records created	  */
	public void setIsCreated (String IsCreated);

	/** Get Records created	  */
	public String getIsCreated();

    /** Column name IsEndingStockBase */
    public static final String COLUMNNAME_IsEndingStockBase = "IsEndingStockBase";

	/** Set Ending Stock Base	  */
	public void setIsEndingStockBase (boolean IsEndingStockBase);

	/** Get Ending Stock Base	  */
	public boolean isEndingStockBase();

    /** Column name IsOverTime */
    public static final String COLUMNNAME_IsOverTime = "IsOverTime";

	/** Set Has Overtime Hours.
	  * If the production has overtime hours, then set it to true (checked)
	  */
	public void setIsOverTime (boolean IsOverTime);

	/** Get Has Overtime Hours.
	  * If the production has overtime hours, then set it to true (checked)
	  */
	public boolean isOverTime();

    /** Column name IsPersonalResult */
    public static final String COLUMNNAME_IsPersonalResult = "IsPersonalResult";

	/** Set Personal Result	  */
	public void setIsPersonalResult (boolean IsPersonalResult);

	/** Get Personal Result	  */
	public boolean isPersonalResult();

    /** Column name IsWorkerBase */
    public static final String COLUMNNAME_IsWorkerBase = "IsWorkerBase";

	/** Set Worker Base	  */
	public void setIsWorkerBase (boolean IsWorkerBase);

	/** Get Worker Base	  */
	public boolean isWorkerBase();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Output Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Output Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product (Output).
	  * The output of product to be produced
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product (Output).
	  * The output of product to be produced
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/** Set Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate);

	/** Get Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate();

    /** Column name MustSyncWithMPS */
    public static final String COLUMNNAME_MustSyncWithMPS = "MustSyncWithMPS";

	/** Set Must Synchronize With MPS	  */
	public void setMustSyncWithMPS (boolean MustSyncWithMPS);

	/** Get Must Synchronize With MPS	  */
	public boolean isMustSyncWithMPS();

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

    /** Column name OutputType */
    public static final String COLUMNNAME_OutputType = "OutputType";

	/** Set Output Type	  */
	public void setOutputType (String OutputType);

	/** Get Output Type	  */
	public String getOutputType();

    /** Column name PSType */
    public static final String COLUMNNAME_PSType = "PSType";

	/** Set Production Type.
	  * The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public void setPSType (String PSType);

	/** Get Production Type.
	  * The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public String getPSType();

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

    /** Column name ProductSticker_ID */
    public static final String COLUMNNAME_ProductSticker_ID = "ProductSticker_ID";

	/** Set Sticker	  */
	public void setProductSticker_ID (int ProductSticker_ID);

	/** Get Sticker	  */
	public int getProductSticker_ID();

	public org.compiere.model.I_M_Product getProductSticker() throws RuntimeException;

    /** Column name ProductionDate */
    public static final String COLUMNNAME_ProductionDate = "ProductionDate";

	/** Set Production Date	  */
	public void setProductionDate (Timestamp ProductionDate);

	/** Get Production Date	  */
	public Timestamp getProductionDate();

    /** Column name ProductionQty */
    public static final String COLUMNNAME_ProductionQty = "ProductionQty";

	/** Set Production Quantity.
	  * Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty);

	/** Get Production Quantity.
	  * Quantity of products to produce
	  */
	public BigDecimal getProductionQty();

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

	public com.uns.model.I_UNS_Production getReversal() throws RuntimeException;

    /** Column name StickerInfo */
    public static final String COLUMNNAME_StickerInfo = "StickerInfo";

	/** Set Sticker Info	  */
	public void setStickerInfo (String StickerInfo);

	/** Get Sticker Info	  */
	public String getStickerInfo();

    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/** Set Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor_ID (int Supervisor_ID);

	/** Get Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID();

    /** Column name UNS_ProductionPayConfig_ID */
    public static final String COLUMNNAME_UNS_ProductionPayConfig_ID = "UNS_ProductionPayConfig_ID";

	/** Set Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID);

	/** Get Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID();

    /** Column name UNS_ProductionSchedule_ID */
    public static final String COLUMNNAME_UNS_ProductionSchedule_ID = "UNS_ProductionSchedule_ID";

	/** Set Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID);

	/** Get Production Schedule	  */
	public int getUNS_ProductionSchedule_ID();

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

    /** Column name UNS_Production_UU */
    public static final String COLUMNNAME_UNS_Production_UU = "UNS_Production_UU";

	/** Set UNS_Production_UU	  */
	public void setUNS_Production_UU (String UNS_Production_UU);

	/** Get UNS_Production_UU	  */
	public String getUNS_Production_UU();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

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

    /** Column name WorkerResultType */
    public static final String COLUMNNAME_WorkerResultType = "WorkerResultType";

	/** Set Worker Result Type	  */
	public void setWorkerResultType (String WorkerResultType);

	/** Get Worker Result Type	  */
	public String getWorkerResultType();
}
