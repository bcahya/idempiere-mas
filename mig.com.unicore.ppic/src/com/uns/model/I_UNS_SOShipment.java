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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_SOShipment
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_SOShipment 
{

    /** TableName=UNS_SOShipment */
    public static final String Table_Name = "UNS_SOShipment";

    /** AD_Table_ID=1000111 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name ContainerVolume */
    public static final String COLUMNNAME_ContainerVolume = "ContainerVolume";

	/** Set Container Volume	  */
	public void setContainerVolume (BigDecimal ContainerVolume);

	/** Get Container Volume	  */
	public BigDecimal getContainerVolume();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/** Set Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/** Get Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name MinProductionDate */
    public static final String COLUMNNAME_MinProductionDate = "MinProductionDate";

	/** Set Min. Production Date.
	  * The minimum date of production
	  */
	public void setMinProductionDate (Timestamp MinProductionDate);

	/** Get Min. Production Date.
	  * The minimum date of production
	  */
	public Timestamp getMinProductionDate();

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Qty Ordered (MT).
	  * Quantity to be ordered in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Qty Ordered (MT).
	  * Quantity to be ordered in Metric Ton
	  */
	public BigDecimal getQtyMT();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Qty Ordered (UOM).
	  * Quantity to be ordered in uom
	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Qty Ordered (UOM).
	  * Quantity to be ordered in uom
	  */
	public BigDecimal getQtyUom();

    /** Column name RemarkProduct */
    public static final String COLUMNNAME_RemarkProduct = "RemarkProduct";

	/** Set Remark Product	  */
	public void setRemarkProduct (String RemarkProduct);

	/** Get Remark Product	  */
	public String getRemarkProduct();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name RemarkSO */
    public static final String COLUMNNAME_RemarkSO = "RemarkSO";

	/** Set Remark Sales Order	  */
	public void setRemarkSO (String RemarkSO);

	/** Get Remark Sales Order	  */
	public String getRemarkSO();

    /** Column name ShippingMark */
    public static final String COLUMNNAME_ShippingMark = "ShippingMark";

	/** Set Shipping Mark	  */
	public void setShippingMark (String ShippingMark);

	/** Get Shipping Mark	  */
	public String getShippingMark();

    /** Column name UNS_BCCode_ID */
    public static final String COLUMNNAME_UNS_BCCode_ID = "UNS_BCCode_ID";

	/** Set Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID);

	/** Get Bea Cukai Code	  */
	public int getUNS_BCCode_ID();

	public com.uns.model.I_UNS_BCCode getUNS_BCCode() throws RuntimeException;

    /** Column name UNS_ContainerDeparture_ID */
    public static final String COLUMNNAME_UNS_ContainerDeparture_ID = "UNS_ContainerDeparture_ID";

	/** Set Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID);

	/** Get Container Departure	  */
	public int getUNS_ContainerDeparture_ID();

	public com.uns.model.I_UNS_ContainerDeparture getUNS_ContainerDeparture() throws RuntimeException;

    /** Column name UNS_SOShipment_ID */
    public static final String COLUMNNAME_UNS_SOShipment_ID = "UNS_SOShipment_ID";

	/** Set Sales Order Shipment	  */
	public void setUNS_SOShipment_ID (int UNS_SOShipment_ID);

	/** Get Sales Order Shipment	  */
	public int getUNS_SOShipment_ID();

    /** Column name UNS_SOShipment_UU */
    public static final String COLUMNNAME_UNS_SOShipment_UU = "UNS_SOShipment_UU";

	/** Set UNS_SOShipment_UU	  */
	public void setUNS_SOShipment_UU (String UNS_SOShipment_UU);

	/** Get UNS_SOShipment_UU	  */
	public String getUNS_SOShipment_UU();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
