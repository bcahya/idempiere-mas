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

/** Generated Interface for UNS_QAMLab_Result
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_QAMLab_Result 
{

    /** TableName=UNS_QAMLab_Result */
    public static final String Table_Name = "UNS_QAMLab_Result";

    /** AD_Table_ID=1000292 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BatchDocumentNo */
    public static final String COLUMNNAME_BatchDocumentNo = "BatchDocumentNo";

	/** Set Batch Document No.
	  * Document Number of the Batch
	  */
	public void setBatchDocumentNo (String BatchDocumentNo);

	/** Get Batch Document No.
	  * Document Number of the Batch
	  */
	public String getBatchDocumentNo();

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

    /** Column name CodeNo */
    public static final String COLUMNNAME_CodeNo = "CodeNo";

	/** Set Package No.
	  * Bag No / Pallet No / Bottle
	  */
	public void setCodeNo (String CodeNo);

	/** Get Package No.
	  * Bag No / Pallet No / Bottle
	  */
	public String getCodeNo();

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

    /** Column name GenerateTo */
    public static final String COLUMNNAME_GenerateTo = "GenerateTo";

	/** Set Create Result Lines.
	  * Create Result Lines
	  */
	public void setGenerateTo (String GenerateTo);

	/** Get Create Result Lines.
	  * Create Result Lines
	  */
	public String getGenerateTo();

    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

	/** Set Info.
	  * Information
	  */
	public void setInfo (boolean Info);

	/** Get Info.
	  * Information
	  */
	public boolean isInfo();

    /** Column name InspectionDate */
    public static final String COLUMNNAME_InspectionDate = "InspectionDate";

	/** Set Inspection Date	  */
	public void setInspectionDate (Timestamp InspectionDate);

	/** Get Inspection Date	  */
	public Timestamp getInspectionDate();

    /** Column name InspectionType */
    public static final String COLUMNNAME_InspectionType = "InspectionType";

	/** Set Inspection Type	  */
	public void setInspectionType (String InspectionType);

	/** Get Inspection Type	  */
	public String getInspectionType();

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

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

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

	public I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_MovementLine_ID */
    public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	/** Set Move Line.
	  * Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID);

	/** Get Move Line.
	  * Inventory Move document Line
	  */
	public int getM_MovementLine_ID();

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name NC */
    public static final String COLUMNNAME_NC = "NC";

	/** Set Non Conformance	  */
	public void setNC (String NC);

	/** Get Non Conformance	  */
	public String getNC();

    /** Column name NCQty */
    public static final String COLUMNNAME_NCQty = "NCQty";

	/** Set Quantity NC	  */
	public void setNCQty (BigDecimal NCQty);

	/** Get Quantity NC	  */
	public BigDecimal getNCQty();

    /** Column name OnHold */
    public static final String COLUMNNAME_OnHold = "OnHold";

	/** Set Hold	  */
	public void setOnHold (String OnHold);

	/** Get Hold	  */
	public String getOnHold();

    /** Column name OnHoldQty */
    public static final String COLUMNNAME_OnHoldQty = "OnHoldQty";

	/** Set Quantity OnHold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty);

	/** Get Quantity OnHold	  */
	public BigDecimal getOnHoldQty();

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

    /** Column name ProductionDate */
    public static final String COLUMNNAME_ProductionDate = "ProductionDate";

	/** Set Production Date	  */
	public void setProductionDate (Timestamp ProductionDate);

	/** Get Production Date	  */
	public Timestamp getProductionDate();

    /** Column name QAStatus */
    public static final String COLUMNNAME_QAStatus = "QAStatus";

	/** Set QA Status	  */
	public void setQAStatus (String QAStatus);

	/** Get QA Status	  */
	public String getQAStatus();

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

    /** Column name Release */
    public static final String COLUMNNAME_Release = "Release";

	/** Set Release	  */
	public void setRelease (String Release);

	/** Get Release	  */
	public String getRelease();

    /** Column name ReleaseQty */
    public static final String COLUMNNAME_ReleaseQty = "ReleaseQty";

	/** Set Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty);

	/** Get Quantity Release	  */
	public BigDecimal getReleaseQty();

    /** Column name Remark */
    public static final String COLUMNNAME_Remark = "Remark";

	/** Set Remark	  */
	public void setRemark (String Remark);

	/** Get Remark	  */
	public String getRemark();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Person In Charge	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Person In Charge	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_QAMLab_Result_ID */
    public static final String COLUMNNAME_UNS_QAMLab_Result_ID = "UNS_QAMLab_Result_ID";

	/** Set Laboratory Result	  */
	public void setUNS_QAMLab_Result_ID (int UNS_QAMLab_Result_ID);

	/** Get Laboratory Result	  */
	public int getUNS_QAMLab_Result_ID();

    /** Column name UNS_QAMLab_Result_UU */
    public static final String COLUMNNAME_UNS_QAMLab_Result_UU = "UNS_QAMLab_Result_UU";

	/** Set UNS_QAMLab_Result_UU	  */
	public void setUNS_QAMLab_Result_UU (String UNS_QAMLab_Result_UU);

	/** Get UNS_QAMLab_Result_UU	  */
	public String getUNS_QAMLab_Result_UU();

    /** Column name UNS_QAMLab_Summary_ID */
    public static final String COLUMNNAME_UNS_QAMLab_Summary_ID = "UNS_QAMLab_Summary_ID";

	/** Set Monitoring Laboratory Summary	  */
	public void setUNS_QAMLab_Summary_ID (int UNS_QAMLab_Summary_ID);

	/** Get Monitoring Laboratory Summary	  */
	public int getUNS_QAMLab_Summary_ID();

	public com.uns.qad.model.I_UNS_QAMLab_Summary getUNS_QAMLab_Summary() throws RuntimeException;

    /** Column name UNS_QAMonitoringLab_ID */
    public static final String COLUMNNAME_UNS_QAMonitoringLab_ID = "UNS_QAMonitoringLab_ID";

	/** Set Monitoring Laboratory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID);

	/** Get Monitoring Laboratory	  */
	public int getUNS_QAMonitoringLab_ID();

	public com.uns.qad.model.I_UNS_QAMonitoringLab getUNS_QAMonitoringLab() throws RuntimeException;

    /** Column name UNS_QAStatus_PRCLine_ID */
    public static final String COLUMNNAME_UNS_QAStatus_PRCLine_ID = "UNS_QAStatus_PRCLine_ID";

	/** Set Production Inspection Line	  */
	public void setUNS_QAStatus_PRCLine_ID (int UNS_QAStatus_PRCLine_ID);

	/** Get Production Inspection Line	  */
	public int getUNS_QAStatus_PRCLine_ID();

	public com.uns.qad.model.I_UNS_QAStatus_PRCLine getUNS_QAStatus_PRCLine() throws RuntimeException;

    /** Column name UNS_WO_Request_ID */
    public static final String COLUMNNAME_UNS_WO_Request_ID = "UNS_WO_Request_ID";

	/** Set WO Request	  */
	public void setUNS_WO_Request_ID (int UNS_WO_Request_ID);

	/** Get WO Request	  */
	public int getUNS_WO_Request_ID();

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
