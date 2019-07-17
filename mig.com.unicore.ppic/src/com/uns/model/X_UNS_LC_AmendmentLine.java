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

/** Generated Model for UNS_LC_AmendmentLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_AmendmentLine extends PO implements I_UNS_LC_AmendmentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130909L;

    /** Standard Constructor */
    public X_UNS_LC_AmendmentLine (Properties ctx, int UNS_LC_AmendmentLine_ID, String trxName)
    {
      super (ctx, UNS_LC_AmendmentLine_ID, trxName);
      /** if (UNS_LC_AmendmentLine_ID == 0)
        {
			setC_UOM_ID (0);
			setLCQuantity (Env.ZERO);
// 0
			setM_Product_Category_ID (0);
			setPrevLCQuantity (Env.ZERO);
// 0
			setUNS_LC_Amendment_ID (0);
			setUNS_LC_AmendmentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_AmendmentLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_AmendmentLine[")
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

	/** Set LC Quantity.
		@param LCQuantity LC Quantity	  */
	public void setLCQuantity (BigDecimal LCQuantity)
	{
		set_Value (COLUMNNAME_LCQuantity, LCQuantity);
	}

	/** Get LC Quantity.
		@return LC Quantity	  */
	public BigDecimal getLCQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LCQuantity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

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

	/** Set Prev LC Quantity.
		@param PrevLCQuantity Prev LC Quantity	  */
	public void setPrevLCQuantity (BigDecimal PrevLCQuantity)
	{
		set_Value (COLUMNNAME_PrevLCQuantity, PrevLCQuantity);
	}

	/** Get Prev LC Quantity.
		@return Prev LC Quantity	  */
	public BigDecimal getPrevLCQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevLCQuantity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_LC_Amendment getUNS_LC_Amendment() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Amendment)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Amendment.Table_Name)
			.getPO(getUNS_LC_Amendment_ID(), get_TrxName());	}

	/** Set LC Amendment.
		@param UNS_LC_Amendment_ID LC Amendment	  */
	public void setUNS_LC_Amendment_ID (int UNS_LC_Amendment_ID)
	{
		if (UNS_LC_Amendment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, Integer.valueOf(UNS_LC_Amendment_ID));
	}

	/** Get LC Amendment.
		@return LC Amendment	  */
	public int getUNS_LC_Amendment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Amendment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LC Amendment Line.
		@param UNS_LC_AmendmentLine_ID LC Amendment Line	  */
	public void setUNS_LC_AmendmentLine_ID (int UNS_LC_AmendmentLine_ID)
	{
		if (UNS_LC_AmendmentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmendmentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmendmentLine_ID, Integer.valueOf(UNS_LC_AmendmentLine_ID));
	}

	/** Get LC Amendment Line.
		@return LC Amendment Line	  */
	public int getUNS_LC_AmendmentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_AmendmentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_AmendmentLine_UU.
		@param UNS_LC_AmendmentLine_UU UNS_LC_AmendmentLine_UU	  */
	public void setUNS_LC_AmendmentLine_UU (String UNS_LC_AmendmentLine_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_AmendmentLine_UU, UNS_LC_AmendmentLine_UU);
	}

	/** Get UNS_LC_AmendmentLine_UU.
		@return UNS_LC_AmendmentLine_UU	  */
	public String getUNS_LC_AmendmentLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_AmendmentLine_UU);
	}

	public com.uns.model.I_UNS_LC_Lines getUNS_LC_Lines() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Lines)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Lines.Table_Name)
			.getPO(getUNS_LC_Lines_ID(), get_TrxName());	}

	/** Set LC Lines.
		@param UNS_LC_Lines_ID LC Lines	  */
	public void setUNS_LC_Lines_ID (int UNS_LC_Lines_ID)
	{
		if (UNS_LC_Lines_ID < 1) 
			set_Value (COLUMNNAME_UNS_LC_Lines_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_LC_Lines_ID, Integer.valueOf(UNS_LC_Lines_ID));
	}

	/** Get LC Lines.
		@return LC Lines	  */
	public int getUNS_LC_Lines_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Lines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}