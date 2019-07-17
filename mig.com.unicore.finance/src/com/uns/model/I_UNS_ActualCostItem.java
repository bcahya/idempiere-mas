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

/** Generated Interface for UNS_ActualCostItem
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ActualCostItem 
{

    /** TableName=UNS_ActualCostItem */
    public static final String Table_Name = "UNS_ActualCostItem";

    /** AD_Table_ID=1000198 */
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

    /** Column name CostVariance */
    public static final String COLUMNNAME_CostVariance = "CostVariance";

	/** Set Cost Variance.
	  * Variance of standard cost to actual cost (was directly recognized while the transaction posted), negative means creditted.
	  */
	public void setCostVariance (BigDecimal CostVariance);

	/** Get Cost Variance.
	  * Variance of standard cost to actual cost (was directly recognized while the transaction posted), negative means creditted.
	  */
	public BigDecimal getCostVariance();

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

    /** Column name CurrentCostPrice */
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/** Set Current Cost Price.
	  * The currently used cost price
	  */
	public void setCurrentCostPrice (BigDecimal CurrentCostPrice);

	/** Get Current Cost Price.
	  * The currently used cost price
	  */
	public BigDecimal getCurrentCostPrice();

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

    /** Column name DistributableCostAllocation */
    public static final String COLUMNNAME_DistributableCostAllocation = "DistributableCostAllocation";

	/** Set Distributable-Cost Allocated.
	  * Distributable-Cost Allocated
	  */
	public void setDistributableCostAllocation (BigDecimal DistributableCostAllocation);

	/** Get Distributable-Cost Allocated.
	  * Distributable-Cost Allocated
	  */
	public BigDecimal getDistributableCostAllocation();

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

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set Ending On Hand Qty.
	  * Ending On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get Ending On Hand Qty.
	  * Ending On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name TotalActualCost */
    public static final String COLUMNNAME_TotalActualCost = "TotalActualCost";

	/** Set Total Actual Cost.
	  * The Total of actual cost after actual cost reconciliation
	  */
	public void setTotalActualCost (BigDecimal TotalActualCost);

	/** Get Total Actual Cost.
	  * The Total of actual cost after actual cost reconciliation
	  */
	public BigDecimal getTotalActualCost();

    /** Column name TotalCurrentCost */
    public static final String COLUMNNAME_TotalCurrentCost = "TotalCurrentCost";

	/** Set Total Current Cost.
	  * The total of current cost before actual cost reconciliation
	  */
	public void setTotalCurrentCost (BigDecimal TotalCurrentCost);

	/** Get Total Current Cost.
	  * The total of current cost before actual cost reconciliation
	  */
	public BigDecimal getTotalCurrentCost();

    /** Column name TotalDistributableCost */
    public static final String COLUMNNAME_TotalDistributableCost = "TotalDistributableCost";

	/** Set Total Distributable Cost.
	  * Total Distributable Cost
	  */
	public void setTotalDistributableCost (BigDecimal TotalDistributableCost);

	/** Get Total Distributable Cost.
	  * Total Distributable Cost
	  */
	public BigDecimal getTotalDistributableCost();

    /** Column name TotalQty */
    public static final String COLUMNNAME_TotalQty = "TotalQty";

	/** Set Total Quantity.
	  * Total Quantity
	  */
	public void setTotalQty (BigDecimal TotalQty);

	/** Get Total Quantity.
	  * Total Quantity
	  */
	public BigDecimal getTotalQty();

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

    /** Column name UNS_ActualCostItem_UU */
    public static final String COLUMNNAME_UNS_ActualCostItem_UU = "UNS_ActualCostItem_UU";

	/** Set UNS_ActualCostItem_UU.
	  * UNS_ActualCostItem_UU
	  */
	public void setUNS_ActualCostItem_UU (String UNS_ActualCostItem_UU);

	/** Get UNS_ActualCostItem_UU.
	  * UNS_ActualCostItem_UU
	  */
	public String getUNS_ActualCostItem_UU();

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
