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

/** Generated Interface for UNS_Forecast_Header
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Forecast_Header 
{

    /** TableName=UNS_Forecast_Header */
    public static final String Table_Name = "UNS_Forecast_Header";

    /** AD_Table_ID=1000118 */
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

    /** Column name AllocationMethod */
    public static final String COLUMNNAME_AllocationMethod = "AllocationMethod";

	/** Set Allocation Method	  */
	public void setAllocationMethod (String AllocationMethod);

	/** Get Allocation Method	  */
	public String getAllocationMethod();

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name GenerateForecast */
    public static final String COLUMNNAME_GenerateForecast = "GenerateForecast";

	/** Set Generate Forecast	  */
	public void setGenerateForecast (String GenerateForecast);

	/** Get Generate Forecast	  */
	public String getGenerateForecast();

    /** Column name GenerateForecastAllocation */
    public static final String COLUMNNAME_GenerateForecastAllocation = "GenerateForecastAllocation";

	/** Set Generate Forecast Allocation	  */
	public void setGenerateForecastAllocation (String GenerateForecastAllocation);

	/** Get Generate Forecast Allocation	  */
	public String getGenerateForecastAllocation();

    /** Column name GenerateTargetedQty */
    public static final String COLUMNNAME_GenerateTargetedQty = "GenerateTargetedQty";

	/** Set Generate Targeted Quantity	  */
	public void setGenerateTargetedQty (String GenerateTargetedQty);

	/** Get Generate Targeted Quantity	  */
	public String getGenerateTargetedQty();

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

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

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

    /** Column name PrevForecast_ID */
    public static final String COLUMNNAME_PrevForecast_ID = "PrevForecast_ID";

	/** Set Prev Forecast	  */
	public void setPrevForecast_ID (int PrevForecast_ID);

	/** Get Prev Forecast	  */
	public int getPrevForecast_ID();

	public com.uns.model.I_UNS_Forecast_Header getPrevForecast() throws RuntimeException;

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

    /** Column name UNS_Forecast_Header_UU */
    public static final String COLUMNNAME_UNS_Forecast_Header_UU = "UNS_Forecast_Header_UU";

	/** Set Forecast Header UU	  */
	public void setUNS_Forecast_Header_UU (String UNS_Forecast_Header_UU);

	/** Get Forecast Header UU	  */
	public String getUNS_Forecast_Header_UU();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

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
	
	public I_UNS_Resource getUNS_Resource();
}
