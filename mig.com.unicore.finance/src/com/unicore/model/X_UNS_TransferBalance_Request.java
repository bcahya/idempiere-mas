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

/** Generated Model for UNS_TransferBalance_Request
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_TransferBalance_Request extends PO implements I_UNS_TransferBalance_Request, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180605L;

    /** Standard Constructor */
    public X_UNS_TransferBalance_Request (Properties ctx, int UNS_TransferBalance_Request_ID, String trxName)
    {
      super (ctx, UNS_TransferBalance_Request_ID, trxName);
      /** if (UNS_TransferBalance_Request_ID == 0)
        {
			setAccountTo_ID (0);
			setAD_OrgTo_ID (0);
			setAmountRequested (Env.ZERO);
// 0
			setC_BPartner_ID (0);
			setDateRequired (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
			setProcessed (false);
			setRequestType (null);
// BL
			setTrxAmt (Env.ZERO);
// 0
			setUNS_TransferBalance_Request_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_TransferBalance_Request (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_TransferBalance_Request[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BankAccount getAccountFrom() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getAccountFrom_ID(), get_TrxName());	}

	/** Set Account From.
		@param AccountFrom_ID 
		The account the transfer requested from
	  */
	public void setAccountFrom_ID (int AccountFrom_ID)
	{
		if (AccountFrom_ID < 1) 
			set_Value (COLUMNNAME_AccountFrom_ID, null);
		else 
			set_Value (COLUMNNAME_AccountFrom_ID, Integer.valueOf(AccountFrom_ID));
	}

	/** Get Account From.
		@return The account the transfer requested from
	  */
	public int getAccountFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AccountFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BankAccount getAccountTo() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getAccountTo_ID(), get_TrxName());	}

	/** Set Account To.
		@param AccountTo_ID 
		The account the balance will be transfered to
	  */
	public void setAccountTo_ID (int AccountTo_ID)
	{
		if (AccountTo_ID < 1) 
			set_Value (COLUMNNAME_AccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_AccountTo_ID, Integer.valueOf(AccountTo_ID));
	}

	/** Get Account To.
		@return The account the balance will be transfered to
	  */
	public int getAccountTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AccountTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Inter-Organization.
		@param AD_OrgTo_ID 
		Organization valid for intercompany documents
	  */
	public void setAD_OrgTo_ID (int AD_OrgTo_ID)
	{
		if (AD_OrgTo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTo_ID, Integer.valueOf(AD_OrgTo_ID));
	}

	/** Get Inter-Organization.
		@return Organization valid for intercompany documents
	  */
	public int getAD_OrgTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Confirmed Amount.
		@param AmountConfirmed Confirmed Amount	  */
	public void setAmountConfirmed (BigDecimal AmountConfirmed)
	{
		set_Value (COLUMNNAME_AmountConfirmed, AmountConfirmed);
	}

	/** Get Confirmed Amount.
		@return Confirmed Amount	  */
	public BigDecimal getAmountConfirmed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountConfirmed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Requested Amount.
		@param AmountRequested Requested Amount	  */
	public void setAmountRequested (BigDecimal AmountRequested)
	{
		set_Value (COLUMNNAME_AmountRequested, AmountRequested);
	}

	/** Get Requested Amount.
		@return Requested Amount	  */
	public BigDecimal getAmountRequested () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountRequested);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Load Statement Line.
		@param LoadStatementLine Load Statement Line	  */
	public void setLoadStatementLine (String LoadStatementLine)
	{
		set_Value (COLUMNNAME_LoadStatementLine, LoadStatementLine);
	}

	/** Get Load Statement Line.
		@return Load Statement Line	  */
	public String getLoadStatementLine () 
	{
		return (String)get_Value(COLUMNNAME_LoadStatementLine);
	}

	/** Set Previous Balance.
		@param PrevBalanceAmt Previous Balance	  */
	public void setPrevBalanceAmt (BigDecimal PrevBalanceAmt)
	{
		set_Value (COLUMNNAME_PrevBalanceAmt, PrevBalanceAmt);
	}

	/** Get Previous Balance.
		@return Previous Balance	  */
	public BigDecimal getPrevBalanceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevBalanceAmt);
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

	/** Balance = BL */
	public static final String REQUESTTYPE_Balance = "BL";
	/** Petty Cash = PC */
	public static final String REQUESTTYPE_PettyCash = "PC";
	/** Set Request Type.
		@param RequestType Request Type	  */
	public void setRequestType (String RequestType)
	{

		set_Value (COLUMNNAME_RequestType, RequestType);
	}

	/** Get Request Type.
		@return Request Type	  */
	public String getRequestType () 
	{
		return (String)get_Value(COLUMNNAME_RequestType);
	}

	/** Set Transaction Amount.
		@param TrxAmt 
		Amount of a transaction
	  */
	public void setTrxAmt (BigDecimal TrxAmt)
	{
		set_Value (COLUMNNAME_TrxAmt, TrxAmt);
	}

	/** Get Transaction Amount.
		@return Amount of a transaction
	  */
	public BigDecimal getTrxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TrxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transfer Balance Request.
		@param UNS_TransferBalance_Request_ID Transfer Balance Request	  */
	public void setUNS_TransferBalance_Request_ID (int UNS_TransferBalance_Request_ID)
	{
		if (UNS_TransferBalance_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Request_ID, Integer.valueOf(UNS_TransferBalance_Request_ID));
	}

	/** Get Transfer Balance Request.
		@return Transfer Balance Request	  */
	public int getUNS_TransferBalance_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TransferBalance_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_TransferBalance_Request_UU.
		@param UNS_TransferBalance_Request_UU UNS_TransferBalance_Request_UU	  */
	public void setUNS_TransferBalance_Request_UU (String UNS_TransferBalance_Request_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Request_UU, UNS_TransferBalance_Request_UU);
	}

	/** Get UNS_TransferBalance_Request_UU.
		@return UNS_TransferBalance_Request_UU	  */
	public String getUNS_TransferBalance_Request_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_TransferBalance_Request_UU);
	}
}