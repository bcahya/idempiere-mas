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

/** Generated Interface for UNS_VATInvLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VATInvLine 
{

    /** TableName=UNS_VATInvLine */
    public static final String Table_Name = "UNS_VATInvLine";

    /** AD_Table_ID=1000340 */
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

    /** Column name BeforeTaxAmt */
    public static final String COLUMNNAME_BeforeTaxAmt = "BeforeTaxAmt";

	/** Set Before Tax Amount	  */
	public void setBeforeTaxAmt (BigDecimal BeforeTaxAmt);

	/** Get Before Tax Amount	  */
	public BigDecimal getBeforeTaxAmt();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

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

    /** Column name DifferenceTaxAmt */
    public static final String COLUMNNAME_DifferenceTaxAmt = "DifferenceTaxAmt";

	/** Set Difference Tax Amt	  */
	public void setDifferenceTaxAmt (BigDecimal DifferenceTaxAmt);

	/** Get Difference Tax Amt	  */
	public BigDecimal getDifferenceTaxAmt();

    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/** Set Discount Amount.
	  * Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt);

	/** Get Discount Amount.
	  * Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt();

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

    /** Column name RevisionAmt */
    public static final String COLUMNNAME_RevisionAmt = "RevisionAmt";

	/** Set Revision Amount	  */
	public void setRevisionAmt (BigDecimal RevisionAmt);

	/** Get Revision Amount	  */
	public BigDecimal getRevisionAmt();

    /** Column name RevisionBeforeTaxAmt */
    public static final String COLUMNNAME_RevisionBeforeTaxAmt = "RevisionBeforeTaxAmt";

	/** Set Revision Before Tax Amt	  */
	public void setRevisionBeforeTaxAmt (BigDecimal RevisionBeforeTaxAmt);

	/** Get Revision Before Tax Amt	  */
	public BigDecimal getRevisionBeforeTaxAmt();

    /** Column name RevisionDiscAmt */
    public static final String COLUMNNAME_RevisionDiscAmt = "RevisionDiscAmt";

	/** Set Revision Discount Amount	  */
	public void setRevisionDiscAmt (BigDecimal RevisionDiscAmt);

	/** Get Revision Discount Amount	  */
	public BigDecimal getRevisionDiscAmt();

    /** Column name RevisionPriceList */
    public static final String COLUMNNAME_RevisionPriceList = "RevisionPriceList";

	/** Set Revision Price List	  */
	public void setRevisionPriceList (BigDecimal RevisionPriceList);

	/** Get Revision Price List	  */
	public BigDecimal getRevisionPriceList();

    /** Column name RevisionTaxAmt */
    public static final String COLUMNNAME_RevisionTaxAmt = "RevisionTaxAmt";

	/** Set Revision Tax Amount	  */
	public void setRevisionTaxAmt (BigDecimal RevisionTaxAmt);

	/** Get Revision Tax Amount	  */
	public BigDecimal getRevisionTaxAmt();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

    /** Column name UNS_VATInvLine_ID */
    public static final String COLUMNNAME_UNS_VATInvLine_ID = "UNS_VATInvLine_ID";

	/** Set VAT Invoice Lines	  */
	public void setUNS_VATInvLine_ID (int UNS_VATInvLine_ID);

	/** Get VAT Invoice Lines	  */
	public int getUNS_VATInvLine_ID();

    /** Column name UNS_VATInvLine_UU */
    public static final String COLUMNNAME_UNS_VATInvLine_UU = "UNS_VATInvLine_UU";

	/** Set UNS_VATInvLine_UU	  */
	public void setUNS_VATInvLine_UU (String UNS_VATInvLine_UU);

	/** Get UNS_VATInvLine_UU	  */
	public String getUNS_VATInvLine_UU();

    /** Column name UNS_VATLine_ID */
    public static final String COLUMNNAME_UNS_VATLine_ID = "UNS_VATLine_ID";

	/** Set VAT Lines	  */
	public void setUNS_VATLine_ID (int UNS_VATLine_ID);

	/** Get VAT Lines	  */
	public int getUNS_VATLine_ID();

	public I_UNS_VATLine getUNS_VATLine() throws RuntimeException;

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
