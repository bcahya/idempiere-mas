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

/** Generated Model for UNS_PeriodicCostBenefitLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PeriodicCostBenefitLine extends PO implements I_UNS_PeriodicCostBenefitLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180227L;

    /** Standard Constructor */
    public X_UNS_PeriodicCostBenefitLine (Properties ctx, int UNS_PeriodicCostBenefitLine_ID, String trxName)
    {
      super (ctx, UNS_PeriodicCostBenefitLine_ID, trxName);
      /** if (UNS_PeriodicCostBenefitLine_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setPaidAmt (Env.ZERO);
// 0
			setPrevAmount (Env.ZERO);
// 0
			setProcessed (false);
// N
			setRemainingAmount (Env.ZERO);
// 0
			setUNS_Employee_ID (0);
			setUNS_PeriodicCostBenefit_ID (0);
			setUNS_PeriodicCostBenefitLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PeriodicCostBenefitLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PeriodicCostBenefitLine[")
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

	/** Set Paid Amount.
		@param PaidAmt Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	/** Get Paid Amount.
		@return Paid Amount	  */
	public BigDecimal getPaidAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Previous Amount.
		@param PrevAmount Previous Amount	  */
	public void setPrevAmount (BigDecimal PrevAmount)
	{
		set_Value (COLUMNNAME_PrevAmount, PrevAmount);
	}

	/** Get Previous Amount.
		@return Previous Amount	  */
	public BigDecimal getPrevAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remaining Amount.
		@param RemainingAmount Remaining Amount	  */
	public void setRemainingAmount (BigDecimal RemainingAmount)
	{
		set_ValueNoCheck (COLUMNNAME_RemainingAmount, RemainingAmount);
	}

	/** Get Remaining Amount.
		@return Remaining Amount	  */
	public BigDecimal getRemainingAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainingAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
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

	public com.uns.model.I_UNS_PeriodicCostBenefit getUNS_PeriodicCostBenefit() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PeriodicCostBenefit)MTable.get(getCtx(), com.uns.model.I_UNS_PeriodicCostBenefit.Table_Name)
			.getPO(getUNS_PeriodicCostBenefit_ID(), get_TrxName());	}

	/** Set Periodic Cost And Benefit.
		@param UNS_PeriodicCostBenefit_ID Periodic Cost And Benefit	  */
	public void setUNS_PeriodicCostBenefit_ID (int UNS_PeriodicCostBenefit_ID)
	{
		if (UNS_PeriodicCostBenefit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefit_ID, Integer.valueOf(UNS_PeriodicCostBenefit_ID));
	}

	/** Get Periodic Cost And Benefit.
		@return Periodic Cost And Benefit	  */
	public int getUNS_PeriodicCostBenefit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PeriodicCostBenefit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Detail.
		@param UNS_PeriodicCostBenefitLine_ID Detail	  */
	public void setUNS_PeriodicCostBenefitLine_ID (int UNS_PeriodicCostBenefitLine_ID)
	{
		if (UNS_PeriodicCostBenefitLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefitLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefitLine_ID, Integer.valueOf(UNS_PeriodicCostBenefitLine_ID));
	}

	/** Get Detail.
		@return Detail	  */
	public int getUNS_PeriodicCostBenefitLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PeriodicCostBenefitLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PeriodicCostBenefitLine_UU.
		@param UNS_PeriodicCostBenefitLine_UU UNS_PeriodicCostBenefitLine_UU	  */
	public void setUNS_PeriodicCostBenefitLine_UU (String UNS_PeriodicCostBenefitLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefitLine_UU, UNS_PeriodicCostBenefitLine_UU);
	}

	/** Get UNS_PeriodicCostBenefitLine_UU.
		@return UNS_PeriodicCostBenefitLine_UU	  */
	public String getUNS_PeriodicCostBenefitLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PeriodicCostBenefitLine_UU);
	}
}