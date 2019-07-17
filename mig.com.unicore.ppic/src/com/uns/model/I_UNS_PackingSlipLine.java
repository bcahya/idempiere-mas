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

/** Generated Interface for UNS_PackingSlipLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_PackingSlipLine 
{

    /** TableName=UNS_PackingSlipLine */
    public static final String Table_Name = "UNS_PackingSlipLine";

    /** AD_Table_ID=1000060 */
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

    /** Column name BPartner_Original_ID */
    public static final String COLUMNNAME_BPartner_Original_ID = "BPartner_Original_ID";

	/** Set Supplier Original	  */
	public void setBPartner_Original_ID (int BPartner_Original_ID);

	/** Get Supplier Original	  */
	public int getBPartner_Original_ID();

	public org.compiere.model.I_C_BPartner getBPartner_Original() throws RuntimeException;

    /** Column name BrutoQuantity */
    public static final String COLUMNNAME_BrutoQuantity = "BrutoQuantity";

	/** Set Bruto Quantity	  */
	public void setBrutoQuantity (BigDecimal BrutoQuantity);

	/** Get Bruto Quantity	  */
	public BigDecimal getBrutoQuantity();

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

    /** Column name HSCode */
    public static final String COLUMNNAME_HSCode = "HSCode";

	/** Set HS Code	  */
	public void setHSCode (String HSCode);

	/** Get HS Code	  */
	public String getHSCode();

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

    /** Column name M_InOutLineConfirm_ID */
    public static final String COLUMNNAME_M_InOutLineConfirm_ID = "M_InOutLineConfirm_ID";

	/** Set Ship/Receipt Confirmation Line.
	  * Material Shipment or Receipt Confirmation Line
	  */
	public void setM_InOutLineConfirm_ID (int M_InOutLineConfirm_ID);

	/** Get Ship/Receipt Confirmation Line.
	  * Material Shipment or Receipt Confirmation Line
	  */
	public int getM_InOutLineConfirm_ID();

	public org.compiere.model.I_M_InOutLineConfirm getM_InOutLineConfirm() throws RuntimeException;

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

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

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

    /** Column name PMSMCode */
    public static final String COLUMNNAME_PMSMCode = "PMSMCode";

	/** Set PM/SM Code	  */
	public void setPMSMCode (String PMSMCode);

	/** Get PM/SM Code	  */
	public String getPMSMCode();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Delivered Quantity.
	  * Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Delivered Quantity.
	  * Delivered Quantity
	  */
	public BigDecimal getQtyDelivered();

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/** Set Ordered Quantity.
	  * Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered);

	/** Get Ordered Quantity.
	  * Ordered Quantity
	  */
	public BigDecimal getQtyOrdered();

    /** Column name QtyReceived */
    public static final String COLUMNNAME_QtyReceived = "QtyReceived";

	/** Set Received Quantity.
	  * The quantity actually received
	  */
	public void setQtyReceived (BigDecimal QtyReceived);

	/** Get Received Quantity.
	  * The quantity actually received
	  */
	public BigDecimal getQtyReceived();

    /** Column name UNS_PackingSlipLine_ID */
    public static final String COLUMNNAME_UNS_PackingSlipLine_ID = "UNS_PackingSlipLine_ID";

	/** Set Packing Slip Line	  */
	public void setUNS_PackingSlipLine_ID (int UNS_PackingSlipLine_ID);

	/** Get Packing Slip Line	  */
	public int getUNS_PackingSlipLine_ID();

    /** Column name UNS_PackingSlipLine_UU */
    public static final String COLUMNNAME_UNS_PackingSlipLine_UU = "UNS_PackingSlipLine_UU";

	/** Set Packing Slip Line UU	  */
	public void setUNS_PackingSlipLine_UU (String UNS_PackingSlipLine_UU);

	/** Get Packing Slip Line UU	  */
	public String getUNS_PackingSlipLine_UU();

    /** Column name UNS_PackingSlipSupplier_ID */
    public static final String COLUMNNAME_UNS_PackingSlipSupplier_ID = "UNS_PackingSlipSupplier_ID";

	/** Set Packing Slip Supplier	  */
	public void setUNS_PackingSlipSupplier_ID (int UNS_PackingSlipSupplier_ID);

	/** Get Packing Slip Supplier	  */
	public int getUNS_PackingSlipSupplier_ID();

	public com.uns.model.I_UNS_PackingSlipSupplier getUNS_PackingSlipSupplier() throws RuntimeException;

    /** Column name UNS_PackingSlipSupplier_UU */
    public static final String COLUMNNAME_UNS_PackingSlipSupplier_UU = "UNS_PackingSlipSupplier_UU";

	/** Set PackingSlip Supplier UU	  */
	public void setUNS_PackingSlipSupplier_UU (String UNS_PackingSlipSupplier_UU);

	/** Get PackingSlip Supplier UU	  */
	public String getUNS_PackingSlipSupplier_UU();

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

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
