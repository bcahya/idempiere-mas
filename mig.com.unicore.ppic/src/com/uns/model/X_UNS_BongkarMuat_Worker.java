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

/** Generated Model for UNS_BongkarMuat_Worker
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_BongkarMuat_Worker extends PO implements I_UNS_BongkarMuat_Worker, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141006L;

    /** Standard Constructor */
    public X_UNS_BongkarMuat_Worker (Properties ctx, int UNS_BongkarMuat_Worker_ID, String trxName)
    {
      super (ctx, UNS_BongkarMuat_Worker_ID, trxName);
      /** if (UNS_BongkarMuat_Worker_ID == 0)
        {
			setLabor_ID (0);
			setPayrollTerm (null);
			setUNS_BongkarMuat_ID (0);
			setUNS_BongkarMuat_Worker_ID (0);
			setUNS_Job_Role_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BongkarMuat_Worker (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_BongkarMuat_Worker[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contractor Incentive.
		@param InsentifPemborong 
		The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong)
	{
		set_Value (COLUMNNAME_InsentifPemborong, InsentifPemborong);
	}

	/** Get Contractor Incentive.
		@return The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InsentifPemborong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Is Subcontracting.
		@param IsSubcontracting Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, Boolean.valueOf(IsSubcontracting));
	}

	/** Get Is Subcontracting.
		@return Is Subcontracting	  */
	public boolean isSubcontracting () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubcontracting);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLabor_ID()));
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

	/** Set Receivable Amt.
		@param ReceivableAmt Receivable Amt	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt)
	{
		set_Value (COLUMNNAME_ReceivableAmt, ReceivableAmt);
	}

	/** Get Receivable Amt.
		@return Receivable Amt	  */
	public BigDecimal getReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Replacement Labor.
		@param ReplacementLabor_ID Replacement Labor	  */
	public void setReplacementLabor_ID (int ReplacementLabor_ID)
	{
		if (ReplacementLabor_ID < 1) 
			set_Value (COLUMNNAME_ReplacementLabor_ID, null);
		else 
			set_Value (COLUMNNAME_ReplacementLabor_ID, Integer.valueOf(ReplacementLabor_ID));
	}

	/** Get Replacement Labor.
		@return Replacement Labor	  */
	public int getReplacementLabor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReplacementLabor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_BongkarMuat getUNS_BongkarMuat() throws RuntimeException
    {
		return (com.uns.model.I_UNS_BongkarMuat)MTable.get(getCtx(), com.uns.model.I_UNS_BongkarMuat.Table_Name)
			.getPO(getUNS_BongkarMuat_ID(), get_TrxName());	}

	/** Set Bongkar Muat.
		@param UNS_BongkarMuat_ID Bongkar Muat	  */
	public void setUNS_BongkarMuat_ID (int UNS_BongkarMuat_ID)
	{
		if (UNS_BongkarMuat_ID < 1) 
			set_Value (COLUMNNAME_UNS_BongkarMuat_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BongkarMuat_ID, Integer.valueOf(UNS_BongkarMuat_ID));
	}

	/** Get Bongkar Muat.
		@return Bongkar Muat	  */
	public int getUNS_BongkarMuat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BongkarMuat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Worker Unloading.
		@param UNS_BongkarMuat_Worker_ID Worker Unloading	  */
	public void setUNS_BongkarMuat_Worker_ID (int UNS_BongkarMuat_Worker_ID)
	{
		if (UNS_BongkarMuat_Worker_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BongkarMuat_Worker_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BongkarMuat_Worker_ID, Integer.valueOf(UNS_BongkarMuat_Worker_ID));
	}

	/** Get Worker Unloading.
		@return Worker Unloading	  */
	public int getUNS_BongkarMuat_Worker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BongkarMuat_Worker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BongkarMuat_Worker_UU.
		@param UNS_BongkarMuat_Worker_UU UNS_BongkarMuat_Worker_UU	  */
	public void setUNS_BongkarMuat_Worker_UU (String UNS_BongkarMuat_Worker_UU)
	{
		set_Value (COLUMNNAME_UNS_BongkarMuat_Worker_UU, UNS_BongkarMuat_Worker_UU);
	}

	/** Get UNS_BongkarMuat_Worker_UU.
		@return UNS_BongkarMuat_Worker_UU	  */
	public String getUNS_BongkarMuat_Worker_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BongkarMuat_Worker_UU);
	}

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
}