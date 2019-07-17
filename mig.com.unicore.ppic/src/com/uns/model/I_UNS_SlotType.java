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

/** Generated Interface for UNS_SlotType
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_SlotType 
{

    /** TableName=UNS_SlotType */
    public static final String Table_Name = "UNS_SlotType";

    /** AD_Table_ID=1000057 */
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

    /** Column name AutoAddOvertime */
    public static final String COLUMNNAME_AutoAddOvertime = "AutoAddOvertime";

	/** Set Auto Add Overtime	  */
	public void setAutoAddOvertime (boolean AutoAddOvertime);

	/** Get Auto Add Overtime	  */
	public boolean isAutoAddOvertime();

    /** Column name BreakTimeFriday */
    public static final String COLUMNNAME_BreakTimeFriday = "BreakTimeFriday";

	/** Set Break Time Friday	  */
	public void setBreakTimeFriday (BigDecimal BreakTimeFriday);

	/** Get Break Time Friday	  */
	public BigDecimal getBreakTimeFriday();

    /** Column name BreakTimeMonday */
    public static final String COLUMNNAME_BreakTimeMonday = "BreakTimeMonday";

	/** Set Break Time Monday	  */
	public void setBreakTimeMonday (BigDecimal BreakTimeMonday);

	/** Get Break Time Monday	  */
	public BigDecimal getBreakTimeMonday();

    /** Column name BreakTimeSaturday */
    public static final String COLUMNNAME_BreakTimeSaturday = "BreakTimeSaturday";

	/** Set Break Time Saturday	  */
	public void setBreakTimeSaturday (BigDecimal BreakTimeSaturday);

	/** Get Break Time Saturday	  */
	public BigDecimal getBreakTimeSaturday();

    /** Column name BreakTimeSunday */
    public static final String COLUMNNAME_BreakTimeSunday = "BreakTimeSunday";

	/** Set Break Time Sunday	  */
	public void setBreakTimeSunday (BigDecimal BreakTimeSunday);

	/** Get Break Time Sunday	  */
	public BigDecimal getBreakTimeSunday();

    /** Column name BreakTimeThursday */
    public static final String COLUMNNAME_BreakTimeThursday = "BreakTimeThursday";

	/** Set Break Time Thursday	  */
	public void setBreakTimeThursday (BigDecimal BreakTimeThursday);

	/** Get Break Time Thursday	  */
	public BigDecimal getBreakTimeThursday();

    /** Column name BreakTimeTuesday */
    public static final String COLUMNNAME_BreakTimeTuesday = "BreakTimeTuesday";

	/** Set Break Time Tuesday	  */
	public void setBreakTimeTuesday (BigDecimal BreakTimeTuesday);

	/** Get Break Time Tuesday	  */
	public BigDecimal getBreakTimeTuesday();

    /** Column name BreakTimeWednesday */
    public static final String COLUMNNAME_BreakTimeWednesday = "BreakTimeWednesday";

	/** Set Break Time Wednesday	  */
	public void setBreakTimeWednesday (BigDecimal BreakTimeWednesday);

	/** Get Break Time Wednesday	  */
	public BigDecimal getBreakTimeWednesday();

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

    /** Column name isAutoSynchronize */
    public static final String COLUMNNAME_isAutoSynchronize = "isAutoSynchronize";

	/** Set Auto Synchronize	  */
	public void setisAutoSynchronize (boolean isAutoSynchronize);

	/** Get Auto Synchronize	  */
	public boolean isAutoSynchronize();

    /** Column name IsDaySlot */
    public static final String COLUMNNAME_IsDaySlot = "IsDaySlot";

	/** Set Day Slot	  */
	public void setIsDaySlot (boolean IsDaySlot);

	/** Get Day Slot	  */
	public boolean isDaySlot();

    /** Column name IsTimeSlot */
    public static final String COLUMNNAME_IsTimeSlot = "IsTimeSlot";

	/** Set Time Slot.
	  * Resource has time slot availability
	  */
	public void setIsTimeSlot (boolean IsTimeSlot);

	/** Get Time Slot.
	  * Resource has time slot availability
	  */
	public boolean isTimeSlot();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NormalDayWorkHours */
    public static final String COLUMNNAME_NormalDayWorkHours = "NormalDayWorkHours";

	/** Set Normal Day Work Hours	  */
	public void setNormalDayWorkHours (BigDecimal NormalDayWorkHours);

	/** Get Normal Day Work Hours	  */
	public BigDecimal getNormalDayWorkHours();

    /** Column name OnFriday */
    public static final String COLUMNNAME_OnFriday = "OnFriday";

	/** Set Friday.
	  * Available on Fridays
	  */
	public void setOnFriday (boolean OnFriday);

	/** Get Friday.
	  * Available on Fridays
	  */
	public boolean isOnFriday();

    /** Column name OnMonday */
    public static final String COLUMNNAME_OnMonday = "OnMonday";

	/** Set Monday.
	  * Available on Mondays
	  */
	public void setOnMonday (boolean OnMonday);

	/** Get Monday.
	  * Available on Mondays
	  */
	public boolean isOnMonday();

    /** Column name OnSaturday */
    public static final String COLUMNNAME_OnSaturday = "OnSaturday";

	/** Set Saturday.
	  * Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday);

	/** Get Saturday.
	  * Available on Saturday
	  */
	public boolean isOnSaturday();

    /** Column name OnSunday */
    public static final String COLUMNNAME_OnSunday = "OnSunday";

	/** Set Sunday.
	  * Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday);

	/** Get Sunday.
	  * Available on Sundays
	  */
	public boolean isOnSunday();

    /** Column name OnThursday */
    public static final String COLUMNNAME_OnThursday = "OnThursday";

	/** Set Thursday.
	  * Available on Thursdays
	  */
	public void setOnThursday (boolean OnThursday);

	/** Get Thursday.
	  * Available on Thursdays
	  */
	public boolean isOnThursday();

    /** Column name OnTuesday */
    public static final String COLUMNNAME_OnTuesday = "OnTuesday";

	/** Set Tuesday.
	  * Available on Tuesdays
	  */
	public void setOnTuesday (boolean OnTuesday);

	/** Get Tuesday.
	  * Available on Tuesdays
	  */
	public boolean isOnTuesday();

    /** Column name OnWednesday */
    public static final String COLUMNNAME_OnWednesday = "OnWednesday";

	/** Set Wednesday.
	  * Available on Wednesdays
	  */
	public void setOnWednesday (boolean OnWednesday);

	/** Get Wednesday.
	  * Available on Wednesdays
	  */
	public boolean isOnWednesday();

    /** Column name PreparationTimeFriday */
    public static final String COLUMNNAME_PreparationTimeFriday = "PreparationTimeFriday";

	/** Set Preparation Time Friday	  */
	public void setPreparationTimeFriday (BigDecimal PreparationTimeFriday);

	/** Get Preparation Time Friday	  */
	public BigDecimal getPreparationTimeFriday();

    /** Column name PreparationTimeMonday */
    public static final String COLUMNNAME_PreparationTimeMonday = "PreparationTimeMonday";

	/** Set Preparation Time Monday	  */
	public void setPreparationTimeMonday (BigDecimal PreparationTimeMonday);

	/** Get Preparation Time Monday	  */
	public BigDecimal getPreparationTimeMonday();

    /** Column name PreparationTimeSaturday */
    public static final String COLUMNNAME_PreparationTimeSaturday = "PreparationTimeSaturday";

	/** Set Preparation Time Saturday	  */
	public void setPreparationTimeSaturday (BigDecimal PreparationTimeSaturday);

	/** Get Preparation Time Saturday	  */
	public BigDecimal getPreparationTimeSaturday();

    /** Column name PreparationTimeSunday */
    public static final String COLUMNNAME_PreparationTimeSunday = "PreparationTimeSunday";

	/** Set Preparation Time Sunday	  */
	public void setPreparationTimeSunday (BigDecimal PreparationTimeSunday);

	/** Get Preparation Time Sunday	  */
	public BigDecimal getPreparationTimeSunday();

    /** Column name PreparationTimeThursday */
    public static final String COLUMNNAME_PreparationTimeThursday = "PreparationTimeThursday";

	/** Set Preparation Time Thursday	  */
	public void setPreparationTimeThursday (BigDecimal PreparationTimeThursday);

	/** Get Preparation Time Thursday	  */
	public BigDecimal getPreparationTimeThursday();

    /** Column name PreparationTimeTuesday */
    public static final String COLUMNNAME_PreparationTimeTuesday = "PreparationTimeTuesday";

	/** Set Preparation Time Tuesday	  */
	public void setPreparationTimeTuesday (BigDecimal PreparationTimeTuesday);

	/** Get Preparation Time Tuesday	  */
	public BigDecimal getPreparationTimeTuesday();

    /** Column name PreparationTimeWednesday */
    public static final String COLUMNNAME_PreparationTimeWednesday = "PreparationTimeWednesday";

	/** Set Preparation Time Wednesday	  */
	public void setPreparationTimeWednesday (BigDecimal PreparationTimeWednesday);

	/** Get Preparation Time Wednesday	  */
	public BigDecimal getPreparationTimeWednesday();

    /** Column name ShortDayWorkHours */
    public static final String COLUMNNAME_ShortDayWorkHours = "ShortDayWorkHours";

	/** Set Short Day Work Hours	  */
	public void setShortDayWorkHours (BigDecimal ShortDayWorkHours);

	/** Get Short Day Work Hours	  */
	public BigDecimal getShortDayWorkHours();

    /** Column name TimeSlotEnd */
    public static final String COLUMNNAME_TimeSlotEnd = "TimeSlotEnd";

	/** Set Slot End.
	  * Time when timeslot ends
	  */
	public void setTimeSlotEnd (Timestamp TimeSlotEnd);

	/** Get Slot End.
	  * Time when timeslot ends
	  */
	public Timestamp getTimeSlotEnd();

    /** Column name TimeSlotEndFriday */
    public static final String COLUMNNAME_TimeSlotEndFriday = "TimeSlotEndFriday";

	/** Set Time End Friday	  */
	public void setTimeSlotEndFriday (Timestamp TimeSlotEndFriday);

	/** Get Time End Friday	  */
	public Timestamp getTimeSlotEndFriday();

    /** Column name TimeSlotEndMonday */
    public static final String COLUMNNAME_TimeSlotEndMonday = "TimeSlotEndMonday";

	/** Set Time End Monday	  */
	public void setTimeSlotEndMonday (Timestamp TimeSlotEndMonday);

	/** Get Time End Monday	  */
	public Timestamp getTimeSlotEndMonday();

    /** Column name TimeSlotEndSaturday */
    public static final String COLUMNNAME_TimeSlotEndSaturday = "TimeSlotEndSaturday";

	/** Set Time End Saturday	  */
	public void setTimeSlotEndSaturday (Timestamp TimeSlotEndSaturday);

	/** Get Time End Saturday	  */
	public Timestamp getTimeSlotEndSaturday();

    /** Column name TimeSlotEndSunday */
    public static final String COLUMNNAME_TimeSlotEndSunday = "TimeSlotEndSunday";

	/** Set Time End Sunday	  */
	public void setTimeSlotEndSunday (Timestamp TimeSlotEndSunday);

	/** Get Time End Sunday	  */
	public Timestamp getTimeSlotEndSunday();

    /** Column name TimeSlotEndThursday */
    public static final String COLUMNNAME_TimeSlotEndThursday = "TimeSlotEndThursday";

	/** Set Time End Thursday	  */
	public void setTimeSlotEndThursday (Timestamp TimeSlotEndThursday);

	/** Get Time End Thursday	  */
	public Timestamp getTimeSlotEndThursday();

    /** Column name TimeSlotEndTuesday */
    public static final String COLUMNNAME_TimeSlotEndTuesday = "TimeSlotEndTuesday";

	/** Set Time End Tuesday	  */
	public void setTimeSlotEndTuesday (Timestamp TimeSlotEndTuesday);

	/** Get Time End Tuesday	  */
	public Timestamp getTimeSlotEndTuesday();

    /** Column name TimeSlotEndWednesday */
    public static final String COLUMNNAME_TimeSlotEndWednesday = "TimeSlotEndWednesday";

	/** Set Time End Wednesday	  */
	public void setTimeSlotEndWednesday (Timestamp TimeSlotEndWednesday);

	/** Get Time End Wednesday	  */
	public Timestamp getTimeSlotEndWednesday();

    /** Column name TimeSlotStart */
    public static final String COLUMNNAME_TimeSlotStart = "TimeSlotStart";

	/** Set Slot Start.
	  * Time when timeslot starts
	  */
	public void setTimeSlotStart (Timestamp TimeSlotStart);

	/** Get Slot Start.
	  * Time when timeslot starts
	  */
	public Timestamp getTimeSlotStart();

    /** Column name TimeSlotStartFriday */
    public static final String COLUMNNAME_TimeSlotStartFriday = "TimeSlotStartFriday";

	/** Set Time Start Friday	  */
	public void setTimeSlotStartFriday (Timestamp TimeSlotStartFriday);

	/** Get Time Start Friday	  */
	public Timestamp getTimeSlotStartFriday();

    /** Column name TimeSlotStartMonday */
    public static final String COLUMNNAME_TimeSlotStartMonday = "TimeSlotStartMonday";

	/** Set Time Start Monday	  */
	public void setTimeSlotStartMonday (Timestamp TimeSlotStartMonday);

	/** Get Time Start Monday	  */
	public Timestamp getTimeSlotStartMonday();

    /** Column name TimeSlotStartSaturday */
    public static final String COLUMNNAME_TimeSlotStartSaturday = "TimeSlotStartSaturday";

	/** Set Time Start Saturday	  */
	public void setTimeSlotStartSaturday (Timestamp TimeSlotStartSaturday);

	/** Get Time Start Saturday	  */
	public Timestamp getTimeSlotStartSaturday();

    /** Column name TimeSlotStartSunday */
    public static final String COLUMNNAME_TimeSlotStartSunday = "TimeSlotStartSunday";

	/** Set Time Start Sunday	  */
	public void setTimeSlotStartSunday (Timestamp TimeSlotStartSunday);

	/** Get Time Start Sunday	  */
	public Timestamp getTimeSlotStartSunday();

    /** Column name TimeSlotStartThursday */
    public static final String COLUMNNAME_TimeSlotStartThursday = "TimeSlotStartThursday";

	/** Set Time Start Thursday	  */
	public void setTimeSlotStartThursday (Timestamp TimeSlotStartThursday);

	/** Get Time Start Thursday	  */
	public Timestamp getTimeSlotStartThursday();

    /** Column name TimeSlotStartTuesday */
    public static final String COLUMNNAME_TimeSlotStartTuesday = "TimeSlotStartTuesday";

	/** Set Time Start Tuesday	  */
	public void setTimeSlotStartTuesday (Timestamp TimeSlotStartTuesday);

	/** Get Time Start Tuesday	  */
	public Timestamp getTimeSlotStartTuesday();

    /** Column name TimeSlotStartWednesday */
    public static final String COLUMNNAME_TimeSlotStartWednesday = "TimeSlotStartWednesday";

	/** Set Time Start Wednesday	  */
	public void setTimeSlotStartWednesday (Timestamp TimeSlotStartWednesday);

	/** Get Time Start Wednesday	  */
	public Timestamp getTimeSlotStartWednesday();

    /** Column name UNS_AttConfiguration_ID */
    public static final String COLUMNNAME_UNS_AttConfiguration_ID = "UNS_AttConfiguration_ID";

	/** Set UNS_AttConfiguration_ID	  */
	public void setUNS_AttConfiguration_ID (int UNS_AttConfiguration_ID);

	/** Get UNS_AttConfiguration_ID	  */
	public int getUNS_AttConfiguration_ID();

    /** Column name UNS_SlotType_ID */
    public static final String COLUMNNAME_UNS_SlotType_ID = "UNS_SlotType_ID";

	/** Set Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID);

	/** Get Slot Type	  */
	public int getUNS_SlotType_ID();

    /** Column name UNS_SlotType_UU */
    public static final String COLUMNNAME_UNS_SlotType_UU = "UNS_SlotType_UU";

	/** Set Slot Type UU	  */
	public void setUNS_SlotType_UU (String UNS_SlotType_UU);

	/** Get Slot Type UU	  */
	public String getUNS_SlotType_UU();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name WorkHoursFriday */
    public static final String COLUMNNAME_WorkHoursFriday = "WorkHoursFriday";

	/** Set Work Hours Friday	  */
	public void setWorkHoursFriday (BigDecimal WorkHoursFriday);

	/** Get Work Hours Friday	  */
	public BigDecimal getWorkHoursFriday();

    /** Column name WorkHoursMonday */
    public static final String COLUMNNAME_WorkHoursMonday = "WorkHoursMonday";

	/** Set Work Hours Monday	  */
	public void setWorkHoursMonday (BigDecimal WorkHoursMonday);

	/** Get Work Hours Monday	  */
	public BigDecimal getWorkHoursMonday();

    /** Column name WorkHoursSaturday */
    public static final String COLUMNNAME_WorkHoursSaturday = "WorkHoursSaturday";

	/** Set Work Hours Saturday	  */
	public void setWorkHoursSaturday (BigDecimal WorkHoursSaturday);

	/** Get Work Hours Saturday	  */
	public BigDecimal getWorkHoursSaturday();

    /** Column name WorkHoursStatus */
    public static final String COLUMNNAME_WorkHoursStatus = "WorkHoursStatus";

	/** Set Work Hours Status	  */
	public void setWorkHoursStatus (String WorkHoursStatus);

	/** Get Work Hours Status	  */
	public String getWorkHoursStatus();

    /** Column name WorkHoursSunday */
    public static final String COLUMNNAME_WorkHoursSunday = "WorkHoursSunday";

	/** Set Work Hours Sunday	  */
	public void setWorkHoursSunday (BigDecimal WorkHoursSunday);

	/** Get Work Hours Sunday	  */
	public BigDecimal getWorkHoursSunday();

    /** Column name WorkHoursThursday */
    public static final String COLUMNNAME_WorkHoursThursday = "WorkHoursThursday";

	/** Set Work Hours Thursday	  */
	public void setWorkHoursThursday (BigDecimal WorkHoursThursday);

	/** Get Work Hours Thursday	  */
	public BigDecimal getWorkHoursThursday();

    /** Column name WorkHoursTuesday */
    public static final String COLUMNNAME_WorkHoursTuesday = "WorkHoursTuesday";

	/** Set Work Hours Tuesday	  */
	public void setWorkHoursTuesday (BigDecimal WorkHoursTuesday);

	/** Get Work Hours Tuesday	  */
	public BigDecimal getWorkHoursTuesday();

    /** Column name WorkHoursWednesday */
    public static final String COLUMNNAME_WorkHoursWednesday = "WorkHoursWednesday";

	/** Set Work Hours Wednesday	  */
	public void setWorkHoursWednesday (BigDecimal WorkHoursWednesday);

	/** Get Work Hours Wednesday	  */
	public BigDecimal getWorkHoursWednesday();
}
