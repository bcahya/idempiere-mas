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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_MonthlyPresenceSummary
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_MonthlyPresenceSummary extends PO implements I_UNS_MonthlyPresenceSummary, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160602L;

    /** Standard Constructor */
    public X_UNS_MonthlyPresenceSummary (Properties ctx, int UNS_MonthlyPresenceSummary_ID, String trxName)
    {
      super (ctx, UNS_MonthlyPresenceSummary_ID, trxName);
      /** if (UNS_MonthlyPresenceSummary_ID == 0)
        {
			setC_Period_ID (0);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setTotalAbsence (0);
// 0
			setTotalHalfDayPresence (0);
// 0
			setTotalLD1 (Env.ZERO);
// 0
			setTotalLD2 (Env.ZERO);
// 0
			setTotalLD3 (Env.ZERO);
// 0
			setTotalNonPayableHalfDay (0);
// 0
			setTotalOvertime (Env.ZERO);
// 0
			setTotalOvertime1stHour (Env.ZERO);
// 0
			setTotalOvertimeNextHour (Env.ZERO);
// 0
			setTotalPayableHalfDay (0);
// 0
			setTotalSK (Env.ZERO);
// 0
			setTotalSKK (Env.ZERO);
// 0
			setTotalWorkHours (Env.ZERO);
// 0
			setUNS_MonthlyPresenceSummary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MonthlyPresenceSummary (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MonthlyPresenceSummary[")
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
			set_ValueNoCheck (COLUMNNAME_C_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Confirmed = CF */
	public static final String DOCACTION_Confirmed = "CF";
	/** Finished = FN */
	public static final String DOCACTION_Finished = "FN";
	/** Cancelled = CN */
	public static final String DOCACTION_Cancelled = "CN";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

	/** Set Generate Daily Presence.
		@param GenerateDailyPresence Generate Daily Presence	  */
	public void setGenerateDailyPresence (String GenerateDailyPresence)
	{
		set_Value (COLUMNNAME_GenerateDailyPresence, GenerateDailyPresence);
	}

	/** Get Generate Daily Presence.
		@return Generate Daily Presence	  */
	public String getGenerateDailyPresence () 
	{
		return (String)get_Value(COLUMNNAME_GenerateDailyPresence);
	}

	/** HRD Level 1 = 1 */
	public static final String HRDLEVEL_HRDLevel1 = "1";
	/** HRD Level 2 = 2 */
	public static final String HRDLEVEL_HRDLevel2 = "2";
	/** HRD Level 3 = 3 */
	public static final String HRDLEVEL_HRDLevel3 = "3";
	/** Set HRD Level.
		@param HRDLevel HRD Level	  */
	public void setHRDLevel (String HRDLevel)
	{

		set_Value (COLUMNNAME_HRDLevel, HRDLevel);
	}

	/** Get HRD Level.
		@return HRD Level	  */
	public String getHRDLevel () 
	{
		return (String)get_Value(COLUMNNAME_HRDLevel);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsGenerate.
		@param IsGenerate IsGenerate	  */
	public void setIsGenerate (boolean IsGenerate)
	{
		set_Value (COLUMNNAME_IsGenerate, Boolean.valueOf(IsGenerate));
	}

	/** Get IsGenerate.
		@return IsGenerate	  */
	public boolean isGenerate () 
	{
		Object oo = get_Value(COLUMNNAME_IsGenerate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Borongan = 01 */
	public static final String JOBSTATUS_Borongan = "01";
	/** Training Borongan = 02 */
	public static final String JOBSTATUS_TrainingBorongan = "02";
	/** Harian = 03 */
	public static final String JOBSTATUS_Harian = "03";
	/** Bulanan = 04 */
	public static final String JOBSTATUS_Bulanan = "04";
	/** Set Job Status.
		@param JobStatus Job Status	  */
	public void setJobStatus (String JobStatus)
	{

		set_Value (COLUMNNAME_JobStatus, JobStatus);
	}

	/** Get Job Status.
		@return Job Status	  */
	public String getJobStatus () 
	{
		return (String)get_Value(COLUMNNAME_JobStatus);
	}

	/** Saturday = 7 */
	public static final String NOWORKDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String NOWORKDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String NOWORKDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String NOWORKDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String NOWORKDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String NOWORKDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String NOWORKDAY_Sunday = "1";
	/** Set No Work Day.
		@param NoWorkDay No Work Day	  */
	public void setNoWorkDay (String NoWorkDay)
	{

		set_ValueNoCheck (COLUMNNAME_NoWorkDay, NoWorkDay);
	}

	/** Get No Work Day.
		@return No Work Day	  */
	public String getNoWorkDay () 
	{
		return (String)get_Value(COLUMNNAME_NoWorkDay);
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

	/** Set Shift.
		@param Shift Shift	  */
	public void setShift (String Shift)
	{
		set_Value (COLUMNNAME_Shift, Shift);
	}

	/** Get Shift.
		@return Shift	  */
	public String getShift () 
	{
		return (String)get_Value(COLUMNNAME_Shift);
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

	/** Set Total Non Payable Absence.
		@param TotalNonPayableAbsence 
		The number of employee absences with non-payable permission
	  */
	public void setTotalNonPayableAbsence (int TotalNonPayableAbsence)
	{
		set_Value (COLUMNNAME_TotalNonPayableAbsence, Integer.valueOf(TotalNonPayableAbsence));
	}

	/** Get Total Non Payable Absence.
		@return The number of employee absences with non-payable permission
	  */
	public int getTotalNonPayableAbsence () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalNonPayableAbsence);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Non-Payable Half Day.
		@param TotalNonPayableHalfDay 
		Total half day absence with non-payable permission
	  */
	public void setTotalNonPayableHalfDay (int TotalNonPayableHalfDay)
	{
		set_Value (COLUMNNAME_TotalNonPayableHalfDay, Integer.valueOf(TotalNonPayableHalfDay));
	}

	/** Get Total Non-Payable Half Day.
		@return Total half day absence with non-payable permission
	  */
	public int getTotalNonPayableHalfDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalNonPayableHalfDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Total Payable Absence.
		@param TotalPayableAbsence 
		The number of employee absences with payable permission
	  */
	public void setTotalPayableAbsence (int TotalPayableAbsence)
	{
		set_Value (COLUMNNAME_TotalPayableAbsence, Integer.valueOf(TotalPayableAbsence));
	}

	/** Get Total Payable Absence.
		@return The number of employee absences with payable permission
	  */
	public int getTotalPayableAbsence () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalPayableAbsence);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Payable Half Day.
		@param TotalPayableHalfDay 
		Total half day absence with payable permission
	  */
	public void setTotalPayableHalfDay (int TotalPayableHalfDay)
	{
		set_Value (COLUMNNAME_TotalPayableHalfDay, Integer.valueOf(TotalPayableHalfDay));
	}

	/** Get Total Payable Half Day.
		@return Total half day absence with payable permission
	  */
	public int getTotalPayableHalfDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalPayableHalfDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Total Work Hours.
		@param TotalWorkHours Total Work Hours	  */
	public void setTotalWorkHours (BigDecimal TotalWorkHours)
	{
		set_Value (COLUMNNAME_TotalWorkHours, TotalWorkHours);
	}

	/** Get Total Work Hours.
		@return Total Work Hours	  */
	public BigDecimal getTotalWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Employee_ID()));
    }

	/** Set Monthly Presence Summary.
		@param UNS_MonthlyPresenceSummary_ID Monthly Presence Summary	  */
	public void setUNS_MonthlyPresenceSummary_ID (int UNS_MonthlyPresenceSummary_ID)
	{
		if (UNS_MonthlyPresenceSummary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPresenceSummary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPresenceSummary_ID, Integer.valueOf(UNS_MonthlyPresenceSummary_ID));
	}

	/** Get Monthly Presence Summary.
		@return Monthly Presence Summary	  */
	public int getUNS_MonthlyPresenceSummary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MonthlyPresenceSummary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MonthlyPresenceSummary_UU.
		@param UNS_MonthlyPresenceSummary_UU UNS_MonthlyPresenceSummary_UU	  */
	public void setUNS_MonthlyPresenceSummary_UU (String UNS_MonthlyPresenceSummary_UU)
	{
		set_Value (COLUMNNAME_UNS_MonthlyPresenceSummary_UU, UNS_MonthlyPresenceSummary_UU);
	}

	/** Get UNS_MonthlyPresenceSummary_UU.
		@return UNS_MonthlyPresenceSummary_UU	  */
	public String getUNS_MonthlyPresenceSummary_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MonthlyPresenceSummary_UU);
	}

	public com.uns.model.I_UNS_YearlyPresenceSummary getUNS_YearlyPresenceSummary() throws RuntimeException
    {
		return (com.uns.model.I_UNS_YearlyPresenceSummary)MTable.get(getCtx(), com.uns.model.I_UNS_YearlyPresenceSummary.Table_Name)
			.getPO(getUNS_YearlyPresenceSummary_ID(), get_TrxName());	}

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
}