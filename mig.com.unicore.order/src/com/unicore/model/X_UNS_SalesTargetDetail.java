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

/** Generated Model for UNS_SalesTargetDetail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SalesTargetDetail extends PO implements I_UNS_SalesTargetDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150125L;

    /** Standard Constructor */
    public X_UNS_SalesTargetDetail (Properties ctx, int UNS_SalesTargetDetail_ID, String trxName)
    {
      super (ctx, UNS_SalesTargetDetail_ID, trxName);
      /** if (UNS_SalesTargetDetail_ID == 0)
        {
			setC_BPartner_ID (0);
			setTOPRate (Env.ZERO);
			setUNS_SalesTargetDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SalesTargetDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SalesTargetDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amt Achieved.
		@param AmtAchieved Amt Achieved	  */
	public void setAmtAchieved (BigDecimal AmtAchieved)
	{
		set_Value (COLUMNNAME_AmtAchieved, AmtAchieved);
	}

	/** Get Amt Achieved.
		@return Amt Achieved	  */
	public BigDecimal getAmtAchieved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAchieved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Incentive Amount.
		@param IncentiveAmt Incentive Amount	  */
	public void setIncentiveAmt (BigDecimal IncentiveAmt)
	{
		set_ValueNoCheck (COLUMNNAME_IncentiveAmt, IncentiveAmt);
	}

	/** Get Incentive Amount.
		@return Incentive Amount	  */
	public BigDecimal getIncentiveAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IncentiveAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Percent Amount.
		@param PercentAmt Percent Amount	  */
	public void setPercentAmt (BigDecimal PercentAmt)
	{
		set_Value (COLUMNNAME_PercentAmt, PercentAmt);
	}

	/** Get Percent Amount.
		@return Percent Amount	  */
	public BigDecimal getPercentAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Percent Rate.
		@param PercentRate Percent Rate	  */
	public void setPercentRate (BigDecimal PercentRate)
	{
		set_Value (COLUMNNAME_PercentRate, PercentRate);
	}

	/** Get Percent Rate.
		@return Percent Rate	  */
	public BigDecimal getPercentRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TOP Rate.
		@param TOPRate TOP Rate	  */
	public void setTOPRate (BigDecimal TOPRate)
	{
		set_Value (COLUMNNAME_TOPRate, TOPRate);
	}

	/** Get TOP Rate.
		@return TOP Rate	  */
	public BigDecimal getTOPRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOPRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sales Target Detail.
		@param UNS_SalesTargetDetail_ID Sales Target Detail	  */
	public void setUNS_SalesTargetDetail_ID (int UNS_SalesTargetDetail_ID)
	{
		if (UNS_SalesTargetDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetDetail_ID, Integer.valueOf(UNS_SalesTargetDetail_ID));
	}

	/** Get Sales Target Detail.
		@return Sales Target Detail	  */
	public int getUNS_SalesTargetDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesTargetDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SalesTargetDetail_UU.
		@param UNS_SalesTargetDetail_UU UNS_SalesTargetDetail_UU	  */
	public void setUNS_SalesTargetDetail_UU (String UNS_SalesTargetDetail_UU)
	{
		set_Value (COLUMNNAME_UNS_SalesTargetDetail_UU, UNS_SalesTargetDetail_UU);
	}

	/** Get UNS_SalesTargetDetail_UU.
		@return UNS_SalesTargetDetail_UU	  */
	public String getUNS_SalesTargetDetail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SalesTargetDetail_UU);
	}

	public com.unicore.model.I_UNS_SalesTargetPeriodic getUNS_SalesTargetPeriodic() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_SalesTargetPeriodic)MTable.get(getCtx(), com.unicore.model.I_UNS_SalesTargetPeriodic.Table_Name)
			.getPO(getUNS_SalesTargetPeriodic_ID(), get_TrxName());	}

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
}