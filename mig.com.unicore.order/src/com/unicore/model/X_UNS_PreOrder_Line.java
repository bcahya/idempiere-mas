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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PreOrder_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PreOrder_Line extends PO implements I_UNS_PreOrder_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170711L;

    /** Standard Constructor */
    public X_UNS_PreOrder_Line (Properties ctx, int UNS_PreOrder_Line_ID, String trxName)
    {
      super (ctx, UNS_PreOrder_Line_ID, trxName);
      /** if (UNS_PreOrder_Line_ID == 0)
        {
			setC_UOM_ID (0);
			setLineNo (0);
// 0
			setQtyOrdered (Env.ZERO);
// 0
			setUNS_PreOrder_ID (0);
			setUOMQtyOrderedL2 (Env.ZERO);
// 0
			setUOMQtyOrderedL3 (Env.ZERO);
			setUOMQtyOrderedL4 (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_PreOrder_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PreOrder_Line[")
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered Quantity.
		@return Ordered Quantity
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_PreOrder getUNS_PreOrder() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PreOrder)MTable.get(getCtx(), com.unicore.model.I_UNS_PreOrder.Table_Name)
			.getPO(getUNS_PreOrder_ID(), get_TrxName());	}

	/** Set Pre Order.
		@param UNS_PreOrder_ID Pre Order	  */
	public void setUNS_PreOrder_ID (int UNS_PreOrder_ID)
	{
		if (UNS_PreOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PreOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PreOrder_ID, Integer.valueOf(UNS_PreOrder_ID));
	}

	/** Get Pre Order.
		@return Pre Order	  */
	public int getUNS_PreOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PreOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pre Order Line.
		@param UNS_PreOrder_Line_ID Pre Order Line	  */
	public void setUNS_PreOrder_Line_ID (int UNS_PreOrder_Line_ID)
	{
		if (UNS_PreOrder_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PreOrder_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PreOrder_Line_ID, Integer.valueOf(UNS_PreOrder_Line_ID));
	}

	/** Get Pre Order Line.
		@return Pre Order Line	  */
	public int getUNS_PreOrder_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PreOrder_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PreOrder_Line_UU.
		@param UNS_PreOrder_Line_UU UNS_PreOrder_Line_UU	  */
	public void setUNS_PreOrder_Line_UU (String UNS_PreOrder_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PreOrder_Line_UU, UNS_PreOrder_Line_UU);
	}

	/** Get UNS_PreOrder_Line_UU.
		@return UNS_PreOrder_Line_UU	  */
	public String getUNS_PreOrder_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PreOrder_Line_UU);
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

	/** Set Qty Ordered L1.
		@param UOMQtyOrderedL1 Qty Ordered L1	  */
	public void setUOMQtyOrderedL1 (BigDecimal UOMQtyOrderedL1)
	{
		set_Value (COLUMNNAME_UOMQtyOrderedL1, UOMQtyOrderedL1);
	}

	/** Get Qty Ordered L1.
		@return Qty Ordered L1	  */
	public BigDecimal getUOMQtyOrderedL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyOrderedL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Ordered L2.
		@param UOMQtyOrderedL2 Qty Ordered L2	  */
	public void setUOMQtyOrderedL2 (BigDecimal UOMQtyOrderedL2)
	{
		set_Value (COLUMNNAME_UOMQtyOrderedL2, UOMQtyOrderedL2);
	}

	/** Get Qty Ordered L2.
		@return Qty Ordered L2	  */
	public BigDecimal getUOMQtyOrderedL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyOrderedL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Ordered L3.
		@param UOMQtyOrderedL3 Qty Ordered L3	  */
	public void setUOMQtyOrderedL3 (BigDecimal UOMQtyOrderedL3)
	{
		set_Value (COLUMNNAME_UOMQtyOrderedL3, UOMQtyOrderedL3);
	}

	/** Get Qty Ordered L3.
		@return Qty Ordered L3	  */
	public BigDecimal getUOMQtyOrderedL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyOrderedL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Ordered L4.
		@param UOMQtyOrderedL4 Qty Ordered L4	  */
	public void setUOMQtyOrderedL4 (BigDecimal UOMQtyOrderedL4)
	{
		set_Value (COLUMNNAME_UOMQtyOrderedL4, UOMQtyOrderedL4);
	}

	/** Get Qty Ordered L4.
		@return Qty Ordered L4	  */
	public BigDecimal getUOMQtyOrderedL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyOrderedL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}