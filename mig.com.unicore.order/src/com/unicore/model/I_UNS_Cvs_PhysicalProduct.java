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

/** Generated Interface for UNS_Cvs_PhysicalProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Cvs_PhysicalProduct 
{

    /** TableName=UNS_Cvs_PhysicalProduct */
    public static final String Table_Name = "UNS_Cvs_PhysicalProduct";

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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name QtyBook */
    public static final String COLUMNNAME_QtyBook = "QtyBook";

	/** Set Quantity book.
	  * Book Quantity
	  */
	public void setQtyBook (BigDecimal QtyBook);

	/** Get Quantity book.
	  * Book Quantity
	  */
	public BigDecimal getQtyBook();

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public BigDecimal getQtyCount();

    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/** Set Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID);

	/** Get Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID();

	public org.compiere.model.I_M_InventoryLine getReversalLine() throws RuntimeException;

    /** Column name UNS_Cvs_Physical_ID */
    public static final String COLUMNNAME_UNS_Cvs_Physical_ID = "UNS_Cvs_Physical_ID";

	/** Set Canvas Physical Inventory	  */
	public void setUNS_Cvs_Physical_ID (int UNS_Cvs_Physical_ID);

	/** Get Canvas Physical Inventory	  */
	public int getUNS_Cvs_Physical_ID();

	public com.unicore.model.I_UNS_Cvs_Physical getUNS_Cvs_Physical() throws RuntimeException;

    /** Column name UNS_Cvs_PhysicalProduct_ID */
    public static final String COLUMNNAME_UNS_Cvs_PhysicalProduct_ID = "UNS_Cvs_PhysicalProduct_ID";

	/** Set Canvas Physical Product	  */
	public void setUNS_Cvs_PhysicalProduct_ID (int UNS_Cvs_PhysicalProduct_ID);

	/** Get Canvas Physical Product	  */
	public int getUNS_Cvs_PhysicalProduct_ID();

    /** Column name UNS_Cvs_PhysicalProduct_UU */
    public static final String COLUMNNAME_UNS_Cvs_PhysicalProduct_UU = "UNS_Cvs_PhysicalProduct_UU";

	/** Set UNS_Cvs_PhysicalProduct_UU	  */
	public void setUNS_Cvs_PhysicalProduct_UU (String UNS_Cvs_PhysicalProduct_UU);

	/** Get UNS_Cvs_PhysicalProduct_UU	  */
	public String getUNS_Cvs_PhysicalProduct_UU();

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

    /** Column name UOMQtyBookL1 */
    public static final String COLUMNNAME_UOMQtyBookL1 = "UOMQtyBookL1";

	/** Set Qty Book L1	  */
	public void setUOMQtyBookL1 (BigDecimal UOMQtyBookL1);

	/** Get Qty Book L1	  */
	public BigDecimal getUOMQtyBookL1();

    /** Column name UOMQtyBookL2 */
    public static final String COLUMNNAME_UOMQtyBookL2 = "UOMQtyBookL2";

	/** Set Qty Book L2	  */
	public void setUOMQtyBookL2 (BigDecimal UOMQtyBookL2);

	/** Get Qty Book L2	  */
	public BigDecimal getUOMQtyBookL2();

    /** Column name UOMQtyBookL3 */
    public static final String COLUMNNAME_UOMQtyBookL3 = "UOMQtyBookL3";

	/** Set Qty Book L3	  */
	public void setUOMQtyBookL3 (BigDecimal UOMQtyBookL3);

	/** Get Qty Book L3	  */
	public BigDecimal getUOMQtyBookL3();

    /** Column name UOMQtyBookL4 */
    public static final String COLUMNNAME_UOMQtyBookL4 = "UOMQtyBookL4";

	/** Set Qty Book L4	  */
	public void setUOMQtyBookL4 (BigDecimal UOMQtyBookL4);

	/** Get Qty Book L4	  */
	public BigDecimal getUOMQtyBookL4();

    /** Column name UOMQtyCountL1 */
    public static final String COLUMNNAME_UOMQtyCountL1 = "UOMQtyCountL1";

	/** Set Qty Count L1	  */
	public void setUOMQtyCountL1 (BigDecimal UOMQtyCountL1);

	/** Get Qty Count L1	  */
	public BigDecimal getUOMQtyCountL1();

    /** Column name UOMQtyCountL2 */
    public static final String COLUMNNAME_UOMQtyCountL2 = "UOMQtyCountL2";

	/** Set Qty Count L2	  */
	public void setUOMQtyCountL2 (BigDecimal UOMQtyCountL2);

	/** Get Qty Count L2	  */
	public BigDecimal getUOMQtyCountL2();

    /** Column name UOMQtyCountL3 */
    public static final String COLUMNNAME_UOMQtyCountL3 = "UOMQtyCountL3";

	/** Set Qty Count L3	  */
	public void setUOMQtyCountL3 (BigDecimal UOMQtyCountL3);

	/** Get Qty Count L3	  */
	public BigDecimal getUOMQtyCountL3();

    /** Column name UOMQtyCountL4 */
    public static final String COLUMNNAME_UOMQtyCountL4 = "UOMQtyCountL4";

	/** Set Qty Count L4	  */
	public void setUOMQtyCountL4 (BigDecimal UOMQtyCountL4);

	/** Get Qty Count L4	  */
	public BigDecimal getUOMQtyCountL4();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
