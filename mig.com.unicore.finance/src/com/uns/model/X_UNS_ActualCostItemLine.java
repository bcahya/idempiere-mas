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

/** Generated Model for UNS_ActualCostItemLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ActualCostItemLine extends PO implements I_UNS_ActualCostItemLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130630L;

    /** Standard Constructor */
    public X_UNS_ActualCostItemLine (Properties ctx, int UNS_ActualCostItemLine_ID, String trxName)
    {
      super (ctx, UNS_ActualCostItemLine_ID, trxName);
      /** if (UNS_ActualCostItemLine_ID == 0)
        {
			setAdjustedCostPrice (Env.ZERO);
// @CurrentCost@
			setC_ElementValue_ID (0);
			setName (null);
			setProductType (null);
// NO
			setUNS_ActualCostItemLine_ID (0);
			setUNS_ActualCostReconcile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ActualCostItemLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ActualCostItemLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Cost.
		@param ActualCost 
		Actual Cost
	  */
	public void setActualCost (BigDecimal ActualCost)
	{
		set_Value (COLUMNNAME_ActualCost, ActualCost);
	}

	/** Get Actual Cost.
		@return Actual Cost
	  */
	public BigDecimal getActualCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Cost Variance.
		@param AllocatedCost 
		Difference of Actual Cost to Current Cost
	  */
	public void setAllocatedCost (BigDecimal AllocatedCost)
	{
		set_ValueNoCheck (COLUMNNAME_AllocatedCost, AllocatedCost);
	}

	/** Get Cost Variance.
		@return Difference of Actual Cost to Current Cost
	  */
	public BigDecimal getAllocatedCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllocatedCost);
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

	/** Set Current Cost.
		@param CurrentCost 
		Current Cost
	  */
	public void setCurrentCost (BigDecimal CurrentCost)
	{
		set_Value (COLUMNNAME_CurrentCost, CurrentCost);
	}

	/** Get Current Cost.
		@return Current Cost
	  */
	public BigDecimal getCurrentCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Current Used Cost.
		@param CurrentUsedCost 
		Current Used Cost
	  */
	public void setCurrentUsedCost (BigDecimal CurrentUsedCost)
	{
		set_ValueNoCheck (COLUMNNAME_CurrentUsedCost, CurrentUsedCost);
	}

	/** Get Current Used Cost.
		@return Current Used Cost
	  */
	public BigDecimal getCurrentUsedCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentUsedCost);
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

	/** ProductType AD_Reference_ID=1000114 */
	public static final int PRODUCTTYPE_AD_Reference_ID=1000114;
	/** Service-Man PerHour = SERV */
	public static final String PRODUCTTYPE_Service_ManPerHour = "SERV";
	/** No Specific Consumption Type = NO */
	public static final String PRODUCTTYPE_NoSpecificConsumptionType = "NO";
	/** Work In Process = WIP */
	public static final String PRODUCTTYPE_WorkInProcess = "WIP";
	/** Finished Goods = FG */
	public static final String PRODUCTTYPE_FinishedGoods = "FG";
	/** Raw Material = RM */
	public static final String PRODUCTTYPE_RawMaterial = "RM";
	/** Utilities Consumption = UTL */
	public static final String PRODUCTTYPE_UtilitiesConsumption = "UTL";
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

	public com.uns.model.I_UNS_ActualCostItem getUNS_ActualCostItem() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ActualCostItem)MTable.get(getCtx(), com.uns.model.I_UNS_ActualCostItem.Table_Name)
			.getPO(getUNS_ActualCostItem_ID(), get_TrxName());	}

	/** Set Actual Cost Item.
		@param UNS_ActualCostItem_ID 
		Actual Cost Item
	  */
	public void setUNS_ActualCostItem_ID (int UNS_ActualCostItem_ID)
	{
		if (UNS_ActualCostItem_ID < 1) 
			set_Value (COLUMNNAME_UNS_ActualCostItem_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ActualCostItem_ID, Integer.valueOf(UNS_ActualCostItem_ID));
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

	/** Set Actual Cost Item Line.
		@param UNS_ActualCostItemLine_ID Actual Cost Item Line	  */
	public void setUNS_ActualCostItemLine_ID (int UNS_ActualCostItemLine_ID)
	{
		if (UNS_ActualCostItemLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostItemLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostItemLine_ID, Integer.valueOf(UNS_ActualCostItemLine_ID));
	}

	/** Get Actual Cost Item Line.
		@return Actual Cost Item Line	  */
	public int getUNS_ActualCostItemLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostItemLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ActualCostItemLine_UU.
		@param UNS_ActualCostItemLine_UU 
		UNS_ActualCostItemLine_UU
	  */
	public void setUNS_ActualCostItemLine_UU (String UNS_ActualCostItemLine_UU)
	{
		set_Value (COLUMNNAME_UNS_ActualCostItemLine_UU, UNS_ActualCostItemLine_UU);
	}

	/** Get UNS_ActualCostItemLine_UU.
		@return UNS_ActualCostItemLine_UU
	  */
	public String getUNS_ActualCostItemLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ActualCostItemLine_UU);
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