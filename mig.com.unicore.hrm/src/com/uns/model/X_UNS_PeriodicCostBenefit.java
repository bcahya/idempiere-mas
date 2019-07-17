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

/** Generated Model for UNS_PeriodicCostBenefit
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PeriodicCostBenefit extends PO implements I_UNS_PeriodicCostBenefit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180302L;

    /** Standard Constructor */
    public X_UNS_PeriodicCostBenefit (Properties ctx, int UNS_PeriodicCostBenefit_ID, String trxName)
    {
      super (ctx, UNS_PeriodicCostBenefit_ID, trxName);
      /** if (UNS_PeriodicCostBenefit_ID == 0)
        {
			setC_Year_ID (0);
			setCostBenefitType (null);
			setIsBenefit (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setTotalAmt (Env.ZERO);
// 0
			setTotalRemainingAmt (Env.ZERO);
// 0
			setUNS_PeriodicCostBenefit_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PeriodicCostBenefit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PeriodicCostBenefit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Canteen = CNT */
	public static final String COSTBENEFITTYPE_Canteen = "CNT";
	/** Cooperative = CPS */
	public static final String COSTBENEFITTYPE_Cooperative = "CPS";
	/** Pinjaman Karyawan = PKR */
	public static final String COSTBENEFITTYPE_PinjamanKaryawan = "PKR";
	/** Bonuses = BNS */
	public static final String COSTBENEFITTYPE_Bonuses = "BNS";
	/** Set Cost / Benefit Type.
		@param CostBenefitType Cost / Benefit Type	  */
	public void setCostBenefitType (String CostBenefitType)
	{

		set_Value (COLUMNNAME_CostBenefitType, CostBenefitType);
	}

	/** Get Cost / Benefit Type.
		@return Cost / Benefit Type	  */
	public String getCostBenefitType () 
	{
		return (String)get_Value(COLUMNNAME_CostBenefitType);
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	/** Set Generate Canteen Cost.
		@param GenerateCanteenCost Generate Canteen Cost	  */
	public void setGenerateCanteenCost (String GenerateCanteenCost)
	{
		set_Value (COLUMNNAME_GenerateCanteenCost, GenerateCanteenCost);
	}

	/** Get Generate Canteen Cost.
		@return Generate Canteen Cost	  */
	public String getGenerateCanteenCost () 
	{
		return (String)get_Value(COLUMNNAME_GenerateCanteenCost);
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

	/** Set Is Benefit?.
		@param IsBenefit 
		If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public void setIsBenefit (boolean IsBenefit)
	{
		set_Value (COLUMNNAME_IsBenefit, Boolean.valueOf(IsBenefit));
	}

	/** Get Is Benefit?.
		@return If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public boolean isBenefit () 
	{
		Object oo = get_Value(COLUMNNAME_IsBenefit);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Remaining Amt.
		@param TotalRemainingAmt 
		Remaining Amount
	  */
	public void setTotalRemainingAmt (BigDecimal TotalRemainingAmt)
	{
		set_Value (COLUMNNAME_TotalRemainingAmt, TotalRemainingAmt);
	}

	/** Get Total Remaining Amt.
		@return Remaining Amount
	  */
	public BigDecimal getTotalRemainingAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalRemainingAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Periodic Cost And Benefit.
		@param UNS_PeriodicCostBenefit_ID Periodic Cost And Benefit	  */
	public void setUNS_PeriodicCostBenefit_ID (int UNS_PeriodicCostBenefit_ID)
	{
		if (UNS_PeriodicCostBenefit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefit_ID, Integer.valueOf(UNS_PeriodicCostBenefit_ID));
	}

	/** Get Periodic Cost And Benefit.
		@return Periodic Cost And Benefit	  */
	public int getUNS_PeriodicCostBenefit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PeriodicCostBenefit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PeriodicCostBenefit_UU.
		@param UNS_PeriodicCostBenefit_UU UNS_PeriodicCostBenefit_UU	  */
	public void setUNS_PeriodicCostBenefit_UU (String UNS_PeriodicCostBenefit_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PeriodicCostBenefit_UU, UNS_PeriodicCostBenefit_UU);
	}

	/** Get UNS_PeriodicCostBenefit_UU.
		@return UNS_PeriodicCostBenefit_UU	  */
	public String getUNS_PeriodicCostBenefit_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PeriodicCostBenefit_UU);
	}

	/** 10 = 10 */
	public static final String WEEKNO_10 = "10";
	/** 11 = 11 */
	public static final String WEEKNO_11 = "11";
	/** 12 = 12 */
	public static final String WEEKNO_12 = "12";
	/** 13 = 13 */
	public static final String WEEKNO_13 = "13";
	/** 14 = 14 */
	public static final String WEEKNO_14 = "14";
	/** 15 = 15 */
	public static final String WEEKNO_15 = "15";
	/** 16 = 16 */
	public static final String WEEKNO_16 = "16";
	/** 17 = 17 */
	public static final String WEEKNO_17 = "17";
	/** 18 = 18 */
	public static final String WEEKNO_18 = "18";
	/** 19 = 19 */
	public static final String WEEKNO_19 = "19";
	/** 20 = 20 */
	public static final String WEEKNO_20 = "20";
	/** 21 = 21 */
	public static final String WEEKNO_21 = "21";
	/** 22 = 22 */
	public static final String WEEKNO_22 = "22";
	/** 23 = 23 */
	public static final String WEEKNO_23 = "23";
	/** 24 = 24 */
	public static final String WEEKNO_24 = "24";
	/** 25 = 25 */
	public static final String WEEKNO_25 = "25";
	/** 26 = 26 */
	public static final String WEEKNO_26 = "26";
	/** 27 = 27 */
	public static final String WEEKNO_27 = "27";
	/** 28 = 28 */
	public static final String WEEKNO_28 = "28";
	/** 29 = 29 */
	public static final String WEEKNO_29 = "29";
	/** 30 = 30 */
	public static final String WEEKNO_30 = "30";
	/** 31 = 31 */
	public static final String WEEKNO_31 = "31";
	/** 32 = 32 */
	public static final String WEEKNO_32 = "32";
	/** 33 = 33 */
	public static final String WEEKNO_33 = "33";
	/** 34 = 34 */
	public static final String WEEKNO_34 = "34";
	/** 35 = 35 */
	public static final String WEEKNO_35 = "35";
	/** 36 = 36 */
	public static final String WEEKNO_36 = "36";
	/** 37 = 37 */
	public static final String WEEKNO_37 = "37";
	/** 38 = 38 */
	public static final String WEEKNO_38 = "38";
	/** 39 = 39 */
	public static final String WEEKNO_39 = "39";
	/** 01 = 1 */
	public static final String WEEKNO_01 = "1";
	/** 02 = 2 */
	public static final String WEEKNO_02 = "2";
	/** 03 = 3 */
	public static final String WEEKNO_03 = "3";
	/** 04 = 4 */
	public static final String WEEKNO_04 = "4";
	/** 05 = 5 */
	public static final String WEEKNO_05 = "5";
	/** 06 = 6 */
	public static final String WEEKNO_06 = "6";
	/** 07 = 7 */
	public static final String WEEKNO_07 = "7";
	/** 08 = 8 */
	public static final String WEEKNO_08 = "8";
	/** 09 = 9 */
	public static final String WEEKNO_09 = "9";
	/** 40 = 40 */
	public static final String WEEKNO_40 = "40";
	/** 42 = 42 */
	public static final String WEEKNO_42 = "42";
	/** 43 = 43 */
	public static final String WEEKNO_43 = "43";
	/** 44 = 44 */
	public static final String WEEKNO_44 = "44";
	/** 45 = 45 */
	public static final String WEEKNO_45 = "45";
	/** 46 = 46 */
	public static final String WEEKNO_46 = "46";
	/** 47 = 47 */
	public static final String WEEKNO_47 = "47";
	/** 48 = 48 */
	public static final String WEEKNO_48 = "48";
	/** 49 = 49 */
	public static final String WEEKNO_49 = "49";
	/** 41 = 41 */
	public static final String WEEKNO_41 = "41";
	/** 50 = 50 */
	public static final String WEEKNO_50 = "50";
	/** 51 = 51 */
	public static final String WEEKNO_51 = "51";
	/** 52 = 52 */
	public static final String WEEKNO_52 = "52";
	/** 53 = 53 */
	public static final String WEEKNO_53 = "53";
	/** Set Week No.
		@param WeekNo Week No	  */
	public void setWeekNo (String WeekNo)
	{

		set_Value (COLUMNNAME_WeekNo, WeekNo);
	}

	/** Get Week No.
		@return Week No	  */
	public String getWeekNo () 
	{
		return (String)get_Value(COLUMNNAME_WeekNo);
	}
}