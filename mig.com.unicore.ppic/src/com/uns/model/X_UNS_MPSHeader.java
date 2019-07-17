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

/** Generated Model for UNS_MPSHeader
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MPSHeader extends PO implements I_UNS_MPSHeader, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130817L;

    /** Standard Constructor */
    public X_UNS_MPSHeader (Properties ctx, int UNS_MPSHeader_ID, String trxName)
    {
      super (ctx, UNS_MPSHeader_ID, trxName);
      /** if (UNS_MPSHeader_ID == 0)
        {
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setIsSyncDatabase (false);
// N
			setName (null);
			setPeriodEnd_ID (0);
			setPeriodStart_ID (0);
			setProcessed (false);
// N
			setUNS_Forecast_Header_ID (0);
			setUNS_MPSHeader_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MPSHeader (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MPSHeader[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Generate MPS.
		@param CreateProductFrom Generate MPS	  */
	public void setCreateProductFrom (String CreateProductFrom)
	{
		set_Value (COLUMNNAME_CreateProductFrom, CreateProductFrom);
	}

	/** Get Generate MPS.
		@return Generate MPS	  */
	public String getCreateProductFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateProductFrom);
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

	/** Set Generate MRP.
		@param Generate_MRP 
		Generate Material Requirement Planing By MPS Product
	  */
	public void setGenerate_MRP (String Generate_MRP)
	{
		set_Value (COLUMNNAME_Generate_MRP, Generate_MRP);
	}

	/** Get Generate MRP.
		@return Generate Material Requirement Planing By MPS Product
	  */
	public String getGenerate_MRP () 
	{
		return (String)get_Value(COLUMNNAME_Generate_MRP);
	}

	/** Set Synchronize Database.
		@param IsSyncDatabase 
		Change database table definition when changing dictionary definition
	  */
	public void setIsSyncDatabase (boolean IsSyncDatabase)
	{
		set_ValueNoCheck (COLUMNNAME_IsSyncDatabase, Boolean.valueOf(IsSyncDatabase));
	}

	/** Get Synchronize Database.
		@return Change database table definition when changing dictionary definition
	  */
	public boolean isSyncDatabase () 
	{
		Object oo = get_Value(COLUMNNAME_IsSyncDatabase);
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

	public com.uns.model.I_UNS_MPSHeader getPrevMPS() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSHeader)MTable.get(getCtx(), com.uns.model.I_UNS_MPSHeader.Table_Name)
			.getPO(getPrevMPS_ID(), get_TrxName());	}

	/** Set Prev MPS.
		@param PrevMPS_ID Prev MPS	  */
	public void setPrevMPS_ID (int PrevMPS_ID)
	{
		if (PrevMPS_ID < 1) 
			set_Value (COLUMNNAME_PrevMPS_ID, null);
		else 
			set_Value (COLUMNNAME_PrevMPS_ID, Integer.valueOf(PrevMPS_ID));
	}

	/** Get Prev MPS.
		@return Prev MPS	  */
	public int getPrevMPS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevMPS_ID);
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

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Header)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Header.Table_Name)
			.getPO(getUNS_Forecast_Header_ID(), get_TrxName());	}

	/** Set Manufacturing Forecast.
		@param UNS_Forecast_Header_ID Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID)
	{
		if (UNS_Forecast_Header_ID < 1) 
			set_Value (COLUMNNAME_UNS_Forecast_Header_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Forecast_Header_ID, Integer.valueOf(UNS_Forecast_Header_ID));
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

	/** Set Master Production Schedule.
		@param UNS_MPSHeader_ID Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID)
	{
		if (UNS_MPSHeader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSHeader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSHeader_ID, Integer.valueOf(UNS_MPSHeader_ID));
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

	/** Set MPS Header UU.
		@param UNS_MPSHeader_UU MPS Header UU	  */
	public void setUNS_MPSHeader_UU (String UNS_MPSHeader_UU)
	{
		set_Value (COLUMNNAME_UNS_MPSHeader_UU, UNS_MPSHeader_UU);
	}

	/** Get MPS Header UU.
		@return MPS Header UU	  */
	public String getUNS_MPSHeader_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MPSHeader_UU);
	}

	/** Set Update MPS Changes.
		@param UpdateMPS 
		Update MPS actualization data based on changes made by other processes
	  */
	public void setUpdateMPS (String UpdateMPS)
	{
		set_Value (COLUMNNAME_UpdateMPS, UpdateMPS);
	}

	/** Get Update MPS Changes.
		@return Update MPS actualization data based on changes made by other processes
	  */
	public String getUpdateMPS () 
	{
		return (String)get_Value(COLUMNNAME_UpdateMPS);
	}

	/** Set Update MRP (From Receipt / Production).
		@param UpdateMRP Update MRP (From Receipt / Production)	  */
	public void setUpdateMRP (String UpdateMRP)
	{
		set_Value (COLUMNNAME_UpdateMRP, UpdateMRP);
	}

	/** Get Update MRP (From Receipt / Production).
		@return Update MRP (From Receipt / Production)	  */
	public String getUpdateMRP () 
	{
		return (String)get_Value(COLUMNNAME_UpdateMRP);
	}
}