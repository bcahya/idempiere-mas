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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_QAMonitoringLab
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMonitoringLab extends PO implements I_UNS_QAMonitoringLab, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130530L;

    /** Standard Constructor */
    public X_UNS_QAMonitoringLab (Properties ctx, int UNS_QAMonitoringLab_ID, String trxName)
    {
      super (ctx, UNS_QAMonitoringLab_ID, trxName);
      /** if (UNS_QAMonitoringLab_ID == 0)
        {
			setName (null);
			setTestType (null);
			setUNS_QAMonitoringLab_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMonitoringLab (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMonitoringLab[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Day After Production.
		@param DayAfterProduction Day After Production	  */
	public void setDayAfterProduction (BigDecimal DayAfterProduction)
	{
		set_Value (COLUMNNAME_DayAfterProduction, DayAfterProduction);
	}

	/** Get Day After Production.
		@return Day After Production	  */
	public BigDecimal getDayAfterProduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayAfterProduction);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** TestType AD_Reference_ID=1000135 */
	public static final int TESTTYPE_AD_Reference_ID=1000135;
	/** Chemical = C */
	public static final String TESTTYPE_Chemical = "C";
	/** Microbiological = M */
	public static final String TESTTYPE_Microbiological = "M";
	/** Set Test Type.
		@param TestType Test Type	  */
	public void setTestType (String TestType)
	{

		set_Value (COLUMNNAME_TestType, TestType);
	}

	/** Get Test Type.
		@return Test Type	  */
	public String getTestType () 
	{
		return (String)get_Value(COLUMNNAME_TestType);
	}

	/** Set Monitoring Labolatory.
		@param UNS_QAMonitoringLab_ID Monitoring Labolatory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID)
	{
		if (UNS_QAMonitoringLab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, Integer.valueOf(UNS_QAMonitoringLab_ID));
	}

	/** Get Monitoring Labolatory.
		@return Monitoring Labolatory	  */
	public int getUNS_QAMonitoringLab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringLab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAMonitoringLab_UU.
		@param UNS_QAMonitoringLab_UU UNS_QAMonitoringLab_UU	  */
	public void setUNS_QAMonitoringLab_UU (String UNS_QAMonitoringLab_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMonitoringLab_UU, UNS_QAMonitoringLab_UU);
	}

	/** Get UNS_QAMonitoringLab_UU.
		@return UNS_QAMonitoringLab_UU	  */
	public String getUNS_QAMonitoringLab_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMonitoringLab_UU);
	}

	/** Set Monitoring Code.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Monitoring Code.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}