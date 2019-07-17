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

/** Generated Model for UNS_Forecast_Date
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Forecast_Date extends PO implements I_UNS_Forecast_Date, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130720L;

    /** Standard Constructor */
    public X_UNS_Forecast_Date (Properties ctx, int UNS_Forecast_Date_ID, String trxName)
    {
      super (ctx, UNS_Forecast_Date_ID, trxName);
      /** if (UNS_Forecast_Date_ID == 0)
        {
			setC_UOM_ID (0);
			setIsPrimary (false);
// N
			setM_Product_ID (0);
			setUNS_Forecast_Date_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Forecast_Date (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Forecast_Date[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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

	/** Set Targeted-F Qty (UOM).
		@param DecidedQty 
		Quantity Forecast By Targeted
	  */
	public void setDecidedQty (BigDecimal DecidedQty)
	{
		set_Value (COLUMNNAME_DecidedQty, DecidedQty);
	}

	/** Get Targeted-F Qty (UOM).
		@return Quantity Forecast By Targeted
	  */
	public BigDecimal getDecidedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DecidedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Targeted Qty (MT).
		@param DecidedQtyMT Targeted Qty (MT)	  */
	public void setDecidedQtyMT (BigDecimal DecidedQtyMT)
	{
		set_Value (COLUMNNAME_DecidedQtyMT, DecidedQtyMT);
	}

	/** Get Targeted Qty (MT).
		@return Targeted Qty (MT)	  */
	public BigDecimal getDecidedQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DecidedQtyMT);
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

	/** Set Expected Cost (IDR).
		@param ExpectedCostIDR Expected Cost (IDR)	  */
	public void setExpectedCostIDR (BigDecimal ExpectedCostIDR)
	{
		set_Value (COLUMNNAME_ExpectedCostIDR, ExpectedCostIDR);
	}

	/** Get Expected Cost (IDR).
		@return Expected Cost (IDR)	  */
	public BigDecimal getExpectedCostIDR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedCostIDR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Cost (SGD).
		@param ExpectedCostSGD Expected Cost (SGD)	  */
	public void setExpectedCostSGD (BigDecimal ExpectedCostSGD)
	{
		set_Value (COLUMNNAME_ExpectedCostSGD, ExpectedCostSGD);
	}

	/** Get Expected Cost (SGD).
		@return Expected Cost (SGD)	  */
	public BigDecimal getExpectedCostSGD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedCostSGD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Cost (USD).
		@param ExpectedCostUSD Expected Cost (USD)	  */
	public void setExpectedCostUSD (BigDecimal ExpectedCostUSD)
	{
		set_Value (COLUMNNAME_ExpectedCostUSD, ExpectedCostUSD);
	}

	/** Get Expected Cost (USD).
		@return Expected Cost (USD)	  */
	public BigDecimal getExpectedCostUSD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedCostUSD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Revenue (IDR).
		@param ExpectedRevenueIDR Expected Revenue (IDR)	  */
	public void setExpectedRevenueIDR (BigDecimal ExpectedRevenueIDR)
	{
		set_Value (COLUMNNAME_ExpectedRevenueIDR, ExpectedRevenueIDR);
	}

	/** Get Expected Revenue (IDR).
		@return Expected Revenue (IDR)	  */
	public BigDecimal getExpectedRevenueIDR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedRevenueIDR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Revenue (SGD).
		@param ExpectedRevenueSGD Expected Revenue (SGD)	  */
	public void setExpectedRevenueSGD (BigDecimal ExpectedRevenueSGD)
	{
		set_Value (COLUMNNAME_ExpectedRevenueSGD, ExpectedRevenueSGD);
	}

	/** Get Expected Revenue (SGD).
		@return Expected Revenue (SGD)	  */
	public BigDecimal getExpectedRevenueSGD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedRevenueSGD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Revenue (USD).
		@param ExpectedRevenueUSD Expected Revenue (USD)	  */
	public void setExpectedRevenueUSD (BigDecimal ExpectedRevenueUSD)
	{
		set_Value (COLUMNNAME_ExpectedRevenueUSD, ExpectedRevenueUSD);
	}

	/** Get Expected Revenue (USD).
		@return Expected Revenue (USD)	  */
	public BigDecimal getExpectedRevenueUSD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedRevenueUSD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forcasted Date.
		@param ForcastedDate Forcasted Date	  */
	public void setForcastedDate (Timestamp ForcastedDate)
	{
		set_Value (COLUMNNAME_ForcastedDate, ForcastedDate);
	}

	/** Get Forcasted Date.
		@return Forcasted Date	  */
	public Timestamp getForcastedDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ForcastedDate);
	}

	/** Set Is Primary Output.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_ValueNoCheck (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Is Primary Output.
		@return Indicates if this is the primary budget
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
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

	/** Set Max. Capaciy.
		@param MaxCaps Max. Capaciy	  */
	public void setMaxCaps (BigDecimal MaxCaps)
	{
		set_Value (COLUMNNAME_MaxCaps, MaxCaps);
	}

	/** Get Max. Capaciy.
		@return Max. Capaciy	  */
	public BigDecimal getMaxCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Forecast_Date getMonthly() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Date)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Date.Table_Name)
			.getPO(getMonthly_ID(), get_TrxName());	}

	/** Set Monthly.
		@param Monthly_ID Monthly	  */
	public void setMonthly_ID (int Monthly_ID)
	{
		if (Monthly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Monthly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Monthly_ID, Integer.valueOf(Monthly_ID));
	}

	/** Get Monthly.
		@return Monthly	  */
	public int getMonthly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Monthly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Optimum Caps.
		@param OptimumCaps Optimum Caps	  */
	public void setOptimumCaps (BigDecimal OptimumCaps)
	{
		set_Value (COLUMNNAME_OptimumCaps, OptimumCaps);
	}

	/** Get Optimum Caps.
		@return Optimum Caps	  */
	public BigDecimal getOptimumCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OptimumCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty MF.
		@param QtyMForecast 
		Qunatity forecast of Marketing by Finish Good Product
	  */
	public void setQtyMForecast (BigDecimal QtyMForecast)
	{
		set_Value (COLUMNNAME_QtyMForecast, QtyMForecast);
	}

	/** Get Qty MF.
		@return Qunatity forecast of Marketing by Finish Good Product
	  */
	public BigDecimal getQtyMForecast () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMForecast);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty MF (MT).
		@param QtyMForecastMT 
		Qunatity forecast of Marketing by Finish Good Product
	  */
	public void setQtyMForecastMT (BigDecimal QtyMForecastMT)
	{
		set_Value (COLUMNNAME_QtyMForecastMT, QtyMForecastMT);
	}

	/** Get Qty MF (MT).
		@return Qunatity forecast of Marketing by Finish Good Product
	  */
	public BigDecimal getQtyMForecastMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMForecastMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty MT.
		@param QtyMT Qty MT	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_Value (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Qty MT.
		@return Qty MT	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty UOM.
		@param QtyUom Qty UOM	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_Value (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Qty UOM.
		@return Qty UOM	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Total Expected Cost (IDR).
		@param TotalExpectedCostIDR Total Expected Cost (IDR)	  */
	public void setTotalExpectedCostIDR (BigDecimal TotalExpectedCostIDR)
	{
		set_Value (COLUMNNAME_TotalExpectedCostIDR, TotalExpectedCostIDR);
	}

	/** Get Total Expected Cost (IDR).
		@return Total Expected Cost (IDR)	  */
	public BigDecimal getTotalExpectedCostIDR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedCostIDR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Expected Cost (SGD).
		@param TotalExpectedCostSGD Total Expected Cost (SGD)	  */
	public void setTotalExpectedCostSGD (BigDecimal TotalExpectedCostSGD)
	{
		set_Value (COLUMNNAME_TotalExpectedCostSGD, TotalExpectedCostSGD);
	}

	/** Get Total Expected Cost (SGD).
		@return Total Expected Cost (SGD)	  */
	public BigDecimal getTotalExpectedCostSGD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedCostSGD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Expected Cost (USD).
		@param TotalExpectedCostUSD Total Expected Cost (USD)	  */
	public void setTotalExpectedCostUSD (BigDecimal TotalExpectedCostUSD)
	{
		set_Value (COLUMNNAME_TotalExpectedCostUSD, TotalExpectedCostUSD);
	}

	/** Get Total Expected Cost (USD).
		@return Total Expected Cost (USD)	  */
	public BigDecimal getTotalExpectedCostUSD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedCostUSD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Expected Revenue (IDR).
		@param TotalExpectedRevenueIDR Total Expected Revenue (IDR)	  */
	public void setTotalExpectedRevenueIDR (BigDecimal TotalExpectedRevenueIDR)
	{
		set_Value (COLUMNNAME_TotalExpectedRevenueIDR, TotalExpectedRevenueIDR);
	}

	/** Get Total Expected Revenue (IDR).
		@return Total Expected Revenue (IDR)	  */
	public BigDecimal getTotalExpectedRevenueIDR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedRevenueIDR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Expected Revenue (SGD).
		@param TotalExpectedRevenueSGD Total Expected Revenue (SGD)	  */
	public void setTotalExpectedRevenueSGD (BigDecimal TotalExpectedRevenueSGD)
	{
		set_Value (COLUMNNAME_TotalExpectedRevenueSGD, TotalExpectedRevenueSGD);
	}

	/** Get Total Expected Revenue (SGD).
		@return Total Expected Revenue (SGD)	  */
	public BigDecimal getTotalExpectedRevenueSGD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedRevenueSGD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Expected Revenue (USD).
		@param TotalExpectedRevenueUSD Total Expected Revenue (USD)	  */
	public void setTotalExpectedRevenueUSD (BigDecimal TotalExpectedRevenueUSD)
	{
		set_Value (COLUMNNAME_TotalExpectedRevenueUSD, TotalExpectedRevenueUSD);
	}

	/** Get Total Expected Revenue (USD).
		@return Total Expected Revenue (USD)	  */
	public BigDecimal getTotalExpectedRevenueUSD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalExpectedRevenueUSD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Date.
		@param UNS_Forecast_Date_ID Forecast Date	  */
	public void setUNS_Forecast_Date_ID (int UNS_Forecast_Date_ID)
	{
		if (UNS_Forecast_Date_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Date_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Date_ID, Integer.valueOf(UNS_Forecast_Date_ID));
	}

	/** Get Forecast Date.
		@return Forecast Date	  */
	public int getUNS_Forecast_Date_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Forecast_Date_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Forecast Date UU.
		@param UNS_Forecast_Date_UU Forecast Date UU	  */
	public void setUNS_Forecast_Date_UU (String UNS_Forecast_Date_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Date_UU, UNS_Forecast_Date_UU);
	}

	/** Get Forecast Date UU.
		@return Forecast Date UU	  */
	public String getUNS_Forecast_Date_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Forecast_Date_UU);
	}

	/** Set Manufacturing Forecast.
		@param UNS_Forecast_Header_ID Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID)
	{
		if (UNS_Forecast_Header_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, Integer.valueOf(UNS_Forecast_Header_ID));
	}

	/** Get Manufacturing Forecast.
		@return Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Forecast_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Forecast_Date getWeekly() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Date)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Date.Table_Name)
			.getPO(getWeekly_ID(), get_TrxName());	}

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

	public com.uns.model.I_UNS_Forecast_Date getYearly() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Date)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Date.Table_Name)
			.getPO(getYearly_ID(), get_TrxName());	}

	/** Set Yearly.
		@param Yearly_ID Yearly	  */
	public void setYearly_ID (int Yearly_ID)
	{
		if (Yearly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Yearly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Yearly_ID, Integer.valueOf(Yearly_ID));
	}

	/** Get Yearly.
		@return Yearly	  */
	public int getYearly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Yearly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}