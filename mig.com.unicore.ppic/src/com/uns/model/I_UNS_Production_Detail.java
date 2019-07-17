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

/** Generated Interface for UNS_Production_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Production_Detail 
{

    /** TableName=UNS_Production_Detail */
    public static final String Table_Name = "UNS_Production_Detail";

    /** AD_Table_ID=1000059 */
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

    /** Column name BatchDocumentNo */
    public static final String COLUMNNAME_BatchDocumentNo = "BatchDocumentNo";

	/** Set Batch Document No.
	  * Document Number of the Batch
	  */
	public void setBatchDocumentNo (String BatchDocumentNo);

	/** Get Batch Document No.
	  * Document Number of the Batch
	  */
	public String getBatchDocumentNo();

    /** Column name BOMType */
    public static final String COLUMNNAME_BOMType = "BOMType";

	/** Set BOM Type.
	  * Type of BOM
	  */
	public void setBOMType (String BOMType);

	/** Get BOM Type.
	  * Type of BOM
	  */
	public String getBOMType();

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

    /** Column name EndingStock */
    public static final String COLUMNNAME_EndingStock = "EndingStock";

	/** Set Ending Stock	  */
	public void setEndingStock (BigDecimal EndingStock);

	/** Get Ending Stock	  */
	public BigDecimal getEndingStock();

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

    /** Column name IsEndProduct */
    public static final String COLUMNNAME_IsEndProduct = "IsEndProduct";

	/** Set End Product.
	  * End Product of production
	  */
	public void setIsEndProduct (boolean IsEndProduct);

	/** Get End Product.
	  * End Product of production
	  */
	public boolean isEndProduct();

    /** Column name IsPrimary */
    public static final String COLUMNNAME_IsPrimary = "IsPrimary";

	/** Set Primary.
	  * Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary);

	/** Get Primary.
	  * Indicates if this is the primary budget
	  */
	public boolean isPrimary();

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

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_Product_BOM_ID */
    public static final String COLUMNNAME_M_Product_BOM_ID = "M_Product_BOM_ID";

	/** Set BOM Line	  */
	public void setM_Product_BOM_ID (int M_Product_BOM_ID);

	/** Get BOM Line	  */
	public int getM_Product_BOM_ID();

	public org.compiere.model.I_M_Product_BOM getM_Product_BOM() throws RuntimeException;

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

    /** Column name PlannedQty */
    public static final String COLUMNNAME_PlannedQty = "PlannedQty";

	/** Set Planned Quantity.
	  * Planned quantity for this project
	  */
	public void setPlannedQty (BigDecimal PlannedQty);

	/** Get Planned Quantity.
	  * Planned quantity for this project
	  */
	public BigDecimal getPlannedQty();

    /** Column name PrimaryOutput_ID */
    public static final String COLUMNNAME_PrimaryOutput_ID = "PrimaryOutput_ID";

	/** Set Primary Output.
	  * The related primary output of this non-primary output.
	  */
	public void setPrimaryOutput_ID (int PrimaryOutput_ID);

	/** Get Primary Output.
	  * The related primary output of this non-primary output.
	  */
	public int getPrimaryOutput_ID();

	public com.uns.model.I_UNS_Production_Detail getPrimaryOutput() throws RuntimeException;

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

    /** Column name QtyUsed */
    public static final String COLUMNNAME_QtyUsed = "QtyUsed";

	/** Set Quantity Used	  */
	public void setQtyUsed (BigDecimal QtyUsed);

	/** Get Quantity Used	  */
	public BigDecimal getQtyUsed();

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

	public com.uns.model.I_UNS_Production_Detail getReversalLine() throws RuntimeException;

    /** Column name UNS_PD_Reff_ID */
    public static final String COLUMNNAME_UNS_PD_Reff_ID = "UNS_PD_Reff_ID";

	/** Set Production DetailID2.
	  * Tricky solution to enable more than 1 included tab in production detail.
	  */
	public void setUNS_PD_Reff_ID (int UNS_PD_Reff_ID);

	/** Get Production DetailID2.
	  * Tricky solution to enable more than 1 included tab in production detail.
	  */
	public int getUNS_PD_Reff_ID();

	public com.uns.model.I_UNS_Production_Detail getUNS_PD_Reff() throws RuntimeException;

    /** Column name UNS_Production_Detail_ID */
    public static final String COLUMNNAME_UNS_Production_Detail_ID = "UNS_Production_Detail_ID";

	/** Set Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID);

	/** Get Production Detail	  */
	public int getUNS_Production_Detail_ID();

    /** Column name UNS_Production_Detail_UU */
    public static final String COLUMNNAME_UNS_Production_Detail_UU = "UNS_Production_Detail_UU";

	/** Set UNS_Production_Detail_UU	  */
	public void setUNS_Production_Detail_UU (String UNS_Production_Detail_UU);

	/** Get UNS_Production_Detail_UU	  */
	public String getUNS_Production_Detail_UU();

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException;

    /** Column name UNS_Resource_InOut_ID */
    public static final String COLUMNNAME_UNS_Resource_InOut_ID = "UNS_Resource_InOut_ID";

	/** Set Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID);

	/** Get Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID();

	public com.uns.model.I_UNS_Resource_InOut getUNS_Resource_InOut() throws RuntimeException;

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
