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

/** Generated Model for UNS_Billing_Result
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Billing_Result extends PO implements I_UNS_Billing_Result, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150309L;

    /** Standard Constructor */
    public X_UNS_Billing_Result (Properties ctx, int UNS_Billing_Result_ID, String trxName)
    {
      super (ctx, UNS_Billing_Result_ID, trxName);
      /** if (UNS_Billing_Result_ID == 0)
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
			setUNS_BillingGroup_Result_ID (0);
			setUNS_Billing_ID (0);
			setUNS_Billing_Result_ID (0);
			setUNS_Billing_Result_UU (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Billing_Result (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Billing_Result[")
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

	public com.unicore.model.I_UNS_BillingGroup_Result getUNS_BillingGroup_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingGroup_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingGroup_Result.Table_Name)
			.getPO(getUNS_BillingGroup_Result_ID(), get_TrxName());	}

	/** Set Grouping Billing Result.
		@param UNS_BillingGroup_Result_ID Grouping Billing Result	  */
	public void setUNS_BillingGroup_Result_ID (int UNS_BillingGroup_Result_ID)
	{
		if (UNS_BillingGroup_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_Result_ID, Integer.valueOf(UNS_BillingGroup_Result_ID));
	}

	/** Get Grouping Billing Result.
		@return Grouping Billing Result	  */
	public int getUNS_BillingGroup_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingGroup_Result_ID);
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
			set_Value (COLUMNNAME_UNS_Billing_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Billing_ID, Integer.valueOf(UNS_Billing_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Billing_ID()));
    }

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

	/** Set UNS_Billing_Result_UU.
		@param UNS_Billing_Result_UU UNS_Billing_Result_UU	  */
	public void setUNS_Billing_Result_UU (String UNS_Billing_Result_UU)
	{
		set_Value (COLUMNNAME_UNS_Billing_Result_UU, UNS_Billing_Result_UU);
	}

	/** Get UNS_Billing_Result_UU.
		@return UNS_Billing_Result_UU	  */
	public String getUNS_Billing_Result_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Billing_Result_UU);
	}
}