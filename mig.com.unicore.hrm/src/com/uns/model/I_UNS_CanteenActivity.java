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

/** Generated Interface for UNS_CanteenActivity
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CanteenActivity 
{

    /** TableName=UNS_CanteenActivity */
    public static final String Table_Name = "UNS_CanteenActivity";

    /** AD_Table_ID=1000337 */
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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name IsBreakfast */
    public static final String COLUMNNAME_IsBreakfast = "IsBreakfast";

	/** Set Breakfast	  */
	public void setIsBreakfast (boolean IsBreakfast);

	/** Get Breakfast	  */
	public boolean isBreakfast();

    /** Column name IsDinner */
    public static final String COLUMNNAME_IsDinner = "IsDinner";

	/** Set Dinner?	  */
	public void setIsDinner (boolean IsDinner);

	/** Get Dinner?	  */
	public boolean isDinner();

    /** Column name IsLunch */
    public static final String COLUMNNAME_IsLunch = "IsLunch";

	/** Set Lunch	  */
	public void setIsLunch (boolean IsLunch);

	/** Get Lunch	  */
	public boolean isLunch();

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

    /** Column name UNS_CanteenActivity_ID */
    public static final String COLUMNNAME_UNS_CanteenActivity_ID = "UNS_CanteenActivity_ID";

	/** Set Canteen Activity	  */
	public void setUNS_CanteenActivity_ID (int UNS_CanteenActivity_ID);

	/** Get Canteen Activity	  */
	public int getUNS_CanteenActivity_ID();

    /** Column name UNS_CanteenActivity_UU */
    public static final String COLUMNNAME_UNS_CanteenActivity_UU = "UNS_CanteenActivity_UU";

	/** Set UNS_CanteenActivity_UU	  */
	public void setUNS_CanteenActivity_UU (String UNS_CanteenActivity_UU);

	/** Get UNS_CanteenActivity_UU	  */
	public String getUNS_CanteenActivity_UU();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_MonthlyPresenceSummary_ID */
    public static final String COLUMNNAME_UNS_MonthlyPresenceSummary_ID = "UNS_MonthlyPresenceSummary_ID";

	/** Set Monthly Presence Summary	  */
	public void setUNS_MonthlyPresenceSummary_ID (int UNS_MonthlyPresenceSummary_ID);

	/** Get Monthly Presence Summary	  */
	public int getUNS_MonthlyPresenceSummary_ID();

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
