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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_SalesSchedule
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SalesSchedule extends PO implements I_UNS_SalesSchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141209L;

    /** Standard Constructor */
    public X_UNS_SalesSchedule (Properties ctx, int UNS_SalesSchedule_ID, String trxName)
    {
      super (ctx, UNS_SalesSchedule_ID, trxName);
      /** if (UNS_SalesSchedule_ID == 0)
        {
			setDay (null);
			setName (null);
			setSalesRep_ID (0);
			setUNS_SalesSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SalesSchedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SalesSchedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Saturday = 7 */
	public static final String DAY_Saturday = "7";
	/** Friday = 6 */
	public static final String DAY_Friday = "6";
	/** Thursday = 5 */
	public static final String DAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String DAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String DAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String DAY_Monday = "2";
	/** Sunday = 1 */
	public static final String DAY_Sunday = "1";
	/** Set Day.
		@param Day Day	  */
	public void setDay (String Day)
	{

		set_Value (COLUMNNAME_Day, Day);
	}

	/** Get Day.
		@return Day	  */
	public String getDay () 
	{
		return (String)get_Value(COLUMNNAME_Day);
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Schedule.
		@param UNS_SalesSchedule_ID Sales Schedule	  */
	public void setUNS_SalesSchedule_ID (int UNS_SalesSchedule_ID)
	{
		if (UNS_SalesSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesSchedule_ID, Integer.valueOf(UNS_SalesSchedule_ID));
	}

	/** Get Sales Schedule.
		@return Sales Schedule	  */
	public int getUNS_SalesSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SalesSchedule_UU.
		@param UNS_SalesSchedule_UU UNS_SalesSchedule_UU	  */
	public void setUNS_SalesSchedule_UU (String UNS_SalesSchedule_UU)
	{
		set_Value (COLUMNNAME_UNS_SalesSchedule_UU, UNS_SalesSchedule_UU);
	}

	/** Get UNS_SalesSchedule_UU.
		@return UNS_SalesSchedule_UU	  */
	public String getUNS_SalesSchedule_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SalesSchedule_UU);
	}
}