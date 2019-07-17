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

/** Generated Model for UNS_Customer_SalesPricing
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Customer_SalesPricing extends PO implements I_UNS_Customer_SalesPricing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171105L;

    /** Standard Constructor */
    public X_UNS_Customer_SalesPricing (Properties ctx, int UNS_Customer_SalesPricing_ID, String trxName)
    {
      super (ctx, UNS_Customer_SalesPricing_ID, trxName);
      /** if (UNS_Customer_SalesPricing_ID == 0)
        {
			setAD_User_ID (0);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setInvoiceCountLimit (0);
// 0
			setM_PriceList_ID (0);
			setUNS_Customer_SalesPricing_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Customer_SalesPricing (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Customer_SalesPricing[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set Salesman.
		@param AD_User_ID 
		The salesman to whom the customer's linked to.
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Salesman.
		@return The salesman to whom the customer's linked to.
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_BPartner_ID()));
    }

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Invoice Count Limit.
		@param InvoiceCountLimit 
		The limitation to the business partner's number of invoice(s) of a product/category
	  */
	public void setInvoiceCountLimit (int InvoiceCountLimit)
	{
		set_Value (COLUMNNAME_InvoiceCountLimit, Integer.valueOf(InvoiceCountLimit));
	}

	/** Get Invoice Count Limit.
		@return The limitation to the business partner's number of invoice(s) of a product/category
	  */
	public int getInvoiceCountLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceCountLimit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Term.
		@param PaymentTerm 
		Payment Term
	  */
	public void setPaymentTerm (int PaymentTerm)
	{
		set_Value (COLUMNNAME_PaymentTerm, Integer.valueOf(PaymentTerm));
	}

	/** Get Payment Term.
		@return Payment Term
	  */
	public int getPaymentTerm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PaymentTerm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Sales Pricing.
		@param UNS_Customer_SalesPricing_ID Customer Sales Pricing	  */
	public void setUNS_Customer_SalesPricing_ID (int UNS_Customer_SalesPricing_ID)
	{
		if (UNS_Customer_SalesPricing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Customer_SalesPricing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Customer_SalesPricing_ID, Integer.valueOf(UNS_Customer_SalesPricing_ID));
	}

	/** Get Customer Sales Pricing.
		@return Customer Sales Pricing	  */
	public int getUNS_Customer_SalesPricing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Customer_SalesPricing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Customer_SalesPricing_UU.
		@param UNS_Customer_SalesPricing_UU UNS_Customer_SalesPricing_UU	  */
	public void setUNS_Customer_SalesPricing_UU (String UNS_Customer_SalesPricing_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Customer_SalesPricing_UU, UNS_Customer_SalesPricing_UU);
	}

	/** Get UNS_Customer_SalesPricing_UU.
		@return UNS_Customer_SalesPricing_UU	  */
	public String getUNS_Customer_SalesPricing_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Customer_SalesPricing_UU);
	}

	public com.unicore.model.I_UNS_Rayon getUNS_Rayon() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Rayon)MTable.get(getCtx(), com.unicore.model.I_UNS_Rayon.Table_Name)
			.getPO(getUNS_Rayon_ID(), get_TrxName());	}

	/** Set Rayon.
		@param UNS_Rayon_ID Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID)
	{
		if (UNS_Rayon_ID < 1) 
			set_Value (COLUMNNAME_UNS_Rayon_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Rayon_ID, Integer.valueOf(UNS_Rayon_ID));
	}

	/** Get Rayon.
		@return Rayon	  */
	public int getUNS_Rayon_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Rayon_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}