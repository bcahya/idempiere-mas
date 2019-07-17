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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Machine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Machine extends PO implements I_UNS_Machine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130111L;

    /** Standard Constructor */
    public X_UNS_Machine (Properties ctx, int UNS_Machine_ID, String trxName)
    {
      super (ctx, UNS_Machine_ID, trxName);
      /** if (UNS_Machine_ID == 0)
        {
			setA_Asset_ID (0);
			setIsTimeSlot (false);
			setName (null);
			setUNS_Machine_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Machine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Machine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
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

	/** Set Time Slot.
		@param IsTimeSlot 
		Resource has time slot availability
	  */
	public void setIsTimeSlot (boolean IsTimeSlot)
	{
		set_Value (COLUMNNAME_IsTimeSlot, Boolean.valueOf(IsTimeSlot));
	}

	/** Get Time Slot.
		@return Resource has time slot availability
	  */
	public boolean isTimeSlot () 
	{
		Object oo = get_Value(COLUMNNAME_IsTimeSlot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max. Capaciy.
		@param MaxCaps Max. Capaciy	  */
	public void setMaxCaps (BigDecimal MaxCaps)
	{
		set_Value (COLUMNNAME_MaxCaps, MaxCaps);
	}

	/** Get Max. Capaciy.
		@return Max. Capaciy	  */
	public BigDecimal getMaxCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min. Capaciy.
		@param MinCaps Min. Capaciy	  */
	public void setMinCaps (BigDecimal MinCaps)
	{
		set_Value (COLUMNNAME_MinCaps, MinCaps);
	}

	/** Get Min. Capaciy.
		@return Min. Capaciy	  */
	public BigDecimal getMinCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinCaps);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Slot End.
		@param TimeSlotEnd 
		Time when timeslot ends
	  */
	public void setTimeSlotEnd (Timestamp TimeSlotEnd)
	{
		set_Value (COLUMNNAME_TimeSlotEnd, TimeSlotEnd);
	}

	/** Get Slot End.
		@return Time when timeslot ends
	  */
	public Timestamp getTimeSlotEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEnd);
	}

	/** Set Slot Start.
		@param TimeSlotStart 
		Time when timeslot starts
	  */
	public void setTimeSlotStart (Timestamp TimeSlotStart)
	{
		set_Value (COLUMNNAME_TimeSlotStart, TimeSlotStart);
	}

	/** Get Slot Start.
		@return Time when timeslot starts
	  */
	public Timestamp getTimeSlotStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStart);
	}

	/** Set Machine.
		@param UNS_Machine_ID Machine	  */
	public void setUNS_Machine_ID (int UNS_Machine_ID)
	{
		if (UNS_Machine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Machine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Machine_ID, Integer.valueOf(UNS_Machine_ID));
	}

	/** Get Machine.
		@return Machine	  */
	public int getUNS_Machine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Machine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Machine_UU.
		@param UNS_Machine_UU UNS_Machine_UU	  */
	public void setUNS_Machine_UU (String UNS_Machine_UU)
	{
		set_Value (COLUMNNAME_UNS_Machine_UU, UNS_Machine_UU);
	}

	/** Get UNS_Machine_UU.
		@return UNS_Machine_UU	  */
	public String getUNS_Machine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Machine_UU);
	}

	public org.compiere.model.I_C_UOM getUOMC() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMCaps(), get_TrxName());	}

	/** Set UOM Capaciy.
		@param UOMCaps UOM Capaciy	  */
	public void setUOMCaps (int UOMCaps)
	{
		set_Value (COLUMNNAME_UOMCaps, Integer.valueOf(UOMCaps));
	}

	/** Get UOM Capaciy.
		@return UOM Capaciy	  */
	public int getUOMCaps () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMCaps);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMT() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMTime(), get_TrxName());	}

	/** Set UOM Time.
		@param UOMTime UOM Time	  */
	public void setUOMTime (int UOMTime)
	{
		set_Value (COLUMNNAME_UOMTime, Integer.valueOf(UOMTime));
	}

	/** Get UOM Time.
		@return UOM Time	  */
	public int getUOMTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMTime);
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