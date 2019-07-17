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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_ExpeditionPath
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ExpeditionPath 
{

    /** TableName=UNS_ExpeditionPath */
    public static final String Table_Name = "UNS_ExpeditionPath";

    /** AD_Table_ID=1000281 */
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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name Destination_ID */
    public static final String COLUMNNAME_Destination_ID = "Destination_ID";

	/** Set Destination	  */
	public void setDestination_ID (int Destination_ID);

	/** Get Destination	  */
	public int getDestination_ID();

	public org.compiere.model.I_C_Location getDestination() throws RuntimeException;

    /** Column name Distance */
    public static final String COLUMNNAME_Distance = "Distance";

	/** Set Distance.
	  * The number of distance in Kilo Meter from an area to another.
	  */
	public void setDistance (BigDecimal Distance);

	/** Get Distance.
	  * The number of distance in Kilo Meter from an area to another.
	  */
	public BigDecimal getDistance();

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

    /** Column name JobSOPath_ID */
    public static final String COLUMNNAME_JobSOPath_ID = "JobSOPath_ID";

	/** Set JobSOPath_ID	  */
	public void setJobSOPath_ID (int JobSOPath_ID);

	/** Get JobSOPath_ID	  */
	public int getJobSOPath_ID();

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

    /** Column name Origin_ID */
    public static final String COLUMNNAME_Origin_ID = "Origin_ID";

	/** Set Origin	  */
	public void setOrigin_ID (int Origin_ID);

	/** Get Origin	  */
	public int getOrigin_ID();

	public org.compiere.model.I_C_Location getOrigin() throws RuntimeException;

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Unit Price.
	  * Actual Price
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Unit Price.
	  * Actual Price
	  */
	public BigDecimal getPriceActual();

    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/** Set Limit Price.
	  * Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit);

	/** Get Limit Price.
	  * Lowest price for a product
	  */
	public BigDecimal getPriceLimit();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

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

    /** Column name Tonase */
    public static final String COLUMNNAME_Tonase = "Tonase";

	/** Set Tonase.
	  * Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase);

	/** Get Tonase.
	  * Indicate total tonase
	  */
	public BigDecimal getTonase();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name UNS_Expedition_ID */
    public static final String COLUMNNAME_UNS_Expedition_ID = "UNS_Expedition_ID";

	/** Set Expedition	  */
	public void setUNS_Expedition_ID (int UNS_Expedition_ID);

	/** Get Expedition	  */
	public int getUNS_Expedition_ID();

	public com.unicore.model.I_UNS_Expedition getUNS_Expedition() throws RuntimeException;

    /** Column name UNS_ExpeditionPath_ID */
    public static final String COLUMNNAME_UNS_ExpeditionPath_ID = "UNS_ExpeditionPath_ID";

	/** Set Expedetion Path	  */
	public void setUNS_ExpeditionPath_ID (int UNS_ExpeditionPath_ID);

	/** Get Expedetion Path	  */
	public int getUNS_ExpeditionPath_ID();

    /** Column name UNS_ExpeditionPath_UU */
    public static final String COLUMNNAME_UNS_ExpeditionPath_UU = "UNS_ExpeditionPath_UU";

	/** Set UNS_ExpeditionPath_UU	  */
	public void setUNS_ExpeditionPath_UU (String UNS_ExpeditionPath_UU);

	/** Get UNS_ExpeditionPath_UU	  */
	public String getUNS_ExpeditionPath_UU();

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

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();
}
