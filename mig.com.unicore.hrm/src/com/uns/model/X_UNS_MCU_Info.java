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

/** Generated Model for UNS_MCU_Info
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MCU_Info extends PO implements I_UNS_MCU_Info, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140205L;

    /** Standard Constructor */
    public X_UNS_MCU_Info (Properties ctx, int UNS_MCU_Info_ID, String trxName)
    {
      super (ctx, UNS_MCU_Info_ID, trxName);
      /** if (UNS_MCU_Info_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setBloodType (null);
			setC_DocType_ID (0);
// 1000071
			setChild (false);
// N
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDoctor_ID (0);
			setHeight (Env.ZERO);
			setIsApproved (false);
// N
			setMCUDate (new Timestamp( System.currentTimeMillis() ));
			setProcessed (false);
// N
			setResult (null);
			setUNS_Employee_ID (0);
			setUNS_MCU_Info_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
// @MCUDate@
			setValidTo (new Timestamp( System.currentTimeMillis() ));
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_MCU_Info (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_MCU_Info[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** BloodType AD_Reference_ID=1000079 */
	public static final int BLOODTYPE_AD_Reference_ID=1000079;
	/** A = 1 */
	public static final String BLOODTYPE_A = "1";
	/** B = 2 */
	public static final String BLOODTYPE_B = "2";
	/** AB = 3 */
	public static final String BLOODTYPE_AB = "3";
	/** O = 4 */
	public static final String BLOODTYPE_O = "4";
	/** Unknow = 5 */
	public static final String BLOODTYPE_Unknow = "5";
	/** Set Blood Type.
		@param BloodType Blood Type	  */
	public void setBloodType (String BloodType)
	{

		set_Value (COLUMNNAME_BloodType, BloodType);
	}

	/** Get Blood Type.
		@return Blood Type	  */
	public String getBloodType () 
	{
		return (String)get_Value(COLUMNNAME_BloodType);
	}

	/** Set Body Mass Index (BMI).
		@param BMI Body Mass Index (BMI)	  */
	public void setBMI (BigDecimal BMI)
	{
		set_ValueNoCheck (COLUMNNAME_BMI, BMI);
	}

	/** Get Body Mass Index (BMI).
		@return Body Mass Index (BMI)	  */
	public BigDecimal getBMI () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BMI);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Child.
		@param Child Child	  */
	public void setChild (boolean Child)
	{
		set_Value (COLUMNNAME_Child, Boolean.valueOf(Child));
	}

	/** Get Child.
		@return Child	  */
	public boolean isChild () 
	{
		Object oo = get_Value(COLUMNNAME_Child);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
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

	/** Gender AD_Reference_ID=1000054 */
	public static final int GENDER_AD_Reference_ID=1000054;
	/** Male = M */
	public static final String GENDER_Male = "M";
	/** Female = F */
	public static final String GENDER_Female = "F";
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

	/** Set Height.
		@param Height Height	  */
	public void setHeight (BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	/** Get Height.
		@return Height	  */
	public BigDecimal getHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Height);
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
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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

	/** Set Is HRD Recruiment.
		@param isHRDRequitment Is HRD Recruiment	  */
	public void setisHRDRequitment (boolean isHRDRequitment)
	{
		set_Value (COLUMNNAME_isHRDRequitment, Boolean.valueOf(isHRDRequitment));
	}

	/** Get Is HRD Recruiment.
		@return Is HRD Recruiment	  */
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

	/** Set MCU Date.
		@param MCUDate 
		Date of MCU
	  */
	public void setMCUDate (Timestamp MCUDate)
	{
		set_Value (COLUMNNAME_MCUDate, MCUDate);
	}

	/** Get MCU Date.
		@return Date of MCU
	  */
	public Timestamp getMCUDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MCUDate);
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Result AD_Reference_ID=1000080 */
	public static final int RESULT_AD_Reference_ID=1000080;
	/** Fit With Notes = FN */
	public static final String RESULT_FitWithNotes = "FN";
	/** Unfit = UF */
	public static final String RESULT_Unfit = "UF";
	/** Fit = F */
	public static final String RESULT_Fit = "F";
	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{

		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
	}

	/** Set Result Notes.
		@param ResultNotes 
		Notes to the result.
	  */
	public void setResultNotes (String ResultNotes)
	{
		set_Value (COLUMNNAME_ResultNotes, ResultNotes);
	}

	/** Get Result Notes.
		@return Notes to the result.
	  */
	public String getResultNotes () 
	{
		return (String)get_Value(COLUMNNAME_ResultNotes);
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

	/** Set MCU Info.
		@param UNS_MCU_Info_ID 
		UNS_MCU_Info_ID
	  */
	public void setUNS_MCU_Info_ID (int UNS_MCU_Info_ID)
	{
		if (UNS_MCU_Info_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MCU_Info_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MCU_Info_ID, Integer.valueOf(UNS_MCU_Info_ID));
	}

	/** Get MCU Info.
		@return UNS_MCU_Info_ID
	  */
	public int getUNS_MCU_Info_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MCU_Info_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MCU_Info_UU.
		@param UNS_MCU_Info_UU UNS_MCU_Info_UU	  */
	public void setUNS_MCU_Info_UU (String UNS_MCU_Info_UU)
	{
		set_Value (COLUMNNAME_UNS_MCU_Info_UU, UNS_MCU_Info_UU);
	}

	/** Get UNS_MCU_Info_UU.
		@return UNS_MCU_Info_UU	  */
	public String getUNS_MCU_Info_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MCU_Info_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}