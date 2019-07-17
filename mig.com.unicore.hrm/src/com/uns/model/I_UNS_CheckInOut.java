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

/** Generated Interface for UNS_CheckInOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CheckInOut 
{

    /** TableName=UNS_CheckInOut */
    public static final String Table_Name = "UNS_CheckInOut";

    /** AD_Table_ID=1000297 */
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

    /** Column name AttendanceName */
    public static final String COLUMNNAME_AttendanceName = "AttendanceName";

	/** Set Attendance Name	  */
	public void setAttendanceName (String AttendanceName);

	/** Get Attendance Name	  */
	public String getAttendanceName();

    /** Column name CheckTime */
    public static final String COLUMNNAME_CheckTime = "CheckTime";

	/** Set Check Time	  */
	public void setCheckTime (Timestamp CheckTime);

	/** Get Check Time	  */
	public Timestamp getCheckTime();

    /** Column name CheckType */
    public static final String COLUMNNAME_CheckType = "CheckType";

	/** Set Check Type	  */
	public void setCheckType (String CheckType);

	/** Get Check Type	  */
	public String getCheckType();

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

    /** Column name IsLinkedToEmployee */
    public static final String COLUMNNAME_IsLinkedToEmployee = "IsLinkedToEmployee";

	/** Set Linked To Employee	  */
	public void setIsLinkedToEmployee (boolean IsLinkedToEmployee);

	/** Get Linked To Employee	  */
	public boolean isLinkedToEmployee();

    /** Column name isPostpone */
    public static final String COLUMNNAME_isPostpone = "isPostpone";

	/** Set Postpone	  */
	public void setisPostpone (boolean isPostpone);

	/** Get Postpone	  */
	public boolean isPostpone();

    /** Column name MemoInfo */
    public static final String COLUMNNAME_MemoInfo = "MemoInfo";

	/** Set MemoInfo	  */
	public void setMemoInfo (String MemoInfo);

	/** Get MemoInfo	  */
	public String getMemoInfo();

    /** Column name PresenceDate */
    public static final String COLUMNNAME_PresenceDate = "PresenceDate";

	/** Set Presence Date	  */
	public void setPresenceDate (Timestamp PresenceDate);

	/** Get Presence Date	  */
	public Timestamp getPresenceDate();

    /** Column name UNS_CheckInOut_ID */
    public static final String COLUMNNAME_UNS_CheckInOut_ID = "UNS_CheckInOut_ID";

	/** Set Chek In Out	  */
	public void setUNS_CheckInOut_ID (int UNS_CheckInOut_ID);

	/** Get Chek In Out	  */
	public int getUNS_CheckInOut_ID();

    /** Column name UNS_CheckInOut_UU */
    public static final String COLUMNNAME_UNS_CheckInOut_UU = "UNS_CheckInOut_UU";

	/** Set ChekInOut_UU	  */
	public void setUNS_CheckInOut_UU (String UNS_CheckInOut_UU);

	/** Get ChekInOut_UU	  */
	public String getUNS_CheckInOut_UU();

    /** Column name UNS_DailyPresence_ID */
    public static final String COLUMNNAME_UNS_DailyPresence_ID = "UNS_DailyPresence_ID";

	/** Set Daily Presence	  */
	public void setUNS_DailyPresence_ID (int UNS_DailyPresence_ID);

	/** Get Daily Presence	  */
	public int getUNS_DailyPresence_ID();

	public com.uns.model.I_UNS_DailyPresence getUNS_DailyPresence() throws RuntimeException;

    /** Column name UNS_MonthlyPresenceVal_ID */
    public static final String COLUMNNAME_UNS_MonthlyPresenceVal_ID = "UNS_MonthlyPresenceVal_ID";

	/** Set Monthly Presence Val	  */
	public void setUNS_MonthlyPresenceVal_ID (int UNS_MonthlyPresenceVal_ID);

	/** Get Monthly Presence Val	  */
	public int getUNS_MonthlyPresenceVal_ID();

	public com.uns.model.I_UNS_MonthlyPresenceVal getUNS_MonthlyPresenceVal() throws RuntimeException;

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

    /** Column name UserExtFmt */
    public static final String COLUMNNAME_UserExtFmt = "UserExtFmt";

	/** Set User Ext Fmt	  */
	public void setUserExtFmt (int UserExtFmt);

	/** Get User Ext Fmt	  */
	public int getUserExtFmt();

    /** Column name VerifyCode */
    public static final String COLUMNNAME_VerifyCode = "VerifyCode";

	/** Set Verify Code	  */
	public void setVerifyCode (int VerifyCode);

	/** Get Verify Code	  */
	public int getVerifyCode();

    /** Column name WorkCode */
    public static final String COLUMNNAME_WorkCode = "WorkCode";

	/** Set Work Code	  */
	public void setWorkCode (String WorkCode);

	/** Get Work Code	  */
	public String getWorkCode();
}
