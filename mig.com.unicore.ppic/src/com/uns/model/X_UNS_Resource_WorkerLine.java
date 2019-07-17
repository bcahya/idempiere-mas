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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Resource_WorkerLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Resource_WorkerLine extends PO implements I_UNS_Resource_WorkerLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150312L;

    /** Standard Constructor */
    public X_UNS_Resource_WorkerLine (Properties ctx, int UNS_Resource_WorkerLine_ID, String trxName)
    {
      super (ctx, UNS_Resource_WorkerLine_ID, trxName);
      /** if (UNS_Resource_WorkerLine_ID == 0)
        {
			setIsAdditional (false);
// N
			setIsPrimePortion (false);
// N
			setLabor_ID (0);
			setPayrollTerm (null);
// '01'
			setResultProportion (Env.ZERO);
			setUNS_Job_Role_ID (0);
			setUNS_Resource_ID (0);
			setUNS_Resource_WorkerLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_WorkerLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_WorkerLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Additional.
		@param IsAdditional 
		Check this box if this is additional
	  */
	public void setIsAdditional (boolean IsAdditional)
	{
		set_Value (COLUMNNAME_IsAdditional, Boolean.valueOf(IsAdditional));
	}

	/** Get Additional.
		@return Check this box if this is additional
	  */
	public boolean isAdditional () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdditional);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Prime Portion.
		@param IsPrimePortion Prime Portion	  */
	public void setIsPrimePortion (boolean IsPrimePortion)
	{
		set_Value (COLUMNNAME_IsPrimePortion, Boolean.valueOf(IsPrimePortion));
	}

	/** Get Prime Portion.
		@return Prime Portion	  */
	public boolean isPrimePortion () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimePortion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Labor ID.
		@param Labor_ID 
		User ID or account number
	  */
	public void setLabor_ID (int Labor_ID)
	{
		if (Labor_ID < 1) 
			set_Value (COLUMNNAME_Labor_ID, null);
		else 
			set_Value (COLUMNNAME_Labor_ID, Integer.valueOf(Labor_ID));
	}

	/** Get Labor ID.
		@return User ID or account number
	  */
	public int getLabor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Labor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Monthly = 01 */
	public static final String PAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PAYROLLTERM_HarianBulanan = "04";
	/** Set Payroll Term.
		@param PayrollTerm Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm)
	{

		set_Value (COLUMNNAME_PayrollTerm, PayrollTerm);
	}

	/** Get Payroll Term.
		@return Payroll Term	  */
	public String getPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PayrollTerm);
	}

	/** Set Result Proportion (%).
		@param ResultProportion Result Proportion (%)	  */
	public void setResultProportion (BigDecimal ResultProportion)
	{
		set_Value (COLUMNNAME_ResultProportion, ResultProportion);
	}

	/** Get Result Proportion (%).
		@return Result Proportion (%)	  */
	public BigDecimal getResultProportion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResultProportion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Job_Role getUNS_Job_Role() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Job_Role)MTable.get(getCtx(), com.uns.model.I_UNS_Job_Role.Table_Name)
			.getPO(getUNS_Job_Role_ID(), get_TrxName());	}

	/** Set Job Role.
		@param UNS_Job_Role_ID Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID)
	{
		if (UNS_Job_Role_ID < 1) 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, Integer.valueOf(UNS_Job_Role_ID));
	}

	/** Get Job Role.
		@return Job Role	  */
	public int getUNS_Job_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Job_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Job_Role_ID()));
    }

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacturing Resource Worker Line.
		@param UNS_Resource_WorkerLine_ID Manufacturing Resource Worker Line	  */
	public void setUNS_Resource_WorkerLine_ID (int UNS_Resource_WorkerLine_ID)
	{
		if (UNS_Resource_WorkerLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_WorkerLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_WorkerLine_ID, Integer.valueOf(UNS_Resource_WorkerLine_ID));
	}

	/** Get Manufacturing Resource Worker Line.
		@return Manufacturing Resource Worker Line	  */
	public int getUNS_Resource_WorkerLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_WorkerLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Resource_WorkerLine_UU.
		@param UNS_Resource_WorkerLine_UU UNS_Resource_WorkerLine_UU	  */
	public void setUNS_Resource_WorkerLine_UU (String UNS_Resource_WorkerLine_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_WorkerLine_UU, UNS_Resource_WorkerLine_UU);
	}

	/** Get UNS_Resource_WorkerLine_UU.
		@return UNS_Resource_WorkerLine_UU	  */
	public String getUNS_Resource_WorkerLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_WorkerLine_UU);
	}
}