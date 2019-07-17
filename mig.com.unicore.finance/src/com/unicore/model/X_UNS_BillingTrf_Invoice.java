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

/** Generated Model for UNS_BillingTrf_Invoice
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingTrf_Invoice extends PO implements I_UNS_BillingTrf_Invoice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171018L;

    /** Standard Constructor */
    public X_UNS_BillingTrf_Invoice (Properties ctx, int UNS_BillingTrf_Invoice_ID, String trxName)
    {
      super (ctx, UNS_BillingTrf_Invoice_ID, trxName);
      /** if (UNS_BillingTrf_Invoice_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setC_BPartner_ID (0);
			setC_Invoice_ID (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
			setTotalAmt (Env.ZERO);
// 0
			setUNS_BillingTrf_Invoice_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingTrf_Invoice (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_BillingTrf_Invoice[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
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

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_BillingTrf_Detail getUNS_BillingTrf_Detail() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingTrf_Detail)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingTrf_Detail.Table_Name)
			.getPO(getUNS_BillingTrf_Detail_ID(), get_TrxName());	}

	/** Set Billing Transfer Detail.
		@param UNS_BillingTrf_Detail_ID Billing Transfer Detail	  */
	public void setUNS_BillingTrf_Detail_ID (int UNS_BillingTrf_Detail_ID)
	{
		if (UNS_BillingTrf_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrf_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrf_Detail_ID, Integer.valueOf(UNS_BillingTrf_Detail_ID));
	}

	/** Get Billing Transfer Detail.
		@return Billing Transfer Detail	  */
	public int getUNS_BillingTrf_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingTrf_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Billing Transfer Invoice.
		@param UNS_BillingTrf_Invoice_ID Billing Transfer Invoice	  */
	public void setUNS_BillingTrf_Invoice_ID (int UNS_BillingTrf_Invoice_ID)
	{
		if (UNS_BillingTrf_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrf_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrf_Invoice_ID, Integer.valueOf(UNS_BillingTrf_Invoice_ID));
	}

	/** Get Billing Transfer Invoice.
		@return Billing Transfer Invoice	  */
	public int getUNS_BillingTrf_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingTrf_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BillingTrf_Invoice_UU.
		@param UNS_BillingTrf_Invoice_UU UNS_BillingTrf_Invoice_UU	  */
	public void setUNS_BillingTrf_Invoice_UU (String UNS_BillingTrf_Invoice_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingTrf_Invoice_UU, UNS_BillingTrf_Invoice_UU);
	}

	/** Get UNS_BillingTrf_Invoice_UU.
		@return UNS_BillingTrf_Invoice_UU	  */
	public String getUNS_BillingTrf_Invoice_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingTrf_Invoice_UU);
	}
}