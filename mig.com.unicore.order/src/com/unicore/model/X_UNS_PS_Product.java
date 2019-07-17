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

/** Generated Model for UNS_PS_Product
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PS_Product extends PO implements I_UNS_PS_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150301L;

    /** Standard Constructor */
    public X_UNS_PS_Product (Properties ctx, int UNS_PS_Product_ID, String trxName)
    {
      super (ctx, UNS_PS_Product_ID, trxName);
      /** if (UNS_PS_Product_ID == 0)
        {
			setUNS_PointSchema_ID (0);
			setUNS_PS_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PS_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PS_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
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

	public com.unicore.model.I_UNS_PointSchema getUNS_PointSchema() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PointSchema)MTable.get(getCtx(), com.unicore.model.I_UNS_PointSchema.Table_Name)
			.getPO(getUNS_PointSchema_ID(), get_TrxName());	}

	/** Set Point Schema.
		@param UNS_PointSchema_ID Point Schema	  */
	public void setUNS_PointSchema_ID (int UNS_PointSchema_ID)
	{
		if (UNS_PointSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PointSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PointSchema_ID, Integer.valueOf(UNS_PointSchema_ID));
	}

	/** Get Point Schema.
		@return Point Schema	  */
	public int getUNS_PointSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PointSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Point Schema.
		@param UNS_PS_Product_ID Product Point Schema	  */
	public void setUNS_PS_Product_ID (int UNS_PS_Product_ID)
	{
		if (UNS_PS_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PS_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PS_Product_ID, Integer.valueOf(UNS_PS_Product_ID));
	}

	/** Get Product Point Schema.
		@return Product Point Schema	  */
	public int getUNS_PS_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PS_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}