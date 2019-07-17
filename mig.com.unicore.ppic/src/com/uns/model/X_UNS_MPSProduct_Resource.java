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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_MPSProduct_Resource
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MPSProduct_Resource extends PO implements I_UNS_MPSProduct_Resource, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130823L;

    /** Standard Constructor */
    public X_UNS_MPSProduct_Resource (Properties ctx, int UNS_MPSProduct_Resource_ID, String trxName)
    {
      super (ctx, UNS_MPSProduct_Resource_ID, trxName);
      /** if (UNS_MPSProduct_Resource_ID == 0)
        {
			setActualProduced (Env.ZERO);
// 0
			setActualScheduledMT (Env.ZERO);
// 0
			setActualScheduledUOM (Env.ZERO);
// 0
			setUNS_MPSProduct_ID (0);
			setUNS_MPSProduct_Resource_ID (0);
			setUNS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MPSProduct_Resource (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MPSProduct_Resource[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Total Actual Manufactured.
		@param ActualProduced 
		Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced)
	{
		set_ValueNoCheck (COLUMNNAME_ActualProduced, ActualProduced);
	}

	/** Get Total Actual Manufactured.
		@return Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualProduced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Actual Scheduled (MT).
		@param ActualScheduledMT 
		Total Actual Scheduled (MT)
	  */
	public void setActualScheduledMT (BigDecimal ActualScheduledMT)
	{
		set_ValueNoCheck (COLUMNNAME_ActualScheduledMT, ActualScheduledMT);
	}

	/** Get Total Actual Scheduled (MT).
		@return Total Actual Scheduled (MT)
	  */
	public BigDecimal getActualScheduledMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Actual Scheduled (UOM).
		@param ActualScheduledUOM 
		Total Actual Scheduled (UOM)
	  */
	public void setActualScheduledUOM (BigDecimal ActualScheduledUOM)
	{
		set_ValueNoCheck (COLUMNNAME_ActualScheduledUOM, ActualScheduledUOM);
	}

	/** Get Total Actual Scheduled (UOM).
		@return Total Actual Scheduled (UOM)
	  */
	public BigDecimal getActualScheduledUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual To Order (MT).
		@param ActualToOrderMT 
		Actual To Order (MT)
	  */
	public void setActualToOrderMT (BigDecimal ActualToOrderMT)
	{
		set_Value (COLUMNNAME_ActualToOrderMT, ActualToOrderMT);
	}

	/** Get Actual To Order (MT).
		@return Actual To Order (MT)
	  */
	public BigDecimal getActualToOrderMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual To Order (UOM).
		@param ActualToOrderUOM 
		Actual To Order (UOM)
	  */
	public void setActualToOrderUOM (BigDecimal ActualToOrderUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderUOM, ActualToOrderUOM);
	}

	/** Get Actual To Order (UOM).
		@return Actual To Order (UOM)
	  */
	public BigDecimal getActualToOrderUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Master Scheduled (MT).
		@param ActualToStockMT 
		Master Scheduled (MT)
	  */
	public void setActualToStockMT (BigDecimal ActualToStockMT)
	{
		set_Value (COLUMNNAME_ActualToStockMT, ActualToStockMT);
	}

	/** Get Master Scheduled (MT).
		@return Master Scheduled (MT)
	  */
	public BigDecimal getActualToStockMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToStockMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Master Scheduled (UOM).
		@param ActualToStockUOM 
		Master Scheduled (UOM)
	  */
	public void setActualToStockUOM (BigDecimal ActualToStockUOM)
	{
		set_Value (COLUMNNAME_ActualToStockUOM, ActualToStockUOM);
	}

	/** Get Master Scheduled (UOM).
		@return Master Scheduled (UOM)
	  */
	public BigDecimal getActualToStockUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToStockUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_MPSProduct getUNS_MPSProduct() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSProduct)MTable.get(getCtx(), com.uns.model.I_UNS_MPSProduct.Table_Name)
			.getPO(getUNS_MPSProduct_ID(), get_TrxName());	}

	/** Set MPS Product.
		@param UNS_MPSProduct_ID MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID)
	{
		if (UNS_MPSProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_ID, Integer.valueOf(UNS_MPSProduct_ID));
	}

	/** Get MPS Product.
		@return MPS Product	  */
	public int getUNS_MPSProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MPS Product Resource.
		@param UNS_MPSProduct_Resource_ID MPS Product Resource	  */
	public void setUNS_MPSProduct_Resource_ID (int UNS_MPSProduct_Resource_ID)
	{
		if (UNS_MPSProduct_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_Resource_ID, Integer.valueOf(UNS_MPSProduct_Resource_ID));
	}

	/** Get MPS Product Resource.
		@return MPS Product Resource	  */
	public int getUNS_MPSProduct_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProduct_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MPSProduct_resource_UU.
		@param UNS_MPSProduct_resource_UU UNS_MPSProduct_resource_UU	  */
	public void setUNS_MPSProduct_resource_UU (String UNS_MPSProduct_resource_UU)
	{
		set_Value (COLUMNNAME_UNS_MPSProduct_resource_UU, UNS_MPSProduct_resource_UU);
	}

	/** Get UNS_MPSProduct_resource_UU.
		@return UNS_MPSProduct_resource_UU	  */
	public String getUNS_MPSProduct_resource_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MPSProduct_resource_UU);
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}