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

/** Generated Model for UNS_CheckerLine_Result
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CheckerLine_Result extends PO implements I_UNS_CheckerLine_Result, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151121L;

    /** Standard Constructor */
    public X_UNS_CheckerLine_Result (Properties ctx, int UNS_CheckerLine_Result_ID, String trxName)
    {
      super (ctx, UNS_CheckerLine_Result_ID, trxName);
      /** if (UNS_CheckerLine_Result_ID == 0)
        {
			setUNS_Checker_Result_ID (0);
			setUNS_CheckerLine_Result_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CheckerLine_Result (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CheckerLine_Result[")
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

	public com.unicore.model.I_UNS_Checker_Result getUNS_Checker_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Checker_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_Checker_Result.Table_Name)
			.getPO(getUNS_Checker_Result_ID(), get_TrxName());	}

	/** Set Checker Result.
		@param UNS_Checker_Result_ID Checker Result	  */
	public void setUNS_Checker_Result_ID (int UNS_Checker_Result_ID)
	{
		if (UNS_Checker_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Checker_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Checker_Result_ID, Integer.valueOf(UNS_Checker_Result_ID));
	}

	/** Get Checker Result.
		@return Checker Result	  */
	public int getUNS_Checker_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Checker_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_CheckerLine getUNS_CheckerLine() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CheckerLine)MTable.get(getCtx(), com.unicore.model.I_UNS_CheckerLine.Table_Name)
			.getPO(getUNS_CheckerLine_ID(), get_TrxName());	}

	/** Set Detail Of Checker.
		@param UNS_CheckerLine_ID Detail Of Checker	  */
	public void setUNS_CheckerLine_ID (int UNS_CheckerLine_ID)
	{
		if (UNS_CheckerLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerLine_ID, Integer.valueOf(UNS_CheckerLine_ID));
	}

	/** Get Detail Of Checker.
		@return Detail Of Checker	  */
	public int getUNS_CheckerLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CheckerLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Checker Line Result.
		@param UNS_CheckerLine_Result_ID Checker Line Result	  */
	public void setUNS_CheckerLine_Result_ID (int UNS_CheckerLine_Result_ID)
	{
		if (UNS_CheckerLine_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerLine_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerLine_Result_ID, Integer.valueOf(UNS_CheckerLine_Result_ID));
	}

	/** Get Checker Line Result.
		@return Checker Line Result	  */
	public int getUNS_CheckerLine_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CheckerLine_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CheckerLine_Result_UU.
		@param UNS_CheckerLine_Result_UU UNS_CheckerLine_Result_UU	  */
	public void setUNS_CheckerLine_Result_UU (String UNS_CheckerLine_Result_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CheckerLine_Result_UU, UNS_CheckerLine_Result_UU);
	}

	/** Get UNS_CheckerLine_Result_UU.
		@return UNS_CheckerLine_Result_UU	  */
	public String getUNS_CheckerLine_Result_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CheckerLine_Result_UU);
	}
}