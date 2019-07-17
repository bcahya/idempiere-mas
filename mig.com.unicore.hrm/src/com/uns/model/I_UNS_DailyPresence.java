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

/** Generated Interface for UNS_DailyPresence
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DailyPresence 
{

    /** TableName=UNS_DailyPresence */
    public static final String Table_Name = "UNS_DailyPresence";

    /** AD_Table_ID=1000079 */
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

    /** Column name AddWorkHours */
    public static final String COLUMNNAME_AddWorkHours = "AddWorkHours";

	/** Set AddWorkHours	  */
	public void setAddWorkHours (int AddWorkHours);

	/** Get AddWorkHours	  */
	public int getAddWorkHours();

    /** Column name AttendanceName */
    public static final String COLUMNNAME_AttendanceName = "AttendanceName";

	/** Set Attendance Name	  */
	public void setAttendanceName (String AttendanceName);

	/** Get Attendance Name	  */
	public String getAttendanceName();

    /** Column name BelatedDuration */
    public static final String COLUMNNAME_BelatedDuration = "BelatedDuration";

	/** Set Belated Duration	  */
	public void setBelatedDuration (int BelatedDuration);

	/** Get Belated Duration	  */
	public int getBelatedDuration();

    /** Column name ChangeDailyPresence */
    public static final String COLUMNNAME_ChangeDailyPresence = "ChangeDailyPresence";

	/** Set Change Daily Presence	  */
	public void setChangeDailyPresence (String ChangeDailyPresence);

	/** Get Change Daily Presence	  */
	public String getChangeDailyPresence();

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

    /** Column name Day */
    public static final String COLUMNNAME_Day = "Day";

	/** Set Day	  */
	public void setDay (String Day);

	/** Get Day	  */
	public String getDay();

    /** Column name DayType */
    public static final String COLUMNNAME_DayType = "DayType";

	/** Set Day Type	  */
	public void setDayType (String DayType);

	/** Get Day Type	  */
	public String getDayType();

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

    /** Column name FSTimeIn */
    public static final String COLUMNNAME_FSTimeIn = "FSTimeIn";

	/** Set FS Time In.
	  * FS Time In
	  */
	public void setFSTimeIn (Timestamp FSTimeIn);

	/** Get FS Time In.
	  * FS Time In
	  */
	public Timestamp getFSTimeIn();

    /** Column name FSTimeOut */
    public static final String COLUMNNAME_FSTimeOut = "FSTimeOut";

	/** Set FS Time Out.
	  * FS Time Out
	  */
	public void setFSTimeOut (Timestamp FSTimeOut);

	/** Get FS Time Out.
	  * FS Time Out
	  */
	public Timestamp getFSTimeOut();

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

    /** Column name IsNeedAdjustRule */
    public static final String COLUMNNAME_IsNeedAdjustRule = "IsNeedAdjustRule";

	/** Set Need Adjustment Rule	  */
	public void setIsNeedAdjustRule (boolean IsNeedAdjustRule);

	/** Get Need Adjustment Rule	  */
	public boolean isNeedAdjustRule();

    /** Column name LD1 */
    public static final String COLUMNNAME_LD1 = "LD1";

	/** Set LD1	  */
	public void setLD1 (BigDecimal LD1);

	/** Get LD1	  */
	public BigDecimal getLD1();

    /** Column name LD2 */
    public static final String COLUMNNAME_LD2 = "LD2";

	/** Set LD2	  */
	public void setLD2 (BigDecimal LD2);

	/** Get LD2	  */
	public BigDecimal getLD2();

    /** Column name LD3 */
    public static final String COLUMNNAME_LD3 = "LD3";

	/** Set LD3	  */
	public void setLD3 (BigDecimal LD3);

	/** Get LD3	  */
	public BigDecimal getLD3();

    /** Column name MaxTimeOutRule */
    public static final String COLUMNNAME_MaxTimeOutRule = "MaxTimeOutRule";

	/** Set Max. Time Out Rule	  */
	public void setMaxTimeOutRule (Timestamp MaxTimeOutRule);

	/** Get Max. Time Out Rule	  */
	public Timestamp getMaxTimeOutRule();

    /** Column name MinTimeInRule */
    public static final String COLUMNNAME_MinTimeInRule = "MinTimeInRule";

	/** Set Min. Time In Rule	  */
	public void setMinTimeInRule (Timestamp MinTimeInRule);

	/** Get Min. Time In Rule	  */
	public Timestamp getMinTimeInRule();

    /** Column name Overtime */
    public static final String COLUMNNAME_Overtime = "Overtime";

	/** Set Overtime	  */
	public void setOvertime (BigDecimal Overtime);

	/** Get Overtime	  */
	public BigDecimal getOvertime();

    /** Column name PermissionType */
    public static final String COLUMNNAME_PermissionType = "PermissionType";

	/** Set PermissionType	  */
	public void setPermissionType (String PermissionType);

	/** Get PermissionType	  */
	public String getPermissionType();

    /** Column name PresenceDate */
    public static final String COLUMNNAME_PresenceDate = "PresenceDate";

	/** Set Presence Date	  */
	public void setPresenceDate (Timestamp PresenceDate);

	/** Get Presence Date	  */
	public Timestamp getPresenceDate();

    /** Column name PresenceStatus */
    public static final String COLUMNNAME_PresenceStatus = "PresenceStatus";

	/** Set Presence Status	  */
	public void setPresenceStatus (String PresenceStatus);

	/** Get Presence Status	  */
	public String getPresenceStatus();

    /** Column name TimeInRules */
    public static final String COLUMNNAME_TimeInRules = "TimeInRules";

	/** Set TimeInRules	  */
	public void setTimeInRules (Timestamp TimeInRules);

	/** Get TimeInRules	  */
	public Timestamp getTimeInRules();

    /** Column name TimeOutRules */
    public static final String COLUMNNAME_TimeOutRules = "TimeOutRules";

	/** Set TimeOutRules	  */
	public void setTimeOutRules (Timestamp TimeOutRules);

	/** Get TimeOutRules	  */
	public Timestamp getTimeOutRules();

    /** Column name UNS_DailyPresence_ID */
    public static final String COLUMNNAME_UNS_DailyPresence_ID = "UNS_DailyPresence_ID";

	/** Set Daily Presence	  */
	public void setUNS_DailyPresence_ID (int UNS_DailyPresence_ID);

	/** Get Daily Presence	  */
	public int getUNS_DailyPresence_ID();

    /** Column name UNS_DailyPresence_UU */
    public static final String COLUMNNAME_UNS_DailyPresence_UU = "UNS_DailyPresence_UU";

	/** Set UNS_DailyPresence_UU	  */
	public void setUNS_DailyPresence_UU (String UNS_DailyPresence_UU);

	/** Get UNS_DailyPresence_UU	  */
	public String getUNS_DailyPresence_UU();

    /** Column name UNS_MonthlyPresenceSummary_ID */
    public static final String COLUMNNAME_UNS_MonthlyPresenceSummary_ID = "UNS_MonthlyPresenceSummary_ID";

	/** Set Monthly Presence Summary	  */
	public void setUNS_MonthlyPresenceSummary_ID (int UNS_MonthlyPresenceSummary_ID);

	/** Get Monthly Presence Summary	  */
	public int getUNS_MonthlyPresenceSummary_ID();

	public I_UNS_MonthlyPresenceSummary getUNS_MonthlyPresenceSummary() throws RuntimeException;

    /** Column name UNS_MonthlyPresenceVal_ID */
    public static final String COLUMNNAME_UNS_MonthlyPresenceVal_ID = "UNS_MonthlyPresenceVal_ID";

	/** Set Monthly Presence Val	  */
	public void setUNS_MonthlyPresenceVal_ID (int UNS_MonthlyPresenceVal_ID);

	/** Get Monthly Presence Val	  */
	public int getUNS_MonthlyPresenceVal_ID();

	public I_UNS_MonthlyPresenceVal getUNS_MonthlyPresenceVal() throws RuntimeException;

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

    /** Column name WorkHours */
    public static final String COLUMNNAME_WorkHours = "WorkHours";

	/** Set Work Hours	  */
	public void setWorkHours (BigDecimal WorkHours);

	/** Get Work Hours	  */
	public BigDecimal getWorkHours();
}
