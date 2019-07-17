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

/** Generated Model for UNS_LeavePermissionTrx
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LeavePermissionTrx extends PO implements I_UNS_LeavePermissionTrx, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140224L;

    /** Standard Constructor */
    public X_UNS_LeavePermissionTrx (Properties ctx, int UNS_LeavePermissionTrx_ID, String trxName)
    {
      super (ctx, UNS_LeavePermissionTrx_ID, trxName);
      /** if (UNS_LeavePermissionTrx_ID == 0)
        {
			setC_Period_ID (0);
			setC_Year_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setEmploymentType (null);
			setLastLeaveUsed (Env.ZERO);
// 0
			setLeaveDateEnd (new Timestamp( System.currentTimeMillis() ));
			setLeaveDateStart (new Timestamp( System.currentTimeMillis() ));
			setLeaveRequested (Env.ZERO);
// 1
			setLeaveType (null);
			setProcessed (false);
// N
			setUNS_Employee_ID (0);
			setUNS_LeavePermissionTrx_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LeavePermissionTrx (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LeavePermissionTrx[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BackFromLeaveDate.
		@param BackFromLeaveDate BackFromLeaveDate	  */
	public void setBackFromLeaveDate (Timestamp BackFromLeaveDate)
	{
		set_Value (COLUMNNAME_BackFromLeaveDate, BackFromLeaveDate);
	}

	/** Get BackFromLeaveDate.
		@return BackFromLeaveDate	  */
	public Timestamp getBackFromLeaveDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BackFromLeaveDate);
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

	/** EmploymentType AD_Reference_ID=1000089 */
	public static final int EMPLOYMENTTYPE_AD_Reference_ID=1000089;
	/** Sub Contract = SUB */
	public static final String EMPLOYMENTTYPE_SubContract = "SUB";
	/** Company = COM */
	public static final String EMPLOYMENTTYPE_Company = "COM";
	/** Set Employment Type.
		@param EmploymentType Employment Type	  */
	public void setEmploymentType (String EmploymentType)
	{

		set_Value (COLUMNNAME_EmploymentType, EmploymentType);
	}

	/** Get Employment Type.
		@return Employment Type	  */
	public String getEmploymentType () 
	{
		return (String)get_Value(COLUMNNAME_EmploymentType);
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

	public com.uns.model.I_UNS_Employee getJobCareTaker() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getJobCareTaker_ID(), get_TrxName());	}

	/** Set Job Care Taker.
		@param JobCareTaker_ID Job Care Taker	  */
	public void setJobCareTaker_ID (int JobCareTaker_ID)
	{
		if (JobCareTaker_ID < 1) 
			set_Value (COLUMNNAME_JobCareTaker_ID, null);
		else 
			set_Value (COLUMNNAME_JobCareTaker_ID, Integer.valueOf(JobCareTaker_ID));
	}

	/** Get Job Care Taker.
		@return Job Care Taker	  */
	public int getJobCareTaker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JobCareTaker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Leave Used.
		@param LastLeaveUsed Last Leave Used	  */
	public void setLastLeaveUsed (BigDecimal LastLeaveUsed)
	{
		set_Value (COLUMNNAME_LastLeaveUsed, LastLeaveUsed);
	}

	/** Get Last Leave Used.
		@return Last Leave Used	  */
	public BigDecimal getLastLeaveUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastLeaveUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Leave Date End.
		@param LeaveDateEnd Leave Date End	  */
	public void setLeaveDateEnd (Timestamp LeaveDateEnd)
	{
		set_Value (COLUMNNAME_LeaveDateEnd, LeaveDateEnd);
	}

	/** Get Leave Date End.
		@return Leave Date End	  */
	public Timestamp getLeaveDateEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LeaveDateEnd);
	}

	/** Set Leave Date Start.
		@param LeaveDateStart Leave Date Start	  */
	public void setLeaveDateStart (Timestamp LeaveDateStart)
	{
		set_Value (COLUMNNAME_LeaveDateStart, LeaveDateStart);
	}

	/** Get Leave Date Start.
		@return Leave Date Start	  */
	public Timestamp getLeaveDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LeaveDateStart);
	}

	/** Set Leave Destination.
		@param LeaveDestination Leave Destination	  */
	public void setLeaveDestination (String LeaveDestination)
	{
		set_Value (COLUMNNAME_LeaveDestination, LeaveDestination);
	}

	/** Get Leave Destination.
		@return Leave Destination	  */
	public String getLeaveDestination () 
	{
		return (String)get_Value(COLUMNNAME_LeaveDestination);
	}

	/** LeavePeriodType AD_Reference_ID=1000198 */
	public static final int LEAVEPERIODTYPE_AD_Reference_ID=1000198;
	/** Full Day = AFD */
	public static final String LEAVEPERIODTYPE_FullDay = "AFD";
	/** Start Date is Half Day = SHD */
	public static final String LEAVEPERIODTYPE_StartDateIsHalfDay = "SHD";
	/** End Date is Half Day = EHD */
	public static final String LEAVEPERIODTYPE_EndDateIsHalfDay = "EHD";
	/** Start and End Date is Half Day = SEHD */
	public static final String LEAVEPERIODTYPE_StartAndEndDateIsHalfDay = "SEHD";
	/** Set Leave Period Type.
		@param LeavePeriodType 
		Select one of the list based on the requested period.
	  */
	public void setLeavePeriodType (String LeavePeriodType)
	{

		set_Value (COLUMNNAME_LeavePeriodType, LeavePeriodType);
	}

	/** Get Leave Period Type.
		@return Select one of the list based on the requested period.
	  */
	public String getLeavePeriodType () 
	{
		return (String)get_Value(COLUMNNAME_LeavePeriodType);
	}

	/** Set Leave Requested.
		@param LeaveRequested Leave Requested	  */
	public void setLeaveRequested (BigDecimal LeaveRequested)
	{
		set_Value (COLUMNNAME_LeaveRequested, LeaveRequested);
	}

	/** Get Leave Requested.
		@return Leave Requested	  */
	public BigDecimal getLeaveRequested () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LeaveRequested);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** LeaveType AD_Reference_ID=1000075 */
	public static final int LEAVETYPE_AD_Reference_ID=1000075;
	/** Leave / Cuti = LCTI */
	public static final String LEAVETYPE_LeaveCuti = "LCTI";
	/** Pay Permission / Izin Potong Gaji = PPAY */
	public static final String LEAVETYPE_PayPermissionIzinPotongGaji = "PPAY";
	/** Permission (Dispensation) / Izin Dibayar = PMDB */
	public static final String LEAVETYPE_PermissionDispensationIzinDibayar = "PMDB";
	/** Maternity / Hamil+Melahirkan = MLHR */
	public static final String LEAVETYPE_MaternityHamilPlusMelahirkan = "MLHR";
	/** Permission (Dinas) = PMDN */
	public static final String LEAVETYPE_PermissionDinas = "PMDN";
	/** Surat Keterangan Istirahat = SKI */
	public static final String LEAVETYPE_SuratKeteranganIstirahat = "SKI";
	/** Surat Keterangan Istirahat Kecelakaan Kerja = SKIKK */
	public static final String LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja = "SKIKK";
	/** Other = OTHR */
	public static final String LEAVETYPE_Other = "OTHR";
	/** Set Leave Type.
		@param LeaveType Leave Type	  */
	public void setLeaveType (String LeaveType)
	{

		set_Value (COLUMNNAME_LeaveType, LeaveType);
	}

	/** Get Leave Type.
		@return Leave Type	  */
	public String getLeaveType () 
	{
		return (String)get_Value(COLUMNNAME_LeaveType);
	}

	/** Set Other Remarks.
		@param OtherRemarks Other Remarks	  */
	public void setOtherRemarks (String OtherRemarks)
	{
		set_Value (COLUMNNAME_OtherRemarks, OtherRemarks);
	}

	/** Get Other Remarks.
		@return Other Remarks	  */
	public String getOtherRemarks () 
	{
		return (String)get_Value(COLUMNNAME_OtherRemarks);
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

	/** Set Total Leave Claim Reserved.
		@param TotalLeaveClaimReserved Total Leave Claim Reserved	  */
	public void setTotalLeaveClaimReserved (BigDecimal TotalLeaveClaimReserved)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLeaveClaimReserved, TotalLeaveClaimReserved);
	}

	/** Get Total Leave Claim Reserved.
		@return Total Leave Claim Reserved	  */
	public BigDecimal getTotalLeaveClaimReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLeaveClaimReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_DispensationConfig getUNS_DispensationConfig() throws RuntimeException
    {
		return (com.uns.model.I_UNS_DispensationConfig)MTable.get(getCtx(), com.uns.model.I_UNS_DispensationConfig.Table_Name)
			.getPO(getUNS_DispensationConfig_ID(), get_TrxName());	}

	/** Set Dispensation.
		@param UNS_DispensationConfig_ID Dispensation	  */
	public void setUNS_DispensationConfig_ID (int UNS_DispensationConfig_ID)
	{
		if (UNS_DispensationConfig_ID < 1) 
			set_Value (COLUMNNAME_UNS_DispensationConfig_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_DispensationConfig_ID, Integer.valueOf(UNS_DispensationConfig_ID));
	}

	/** Get Dispensation.
		@return Dispensation	  */
	public int getUNS_DispensationConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DispensationConfig_ID);
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

	/** Set UNS_LeavePermissionTrx.
		@param UNS_LeavePermissionTrx_ID UNS_LeavePermissionTrx	  */
	public void setUNS_LeavePermissionTrx_ID (int UNS_LeavePermissionTrx_ID)
	{
		if (UNS_LeavePermissionTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LeavePermissionTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LeavePermissionTrx_ID, Integer.valueOf(UNS_LeavePermissionTrx_ID));
	}

	/** Get UNS_LeavePermissionTrx.
		@return UNS_LeavePermissionTrx	  */
	public int getUNS_LeavePermissionTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LeavePermissionTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LeavePermissionTrx_UU.
		@param UNS_LeavePermissionTrx_UU UNS_LeavePermissionTrx_UU	  */
	public void setUNS_LeavePermissionTrx_UU (String UNS_LeavePermissionTrx_UU)
	{
		set_Value (COLUMNNAME_UNS_LeavePermissionTrx_UU, UNS_LeavePermissionTrx_UU);
	}

	/** Get UNS_LeavePermissionTrx_UU.
		@return UNS_LeavePermissionTrx_UU	  */
	public String getUNS_LeavePermissionTrx_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LeavePermissionTrx_UU);
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