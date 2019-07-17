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

/** Generated Model for UNS_RecidenceGroup
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_RecidenceGroup extends PO implements I_UNS_RecidenceGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130521L;

    /** Standard Constructor */
    public X_UNS_RecidenceGroup (Properties ctx, int UNS_RecidenceGroup_ID, String trxName)
    {
      super (ctx, UNS_RecidenceGroup_ID, trxName);
      /** if (UNS_RecidenceGroup_ID == 0)
        {
			setAddress (0);
			setAvailableToOccupy (0);
			setMaximumToOccupy (0);
			setName (null);
			setOccupiedByOccupants (0);
			setUNS_RecidenceGroup_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_RecidenceGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_RecidenceGroup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Location getAddr() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getAddress(), get_TrxName());	}

	/** Set Address.
		@param Address Address	  */
	public void setAddress (int Address)
	{
		set_Value (COLUMNNAME_Address, Integer.valueOf(Address));
	}

	/** Get Address.
		@return Address	  */
	public int getAddress () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Address);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Available To Occupy.
		@param AvailableToOccupy Available To Occupy	  */
	public void setAvailableToOccupy (int AvailableToOccupy)
	{
		set_Value (COLUMNNAME_AvailableToOccupy, Integer.valueOf(AvailableToOccupy));
	}

	/** Get Available To Occupy.
		@return Available To Occupy	  */
	public int getAvailableToOccupy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AvailableToOccupy);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Maximum To Occupy.
		@param MaximumToOccupy Maximum To Occupy	  */
	public void setMaximumToOccupy (int MaximumToOccupy)
	{
		set_Value (COLUMNNAME_MaximumToOccupy, Integer.valueOf(MaximumToOccupy));
	}

	/** Get Maximum To Occupy.
		@return Maximum To Occupy	  */
	public int getMaximumToOccupy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaximumToOccupy);
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

	/** Set Occuped By Occupants.
		@param OccupiedByOccupants Occuped By Occupants	  */
	public void setOccupiedByOccupants (int OccupiedByOccupants)
	{
		set_Value (COLUMNNAME_OccupiedByOccupants, Integer.valueOf(OccupiedByOccupants));
	}

	/** Get Occuped By Occupants.
		@return Occuped By Occupants	  */
	public int getOccupiedByOccupants () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OccupiedByOccupants);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Recidence Group.
		@param UNS_RecidenceGroup_ID Recidence Group	  */
	public void setUNS_RecidenceGroup_ID (int UNS_RecidenceGroup_ID)
	{
		if (UNS_RecidenceGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_RecidenceGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_RecidenceGroup_ID, Integer.valueOf(UNS_RecidenceGroup_ID));
	}

	/** Get Recidence Group.
		@return Recidence Group	  */
	public int getUNS_RecidenceGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_RecidenceGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_RecidenceGroup_UU.
		@param UNS_RecidenceGroup_UU UNS_RecidenceGroup_UU	  */
	public void setUNS_RecidenceGroup_UU (String UNS_RecidenceGroup_UU)
	{
		set_Value (COLUMNNAME_UNS_RecidenceGroup_UU, UNS_RecidenceGroup_UU);
	}

	/** Get UNS_RecidenceGroup_UU.
		@return UNS_RecidenceGroup_UU	  */
	public String getUNS_RecidenceGroup_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_RecidenceGroup_UU);
	}
}