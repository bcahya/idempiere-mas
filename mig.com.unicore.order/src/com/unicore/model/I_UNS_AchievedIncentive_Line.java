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

/** Generated Interface for UNS_AchievedIncentive_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_AchievedIncentive_Line 
{

    /** TableName=UNS_AchievedIncentive_Line */
    public static final String Table_Name = "UNS_AchievedIncentive_Line";

    /** AD_Table_ID=1000151 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name AmountBase */
    public static final String COLUMNNAME_AmountBase = "AmountBase";

	/** Set Amount Base	  */
	public void setAmountBase (BigDecimal AmountBase);

	/** Get Amount Base	  */
	public BigDecimal getAmountBase();

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

    /** Column name C_PaymentAllocate_ID */
    public static final String COLUMNNAME_C_PaymentAllocate_ID = "C_PaymentAllocate_ID";

	/** Set Allocate Payment.
	  * Allocate Payment to Invoices
	  */
	public void setC_PaymentAllocate_ID (int C_PaymentAllocate_ID);

	/** Get Allocate Payment.
	  * Allocate Payment to Invoices
	  */
	public int getC_PaymentAllocate_ID();

	public org.compiere.model.I_C_PaymentAllocate getC_PaymentAllocate() throws RuntimeException;

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

    /** Column name Reference_ID */
    public static final String COLUMNNAME_Reference_ID = "Reference_ID";

	/** Set Refrerence Record	  */
	public void setReference_ID (int Reference_ID);

	/** Get Refrerence Record	  */
	public int getReference_ID();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name SchemaType */
    public static final String COLUMNNAME_SchemaType = "SchemaType";

	/** Set Schema Type	  */
	public void setSchemaType (String SchemaType);

	/** Get Schema Type	  */
	public String getSchemaType();

    /** Column name SourceType */
    public static final String COLUMNNAME_SourceType = "SourceType";

	/** Set Source Type	  */
	public void setSourceType (String SourceType);

	/** Get Source Type	  */
	public String getSourceType();

    /** Column name UNS_AchievedIncentive_ID */
    public static final String COLUMNNAME_UNS_AchievedIncentive_ID = "UNS_AchievedIncentive_ID";

	/** Set Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID);

	/** Get Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID();

	public com.unicore.model.I_UNS_AchievedIncentive getUNS_AchievedIncentive() throws RuntimeException;

    /** Column name UNS_AchievedIncentive_Line_ID */
    public static final String COLUMNNAME_UNS_AchievedIncentive_Line_ID = "UNS_AchievedIncentive_Line_ID";

	/** Set Achieved Incentive Line	  */
	public void setUNS_AchievedIncentive_Line_ID (int UNS_AchievedIncentive_Line_ID);

	/** Get Achieved Incentive Line	  */
	public int getUNS_AchievedIncentive_Line_ID();

    /** Column name UNS_AchievedIncentive_Line_UU */
    public static final String COLUMNNAME_UNS_AchievedIncentive_Line_UU = "UNS_AchievedIncentive_Line_UU";

	/** Set UNS_AchievedIncentive_Line_UU	  */
	public void setUNS_AchievedIncentive_Line_UU (String UNS_AchievedIncentive_Line_UU);

	/** Get UNS_AchievedIncentive_Line_UU	  */
	public String getUNS_AchievedIncentive_Line_UU();

    /** Column name UNS_AcvIncentiveByPeriod_ID */
    public static final String COLUMNNAME_UNS_AcvIncentiveByPeriod_ID = "UNS_AcvIncentiveByPeriod_ID";

	/** Set Achieved Incentive By Period	  */
	public void setUNS_AcvIncentiveByPeriod_ID (int UNS_AcvIncentiveByPeriod_ID);

	/** Get Achieved Incentive By Period	  */
	public int getUNS_AcvIncentiveByPeriod_ID();

    /** Column name UNS_Incentive_ID */
    public static final String COLUMNNAME_UNS_Incentive_ID = "UNS_Incentive_ID";

	/** Set Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID);

	/** Get Incentive	  */
	public int getUNS_Incentive_ID();

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException;

    /** Column name UNS_Shipping_ID */
    public static final String COLUMNNAME_UNS_Shipping_ID = "UNS_Shipping_ID";

	/** Set Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID);

	/** Get Shipping Document	  */
	public int getUNS_Shipping_ID();

	public com.unicore.model.I_UNS_Shipping getUNS_Shipping() throws RuntimeException;

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
