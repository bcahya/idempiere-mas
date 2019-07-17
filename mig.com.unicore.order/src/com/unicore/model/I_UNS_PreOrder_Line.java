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

/** Generated Interface for UNS_PreOrder_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PreOrder_Line 
{

    /** TableName=UNS_PreOrder_Line */
    public static final String Table_Name = "UNS_PreOrder_Line";

    /** AD_Table_ID=1000345 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/** Set Line.
	  * Line No
	  */
	public void setLineNo (int LineNo);

	/** Get Line.
	  * Line No
	  */
	public int getLineNo();

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

    /** Column name UNS_PreOrder_ID */
    public static final String COLUMNNAME_UNS_PreOrder_ID = "UNS_PreOrder_ID";

	/** Set Pre Order	  */
	public void setUNS_PreOrder_ID (int UNS_PreOrder_ID);

	/** Get Pre Order	  */
	public int getUNS_PreOrder_ID();

	public I_UNS_PreOrder getUNS_PreOrder() throws RuntimeException;

    /** Column name UNS_PreOrder_Line_ID */
    public static final String COLUMNNAME_UNS_PreOrder_Line_ID = "UNS_PreOrder_Line_ID";

	/** Set Pre Order Line	  */
	public void setUNS_PreOrder_Line_ID (int UNS_PreOrder_Line_ID);

	/** Get Pre Order Line	  */
	public int getUNS_PreOrder_Line_ID();

    /** Column name UNS_PreOrder_Line_UU */
    public static final String COLUMNNAME_UNS_PreOrder_Line_UU = "UNS_PreOrder_Line_UU";

	/** Set UNS_PreOrder_Line_UU	  */
	public void setUNS_PreOrder_Line_UU (String UNS_PreOrder_Line_UU);

	/** Get UNS_PreOrder_Line_UU	  */
	public String getUNS_PreOrder_Line_UU();

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

    /** Column name UOMQtyOrderedL1 */
    public static final String COLUMNNAME_UOMQtyOrderedL1 = "UOMQtyOrderedL1";

	/** Set Qty Ordered L1	  */
	public void setUOMQtyOrderedL1 (BigDecimal UOMQtyOrderedL1);

	/** Get Qty Ordered L1	  */
	public BigDecimal getUOMQtyOrderedL1();

    /** Column name UOMQtyOrderedL2 */
    public static final String COLUMNNAME_UOMQtyOrderedL2 = "UOMQtyOrderedL2";

	/** Set Qty Ordered L2	  */
	public void setUOMQtyOrderedL2 (BigDecimal UOMQtyOrderedL2);

	/** Get Qty Ordered L2	  */
	public BigDecimal getUOMQtyOrderedL2();

    /** Column name UOMQtyOrderedL3 */
    public static final String COLUMNNAME_UOMQtyOrderedL3 = "UOMQtyOrderedL3";

	/** Set Qty Ordered L3	  */
	public void setUOMQtyOrderedL3 (BigDecimal UOMQtyOrderedL3);

	/** Get Qty Ordered L3	  */
	public BigDecimal getUOMQtyOrderedL3();

    /** Column name UOMQtyOrderedL4 */
    public static final String COLUMNNAME_UOMQtyOrderedL4 = "UOMQtyOrderedL4";

	/** Set Qty Ordered L4	  */
	public void setUOMQtyOrderedL4 (BigDecimal UOMQtyOrderedL4);

	/** Get Qty Ordered L4	  */
	public BigDecimal getUOMQtyOrderedL4();

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
