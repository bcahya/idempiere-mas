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

/** Generated Interface for UNS_Cvs_RptProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Cvs_RptProduct 
{

    /** TableName=UNS_Cvs_RptProduct */
    public static final String Table_Name = "UNS_Cvs_RptProduct";

    /** AD_Table_ID=1000228 */
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

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

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

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Unit Price.
	  * Actual Price
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Unit Price.
	  * Actual Price
	  */
	public BigDecimal getPriceActual();

    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/** Set Limit Price.
	  * Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit);

	/** Get Limit Price.
	  * Lowest price for a product
	  */
	public BigDecimal getPriceLimit();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

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
	
	/** Column name Processed */
    public static final String COLUMNNAME_isManual = "isManual";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setisManual (boolean isManual);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isManual();

    /** Column name QtyAvailable */
    public static final String COLUMNNAME_QtyAvailable = "QtyAvailable";

	/** Set Available Quantity.
	  * Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable);

	/** Get Available Quantity.
	  * Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable();

    /** Column name QtySold */
    public static final String COLUMNNAME_QtySold = "QtySold";

	/** Set Quantity Sold	  */
	public void setQtySold (BigDecimal QtySold);

	/** Get Quantity Sold	  */
	public BigDecimal getQtySold();

    /** Column name UNS_Cvs_RptCustomer_ID */
    public static final String COLUMNNAME_UNS_Cvs_RptCustomer_ID = "UNS_Cvs_RptCustomer_ID";

	/** Set Customer	  */
	public void setUNS_Cvs_RptCustomer_ID (int UNS_Cvs_RptCustomer_ID);

	/** Get Customer	  */
	public int getUNS_Cvs_RptCustomer_ID();

	public com.unicore.model.I_UNS_Cvs_RptCustomer getUNS_Cvs_RptCustomer() throws RuntimeException;

    /** Column name UNS_Cvs_RptProduct_ID */
    public static final String COLUMNNAME_UNS_Cvs_RptProduct_ID = "UNS_Cvs_RptProduct_ID";

	/** Set Canvas Product Report	  */
	public void setUNS_Cvs_RptProduct_ID (int UNS_Cvs_RptProduct_ID);

	/** Get Canvas Product Report	  */
	public int getUNS_Cvs_RptProduct_ID();

    /** Column name UNS_Cvs_RptProduct_UU */
    public static final String COLUMNNAME_UNS_Cvs_RptProduct_UU = "UNS_Cvs_RptProduct_UU";

	/** Set UNS_Cvs_RptProduct_UU	  */
	public void setUNS_Cvs_RptProduct_UU (String UNS_Cvs_RptProduct_UU);

	/** Get UNS_Cvs_RptProduct_UU	  */
	public String getUNS_Cvs_RptProduct_UU();

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

    /** Column name UOMQtyAvailableL1 */
    public static final String COLUMNNAME_UOMQtyAvailableL1 = "UOMQtyAvailableL1";

	/** Set Quantity Available L1	  */
	public void setUOMQtyAvailableL1 (BigDecimal UOMQtyAvailableL1);

	/** Get Quantity Available L1	  */
	public BigDecimal getUOMQtyAvailableL1();

    /** Column name UOMQtyAvailableL2 */
    public static final String COLUMNNAME_UOMQtyAvailableL2 = "UOMQtyAvailableL2";

	/** Set Quantity Available L2	  */
	public void setUOMQtyAvailableL2 (BigDecimal UOMQtyAvailableL2);

	/** Get Quantity Available L2	  */
	public BigDecimal getUOMQtyAvailableL2();

    /** Column name UOMQtyAvailableL3 */
    public static final String COLUMNNAME_UOMQtyAvailableL3 = "UOMQtyAvailableL3";

	/** Set Quantity Available L3	  */
	public void setUOMQtyAvailableL3 (BigDecimal UOMQtyAvailableL3);

	/** Get Quantity Available L3	  */
	public BigDecimal getUOMQtyAvailableL3();

    /** Column name UOMQtyAvailableL4 */
    public static final String COLUMNNAME_UOMQtyAvailableL4 = "UOMQtyAvailableL4";

	/** Set Quantity Available L4	  */
	public void setUOMQtyAvailableL4 (BigDecimal UOMQtyAvailableL4);

	/** Get Quantity Available L4	  */
	public BigDecimal getUOMQtyAvailableL4();

    /** Column name UOMQtySoldL1 */
    public static final String COLUMNNAME_UOMQtySoldL1 = "UOMQtySoldL1";

	/** Set Sold Quantity L1	  */
	public void setUOMQtySoldL1 (BigDecimal UOMQtySoldL1);

	/** Get Sold Quantity L1	  */
	public BigDecimal getUOMQtySoldL1();

    /** Column name UOMQtySoldL2 */
    public static final String COLUMNNAME_UOMQtySoldL2 = "UOMQtySoldL2";

	/** Set Sold Quantity L2	  */
	public void setUOMQtySoldL2 (BigDecimal UOMQtySoldL2);

	/** Get Sold Quantity L2	  */
	public BigDecimal getUOMQtySoldL2();

    /** Column name UOMQtySoldL3 */
    public static final String COLUMNNAME_UOMQtySoldL3 = "UOMQtySoldL3";

	/** Set Sold Quantity L3	  */
	public void setUOMQtySoldL3 (BigDecimal UOMQtySoldL3);

	/** Get Sold Quantity L3	  */
	public BigDecimal getUOMQtySoldL3();

    /** Column name UOMQtySoldL4 */
    public static final String COLUMNNAME_UOMQtySoldL4 = "UOMQtySoldL4";

	/** Set Sold Quantity L4	  */
	public void setUOMQtySoldL4 (BigDecimal UOMQtySoldL4);

	/** Get Sold Quantity L4	  */
	public BigDecimal getUOMQtySoldL4();

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
