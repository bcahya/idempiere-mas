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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_BP_InvoiceLimit
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BP_InvoiceLimit extends PO implements I_UNS_BP_InvoiceLimit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141211L;

    /** Standard Constructor */
    public X_UNS_BP_InvoiceLimit (Properties ctx, int UNS_BP_InvoiceLimit_ID, String trxName)
    {
      super (ctx, UNS_BP_InvoiceLimit_ID, trxName);
      /** if (UNS_BP_InvoiceLimit_ID == 0)
        {
			setC_BPartner_ID (0);
			setLimit (0);
			setM_Product_Category_ID (0);
			setUNS_BP_InvoiceLimit_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BP_InvoiceLimit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BP_InvoiceLimit[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Duration Limit.
		@param Limit 
		Maximum Duration in Duration Unit
	  */
	public void setLimit (int Limit)
	{
		set_Value (COLUMNNAME_Limit, Integer.valueOf(Limit));
	}

	/** Get Duration Limit.
		@return Maximum Duration in Duration Unit
	  */
	public int getLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Limit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice Limit.
		@param UNS_BP_InvoiceLimit_ID Invoice Limit	  */
	public void setUNS_BP_InvoiceLimit_ID (int UNS_BP_InvoiceLimit_ID)
	{
		if (UNS_BP_InvoiceLimit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BP_InvoiceLimit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BP_InvoiceLimit_ID, Integer.valueOf(UNS_BP_InvoiceLimit_ID));
	}

	/** Get Invoice Limit.
		@return Invoice Limit	  */
	public int getUNS_BP_InvoiceLimit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BP_InvoiceLimit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BP_InvoiceLimit_UU.
		@param UNS_BP_InvoiceLimit_UU UNS_BP_InvoiceLimit_UU	  */
	public void setUNS_BP_InvoiceLimit_UU (String UNS_BP_InvoiceLimit_UU)
	{
		set_Value (COLUMNNAME_UNS_BP_InvoiceLimit_UU, UNS_BP_InvoiceLimit_UU);
	}

	/** Get UNS_BP_InvoiceLimit_UU.
		@return UNS_BP_InvoiceLimit_UU	  */
	public String getUNS_BP_InvoiceLimit_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BP_InvoiceLimit_UU);
	}
}