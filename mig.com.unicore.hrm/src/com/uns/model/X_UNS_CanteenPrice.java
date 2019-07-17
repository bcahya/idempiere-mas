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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_CanteenPrice
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CanteenPrice extends PO implements I_UNS_CanteenPrice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180209L;

    /** Standard Constructor */
    public X_UNS_CanteenPrice (Properties ctx, int UNS_CanteenPrice_ID, String trxName)
    {
      super (ctx, UNS_CanteenPrice_ID, trxName);
      /** if (UNS_CanteenPrice_ID == 0)
        {
			setBreakfast (Env.ZERO);
// 0
			setDinner (Env.ZERO);
// 0
			setLunch (Env.ZERO);
// 0
			setUNS_CanteenPrice_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
// @#Date@
        } */
    }

    /** Load Constructor */
    public X_UNS_CanteenPrice (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CanteenPrice[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Breakfast.
		@param Breakfast Breakfast	  */
	public void setBreakfast (BigDecimal Breakfast)
	{
		set_Value (COLUMNNAME_Breakfast, Breakfast);
	}

	/** Get Breakfast.
		@return Breakfast	  */
	public BigDecimal getBreakfast () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Breakfast);
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

	/** Set Dinner.
		@param Dinner Dinner	  */
	public void setDinner (BigDecimal Dinner)
	{
		set_Value (COLUMNNAME_Dinner, Dinner);
	}

	/** Get Dinner.
		@return Dinner	  */
	public BigDecimal getDinner () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Dinner);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Lunch.
		@param Lunch Lunch	  */
	public void setLunch (BigDecimal Lunch)
	{
		set_Value (COLUMNNAME_Lunch, Lunch);
	}

	/** Get Lunch.
		@return Lunch	  */
	public BigDecimal getLunch () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Lunch);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Canteen Price.
		@param UNS_CanteenPrice_ID Canteen Price	  */
	public void setUNS_CanteenPrice_ID (int UNS_CanteenPrice_ID)
	{
		if (UNS_CanteenPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CanteenPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CanteenPrice_ID, Integer.valueOf(UNS_CanteenPrice_ID));
	}

	/** Get Canteen Price.
		@return Canteen Price	  */
	public int getUNS_CanteenPrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CanteenPrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CanteenPrice_UU.
		@param UNS_CanteenPrice_UU UNS_CanteenPrice_UU	  */
	public void setUNS_CanteenPrice_UU (String UNS_CanteenPrice_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CanteenPrice_UU, UNS_CanteenPrice_UU);
	}

	/** Get UNS_CanteenPrice_UU.
		@return UNS_CanteenPrice_UU	  */
	public String getUNS_CanteenPrice_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CanteenPrice_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}