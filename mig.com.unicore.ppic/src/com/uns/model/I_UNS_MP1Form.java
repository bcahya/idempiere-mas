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

/** Generated Interface for UNS_MP1Form
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_MP1Form 
{

    /** TableName=UNS_MP1Form */
    public static final String Table_Name = "UNS_MP1Form";

    /** AD_Table_ID=1000392 */
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

    /** Column name AD_OrgTo_ID */
    public static final String COLUMNNAME_AD_OrgTo_ID = "AD_OrgTo_ID";

	/** Set Inter-Department.
	  * Departmention valid for intercompany documents
	  */
	public void setAD_OrgTo_ID (int AD_OrgTo_ID);

	/** Get Inter-Department.
	  * Departmention valid for intercompany documents
	  */
	public int getAD_OrgTo_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTo() throws RuntimeException;

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

    /** Column name IsCreated */
    public static final String COLUMNNAME_IsCreated = "IsCreated";

	/** Set Records created	  */
	public void setIsCreated (boolean IsCreated);

	/** Get Records created	  */
	public boolean isCreated();

    /** Column name IsKBPecahSegar */
    public static final String COLUMNNAME_IsKBPecahSegar = "IsKBPecahSegar";

	/** Set KB Pecah Segar	  */
	public void setIsKBPecahSegar (boolean IsKBPecahSegar);

	/** Get KB Pecah Segar	  */
	public boolean isKBPecahSegar();

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

    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/** Set Inventory Move.
	  * Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID);

	/** Get Inventory Move.
	  * Movement of Inventory
	  */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException;

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

    /** Column name OpenFormInput */
    public static final String COLUMNNAME_OpenFormInput = "OpenFormInput";

	/** Set Open/View Form Input.
	  * Open the form to input this document details in one page
	  */
	public void setOpenFormInput (String OpenFormInput);

	/** Get Open/View Form Input.
	  * Open the form to input this document details in one page
	  */
	public String getOpenFormInput();

    /** Column name PreReject */
    public static final String COLUMNNAME_PreReject = "PreReject";

	/** Set Pre Reject.
	  * Pre Reject for LKU
	  */
	public void setPreReject (BigDecimal PreReject);

	/** Get Pre Reject.
	  * Pre Reject for LKU
	  */
	public BigDecimal getPreReject();

    /** Column name ProductionDate */
    public static final String COLUMNNAME_ProductionDate = "ProductionDate";

	/** Set Production Date	  */
	public void setProductionDate (Timestamp ProductionDate);

	/** Get Production Date	  */
	public Timestamp getProductionDate();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name Shift */
    public static final String COLUMNNAME_Shift = "Shift";

	/** Set Shift	  */
	public void setShift (String Shift);

	/** Get Shift	  */
	public String getShift();

    /** Column name Supervisor1_ID */
    public static final String COLUMNNAME_Supervisor1_ID = "Supervisor1_ID";

	/** Set Supervisor Sheller 2.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor1_ID (int Supervisor1_ID);

	/** Get Supervisor Sheller 2.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor1_ID();

    /** Column name Supervisor2a_ID */
    public static final String COLUMNNAME_Supervisor2a_ID = "Supervisor2a_ID";

	/** Set Supervisor Parrer 1.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2a_ID (int Supervisor2a_ID);

	/** Get Supervisor Parrer 1.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2a_ID();

    /** Column name Supervisor2b_ID */
    public static final String COLUMNNAME_Supervisor2b_ID = "Supervisor2b_ID";

	/** Set Supervisor Parrer 2.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2b_ID (int Supervisor2b_ID);

	/** Get Supervisor Parrer 2.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2b_ID();

    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/** Set Supervisor Sheller 1.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor_ID (int Supervisor_ID);

	/** Get Supervisor Sheller 1.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID();

    /** Column name TeoritisBKM */
    public static final String COLUMNNAME_TeoritisBKM = "TeoritisBKM";

	/** Set Teoritis Black Meat	  */
	public void setTeoritisBKM (BigDecimal TeoritisBKM);

	/** Get Teoritis Black Meat	  */
	public BigDecimal getTeoritisBKM();

    /** Column name TeoritisBM */
    public static final String COLUMNNAME_TeoritisBM = "TeoritisBM";

	/** Set Teoritis KC Busuk	  */
	public void setTeoritisBM (BigDecimal TeoritisBM);

	/** Get Teoritis KC Busuk	  */
	public BigDecimal getTeoritisBM();

    /** Column name TeoritisRM */
    public static final String COLUMNNAME_TeoritisRM = "TeoritisRM";

	/** Set Teoritis Raw Meat	  */
	public void setTeoritisRM (BigDecimal TeoritisRM);

	/** Get Teoritis Raw Meat	  */
	public BigDecimal getTeoritisRM();

    /** Column name TeoritisSM */
    public static final String COLUMNNAME_TeoritisSM = "TeoritisSM";

	/** Set Teoritis Skin Meat	  */
	public void setTeoritisSM (BigDecimal TeoritisSM);

	/** Get Teoritis Skin Meat	  */
	public BigDecimal getTeoritisSM();

    /** Column name UNS_MP1Form_ID */
    public static final String COLUMNNAME_UNS_MP1Form_ID = "UNS_MP1Form_ID";

	/** Set MP1 Form	  */
	public void setUNS_MP1Form_ID (int UNS_MP1Form_ID);

	/** Get MP1 Form	  */
	public int getUNS_MP1Form_ID();

    /** Column name UNS_MP1Form_UU */
    public static final String COLUMNNAME_UNS_MP1Form_UU = "UNS_MP1Form_UU";

	/** Set UNS_MP1Form_UU	  */
	public void setUNS_MP1Form_UU (String UNS_MP1Form_UU);

	/** Get UNS_MP1Form_UU	  */
	public String getUNS_MP1Form_UU();

    /** Column name UNS_ProductionPayConfig_ID */
    public static final String COLUMNNAME_UNS_ProductionPayConfig_ID = "UNS_ProductionPayConfig_ID";

	/** Set Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID);

	/** Get Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

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
}
