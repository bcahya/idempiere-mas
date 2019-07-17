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

/** Generated Model for UNS_ProductionPayConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ProductionPayConfig extends PO implements I_UNS_ProductionPayConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150314L;

    /** Standard Constructor */
    public X_UNS_ProductionPayConfig (Properties ctx, int UNS_ProductionPayConfig_ID, String trxName)
    {
      super (ctx, UNS_ProductionPayConfig_ID, trxName);
      /** if (UNS_ProductionPayConfig_ID == 0)
        {
			setCutOffWeekDay (null);
// 7
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setIsDefault (false);
// N
			setIsPayableSK (false);
// N
			setIsPayableSKK (false);
// N
			setName (null);
			setNoWorkDayIncentive (Env.ZERO);
// 0
			setPayrollTerm (null);
// 01
			setProcessed (false);
// N
			setSpv_Period (null);
			setSupervisedInsentifPemborong (Env.ZERO);
// 0
			setSupervisedPayFullDay (Env.ZERO);
// 0
			setSupervisedPayFullHoliday (Env.ZERO);
// 0
			setSupervisedPayFullNHoliday (Env.ZERO);
// 0
			setSupervisedPayHalfDay (Env.ZERO);
// 0
			setSupervisedPayHalfHoliday (Env.ZERO);
// 0
			setSupervisedPayHalfNHoliday (Env.ZERO);
// 0
			setSupervisedPayHolidayOTPerHour (Env.ZERO);
// 0
			setSupervisedPayNHolidayOTPerHour (Env.ZERO);
// 0
			setSupervisedPayOTPerHour (Env.ZERO);
// 0
			setSupervisorInsentifPemborong (Env.ZERO);
// 0
			setSupervisorPayFullDay (Env.ZERO);
// 0
			setSupervisorPayFullHoliday (Env.ZERO);
// 0
			setSupervisorPayFullNHoliday (Env.ZERO);
// 0
			setSupervisorPayHalfDay (Env.ZERO);
// 0
			setSupervisorPayHalfHoliday (Env.ZERO);
// 0
			setSupervisorPayHalfNHoliday (Env.ZERO);
// 0
			setSupervisorPayHolidayOTPerHours (Env.ZERO);
// 0
			setSupervisorPayNHolidayOTPerHour (Env.ZERO);
// 0
			setSupervisorPayOTPerhours (Env.ZERO);
// 0
			setTargetPeriod (null);
			setUNS_ProductionPayConfig_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_ProductionPayConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ProductionPayConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Saturday = 7 */
	public static final String CUTOFFWEEKDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String CUTOFFWEEKDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String CUTOFFWEEKDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String CUTOFFWEEKDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String CUTOFFWEEKDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String CUTOFFWEEKDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String CUTOFFWEEKDAY_Sunday = "1";
	/** Set Cut Off Week Day.
		@param CutOffWeekDay 
		Teh day to cut off production/transaction in every week.
	  */
	public void setCutOffWeekDay (String CutOffWeekDay)
	{

		set_Value (COLUMNNAME_CutOffWeekDay, CutOffWeekDay);
	}

	/** Get Cut Off Week Day.
		@return Teh day to cut off production/transaction in every week.
	  */
	public String getCutOffWeekDay () 
	{
		return (String)get_Value(COLUMNNAME_CutOffWeekDay);
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
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SK Daily Payable.
		@param IsPayableSK SK Daily Payable	  */
	public void setIsPayableSK (boolean IsPayableSK)
	{
		set_Value (COLUMNNAME_IsPayableSK, Boolean.valueOf(IsPayableSK));
	}

	/** Get SK Daily Payable.
		@return SK Daily Payable	  */
	public boolean isPayableSK () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayableSK);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SKK Daily Payable.
		@param IsPayableSKK SKK Daily Payable	  */
	public void setIsPayableSKK (boolean IsPayableSKK)
	{
		set_Value (COLUMNNAME_IsPayableSKK, Boolean.valueOf(IsPayableSKK));
	}

	/** Get SKK Daily Payable.
		@return SKK Daily Payable	  */
	public boolean isPayableSKK () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayableSKK);
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

	/** Set No-Work Day Incentive.
		@param NoWorkDayIncentive 
		The default incentive amount for supervised worker if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive)
	{
		set_Value (COLUMNNAME_NoWorkDayIncentive, NoWorkDayIncentive);
	}

	/** Get No-Work Day Incentive.
		@return The default incentive amount for supervised worker if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NoWorkDayIncentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Monthly = 01 */
	public static final String PAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PAYROLLTERM_HarianBulanan = "04";
	/** Set Payroll Term.
		@param PayrollTerm Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm)
	{

		set_Value (COLUMNNAME_PayrollTerm, PayrollTerm);
	}

	/** Get Payroll Term.
		@return Payroll Term	  */
	public String getPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PayrollTerm);
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

	/** 1/2 Month = 1/2_Month */
	public static final String SPV_PERIOD_12Month = "1/2_Month";
	/** 1 Month = 1_Month */
	public static final String SPV_PERIOD_1Month = "1_Month";
	/** Set Supervisor Period.
		@param Spv_Period Supervisor Period	  */
	public void setSpv_Period (String Spv_Period)
	{

		set_Value (COLUMNNAME_Spv_Period, Spv_Period);
	}

	/** Get Supervisor Period.
		@return Supervisor Period	  */
	public String getSpv_Period () 
	{
		return (String)get_Value(COLUMNNAME_Spv_Period);
	}

	/** Set Supervised Insentif Pemborong.
		@param SupervisedInsentifPemborong Supervised Insentif Pemborong	  */
	public void setSupervisedInsentifPemborong (BigDecimal SupervisedInsentifPemborong)
	{
		set_Value (COLUMNNAME_SupervisedInsentifPemborong, SupervisedInsentifPemborong);
	}

	/** Get Supervised Insentif Pemborong.
		@return Supervised Insentif Pemborong	  */
	public BigDecimal getSupervisedInsentifPemborong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedInsentifPemborong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full Day.
		@param SupervisedPayFullDay 
		Supervised Pay Full Day
	  */
	public void setSupervisedPayFullDay (BigDecimal SupervisedPayFullDay)
	{
		set_Value (COLUMNNAME_SupervisedPayFullDay, SupervisedPayFullDay);
	}

	/** Get Pay Full Day.
		@return Supervised Pay Full Day
	  */
	public BigDecimal getSupervisedPayFullDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayFullDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full Holiday.
		@param SupervisedPayFullHoliday 
		Supervised Pay Full Holiday
	  */
	public void setSupervisedPayFullHoliday (BigDecimal SupervisedPayFullHoliday)
	{
		set_Value (COLUMNNAME_SupervisedPayFullHoliday, SupervisedPayFullHoliday);
	}

	/** Get Pay Full Holiday.
		@return Supervised Pay Full Holiday
	  */
	public BigDecimal getSupervisedPayFullHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayFullHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full National Holiday.
		@param SupervisedPayFullNHoliday 
		Supervised Pay Full National Holiday
	  */
	public void setSupervisedPayFullNHoliday (BigDecimal SupervisedPayFullNHoliday)
	{
		set_Value (COLUMNNAME_SupervisedPayFullNHoliday, SupervisedPayFullNHoliday);
	}

	/** Get Pay Full National Holiday.
		@return Supervised Pay Full National Holiday
	  */
	public BigDecimal getSupervisedPayFullNHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayFullNHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half Day.
		@param SupervisedPayHalfDay 
		Supervised Pay Half Day
	  */
	public void setSupervisedPayHalfDay (BigDecimal SupervisedPayHalfDay)
	{
		set_Value (COLUMNNAME_SupervisedPayHalfDay, SupervisedPayHalfDay);
	}

	/** Get Pay Half Day.
		@return Supervised Pay Half Day
	  */
	public BigDecimal getSupervisedPayHalfDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayHalfDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half Holiday.
		@param SupervisedPayHalfHoliday 
		Supervised Pay Half Holiday
	  */
	public void setSupervisedPayHalfHoliday (BigDecimal SupervisedPayHalfHoliday)
	{
		set_Value (COLUMNNAME_SupervisedPayHalfHoliday, SupervisedPayHalfHoliday);
	}

	/** Get Pay Half Holiday.
		@return Supervised Pay Half Holiday
	  */
	public BigDecimal getSupervisedPayHalfHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayHalfHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half National Holiday.
		@param SupervisedPayHalfNHoliday 
		Supervised Pay Half National Holiday
	  */
	public void setSupervisedPayHalfNHoliday (BigDecimal SupervisedPayHalfNHoliday)
	{
		set_Value (COLUMNNAME_SupervisedPayHalfNHoliday, SupervisedPayHalfNHoliday);
	}

	/** Get Pay Half National Holiday.
		@return Supervised Pay Half National Holiday
	  */
	public BigDecimal getSupervisedPayHalfNHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayHalfNHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Holiday OT Per Hour.
		@param SupervisedPayHolidayOTPerHour 
		Supervised Pay Holiday Over Time Per Hour
	  */
	public void setSupervisedPayHolidayOTPerHour (BigDecimal SupervisedPayHolidayOTPerHour)
	{
		set_Value (COLUMNNAME_SupervisedPayHolidayOTPerHour, SupervisedPayHolidayOTPerHour);
	}

	/** Get Pay Holiday OT Per Hour.
		@return Supervised Pay Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayHolidayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayHolidayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay N.Holiday OT Per Hour.
		@param SupervisedPayNHolidayOTPerHour 
		Supervised Pay National Holiday Over Time Per Hour
	  */
	public void setSupervisedPayNHolidayOTPerHour (BigDecimal SupervisedPayNHolidayOTPerHour)
	{
		set_Value (COLUMNNAME_SupervisedPayNHolidayOTPerHour, SupervisedPayNHolidayOTPerHour);
	}

	/** Get Pay N.Holiday OT Per Hour.
		@return Supervised Pay National Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayNHolidayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayNHolidayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Over Time Per Hour.
		@param SupervisedPayOTPerHour 
		Supervised Pay Over Time Per Hour
	  */
	public void setSupervisedPayOTPerHour (BigDecimal SupervisedPayOTPerHour)
	{
		set_Value (COLUMNNAME_SupervisedPayOTPerHour, SupervisedPayOTPerHour);
	}

	/** Get Pay Over Time Per Hour.
		@return Supervised Pay Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisedPayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Supervisor Insentif Pemborong.
		@param SupervisorInsentifPemborong Supervisor Insentif Pemborong	  */
	public void setSupervisorInsentifPemborong (BigDecimal SupervisorInsentifPemborong)
	{
		set_Value (COLUMNNAME_SupervisorInsentifPemborong, SupervisorInsentifPemborong);
	}

	/** Get Supervisor Insentif Pemborong.
		@return Supervisor Insentif Pemborong	  */
	public BigDecimal getSupervisorInsentifPemborong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorInsentifPemborong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full Day.
		@param SupervisorPayFullDay 
		Supervisor Pay Full Day
	  */
	public void setSupervisorPayFullDay (BigDecimal SupervisorPayFullDay)
	{
		set_Value (COLUMNNAME_SupervisorPayFullDay, SupervisorPayFullDay);
	}

	/** Get Pay Full Day.
		@return Supervisor Pay Full Day
	  */
	public BigDecimal getSupervisorPayFullDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayFullDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full Holiday.
		@param SupervisorPayFullHoliday 
		Supervisor Pay Full Holiday
	  */
	public void setSupervisorPayFullHoliday (BigDecimal SupervisorPayFullHoliday)
	{
		set_Value (COLUMNNAME_SupervisorPayFullHoliday, SupervisorPayFullHoliday);
	}

	/** Get Pay Full Holiday.
		@return Supervisor Pay Full Holiday
	  */
	public BigDecimal getSupervisorPayFullHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayFullHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full National Holiday.
		@param SupervisorPayFullNHoliday 
		Supervisor Pay Full National Holiday
	  */
	public void setSupervisorPayFullNHoliday (BigDecimal SupervisorPayFullNHoliday)
	{
		set_Value (COLUMNNAME_SupervisorPayFullNHoliday, SupervisorPayFullNHoliday);
	}

	/** Get Pay Full National Holiday.
		@return Supervisor Pay Full National Holiday
	  */
	public BigDecimal getSupervisorPayFullNHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayFullNHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half Day.
		@param SupervisorPayHalfDay 
		Supervisor Pay Half Day
	  */
	public void setSupervisorPayHalfDay (BigDecimal SupervisorPayHalfDay)
	{
		set_Value (COLUMNNAME_SupervisorPayHalfDay, SupervisorPayHalfDay);
	}

	/** Get Pay Half Day.
		@return Supervisor Pay Half Day
	  */
	public BigDecimal getSupervisorPayHalfDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayHalfDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half Holiday.
		@param SupervisorPayHalfHoliday 
		Supervisor Pay Half Holiday
	  */
	public void setSupervisorPayHalfHoliday (BigDecimal SupervisorPayHalfHoliday)
	{
		set_Value (COLUMNNAME_SupervisorPayHalfHoliday, SupervisorPayHalfHoliday);
	}

	/** Get Pay Half Holiday.
		@return Supervisor Pay Half Holiday
	  */
	public BigDecimal getSupervisorPayHalfHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayHalfHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half National Holiday.
		@param SupervisorPayHalfNHoliday 
		Supervisor Pay Half National Holiday
	  */
	public void setSupervisorPayHalfNHoliday (BigDecimal SupervisorPayHalfNHoliday)
	{
		set_Value (COLUMNNAME_SupervisorPayHalfNHoliday, SupervisorPayHalfNHoliday);
	}

	/** Get Pay Half National Holiday.
		@return Supervisor Pay Half National Holiday
	  */
	public BigDecimal getSupervisorPayHalfNHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayHalfNHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Holiday OTPer Hour.
		@param SupervisorPayHolidayOTPerHours 
		Supervisor Pay Holiday Over Time Per Hour
	  */
	public void setSupervisorPayHolidayOTPerHours (BigDecimal SupervisorPayHolidayOTPerHours)
	{
		set_Value (COLUMNNAME_SupervisorPayHolidayOTPerHours, SupervisorPayHolidayOTPerHours);
	}

	/** Get Pay Holiday OTPer Hour.
		@return Supervisor Pay Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisorPayHolidayOTPerHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayHolidayOTPerHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay N. Holiday OT Per Hour.
		@param SupervisorPayNHolidayOTPerHour 
		Supervisor Pay National Holiday Over Time Per Hour
	  */
	public void setSupervisorPayNHolidayOTPerHour (BigDecimal SupervisorPayNHolidayOTPerHour)
	{
		set_Value (COLUMNNAME_SupervisorPayNHolidayOTPerHour, SupervisorPayNHolidayOTPerHour);
	}

	/** Get Pay N. Holiday OT Per Hour.
		@return Supervisor Pay National Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisorPayNHolidayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayNHolidayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Over Time Per Hour.
		@param SupervisorPayOTPerhours 
		Supervisor Pay Over Time Per Hours
	  */
	public void setSupervisorPayOTPerhours (BigDecimal SupervisorPayOTPerhours)
	{
		set_Value (COLUMNNAME_SupervisorPayOTPerhours, SupervisorPayOTPerhours);
	}

	/** Get Pay Over Time Per Hour.
		@return Supervisor Pay Over Time Per Hours
	  */
	public BigDecimal getSupervisorPayOTPerhours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SupervisorPayOTPerhours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 1/2 Month = 1/2_Month */
	public static final String TARGETPERIOD_12Month = "1/2_Month";
	/** 1 Month = 1_Month */
	public static final String TARGETPERIOD_1Month = "1_Month";
	/** Set Supervised Period.
		@param TargetPeriod Supervised Period	  */
	public void setTargetPeriod (String TargetPeriod)
	{

		set_Value (COLUMNNAME_TargetPeriod, TargetPeriod);
	}

	/** Get Supervised Period.
		@return Supervised Period	  */
	public String getTargetPeriod () 
	{
		return (String)get_Value(COLUMNNAME_TargetPeriod);
	}

	/** Set Production Pay Config.
		@param UNS_ProductionPayConfig_ID Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID)
	{
		if (UNS_ProductionPayConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionPayConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionPayConfig_ID, Integer.valueOf(UNS_ProductionPayConfig_ID));
	}

	/** Get Production Pay Config.
		@return Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionPayConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ProductionPayConfig_UU.
		@param UNS_ProductionPayConfig_UU UNS_ProductionPayConfig_UU	  */
	public void setUNS_ProductionPayConfig_UU (String UNS_ProductionPayConfig_UU)
	{
		set_Value (COLUMNNAME_UNS_ProductionPayConfig_UU, UNS_ProductionPayConfig_UU);
	}

	/** Get UNS_ProductionPayConfig_UU.
		@return UNS_ProductionPayConfig_UU	  */
	public String getUNS_ProductionPayConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ProductionPayConfig_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
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
}