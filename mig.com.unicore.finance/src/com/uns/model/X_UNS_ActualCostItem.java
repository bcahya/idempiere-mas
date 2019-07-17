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
/** Generated Model - DO NOT CHANGE */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_ActualCostItem
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ActualCostItem extends PO implements I_UNS_ActualCostItem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130630L;

    /** Standard Constructor */
    public X_UNS_ActualCostItem (Properties ctx, int UNS_ActualCostItem_ID, String trxName)
    {
      super (ctx, UNS_ActualCostItem_ID, trxName);
      /** if (UNS_ActualCostItem_ID == 0)
        {
			setC_ElementValue_ID (0);
			setCurrentCostPrice (Env.ZERO);
// 0
			setIsJointCost (false);
// 'N'
			setM_Product_ID (0);
			setName (null);
			setProductType (null);
// MPH
			setTotalDistributableCost (Env.ZERO);
// 0
			setTotalQty (Env.ZERO);
// 0
			setTotalUsedQty (Env.ZERO);
// 0
			setUNS_ActualCostItem_ID (0);
			setUNS_ActualCostReconcile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ActualCostItem (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_ActualCostItem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Adjusted Cost Price.
		@param AdjustedCostPrice 
		The actual of the cost price after adjustment calculation
	  */
	public void setAdjustedCostPrice (BigDecimal AdjustedCostPrice)
	{
		set_Value (COLUMNNAME_AdjustedCostPrice, AdjustedCostPrice);
	}

	/** Get Adjusted Cost Price.
		@return The actual of the cost price after adjustment calculation
	  */
	public BigDecimal getAdjustedCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AdjustedCostPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost Variance.
		@param CostVariance 
		Variance of standard cost to actual cost (was directly recognized while the transaction posted), negative means creditted.
	  */
	public void setCostVariance (BigDecimal CostVariance)
	{
		set_Value (COLUMNNAME_CostVariance, CostVariance);
	}

	/** Get Cost Variance.
		@return Variance of standard cost to actual cost (was directly recognized while the transaction posted), negative means creditted.
	  */
	public BigDecimal getCostVariance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostVariance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Current Cost Price.
		@param CurrentCostPrice 
		The currently used cost price
	  */
	public void setCurrentCostPrice (BigDecimal CurrentCostPrice)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	/** Get Current Cost Price.
		@return The currently used cost price
	  */
	public BigDecimal getCurrentCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentCostPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Current Used Quantity.
		@param CurrentUsedQty 
		The quantity used of which the product acquisitioned in the current period
	  */
	public void setCurrentUsedQty (BigDecimal CurrentUsedQty)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentUsedQty, CurrentUsedQty);
	}

	/** Get Current Used Quantity.
		@return The quantity used of which the product acquisitioned in the current period
	  */
	public BigDecimal getCurrentUsedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentUsedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Distributable-Cost Allocated.
		@param DistributableCostAllocation 
		Distributable-Cost Allocated
	  */
	public void setDistributableCostAllocation (BigDecimal DistributableCostAllocation)
	{
		set_Value (COLUMNNAME_DistributableCostAllocation, DistributableCostAllocation);
	}

	/** Get Distributable-Cost Allocated.
		@return Distributable-Cost Allocated
	  */
	public BigDecimal getDistributableCostAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DistributableCostAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Is Joint Cost.
		@param IsJointCost 
		A flag to indicate if this cost item is subject of joint cost with other cost item in this department
	  */
	public void setIsJointCost (boolean IsJointCost)
	{
		set_Value (COLUMNNAME_IsJointCost, Boolean.valueOf(IsJointCost));
	}

	/** Get Is Joint Cost.
		@return A flag to indicate if this cost item is subject of joint cost with other cost item in this department
	  */
	public boolean isJointCost () 
	{
		Object oo = get_Value(COLUMNNAME_IsJointCost);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** ProductType AD_Reference_ID=1000115 */
	public static final int PRODUCTTYPE_AD_Reference_ID=1000115;
	/** Finished Goods = FG */
	public static final String PRODUCTTYPE_FinishedGoods = "FG";
	/** Raw Material = RM */
	public static final String PRODUCTTYPE_RawMaterial = "RM";
	/** Man Per Hour = MPH */
	public static final String PRODUCTTYPE_ManPerHour = "MPH";
	/** Work In Process = WIP */
	public static final String PRODUCTTYPE_WorkInProcess = "WIP";
	/** Utilities = UTL */
	public static final String PRODUCTTYPE_Utilities = "UTL";
	/** Sub Raw Material = SUB */
	public static final String PRODUCTTYPE_SubRawMaterial = "SUB";
	/** Set Product Type.
		@param ProductType 
		Type of product
	  */
	public void setProductType (String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Product Type.
		@return Type of product
	  */
	public String getProductType () 
	{
		return (String)get_Value(COLUMNNAME_ProductType);
	}

	/** Set Ending On Hand Qty.
		@param QtyOnHand 
		Ending On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get Ending On Hand Qty.
		@return Ending On Hand Quantity
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Actual Cost.
		@param TotalActualCost 
		The Total of actual cost after actual cost reconciliation
	  */
	public void setTotalActualCost (BigDecimal TotalActualCost)
	{
		set_Value (COLUMNNAME_TotalActualCost, TotalActualCost);
	}

	/** Get Total Actual Cost.
		@return The Total of actual cost after actual cost reconciliation
	  */
	public BigDecimal getTotalActualCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalActualCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Current Cost.
		@param TotalCurrentCost 
		The total of current cost before actual cost reconciliation
	  */
	public void setTotalCurrentCost (BigDecimal TotalCurrentCost)
	{
		set_Value (COLUMNNAME_TotalCurrentCost, TotalCurrentCost);
	}

	/** Get Total Current Cost.
		@return The total of current cost before actual cost reconciliation
	  */
	public BigDecimal getTotalCurrentCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalCurrentCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Distributable Cost.
		@param TotalDistributableCost 
		Total Distributable Cost
	  */
	public void setTotalDistributableCost (BigDecimal TotalDistributableCost)
	{
		set_Value (COLUMNNAME_TotalDistributableCost, TotalDistributableCost);
	}

	/** Get Total Distributable Cost.
		@return Total Distributable Cost
	  */
	public BigDecimal getTotalDistributableCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDistributableCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Quantity.
		@param TotalQty 
		Total Quantity
	  */
	public void setTotalQty (BigDecimal TotalQty)
	{
		set_Value (COLUMNNAME_TotalQty, TotalQty);
	}

	/** Get Total Quantity.
		@return Total Quantity
	  */
	public BigDecimal getTotalQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Used Qty.
		@param TotalUsedQty 
		Total quantity used by other department
	  */
	public void setTotalUsedQty (BigDecimal TotalUsedQty)
	{
		set_Value (COLUMNNAME_TotalUsedQty, TotalUsedQty);
	}

	/** Get Total Used Qty.
		@return Total quantity used by other department
	  */
	public BigDecimal getTotalUsedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalUsedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Cost Item.
		@param UNS_ActualCostItem_ID 
		Actual Cost Item
	  */
	public void setUNS_ActualCostItem_ID (int UNS_ActualCostItem_ID)
	{
		if (UNS_ActualCostItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostItem_ID, Integer.valueOf(UNS_ActualCostItem_ID));
	}

	/** Get Actual Cost Item.
		@return Actual Cost Item
	  */
	public int getUNS_ActualCostItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ActualCostItem_UU.
		@param UNS_ActualCostItem_UU 
		UNS_ActualCostItem_UU
	  */
	public void setUNS_ActualCostItem_UU (String UNS_ActualCostItem_UU)
	{
		set_Value (COLUMNNAME_UNS_ActualCostItem_UU, UNS_ActualCostItem_UU);
	}

	/** Get UNS_ActualCostItem_UU.
		@return UNS_ActualCostItem_UU
	  */
	public String getUNS_ActualCostItem_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ActualCostItem_UU);
	}

	public com.uns.model.I_UNS_ActualCostReconcile getUNS_ActualCostReconcile() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ActualCostReconcile)MTable.get(getCtx(), com.uns.model.I_UNS_ActualCostReconcile.Table_Name)
			.getPO(getUNS_ActualCostReconcile_ID(), get_TrxName());	}

	/** Set Actual Cost Reconciliation.
		@param UNS_ActualCostReconcile_ID Actual Cost Reconciliation	  */
	public void setUNS_ActualCostReconcile_ID (int UNS_ActualCostReconcile_ID)
	{
		if (UNS_ActualCostReconcile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostReconcile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostReconcile_ID, Integer.valueOf(UNS_ActualCostReconcile_ID));
	}

	/** Get Actual Cost Reconciliation.
		@return Actual Cost Reconciliation	  */
	public int getUNS_ActualCostReconcile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostReconcile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_JointCostGroup getUNS_JointCostGroup() throws RuntimeException
    {
		return (com.uns.model.I_UNS_JointCostGroup)MTable.get(getCtx(), com.uns.model.I_UNS_JointCostGroup.Table_Name)
			.getPO(getUNS_JointCostGroup_ID(), get_TrxName());	}

	/** Set Joint Cost Group.
		@param UNS_JointCostGroup_ID 
		Joint Cost Group
	  */
	public void setUNS_JointCostGroup_ID (int UNS_JointCostGroup_ID)
	{
		if (UNS_JointCostGroup_ID < 1) 
			set_Value (COLUMNNAME_UNS_JointCostGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_JointCostGroup_ID, Integer.valueOf(UNS_JointCostGroup_ID));
	}

	/** Get Joint Cost Group.
		@return Joint Cost Group
	  */
	public int getUNS_JointCostGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_JointCostGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}