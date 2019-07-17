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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_LC_Amendment
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_Amendment extends PO implements I_UNS_LC_Amendment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130523L;

    /** Standard Constructor */
    public X_UNS_LC_Amendment (Properties ctx, int UNS_LC_Amendment_ID, String trxName)
    {
      super (ctx, UNS_LC_Amendment_ID, trxName);
      /** if (UNS_LC_Amendment_ID == 0)
        {
			setDescription (null);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setUNS_LC_Amendment_ID (0);
			setUNS_LC_Balance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_Amendment (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_Amendment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Create Lines.
		@param CreateLines Create Lines	  */
	public void setCreateLines (String CreateLines)
	{
		set_Value (COLUMNNAME_CreateLines, CreateLines);
	}

	/** Get Create Lines.
		@return Create Lines	  */
	public String getCreateLines () 
	{
		return (String)get_Value(COLUMNNAME_CreateLines);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	/** Set LC Amendment.
		@param UNS_LC_Amendment_ID LC Amendment	  */
	public void setUNS_LC_Amendment_ID (int UNS_LC_Amendment_ID)
	{
		if (UNS_LC_Amendment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, Integer.valueOf(UNS_LC_Amendment_ID));
	}

	/** Get LC Amendment.
		@return LC Amendment	  */
	public int getUNS_LC_Amendment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Amendment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_Amendment_UU.
		@param UNS_LC_Amendment_UU UNS_LC_Amendment_UU	  */
	public void setUNS_LC_Amendment_UU (String UNS_LC_Amendment_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_Amendment_UU, UNS_LC_Amendment_UU);
	}

	/** Get UNS_LC_Amendment_UU.
		@return UNS_LC_Amendment_UU	  */
	public String getUNS_LC_Amendment_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_Amendment_UU);
	}

	public com.uns.model.I_UNS_LC_Balance getUNS_LC_Balance() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Balance)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Balance.Table_Name)
			.getPO(getUNS_LC_Balance_ID(), get_TrxName());	}

	/** Set LC Balance.
		@param UNS_LC_Balance_ID LC Balance	  */
	public void setUNS_LC_Balance_ID (int UNS_LC_Balance_ID)
	{
		if (UNS_LC_Balance_ID < 1) 
			set_Value (COLUMNNAME_UNS_LC_Balance_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_LC_Balance_ID, Integer.valueOf(UNS_LC_Balance_ID));
	}

	/** Get LC Balance.
		@return LC Balance	  */
	public int getUNS_LC_Balance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Balance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}