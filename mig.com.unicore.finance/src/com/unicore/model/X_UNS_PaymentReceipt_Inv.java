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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_PaymentReceipt_Inv
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PaymentReceipt_Inv extends PO implements I_UNS_PaymentReceipt_Inv, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141224L;

    /** Standard Constructor */
    public X_UNS_PaymentReceipt_Inv (Properties ctx, int UNS_PaymentReceipt_Inv_ID, String trxName)
    {
      super (ctx, UNS_PaymentReceipt_Inv_ID, trxName);
      /** if (UNS_PaymentReceipt_Inv_ID == 0)
        {
			setDifferenceAmt (Env.ZERO);
			setNetAmtToInvoice (Env.ZERO);
// 0
			setPaidAmt (Env.ZERO);
			setPaymentStatus (null);
			setReceiptAmt (Env.ZERO);
// 0
			setUNS_BillingLine_Result_ID (0);
			setUNS_PaymentReceipt_BP_ID (0);
			setUNS_PaymentReceipt_Inv_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PaymentReceipt_Inv (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PaymentReceipt_Inv[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BankStatement getC_BankStatement() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankStatement)MTable.get(getCtx(), org.compiere.model.I_C_BankStatement.Table_Name)
			.getPO(getC_BankStatement_ID(), get_TrxName());	}

	/** Set Bank Statement.
		@param C_BankStatement_ID 
		Bank Statement of account
	  */
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	/** Get Bank Statement.
		@return Bank Statement of account
	  */
	public int getC_BankStatement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatement_ID);
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
		@param PaidAmt 
		Total Paid Amount of Document
	  */
	public void setPaidAmt (BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	/** Get Paid Amount.
		@return Total Paid Amount of Document
	  */
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

	/** Paid = PD */
	public static final String PAYMENTSTATUS_Paid = "PD";
	/** Partial Paid = PP */
	public static final String PAYMENTSTATUS_PartialPaid = "PP";
	/** Paid by Giro = PG */
	public static final String PAYMENTSTATUS_PaidByGiro = "PG";
	/** Not Paid = NP */
	public static final String PAYMENTSTATUS_NotPaid = "NP";
	/** Refusal = RS */
	public static final String PAYMENTSTATUS_Refusal = "RS";
	/** Handover Invoice = HI */
	public static final String PAYMENTSTATUS_HandoverInvoice = "HI";
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

	public com.unicore.model.I_UNS_BillingLine_Result getUNS_BillingLine_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingLine_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingLine_Result.Table_Name)
			.getPO(getUNS_BillingLine_Result_ID(), get_TrxName());	}

	/** Set Invoice Result.
		@param UNS_BillingLine_Result_ID Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID)
	{
		if (UNS_BillingLine_Result_ID < 1) 
			set_Value (COLUMNNAME_UNS_BillingLine_Result_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BillingLine_Result_ID, Integer.valueOf(UNS_BillingLine_Result_ID));
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

	public com.uns.model.I_UNS_CustomerBG getUNS_CustomerBG() throws RuntimeException
    {
		return (com.uns.model.I_UNS_CustomerBG)MTable.get(getCtx(), com.uns.model.I_UNS_CustomerBG.Table_Name)
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

	public com.unicore.model.I_UNS_PaymentReceipt_BP getUNS_PaymentReceipt_BP() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PaymentReceipt_BP)MTable.get(getCtx(), com.unicore.model.I_UNS_PaymentReceipt_BP.Table_Name)
			.getPO(getUNS_PaymentReceipt_BP_ID(), get_TrxName());	}

	/** Set Customer.
		@param UNS_PaymentReceipt_BP_ID Customer	  */
	public void setUNS_PaymentReceipt_BP_ID (int UNS_PaymentReceipt_BP_ID)
	{
		if (UNS_PaymentReceipt_BP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_BP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_BP_ID, Integer.valueOf(UNS_PaymentReceipt_BP_ID));
	}

	/** Get Customer.
		@return Customer	  */
	public int getUNS_PaymentReceipt_BP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PaymentReceipt_BP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice.
		@param UNS_PaymentReceipt_Inv_ID Invoice	  */
	public void setUNS_PaymentReceipt_Inv_ID (int UNS_PaymentReceipt_Inv_ID)
	{
		if (UNS_PaymentReceipt_Inv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_Inv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_Inv_ID, Integer.valueOf(UNS_PaymentReceipt_Inv_ID));
	}

	/** Get Invoice.
		@return Invoice	  */
	public int getUNS_PaymentReceipt_Inv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PaymentReceipt_Inv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PaymentReceipt_Inv_UU.
		@param UNS_PaymentReceipt_Inv_UU UNS_PaymentReceipt_Inv_UU	  */
	public void setUNS_PaymentReceipt_Inv_UU (String UNS_PaymentReceipt_Inv_UU)
	{
		set_Value (COLUMNNAME_UNS_PaymentReceipt_Inv_UU, UNS_PaymentReceipt_Inv_UU);
	}

	/** Get UNS_PaymentReceipt_Inv_UU.
		@return UNS_PaymentReceipt_Inv_UU	  */
	public String getUNS_PaymentReceipt_Inv_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PaymentReceipt_Inv_UU);
	}
}