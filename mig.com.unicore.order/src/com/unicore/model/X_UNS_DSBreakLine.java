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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_DSBreakLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DSBreakLine extends PO implements I_UNS_DSBreakLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150411L;

    /** Standard Constructor */
    public X_UNS_DSBreakLine (Properties ctx, int UNS_DSBreakLine_ID, String trxName)
    {
      super (ctx, UNS_DSBreakLine_ID, trxName);
      /** if (UNS_DSBreakLine_ID == 0)
        {
			setBreakDiscount (Env.ZERO);
			setBreakValue (Env.ZERO);
			setBreakValueTo (Env.ZERO);
// 0
			setM_DiscountSchemaBreak_ID (0);
			setName (null);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM UNS_DSBreakLine WHERE M_DiscountSchemaBreak_ID=@M_DiscountSchemaBreak_ID@
			setUNS_DSBreakLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DSBreakLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DSBreakLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Discount Value.
		@param BreakDiscount 
		Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public void setBreakDiscount (BigDecimal BreakDiscount)
	{
		set_Value (COLUMNNAME_BreakDiscount, BreakDiscount);
	}

	/** Get Discount Value.
		@return Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public BigDecimal getBreakDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Value.
		@param BreakValue 
		Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	/** Get Break Value.
		@return Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Value To.
		@param BreakValueTo 
		Low Value of trade discount break level
	  */
	public void setBreakValueTo (BigDecimal BreakValueTo)
	{
		set_Value (COLUMNNAME_BreakValueTo, BreakValueTo);
	}

	/** Get Break Value To.
		@return Low Value of trade discount break level
	  */
	public BigDecimal getBreakValueTo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValueTo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 5th Discount %.
		@param FifthDiscount 
		Fifth discount percentage
	  */
	public void setFifthDiscount (BigDecimal FifthDiscount)
	{
		set_Value (COLUMNNAME_FifthDiscount, FifthDiscount);
	}

	/** Get 5th Discount %.
		@return Fifth discount percentage
	  */
	public BigDecimal getFifthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FifthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Discount %.
		@param FourthDiscount 
		Fourth discount percentage
	  */
	public void setFourthDiscount (BigDecimal FourthDiscount)
	{
		set_Value (COLUMNNAME_FourthDiscount, FourthDiscount);
	}

	/** Get 4th Discount %.
		@return Fourth discount percentage
	  */
	public BigDecimal getFourthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FourthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak() throws RuntimeException
    {
		return (com.unicore.model.I_M_DiscountSchemaBreak)MTable.get(getCtx(), com.unicore.model.I_M_DiscountSchemaBreak.Table_Name)
			.getPO(getM_DiscountSchemaBreak_ID(), get_TrxName());	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
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

	/** Set Number of Multiples.
		@param NofMultiples Number of Multiples	  */
	public void setNofMultiples (BigDecimal NofMultiples)
	{
		set_Value (COLUMNNAME_NofMultiples, NofMultiples);
	}

	/** Get Number of Multiples.
		@return Number of Multiples	  */
	public BigDecimal getNofMultiples () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NofMultiples);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getPBonus1() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus1_ID(), get_TrxName());	}

	/** Set 1st Product Bonus.
		@param PBonus1_ID 1st Product Bonus	  */
	public void setPBonus1_ID (int PBonus1_ID)
	{
		if (PBonus1_ID < 1) 
			set_Value (COLUMNNAME_PBonus1_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus1_ID, Integer.valueOf(PBonus1_ID));
	}

	/** Get 1st Product Bonus.
		@return 1st Product Bonus	  */
	public int getPBonus1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus2() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus2_ID(), get_TrxName());	}

	/** Set 2nd Product Bonus.
		@param PBonus2_ID 2nd Product Bonus	  */
	public void setPBonus2_ID (int PBonus2_ID)
	{
		if (PBonus2_ID < 1) 
			set_Value (COLUMNNAME_PBonus2_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus2_ID, Integer.valueOf(PBonus2_ID));
	}

	/** Get 2nd Product Bonus.
		@return 2nd Product Bonus	  */
	public int getPBonus2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus3() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus3_ID(), get_TrxName());	}

	/** Set 3rd Product Bonus.
		@param PBonus3_ID 3rd Product Bonus	  */
	public void setPBonus3_ID (int PBonus3_ID)
	{
		if (PBonus3_ID < 1) 
			set_Value (COLUMNNAME_PBonus3_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus3_ID, Integer.valueOf(PBonus3_ID));
	}

	/** Get 3rd Product Bonus.
		@return 3rd Product Bonus	  */
	public int getPBonus3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus4() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus4_ID(), get_TrxName());	}

	/** Set 4th Product Bonus.
		@param PBonus4_ID 4th Product Bonus	  */
	public void setPBonus4_ID (int PBonus4_ID)
	{
		if (PBonus4_ID < 1) 
			set_Value (COLUMNNAME_PBonus4_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus4_ID, Integer.valueOf(PBonus4_ID));
	}

	/** Get 4th Product Bonus.
		@return 4th Product Bonus	  */
	public int getPBonus4_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus4_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus5() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus5_ID(), get_TrxName());	}

	/** Set 5th Product Bonus.
		@param PBonus5_ID 5th Product Bonus	  */
	public void setPBonus5_ID (int PBonus5_ID)
	{
		if (PBonus5_ID < 1) 
			set_Value (COLUMNNAME_PBonus5_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus5_ID, Integer.valueOf(PBonus5_ID));
	}

	/** Get 5th Product Bonus.
		@return 5th Product Bonus	  */
	public int getPBonus5_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus5_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 1st Product Qty.
		@param PQty1_ID 1st Product Qty	  */
	public void setPQty1_ID (BigDecimal PQty1_ID)
	{
		set_Value (COLUMNNAME_PQty1_ID, PQty1_ID);
	}

	/** Get 1st Product Qty.
		@return 1st Product Qty	  */
	public BigDecimal getPQty1_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty1_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 2nd Product Qty.
		@param PQty2_ID 2nd Product Qty	  */
	public void setPQty2_ID (BigDecimal PQty2_ID)
	{
		set_Value (COLUMNNAME_PQty2_ID, PQty2_ID);
	}

	/** Get 2nd Product Qty.
		@return 2nd Product Qty	  */
	public BigDecimal getPQty2_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty2_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 3rd Product Qty.
		@param PQty3_ID 3rd Product Qty	  */
	public void setPQty3_ID (BigDecimal PQty3_ID)
	{
		set_Value (COLUMNNAME_PQty3_ID, PQty3_ID);
	}

	/** Get 3rd Product Qty.
		@return 3rd Product Qty	  */
	public BigDecimal getPQty3_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty3_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Product Qty.
		@param PQty4_ID 4th Product Qty	  */
	public void setPQty4_ID (BigDecimal PQty4_ID)
	{
		set_Value (COLUMNNAME_PQty4_ID, PQty4_ID);
	}

	/** Get 4th Product Qty.
		@return 4th Product Qty	  */
	public BigDecimal getPQty4_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty4_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 5th Product Qty.
		@param PQty5_ID 5th Product Qty	  */
	public void setPQty5_ID (BigDecimal PQty5_ID)
	{
		set_Value (COLUMNNAME_PQty5_ID, PQty5_ID);
	}

	/** Get 5th Product Qty.
		@return 5th Product Qty	  */
	public BigDecimal getPQty5_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty5_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
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

	/** Set 2nd Discount %.
		@param SecondDiscount 
		Second discount percentage
	  */
	public void setSecondDiscount (BigDecimal SecondDiscount)
	{
		set_Value (COLUMNNAME_SecondDiscount, SecondDiscount);
	}

	/** Get 2nd Discount %.
		@return Second discount percentage
	  */
	public BigDecimal getSecondDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SecondDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getSeqNo()));
    }

	/** Set 3rd Discount %.
		@param ThirdDiscount 
		Third discount percentage
	  */
	public void setThirdDiscount (BigDecimal ThirdDiscount)
	{
		set_Value (COLUMNNAME_ThirdDiscount, ThirdDiscount);
	}

	/** Get 3rd Discount %.
		@return Third discount percentage
	  */
	public BigDecimal getThirdDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ThirdDiscount);
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

	/** Set UNS_DSBreakLine_UU.
		@param UNS_DSBreakLine_UU UNS_DSBreakLine_UU	  */
	public void setUNS_DSBreakLine_UU (String UNS_DSBreakLine_UU)
	{
		set_Value (COLUMNNAME_UNS_DSBreakLine_UU, UNS_DSBreakLine_UU);
	}

	/** Get UNS_DSBreakLine_UU.
		@return UNS_DSBreakLine_UU	  */
	public String getUNS_DSBreakLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DSBreakLine_UU);
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
}