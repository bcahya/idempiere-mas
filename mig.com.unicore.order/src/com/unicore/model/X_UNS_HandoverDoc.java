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
package com.unicore.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_HandoverDoc
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_HandoverDoc extends PO implements I_UNS_HandoverDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151226L;

    /** Standard Constructor */
    public X_UNS_HandoverDoc (Properties ctx, int UNS_HandoverDoc_ID, String trxName)
    {
      super (ctx, UNS_HandoverDoc_ID, trxName);
      /** if (UNS_HandoverDoc_ID == 0)
        {
			setAcceptedBy (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateSubmitted (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
			setProcessed (false);
			setUNS_HandoverDoc_ID (0);
			setUNS_PL_Return_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_HandoverDoc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_HandoverDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAccepte() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAcceptedBy(), get_TrxName());	}

	/** Set Accepted By.
		@param AcceptedBy Accepted By	  */
	public void setAcceptedBy (int AcceptedBy)
	{
		set_Value (COLUMNNAME_AcceptedBy, Integer.valueOf(AcceptedBy));
	}

	/** Get Accepted By.
		@return Accepted By	  */
	public int getAcceptedBy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AcceptedBy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Date Submitted.
		@param DateSubmitted Date Submitted	  */
	public void setDateSubmitted (Timestamp DateSubmitted)
	{
		set_Value (COLUMNNAME_DateSubmitted, DateSubmitted);
	}

	/** Get Date Submitted.
		@return Date Submitted	  */
	public Timestamp getDateSubmitted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateSubmitted);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
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

	/** Set Load Document.
		@param LoadDocument Load Document	  */
	public void setLoadDocument (String LoadDocument)
	{
		set_Value (COLUMNNAME_LoadDocument, LoadDocument);
	}

	/** Get Load Document.
		@return Load Document	  */
	public String getLoadDocument () 
	{
		return (String)get_Value(COLUMNNAME_LoadDocument);
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

	public org.compiere.model.I_AD_User getSubmitte() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSubmittedBy(), get_TrxName());	}

	/** Set Submitted By.
		@param SubmittedBy Submitted By	  */
	public void setSubmittedBy (int SubmittedBy)
	{
		set_Value (COLUMNNAME_SubmittedBy, Integer.valueOf(SubmittedBy));
	}

	/** Get Submitted By.
		@return Submitted By	  */
	public int getSubmittedBy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SubmittedBy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handover Document.
		@param UNS_HandoverDoc_ID Handover Document	  */
	public void setUNS_HandoverDoc_ID (int UNS_HandoverDoc_ID)
	{
		if (UNS_HandoverDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_ID, Integer.valueOf(UNS_HandoverDoc_ID));
	}

	/** Get Handover Document.
		@return Handover Document	  */
	public int getUNS_HandoverDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HandoverDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_HandoverDoc_UU.
		@param UNS_HandoverDoc_UU UNS_HandoverDoc_UU	  */
	public void setUNS_HandoverDoc_UU (String UNS_HandoverDoc_UU)
	{
		set_Value (COLUMNNAME_UNS_HandoverDoc_UU, UNS_HandoverDoc_UU);
	}

	/** Get UNS_HandoverDoc_UU.
		@return UNS_HandoverDoc_UU	  */
	public String getUNS_HandoverDoc_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_HandoverDoc_UU);
	}

	/** Set Packing List Return.
		@param UNS_PL_Return_ID Packing List Return	  */
	public void setUNS_PL_Return_ID (int UNS_PL_Return_ID)
	{
		if (UNS_PL_Return_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Return_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Return_ID, Integer.valueOf(UNS_PL_Return_ID));
	}

	/** Get Packing List Return.
		@return Packing List Return	  */
	public int getUNS_PL_Return_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_Return_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}