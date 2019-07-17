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

/** Generated Model for UNS_TrxWeeklyStock_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_TrxWeeklyStock_Line extends PO implements I_UNS_TrxWeeklyStock_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170417L;

    /** Standard Constructor */
    public X_UNS_TrxWeeklyStock_Line (Properties ctx, int UNS_TrxWeeklyStock_Line_ID, String trxName)
    {
      super (ctx, UNS_TrxWeeklyStock_Line_ID, trxName);
      /** if (UNS_TrxWeeklyStock_Line_ID == 0)
        {
			setC_UOM_ID (0);
			setIsInTransit (false);
// N
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setMovementQtyIn (Env.ZERO);
// 0
			setMovementQtyOut (Env.ZERO);
// 0
			setOtherQtyIn (Env.ZERO);
// 0
			setOtherQtyOut (Env.ZERO);
// 0
			setProductionQtyIn (Env.ZERO);
// 0
			setProductionQtyOut (Env.ZERO);
// 0
			setQtyDelivered (Env.ZERO);
// 0
			setQtyOnDate (Env.ZERO);
// 0
			setQtyOnHand (Env.ZERO);
// 0
			setQtyPacked (Env.ZERO);
// 0
			setQtyReceipt (Env.ZERO);
// 0
			setQtyReturnC (Env.ZERO);
// 0
			setQtyReturnV (Env.ZERO);
// 0
			setQtySold (Env.ZERO);
// 0
			setTransactionQty (Env.ZERO);
// 0
			setUNS_TrxWeeklyStock_ID (0);
			setUNS_TrxWeeklyStock_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_TrxWeeklyStock_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_TrxWeeklyStock_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set In Transit.
		@param IsInTransit 
		Movement is in transit
	  */
	public void setIsInTransit (boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, Boolean.valueOf(IsInTransit));
	}

	/** Get In Transit.
		@return Movement is in transit
	  */
	public boolean isInTransit () 
	{
		Object oo = get_Value(COLUMNNAME_IsInTransit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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

	/** Set Movement Quantity In.
		@param MovementQtyIn 
		Quantity of a product moved.
	  */
	public void setMovementQtyIn (BigDecimal MovementQtyIn)
	{
		set_Value (COLUMNNAME_MovementQtyIn, MovementQtyIn);
	}

	/** Get Movement Quantity In.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQtyIn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQtyIn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Quantity Out.
		@param MovementQtyOut 
		Quantity of a product moved.
	  */
	public void setMovementQtyOut (BigDecimal MovementQtyOut)
	{
		set_Value (COLUMNNAME_MovementQtyOut, MovementQtyOut);
	}

	/** Get Movement Quantity Out.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQtyOut () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQtyOut);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Other Qty In.
		@param OtherQtyIn Other Qty In	  */
	public void setOtherQtyIn (BigDecimal OtherQtyIn)
	{
		set_Value (COLUMNNAME_OtherQtyIn, OtherQtyIn);
	}

	/** Get Other Qty In.
		@return Other Qty In	  */
	public BigDecimal getOtherQtyIn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OtherQtyIn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Other Qty Out.
		@param OtherQtyOut Other Qty Out	  */
	public void setOtherQtyOut (BigDecimal OtherQtyOut)
	{
		set_Value (COLUMNNAME_OtherQtyOut, OtherQtyOut);
	}

	/** Get Other Qty Out.
		@return Other Qty Out	  */
	public BigDecimal getOtherQtyOut () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OtherQtyOut);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Production Qty In.
		@param ProductionQtyIn Production Qty In	  */
	public void setProductionQtyIn (BigDecimal ProductionQtyIn)
	{
		set_Value (COLUMNNAME_ProductionQtyIn, ProductionQtyIn);
	}

	/** Get Production Qty In.
		@return Production Qty In	  */
	public BigDecimal getProductionQtyIn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQtyIn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Production Qty Out.
		@param ProductionQtyOut Production Qty Out	  */
	public void setProductionQtyOut (BigDecimal ProductionQtyOut)
	{
		set_Value (COLUMNNAME_ProductionQtyOut, ProductionQtyOut);
	}

	/** Get Production Qty Out.
		@return Production Qty Out	  */
	public BigDecimal getProductionQtyOut () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQtyOut);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty On Date.
		@param QtyOnDate Qty On Date	  */
	public void setQtyOnDate (BigDecimal QtyOnDate)
	{
		set_Value (COLUMNNAME_QtyOnDate, QtyOnDate);
	}

	/** Get Qty On Date.
		@return Qty On Date	  */
	public BigDecimal getQtyOnDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnDate);
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
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
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

	/** Set Quantity Packed.
		@param QtyPacked Quantity Packed	  */
	public void setQtyPacked (BigDecimal QtyPacked)
	{
		set_Value (COLUMNNAME_QtyPacked, QtyPacked);
	}

	/** Get Quantity Packed.
		@return Quantity Packed	  */
	public BigDecimal getQtyPacked () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPacked);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Receipt.
		@param QtyReceipt Qty Receipt	  */
	public void setQtyReceipt (BigDecimal QtyReceipt)
	{
		set_Value (COLUMNNAME_QtyReceipt, QtyReceipt);
	}

	/** Get Qty Receipt.
		@return Qty Receipt	  */
	public BigDecimal getQtyReceipt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReceipt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Return Customer.
		@param QtyReturnC Return Customer	  */
	public void setQtyReturnC (BigDecimal QtyReturnC)
	{
		set_Value (COLUMNNAME_QtyReturnC, QtyReturnC);
	}

	/** Get Return Customer.
		@return Return Customer	  */
	public BigDecimal getQtyReturnC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReturnC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Return Vendor.
		@param QtyReturnV Return Vendor	  */
	public void setQtyReturnV (BigDecimal QtyReturnV)
	{
		set_Value (COLUMNNAME_QtyReturnV, QtyReturnV);
	}

	/** Get Return Vendor.
		@return Return Vendor	  */
	public BigDecimal getQtyReturnV () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReturnV);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Sold.
		@param QtySold Quantity Sold	  */
	public void setQtySold (BigDecimal QtySold)
	{
		set_Value (COLUMNNAME_QtySold, QtySold);
	}

	/** Get Quantity Sold.
		@return Quantity Sold	  */
	public BigDecimal getQtySold () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySold);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_TrxWeeklyStock_Line getReference() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_TrxWeeklyStock_Line)MTable.get(getCtx(), com.unicore.model.I_UNS_TrxWeeklyStock_Line.Table_Name)
			.getPO(getReference_ID(), get_TrxName());	}

	/** Set Refrerence Record.
		@param Reference_ID Refrerence Record	  */
	public void setReference_ID (int Reference_ID)
	{
		if (Reference_ID < 1) 
			set_Value (COLUMNNAME_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_Reference_ID, Integer.valueOf(Reference_ID));
	}

	/** Get Refrerence Record.
		@return Refrerence Record	  */
	public int getReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Qty.
		@param TransactionQty Transaction Qty	  */
	public void setTransactionQty (BigDecimal TransactionQty)
	{
		set_Value (COLUMNNAME_TransactionQty, TransactionQty);
	}

	/** Get Transaction Qty.
		@return Transaction Qty	  */
	public BigDecimal getTransactionQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TransactionQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_TrxWeeklyStock getUNS_TrxWeeklyStock() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_TrxWeeklyStock)MTable.get(getCtx(), com.unicore.model.I_UNS_TrxWeeklyStock.Table_Name)
			.getPO(getUNS_TrxWeeklyStock_ID(), get_TrxName());	}

	/** Set Transaction Weekly Stock.
		@param UNS_TrxWeeklyStock_ID Transaction Weekly Stock	  */
	public void setUNS_TrxWeeklyStock_ID (int UNS_TrxWeeklyStock_ID)
	{
		if (UNS_TrxWeeklyStock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_ID, Integer.valueOf(UNS_TrxWeeklyStock_ID));
	}

	/** Get Transaction Weekly Stock.
		@return Transaction Weekly Stock	  */
	public int getUNS_TrxWeeklyStock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Weekly Line.
		@param UNS_TrxWeeklyStock_Line_ID Transaction Weekly Line	  */
	public void setUNS_TrxWeeklyStock_Line_ID (int UNS_TrxWeeklyStock_Line_ID)
	{
		if (UNS_TrxWeeklyStock_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Line_ID, Integer.valueOf(UNS_TrxWeeklyStock_Line_ID));
	}

	/** Get Transaction Weekly Line.
		@return Transaction Weekly Line	  */
	public int getUNS_TrxWeeklyStock_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_TrxWeeklyStock_Line_UU.
		@param UNS_TrxWeeklyStock_Line_UU UNS_TrxWeeklyStock_Line_UU	  */
	public void setUNS_TrxWeeklyStock_Line_UU (String UNS_TrxWeeklyStock_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Line_UU, UNS_TrxWeeklyStock_Line_UU);
	}

	/** Get UNS_TrxWeeklyStock_Line_UU.
		@return UNS_TrxWeeklyStock_Line_UU	  */
	public String getUNS_TrxWeeklyStock_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_Line_UU);
	}
}