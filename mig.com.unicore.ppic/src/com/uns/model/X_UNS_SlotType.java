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

/** Generated Model for UNS_SlotType
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SlotType extends PO implements I_UNS_SlotType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180228L;

    /** Standard Constructor */
    public X_UNS_SlotType (Properties ctx, int UNS_SlotType_ID, String trxName)
    {
      super (ctx, UNS_SlotType_ID, trxName);
      /** if (UNS_SlotType_ID == 0)
        {
			setAutoAddOvertime (false);
// N
			setisAutoSynchronize (true);
// Y
			setIsDaySlot (false);
			setIsTimeSlot (false);
			setName (null);
			setNormalDayWorkHours (Env.ZERO);
// 8
			setOnFriday (false);
			setOnMonday (false);
			setOnSaturday (false);
			setOnSunday (false);
			setOnThursday (false);
			setOnTuesday (false);
			setOnWednesday (false);
			setShortDayWorkHours (Env.ZERO);
// 6
			setUNS_SlotType_ID (0);
			setValue (null);
			setWorkHoursFriday (Env.ZERO);
// 0
			setWorkHoursMonday (Env.ZERO);
// 0
			setWorkHoursSaturday (Env.ZERO);
// 0
			setWorkHoursStatus (null);
// UGW
			setWorkHoursSunday (Env.ZERO);
// 0
			setWorkHoursThursday (Env.ZERO);
// 0
			setWorkHoursTuesday (Env.ZERO);
// 0
			setWorkHoursWednesday (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_SlotType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SlotType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Auto Add Overtime.
		@param AutoAddOvertime Auto Add Overtime	  */
	public void setAutoAddOvertime (boolean AutoAddOvertime)
	{
		set_Value (COLUMNNAME_AutoAddOvertime, Boolean.valueOf(AutoAddOvertime));
	}

	/** Get Auto Add Overtime.
		@return Auto Add Overtime	  */
	public boolean isAutoAddOvertime () 
	{
		Object oo = get_Value(COLUMNNAME_AutoAddOvertime);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Break Time Friday.
		@param BreakTimeFriday Break Time Friday	  */
	public void setBreakTimeFriday (BigDecimal BreakTimeFriday)
	{
		set_Value (COLUMNNAME_BreakTimeFriday, BreakTimeFriday);
	}

	/** Get Break Time Friday.
		@return Break Time Friday	  */
	public BigDecimal getBreakTimeFriday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeFriday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Monday.
		@param BreakTimeMonday Break Time Monday	  */
	public void setBreakTimeMonday (BigDecimal BreakTimeMonday)
	{
		set_Value (COLUMNNAME_BreakTimeMonday, BreakTimeMonday);
	}

	/** Get Break Time Monday.
		@return Break Time Monday	  */
	public BigDecimal getBreakTimeMonday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeMonday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Saturday.
		@param BreakTimeSaturday Break Time Saturday	  */
	public void setBreakTimeSaturday (BigDecimal BreakTimeSaturday)
	{
		set_Value (COLUMNNAME_BreakTimeSaturday, BreakTimeSaturday);
	}

	/** Get Break Time Saturday.
		@return Break Time Saturday	  */
	public BigDecimal getBreakTimeSaturday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeSaturday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Sunday.
		@param BreakTimeSunday Break Time Sunday	  */
	public void setBreakTimeSunday (BigDecimal BreakTimeSunday)
	{
		set_Value (COLUMNNAME_BreakTimeSunday, BreakTimeSunday);
	}

	/** Get Break Time Sunday.
		@return Break Time Sunday	  */
	public BigDecimal getBreakTimeSunday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeSunday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Thursday.
		@param BreakTimeThursday Break Time Thursday	  */
	public void setBreakTimeThursday (BigDecimal BreakTimeThursday)
	{
		set_Value (COLUMNNAME_BreakTimeThursday, BreakTimeThursday);
	}

	/** Get Break Time Thursday.
		@return Break Time Thursday	  */
	public BigDecimal getBreakTimeThursday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeThursday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Tuesday.
		@param BreakTimeTuesday Break Time Tuesday	  */
	public void setBreakTimeTuesday (BigDecimal BreakTimeTuesday)
	{
		set_Value (COLUMNNAME_BreakTimeTuesday, BreakTimeTuesday);
	}

	/** Get Break Time Tuesday.
		@return Break Time Tuesday	  */
	public BigDecimal getBreakTimeTuesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeTuesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Break Time Wednesday.
		@param BreakTimeWednesday Break Time Wednesday	  */
	public void setBreakTimeWednesday (BigDecimal BreakTimeWednesday)
	{
		set_Value (COLUMNNAME_BreakTimeWednesday, BreakTimeWednesday);
	}

	/** Get Break Time Wednesday.
		@return Break Time Wednesday	  */
	public BigDecimal getBreakTimeWednesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTimeWednesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Auto Synchronize.
		@param isAutoSynchronize Auto Synchronize	  */
	public void setisAutoSynchronize (boolean isAutoSynchronize)
	{
		set_Value (COLUMNNAME_isAutoSynchronize, Boolean.valueOf(isAutoSynchronize));
	}

	/** Get Auto Synchronize.
		@return Auto Synchronize	  */
	public boolean isAutoSynchronize () 
	{
		Object oo = get_Value(COLUMNNAME_isAutoSynchronize);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Day Slot.
		@param IsDaySlot Day Slot	  */
	public void setIsDaySlot (boolean IsDaySlot)
	{
		set_Value (COLUMNNAME_IsDaySlot, Boolean.valueOf(IsDaySlot));
	}

	/** Get Day Slot.
		@return Day Slot	  */
	public boolean isDaySlot () 
	{
		Object oo = get_Value(COLUMNNAME_IsDaySlot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Time Slot.
		@param IsTimeSlot 
		Resource has time slot availability
	  */
	public void setIsTimeSlot (boolean IsTimeSlot)
	{
		set_Value (COLUMNNAME_IsTimeSlot, Boolean.valueOf(IsTimeSlot));
	}

	/** Get Time Slot.
		@return Resource has time slot availability
	  */
	public boolean isTimeSlot () 
	{
		Object oo = get_Value(COLUMNNAME_IsTimeSlot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Normal Day Work Hours.
		@param NormalDayWorkHours Normal Day Work Hours	  */
	public void setNormalDayWorkHours (BigDecimal NormalDayWorkHours)
	{
		set_Value (COLUMNNAME_NormalDayWorkHours, NormalDayWorkHours);
	}

	/** Get Normal Day Work Hours.
		@return Normal Day Work Hours	  */
	public BigDecimal getNormalDayWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NormalDayWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Friday.
		@param OnFriday 
		Available on Fridays
	  */
	public void setOnFriday (boolean OnFriday)
	{
		set_Value (COLUMNNAME_OnFriday, Boolean.valueOf(OnFriday));
	}

	/** Get Friday.
		@return Available on Fridays
	  */
	public boolean isOnFriday () 
	{
		Object oo = get_Value(COLUMNNAME_OnFriday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Monday.
		@param OnMonday 
		Available on Mondays
	  */
	public void setOnMonday (boolean OnMonday)
	{
		set_Value (COLUMNNAME_OnMonday, Boolean.valueOf(OnMonday));
	}

	/** Get Monday.
		@return Available on Mondays
	  */
	public boolean isOnMonday () 
	{
		Object oo = get_Value(COLUMNNAME_OnMonday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Saturday.
		@param OnSaturday 
		Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, Boolean.valueOf(OnSaturday));
	}

	/** Get Saturday.
		@return Available on Saturday
	  */
	public boolean isOnSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sunday.
		@param OnSunday 
		Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, Boolean.valueOf(OnSunday));
	}

	/** Get Sunday.
		@return Available on Sundays
	  */
	public boolean isOnSunday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Thursday.
		@param OnThursday 
		Available on Thursdays
	  */
	public void setOnThursday (boolean OnThursday)
	{
		set_Value (COLUMNNAME_OnThursday, Boolean.valueOf(OnThursday));
	}

	/** Get Thursday.
		@return Available on Thursdays
	  */
	public boolean isOnThursday () 
	{
		Object oo = get_Value(COLUMNNAME_OnThursday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tuesday.
		@param OnTuesday 
		Available on Tuesdays
	  */
	public void setOnTuesday (boolean OnTuesday)
	{
		set_Value (COLUMNNAME_OnTuesday, Boolean.valueOf(OnTuesday));
	}

	/** Get Tuesday.
		@return Available on Tuesdays
	  */
	public boolean isOnTuesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnTuesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Wednesday.
		@param OnWednesday 
		Available on Wednesdays
	  */
	public void setOnWednesday (boolean OnWednesday)
	{
		set_Value (COLUMNNAME_OnWednesday, Boolean.valueOf(OnWednesday));
	}

	/** Get Wednesday.
		@return Available on Wednesdays
	  */
	public boolean isOnWednesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnWednesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preparation Time Friday.
		@param PreparationTimeFriday Preparation Time Friday	  */
	public void setPreparationTimeFriday (BigDecimal PreparationTimeFriday)
	{
		set_Value (COLUMNNAME_PreparationTimeFriday, PreparationTimeFriday);
	}

	/** Get Preparation Time Friday.
		@return Preparation Time Friday	  */
	public BigDecimal getPreparationTimeFriday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeFriday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Monday.
		@param PreparationTimeMonday Preparation Time Monday	  */
	public void setPreparationTimeMonday (BigDecimal PreparationTimeMonday)
	{
		set_Value (COLUMNNAME_PreparationTimeMonday, PreparationTimeMonday);
	}

	/** Get Preparation Time Monday.
		@return Preparation Time Monday	  */
	public BigDecimal getPreparationTimeMonday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeMonday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Saturday.
		@param PreparationTimeSaturday Preparation Time Saturday	  */
	public void setPreparationTimeSaturday (BigDecimal PreparationTimeSaturday)
	{
		set_Value (COLUMNNAME_PreparationTimeSaturday, PreparationTimeSaturday);
	}

	/** Get Preparation Time Saturday.
		@return Preparation Time Saturday	  */
	public BigDecimal getPreparationTimeSaturday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeSaturday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Sunday.
		@param PreparationTimeSunday Preparation Time Sunday	  */
	public void setPreparationTimeSunday (BigDecimal PreparationTimeSunday)
	{
		set_Value (COLUMNNAME_PreparationTimeSunday, PreparationTimeSunday);
	}

	/** Get Preparation Time Sunday.
		@return Preparation Time Sunday	  */
	public BigDecimal getPreparationTimeSunday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeSunday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Thursday.
		@param PreparationTimeThursday Preparation Time Thursday	  */
	public void setPreparationTimeThursday (BigDecimal PreparationTimeThursday)
	{
		set_Value (COLUMNNAME_PreparationTimeThursday, PreparationTimeThursday);
	}

	/** Get Preparation Time Thursday.
		@return Preparation Time Thursday	  */
	public BigDecimal getPreparationTimeThursday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeThursday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Tuesday.
		@param PreparationTimeTuesday Preparation Time Tuesday	  */
	public void setPreparationTimeTuesday (BigDecimal PreparationTimeTuesday)
	{
		set_Value (COLUMNNAME_PreparationTimeTuesday, PreparationTimeTuesday);
	}

	/** Get Preparation Time Tuesday.
		@return Preparation Time Tuesday	  */
	public BigDecimal getPreparationTimeTuesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeTuesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preparation Time Wednesday.
		@param PreparationTimeWednesday Preparation Time Wednesday	  */
	public void setPreparationTimeWednesday (BigDecimal PreparationTimeWednesday)
	{
		set_Value (COLUMNNAME_PreparationTimeWednesday, PreparationTimeWednesday);
	}

	/** Get Preparation Time Wednesday.
		@return Preparation Time Wednesday	  */
	public BigDecimal getPreparationTimeWednesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreparationTimeWednesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Short Day Work Hours.
		@param ShortDayWorkHours Short Day Work Hours	  */
	public void setShortDayWorkHours (BigDecimal ShortDayWorkHours)
	{
		set_Value (COLUMNNAME_ShortDayWorkHours, ShortDayWorkHours);
	}

	/** Get Short Day Work Hours.
		@return Short Day Work Hours	  */
	public BigDecimal getShortDayWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShortDayWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Slot End.
		@param TimeSlotEnd 
		Time when timeslot ends
	  */
	public void setTimeSlotEnd (Timestamp TimeSlotEnd)
	{
		set_Value (COLUMNNAME_TimeSlotEnd, TimeSlotEnd);
	}

	/** Get Slot End.
		@return Time when timeslot ends
	  */
	public Timestamp getTimeSlotEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEnd);
	}

	/** Set Time End Friday.
		@param TimeSlotEndFriday Time End Friday	  */
	public void setTimeSlotEndFriday (Timestamp TimeSlotEndFriday)
	{
		set_Value (COLUMNNAME_TimeSlotEndFriday, TimeSlotEndFriday);
	}

	/** Get Time End Friday.
		@return Time End Friday	  */
	public Timestamp getTimeSlotEndFriday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndFriday);
	}

	/** Set Time End Monday.
		@param TimeSlotEndMonday Time End Monday	  */
	public void setTimeSlotEndMonday (Timestamp TimeSlotEndMonday)
	{
		set_Value (COLUMNNAME_TimeSlotEndMonday, TimeSlotEndMonday);
	}

	/** Get Time End Monday.
		@return Time End Monday	  */
	public Timestamp getTimeSlotEndMonday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndMonday);
	}

	/** Set Time End Saturday.
		@param TimeSlotEndSaturday Time End Saturday	  */
	public void setTimeSlotEndSaturday (Timestamp TimeSlotEndSaturday)
	{
		set_Value (COLUMNNAME_TimeSlotEndSaturday, TimeSlotEndSaturday);
	}

	/** Get Time End Saturday.
		@return Time End Saturday	  */
	public Timestamp getTimeSlotEndSaturday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndSaturday);
	}

	/** Set Time End Sunday.
		@param TimeSlotEndSunday Time End Sunday	  */
	public void setTimeSlotEndSunday (Timestamp TimeSlotEndSunday)
	{
		set_Value (COLUMNNAME_TimeSlotEndSunday, TimeSlotEndSunday);
	}

	/** Get Time End Sunday.
		@return Time End Sunday	  */
	public Timestamp getTimeSlotEndSunday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndSunday);
	}

	/** Set Time End Thursday.
		@param TimeSlotEndThursday Time End Thursday	  */
	public void setTimeSlotEndThursday (Timestamp TimeSlotEndThursday)
	{
		set_Value (COLUMNNAME_TimeSlotEndThursday, TimeSlotEndThursday);
	}

	/** Get Time End Thursday.
		@return Time End Thursday	  */
	public Timestamp getTimeSlotEndThursday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndThursday);
	}

	/** Set Time End Tuesday.
		@param TimeSlotEndTuesday Time End Tuesday	  */
	public void setTimeSlotEndTuesday (Timestamp TimeSlotEndTuesday)
	{
		set_Value (COLUMNNAME_TimeSlotEndTuesday, TimeSlotEndTuesday);
	}

	/** Get Time End Tuesday.
		@return Time End Tuesday	  */
	public Timestamp getTimeSlotEndTuesday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndTuesday);
	}

	/** Set Time End Wednesday.
		@param TimeSlotEndWednesday Time End Wednesday	  */
	public void setTimeSlotEndWednesday (Timestamp TimeSlotEndWednesday)
	{
		set_Value (COLUMNNAME_TimeSlotEndWednesday, TimeSlotEndWednesday);
	}

	/** Get Time End Wednesday.
		@return Time End Wednesday	  */
	public Timestamp getTimeSlotEndWednesday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotEndWednesday);
	}

	/** Set Slot Start.
		@param TimeSlotStart 
		Time when timeslot starts
	  */
	public void setTimeSlotStart (Timestamp TimeSlotStart)
	{
		set_Value (COLUMNNAME_TimeSlotStart, TimeSlotStart);
	}

	/** Get Slot Start.
		@return Time when timeslot starts
	  */
	public Timestamp getTimeSlotStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStart);
	}

	/** Set Time Start Friday.
		@param TimeSlotStartFriday Time Start Friday	  */
	public void setTimeSlotStartFriday (Timestamp TimeSlotStartFriday)
	{
		set_Value (COLUMNNAME_TimeSlotStartFriday, TimeSlotStartFriday);
	}

	/** Get Time Start Friday.
		@return Time Start Friday	  */
	public Timestamp getTimeSlotStartFriday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartFriday);
	}

	/** Set Time Start Monday.
		@param TimeSlotStartMonday Time Start Monday	  */
	public void setTimeSlotStartMonday (Timestamp TimeSlotStartMonday)
	{
		set_Value (COLUMNNAME_TimeSlotStartMonday, TimeSlotStartMonday);
	}

	/** Get Time Start Monday.
		@return Time Start Monday	  */
	public Timestamp getTimeSlotStartMonday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartMonday);
	}

	/** Set Time Start Saturday.
		@param TimeSlotStartSaturday Time Start Saturday	  */
	public void setTimeSlotStartSaturday (Timestamp TimeSlotStartSaturday)
	{
		set_Value (COLUMNNAME_TimeSlotStartSaturday, TimeSlotStartSaturday);
	}

	/** Get Time Start Saturday.
		@return Time Start Saturday	  */
	public Timestamp getTimeSlotStartSaturday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartSaturday);
	}

	/** Set Time Start Sunday.
		@param TimeSlotStartSunday Time Start Sunday	  */
	public void setTimeSlotStartSunday (Timestamp TimeSlotStartSunday)
	{
		set_Value (COLUMNNAME_TimeSlotStartSunday, TimeSlotStartSunday);
	}

	/** Get Time Start Sunday.
		@return Time Start Sunday	  */
	public Timestamp getTimeSlotStartSunday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartSunday);
	}

	/** Set Time Start Thursday.
		@param TimeSlotStartThursday Time Start Thursday	  */
	public void setTimeSlotStartThursday (Timestamp TimeSlotStartThursday)
	{
		set_Value (COLUMNNAME_TimeSlotStartThursday, TimeSlotStartThursday);
	}

	/** Get Time Start Thursday.
		@return Time Start Thursday	  */
	public Timestamp getTimeSlotStartThursday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartThursday);
	}

	/** Set Time Start Tuesday.
		@param TimeSlotStartTuesday Time Start Tuesday	  */
	public void setTimeSlotStartTuesday (Timestamp TimeSlotStartTuesday)
	{
		set_Value (COLUMNNAME_TimeSlotStartTuesday, TimeSlotStartTuesday);
	}

	/** Get Time Start Tuesday.
		@return Time Start Tuesday	  */
	public Timestamp getTimeSlotStartTuesday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartTuesday);
	}

	/** Set Time Start Wednesday.
		@param TimeSlotStartWednesday Time Start Wednesday	  */
	public void setTimeSlotStartWednesday (Timestamp TimeSlotStartWednesday)
	{
		set_Value (COLUMNNAME_TimeSlotStartWednesday, TimeSlotStartWednesday);
	}

	/** Get Time Start Wednesday.
		@return Time Start Wednesday	  */
	public Timestamp getTimeSlotStartWednesday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeSlotStartWednesday);
	}

	/** Set UNS_AttConfiguration_ID.
		@param UNS_AttConfiguration_ID UNS_AttConfiguration_ID	  */
	public void setUNS_AttConfiguration_ID (int UNS_AttConfiguration_ID)
	{
		if (UNS_AttConfiguration_ID < 1) 
			set_Value (COLUMNNAME_UNS_AttConfiguration_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_AttConfiguration_ID, Integer.valueOf(UNS_AttConfiguration_ID));
	}

	/** Get UNS_AttConfiguration_ID.
		@return UNS_AttConfiguration_ID	  */
	public int getUNS_AttConfiguration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AttConfiguration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
	}

	/** Get Slot Type.
		@return Slot Type	  */
	public int getUNS_SlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Slot Type UU.
		@param UNS_SlotType_UU Slot Type UU	  */
	public void setUNS_SlotType_UU (String UNS_SlotType_UU)
	{
		set_Value (COLUMNNAME_UNS_SlotType_UU, UNS_SlotType_UU);
	}

	/** Get Slot Type UU.
		@return Slot Type UU	  */
	public String getUNS_SlotType_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SlotType_UU);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Work Hours Friday.
		@param WorkHoursFriday Work Hours Friday	  */
	public void setWorkHoursFriday (BigDecimal WorkHoursFriday)
	{
		set_Value (COLUMNNAME_WorkHoursFriday, WorkHoursFriday);
	}

	/** Get Work Hours Friday.
		@return Work Hours Friday	  */
	public BigDecimal getWorkHoursFriday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursFriday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Work Hours Monday.
		@param WorkHoursMonday Work Hours Monday	  */
	public void setWorkHoursMonday (BigDecimal WorkHoursMonday)
	{
		set_Value (COLUMNNAME_WorkHoursMonday, WorkHoursMonday);
	}

	/** Get Work Hours Monday.
		@return Work Hours Monday	  */
	public BigDecimal getWorkHoursMonday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursMonday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Work Hours Saturday.
		@param WorkHoursSaturday Work Hours Saturday	  */
	public void setWorkHoursSaturday (BigDecimal WorkHoursSaturday)
	{
		set_Value (COLUMNNAME_WorkHoursSaturday, WorkHoursSaturday);
	}

	/** Get Work Hours Saturday.
		@return Work Hours Saturday	  */
	public BigDecimal getWorkHoursSaturday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursSaturday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Use General Work Hours = UGW */
	public static final String WORKHOURSSTATUS_UseGeneralWorkHours = "UGW";
	/** Overwrite General Work Hours = OGW */
	public static final String WORKHOURSSTATUS_OverwriteGeneralWorkHours = "OGW";
	/** Overwrite Daily Work Hours = ODW */
	public static final String WORKHOURSSTATUS_OverwriteDailyWorkHours = "ODW";
	/** Set Work Hours Status.
		@param WorkHoursStatus Work Hours Status	  */
	public void setWorkHoursStatus (String WorkHoursStatus)
	{

		set_Value (COLUMNNAME_WorkHoursStatus, WorkHoursStatus);
	}

	/** Get Work Hours Status.
		@return Work Hours Status	  */
	public String getWorkHoursStatus () 
	{
		return (String)get_Value(COLUMNNAME_WorkHoursStatus);
	}

	/** Set Work Hours Sunday.
		@param WorkHoursSunday Work Hours Sunday	  */
	public void setWorkHoursSunday (BigDecimal WorkHoursSunday)
	{
		set_Value (COLUMNNAME_WorkHoursSunday, WorkHoursSunday);
	}

	/** Get Work Hours Sunday.
		@return Work Hours Sunday	  */
	public BigDecimal getWorkHoursSunday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursSunday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Work Hours Thursday.
		@param WorkHoursThursday Work Hours Thursday	  */
	public void setWorkHoursThursday (BigDecimal WorkHoursThursday)
	{
		set_Value (COLUMNNAME_WorkHoursThursday, WorkHoursThursday);
	}

	/** Get Work Hours Thursday.
		@return Work Hours Thursday	  */
	public BigDecimal getWorkHoursThursday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursThursday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Work Hours Tuesday.
		@param WorkHoursTuesday Work Hours Tuesday	  */
	public void setWorkHoursTuesday (BigDecimal WorkHoursTuesday)
	{
		set_Value (COLUMNNAME_WorkHoursTuesday, WorkHoursTuesday);
	}

	/** Get Work Hours Tuesday.
		@return Work Hours Tuesday	  */
	public BigDecimal getWorkHoursTuesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursTuesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Work Hours Wednesday.
		@param WorkHoursWednesday Work Hours Wednesday	  */
	public void setWorkHoursWednesday (BigDecimal WorkHoursWednesday)
	{
		set_Value (COLUMNNAME_WorkHoursWednesday, WorkHoursWednesday);
	}

	/** Get Work Hours Wednesday.
		@return Work Hours Wednesday	  */
	public BigDecimal getWorkHoursWednesday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHoursWednesday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}