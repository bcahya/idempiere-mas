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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_BCNumber_Generator
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_BCNumber_Generator extends PO implements I_UNS_BCNumber_Generator, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130801L;

    /** Standard Constructor */
    public X_UNS_BCNumber_Generator (Properties ctx, int UNS_BCNumber_Generator_ID, String trxName)
    {
      super (ctx, UNS_BCNumber_Generator_ID, trxName);
      /** if (UNS_BCNumber_Generator_ID == 0)
        {
			setC_Year_ID (0);
			setLatestUsedNumber (null);
// 0
			setUNS_BCCode_ID (0);
			setUNS_BCNumber_Generator_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BCNumber_Generator (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BCNumber_Generator[")
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

	/** Set Latest Used Number.
		@param LatestUsedNumber Latest Used Number	  */
	public void setLatestUsedNumber (String LatestUsedNumber)
	{
		set_ValueNoCheck (COLUMNNAME_LatestUsedNumber, LatestUsedNumber);
	}

	/** Get Latest Used Number.
		@return Latest Used Number	  */
	public String getLatestUsedNumber () 
	{
		return (String)get_Value(COLUMNNAME_LatestUsedNumber);
	}

	/** Set Bea Cukai Code.
		@param UNS_BCCode_ID Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID)
	{
		if (UNS_BCCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCCode_ID, Integer.valueOf(UNS_BCCode_ID));
	}

	/** Get Bea Cukai Code.
		@return Bea Cukai Code	  */
	public int getUNS_BCCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_BCCode_ID()));
    }

	/** Set BC Number Generator.
		@param UNS_BCNumber_Generator_ID BC Number Generator	  */
	public void setUNS_BCNumber_Generator_ID (int UNS_BCNumber_Generator_ID)
	{
		if (UNS_BCNumber_Generator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Generator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Generator_ID, Integer.valueOf(UNS_BCNumber_Generator_ID));
	}

	/** Get BC Number Generator.
		@return BC Number Generator	  */
	public int getUNS_BCNumber_Generator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCNumber_Generator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BCNumber_Generator_UU.
		@param UNS_BCNumber_Generator_UU UNS_BCNumber_Generator_UU	  */
	public void setUNS_BCNumber_Generator_UU (String UNS_BCNumber_Generator_UU)
	{
		set_Value (COLUMNNAME_UNS_BCNumber_Generator_UU, UNS_BCNumber_Generator_UU);
	}

	/** Get UNS_BCNumber_Generator_UU.
		@return UNS_BCNumber_Generator_UU	  */
	public String getUNS_BCNumber_Generator_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BCNumber_Generator_UU);
	}
}