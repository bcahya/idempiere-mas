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

import com.uns.model.I_UNS_BillingLine;
import com.uns.model.I_UNS_CustomerBG;

/** Generated Interface for UNS_BillingLine_Result
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_BillingLine_Result 
{

    /** TableName=UNS_BillingLine_Result */
    public static final String Table_Name = "UNS_BillingLine_Result";

    /** AD_Table_ID=1000195 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AcceptedBy */
    public static final String COLUMNNAME_AcceptedBy = "AcceptedBy";

	/** Set Accepted By	  */
	public void setAcceptedBy (String AcceptedBy);

	/** Get Accepted By	  */
	public String getAcceptedBy();

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

    /** Column name AmountTrf */
    public static final String COLUMNNAME_AmountTrf = "AmountTrf";

	/** Set Amount Transferred	  */
	public void setAmountTrf (BigDecimal AmountTrf);

	/** Get Amount Transferred	  */
	public BigDecimal getAmountTrf();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID();

	public I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

    /** Column name CreateCustomerBG */
    public static final String COLUMNNAME_CreateCustomerBG = "CreateCustomerBG";

	/** Set Create New Customer Giro	  */
	public void setCreateCustomerBG (String CreateCustomerBG);

	/** Get Create New Customer Giro	  */
	public String getCreateCustomerBG();

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

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

    /** Column name IsHandoverLetter */
    public static final String COLUMNNAME_IsHandoverLetter = "IsHandoverLetter";

	/** Set Handover Letter	  */
	public void setIsHandoverLetter (boolean IsHandoverLetter);

	/** Get Handover Letter	  */
	public boolean isHandoverLetter();

    /** Column name IsHasReceipt */
    public static final String COLUMNNAME_IsHasReceipt = "IsHasReceipt";

	/** Set Has Receipt	  */
	public void setIsHasReceipt (boolean IsHasReceipt);

	/** Get Has Receipt	  */
	public boolean isHasReceipt();

    /** Column name IsStandartTaxInvoice */
    public static final String COLUMNNAME_IsStandartTaxInvoice = "IsStandartTaxInvoice";

	/** Set Standart Tax Invoice	  */
	public void setIsStandartTaxInvoice (boolean IsStandartTaxInvoice);

	/** Get Standart Tax Invoice	  */
	public boolean isStandartTaxInvoice();

    /** Column name IsWhiteSheet */
    public static final String COLUMNNAME_IsWhiteSheet = "IsWhiteSheet";

	/** Set White Sheet of Invoice	  */
	public void setIsWhiteSheet (boolean IsWhiteSheet);

	/** Get White Sheet of Invoice	  */
	public boolean isWhiteSheet();

    /** Column name NetAmtToInvoice */
    public static final String COLUMNNAME_NetAmtToInvoice = "NetAmtToInvoice";

	/** Set Invoice net Amount.
	  * Net amount of this Invoice
	  */
	public void setNetAmtToInvoice (BigDecimal NetAmtToInvoice);

	/** Get Invoice net Amount.
	  * Net amount of this Invoice
	  */
	public BigDecimal getNetAmtToInvoice();

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount	  */
	public BigDecimal getPaidAmt();

    /** Column name PaidAmtGiro */
    public static final String COLUMNNAME_PaidAmtGiro = "PaidAmtGiro";

	/** Set Paid Amt By Giro.
	  * Paid Amount by Giro
	  */
	public void setPaidAmtGiro (BigDecimal PaidAmtGiro);

	/** Get Paid Amt By Giro.
	  * Paid Amount by Giro
	  */
	public BigDecimal getPaidAmtGiro();

    /** Column name PaymentStatus */
    public static final String COLUMNNAME_PaymentStatus = "PaymentStatus";

	/** Set Payment Status.
	  * Status of payment
	  */
	public void setPaymentStatus (String PaymentStatus);

	/** Get Payment Status.
	  * Status of payment
	  */
	public String getPaymentStatus();

    /** Column name ReceiptAmt */
    public static final String COLUMNNAME_ReceiptAmt = "ReceiptAmt";

	/** Set Receipt Amount	  */
	public void setReceiptAmt (BigDecimal ReceiptAmt);

	/** Get Receipt Amount	  */
	public BigDecimal getReceiptAmt();

    /** Column name ReceiptAmtGiro */
    public static final String COLUMNNAME_ReceiptAmtGiro = "ReceiptAmtGiro";

	/** Set Receipt Amt By Giro.
	  * Receipt Amount By Giro
	  */
	public void setReceiptAmtGiro (BigDecimal ReceiptAmtGiro);

	/** Get Receipt Amt By Giro.
	  * Receipt Amount By Giro
	  */
	public BigDecimal getReceiptAmtGiro();

    /** Column name ReceiptDate */
    public static final String COLUMNNAME_ReceiptDate = "ReceiptDate";

	/** Set Receipt Date.
	  * Receipt Date
	  */
	public void setReceiptDate (Timestamp ReceiptDate);

	/** Get Receipt Date.
	  * Receipt Date
	  */
	public Timestamp getReceiptDate();

    /** Column name UNS_Billing_Result_ID */
    public static final String COLUMNNAME_UNS_Billing_Result_ID = "UNS_Billing_Result_ID";

	/** Set Customer Billing	  */
	public void setUNS_Billing_Result_ID (int UNS_Billing_Result_ID);

	/** Get Customer Billing	  */
	public int getUNS_Billing_Result_ID();

	public com.unicore.model.I_UNS_Billing_Result getUNS_Billing_Result() throws RuntimeException;

    /** Column name UNS_BillingLine_ID */
    public static final String COLUMNNAME_UNS_BillingLine_ID = "UNS_BillingLine_ID";

	/** Set Billing Line	  */
	public void setUNS_BillingLine_ID (int UNS_BillingLine_ID);

	/** Get Billing Line	  */
	public int getUNS_BillingLine_ID();

	public I_UNS_BillingLine getUNS_BillingLine() throws RuntimeException;

    /** Column name UNS_BillingLine_Result_ID */
    public static final String COLUMNNAME_UNS_BillingLine_Result_ID = "UNS_BillingLine_Result_ID";

	/** Set Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID);

	/** Get Invoice Result	  */
	public int getUNS_BillingLine_Result_ID();

    /** Column name UNS_BillingLine_Result_UU */
    public static final String COLUMNNAME_UNS_BillingLine_Result_UU = "UNS_BillingLine_Result_UU";

	/** Set UNS_BillingLine_Result_UU	  */
	public void setUNS_BillingLine_Result_UU (String UNS_BillingLine_Result_UU);

	/** Get UNS_BillingLine_Result_UU	  */
	public String getUNS_BillingLine_Result_UU();

    /** Column name UNS_CustomerBG_ID */
    public static final String COLUMNNAME_UNS_CustomerBG_ID = "UNS_CustomerBG_ID";

	/** Set Customer Billed Giro	  */
	public void setUNS_CustomerBG_ID (int UNS_CustomerBG_ID);

	/** Get Customer Billed Giro	  */
	public int getUNS_CustomerBG_ID();

	public I_UNS_CustomerBG getUNS_CustomerBG() throws RuntimeException;

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

    /** Column name WriteOffAmt */
    public static final String COLUMNNAME_WriteOffAmt = "WriteOffAmt";

	/** Set Write-off Amount.
	  * Amount to write-off
	  */
	public void setWriteOffAmt (BigDecimal WriteOffAmt);

	/** Get Write-off Amount.
	  * Amount to write-off
	  */
	public BigDecimal getWriteOffAmt();
}
