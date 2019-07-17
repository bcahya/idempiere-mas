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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_SOShipment
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SOShipment extends PO implements I_UNS_SOShipment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131208L;

    /** Standard Constructor */
    public X_UNS_SOShipment (Properties ctx, int UNS_SOShipment_ID, String trxName)
    {
      super (ctx, UNS_SOShipment_ID, trxName);
      /** if (UNS_SOShipment_ID == 0)
        {
			setContainerVolume (Env.ZERO);
// 0
			setM_AttributeSetInstance_ID (0);
			setM_Warehouse_ID (0);
// @0|M_Warehouse_ID@
			setQtyMT (Env.ZERO);
			setQtyUom (Env.ZERO);
			setUNS_ContainerDeparture_ID (0);
			setUNS_SOShipment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SOShipment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SOShipment[")
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

	/** Set Container Volume.
		@param ContainerVolume Container Volume	  */
	public void setContainerVolume (BigDecimal ContainerVolume)
	{
		set_Value (COLUMNNAME_ContainerVolume, ContainerVolume);
	}

	/** Get Container Volume.
		@return Container Volume	  */
	public BigDecimal getContainerVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ContainerVolume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Min. Production Date.
		@param MinProductionDate 
		The minimum date of production
	  */
	public void setMinProductionDate (Timestamp MinProductionDate)
	{
		set_Value (COLUMNNAME_MinProductionDate, MinProductionDate);
	}

	/** Get Min. Production Date.
		@return The minimum date of production
	  */
	public Timestamp getMinProductionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MinProductionDate);
	}

	/** Set Qty Ordered (MT).
		@param QtyMT 
		Quantity to be ordered in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_Value (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Qty Ordered (MT).
		@return Quantity to be ordered in Metric Ton
	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Ordered (UOM).
		@param QtyUom 
		Quantity to be ordered in uom
	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_Value (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Qty Ordered (UOM).
		@return Quantity to be ordered in uom
	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getQtyUom()));
    }

	/** Set Remark Product.
		@param RemarkProduct Remark Product	  */
	public void setRemarkProduct (String RemarkProduct)
	{
		set_Value (COLUMNNAME_RemarkProduct, RemarkProduct);
	}

	/** Get Remark Product.
		@return Remark Product	  */
	public String getRemarkProduct () 
	{
		return (String)get_Value(COLUMNNAME_RemarkProduct);
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_ValueNoCheck (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Set Remark Sales Order.
		@param RemarkSO Remark Sales Order	  */
	public void setRemarkSO (String RemarkSO)
	{
		set_Value (COLUMNNAME_RemarkSO, RemarkSO);
	}

	/** Get Remark Sales Order.
		@return Remark Sales Order	  */
	public String getRemarkSO () 
	{
		return (String)get_Value(COLUMNNAME_RemarkSO);
	}

	/** Set Shipping Mark.
		@param ShippingMark Shipping Mark	  */
	public void setShippingMark (String ShippingMark)
	{
		set_Value (COLUMNNAME_ShippingMark, ShippingMark);
	}

	/** Get Shipping Mark.
		@return Shipping Mark	  */
	public String getShippingMark () 
	{
		return (String)get_Value(COLUMNNAME_ShippingMark);
	}

	public com.uns.model.I_UNS_BCCode getUNS_BCCode() throws RuntimeException
    {
		return (com.uns.model.I_UNS_BCCode)MTable.get(getCtx(), com.uns.model.I_UNS_BCCode.Table_Name)
			.getPO(getUNS_BCCode_ID(), get_TrxName());	}

	/** Set Bea Cukai Code.
		@param UNS_BCCode_ID Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID)
	{
		if (UNS_BCCode_ID < 1) 
			set_Value (COLUMNNAME_UNS_BCCode_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BCCode_ID, Integer.valueOf(UNS_BCCode_ID));
	}

	/** Get Bea Cukai Code.
		@return Bea Cukai Code	  */
	public int getUNS_BCCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_ContainerDeparture getUNS_ContainerDeparture() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ContainerDeparture)MTable.get(getCtx(), com.uns.model.I_UNS_ContainerDeparture.Table_Name)
			.getPO(getUNS_ContainerDeparture_ID(), get_TrxName());	}

	/** Set Container Departure.
		@param UNS_ContainerDeparture_ID Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID)
	{
		if (UNS_ContainerDeparture_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerDeparture_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerDeparture_ID, Integer.valueOf(UNS_ContainerDeparture_ID));
	}

	/** Get Container Departure.
		@return Container Departure	  */
	public int getUNS_ContainerDeparture_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerDeparture_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Order Shipment.
		@param UNS_SOShipment_ID Sales Order Shipment	  */
	public void setUNS_SOShipment_ID (int UNS_SOShipment_ID)
	{
		if (UNS_SOShipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SOShipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SOShipment_ID, Integer.valueOf(UNS_SOShipment_ID));
	}

	/** Get Sales Order Shipment.
		@return Sales Order Shipment	  */
	public int getUNS_SOShipment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SOShipment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SOShipment_UU.
		@param UNS_SOShipment_UU UNS_SOShipment_UU	  */
	public void setUNS_SOShipment_UU (String UNS_SOShipment_UU)
	{
		set_Value (COLUMNNAME_UNS_SOShipment_UU, UNS_SOShipment_UU);
	}

	/** Get UNS_SOShipment_UU.
		@return UNS_SOShipment_UU	  */
	public String getUNS_SOShipment_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SOShipment_UU);
	}
}