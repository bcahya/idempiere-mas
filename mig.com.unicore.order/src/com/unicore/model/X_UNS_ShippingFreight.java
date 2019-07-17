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

/** Generated Model for UNS_ShippingFreight
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ShippingFreight extends PO implements I_UNS_ShippingFreight, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151018L;

    /** Standard Constructor */
    public X_UNS_ShippingFreight (Properties ctx, int UNS_ShippingFreight_ID, String trxName)
    {
      super (ctx, UNS_ShippingFreight_ID, trxName);
      /** if (UNS_ShippingFreight_ID == 0)
        {
			setDescription (null);
			setFreightType (null);
// PLS
			setProcessed (false);
// N
			setTonase (Env.ZERO);
// 0
			setUNS_ShippingFreight_ID (0);
			setVolume (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_ShippingFreight (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ShippingFreight[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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

	/** Packing List = PLS */
	public static final String FREIGHTTYPE_PackingList = "PLS";
	/** Canvas = CVS */
	public static final String FREIGHTTYPE_Canvas = "CVS";
	/** Material Receipt = MMR */
	public static final String FREIGHTTYPE_MaterialReceipt = "MMR";
	/** Movement = MVM */
	public static final String FREIGHTTYPE_Movement = "MVM";
	/** Expedition = EXP */
	public static final String FREIGHTTYPE_Expedition = "EXP";
	/** Set Freight Type.
		@param FreightType Freight Type	  */
	public void setFreightType (String FreightType)
	{

		set_Value (COLUMNNAME_FreightType, FreightType);
	}

	/** Get Freight Type.
		@return Freight Type	  */
	public String getFreightType () 
	{
		return (String)get_Value(COLUMNNAME_FreightType);
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
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
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
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
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

	/** Set Packing List.
		@param UNS_PackingList_ID Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID)
	{
		if (UNS_PackingList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, Integer.valueOf(UNS_PackingList_ID));
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

	/** Set Freight.
		@param UNS_ShippingFreight_ID Freight	  */
	public void setUNS_ShippingFreight_ID (int UNS_ShippingFreight_ID)
	{
		if (UNS_ShippingFreight_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingFreight_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingFreight_ID, Integer.valueOf(UNS_ShippingFreight_ID));
	}

	/** Get Freight.
		@return Freight	  */
	public int getUNS_ShippingFreight_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingFreight_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ShippingFreight_UU.
		@param UNS_ShippingFreight_UU UNS_ShippingFreight_UU	  */
	public void setUNS_ShippingFreight_UU (String UNS_ShippingFreight_UU)
	{
		set_Value (COLUMNNAME_UNS_ShippingFreight_UU, UNS_ShippingFreight_UU);
	}

	/** Get UNS_ShippingFreight_UU.
		@return UNS_ShippingFreight_UU	  */
	public String getUNS_ShippingFreight_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ShippingFreight_UU);
	}

	/** Set Shipping Document.
		@param UNS_Shipping_ID Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID)
	{
		if (UNS_Shipping_ID < 1) 
			set_Value (COLUMNNAME_UNS_Shipping_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Shipping_ID, Integer.valueOf(UNS_Shipping_ID));
	}

	/** Get Shipping Document.
		@return Shipping Document	  */
	public int getUNS_Shipping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Shipping_ID);
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
		set_ValueNoCheck (COLUMNNAME_Volume, Volume);
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
}