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

/** Generated Model for UNS_DailyPresenceVal
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DailyPresenceVal extends PO implements I_UNS_DailyPresenceVal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160630L;

    /** Standard Constructor */
    public X_UNS_DailyPresenceVal (Properties ctx, int UNS_DailyPresenceVal_ID, String trxName)
    {
      super (ctx, UNS_DailyPresenceVal_ID, trxName);
      /** if (UNS_DailyPresenceVal_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_UNS_DailyPresenceVal (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DailyPresenceVal[")
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

	/** Set Only Validate Data.
		@param IsValidateOnly 
		Validate the date and do not process
	  */
	public void setIsValidateOnly (boolean IsValidateOnly)
	{
		set_Value (COLUMNNAME_IsValidateOnly, Boolean.valueOf(IsValidateOnly));
	}

	/** Get Only Validate Data.
		@return Validate the date and do not process
	  */
	public boolean isValidateOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsValidateOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Full Day = FLD */
	public static final String PRESENCESTATUS_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String PRESENCESTATUS_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String PRESENCESTATUS_Izin = "IZN";
	/** Libur = LBR */
	public static final String PRESENCESTATUS_Libur = "LBR";
	/** Lembur = LMR */
	public static final String PRESENCESTATUS_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String PRESENCESTATUS_Mangkir = "MKR";
	/** Reversed = RVD */
	public static final String PRESENCESTATUS_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String PRESENCESTATUS_Reversal = "RVL";
	/** Belated = BLD */
	public static final String PRESENCESTATUS_Belated = "BLD";
	/** Set Presence Status.
		@param PresenceStatus Presence Status	  */
	public void setPresenceStatus (String PresenceStatus)
	{

		set_Value (COLUMNNAME_PresenceStatus, PresenceStatus);
	}

	/** Get Presence Status.
		@return Presence Status	  */
	public String getPresenceStatus () 
	{
		return (String)get_Value(COLUMNNAME_PresenceStatus);
	}

	/** Set Daily Presence.
		@param UNS_DailyPresence_ID Daily Presence	  */
	public void setUNS_DailyPresence_ID (int UNS_DailyPresence_ID)
	{
		if (UNS_DailyPresence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresence_ID, Integer.valueOf(UNS_DailyPresence_ID));
	}

	/** Get Daily Presence.
		@return Daily Presence	  */
	public int getUNS_DailyPresence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DailyPresence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Daily Presence Val UU.
		@param UNS_DailyPresenceVal_ID Daily Presence Val UU	  */
	public void setUNS_DailyPresenceVal_ID (int UNS_DailyPresenceVal_ID)
	{
		if (UNS_DailyPresenceVal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresenceVal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresenceVal_ID, Integer.valueOf(UNS_DailyPresenceVal_ID));
	}

	/** Get Daily Presence Val UU.
		@return Daily Presence Val UU	  */
	public int getUNS_DailyPresenceVal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DailyPresenceVal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS Daily Presence Val.
		@param UNS_DailyPresenceVal_UU UNS Daily Presence Val	  */
	public void setUNS_DailyPresenceVal_UU (String UNS_DailyPresenceVal_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_DailyPresenceVal_UU, UNS_DailyPresenceVal_UU);
	}

	/** Get UNS Daily Presence Val.
		@return UNS Daily Presence Val	  */
	public String getUNS_DailyPresenceVal_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DailyPresenceVal_UU);
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}