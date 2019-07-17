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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Forecast_Header
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Forecast_Header extends PO implements I_UNS_Forecast_Header, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130426L;

    /** Standard Constructor */
    public X_UNS_Forecast_Header (Properties ctx, int UNS_Forecast_Header_ID, String trxName)
    {
      super (ctx, UNS_Forecast_Header_ID, trxName);
      /** if (UNS_Forecast_Header_ID == 0)
        {
			setAllocationMethod (null);
// MXM
			setC_Year_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setGenerateForecast (null);
// N
			setIsApproved (false);
// N
			setM_PriceList_ID (0);
			setName (null);
			setPeriodEnd_ID (0);
			setPeriodStart_ID (0);
			setProcessed (false);
// N
			setUNS_Forecast_Header_ID (0);
			setUNS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Forecast_Header (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Forecast_Header[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AllocationMethod AD_Reference_ID=1000072 */
	public static final int ALLOCATIONMETHOD_AD_Reference_ID=1000072;
	/** Minimum To Maximum = MMX */
	public static final String ALLOCATIONMETHOD_MinimumToMaximum = "MMX";
	/** Maximum To Minimum = MXM */
	public static final String ALLOCATIONMETHOD_MaximumToMinimum = "MXM";
	/** Proportion = PRP */
	public static final String ALLOCATIONMETHOD_Proportion = "PRP";
	/** Set Allocation Method.
		@param AllocationMethod Allocation Method	  */
	public void setAllocationMethod (String AllocationMethod)
	{

		set_Value (COLUMNNAME_AllocationMethod, AllocationMethod);
	}

	/** Get Allocation Method.
		@return Allocation Method	  */
	public String getAllocationMethod () 
	{
		return (String)get_Value(COLUMNNAME_AllocationMethod);
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

	/** Set Generate Forecast.
		@param GenerateForecast Generate Forecast	  */
	public void setGenerateForecast (String GenerateForecast)
	{
		set_Value (COLUMNNAME_GenerateForecast, GenerateForecast);
	}

	/** Get Generate Forecast.
		@return Generate Forecast	  */
	public String getGenerateForecast () 
	{
		return (String)get_Value(COLUMNNAME_GenerateForecast);
	}

	/** Set Generate Forecast Allocation.
		@param GenerateForecastAllocation Generate Forecast Allocation	  */
	public void setGenerateForecastAllocation (String GenerateForecastAllocation)
	{
		set_Value (COLUMNNAME_GenerateForecastAllocation, GenerateForecastAllocation);
	}

	/** Get Generate Forecast Allocation.
		@return Generate Forecast Allocation	  */
	public String getGenerateForecastAllocation () 
	{
		return (String)get_Value(COLUMNNAME_GenerateForecastAllocation);
	}

	/** Set Generate Targeted Quantity.
		@param GenerateTargetedQty Generate Targeted Quantity	  */
	public void setGenerateTargetedQty (String GenerateTargetedQty)
	{
		set_Value (COLUMNNAME_GenerateTargetedQty, GenerateTargetedQty);
	}

	/** Get Generate Targeted Quantity.
		@return Generate Targeted Quantity	  */
	public String getGenerateTargetedQty () 
	{
		return (String)get_Value(COLUMNNAME_GenerateTargetedQty);
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

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Period getPeriodEnd() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getPeriodEnd_ID(), get_TrxName());	}

	/** Set Period End.
		@param PeriodEnd_ID Period End	  */
	public void setPeriodEnd_ID (int PeriodEnd_ID)
	{
		if (PeriodEnd_ID < 1) 
			set_Value (COLUMNNAME_PeriodEnd_ID, null);
		else 
			set_Value (COLUMNNAME_PeriodEnd_ID, Integer.valueOf(PeriodEnd_ID));
	}

	/** Get Period End.
		@return Period End	  */
	public int getPeriodEnd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodEnd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getPeriodStart() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getPeriodStart_ID(), get_TrxName());	}

	/** Set Period Start.
		@param PeriodStart_ID Period Start	  */
	public void setPeriodStart_ID (int PeriodStart_ID)
	{
		if (PeriodStart_ID < 1) 
			set_Value (COLUMNNAME_PeriodStart_ID, null);
		else 
			set_Value (COLUMNNAME_PeriodStart_ID, Integer.valueOf(PeriodStart_ID));
	}

	/** Get Period Start.
		@return Period Start	  */
	public int getPeriodStart_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodStart_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Forecast_Header getPrevForecast() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Header)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Header.Table_Name)
			.getPO(getPrevForecast_ID(), get_TrxName());	}

	/** Set Prev Forecast.
		@param PrevForecast_ID Prev Forecast	  */
	public void setPrevForecast_ID (int PrevForecast_ID)
	{
		if (PrevForecast_ID < 1) 
			set_Value (COLUMNNAME_PrevForecast_ID, null);
		else 
			set_Value (COLUMNNAME_PrevForecast_ID, Integer.valueOf(PrevForecast_ID));
	}

	/** Get Prev Forecast.
		@return Prev Forecast	  */
	public int getPrevForecast_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevForecast_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Manufacturing Forecast.
		@param UNS_Forecast_Header_ID Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID)
	{
		if (UNS_Forecast_Header_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, Integer.valueOf(UNS_Forecast_Header_ID));
	}

	/** Get Manufacturing Forecast.
		@return Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Forecast_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Forecast Header UU.
		@param UNS_Forecast_Header_UU Forecast Header UU	  */
	public void setUNS_Forecast_Header_UU (String UNS_Forecast_Header_UU)
	{
		set_Value (COLUMNNAME_UNS_Forecast_Header_UU, UNS_Forecast_Header_UU);
	}

	/** Get Forecast Header UU.
		@return Forecast Header UU	  */
	public String getUNS_Forecast_Header_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Forecast_Header_UU);
	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/**
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

}