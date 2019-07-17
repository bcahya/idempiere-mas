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

/** Generated Model for UNS_PointSchema_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PointSchema_Line extends PO implements I_UNS_PointSchema_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150306L;

    /** Standard Constructor */
    public X_UNS_PointSchema_Line (Properties ctx, int UNS_PointSchema_Line_ID, String trxName)
    {
      super (ctx, UNS_PointSchema_Line_ID, trxName);
      /** if (UNS_PointSchema_Line_ID == 0)
        {
			setUNS_PointSchema_Line_ID (0);
			setUNS_PS_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PointSchema_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PointSchema_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Point Schema Line.
		@param UNS_PointSchema_Line_ID Point Schema Line	  */
	public void setUNS_PointSchema_Line_ID (int UNS_PointSchema_Line_ID)
	{
		if (UNS_PointSchema_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PointSchema_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PointSchema_Line_ID, Integer.valueOf(UNS_PointSchema_Line_ID));
	}

	/** Get Point Schema Line.
		@return Point Schema Line	  */
	public int getUNS_PointSchema_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PointSchema_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_PS_Product getUNS_PS_Product() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PS_Product)MTable.get(getCtx(), com.unicore.model.I_UNS_PS_Product.Table_Name)
			.getPO(getUNS_PS_Product_ID(), get_TrxName());	}

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

	/** Set ValuePoint.
		@param ValuePoint ValuePoint	  */
	public void setValuePoint (BigDecimal ValuePoint)
	{
		set_Value (COLUMNNAME_ValuePoint, ValuePoint);
	}

	/** Get ValuePoint.
		@return ValuePoint	  */
	public BigDecimal getValuePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValuePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}