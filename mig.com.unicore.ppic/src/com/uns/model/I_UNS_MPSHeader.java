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

/** Generated Interface for UNS_MPSHeader
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MPSHeader 
{

    /** TableName=UNS_MPSHeader */
    public static final String Table_Name = "UNS_MPSHeader";

    /** AD_Table_ID=1000310 */
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

    /** Column name CreateProductFrom */
    public static final String COLUMNNAME_CreateProductFrom = "CreateProductFrom";

	/** Set Generate MPS	  */
	public void setCreateProductFrom (String CreateProductFrom);

	/** Get Generate MPS	  */
	public String getCreateProductFrom();

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

    /** Column name Generate_MRP */
    public static final String COLUMNNAME_Generate_MRP = "Generate_MRP";

	/** Set Generate MRP.
	  * Generate Material Requirement Planing By MPS Product
	  */
	public void setGenerate_MRP (String Generate_MRP);

	/** Get Generate MRP.
	  * Generate Material Requirement Planing By MPS Product
	  */
	public String getGenerate_MRP();

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

    /** Column name IsSyncDatabase */
    public static final String COLUMNNAME_IsSyncDatabase = "IsSyncDatabase";

	/** Set Synchronize Database.
	  * Change database table definition when changing dictionary definition
	  */
	public void setIsSyncDatabase (boolean IsSyncDatabase);

	/** Get Synchronize Database.
	  * Change database table definition when changing dictionary definition
	  */
	public boolean isSyncDatabase();

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

    /** Column name PeriodEnd_ID */
    public static final String COLUMNNAME_PeriodEnd_ID = "PeriodEnd_ID";

	/** Set Period End	  */
	public void setPeriodEnd_ID (int PeriodEnd_ID);

	/** Get Period End	  */
	public int getPeriodEnd_ID();

	public org.compiere.model.I_C_Period getPeriodEnd() throws RuntimeException;

    /** Column name PeriodStart_ID */
    public static final String COLUMNNAME_PeriodStart_ID = "PeriodStart_ID";

	/** Set Period Start	  */
	public void setPeriodStart_ID (int PeriodStart_ID);

	/** Get Period Start	  */
	public int getPeriodStart_ID();

	public org.compiere.model.I_C_Period getPeriodStart() throws RuntimeException;

    /** Column name PrevMPS_ID */
    public static final String COLUMNNAME_PrevMPS_ID = "PrevMPS_ID";

	/** Set Prev MPS	  */
	public void setPrevMPS_ID (int PrevMPS_ID);

	/** Get Prev MPS	  */
	public int getPrevMPS_ID();

	public com.uns.model.I_UNS_MPSHeader getPrevMPS() throws RuntimeException;

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

    /** Column name UNS_Forecast_Header_ID */
    public static final String COLUMNNAME_UNS_Forecast_Header_ID = "UNS_Forecast_Header_ID";

	/** Set Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID);

	/** Get Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID();

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException;

    /** Column name UNS_MPSHeader_ID */
    public static final String COLUMNNAME_UNS_MPSHeader_ID = "UNS_MPSHeader_ID";

	/** Set Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID);

	/** Get Master Production Schedule	  */
	public int getUNS_MPSHeader_ID();

    /** Column name UNS_MPSHeader_UU */
    public static final String COLUMNNAME_UNS_MPSHeader_UU = "UNS_MPSHeader_UU";

	/** Set MPS Header UU	  */
	public void setUNS_MPSHeader_UU (String UNS_MPSHeader_UU);

	/** Get MPS Header UU	  */
	public String getUNS_MPSHeader_UU();

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

    /** Column name UpdateMPS */
    public static final String COLUMNNAME_UpdateMPS = "UpdateMPS";

	/** Set Update MPS Changes.
	  * Update MPS actualization data based on changes made by other processes
	  */
	public void setUpdateMPS (String UpdateMPS);

	/** Get Update MPS Changes.
	  * Update MPS actualization data based on changes made by other processes
	  */
	public String getUpdateMPS();

    /** Column name UpdateMRP */
    public static final String COLUMNNAME_UpdateMRP = "UpdateMRP";

	/** Set Update MRP (From Receipt / Production)	  */
	public void setUpdateMRP (String UpdateMRP);

	/** Get Update MRP (From Receipt / Production)	  */
	public String getUpdateMRP();
}
