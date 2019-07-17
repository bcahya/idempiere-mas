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

/** Generated Interface for UNS_PackingSlip
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_PackingSlip 
{

    /** TableName=UNS_PackingSlip */
    public static final String Table_Name = "UNS_PackingSlip";

    /** AD_Table_ID=1000059 */
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

    /** Column name ArriveDate */
    public static final String COLUMNNAME_ArriveDate = "ArriveDate";

	/** Set Arrival Date	  */
	public void setArriveDate (Timestamp ArriveDate);

	/** Get Arrival Date	  */
	public Timestamp getArriveDate();

    /** Column name BCDocDate */
    public static final String COLUMNNAME_BCDocDate = "BCDocDate";

	/** Set BC Document Date	  */
	public void setBCDocDate (Timestamp BCDocDate);

	/** Get BC Document Date	  */
	public Timestamp getBCDocDate();

    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/** Set Create Lines.
	  * Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom);

	/** Get Create Lines.
	  * Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom();

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

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Date Ordered.
	  * Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered);

	/** Get Date Ordered.
	  * Date of Order
	  */
	public Timestamp getDateOrdered();

    /** Column name DeptToInvoice_ID */
    public static final String COLUMNNAME_DeptToInvoice_ID = "DeptToInvoice_ID";

	/** Set Dept. To Invoice.
	  * Departemen to handle and assigned to the invoice
	  */
	public void setDeptToInvoice_ID (int DeptToInvoice_ID);

	/** Get Dept. To Invoice.
	  * Departemen to handle and assigned to the invoice
	  */
	public int getDeptToInvoice_ID();

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

    /** Column name GenerateTo */
    public static final String COLUMNNAME_GenerateTo = "GenerateTo";

	/** Set Generate Receipt.
	  * Generate Receipt
	  */
	public void setGenerateTo (String GenerateTo);

	/** Get Generate Receipt.
	  * Generate Receipt
	  */
	public String getGenerateTo();

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

    /** Column name PaymentTerm */
    public static final String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/** Set Payment Term.
	  * Payment Term
	  */
	public void setPaymentTerm (String PaymentTerm);

	/** Get Payment Term.
	  * Payment Term
	  */
	public String getPaymentTerm();

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

    /** Column name SOPOType */
    public static final String COLUMNNAME_SOPOType = "SOPOType";

	/** Set SO/PO Type.
	  * Sales Tax applies to sales situations, Purchase Tax to purchase situations
	  */
	public void setSOPOType (String SOPOType);

	/** Get SO/PO Type.
	  * Sales Tax applies to sales situations, Purchase Tax to purchase situations
	  */
	public String getSOPOType();

    /** Column name ShipDate */
    public static final String COLUMNNAME_ShipDate = "ShipDate";

	/** Set Departure Date.
	  * Shipment Date/Time
	  */
	public void setShipDate (Timestamp ShipDate);

	/** Get Departure Date.
	  * Shipment Date/Time
	  */
	public Timestamp getShipDate();

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

    /** Column name Terms */
    public static final String COLUMNNAME_Terms = "Terms";

	/** Set Terms	  */
	public void setTerms (String Terms);

	/** Get Terms	  */
	public String getTerms();

    /** Column name UNS_BCCode_ID */
    public static final String COLUMNNAME_UNS_BCCode_ID = "UNS_BCCode_ID";

	/** Set Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID);

	/** Get Bea Cukai Code	  */
	public int getUNS_BCCode_ID();

	public com.uns.model.I_UNS_BCCode getUNS_BCCode() throws RuntimeException;

    /** Column name UNS_Kapal_ID */
    public static final String COLUMNNAME_UNS_Kapal_ID = "UNS_Kapal_ID";

	/** Set Kapal	  */
	public void setUNS_Kapal_ID (int UNS_Kapal_ID);

	/** Get Kapal	  */
	public int getUNS_Kapal_ID();

    /** Column name UNS_PackingSlip_ID */
    public static final String COLUMNNAME_UNS_PackingSlip_ID = "UNS_PackingSlip_ID";

	/** Set Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID);

	/** Get Packing Slip	  */
	public int getUNS_PackingSlip_ID();

    /** Column name UNS_PackingSlip_UU */
    public static final String COLUMNNAME_UNS_PackingSlip_UU = "UNS_PackingSlip_UU";

	/** Set PackingSlip UU	  */
	public void setUNS_PackingSlip_UU (String UNS_PackingSlip_UU);

	/** Get PackingSlip UU	  */
	public String getUNS_PackingSlip_UU();

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
