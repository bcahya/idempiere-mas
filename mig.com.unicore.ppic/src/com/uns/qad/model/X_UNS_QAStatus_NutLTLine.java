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

/** Generated Model for UNS_QAStatus_NutLTLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_NutLTLine extends PO implements I_UNS_QAStatus_NutLTLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130520L;

    /** Standard Constructor */
    public X_UNS_QAStatus_NutLTLine (Properties ctx, int UNS_QAStatus_NutLTLine_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_NutLTLine_ID, trxName);
      /** if (UNS_QAStatus_NutLTLine_ID == 0)
        {
			setUNS_QAAttributeTest_ID (0);
			setUNS_QAStatus_NutLabTest_ID (0);
			setUNS_QAStatus_NutLTLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_NutLTLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_NutLTLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Average.
		@param Average Average	  */
	public void setAverage (BigDecimal Average)
	{
		set_Value (COLUMNNAME_Average, Average);
	}

	/** Get Average.
		@return Average	  */
	public BigDecimal getAverage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Average);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Calculated.
		@param IsCalculated 
		The value is calculated by the system
	  */
	public void setIsCalculated (boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, Boolean.valueOf(IsCalculated));
	}

	/** Get Calculated.
		@return The value is calculated by the system
	  */
	public boolean isCalculated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.qad.model.I_UNS_QAAttributeTest getUNS_QAAttributeTest() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAAttributeTest)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAAttributeTest.Table_Name)
			.getPO(getUNS_QAAttributeTest_ID(), get_TrxName());	}

	/** Set Attribute Test.
		@param UNS_QAAttributeTest_ID Attribute Test	  */
	public void setUNS_QAAttributeTest_ID (int UNS_QAAttributeTest_ID)
	{
		if (UNS_QAAttributeTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, Integer.valueOf(UNS_QAAttributeTest_ID));
	}

	/** Get Attribute Test.
		@return Attribute Test	  */
	public int getUNS_QAAttributeTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAAttributeTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAStatus_NutLabTest getUNS_QAStatus_NutLabTest() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_NutLabTest)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_NutLabTest.Table_Name)
			.getPO(getUNS_QAStatus_NutLabTest_ID(), get_TrxName());	}

	/** Set Coconut Labolatory Test.
		@param UNS_QAStatus_NutLabTest_ID Coconut Labolatory Test	  */
	public void setUNS_QAStatus_NutLabTest_ID (int UNS_QAStatus_NutLabTest_ID)
	{
		if (UNS_QAStatus_NutLabTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLabTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLabTest_ID, Integer.valueOf(UNS_QAStatus_NutLabTest_ID));
	}

	/** Get Coconut Labolatory Test.
		@return Coconut Labolatory Test	  */
	public int getUNS_QAStatus_NutLabTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_NutLabTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CLT Line.
		@param UNS_QAStatus_NutLTLine_ID CLT Line	  */
	public void setUNS_QAStatus_NutLTLine_ID (int UNS_QAStatus_NutLTLine_ID)
	{
		if (UNS_QAStatus_NutLTLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLTLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLTLine_ID, Integer.valueOf(UNS_QAStatus_NutLTLine_ID));
	}

	/** Get CLT Line.
		@return CLT Line	  */
	public int getUNS_QAStatus_NutLTLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_NutLTLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_NutLTLine_UU.
		@param UNS_QAStatus_NutLTLine_UU UNS_QAStatus_NutLTLine_UU	  */
	public void setUNS_QAStatus_NutLTLine_UU (String UNS_QAStatus_NutLTLine_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_NutLTLine_UU, UNS_QAStatus_NutLTLine_UU);
	}

	/** Get UNS_QAStatus_NutLTLine_UU.
		@return UNS_QAStatus_NutLTLine_UU	  */
	public String getUNS_QAStatus_NutLTLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_NutLTLine_UU);
	}
}