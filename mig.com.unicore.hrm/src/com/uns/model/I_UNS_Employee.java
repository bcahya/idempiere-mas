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

/** Generated Interface for UNS_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Employee 
{

    /** TableName=UNS_Employee */
    public static final String Table_Name = "UNS_Employee";

    /** AD_Table_ID=1000068 */
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

    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

	/** Set Address	  */
	public void setAddress (String Address);

	/** Get Address	  */
	public String getAddress();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Address 2.
	  * Address line 2 for this location
	  */
	public void setAddress2 (String Address2);

	/** Get Address 2.
	  * Address line 2 for this location
	  */
	public String getAddress2();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AttendanceName */
    public static final String COLUMNNAME_AttendanceName = "AttendanceName";

	/** Set Attendance Name	  */
	public void setAttendanceName (String AttendanceName);

	/** Get Attendance Name	  */
	public String getAttendanceName();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Job_ID */
    public static final String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/** Set Position.
	  * Job Position
	  */
	public void setC_Job_ID (int C_Job_ID);

	/** Get Position.
	  * Job Position
	  */
	public int getC_Job_ID();

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException;

    /** Column name ContractNumber */
    public static final String COLUMNNAME_ContractNumber = "ContractNumber";

	/** Set Contract Number	  */
	public void setContractNumber (int ContractNumber);

	/** Get Contract Number	  */
	public int getContractNumber();

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

    /** Column name DateOfBirth */
    public static final String COLUMNNAME_DateOfBirth = "DateOfBirth";

	/** Set Date Of Birth	  */
	public void setDateOfBirth (Timestamp DateOfBirth);

	/** Get Date Of Birth	  */
	public Timestamp getDateOfBirth();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/** Set Duration.
	  * Normal Duration in Duration Unit
	  */
	public void setDuration (String Duration);

	/** Get Duration.
	  * Normal Duration in Duration Unit
	  */
	public String getDuration();

    /** Column name EmployeeInsured1 */
    public static final String COLUMNNAME_EmployeeInsured1 = "EmployeeInsured1";

	/** Set Employee Insured 1	  */
	public void setEmployeeInsured1 (String EmployeeInsured1);

	/** Get Employee Insured 1	  */
	public String getEmployeeInsured1();

    /** Column name EmployeeInsured2 */
    public static final String COLUMNNAME_EmployeeInsured2 = "EmployeeInsured2";

	/** Set Employee Insured 2	  */
	public void setEmployeeInsured2 (String EmployeeInsured2);

	/** Get Employee Insured 2	  */
	public String getEmployeeInsured2();

    /** Column name EmployeeInsured3 */
    public static final String COLUMNNAME_EmployeeInsured3 = "EmployeeInsured3";

	/** Set Employee Insured 3	  */
	public void setEmployeeInsured3 (String EmployeeInsured3);

	/** Get Employee Insured 3	  */
	public String getEmployeeInsured3();

    /** Column name EmployeeInsured4 */
    public static final String COLUMNNAME_EmployeeInsured4 = "EmployeeInsured4";

	/** Set Employee Insured 4	  */
	public void setEmployeeInsured4 (String EmployeeInsured4);

	/** Get Employee Insured 4	  */
	public String getEmployeeInsured4();

    /** Column name EmploymentType */
    public static final String COLUMNNAME_EmploymentType = "EmploymentType";

	/** Set Employment Type	  */
	public void setEmploymentType (String EmploymentType);

	/** Get Employment Type	  */
	public String getEmploymentType();

    /** Column name EntryDate */
    public static final String COLUMNNAME_EntryDate = "EntryDate";

	/** Set Entry Date	  */
	public void setEntryDate (Timestamp EntryDate);

	/** Get Entry Date	  */
	public Timestamp getEntryDate();

    /** Column name FamilyName */
    public static final String COLUMNNAME_FamilyName = "FamilyName";

	/** Set Family Name	  */
	public void setFamilyName (String FamilyName);

	/** Get Family Name	  */
	public String getFamilyName();

    /** Column name FatherName */
    public static final String COLUMNNAME_FatherName = "FatherName";

	/** Set Father Name	  */
	public void setFatherName (String FatherName);

	/** Get Father Name	  */
	public String getFatherName();

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name HighestEdBackround */
    public static final String COLUMNNAME_HighestEdBackround = "HighestEdBackround";

	/** Set Highest Ed Backround	  */
	public void setHighestEdBackround (String HighestEdBackround);

	/** Get Highest Ed Backround	  */
	public String getHighestEdBackround();

    /** Column name HomePhone */
    public static final String COLUMNNAME_HomePhone = "HomePhone";

	/** Set Home Phone	  */
	public void setHomePhone (String HomePhone);

	/** Get Home Phone	  */
	public String getHomePhone();

    /** Column name HourPerDay */
    public static final String COLUMNNAME_HourPerDay = "HourPerDay";

	/** Set Hour Per Day	  */
	public void setHourPerDay (int HourPerDay);

	/** Get Hour Per Day	  */
	public int getHourPerDay();

    /** Column name HWname */
    public static final String COLUMNNAME_HWname = "HWname";

	/** Set Mate Name	  */
	public void setHWname (String HWname);

	/** Get Mate Name	  */
	public String getHWname();

    /** Column name IDNo */
    public static final String COLUMNNAME_IDNo = "IDNo";

	/** Set ID No	  */
	public void setIDNo (String IDNo);

	/** Get ID No	  */
	public String getIDNo();

    /** Column name IDType */
    public static final String COLUMNNAME_IDType = "IDType";

	/** Set ID Type	  */
	public void setIDType (String IDType);

	/** Get ID Type	  */
	public String getIDType();

    /** Column name Image_ID */
    public static final String COLUMNNAME_Image_ID = "Image_ID";

	/** Set The Image Attached.
	  * The Image Attached
	  */
	public void setImage_ID (int Image_ID);

	/** Get The Image Attached.
	  * The Image Attached
	  */
	public int getImage_ID();

    /** Column name IndicatorTransfer */
    public static final String COLUMNNAME_IndicatorTransfer = "IndicatorTransfer";

	/** Set Last Transfer Date	  */
	public void setIndicatorTransfer (Timestamp IndicatorTransfer);

	/** Get Last Transfer Date	  */
	public Timestamp getIndicatorTransfer();

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

    /** Column name IsApplicationLetter */
    public static final String COLUMNNAME_IsApplicationLetter = "IsApplicationLetter";

	/** Set Is Application Letter	  */
	public void setIsApplicationLetter (boolean IsApplicationLetter);

	/** Get Is Application Letter	  */
	public boolean isApplicationLetter();

    /** Column name IsBlacklist */
    public static final String COLUMNNAME_IsBlacklist = "IsBlacklist";

	/** Set Is Black List	  */
	public void setIsBlacklist (boolean IsBlacklist);

	/** Get Is Black List	  */
	public boolean isBlacklist();

    /** Column name IsCopyCertificateOfCourses */
    public static final String COLUMNNAME_IsCopyCertificateOfCourses = "IsCopyCertificateOfCourses";

	/** Set Is Copy Certificate Of Courses	  */
	public void setIsCopyCertificateOfCourses (boolean IsCopyCertificateOfCourses);

	/** Get Is Copy Certificate Of Courses	  */
	public boolean isCopyCertificateOfCourses();

    /** Column name IsCopyOfIdentity */
    public static final String COLUMNNAME_IsCopyOfIdentity = "IsCopyOfIdentity";

	/** Set Is Copy Of Identity	  */
	public void setIsCopyOfIdentity (boolean IsCopyOfIdentity);

	/** Get Is Copy Of Identity	  */
	public boolean isCopyOfIdentity();

    /** Column name IsCopyOfSKCK */
    public static final String COLUMNNAME_IsCopyOfSKCK = "IsCopyOfSKCK";

	/** Set Is Copy Of SKCK	  */
	public void setIsCopyOfSKCK (boolean IsCopyOfSKCK);

	/** Get Is Copy Of SKCK	  */
	public boolean isCopyOfSKCK();

    /** Column name IsCopyOfWorkExperience */
    public static final String COLUMNNAME_IsCopyOfWorkExperience = "IsCopyOfWorkExperience";

	/** Set Is Copy Of Work Experience	  */
	public void setIsCopyOfWorkExperience (boolean IsCopyOfWorkExperience);

	/** Get Is Copy Of Work Experience	  */
	public boolean isCopyOfWorkExperience();

    /** Column name IsCurriculumVitae */
    public static final String COLUMNNAME_IsCurriculumVitae = "IsCurriculumVitae";

	/** Set Is Curriculum Vitae	  */
	public void setIsCurriculumVitae (boolean IsCurriculumVitae);

	/** Get Is Curriculum Vitae	  */
	public boolean isCurriculumVitae();

    /** Column name IsDiploma */
    public static final String COLUMNNAME_IsDiploma = "IsDiploma";

	/** Set Is Diploma	  */
	public void setIsDiploma (boolean IsDiploma);

	/** Get Is Diploma	  */
	public boolean isDiploma();

    /** Column name IsHealthCheck */
    public static final String COLUMNNAME_IsHealthCheck = "IsHealthCheck";

	/** Set Is Health Check	  */
	public void setIsHealthCheck (boolean IsHealthCheck);

	/** Get Is Health Check	  */
	public boolean isHealthCheck();

    /** Column name IsPasPhoto */
    public static final String COLUMNNAME_IsPasPhoto = "IsPasPhoto";

	/** Set Is Pas Photo	  */
	public void setIsPasPhoto (boolean IsPasPhoto);

	/** Get Is Pas Photo	  */
	public boolean isPasPhoto();

    /** Column name IsStatementLetter */
    public static final String COLUMNNAME_IsStatementLetter = "IsStatementLetter";

	/** Set Is Statement Letter	  */
	public void setIsStatementLetter (boolean IsStatementLetter);

	/** Get Is Statement Letter	  */
	public boolean isStatementLetter();

    /** Column name IsTemporary */
    public static final String COLUMNNAME_IsTemporary = "IsTemporary";

	/** Set Is Temporary	  */
	public void setIsTemporary (boolean IsTemporary);

	/** Get Is Temporary	  */
	public boolean isTemporary();

    /** Column name IsTerminate */
    public static final String COLUMNNAME_IsTerminate = "IsTerminate";

	/** Set Terminate	  */
	public void setIsTerminate (boolean IsTerminate);

	/** Get Terminate	  */
	public boolean isTerminate();

    /** Column name IsTranscript */
    public static final String COLUMNNAME_IsTranscript = "IsTranscript";

	/** Set Is Transcript	  */
	public void setIsTranscript (boolean IsTranscript);

	/** Get Is Transcript	  */
	public boolean isTranscript();

    /** Column name IsUseGeneralPayroll */
    public static final String COLUMNNAME_IsUseGeneralPayroll = "IsUseGeneralPayroll";

	/** Set Use General Payroll	  */
	public void setIsUseGeneralPayroll (boolean IsUseGeneralPayroll);

	/** Get Use General Payroll	  */
	public boolean isUseGeneralPayroll();

    /** Column name JamsostekID */
    public static final String COLUMNNAME_JamsostekID = "JamsostekID";

	/** Set Jamsostek ID	  */
	public void setJamsostekID (String JamsostekID);

	/** Get Jamsostek ID	  */
	public String getJamsostekID();

    /** Column name Jurusan */
    public static final String COLUMNNAME_Jurusan = "Jurusan";

	/** Set Subject	  */
	public void setJurusan (String Jurusan);

	/** Get Subject	  */
	public String getJurusan();

    /** Column name Last_Evaluation_ID */
    public static final String COLUMNNAME_Last_Evaluation_ID = "Last_Evaluation_ID";

	/** Set Last Evaluation.
	  * Last Evaluation
	  */
	public void setLast_Evaluation_ID (int Last_Evaluation_ID);

	/** Get Last Evaluation.
	  * Last Evaluation
	  */
	public int getLast_Evaluation_ID();

	public com.uns.model.I_UNS_Contract_Evaluation getLast_Evaluation() throws RuntimeException;

    /** Column name LastLeaveEnd */
    public static final String COLUMNNAME_LastLeaveEnd = "LastLeaveEnd";

	/** Set Last Leave End	  */
	public void setLastLeaveEnd (Timestamp LastLeaveEnd);

	/** Get Last Leave End	  */
	public Timestamp getLastLeaveEnd();

    /** Column name LastLeaveStart */
    public static final String COLUMNNAME_LastLeaveStart = "LastLeaveStart";

	/** Set Last Leave Start	  */
	public void setLastLeaveStart (Timestamp LastLeaveStart);

	/** Get Last Leave Start	  */
	public Timestamp getLastLeaveStart();

    /** Column name LastTransferFrom */
    public static final String COLUMNNAME_LastTransferFrom = "LastTransferFrom";

	/** Set Last Transfer From	  */
	public void setLastTransferFrom (int LastTransferFrom);

	/** Get Last Transfer From	  */
	public int getLastTransferFrom();

    /** Column name MaritalStatus */
    public static final String COLUMNNAME_MaritalStatus = "MaritalStatus";

	/** Set Marital Status	  */
	public void setMaritalStatus (String MaritalStatus);

	/** Get Marital Status	  */
	public String getMaritalStatus();

    /** Column name MedicalAllowance */
    public static final String COLUMNNAME_MedicalAllowance = "MedicalAllowance";

	/** Set Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance);

	/** Get Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance();

    /** Column name MedicalAllowanceUsed */
    public static final String COLUMNNAME_MedicalAllowanceUsed = "MedicalAllowanceUsed";

	/** Set Medical Allowance Used	  */
	public void setMedicalAllowanceUsed (BigDecimal MedicalAllowanceUsed);

	/** Get Medical Allowance Used	  */
	public BigDecimal getMedicalAllowanceUsed();

    /** Column name MobilePhone */
    public static final String COLUMNNAME_MobilePhone = "MobilePhone";

	/** Set Mobile Phone	  */
	public void setMobilePhone (String MobilePhone);

	/** Get Mobile Phone	  */
	public String getMobilePhone();

    /** Column name MotherName */
    public static final String COLUMNNAME_MotherName = "MotherName";

	/** Set Mother Name	  */
	public void setMotherName (String MotherName);

	/** Get Mother Name	  */
	public String getMotherName();

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

    /** Column name Nationality */
    public static final String COLUMNNAME_Nationality = "Nationality";

	/** Set Nationality	  */
	public void setNationality (String Nationality);

	/** Get Nationality	  */
	public String getNationality();

    /** Column name NickName */
    public static final String COLUMNNAME_NickName = "NickName";

	/** Set Nick Name	  */
	public void setNickName (String NickName);

	/** Get Nick Name	  */
	public String getNickName();

    /** Column name NoWorkDay */
    public static final String COLUMNNAME_NoWorkDay = "NoWorkDay";

	/** Set No Work Day	  */
	public void setNoWorkDay (String NoWorkDay);

	/** Get No Work Day	  */
	public String getNoWorkDay();

    /** Column name NPWP */
    public static final String COLUMNNAME_NPWP = "NPWP";

	/** Set NPWP	  */
	public void setNPWP (String NPWP);

	/** Get NPWP	  */
	public String getNPWP();

    /** Column name NumberOfChild */
    public static final String COLUMNNAME_NumberOfChild = "NumberOfChild";

	/** Set Number Of Child	  */
	public void setNumberOfChild (int NumberOfChild);

	/** Get Number Of Child	  */
	public int getNumberOfChild();

    /** Column name PayrollLevel */
    public static final String COLUMNNAME_PayrollLevel = "PayrollLevel";

	/** Set Payroll Level	  */
	public void setPayrollLevel (String PayrollLevel);

	/** Get Payroll Level	  */
	public String getPayrollLevel();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name PlaceOfBirth */
    public static final String COLUMNNAME_PlaceOfBirth = "PlaceOfBirth";

	/** Set Place Of Birth	  */
	public void setPlaceOfBirth (String PlaceOfBirth);

	/** Get Place Of Birth	  */
	public String getPlaceOfBirth();

    /** Column name Race */
    public static final String COLUMNNAME_Race = "Race";

	/** Set Race	  */
	public void setRace (String Race);

	/** Get Race	  */
	public String getRace();

    /** Column name Recommendation */
    public static final String COLUMNNAME_Recommendation = "Recommendation";

	/** Set Recommendation	  */
	public void setRecommendation (String Recommendation);

	/** Get Recommendation	  */
	public String getRecommendation();

    /** Column name RegNo */
    public static final String COLUMNNAME_RegNo = "RegNo";

	/** Set Reg No	  */
	public void setRegNo (String RegNo);

	/** Get Reg No	  */
	public String getRegNo();

    /** Column name Religion */
    public static final String COLUMNNAME_Religion = "Religion";

	/** Set Religion	  */
	public void setReligion (String Religion);

	/** Get Religion	  */
	public String getReligion();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name RemarksOut */
    public static final String COLUMNNAME_RemarksOut = "RemarksOut";

	/** Set Remarks Out	  */
	public void setRemarksOut (String RemarksOut);

	/** Get Remarks Out	  */
	public String getRemarksOut();

    /** Column name Shift */
    public static final String COLUMNNAME_Shift = "Shift";

	/** Set Shift	  */
	public void setShift (String Shift);

	/** Get Shift	  */
	public String getShift();

    /** Column name Umur */
    public static final String COLUMNNAME_Umur = "Umur";

	/** Set Umur	  */
	public void setUmur (String Umur);

	/** Get Umur	  */
	public String getUmur();

    /** Column name UNS_Contract_Recommendation_ID */
    public static final String COLUMNNAME_UNS_Contract_Recommendation_ID = "UNS_Contract_Recommendation_ID";

	/** Set Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID);

	/** Get Contract	  */
	public int getUNS_Contract_Recommendation_ID();

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException;

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_Employee_UU */
    public static final String COLUMNNAME_UNS_Employee_UU = "UNS_Employee_UU";

	/** Set UNS_Employee_UU	  */
	public void setUNS_Employee_UU (String UNS_Employee_UU);

	/** Get UNS_Employee_UU	  */
	public String getUNS_Employee_UU();

    /** Column name UNS_Mess_Partition_ID */
    public static final String COLUMNNAME_UNS_Mess_Partition_ID = "UNS_Mess_Partition_ID";

	/** Set Mess	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID);

	/** Get Mess	  */
	public int getUNS_Mess_Partition_ID();

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException;

    /** Column name UNS_SlotType_ID */
    public static final String COLUMNNAME_UNS_SlotType_ID = "UNS_SlotType_ID";

	/** Set Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID);

	/** Get Slot Type	  */
	public int getUNS_SlotType_ID();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set NIK.
	  * Employee Number ID
	  */
	public void setValue (String Value);

	/** Get NIK.
	  * Employee Number ID
	  */
	public String getValue();

    /** Column name Vendor_ID */
    public static final String COLUMNNAME_Vendor_ID = "Vendor_ID";

	/** Set Vendor.
	  * The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID);

	/** Get Vendor.
	  * The Vendor of the product/service
	  */
	public int getVendor_ID();

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException;
}
