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

/** Generated Model for UNS_PackingSlipLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PackingSlipLine extends PO implements I_UNS_PackingSlipLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140201L;

    /** Standard Constructor */
    public X_UNS_PackingSlipLine (Properties ctx, int UNS_PackingSlipLine_ID, String trxName)
    {
      super (ctx, UNS_PackingSlipLine_ID, trxName);
      /** if (UNS_PackingSlipLine_ID == 0)
        {
			setBrutoQuantity (Env.ZERO);
			setC_UOM_ID (0);
			setM_AttributeSetInstance_ID (0);
// 0
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
// N
			setQtyDelivered (Env.ZERO);
// 0
			setQtyReceived (Env.ZERO);
// 0
			setUNS_PackingSlipLine_ID (0);
			setUNS_PackingSlipSupplier_ID (0);
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_PackingSlipLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PackingSlipLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getBPartner_Original() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getBPartner_Original_ID(), get_TrxName());	}

	/** Set Supplier Original.
		@param BPartner_Original_ID Supplier Original	  */
	public void setBPartner_Original_ID (int BPartner_Original_ID)
	{
		if (BPartner_Original_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BPartner_Original_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BPartner_Original_ID, Integer.valueOf(BPartner_Original_ID));
	}

	/** Get Supplier Original.
		@return Supplier Original	  */
	public int getBPartner_Original_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BPartner_Original_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bruto Quantity.
		@param BrutoQuantity Bruto Quantity	  */
	public void setBrutoQuantity (BigDecimal BrutoQuantity)
	{
		set_Value (COLUMNNAME_BrutoQuantity, BrutoQuantity);
	}

	/** Get Bruto Quantity.
		@return Bruto Quantity	  */
	public BigDecimal getBrutoQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BrutoQuantity);
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

	/** Set HS Code.
		@param HSCode HS Code	  */
	public void setHSCode (String HSCode)
	{
		set_Value (COLUMNNAME_HSCode, HSCode);
	}

	/** Get HS Code.
		@return HS Code	  */
	public String getHSCode () 
	{
		return (String)get_Value(COLUMNNAME_HSCode);
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

	public org.compiere.model.I_M_InOutLineConfirm getM_InOutLineConfirm() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLineConfirm)MTable.get(getCtx(), org.compiere.model.I_M_InOutLineConfirm.Table_Name)
			.getPO(getM_InOutLineConfirm_ID(), get_TrxName());	}

	/** Set Ship/Receipt Confirmation Line.
		@param M_InOutLineConfirm_ID 
		Material Shipment or Receipt Confirmation Line
	  */
	public void setM_InOutLineConfirm_ID (int M_InOutLineConfirm_ID)
	{
		if (M_InOutLineConfirm_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLineConfirm_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLineConfirm_ID, Integer.valueOf(M_InOutLineConfirm_ID));
	}

	/** Get Ship/Receipt Confirmation Line.
		@return Material Shipment or Receipt Confirmation Line
	  */
	public int getM_InOutLineConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLineConfirm_ID);
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

	/** Set PM/SM Code.
		@param PMSMCode PM/SM Code	  */
	public void setPMSMCode (String PMSMCode)
	{
		set_Value (COLUMNNAME_PMSMCode, PMSMCode);
	}

	/** Get PM/SM Code.
		@return PM/SM Code	  */
	public String getPMSMCode () 
	{
		return (String)get_Value(COLUMNNAME_PMSMCode);
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

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Received Quantity.
		@param QtyReceived 
		The quantity actually received
	  */
	public void setQtyReceived (BigDecimal QtyReceived)
	{
		set_Value (COLUMNNAME_QtyReceived, QtyReceived);
	}

	/** Get Received Quantity.
		@return The quantity actually received
	  */
	public BigDecimal getQtyReceived () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReceived);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Packing Slip Line.
		@param UNS_PackingSlipLine_ID Packing Slip Line	  */
	public void setUNS_PackingSlipLine_ID (int UNS_PackingSlipLine_ID)
	{
		if (UNS_PackingSlipLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipLine_ID, Integer.valueOf(UNS_PackingSlipLine_ID));
	}

	/** Get Packing Slip Line.
		@return Packing Slip Line	  */
	public int getUNS_PackingSlipLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlipLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packing Slip Line UU.
		@param UNS_PackingSlipLine_UU Packing Slip Line UU	  */
	public void setUNS_PackingSlipLine_UU (String UNS_PackingSlipLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipLine_UU, UNS_PackingSlipLine_UU);
	}

	/** Get Packing Slip Line UU.
		@return Packing Slip Line UU	  */
	public String getUNS_PackingSlipLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingSlipLine_UU);
	}

	public com.uns.model.I_UNS_PackingSlipSupplier getUNS_PackingSlipSupplier() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PackingSlipSupplier)MTable.get(getCtx(), com.uns.model.I_UNS_PackingSlipSupplier.Table_Name)
			.getPO(getUNS_PackingSlipSupplier_ID(), get_TrxName());	}

	/** Set Packing Slip Supplier.
		@param UNS_PackingSlipSupplier_ID Packing Slip Supplier	  */
	public void setUNS_PackingSlipSupplier_ID (int UNS_PackingSlipSupplier_ID)
	{
		if (UNS_PackingSlipSupplier_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipSupplier_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipSupplier_ID, Integer.valueOf(UNS_PackingSlipSupplier_ID));
	}

	/** Get Packing Slip Supplier.
		@return Packing Slip Supplier	  */
	public int getUNS_PackingSlipSupplier_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlipSupplier_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PackingSlip Supplier UU.
		@param UNS_PackingSlipSupplier_UU PackingSlip Supplier UU	  */
	public void setUNS_PackingSlipSupplier_UU (String UNS_PackingSlipSupplier_UU)
	{
		set_Value (COLUMNNAME_UNS_PackingSlipSupplier_UU, UNS_PackingSlipSupplier_UU);
	}

	/** Get PackingSlip Supplier UU.
		@return PackingSlip Supplier UU	  */
	public String getUNS_PackingSlipSupplier_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingSlipSupplier_UU);
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}