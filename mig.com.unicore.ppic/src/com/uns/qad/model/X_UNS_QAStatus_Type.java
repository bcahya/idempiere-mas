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
package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_QAStatus_Type
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_Type extends PO implements I_UNS_QAStatus_Type, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130618L;

    /** Standard Constructor */
    public X_UNS_QAStatus_Type (Properties ctx, int UNS_QAStatus_Type_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_Type_ID, trxName);
      /** if (UNS_QAStatus_Type_ID == 0)
        {
			setCategory (null);
			setDescription (null);
			setName (null);
			setResult (false);
// N
			setUNS_QAStatus_Type_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_Type (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_Type[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Category AD_Reference_ID=1000138 */
	public static final int CATEGORY_AD_Reference_ID=1000138;
	/** PM/SM = PM */
	public static final String CATEGORY_PMSM = "PM";
	/** RAW = RW */
	public static final String CATEGORY_RAW = "RW";
	/** FGs = FG */
	public static final String CATEGORY_FGs = "FG";
	/** Set Category.
		@param Category Category	  */
	public void setCategory (String Category)
	{

		set_Value (COLUMNNAME_Category, Category);
	}

	/** Get Category.
		@return Category	  */
	public String getCategory () 
	{
		return (String)get_Value(COLUMNNAME_Category);
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

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (boolean Result)
	{
		set_Value (COLUMNNAME_Result, Boolean.valueOf(Result));
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public boolean isResult () 
	{
		Object oo = get_Value(COLUMNNAME_Result);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Attribute Type.
		@param UNS_QAStatus_Type_ID Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID)
	{
		if (UNS_QAStatus_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, Integer.valueOf(UNS_QAStatus_Type_ID));
	}

	/** Get Attribute Type.
		@return Attribute Type	  */
	public int getUNS_QAStatus_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_PMSMType_UU.
		@param UNS_QAStatus_Type_UU UNS_QAStatus_PMSMType_UU	  */
	public void setUNS_QAStatus_Type_UU (String UNS_QAStatus_Type_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_Type_UU, UNS_QAStatus_Type_UU);
	}

	/** Get UNS_QAStatus_PMSMType_UU.
		@return UNS_QAStatus_PMSMType_UU	  */
	public String getUNS_QAStatus_Type_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_Type_UU);
	}
}