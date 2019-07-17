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

/** Generated Model for UNS_ShippingInctvSch_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ShippingInctvSch_Line extends PO implements I_UNS_ShippingInctvSch_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170826L;

    /** Standard Constructor */
    public X_UNS_ShippingInctvSch_Line (Properties ctx, int UNS_ShippingInctvSch_Line_ID, String trxName)
    {
      super (ctx, UNS_ShippingInctvSch_Line_ID, trxName);
      /** if (UNS_ShippingInctvSch_Line_ID == 0)
        {
			setHelperValue (Env.ZERO);
// 0
			setIncentiveType (null);
			setUNS_ShippingInctvSch_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ShippingInctvSch_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ShippingInctvSch_Line[")
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

	/** Set Date Incentive.
		@param DateIncentive Date Incentive	  */
	public void setDateIncentive (Timestamp DateIncentive)
	{
		set_Value (COLUMNNAME_DateIncentive, DateIncentive);
	}

	/** Get Date Incentive.
		@return Date Incentive	  */
	public Timestamp getDateIncentive () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateIncentive);
	}

	public org.compiere.model.I_C_City getDestination() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getDestination_ID(), get_TrxName());	}

	/** Set Destination.
		@param Destination_ID Destination	  */
	public void setDestination_ID (int Destination_ID)
	{
		if (Destination_ID < 1) 
			set_Value (COLUMNNAME_Destination_ID, null);
		else 
			set_Value (COLUMNNAME_Destination_ID, Integer.valueOf(Destination_ID));
	}

	/** Get Destination.
		@return Destination	  */
	public int getDestination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Destination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Helper Value.
		@param HelperValue Helper Value	  */
	public void setHelperValue (BigDecimal HelperValue)
	{
		set_Value (COLUMNNAME_HelperValue, HelperValue);
	}

	/** Get Helper Value.
		@return Helper Value	  */
	public BigDecimal getHelperValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HelperValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Incentive Deduction = DD */
	public static final String INCENTIVETYPE_IncentiveDeduction = "DD";
	/** Daily Incentive = DL */
	public static final String INCENTIVETYPE_DailyIncentive = "DL";
	/** Outlet Incentive = OT */
	public static final String INCENTIVETYPE_OutletIncentive = "OT";
	/** Rittase = RT */
	public static final String INCENTIVETYPE_Rittase = "RT";
	/** City To City = CC */
	public static final String INCENTIVETYPE_CityToCity = "CC";
	/** Rayon = RY */
	public static final String INCENTIVETYPE_Rayon = "RY";
	/** Set Incentive Type.
		@param IncentiveType 
		Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType)
	{

		set_Value (COLUMNNAME_IncentiveType, IncentiveType);
	}

	/** Get Incentive Type.
		@return Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType () 
	{
		return (String)get_Value(COLUMNNAME_IncentiveType);
	}

	/** Set Incentive Value.
		@param IncentiveValue Incentive Value	  */
	public void setIncentiveValue (int IncentiveValue)
	{
		set_Value (COLUMNNAME_IncentiveValue, Integer.valueOf(IncentiveValue));
	}

	/** Get Incentive Value.
		@return Incentive Value	  */
	public int getIncentiveValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncentiveValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 1 = 1 */
	public static final String MINRITASE_1 = "1";
	/** 2 = 2 */
	public static final String MINRITASE_2 = "2";
	/** 3 = 3 */
	public static final String MINRITASE_3 = "3";
	/** 4 = 4 */
	public static final String MINRITASE_4 = "4";
	/** 5 = 5 */
	public static final String MINRITASE_5 = "5";
	/** 6 = 6 */
	public static final String MINRITASE_6 = "6";
	/** 7 = 7 */
	public static final String MINRITASE_7 = "7";
	/** 8 = 8 */
	public static final String MINRITASE_8 = "8";
	/** 9 = 9 */
	public static final String MINRITASE_9 = "9";
	/** Set Minimum Ritase.
		@param minRitase Minimum Ritase	  */
	public void setminRitase (String minRitase)
	{

		set_Value (COLUMNNAME_minRitase, minRitase);
	}

	/** Get Minimum Ritase.
		@return Minimum Ritase	  */
	public String getminRitase () 
	{
		return (String)get_Value(COLUMNNAME_minRitase);
	}

	public org.compiere.model.I_C_City getOrigin() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getOrigin_ID(), get_TrxName());	}

	/** Set Origin.
		@param Origin_ID Origin	  */
	public void setOrigin_ID (int Origin_ID)
	{
		if (Origin_ID < 1) 
			set_Value (COLUMNNAME_Origin_ID, null);
		else 
			set_Value (COLUMNNAME_Origin_ID, Integer.valueOf(Origin_ID));
	}

	/** Get Origin.
		@return Origin	  */
	public int getOrigin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Origin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Other reason = NA */
	public static final String REASON_OtherReason = "NA";
	/** Toko tidak ditemukan = TK */
	public static final String REASON_TokoTidakDitemukan = "TK";
	/** Tutup = TP */
	public static final String REASON_Tutup = "TP";
	/** Terlambat = TR */
	public static final String REASON_Terlambat = "TR";
	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{

		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

	/** Set Rayon.
		@param UNS_Rayon_ID Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID)
	{
		if (UNS_Rayon_ID < 1) 
			set_Value (COLUMNNAME_UNS_Rayon_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Rayon_ID, Integer.valueOf(UNS_Rayon_ID));
	}

	/** Get Rayon.
		@return Rayon	  */
	public int getUNS_Rayon_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Rayon_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Incentive Schema ID.
		@param UNS_ShippingIncentiveSch_ID Shipping Incentive Schema ID	  */
	public void setUNS_ShippingIncentiveSch_ID (int UNS_ShippingIncentiveSch_ID)
	{
		if (UNS_ShippingIncentiveSch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingIncentiveSch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingIncentiveSch_ID, Integer.valueOf(UNS_ShippingIncentiveSch_ID));
	}

	/** Get Shipping Incentive Schema ID.
		@return Shipping Incentive Schema ID	  */
	public int getUNS_ShippingIncentiveSch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingIncentiveSch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Incentive Schema Line ID.
		@param UNS_ShippingInctvSch_Line_ID Shipping Incentive Schema Line ID	  */
	public void setUNS_ShippingInctvSch_Line_ID (int UNS_ShippingInctvSch_Line_ID)
	{
		if (UNS_ShippingInctvSch_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingInctvSch_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingInctvSch_Line_ID, Integer.valueOf(UNS_ShippingInctvSch_Line_ID));
	}

	/** Get Shipping Incentive Schema Line ID.
		@return Shipping Incentive Schema Line ID	  */
	public int getUNS_ShippingInctvSch_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingInctvSch_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Incentive Schema Line UU.
		@param UNS_ShippingInctvSch_Line_UU Shipping Incentive Schema Line UU	  */
	public void setUNS_ShippingInctvSch_Line_UU (String UNS_ShippingInctvSch_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ShippingInctvSch_Line_UU, UNS_ShippingInctvSch_Line_UU);
	}

	/** Get Shipping Incentive Schema Line UU.
		@return Shipping Incentive Schema Line UU	  */
	public String getUNS_ShippingInctvSch_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ShippingInctvSch_Line_UU);
	}
}