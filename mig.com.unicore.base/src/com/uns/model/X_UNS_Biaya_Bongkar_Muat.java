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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Biaya_Bongkar_Muat
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Biaya_Bongkar_Muat extends PO implements I_UNS_Biaya_Bongkar_Muat, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130621L;

    /** Standard Constructor */
    public X_UNS_Biaya_Bongkar_Muat (Properties ctx, int UNS_Biaya_Bongkar_Muat_ID, String trxName)
    {
      super (ctx, UNS_Biaya_Bongkar_Muat_ID, trxName);
      /** if (UNS_Biaya_Bongkar_Muat_ID == 0)
        {
			setC_UOM_ID (0);
			setLoadingManual (Env.ZERO);
// 0
			setLoadingWithCrane (Env.ZERO);
// 0
			setLoadingWithForklift (Env.ZERO);
// 0
			setM_Product_ID (0);
			setName (null);
			setUnloadingManual (Env.ZERO);
// 0
			setUnloadingWithCrane (Env.ZERO);
// 0
			setUnloadingWithForklift (Env.ZERO);
// 0
			setUNS_Biaya_Bongkar_Muat_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_Biaya_Bongkar_Muat (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_Biaya_Bongkar_Muat[")
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

	/** Set Loading Manual.
		@param LoadingManual 
		Loading Manual (by hand)
	  */
	public void setLoadingManual (BigDecimal LoadingManual)
	{
		set_Value (COLUMNNAME_LoadingManual, LoadingManual);
	}

	/** Get Loading Manual.
		@return Loading Manual (by hand)
	  */
	public BigDecimal getLoadingManual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoadingManual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Loading w/ Crane.
		@param LoadingWithCrane 
		Loading w/ Crane
	  */
	public void setLoadingWithCrane (BigDecimal LoadingWithCrane)
	{
		set_Value (COLUMNNAME_LoadingWithCrane, LoadingWithCrane);
	}

	/** Get Loading w/ Crane.
		@return Loading w/ Crane
	  */
	public BigDecimal getLoadingWithCrane () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoadingWithCrane);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Loading w/ Forklift.
		@param LoadingWithForklift 
		Loading w/ Forklift
	  */
	public void setLoadingWithForklift (BigDecimal LoadingWithForklift)
	{
		set_Value (COLUMNNAME_LoadingWithForklift, LoadingWithForklift);
	}

	/** Get Loading w/ Forklift.
		@return Loading w/ Forklift
	  */
	public BigDecimal getLoadingWithForklift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoadingWithForklift);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Unloading Manual.
		@param UnloadingManual 
		Unloading Manual (by hand)
	  */
	public void setUnloadingManual (BigDecimal UnloadingManual)
	{
		set_Value (COLUMNNAME_UnloadingManual, UnloadingManual);
	}

	/** Get Unloading Manual.
		@return Unloading Manual (by hand)
	  */
	public BigDecimal getUnloadingManual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnloadingManual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unloading w/ Crane.
		@param UnloadingWithCrane 
		Unloading w/ Crane
	  */
	public void setUnloadingWithCrane (BigDecimal UnloadingWithCrane)
	{
		set_Value (COLUMNNAME_UnloadingWithCrane, UnloadingWithCrane);
	}

	/** Get Unloading w/ Crane.
		@return Unloading w/ Crane
	  */
	public BigDecimal getUnloadingWithCrane () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnloadingWithCrane);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unloading w/ Forklift.
		@param UnloadingWithForklift 
		Unloading w/ Forklift
	  */
	public void setUnloadingWithForklift (BigDecimal UnloadingWithForklift)
	{
		set_Value (COLUMNNAME_UnloadingWithForklift, UnloadingWithForklift);
	}

	/** Get Unloading w/ Forklift.
		@return Unloading w/ Forklift
	  */
	public BigDecimal getUnloadingWithForklift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnloadingWithForklift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Biaya Bongkar Muat.
		@param UNS_Biaya_Bongkar_Muat_ID Biaya Bongkar Muat	  */
	public void setUNS_Biaya_Bongkar_Muat_ID (int UNS_Biaya_Bongkar_Muat_ID)
	{
		if (UNS_Biaya_Bongkar_Muat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Biaya_Bongkar_Muat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Biaya_Bongkar_Muat_ID, Integer.valueOf(UNS_Biaya_Bongkar_Muat_ID));
	}

	/** Get Biaya Bongkar Muat.
		@return Biaya Bongkar Muat	  */
	public int getUNS_Biaya_Bongkar_Muat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Biaya_Bongkar_Muat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Biaya_Bongkar_Muat_UU.
		@param UNS_Biaya_Bongkar_Muat_UU UNS_Biaya_Bongkar_Muat_UU	  */
	public void setUNS_Biaya_Bongkar_Muat_UU (String UNS_Biaya_Bongkar_Muat_UU)
	{
		set_Value (COLUMNNAME_UNS_Biaya_Bongkar_Muat_UU, UNS_Biaya_Bongkar_Muat_UU);
	}

	/** Get UNS_Biaya_Bongkar_Muat_UU.
		@return UNS_Biaya_Bongkar_Muat_UU	  */
	public String getUNS_Biaya_Bongkar_Muat_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Biaya_Bongkar_Muat_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}