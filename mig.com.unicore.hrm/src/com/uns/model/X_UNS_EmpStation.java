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

/** Generated Model for UNS_EmpStation
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_EmpStation extends PO implements I_UNS_EmpStation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160516L;

    /** Standard Constructor */
    public X_UNS_EmpStation (Properties ctx, int UNS_EmpStation_ID, String trxName)
    {
      super (ctx, UNS_EmpStation_ID, trxName);
      /** if (UNS_EmpStation_ID == 0)
        {
			setUNS_EmpStation_ID (0);
			setUNS_SlotType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_EmpStation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_EmpStation[")
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

	/** Set Station.
		@param UNS_EmpStation_ID Station	  */
	public void setUNS_EmpStation_ID (int UNS_EmpStation_ID)
	{
		if (UNS_EmpStation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, Integer.valueOf(UNS_EmpStation_ID));
	}

	/** Get Station.
		@return Station	  */
	public int getUNS_EmpStation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmpStation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_EmpStation_UU.
		@param UNS_EmpStation_UU UNS_EmpStation_UU	  */
	public void setUNS_EmpStation_UU (String UNS_EmpStation_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_UU, UNS_EmpStation_UU);
	}

	/** Get UNS_EmpStation_UU.
		@return UNS_EmpStation_UU	  */
	public String getUNS_EmpStation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_EmpStation_UU);
	}

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_Value (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
	}

	/** Get Slot Type.
		@return Slot Type	  */
	public int getUNS_SlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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