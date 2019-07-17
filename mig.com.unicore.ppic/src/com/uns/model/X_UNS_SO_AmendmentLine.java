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

/** Generated Model for UNS_SO_AmendmentLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SO_AmendmentLine extends PO implements I_UNS_SO_AmendmentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130528L;

    /** Standard Constructor */
    public X_UNS_SO_AmendmentLine (Properties ctx, int UNS_SO_AmendmentLine_ID, String trxName)
    {
      super (ctx, UNS_SO_AmendmentLine_ID, trxName);
      /** if (UNS_SO_AmendmentLine_ID == 0)
        {
			setC_OrderLine_ID (0);
			setC_UOM_ID (0);
			setDescription (null);
			setLineNo (0);
			setM_Product_ID (0);
			setNewAmt (Env.ZERO);
// 0
			setNewPrice (Env.ZERO);
// 0
			setPrevAmt (Env.ZERO);
// 0
			setPrevPrice (Env.ZERO);
// 0
			setPrevQuantity (Env.ZERO);
// 0
			setQuantity (Env.ZERO);
			setUNS_SO_Amendment_ID (0);
			setUNS_SO_AmendmentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SO_AmendmentLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_SO_AmendmentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Line.
		@param LineNo 
		Line No
	  */
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Line.
		@return Line No
	  */
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New Amount.
		@param NewAmt New Amount	  */
	public void setNewAmt (BigDecimal NewAmt)
	{
		set_Value (COLUMNNAME_NewAmt, NewAmt);
	}

	/** Get New Amount.
		@return New Amount	  */
	public BigDecimal getNewAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New Price.
		@param NewPrice New Price	  */
	public void setNewPrice (BigDecimal NewPrice)
	{
		set_Value (COLUMNNAME_NewPrice, NewPrice);
	}

	/** Get New Price.
		@return New Price	  */
	public BigDecimal getNewPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Amount.
		@param PrevAmt Prev Amount	  */
	public void setPrevAmt (BigDecimal PrevAmt)
	{
		set_Value (COLUMNNAME_PrevAmt, PrevAmt);
	}

	/** Get Prev Amount.
		@return Prev Amount	  */
	public BigDecimal getPrevAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Price.
		@param PrevPrice Prev Price	  */
	public void setPrevPrice (BigDecimal PrevPrice)
	{
		set_Value (COLUMNNAME_PrevPrice, PrevPrice);
	}

	/** Get Prev Price.
		@return Prev Price	  */
	public BigDecimal getPrevPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Quantity.
		@param PrevQuantity Prev Quantity	  */
	public void setPrevQuantity (BigDecimal PrevQuantity)
	{
		set_Value (COLUMNNAME_PrevQuantity, PrevQuantity);
	}

	/** Get Prev Quantity.
		@return Prev Quantity	  */
	public BigDecimal getPrevQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevQuantity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Quantity Quantity	  */
	public void setQuantity (BigDecimal Quantity)
	{
		set_Value (COLUMNNAME_Quantity, Quantity);
	}

	/** Get Quantity.
		@return Quantity	  */
	public BigDecimal getQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Quantity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_SO_Amendment getUNS_SO_Amendment() throws RuntimeException
    {
		return (com.uns.model.I_UNS_SO_Amendment)MTable.get(getCtx(), com.uns.model.I_UNS_SO_Amendment.Table_Name)
			.getPO(getUNS_SO_Amendment_ID(), get_TrxName());	}

	/** Set SO Amendment.
		@param UNS_SO_Amendment_ID SO Amendment	  */
	public void setUNS_SO_Amendment_ID (int UNS_SO_Amendment_ID)
	{
		if (UNS_SO_Amendment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SO_Amendment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SO_Amendment_ID, Integer.valueOf(UNS_SO_Amendment_ID));
	}

	/** Get SO Amendment.
		@return SO Amendment	  */
	public int getUNS_SO_Amendment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SO_Amendment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SO Amendment Line.
		@param UNS_SO_AmendmentLine_ID SO Amendment Line	  */
	public void setUNS_SO_AmendmentLine_ID (int UNS_SO_AmendmentLine_ID)
	{
		if (UNS_SO_AmendmentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SO_AmendmentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SO_AmendmentLine_ID, Integer.valueOf(UNS_SO_AmendmentLine_ID));
	}

	/** Get SO Amendment Line.
		@return SO Amendment Line	  */
	public int getUNS_SO_AmendmentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SO_AmendmentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SO_AmendmentLine_UU.
		@param UNS_SO_AmendmentLine_UU UNS_SO_AmendmentLine_UU	  */
	public void setUNS_SO_AmendmentLine_UU (String UNS_SO_AmendmentLine_UU)
	{
		set_Value (COLUMNNAME_UNS_SO_AmendmentLine_UU, UNS_SO_AmendmentLine_UU);
	}

	/** Get UNS_SO_AmendmentLine_UU.
		@return UNS_SO_AmendmentLine_UU	  */
	public String getUNS_SO_AmendmentLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SO_AmendmentLine_UU);
	}
}