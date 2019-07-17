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

/** Generated Model for UNS_PaymentReceipt_BP
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PaymentReceipt_BP extends PO implements I_UNS_PaymentReceipt_BP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141224L;

    /** Standard Constructor */
    public X_UNS_PaymentReceipt_BP (Properties ctx, int UNS_PaymentReceipt_BP_ID, String trxName)
    {
      super (ctx, UNS_PaymentReceipt_BP_ID, trxName);
      /** if (UNS_PaymentReceipt_BP_ID == 0)
        {
			setDifferenceAmt (Env.ZERO);
// 0
			setIsApproved (false);
// N
			setPaidAmt (Env.ZERO);
// 0
			setReceiptAmt (Env.ZERO);
// 0
			setTotalAmt (Env.ZERO);
// 0
			setUNS_Billing_Result_ID (0);
			setUNS_PaymentReceipt_BP_ID (0);
			setUNS_PaymentReceipt_BP_UU (null);
			setUNS_PaymentReceipt_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PaymentReceipt_BP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PaymentReceipt_BP[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
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

	/** Set UNS_PaymentReceipt_BP_UU.
		@param UNS_PaymentReceipt_BP_UU UNS_PaymentReceipt_BP_UU	  */
	public void setUNS_PaymentReceipt_BP_UU (String UNS_PaymentReceipt_BP_UU)
	{
		set_Value (COLUMNNAME_UNS_PaymentReceipt_BP_UU, UNS_PaymentReceipt_BP_UU);
	}

	/** Get UNS_PaymentReceipt_BP_UU.
		@return UNS_PaymentReceipt_BP_UU	  */
	public String getUNS_PaymentReceipt_BP_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PaymentReceipt_BP_UU);
	}

	public com.unicore.model.I_UNS_PaymentReceipt getUNS_PaymentReceipt() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PaymentReceipt)MTable.get(getCtx(), com.unicore.model.I_UNS_PaymentReceipt.Table_Name)
			.getPO(getUNS_PaymentReceipt_ID(), get_TrxName());	}

	/** Set Billing Payment Receipt.
		@param UNS_PaymentReceipt_ID Billing Payment Receipt	  */
	public void setUNS_PaymentReceipt_ID (int UNS_PaymentReceipt_ID)
	{
		if (UNS_PaymentReceipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_ID, Integer.valueOf(UNS_PaymentReceipt_ID));
	}

	/** Get Billing Payment Receipt.
		@return Billing Payment Receipt	  */
	public int getUNS_PaymentReceipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PaymentReceipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}