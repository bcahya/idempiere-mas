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

/** Generated Model for UNS_BillingLine_Giro
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingLine_Giro extends PO implements I_UNS_BillingLine_Giro, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150815L;

    /** Standard Constructor */
    public X_UNS_BillingLine_Giro (Properties ctx, int UNS_BillingLine_Giro_ID, String trxName)
    {
      super (ctx, UNS_BillingLine_Giro_ID, trxName);
      /** if (UNS_BillingLine_Giro_ID == 0)
        {
			setCreateCustomerBG (null);
// N
			setUNS_BillingLine_Giro_ID (0);
			setUNS_BillingLine_Result_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingLine_Giro (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BillingLine_Giro[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Giro List.
		@param UNS_BillingLine_Giro_ID Giro List	  */
	public void setUNS_BillingLine_Giro_ID (int UNS_BillingLine_Giro_ID)
	{
		if (UNS_BillingLine_Giro_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_Giro_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingLine_Giro_ID, Integer.valueOf(UNS_BillingLine_Giro_ID));
	}

	/** Get Giro List.
		@return Giro List	  */
	public int getUNS_BillingLine_Giro_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingLine_Giro_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BillingLine_Giro_UU.
		@param UNS_BillingLine_Giro_UU UNS_BillingLine_Giro_UU	  */
	public void setUNS_BillingLine_Giro_UU (String UNS_BillingLine_Giro_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingLine_Giro_UU, UNS_BillingLine_Giro_UU);
	}

	/** Get UNS_BillingLine_Giro_UU.
		@return UNS_BillingLine_Giro_UU	  */
	public String getUNS_BillingLine_Giro_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingLine_Giro_UU);
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
}