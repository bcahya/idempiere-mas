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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PL_ConfirmLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ConfirmLine extends PO implements I_UNS_PL_ConfirmLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141127L;

    /** Standard Constructor */
    public X_UNS_PL_ConfirmLine (Properties ctx, int UNS_PL_ConfirmLine_ID, String trxName)
    {
      super (ctx, UNS_PL_ConfirmLine_ID, trxName);
      /** if (UNS_PL_ConfirmLine_ID == 0)
        {
			setM_Product_ID (0);
			setQtyEntered (Env.ZERO);
			setTargetQty (Env.ZERO);
			setUNS_PackingList_Line_ID (0);
			setUNS_PL_ConfirmLine_ID (0);
			setUNS_PL_ConfirmProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ConfirmLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ConfirmLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
		Virtual column can't be set
	  */
	@Deprecated
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		throw new IllegalArgumentException ("C_InvoiceLine_ID is virtual column");	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
		Virtual column can't be set
	  */
	@Deprecated
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		throw new IllegalArgumentException ("C_OrderLine_ID is virtual column");	}

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

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
		Virtual column can't be set
	  */
	@Deprecated
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		throw new IllegalArgumentException ("M_InOutLine_ID is virtual column");	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Quantity.
		@param TargetQty 
		Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	/** Get Target Quantity.
		@return Target Movement Quantity
	  */
	public BigDecimal getTargetQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_PackingList_Line getUNS_PackingList_Line() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList_Line)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList_Line.Table_Name)
			.getPO(getUNS_PackingList_Line_ID(), get_TrxName());	}

	/** Set Packing List Line.
		@param UNS_PackingList_Line_ID Packing List Line	  */
	public void setUNS_PackingList_Line_ID (int UNS_PackingList_Line_ID)
	{
		if (UNS_PackingList_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Line_ID, Integer.valueOf(UNS_PackingList_Line_ID));
	}

	/** Get Packing List Line.
		@return Packing List Line	  */
	public int getUNS_PackingList_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Confirmation Order Line.
		@param UNS_PL_ConfirmLine_ID Confirmation Order Line	  */
	public void setUNS_PL_ConfirmLine_ID (int UNS_PL_ConfirmLine_ID)
	{
		if (UNS_PL_ConfirmLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmLine_ID, Integer.valueOf(UNS_PL_ConfirmLine_ID));
	}

	/** Get Confirmation Order Line.
		@return Confirmation Order Line	  */
	public int getUNS_PL_ConfirmLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ConfirmLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PL_ConfirmLine_UU.
		@param UNS_PL_ConfirmLine_UU UNS_PL_ConfirmLine_UU	  */
	public void setUNS_PL_ConfirmLine_UU (String UNS_PL_ConfirmLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmLine_UU, UNS_PL_ConfirmLine_UU);
	}

	/** Get UNS_PL_ConfirmLine_UU.
		@return UNS_PL_ConfirmLine_UU	  */
	public String getUNS_PL_ConfirmLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ConfirmLine_UU);
	}

	public com.unicore.model.I_UNS_PL_ConfirmProduct getUNS_PL_ConfirmProduct() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PL_ConfirmProduct)MTable.get(getCtx(), com.unicore.model.I_UNS_PL_ConfirmProduct.Table_Name)
			.getPO(getUNS_PL_ConfirmProduct_ID(), get_TrxName());	}

	/** Set Confirmation Product.
		@param UNS_PL_ConfirmProduct_ID Confirmation Product	  */
	public void setUNS_PL_ConfirmProduct_ID (int UNS_PL_ConfirmProduct_ID)
	{
		if (UNS_PL_ConfirmProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, Integer.valueOf(UNS_PL_ConfirmProduct_ID));
	}

	/** Get Confirmation Product.
		@return Confirmation Product	  */
	public int getUNS_PL_ConfirmProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ConfirmProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}