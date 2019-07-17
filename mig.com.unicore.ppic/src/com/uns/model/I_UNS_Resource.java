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

/** Generated Interface for UNS_Resource
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Resource 
{

    /** TableName=UNS_Resource */
    public static final String Table_Name = "UNS_Resource";

    /** AD_Table_ID=1000039 */
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

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (String CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public String getCopyFrom();

    /** Column name CopyLines */
    public static final String COLUMNNAME_CopyLines = "CopyLines";

	/** Set Copy Lines	  */
	public void setCopyLines (String CopyLines);

	/** Get Copy Lines	  */
	public String getCopyLines();

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

    /** Column name GenerateInOut */
    public static final String COLUMNNAME_GenerateInOut = "GenerateInOut";

	/** Set Generate Input/Output	  */
	public void setGenerateInOut (String GenerateInOut);

	/** Get Generate Input/Output	  */
	public String getGenerateInOut();

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

    /** Column name IsEndingStockBase */
    public static final String COLUMNNAME_IsEndingStockBase = "IsEndingStockBase";

	/** Set Ending Stock Base	  */
	public void setIsEndingStockBase (boolean IsEndingStockBase);

	/** Get Ending Stock Base	  */
	public boolean isEndingStockBase();

    /** Column name IsParentEndNode */
    public static final String COLUMNNAME_IsParentEndNode = "IsParentEndNode";

	/** Set Is Parent's End Node.
	  * This is to indicate if this resource is parent's end node.
	  */
	public void setIsParentEndNode (boolean IsParentEndNode);

	/** Get Is Parent's End Node.
	  * This is to indicate if this resource is parent's end node.
	  */
	public boolean isParentEndNode();

    /** Column name IsParentStartNode */
    public static final String COLUMNNAME_IsParentStartNode = "IsParentStartNode";

	/** Set Is Parent's Start Node.
	  * This is to indicate if this resource is parent's start node.
	  */
	public void setIsParentStartNode (boolean IsParentStartNode);

	/** Get Is Parent's Start Node.
	  * This is to indicate if this resource is parent's start node.
	  */
	public boolean isParentStartNode();

    /** Column name IsRework */
    public static final String COLUMNNAME_IsRework = "IsRework";

	/** Set Is Rework.
	  * This resource's main job is considered as rework (reprocess)
	  */
	public void setIsRework (boolean IsRework);

	/** Get Is Rework.
	  * This resource's main job is considered as rework (reprocess)
	  */
	public boolean isRework();

    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/** Set Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/** Get Is Subcontracting	  */
	public boolean isSubcontracting();

    /** Column name IsWorkerBase */
    public static final String COLUMNNAME_IsWorkerBase = "IsWorkerBase";

	/** Set Worker Base	  */
	public void setIsWorkerBase (boolean IsWorkerBase);

	/** Get Worker Base	  */
	public boolean isWorkerBase();

    /** Column name MaxMachineSuprv1 */
    public static final String COLUMNNAME_MaxMachineSuprv1 = "MaxMachineSuprv1";

	/** Set Max Machine Suprv 1	  */
	public void setMaxMachineSuprv1 (int MaxMachineSuprv1);

	/** Get Max Machine Suprv 1	  */
	public int getMaxMachineSuprv1();

    /** Column name MaxMachineSuprv2 */
    public static final String COLUMNNAME_MaxMachineSuprv2 = "MaxMachineSuprv2";

	/** Set Max Machine Suprv 2	  */
	public void setMaxMachineSuprv2 (int MaxMachineSuprv2);

	/** Get Max Machine Suprv 2	  */
	public int getMaxMachineSuprv2();

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

    /** Column name PaymentMethod */
    public static final String COLUMNNAME_PaymentMethod = "PaymentMethod";

	/** Set Result Method	  */
	public void setPaymentMethod (String PaymentMethod);

	/** Get Result Method	  */
	public String getPaymentMethod();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

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

    /** Column name ResetMRPFGBOM */
    public static final String COLUMNNAME_ResetMRPFGBOM = "ResetMRPFGBOM";

	/** Set Reset MRP FG BOM	  */
	public void setResetMRPFGBOM (String ResetMRPFGBOM);

	/** Get Reset MRP FG BOM	  */
	public String getResetMRPFGBOM();

    /** Column name ResourceParent_ID */
    public static final String COLUMNNAME_ResourceParent_ID = "ResourceParent_ID";

	/** Set Resource Parent.
	  * Parent of Entity
	  */
	public void setResourceParent_ID (int ResourceParent_ID);

	/** Get Resource Parent.
	  * Parent of Entity
	  */
	public int getResourceParent_ID();

	public com.uns.model.I_UNS_Resource getResourceParent() throws RuntimeException;

    /** Column name ResourceType */
    public static final String COLUMNNAME_ResourceType = "ResourceType";

	/** Set Resource Type	  */
	public void setResourceType (String ResourceType);

	/** Get Resource Type	  */
	public String getResourceType();

    /** Column name Supervisor1_ID */
    public static final String COLUMNNAME_Supervisor1_ID = "Supervisor1_ID";

	/** Set Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor1_ID (int Supervisor1_ID);

	/** Get Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor1_ID();

    /** Column name Supervisor2a_ID */
    public static final String COLUMNNAME_Supervisor2a_ID = "Supervisor2a_ID";

	/** Set Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2a_ID (int Supervisor2a_ID);

	/** Get Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2a_ID();

    /** Column name Supervisor2b_ID */
    public static final String COLUMNNAME_Supervisor2b_ID = "Supervisor2b_ID";

	/** Set Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2b_ID (int Supervisor2b_ID);

	/** Get Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2b_ID();

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

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

    /** Column name UNS_Resource_UU */
    public static final String COLUMNNAME_UNS_Resource_UU = "UNS_Resource_UU";

	/** Set UNS_Resource_UU	  */
	public void setUNS_Resource_UU (String UNS_Resource_UU);

	/** Get UNS_Resource_UU	  */
	public String getUNS_Resource_UU();

    /** Column name UNS_SlotType_ID */
    public static final String COLUMNNAME_UNS_SlotType_ID = "UNS_SlotType_ID";

	/** Set Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID);

	/** Get Slot Type	  */
	public int getUNS_SlotType_ID();

	public com.uns.model.I_UNS_SlotType getUNS_SlotType() throws RuntimeException;

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}