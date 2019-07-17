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

/** Generated Interface for UNS_PrematureReq_Line
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_PrematureReq_Line 
{

    /** TableName=UNS_PrematureReq_Line */
    public static final String Table_Name = "UNS_PrematureReq_Line";

    /** AD_Table_ID=1000295 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name ApprovedQty */
    public static final String COLUMNNAME_ApprovedQty = "ApprovedQty";

	/** Set Approved Qty.
	  * Quantity to be approved for the requested qty by user/department
	  */
	public void setApprovedQty (BigDecimal ApprovedQty);

	/** Get Approved Qty.
	  * Quantity to be approved for the requested qty by user/department
	  */
	public BigDecimal getApprovedQty();

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

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public I_M_Locator getM_Locator() throws RuntimeException;

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

    /** Column name OnHoldQty */
    public static final String COLUMNNAME_OnHoldQty = "OnHoldQty";

	/** Set Quantity On Hold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty);

	/** Get Quantity On Hold	  */
	public BigDecimal getOnHoldQty();

    /** Column name Packages */
    public static final String COLUMNNAME_Packages = "Packages";

	/** Set Packages.
	  * Packages numbers in string sequence format (i.e.: 1-100;
 210-250;
 254;
 256)
	  */
	public void setPackages (String Packages);

	/** Get Packages.
	  * Packages numbers in string sequence format (i.e.: 1-100;
 210-250;
 254;
 256)
	  */
	public String getPackages();

    /** Column name Pallets */
    public static final String COLUMNNAME_Pallets = "Pallets";

	/** Set Pallets.
	  * Pallet numbers in string sequence format (i.e.: 1-10;
 12;
 14).
	  */
	public void setPallets (String Pallets);

	/** Get Pallets.
	  * Pallet numbers in string sequence format (i.e.: 1-10;
 12;
 14).
	  */
	public String getPallets();

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

    /** Column name ReleaseQty */
    public static final String COLUMNNAME_ReleaseQty = "ReleaseQty";

	/** Set Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty);

	/** Get Quantity Release	  */
	public BigDecimal getReleaseQty();

    /** Column name RequestedQty */
    public static final String COLUMNNAME_RequestedQty = "RequestedQty";

	/** Set Requested Qty.
	  * Quantity requested by user/department
	  */
	public void setRequestedQty (BigDecimal RequestedQty);

	/** Get Requested Qty.
	  * Quantity requested by user/department
	  */
	public BigDecimal getRequestedQty();

    /** Column name UNS_PrematureReq_Line_ID */
    public static final String COLUMNNAME_UNS_PrematureReq_Line_ID = "UNS_PrematureReq_Line_ID";

	/** Set Premature Release Line	  */
	public void setUNS_PrematureReq_Line_ID (int UNS_PrematureReq_Line_ID);

	/** Get Premature Release Line	  */
	public int getUNS_PrematureReq_Line_ID();

    /** Column name UNS_PrematureReq_Line_UU */
    public static final String COLUMNNAME_UNS_PrematureReq_Line_UU = "UNS_PrematureReq_Line_UU";

	/** Set UNS_PrematureReq_Line_UU.
	  * UNS_PrematureReq_Line_UU
	  */
	public void setUNS_PrematureReq_Line_UU (String UNS_PrematureReq_Line_UU);

	/** Get UNS_PrematureReq_Line_UU.
	  * UNS_PrematureReq_Line_UU
	  */
	public String getUNS_PrematureReq_Line_UU();

    /** Column name UNS_PrematureRequest_ID */
    public static final String COLUMNNAME_UNS_PrematureRequest_ID = "UNS_PrematureRequest_ID";

	/** Set Premature Release Request	  */
	public void setUNS_PrematureRequest_ID (int UNS_PrematureRequest_ID);

	/** Get Premature Release Request	  */
	public int getUNS_PrematureRequest_ID();

	public com.uns.model.I_UNS_PrematureRequest getUNS_PrematureRequest() throws RuntimeException;

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
