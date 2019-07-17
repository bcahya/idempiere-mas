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

/** Generated Model for UNS_ConfirmationQueue_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ConfirmationQueue_Line extends PO implements I_UNS_ConfirmationQueue_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150330L;

    /** Standard Constructor */
    public X_UNS_ConfirmationQueue_Line (Properties ctx, int UNS_ConfirmationQueue_Line_ID, String trxName)
    {
      super (ctx, UNS_ConfirmationQueue_Line_ID, trxName);
      /** if (UNS_ConfirmationQueue_Line_ID == 0)
        {
			setUNS_ConfirmationQueue_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ConfirmationQueue_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ConfirmationQueue_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set isReserved.
		@param isReserved isReserved	  */
	public void setisReserved (boolean isReserved)
	{
		set_Value (COLUMNNAME_isReserved, Boolean.valueOf(isReserved));
	}

	/** Get isReserved.
		@return isReserved	  */
	public boolean isReserved () 
	{
		Object oo = get_Value(COLUMNNAME_isReserved);
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

	/** Set On Hand Quantity.
		@param QtyOnHand 
		On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get On Hand Quantity.
		@return On Hand Quantity
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QueueBeforeThis.
		@param QueueBeforeThis QueueBeforeThis	  */
	public void setQueueBeforeThis (BigDecimal QueueBeforeThis)
	{
		throw new IllegalArgumentException ("QueueBeforeThis is virtual column");	}

	/** Get QueueBeforeThis.
		@return QueueBeforeThis	  */
	public BigDecimal getQueueBeforeThis () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QueueBeforeThis);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param Sequence Sequence	  */
	public void setSequence (BigDecimal Sequence)
	{
		set_Value (COLUMNNAME_Sequence, Sequence);
	}

	/** Get Sequence.
		@return Sequence	  */
	public BigDecimal getSequence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Sequence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_ConfirmationQueue getUNS_ConfirmationQueue() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ConfirmationQueue)MTable.get(getCtx(), com.unicore.model.I_UNS_ConfirmationQueue.Table_Name)
			.getPO(getUNS_ConfirmationQueue_ID(), get_TrxName());	}

	/** Set Confirmation Queue.
		@param UNS_ConfirmationQueue_ID Confirmation Queue	  */
	public void setUNS_ConfirmationQueue_ID (int UNS_ConfirmationQueue_ID)
	{
		if (UNS_ConfirmationQueue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ConfirmationQueue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ConfirmationQueue_ID, Integer.valueOf(UNS_ConfirmationQueue_ID));
	}

	/** Get Confirmation Queue.
		@return Confirmation Queue	  */
	public int getUNS_ConfirmationQueue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ConfirmationQueue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Confirmation Queue Line.
		@param UNS_ConfirmationQueue_Line_ID Confirmation Queue Line	  */
	public void setUNS_ConfirmationQueue_Line_ID (int UNS_ConfirmationQueue_Line_ID)
	{
		if (UNS_ConfirmationQueue_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ConfirmationQueue_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ConfirmationQueue_Line_ID, Integer.valueOf(UNS_ConfirmationQueue_Line_ID));
	}

	/** Get Confirmation Queue Line.
		@return Confirmation Queue Line	  */
	public int getUNS_ConfirmationQueue_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ConfirmationQueue_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ConfirmationQueue_Line_UU.
		@param UNS_ConfirmationQueue_Line_UU UNS_ConfirmationQueue_Line_UU	  */
	public void setUNS_ConfirmationQueue_Line_UU (String UNS_ConfirmationQueue_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ConfirmationQueue_Line_UU, UNS_ConfirmationQueue_Line_UU);
	}

	/** Get UNS_ConfirmationQueue_Line_UU.
		@return UNS_ConfirmationQueue_Line_UU	  */
	public String getUNS_ConfirmationQueue_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ConfirmationQueue_Line_UU);
	}
}