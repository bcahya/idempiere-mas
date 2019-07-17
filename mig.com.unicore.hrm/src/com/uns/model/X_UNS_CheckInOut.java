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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_CheckInOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CheckInOut extends PO implements I_UNS_CheckInOut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180206L;

    /** Standard Constructor */
    public X_UNS_CheckInOut (Properties ctx, int UNS_CheckInOut_ID, String trxName)
    {
      super (ctx, UNS_CheckInOut_ID, trxName);
      /** if (UNS_CheckInOut_ID == 0)
        {
			setAttendanceName (null);
			setCheckTime (new Timestamp( System.currentTimeMillis() ));
			setCheckType (null);
			setIsLinkedToEmployee (false);
// N
			setisPostpone (false);
// N
			setUNS_CheckInOut_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CheckInOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CheckInOut[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Check Time.
		@param CheckTime Check Time	  */
	public void setCheckTime (Timestamp CheckTime)
	{
		set_Value (COLUMNNAME_CheckTime, CheckTime);
	}

	/** Get Check Time.
		@return Check Time	  */
	public Timestamp getCheckTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CheckTime);
	}

	/** Kembali = 0 */
	public static final String CHECKTYPE_Kembali = "0";
	/** Keluar = 1 */
	public static final String CHECKTYPE_Keluar = "1";
	/** LemburMasuk = i */
	public static final String CHECKTYPE_LemburMasuk = "i";
	/** In = I */
	public static final String CHECKTYPE_In = "I";
	/** LemburKeluar = o */
	public static final String CHECKTYPE_LemburKeluar = "o";
	/** Out = O */
	public static final String CHECKTYPE_Out = "O";
	/** Set Check Type.
		@param CheckType Check Type	  */
	public void setCheckType (String CheckType)
	{

		set_Value (COLUMNNAME_CheckType, CheckType);
	}

	/** Get Check Type.
		@return Check Type	  */
	public String getCheckType () 
	{
		return (String)get_Value(COLUMNNAME_CheckType);
	}

	/** Set Linked To Employee.
		@param IsLinkedToEmployee Linked To Employee	  */
	public void setIsLinkedToEmployee (boolean IsLinkedToEmployee)
	{
		set_Value (COLUMNNAME_IsLinkedToEmployee, Boolean.valueOf(IsLinkedToEmployee));
	}

	/** Get Linked To Employee.
		@return Linked To Employee	  */
	public boolean isLinkedToEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsLinkedToEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Postpone.
		@param isPostpone Postpone	  */
	public void setisPostpone (boolean isPostpone)
	{
		set_Value (COLUMNNAME_isPostpone, Boolean.valueOf(isPostpone));
	}

	/** Get Postpone.
		@return Postpone	  */
	public boolean isPostpone () 
	{
		Object oo = get_Value(COLUMNNAME_isPostpone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MemoInfo.
		@param MemoInfo MemoInfo	  */
	public void setMemoInfo (String MemoInfo)
	{
		set_Value (COLUMNNAME_MemoInfo, MemoInfo);
	}

	/** Get MemoInfo.
		@return MemoInfo	  */
	public String getMemoInfo () 
	{
		return (String)get_Value(COLUMNNAME_MemoInfo);
	}

	/** Set Presence Date.
		@param PresenceDate Presence Date	  */
	public void setPresenceDate (Timestamp PresenceDate)
	{
		set_Value (COLUMNNAME_PresenceDate, PresenceDate);
	}

	/** Get Presence Date.
		@return Presence Date	  */
	public Timestamp getPresenceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PresenceDate);
	}

	/** Set Chek In Out.
		@param UNS_CheckInOut_ID Chek In Out	  */
	public void setUNS_CheckInOut_ID (int UNS_CheckInOut_ID)
	{
		if (UNS_CheckInOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckInOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckInOut_ID, Integer.valueOf(UNS_CheckInOut_ID));
	}

	/** Get Chek In Out.
		@return Chek In Out	  */
	public int getUNS_CheckInOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CheckInOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ChekInOut_UU.
		@param UNS_CheckInOut_UU ChekInOut_UU	  */
	public void setUNS_CheckInOut_UU (String UNS_CheckInOut_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CheckInOut_UU, UNS_CheckInOut_UU);
	}

	/** Get ChekInOut_UU.
		@return ChekInOut_UU	  */
	public String getUNS_CheckInOut_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CheckInOut_UU);
	}

	public com.uns.model.I_UNS_DailyPresence getUNS_DailyPresence() throws RuntimeException
    {
		return (com.uns.model.I_UNS_DailyPresence)MTable.get(getCtx(), com.uns.model.I_UNS_DailyPresence.Table_Name)
			.getPO(getUNS_DailyPresence_ID(), get_TrxName());	}

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

	public com.uns.model.I_UNS_MonthlyPresenceVal getUNS_MonthlyPresenceVal() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MonthlyPresenceVal)MTable.get(getCtx(), com.uns.model.I_UNS_MonthlyPresenceVal.Table_Name)
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

	/** Set User Ext Fmt.
		@param UserExtFmt User Ext Fmt	  */
	public void setUserExtFmt (int UserExtFmt)
	{
		set_Value (COLUMNNAME_UserExtFmt, Integer.valueOf(UserExtFmt));
	}

	/** Get User Ext Fmt.
		@return User Ext Fmt	  */
	public int getUserExtFmt () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UserExtFmt);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verify Code.
		@param VerifyCode Verify Code	  */
	public void setVerifyCode (int VerifyCode)
	{
		set_Value (COLUMNNAME_VerifyCode, Integer.valueOf(VerifyCode));
	}

	/** Get Verify Code.
		@return Verify Code	  */
	public int getVerifyCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VerifyCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work Code.
		@param WorkCode Work Code	  */
	public void setWorkCode (String WorkCode)
	{
		set_Value (COLUMNNAME_WorkCode, WorkCode);
	}

	/** Get Work Code.
		@return Work Code	  */
	public String getWorkCode () 
	{
		return (String)get_Value(COLUMNNAME_WorkCode);
	}
}