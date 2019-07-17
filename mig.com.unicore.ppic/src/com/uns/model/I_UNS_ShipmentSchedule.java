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

/** Generated Interface for UNS_ShipmentSchedule
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_ShipmentSchedule 
{

    /** TableName=UNS_ShipmentSchedule */
    public static final String Table_Name = "UNS_ShipmentSchedule";

    /** AD_Table_ID=1000109 */
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

    /** Column name ChangeType */
    public static final String COLUMNNAME_ChangeType = "ChangeType";

	/** Set Change Container	  */
	public void setChangeType (String ChangeType);

	/** Get Change Container	  */
	public String getChangeType();

    /** Column name CheckAvailability */
    public static final String COLUMNNAME_CheckAvailability = "CheckAvailability";

	/** Set Check Availability.
	  * Process to check things that must be available for document processing
	  */
	public void setCheckAvailability (String CheckAvailability);

	/** Get Check Availability.
	  * Process to check things that must be available for document processing
	  */
	public String getCheckAvailability();

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException;

    /** Column name ConfirmationStatus */
    public static final String COLUMNNAME_ConfirmationStatus = "ConfirmationStatus";

	/** Set ConfirmationStatus.
	  * Confirmation Status
	  */
	public void setConfirmationStatus (String ConfirmationStatus);

	/** Get ConfirmationStatus.
	  * Confirmation Status
	  */
	public String getConfirmationStatus();

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

    /** Column name CreateInvoice */
    public static final String COLUMNNAME_CreateInvoice = "CreateInvoice";

	/** Set Create Invoice	  */
	public void setCreateInvoice (boolean CreateInvoice);

	/** Get Create Invoice	  */
	public boolean isCreateInvoice();

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

    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/** Set Drop Shipment Partner.
	  * Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/** Get Drop Shipment Partner.
	  * Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID();

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException;

    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/** Set Drop Shipment Location.
	  * Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/** Get Drop Shipment Location.
	  * Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException;

    /** Column name ETA */
    public static final String COLUMNNAME_ETA = "ETA";

	/** Set ETA.
	  * Estimation Time Arived
	  */
	public void setETA (Timestamp ETA);

	/** Get ETA.
	  * Estimation Time Arived
	  */
	public Timestamp getETA();

    /** Column name ETD */
    public static final String COLUMNNAME_ETD = "ETD";

	/** Set ETD.
	  * Estimation Time Departure
	  */
	public void setETD (Timestamp ETD);

	/** Get ETD.
	  * Estimation Time Departure
	  */
	public Timestamp getETD();

    /** Column name InvoicingDept_ID */
    public static final String COLUMNNAME_InvoicingDept_ID = "InvoicingDept_ID";

	/** Set Invoice Mgt Department	  */
	public void setInvoicingDept_ID (int InvoicingDept_ID);

	/** Get Invoice Mgt Department	  */
	public int getInvoicingDept_ID();

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

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name ShipReceiveConfirmDept_ID */
    public static final String COLUMNNAME_ShipReceiveConfirmDept_ID = "ShipReceiveConfirmDept_ID";

	/** Set Ship/Receive Confirm Department.
	  * Ship/Receive Confirm Department
	  */
	public void setShipReceiveConfirmDept_ID (int ShipReceiveConfirmDept_ID);

	/** Get Ship/Receive Confirm Department.
	  * Ship/Receive Confirm Department
	  */
	public int getShipReceiveConfirmDept_ID();

    /** Column name ShipReceiveDept_ID */
    public static final String COLUMNNAME_ShipReceiveDept_ID = "ShipReceiveDept_ID";

	/** Set Ship/Receive Department.
	  * Ship/Receive Department
	  */
	public void setShipReceiveDept_ID (int ShipReceiveDept_ID);

	/** Get Ship/Receive Department.
	  * Ship/Receive Department
	  */
	public int getShipReceiveDept_ID();

    /** Column name UNS_Billing_ID */
    public static final String COLUMNNAME_UNS_Billing_ID = "UNS_Billing_ID";

	/** Set Billing Reff	  */
	public void setUNS_Billing_ID (int UNS_Billing_ID);

	/** Get Billing Reff	  */
	public int getUNS_Billing_ID();

    /** Column name UNS_Kapal_ID */
    public static final String COLUMNNAME_UNS_Kapal_ID = "UNS_Kapal_ID";

	/** Set Barge	  */
	public void setUNS_Kapal_ID (int UNS_Kapal_ID);

	/** Get Barge	  */
	public int getUNS_Kapal_ID();

    /** Column name UNS_QAContainerInspection_ID */
    public static final String COLUMNNAME_UNS_QAContainerInspection_ID = "UNS_QAContainerInspection_ID";

	/** Set Container Inspection	  */
	public void setUNS_QAContainerInspection_ID (int UNS_QAContainerInspection_ID);

	/** Get Container Inspection	  */
	public int getUNS_QAContainerInspection_ID();

    /** Column name UNS_ShipmentSchedule_ID */
    public static final String COLUMNNAME_UNS_ShipmentSchedule_ID = "UNS_ShipmentSchedule_ID";

	/** Set Shipment Arragement	  */
	public void setUNS_ShipmentSchedule_ID (int UNS_ShipmentSchedule_ID);

	/** Get Shipment Arragement	  */
	public int getUNS_ShipmentSchedule_ID();

    /** Column name UNS_ShipmentSchedule_UU */
    public static final String COLUMNNAME_UNS_ShipmentSchedule_UU = "UNS_ShipmentSchedule_UU";

	/** Set UNS_ShipmentSchedule_UU	  */
	public void setUNS_ShipmentSchedule_UU (String UNS_ShipmentSchedule_UU);

	/** Get UNS_ShipmentSchedule_UU	  */
	public String getUNS_ShipmentSchedule_UU();

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

    /** Column name Voyage */
    public static final String COLUMNNAME_Voyage = "Voyage";

	/** Set Voyage	  */
	public void setVoyage (String Voyage);

	/** Get Voyage	  */
	public String getVoyage();
}
