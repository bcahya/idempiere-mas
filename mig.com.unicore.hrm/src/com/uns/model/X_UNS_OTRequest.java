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

/** Generated Model for UNS_OTRequest
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_OTRequest extends PO implements I_UNS_OTRequest, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_OTRequest (Properties ctx, int UNS_OTRequest_ID, String trxName)
    {
      super (ctx, UNS_OTRequest_ID, trxName);
      /** if (UNS_OTRequest_ID == 0)
        {
			setBreakTime (Env.ZERO);
// 0
			setDateDoOT (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setEndTime (new Timestamp( System.currentTimeMillis() ));
			setRequestDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setRequestedHours (Env.ZERO);
			setStartTime (new Timestamp( System.currentTimeMillis() ));
			setUNS_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OTRequest (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OTRequest[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Break Time.
		@param BreakTime Break Time	  */
	public void setBreakTime (BigDecimal BreakTime)
	{
		set_Value (COLUMNNAME_BreakTime, BreakTime);
	}

	/** Get Break Time.
		@return Break Time	  */
	public BigDecimal getBreakTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ConfirmedHours.
		@param ConfirmedHours ConfirmedHours	  */
	public void setConfirmedHours (BigDecimal ConfirmedHours)
	{
		set_Value (COLUMNNAME_ConfirmedHours, ConfirmedHours);
	}

	/** Get ConfirmedHours.
		@return ConfirmedHours	  */
	public BigDecimal getConfirmedHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DateDoOT.
		@param DateDoOT DateDoOT	  */
	public void setDateDoOT (Timestamp DateDoOT)
	{
		set_Value (COLUMNNAME_DateDoOT, DateDoOT);
	}

	/** Get DateDoOT.
		@return DateDoOT	  */
	public Timestamp getDateDoOT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoOT);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Request Date.
		@param RequestDate Request Date	  */
	public void setRequestDate (Timestamp RequestDate)
	{
		set_Value (COLUMNNAME_RequestDate, RequestDate);
	}

	/** Get Request Date.
		@return Request Date	  */
	public Timestamp getRequestDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_RequestDate);
	}

	/** Set RequestedHours.
		@param RequestedHours RequestedHours	  */
	public void setRequestedHours (BigDecimal RequestedHours)
	{
		set_Value (COLUMNNAME_RequestedHours, RequestedHours);
	}

	/** Get RequestedHours.
		@return RequestedHours	  */
	public BigDecimal getRequestedHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RequestedHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	public I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (I_UNS_Employee)MTable.get(getCtx(), I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
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

	/** Set UNS_OTRequest_ID.
		@param UNS_OTRequest_ID UNS_OTRequest_ID	  */
	public void setUNS_OTRequest_ID (int UNS_OTRequest_ID)
	{
		if (UNS_OTRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OTRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OTRequest_ID, Integer.valueOf(UNS_OTRequest_ID));
	}

	/** Get UNS_OTRequest_ID.
		@return UNS_OTRequest_ID	  */
	public int getUNS_OTRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OTRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OTRequest_UU.
		@param UNS_OTRequest_UU UNS_OTRequest_UU	  */
	public void setUNS_OTRequest_UU (String UNS_OTRequest_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_OTRequest_UU, UNS_OTRequest_UU);
	}

	/** Get UNS_OTRequest_UU.
		@return UNS_OTRequest_UU	  */
	public String getUNS_OTRequest_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_OTRequest_UU);
	}

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_Value (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
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
}