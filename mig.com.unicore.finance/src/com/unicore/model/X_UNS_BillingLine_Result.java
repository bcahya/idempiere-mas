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
/** Generated Model - DO NOT CHANGE */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import com.uns.model.I_UNS_BillingLine;
import com.uns.model.I_UNS_CustomerBG;

/** Generated Model for UNS_BillingLine_Result
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingLine_Result extends PO implements I_UNS_BillingLine_Result, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180309L;

    /** Standard Constructor */
    public X_UNS_BillingLine_Result (Properties ctx, int UNS_BillingLine_Result_ID, String trxName)
    {
      super (ctx, UNS_BillingLine_Result_ID, trxName);
      /** if (UNS_BillingLine_Result_ID == 0)
        {
			setAmountTrf (Env.ZERO);
// 0
			setC_Invoice_ID (0);
			setCreateCustomerBG (null);
// N
			setDifferenceAmt (Env.ZERO);
			setIsHandoverLetter (false);
// N
			setIsHasReceipt (false);
// N
			setIsStandartTaxInvoice (false);
// N
			setIsWhiteSheet (false);
// N
			setNetAmtToInvoice (Env.ZERO);
// 0
			setPaidAmt (Env.ZERO);
			setPaymentStatus (null);
			setReceiptAmt (Env.ZERO);
// 0
			setUNS_Billing_Result_ID (0);
			setUNS_BillingLine_ID (0);
			setUNS_BillingLine_Result_ID (0);
			setWriteOffAmt (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingLine_Result (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_BillingLine_Result[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accepted By.
		@param AcceptedBy Accepted By	  */
	public void setAcceptedBy (String AcceptedBy)
	{
		set_Value (COLUMNNAME_AcceptedBy, AcceptedBy);
	}

	/** Get Accepted By.
		@return Accepted By	  */
	public String getAcceptedBy () 
	{
		return (String)get_Value(COLUMNNAME_AcceptedBy);
	}

	/** Set Amount Transferred.
		@param AmountTrf Amount Transferred	  */
	public void setAmountTrf (BigDecimal AmountTrf)
	{
		set_Value (COLUMNNAME_AmountTrf, AmountTrf);
	}

	/** Get Amount Transferred.
		@return Amount Transferred	  */
	public BigDecimal getAmountTrf () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountTrf);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Cash / Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Cash / Bank Account.
		@return Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentAllocate getC_PaymentAllocate() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentAllocate)MTable.get(getCtx(), org.compiere.model.I_C_PaymentAllocate.Table_Name)
			.getPO(getC_PaymentAllocate_ID(), get_TrxName());	}

	/** Set Allocate Payment.
		@param C_PaymentAllocate_ID 
		Allocate Payment to Invoices
	  */
	public void setC_PaymentAllocate_ID (int C_PaymentAllocate_ID)
	{
		if (C_PaymentAllocate_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentAllocate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentAllocate_ID, Integer.valueOf(C_PaymentAllocate_ID));
	}

	/** Get Allocate Payment.
		@return Allocate Payment to Invoices
	  */
	public int getC_PaymentAllocate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentAllocate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create New Customer Giro.
		@param CreateCustomerBG Create New Customer Giro	  */
	public void setCreateCustomerBG (String CreateCustomerBG)
	{
		set_Value (COLUMNNAME_CreateCustomerBG, CreateCustomerBG);
	}

	/** Get Create New Customer Giro.
		@return Create New Customer Giro	  */
	public String getCreateCustomerBG () 
	{
		return (String)get_Value(COLUMNNAME_CreateCustomerBG);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Handover Letter.
		@param IsHandoverLetter Handover Letter	  */
	public void setIsHandoverLetter (boolean IsHandoverLetter)
	{
		set_Value (COLUMNNAME_IsHandoverLetter, Boolean.valueOf(IsHandoverLetter));
	}

	/** Get Handover Letter.
		@return Handover Letter	  */
	public boolean isHandoverLetter () 
	{
		Object oo = get_Value(COLUMNNAME_IsHandoverLetter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Has Receipt.
		@param IsHasReceipt Has Receipt	  */
	public void setIsHasReceipt (boolean IsHasReceipt)
	{
		set_Value (COLUMNNAME_IsHasReceipt, Boolean.valueOf(IsHasReceipt));
	}

	/** Get Has Receipt.
		@return Has Receipt	  */
	public boolean isHasReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsHasReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standart Tax Invoice.
		@param IsStandartTaxInvoice Standart Tax Invoice	  */
	public void setIsStandartTaxInvoice (boolean IsStandartTaxInvoice)
	{
		set_Value (COLUMNNAME_IsStandartTaxInvoice, Boolean.valueOf(IsStandartTaxInvoice));
	}

	/** Get Standart Tax Invoice.
		@return Standart Tax Invoice	  */
	public boolean isStandartTaxInvoice () 
	{
		Object oo = get_Value(COLUMNNAME_IsStandartTaxInvoice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set White Sheet of Invoice.
		@param IsWhiteSheet White Sheet of Invoice	  */
	public void setIsWhiteSheet (boolean IsWhiteSheet)
	{
		set_Value (COLUMNNAME_IsWhiteSheet, Boolean.valueOf(IsWhiteSheet));
	}

	/** Get White Sheet of Invoice.
		@return White Sheet of Invoice	  */
	public boolean isWhiteSheet () 
	{
		Object oo = get_Value(COLUMNNAME_IsWhiteSheet);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoice net Amount.
		@param NetAmtToInvoice 
		Net amount of this Invoice
	  */
	public void setNetAmtToInvoice (BigDecimal NetAmtToInvoice)
	{
		set_Value (COLUMNNAME_NetAmtToInvoice, NetAmtToInvoice);
	}

	/** Get Invoice net Amount.
		@return Net amount of this Invoice
	  */
	public BigDecimal getNetAmtToInvoice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NetAmtToInvoice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Paid Amount.
		@param PaidAmt Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	/** Get Paid Amount.
		@return Paid Amount	  */
	public BigDecimal getPaidAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getPaidAmt()));
    }

	/** Set Paid Amt By Giro.
		@param PaidAmtGiro 
		Paid Amount by Giro
	  */
	public void setPaidAmtGiro (BigDecimal PaidAmtGiro)
	{
		set_Value (COLUMNNAME_PaidAmtGiro, PaidAmtGiro);
	}

	/** Get Paid Amt By Giro.
		@return Paid Amount by Giro
	  */
	public BigDecimal getPaidAmtGiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidAmtGiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Handover Invoice = HI */
	public static final String PAYMENTSTATUS_HandoverInvoice = "HI";
	/** Not Paid = NP */
	public static final String PAYMENTSTATUS_NotPaid = "NP";
	/** On Process = OP */
	public static final String PAYMENTSTATUS_OnProcess = "OP";
	/** Paid = PD */
	public static final String PAYMENTSTATUS_Paid = "PD";
	/** Paid by Giro = PG */
	public static final String PAYMENTSTATUS_PaidByGiro = "PG";
	/** Partial Paid = PP */
	public static final String PAYMENTSTATUS_PartialPaid = "PP";
	/** Refusal = RS */
	public static final String PAYMENTSTATUS_Refusal = "RS";
	/** Paid By Cash And Giro = CG */
	public static final String PAYMENTSTATUS_PaidByCashAndGiro = "CG";
	/** Paid by Transfer = PT */
	public static final String PAYMENTSTATUS_PaidByTransfer = "PT";
	/** Set Payment Status.
		@param PaymentStatus 
		Status of payment
	  */
	public void setPaymentStatus (String PaymentStatus)
	{

		set_Value (COLUMNNAME_PaymentStatus, PaymentStatus);
	}

	/** Get Payment Status.
		@return Status of payment
	  */
	public String getPaymentStatus () 
	{
		return (String)get_Value(COLUMNNAME_PaymentStatus);
	}

	/** Set Receipt Amount.
		@param ReceiptAmt Receipt Amount	  */
	public void setReceiptAmt (BigDecimal ReceiptAmt)
	{
		set_Value (COLUMNNAME_ReceiptAmt, ReceiptAmt);
	}

	/** Get Receipt Amount.
		@return Receipt Amount	  */
	public BigDecimal getReceiptAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceiptAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Receipt Amt By Giro.
		@param ReceiptAmtGiro 
		Receipt Amount By Giro
	  */
	public void setReceiptAmtGiro (BigDecimal ReceiptAmtGiro)
	{
		set_Value (COLUMNNAME_ReceiptAmtGiro, ReceiptAmtGiro);
	}

	/** Get Receipt Amt By Giro.
		@return Receipt Amount By Giro
	  */
	public BigDecimal getReceiptAmtGiro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceiptAmtGiro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Receipt Date.
		@param ReceiptDate 
		Receipt Date
	  */
	public void setReceiptDate (Timestamp ReceiptDate)
	{
		set_Value (COLUMNNAME_ReceiptDate, ReceiptDate);
	}

	/** Get Receipt Date.
		@return Receipt Date
	  */
	public Timestamp getReceiptDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReceiptDate);
	}

	public com.unicore.model.I_UNS_Billing_Result getUNS_Billing_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Billing_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_Billing_Result.Table_Name)
			.getPO(getUNS_Billing_Result_ID(), get_TrxName());	}

	/** Set Customer Billing.
		@param UNS_Billing_Result_ID Customer Billing	  */
	public void setUNS_Billing_Result_ID (int UNS_Billing_Result_ID)
	{
		if (UNS_Billing_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_Result_ID, Integer.valueOf(UNS_Billing_Result_ID));
	}

	/** Get Customer Billing.
		@return Customer Billing	  */
	public int getUNS_Billing_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Billing_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_BillingLine getUNS_BillingLine() throws RuntimeException
    {
		return (I_UNS_BillingLine)MTable.get(getCtx(), I_UNS_BillingLine.Table_Name)
			.getPO(getUNS_BillingLine_ID(), get_TrxName());	}

	/** Set Billing Line.
		@param UNS_BillingLine_ID Billing Line	  */
	public void setUNS_BillingLine_ID (int UNS_BillingLine_ID)
	{
		if (UNS_BillingLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_ID, Integer.valueOf(UNS_BillingLine_ID));
	}

	/** Get Billing Line.
		@return Billing Line	  */
	public int getUNS_BillingLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice Result.
		@param UNS_BillingLine_Result_ID Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID)
	{
		if (UNS_BillingLine_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_Result_ID, Integer.valueOf(UNS_BillingLine_Result_ID));
	}

	/** Get Invoice Result.
		@return Invoice Result	  */
	public int getUNS_BillingLine_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingLine_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BillingLine_Result_UU.
		@param UNS_BillingLine_Result_UU UNS_BillingLine_Result_UU	  */
	public void setUNS_BillingLine_Result_UU (String UNS_BillingLine_Result_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingLine_Result_UU, UNS_BillingLine_Result_UU);
	}

	/** Get UNS_BillingLine_Result_UU.
		@return UNS_BillingLine_Result_UU	  */
	public String getUNS_BillingLine_Result_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingLine_Result_UU);
	}

	public I_UNS_CustomerBG getUNS_CustomerBG() throws RuntimeException
    {
		return (I_UNS_CustomerBG)MTable.get(getCtx(), I_UNS_CustomerBG.Table_Name)
			.getPO(getUNS_CustomerBG_ID(), get_TrxName());	}

	/** Set Customer Billed Giro.
		@param UNS_CustomerBG_ID Customer Billed Giro	  */
	public void setUNS_CustomerBG_ID (int UNS_CustomerBG_ID)
	{
		if (UNS_CustomerBG_ID < 1) 
			set_Value (COLUMNNAME_UNS_CustomerBG_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_CustomerBG_ID, Integer.valueOf(UNS_CustomerBG_ID));
	}

	/** Get Customer Billed Giro.
		@return Customer Billed Giro	  */
	public int getUNS_CustomerBG_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerBG_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Write-off Amount.
		@param WriteOffAmt 
		Amount to write-off
	  */
	public void setWriteOffAmt (BigDecimal WriteOffAmt)
	{
		set_ValueNoCheck (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	/** Get Write-off Amount.
		@return Amount to write-off
	  */
	public BigDecimal getWriteOffAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WriteOffAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}