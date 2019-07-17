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

/** Generated Model for UNS_StationSlotType
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_StationSlotType extends PO implements I_UNS_StationSlotType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171017L;

    /** Standard Constructor */
    public X_UNS_StationSlotType (Properties ctx, int UNS_StationSlotType_ID, String trxName)
    {
      super (ctx, UNS_StationSlotType_ID, trxName);
      /** if (UNS_StationSlotType_ID == 0)
        {
			setUNS_EmpStation_ID (0);
			setUNS_SlotType_ID (0);
			setUNS_StationSlotType_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_StationSlotType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_StationSlotType[")
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

	public com.uns.model.I_UNS_EmpStation getUNS_EmpStation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_EmpStation)MTable.get(getCtx(), com.uns.model.I_UNS_EmpStation.Table_Name)
			.getPO(getUNS_EmpStation_ID(), get_TrxName());	}

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

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
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

	/** Set Station Slot Type.
		@param UNS_StationSlotType_ID Station Slot Type	  */
	public void setUNS_StationSlotType_ID (int UNS_StationSlotType_ID)
	{
		if (UNS_StationSlotType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_StationSlotType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_StationSlotType_ID, Integer.valueOf(UNS_StationSlotType_ID));
	}

	/** Get Station Slot Type.
		@return Station Slot Type	  */
	public int getUNS_StationSlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_StationSlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_StationSlotType_UU.
		@param UNS_StationSlotType_UU UNS_StationSlotType_UU	  */
	public void setUNS_StationSlotType_UU (String UNS_StationSlotType_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_StationSlotType_UU, UNS_StationSlotType_UU);
	}

	/** Get UNS_StationSlotType_UU.
		@return UNS_StationSlotType_UU	  */
	public String getUNS_StationSlotType_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_StationSlotType_UU);
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

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}