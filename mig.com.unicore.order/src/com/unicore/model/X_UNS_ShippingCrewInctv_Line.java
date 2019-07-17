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

/** Generated Model for UNS_ShippingCrewInctv_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ShippingCrewInctv_Line extends PO implements I_UNS_ShippingCrewInctv_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170411L;

    /** Standard Constructor */
    public X_UNS_ShippingCrewInctv_Line (Properties ctx, int UNS_ShippingCrewInctv_Line_ID, String trxName)
    {
      super (ctx, UNS_ShippingCrewInctv_Line_ID, trxName);
      /** if (UNS_ShippingCrewInctv_Line_ID == 0)
        {
			setHelperAmount (Env.ZERO);
// 0
			setUNS_ShippingCrewInctv_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ShippingCrewInctv_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ShippingCrewInctv_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Driver = DRV */
	public static final String CREWTYPE_Driver = "DRV";
	/** Helper = HPR */
	public static final String CREWTYPE_Helper = "HPR";
	/** Set Crew Type.
		@param crewTYpe Crew Type	  */
	public void setcrewTYpe (String crewTYpe)
	{

		set_Value (COLUMNNAME_crewTYpe, crewTYpe);
	}

	/** Get Crew Type.
		@return Crew Type	  */
	public String getcrewTYpe () 
	{
		return (String)get_Value(COLUMNNAME_crewTYpe);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Date Shipping.
		@param dateShipping Date Shipping	  */
	public void setdateShipping (Timestamp dateShipping)
	{
		set_Value (COLUMNNAME_dateShipping, dateShipping);
	}

	/** Get Date Shipping.
		@return Date Shipping	  */
	public Timestamp getdateShipping () 
	{
		return (Timestamp)get_Value(COLUMNNAME_dateShipping);
	}

	/** Set Helper Amount.
		@param HelperAmount Helper Amount	  */
	public void setHelperAmount (BigDecimal HelperAmount)
	{
		set_Value (COLUMNNAME_HelperAmount, HelperAmount);
	}

	/** Get Helper Amount.
		@return Helper Amount	  */
	public BigDecimal getHelperAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HelperAmount);
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

	/** Set Shipping Description.
		@param shippingDesc Shipping Description	  */
	public void setshippingDesc (String shippingDesc)
	{
		set_Value (COLUMNNAME_shippingDesc, shippingDesc);
	}

	/** Get Shipping Description.
		@return Shipping Description	  */
	public String getshippingDesc () 
	{
		return (String)get_Value(COLUMNNAME_shippingDesc);
	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UNS_ShippingCrewIncentive getUNS_ShippingCrewIncentive() throws RuntimeException
    {
		return (I_UNS_ShippingCrewIncentive)MTable.get(getCtx(), I_UNS_ShippingCrewIncentive.Table_Name)
			.getPO(getUNS_ShippingCrewIncentive_ID(), get_TrxName());	}

	/** Set Shipping Crew Incentive ID.
		@param UNS_ShippingCrewIncentive_ID Shipping Crew Incentive ID	  */
	public void setUNS_ShippingCrewIncentive_ID (int UNS_ShippingCrewIncentive_ID)
	{
		if (UNS_ShippingCrewIncentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewIncentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewIncentive_ID, Integer.valueOf(UNS_ShippingCrewIncentive_ID));
	}

	/** Get Shipping Crew Incentive ID.
		@return Shipping Crew Incentive ID	  */
	public int getUNS_ShippingCrewIncentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingCrewIncentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Crew Incentive Line ID.
		@param UNS_ShippingCrewInctv_Line_ID Shipping Crew Incentive Line ID	  */
	public void setUNS_ShippingCrewInctv_Line_ID (int UNS_ShippingCrewInctv_Line_ID)
	{
		if (UNS_ShippingCrewInctv_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewInctv_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewInctv_Line_ID, Integer.valueOf(UNS_ShippingCrewInctv_Line_ID));
	}

	/** Get Shipping Crew Incentive Line ID.
		@return Shipping Crew Incentive Line ID	  */
	public int getUNS_ShippingCrewInctv_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingCrewInctv_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Crew Incentive Line UU.
		@param UNS_ShippingCrewInctv_Line_UU Shipping Crew Incentive Line UU	  */
	public void setUNS_ShippingCrewInctv_Line_UU (String UNS_ShippingCrewInctv_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewInctv_Line_UU, UNS_ShippingCrewInctv_Line_UU);
	}

	/** Get Shipping Crew Incentive Line UU.
		@return Shipping Crew Incentive Line UU	  */
	public String getUNS_ShippingCrewInctv_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ShippingCrewInctv_Line_UU);
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

	public I_UNS_ShippingInctvSch_Line getUNS_ShippingInctvSch_Line() throws RuntimeException
    {
		return (I_UNS_ShippingInctvSch_Line)MTable.get(getCtx(), I_UNS_ShippingInctvSch_Line.Table_Name)
			.getPO(getUNS_ShippingInctvSch_Line_ID(), get_TrxName());	}

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
}