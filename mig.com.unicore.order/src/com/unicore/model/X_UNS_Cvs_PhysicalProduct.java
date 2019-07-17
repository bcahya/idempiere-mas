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
import java.util.logging.Level;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Cvs_PhysicalProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Cvs_PhysicalProduct extends PO implements I_UNS_Cvs_PhysicalProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150325L;

    /** Standard Constructor */
    public X_UNS_Cvs_PhysicalProduct (Properties ctx, int UNS_Cvs_PhysicalProduct_ID, String trxName)
    {
      super (ctx, UNS_Cvs_PhysicalProduct_ID, trxName);
      /** if (UNS_Cvs_PhysicalProduct_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
			setQtyBook (Env.ZERO);
			setQtyCount (Env.ZERO);
			setUNS_Cvs_Physical_ID (0);
			setUNS_Cvs_PhysicalProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Cvs_PhysicalProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Cvs_PhysicalProduct[")
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
		log.log(Level.INFO, "Try to set Virtual Column");}

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

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
    }

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity book.
		@param QtyBook 
		Book Quantity
	  */
	public void setQtyBook (BigDecimal QtyBook)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBook, QtyBook);
	}

	/** Get Quantity book.
		@return Book Quantity
	  */
	public BigDecimal getQtyBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBook);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_InventoryLine getReversalLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InventoryLine)MTable.get(getCtx(), org.compiere.model.I_M_InventoryLine.Table_Name)
			.getPO(getReversalLine_ID(), get_TrxName());	}

	/** Set Reversal Line.
		@param ReversalLine_ID 
		Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Reversal Line.
		@return Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Cvs_Physical getUNS_Cvs_Physical() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Cvs_Physical)MTable.get(getCtx(), com.unicore.model.I_UNS_Cvs_Physical.Table_Name)
			.getPO(getUNS_Cvs_Physical_ID(), get_TrxName());	}

	/** Set Canvas Physical Inventory.
		@param UNS_Cvs_Physical_ID Canvas Physical Inventory	  */
	public void setUNS_Cvs_Physical_ID (int UNS_Cvs_Physical_ID)
	{
		if (UNS_Cvs_Physical_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Physical_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Physical_ID, Integer.valueOf(UNS_Cvs_Physical_ID));
	}

	/** Get Canvas Physical Inventory.
		@return Canvas Physical Inventory	  */
	public int getUNS_Cvs_Physical_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_Physical_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canvas Physical Product.
		@param UNS_Cvs_PhysicalProduct_ID Canvas Physical Product	  */
	public void setUNS_Cvs_PhysicalProduct_ID (int UNS_Cvs_PhysicalProduct_ID)
	{
		if (UNS_Cvs_PhysicalProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_PhysicalProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_PhysicalProduct_ID, Integer.valueOf(UNS_Cvs_PhysicalProduct_ID));
	}

	/** Get Canvas Physical Product.
		@return Canvas Physical Product	  */
	public int getUNS_Cvs_PhysicalProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_PhysicalProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Cvs_PhysicalProduct_UU.
		@param UNS_Cvs_PhysicalProduct_UU UNS_Cvs_PhysicalProduct_UU	  */
	public void setUNS_Cvs_PhysicalProduct_UU (String UNS_Cvs_PhysicalProduct_UU)
	{
		set_Value (COLUMNNAME_UNS_Cvs_PhysicalProduct_UU, UNS_Cvs_PhysicalProduct_UU);
	}

	/** Get UNS_Cvs_PhysicalProduct_UU.
		@return UNS_Cvs_PhysicalProduct_UU	  */
	public String getUNS_Cvs_PhysicalProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Cvs_PhysicalProduct_UU);
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
		throw new IllegalArgumentException ("UOMConversionL1_ID is virtual column");	}

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
		throw new IllegalArgumentException ("UOMConversionL2_ID is virtual column");	}

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
		throw new IllegalArgumentException ("UOMConversionL3_ID is virtual column");	}

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
		throw new IllegalArgumentException ("UOMConversionL4_ID is virtual column");	}

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

	/** Set Qty Book L1.
		@param UOMQtyBookL1 Qty Book L1	  */
	public void setUOMQtyBookL1 (BigDecimal UOMQtyBookL1)
	{
		set_Value (COLUMNNAME_UOMQtyBookL1, UOMQtyBookL1);
	}

	/** Get Qty Book L1.
		@return Qty Book L1	  */
	public BigDecimal getUOMQtyBookL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyBookL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Book L2.
		@param UOMQtyBookL2 Qty Book L2	  */
	public void setUOMQtyBookL2 (BigDecimal UOMQtyBookL2)
	{
		set_Value (COLUMNNAME_UOMQtyBookL2, UOMQtyBookL2);
	}

	/** Get Qty Book L2.
		@return Qty Book L2	  */
	public BigDecimal getUOMQtyBookL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyBookL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Book L3.
		@param UOMQtyBookL3 Qty Book L3	  */
	public void setUOMQtyBookL3 (BigDecimal UOMQtyBookL3)
	{
		set_Value (COLUMNNAME_UOMQtyBookL3, UOMQtyBookL3);
	}

	/** Get Qty Book L3.
		@return Qty Book L3	  */
	public BigDecimal getUOMQtyBookL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyBookL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Book L4.
		@param UOMQtyBookL4 Qty Book L4	  */
	public void setUOMQtyBookL4 (BigDecimal UOMQtyBookL4)
	{
		set_Value (COLUMNNAME_UOMQtyBookL4, UOMQtyBookL4);
	}

	/** Get Qty Book L4.
		@return Qty Book L4	  */
	public BigDecimal getUOMQtyBookL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyBookL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Count L1.
		@param UOMQtyCountL1 Qty Count L1	  */
	public void setUOMQtyCountL1 (BigDecimal UOMQtyCountL1)
	{
		set_Value (COLUMNNAME_UOMQtyCountL1, UOMQtyCountL1);
	}

	/** Get Qty Count L1.
		@return Qty Count L1	  */
	public BigDecimal getUOMQtyCountL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyCountL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Count L2.
		@param UOMQtyCountL2 Qty Count L2	  */
	public void setUOMQtyCountL2 (BigDecimal UOMQtyCountL2)
	{
		set_Value (COLUMNNAME_UOMQtyCountL2, UOMQtyCountL2);
	}

	/** Get Qty Count L2.
		@return Qty Count L2	  */
	public BigDecimal getUOMQtyCountL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyCountL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Count L3.
		@param UOMQtyCountL3 Qty Count L3	  */
	public void setUOMQtyCountL3 (BigDecimal UOMQtyCountL3)
	{
		set_Value (COLUMNNAME_UOMQtyCountL3, UOMQtyCountL3);
	}

	/** Get Qty Count L3.
		@return Qty Count L3	  */
	public BigDecimal getUOMQtyCountL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyCountL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Count L4.
		@param UOMQtyCountL4 Qty Count L4	  */
	public void setUOMQtyCountL4 (BigDecimal UOMQtyCountL4)
	{
		set_Value (COLUMNNAME_UOMQtyCountL4, UOMQtyCountL4);
	}

	/** Get Qty Count L4.
		@return Qty Count L4	  */
	public BigDecimal getUOMQtyCountL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyCountL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		throw new IllegalArgumentException ("Value is virtual column");	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}