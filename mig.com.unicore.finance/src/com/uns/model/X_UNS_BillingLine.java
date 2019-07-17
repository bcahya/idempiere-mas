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

/** Generated Model for UNS_BillingLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingLine extends PO implements I_UNS_BillingLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150730L;

    /** Standard Constructor */
    public X_UNS_BillingLine (Properties ctx, int UNS_BillingLine_ID, String trxName)
    {
      super (ctx, UNS_BillingLine_ID, trxName);
      /** if (UNS_BillingLine_ID == 0)
        {
			setC_Invoice_ID (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setImportDutyAmt (Env.ZERO);
// 0
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_InvoiceLine WHERE C_Billing_ID=@C_Billing_ID@
			setNetAmtToInvoice (Env.ZERO);
// 0
			setPaidAmt (Env.ZERO);
// 0
			setPPh22Import (Env.ZERO);
// 0
			setProcessed (false);
			setUNS_Billing_ID (0);
			setUNS_BillingLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BillingLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoicePaySchedule)MTable.get(getCtx(), org.compiere.model.I_C_InvoicePaySchedule.Table_Name)
			.getPO(getC_InvoicePaySchedule_ID(), get_TrxName());	}

	/** Set Invoice Payment Schedule.
		@param C_InvoicePaySchedule_ID 
		Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, Integer.valueOf(C_InvoicePaySchedule_ID));
	}

	/** Get Invoice Payment Schedule.
		@return Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
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

	/** Set Import Duty Amount.
		@param ImportDutyAmt Import Duty Amount	  */
	public void setImportDutyAmt (BigDecimal ImportDutyAmt)
	{
		set_Value (COLUMNNAME_ImportDutyAmt, ImportDutyAmt);
	}

	/** Get Import Duty Amount.
		@return Import Duty Amount	  */
	public BigDecimal getImportDutyAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ImportDutyAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set PPh 22 Import.
		@param PPh22Import 
		Total amount of PPh 22 for imported content
	  */
	public void setPPh22Import (BigDecimal PPh22Import)
	{
		set_Value (COLUMNNAME_PPh22Import, PPh22Import);
	}

	/** Get PPh 22 Import.
		@return Total amount of PPh 22 for imported content
	  */
	public BigDecimal getPPh22Import () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PPh22Import);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
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
		throw new IllegalArgumentException ("SalesRep_ID is virtual column");	}

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

	public com.uns.model.I_UNS_Billing getUNS_Billing() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Billing)MTable.get(getCtx(), com.uns.model.I_UNS_Billing.Table_Name)
			.getPO(getUNS_Billing_ID(), get_TrxName());	}

	/** Set Billing.
		@param UNS_Billing_ID Billing	  */
	public void setUNS_Billing_ID (int UNS_Billing_ID)
	{
		if (UNS_Billing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_ID, Integer.valueOf(UNS_Billing_ID));
	}

	/** Get Billing.
		@return Billing	  */
	public int getUNS_Billing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Billing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set UNS_BillingLine_UU.
		@param UNS_BillingLine_UU UNS_BillingLine_UU	  */
	public void setUNS_BillingLine_UU (String UNS_BillingLine_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingLine_UU, UNS_BillingLine_UU);
	}

	/** Get UNS_BillingLine_UU.
		@return UNS_BillingLine_UU	  */
	public String getUNS_BillingLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingLine_UU);
	}
}