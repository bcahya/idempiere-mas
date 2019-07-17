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

/** Generated Interface for UNS_IncentiveConfigRequire
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_IncentiveConfigRequire 
{

    /** TableName=UNS_IncentiveConfigRequire */
    public static final String Table_Name = "UNS_IncentiveConfigRequire";

    /** AD_Table_ID=1000368 */
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

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

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

    /** Column name UNS_IncentiveConfig_ID */
    public static final String COLUMNNAME_UNS_IncentiveConfig_ID = "UNS_IncentiveConfig_ID";

	/** Set Incentive Configuration	  */
	public void setUNS_IncentiveConfig_ID (int UNS_IncentiveConfig_ID);

	/** Get Incentive Configuration	  */
	public int getUNS_IncentiveConfig_ID();

	public com.unicore.model.I_UNS_IncentiveConfig getUNS_IncentiveConfig() throws RuntimeException;

    /** Column name UNS_IncentiveConfigRequire_ID */
    public static final String COLUMNNAME_UNS_IncentiveConfigRequire_ID = "UNS_IncentiveConfigRequire_ID";

	/** Set Incentive Criteria	  */
	public void setUNS_IncentiveConfigRequire_ID (int UNS_IncentiveConfigRequire_ID);

	/** Get Incentive Criteria	  */
	public int getUNS_IncentiveConfigRequire_ID();

    /** Column name UNS_IncentiveConfigRequire_UU */
    public static final String COLUMNNAME_UNS_IncentiveConfigRequire_UU = "UNS_IncentiveConfigRequire_UU";

	/** Set UNS_IncentiveConfigRequire_UU	  */
	public void setUNS_IncentiveConfigRequire_UU (String UNS_IncentiveConfigRequire_UU);

	/** Get UNS_IncentiveConfigRequire_UU	  */
	public String getUNS_IncentiveConfigRequire_UU();

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

    /** Column name Vendor_ID */
    public static final String COLUMNNAME_Vendor_ID = "Vendor_ID";

	/** Set Vendor.
	  * The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID);

	/** Get Vendor.
	  * The Vendor of the product/service
	  */
	public int getVendor_ID();

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException;
}
