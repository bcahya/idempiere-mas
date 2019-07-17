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

/** Generated Model for UNS_EmployeeAtt
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_EmployeeAtt extends PO implements I_UNS_EmployeeAtt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160516L;

    /** Standard Constructor */
    public X_UNS_EmployeeAtt (Properties ctx, int UNS_EmployeeAtt_ID, String trxName)
    {
      super (ctx, UNS_EmployeeAtt_ID, trxName);
      /** if (UNS_EmployeeAtt_ID == 0)
        {
			setName (null);
			setUNS_EmployeeAtt_ID (0);
			setUNS_EmployeeAtt_UU (null);
			setUNS_Employee_ID (0);
			setUNS_RegisteredAtt_ID (0);
			setVerifyPassword (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_EmployeeAtt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_EmployeeAtt[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Badge Number.
		@param BadgeNumber Badge Number	  */
	public void setBadgeNumber (int BadgeNumber)
	{
		set_ValueNoCheck (COLUMNNAME_BadgeNumber, Integer.valueOf(BadgeNumber));
	}

	/** Get Badge Number.
		@return Badge Number	  */
	public int getBadgeNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BadgeNumber);
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

	/** Set Employee Attendance.
		@param UNS_EmployeeAtt_ID Employee Attendance	  */
	public void setUNS_EmployeeAtt_ID (int UNS_EmployeeAtt_ID)
	{
		if (UNS_EmployeeAtt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmployeeAtt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmployeeAtt_ID, Integer.valueOf(UNS_EmployeeAtt_ID));
	}

	/** Get Employee Attendance.
		@return Employee Attendance	  */
	public int getUNS_EmployeeAtt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmployeeAtt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee Attendance UU.
		@param UNS_EmployeeAtt_UU Employee Attendance UU	  */
	public void setUNS_EmployeeAtt_UU (String UNS_EmployeeAtt_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_EmployeeAtt_UU, UNS_EmployeeAtt_UU);
	}

	/** Get Employee Attendance UU.
		@return Employee Attendance UU	  */
	public String getUNS_EmployeeAtt_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_EmployeeAtt_UU);
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

	public com.uns.model.I_UNS_RegisteredAtt getUNS_RegisteredAtt() throws RuntimeException
    {
		return (com.uns.model.I_UNS_RegisteredAtt)MTable.get(getCtx(), com.uns.model.I_UNS_RegisteredAtt.Table_Name)
			.getPO(getUNS_RegisteredAtt_ID(), get_TrxName());	}

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

	/** Set Verify Password.
		@param VerifyPassword Verify Password	  */
	public void setVerifyPassword (String VerifyPassword)
	{
		set_Value (COLUMNNAME_VerifyPassword, VerifyPassword);
	}

	/** Get Verify Password.
		@return Verify Password	  */
	public String getVerifyPassword () 
	{
		return (String)get_Value(COLUMNNAME_VerifyPassword);
	}
}