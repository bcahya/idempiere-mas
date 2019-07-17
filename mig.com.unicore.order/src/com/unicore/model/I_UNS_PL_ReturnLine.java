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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_PL_ReturnLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PL_ReturnLine 
{

    /** TableName=UNS_PL_ReturnLine */
    public static final String Table_Name = "UNS_PL_ReturnLine";

    /** AD_Table_ID=1000285 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name CancelledQty */
    public static final String COLUMNNAME_CancelledQty = "CancelledQty";

	/** Set Cancelled Qty.
	  * The quantity of (product) order line eventually cancelled (returned) back to the warehouse
	  */
	public void setCancelledQty (BigDecimal CancelledQty);

	/** Get Cancelled Qty.
	  * The quantity of (product) order line eventually cancelled (returned) back to the warehouse
	  */
	public BigDecimal getCancelledQty();

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

	public I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

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

    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/** Set Reason	  */
	public void setReason (String Reason);

	/** Get Reason	  */
	public String getReason();

    /** Column name UNS_PackingList_Order_ID */
    public static final String COLUMNNAME_UNS_PackingList_Order_ID = "UNS_PackingList_Order_ID";

	/** Set Packing List Order	  */
	public void setUNS_PackingList_Order_ID (int UNS_PackingList_Order_ID);

	/** Get Packing List Order	  */
	public int getUNS_PackingList_Order_ID();

    /** Column name UNS_PL_ReturnLine_ID */
    public static final String COLUMNNAME_UNS_PL_ReturnLine_ID = "UNS_PL_ReturnLine_ID";

	/** Set PL Return Line	  */
	public void setUNS_PL_ReturnLine_ID (int UNS_PL_ReturnLine_ID);

	/** Get PL Return Line	  */
	public int getUNS_PL_ReturnLine_ID();

    /** Column name UNS_PL_ReturnLine_UU */
    public static final String COLUMNNAME_UNS_PL_ReturnLine_UU = "UNS_PL_ReturnLine_UU";

	/** Set UNS_PL_ReturnLine_UU	  */
	public void setUNS_PL_ReturnLine_UU (String UNS_PL_ReturnLine_UU);

	/** Get UNS_PL_ReturnLine_UU	  */
	public String getUNS_PL_ReturnLine_UU();

    /** Column name UNS_PL_ReturnOrder_ID */
    public static final String COLUMNNAME_UNS_PL_ReturnOrder_ID = "UNS_PL_ReturnOrder_ID";

	/** Set Canceled Shipment	  */
	public void setUNS_PL_ReturnOrder_ID (int UNS_PL_ReturnOrder_ID);

	/** Get Canceled Shipment	  */
	public int getUNS_PL_ReturnOrder_ID();

    /** Column name UOMCancelledQtyL1 */
    public static final String COLUMNNAME_UOMCancelledQtyL1 = "UOMCancelledQtyL1";

	/** Set UOMCancelled Qty L1	  */
	public void setUOMCancelledQtyL1 (BigDecimal UOMCancelledQtyL1);

	/** Get UOMCancelled Qty L1	  */
	public BigDecimal getUOMCancelledQtyL1();

    /** Column name UOMCancelledQtyL2 */
    public static final String COLUMNNAME_UOMCancelledQtyL2 = "UOMCancelledQtyL2";

	/** Set UOMCancelled Qty L2	  */
	public void setUOMCancelledQtyL2 (BigDecimal UOMCancelledQtyL2);

	/** Get UOMCancelled Qty L2	  */
	public BigDecimal getUOMCancelledQtyL2();

    /** Column name UOMCancelledQtyL3 */
    public static final String COLUMNNAME_UOMCancelledQtyL3 = "UOMCancelledQtyL3";

	/** Set UOMCancelled Qty L3	  */
	public void setUOMCancelledQtyL3 (BigDecimal UOMCancelledQtyL3);

	/** Get UOMCancelled Qty L3	  */
	public BigDecimal getUOMCancelledQtyL3();

    /** Column name UOMCancelledQtyL4 */
    public static final String COLUMNNAME_UOMCancelledQtyL4 = "UOMCancelledQtyL4";

	/** Set UOMCancelled Qty L4	  */
	public void setUOMCancelledQtyL4 (BigDecimal UOMCancelledQtyL4);

	/** Get UOMCancelled Qty L4	  */
	public BigDecimal getUOMCancelledQtyL4();

    /** Column name UOMConversionL1_ID */
    public static final String COLUMNNAME_UOMConversionL1_ID = "UOMConversionL1_ID";

	/** Set UOM Conversion L1.
	  * The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public void setUOMConversionL1_ID (int UOMConversionL1_ID);

	/** Get UOM Conversion L1.
	  * The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public int getUOMConversionL1_ID();

	public org.compiere.model.I_C_UOM getUOMConversionL1() throws RuntimeException;

    /** Column name UOMConversionL2_ID */
    public static final String COLUMNNAME_UOMConversionL2_ID = "UOMConversionL2_ID";

	/** Set UOM Conversion L2.
	  * The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public void setUOMConversionL2_ID (int UOMConversionL2_ID);

	/** Get UOM Conversion L2.
	  * The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public int getUOMConversionL2_ID();

	public org.compiere.model.I_C_UOM getUOMConversionL2() throws RuntimeException;

    /** Column name UOMConversionL3_ID */
    public static final String COLUMNNAME_UOMConversionL3_ID = "UOMConversionL3_ID";

	/** Set UOM Conversion L3.
	  * The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public void setUOMConversionL3_ID (int UOMConversionL3_ID);

	/** Get UOM Conversion L3.
	  * The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public int getUOMConversionL3_ID();

	public org.compiere.model.I_C_UOM getUOMConversionL3() throws RuntimeException;

    /** Column name UOMConversionL4_ID */
    public static final String COLUMNNAME_UOMConversionL4_ID = "UOMConversionL4_ID";

	/** Set UOM Conversion L4.
	  * The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public void setUOMConversionL4_ID (int UOMConversionL4_ID);

	/** Get UOM Conversion L4.
	  * The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public int getUOMConversionL4_ID();

	public org.compiere.model.I_C_UOM getUOMConversionL4() throws RuntimeException;

    /** Column name UOMMovementQtyL1 */
    public static final String COLUMNNAME_UOMMovementQtyL1 = "UOMMovementQtyL1";

	/** Set Movement Qty L1	  */
	public void setUOMMovementQtyL1 (BigDecimal UOMMovementQtyL1);

	/** Get Movement Qty L1	  */
	public BigDecimal getUOMMovementQtyL1();

    /** Column name UOMMovementQtyL2 */
    public static final String COLUMNNAME_UOMMovementQtyL2 = "UOMMovementQtyL2";

	/** Set Movement Qty L2	  */
	public void setUOMMovementQtyL2 (BigDecimal UOMMovementQtyL2);

	/** Get Movement Qty L2	  */
	public BigDecimal getUOMMovementQtyL2();

    /** Column name UOMMovementQtyL3 */
    public static final String COLUMNNAME_UOMMovementQtyL3 = "UOMMovementQtyL3";

	/** Set Movement Qty L3	  */
	public void setUOMMovementQtyL3 (BigDecimal UOMMovementQtyL3);

	/** Get Movement Qty L3	  */
	public BigDecimal getUOMMovementQtyL3();

    /** Column name UOMMovementQtyL4 */
    public static final String COLUMNNAME_UOMMovementQtyL4 = "UOMMovementQtyL4";

	/** Set Movement Qty L4	  */
	public void setUOMMovementQtyL4 (BigDecimal UOMMovementQtyL4);

	/** Get Movement Qty L4	  */
	public BigDecimal getUOMMovementQtyL4();

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
