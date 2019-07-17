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

/** Generated Interface for UNS_ContainerManagement
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ContainerManagement 
{

    /** TableName=UNS_ContainerManagement */
    public static final String Table_Name = "UNS_ContainerManagement";

    /** AD_Table_ID=1000067 */
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

    /** Column name ArrivedBy_ID */
    public static final String COLUMNNAME_ArrivedBy_ID = "ArrivedBy_ID";

	/** Set Arrived By	  */
	public void setArrivedBy_ID (int ArrivedBy_ID);

	/** Get Arrived By	  */
	public int getArrivedBy_ID();

	public org.compiere.model.I_AD_User getArrivedBy() throws RuntimeException;

    /** Column name ContainerNo */
    public static final String COLUMNNAME_ContainerNo = "ContainerNo";

	/** Set Container No	  */
	public void setContainerNo (String ContainerNo);

	/** Get Container No	  */
	public String getContainerNo();

    /** Column name ContainerType */
    public static final String COLUMNNAME_ContainerType = "ContainerType";

	/** Set Web Container Type.
	  * Web Container Type
	  */
	public void setContainerType (String ContainerType);

	/** Get Web Container Type.
	  * Web Container Type
	  */
	public String getContainerType();

    /** Column name ContainerVolume */
    public static final String COLUMNNAME_ContainerVolume = "ContainerVolume";

	/** Set Container Volume.
	  * Container Volume (20', 40', 45')
	  */
	public void setContainerVolume (String ContainerVolume);

	/** Get Container Volume.
	  * Container Volume (20', 40', 45')
	  */
	public String getContainerVolume();

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

    /** Column name CurrentStatus */
    public static final String COLUMNNAME_CurrentStatus = "CurrentStatus";

	/** Set Current Status	  */
	public void setCurrentStatus (String CurrentStatus);

	/** Get Current Status	  */
	public String getCurrentStatus();

    /** Column name DepartedBy_ID */
    public static final String COLUMNNAME_DepartedBy_ID = "DepartedBy_ID";

	/** Set Departed By	  */
	public void setDepartedBy_ID (int DepartedBy_ID);

	/** Get Departed By	  */
	public int getDepartedBy_ID();

	public org.compiere.model.I_AD_User getDepartedBy() throws RuntimeException;

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

    /** Column name LastArrivalDate */
    public static final String COLUMNNAME_LastArrivalDate = "LastArrivalDate";

	/** Set Last Arrival Date	  */
	public void setLastArrivalDate (Timestamp LastArrivalDate);

	/** Get Last Arrival Date	  */
	public Timestamp getLastArrivalDate();

    /** Column name LastDepartureDate */
    public static final String COLUMNNAME_LastDepartureDate = "LastDepartureDate";

	/** Set Last Departure Date	  */
	public void setLastDepartureDate (Timestamp LastDepartureDate);

	/** Get Last Departure Date	  */
	public Timestamp getLastDepartureDate();

    /** Column name LastQADate */
    public static final String COLUMNNAME_LastQADate = "LastQADate";

	/** Set Last QA Date	  */
	public void setLastQADate (Timestamp LastQADate);

	/** Get Last QA Date	  */
	public Timestamp getLastQADate();

    /** Column name LastQAStatus */
    public static final String COLUMNNAME_LastQAStatus = "LastQAStatus";

	/** Set Last QA Status	  */
	public void setLastQAStatus (String LastQAStatus);

	/** Get Last QA Status	  */
	public String getLastQAStatus();

    /** Column name Max_StandbyTime */
    public static final String COLUMNNAME_Max_StandbyTime = "Max_StandbyTime";

	/** Set Max Standby Time (Day)	  */
	public void setMax_StandbyTime (BigDecimal Max_StandbyTime);

	/** Get Max Standby Time (Day)	  */
	public BigDecimal getMax_StandbyTime();

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

    /** Column name ShippingLine_ID */
    public static final String COLUMNNAME_ShippingLine_ID = "ShippingLine_ID";

	/** Set Shipping Line	  */
	public void setShippingLine_ID (int ShippingLine_ID);

	/** Get Shipping Line	  */
	public int getShippingLine_ID();

	public org.compiere.model.I_M_Shipper getShippingLine() throws RuntimeException;

    /** Column name UNS_ContainerManagement_ID */
    public static final String COLUMNNAME_UNS_ContainerManagement_ID = "UNS_ContainerManagement_ID";

	/** Set Container Management	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID);

	/** Get Container Management	  */
	public int getUNS_ContainerManagement_ID();

    /** Column name UNS_ContainerManagement_UU */
    public static final String COLUMNNAME_UNS_ContainerManagement_UU = "UNS_ContainerManagement_UU";

	/** Set Container Management UU	  */
	public void setUNS_ContainerManagement_UU (String UNS_ContainerManagement_UU);

	/** Get Container Management UU	  */
	public String getUNS_ContainerManagement_UU();

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
