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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Resource_Utilities
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Resource_Utilities extends PO implements I_UNS_Resource_Utilities, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130128L;

    /** Standard Constructor */
    public X_UNS_Resource_Utilities (Properties ctx, int UNS_Resource_Utilities_ID, String trxName)
    {
      super (ctx, UNS_Resource_Utilities_ID, trxName);
      /** if (UNS_Resource_Utilities_ID == 0)
        {
			setC_UOM_ID (0);
			setUNS_Resource_ID (0);
			setUNS_Resource_Utilities_ID (0);
			setUtilityType (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_Utilities (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_Utilities[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max. Consumption.
		@param MaxConsumption Max. Consumption	  */
	public void setMaxConsumption (BigDecimal MaxConsumption)
	{
		set_Value (COLUMNNAME_MaxConsumption, MaxConsumption);
	}

	/** Get Max. Consumption.
		@return Max. Consumption	  */
	public BigDecimal getMaxConsumption () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxConsumption);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min. Consumption.
		@param MinConsumption Min. Consumption	  */
	public void setMinConsumption (BigDecimal MinConsumption)
	{
		set_Value (COLUMNNAME_MinConsumption, MinConsumption);
	}

	/** Get Min. Consumption.
		@return Min. Consumption	  */
	public BigDecimal getMinConsumption () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinConsumption);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Machine.
		@param UNS_Resource_ID Machine	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Machine.
		@return Machine	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Resource Utilities.
		@param UNS_Resource_Utilities_ID Resource Utilities	  */
	public void setUNS_Resource_Utilities_ID (int UNS_Resource_Utilities_ID)
	{
		if (UNS_Resource_Utilities_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Utilities_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Utilities_ID, Integer.valueOf(UNS_Resource_Utilities_ID));
	}

	/** Get Resource Utilities.
		@return Resource Utilities	  */
	public int getUNS_Resource_Utilities_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Utilities_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Resource Utilities UU.
		@param UNS_Resource_Utilities_UU Resource Utilities UU	  */
	public void setUNS_Resource_Utilities_UU (String UNS_Resource_Utilities_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_Utilities_UU, UNS_Resource_Utilities_UU);
	}

	/** Get Resource Utilities UU.
		@return Resource Utilities UU	  */
	public String getUNS_Resource_Utilities_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_Utilities_UU);
	}

	/** UtilityType AD_Reference_ID=1000012 */
	public static final int UTILITYTYPE_AD_Reference_ID=1000012;
	/** Solar = SLR */
	public static final String UTILITYTYPE_Solar = "SLR";
	/** Bensin = BNS */
	public static final String UTILITYTYPE_Bensin = "BNS";
	/** Listrik = LST */
	public static final String UTILITYTYPE_Listrik = "LST";
	/** Accu = ACC */
	public static final String UTILITYTYPE_Accu = "ACC";
	/** Steam = STM */
	public static final String UTILITYTYPE_Steam = "STM";
	/** Set Utility Type.
		@param UtilityType Utility Type	  */
	public void setUtilityType (String UtilityType)
	{

		set_Value (COLUMNNAME_UtilityType, UtilityType);
	}

	/** Get Utility Type.
		@return Utility Type	  */
	public String getUtilityType () 
	{
		return (String)get_Value(COLUMNNAME_UtilityType);
	}
}