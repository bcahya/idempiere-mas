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

/** Generated Model for UNS_Payroll_CostBenefit
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Payroll_CostBenefit extends PO implements I_UNS_Payroll_CostBenefit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180226L;

    /** Standard Constructor */
    public X_UNS_Payroll_CostBenefit (Properties ctx, int UNS_Payroll_CostBenefit_ID, String trxName)
    {
      super (ctx, UNS_Payroll_CostBenefit_ID, trxName);
      /** if (UNS_Payroll_CostBenefit_ID == 0)
        {
			setAmount (Env.ZERO);
			setCostBenefit_Acct (0);
			setIsBenefit (false);
// N
			setIsPPHComp (false);
// N
			setName (null);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM UNS_Payroll_CostBenefit WHERE UNS_Payroll_CostBenefit_ID=@UNS_Payroll_CostBenefit_ID@
			setUNS_Payroll_CostBenefit_ID (0);
			setUNS_Payroll_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Payroll_CostBenefit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Payroll_CostBenefit[")
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

	public I_C_ValidCombination getCostBenefit_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCostBenefit_Acct(), get_TrxName());	}

	/** Set Cost/Benefit Acct.
		@param CostBenefit_Acct 
		The accounting element of this cost/benefit
	  */
	public void setCostBenefit_Acct (int CostBenefit_Acct)
	{
		set_Value (COLUMNNAME_CostBenefit_Acct, Integer.valueOf(CostBenefit_Acct));
	}

	/** Get Cost/Benefit Acct.
		@return The accounting element of this cost/benefit
	  */
	public int getCostBenefit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CostBenefit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Is Benefit?.
		@param IsBenefit 
		If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public void setIsBenefit (boolean IsBenefit)
	{
		set_Value (COLUMNNAME_IsBenefit, Boolean.valueOf(IsBenefit));
	}

	/** Get Is Benefit?.
		@return If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public boolean isBenefit () 
	{
		Object oo = get_Value(COLUMNNAME_IsBenefit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PPH Component ?.
		@param IsPPHComp PPH Component ?	  */
	public void setIsPPHComp (boolean IsPPHComp)
	{
		set_Value (COLUMNNAME_IsPPHComp, Boolean.valueOf(IsPPHComp));
	}

	/** Get PPH Component ?.
		@return PPH Component ?	  */
	public boolean isPPHComp () 
	{
		Object oo = get_Value(COLUMNNAME_IsPPHComp);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Payroll_Component_Conf getUNS_Payroll_Component_Conf() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Payroll_Component_Conf)MTable.get(getCtx(), com.uns.model.I_UNS_Payroll_Component_Conf.Table_Name)
			.getPO(getUNS_Payroll_Component_Conf_ID(), get_TrxName());	}

	/** Set Payroll Component Configuration.
		@param UNS_Payroll_Component_Conf_ID Payroll Component Configuration	  */
	public void setUNS_Payroll_Component_Conf_ID (int UNS_Payroll_Component_Conf_ID)
	{
		if (UNS_Payroll_Component_Conf_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_Component_Conf_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_Component_Conf_ID, Integer.valueOf(UNS_Payroll_Component_Conf_ID));
	}

	/** Get Payroll Component Configuration.
		@return Payroll Component Configuration	  */
	public int getUNS_Payroll_Component_Conf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Payroll_Component_Conf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Cost/Benefit.
		@param UNS_Payroll_CostBenefit_ID Payroll Cost/Benefit	  */
	public void setUNS_Payroll_CostBenefit_ID (int UNS_Payroll_CostBenefit_ID)
	{
		if (UNS_Payroll_CostBenefit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_CostBenefit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_CostBenefit_ID, Integer.valueOf(UNS_Payroll_CostBenefit_ID));
	}

	/** Get Payroll Cost/Benefit.
		@return Payroll Cost/Benefit	  */
	public int getUNS_Payroll_CostBenefit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Payroll_CostBenefit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Payroll_CostBenefit_UU.
		@param UNS_Payroll_CostBenefit_UU UNS_Payroll_CostBenefit_UU	  */
	public void setUNS_Payroll_CostBenefit_UU (String UNS_Payroll_CostBenefit_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Payroll_CostBenefit_UU, UNS_Payroll_CostBenefit_UU);
	}

	/** Get UNS_Payroll_CostBenefit_UU.
		@return UNS_Payroll_CostBenefit_UU	  */
	public String getUNS_Payroll_CostBenefit_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Payroll_CostBenefit_UU);
	}

	public com.uns.model.I_UNS_Payroll_Employee getUNS_Payroll_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Payroll_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Payroll_Employee.Table_Name)
			.getPO(getUNS_Payroll_Employee_ID(), get_TrxName());	}

	/** Set Payroll Employee.
		@param UNS_Payroll_Employee_ID Payroll Employee	  */
	public void setUNS_Payroll_Employee_ID (int UNS_Payroll_Employee_ID)
	{
		if (UNS_Payroll_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Payroll_Employee_ID, Integer.valueOf(UNS_Payroll_Employee_ID));
	}

	/** Get Payroll Employee.
		@return Payroll Employee	  */
	public int getUNS_Payroll_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Payroll_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}