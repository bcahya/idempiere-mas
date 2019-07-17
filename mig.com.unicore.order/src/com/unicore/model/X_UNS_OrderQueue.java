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

/** Generated Model for UNS_OrderQueue
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_OrderQueue extends PO implements I_UNS_OrderQueue, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150330L;

    /** Standard Constructor */
    public X_UNS_OrderQueue (Properties ctx, int UNS_OrderQueue_ID, String trxName)
    {
      super (ctx, UNS_OrderQueue_ID, trxName);
      /** if (UNS_OrderQueue_ID == 0)
        {
			setUNS_OrderQueue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OrderQueue (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OrderQueue[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set On Hand Quantity.
		@param QtyOnHand 
		On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		throw new IllegalArgumentException ("QtyOnHand is virtual column");	}

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

	/** Set QtyQueue.
		@param QtyQueue QtyQueue	  */
	public void setQtyQueue (BigDecimal QtyQueue)
	{
		throw new IllegalArgumentException ("QtyQueue is virtual column");	}

	/** Get QtyQueue.
		@return QtyQueue	  */
	public BigDecimal getQtyQueue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyQueue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Order Queue.
		@param UNS_OrderQueue_ID Order Queue	  */
	public void setUNS_OrderQueue_ID (int UNS_OrderQueue_ID)
	{
		if (UNS_OrderQueue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_ID, Integer.valueOf(UNS_OrderQueue_ID));
	}

	/** Get Order Queue.
		@return Order Queue	  */
	public int getUNS_OrderQueue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrderQueue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OrderQueue_UU.
		@param UNS_OrderQueue_UU UNS_OrderQueue_UU	  */
	public void setUNS_OrderQueue_UU (String UNS_OrderQueue_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_UU, UNS_OrderQueue_UU);
	}

	/** Get UNS_OrderQueue_UU.
		@return UNS_OrderQueue_UU	  */
	public String getUNS_OrderQueue_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_OrderQueue_UU);
	}
}