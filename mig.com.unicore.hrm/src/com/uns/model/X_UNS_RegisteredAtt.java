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

/** Generated Model for UNS_RegisteredAtt
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_RegisteredAtt extends PO implements I_UNS_RegisteredAtt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160516L;

    /** Standard Constructor */
    public X_UNS_RegisteredAtt (Properties ctx, int UNS_RegisteredAtt_ID, String trxName)
    {
      super (ctx, UNS_RegisteredAtt_ID, trxName);
      /** if (UNS_RegisteredAtt_ID == 0)
        {
			setC_Location_ID (0);
			setName (null);
			setSerialNo (null);
			setUNS_RegisteredAtt_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_RegisteredAtt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_RegisteredAtt[")
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

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_Location)MTable.get(getCtx(), org.compiere.model.I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	/** Set Serial No.
		@param SerialNo Serial No	  */
	public void setSerialNo (String SerialNo)
	{
		set_Value (COLUMNNAME_SerialNo, SerialNo);
	}

	/** Get Serial No.
		@return Serial No	  */
	public String getSerialNo () 
	{
		return (String)get_Value(COLUMNNAME_SerialNo);
	}

	/** Set Registered Attendance.
		@param UNS_RegisteredAtt_ID Registered Attendance	  */
	public void setUNS_RegisteredAtt_ID (int UNS_RegisteredAtt_ID)
	{
		if (UNS_RegisteredAtt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_RegisteredAtt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_RegisteredAtt_ID, Integer.valueOf(UNS_RegisteredAtt_ID));
	}

	/** Get Registered Attendance.
		@return Registered Attendance	  */
	public int getUNS_RegisteredAtt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_RegisteredAtt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Registered Attendance UU.
		@param UNS_RegisteredAtt_UU Registered Attendance UU	  */
	public void setUNS_RegisteredAtt_UU (String UNS_RegisteredAtt_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_RegisteredAtt_UU, UNS_RegisteredAtt_UU);
	}

	/** Get Registered Attendance UU.
		@return Registered Attendance UU	  */
	public String getUNS_RegisteredAtt_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_RegisteredAtt_UU);
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