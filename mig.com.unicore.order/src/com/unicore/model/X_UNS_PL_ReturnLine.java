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

/** Generated Model for UNS_PL_ReturnLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ReturnLine extends PO implements I_UNS_PL_ReturnLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151218L;

    /** Standard Constructor */
    public X_UNS_PL_ReturnLine (Properties ctx, int UNS_PL_ReturnLine_ID, String trxName)
    {
      super (ctx, UNS_PL_ReturnLine_ID, trxName);
      /** if (UNS_PL_ReturnLine_ID == 0)
        {
			setCancelledQty (Env.ZERO);
			setC_OrderLine_ID (0);
			setC_UOM_ID (0);
			setMovementQty (Env.ZERO);
			setM_Product_ID (0);
			setReason (null);
			setUNS_PackingList_Order_ID (0);
			setUNS_PL_ReturnLine_ID (0);
			setUNS_PL_ReturnOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ReturnLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ReturnLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cancelled Qty.
		@param CancelledQty 
		The quantity of (product) order line eventually cancelled (returned) back to the warehouse
	  */
	public void setCancelledQty (BigDecimal CancelledQty)
	{
		set_Value (COLUMNNAME_CancelledQty, CancelledQty);
	}

	/** Get Cancelled Qty.
		@return The quantity of (product) order line eventually cancelled (returned) back to the warehouse
	  */
	public BigDecimal getCancelledQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CancelledQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
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
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
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

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_ValueNoCheck (COLUMNNAME_MovementQty, MovementQty);
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

	/** Other reason = NA */
	public static final String REASON_OtherReason = "NA";
	/** Toko tidak ditemukan = TK */
	public static final String REASON_TokoTidakDitemukan = "TK";
	/** Tutup = TP */
	public static final String REASON_Tutup = "TP";
	/** Terlambat = TR */
	public static final String REASON_Terlambat = "TR";
	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{

		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

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

	/** Set PL Return Line.
		@param UNS_PL_ReturnLine_ID PL Return Line	  */
	public void setUNS_PL_ReturnLine_ID (int UNS_PL_ReturnLine_ID)
	{
		if (UNS_PL_ReturnLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnLine_ID, Integer.valueOf(UNS_PL_ReturnLine_ID));
	}

	/** Get PL Return Line.
		@return PL Return Line	  */
	public int getUNS_PL_ReturnLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ReturnLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PL_ReturnLine_UU.
		@param UNS_PL_ReturnLine_UU UNS_PL_ReturnLine_UU	  */
	public void setUNS_PL_ReturnLine_UU (String UNS_PL_ReturnLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnLine_UU, UNS_PL_ReturnLine_UU);
	}

	/** Get UNS_PL_ReturnLine_UU.
		@return UNS_PL_ReturnLine_UU	  */
	public String getUNS_PL_ReturnLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ReturnLine_UU);
	}

	/** Set Canceled Shipment.
		@param UNS_PL_ReturnOrder_ID Canceled Shipment	  */
	public void setUNS_PL_ReturnOrder_ID (int UNS_PL_ReturnOrder_ID)
	{
		if (UNS_PL_ReturnOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnOrder_ID, Integer.valueOf(UNS_PL_ReturnOrder_ID));
	}

	/** Get Canceled Shipment.
		@return Canceled Shipment	  */
	public int getUNS_PL_ReturnOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ReturnOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UOMCancelled Qty L1.
		@param UOMCancelledQtyL1 UOMCancelled Qty L1	  */
	public void setUOMCancelledQtyL1 (BigDecimal UOMCancelledQtyL1)
	{
		set_Value (COLUMNNAME_UOMCancelledQtyL1, UOMCancelledQtyL1);
	}

	/** Get UOMCancelled Qty L1.
		@return UOMCancelled Qty L1	  */
	public BigDecimal getUOMCancelledQtyL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMCancelledQtyL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UOMCancelled Qty L2.
		@param UOMCancelledQtyL2 UOMCancelled Qty L2	  */
	public void setUOMCancelledQtyL2 (BigDecimal UOMCancelledQtyL2)
	{
		set_Value (COLUMNNAME_UOMCancelledQtyL2, UOMCancelledQtyL2);
	}

	/** Get UOMCancelled Qty L2.
		@return UOMCancelled Qty L2	  */
	public BigDecimal getUOMCancelledQtyL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMCancelledQtyL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UOMCancelled Qty L3.
		@param UOMCancelledQtyL3 UOMCancelled Qty L3	  */
	public void setUOMCancelledQtyL3 (BigDecimal UOMCancelledQtyL3)
	{
		set_Value (COLUMNNAME_UOMCancelledQtyL3, UOMCancelledQtyL3);
	}

	/** Get UOMCancelled Qty L3.
		@return UOMCancelled Qty L3	  */
	public BigDecimal getUOMCancelledQtyL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMCancelledQtyL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UOMCancelled Qty L4.
		@param UOMCancelledQtyL4 UOMCancelled Qty L4	  */
	public void setUOMCancelledQtyL4 (BigDecimal UOMCancelledQtyL4)
	{
		set_Value (COLUMNNAME_UOMCancelledQtyL4, UOMCancelledQtyL4);
	}

	/** Get UOMCancelled Qty L4.
		@return UOMCancelled Qty L4	  */
	public BigDecimal getUOMCancelledQtyL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMCancelledQtyL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_UOMConversionL1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UOMConversionL1_ID, Integer.valueOf(UOMConversionL1_ID));
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
			set_ValueNoCheck (COLUMNNAME_UOMConversionL2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UOMConversionL2_ID, Integer.valueOf(UOMConversionL2_ID));
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
			set_ValueNoCheck (COLUMNNAME_UOMConversionL3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UOMConversionL3_ID, Integer.valueOf(UOMConversionL3_ID));
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
			set_ValueNoCheck (COLUMNNAME_UOMConversionL4_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UOMConversionL4_ID, Integer.valueOf(UOMConversionL4_ID));
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
}