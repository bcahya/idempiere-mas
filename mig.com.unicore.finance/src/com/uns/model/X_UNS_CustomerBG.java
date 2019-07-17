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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_CustomerBG
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerBG extends PO implements I_UNS_CustomerBG, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160216L;

    /** Standard Constructor */
    public X_UNS_CustomerBG (Properties ctx, int UNS_CustomerBG_ID, String trxName)
    {
      super (ctx, UNS_CustomerBG_ID, trxName);
      /** if (UNS_CustomerBG_ID == 0)
        {
			setAccountNo (null);
			setC_Bank_ID (0);
			setC_BPartner_ID (0);
			setDocStatus (null);
// DR
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setGrandTotal (Env.ZERO);
// 0
			setName (null);
			setPaidAmt (Env.ZERO);
// 0
			setPaymentStatus (null);
			setReceiptDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_CustomerBG_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerBG (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerBG[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Advance Customer BG.
		@param AdvancaCustomerBg Advance Customer BG	  */
	public void setAdvancaCustomerBg (String AdvancaCustomerBg)
	{
		set_ValueNoCheck (COLUMNNAME_AdvancaCustomerBg, AdvancaCustomerBg);
	}

	/** Get Advance Customer BG.
		@return Advance Customer BG	  */
	public String getAdvancaCustomerBg () 
	{
		return (String)get_Value(COLUMNNAME_AdvancaCustomerBg);
	}

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
    {
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
			.getPO(getC_Bank_ID(), get_TrxName());	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Charge amount.
		@param ChargeAmt 
		Charge Amount
	  */
	public void setChargeAmt (BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	/** Get Charge amount.
		@return Charge Amount
	  */
	public BigDecimal getChargeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChargeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Disbursement Date.
		@param DisbursementDate 
		Date when payment can be processed (Cek/Giro)
	  */
	public void setDisbursementDate (Timestamp DisbursementDate)
	{
		set_Value (COLUMNNAME_DisbursementDate, DisbursementDate);
	}

	/** Get Disbursement Date.
		@return Date when payment can be processed (Cek/Giro)
	  */
	public Timestamp getDisbursementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DisbursementDate);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit Amount.
		@param LimitAmt Limit Amount	  */
	public void setLimitAmt (BigDecimal LimitAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LimitAmt, LimitAmt);
	}

	/** Get Limit Amount.
		@return Limit Amount	  */
	public BigDecimal getLimitAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LimitAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unallocated Amount.
		@param UnallocatedAmt Unallocated Amount	  */
	public void setUnallocatedAmt (BigDecimal UnallocatedAmt)
	{
		throw new IllegalArgumentException ("UnallocatedAmt is virtual column");	}

	/** Get Unallocated Amount.
		@return Unallocated Amount	  */
	public BigDecimal getUnallocatedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnallocatedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Customer Billed Giro.
		@param UNS_CustomerBG_ID Customer Billed Giro	  */
	public void setUNS_CustomerBG_ID (int UNS_CustomerBG_ID)
	{
		if (UNS_CustomerBG_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerBG_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerBG_ID, Integer.valueOf(UNS_CustomerBG_ID));
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

	/** Set UNS_CustomerBG_UU.
		@param UNS_CustomerBG_UU UNS_CustomerBG_UU	  */
	public void setUNS_CustomerBG_UU (String UNS_CustomerBG_UU)
	{
		set_Value (COLUMNNAME_UNS_CustomerBG_UU, UNS_CustomerBG_UU);
	}

	/** Get UNS_CustomerBG_UU.
		@return UNS_CustomerBG_UU	  */
	public String getUNS_CustomerBG_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerBG_UU);
	}
}