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

/** Generated Model for UNS_CustomerRedeem_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerRedeem_Line extends PO implements I_UNS_CustomerRedeem_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150520L;

    /** Standard Constructor */
    public X_UNS_CustomerRedeem_Line (Properties ctx, int UNS_CustomerRedeem_Line_ID, String trxName)
    {
      super (ctx, UNS_CustomerRedeem_Line_ID, trxName);
      /** if (UNS_CustomerRedeem_Line_ID == 0)
        {
			setQty (Env.ZERO);
// 1
			setUNS_CustomerRedeem_Line_ID (0);
			setUNS_CustomerRedeem_Line_UU (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerRedeem_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerRedeem_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Balance Point.
		@param BalancePoint 
		Balance of Point
	  */
	public void setBalancePoint (BigDecimal BalancePoint)
	{
		throw new IllegalArgumentException ("BalancePoint is virtual column");	}

	/** Get Balance Point.
		@return Balance of Point
	  */
	public BigDecimal getBalancePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BalancePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CurrentPoint.
		@param CurrentPoint CurrentPoint	  */
	public void setCurrentPoint (BigDecimal CurrentPoint)
	{
		set_Value (COLUMNNAME_CurrentPoint, CurrentPoint);
	}

	/** Get CurrentPoint.
		@return CurrentPoint	  */
	public BigDecimal getCurrentPoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentPoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_CustomerRedeem getUNS_CustomerRedeem() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CustomerRedeem)MTable.get(getCtx(), com.unicore.model.I_UNS_CustomerRedeem.Table_Name)
			.getPO(getUNS_CustomerRedeem_ID(), get_TrxName());	}

	/** Set Customer Redeem Point.
		@param UNS_CustomerRedeem_ID Customer Redeem Point	  */
	public void setUNS_CustomerRedeem_ID (int UNS_CustomerRedeem_ID)
	{
		if (UNS_CustomerRedeem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, Integer.valueOf(UNS_CustomerRedeem_ID));
	}

	/** Get Customer Redeem Point.
		@return Customer Redeem Point	  */
	public int getUNS_CustomerRedeem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerRedeem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Redeem Point.
		@param UNS_CustomerRedeem_Line_ID Customer Redeem Point	  */
	public void setUNS_CustomerRedeem_Line_ID (int UNS_CustomerRedeem_Line_ID)
	{
		if (UNS_CustomerRedeem_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_Line_ID, Integer.valueOf(UNS_CustomerRedeem_Line_ID));
	}

	/** Get Customer Redeem Point.
		@return Customer Redeem Point	  */
	public int getUNS_CustomerRedeem_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerRedeem_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CustomerRedeem_Line_UU.
		@param UNS_CustomerRedeem_Line_UU UNS_CustomerRedeem_Line_UU	  */
	public void setUNS_CustomerRedeem_Line_UU (String UNS_CustomerRedeem_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_Line_UU, UNS_CustomerRedeem_Line_UU);
	}

	/** Get UNS_CustomerRedeem_Line_UU.
		@return UNS_CustomerRedeem_Line_UU	  */
	public String getUNS_CustomerRedeem_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerRedeem_Line_UU);
	}

	public com.unicore.model.I_UNS_ItemReward_Selection getUNS_ItemReward_Selection() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ItemReward_Selection)MTable.get(getCtx(), com.unicore.model.I_UNS_ItemReward_Selection.Table_Name)
			.getPO(getUNS_ItemReward_Selection_ID(), get_TrxName());	}

	/** Set Item Reward Selection.
		@param UNS_ItemReward_Selection_ID Item Reward Selection	  */
	public void setUNS_ItemReward_Selection_ID (int UNS_ItemReward_Selection_ID)
	{
		if (UNS_ItemReward_Selection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ItemReward_Selection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ItemReward_Selection_ID, Integer.valueOf(UNS_ItemReward_Selection_ID));
	}

	/** Get Item Reward Selection.
		@return Item Reward Selection	  */
	public int getUNS_ItemReward_Selection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ItemReward_Selection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ValuePoint.
		@param ValuePoint ValuePoint	  */
	public void setValuePoint (BigDecimal ValuePoint)
	{
		set_Value (COLUMNNAME_ValuePoint, ValuePoint);
	}

	/** Get ValuePoint.
		@return ValuePoint	  */
	public BigDecimal getValuePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValuePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}