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

/** Generated Model for UNS_ItemReward_Selection
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ItemReward_Selection extends PO implements I_UNS_ItemReward_Selection, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150520L;

    /** Standard Constructor */
    public X_UNS_ItemReward_Selection (Properties ctx, int UNS_ItemReward_Selection_ID, String trxName)
    {
      super (ctx, UNS_ItemReward_Selection_ID, trxName);
      /** if (UNS_ItemReward_Selection_ID == 0)
        {
			setUNS_ItemReward_Selection_ID (0);
			setUNS_ItemReward_Selection_UU (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_ItemReward_Selection (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ItemReward_Selection[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UNS_ItemReward_Selection_UU.
		@param UNS_ItemReward_Selection_UU UNS_ItemReward_Selection_UU	  */
	public void setUNS_ItemReward_Selection_UU (String UNS_ItemReward_Selection_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ItemReward_Selection_UU, UNS_ItemReward_Selection_UU);
	}

	/** Get UNS_ItemReward_Selection_UU.
		@return UNS_ItemReward_Selection_UU	  */
	public String getUNS_ItemReward_Selection_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ItemReward_Selection_UU);
	}

	public com.unicore.model.I_UNS_PeriodRedeem getUNS_PeriodRedeem() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PeriodRedeem)MTable.get(getCtx(), com.unicore.model.I_UNS_PeriodRedeem.Table_Name)
			.getPO(getUNS_PeriodRedeem_ID(), get_TrxName());	}

	/** Set Period Redeem Point.
		@param UNS_PeriodRedeem_ID Period Redeem Point	  */
	public void setUNS_PeriodRedeem_ID (int UNS_PeriodRedeem_ID)
	{
		if (UNS_PeriodRedeem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodRedeem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodRedeem_ID, Integer.valueOf(UNS_PeriodRedeem_ID));
	}

	/** Get Period Redeem Point.
		@return Period Redeem Point	  */
	public int getUNS_PeriodRedeem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PeriodRedeem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Value Point.
		@param ValuePoint Value Point	  */
	public void setValuePoint (BigDecimal ValuePoint)
	{
		set_Value (COLUMNNAME_ValuePoint, ValuePoint);
	}

	/** Get Value Point.
		@return Value Point	  */
	public BigDecimal getValuePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValuePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}