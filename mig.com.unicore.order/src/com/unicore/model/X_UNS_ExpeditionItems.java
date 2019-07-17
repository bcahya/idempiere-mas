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

/** Generated Model for UNS_ExpeditionItems
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ExpeditionItems extends PO implements I_UNS_ExpeditionItems, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170228L;

    /** Standard Constructor */
    public X_UNS_ExpeditionItems (Properties ctx, int UNS_ExpeditionItems_ID, String trxName)
    {
      super (ctx, UNS_ExpeditionItems_ID, trxName);
      /** if (UNS_ExpeditionItems_ID == 0)
        {
			setC_UOM_ID (0);
			setIsManual (true);
// Y
			setProductName (null);
			setQty (Env.ZERO);
// 0
			setUNS_ExpeditionItems_ID (0);
			setUNS_ExpeditionSign_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ExpeditionItems (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ExpeditionItems[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Product Name.
		@param ProductName 
		Name of the Product
	  */
	public void setProductName (String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	/** Get Product Name.
		@return Name of the Product
	  */
	public String getProductName () 
	{
		return (String)get_Value(COLUMNNAME_ProductName);
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

	/** Set Expedition Items.
		@param UNS_ExpeditionItems_ID Expedition Items	  */
	public void setUNS_ExpeditionItems_ID (int UNS_ExpeditionItems_ID)
	{
		if (UNS_ExpeditionItems_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionItems_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionItems_ID, Integer.valueOf(UNS_ExpeditionItems_ID));
	}

	/** Get Expedition Items.
		@return Expedition Items	  */
	public int getUNS_ExpeditionItems_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionItems_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ExpeditionItems_UU.
		@param UNS_ExpeditionItems_UU UNS_ExpeditionItems_UU	  */
	public void setUNS_ExpeditionItems_UU (String UNS_ExpeditionItems_UU)
	{
		set_Value (COLUMNNAME_UNS_ExpeditionItems_UU, UNS_ExpeditionItems_UU);
	}

	/** Get UNS_ExpeditionItems_UU.
		@return UNS_ExpeditionItems_UU	  */
	public String getUNS_ExpeditionItems_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ExpeditionItems_UU);
	}

	public com.unicore.model.I_UNS_ExpeditionSign getUNS_ExpeditionSign() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ExpeditionSign)MTable.get(getCtx(), com.unicore.model.I_UNS_ExpeditionSign.Table_Name)
			.getPO(getUNS_ExpeditionSign_ID(), get_TrxName());	}

	/** Set Expedition Sign.
		@param UNS_ExpeditionSign_ID Expedition Sign	  */
	public void setUNS_ExpeditionSign_ID (int UNS_ExpeditionSign_ID)
	{
		if (UNS_ExpeditionSign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionSign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionSign_ID, Integer.valueOf(UNS_ExpeditionSign_ID));
	}

	/** Get Expedition Sign.
		@return Expedition Sign	  */
	public int getUNS_ExpeditionSign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionSign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}