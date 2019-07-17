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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Contract_Evaluation
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Contract_Evaluation extends PO implements I_UNS_Contract_Evaluation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140226L;

    /** Standard Constructor */
    public X_UNS_Contract_Evaluation (Properties ctx, int UNS_Contract_Evaluation_ID, String trxName)
    {
      super (ctx, UNS_Contract_Evaluation_ID, trxName);
      /** if (UNS_Contract_Evaluation_ID == 0)
        {
			setCreateNewSalary (null);
// N
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setProcessed (false);
// N
			setRecommendation (null);
			setUNS_Contract_Evaluation_ID (0);
			setUNS_Contract_Recommendation_ID (0);
			setUNS_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Contract_Evaluation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Contract_Evaluation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Employee Dept..
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Employee Dept..
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create New Contract.
		@param CreateNewSalary Create New Contract	  */
	public void setCreateNewSalary (String CreateNewSalary)
	{
		set_Value (COLUMNNAME_CreateNewSalary, CreateNewSalary);
	}

	/** Get Create New Contract.
		@return Create New Contract	  */
	public String getCreateNewSalary () 
	{
		return (String)get_Value(COLUMNNAME_CreateNewSalary);
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

	/** Set Evaluation Detail Ref No.
		@param EvalDetailRefNo Evaluation Detail Ref No	  */
	public void setEvalDetailRefNo (String EvalDetailRefNo)
	{
		set_Value (COLUMNNAME_EvalDetailRefNo, EvalDetailRefNo);
	}

	/** Get Evaluation Detail Ref No.
		@return Evaluation Detail Ref No	  */
	public String getEvalDetailRefNo () 
	{
		return (String)get_Value(COLUMNNAME_EvalDetailRefNo);
	}

	/** Grade AD_Reference_ID=1000093 */
	public static final int GRADE_AD_Reference_ID=1000093;
	/** A = 1 */
	public static final String GRADE_A = "1";
	/** B = 2 */
	public static final String GRADE_B = "2";
	/** C = 3 */
	public static final String GRADE_C = "3";
	/** D = 4 */
	public static final String GRADE_D = "4";
	/** E = 5 */
	public static final String GRADE_E = "5";
	/** Set Grade.
		@param Grade Grade	  */
	public void setGrade (String Grade)
	{

		set_Value (COLUMNNAME_Grade, Grade);
	}

	/** Get Grade.
		@return Grade	  */
	public String getGrade () 
	{
		return (String)get_Value(COLUMNNAME_Grade);
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

	/** Set Last Contract Date.
		@param LastContractDate Last Contract Date	  */
	public void setLastContractDate (Timestamp LastContractDate)
	{
		set_Value (COLUMNNAME_LastContractDate, LastContractDate);
	}

	/** Get Last Contract Date.
		@return Last Contract Date	  */
	public Timestamp getLastContractDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastContractDate);
	}

	/** Set Last End Contract Date.
		@param LastEndContractDate Last End Contract Date	  */
	public void setLastEndContractDate (Timestamp LastEndContractDate)
	{
		set_Value (COLUMNNAME_LastEndContractDate, LastEndContractDate);
	}

	/** Get Last End Contract Date.
		@return Last End Contract Date	  */
	public Timestamp getLastEndContractDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastEndContractDate);
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

	/** Recommendation AD_Reference_ID=1000091 */
	public static final int RECOMMENDATION_AD_Reference_ID=1000091;
	/** To Permanen Status = PS */
	public static final String RECOMMENDATION_ToPermanenStatus = "PS";
	/** Contract 2 = SC */
	public static final String RECOMMENDATION_Contract2 = "SC";
	/** Re-Contract = RC */
	public static final String RECOMMENDATION_Re_Contract = "RC";
	/** Contract Termination = CT */
	public static final String RECOMMENDATION_ContractTermination = "CT";
	/** Move To Another Dept = MD */
	public static final String RECOMMENDATION_MoveToAnotherDept = "MD";
	/** Promoted To Position = PP */
	public static final String RECOMMENDATION_PromotedToPosition = "PP";
	/** Demoted To Position = DP */
	public static final String RECOMMENDATION_DemotedToPosition = "DP";
	/** Sequence Contract = SQ */
	public static final String RECOMMENDATION_SequenceContract = "SQ";
	/** Set Recommendation.
		@param Recommendation Recommendation	  */
	public void setRecommendation (String Recommendation)
	{

		set_Value (COLUMNNAME_Recommendation, Recommendation);
	}

	/** Get Recommendation.
		@return Recommendation	  */
	public String getRecommendation () 
	{
		return (String)get_Value(COLUMNNAME_Recommendation);
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

	/** Set Total Grade.
		@param TotalGrade Total Grade	  */
	public void setTotalGrade (BigDecimal TotalGrade)
	{
		set_Value (COLUMNNAME_TotalGrade, TotalGrade);
	}

	/** Get Total Grade.
		@return Total Grade	  */
	public BigDecimal getTotalGrade () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalGrade);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Contract Evaluation.
		@param UNS_Contract_Evaluation_ID Contract Evaluation	  */
	public void setUNS_Contract_Evaluation_ID (int UNS_Contract_Evaluation_ID)
	{
		if (UNS_Contract_Evaluation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Contract_Evaluation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Contract_Evaluation_ID, Integer.valueOf(UNS_Contract_Evaluation_ID));
	}

	/** Get Contract Evaluation.
		@return Contract Evaluation	  */
	public int getUNS_Contract_Evaluation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Contract_Evaluation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Contract_Evaluation_UU.
		@param UNS_Contract_Evaluation_UU UNS_Contract_Evaluation_UU	  */
	public void setUNS_Contract_Evaluation_UU (String UNS_Contract_Evaluation_UU)
	{
		set_Value (COLUMNNAME_UNS_Contract_Evaluation_UU, UNS_Contract_Evaluation_UU);
	}

	/** Get UNS_Contract_Evaluation_UU.
		@return UNS_Contract_Evaluation_UU	  */
	public String getUNS_Contract_Evaluation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Contract_Evaluation_UU);
	}

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Contract_Recommendation)MTable.get(getCtx(), com.uns.model.I_UNS_Contract_Recommendation.Table_Name)
			.getPO(getUNS_Contract_Recommendation_ID(), get_TrxName());	}

	/** Set Contract.
		@param UNS_Contract_Recommendation_ID Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID)
	{
		if (UNS_Contract_Recommendation_ID < 1) 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, Integer.valueOf(UNS_Contract_Recommendation_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getUNS_Contract_Recommendation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Contract_Recommendation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Employee_ID()));
    }
}