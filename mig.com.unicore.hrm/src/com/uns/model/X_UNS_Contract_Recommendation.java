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

/** Generated Model for UNS_Contract_Recommendation
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Contract_Recommendation extends PO implements I_UNS_Contract_Recommendation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_Contract_Recommendation (Properties ctx, int UNS_Contract_Recommendation_ID, String trxName)
    {
      super (ctx, UNS_Contract_Recommendation_ID, trxName);
      /** if (UNS_Contract_Recommendation_ID == 0)
        {
			setDateContractEnd (new Timestamp( System.currentTimeMillis() ));
			setDateContractStart (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setEmploymentType (null);
			setGenerateNIK (null);
// N
			setIsApproved (false);
// N
			setIsJHTApplyed (false);
// N
			setIsJKApplyed (false);
// N
			setIsJKKApplyed (false);
// N
			setIsJPApplied (false);
// N
			setIsJPKApplyed (false);
// N
			setIsMoveTo (false);
// N
			setIsUseGeneralPayroll (false);
// N
			setLoadBasicPayroll (null);
// N
			setNewDept_ID (0);
// @AD_Org_ID@
			setNewGender (null);
			setNewJob_ID (0);
			setNewNIK (null);
			setNewPayrollLevel (null);
			setNewSectionOfDept_ID (0);
			setNewShift (null);
// NS
			setNextPayrollTerm (null);
			setProcessed (false);
// N
			setUNS_Contract_Recommendation_ID (0);
			setUNS_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Contract_Recommendation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Contract_Recommendation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Contract End.
		@param DateContractEnd Date Contract End	  */
	public void setDateContractEnd (Timestamp DateContractEnd)
	{
		set_Value (COLUMNNAME_DateContractEnd, DateContractEnd);
	}

	/** Get Date Contract End.
		@return Date Contract End	  */
	public Timestamp getDateContractEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateContractEnd);
	}

	/** Set Date Contract Start.
		@param DateContractStart Date Contract Start	  */
	public void setDateContractStart (Timestamp DateContractStart)
	{
		set_Value (COLUMNNAME_DateContractStart, DateContractStart);
	}

	/** Get Date Contract Start.
		@return Date Contract Start	  */
	public Timestamp getDateContractStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateContractStart);
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

	/** Company = COM */
	public static final String EMPLOYMENTTYPE_Company = "COM";
	/** Sub Contract = SUB */
	public static final String EMPLOYMENTTYPE_SubContract = "SUB";
	/** Set Employment Type.
		@param EmploymentType Employment Type	  */
	public void setEmploymentType (String EmploymentType)
	{

		set_Value (COLUMNNAME_EmploymentType, EmploymentType);
	}

	/** Get Employment Type.
		@return Employment Type	  */
	public String getEmploymentType () 
	{
		return (String)get_Value(COLUMNNAME_EmploymentType);
	}

	/** Set Generate NIK.
		@param GenerateNIK Generate NIK	  */
	public void setGenerateNIK (String GenerateNIK)
	{
		set_Value (COLUMNNAME_GenerateNIK, GenerateNIK);
	}

	/** Get Generate NIK.
		@return Generate NIK	  */
	public String getGenerateNIK () 
	{
		return (String)get_Value(COLUMNNAME_GenerateNIK);
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

	/** Set JHT Applied.
		@param IsJHTApplyed 
		centang jika memiliki jaminan hari tua
	  */
	public void setIsJHTApplyed (boolean IsJHTApplyed)
	{
		set_Value (COLUMNNAME_IsJHTApplyed, Boolean.valueOf(IsJHTApplyed));
	}

	/** Get JHT Applied.
		@return centang jika memiliki jaminan hari tua
	  */
	public boolean isJHTApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJHTApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JK Applied.
		@param IsJKApplyed 
		centang jika memiliki jaminan kematian
	  */
	public void setIsJKApplyed (boolean IsJKApplyed)
	{
		set_Value (COLUMNNAME_IsJKApplyed, Boolean.valueOf(IsJKApplyed));
	}

	/** Get JK Applied.
		@return centang jika memiliki jaminan kematian
	  */
	public boolean isJKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JKK Applied.
		@param IsJKKApplyed 
		centang jika memiliki jaminan kecelakaan kerja
	  */
	public void setIsJKKApplyed (boolean IsJKKApplyed)
	{
		set_Value (COLUMNNAME_IsJKKApplyed, Boolean.valueOf(IsJKKApplyed));
	}

	/** Get JKK Applied.
		@return centang jika memiliki jaminan kecelakaan kerja
	  */
	public boolean isJKKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJKKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JP Applied.
		@param IsJPApplied JP Applied	  */
	public void setIsJPApplied (boolean IsJPApplied)
	{
		set_Value (COLUMNNAME_IsJPApplied, Boolean.valueOf(IsJPApplied));
	}

	/** Get JP Applied.
		@return JP Applied	  */
	public boolean isJPApplied () 
	{
		Object oo = get_Value(COLUMNNAME_IsJPApplied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JPK Applied.
		@param IsJPKApplyed 
		centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public void setIsJPKApplyed (boolean IsJPKApplyed)
	{
		set_Value (COLUMNNAME_IsJPKApplyed, Boolean.valueOf(IsJPKApplyed));
	}

	/** Get JPK Applied.
		@return centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public boolean isJPKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJPKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Move To.
		@param IsMoveTo Move To	  */
	public void setIsMoveTo (boolean IsMoveTo)
	{
		set_Value (COLUMNNAME_IsMoveTo, Boolean.valueOf(IsMoveTo));
	}

	/** Get Move To.
		@return Move To	  */
	public boolean isMoveTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsMoveTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use General Payroll.
		@param IsUseGeneralPayroll Use General Payroll	  */
	public void setIsUseGeneralPayroll (boolean IsUseGeneralPayroll)
	{
		set_Value (COLUMNNAME_IsUseGeneralPayroll, Boolean.valueOf(IsUseGeneralPayroll));
	}

	/** Get Use General Payroll.
		@return Use General Payroll	  */
	public boolean isUseGeneralPayroll () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseGeneralPayroll);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Load Basic Payroll.
		@param LoadBasicPayroll Load Basic Payroll	  */
	public void setLoadBasicPayroll (String LoadBasicPayroll)
	{
		set_Value (COLUMNNAME_LoadBasicPayroll, LoadBasicPayroll);
	}

	/** Get Load Basic Payroll.
		@return Load Basic Payroll	  */
	public String getLoadBasicPayroll () 
	{
		return (String)get_Value(COLUMNNAME_LoadBasicPayroll);
	}

	public org.compiere.model.I_C_BPartner getNewAgent() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getNewAgent_ID(), get_TrxName());	}

	/** Set New Agent.
		@param NewAgent_ID New Agent	  */
	public void setNewAgent_ID (int NewAgent_ID)
	{
		if (NewAgent_ID < 1) 
			set_Value (COLUMNNAME_NewAgent_ID, null);
		else 
			set_Value (COLUMNNAME_NewAgent_ID, Integer.valueOf(NewAgent_ID));
	}

	/** Get New Agent.
		@return New Agent	  */
	public int getNewAgent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewAgent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New A L1.
		@param New_A_L1 New A L1	  */
	public void setNew_A_L1 (BigDecimal New_A_L1)
	{
		set_Value (COLUMNNAME_New_A_L1, New_A_L1);
	}

	/** Get New A L1.
		@return New A L1	  */
	public BigDecimal getNew_A_L1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_A_L1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New A L2.
		@param New_A_L2 New A L2	  */
	public void setNew_A_L2 (BigDecimal New_A_L2)
	{
		set_Value (COLUMNNAME_New_A_L2, New_A_L2);
	}

	/** Get New A L2.
		@return New A L2	  */
	public BigDecimal getNew_A_L2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_A_L2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New A L3.
		@param New_A_L3 New A L3	  */
	public void setNew_A_L3 (BigDecimal New_A_L3)
	{
		set_Value (COLUMNNAME_New_A_L3, New_A_L3);
	}

	/** Get New A L3.
		@return New A L3	  */
	public BigDecimal getNew_A_L3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_A_L3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New A Lain2.
		@param New_A_Lain2 New A Lain2	  */
	public void setNew_A_Lain2 (BigDecimal New_A_Lain2)
	{
		set_Value (COLUMNNAME_New_A_Lain2, New_A_Lain2);
	}

	/** Get New A Lain2.
		@return New A Lain2	  */
	public BigDecimal getNew_A_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_A_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New A Premi.
		@param New_A_Premi New A Premi	  */
	public void setNew_A_Premi (BigDecimal New_A_Premi)
	{
		set_Value (COLUMNNAME_New_A_Premi, New_A_Premi);
	}

	/** Get New A Premi.
		@return New A Premi	  */
	public BigDecimal getNew_A_Premi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_A_Premi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New Contract Ref.
		@param NewContractRef New Contract Ref	  */
	public void setNewContractRef (String NewContractRef)
	{
		set_Value (COLUMNNAME_NewContractRef, NewContractRef);
	}

	/** Get New Contract Ref.
		@return New Contract Ref	  */
	public String getNewContractRef () 
	{
		return (String)get_Value(COLUMNNAME_NewContractRef);
	}

	/** Set New Department.
		@param NewDept_ID New Department	  */
	public void setNewDept_ID (int NewDept_ID)
	{
		if (NewDept_ID < 1) 
			set_Value (COLUMNNAME_NewDept_ID, null);
		else 
			set_Value (COLUMNNAME_NewDept_ID, Integer.valueOf(NewDept_ID));
	}

	/** Get New Department.
		@return New Department	  */
	public int getNewDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Female = F */
	public static final String NEWGENDER_Female = "F";
	/** Male = M */
	public static final String NEWGENDER_Male = "M";
	/** Set New Gender.
		@param NewGender New Gender	  */
	public void setNewGender (String NewGender)
	{

		set_Value (COLUMNNAME_NewGender, NewGender);
	}

	/** Get New Gender.
		@return New Gender	  */
	public String getNewGender () 
	{
		return (String)get_Value(COLUMNNAME_NewGender);
	}

	/** Set New G Pokok.
		@param New_G_Pokok New G Pokok	  */
	public void setNew_G_Pokok (BigDecimal New_G_Pokok)
	{
		set_Value (COLUMNNAME_New_G_Pokok, New_G_Pokok);
	}

	/** Get New G Pokok.
		@return New G Pokok	  */
	public BigDecimal getNew_G_Pokok () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_G_Pokok);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Job getNewJob() throws RuntimeException
    {
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_Name)
			.getPO(getNewJob_ID(), get_TrxName());	}

	/** Set New Position.
		@param NewJob_ID New Position	  */
	public void setNewJob_ID (int NewJob_ID)
	{
		if (NewJob_ID < 1) 
			set_Value (COLUMNNAME_NewJob_ID, null);
		else 
			set_Value (COLUMNNAME_NewJob_ID, Integer.valueOf(NewJob_ID));
	}

	/** Get New Position.
		@return New Position	  */
	public int getNewJob_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewJob_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New Lembur Jam Berikutnya.
		@param NewLeburJamBerikutnya New Lembur Jam Berikutnya	  */
	public void setNewLeburJamBerikutnya (BigDecimal NewLeburJamBerikutnya)
	{
		set_Value (COLUMNNAME_NewLeburJamBerikutnya, NewLeburJamBerikutnya);
	}

	/** Get New Lembur Jam Berikutnya.
		@return New Lembur Jam Berikutnya	  */
	public BigDecimal getNewLeburJamBerikutnya () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewLeburJamBerikutnya);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New Lembur Jam Pertama.
		@param NewLeburJamPertama New Lembur Jam Pertama	  */
	public void setNewLeburJamPertama (BigDecimal NewLeburJamPertama)
	{
		set_Value (COLUMNNAME_NewLeburJamPertama, NewLeburJamPertama);
	}

	/** Get New Lembur Jam Pertama.
		@return New Lembur Jam Pertama	  */
	public BigDecimal getNewLeburJamPertama () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewLeburJamPertama);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New NIK.
		@param NewNIK New NIK	  */
	public void setNewNIK (String NewNIK)
	{
		set_Value (COLUMNNAME_NewNIK, NewNIK);
	}

	/** Get New NIK.
		@return New NIK	  */
	public String getNewNIK () 
	{
		return (String)get_Value(COLUMNNAME_NewNIK);
	}

	/** Level 1 = 1 */
	public static final String NEWPAYROLLLEVEL_Level1 = "1";
	/** Level 2 = 2 */
	public static final String NEWPAYROLLLEVEL_Level2 = "2";
	/** Level 3 = 3 */
	public static final String NEWPAYROLLLEVEL_Level3 = "3";
	/** Level 4 = 4 */
	public static final String NEWPAYROLLLEVEL_Level4 = "4";
	/** Level 5 = 5 */
	public static final String NEWPAYROLLLEVEL_Level5 = "5";
	/** Level 6 = 6 */
	public static final String NEWPAYROLLLEVEL_Level6 = "6";
	/** Not Defined = 0 */
	public static final String NEWPAYROLLLEVEL_NotDefined = "0";
	/** Level 7 = 7 */
	public static final String NEWPAYROLLLEVEL_Level7 = "7";
	/** Level 8 = 8 */
	public static final String NEWPAYROLLLEVEL_Level8 = "8";
	/** Level 9 = 9 */
	public static final String NEWPAYROLLLEVEL_Level9 = "9";
	/** Set New Payroll Level.
		@param NewPayrollLevel New Payroll Level	  */
	public void setNewPayrollLevel (String NewPayrollLevel)
	{

		set_Value (COLUMNNAME_NewPayrollLevel, NewPayrollLevel);
	}

	/** Get New Payroll Level.
		@return New Payroll Level	  */
	public String getNewPayrollLevel () 
	{
		return (String)get_Value(COLUMNNAME_NewPayrollLevel);
	}

	/** Set New P Label.
		@param New_P_Label New P Label	  */
	public void setNew_P_Label (BigDecimal New_P_Label)
	{
		set_Value (COLUMNNAME_New_P_Label, New_P_Label);
	}

	/** Get New P Label.
		@return New P Label	  */
	public BigDecimal getNew_P_Label () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_P_Label);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New P Lain2.
		@param New_P_Lain2 New P Lain2	  */
	public void setNew_P_Lain2 (BigDecimal New_P_Lain2)
	{
		set_Value (COLUMNNAME_New_P_Lain2, New_P_Lain2);
	}

	/** Get New P Lain2.
		@return New P Lain2	  */
	public BigDecimal getNew_P_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_P_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New P Mangkir.
		@param New_P_Mangkir New P Mangkir	  */
	public void setNew_P_Mangkir (BigDecimal New_P_Mangkir)
	{
		set_Value (COLUMNNAME_New_P_Mangkir, New_P_Mangkir);
	}

	/** Get New P Mangkir.
		@return New P Mangkir	  */
	public BigDecimal getNew_P_Mangkir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_P_Mangkir);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New P SPTP.
		@param New_P_SPTP New P SPTP	  */
	public void setNew_P_SPTP (BigDecimal New_P_SPTP)
	{
		set_Value (COLUMNNAME_New_P_SPTP, New_P_SPTP);
	}

	/** Get New P SPTP.
		@return New P SPTP	  */
	public BigDecimal getNew_P_SPTP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_P_SPTP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getNewSectionOfDept() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getNewSectionOfDept_ID(), get_TrxName());	}

	/** Set New Section Of Dept ID.
		@param NewSectionOfDept_ID New Section Of Dept ID	  */
	public void setNewSectionOfDept_ID (int NewSectionOfDept_ID)
	{
		if (NewSectionOfDept_ID < 1) 
			set_Value (COLUMNNAME_NewSectionOfDept_ID, null);
		else 
			set_Value (COLUMNNAME_NewSectionOfDept_ID, Integer.valueOf(NewSectionOfDept_ID));
	}

	/** Get New Section Of Dept ID.
		@return New Section Of Dept ID	  */
	public int getNewSectionOfDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewSectionOfDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Non Shift = NS */
	public static final String NEWSHIFT_NonShift = "NS";
	/** Shift = SH */
	public static final String NEWSHIFT_Shift = "SH";
	/** Set New Shift.
		@param NewShift New Shift	  */
	public void setNewShift (String NewShift)
	{

		set_Value (COLUMNNAME_NewShift, NewShift);
	}

	/** Get New Shift.
		@return New Shift	  */
	public String getNewShift () 
	{
		return (String)get_Value(COLUMNNAME_NewShift);
	}

	/** Set New Slot Type.
		@param NewSlotType_ID New Slot Type	  */
	public void setNewSlotType_ID (int NewSlotType_ID)
	{
		if (NewSlotType_ID < 1) 
			set_Value (COLUMNNAME_NewSlotType_ID, null);
		else 
			set_Value (COLUMNNAME_NewSlotType_ID, Integer.valueOf(NewSlotType_ID));
	}

	/** Get New Slot Type.
		@return New Slot Type	  */
	public int getNewSlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewSlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New T Jabatan.
		@param New_T_Jabatan New T Jabatan	  */
	public void setNew_T_Jabatan (BigDecimal New_T_Jabatan)
	{
		set_Value (COLUMNNAME_New_T_Jabatan, New_T_Jabatan);
	}

	/** Get New T Jabatan.
		@return New T Jabatan	  */
	public BigDecimal getNew_T_Jabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_T_Jabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New T Kesejahteraan.
		@param New_T_Kesejahteraan New T Kesejahteraan	  */
	public void setNew_T_Kesejahteraan (BigDecimal New_T_Kesejahteraan)
	{
		set_Value (COLUMNNAME_New_T_Kesejahteraan, New_T_Kesejahteraan);
	}

	/** Get New T Kesejahteraan.
		@return New T Kesejahteraan	  */
	public BigDecimal getNew_T_Kesejahteraan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_T_Kesejahteraan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New T Lembur.
		@param New_T_Lembur New T Lembur	  */
	public void setNew_T_Lembur (BigDecimal New_T_Lembur)
	{
		set_Value (COLUMNNAME_New_T_Lembur, New_T_Lembur);
	}

	/** Get New T Lembur.
		@return New T Lembur	  */
	public BigDecimal getNew_T_Lembur () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_New_T_Lembur);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New Contract Number.
		@param NextContractNumber New Contract Number	  */
	public void setNextContractNumber (int NextContractNumber)
	{
		set_Value (COLUMNNAME_NextContractNumber, Integer.valueOf(NextContractNumber));
	}

	/** Get New Contract Number.
		@return New Contract Number	  */
	public int getNextContractNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NextContractNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Contract 1 = CN1 */
	public static final String NEXTCONTRACTTYPE_Contract1 = "CN1";
	/** Contract 2 = CN2 */
	public static final String NEXTCONTRACTTYPE_Contract2 = "CN2";
	/** Permanen = PRM */
	public static final String NEXTCONTRACTTYPE_Permanen = "PRM";
	/** Recontract 1 = RC1 */
	public static final String NEXTCONTRACTTYPE_Recontract1 = "RC1";
	/** Recontract 2 = RC2 */
	public static final String NEXTCONTRACTTYPE_Recontract2 = "RC2";
	/** Squence Contract = SCT */
	public static final String NEXTCONTRACTTYPE_SquenceContract = "SCT";
	/** Borongan = BRG */
	public static final String NEXTCONTRACTTYPE_Borongan = "BRG";
	/** Borongan CV = BCV */
	public static final String NEXTCONTRACTTYPE_BoronganCV = "BCV";
	/** Borongan Harian = BRH */
	public static final String NEXTCONTRACTTYPE_BoronganHarian = "BRH";
	/** Borongan Harian CV = BHC */
	public static final String NEXTCONTRACTTYPE_BoronganHarianCV = "BHC";
	/** Set New Contract.
		@param NextContractType New Contract	  */
	public void setNextContractType (String NextContractType)
	{

		set_Value (COLUMNNAME_NextContractType, NextContractType);
	}

	/** Get New Contract.
		@return New Contract	  */
	public String getNextContractType () 
	{
		return (String)get_Value(COLUMNNAME_NextContractType);
	}

	/** Monthly = 01 */
	public static final String NEXTPAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String NEXTPAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String NEXTPAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String NEXTPAYROLLTERM_HarianBulanan = "04";
	/** Set New Payroll Term.
		@param NextPayrollTerm New Payroll Term	  */
	public void setNextPayrollTerm (String NextPayrollTerm)
	{

		set_Value (COLUMNNAME_NextPayrollTerm, NextPayrollTerm);
	}

	/** Get New Payroll Term.
		@return New Payroll Term	  */
	public String getNextPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_NextPayrollTerm);
	}

	/** Set Notes.
		@param Notes Notes	  */
	public void setNotes (String Notes)
	{
		set_Value (COLUMNNAME_Notes, Notes);
	}

	/** Get Notes.
		@return Notes	  */
	public String getNotes () 
	{
		return (String)get_Value(COLUMNNAME_Notes);
	}

	/** Set PPH21PaidByCompany.
		@param PPH21PaidByCompany PPH21PaidByCompany	  */
	public void setPPH21PaidByCompany (boolean PPH21PaidByCompany)
	{
		set_Value (COLUMNNAME_PPH21PaidByCompany, Boolean.valueOf(PPH21PaidByCompany));
	}

	/** Get PPH21PaidByCompany.
		@return PPH21PaidByCompany	  */
	public boolean isPPH21PaidByCompany () 
	{
		Object oo = get_Value(COLUMNNAME_PPH21PaidByCompany);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_C_BPartner getPrevAgent() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getPrevAgent_ID(), get_TrxName());	}

	/** Set Prev Agent.
		@param PrevAgent_ID Prev Agent	  */
	public void setPrevAgent_ID (int PrevAgent_ID)
	{
		if (PrevAgent_ID < 1) 
			set_Value (COLUMNNAME_PrevAgent_ID, null);
		else 
			set_Value (COLUMNNAME_PrevAgent_ID, Integer.valueOf(PrevAgent_ID));
	}

	/** Get Prev Agent.
		@return Prev Agent	  */
	public int getPrevAgent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevAgent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev A L1.
		@param Prev_A_L1 Prev A L1	  */
	public void setPrev_A_L1 (BigDecimal Prev_A_L1)
	{
		set_Value (COLUMNNAME_Prev_A_L1, Prev_A_L1);
	}

	/** Get Prev A L1.
		@return Prev A L1	  */
	public BigDecimal getPrev_A_L1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_A_L1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev A L2.
		@param Prev_A_L2 Prev A L2	  */
	public void setPrev_A_L2 (BigDecimal Prev_A_L2)
	{
		set_Value (COLUMNNAME_Prev_A_L2, Prev_A_L2);
	}

	/** Get Prev A L2.
		@return Prev A L2	  */
	public BigDecimal getPrev_A_L2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_A_L2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev A L3.
		@param Prev_A_L3 Prev A L3	  */
	public void setPrev_A_L3 (BigDecimal Prev_A_L3)
	{
		set_Value (COLUMNNAME_Prev_A_L3, Prev_A_L3);
	}

	/** Get Prev A L3.
		@return Prev A L3	  */
	public BigDecimal getPrev_A_L3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_A_L3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev A Lain2.
		@param Prev_A_Lain2 Prev A Lain2	  */
	public void setPrev_A_Lain2 (BigDecimal Prev_A_Lain2)
	{
		set_Value (COLUMNNAME_Prev_A_Lain2, Prev_A_Lain2);
	}

	/** Get Prev A Lain2.
		@return Prev A Lain2	  */
	public BigDecimal getPrev_A_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_A_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev A Premi.
		@param Prev_A_Premi Prev A Premi	  */
	public void setPrev_A_Premi (BigDecimal Prev_A_Premi)
	{
		set_Value (COLUMNNAME_Prev_A_Premi, Prev_A_Premi);
	}

	/** Get Prev A Premi.
		@return Prev A Premi	  */
	public BigDecimal getPrev_A_Premi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_A_Premi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Contract Number.
		@param PrevContractNumber Prev Contract Number	  */
	public void setPrevContractNumber (int PrevContractNumber)
	{
		set_Value (COLUMNNAME_PrevContractNumber, Integer.valueOf(PrevContractNumber));
	}

	/** Get Prev Contract Number.
		@return Prev Contract Number	  */
	public int getPrevContractNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevContractNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev Contract Ref.
		@param PrevContractRef Prev Contract Ref	  */
	public void setPrevContractRef (String PrevContractRef)
	{
		set_Value (COLUMNNAME_PrevContractRef, PrevContractRef);
	}

	/** Get Prev Contract Ref.
		@return Prev Contract Ref	  */
	public String getPrevContractRef () 
	{
		return (String)get_Value(COLUMNNAME_PrevContractRef);
	}

	/** Contract 1 = CN1 */
	public static final String PREVCONTRACTTYPE_Contract1 = "CN1";
	/** Contract 2 = CN2 */
	public static final String PREVCONTRACTTYPE_Contract2 = "CN2";
	/** Permanen = PRM */
	public static final String PREVCONTRACTTYPE_Permanen = "PRM";
	/** Recontract 1 = RC1 */
	public static final String PREVCONTRACTTYPE_Recontract1 = "RC1";
	/** Recontract 2 = RC2 */
	public static final String PREVCONTRACTTYPE_Recontract2 = "RC2";
	/** Squence Contract = SCT */
	public static final String PREVCONTRACTTYPE_SquenceContract = "SCT";
	/** Borongan = BRG */
	public static final String PREVCONTRACTTYPE_Borongan = "BRG";
	/** Borongan CV = BCV */
	public static final String PREVCONTRACTTYPE_BoronganCV = "BCV";
	/** Borongan Harian = BRH */
	public static final String PREVCONTRACTTYPE_BoronganHarian = "BRH";
	/** Borongan Harian CV = BHC */
	public static final String PREVCONTRACTTYPE_BoronganHarianCV = "BHC";
	/** Set Prev Contract.
		@param PrevContractType Prev Contract	  */
	public void setPrevContractType (String PrevContractType)
	{

		set_Value (COLUMNNAME_PrevContractType, PrevContractType);
	}

	/** Get Prev Contract.
		@return Prev Contract	  */
	public String getPrevContractType () 
	{
		return (String)get_Value(COLUMNNAME_PrevContractType);
	}

	/** Set Prev Department.
		@param PrevDept_ID Prev Department	  */
	public void setPrevDept_ID (int PrevDept_ID)
	{
		if (PrevDept_ID < 1) 
			set_Value (COLUMNNAME_PrevDept_ID, null);
		else 
			set_Value (COLUMNNAME_PrevDept_ID, Integer.valueOf(PrevDept_ID));
	}

	/** Get Prev Department.
		@return Prev Department	  */
	public int getPrevDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Female = F */
	public static final String PREVGENDER_Female = "F";
	/** Male = M */
	public static final String PREVGENDER_Male = "M";
	/** Set Prev Gender.
		@param PrevGender Prev Gender	  */
	public void setPrevGender (String PrevGender)
	{

		set_Value (COLUMNNAME_PrevGender, PrevGender);
	}

	/** Get Prev Gender.
		@return Prev Gender	  */
	public String getPrevGender () 
	{
		return (String)get_Value(COLUMNNAME_PrevGender);
	}

	/** Set Prev G Pokok.
		@param Prev_G_Pokok Prev G Pokok	  */
	public void setPrev_G_Pokok (BigDecimal Prev_G_Pokok)
	{
		set_Value (COLUMNNAME_Prev_G_Pokok, Prev_G_Pokok);
	}

	/** Get Prev G Pokok.
		@return Prev G Pokok	  */
	public BigDecimal getPrev_G_Pokok () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_G_Pokok);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Job getPrevJob() throws RuntimeException
    {
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_Name)
			.getPO(getPrevJob_ID(), get_TrxName());	}

	/** Set Prev Position.
		@param PrevJob_ID Prev Position	  */
	public void setPrevJob_ID (int PrevJob_ID)
	{
		if (PrevJob_ID < 1) 
			set_Value (COLUMNNAME_PrevJob_ID, null);
		else 
			set_Value (COLUMNNAME_PrevJob_ID, Integer.valueOf(PrevJob_ID));
	}

	/** Get Prev Position.
		@return Prev Position	  */
	public int getPrevJob_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevJob_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev Lembur Jam Berikutnya.
		@param PrevLeburJamBerikutnya Prev Lembur Jam Berikutnya	  */
	public void setPrevLeburJamBerikutnya (BigDecimal PrevLeburJamBerikutnya)
	{
		set_Value (COLUMNNAME_PrevLeburJamBerikutnya, PrevLeburJamBerikutnya);
	}

	/** Get Prev Lembur Jam Berikutnya.
		@return Prev Lembur Jam Berikutnya	  */
	public BigDecimal getPrevLeburJamBerikutnya () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevLeburJamBerikutnya);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Lembur Jam Pertama.
		@param PrevLeburJamPertama Prev Lembur Jam Pertama	  */
	public void setPrevLeburJamPertama (BigDecimal PrevLeburJamPertama)
	{
		set_Value (COLUMNNAME_PrevLeburJamPertama, PrevLeburJamPertama);
	}

	/** Get Prev Lembur Jam Pertama.
		@return Prev Lembur Jam Pertama	  */
	public BigDecimal getPrevLeburJamPertama () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevLeburJamPertama);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev NIK.
		@param PrevNIK Prev NIK	  */
	public void setPrevNIK (String PrevNIK)
	{
		set_Value (COLUMNNAME_PrevNIK, PrevNIK);
	}

	/** Get Prev NIK.
		@return Prev NIK	  */
	public String getPrevNIK () 
	{
		return (String)get_Value(COLUMNNAME_PrevNIK);
	}

	/** Level 1 = 1 */
	public static final String PREVPAYROLLLEVEL_Level1 = "1";
	/** Level 2 = 2 */
	public static final String PREVPAYROLLLEVEL_Level2 = "2";
	/** Level 3 = 3 */
	public static final String PREVPAYROLLLEVEL_Level3 = "3";
	/** Level 4 = 4 */
	public static final String PREVPAYROLLLEVEL_Level4 = "4";
	/** Level 5 = 5 */
	public static final String PREVPAYROLLLEVEL_Level5 = "5";
	/** Level 6 = 6 */
	public static final String PREVPAYROLLLEVEL_Level6 = "6";
	/** Not Defined = 0 */
	public static final String PREVPAYROLLLEVEL_NotDefined = "0";
	/** Level 7 = 7 */
	public static final String PREVPAYROLLLEVEL_Level7 = "7";
	/** Level 8 = 8 */
	public static final String PREVPAYROLLLEVEL_Level8 = "8";
	/** Level 9 = 9 */
	public static final String PREVPAYROLLLEVEL_Level9 = "9";
	/** Set Prev Payroll Level.
		@param PrevPayrollLevel Prev Payroll Level	  */
	public void setPrevPayrollLevel (String PrevPayrollLevel)
	{

		set_Value (COLUMNNAME_PrevPayrollLevel, PrevPayrollLevel);
	}

	/** Get Prev Payroll Level.
		@return Prev Payroll Level	  */
	public String getPrevPayrollLevel () 
	{
		return (String)get_Value(COLUMNNAME_PrevPayrollLevel);
	}

	/** Monthly = 01 */
	public static final String PREVPAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PREVPAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PREVPAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PREVPAYROLLTERM_HarianBulanan = "04";
	/** Set Prev Payroll Term.
		@param PrevPayrollTerm Prev Payroll Term	  */
	public void setPrevPayrollTerm (String PrevPayrollTerm)
	{

		set_Value (COLUMNNAME_PrevPayrollTerm, PrevPayrollTerm);
	}

	/** Get Prev Payroll Term.
		@return Prev Payroll Term	  */
	public String getPrevPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PrevPayrollTerm);
	}

	/** Set Prev P Label.
		@param Prev_P_Label Prev P Label	  */
	public void setPrev_P_Label (BigDecimal Prev_P_Label)
	{
		set_Value (COLUMNNAME_Prev_P_Label, Prev_P_Label);
	}

	/** Get Prev P Label.
		@return Prev P Label	  */
	public BigDecimal getPrev_P_Label () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_P_Label);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev P Lain2.
		@param Prev_P_Lain2 Prev P Lain2	  */
	public void setPrev_P_Lain2 (BigDecimal Prev_P_Lain2)
	{
		set_Value (COLUMNNAME_Prev_P_Lain2, Prev_P_Lain2);
	}

	/** Get Prev P Lain2.
		@return Prev P Lain2	  */
	public BigDecimal getPrev_P_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_P_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev P Mangkir.
		@param Prev_P_Mangkir Prev P Mangkir	  */
	public void setPrev_P_Mangkir (BigDecimal Prev_P_Mangkir)
	{
		set_Value (COLUMNNAME_Prev_P_Mangkir, Prev_P_Mangkir);
	}

	/** Get Prev P Mangkir.
		@return Prev P Mangkir	  */
	public BigDecimal getPrev_P_Mangkir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_P_Mangkir);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev P SPTP.
		@param Prev_P_SPTP Prev P SPTP	  */
	public void setPrev_P_SPTP (BigDecimal Prev_P_SPTP)
	{
		set_Value (COLUMNNAME_Prev_P_SPTP, Prev_P_SPTP);
	}

	/** Get Prev P SPTP.
		@return Prev P SPTP	  */
	public BigDecimal getPrev_P_SPTP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_P_SPTP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getPrevSectionOfDept() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getPrevSectionOfDept_ID(), get_TrxName());	}

	/** Set Prev Section Of Dept_ID.
		@param PrevSectionOfDept_ID Prev Section Of Dept_ID	  */
	public void setPrevSectionOfDept_ID (int PrevSectionOfDept_ID)
	{
		if (PrevSectionOfDept_ID < 1) 
			set_Value (COLUMNNAME_PrevSectionOfDept_ID, null);
		else 
			set_Value (COLUMNNAME_PrevSectionOfDept_ID, Integer.valueOf(PrevSectionOfDept_ID));
	}

	/** Get Prev Section Of Dept_ID.
		@return Prev Section Of Dept_ID	  */
	public int getPrevSectionOfDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevSectionOfDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Non Shift = NS */
	public static final String PREVSHIFT_NonShift = "NS";
	/** Shift = SH */
	public static final String PREVSHIFT_Shift = "SH";
	/** Set Prev Shift.
		@param PrevShift Prev Shift	  */
	public void setPrevShift (String PrevShift)
	{

		set_Value (COLUMNNAME_PrevShift, PrevShift);
	}

	/** Get Prev Shift.
		@return Prev Shift	  */
	public String getPrevShift () 
	{
		return (String)get_Value(COLUMNNAME_PrevShift);
	}

	/** Set Prev Slot Type.
		@param PrevSlotType_ID Prev Slot Type	  */
	public void setPrevSlotType_ID (int PrevSlotType_ID)
	{
		if (PrevSlotType_ID < 1) 
			set_Value (COLUMNNAME_PrevSlotType_ID, null);
		else 
			set_Value (COLUMNNAME_PrevSlotType_ID, Integer.valueOf(PrevSlotType_ID));
	}

	/** Get Prev Slot Type.
		@return Prev Slot Type	  */
	public int getPrevSlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevSlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev T Jabatan.
		@param Prev_T_Jabatan Prev T Jabatan	  */
	public void setPrev_T_Jabatan (BigDecimal Prev_T_Jabatan)
	{
		set_Value (COLUMNNAME_Prev_T_Jabatan, Prev_T_Jabatan);
	}

	/** Get Prev T Jabatan.
		@return Prev T Jabatan	  */
	public BigDecimal getPrev_T_Jabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_T_Jabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev T Kesejahteraan.
		@param Prev_T_Kesejahteraan Prev T Kesejahteraan	  */
	public void setPrev_T_Kesejahteraan (BigDecimal Prev_T_Kesejahteraan)
	{
		set_Value (COLUMNNAME_Prev_T_Kesejahteraan, Prev_T_Kesejahteraan);
	}

	/** Get Prev T Kesejahteraan.
		@return Prev T Kesejahteraan	  */
	public BigDecimal getPrev_T_Kesejahteraan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_T_Kesejahteraan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev T Lembur.
		@param Prev_T_Lembur Prev T Lembur	  */
	public void setPrev_T_Lembur (BigDecimal Prev_T_Lembur)
	{
		set_Value (COLUMNNAME_Prev_T_Lembur, Prev_T_Lembur);
	}

	/** Get Prev T Lembur.
		@return Prev T Lembur	  */
	public BigDecimal getPrev_T_Lembur () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_T_Lembur);
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

	/** Set Total Other Allowances.
		@param TotalOtherAllowances 
		Total other allowances listed for employee's payroll
	  */
	public void setTotalOtherAllowances (BigDecimal TotalOtherAllowances)
	{
		set_Value (COLUMNNAME_TotalOtherAllowances, TotalOtherAllowances);
	}

	/** Get Total Other Allowances.
		@return Total other allowances listed for employee's payroll
	  */
	public BigDecimal getTotalOtherAllowances () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOtherAllowances);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Other Deductions.
		@param TotalOtherDeductions 
		Total other deductions listed for employee's payroll
	  */
	public void setTotalOtherDeductions (BigDecimal TotalOtherDeductions)
	{
		set_Value (COLUMNNAME_TotalOtherDeductions, TotalOtherDeductions);
	}

	/** Get Total Other Deductions.
		@return Total other deductions listed for employee's payroll
	  */
	public BigDecimal getTotalOtherDeductions () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOtherDeductions);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Contract_Evaluation getUNS_Contract_Evaluation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Contract_Evaluation)MTable.get(getCtx(), com.uns.model.I_UNS_Contract_Evaluation.Table_Name)
			.getPO(getUNS_Contract_Evaluation_ID(), get_TrxName());	}

	/** Set Contract Evaluation.
		@param UNS_Contract_Evaluation_ID Contract Evaluation	  */
	public void setUNS_Contract_Evaluation_ID (int UNS_Contract_Evaluation_ID)
	{
		if (UNS_Contract_Evaluation_ID < 1) 
			set_Value (COLUMNNAME_UNS_Contract_Evaluation_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Contract_Evaluation_ID, Integer.valueOf(UNS_Contract_Evaluation_ID));
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

	/** Set Contract.
		@param UNS_Contract_Recommendation_ID Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID)
	{
		if (UNS_Contract_Recommendation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Contract_Recommendation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Contract_Recommendation_ID, Integer.valueOf(UNS_Contract_Recommendation_ID));
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

	/** Set UNS_Contract_Recommendation_UU.
		@param UNS_Contract_Recommendation_UU UNS_Contract_Recommendation_UU	  */
	public void setUNS_Contract_Recommendation_UU (String UNS_Contract_Recommendation_UU)
	{
		set_Value (COLUMNNAME_UNS_Contract_Recommendation_UU, UNS_Contract_Recommendation_UU);
	}

	/** Get UNS_Contract_Recommendation_UU.
		@return UNS_Contract_Recommendation_UU	  */
	public String getUNS_Contract_Recommendation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Contract_Recommendation_UU);
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
}