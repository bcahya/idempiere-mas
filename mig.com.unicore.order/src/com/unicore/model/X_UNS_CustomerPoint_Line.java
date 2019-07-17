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

/** Generated Model for UNS_CustomerPoint_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerPoint_Line extends PO implements I_UNS_CustomerPoint_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150425L;

    /** Standard Constructor */
    public X_UNS_CustomerPoint_Line (Properties ctx, int UNS_CustomerPoint_Line_ID, String trxName)
    {
      super (ctx, UNS_CustomerPoint_Line_ID, trxName);
      /** if (UNS_CustomerPoint_Line_ID == 0)
        {
			setUNS_CustomerPoint_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerPoint_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerPoint_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Point.
		@param Point Point	  */
	public void setPoint (BigDecimal Point)
	{
		set_Value (COLUMNNAME_Point, Point);
	}

	/** Get Point.
		@return Point	  */
	public BigDecimal getPoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Point);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public com.unicore.model.I_UNS_CustomerPoint getUNS_CustomerPoint() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CustomerPoint)MTable.get(getCtx(), com.unicore.model.I_UNS_CustomerPoint.Table_Name)
			.getPO(getUNS_CustomerPoint_ID(), get_TrxName());	}

	/** Set Customer Point.
		@param UNS_CustomerPoint_ID Customer Point	  */
	public void setUNS_CustomerPoint_ID (int UNS_CustomerPoint_ID)
	{
		if (UNS_CustomerPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, Integer.valueOf(UNS_CustomerPoint_ID));
	}

	/** Get Customer Point.
		@return Customer Point	  */
	public int getUNS_CustomerPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Point Line.
		@param UNS_CustomerPoint_Line_ID Customer Point Line	  */
	public void setUNS_CustomerPoint_Line_ID (int UNS_CustomerPoint_Line_ID)
	{
		if (UNS_CustomerPoint_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_Line_ID, Integer.valueOf(UNS_CustomerPoint_Line_ID));
	}

	/** Get Customer Point Line.
		@return Customer Point Line	  */
	public int getUNS_CustomerPoint_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerPoint_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CustomerPoint_Line_UU.
		@param UNS_CustomerPoint_Line_UU UNS_CustomerPoint_Line_UU	  */
	public void setUNS_CustomerPoint_Line_UU (String UNS_CustomerPoint_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_Line_UU, UNS_CustomerPoint_Line_UU);
	}

	/** Get UNS_CustomerPoint_Line_UU.
		@return UNS_CustomerPoint_Line_UU	  */
	public String getUNS_CustomerPoint_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerPoint_Line_UU);
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
}