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

/** Generated Model for UNS_Cvs_LimitProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Cvs_LimitProduct extends PO implements I_UNS_Cvs_LimitProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150319L;

    /** Standard Constructor */
    public X_UNS_Cvs_LimitProduct (Properties ctx, int UNS_Cvs_LimitProduct_ID, String trxName)
    {
      super (ctx, UNS_Cvs_LimitProduct_ID, trxName);
      /** if (UNS_Cvs_LimitProduct_ID == 0)
        {
			setLimitAmt (Env.ZERO);
// 0
			setLimitQty (Env.ZERO);
// 0
			setM_Product_ID (0);
			setProcessed (false);
// N
			setUNS_Cvs_Limit_ID (0);
			setUNS_Cvs_LimitProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Cvs_LimitProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Cvs_LimitProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Limit Quantity.
		@param LimitQty Limit Quantity	  */
	public void setLimitQty (BigDecimal LimitQty)
	{
		set_Value (COLUMNNAME_LimitQty, LimitQty);
	}

	/** Get Limit Quantity.
		@return Limit Quantity	  */
	public BigDecimal getLimitQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LimitQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public com.unicore.model.I_UNS_Cvs_Limit getUNS_Cvs_Limit() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Cvs_Limit)MTable.get(getCtx(), com.unicore.model.I_UNS_Cvs_Limit.Table_Name)
			.getPO(getUNS_Cvs_Limit_ID(), get_TrxName());	}

	/** Set Canvas Limit.
		@param UNS_Cvs_Limit_ID Canvas Limit	  */
	public void setUNS_Cvs_Limit_ID (int UNS_Cvs_Limit_ID)
	{
		if (UNS_Cvs_Limit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Limit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Limit_ID, Integer.valueOf(UNS_Cvs_Limit_ID));
	}

	/** Get Canvas Limit.
		@return Canvas Limit	  */
	public int getUNS_Cvs_Limit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_Limit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canvas Limit Product.
		@param UNS_Cvs_LimitProduct_ID Canvas Limit Product	  */
	public void setUNS_Cvs_LimitProduct_ID (int UNS_Cvs_LimitProduct_ID)
	{
		if (UNS_Cvs_LimitProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_LimitProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_LimitProduct_ID, Integer.valueOf(UNS_Cvs_LimitProduct_ID));
	}

	/** Get Canvas Limit Product.
		@return Canvas Limit Product	  */
	public int getUNS_Cvs_LimitProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_LimitProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Cvs_LimitProduct_UU.
		@param UNS_Cvs_LimitProduct_UU UNS_Cvs_LimitProduct_UU	  */
	public void setUNS_Cvs_LimitProduct_UU (String UNS_Cvs_LimitProduct_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Cvs_LimitProduct_UU, UNS_Cvs_LimitProduct_UU);
	}

	/** Get UNS_Cvs_LimitProduct_UU.
		@return UNS_Cvs_LimitProduct_UU	  */
	public String getUNS_Cvs_LimitProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Cvs_LimitProduct_UU);
	}
}