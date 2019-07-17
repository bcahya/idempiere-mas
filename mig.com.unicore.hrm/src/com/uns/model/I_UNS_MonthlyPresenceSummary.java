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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_MonthlyPresenceSummary
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_MonthlyPresenceSummary 
{

    /** TableName=UNS_MonthlyPresenceSummary */
    public static final String Table_Name = "UNS_MonthlyPresenceSummary";

    /** AD_Table_ID=1000080 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Job_ID */
    public static final String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/** Set Position.
	  * Job Position
	  */
	public void setC_Job_ID (int C_Job_ID);

	/** Get Position.
	  * Job Position
	  */
	public int getC_Job_ID();

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException;

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name GenerateDailyPresence */
    public static final String COLUMNNAME_GenerateDailyPresence = "GenerateDailyPresence";

	/** Set Generate Daily Presence	  */
	public void setGenerateDailyPresence (String GenerateDailyPresence);

	/** Get Generate Daily Presence	  */
	public String getGenerateDailyPresence();

    /** Column name HRDLevel */
    public static final String COLUMNNAME_HRDLevel = "HRDLevel";

	/** Set HRD Level	  */
	public void setHRDLevel (String HRDLevel);

	/** Get HRD Level	  */
	public String getHRDLevel();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsGenerate */
    public static final String COLUMNNAME_IsGenerate = "IsGenerate";

	/** Set IsGenerate	  */
	public void setIsGenerate (boolean IsGenerate);

	/** Get IsGenerate	  */
	public boolean isGenerate();

    /** Column name JobStatus */
    public static final String COLUMNNAME_JobStatus = "JobStatus";

	/** Set Job Status	  */
	public void setJobStatus (String JobStatus);

	/** Get Job Status	  */
	public String getJobStatus();

    /** Column name NoWorkDay */
    public static final String COLUMNNAME_NoWorkDay = "NoWorkDay";

	/** Set No Work Day	  */
	public void setNoWorkDay (String NoWorkDay);

	/** Get No Work Day	  */
	public String getNoWorkDay();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Shift */
    public static final String COLUMNNAME_Shift = "Shift";

	/** Set Shift	  */
	public void setShift (String Shift);

	/** Get Shift	  */
	public String getShift();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name TotalAbsence */
    public static final String COLUMNNAME_TotalAbsence = "TotalAbsence";

	/** Set Total Absence.
	  * The number of employee absences
	  */
	public void setTotalAbsence (int TotalAbsence);

	/** Get Total Absence.
	  * The number of employee absences
	  */
	public int getTotalAbsence();

    /** Column name TotalFullDayPresence */
    public static final String COLUMNNAME_TotalFullDayPresence = "TotalFullDayPresence";

	/** Set Total Full Day Presence	  */
	public void setTotalFullDayPresence (int TotalFullDayPresence);

	/** Get Total Full Day Presence	  */
	public int getTotalFullDayPresence();

    /** Column name TotalHalfDayPresence */
    public static final String COLUMNNAME_TotalHalfDayPresence = "TotalHalfDayPresence";

	/** Set Total Half Day Presence	  */
	public void setTotalHalfDayPresence (int TotalHalfDayPresence);

	/** Get Total Half Day Presence	  */
	public int getTotalHalfDayPresence();

    /** Column name TotalLD1 */
    public static final String COLUMNNAME_TotalLD1 = "TotalLD1";

	/** Set Total LD1	  */
	public void setTotalLD1 (BigDecimal TotalLD1);

	/** Get Total LD1	  */
	public BigDecimal getTotalLD1();

    /** Column name TotalLD2 */
    public static final String COLUMNNAME_TotalLD2 = "TotalLD2";

	/** Set Total LD2	  */
	public void setTotalLD2 (BigDecimal TotalLD2);

	/** Get Total LD2	  */
	public BigDecimal getTotalLD2();

    /** Column name TotalLD3 */
    public static final String COLUMNNAME_TotalLD3 = "TotalLD3";

	/** Set Total LD3	  */
	public void setTotalLD3 (BigDecimal TotalLD3);

	/** Get Total LD3	  */
	public BigDecimal getTotalLD3();

    /** Column name TotalNonPayableAbsence */
    public static final String COLUMNNAME_TotalNonPayableAbsence = "TotalNonPayableAbsence";

	/** Set Total Non Payable Absence.
	  * The number of employee absences with non-payable permission
	  */
	public void setTotalNonPayableAbsence (int TotalNonPayableAbsence);

	/** Get Total Non Payable Absence.
	  * The number of employee absences with non-payable permission
	  */
	public int getTotalNonPayableAbsence();

    /** Column name TotalNonPayableHalfDay */
    public static final String COLUMNNAME_TotalNonPayableHalfDay = "TotalNonPayableHalfDay";

	/** Set Total Non-Payable Half Day.
	  * Total half day absence with non-payable permission
	  */
	public void setTotalNonPayableHalfDay (int TotalNonPayableHalfDay);

	/** Get Total Non-Payable Half Day.
	  * Total half day absence with non-payable permission
	  */
	public int getTotalNonPayableHalfDay();

    /** Column name TotalOvertime */
    public static final String COLUMNNAME_TotalOvertime = "TotalOvertime";

	/** Set Total Overtime.
	  * Total emloyee's overtime in month
	  */
	public void setTotalOvertime (BigDecimal TotalOvertime);

	/** Get Total Overtime.
	  * Total emloyee's overtime in month
	  */
	public BigDecimal getTotalOvertime();

    /** Column name TotalOvertime1stHour */
    public static final String COLUMNNAME_TotalOvertime1stHour = "TotalOvertime1stHour";

	/** Set Total Overtime (1st Hour).
	  * Total 1st hour of emloyee's overtime in period
	  */
	public void setTotalOvertime1stHour (BigDecimal TotalOvertime1stHour);

	/** Get Total Overtime (1st Hour).
	  * Total 1st hour of emloyee's overtime in period
	  */
	public BigDecimal getTotalOvertime1stHour();

    /** Column name TotalOvertimeNextHour */
    public static final String COLUMNNAME_TotalOvertimeNextHour = "TotalOvertimeNextHour";

	/** Set Total Overtime (Next Hour).
	  * Total after 1st hour of emloyee's overtime in period
	  */
	public void setTotalOvertimeNextHour (BigDecimal TotalOvertimeNextHour);

	/** Get Total Overtime (Next Hour).
	  * Total after 1st hour of emloyee's overtime in period
	  */
	public BigDecimal getTotalOvertimeNextHour();

    /** Column name TotalPayableAbsence */
    public static final String COLUMNNAME_TotalPayableAbsence = "TotalPayableAbsence";

	/** Set Total Payable Absence.
	  * The number of employee absences with payable permission
	  */
	public void setTotalPayableAbsence (int TotalPayableAbsence);

	/** Get Total Payable Absence.
	  * The number of employee absences with payable permission
	  */
	public int getTotalPayableAbsence();

    /** Column name TotalPayableHalfDay */
    public static final String COLUMNNAME_TotalPayableHalfDay = "TotalPayableHalfDay";

	/** Set Total Payable Half Day.
	  * Total half day absence with payable permission
	  */
	public void setTotalPayableHalfDay (int TotalPayableHalfDay);

	/** Get Total Payable Half Day.
	  * Total half day absence with payable permission
	  */
	public int getTotalPayableHalfDay();

    /** Column name TotalSK */
    public static final String COLUMNNAME_TotalSK = "TotalSK";

	/** Set Total SK	  */
	public void setTotalSK (BigDecimal TotalSK);

	/** Get Total SK	  */
	public BigDecimal getTotalSK();

    /** Column name TotalSKK */
    public static final String COLUMNNAME_TotalSKK = "TotalSKK";

	/** Set Total SKK	  */
	public void setTotalSKK (BigDecimal TotalSKK);

	/** Get Total SKK	  */
	public BigDecimal getTotalSKK();

    /** Column name TotalWorkDay */
    public static final String COLUMNNAME_TotalWorkDay = "TotalWorkDay";

	/** Set Total Work Day	  */
	public void setTotalWorkDay (int TotalWorkDay);

	/** Get Total Work Day	  */
	public int getTotalWorkDay();

    /** Column name TotalWorkHours */
    public static final String COLUMNNAME_TotalWorkHours = "TotalWorkHours";

	/** Set Total Work Hours	  */
	public void setTotalWorkHours (BigDecimal TotalWorkHours);

	/** Get Total Work Hours	  */
	public BigDecimal getTotalWorkHours();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_MonthlyPresenceSummary_ID */
    public static final String COLUMNNAME_UNS_MonthlyPresenceSummary_ID = "UNS_MonthlyPresenceSummary_ID";

	/** Set Monthly Presence Summary	  */
	public void setUNS_MonthlyPresenceSummary_ID (int UNS_MonthlyPresenceSummary_ID);

	/** Get Monthly Presence Summary	  */
	public int getUNS_MonthlyPresenceSummary_ID();

    /** Column name UNS_MonthlyPresenceSummary_UU */
    public static final String COLUMNNAME_UNS_MonthlyPresenceSummary_UU = "UNS_MonthlyPresenceSummary_UU";

	/** Set UNS_MonthlyPresenceSummary_UU	  */
	public void setUNS_MonthlyPresenceSummary_UU (String UNS_MonthlyPresenceSummary_UU);

	/** Get UNS_MonthlyPresenceSummary_UU	  */
	public String getUNS_MonthlyPresenceSummary_UU();

    /** Column name UNS_YearlyPresenceSummary_ID */
    public static final String COLUMNNAME_UNS_YearlyPresenceSummary_ID = "UNS_YearlyPresenceSummary_ID";

	/** Set Yearly Presence Summary	  */
	public void setUNS_YearlyPresenceSummary_ID (int UNS_YearlyPresenceSummary_ID);

	/** Get Yearly Presence Summary	  */
	public int getUNS_YearlyPresenceSummary_ID();

	public com.uns.model.I_UNS_YearlyPresenceSummary getUNS_YearlyPresenceSummary() throws RuntimeException;

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
