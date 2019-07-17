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

/** Generated Model for UNS_Golongan
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Golongan extends PO implements I_UNS_Golongan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150329L;

    /** Standard Constructor */
    public X_UNS_Golongan (Properties ctx, int UNS_Golongan_ID, String trxName)
    {
      super (ctx, UNS_Golongan_ID, trxName);
      /** if (UNS_Golongan_ID == 0)
        {
			setUNS_Golongan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Golongan (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Golongan[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Compulsory Saving Amount .
		@param UNS_CompulsorySavingAmount Compulsory Saving Amount 	  */
	public void setUNS_CompulsorySavingAmount (BigDecimal UNS_CompulsorySavingAmount)
	{
		set_Value (COLUMNNAME_UNS_CompulsorySavingAmount, UNS_CompulsorySavingAmount);
	}

	/** Get Compulsory Saving Amount .
		@return Compulsory Saving Amount 	  */
	public BigDecimal getUNS_CompulsorySavingAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UNS_CompulsorySavingAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Golongan ID.
		@param UNS_Golongan_ID Golongan ID	  */
	public void setUNS_Golongan_ID (int UNS_Golongan_ID)
	{
		if (UNS_Golongan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, Integer.valueOf(UNS_Golongan_ID));
	}

	/** Get Golongan ID.
		@return Golongan ID	  */
	public int getUNS_Golongan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Golongan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Golongan UU.
		@param UNS_Golongan_UU Golongan UU	  */
	public void setUNS_Golongan_UU (String UNS_Golongan_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Golongan_UU, UNS_Golongan_UU);
	}

	/** Get Golongan UU.
		@return Golongan UU	  */
	public String getUNS_Golongan_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Golongan_UU);
	}

	/** Set Primary Saving Amount.
		@param UNS_PrimarySavingAmount Primary Saving Amount	  */
	public void setUNS_PrimarySavingAmount (BigDecimal UNS_PrimarySavingAmount)
	{
		set_Value (COLUMNNAME_UNS_PrimarySavingAmount, UNS_PrimarySavingAmount);
	}

	/** Get Primary Saving Amount.
		@return Primary Saving Amount	  */
	public BigDecimal getUNS_PrimarySavingAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UNS_PrimarySavingAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Primary Saving On Registered.
		@param UNS_PSavingOnRegistered Primary Saving On Registered	  */
	public void setUNS_PSavingOnRegistered (boolean UNS_PSavingOnRegistered)
	{
		set_Value (COLUMNNAME_UNS_PSavingOnRegistered, Boolean.valueOf(UNS_PSavingOnRegistered));
	}

	/** Get Primary Saving On Registered.
		@return Primary Saving On Registered	  */
	public boolean isUNS_PSavingOnRegistered () 
	{
		Object oo = get_Value(COLUMNNAME_UNS_PSavingOnRegistered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Primary Saving Required.
		@param UNS_PSavingRequired 
		Primary Saving Requierd when Members Registration
	  */
	public void setUNS_PSavingRequired (boolean UNS_PSavingRequired)
	{
		set_Value (COLUMNNAME_UNS_PSavingRequired, Boolean.valueOf(UNS_PSavingRequired));
	}

	/** Get Primary Saving Required.
		@return Primary Saving Requierd when Members Registration
	  */
	public boolean isUNS_PSavingRequired () 
	{
		Object oo = get_Value(COLUMNNAME_UNS_PSavingRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}