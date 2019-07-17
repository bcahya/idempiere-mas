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

/** Generated Model for UNS_Discount_Product
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Discount_Product extends PO implements I_UNS_Discount_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150419L;

    /** Standard Constructor */
    public X_UNS_Discount_Product (Properties ctx, int UNS_Discount_Product_ID, String trxName)
    {
      super (ctx, UNS_Discount_Product_ID, trxName);
      /** if (UNS_Discount_Product_ID == 0)
        {
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM UNS_DiscountBonus WHERE  CASE   WHEN UNS_DiscountBonus.M_DiscountSchema_ID IS NOT NULL    THEN UNS_DiscountBonus.M_DiscountSchema_ID=@M_DiscountSchema_ID@  WHEN UNS_DiscountBonus.M_DiscountSchemaBreak_ID IS NOT NULL    THEN UNS_DiscountBonus.M_DiscountSchemaBreak_ID=@M_DiscountSchemaBreak_ID@  WHEN UNS_DiscountBonus.UNS_DSBreakLine_ID IS NOT NULL    THEN UNS_DiscountBonus.UNS_DSBreakLine_ID=@UNS_DSBreakLine_ID@  ELSE 1=1 END
			setUNS_Discount_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Discount_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Discount_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
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

	/** Set Qty Requiered.
		@param QtyRequiered Qty Requiered	  */
	public void setQtyRequiered (BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	/** Get Qty Requiered.
		@return Qty Requiered	  */
	public BigDecimal getQtyRequiered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Selection.
		@param UNS_Discount_Product_ID Product Selection	  */
	public void setUNS_Discount_Product_ID (int UNS_Discount_Product_ID)
	{
		if (UNS_Discount_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Product_ID, Integer.valueOf(UNS_Discount_Product_ID));
	}

	/** Get Product Selection.
		@return Product Selection	  */
	public int getUNS_Discount_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Discount_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Discount_Product_UU.
		@param UNS_Discount_Product_UU UNS_Discount_Product_UU	  */
	public void setUNS_Discount_Product_UU (String UNS_Discount_Product_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Discount_Product_UU, UNS_Discount_Product_UU);
	}

	/** Get UNS_Discount_Product_UU.
		@return UNS_Discount_Product_UU	  */
	public String getUNS_Discount_Product_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Discount_Product_UU);
	}

	/** Set Discount Break Line.
		@param UNS_DSBreakLine_ID Discount Break Line	  */
	public void setUNS_DSBreakLine_ID (int UNS_DSBreakLine_ID)
	{
		if (UNS_DSBreakLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, Integer.valueOf(UNS_DSBreakLine_ID));
	}

	/** Get Discount Break Line.
		@return Discount Break Line	  */
	public int getUNS_DSBreakLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DSBreakLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}