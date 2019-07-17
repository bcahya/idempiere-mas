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

/** Generated Model for UNS_Cvs_RptProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Cvs_RptProduct extends PO implements I_UNS_Cvs_RptProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151001L;

    /** Standard Constructor */
    public X_UNS_Cvs_RptProduct (Properties ctx, int UNS_Cvs_RptProduct_ID, String trxName)
    {
      super (ctx, UNS_Cvs_RptProduct_ID, trxName);
      /** if (UNS_Cvs_RptProduct_ID == 0)
        {
			setC_UOM_ID (0);
			setLineNetAmt (Env.ZERO);
// 0
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setPriceActual (Env.ZERO);
// 0
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
// 0
			setProcessed (false);
// N
			setQtySold (Env.ZERO);
// 0
			setUNS_Cvs_RptProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Cvs_RptProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Cvs_RptProduct[")
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

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Unit Price.
		@param PriceActual 
		Actual Price
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_ValueNoCheck (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit Price.
		@param PriceLimit 
		Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Limit Price.
		@return Lowest price for a product
	  */
	public BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable)
	{
		throw new IllegalArgumentException ("QtyAvailable is virtual column");	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Sold.
		@param QtySold Quantity Sold	  */
	public void setQtySold (BigDecimal QtySold)
	{
		set_Value (COLUMNNAME_QtySold, QtySold);
	}

	/** Get Quantity Sold.
		@return Quantity Sold	  */
	public BigDecimal getQtySold () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySold);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_Cvs_RptCustomer getUNS_Cvs_RptCustomer() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Cvs_RptCustomer)MTable.get(getCtx(), com.unicore.model.I_UNS_Cvs_RptCustomer.Table_Name)
			.getPO(getUNS_Cvs_RptCustomer_ID(), get_TrxName());	}

	/** Set Customer.
		@param UNS_Cvs_RptCustomer_ID Customer	  */
	public void setUNS_Cvs_RptCustomer_ID (int UNS_Cvs_RptCustomer_ID)
	{
		if (UNS_Cvs_RptCustomer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptCustomer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptCustomer_ID, Integer.valueOf(UNS_Cvs_RptCustomer_ID));
	}

	/** Get Customer.
		@return Customer	  */
	public int getUNS_Cvs_RptCustomer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_RptCustomer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canvas Product Report.
		@param UNS_Cvs_RptProduct_ID Canvas Product Report	  */
	public void setUNS_Cvs_RptProduct_ID (int UNS_Cvs_RptProduct_ID)
	{
		if (UNS_Cvs_RptProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptProduct_ID, Integer.valueOf(UNS_Cvs_RptProduct_ID));
	}

	/** Get Canvas Product Report.
		@return Canvas Product Report	  */
	public int getUNS_Cvs_RptProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_RptProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Cvs_RptProduct_UU.
		@param UNS_Cvs_RptProduct_UU UNS_Cvs_RptProduct_UU	  */
	public void setUNS_Cvs_RptProduct_UU (String UNS_Cvs_RptProduct_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptProduct_UU, UNS_Cvs_RptProduct_UU);
	}

	/** Get UNS_Cvs_RptProduct_UU.
		@return UNS_Cvs_RptProduct_UU	  */
	public String getUNS_Cvs_RptProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Cvs_RptProduct_UU);
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

	/** Set Quantity Available L1.
		@param UOMQtyAvailableL1 Quantity Available L1	  */
	public void setUOMQtyAvailableL1 (BigDecimal UOMQtyAvailableL1)
	{
		throw new IllegalArgumentException ("UOMQtyAvailableL1 is virtual column");	}

	/** Get Quantity Available L1.
		@return Quantity Available L1	  */
	public BigDecimal getUOMQtyAvailableL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyAvailableL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Available L2.
		@param UOMQtyAvailableL2 Quantity Available L2	  */
	public void setUOMQtyAvailableL2 (BigDecimal UOMQtyAvailableL2)
	{
		throw new IllegalArgumentException ("UOMQtyAvailableL2 is virtual column");	}

	/** Get Quantity Available L2.
		@return Quantity Available L2	  */
	public BigDecimal getUOMQtyAvailableL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyAvailableL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Available L3.
		@param UOMQtyAvailableL3 Quantity Available L3	  */
	public void setUOMQtyAvailableL3 (BigDecimal UOMQtyAvailableL3)
	{
		throw new IllegalArgumentException ("UOMQtyAvailableL3 is virtual column");	}

	/** Get Quantity Available L3.
		@return Quantity Available L3	  */
	public BigDecimal getUOMQtyAvailableL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyAvailableL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Available L4.
		@param UOMQtyAvailableL4 Quantity Available L4	  */
	public void setUOMQtyAvailableL4 (BigDecimal UOMQtyAvailableL4)
	{
		throw new IllegalArgumentException ("UOMQtyAvailableL4 is virtual column");	}

	/** Get Quantity Available L4.
		@return Quantity Available L4	  */
	public BigDecimal getUOMQtyAvailableL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtyAvailableL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sold Quantity L1.
		@param UOMQtySoldL1 Sold Quantity L1	  */
	public void setUOMQtySoldL1 (BigDecimal UOMQtySoldL1)
	{
		set_Value (COLUMNNAME_UOMQtySoldL1, UOMQtySoldL1);
	}

	/** Get Sold Quantity L1.
		@return Sold Quantity L1	  */
	public BigDecimal getUOMQtySoldL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtySoldL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sold Quantity L2.
		@param UOMQtySoldL2 Sold Quantity L2	  */
	public void setUOMQtySoldL2 (BigDecimal UOMQtySoldL2)
	{
		set_Value (COLUMNNAME_UOMQtySoldL2, UOMQtySoldL2);
	}

	/** Get Sold Quantity L2.
		@return Sold Quantity L2	  */
	public BigDecimal getUOMQtySoldL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtySoldL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sold Quantity L3.
		@param UOMQtySoldL3 Sold Quantity L3	  */
	public void setUOMQtySoldL3 (BigDecimal UOMQtySoldL3)
	{
		set_Value (COLUMNNAME_UOMQtySoldL3, UOMQtySoldL3);
	}

	/** Get Sold Quantity L3.
		@return Sold Quantity L3	  */
	public BigDecimal getUOMQtySoldL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtySoldL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sold Quantity L4.
		@param UOMQtySoldL4 Sold Quantity L4	  */
	public void setUOMQtySoldL4 (BigDecimal UOMQtySoldL4)
	{
		set_Value (COLUMNNAME_UOMQtySoldL4, UOMQtySoldL4);
	}

	/** Get Sold Quantity L4.
		@return Sold Quantity L4	  */
	public BigDecimal getUOMQtySoldL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMQtySoldL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setisManual(boolean isManual) {
		set_Value (COLUMNNAME_isManual, Boolean.valueOf(isManual));
	}

	@Override
	public boolean isManual() {
		Object oo = get_Value(COLUMNNAME_isManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}

