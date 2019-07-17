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

/** Generated Model for UNS_Manufacturing_Order
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Manufacturing_Order extends PO implements I_UNS_Manufacturing_Order, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130813L;

    /** Standard Constructor */
    public X_UNS_Manufacturing_Order (Properties ctx, int UNS_Manufacturing_Order_ID, String trxName)
    {
      super (ctx, UNS_Manufacturing_Order_ID, trxName);
      /** if (UNS_Manufacturing_Order_ID == 0)
        {
			setC_Year_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setIsApproved (false);
// 0
			setName (null);
			setProcessed (false);
			setUNS_Manufacturing_Order_ID (0);
			setWeekNo (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Manufacturing_Order (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Manufacturing_Order[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Create Production.
		@param CreateProduction Create Production	  */
	public void setCreateProduction (String CreateProduction)
	{
		set_Value (COLUMNNAME_CreateProduction, CreateProduction);
	}

	/** Get Create Production.
		@return Create Production	  */
	public String getCreateProduction () 
	{
		return (String)get_Value(COLUMNNAME_CreateProduction);
	}

	/** Set Create SO Allocation.
		@param CreatePSSOAllocation Create SO Allocation	  */
	public void setCreatePSSOAllocation (String CreatePSSOAllocation)
	{
		set_Value (COLUMNNAME_CreatePSSOAllocation, CreatePSSOAllocation);
	}

	/** Get Create SO Allocation.
		@return Create SO Allocation	  */
	public String getCreatePSSOAllocation () 
	{
		return (String)get_Value(COLUMNNAME_CreatePSSOAllocation);
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

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
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

	/** Set Notes.
		@param Notes 
		Notes to the schedule from Planner Department
	  */
	public void setNotes (String Notes)
	{
		set_Value (COLUMNNAME_Notes, Notes);
	}

	/** Get Notes.
		@return Notes to the schedule from Planner Department
	  */
	public String getNotes () 
	{
		return (String)get_Value(COLUMNNAME_Notes);
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

	/** Set Production Department.
		@param ProductionDepartment_ID 
		Department to do the production
	  */
	public void setProductionDepartment_ID (int ProductionDepartment_ID)
	{
		if (ProductionDepartment_ID < 1) 
			set_Value (COLUMNNAME_ProductionDepartment_ID, null);
		else 
			set_Value (COLUMNNAME_ProductionDepartment_ID, Integer.valueOf(ProductionDepartment_ID));
	}

	/** Get Production Department.
		@return Department to do the production
	  */
	public int getProductionDepartment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductionDepartment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getProductionDepartment_ID()));
    }

	/** Set Target Production Date End.
		@param Target_PD_End Target Production Date End	  */
	public void setTarget_PD_End (Timestamp Target_PD_End)
	{
		set_Value (COLUMNNAME_Target_PD_End, Target_PD_End);
	}

	/** Get Target Production Date End.
		@return Target Production Date End	  */
	public Timestamp getTarget_PD_End () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Target_PD_End);
	}

	/** Set Target Production Date Start.
		@param Target_PD_Start Target Production Date Start	  */
	public void setTarget_PD_Start (Timestamp Target_PD_Start)
	{
		set_Value (COLUMNNAME_Target_PD_Start, Target_PD_Start);
	}

	/** Get Target Production Date Start.
		@return Target Production Date Start	  */
	public Timestamp getTarget_PD_Start () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Target_PD_Start);
	}

	/** Set Manufacturing Order.
		@param UNS_Manufacturing_Order_ID Manufacturing Order	  */
	public void setUNS_Manufacturing_Order_ID (int UNS_Manufacturing_Order_ID)
	{
		if (UNS_Manufacturing_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Manufacturing_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Manufacturing_Order_ID, Integer.valueOf(UNS_Manufacturing_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order	  */
	public int getUNS_Manufacturing_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Manufacturing_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Manufacturing_Order_UU.
		@param UNS_Manufacturing_Order_UU UNS_Manufacturing_Order_UU	  */
	public void setUNS_Manufacturing_Order_UU (String UNS_Manufacturing_Order_UU)
	{
		set_Value (COLUMNNAME_UNS_Manufacturing_Order_UU, UNS_Manufacturing_Order_UU);
	}

	/** Get UNS_Manufacturing_Order_UU.
		@return UNS_Manufacturing_Order_UU	  */
	public String getUNS_Manufacturing_Order_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Manufacturing_Order_UU);
	}

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSHeader)MTable.get(getCtx(), com.uns.model.I_UNS_MPSHeader.Table_Name)
			.getPO(getUNS_MPSHeader_ID(), get_TrxName());	}

	/** Set Master Production Schedule.
		@param UNS_MPSHeader_ID Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID)
	{
		if (UNS_MPSHeader_ID < 1) 
			set_Value (COLUMNNAME_UNS_MPSHeader_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MPSHeader_ID, Integer.valueOf(UNS_MPSHeader_ID));
	}

	/** Get Master Production Schedule.
		@return Master Production Schedule	  */
	public int getUNS_MPSHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** WeekNo AD_Reference_ID=1000169 */
	public static final int WEEKNO_AD_Reference_ID=1000169;
	/** 01 = 01 */
	public static final String WEEKNO_01 = "01";
	/** 02 = 02 */
	public static final String WEEKNO_02 = "02";
	/** 03 = 03 */
	public static final String WEEKNO_03 = "03";
	/** 04 = 04 */
	public static final String WEEKNO_04 = "04";
	/** 05 = 05 */
	public static final String WEEKNO_05 = "05";
	/** 06 = 06 */
	public static final String WEEKNO_06 = "06";
	/** 07 = 07 */
	public static final String WEEKNO_07 = "07";
	/** 08 = 08 */
	public static final String WEEKNO_08 = "08";
	/** 09 = 09 */
	public static final String WEEKNO_09 = "09";
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
	/** 40 = 40 */
	public static final String WEEKNO_40 = "40";
	/** 41 = 41 */
	public static final String WEEKNO_41 = "41";
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