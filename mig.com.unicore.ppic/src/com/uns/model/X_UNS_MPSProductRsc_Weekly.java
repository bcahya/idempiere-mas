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

/** Generated Model for UNS_MPSProductRsc_Weekly
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MPSProductRsc_Weekly extends PO implements I_UNS_MPSProductRsc_Weekly, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130823L;

    /** Standard Constructor */
    public X_UNS_MPSProductRsc_Weekly (Properties ctx, int UNS_MPSProductRsc_Weekly_ID, String trxName)
    {
      super (ctx, UNS_MPSProductRsc_Weekly_ID, trxName);
      /** if (UNS_MPSProductRsc_Weekly_ID == 0)
        {
			setActualProduced (Env.ZERO);
// 0
			setActualScheduledMT (Env.ZERO);
// 0
			setActualScheduledUOM (Env.ZERO);
// 0
			setUNS_MPSProductRsc_Weekly_ID (0);
			setWeekNo (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MPSProductRsc_Weekly (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MPSProductRsc_Weekly[")
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Date.
		@param MPSDate Date	  */
	public void setMPSDate (Timestamp MPSDate)
	{
		set_Value (COLUMNNAME_MPSDate, MPSDate);
	}

	/** Get Date.
		@return Date	  */
	public Timestamp getMPSDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MPSDate);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	public com.uns.model.I_UNS_MPSProduct_Resource getUNS_MPSProduct_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSProduct_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_MPSProduct_Resource.Table_Name)
			.getPO(getUNS_MPSProduct_Resource_ID(), get_TrxName());	}

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

	/** Set MPS Product Resource Weekly.
		@param UNS_MPSProductRsc_Weekly_ID MPS Product Resource Weekly	  */
	public void setUNS_MPSProductRsc_Weekly_ID (int UNS_MPSProductRsc_Weekly_ID)
	{
		if (UNS_MPSProductRsc_Weekly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProductRsc_Weekly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProductRsc_Weekly_ID, Integer.valueOf(UNS_MPSProductRsc_Weekly_ID));
	}

	/** Get MPS Product Resource Weekly.
		@return MPS Product Resource Weekly	  */
	public int getUNS_MPSProductRsc_Weekly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProductRsc_Weekly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MPSProductRsc_Weekly_UU.
		@param UNS_MPSProductRsc_Weekly_UU UNS_MPSProductRsc_Weekly_UU	  */
	public void setUNS_MPSProductRsc_Weekly_UU (String UNS_MPSProductRsc_Weekly_UU)
	{
		set_Value (COLUMNNAME_UNS_MPSProductRsc_Weekly_UU, UNS_MPSProductRsc_Weekly_UU);
	}

	/** Get UNS_MPSProductRsc_Weekly_UU.
		@return UNS_MPSProductRsc_Weekly_UU	  */
	public String getUNS_MPSProductRsc_Weekly_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MPSProductRsc_Weekly_UU);
	}

	/** Set Weekly.
		@param Weekly_ID Weekly	  */
	public void setWeekly_ID (int Weekly_ID)
	{
		if (Weekly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Weekly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Weekly_ID, Integer.valueOf(Weekly_ID));
	}

	/** Get Weekly.
		@return Weekly	  */
	public int getWeekly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Weekly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Week No.
		@param WeekNo Week No	  */
	public void setWeekNo (int WeekNo)
	{
		set_Value (COLUMNNAME_WeekNo, Integer.valueOf(WeekNo));
	}

	/** Get Week No.
		@return Week No	  */
	public int getWeekNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeekNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}