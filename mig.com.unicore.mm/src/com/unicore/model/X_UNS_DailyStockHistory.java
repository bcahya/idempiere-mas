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

/** Generated Model for UNS_DailyStockHistory
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DailyStockHistory extends PO implements I_UNS_DailyStockHistory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150531L;

    /** Standard Constructor */
    public X_UNS_DailyStockHistory (Properties ctx, int UNS_DailyStockHistory_ID, String trxName)
    {
      super (ctx, UNS_DailyStockHistory_ID, trxName);
      /** if (UNS_DailyStockHistory_ID == 0)
        {
			setEndingStock (Env.ZERO);
// 0
			setInitialBalance (Env.ZERO);
// 0
			setInQty (Env.ZERO);
// 0
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setOutQty (Env.ZERO);
// 0
			setUNS_DailyStockHistory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DailyStockHistory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DailyStockHistory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Ending Stock.
		@param EndingStock Ending Stock	  */
	public void setEndingStock (BigDecimal EndingStock)
	{
		set_Value (COLUMNNAME_EndingStock, EndingStock);
	}

	/** Get Ending Stock.
		@return Ending Stock	  */
	public BigDecimal getEndingStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EndingStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Initial Balance.
		@param InitialBalance Initial Balance	  */
	public void setInitialBalance (BigDecimal InitialBalance)
	{
		set_Value (COLUMNNAME_InitialBalance, InitialBalance);
	}

	/** Get Initial Balance.
		@return Initial Balance	  */
	public BigDecimal getInitialBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InitialBalance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set In Quantity.
		@param InQty In Quantity	  */
	public void setInQty (BigDecimal InQty)
	{
		set_Value (COLUMNNAME_InQty, InQty);
	}

	/** Get In Quantity.
		@return In Quantity	  */
	public BigDecimal getInQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InQty);
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Out Quantity.
		@param OutQty Out Quantity	  */
	public void setOutQty (BigDecimal OutQty)
	{
		set_Value (COLUMNNAME_OutQty, OutQty);
	}

	/** Get Out Quantity.
		@return Out Quantity	  */
	public BigDecimal getOutQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OutQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DailyStockHistory.
		@param UNS_DailyStockHistory_ID DailyStockHistory	  */
	public void setUNS_DailyStockHistory_ID (int UNS_DailyStockHistory_ID)
	{
		if (UNS_DailyStockHistory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyStockHistory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyStockHistory_ID, Integer.valueOf(UNS_DailyStockHistory_ID));
	}

	/** Get DailyStockHistory.
		@return DailyStockHistory	  */
	public int getUNS_DailyStockHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DailyStockHistory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DailyStockHistory_UU.
		@param UNS_DailyStockHistory_UU UNS_DailyStockHistory_UU	  */
	public void setUNS_DailyStockHistory_UU (String UNS_DailyStockHistory_UU)
	{
		set_Value (COLUMNNAME_UNS_DailyStockHistory_UU, UNS_DailyStockHistory_UU);
	}

	/** Get UNS_DailyStockHistory_UU.
		@return UNS_DailyStockHistory_UU	  */
	public String getUNS_DailyStockHistory_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DailyStockHistory_UU);
	}
}