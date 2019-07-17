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

/** Generated Model for UNS_CanteenActivity
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CanteenActivity extends PO implements I_UNS_CanteenActivity, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180209L;

    /** Standard Constructor */
    public X_UNS_CanteenActivity (Properties ctx, int UNS_CanteenActivity_ID, String trxName)
    {
      super (ctx, UNS_CanteenActivity_ID, trxName);
      /** if (UNS_CanteenActivity_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setIsBreakfast (false);
// N
			setIsDinner (false);
			setIsLunch (false);
// N
			setProcessed (false);
// N
			setUNS_CanteenActivity_ID (0);
			setUNS_Employee_ID (0);
			setUNS_MonthlyPresenceSummary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CanteenActivity (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CanteenActivity[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Breakfast.
		@param IsBreakfast Breakfast	  */
	public void setIsBreakfast (boolean IsBreakfast)
	{
		set_Value (COLUMNNAME_IsBreakfast, Boolean.valueOf(IsBreakfast));
	}

	/** Get Breakfast.
		@return Breakfast	  */
	public boolean isBreakfast () 
	{
		Object oo = get_Value(COLUMNNAME_IsBreakfast);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Dinner?.
		@param IsDinner Dinner?	  */
	public void setIsDinner (boolean IsDinner)
	{
		set_Value (COLUMNNAME_IsDinner, Boolean.valueOf(IsDinner));
	}

	/** Get Dinner?.
		@return Dinner?	  */
	public boolean isDinner () 
	{
		Object oo = get_Value(COLUMNNAME_IsDinner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lunch.
		@param IsLunch Lunch	  */
	public void setIsLunch (boolean IsLunch)
	{
		set_Value (COLUMNNAME_IsLunch, Boolean.valueOf(IsLunch));
	}

	/** Get Lunch.
		@return Lunch	  */
	public boolean isLunch () 
	{
		Object oo = get_Value(COLUMNNAME_IsLunch);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Canteen Activity.
		@param UNS_CanteenActivity_ID Canteen Activity	  */
	public void setUNS_CanteenActivity_ID (int UNS_CanteenActivity_ID)
	{
		if (UNS_CanteenActivity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CanteenActivity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CanteenActivity_ID, Integer.valueOf(UNS_CanteenActivity_ID));
	}

	/** Get Canteen Activity.
		@return Canteen Activity	  */
	public int getUNS_CanteenActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CanteenActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CanteenActivity_UU.
		@param UNS_CanteenActivity_UU UNS_CanteenActivity_UU	  */
	public void setUNS_CanteenActivity_UU (String UNS_CanteenActivity_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CanteenActivity_UU, UNS_CanteenActivity_UU);
	}

	/** Get UNS_CanteenActivity_UU.
		@return UNS_CanteenActivity_UU	  */
	public String getUNS_CanteenActivity_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CanteenActivity_UU);
	}

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

	/** Set Monthly Presence Summary.
		@param UNS_MonthlyPresenceSummary_ID Monthly Presence Summary	  */
	public void setUNS_MonthlyPresenceSummary_ID (int UNS_MonthlyPresenceSummary_ID)
	{
		if (UNS_MonthlyPresenceSummary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPresenceSummary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPresenceSummary_ID, Integer.valueOf(UNS_MonthlyPresenceSummary_ID));
	}

	/** Get Monthly Presence Summary.
		@return Monthly Presence Summary	  */
	public int getUNS_MonthlyPresenceSummary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MonthlyPresenceSummary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}