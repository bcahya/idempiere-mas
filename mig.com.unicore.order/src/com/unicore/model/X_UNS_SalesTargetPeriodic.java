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

/** Generated Model for UNS_SalesTargetPeriodic
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SalesTargetPeriodic extends PO implements I_UNS_SalesTargetPeriodic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141212L;

    /** Standard Constructor */
    public X_UNS_SalesTargetPeriodic (Properties ctx, int UNS_SalesTargetPeriodic_ID, String trxName)
    {
      super (ctx, UNS_SalesTargetPeriodic_ID, trxName);
      /** if (UNS_SalesTargetPeriodic_ID == 0)
        {
			setUNS_SalesTargetPeriodic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SalesTargetPeriodic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SalesTargetPeriodic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Deduction.
		@param Deduction Deduction	  */
	public void setDeduction (BigDecimal Deduction)
	{
		set_Value (COLUMNNAME_Deduction, Deduction);
	}

	/** Get Deduction.
		@return Deduction	  */
	public BigDecimal getDeduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Deduction);
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

	/** Set Incentive %.
		@param Incentive Incentive %	  */
	public void setIncentive (BigDecimal Incentive)
	{
		set_Value (COLUMNNAME_Incentive, Incentive);
	}

	/** Get Incentive %.
		@return Incentive %	  */
	public BigDecimal getIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Incentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Period getPeriodEnd() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getPeriodEnd_ID(), get_TrxName());	}

	/** Set Period End.
		@param PeriodEnd_ID Period End	  */
	public void setPeriodEnd_ID (int PeriodEnd_ID)
	{
		if (PeriodEnd_ID < 1) 
			set_Value (COLUMNNAME_PeriodEnd_ID, null);
		else 
			set_Value (COLUMNNAME_PeriodEnd_ID, Integer.valueOf(PeriodEnd_ID));
	}

	/** Get Period End.
		@return Period End	  */
	public int getPeriodEnd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodEnd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getPeriodStart() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getPeriodStart_ID(), get_TrxName());	}

	/** Set Period Start.
		@param PeriodStart_ID Period Start	  */
	public void setPeriodStart_ID (int PeriodStart_ID)
	{
		if (PeriodStart_ID < 1) 
			set_Value (COLUMNNAME_PeriodStart_ID, null);
		else 
			set_Value (COLUMNNAME_PeriodStart_ID, Integer.valueOf(PeriodStart_ID));
	}

	/** Get Period Start.
		@return Period Start	  */
	public int getPeriodStart_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodStart_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Top Target.
		@param TOPTarget 
		Term of Payment target.
	  */
	public void setTOPTarget (BigDecimal TOPTarget)
	{
		set_Value (COLUMNNAME_TOPTarget, TOPTarget);
	}

	/** Get Top Target.
		@return Term of Payment target.
	  */
	public BigDecimal getTOPTarget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOPTarget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_SalesTargetLine getUNS_SalesTargetLine() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_SalesTargetLine)MTable.get(getCtx(), com.unicore.model.I_UNS_SalesTargetLine.Table_Name)
			.getPO(getUNS_SalesTargetLine_ID(), get_TrxName());	}

	/** Set Sales Target Line.
		@param UNS_SalesTargetLine_ID Sales Target Line	  */
	public void setUNS_SalesTargetLine_ID (int UNS_SalesTargetLine_ID)
	{
		if (UNS_SalesTargetLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetLine_ID, Integer.valueOf(UNS_SalesTargetLine_ID));
	}

	/** Get Sales Target Line.
		@return Sales Target Line	  */
	public int getUNS_SalesTargetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesTargetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Taget Periodic.
		@param UNS_SalesTargetPeriodic_ID Sales Taget Periodic	  */
	public void setUNS_SalesTargetPeriodic_ID (int UNS_SalesTargetPeriodic_ID)
	{
		if (UNS_SalesTargetPeriodic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetPeriodic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetPeriodic_ID, Integer.valueOf(UNS_SalesTargetPeriodic_ID));
	}

	/** Get Sales Taget Periodic.
		@return Sales Taget Periodic	  */
	public int getUNS_SalesTargetPeriodic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesTargetPeriodic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SalesTargetPeriodic_UU.
		@param UNS_SalesTargetPeriodic_UU UNS_SalesTargetPeriodic_UU	  */
	public void setUNS_SalesTargetPeriodic_UU (String UNS_SalesTargetPeriodic_UU)
	{
		set_Value (COLUMNNAME_UNS_SalesTargetPeriodic_UU, UNS_SalesTargetPeriodic_UU);
	}

	/** Get UNS_SalesTargetPeriodic_UU.
		@return UNS_SalesTargetPeriodic_UU	  */
	public String getUNS_SalesTargetPeriodic_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SalesTargetPeriodic_UU);
	}

	/** Set Value Target.
		@param ValueTarget Value Target	  */
	public void setValueTarget (BigDecimal ValueTarget)
	{
		set_Value (COLUMNNAME_ValueTarget, ValueTarget);
	}

	/** Get Value Target.
		@return Value Target	  */
	public BigDecimal getValueTarget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueTarget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}