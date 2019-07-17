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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_Resource_Machine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Resource_Machine extends PO implements I_UNS_Resource_Machine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130908L;

    /** Standard Constructor */
    public X_UNS_Resource_Machine (Properties ctx, int UNS_Resource_Machine_ID, String trxName)
    {
      super (ctx, UNS_Resource_Machine_ID, trxName);
      /** if (UNS_Resource_Machine_ID == 0)
        {
			setDescription (null);
			setName (null);
			setUNS_Resource_ID (0);
			setUNS_Resource_Machine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_Machine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_Machine[")
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

	/** Set Last Maintenance.
		@param LastMaintenanceDate 
		Last Maintenance Date
	  */
	public void setLastMaintenanceDate (Timestamp LastMaintenanceDate)
	{
		set_Value (COLUMNNAME_LastMaintenanceDate, LastMaintenanceDate);
	}

	/** Get Last Maintenance.
		@return Last Maintenance Date
	  */
	public Timestamp getLastMaintenanceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastMaintenanceDate);
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

	/** Set Next Maintenence.
		@param NextMaintenenceDate 
		Next Maintenence Date
	  */
	public void setNextMaintenenceDate (Timestamp NextMaintenenceDate)
	{
		set_Value (COLUMNNAME_NextMaintenenceDate, NextMaintenenceDate);
	}

	/** Get Next Maintenence.
		@return Next Maintenence Date
	  */
	public Timestamp getNextMaintenenceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NextMaintenenceDate);
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacture Resource Machine.
		@param UNS_Resource_Machine_ID Manufacture Resource Machine	  */
	public void setUNS_Resource_Machine_ID (int UNS_Resource_Machine_ID)
	{
		if (UNS_Resource_Machine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Machine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Machine_ID, Integer.valueOf(UNS_Resource_Machine_ID));
	}

	/** Get Manufacture Resource Machine.
		@return Manufacture Resource Machine	  */
	public int getUNS_Resource_Machine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Machine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Resource_Machine_UU.
		@param UNS_Resource_Machine_UU UNS_Resource_Machine_UU	  */
	public void setUNS_Resource_Machine_UU (String UNS_Resource_Machine_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_Machine_UU, UNS_Resource_Machine_UU);
	}

	/** Get UNS_Resource_Machine_UU.
		@return UNS_Resource_Machine_UU	  */
	public String getUNS_Resource_Machine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_Machine_UU);
	}
}