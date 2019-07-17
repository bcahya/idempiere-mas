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

/** Generated Interface for UNS_LeavePermissionTrx
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_LeavePermissionTrx 
{

    /** TableName=UNS_LeavePermissionTrx */
    public static final String Table_Name = "UNS_LeavePermissionTrx";

    /** AD_Table_ID=1000149 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BackFromLeaveDate */
    public static final String COLUMNNAME_BackFromLeaveDate = "BackFromLeaveDate";

	/** Set BackFromLeaveDate	  */
	public void setBackFromLeaveDate (Timestamp BackFromLeaveDate);

	/** Get BackFromLeaveDate	  */
	public Timestamp getBackFromLeaveDate();

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

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name EmploymentType */
    public static final String COLUMNNAME_EmploymentType = "EmploymentType";

	/** Set Employment Type	  */
	public void setEmploymentType (String EmploymentType);

	/** Get Employment Type	  */
	public String getEmploymentType();

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

    /** Column name JobCareTaker_ID */
    public static final String COLUMNNAME_JobCareTaker_ID = "JobCareTaker_ID";

	/** Set Job Care Taker	  */
	public void setJobCareTaker_ID (int JobCareTaker_ID);

	/** Get Job Care Taker	  */
	public int getJobCareTaker_ID();

	public com.uns.model.I_UNS_Employee getJobCareTaker() throws RuntimeException;

    /** Column name LastLeaveUsed */
    public static final String COLUMNNAME_LastLeaveUsed = "LastLeaveUsed";

	/** Set Last Leave Used	  */
	public void setLastLeaveUsed (BigDecimal LastLeaveUsed);

	/** Get Last Leave Used	  */
	public BigDecimal getLastLeaveUsed();

    /** Column name LeaveDateEnd */
    public static final String COLUMNNAME_LeaveDateEnd = "LeaveDateEnd";

	/** Set Leave Date End	  */
	public void setLeaveDateEnd (Timestamp LeaveDateEnd);

	/** Get Leave Date End	  */
	public Timestamp getLeaveDateEnd();

    /** Column name LeaveDateStart */
    public static final String COLUMNNAME_LeaveDateStart = "LeaveDateStart";

	/** Set Leave Date Start	  */
	public void setLeaveDateStart (Timestamp LeaveDateStart);

	/** Get Leave Date Start	  */
	public Timestamp getLeaveDateStart();

    /** Column name LeaveDestination */
    public static final String COLUMNNAME_LeaveDestination = "LeaveDestination";

	/** Set Leave Destination	  */
	public void setLeaveDestination (String LeaveDestination);

	/** Get Leave Destination	  */
	public String getLeaveDestination();

    /** Column name LeavePeriodType */
    public static final String COLUMNNAME_LeavePeriodType = "LeavePeriodType";

	/** Set Leave Period Type.
	  * Select one of the list based on the requested period.
	  */
	public void setLeavePeriodType (String LeavePeriodType);

	/** Get Leave Period Type.
	  * Select one of the list based on the requested period.
	  */
	public String getLeavePeriodType();

    /** Column name LeaveRequested */
    public static final String COLUMNNAME_LeaveRequested = "LeaveRequested";

	/** Set Leave Requested	  */
	public void setLeaveRequested (BigDecimal LeaveRequested);

	/** Get Leave Requested	  */
	public BigDecimal getLeaveRequested();

    /** Column name LeaveType */
    public static final String COLUMNNAME_LeaveType = "LeaveType";

	/** Set Leave Type	  */
	public void setLeaveType (String LeaveType);

	/** Get Leave Type	  */
	public String getLeaveType();

    /** Column name OtherRemarks */
    public static final String COLUMNNAME_OtherRemarks = "OtherRemarks";

	/** Set Other Remarks	  */
	public void setOtherRemarks (String OtherRemarks);

	/** Get Other Remarks	  */
	public String getOtherRemarks();

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

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name TotalLeaveClaimReserved */
    public static final String COLUMNNAME_TotalLeaveClaimReserved = "TotalLeaveClaimReserved";

	/** Set Total Leave Claim Reserved	  */
	public void setTotalLeaveClaimReserved (BigDecimal TotalLeaveClaimReserved);

	/** Get Total Leave Claim Reserved	  */
	public BigDecimal getTotalLeaveClaimReserved();

    /** Column name UNS_DispensationConfig_ID */
    public static final String COLUMNNAME_UNS_DispensationConfig_ID = "UNS_DispensationConfig_ID";

	/** Set Dispensation	  */
	public void setUNS_DispensationConfig_ID (int UNS_DispensationConfig_ID);

	/** Get Dispensation	  */
	public int getUNS_DispensationConfig_ID();

	public com.uns.model.I_UNS_DispensationConfig getUNS_DispensationConfig() throws RuntimeException;

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_LeavePermissionTrx_ID */
    public static final String COLUMNNAME_UNS_LeavePermissionTrx_ID = "UNS_LeavePermissionTrx_ID";

	/** Set UNS_LeavePermissionTrx	  */
	public void setUNS_LeavePermissionTrx_ID (int UNS_LeavePermissionTrx_ID);

	/** Get UNS_LeavePermissionTrx	  */
	public int getUNS_LeavePermissionTrx_ID();

    /** Column name UNS_LeavePermissionTrx_UU */
    public static final String COLUMNNAME_UNS_LeavePermissionTrx_UU = "UNS_LeavePermissionTrx_UU";

	/** Set UNS_LeavePermissionTrx_UU	  */
	public void setUNS_LeavePermissionTrx_UU (String UNS_LeavePermissionTrx_UU);

	/** Get UNS_LeavePermissionTrx_UU	  */
	public String getUNS_LeavePermissionTrx_UU();

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
