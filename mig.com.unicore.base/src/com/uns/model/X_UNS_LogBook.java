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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_LogBook
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LogBook extends PO implements I_UNS_LogBook, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140207L;

    /** Standard Constructor */
    public X_UNS_LogBook (Properties ctx, int UNS_LogBook_ID, String trxName)
    {
      super (ctx, UNS_LogBook_ID, trxName);
      /** if (UNS_LogBook_ID == 0)
        {
			setLogNo (0);
// @SQL=SELECT NVL(MAX(LogNo),0)+1 AS DefaultValue FROM UNS_LogBook
			setLogType (null);
// NT
			setNote (null);
			setReportedBy_ID (0);
// @AD_User_ID@
			setSubject (null);
			setUNS_LogBook_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LogBook (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LogBook[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Form)MTable.get(getCtx(), org.compiere.model.I_AD_Form.Table_Name)
			.getPO(getAD_Form_ID(), get_TrxName());	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	/** Get Special Form.
		@return Special Form
	  */
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Process)MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
			.getPO(getAD_Process_ID(), get_TrxName());	}

	/** Set Process.
		@param AD_Process_ID 
		Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Process.
		@return Process or Report
	  */
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Window)MTable.get(getCtx(), org.compiere.model.I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Log No.
		@param LogNo Log No	  */
	public void setLogNo (int LogNo)
	{
		set_Value (COLUMNNAME_LogNo, Integer.valueOf(LogNo));
	}

	/** Get Log No.
		@return Log No	  */
	public int getLogNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LogNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** LogType AD_Reference_ID=1000204 */
	public static final int LOGTYPE_AD_Reference_ID=1000204;
	/** Form = FR */
	public static final String LOGTYPE_Form = "FR";
	/** Note = NT */
	public static final String LOGTYPE_Note = "NT";
	/** Process or Report = PR */
	public static final String LOGTYPE_ProcessOrReport = "PR";
	/** Window = WN */
	public static final String LOGTYPE_Window = "WN";
	/** Set Log Type.
		@param LogType 
		Web Log Type
	  */
	public void setLogType (String LogType)
	{

		set_Value (COLUMNNAME_LogType, LogType);
	}

	/** Get Log Type.
		@return Web Log Type
	  */
	public String getLogType () 
	{
		return (String)get_Value(COLUMNNAME_LogType);
	}

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Priority AD_Reference_ID=154 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITY_High = "3";
	/** Medium = 5 */
	public static final String PRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITY_Minor = "9";
	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority)
	{

		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority () 
	{
		return (String)get_Value(COLUMNNAME_Priority);
	}

	public org.compiere.model.I_AD_User getReportedBy() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getReportedBy_ID(), get_TrxName());	}

	/** Set Reported By.
		@param ReportedBy_ID Reported By	  */
	public void setReportedBy_ID (int ReportedBy_ID)
	{
		if (ReportedBy_ID < 1) 
			set_Value (COLUMNNAME_ReportedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ReportedBy_ID, Integer.valueOf(ReportedBy_ID));
	}

	/** Get Reported By.
		@return Reported By	  */
	public int getReportedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReportedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Send.
		@param SendIt Send	  */
	public void setSendIt (String SendIt)
	{
		set_Value (COLUMNNAME_SendIt, SendIt);
	}

	/** Get Send.
		@return Send	  */
	public String getSendIt () 
	{
		return (String)get_Value(COLUMNNAME_SendIt);
	}

	/** Status AD_Reference_ID=366 */
	public static final int STATUS_AD_Reference_ID=366;
	/**  0% Not Started = 0 */
	public static final String STATUS_0NotStarted = "0";
	/** 100% Complete = D */
	public static final String STATUS_100Complete = "D";
	/**  20% Started = 2 */
	public static final String STATUS_20Started = "2";
	/**  80% Nearly Done = 8 */
	public static final String STATUS_80NearlyDone = "8";
	/**  40% Busy = 4 */
	public static final String STATUS_40Busy = "4";
	/**  60% Good Progress = 6 */
	public static final String STATUS_60GoodProgress = "6";
	/**  90% Finishing = 9 */
	public static final String STATUS_90Finishing = "9";
	/**  95% Almost Done = A */
	public static final String STATUS_95AlmostDone = "A";
	/**  99% Cleaning up = C */
	public static final String STATUS_99CleaningUp = "C";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Subject.
		@param Subject 
		Email Message Subject
	  */
	public void setSubject (String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Subject.
		@return Email Message Subject
	  */
	public String getSubject () 
	{
		return (String)get_Value(COLUMNNAME_Subject);
	}

	/** Set Log Book.
		@param UNS_LogBook_ID Log Book	  */
	public void setUNS_LogBook_ID (int UNS_LogBook_ID)
	{
		if (UNS_LogBook_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LogBook_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LogBook_ID, Integer.valueOf(UNS_LogBook_ID));
	}

	/** Get Log Book.
		@return Log Book	  */
	public int getUNS_LogBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LogBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LogBook_UU.
		@param UNS_LogBook_UU UNS_LogBook_UU	  */
	public void setUNS_LogBook_UU (String UNS_LogBook_UU)
	{
		set_Value (COLUMNNAME_UNS_LogBook_UU, UNS_LogBook_UU);
	}

	/** Get UNS_LogBook_UU.
		@return UNS_LogBook_UU	  */
	public String getUNS_LogBook_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LogBook_UU);
	}
}