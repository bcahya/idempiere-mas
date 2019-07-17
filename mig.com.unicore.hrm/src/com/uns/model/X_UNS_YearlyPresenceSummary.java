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

/** Generated Model for UNS_YearlyPresenceSummary
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_YearlyPresenceSummary extends PO implements I_UNS_YearlyPresenceSummary, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141006L;

    /** Standard Constructor */
    public X_UNS_YearlyPresenceSummary (Properties ctx, int UNS_YearlyPresenceSummary_ID, String trxName)
    {
      super (ctx, UNS_YearlyPresenceSummary_ID, trxName);
      /** if (UNS_YearlyPresenceSummary_ID == 0)
        {
			setC_Year_ID (0);
			setTotalAbsence (0);
// 0
			setTotalLD1 (Env.ZERO);
// 0
			setTotalLD2 (Env.ZERO);
// 0
			setTotalLD3 (Env.ZERO);
// 0
			setTotalOvertime (Env.ZERO);
// 0
			setTotalOvertime1stHour (Env.ZERO);
// 0
			setTotalOvertimeNextHour (Env.ZERO);
// 0
			setTotalSK (Env.ZERO);
// 0
			setTotalSKK (Env.ZERO);
// 0
			setUNS_Employee_ID (0);
			setUNS_YearlyPresenceSummary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_YearlyPresenceSummary (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_YearlyPresenceSummary[")
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

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
    {
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_Name)
			.getPO(getC_Job_ID(), get_TrxName());	}

	/** Set Position.
		@param C_Job_ID 
		Job Position
	  */
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Job Position
	  */
	public int getC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reload Presence Record.
		@param LoadItemLines Reload Presence Record	  */
	public void setLoadItemLines (String LoadItemLines)
	{
		set_Value (COLUMNNAME_LoadItemLines, LoadItemLines);
	}

	/** Get Reload Presence Record.
		@return Reload Presence Record	  */
	public String getLoadItemLines () 
	{
		return (String)get_Value(COLUMNNAME_LoadItemLines);
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

	/** Set Total Absence.
		@param TotalAbsence 
		The number of employee absences
	  */
	public void setTotalAbsence (int TotalAbsence)
	{
		set_Value (COLUMNNAME_TotalAbsence, Integer.valueOf(TotalAbsence));
	}

	/** Get Total Absence.
		@return The number of employee absences
	  */
	public int getTotalAbsence () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalAbsence);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Full Day Presence.
		@param TotalFullDayPresence Total Full Day Presence	  */
	public void setTotalFullDayPresence (int TotalFullDayPresence)
	{
		set_Value (COLUMNNAME_TotalFullDayPresence, Integer.valueOf(TotalFullDayPresence));
	}

	/** Get Total Full Day Presence.
		@return Total Full Day Presence	  */
	public int getTotalFullDayPresence () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalFullDayPresence);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Half Day Presence.
		@param TotalHalfDayPresence Total Half Day Presence	  */
	public void setTotalHalfDayPresence (int TotalHalfDayPresence)
	{
		set_Value (COLUMNNAME_TotalHalfDayPresence, Integer.valueOf(TotalHalfDayPresence));
	}

	/** Get Total Half Day Presence.
		@return Total Half Day Presence	  */
	public int getTotalHalfDayPresence () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalHalfDayPresence);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total LD1.
		@param TotalLD1 Total LD1	  */
	public void setTotalLD1 (BigDecimal TotalLD1)
	{
		set_Value (COLUMNNAME_TotalLD1, TotalLD1);
	}

	/** Get Total LD1.
		@return Total LD1	  */
	public BigDecimal getTotalLD1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLD1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total LD2.
		@param TotalLD2 Total LD2	  */
	public void setTotalLD2 (BigDecimal TotalLD2)
	{
		set_Value (COLUMNNAME_TotalLD2, TotalLD2);
	}

	/** Get Total LD2.
		@return Total LD2	  */
	public BigDecimal getTotalLD2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLD2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total LD3.
		@param TotalLD3 Total LD3	  */
	public void setTotalLD3 (BigDecimal TotalLD3)
	{
		set_Value (COLUMNNAME_TotalLD3, TotalLD3);
	}

	/** Get Total LD3.
		@return Total LD3	  */
	public BigDecimal getTotalLD3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLD3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Overtime.
		@param TotalOvertime 
		Total emloyee's overtime in month
	  */
	public void setTotalOvertime (BigDecimal TotalOvertime)
	{
		set_Value (COLUMNNAME_TotalOvertime, TotalOvertime);
	}

	/** Get Total Overtime.
		@return Total emloyee's overtime in month
	  */
	public BigDecimal getTotalOvertime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOvertime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Overtime (1st Hour).
		@param TotalOvertime1stHour 
		Total 1st hour of emloyee's overtime in period
	  */
	public void setTotalOvertime1stHour (BigDecimal TotalOvertime1stHour)
	{
		set_Value (COLUMNNAME_TotalOvertime1stHour, TotalOvertime1stHour);
	}

	/** Get Total Overtime (1st Hour).
		@return Total 1st hour of emloyee's overtime in period
	  */
	public BigDecimal getTotalOvertime1stHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOvertime1stHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Overtime (Next Hour).
		@param TotalOvertimeNextHour 
		Total after 1st hour of emloyee's overtime in period
	  */
	public void setTotalOvertimeNextHour (BigDecimal TotalOvertimeNextHour)
	{
		set_Value (COLUMNNAME_TotalOvertimeNextHour, TotalOvertimeNextHour);
	}

	/** Get Total Overtime (Next Hour).
		@return Total after 1st hour of emloyee's overtime in period
	  */
	public BigDecimal getTotalOvertimeNextHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOvertimeNextHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total SK.
		@param TotalSK Total SK	  */
	public void setTotalSK (BigDecimal TotalSK)
	{
		set_Value (COLUMNNAME_TotalSK, TotalSK);
	}

	/** Get Total SK.
		@return Total SK	  */
	public BigDecimal getTotalSK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalSK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total SKK.
		@param TotalSKK Total SKK	  */
	public void setTotalSKK (BigDecimal TotalSKK)
	{
		set_Value (COLUMNNAME_TotalSKK, TotalSKK);
	}

	/** Get Total SKK.
		@return Total SKK	  */
	public BigDecimal getTotalSKK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalSKK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Work Day.
		@param TotalWorkDay Total Work Day	  */
	public void setTotalWorkDay (int TotalWorkDay)
	{
		set_Value (COLUMNNAME_TotalWorkDay, Integer.valueOf(TotalWorkDay));
	}

	/** Get Total Work Day.
		@return Total Work Day	  */
	public int getTotalWorkDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalWorkDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

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

	/** Set Yearly Presence Summary.
		@param UNS_YearlyPresenceSummary_ID Yearly Presence Summary	  */
	public void setUNS_YearlyPresenceSummary_ID (int UNS_YearlyPresenceSummary_ID)
	{
		if (UNS_YearlyPresenceSummary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_YearlyPresenceSummary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_YearlyPresenceSummary_ID, Integer.valueOf(UNS_YearlyPresenceSummary_ID));
	}

	/** Get Yearly Presence Summary.
		@return Yearly Presence Summary	  */
	public int getUNS_YearlyPresenceSummary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_YearlyPresenceSummary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_YearlyPresenceSummary_UU.
		@param UNS_YearlyPresenceSummary_UU UNS_YearlyPresenceSummary_UU	  */
	public void setUNS_YearlyPresenceSummary_UU (String UNS_YearlyPresenceSummary_UU)
	{
		set_Value (COLUMNNAME_UNS_YearlyPresenceSummary_UU, UNS_YearlyPresenceSummary_UU);
	}

	/** Get UNS_YearlyPresenceSummary_UU.
		@return UNS_YearlyPresenceSummary_UU	  */
	public String getUNS_YearlyPresenceSummary_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_YearlyPresenceSummary_UU);
	}
}