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

/** Generated Model for UNS_BillingTrf_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingTrf_Detail extends PO implements I_UNS_BillingTrf_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160315L;

    /** Standard Constructor */
    public X_UNS_BillingTrf_Detail (Properties ctx, int UNS_BillingTrf_Detail_ID, String trxName)
    {
      super (ctx, UNS_BillingTrf_Detail_ID, trxName);
      /** if (UNS_BillingTrf_Detail_ID == 0)
        {
			setUNS_BillingTrf_Detail_ID (0);
			setUNS_BillingTrfValidation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingTrf_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BillingTrf_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		throw new IllegalArgumentException ("DateDoc is virtual column");	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Grouping Billing.
		@param UNS_BillingGroup_ID 
		Grouping Billing by parameter
	  */
	public void setUNS_BillingGroup_ID (int UNS_BillingGroup_ID)
	{
		throw new IllegalArgumentException ("UNS_BillingGroup_ID is virtual column");	}

	/** Get Grouping Billing.
		@return Grouping Billing by parameter
	  */
	public int getUNS_BillingGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set UNS_BillingTrf_Detail_UU.
		@param UNS_BillingTrf_Detail_UU UNS_BillingTrf_Detail_UU	  */
	public void setUNS_BillingTrf_Detail_UU (String UNS_BillingTrf_Detail_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingTrf_Detail_UU, UNS_BillingTrf_Detail_UU);
	}

	/** Get UNS_BillingTrf_Detail_UU.
		@return UNS_BillingTrf_Detail_UU	  */
	public String getUNS_BillingTrf_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingTrf_Detail_UU);
	}

	public com.unicore.model.I_UNS_BillingTrfValidation getUNS_BillingTrfValidation() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingTrfValidation)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingTrfValidation.Table_Name)
			.getPO(getUNS_BillingTrfValidation_ID(), get_TrxName());	}

	/** Set Billing Transfer Validation.
		@param UNS_BillingTrfValidation_ID Billing Transfer Validation	  */
	public void setUNS_BillingTrfValidation_ID (int UNS_BillingTrfValidation_ID)
	{
		if (UNS_BillingTrfValidation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrfValidation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingTrfValidation_ID, Integer.valueOf(UNS_BillingTrfValidation_ID));
	}

	/** Get Billing Transfer Validation.
		@return Billing Transfer Validation	  */
	public int getUNS_BillingTrfValidation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingTrfValidation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_PaymentReceipt_ID()));
    }
}