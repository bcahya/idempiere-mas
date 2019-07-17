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

/** Generated Model for UNS_Production_Worker
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Production_Worker extends PO implements I_UNS_Production_Worker, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150311L;

    /** Standard Constructor */
    public X_UNS_Production_Worker (Properties ctx, int UNS_Production_Worker_ID, String trxName)
    {
      super (ctx, UNS_Production_Worker_ID, trxName);
      /** if (UNS_Production_Worker_ID == 0)
        {
			setInsentifPemborong (Env.ZERO);
// 0
			setIsAdditional (true);
// Y
			setLabor_ID (0);
			setOvertimeReceivable (Env.ZERO);
// 0
			setPayrollTerm (null);
// 01
			setPresenceStatus (null);
// FLD
			setTotalReceivableAmt (Env.ZERO);
// 0
			setUNS_Job_Role_ID (0);
			setUNS_Production_ID (0);
			setUNS_Production_Worker_ID (0);
			setWorkHours (Env.ZERO);
// 7
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_Worker (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_Worker[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Overtime (Hours).
		@param HoursOverTime 
		The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public void setHoursOverTime (BigDecimal HoursOverTime)
	{
		set_Value (COLUMNNAME_HoursOverTime, HoursOverTime);
	}

	/** Get Overtime (Hours).
		@return The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public BigDecimal getHoursOverTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HoursOverTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Additional.
		@param IsAdditional 
		Check this box if this is additional
	  */
	public void setIsAdditional (boolean IsAdditional)
	{
		set_ValueNoCheck (COLUMNNAME_IsAdditional, Boolean.valueOf(IsAdditional));
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

	/** Set No-Work Day Incentive.
		@param NoWorkDayIncentive 
		The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive)
	{
		set_Value (COLUMNNAME_NoWorkDayIncentive, NoWorkDayIncentive);
	}

	/** Get No-Work Day Incentive.
		@return The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NoWorkDayIncentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Overtime Receivable.
		@param OvertimeReceivable 
		Worker's receivable amount based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public void setOvertimeReceivable (BigDecimal OvertimeReceivable)
	{
		set_Value (COLUMNNAME_OvertimeReceivable, OvertimeReceivable);
	}

	/** Get Overtime Receivable.
		@return Worker's receivable amount based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public BigDecimal getOvertimeReceivable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeReceivable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Full Day = FLD */
	public static final String PRESENCESTATUS_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String PRESENCESTATUS_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String PRESENCESTATUS_Izin = "IZN";
	/** Libur = LBR */
	public static final String PRESENCESTATUS_Libur = "LBR";
	/** Lembur = LMR */
	public static final String PRESENCESTATUS_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String PRESENCESTATUS_Mangkir = "MKR";
	/** Set Presence Status.
		@param PresenceStatus Presence Status	  */
	public void setPresenceStatus (String PresenceStatus)
	{

		set_Value (COLUMNNAME_PresenceStatus, PresenceStatus);
	}

	/** Get Presence Status.
		@return Presence Status	  */
	public String getPresenceStatus () 
	{
		return (String)get_Value(COLUMNNAME_PresenceStatus);
	}

	/** Set Basic Receivable.
		@param ReceivableAmt 
		Basic receivable amount calculated based on worker's production result/presence
	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt)
	{
		set_Value (COLUMNNAME_ReceivableAmt, ReceivableAmt);
	}

	/** Get Basic Receivable.
		@return Basic receivable amount calculated based on worker's production result/presence
	  */
	public BigDecimal getReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
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

	/** Set Reversal Line.
		@param ReversalLine_ID 
		Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Reversal Line.
		@return Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor.
		@param Supervisor_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Receivable Amt.
		@param TotalReceivableAmt Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt)
	{
		set_Value (COLUMNNAME_TotalReceivableAmt, TotalReceivableAmt);
	}

	/** Get Total Receivable Amt.
		@return Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalReceivableAmt);
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

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production)MTable.get(getCtx(), com.uns.model.I_UNS_Production.Table_Name)
			.getPO(getUNS_Production_ID(), get_TrxName());	}

	/** Set Production.
		@param UNS_Production_ID Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID)
	{
		if (UNS_Production_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, Integer.valueOf(UNS_Production_ID));
	}

	/** Get Production.
		@return Production	  */
	public int getUNS_Production_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Worker.
		@param UNS_Production_Worker_ID Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID)
	{
		if (UNS_Production_Worker_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Worker_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Worker_ID, Integer.valueOf(UNS_Production_Worker_ID));
	}

	/** Get Production Worker.
		@return Production Worker	  */
	public int getUNS_Production_Worker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Worker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Production_Worker_UU.
		@param UNS_Production_Worker_UU UNS_Production_Worker_UU	  */
	public void setUNS_Production_Worker_UU (String UNS_Production_Worker_UU)
	{
		set_Value (COLUMNNAME_UNS_Production_Worker_UU, UNS_Production_Worker_UU);
	}

	/** Get UNS_Production_Worker_UU.
		@return UNS_Production_Worker_UU	  */
	public String getUNS_Production_Worker_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_Worker_UU);
	}

	/** Set Normal Work Hours.
		@param WorkHours Normal Work Hours	  */
	public void setWorkHours (BigDecimal WorkHours)
	{
		set_Value (COLUMNNAME_WorkHours, WorkHours);
	}

	/** Get Normal Work Hours.
		@return Normal Work Hours	  */
	public BigDecimal getWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}