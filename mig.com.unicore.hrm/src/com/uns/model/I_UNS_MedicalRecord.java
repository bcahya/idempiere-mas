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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_MedicalRecord
 *  @author iDempiere (generated) 
 *  @version Release 2.0
 */
@SuppressWarnings("all")
public interface I_UNS_MedicalRecord 
{

    /** TableName=UNS_MedicalRecord */
    public static final String Table_Name = "UNS_MedicalRecord";

    /** AD_Table_ID=1000113 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AllowancePayment */
    public static final String COLUMNNAME_AllowancePayment = "AllowancePayment";

	/** Set Allowance Payment.
	  * The amount of payment using employee's medical allowance
	  */
	public void setAllowancePayment (BigDecimal AllowancePayment);

	/** Get Allowance Payment.
	  * The amount of payment using employee's medical allowance
	  */
	public BigDecimal getAllowancePayment();

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

    /** Column name BirthPlace */
    public static final String COLUMNNAME_BirthPlace = "BirthPlace";

	/** Set Place of Birth	  */
	public void setBirthPlace (String BirthPlace);

	/** Get Place of Birth	  */
	public String getBirthPlace();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Cash / Bank Account.
	  * Account at the Bank or Cash account
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

    /** Column name CashPayment */
    public static final String COLUMNNAME_CashPayment = "CashPayment";

	/** Set Cash Payment.
	  * The amount of payment using cash
	  */
	public void setCashPayment (BigDecimal CashPayment);

	/** Get Cash Payment.
	  * The amount of payment using cash
	  */
	public BigDecimal getCashPayment();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name CreateLeavePermission */
    public static final String COLUMNNAME_CreateLeavePermission = "CreateLeavePermission";

	/** Set Create Leave Permission	  */
	public void setCreateLeavePermission (String CreateLeavePermission);

	/** Get Create Leave Permission	  */
	public String getCreateLeavePermission();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name Doctor_ID */
    public static final String COLUMNNAME_Doctor_ID = "Doctor_ID";

	/** Set Doctor	  */
	public void setDoctor_ID (int Doctor_ID);

	/** Get Doctor	  */
	public int getDoctor_ID();

	public com.uns.model.I_UNS_Employee getDoctor() throws RuntimeException;

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EmployeeDepID */
    public static final String COLUMNNAME_EmployeeDepID = "EmployeeDepID";

	/** Set Employee's Dept..
	  * The department of employee.
	  */
	public void setEmployeeDepID (int EmployeeDepID);

	/** Get Employee's Dept..
	  * The department of employee.
	  */
	public int getEmployeeDepID();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name hospital_referal */
    public static final String COLUMNNAME_hospital_referal = "hospital_referal";

	/** Set Hospital Referral	  */
	public void sethospital_referal (boolean hospital_referal);

	/** Get Hospital Referral	  */
	public boolean ishospital_referal();

    /** Column name HospitalNotRelation */
    public static final String COLUMNNAME_HospitalNotRelation = "HospitalNotRelation";

	/** Set Hospital Not Relation.
	  * The hospital refered by employee medical record
	  */
	public void setHospitalNotRelation (String HospitalNotRelation);

	/** Get Hospital Not Relation.
	  * The hospital refered by employee medical record
	  */
	public String getHospitalNotRelation();

    /** Column name HospitalReferalNo */
    public static final String COLUMNNAME_HospitalReferalNo = "HospitalReferalNo";

	/** Set Hospital Referral	  */
	public void setHospitalReferalNo (String HospitalReferalNo);

	/** Get Hospital Referral	  */
	public String getHospitalReferalNo();

    /** Column name HospitalRelation_ID */
    public static final String COLUMNNAME_HospitalRelation_ID = "HospitalRelation_ID";

	/** Set Hospital Relation.
	  * The hospital as business partner contracted by company to served employee's medical treatment needs
	  */
	public void setHospitalRelation_ID (int HospitalRelation_ID);

	/** Get Hospital Relation.
	  * The hospital as business partner contracted by company to served employee's medical treatment needs
	  */
	public int getHospitalRelation_ID();

	public org.compiere.model.I_C_BPartner getHospitalRelation() throws RuntimeException;

    /** Column name insured_name */
    public static final String COLUMNNAME_insured_name = "insured_name";

	/** Set Insured Name	  */
	public void setinsured_name (String insured_name);

	/** Get Insured Name	  */
	public String getinsured_name();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name isHRDRequitment */
    public static final String COLUMNNAME_isHRDRequitment = "isHRDRequitment";

	/** Set HRD Recruiment (New Employee)	  */
	public void setisHRDRequitment (boolean isHRDRequitment);

	/** Get HRD Recruiment (New Employee)	  */
	public boolean isHRDRequitment();

    /** Column name leave_type_recommendation */
    public static final String COLUMNNAME_leave_type_recommendation = "leave_type_recommendation";

	/** Set Leave Type Recommendation	  */
	public void setleave_type_recommendation (String leave_type_recommendation);

	/** Get Leave Type Recommendation	  */
	public String getleave_type_recommendation();

    /** Column name medical_date */
    public static final String COLUMNNAME_medical_date = "medical_date";

	/** Set Medical Date	  */
	public void setmedical_date (Timestamp medical_date);

	/** Get Medical Date	  */
	public Timestamp getmedical_date();

