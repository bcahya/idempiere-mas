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

/** Generated Model for UNS_ExpeditionDetail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ExpeditionDetail extends PO implements I_UNS_ExpeditionDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180517L;

    /** Standard Constructor */
    public X_UNS_ExpeditionDetail (Properties ctx, int UNS_ExpeditionDetail_ID, String trxName)
    {
      super (ctx, UNS_ExpeditionDetail_ID, trxName);
      /** if (UNS_ExpeditionDetail_ID == 0)
        {
			setC_Charge_ID (0);
			setItemDescription (null);
			setPriceActual (Env.ZERO);
// 0
			setPriceLimit (Env.ZERO);
// 0
			setPriceList (Env.ZERO);
// 0
			setQty (Env.ZERO);
// 0
			setUNS_ExpeditionDetail_ID (0);
			setVolume (Env.ZERO);
// 0
			setWeight (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_ExpeditionDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ExpeditionDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Order getC_Order() throws RuntimeException
    {
		return (I_C_Order)MTable.get(getCtx(), I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
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

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Item Description.
		@param ItemDescription Item Description	  */
	public void setItemDescription (String ItemDescription)
	{
		set_Value (COLUMNNAME_ItemDescription, ItemDescription);
	}

	/** Get Item Description.
		@return Item Description	  */
	public String getItemDescription () 
	{
		return (String)get_Value(COLUMNNAME_ItemDescription);
	}

	public com.unicore.model.I_UNS_ExpeditionDetail getJobSODetail() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ExpeditionDetail)MTable.get(getCtx(), com.unicore.model.I_UNS_ExpeditionDetail.Table_Name)
			.getPO(getJobSODetail_ID(), get_TrxName());	}

	/** Set Job SO Detail ID.
		@param JobSODetail_ID Job SO Detail ID	  */
	public void setJobSODetail_ID (int JobSODetail_ID)
	{
		if (JobSODetail_ID < 1) 
			set_Value (COLUMNNAME_JobSODetail_ID, null);
		else 
			set_Value (COLUMNNAME_JobSODetail_ID, Integer.valueOf(JobSODetail_ID));
	}

	/** Get Job SO Detail ID.
		@return Job SO Detail ID	  */
	public int getJobSODetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JobSODetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
    {
		return (org.compiere.model.I_M_Movement)MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
			.getPO(getM_Movement_ID(), get_TrxName());	}

	/** Set Inventory Move.
		@param M_Movement_ID 
		Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Inventory Move.
		@return Movement of Inventory
	  */
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
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

	/** Set Unit Price.
		@param PriceActual 
		Actual Price
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit Price.
		@param PriceLimit 
		Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Limit Price.
		@return Lowest price for a product
	  */
	public BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
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

	/** Set Tonase.
		@param Tonase 
		Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase)
	{
		set_Value (COLUMNNAME_Tonase, Tonase);
	}

	/** Get Tonase.
		@return Indicate total tonase
	  */
	public BigDecimal getTonase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tonase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_Expedition getUNS_Expedition() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Expedition)MTable.get(getCtx(), com.unicore.model.I_UNS_Expedition.Table_Name)
			.getPO(getUNS_Expedition_ID(), get_TrxName());	}

	/** Set Expedition.
		@param UNS_Expedition_ID Expedition	  */
	public void setUNS_Expedition_ID (int UNS_Expedition_ID)
	{
		if (UNS_Expedition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Expedition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Expedition_ID, Integer.valueOf(UNS_Expedition_ID));
	}

	/** Get Expedition.
		@return Expedition	  */
	public int getUNS_Expedition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Expedition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Expedition Detail.
		@param UNS_ExpeditionDetail_ID Expedition Detail	  */
	public void setUNS_ExpeditionDetail_ID (int UNS_ExpeditionDetail_ID)
	{
		if (UNS_ExpeditionDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetail_ID, Integer.valueOf(UNS_ExpeditionDetail_ID));
	}

	/** Get Expedition Detail.
		@return Expedition Detail	  */
	public int getUNS_ExpeditionDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ExpeditionDetail_UU.
		@param UNS_ExpeditionDetail_UU UNS_ExpeditionDetail_UU	  */
	public void setUNS_ExpeditionDetail_UU (String UNS_ExpeditionDetail_UU)
	{
		set_Value (COLUMNNAME_UNS_ExpeditionDetail_UU, UNS_ExpeditionDetail_UU);
	}

	/** Get UNS_ExpeditionDetail_UU.
		@return UNS_ExpeditionDetail_UU	  */
	public String getUNS_ExpeditionDetail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ExpeditionDetail_UU);
	}

	public com.unicore.model.I_UNS_ExpeditionPath getUNS_ExpeditionPath() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ExpeditionPath)MTable.get(getCtx(), com.unicore.model.I_UNS_ExpeditionPath.Table_Name)
			.getPO(getUNS_ExpeditionPath_ID(), get_TrxName());	}

	/** Set Expedetion Path.
		@param UNS_ExpeditionPath_ID Expedetion Path	  */
	public void setUNS_ExpeditionPath_ID (int UNS_ExpeditionPath_ID)
	{
		if (UNS_ExpeditionPath_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionPath_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionPath_ID, Integer.valueOf(UNS_ExpeditionPath_ID));
	}

	/** Get Expedetion Path.
		@return Expedetion Path	  */
	public int getUNS_ExpeditionPath_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionPath_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_PackingList getUNS_PackingList() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList.Table_Name)
			.getPO(getUNS_PackingList_ID(), get_TrxName());	}

	/** Set Packing List.
		@param UNS_PackingList_ID Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID)
	{
		if (UNS_PackingList_ID < 1) 
			set_Value (COLUMNNAME_UNS_PackingList_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PackingList_ID, Integer.valueOf(UNS_PackingList_ID));
	}

	/** Get Packing List.
		@return Packing List	  */
	public int getUNS_PackingList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}