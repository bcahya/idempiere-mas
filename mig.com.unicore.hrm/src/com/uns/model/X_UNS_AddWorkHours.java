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

/** Generated Model for UNS_AddWorkHours
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AddWorkHours extends PO implements I_UNS_AddWorkHours, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171130L;

    /** Standard Constructor */
    public X_UNS_AddWorkHours (Properties ctx, int UNS_AddWorkHours_ID, String trxName)
    {
      super (ctx, UNS_AddWorkHours_ID, trxName);
      /** if (UNS_AddWorkHours_ID == 0)
        {
			setAddWorkHours (0);
			setDocStatus (null);
// DR
			setEarlierAddWorkHours (0);
// 0
			setUNS_EmpStation_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_AddWorkHours (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AddWorkHours[")
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

	/** Set Earlier Add Work Hours.
		@param EarlierAddWorkHours Earlier Add Work Hours	  */
	public void setEarlierAddWorkHours (int EarlierAddWorkHours)
	{
		set_Value (COLUMNNAME_EarlierAddWorkHours, Integer.valueOf(EarlierAddWorkHours));
	}

	/** Get Earlier Add Work Hours.
		@return Earlier Add Work Hours	  */
	public int getEarlierAddWorkHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EarlierAddWorkHours);
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

	/** Set UNS_AddWorkHours_ID.
		@param UNS_AddWorkHours_ID UNS_AddWorkHours_ID	  */
	public void setUNS_AddWorkHours_ID (int UNS_AddWorkHours_ID)
	{
		if (UNS_AddWorkHours_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AddWorkHours_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AddWorkHours_ID, Integer.valueOf(UNS_AddWorkHours_ID));
	}

	/** Get UNS_AddWorkHours_ID.
		@return UNS_AddWorkHours_ID	  */
	public int getUNS_AddWorkHours_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AddWorkHours_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AddWorkHours_UU.
		@param UNS_AddWorkHours_UU UNS_AddWorkHours_UU	  */
	public void setUNS_AddWorkHours_UU (String UNS_AddWorkHours_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AddWorkHours_UU, UNS_AddWorkHours_UU);
	}

	/** Get UNS_AddWorkHours_UU.
		@return UNS_AddWorkHours_UU	  */
	public String getUNS_AddWorkHours_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AddWorkHours_UU);
	}

	public I_UNS_EmpStation getUNS_EmpStation() throws RuntimeException
    {
		return (I_UNS_EmpStation)MTable.get(getCtx(), I_UNS_EmpStation.Table_Name)
			.getPO(getUNS_EmpStation_ID(), get_TrxName());	}

	/** Set Station.
		@param UNS_EmpStation_ID Station	  */
	public void setUNS_EmpStation_ID (int UNS_EmpStation_ID)
	{
		if (UNS_EmpStation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, Integer.valueOf(UNS_EmpStation_ID));
	}

	/** Get Station.
		@return Station	  */
	public int getUNS_EmpStation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmpStation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}