    /** Column name MedicalAllowance */
    public static final String COLUMNNAME_MedicalAllowance = "MedicalAllowance";

	/** Set Employee's Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance);

	/** Get Employee's Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance();

    /** Column name MedicalCosts */
    public static final String COLUMNNAME_MedicalCosts = "MedicalCosts";

	/** Set Medical Costs.
	  * The cost of medical being charged to patient
	  */
	public void setMedicalCosts (BigDecimal MedicalCosts);

	/** Get Medical Costs.
	  * The cost of medical being charged to patient
	  */
	public BigDecimal getMedicalCosts();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NumberOfChild */
    public static final String COLUMNNAME_NumberOfChild = "NumberOfChild";

	/** Set Child Number.
	  * The number of child the patient have had (it should be counted as the number of patient's pregnancies)
	  */
	public void setNumberOfChild (BigDecimal NumberOfChild);

	/** Get Child Number.
	  * The number of child the patient have had (it should be counted as the number of patient's pregnancies)
	  */
	public BigDecimal getNumberOfChild();

    /** Column name Nurse_ID */
    public static final String COLUMNNAME_Nurse_ID = "Nurse_ID";

	/** Set Examiner/Nurse.
	  * Examiner/Nurse
	  */
	public void setNurse_ID (int Nurse_ID);

	/** Get Examiner/Nurse.
	  * Examiner/Nurse
	  */
	public int getNurse_ID();

	public com.uns.model.I_UNS_Employee getNurse() throws RuntimeException;

    /** Column name Nurse2_ID */
    public static final String COLUMNNAME_Nurse2_ID = "Nurse2_ID";

	/** Set Examiner.
	  * Examiner
	  */
	public void setNurse2_ID (int Nurse2_ID);

	/** Get Examiner.
	  * Examiner
	  */
	public int getNurse2_ID();

	public com.uns.model.I_UNS_Employee getNurse2() throws RuntimeException;

    /** Column name PayrollPayment */
    public static final String COLUMNNAME_PayrollPayment = "PayrollPayment";

	/** Set Payroll Payment.
	  * If hospital referal it will be employee's loan to payroll, if not it will directly deducted to next month employee payroll.
	  */
	public void setPayrollPayment (BigDecimal PayrollPayment);

	/** Get Payroll Payment.
	  * If hospital referal it will be employee's loan to payroll, if not it will directly deducted to next month employee payroll.
	  */
	public BigDecimal getPayrollPayment();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name PrevHospitalReferral_ID */
    public static final String COLUMNNAME_PrevHospitalReferral_ID = "PrevHospitalReferral_ID";

	/** Set Previous Hospital Referral.
	  * The previous hospital referral to be referred to
	  */
	public void setPrevHospitalReferral_ID (int PrevHospitalReferral_ID);

	/** Get Previous Hospital Referral.
	  * The previous hospital referral to be referred to
	  */
	public int getPrevHospitalReferral_ID();

	public com.uns.model.I_UNS_MedicalRecord getPrevHospitalReferral() throws RuntimeException;

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name RemainingAllowance */
    public static final String COLUMNNAME_RemainingAllowance = "RemainingAllowance";

	/** Set Remaining Allowance Amt.
	  * Remaining yearly employee's medical allowance amt
	  */
	public void setRemainingAllowance (BigDecimal RemainingAllowance);

	/** Get Remaining Allowance Amt.
	  * Remaining yearly employee's medical allowance amt
	  */
	public BigDecimal getRemainingAllowance();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name sl_recommend_enddate */
    public static final String COLUMNNAME_sl_recommend_enddate = "sl_recommend_enddate";

	/** Set SL Recommend End Date	  */
	public void setsl_recommend_enddate (Timestamp sl_recommend_enddate);

	/** Get SL Recommend End Date	  */
	public Timestamp getsl_recommend_enddate();

    /** Column name sl_recommend_startdate */
    public static final String COLUMNNAME_sl_recommend_startdate = "sl_recommend_startdate";

	/** Set SL Recommend Start Date	  */
	public void setsl_recommend_startdate (Timestamp sl_recommend_startdate);

	/** Get SL Recommend Start Date	  */
	public Timestamp getsl_recommend_startdate();

    /** Column name SpecificMarks */
    public static final String COLUMNNAME_SpecificMarks = "SpecificMarks";

	/** Set Specific Marks	  */
	public void setSpecificMarks (String SpecificMarks);

	/** Get Specific Marks	  */
	public String getSpecificMarks();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_MedicalRecord_ID */
    public static final String COLUMNNAME_UNS_MedicalRecord_ID = "UNS_MedicalRecord_ID";

	/** Set Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID);

	/** Get Medical Record	  */
	public int getUNS_MedicalRecord_ID();

    /** Column name UNS_MedicalRecord_UU */
    public static final String COLUMNNAME_UNS_MedicalRecord_UU = "UNS_MedicalRecord_UU";

	/** Set UNS_MedicalRecord_UU	  */
	public void setUNS_MedicalRecord_UU (String UNS_MedicalRecord_UU);

	/** Get UNS_MedicalRecord_UU	  */
	public String getUNS_MedicalRecord_UU();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name who_is_sick */
    public static final String COLUMNNAME_who_is_sick = "who_is_sick";

	/** Set Who Is Sick	  */
	public void setwho_is_sick (String who_is_sick);

	/** Get Who Is Sick	  */
	public String getwho_is_sick();
}
