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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_ArmadaType
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ArmadaType extends PO implements I_UNS_ArmadaType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141126L;

    /** Standard Constructor */
    public X_UNS_ArmadaType (Properties ctx, int UNS_ArmadaType_ID, String trxName)
    {
      super (ctx, UNS_ArmadaType_ID, trxName);
      /** if (UNS_ArmadaType_ID == 0)
        {
			setName (null);
			setUNS_ArmadaType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ArmadaType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ArmadaType[")
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

	/** Set Max Tonase.
		@param MaxTon 
		Maximum Tonase
	  */
	public void setMaxTon (BigDecimal MaxTon)
	{
		set_Value (COLUMNNAME_MaxTon, MaxTon);
	}

	/** Get Max Tonase.
		@return Maximum Tonase
	  */
	public BigDecimal getMaxTon () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxTon);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max Volume.
		@param MaxVolume 
		Max Volume
	  */
	public void setMaxVolume (BigDecimal MaxVolume)
	{
		set_Value (COLUMNNAME_MaxVolume, MaxVolume);
	}

	/** Get Max Volume.
		@return Max Volume
	  */
	public BigDecimal getMaxVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxVolume);
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

	/** Set Armada Type.
		@param UNS_ArmadaType_ID Armada Type	  */
	public void setUNS_ArmadaType_ID (int UNS_ArmadaType_ID)
	{
		if (UNS_ArmadaType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ArmadaType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ArmadaType_ID, Integer.valueOf(UNS_ArmadaType_ID));
	}

	/** Get Armada Type.
		@return Armada Type	  */
	public int getUNS_ArmadaType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ArmadaType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ArmadaType_UU.
		@param UNS_ArmadaType_UU UNS_ArmadaType_UU	  */
	public void setUNS_ArmadaType_UU (String UNS_ArmadaType_UU)
	{
		set_Value (COLUMNNAME_UNS_ArmadaType_UU, UNS_ArmadaType_UU);
	}

	/** Get UNS_ArmadaType_UU.
		@return UNS_ArmadaType_UU	  */
	public String getUNS_ArmadaType_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ArmadaType_UU);
	}
}