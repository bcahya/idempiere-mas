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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_LC_Balance
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_Balance extends PO implements I_UNS_LC_Balance, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131103L;

    /** Standard Constructor */
    public X_UNS_LC_Balance (Properties ctx, int UNS_LC_Balance_ID, String trxName)
    {
      super (ctx, UNS_LC_Balance_ID, trxName);
      /** if (UNS_LC_Balance_ID == 0)
        {
			setDescription (null);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setFormatCode (null);
			setFromCode (null);
			setGrandTotal (Env.ZERO);
// 0
			setIsApproved (false);
// N
			setIssuingBank (null);
			setLC_No (null);
			setName (null);
			setOBS_Period (0);
// 0
			setPriority (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setReciver (null);
			setSwiftCode (null);
			setToleranceAmt (Env.ZERO);
// 0
			setTotalAmt (Env.ZERO);
// 0
			setTotalAmtLeft (Env.ZERO);
// 0
			setTotalAmtUsed (Env.ZERO);
// 0
			setTrxNo (0);
			setUNS_LC_Balance_ID (0);
			setUserRef (null);
			setVerNo (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_Balance (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_Balance[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Bank Priority.
		@param BankPriority Bank Priority	  */
	public void setBankPriority (String BankPriority)
	{
		set_Value (COLUMNNAME_BankPriority, BankPriority);
	}

	/** Get Bank Priority.
		@return Bank Priority	  */
	public String getBankPriority () 
	{
		return (String)get_Value(COLUMNNAME_BankPriority);
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
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

	/** Set Format Code.
		@param FormatCode Format Code	  */
	public void setFormatCode (String FormatCode)
	{
		set_Value (COLUMNNAME_FormatCode, FormatCode);
	}

	/** Get Format Code.
		@return Format Code	  */
	public String getFormatCode () 
	{
		return (String)get_Value(COLUMNNAME_FormatCode);
	}

	/** Set From.
		@param FromCode From	  */
	public void setFromCode (String FromCode)
	{
		set_Value (COLUMNNAME_FromCode, FromCode);
	}

	/** Get From.
		@return From	  */
	public String getFromCode () 
	{
		return (String)get_Value(COLUMNNAME_FromCode);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
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

	/** Set Issuing Bank.
		@param IssuingBank Issuing Bank	  */
	public void setIssuingBank (String IssuingBank)
	{
		set_Value (COLUMNNAME_IssuingBank, IssuingBank);
	}

	/** Get Issuing Bank.
		@return Issuing Bank	  */
	public String getIssuingBank () 
	{
		return (String)get_Value(COLUMNNAME_IssuingBank);
	}

	/** Set LC No.
		@param LC_No LC No	  */
	public void setLC_No (String LC_No)
	{
		set_Value (COLUMNNAME_LC_No, LC_No);
	}

	/** Get LC No.
		@return LC No	  */
	public String getLC_No () 
	{
		return (String)get_Value(COLUMNNAME_LC_No);
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

	/** Set OBS_Period.
		@param OBS_Period OBS_Period	  */
	public void setOBS_Period (int OBS_Period)
	{
		set_Value (COLUMNNAME_OBS_Period, Integer.valueOf(OBS_Period));
	}

	/** Get OBS_Period.
		@return OBS_Period	  */
	public int getOBS_Period () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OBS_Period);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority () 
	{
		return (String)get_Value(COLUMNNAME_Priority);
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

	/** Set Reciver.
		@param Reciver Reciver	  */
	public void setReciver (String Reciver)
	{
		set_Value (COLUMNNAME_Reciver, Reciver);
	}

	/** Get Reciver.
		@return Reciver	  */
	public String getReciver () 
	{
		return (String)get_Value(COLUMNNAME_Reciver);
	}

	/** Set Swift code.
		@param SwiftCode 
		Swift Code or BIC
	  */
	public void setSwiftCode (String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	/** Get Swift code.
		@return Swift Code or BIC
	  */
	public String getSwiftCode () 
	{
		return (String)get_Value(COLUMNNAME_SwiftCode);
	}

	/** Set Tolerance (%).
		@param ToleranceAmt 
		Tolerance amount in percent values (i.e. 10 for 10%)
	  */
	public void setToleranceAmt (BigDecimal ToleranceAmt)
	{
		set_ValueNoCheck (COLUMNNAME_ToleranceAmt, ToleranceAmt);
	}

	/** Get Tolerance (%).
		@return Tolerance amount in percent values (i.e. 10 for 10%)
	  */
	public BigDecimal getToleranceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ToleranceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
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

	/** Set Total Amt Left.
		@param TotalAmtLeft Total Amt Left	  */
	public void setTotalAmtLeft (BigDecimal TotalAmtLeft)
	{
		set_Value (COLUMNNAME_TotalAmtLeft, TotalAmtLeft);
	}

	/** Get Total Amt Left.
		@return Total Amt Left	  */
	public BigDecimal getTotalAmtLeft () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmtLeft);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amt Used.
		@param TotalAmtUsed Total Amt Used	  */
	public void setTotalAmtUsed (BigDecimal TotalAmtUsed)
	{
		set_Value (COLUMNNAME_TotalAmtUsed, TotalAmtUsed);
	}

	/** Get Total Amt Used.
		@return Total Amt Used	  */
	public BigDecimal getTotalAmtUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmtUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Trx No.
		@param TrxNo Trx No	  */
	public void setTrxNo (int TrxNo)
	{
		set_Value (COLUMNNAME_TrxNo, Integer.valueOf(TrxNo));
	}

	/** Get Trx No.
		@return Trx No	  */
	public int getTrxNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TrxNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LC Balance.
		@param UNS_LC_Balance_ID LC Balance	  */
	public void setUNS_LC_Balance_ID (int UNS_LC_Balance_ID)
	{
		if (UNS_LC_Balance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Balance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Balance_ID, Integer.valueOf(UNS_LC_Balance_ID));
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

	/** Set UNS_LC_Balance_UU.
		@param UNS_LC_Balance_UU UNS_LC_Balance_UU	  */
	public void setUNS_LC_Balance_UU (String UNS_LC_Balance_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_Balance_UU, UNS_LC_Balance_UU);
	}

	/** Get UNS_LC_Balance_UU.
		@return UNS_LC_Balance_UU	  */
	public String getUNS_LC_Balance_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_Balance_UU);
	}

	/** Set User Ref.
		@param UserRef User Ref	  */
	public void setUserRef (String UserRef)
	{
		set_Value (COLUMNNAME_UserRef, UserRef);
	}

	/** Get User Ref.
		@return User Ref	  */
	public String getUserRef () 
	{
		return (String)get_Value(COLUMNNAME_UserRef);
	}

	/** Set Ver No.
		@param VerNo Ver No	  */
	public void setVerNo (int VerNo)
	{
		set_Value (COLUMNNAME_VerNo, Integer.valueOf(VerNo));
	}

	/** Get Ver No.
		@return Ver No	  */
	public int getVerNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VerNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}