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

/** Generated Interface for UNS_ContainerArrival
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ContainerArrival 
{

    /** TableName=UNS_ContainerArrival */
    public static final String Table_Name = "UNS_ContainerArrival";

    /** AD_Table_ID=1000062 */
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

    /** Column name BLRef */
    public static final String COLUMNNAME_BLRef = "BLRef";

	/** Set BL Ref	  */
	public void setBLRef (String BLRef);

	/** Get BL Ref	  */
	public String getBLRef();

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

    /** Column name ETD_Factory */
    public static final String COLUMNNAME_ETD_Factory = "ETD_Factory";

	/** Set ETD Factory	  */
	public void setETD_Factory (Timestamp ETD_Factory);

	/** Get ETD Factory	  */
	public Timestamp getETD_Factory();

    /** Column name GenerateList */
    public static final String COLUMNNAME_GenerateList = "GenerateList";

	/** Set Generate List.
	  * Generate List
	  */
	public void setGenerateList (String GenerateList);

	/** Get Generate List.
	  * Generate List
	  */
	public String getGenerateList();

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
	public void setProcessedOn (int ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public int getProcessedOn();

	public I_C_ValidCombination getProcesse() throws RuntimeException;

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QAStatus */
    public static final String COLUMNNAME_QAStatus = "QAStatus";

	/** Set QA Status	  */
	public void setQAStatus (String QAStatus);

	/** Get QA Status	  */
	public String getQAStatus();

    /** Column name SealNo */
    public static final String COLUMNNAME_SealNo = "SealNo";

	/** Set Seal No	  */
	public void setSealNo (String SealNo);

	/** Get Seal No	  */
	public String getSealNo();

    /** Column name Shipped_ID */
    public static final String COLUMNNAME_Shipped_ID = "Shipped_ID";

	/** Set Shipped By	  */
	public void setShipped_ID (int Shipped_ID);

	/** Get Shipped By	  */
	public int getShipped_ID();

	public org.compiere.model.I_AD_User getShipped() throws RuntimeException;

    /** Column name ShippingLine_ID */
    public static final String COLUMNNAME_ShippingLine_ID = "ShippingLine_ID";

	/** Set Shipping Line	  */
	public void setShippingLine_ID (int ShippingLine_ID);

	/** Get Shipping Line	  */
	public int getShippingLine_ID();

	public org.compiere.model.I_M_Shipper getShippingLine() throws RuntimeException;

    /** Column name UNS_ContainerArrival_ID */
    public static final String COLUMNNAME_UNS_ContainerArrival_ID = "UNS_ContainerArrival_ID";

	/** Set Container Arrival	  */
	public void setUNS_ContainerArrival_ID (int UNS_ContainerArrival_ID);

	/** Get Container Arrival	  */
	public int getUNS_ContainerArrival_ID();

    /** Column name UNS_ContainerArrival_UU */
    public static final String COLUMNNAME_UNS_ContainerArrival_UU = "UNS_ContainerArrival_UU";

	/** Set Container Arrival UU	  */
	public void setUNS_ContainerArrival_UU (String UNS_ContainerArrival_UU);

	/** Get Container Arrival UU	  */
	public String getUNS_ContainerArrival_UU();

    /** Column name UNS_ContainerManagement_ID */
    public static final String COLUMNNAME_UNS_ContainerManagement_ID = "UNS_ContainerManagement_ID";

	/** Set Container No	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID);

	/** Get Container No	  */
	public int getUNS_ContainerManagement_ID();

    /** Column name UNS_PackingSlip_ID */
    public static final String COLUMNNAME_UNS_PackingSlip_ID = "UNS_PackingSlip_ID";

	/** Set Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID);

	/** Get Packing Slip	  */
	public int getUNS_PackingSlip_ID();

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
