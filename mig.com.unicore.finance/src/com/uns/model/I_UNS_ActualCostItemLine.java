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

/** Generated Interface for UNS_ActualCostItemLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ActualCostItemLine 
{

    /** TableName=UNS_ActualCostItemLine */
    public static final String Table_Name = "UNS_ActualCostItemLine";

    /** AD_Table_ID=1000199 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name ActualCost */
    public static final String COLUMNNAME_ActualCost = "ActualCost";

	/** Set Actual Cost.
	  * Actual Cost
	  */
	public void setActualCost (BigDecimal ActualCost);

	/** Get Actual Cost.
	  * Actual Cost
	  */
	public BigDecimal getActualCost();

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

    /** Column name AdjustedCostPrice */
    public static final String COLUMNNAME_AdjustedCostPrice = "AdjustedCostPrice";

	/** Set Adjusted Cost Price.
	  * The actual of the cost price after adjustment calculation
	  */
	public void setAdjustedCostPrice (BigDecimal AdjustedCostPrice);

	/** Get Adjusted Cost Price.
	  * The actual of the cost price after adjustment calculation
	  */
	public BigDecimal getAdjustedCostPrice();

    /** Column name AllocatedCost */
    public static final String COLUMNNAME_AllocatedCost = "AllocatedCost";

	/** Set Cost Variance.
	  * Difference of Actual Cost to Current Cost
	  */
	public void setAllocatedCost (BigDecimal AllocatedCost);

	/** Get Cost Variance.
	  * Difference of Actual Cost to Current Cost
	  */
	public BigDecimal getAllocatedCost();

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

    /** Column name CurrentCost */
    public static final String COLUMNNAME_CurrentCost = "CurrentCost";

	/** Set Current Cost.
	  * Current Cost
	  */
	public void setCurrentCost (BigDecimal CurrentCost);

	/** Get Current Cost.
	  * Current Cost
	  */
	public BigDecimal getCurrentCost();

    /** Column name CurrentUsedCost */
    public static final String COLUMNNAME_CurrentUsedCost = "CurrentUsedCost";

	/** Set Current Used Cost.
	  * Current Used Cost
	  */
	public void setCurrentUsedCost (BigDecimal CurrentUsedCost);

	/** Get Current Used Cost.
	  * Current Used Cost
	  */
	public BigDecimal getCurrentUsedCost();

    /** Column name CurrentUsedQty */
    public static final String COLUMNNAME_CurrentUsedQty = "CurrentUsedQty";

	/** Set Current Used Quantity.
	  * The quantity used of which the product acquisitioned in the current period
	  */
	public void setCurrentUsedQty (BigDecimal CurrentUsedQty);

	/** Get Current Used Quantity.
	  * The quantity used of which the product acquisitioned in the current period
	  */
	public BigDecimal getCurrentUsedQty();

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

    /** Column name TotalUsedQty */
    public static final String COLUMNNAME_TotalUsedQty = "TotalUsedQty";

	/** Set Total Used Qty.
	  * Total quantity used by other department
	  */
	public void setTotalUsedQty (BigDecimal TotalUsedQty);

	/** Get Total Used Qty.
	  * Total quantity used by other department
	  */
	public BigDecimal getTotalUsedQty();

    /** Column name UNS_ActualCostItem_ID */
    public static final String COLUMNNAME_UNS_ActualCostItem_ID = "UNS_ActualCostItem_ID";

	/** Set Actual Cost Item.
	  * Actual Cost Item
	  */
	public void setUNS_ActualCostItem_ID (int UNS_ActualCostItem_ID);

	/** Get Actual Cost Item.
	  * Actual Cost Item
	  */
	public int getUNS_ActualCostItem_ID();

	public com.uns.model.I_UNS_ActualCostItem getUNS_ActualCostItem() throws RuntimeException;

    /** Column name UNS_ActualCostItemLine_ID */
    public static final String COLUMNNAME_UNS_ActualCostItemLine_ID = "UNS_ActualCostItemLine_ID";

	/** Set Actual Cost Item Line	  */
	public void setUNS_ActualCostItemLine_ID (int UNS_ActualCostItemLine_ID);

	/** Get Actual Cost Item Line	  */
	public int getUNS_ActualCostItemLine_ID();

    /** Column name UNS_ActualCostItemLine_UU */
    public static final String COLUMNNAME_UNS_ActualCostItemLine_UU = "UNS_ActualCostItemLine_UU";

	/** Set UNS_ActualCostItemLine_UU.
	  * UNS_ActualCostItemLine_UU
	  */
	public void setUNS_ActualCostItemLine_UU (String UNS_ActualCostItemLine_UU);

	/** Get UNS_ActualCostItemLine_UU.
	  * UNS_ActualCostItemLine_UU
	  */
	public String getUNS_ActualCostItemLine_UU();

    /** Column name UNS_ActualCostReconcile_ID */
    public static final String COLUMNNAME_UNS_ActualCostReconcile_ID = "UNS_ActualCostReconcile_ID";

	/** Set Actual Cost Reconciliation	  */
	public void setUNS_ActualCostReconcile_ID (int UNS_ActualCostReconcile_ID);

	/** Get Actual Cost Reconciliation	  */
	public int getUNS_ActualCostReconcile_ID();

	public com.uns.model.I_UNS_ActualCostReconcile getUNS_ActualCostReconcile() throws RuntimeException;

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
