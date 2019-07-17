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

/** Generated Model for UNS_Reimbursement
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Reimbursement extends PO implements I_UNS_Reimbursement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131205L;

    /** Standard Constructor */
    public X_UNS_Reimbursement (Properties ctx, int UNS_Reimbursement_ID, String trxName)
    {
      super (ctx, UNS_Reimbursement_ID, trxName);
      /** if (UNS_Reimbursement_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_DocType_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @RequestDate@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
			setPosted (false);
// N
			setProcessed (false);
			setProcessing (false);
			setReason (null);
			setRemainingAllowance (Env.ZERO);
// 0
			setRequestDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setSubjectCosts (Env.ZERO);
// 0
			setTotalPaid (Env.ZERO);
// 0
			setUNS_Employee_ID (0);
			setUNS_MedicalRecord_ID (0);
			setUNS_Reimbursement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Reimbursement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Reimbursement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Cash / Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Cash / Bank Account.
		@return Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** Set Medical Allowance.
		@param MedicalAllowance 
		The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance)
	{
		set_Value (COLUMNNAME_MedicalAllowance, MedicalAllowance);
	}

	/** Get Medical Allowance.
		@return The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

	/** Set Remaining Allowance Amt.
		@param RemainingAllowance 
		Remaining yearly employee's medical allowance amt
	  */
	public void setRemainingAllowance (BigDecimal RemainingAllowance)
	{
		set_Value (COLUMNNAME_RemainingAllowance, RemainingAllowance);
	}

	/** Get Remaining Allowance Amt.
		@return Remaining yearly employee's medical allowance amt
	  */
	public BigDecimal getRemainingAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainingAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Set Request Date.
		@param RequestDate Request Date	  */
	public void setRequestDate (Timestamp RequestDate)
	{
		set_Value (COLUMNNAME_RequestDate, RequestDate);
	}

	/** Get Request Date.
		@return Request Date	  */
	public Timestamp getRequestDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_RequestDate);
	}

	/** Set Medical Costs.
		@param SubjectCosts 
		The subject of cost to be reimbursed
	  */
	public void setSubjectCosts (BigDecimal SubjectCosts)
	{
		set_Value (COLUMNNAME_SubjectCosts, SubjectCosts);
	}

	/** Get Medical Costs.
		@return The subject of cost to be reimbursed
	  */
	public BigDecimal getSubjectCosts () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SubjectCosts);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Paid.
		@param TotalPaid Total Paid	  */
	public void setTotalPaid (BigDecimal TotalPaid)
	{
		set_Value (COLUMNNAME_TotalPaid, TotalPaid);
	}

	/** Get Total Paid.
		@return Total Paid	  */
	public BigDecimal getTotalPaid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalPaid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_MedicalRecord getUNS_MedicalRecord() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MedicalRecord)MTable.get(getCtx(), com.uns.model.I_UNS_MedicalRecord.Table_Name)
			.getPO(getUNS_MedicalRecord_ID(), get_TrxName());	}

	/** Set Medical Record.
		@param UNS_MedicalRecord_ID Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID)
	{
		if (UNS_MedicalRecord_ID < 1) 
			set_Value (COLUMNNAME_UNS_MedicalRecord_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MedicalRecord_ID, Integer.valueOf(UNS_MedicalRecord_ID));
	}

	/** Get Medical Record.
		@return Medical Record	  */
	public int getUNS_MedicalRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MedicalRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reimbursement.
		@param UNS_Reimbursement_ID 
		Reimbursement
	  */
	public void setUNS_Reimbursement_ID (int UNS_Reimbursement_ID)
	{
		if (UNS_Reimbursement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Reimbursement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Reimbursement_ID, Integer.valueOf(UNS_Reimbursement_ID));
	}

	/** Get Reimbursement.
		@return Reimbursement
	  */
	public int getUNS_Reimbursement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Reimbursement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Reimbursement_UU.
		@param UNS_Reimbursement_UU 
		UNS_Reimbursement_UU
	  */
	public void setUNS_Reimbursement_UU (String UNS_Reimbursement_UU)
	{
		set_Value (COLUMNNAME_UNS_Reimbursement_UU, UNS_Reimbursement_UU);
	}

	/** Get UNS_Reimbursement_UU.
		@return UNS_Reimbursement_UU
	  */
	public String getUNS_Reimbursement_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Reimbursement_UU);
	}
}