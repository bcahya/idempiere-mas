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

/** Generated Model for UNS_AcvIncentiveByPeriod
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AcvIncentiveByPeriod extends PO implements I_UNS_AcvIncentiveByPeriod, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180126L;

    /** Standard Constructor */
    public X_UNS_AcvIncentiveByPeriod (Properties ctx, int UNS_AcvIncentiveByPeriod_ID, String trxName)
    {
      super (ctx, UNS_AcvIncentiveByPeriod_ID, trxName);
      /** if (UNS_AcvIncentiveByPeriod_ID == 0)
        {
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_AchievedIncentive_ID (0);
			setUNS_AcvIncentiveByPeriod_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_AcvIncentiveByPeriod (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AcvIncentiveByPeriod[")
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

	/** Set Initial Incentive.
		@param InitialIncentive Initial Incentive	  */
	public void setInitialIncentive (String InitialIncentive)
	{
		set_Value (COLUMNNAME_InitialIncentive, InitialIncentive);
	}

	/** Get Initial Incentive.
		@return Initial Incentive	  */
	public String getInitialIncentive () 
	{
		return (String)get_Value(COLUMNNAME_InitialIncentive);
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

	public com.unicore.model.I_UNS_AchievedIncentive getUNS_AchievedIncentive() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_AchievedIncentive)MTable.get(getCtx(), com.unicore.model.I_UNS_AchievedIncentive.Table_Name)
			.getPO(getUNS_AchievedIncentive_ID(), get_TrxName());	}

	/** Set Achieved Incentive.
		@param UNS_AchievedIncentive_ID Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID)
	{
		if (UNS_AchievedIncentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, Integer.valueOf(UNS_AchievedIncentive_ID));
	}

	/** Get Achieved Incentive.
		@return Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AchievedIncentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Achieved Incentive By Period.
		@param UNS_AcvIncentiveByPeriod_ID Achieved Incentive By Period	  */
	public void setUNS_AcvIncentiveByPeriod_ID (int UNS_AcvIncentiveByPeriod_ID)
	{
		if (UNS_AcvIncentiveByPeriod_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AcvIncentiveByPeriod_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AcvIncentiveByPeriod_ID, Integer.valueOf(UNS_AcvIncentiveByPeriod_ID));
	}

	/** Get Achieved Incentive By Period.
		@return Achieved Incentive By Period	  */
	public int getUNS_AcvIncentiveByPeriod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AcvIncentiveByPeriod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AcvIncentiveByPeriod_UU.
		@param UNS_AcvIncentiveByPeriod_UU UNS_AcvIncentiveByPeriod_UU	  */
	public void setUNS_AcvIncentiveByPeriod_UU (String UNS_AcvIncentiveByPeriod_UU)
	{
		set_Value (COLUMNNAME_UNS_AcvIncentiveByPeriod_UU, UNS_AcvIncentiveByPeriod_UU);
	}

	/** Get UNS_AcvIncentiveByPeriod_UU.
		@return UNS_AcvIncentiveByPeriod_UU	  */
	public String getUNS_AcvIncentiveByPeriod_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AcvIncentiveByPeriod_UU);
	}
}