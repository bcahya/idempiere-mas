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
package com.unicore.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_TrxWeeklyStock
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_TrxWeeklyStock extends PO implements I_UNS_TrxWeeklyStock, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170405L;

    /** Standard Constructor */
    public X_UNS_TrxWeeklyStock (Properties ctx, int UNS_TrxWeeklyStock_ID, String trxName)
    {
      super (ctx, UNS_TrxWeeklyStock_ID, trxName);
      /** if (UNS_TrxWeeklyStock_ID == 0)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setM_Warehouse_ID (0);
			setUNS_TrxWeeklyStock_ID (0);
			setWeekNo (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_TrxWeeklyStock (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_TrxWeeklyStock[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Weekly Stock.
		@param UNS_TrxWeeklyStock_ID Transaction Weekly Stock	  */
	public void setUNS_TrxWeeklyStock_ID (int UNS_TrxWeeklyStock_ID)
	{
		if (UNS_TrxWeeklyStock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_ID, Integer.valueOf(UNS_TrxWeeklyStock_ID));
	}

	/** Get Transaction Weekly Stock.
		@return Transaction Weekly Stock	  */
	public int getUNS_TrxWeeklyStock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_TrxWeeklyStock_UU.
		@param UNS_TrxWeeklyStock_UU UNS_TrxWeeklyStock_UU	  */
	public void setUNS_TrxWeeklyStock_UU (String UNS_TrxWeeklyStock_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_UU, UNS_TrxWeeklyStock_UU);
	}

	/** Get UNS_TrxWeeklyStock_UU.
		@return UNS_TrxWeeklyStock_UU	  */
	public String getUNS_TrxWeeklyStock_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_UU);
	}

	/** Set Week No.
		@param WeekNo Week No	  */
	public void setWeekNo (int WeekNo)
	{
		set_Value (COLUMNNAME_WeekNo, Integer.valueOf(WeekNo));
	}

	/** Get Week No.
		@return Week No	  */
	public int getWeekNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeekNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}