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

/** Generated Model for UNS_DailyPresence
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DailyPresence extends PO implements I_UNS_DailyPresence, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180228L;

    /** Standard Constructor */
    public X_UNS_DailyPresence (Properties ctx, int UNS_DailyPresence_ID, String trxName)
    {
      super (ctx, UNS_DailyPresence_ID, trxName);
      /** if (UNS_DailyPresence_ID == 0)
        {
			setAddWorkHours (0);
// 0
			setIsNeedAdjustRule (true);
// Y
			setLD1 (Env.ZERO);
// 0
			setLD2 (Env.ZERO);
// 0
			setLD3 (Env.ZERO);
// 0
			setOvertime (Env.ZERO);
// 0
			setPresenceStatus (null);
			setUNS_DailyPresence_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DailyPresence (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DailyPresence[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AddWorkHours.
		@param AddWorkHours AddWorkHours	  */
	public void setAddWorkHours (int AddWorkHours)
	{
		set_Value (COLUMNNAME_AddWorkHours, Integer.valueOf(AddWorkHours));
	}

	/** Get AddWorkHours.
		@return AddWorkHours	  */
	public int getAddWorkHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AddWorkHours);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attendance Name.
		@param AttendanceName Attendance Name	  */
	public void setAttendanceName (String AttendanceName)
	{
		set_Value (COLUMNNAME_AttendanceName, AttendanceName);
	}

	/** Get Attendance Name.
		@return Attendance Name	  */
	public String getAttendanceName () 
	{
		return (String)get_Value(COLUMNNAME_AttendanceName);
	}

	/** Set Belated Duration.
		@param BelatedDuration Belated Duration	  */
	public void setBelatedDuration (int BelatedDuration)
	{
		set_Value (COLUMNNAME_BelatedDuration, Integer.valueOf(BelatedDuration));
	}

	/** Get Belated Duration.
		@return Belated Duration	  */
	public int getBelatedDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BelatedDuration);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Change Daily Presence.
		@param ChangeDailyPresence Change Daily Presence	  */
	public void setChangeDailyPresence (String ChangeDailyPresence)
	{
		set_Value (COLUMNNAME_ChangeDailyPresence, ChangeDailyPresence);
	}

	/** Get Change Daily Presence.
		@return Change Daily Presence	  */
	public String getChangeDailyPresence () 
	{
		return (String)get_Value(COLUMNNAME_ChangeDailyPresence);
	}

	/** Saturday = 7 */
	public static final String DAY_Saturday = "7";
	/** Friday = 6 */
	public static final String DAY_Friday = "6";
	/** Thursday = 5 */
	public static final String DAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String DAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String DAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String DAY_Monday = "2";
	/** Sunday = 1 */
	public static final String DAY_Sunday = "1";
	/** Set Day.
		@param Day Day	  */
	public void setDay (String Day)
	{

		set_ValueNoCheck (COLUMNNAME_Day, Day);
	}

	/** Get Day.
		@return Day	  */
	public String getDay () 
	{
		return (String)get_Value(COLUMNNAME_Day);
	}

	/** Hari Kerja Biasa = HB */
	public static final String DAYTYPE_HariKerjaBiasa = "HB";
	/** Hari Libur Mingguan = HL */
	public static final String DAYTYPE_HariLiburMingguan = "HL";
	/** Hari Libur Nasional = HN */
	public static final String DAYTYPE_HariLiburNasional = "HN";
	/** Set Day Type.
		@param DayType Day Type	  */
	public void setDayType (String DayType)
	{

		set_ValueNoCheck (COLUMNNAME_DayType, DayType);
	}

	/** Get Day Type.
		@return Day Type	  */
	public String getDayType () 
	{
		return (String)get_Value(COLUMNNAME_DayType);
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

	/** Set FS Time In.
		@param FSTimeIn 
		FS Time In
	  */
	public void setFSTimeIn (Timestamp FSTimeIn)
	{
		set_Value (COLUMNNAME_FSTimeIn, FSTimeIn);
	}

	/** Get FS Time In.
		@return FS Time In
	  */
	public Timestamp getFSTimeIn () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FSTimeIn);
	}

	/** Set FS Time Out.
		@param FSTimeOut 
		FS Time Out
	  */
	public void setFSTimeOut (Timestamp FSTimeOut)
	{
		set_Value (COLUMNNAME_FSTimeOut, FSTimeOut);
	}

	/** Get FS Time Out.
		@return FS Time Out
	  */
	public Timestamp getFSTimeOut () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FSTimeOut);
	}

	/** Set Need Adjustment Rule.
		@param IsNeedAdjustRule Need Adjustment Rule	  */
	public void setIsNeedAdjustRule (boolean IsNeedAdjustRule)
	{
		set_Value (COLUMNNAME_IsNeedAdjustRule, Boolean.valueOf(IsNeedAdjustRule));
	}

	/** Get Need Adjustment Rule.
		@return Need Adjustment Rule	  */
	public boolean isNeedAdjustRule () 
	{
		Object oo = get_Value(COLUMNNAME_IsNeedAdjustRule);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LD1.
		@param LD1 LD1	  */
	public void setLD1 (BigDecimal LD1)
	{
		set_Value (COLUMNNAME_LD1, LD1);
	}

	/** Get LD1.
		@return LD1	  */
	public BigDecimal getLD1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LD1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LD2.
		@param LD2 LD2	  */
	public void setLD2 (BigDecimal LD2)
	{
		set_Value (COLUMNNAME_LD2, LD2);
	}

	/** Get LD2.
		@return LD2	  */
	public BigDecimal getLD2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LD2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LD3.
		@param LD3 LD3	  */
	public void setLD3 (BigDecimal LD3)
	{
		set_Value (COLUMNNAME_LD3, LD3);
	}

	/** Get LD3.
		@return LD3	  */
	public BigDecimal getLD3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LD3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max. Time Out Rule.
		@param MaxTimeOutRule Max. Time Out Rule	  */
	public void setMaxTimeOutRule (Timestamp MaxTimeOutRule)
	{
		set_Value (COLUMNNAME_MaxTimeOutRule, MaxTimeOutRule);
	}

	/** Get Max. Time Out Rule.
		@return Max. Time Out Rule	  */
	public Timestamp getMaxTimeOutRule () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MaxTimeOutRule);
	}

	/** Set Min. Time In Rule.
		@param MinTimeInRule Min. Time In Rule	  */
	public void setMinTimeInRule (Timestamp MinTimeInRule)
	{
		set_Value (COLUMNNAME_MinTimeInRule, MinTimeInRule);
	}

	/** Get Min. Time In Rule.
		@return Min. Time In Rule	  */
	public Timestamp getMinTimeInRule () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MinTimeInRule);
	}

	/** Set Overtime.
		@param Overtime Overtime	  */
	public void setOvertime (BigDecimal Overtime)
	{
		set_Value (COLUMNNAME_Overtime, Overtime);
	}

	/** Get Overtime.
		@return Overtime	  */
	public BigDecimal getOvertime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Overtime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Leave / Cuti = LCTI */
	public static final String PERMISSIONTYPE_LeaveCuti = "LCTI";
	/** Permission (Dispensation) / Izin Dibayar = PMDB */
	public static final String PERMISSIONTYPE_PermissionDispensationIzinDibayar = "PMDB";
	/** Permission (Dinas) = PMDN */
	public static final String PERMISSIONTYPE_PermissionDinas = "PMDN";
	/** Pay Permission / Izin Potong Gaji = PPAY */
	public static final String PERMISSIONTYPE_PayPermissionIzinPotongGaji = "PPAY";
	/** Maternity / Hamil+Melahirkan = MLHR */
	public static final String PERMISSIONTYPE_MaternityHamilPlusMelahirkan = "MLHR";
	/** Surat Keterangan Istirahat = SKI */
	public static final String PERMISSIONTYPE_SuratKeteranganIstirahat = "SKI";
	/** Surat Keterangan Istirahat Kecelakaan Kerja = SKIKK */
	public static final String PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja = "SKIKK";
	/** Other = OTHR */
	public static final String PERMISSIONTYPE_Other = "OTHR";
	/** Set PermissionType.
		@param PermissionType PermissionType	  */
	public void setPermissionType (String PermissionType)
	{

		set_Value (COLUMNNAME_PermissionType, PermissionType);
	}

	/** Get PermissionType.
		@return PermissionType	  */
	public String getPermissionType () 
	{
		return (String)get_Value(COLUMNNAME_PermissionType);
	}

	/** Set Presence Date.
		@param PresenceDate Presence Date	  */
	public void setPresenceDate (Timestamp PresenceDate)
	{
		set_ValueNoCheck (COLUMNNAME_PresenceDate, PresenceDate);
	}

	/** Get Presence Date.
		@return Presence Date	  */
	public Timestamp getPresenceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PresenceDate);
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
	/** Reversed = RVD */
	public static final String PRESENCESTATUS_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String PRESENCESTATUS_Reversal = "RVL";
	/** Belated = BLD */
	public static final String PRESENCESTATUS_Belated = "BLD";
	/** Not Yet Validated = NYV */
	public static final String PRESENCESTATUS_NotYetValidated = "NYV";
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

	/** Set TimeInRules.
		@param TimeInRules TimeInRules	  */
	public void setTimeInRules (Timestamp TimeInRules)
	{
		set_Value (COLUMNNAME_TimeInRules, TimeInRules);
	}

	/** Get TimeInRules.
		@return TimeInRules	  */
	public Timestamp getTimeInRules () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeInRules);
	}

	/** Set TimeOutRules.
		@param TimeOutRules TimeOutRules	  */
	public void setTimeOutRules (Timestamp TimeOutRules)
	{
		set_Value (COLUMNNAME_TimeOutRules, TimeOutRules);
	}

	/** Get TimeOutRules.
		@return TimeOutRules	  */
	public Timestamp getTimeOutRules () 
	{
		return (Timestamp)get_Value(COLUMNNAME_TimeOutRules);
	}

	/** Set Daily Presence.
		@param UNS_DailyPresence_ID Daily Presence	  */
	public void setUNS_DailyPresence_ID (int UNS_DailyPresence_ID)
	{
		if (UNS_DailyPresence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DailyPresence_ID, Integer.valueOf(UNS_DailyPresence_ID));
	}

	/** Get Daily Presence.
		@return Daily Presence	  */
	public int getUNS_DailyPresence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DailyPresence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DailyPresence_UU.
		@param UNS_DailyPresence_UU UNS_DailyPresence_UU	  */
	public void setUNS_DailyPresence_UU (String UNS_DailyPresence_UU)
	{
		set_Value (COLUMNNAME_UNS_DailyPresence_UU, UNS_DailyPresence_UU);
	}

	/** Get UNS_DailyPresence_UU.
		@return UNS_DailyPresence_UU	  */
	public String getUNS_DailyPresence_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DailyPresence_UU);
	}

	public I_UNS_MonthlyPresenceSummary getUNS_MonthlyPresenceSummary() throws RuntimeException
    {
		return (I_UNS_MonthlyPresenceSummary)MTable.get(getCtx(), I_UNS_MonthlyPresenceSummary.Table_Name)
			.getPO(getUNS_MonthlyPresenceSummary_ID(), get_TrxName());	}

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

	public I_UNS_MonthlyPresenceVal getUNS_MonthlyPresenceVal() throws RuntimeException
    {
		return (I_UNS_MonthlyPresenceVal)MTable.get(getCtx(), I_UNS_MonthlyPresenceVal.Table_Name)
			.getPO(getUNS_MonthlyPresenceVal_ID(), get_TrxName());	}

	/** Set Monthly Presence Val.
		@param UNS_MonthlyPresenceVal_ID Monthly Presence Val	  */
	public void setUNS_MonthlyPresenceVal_ID (int UNS_MonthlyPresenceVal_ID)
	{
		if (UNS_MonthlyPresenceVal_ID < 1) 
			set_Value (COLUMNNAME_UNS_MonthlyPresenceVal_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MonthlyPresenceVal_ID, Integer.valueOf(UNS_MonthlyPresenceVal_ID));
	}

	/** Get Monthly Presence Val.
		@return Monthly Presence Val	  */
	public int getUNS_MonthlyPresenceVal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MonthlyPresenceVal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work Hours.
		@param WorkHours Work Hours	  */
	public void setWorkHours (BigDecimal WorkHours)
	{
		set_Value (COLUMNNAME_WorkHours, WorkHours);
	}

	/** Get Work Hours.
		@return Work Hours	  */
	public BigDecimal getWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}