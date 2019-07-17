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

/** Generated Interface for UNS_Biaya_Bongkar_Muat
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Biaya_Bongkar_Muat 
{

    /** TableName=UNS_Biaya_Bongkar_Muat */
    public static final String Table_Name = "UNS_Biaya_Bongkar_Muat";

    /** AD_Table_ID=1000068 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name LoadingManual */
    public static final String COLUMNNAME_LoadingManual = "LoadingManual";

	/** Set Loading Manual.
	  * Loading Manual (by hand)
	  */
	public void setLoadingManual (BigDecimal LoadingManual);

	/** Get Loading Manual.
	  * Loading Manual (by hand)
	  */
	public BigDecimal getLoadingManual();

    /** Column name LoadingWithCrane */
    public static final String COLUMNNAME_LoadingWithCrane = "LoadingWithCrane";

	/** Set Loading w/ Crane.
	  * Loading w/ Crane
	  */
	public void setLoadingWithCrane (BigDecimal LoadingWithCrane);

	/** Get Loading w/ Crane.
	  * Loading w/ Crane
	  */
	public BigDecimal getLoadingWithCrane();

    /** Column name LoadingWithForklift */
    public static final String COLUMNNAME_LoadingWithForklift = "LoadingWithForklift";

	/** Set Loading w/ Forklift.
	  * Loading w/ Forklift
	  */
	public void setLoadingWithForklift (BigDecimal LoadingWithForklift);

	/** Get Loading w/ Forklift.
	  * Loading w/ Forklift
	  */
	public BigDecimal getLoadingWithForklift();

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

    /** Column name UnloadingManual */
    public static final String COLUMNNAME_UnloadingManual = "UnloadingManual";

	/** Set Unloading Manual.
	  * Unloading Manual (by hand)
	  */
	public void setUnloadingManual (BigDecimal UnloadingManual);

	/** Get Unloading Manual.
	  * Unloading Manual (by hand)
	  */
	public BigDecimal getUnloadingManual();

    /** Column name UnloadingWithCrane */
    public static final String COLUMNNAME_UnloadingWithCrane = "UnloadingWithCrane";

	/** Set Unloading w/ Crane.
	  * Unloading w/ Crane
	  */
	public void setUnloadingWithCrane (BigDecimal UnloadingWithCrane);

	/** Get Unloading w/ Crane.
	  * Unloading w/ Crane
	  */
	public BigDecimal getUnloadingWithCrane();

    /** Column name UnloadingWithForklift */
    public static final String COLUMNNAME_UnloadingWithForklift = "UnloadingWithForklift";

	/** Set Unloading w/ Forklift.
	  * Unloading w/ Forklift
	  */
	public void setUnloadingWithForklift (BigDecimal UnloadingWithForklift);

	/** Get Unloading w/ Forklift.
	  * Unloading w/ Forklift
	  */
	public BigDecimal getUnloadingWithForklift();

    /** Column name UNS_Biaya_Bongkar_Muat_ID */
    public static final String COLUMNNAME_UNS_Biaya_Bongkar_Muat_ID = "UNS_Biaya_Bongkar_Muat_ID";

	/** Set Biaya Bongkar Muat	  */
	public void setUNS_Biaya_Bongkar_Muat_ID (int UNS_Biaya_Bongkar_Muat_ID);

	/** Get Biaya Bongkar Muat	  */
	public int getUNS_Biaya_Bongkar_Muat_ID();

    /** Column name UNS_Biaya_Bongkar_Muat_UU */
    public static final String COLUMNNAME_UNS_Biaya_Bongkar_Muat_UU = "UNS_Biaya_Bongkar_Muat_UU";

	/** Set UNS_Biaya_Bongkar_Muat_UU	  */
	public void setUNS_Biaya_Bongkar_Muat_UU (String UNS_Biaya_Bongkar_Muat_UU);

	/** Get UNS_Biaya_Bongkar_Muat_UU	  */
	public String getUNS_Biaya_Bongkar_Muat_UU();

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();
}
