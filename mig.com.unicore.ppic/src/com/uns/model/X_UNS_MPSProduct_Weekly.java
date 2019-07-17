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

/** Generated Model for UNS_MPSProduct_Weekly
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MPSProduct_Weekly extends PO implements I_UNS_MPSProduct_Weekly, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130928L;

    /** Standard Constructor */
    public X_UNS_MPSProduct_Weekly (Properties ctx, int UNS_MPSProduct_Weekly_ID, String trxName)
    {
      super (ctx, UNS_MPSProduct_Weekly_ID, trxName);
      /** if (UNS_MPSProduct_Weekly_ID == 0)
        {
			setActualToOrderUOM (Env.ZERO);
// 0
			setActualToStockUOM (Env.ZERO);
// 0
			setIncubationDays (0);
// 0
			setQtyMiscUsed (Env.ZERO);
// 0
			setSafetyStock (Env.ZERO);
// 0
			setUNS_MPSProduct_ID (0);
			setUNS_MPSProduct_Weekly_ID (0);
			setWeekNo (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MPSProduct_Weekly (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MPSProduct_Weekly[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual ATP.
		@param AATP 
		Actual Available To Production/Promise
	  */
	public void setAATP (BigDecimal AATP)
	{
		set_ValueNoCheck (COLUMNNAME_AATP, AATP);
	}

	/** Get Actual ATP.
		@return Actual Available To Production/Promise
	  */
	public BigDecimal getAATP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AATP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Projected CATP.
		@param Acc_ATP 
		Projected Cummulated Available To Promise
	  */
	public void setAcc_ATP (BigDecimal Acc_ATP)
	{
		set_Value (COLUMNNAME_Acc_ATP, Acc_ATP);
	}

	/** Get Projected CATP.
		@return Projected Cummulated Available To Promise
	  */
	public BigDecimal getAcc_ATP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Acc_ATP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Available Balances.
		@param ActualAvailableBalances 
		Actual Available Balances
	  */
	public void setActualAvailableBalances (BigDecimal ActualAvailableBalances)
	{
		set_ValueNoCheck (COLUMNNAME_ActualAvailableBalances, ActualAvailableBalances);
	}

	/** Get Actual Available Balances.
		@return Actual Available Balances
	  */
	public BigDecimal getActualAvailableBalances () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualAvailableBalances);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Projected ATP.
		@param ATP 
		Projected Available To Promise
	  */
	public void setATP (BigDecimal ATP)
	{
		set_Value (COLUMNNAME_ATP, ATP);
	}

	/** Get Projected ATP.
		@return Projected Available To Promise
	  */
	public BigDecimal getATP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ATP);
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

	/** Set End Of Incubation.
		@param EndOfIncubation End Of Incubation	  */
	public void setEndOfIncubation (Timestamp EndOfIncubation)
	{
		set_Value (COLUMNNAME_EndOfIncubation, EndOfIncubation);
	}

	/** Get End Of Incubation.
		@return End Of Incubation	  */
	public Timestamp getEndOfIncubation () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndOfIncubation);
	}

	/** Set Incubation Days.
		@param IncubationDays 
		Incubation period of product in days
	  */
	public void setIncubationDays (int IncubationDays)
	{
		set_ValueNoCheck (COLUMNNAME_IncubationDays, Integer.valueOf(IncubationDays));
	}

	/** Get Incubation Days.
		@return Incubation period of product in days
	  */
	public int getIncubationDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncubationDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Projected Available Balances.
		@param ProjectedActualBalance 
		Projected Available Balances
	  */
	public void setProjectedActualBalance (BigDecimal ProjectedActualBalance)
	{
		set_Value (COLUMNNAME_ProjectedActualBalance, ProjectedActualBalance);
	}

	/** Get Projected Available Balances.
		@return Projected Available Balances
	  */
	public BigDecimal getProjectedActualBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProjectedActualBalance);
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

	/** Set Misc. Used Qty.
		@param QtyMiscUsed 
		Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public void setQtyMiscUsed (BigDecimal QtyMiscUsed)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMiscUsed, QtyMiscUsed);
	}

	/** Get Misc. Used Qty.
		@return Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public BigDecimal getQtyMiscUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMiscUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Targeted-F Qty (MT).
		@param QtyMT 
		Quantity of Targeted Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_Value (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Targeted-F Qty (MT).
		@return Quantity of Targeted Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
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

	/** Set Targeted-F Qty (UOM).
		@param QtyUom 
		Quantity of Raw Targeted Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_Value (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Targeted-F Qty (UOM).
		@return Quantity of Raw Targeted Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RSFS.
		@param RSFS 
		Actual Rest Stock For Sales
	  */
	public void setRSFS (BigDecimal RSFS)
	{
		set_Value (COLUMNNAME_RSFS, RSFS);
	}

	/** Get RSFS.
		@return Actual Rest Stock For Sales
	  */
	public BigDecimal getRSFS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RSFS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Safety Stock Qty.
		@param SafetyStock 
		Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock)
	{
		set_ValueNoCheck (COLUMNNAME_SafetyStock, SafetyStock);
	}

	/** Get Safety Stock Qty.
		@return Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SafetyStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Schedule Available Balances.
		@param ScheduledAvaialbleBalances 
		Schedule Available Balances
	  */
	public void setScheduledAvaialbleBalances (BigDecimal ScheduledAvaialbleBalances)
	{
		set_Value (COLUMNNAME_ScheduledAvaialbleBalances, ScheduledAvaialbleBalances);
	}

	/** Get Schedule Available Balances.
		@return Schedule Available Balances
	  */
	public BigDecimal getScheduledAvaialbleBalances () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ScheduledAvaialbleBalances);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SOH.
		@param SOH 
		Scheduled (Stock) On Hand
	  */
	public void setSOH (BigDecimal SOH)
	{
		set_Value (COLUMNNAME_SOH, SOH);
	}

	/** Get SOH.
		@return Scheduled (Stock) On Hand
	  */
	public BigDecimal getSOH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SOH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled RSFS.
		@param SRSFS 
		Schedule Rest Stock For Sales
	  */
	public void setSRSFS (BigDecimal SRSFS)
	{
		set_Value (COLUMNNAME_SRSFS, SRSFS);
	}

	/** Get Scheduled RSFS.
		@return Schedule Rest Stock For Sales
	  */
	public BigDecimal getSRSFS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SRSFS);
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

	/** Set Stock.
		@param Stock Stock	  */
	public void setStock (BigDecimal Stock)
	{
		set_Value (COLUMNNAME_Stock, Stock);
	}

	/** Get Stock.
		@return Stock	  */
	public BigDecimal getStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Stock);
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

	/** Set MPS Product Weekly.
		@param UNS_MPSProduct_Weekly_ID MPS Product Weekly	  */
	public void setUNS_MPSProduct_Weekly_ID (int UNS_MPSProduct_Weekly_ID)
	{
		if (UNS_MPSProduct_Weekly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_Weekly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_Weekly_ID, Integer.valueOf(UNS_MPSProduct_Weekly_ID));
	}

	/** Get MPS Product Weekly.
		@return MPS Product Weekly	  */
	public int getUNS_MPSProduct_Weekly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProduct_Weekly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MPSProduct_Weekly_UU.
		@param UNS_MPSProduct_Weekly_UU UNS_MPSProduct_Weekly_UU	  */
	public void setUNS_MPSProduct_Weekly_UU (String UNS_MPSProduct_Weekly_UU)
	{
		set_Value (COLUMNNAME_UNS_MPSProduct_Weekly_UU, UNS_MPSProduct_Weekly_UU);
	}

	/** Get UNS_MPSProduct_Weekly_UU.
		@return UNS_MPSProduct_Weekly_UU	  */
	public String getUNS_MPSProduct_Weekly_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MPSProduct_Weekly_UU);
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