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

/** Generated Interface for UNS_VATLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VATLine 
{

    /** TableName=UNS_VATLine */
    public static final String Table_Name = "UNS_VATLine";

    /** AD_Table_ID=1000342 */
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

    /** Column name AdditionalReferenceNo */
    public static final String COLUMNNAME_AdditionalReferenceNo = "AdditionalReferenceNo";

	/** Set Additional Reference No	  */
	public void setAdditionalReferenceNo (String AdditionalReferenceNo);

	/** Get Additional Reference No	  */
	public String getAdditionalReferenceNo();

    /** Column name BeforeTaxAmt */
    public static final String COLUMNNAME_BeforeTaxAmt = "BeforeTaxAmt";

	/** Set Before Tax Amount	  */
	public void setBeforeTaxAmt (BigDecimal BeforeTaxAmt);

	/** Get Before Tax Amount	  */
	public BigDecimal getBeforeTaxAmt();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name DifferenceTaxAmt */
    public static final String COLUMNNAME_DifferenceTaxAmt = "DifferenceTaxAmt";

	/** Set Difference Tax Amt	  */
	public void setDifferenceTaxAmt (BigDecimal DifferenceTaxAmt);

	/** Get Difference Tax Amt	  */
	public BigDecimal getDifferenceTaxAmt();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name IsCanvas */
    public static final String COLUMNNAME_IsCanvas = "IsCanvas";

	/** Set Canvas	  */
	public void setIsCanvas (boolean IsCanvas);

	/** Get Canvas	  */
	public boolean isCanvas();

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name isReplacement */
    public static final String COLUMNNAME_isReplacement = "isReplacement";

	/** Set isReplacement	  */
	public void setisReplacement (boolean isReplacement);

	/** Get isReplacement	  */
	public boolean isReplacement();

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

    /** Column name Reference_ID */
    public static final String COLUMNNAME_Reference_ID = "Reference_ID";

	/** Set Refrerence Record	  */
	public void setReference_ID (int Reference_ID);

	/** Get Refrerence Record	  */
	public int getReference_ID();

	public I_UNS_VATLine getReference() throws RuntimeException;

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

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

    /** Column name RevisionTaxAmt */
    public static final String COLUMNNAME_RevisionTaxAmt = "RevisionTaxAmt";

	/** Set Revision Tax Amount	  */
	public void setRevisionTaxAmt (BigDecimal RevisionTaxAmt);

	/** Get Revision Tax Amount	  */
	public BigDecimal getRevisionTaxAmt();

    /** Column name TaxAddress */
    public static final String COLUMNNAME_TaxAddress = "TaxAddress";

	/** Set Tax Address.
	  * The address of Business Partner registered on Tax Office
	  */
	public void setTaxAddress (String TaxAddress);

	/** Get Tax Address.
	  * The address of Business Partner registered on Tax Office
	  */
	public String getTaxAddress();

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

    /** Column name TaxInvoiceNo */
    public static final String COLUMNNAME_TaxInvoiceNo = "TaxInvoiceNo";

	/** Set Tax Invoice No	  */
	public void setTaxInvoiceNo (String TaxInvoiceNo);

	/** Get Tax Invoice No	  */
	public String getTaxInvoiceNo();

    /** Column name TaxName */
    public static final String COLUMNNAME_TaxName = "TaxName";

	/** Set Name for Tax.
	  * The name identification of Business Partner registered on Tax Office
	  */
	public void setTaxName (String TaxName);

	/** Get Name for Tax.
	  * The name identification of Business Partner registered on Tax Office
	  */
	public String getTaxName();

    /** Column name TaxSerialNo */
    public static final String COLUMNNAME_TaxSerialNo = "TaxSerialNo";

	/** Set Tax Serial No.
	  * The serial number of Business Partner Tax
	  */
	public void setTaxSerialNo (String TaxSerialNo);

	/** Get Tax Serial No.
	  * The serial number of Business Partner Tax
	  */
	public String getTaxSerialNo();

    /** Column name UNS_VAT_ID */
    public static final String COLUMNNAME_UNS_VAT_ID = "UNS_VAT_ID";

	/** Set UNS_VAT_ID	  */
	public void setUNS_VAT_ID (int UNS_VAT_ID);

	/** Get UNS_VAT_ID	  */
	public int getUNS_VAT_ID();

	public I_UNS_VAT getUNS_VAT() throws RuntimeException;

    /** Column name UNS_VATLine_ID */
    public static final String COLUMNNAME_UNS_VATLine_ID = "UNS_VATLine_ID";

	/** Set VAT Lines	  */
	public void setUNS_VATLine_ID (int UNS_VATLine_ID);

	/** Get VAT Lines	  */
	public int getUNS_VATLine_ID();

    /** Column name UNS_VATLine_UU */
    public static final String COLUMNNAME_UNS_VATLine_UU = "UNS_VATLine_UU";

	/** Set UNS_VATLine_UU	  */
	public void setUNS_VATLine_UU (String UNS_VATLine_UU);

	/** Get UNS_VATLine_UU	  */
	public String getUNS_VATLine_UU();

    /** Column name UpdateAmount */
    public static final String COLUMNNAME_UpdateAmount = "UpdateAmount";

	/** Set Update Amount	  */
	public void setUpdateAmount (String UpdateAmount);

	/** Get Update Amount	  */
	public String getUpdateAmount();

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
