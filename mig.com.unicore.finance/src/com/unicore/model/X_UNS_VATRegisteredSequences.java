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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_VATRegisteredSequences
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VATRegisteredSequences extends PO implements I_UNS_VATRegisteredSequences, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170608L;

    /** Standard Constructor */
    public X_UNS_VATRegisteredSequences (Properties ctx, int UNS_VATRegisteredSequences_ID, String trxName)
    {
      super (ctx, UNS_VATRegisteredSequences_ID, trxName);
      /** if (UNS_VATRegisteredSequences_ID == 0)
        {
			setDateReceived (new Timestamp( System.currentTimeMillis() ));
			setDateRequired (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setPrefixNo (null);
			setProcessed (false);
			setReplacementPrefix (null);
			setSequenceEndNo (0);
			setSequenceStartNo (0);
			setUNS_VATRegisteredSequences_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VATRegisteredSequences (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VATRegisteredSequences[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
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

	/** Set PrefixNo.
		@param PrefixNo PrefixNo	  */
	public void setPrefixNo (String PrefixNo)
	{
		set_Value (COLUMNNAME_PrefixNo, PrefixNo);
	}

	/** Get PrefixNo.
		@return PrefixNo	  */
	public String getPrefixNo () 
	{
		return (String)get_Value(COLUMNNAME_PrefixNo);
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

	/** Set ReceivedBy.
		@param ReceivedBy ReceivedBy	  */
	public void setReceivedBy (String ReceivedBy)
	{
		set_Value (COLUMNNAME_ReceivedBy, ReceivedBy);
	}

	/** Get ReceivedBy.
		@return ReceivedBy	  */
	public String getReceivedBy () 
	{
		return (String)get_Value(COLUMNNAME_ReceivedBy);
	}

	/** Set Received By.
		@param ReceivedBy_ID Received By	  */
	public void setReceivedBy_ID (int ReceivedBy_ID)
	{
		if (ReceivedBy_ID < 1) 
			set_Value (COLUMNNAME_ReceivedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ReceivedBy_ID, Integer.valueOf(ReceivedBy_ID));
	}

	/** Get Received By.
		@return Received By	  */
	public int getReceivedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReceivedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Replacement Prefix.
		@param ReplacementPrefix Replacement Prefix	  */
	public void setReplacementPrefix (String ReplacementPrefix)
	{
		set_Value (COLUMNNAME_ReplacementPrefix, ReplacementPrefix);
	}

	/** Get Replacement Prefix.
		@return Replacement Prefix	  */
	public String getReplacementPrefix () 
	{
		return (String)get_Value(COLUMNNAME_ReplacementPrefix);
	}

	/** Set Requested_By.
		@param Requested_By Requested_By	  */
	public void setRequested_By (String Requested_By)
	{
		set_ValueNoCheck (COLUMNNAME_Requested_By, Requested_By);
	}

	/** Get Requested_By.
		@return Requested_By	  */
	public String getRequested_By () 
	{
		return (String)get_Value(COLUMNNAME_Requested_By);
	}

	/** Set RequestedBy_ID.
		@param RequestedBy_ID RequestedBy_ID	  */
	public void setRequestedBy_ID (int RequestedBy_ID)
	{
		if (RequestedBy_ID < 1) 
			set_Value (COLUMNNAME_RequestedBy_ID, null);
		else 
			set_Value (COLUMNNAME_RequestedBy_ID, Integer.valueOf(RequestedBy_ID));
	}

	/** Get RequestedBy_ID.
		@return RequestedBy_ID	  */
	public int getRequestedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RequestedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SequenceEndNo.
		@param SequenceEndNo SequenceEndNo	  */
	public void setSequenceEndNo (int SequenceEndNo)
	{
		set_Value (COLUMNNAME_SequenceEndNo, Integer.valueOf(SequenceEndNo));
	}

	/** Get SequenceEndNo.
		@return SequenceEndNo	  */
	public int getSequenceEndNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SequenceEndNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SequenceStartNo.
		@param SequenceStartNo SequenceStartNo	  */
	public void setSequenceStartNo (int SequenceStartNo)
	{
		set_Value (COLUMNNAME_SequenceStartNo, Integer.valueOf(SequenceStartNo));
	}

	/** Get SequenceStartNo.
		@return SequenceStartNo	  */
	public int getSequenceStartNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SequenceStartNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TotalNumbers.
		@param TotalNumbers TotalNumbers	  */
	public void setTotalNumbers (int TotalNumbers)
	{
		set_Value (COLUMNNAME_TotalNumbers, Integer.valueOf(TotalNumbers));
	}

	/** Get TotalNumbers.
		@return TotalNumbers	  */
	public int getTotalNumbers () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalNumbers);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VAT Registered Sequences.
		@param UNS_VATRegisteredSequences_ID VAT Registered Sequences	  */
	public void setUNS_VATRegisteredSequences_ID (int UNS_VATRegisteredSequences_ID)
	{
		if (UNS_VATRegisteredSequences_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATRegisteredSequences_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATRegisteredSequences_ID, Integer.valueOf(UNS_VATRegisteredSequences_ID));
	}

	/** Get VAT Registered Sequences.
		@return VAT Registered Sequences	  */
	public int getUNS_VATRegisteredSequences_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATRegisteredSequences_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VATRegisteredSequences_UU.
		@param UNS_VATRegisteredSequences_UU UNS_VATRegisteredSequences_UU	  */
	public void setUNS_VATRegisteredSequences_UU (String UNS_VATRegisteredSequences_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_VATRegisteredSequences_UU, UNS_VATRegisteredSequences_UU);
	}

	/** Get UNS_VATRegisteredSequences_UU.
		@return UNS_VATRegisteredSequences_UU	  */
	public String getUNS_VATRegisteredSequences_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VATRegisteredSequences_UU);
	}

	/** Set UsedNumber.
		@param UsedNumber UsedNumber	  */
	public void setUsedNumber (int UsedNumber)
	{
		throw new IllegalArgumentException ("UsedNumber is virtual column");	}

	/** Get UsedNumber.
		@return UsedNumber	  */
	public int getUsedNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UsedNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}