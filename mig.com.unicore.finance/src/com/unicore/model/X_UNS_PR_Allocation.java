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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_PR_Allocation
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PR_Allocation extends PO implements I_UNS_PR_Allocation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171030L;

    /** Standard Constructor */
    public X_UNS_PR_Allocation (Properties ctx, int UNS_PR_Allocation_ID, String trxName)
    {
      super (ctx, UNS_PR_Allocation_ID, trxName);
      /** if (UNS_PR_Allocation_ID == 0)
        {
			setAmountTrf (Env.ZERO);
// 0
			setDifferenceAmt (Env.ZERO);
// 0
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setGrandTotal (Env.ZERO);
// 0
			setIsApproved (false);
// N
			setPaidAmt (Env.ZERO);
// 0
			setProcessed (false);
// N
			setReceiptAmt (Env.ZERO);
// 0
			setUNS_PR_Allocation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PR_Allocation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PR_Allocation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount Transferred.
		@param AmountTrf Amount Transferred	  */
	public void setAmountTrf (BigDecimal AmountTrf)
	{
		set_Value (COLUMNNAME_AmountTrf, AmountTrf);
	}

	/** Get Amount Transferred.
		@return Amount Transferred	  */
	public BigDecimal getAmountTrf () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountTrf);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Confirmation No.
		@param ConfirmationNo 
		Confirmation Number
	  */
	public void setConfirmationNo (String ConfirmationNo)
	{
		set_Value (COLUMNNAME_ConfirmationNo, ConfirmationNo);
	}

	/** Get Confirmation No.
		@return Confirmation Number
	  */
	public String getConfirmationNo () 
	{
		return (String)get_Value(COLUMNNAME_ConfirmationNo);
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

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Paid Amount.
		@param PaidAmt Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	/** Get Paid Amount.
		@return Paid Amount	  */
	public BigDecimal getPaidAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Receipt Amount.
		@param ReceiptAmt Receipt Amount	  */
	public void setReceiptAmt (BigDecimal ReceiptAmt)
	{
		set_Value (COLUMNNAME_ReceiptAmt, ReceiptAmt);
	}

	/** Get Receipt Amount.
		@return Receipt Amount	  */
	public BigDecimal getReceiptAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceiptAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_BillingGroup_Result getUNS_BillingGroup_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingGroup_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingGroup_Result.Table_Name)
			.getPO(getUNS_BillingGroup_Result_ID(), get_TrxName());	}

	/** Set Grouping Billing Result.
		@param UNS_BillingGroup_Result_ID Grouping Billing Result	  */
	public void setUNS_BillingGroup_Result_ID (int UNS_BillingGroup_Result_ID)
	{
		if (UNS_BillingGroup_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_Result_ID, Integer.valueOf(UNS_BillingGroup_Result_ID));
	}

	/** Get Grouping Billing Result.
		@return Grouping Billing Result	  */
	public int getUNS_BillingGroup_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingGroup_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_PaymentReceipt getUNS_PaymentReceipt() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PaymentReceipt)MTable.get(getCtx(), com.unicore.model.I_UNS_PaymentReceipt.Table_Name)
			.getPO(getUNS_PaymentReceipt_ID(), get_TrxName());	}

	/** Set Billing Payment Receipt.
		@param UNS_PaymentReceipt_ID Billing Payment Receipt	  */
	public void setUNS_PaymentReceipt_ID (int UNS_PaymentReceipt_ID)
	{
		if (UNS_PaymentReceipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PaymentReceipt_ID, Integer.valueOf(UNS_PaymentReceipt_ID));
	}

	/** Get Billing Payment Receipt.
		@return Billing Payment Receipt	  */
	public int getUNS_PaymentReceipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PaymentReceipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Billing Payment Allocation.
		@param UNS_PR_Allocation_ID Billing Payment Allocation	  */
	public void setUNS_PR_Allocation_ID (int UNS_PR_Allocation_ID)
	{
		if (UNS_PR_Allocation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PR_Allocation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PR_Allocation_ID, Integer.valueOf(UNS_PR_Allocation_ID));
	}

	/** Get Billing Payment Allocation.
		@return Billing Payment Allocation	  */
	public int getUNS_PR_Allocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PR_Allocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PR_Allocation_UU.
		@param UNS_PR_Allocation_UU UNS_PR_Allocation_UU	  */
	public void setUNS_PR_Allocation_UU (String UNS_PR_Allocation_UU)
	{
		set_Value (COLUMNNAME_UNS_PR_Allocation_UU, UNS_PR_Allocation_UU);
	}

	/** Get UNS_PR_Allocation_UU.
		@return UNS_PR_Allocation_UU	  */
	public String getUNS_PR_Allocation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PR_Allocation_UU);
	}
}