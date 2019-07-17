/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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

/** Generated Model for UNS_Day
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UNS_Day extends PO implements I_UNS_Day, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121113L;

    /** Standard Constructor */
    public X_UNS_Day (Properties ctx, int UNS_Day_ID, String trxName)
    {
      super (ctx, UNS_Day_ID, trxName);
      /** if (UNS_Day_ID == 0)
        {
			setDate (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setUNS_Day_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Day (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_Day[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Chinese Date.
		@param Chinese_Date Chinese Date	  */
	public void setChinese_Date (BigDecimal Chinese_Date)
	{
		set_Value (COLUMNNAME_Chinese_Date, Chinese_Date);
	}

	/** Get Chinese Date.
		@return Chinese Date	  */
	public BigDecimal getChinese_Date () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Chinese_Date);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Date.
		@param Date Date	  */
	public void setDate (Timestamp Date)
	{
		set_Value (COLUMNNAME_Date, Date);
	}

	/** Get Date.
		@return Date	  */
	public Timestamp getDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date);
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

	/** Set Period No.
		@param PeriodNo 
		Unique Period Number
	  */
	public void setPeriodNo (int PeriodNo)
	{
		set_Value (COLUMNNAME_PeriodNo, Integer.valueOf(PeriodNo));
	}

	/** Get Period No.
		@return Unique Period Number
	  */
	public int getPeriodNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Day.
		@param UNS_Day_ID Day	  */
	public void setUNS_Day_ID (int UNS_Day_ID)
	{
		if (UNS_Day_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Day_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Day_ID, Integer.valueOf(UNS_Day_ID));
	}

	/** Get Day.
		@return Day	  */
	public int getUNS_Day_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Day_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}