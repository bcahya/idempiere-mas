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

/** Generated Interface for UNS_ActualCostConfigItem
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ActualCostConfigItem 
{

    /** TableName=UNS_ActualCostConfigItem */
    public static final String Table_Name = "UNS_ActualCostConfigItem";

    /** AD_Table_ID=1000194 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException;

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

    /** Column name IsJointCost */
    public static final String COLUMNNAME_IsJointCost = "IsJointCost";

	/** Set Is Joint Cost.
	  * A flag to indicate if this cost item is subject of joint cost with other cost item in this department
	  */
	public void setIsJointCost (boolean IsJointCost);

	/** Get Is Joint Cost.
	  * A flag to indicate if this cost item is subject of joint cost with other cost item in this department
	  */
	public boolean isJointCost();

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

    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

	/** Set Product Type.
	  * Type of product
	  */
	public void setProductType (String ProductType);

	/** Get Product Type.
	  * Type of product
	  */
	public String getProductType();

    /** Column name UNS_ActualCostConfig_ID */
    public static final String COLUMNNAME_UNS_ActualCostConfig_ID = "UNS_ActualCostConfig_ID";

	/** Set Actual Cost Config	  */
	public void setUNS_ActualCostConfig_ID (int UNS_ActualCostConfig_ID);

	/** Get Actual Cost Config	  */
	public int getUNS_ActualCostConfig_ID();

	public com.uns.model.I_UNS_ActualCostConfig getUNS_ActualCostConfig() throws RuntimeException;

    /** Column name UNS_ActualCostConfigItem_ID */
    public static final String COLUMNNAME_UNS_ActualCostConfigItem_ID = "UNS_ActualCostConfigItem_ID";

	/** Set Actual Cost Item Config	  */
	public void setUNS_ActualCostConfigItem_ID (int UNS_ActualCostConfigItem_ID);

	/** Get Actual Cost Item Config	  */
	public int getUNS_ActualCostConfigItem_ID();

    /** Column name UNS_ActualCostConfigItem_UU */
    public static final String COLUMNNAME_UNS_ActualCostConfigItem_UU = "UNS_ActualCostConfigItem_UU";

	/** Set UNS_ActualCostConfigItem_UU.
	  * UNS_ActualCostConfigItem_UU
	  */
	public void setUNS_ActualCostConfigItem_UU (String UNS_ActualCostConfigItem_UU);

	/** Get UNS_ActualCostConfigItem_UU.
	  * UNS_ActualCostConfigItem_UU
	  */
	public String getUNS_ActualCostConfigItem_UU();

    /** Column name UNS_JointCostGroup_ID */
    public static final String COLUMNNAME_UNS_JointCostGroup_ID = "UNS_JointCostGroup_ID";

	/** Set Joint Cost Group.
	  * Joint Cost Group
	  */
	public void setUNS_JointCostGroup_ID (int UNS_JointCostGroup_ID);

	/** Get Joint Cost Group.
	  * Joint Cost Group
	  */
	public int getUNS_JointCostGroup_ID();

	public com.uns.model.I_UNS_JointCostGroup getUNS_JointCostGroup() throws RuntimeException;

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
