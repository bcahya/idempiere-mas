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

/** Generated Model for UNS_PL_ConfirmOrder
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ConfirmOrder extends PO implements I_UNS_PL_ConfirmOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141202L;

    /** Standard Constructor */
    public X_UNS_PL_ConfirmOrder (Properties ctx, int UNS_PL_ConfirmOrder_ID, String trxName)
    {
      super (ctx, UNS_PL_ConfirmOrder_ID, trxName);
      /** if (UNS_PL_ConfirmOrder_ID == 0)
        {
			setConfirmedQty (Env.ZERO);
			setDifferenceQty (Env.ZERO);
			setM_Product_ID (0);
			setProcessed (false);
			setScrappedQty (Env.ZERO);
			setTargetQty (Env.ZERO);
			setUNS_PL_ConfirmOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ConfirmOrder (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ConfirmOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Confirmed Quantity.
		@param ConfirmedQty 
		Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	/** Get Confirmed Quantity.
		@return Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		@param DifferenceQty 
		Difference Quantity
	  */
	public void setDifferenceQty (BigDecimal DifferenceQty)
	{
		set_Value (COLUMNNAME_DifferenceQty, DifferenceQty);
	}

	/** Get Difference.
		@return Difference Quantity
	  */
	public BigDecimal getDifferenceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Scrapped Quantity.
		@param ScrappedQty 
		The Quantity scrapped due to QA issues
	  */
	public void setScrappedQty (BigDecimal ScrappedQty)
	{
		set_Value (COLUMNNAME_ScrappedQty, ScrappedQty);
	}

	/** Get Scrapped Quantity.
		@return The Quantity scrapped due to QA issues
	  */
	public BigDecimal getScrappedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ScrappedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Quantity.
		@param TargetQty 
		Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	/** Get Target Quantity.
		@return Target Movement Quantity
	  */
	public BigDecimal getTargetQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Confirmation Order.
		@param UNS_PL_ConfirmOrder_ID Confirmation Order	  */
	public void setUNS_PL_ConfirmOrder_ID (int UNS_PL_ConfirmOrder_ID)
	{
		if (UNS_PL_ConfirmOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmOrder_ID, Integer.valueOf(UNS_PL_ConfirmOrder_ID));
	}

	/** Get Confirmation Order.
		@return Confirmation Order	  */
	public int getUNS_PL_ConfirmOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ConfirmOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PL_ConfirmOrder_UU.
		@param UNS_PL_ConfirmOrder_UU UNS_PL_ConfirmOrder_UU	  */
	public void setUNS_PL_ConfirmOrder_UU (String UNS_PL_ConfirmOrder_UU)
	{
		set_Value (COLUMNNAME_UNS_PL_ConfirmOrder_UU, UNS_PL_ConfirmOrder_UU);
	}

	/** Get UNS_PL_ConfirmOrder_UU.
		@return UNS_PL_ConfirmOrder_UU	  */
	public String getUNS_PL_ConfirmOrder_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ConfirmOrder_UU);
	}

	public com.unicore.model.I_UNS_PL_ReturnOrder getUNS_PL_ReturnOrder() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PL_ReturnOrder)MTable.get(getCtx(), com.unicore.model.I_UNS_PL_ReturnOrder.Table_Name)
			.getPO(getUNS_PL_ReturnOrder_ID(), get_TrxName());	}

	/** Set Canceled Shipment.
		@param UNS_PL_ReturnOrder_ID Canceled Shipment	  */
	public void setUNS_PL_ReturnOrder_ID (int UNS_PL_ReturnOrder_ID)
	{
		if (UNS_PL_ReturnOrder_ID < 1) 
			set_Value (COLUMNNAME_UNS_PL_ReturnOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PL_ReturnOrder_ID, Integer.valueOf(UNS_PL_ReturnOrder_ID));
	}

	/** Get Canceled Shipment.
		@return Canceled Shipment	  */
	public int getUNS_PL_ReturnOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ReturnOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}