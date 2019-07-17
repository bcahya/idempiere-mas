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

/** Generated Model for UNS_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Employee extends PO implements I_UNS_Employee, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160830L;

    /** Standard Constructor */
    public X_UNS_Employee (Properties ctx, int UNS_Employee_ID, String trxName)
    {
      super (ctx, UNS_Employee_ID, trxName);
      /** if (UNS_Employee_ID == 0)
        {
			setDateOfBirth (new Timestamp( System.currentTimeMillis() ));
			setEmploymentType (null);
// COM
			setFamilyName (null);
			setFatherName (null);
			setIsBlacklist (false);
// N
			setIsCopyCertificateOfCourses (false);
// N
			setIsCopyOfIdentity (false);
// N
			setIsCopyOfSKCK (false);
// N
			setIsCopyOfWorkExperience (false);
// N
			setIsCurriculumVitae (false);
// N
			setIsDiploma (false);
// N
			setIsHealthCheck (false);
// N
			setIsPasPhoto (false);
// N
			setIsStatementLetter (false);
// N
			setIsTemporary (false);
// N
			setIsTerminate (false);
// N
			setIsTranscript (false);
// N
			setIsUseGeneralPayroll (false);
// N
			setMaritalStatus (null);
			setMotherName (null);
			setName (null);
			setNationality (null);
			setPlaceOfBirth (null);
			setRegNo (null);
			setUNS_Employee_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Employee (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Employee[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address.
		@param Address Address	  */
	public void setAddress (String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

	/** Get Address.
		@return Address	  */
	public String getAddress () 
	{
		return (String)get_Value(COLUMNNAME_Address);
	}

	/** Set Address 2.
		@param Address2 
		Address line 2 for this location
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Attendance Name.
		@param AttendanceName Attendance Name	  */
	public void setAttendanceName (String AttendanceName)
	{
		set_Value (COLUMNNAME_AttendanceName, AttendanceName);
	}

	/** Get Attendance Name.
		@return Attendance Name	  */
	public String getAttendanceName () 
	{
		return (String)get_Value(COLUMNNAME_AttendanceName);
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
    {
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_Name)
			.getPO(getC_Job_ID(), get_TrxName());	}

	/** Set Position.
		@param C_Job_ID 
		Job Position
	  */
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Job Position
	  */
	public int getC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contract Number.
		@param ContractNumber Contract Number	  */
	public void setContractNumber (int ContractNumber)
	{
		set_Value (COLUMNNAME_ContractNumber, Integer.valueOf(ContractNumber));
	}

	/** Get Contract Number.
		@return Contract Number	  */
	public int getContractNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ContractNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Of Birth.
		@param DateOfBirth Date Of Birth	  */
	public void setDateOfBirth (Timestamp DateOfBirth)
	{
		set_Value (COLUMNNAME_DateOfBirth, DateOfBirth);
	}

	/** Get Date Of Birth.
		@return Date Of Birth	  */
	public Timestamp getDateOfBirth () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOfBirth);
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

	/** Set Duration.
		@param Duration 
		Normal Duration in Duration Unit
	  */
	public void setDuration (String Duration)
	{
		throw new IllegalArgumentException ("Duration is virtual column");	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	public String getDuration () 
	{
		return (String)get_Value(COLUMNNAME_Duration);
	}

	/** Set Employee Insured 1.
		@param EmployeeInsured1 Employee Insured 1	  */
	public void setEmployeeInsured1 (String EmployeeInsured1)
	{
		set_Value (COLUMNNAME_EmployeeInsured1, EmployeeInsured1);
	}

	/** Get Employee Insured 1.
		@return Employee Insured 1	  */
	public String getEmployeeInsured1 () 
	{
		return (String)get_Value(COLUMNNAME_EmployeeInsured1);
	}

	/** Set Employee Insured 2.
		@param EmployeeInsured2 Employee Insured 2	  */
	public void setEmployeeInsured2 (String EmployeeInsured2)
	{
		set_Value (COLUMNNAME_EmployeeInsured2, EmployeeInsured2);
	}

	/** Get Employee Insured 2.
		@return Employee Insured 2	  */
	public String getEmployeeInsured2 () 
	{
		return (String)get_Value(COLUMNNAME_EmployeeInsured2);
	}

	/** Set Employee Insured 3.
		@param EmployeeInsured3 Employee Insured 3	  */
	public void setEmployeeInsured3 (String EmployeeInsured3)
	{
		set_Value (COLUMNNAME_EmployeeInsured3, EmployeeInsured3);
	}

	/** Get Employee Insured 3.
		@return Employee Insured 3	  */
	public String getEmployeeInsured3 () 
	{
		return (String)get_Value(COLUMNNAME_EmployeeInsured3);
	}

	/** Set Employee Insured 4.
		@param EmployeeInsured4 Employee Insured 4	  */
	public void setEmployeeInsured4 (String EmployeeInsured4)
	{
		set_Value (COLUMNNAME_EmployeeInsured4, EmployeeInsured4);
	}

	/** Get Employee Insured 4.
		@return Employee Insured 4	  */
	public String getEmployeeInsured4 () 
	{
		return (String)get_Value(COLUMNNAME_EmployeeInsured4);
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

	/** Set Entry Date.
		@param EntryDate Entry Date	  */
	public void setEntryDate (Timestamp EntryDate)
	{
		throw new IllegalArgumentException ("EntryDate is virtual column");	}

	/** Get Entry Date.
		@return Entry Date	  */
	public Timestamp getEntryDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EntryDate);
	}

	/** Set Family Name.
		@param FamilyName Family Name	  */
	public void setFamilyName (String FamilyName)
	{
		set_Value (COLUMNNAME_FamilyName, FamilyName);
	}

	/** Get Family Name.
		@return Family Name	  */
	public String getFamilyName () 
	{
		return (String)get_Value(COLUMNNAME_FamilyName);
	}

	/** Set Father Name.
		@param FatherName Father Name	  */
	public void setFatherName (String FatherName)
	{
		set_Value (COLUMNNAME_FatherName, FatherName);
	}

	/** Get Father Name.
		@return Father Name	  */
	public String getFatherName () 
	{
		return (String)get_Value(COLUMNNAME_FatherName);
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

	/** No Education = 00 */
	public static final String HIGHESTEDBACKROUND_NoEducation = "00";
	/** Diploma 1 = D1 */
	public static final String HIGHESTEDBACKROUND_Diploma1 = "D1";
	/** Diploma 2 = D2 */
	public static final String HIGHESTEDBACKROUND_Diploma2 = "D2";
	/** Diploma 3 = D3 */
	public static final String HIGHESTEDBACKROUND_Diploma3 = "D3";
	/** Madrasah Aliyah = MA */
	public static final String HIGHESTEDBACKROUND_MadrasahAliyah = "MA";
	/** Madrasah Aliyah Negeri = MAN */
	public static final String HIGHESTEDBACKROUND_MadrasahAliyahNegeri = "MAN";
	/** Madrasah Ibtida`iyah = MI */
	public static final String HIGHESTEDBACKROUND_MadrasahIbtidaIyah = "MI";
	/** Madrasah Ibtida`iyah Negeri = MIN */
	public static final String HIGHESTEDBACKROUND_MadrasahIbtidaIyahNegeri = "MIN";
	/** Madrasah 2 = MTS */
	public static final String HIGHESTEDBACKROUND_Madrasah2 = "MTS";
	/** Madrasah Tsanawiyah = MTSn */
	public static final String HIGHESTEDBACKROUND_MadrasahTsanawiyah = "MTSn";
	/** Tidak Sekolah = NA */
	public static final String HIGHESTEDBACKROUND_TidakSekolah = "NA";
	/** Pendidikan Guru Agama = PGA */
	public static final String HIGHESTEDBACKROUND_PendidikanGuruAgama = "PGA";
	/** Sarjana Strata 1 = S1 */
	public static final String HIGHESTEDBACKROUND_SarjanaStrata1 = "S1";
	/** Sarjana Strata 2 = S2 */
	public static final String HIGHESTEDBACKROUND_SarjanaStrata2 = "S2";
	/** Sarjana Strata 3 = S3 */
	public static final String HIGHESTEDBACKROUND_SarjanaStrata3 = "S3";
	/** Sekolah Dasar = SD */
	public static final String HIGHESTEDBACKROUND_SekolahDasar = "SD";
	/** Sekolah Lanjutan Tingkat Atas = SLTA */
	public static final String HIGHESTEDBACKROUND_SekolahLanjutanTingkatAtas = "SLTA";
	/** Sekolah Lanjutan Tingkat Pertama = SLTP */
	public static final String HIGHESTEDBACKROUND_SekolahLanjutanTingkatPertama = "SLTP";
	/** Sekolah Menengah Atas = SMA */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahAtas = "SMA";
	/** Sekolah Menengah Analysis Kimia = SMAK */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahAnalysisKimia = "SMAK";
	/** Sekolah Menengah Ekonomi Atas = SMEA */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahEkonomiAtas = "SMEA";
	/** Sekolah Menengah Kejuruan = SMK */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahKejuruan = "SMK";
	/** Sekolah Menengah Kejuruan Keluarga = SMKK */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahKejuruanKeluarga = "SMKK";
	/** Sekolah Menengah Pertama = SMP */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahPertama = "SMP";
	/** Sekolah Menengah Teknik Industri = SMTI */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahTeknikIndustri = "SMTI";
	/** Sekolah Menengah Teknik Pertanian = SMTP */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahTeknikPertanian = "SMTP";
	/** Sekolah Menengah Umum = SMU */
	public static final String HIGHESTEDBACKROUND_SekolahMenengahUmum = "SMU";
	/** Sekolah Pendidikan Guru = SPG */
	public static final String HIGHESTEDBACKROUND_SekolahPendidikanGuru = "SPG";
	/** Sekolah Pendidikan Keperawatan = SPK */
	public static final String HIGHESTEDBACKROUND_SekolahPendidikanKeperawatan = "SPK";
	/** Sekolah Pertanian Menengah Atas = SPMA */
	public static final String HIGHESTEDBACKROUND_SekolahPertanianMenengahAtas = "SPMA";
	/** Sekolah Pendidikan Pertanian = SPP */
	public static final String HIGHESTEDBACKROUND_SekolahPendidikanPertanian = "SPP";
	/** Sekolah Teknik Menengah = STM */
	public static final String HIGHESTEDBACKROUND_SekolahTeknikMenengah = "STM";
	/** Not Defined = ND */
	public static final String HIGHESTEDBACKROUND_NotDefined = "ND";
	/** Set Highest Ed Backround.
		@param HighestEdBackround Highest Ed Backround	  */
	public void setHighestEdBackround (String HighestEdBackround)
	{

		set_Value (COLUMNNAME_HighestEdBackround, HighestEdBackround);
	}

	/** Get Highest Ed Backround.
		@return Highest Ed Backround	  */
	public String getHighestEdBackround () 
	{
		return (String)get_Value(COLUMNNAME_HighestEdBackround);
	}

	/** Set Home Phone.
		@param HomePhone Home Phone	  */
	public void setHomePhone (String HomePhone)
	{
		set_Value (COLUMNNAME_HomePhone, HomePhone);
	}

	/** Get Home Phone.
		@return Home Phone	  */
	public String getHomePhone () 
	{
		return (String)get_Value(COLUMNNAME_HomePhone);
	}

	/** Set Hour Per Day.
		@param HourPerDay Hour Per Day	  */
	public void setHourPerDay (int HourPerDay)
	{
		set_Value (COLUMNNAME_HourPerDay, Integer.valueOf(HourPerDay));
	}

	/** Get Hour Per Day.
		@return Hour Per Day	  */
	public int getHourPerDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HourPerDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mate Name.
		@param HWname Mate Name	  */
	public void setHWname (String HWname)
	{
		set_Value (COLUMNNAME_HWname, HWname);
	}

	/** Get Mate Name.
		@return Mate Name	  */
	public String getHWname () 
	{
		return (String)get_Value(COLUMNNAME_HWname);
	}

	/** Set ID No.
		@param IDNo ID No	  */
	public void setIDNo (String IDNo)
	{
		set_Value (COLUMNNAME_IDNo, IDNo);
	}

	/** Get ID No.
		@return ID No	  */
	public String getIDNo () 
	{
		return (String)get_Value(COLUMNNAME_IDNo);
	}

	/** SIM = 1 */
	public static final String IDTYPE_SIM = "1";
	/** KTP = 2 */
	public static final String IDTYPE_KTP = "2";
	/** Pasport = 3 */
	public static final String IDTYPE_Pasport = "3";
	/** Set ID Type.
		@param IDType ID Type	  */
	public void setIDType (String IDType)
	{

		set_Value (COLUMNNAME_IDType, IDType);
	}

	/** Get ID Type.
		@return ID Type	  */
	public String getIDType () 
	{
		return (String)get_Value(COLUMNNAME_IDType);
	}

	/** Set The Image Attached.
		@param Image_ID 
		The Image Attached
	  */
	public void setImage_ID (int Image_ID)
	{
		if (Image_ID < 1) 
			set_Value (COLUMNNAME_Image_ID, null);
		else 
			set_Value (COLUMNNAME_Image_ID, Integer.valueOf(Image_ID));
	}

	/** Get The Image Attached.
		@return The Image Attached
	  */
	public int getImage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Transfer Date.
		@param IndicatorTransfer Last Transfer Date	  */
	public void setIndicatorTransfer (Timestamp IndicatorTransfer)
	{
		set_Value (COLUMNNAME_IndicatorTransfer, IndicatorTransfer);
	}

	/** Get Last Transfer Date.
		@return Last Transfer Date	  */
	public Timestamp getIndicatorTransfer () 
	{
		return (Timestamp)get_Value(COLUMNNAME_IndicatorTransfer);
	}

	/** Set Is Application Letter.
		@param IsApplicationLetter Is Application Letter	  */
	public void setIsApplicationLetter (boolean IsApplicationLetter)
	{
		set_Value (COLUMNNAME_IsApplicationLetter, Boolean.valueOf(IsApplicationLetter));
	}

	/** Get Is Application Letter.
		@return Is Application Letter	  */
	public boolean isApplicationLetter () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplicationLetter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Black List.
		@param IsBlacklist Is Black List	  */
	public void setIsBlacklist (boolean IsBlacklist)
	{
		set_Value (COLUMNNAME_IsBlacklist, Boolean.valueOf(IsBlacklist));
	}

	/** Get Is Black List.
		@return Is Black List	  */
	public boolean isBlacklist () 
	{
		Object oo = get_Value(COLUMNNAME_IsBlacklist);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Copy Certificate Of Courses.
		@param IsCopyCertificateOfCourses Is Copy Certificate Of Courses	  */
	public void setIsCopyCertificateOfCourses (boolean IsCopyCertificateOfCourses)
	{
		set_Value (COLUMNNAME_IsCopyCertificateOfCourses, Boolean.valueOf(IsCopyCertificateOfCourses));
	}

	/** Get Is Copy Certificate Of Courses.
		@return Is Copy Certificate Of Courses	  */
	public boolean isCopyCertificateOfCourses () 
	{
		Object oo = get_Value(COLUMNNAME_IsCopyCertificateOfCourses);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Copy Of Identity.
		@param IsCopyOfIdentity Is Copy Of Identity	  */
	public void setIsCopyOfIdentity (boolean IsCopyOfIdentity)
	{
		set_Value (COLUMNNAME_IsCopyOfIdentity, Boolean.valueOf(IsCopyOfIdentity));
	}

	/** Get Is Copy Of Identity.
		@return Is Copy Of Identity	  */
	public boolean isCopyOfIdentity () 
	{
		Object oo = get_Value(COLUMNNAME_IsCopyOfIdentity);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Copy Of SKCK.
		@param IsCopyOfSKCK Is Copy Of SKCK	  */
	public void setIsCopyOfSKCK (boolean IsCopyOfSKCK)
	{
		set_Value (COLUMNNAME_IsCopyOfSKCK, Boolean.valueOf(IsCopyOfSKCK));
	}

	/** Get Is Copy Of SKCK.
		@return Is Copy Of SKCK	  */
	public boolean isCopyOfSKCK () 
	{
		Object oo = get_Value(COLUMNNAME_IsCopyOfSKCK);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Copy Of Work Experience.
		@param IsCopyOfWorkExperience Is Copy Of Work Experience	  */
	public void setIsCopyOfWorkExperience (boolean IsCopyOfWorkExperience)
	{
		set_Value (COLUMNNAME_IsCopyOfWorkExperience, Boolean.valueOf(IsCopyOfWorkExperience));
	}

	/** Get Is Copy Of Work Experience.
		@return Is Copy Of Work Experience	  */
	public boolean isCopyOfWorkExperience () 
	{
		Object oo = get_Value(COLUMNNAME_IsCopyOfWorkExperience);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Curriculum Vitae.
		@param IsCurriculumVitae Is Curriculum Vitae	  */
	public void setIsCurriculumVitae (boolean IsCurriculumVitae)
	{
		set_Value (COLUMNNAME_IsCurriculumVitae, Boolean.valueOf(IsCurriculumVitae));
	}

	/** Get Is Curriculum Vitae.
		@return Is Curriculum Vitae	  */
	public boolean isCurriculumVitae () 
	{
		Object oo = get_Value(COLUMNNAME_IsCurriculumVitae);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Diploma.
		@param IsDiploma Is Diploma	  */
	public void setIsDiploma (boolean IsDiploma)
	{
		set_Value (COLUMNNAME_IsDiploma, Boolean.valueOf(IsDiploma));
	}

	/** Get Is Diploma.
		@return Is Diploma	  */
	public boolean isDiploma () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiploma);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Health Check.
		@param IsHealthCheck Is Health Check	  */
	public void setIsHealthCheck (boolean IsHealthCheck)
	{
		set_Value (COLUMNNAME_IsHealthCheck, Boolean.valueOf(IsHealthCheck));
	}

	/** Get Is Health Check.
		@return Is Health Check	  */
	public boolean isHealthCheck () 
	{
		Object oo = get_Value(COLUMNNAME_IsHealthCheck);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Pas Photo.
		@param IsPasPhoto Is Pas Photo	  */
	public void setIsPasPhoto (boolean IsPasPhoto)
	{
		set_Value (COLUMNNAME_IsPasPhoto, Boolean.valueOf(IsPasPhoto));
	}

	/** Get Is Pas Photo.
		@return Is Pas Photo	  */
	public boolean isPasPhoto () 
	{
		Object oo = get_Value(COLUMNNAME_IsPasPhoto);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Statement Letter.
		@param IsStatementLetter Is Statement Letter	  */
	public void setIsStatementLetter (boolean IsStatementLetter)
	{
		set_Value (COLUMNNAME_IsStatementLetter, Boolean.valueOf(IsStatementLetter));
	}

	/** Get Is Statement Letter.
		@return Is Statement Letter	  */
	public boolean isStatementLetter () 
	{
		Object oo = get_Value(COLUMNNAME_IsStatementLetter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Temporary.
		@param IsTemporary Is Temporary	  */
	public void setIsTemporary (boolean IsTemporary)
	{
		set_Value (COLUMNNAME_IsTemporary, Boolean.valueOf(IsTemporary));
	}

	/** Get Is Temporary.
		@return Is Temporary	  */
	public boolean isTemporary () 
	{
		Object oo = get_Value(COLUMNNAME_IsTemporary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Terminate.
		@param IsTerminate Terminate	  */
	public void setIsTerminate (boolean IsTerminate)
	{
		set_Value (COLUMNNAME_IsTerminate, Boolean.valueOf(IsTerminate));
	}

	/** Get Terminate.
		@return Terminate	  */
	public boolean isTerminate () 
	{
		Object oo = get_Value(COLUMNNAME_IsTerminate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Transcript.
		@param IsTranscript Is Transcript	  */
	public void setIsTranscript (boolean IsTranscript)
	{
		set_Value (COLUMNNAME_IsTranscript, Boolean.valueOf(IsTranscript));
	}

	/** Get Is Transcript.
		@return Is Transcript	  */
	public boolean isTranscript () 
	{
		Object oo = get_Value(COLUMNNAME_IsTranscript);
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

	/** Set Jamsostek ID.
		@param JamsostekID Jamsostek ID	  */
	public void setJamsostekID (String JamsostekID)
	{
		set_Value (COLUMNNAME_JamsostekID, JamsostekID);
	}

	/** Get Jamsostek ID.
		@return Jamsostek ID	  */
	public String getJamsostekID () 
	{
		return (String)get_Value(COLUMNNAME_JamsostekID);
	}

	/** Biologi = 0101 */
	public static final String JURUSAN_Biologi = "0101";
	/** Akuntansi = 0201 */
	public static final String JURUSAN_Akuntansi = "0201";
	/** Manajemen = 0202 */
	public static final String JURUSAN_Manajemen = "0202";
	/** Ilmu Ekonomi = 0203 */
	public static final String JURUSAN_IlmuEkonomi = "0203";
	/** Studi Pembangunan = 0204 */
	public static final String JURUSAN_StudiPembangunan = "0204";
	/** Obat Alami = 0301 */
	public static final String JURUSAN_ObatAlami = "0301";
	/** Farmasi = 0302 */
	public static final String JURUSAN_Farmasi = "0302";
	/** Ilmu Filsafat = 0401 */
	public static final String JURUSAN_IlmuFilsafat = "0401";
	/** Geografi = 0501 */
	public static final String JURUSAN_Geografi = "0501";
	/** Kartografi dan Penginderaan Jauh = 0502 */
	public static final String JURUSAN_KartografiDanPenginderaanJauh = "0502";
	/** Geografi Fisik dan Lingkungan = 0503 */
	public static final String JURUSAN_GeografiFisikDanLingkungan = "0503";
	/** Geografi Manusia = 0504 */
	public static final String JURUSAN_GeografiManusia = "0504";
	/** Pembangunan Wilayah = 0505 */
	public static final String JURUSAN_PembangunanWilayah = "0505";
	/** Ilmu Hukum = 0601 */
	public static final String JURUSAN_IlmuHukum = "0601";
	/** Antroplologi Budaya = 0701 */
	public static final String JURUSAN_AntroplologiBudaya = "0701";
	/** Arkeologi = 0702 */
	public static final String JURUSAN_Arkeologi = "0702";
	/** Sastra Arab = 0703 */
	public static final String JURUSAN_SastraArab = "0703";
	/** Sastra Indonesia = 0704 */
	public static final String JURUSAN_SastraIndonesia = "0704";
	/** Sastra Inggris = 0705 */
	public static final String JURUSAN_SastraInggris = "0705";
	/** Sastra Jepang = 0706 */
	public static final String JURUSAN_SastraJepang = "0706";
	/** Sastra Daerah = 0707 */
	public static final String JURUSAN_SastraDaerah = "0707";
	/** Sastra Perancis = 0708 */
	public static final String JURUSAN_SastraPerancis = "0708";
	/** Ilmu Sejarah = 0709 */
	public static final String JURUSAN_IlmuSejarah = "0709";
	/** Ilmu Administrasi Negara = 0801 */
	public static final String JURUSAN_IlmuAdministrasiNegara = "0801";
	/** Ilmu Hubungan Internasional = 0802 */
	public static final String JURUSAN_IlmuHubunganInternasional = "0802";
	/** Ilmu Komunikasi = 0803 */
	public static final String JURUSAN_IlmuKomunikasi = "0803";
	/** Ilmu Pemerintahan = 0804 */
	public static final String JURUSAN_IlmuPemerintahan = "0804";
	/** Sosiologi = 0805 */
	public static final String JURUSAN_Sosiologi = "0805";
	/** Ilmu Sosiatri = 0806 */
	public static final String JURUSAN_IlmuSosiatri = "0806";
	/** Pendidikan Dokter = 0901 */
	public static final String JURUSAN_PendidikanDokter = "0901";
	/** Ilmu Keperawatan = 0902 */
	public static final String JURUSAN_IlmuKeperawatan = "0902";
	/** Gizi Kesehatan = 0903 */
	public static final String JURUSAN_GiziKesehatan = "0903";
	/** Pendidikan Dokter Gigi = 1001 */
	public static final String JURUSAN_PendidikanDokterGigi = "1001";
	/** Pendidikan Dokter Hewan = 1101 */
	public static final String JURUSAN_PendidikanDokterHewan = "1101";
	/** Manajemen Hutan = 1201 */
	public static final String JURUSAN_ManajemenHutan = "1201";
	/** Budidaya Hutan = 1202 */
	public static final String JURUSAN_BudidayaHutan = "1202";
	/** Teknologi Hasil Hutan = 1203 */
	public static final String JURUSAN_TeknologiHasilHutan = "1203";
	/** Konservasi Sumberdaya Hutan = 1204 */
	public static final String JURUSAN_KonservasiSumberdayaHutan = "1204";
	/** Matematika = 1301 */
	public static final String JURUSAN_Matematika = "1301";
	/** Ilmu Komputer = 1302 */
	public static final String JURUSAN_IlmuKomputer = "1302";
	/** Statistika = 1303 */
	public static final String JURUSAN_Statistika = "1303";
	/** Fisika = 1304 */
	public static final String JURUSAN_Fisika = "1304";
	/** Geofisika = 1305 */
	public static final String JURUSAN_Geofisika = "1305";
	/** Elektronika & Instrumentasi = 1306 */
	public static final String JURUSAN_ElektronikaInstrumentasi = "1306";
	/** Kimia = 1307 */
	public static final String JURUSAN_Kimia = "1307";
	/** Kimia Industri = 1308 */
	public static final String JURUSAN_KimiaIndustri = "1308";
	/** Agronomi = 1401 */
	public static final String JURUSAN_Agronomi = "1401";
	/** Pemuliaan Tanaman = 1402 */
	public static final String JURUSAN_PemuliaanTanaman = "1402";
	/** Ekonomi Pertanian / Agrobisnis = 1403 */
	public static final String JURUSAN_EkonomiPertanianAgrobisnis = "1403";
	/** Penyuluhan dan Komunikasi Pertanian = 1404 */
	public static final String JURUSAN_PenyuluhanDanKomunikasiPertanian = "1404";
	/** Ilmu Tanah = 1405 */
	public static final String JURUSAN_IlmuTanah = "1405";
	/** Ilmu Hama dan Penyakit Tumbuhan = 1406 */
	public static final String JURUSAN_IlmuHamaDanPenyakitTumbuhan = "1406";
	/** Budidaya Perikanan = 1407 */
	public static final String JURUSAN_BudidayaPerikanan = "1407";
	/** Manajemen Sumberdaya Perikanan = 1408 */
	public static final String JURUSAN_ManajemenSumberdayaPerikanan = "1408";
	/** Teknologi Hasil Perikanan = 1409 */
	public static final String JURUSAN_TeknologiHasilPerikanan = "1409";
	/** Mikrobiologi Pertanian = 1410 */
	public static final String JURUSAN_MikrobiologiPertanian = "1410";
	/** Nurtrisi dan Makanan Ternak = 1501 */
	public static final String JURUSAN_NurtrisiDanMakananTernak = "1501";
	/** Produksi Ternak = 1502 */
	public static final String JURUSAN_ProduksiTernak = "1502";
	/** Sosial Ekonomi Peternakan = 1503 */
	public static final String JURUSAN_SosialEkonomiPeternakan = "1503";
	/** Teknologi Hasil Ternak = 1504 */
	public static final String JURUSAN_TeknologiHasilTernak = "1504";
	/** Psikologi = 1601 */
	public static final String JURUSAN_Psikologi = "1601";
	/** Teknik Mesin = 1701 */
	public static final String JURUSAN_TeknikMesin = "1701";
	/** Teknik Elektro = 1702 */
	public static final String JURUSAN_TeknikElektro = "1702";
	/** Teknik Industri = 1703 */
	public static final String JURUSAN_TeknikIndustri = "1703";
	/** Teknik Komputer = 1704 */
	public static final String JURUSAN_TeknikKomputer = "1704";
	/** Teknik Informatika = 1705 */
	public static final String JURUSAN_TeknikInformatika = "1705";
	/** Teknik Sipil = 1706 */
	public static final String JURUSAN_TeknikSipil = "1706";
	/** Teknik Arsitektur = 1707 */
	public static final String JURUSAN_TeknikArsitektur = "1707";
	/** Teknik Geodesi = 1708 */
	public static final String JURUSAN_TeknikGeodesi = "1708";
	/** Teknik Geologi = 1709 */
	public static final String JURUSAN_TeknikGeologi = "1709";
	/** Teknik Kimia = 1710 */
	public static final String JURUSAN_TeknikKimia = "1710";
	/** Teknik Nuklir = 1711 */
	public static final String JURUSAN_TeknikNuklir = "1711";
	/** Fisika Teknik = 1712 */
	public static final String JURUSAN_FisikaTeknik = "1712";
	/** Manajemen Informatika = 1713 */
	public static final String JURUSAN_ManajemenInformatika = "1713";
	/** Perencanaan Kota dan Daerah = 1718 */
	public static final String JURUSAN_PerencanaanKotaDanDaerah = "1718";
	/** Teknologi Industri Pertanian = 1801 */
	public static final String JURUSAN_TeknologiIndustriPertanian = "1801";
	/** Teknologi Hasil Pertanian = 1802 */
	public static final String JURUSAN_TeknologiHasilPertanian = "1802";
	/** Teknik Pertanian = 1803 */
	public static final String JURUSAN_TeknikPertanian = "1803";
	/** Administrasi Keuangan = 3002 */
	public static final String JURUSAN_AdministrasiKeuangan = "3002";
	/** IPA Biologi = 2002 */
	public static final String JURUSAN_IPABiologi = "2002";
	/** IPA Ekonomi = 2003 */
	public static final String JURUSAN_IPAEkonomi = "2003";
	/** IPS Sosiologi = 2101 */
	public static final String JURUSAN_IPSSosiologi = "2101";
	/** IPS Bahasa = 2102 */
	public static final String JURUSAN_IPSBahasa = "2102";
	/** Management (Multimedia) = 3001 */
	public static final String JURUSAN_ManagementMultimedia = "3001";
	/** IPA Fisika = 2001 */
	public static final String JURUSAN_IPAFisika = "2001";
	/** Set Subject.
		@param Jurusan Subject	  */
	public void setJurusan (String Jurusan)
	{

		set_Value (COLUMNNAME_Jurusan, Jurusan);
	}

	/** Get Subject.
		@return Subject	  */
	public String getJurusan () 
	{
		return (String)get_Value(COLUMNNAME_Jurusan);
	}

	public com.uns.model.I_UNS_Contract_Evaluation getLast_Evaluation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Contract_Evaluation)MTable.get(getCtx(), com.uns.model.I_UNS_Contract_Evaluation.Table_Name)
			.getPO(getLast_Evaluation_ID(), get_TrxName());	}

	/** Set Last Evaluation.
		@param Last_Evaluation_ID 
		Last Evaluation
	  */
	public void setLast_Evaluation_ID (int Last_Evaluation_ID)
	{
		if (Last_Evaluation_ID < 1) 
			set_Value (COLUMNNAME_Last_Evaluation_ID, null);
		else 
			set_Value (COLUMNNAME_Last_Evaluation_ID, Integer.valueOf(Last_Evaluation_ID));
	}

	/** Get Last Evaluation.
		@return Last Evaluation
	  */
	public int getLast_Evaluation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Last_Evaluation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Leave End.
		@param LastLeaveEnd Last Leave End	  */
	public void setLastLeaveEnd (Timestamp LastLeaveEnd)
	{
		set_Value (COLUMNNAME_LastLeaveEnd, LastLeaveEnd);
	}

	/** Get Last Leave End.
		@return Last Leave End	  */
	public Timestamp getLastLeaveEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastLeaveEnd);
	}

	/** Set Last Leave Start.
		@param LastLeaveStart Last Leave Start	  */
	public void setLastLeaveStart (Timestamp LastLeaveStart)
	{
		set_Value (COLUMNNAME_LastLeaveStart, LastLeaveStart);
	}

	/** Get Last Leave Start.
		@return Last Leave Start	  */
	public Timestamp getLastLeaveStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastLeaveStart);
	}

	/** Set Last Transfer From.
		@param LastTransferFrom Last Transfer From	  */
	public void setLastTransferFrom (int LastTransferFrom)
	{
		set_Value (COLUMNNAME_LastTransferFrom, Integer.valueOf(LastTransferFrom));
	}

	/** Get Last Transfer From.
		@return Last Transfer From	  */
	public int getLastTransferFrom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastTransferFrom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Kawin 0 Tanggungan = K0 */
	public static final String MARITALSTATUS_Kawin0Tanggungan = "K0";
	/** Kawin 1 Tanggungan = K1 */
	public static final String MARITALSTATUS_Kawin1Tanggungan = "K1";
	/** Kawin 2 Tanggungan = K2 */
	public static final String MARITALSTATUS_Kawin2Tanggungan = "K2";
	/** Kawin 3 Tanggungan = K3 */
	public static final String MARITALSTATUS_Kawin3Tanggungan = "K3";
	/** Tidak Kawin 0 Tanggungan = TK0 */
	public static final String MARITALSTATUS_TidakKawin0Tanggungan = "TK0";
	/** Tidak Kawin 1 Tanggungan = TK1 */
	public static final String MARITALSTATUS_TidakKawin1Tanggungan = "TK1";
	/** Tidak Kawin 2 Tanggungan = TK2 */
	public static final String MARITALSTATUS_TidakKawin2Tanggungan = "TK2";
	/** Kawin 4 Tanggungan = K4 */
	public static final String MARITALSTATUS_Kawin4Tanggungan = "K4";
	/** Kawin 5 Tanggungan = K5 */
	public static final String MARITALSTATUS_Kawin5Tanggungan = "K5";
	/** Not Definition = ND */
	public static final String MARITALSTATUS_NotDefinition = "ND";
	/** Set Marital Status.
		@param MaritalStatus Marital Status	  */
	public void setMaritalStatus (String MaritalStatus)
	{

		set_Value (COLUMNNAME_MaritalStatus, MaritalStatus);
	}

	/** Get Marital Status.
		@return Marital Status	  */
	public String getMaritalStatus () 
	{
		return (String)get_Value(COLUMNNAME_MaritalStatus);
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

	/** Set Medical Allowance Used.
		@param MedicalAllowanceUsed Medical Allowance Used	  */
	public void setMedicalAllowanceUsed (BigDecimal MedicalAllowanceUsed)
	{
		set_Value (COLUMNNAME_MedicalAllowanceUsed, MedicalAllowanceUsed);
	}

	/** Get Medical Allowance Used.
		@return Medical Allowance Used	  */
	public BigDecimal getMedicalAllowanceUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowanceUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Mobile Phone.
		@param MobilePhone Mobile Phone	  */
	public void setMobilePhone (String MobilePhone)
	{
		set_Value (COLUMNNAME_MobilePhone, MobilePhone);
	}

	/** Get Mobile Phone.
		@return Mobile Phone	  */
	public String getMobilePhone () 
	{
		return (String)get_Value(COLUMNNAME_MobilePhone);
	}

	/** Set Mother Name.
		@param MotherName Mother Name	  */
	public void setMotherName (String MotherName)
	{
		set_Value (COLUMNNAME_MotherName, MotherName);
	}

	/** Get Mother Name.
		@return Mother Name	  */
	public String getMotherName () 
	{
		return (String)get_Value(COLUMNNAME_MotherName);
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

	/** Warga Negara Asing = WNA */
	public static final String NATIONALITY_WargaNegaraAsing = "WNA";
	/** Warga Negara Indonesia = WNI */
	public static final String NATIONALITY_WargaNegaraIndonesia = "WNI";
	/** Set Nationality.
		@param Nationality Nationality	  */
	public void setNationality (String Nationality)
	{

		set_ValueNoCheck (COLUMNNAME_Nationality, Nationality);
	}

	/** Get Nationality.
		@return Nationality	  */
	public String getNationality () 
	{
		return (String)get_Value(COLUMNNAME_Nationality);
	}

	/** Set Nick Name.
		@param NickName Nick Name	  */
	public void setNickName (String NickName)
	{
		set_Value (COLUMNNAME_NickName, NickName);
	}

	/** Get Nick Name.
		@return Nick Name	  */
	public String getNickName () 
	{
		return (String)get_Value(COLUMNNAME_NickName);
	}

	/** Saturday = 7 */
	public static final String NOWORKDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String NOWORKDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String NOWORKDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String NOWORKDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String NOWORKDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String NOWORKDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String NOWORKDAY_Sunday = "1";
	/** Set No Work Day.
		@param NoWorkDay No Work Day	  */
	public void setNoWorkDay (String NoWorkDay)
	{

		set_Value (COLUMNNAME_NoWorkDay, NoWorkDay);
	}

	/** Get No Work Day.
		@return No Work Day	  */
	public String getNoWorkDay () 
	{
		return (String)get_Value(COLUMNNAME_NoWorkDay);
	}

	/** Set NPWP.
		@param NPWP NPWP	  */
	public void setNPWP (String NPWP)
	{
		set_Value (COLUMNNAME_NPWP, NPWP);
	}

	/** Get NPWP.
		@return NPWP	  */
	public String getNPWP () 
	{
		return (String)get_Value(COLUMNNAME_NPWP);
	}

	/** Set Number Of Child.
		@param NumberOfChild Number Of Child	  */
	public void setNumberOfChild (int NumberOfChild)
	{
		set_Value (COLUMNNAME_NumberOfChild, Integer.valueOf(NumberOfChild));
	}

	/** Get Number Of Child.
		@return Number Of Child	  */
	public int getNumberOfChild () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberOfChild);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Level 1 = 1 */
	public static final String PAYROLLLEVEL_Level1 = "1";
	/** Level 2 = 2 */
	public static final String PAYROLLLEVEL_Level2 = "2";
	/** Level 3 = 3 */
	public static final String PAYROLLLEVEL_Level3 = "3";
	/** Level 4 = 4 */
	public static final String PAYROLLLEVEL_Level4 = "4";
	/** Level 5 = 5 */
	public static final String PAYROLLLEVEL_Level5 = "5";
	/** Level 6 = 6 */
	public static final String PAYROLLLEVEL_Level6 = "6";
	/** Not Defined = 0 */
	public static final String PAYROLLLEVEL_NotDefined = "0";
	/** Level 7 = 7 */
	public static final String PAYROLLLEVEL_Level7 = "7";
	/** Level 8 = 8 */
	public static final String PAYROLLLEVEL_Level8 = "8";
	/** Level 9 = 9 */
	public static final String PAYROLLLEVEL_Level9 = "9";
	/** Set Payroll Level.
		@param PayrollLevel Payroll Level	  */
	public void setPayrollLevel (String PayrollLevel)
	{

		set_Value (COLUMNNAME_PayrollLevel, PayrollLevel);
	}

	/** Get Payroll Level.
		@return Payroll Level	  */
	public String getPayrollLevel () 
	{
		return (String)get_Value(COLUMNNAME_PayrollLevel);
	}

	/** Monthly = 01 */
	public static final String PAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PAYROLLTERM_HarianBulanan = "04";
	/** Set Payroll Term.
		@param PayrollTerm Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm)
	{

		set_Value (COLUMNNAME_PayrollTerm, PayrollTerm);
	}

	/** Get Payroll Term.
		@return Payroll Term	  */
	public String getPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PayrollTerm);
	}

	/** Set Place Of Birth.
		@param PlaceOfBirth Place Of Birth	  */
	public void setPlaceOfBirth (String PlaceOfBirth)
	{
		set_Value (COLUMNNAME_PlaceOfBirth, PlaceOfBirth);
	}

	/** Get Place Of Birth.
		@return Place Of Birth	  */
	public String getPlaceOfBirth () 
	{
		return (String)get_Value(COLUMNNAME_PlaceOfBirth);
	}

	/** Aceh = AC */
	public static final String RACE_Aceh = "AC";
	/** Jambak = JK */
	public static final String RACE_Jambak = "JK";
	/** Jambi = JM */
	public static final String RACE_Jambi = "JM";
	/** Jawa = JW */
	public static final String RACE_Jawa = "JW";
	/** Kombiring = KB */
	public static final String RACE_Kombiring = "KB";
	/** Kalimantan = KL */
	public static final String RACE_Kalimantan = "KL";
	/** Karo = KR */
	public static final String RACE_Karo = "KR";
	/** Koto = KT */
	public static final String RACE_Koto = "KT";
	/** Lahat = LHT */
	public static final String RACE_Lahat = "LHT";
	/** Lain-Lain = LL */
	public static final String RACE_Lain_Lain = "LL";
	/** Lombok = LM */
	public static final String RACE_Lombok = "LM";
	/** Lampung = LP */
	public static final String RACE_Lampung = "LP";
	/** Madura = MDR */
	public static final String RACE_Madura = "MDR";
	/** Minahasa = MH */
	public static final String RACE_Minahasa = "MH";
	/** Melayu = ML */
	public static final String RACE_Melayu = "ML";
	/** Minang = MN */
	public static final String RACE_Minang = "MN";
	/** Nias = NS */
	public static final String RACE_Nias = "NS";
	/** Padang = PD */
	public static final String RACE_Padang = "PD";
	/** Pegagan = PG */
	public static final String RACE_Pegagan = "PG";
	/** Piliang = PL */
	public static final String RACE_Piliang = "PL";
	/** Palembang = PLG */
	public static final String RACE_Palembang = "PLG";
	/** Rejang = RJG */
	public static final String RACE_Rejang = "RJG";
	/** Sanger (Manado) = SGR */
	public static final String RACE_SangerManado = "SGR";
	/** Semendu = SM */
	public static final String RACE_Semendu = "SM";
	/** Sunda = SN */
	public static final String RACE_Sunda = "SN";
	/** Serawai = SRW */
	public static final String RACE_Serawai = "SRW";
	/** Sasak = SS */
	public static final String RACE_Sasak = "SS";
	/** Tagalog = TG */
	public static final String RACE_Tagalog = "TG";
	/** Tiong Hoa = THO */
	public static final String RACE_TiongHoa = "THO";
	/** Taluk = TL */
	public static final String RACE_Taluk = "TL";
	/** Timor = TM */
	public static final String RACE_Timor = "TM";
	/** Tapanuli = TP */
	public static final String RACE_Tapanuli = "TP";
	/** Toraja = TRJ */
	public static final String RACE_Toraja = "TRJ";
	/** Bangka = BK */
	public static final String RACE_Bangka = "BK";
	/** Bali = BL */
	public static final String RACE_Bali = "BL";
	/** Ambon = AM */
	public static final String RACE_Ambon = "AM";
	/** Dayak = DYK */
	public static final String RACE_Dayak = "DYK";
	/** China = CH */
	public static final String RACE_China = "CH";
	/** BUGIS = BG */
	public static final String RACE_BUGIS = "BG";
	/** Banjar = BJ */
	public static final String RACE_Banjar = "BJ";
	/** Irian Jaya = IJ */
	public static final String RACE_IrianJaya = "IJ";
	/** Flores = FL */
	public static final String RACE_Flores = "FL";
	/** Arab = ARB */
	public static final String RACE_Arab = "ARB";
	/** Bima = BM */
	public static final String RACE_Bima = "BM";
	/** Batak = BTK */
	public static final String RACE_Batak = "BTK";
	/** Betawi = BTW */
	public static final String RACE_Betawi = "BTW";
	/** Set Race.
		@param Race Race	  */
	public void setRace (String Race)
	{

		set_Value (COLUMNNAME_Race, Race);
	}

	/** Get Race.
		@return Race	  */
	public String getRace () 
	{
		return (String)get_Value(COLUMNNAME_Race);
	}

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

	/** Set Reg No.
		@param RegNo Reg No	  */
	public void setRegNo (String RegNo)
	{
		set_Value (COLUMNNAME_RegNo, RegNo);
	}

	/** Get Reg No.
		@return Reg No	  */
	public String getRegNo () 
	{
		return (String)get_Value(COLUMNNAME_RegNo);
	}

	/** Hindu = H */
	public static final String RELIGION_Hindu = "H";
	/** Islam = I */
	public static final String RELIGION_Islam = "I";
	/** Kristen Protestan = C */
	public static final String RELIGION_KristenProtestan = "C";
	/** Katolik = K */
	public static final String RELIGION_Katolik = "K";
	/** Budha = B */
	public static final String RELIGION_Budha = "B";
	/** Unknown = Z */
	public static final String RELIGION_Unknown = "Z";
	/** Free Thinker = X */
	public static final String RELIGION_FreeThinker = "X";
	/** Set Religion.
		@param Religion Religion	  */
	public void setReligion (String Religion)
	{

		set_Value (COLUMNNAME_Religion, Religion);
	}

	/** Get Religion.
		@return Religion	  */
	public String getReligion () 
	{
		return (String)get_Value(COLUMNNAME_Religion);
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

	/** Set Remarks Out.
		@param RemarksOut Remarks Out	  */
	public void setRemarksOut (String RemarksOut)
	{
		set_Value (COLUMNNAME_RemarksOut, RemarksOut);
	}

	/** Get Remarks Out.
		@return Remarks Out	  */
	public String getRemarksOut () 
	{
		return (String)get_Value(COLUMNNAME_RemarksOut);
	}

	/** Non Shift = NS */
	public static final String SHIFT_NonShift = "NS";
	/** Shift = SH */
	public static final String SHIFT_Shift = "SH";
	/** Set Shift.
		@param Shift Shift	  */
	public void setShift (String Shift)
	{

		set_Value (COLUMNNAME_Shift, Shift);
	}

	/** Get Shift.
		@return Shift	  */
	public String getShift () 
	{
		return (String)get_Value(COLUMNNAME_Shift);
	}

	/** Set Umur.
		@param Umur Umur	  */
	public void setUmur (String Umur)
	{
		throw new IllegalArgumentException ("Umur is virtual column");	}

	/** Get Umur.
		@return Umur	  */
	public String getUmur () 
	{
		return (String)get_Value(COLUMNNAME_Umur);
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

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
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

	/** Set UNS_Employee_UU.
		@param UNS_Employee_UU UNS_Employee_UU	  */
	public void setUNS_Employee_UU (String UNS_Employee_UU)
	{
		set_Value (COLUMNNAME_UNS_Employee_UU, UNS_Employee_UU);
	}

	/** Get UNS_Employee_UU.
		@return UNS_Employee_UU	  */
	public String getUNS_Employee_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Employee_UU);
	}

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Mess_Partition)MTable.get(getCtx(), com.uns.model.I_UNS_Mess_Partition.Table_Name)
			.getPO(getUNS_Mess_Partition_ID(), get_TrxName());	}

	/** Set Mess.
		@param UNS_Mess_Partition_ID Mess	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID)
	{
		if (UNS_Mess_Partition_ID < 1) 
			set_Value (COLUMNNAME_UNS_Mess_Partition_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Mess_Partition_ID, Integer.valueOf(UNS_Mess_Partition_ID));
	}

	/** Get Mess.
		@return Mess	  */
	public int getUNS_Mess_Partition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_Partition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_Value (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
	}

	/** Get Slot Type.
		@return Slot Type	  */
	public int getUNS_SlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NIK.
		@param Value 
		Employee Number ID
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get NIK.
		@return Employee Number ID
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getVendor_ID(), get_TrxName());	}

	/** Set Vendor.
		@param Vendor_ID 
		The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID)
	{
		if (Vendor_ID < 1) 
			set_Value (COLUMNNAME_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_Vendor_ID, Integer.valueOf(Vendor_ID));
	}

	/** Get Vendor.
		@return The Vendor of the product/service
	  */
	public int getVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}