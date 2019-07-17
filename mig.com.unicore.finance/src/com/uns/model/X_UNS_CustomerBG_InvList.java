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
import java.util.Properties;

import org.compiere.model.*;
import org.compiere.util.Env;

import com.unicore.model.I_UNS_BillingLine_Result;

/** Generated Model for UNS_CustomerBG_InvList
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerBG_InvList extends PO implements I_UNS_CustomerBG_InvList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150729L;

    /** Standard Constructor */
    public X_UNS_CustomerBG_InvList (Properties ctx, int UNS_CustomerBG_InvList_ID, String trxName)
    {
      super (ctx, UNS_CustomerBG_InvList_ID, trxName);
      /** if (UNS_CustomerBG_InvList_ID == 0)
        {
			setNetAmtToInvoice (Env.ZERO);
// 0
			setPaidAmt (Env.ZERO);
// 0
			setProcessed (false);
// N
			setUNS_CustomerBG_InvList_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerBG_InvList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerBG_InvList[")
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_PaymentAllocate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentAllocate_ID, Integer.valueOf(C_PaymentAllocate_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
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

	/** Set Giro Invoice List.
		@param UNS_CustomerBG_InvList_ID Giro Invoice List	  */
	public void setUNS_CustomerBG_InvList_ID (int UNS_CustomerBG_InvList_ID)
	{
		if (UNS_CustomerBG_InvList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerBG_InvList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerBG_InvList_ID, Integer.valueOf(UNS_CustomerBG_InvList_ID));
	}

	/** Get Giro Invoice List.
		@return Giro Invoice List	  */
	public int getUNS_CustomerBG_InvList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerBG_InvList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CustomerBG_InvList_UU.
		@param UNS_CustomerBG_InvList_UU UNS_CustomerBG_InvList_UU	  */
	public void setUNS_CustomerBG_InvList_UU (String UNS_CustomerBG_InvList_UU)
	{
		set_Value (COLUMNNAME_UNS_CustomerBG_InvList_UU, UNS_CustomerBG_InvList_UU);
	}

	/** Get UNS_CustomerBG_InvList_UU.
		@return UNS_CustomerBG_InvList_UU	  */
	public String getUNS_CustomerBG_InvList_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerBG_InvList_UU);
	}
}