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

/** Generated Model for UNS_MedicalRecord
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_MedicalRecord extends PO implements I_UNS_MedicalRecord, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141014L;

    /** Standard Constructor */
    public X_UNS_MedicalRecord (Properties ctx, int UNS_MedicalRecord_ID, String trxName)
    {
      super (ctx, UNS_MedicalRecord_ID, trxName);
      /** if (UNS_MedicalRecord_ID == 0)
        {
			setAllowancePayment (Env.ZERO);
// 0
			setC_DocType_ID (0);
// @SQL=Select C_DocType_ID from C_DocType where DocBaseType = 'CLN'
			setC_Period_ID (0);
			setCashPayment (Env.ZERO);
// 0
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setinsured_name (null);
			setIsApproved (false);
// N
			setisHRDRequitment (false);
// N
			setmedical_date (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setMedicalCosts (Env.ZERO);
// 0
			setPayrollPayment (Env.ZERO);
// 0
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setUNS_MedicalRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MedicalRecord (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MedicalRecord[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Allowance Payment.
		@param AllowancePayment 
		The amount of payment using employee's medical allowance
	  */
	public void setAllowancePayment (BigDecimal AllowancePayment)
	{
		set_Value (COLUMNNAME_AllowancePayment, AllowancePayment);
	}

	/** Get Allowance Payment.
		@return The amount of payment using employee's medical allowance
	  */
	public BigDecimal getAllowancePayment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllowancePayment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set Place of Birth.
		@param BirthPlace Place of Birth	  */
	public void setBirthPlace (String BirthPlace)
	{
		set_Value (COLUMNNAME_BirthPlace, BirthPlace);
	}

	/** Get Place of Birth.
		@return Place of Birth	  */
	public String getBirthPlace () 
	{
		return (String)get_Value(COLUMNNAME_BirthPlace);
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
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Payment.
		@param CashPayment 
		The amount of payment using cash
	  */
	public void setCashPayment (BigDecimal CashPayment)
	{
		set_Value (COLUMNNAME_CashPayment, CashPayment);
	}

	/** Get Cash Payment.
		@return The amount of payment using cash
	  */
	public BigDecimal getCashPayment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CashPayment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Create Leave Permission.
		@param CreateLeavePermission Create Leave Permission	  */
	public void setCreateLeavePermission (String CreateLeavePermission)
	{
		set_Value (COLUMNNAME_CreateLeavePermission, CreateLeavePermission);
	}

	/** Get Create Leave Permission.
		@return Create Leave Permission	  */
	public String getCreateLeavePermission () 
	{
		return (String)get_Value(COLUMNNAME_CreateLeavePermission);
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

	public com.uns.model.I_UNS_Employee getDoctor() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getDoctor_ID(), get_TrxName());	}

	/** Set Doctor.
		@param Doctor_ID Doctor	  */
	public void setDoctor_ID (int Doctor_ID)
	{
		if (Doctor_ID < 1) 
			set_Value (COLUMNNAME_Doctor_ID, null);
		else 
			set_Value (COLUMNNAME_Doctor_ID, Integer.valueOf(Doctor_ID));
	}

	/** Get Doctor.
		@return Doctor	  */
	public int getDoctor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Doctor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Employee's Dept..
		@param EmployeeDepID 
		The department of employee.
	  */
	public void setEmployeeDepID (int EmployeeDepID)
	{
		set_Value (COLUMNNAME_EmployeeDepID, Integer.valueOf(EmployeeDepID));
	}

	/** Get Employee's Dept..
		@return The department of employee.
	  */
	public int getEmployeeDepID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EmployeeDepID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Female = F */
	public static final String GENDER_Female = "F";
	/** Male = M */
	public static final String GENDER_Male = "M";
	/** Set Gender.
		@param Gender Gender	  */
	public void setGender (String Gender)
	{

		set_Value (COLUMNNAME_Gender, Gender);
	}

	/** Get Gender.
		@return Gender	  */
	public String getGender () 
	{
		return (String)get_Value(COLUMNNAME_Gender);
	}

	/** Set Hospital Referral.
		@param hospital_referal Hospital Referral	  */
	public void sethospital_referal (boolean hospital_referal)
	{
		set_Value (COLUMNNAME_hospital_referal, Boolean.valueOf(hospital_referal));
	}

	/** Get Hospital Referral.
		@return Hospital Referral	  */
	public boolean ishospital_referal () 
	{
		Object oo = get_Value(COLUMNNAME_hospital_referal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Hospital Not Relation.
		@param HospitalNotRelation 
		The hospital refered by employee medical record
	  */
	public void setHospitalNotRelation (String HospitalNotRelation)
	{
		set_Value (COLUMNNAME_HospitalNotRelation, HospitalNotRelation);
	}

	/** Get Hospital Not Relation.
		@return The hospital refered by employee medical record
	  */
	public String getHospitalNotRelation () 
	{
		return (String)get_Value(COLUMNNAME_HospitalNotRelation);
	}

	/** Set Hospital Referral.
		@param HospitalReferalNo Hospital Referral	  */
	public void setHospitalReferalNo (String HospitalReferalNo)
	{
		set_Value (COLUMNNAME_HospitalReferalNo, HospitalReferalNo);
	}

	/** Get Hospital Referral.
		@return Hospital Referral	  */
	public String getHospitalReferalNo () 
	{
		return (String)get_Value(COLUMNNAME_HospitalReferalNo);
	}

	public org.compiere.model.I_C_BPartner getHospitalRelation() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getHospitalRelation_ID(), get_TrxName());	}

	/** Set Hospital Relation.
		@param HospitalRelation_ID 
		The hospital as business partner contracted by company to served employee's medical treatment needs
	  */
	public void setHospitalRelation_ID (int HospitalRelation_ID)
	{
		if (HospitalRelation_ID < 1) 
			set_Value (COLUMNNAME_HospitalRelation_ID, null);
		else 
			set_Value (COLUMNNAME_HospitalRelation_ID, Integer.valueOf(HospitalRelation_ID));
	}

	/** Get Hospital Relation.
		@return The hospital as business partner contracted by company to served employee's medical treatment needs
	  */
	public int getHospitalRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HospitalRelation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Insured Name.
		@param insured_name Insured Name	  */
	public void setinsured_name (String insured_name)
	{
		set_Value (COLUMNNAME_insured_name, insured_name);
	}

	/** Get Insured Name.
		@return Insured Name	  */
	public String getinsured_name () 
	{
		return (String)get_Value(COLUMNNAME_insured_name);
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

	/** Set HRD Recruiment (New Employee).
		@param isHRDRequitment HRD Recruiment (New Employee)	  */
	public void setisHRDRequitment (boolean isHRDRequitment)
	{
		set_Value (COLUMNNAME_isHRDRequitment, Boolean.valueOf(isHRDRequitment));
	}

	/** Get HRD Recruiment (New Employee).
		@return HRD Recruiment (New Employee)	  */
	public boolean isHRDRequitment () 
	{
		Object oo = get_Value(COLUMNNAME_isHRDRequitment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Rawat Jalan = RJ */
	public static final String LEAVE_TYPE_RECOMMENDATION_RawatJalan = "RJ";
	/** Surat Keterangan Istirahat = SKI */
	public static final String LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahat = "SKI";
	/** Surat Keterangan Istirahat (Kecelakaan Kerja) = SKI_KK */
	public static final String LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahatKecelakaanKerja = "SKI_KK";
	/** Surat Keterangan Istirahat Keguguran = SKIK */
	public static final String LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahatKeguguran = "SKIK";
	/** Set Leave Type Recommendation.
		@param leave_type_recommendation Leave Type Recommendation	  */
	public void setleave_type_recommendation (String leave_type_recommendation)
	{

		set_Value (COLUMNNAME_leave_type_recommendation, leave_type_recommendation);
	}

	/** Get Leave Type Recommendation.
		@return Leave Type Recommendation	  */
	public String getleave_type_recommendation () 
	{
		return (String)get_Value(COLUMNNAME_leave_type_recommendation);
	}

	/** Set Medical Date.
		@param medical_date Medical Date	  */
	public void setmedical_date (Timestamp medical_date)
	{
		set_Value (COLUMNNAME_medical_date, medical_date);
	}

	/** Get Medical Date.
		@return Medical Date	  */
	public Timestamp getmedical_date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_medical_date);
	}

	/** Set Employee's Allowance.
		@param MedicalAllowance 
		The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance)
	{
		set_ValueNoCheck (COLUMNNAME_MedicalAllowance, MedicalAllowance);
	}

	/** Get Employee's Allowance.
		@return The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Medical Costs.
		@param MedicalCosts 
		The cost of medical being charged to patient
	  */
	public void setMedicalCosts (BigDecimal MedicalCosts)
	{
		set_Value (COLUMNNAME_MedicalCosts, MedicalCosts);
	}

	/** Get Medical Costs.
		@return The cost of medical being charged to patient
	  */
	public BigDecimal getMedicalCosts () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalCosts);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Child Number.
		@param NumberOfChild 
		The number of child the patient have had (it should be counted as the number of patient's pregnancies)
	  */
	public void setNumberOfChild (BigDecimal NumberOfChild)
	{
		set_Value (COLUMNNAME_NumberOfChild, NumberOfChild);
	}

	/** Get Child Number.
		@return The number of child the patient have had (it should be counted as the number of patient's pregnancies)
	  */
	public BigDecimal getNumberOfChild () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberOfChild);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Employee getNurse() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getNurse_ID(), get_TrxName());	}

	/** Set Examiner/Nurse.
		@param Nurse_ID 
		Examiner/Nurse
	  */
	public void setNurse_ID (int Nurse_ID)
	{
		if (Nurse_ID < 1) 
			set_Value (COLUMNNAME_Nurse_ID, null);
		else 
			set_Value (COLUMNNAME_Nurse_ID, Integer.valueOf(Nurse_ID));
	}

	/** Get Examiner/Nurse.
		@return Examiner/Nurse
	  */
	public int getNurse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Nurse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getNurse2() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getNurse2_ID(), get_TrxName());	}

	/** Set Examiner.
		@param Nurse2_ID 
		Examiner
	  */
	public void setNurse2_ID (int Nurse2_ID)
	{
		if (Nurse2_ID < 1) 
			set_Value (COLUMNNAME_Nurse2_ID, null);
		else 
			set_Value (COLUMNNAME_Nurse2_ID, Integer.valueOf(Nurse2_ID));
	}

	/** Get Examiner.
		@return Examiner
	  */
	public int getNurse2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Nurse2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Payment.
		@param PayrollPayment 
		If hospital referal it will be employee's loan to payroll, if not it will directly deducted to next month employee payroll.
	  */
	public void setPayrollPayment (BigDecimal PayrollPayment)
	{
		set_Value (COLUMNNAME_PayrollPayment, PayrollPayment);
	}

	/** Get Payroll Payment.
		@return If hospital referal it will be employee's loan to payroll, if not it will directly deducted to next month employee payroll.
	  */
	public BigDecimal getPayrollPayment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayrollPayment);
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

	public com.uns.model.I_UNS_MedicalRecord getPrevHospitalReferral() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MedicalRecord)MTable.get(getCtx(), com.uns.model.I_UNS_MedicalRecord.Table_Name)
			.getPO(getPrevHospitalReferral_ID(), get_TrxName());	}

	/** Set Previous Hospital Referral.
		@param PrevHospitalReferral_ID 
		The previous hospital referral to be referred to
	  */
	public void setPrevHospitalReferral_ID (int PrevHospitalReferral_ID)
	{
		if (PrevHospitalReferral_ID < 1) 
			set_Value (COLUMNNAME_PrevHospitalReferral_ID, null);
		else 
			set_Value (COLUMNNAME_PrevHospitalReferral_ID, Integer.valueOf(PrevHospitalReferral_ID));
	}

	/** Get Previous Hospital Referral.
		@return The previous hospital referral to be referred to
	  */
	public int getPrevHospitalReferral_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevHospitalReferral_ID);
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

	/** Set Remaining Allowance Amt.
		@param RemainingAllowance 
		Remaining yearly employee's medical allowance amt
	  */
	public void setRemainingAllowance (BigDecimal RemainingAllowance)
	{
		set_ValueNoCheck (COLUMNNAME_RemainingAllowance, RemainingAllowance);
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

	/** Set SL Recommend End Date.
		@param sl_recommend_enddate SL Recommend End Date	  */
	public void setsl_recommend_enddate (Timestamp sl_recommend_enddate)
	{
		set_Value (COLUMNNAME_sl_recommend_enddate, sl_recommend_enddate);
	}

	/** Get SL Recommend End Date.
		@return SL Recommend End Date	  */
	public Timestamp getsl_recommend_enddate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_sl_recommend_enddate);
	}

	/** Set SL Recommend Start Date.
		@param sl_recommend_startdate SL Recommend Start Date	  */
	public void setsl_recommend_startdate (Timestamp sl_recommend_startdate)
	{
		set_Value (COLUMNNAME_sl_recommend_startdate, sl_recommend_startdate);
	}

	/** Get SL Recommend Start Date.
		@return SL Recommend Start Date	  */
	public Timestamp getsl_recommend_startdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_sl_recommend_startdate);
	}

	/** Set Specific Marks.
		@param SpecificMarks Specific Marks	  */
	public void setSpecificMarks (String SpecificMarks)
	{
		set_Value (COLUMNNAME_SpecificMarks, SpecificMarks);
	}

	/** Get Specific Marks.
		@return Specific Marks	  */
	public String getSpecificMarks () 
	{
		return (String)get_Value(COLUMNNAME_SpecificMarks);
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

	/** Set Medical Record.
		@param UNS_MedicalRecord_ID Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID)
	{
		if (UNS_MedicalRecord_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalRecord_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalRecord_ID, Integer.valueOf(UNS_MedicalRecord_ID));
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

	/** Set UNS_MedicalRecord_UU.
		@param UNS_MedicalRecord_UU UNS_MedicalRecord_UU	  */
	public void setUNS_MedicalRecord_UU (String UNS_MedicalRecord_UU)
	{
		set_Value (COLUMNNAME_UNS_MedicalRecord_UU, UNS_MedicalRecord_UU);
	}

	/** Get UNS_MedicalRecord_UU.
		@return UNS_MedicalRecord_UU	  */
	public String getUNS_MedicalRecord_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MedicalRecord_UU);
	}

	/** Employee Insured 1 = employeeinsured1 */
	public static final String WHO_IS_SICK_EmployeeInsured1 = "employeeinsured1";
	/** Employee Insured 2 = employeeinsured2 */
	public static final String WHO_IS_SICK_EmployeeInsured2 = "employeeinsured2";
	/** Employee Insured 3 = employeeinsured3 */
	public static final String WHO_IS_SICK_EmployeeInsured3 = "employeeinsured3";
	/** Employee Insured 4 = employeeinsured4 */
	public static final String WHO_IS_SICK_EmployeeInsured4 = "employeeinsured4";
	/** Employee = name */
	public static final String WHO_IS_SICK_Employee = "name";
	/** Set Who Is Sick.
		@param who_is_sick Who Is Sick	  */
	public void setwho_is_sick (String who_is_sick)
	{

		set_Value (COLUMNNAME_who_is_sick, who_is_sick);
	}

	/** Get Who Is Sick.
		@return Who Is Sick	  */
	public String getwho_is_sick () 
	{
		return (String)get_Value(COLUMNNAME_who_is_sick);
	}
}