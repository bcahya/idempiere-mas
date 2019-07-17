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

/** Generated Model for UNS_PackingList_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PackingList_Line extends PO implements I_UNS_PackingList_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170828L;

    /** Standard Constructor */
    public X_UNS_PackingList_Line (Properties ctx, int UNS_PackingList_Line_ID, String trxName)
    {
      super (ctx, UNS_PackingList_Line_ID, trxName);
      /** if (UNS_PackingList_Line_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setMovementQty (Env.ZERO);
			setQtyEntered (Env.ZERO);
			setQtyRequest (Env.ZERO);
// 0
			setTargetQty (Env.ZERO);
			setUNS_PackingList_Line_ID (0);
			setUNS_PackingList_Order_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PackingList_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PackingList_Line[")
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
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

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

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

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

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Qty Request.
		@param QtyRequest Qty Request	  */
	public void setQtyRequest (BigDecimal QtyRequest)
	{
		set_Value (COLUMNNAME_QtyRequest, QtyRequest);
	}

	/** Get Qty Request.
		@return Qty Request	  */
	public BigDecimal getQtyRequest () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequest);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Shipping Reserve Qty.
		@param QtyShipping 
		Shipping Reserve Quantity
	  */
	public void setQtyShipping (BigDecimal QtyShipping)
	{
		set_Value (COLUMNNAME_QtyShipping, QtyShipping);
	}

	/** Get Shipping Reserve Qty.
		@return Shipping Reserve Quantity
	  */
	public BigDecimal getQtyShipping () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyShipping);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_PackingList_Line getReference() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList_Line)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList_Line.Table_Name)
			.getPO(getReference_ID(), get_TrxName());	}

	/** Set Refrerence Record.
		@param Reference_ID Refrerence Record	  */
	public void setReference_ID (int Reference_ID)
	{
		if (Reference_ID < 1) 
			set_Value (COLUMNNAME_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_Reference_ID, Integer.valueOf(Reference_ID));
	}

	/** Get Refrerence Record.
		@return Refrerence Record	  */
	public int getReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UNS_PackingList_Line_UU.
		@param UNS_PackingList_Line_UU UNS_PackingList_Line_UU	  */
	public void setUNS_PackingList_Line_UU (String UNS_PackingList_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Line_UU, UNS_PackingList_Line_UU);
	}

	/** Get UNS_PackingList_Line_UU.
		@return UNS_PackingList_Line_UU	  */
	public String getUNS_PackingList_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingList_Line_UU);
	}

	public com.unicore.model.I_UNS_PackingList_Order getUNS_PackingList_Order() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList_Order)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList_Order.Table_Name)
			.getPO(getUNS_PackingList_Order_ID(), get_TrxName());	}

	/** Set Packing List Order.
		@param UNS_PackingList_Order_ID Packing List Order	  */
	public void setUNS_PackingList_Order_ID (int UNS_PackingList_Order_ID)
	{
		if (UNS_PackingList_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Order_ID, Integer.valueOf(UNS_PackingList_Order_ID));
	}

	/** Get Packing List Order.
		@return Packing List Order	  */
	public int getUNS_PackingList_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Shipping getUNS_Shipping() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Shipping)MTable.get(getCtx(), com.unicore.model.I_UNS_Shipping.Table_Name)
			.getPO(getUNS_Shipping_ID(), get_TrxName());	}

	/** Set Shipping Document.
		@param UNS_Shipping_ID Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID)
	{
		if (UNS_Shipping_ID < 1) 
			set_Value (COLUMNNAME_UNS_Shipping_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Shipping_ID, Integer.valueOf(UNS_Shipping_ID));
	}

	/** Get Shipping Document.
		@return Shipping Document	  */
	public int getUNS_Shipping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Shipping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL1() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL1_ID(), get_TrxName());	}

	/** Set UOM Conversion L1.
		@param UOMConversionL1_ID 
		The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public void setUOMConversionL1_ID (int UOMConversionL1_ID)
	{
		if (UOMConversionL1_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL1_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL1_ID, Integer.valueOf(UOMConversionL1_ID));
	}

	/** Get UOM Conversion L1.
		@return The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public int getUOMConversionL1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL2() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL2_ID(), get_TrxName());	}

	/** Set UOM Conversion L2.
		@param UOMConversionL2_ID 
		The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public void setUOMConversionL2_ID (int UOMConversionL2_ID)
	{
		if (UOMConversionL2_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL2_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL2_ID, Integer.valueOf(UOMConversionL2_ID));
	}

	/** Get UOM Conversion L2.
		@return The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public int getUOMConversionL2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL3() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL3_ID(), get_TrxName());	}

	/** Set UOM Conversion L3.
		@param UOMConversionL3_ID 
		The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public void setUOMConversionL3_ID (int UOMConversionL3_ID)
	{
		if (UOMConversionL3_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL3_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL3_ID, Integer.valueOf(UOMConversionL3_ID));
	}

	/** Get UOM Conversion L3.
		@return The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public int getUOMConversionL3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL4() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL4_ID(), get_TrxName());	}

	/** Set UOM Conversion L4.
		@param UOMConversionL4_ID 
		The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public void setUOMConversionL4_ID (int UOMConversionL4_ID)
	{
		if (UOMConversionL4_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL4_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL4_ID, Integer.valueOf(UOMConversionL4_ID));
	}

	/** Get UOM Conversion L4.
		@return The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public int getUOMConversionL4_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL4_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Qty L1.
		@param UOMMovementQtyL1 Movement Qty L1	  */
	public void setUOMMovementQtyL1 (BigDecimal UOMMovementQtyL1)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL1, UOMMovementQtyL1);
	}

	/** Get Movement Qty L1.
		@return Movement Qty L1	  */
	public BigDecimal getUOMMovementQtyL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L2.
		@param UOMMovementQtyL2 Movement Qty L2	  */
	public void setUOMMovementQtyL2 (BigDecimal UOMMovementQtyL2)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL2, UOMMovementQtyL2);
	}

	/** Get Movement Qty L2.
		@return Movement Qty L2	  */
	public BigDecimal getUOMMovementQtyL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L3.
		@param UOMMovementQtyL3 Movement Qty L3	  */
	public void setUOMMovementQtyL3 (BigDecimal UOMMovementQtyL3)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL3, UOMMovementQtyL3);
	}

	/** Get Movement Qty L3.
		@return Movement Qty L3	  */
	public BigDecimal getUOMMovementQtyL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L4.
		@param UOMMovementQtyL4 Movement Qty L4	  */
	public void setUOMMovementQtyL4 (BigDecimal UOMMovementQtyL4)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL4, UOMMovementQtyL4);
	}

	/** Get Movement Qty L4.
		@return Movement Qty L4	  */
	public BigDecimal getUOMMovementQtyL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Entered L1.
		@param UOMQtyEnteredL1 Qty Entered L1	  */
	public void setUOMQtyEnteredL1 (BigDecimal UOMQtyEnteredL1)
	{
		set_Value (COLUMNNAME_UOMQtyEnteredL1, UOMQtyEnteredL1);
	}

	/** Get Qty Entered L1.
		@return Qty Entered L1	  */
	public BigDecimal getUOMQtyEnteredL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyEnteredL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Entered L2.
		@param UOMQtyEnteredL2 Qty Entered L2	  */
	public void setUOMQtyEnteredL2 (BigDecimal UOMQtyEnteredL2)
	{
		set_Value (COLUMNNAME_UOMQtyEnteredL2, UOMQtyEnteredL2);
	}

	/** Get Qty Entered L2.
		@return Qty Entered L2	  */
	public BigDecimal getUOMQtyEnteredL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyEnteredL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Entered L3.
		@param UOMQtyEnteredL3 Qty Entered L3	  */
	public void setUOMQtyEnteredL3 (BigDecimal UOMQtyEnteredL3)
	{
		set_Value (COLUMNNAME_UOMQtyEnteredL3, UOMQtyEnteredL3);
	}

	/** Get Qty Entered L3.
		@return Qty Entered L3	  */
	public BigDecimal getUOMQtyEnteredL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyEnteredL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Entered L4.
		@param UOMQtyEnteredL4 Qty Entered L4	  */
	public void setUOMQtyEnteredL4 (BigDecimal UOMQtyEnteredL4)
	{
		set_Value (COLUMNNAME_UOMQtyEnteredL4, UOMQtyEnteredL4);
	}

	/** Get Qty Entered L4.
		@return Qty Entered L4	  */
	public BigDecimal getUOMQtyEnteredL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyEnteredL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Shipping L1.
		@param UOMQtyShippingL1 Qty Shipping L1	  */
	public void setUOMQtyShippingL1 (BigDecimal UOMQtyShippingL1)
	{
		set_Value (COLUMNNAME_UOMQtyShippingL1, UOMQtyShippingL1);
	}

	/** Get Qty Shipping L1.
		@return Qty Shipping L1	  */
	public BigDecimal getUOMQtyShippingL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyShippingL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Shipping L2.
		@param UOMQtyShippingL2 Qty Shipping L2	  */
	public void setUOMQtyShippingL2 (BigDecimal UOMQtyShippingL2)
	{
		set_Value (COLUMNNAME_UOMQtyShippingL2, UOMQtyShippingL2);
	}

	/** Get Qty Shipping L2.
		@return Qty Shipping L2	  */
	public BigDecimal getUOMQtyShippingL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyShippingL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Shipping L3.
		@param UOMQtyShippingL3 Qty Shipping L3	  */
	public void setUOMQtyShippingL3 (BigDecimal UOMQtyShippingL3)
	{
		set_Value (COLUMNNAME_UOMQtyShippingL3, UOMQtyShippingL3);
	}

	/** Get Qty Shipping L3.
		@return Qty Shipping L3	  */
	public BigDecimal getUOMQtyShippingL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyShippingL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Shipping L4.
		@param UOMQtyShippingL4 Qty Shipping L4	  */
	public void setUOMQtyShippingL4 (BigDecimal UOMQtyShippingL4)
	{
		set_Value (COLUMNNAME_UOMQtyShippingL4, UOMQtyShippingL4);
	}

	/** Get Qty Shipping L4.
		@return Qty Shipping L4	  */
	public BigDecimal getUOMQtyShippingL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyShippingL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Qty L1.
		@param UOMTargetQtyL1 Target Qty L1	  */
	public void setUOMTargetQtyL1 (BigDecimal UOMTargetQtyL1)
	{
		set_Value (COLUMNNAME_UOMTargetQtyL1, UOMTargetQtyL1);
	}

	/** Get Target Qty L1.
		@return Target Qty L1	  */
	public BigDecimal getUOMTargetQtyL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMTargetQtyL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Qty L2.
		@param UOMTargetQtyL2 Target Qty L2	  */
	public void setUOMTargetQtyL2 (BigDecimal UOMTargetQtyL2)
	{
		set_Value (COLUMNNAME_UOMTargetQtyL2, UOMTargetQtyL2);
	}

	/** Get Target Qty L2.
		@return Target Qty L2	  */
	public BigDecimal getUOMTargetQtyL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMTargetQtyL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Qty L3.
		@param UOMTargetQtyL3 Target Qty L3	  */
	public void setUOMTargetQtyL3 (BigDecimal UOMTargetQtyL3)
	{
		set_Value (COLUMNNAME_UOMTargetQtyL3, UOMTargetQtyL3);
	}

	/** Get Target Qty L3.
		@return Target Qty L3	  */
	public BigDecimal getUOMTargetQtyL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMTargetQtyL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Qty L4.
		@param UOMTargetQtyL4 Target Qty L4	  */
	public void setUOMTargetQtyL4 (BigDecimal UOMTargetQtyL4)
	{
		set_Value (COLUMNNAME_UOMTargetQtyL4, UOMTargetQtyL4);
	}

	/** Get Target Qty L4.
		@return Target Qty L4	  */
	public BigDecimal getUOMTargetQtyL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMTargetQtyL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}