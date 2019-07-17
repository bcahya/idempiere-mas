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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Mess
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Mess extends PO implements I_UNS_Mess, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130228L;

    /** Standard Constructor */
    public X_UNS_Mess (Properties ctx, int UNS_Mess_ID, String trxName)
    {
      super (ctx, UNS_Mess_ID, trxName);
      /** if (UNS_Mess_ID == 0)
        {
			setUNS_Mess_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Mess (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Mess[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Blok.
		@param Blok Blok	  */
	public void setBlok (String Blok)
	{
		set_Value (COLUMNNAME_Blok, Blok);
	}

	/** Get Blok.
		@return Blok	  */
	public String getBlok () 
	{
		return (String)get_Value(COLUMNNAME_Blok);
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

	/** Set Nomer.
		@param Nomer Nomer	  */
	public void setNomer (String Nomer)
	{
		set_Value (COLUMNNAME_Nomer, Nomer);
	}

	/** Get Nomer.
		@return Nomer	  */
	public String getNomer () 
	{
		return (String)get_Value(COLUMNNAME_Nomer);
	}

	/** Set UNS_Mess_ID.
		@param UNS_Mess_ID UNS_Mess_ID	  */
	public void setUNS_Mess_ID (int UNS_Mess_ID)
	{
		if (UNS_Mess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_ID, Integer.valueOf(UNS_Mess_ID));
	}

	/** Get UNS_Mess_ID.
		@return UNS_Mess_ID	  */
	public int getUNS_Mess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Mess_UU.
		@param UNS_Mess_UU UNS_Mess_UU	  */
	public void setUNS_Mess_UU (String UNS_Mess_UU)
	{
		set_Value (COLUMNNAME_UNS_Mess_UU, UNS_Mess_UU);
	}

	/** Get UNS_Mess_UU.
		@return UNS_Mess_UU	  */
	public String getUNS_Mess_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Mess_UU);
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