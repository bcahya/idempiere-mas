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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_SalesBudget
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SalesBudget extends PO implements I_UNS_SalesBudget, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150402L;

    /** Standard Constructor */
    public X_UNS_SalesBudget (Properties ctx, int UNS_SalesBudget_ID, String trxName)
    {
      super (ctx, UNS_SalesBudget_ID, trxName);
      /** if (UNS_SalesBudget_ID == 0)
        {
			setProcessed (false);
// N
			setUNS_SalesBudget_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SalesBudget (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_SalesBudget[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Discount Schema.
		@return Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_ValueNoCheck (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Qty Allocated.
		@param QtyAllocated Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated)
	{
		set_Value (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Qty Allocated.
		@return Qty Allocated	  */
	public BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reserved Quantity.
		@param QtyReserved 
		Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reserved Quantity.
		@return Reserved Quantity
	  */
	public BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unallocated Budget.
		@param UnallocatedBudget Unallocated Budget	  */
	public void setUnallocatedBudget (BigDecimal UnallocatedBudget)
	{
		throw new IllegalArgumentException ("UnallocatedBudget is virtual column");	}

	/** Get Unallocated Budget.
		@return Unallocated Budget	  */
	public BigDecimal getUnallocatedBudget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnallocatedBudget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount Break Line.
		@param UNS_DSBreakLine_ID Discount Break Line	  */
	public void setUNS_DSBreakLine_ID (int UNS_DSBreakLine_ID)
	{
		if (UNS_DSBreakLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, Integer.valueOf(UNS_DSBreakLine_ID));
	}

	/** Get Discount Break Line.
		@return Discount Break Line	  */
	public int getUNS_DSBreakLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DSBreakLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Outlet Grade.
		@param UNS_Outlet_Grade_ID Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID)
	{
		if (UNS_Outlet_Grade_ID < 1) 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, Integer.valueOf(UNS_Outlet_Grade_ID));
	}

	/** Get Outlet Grade.
		@return Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Outlet_Grade_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Budget.
		@param UNS_SalesBudget_ID Sales Budget	  */
	public void setUNS_SalesBudget_ID (int UNS_SalesBudget_ID)
	{
		if (UNS_SalesBudget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesBudget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesBudget_ID, Integer.valueOf(UNS_SalesBudget_ID));
	}

	/** Get Sales Budget.
		@return Sales Budget	  */
	public int getUNS_SalesBudget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesBudget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Budget UU.
		@param UNS_SalesBudget_UU Sales Budget UU	  */
	public void setUNS_SalesBudget_UU (String UNS_SalesBudget_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_SalesBudget_UU, UNS_SalesBudget_UU);
	}

	/** Get Sales Budget UU.
		@return Sales Budget UU	  */
	public String getUNS_SalesBudget_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SalesBudget_UU);
	}
}