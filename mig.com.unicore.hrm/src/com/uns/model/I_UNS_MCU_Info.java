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

/** Generated Interface for UNS_MCU_Info
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_MCU_Info 
{

    /** TableName=UNS_MCU_Info */
    public static final String Table_Name = "UNS_MCU_Info";

    /** AD_Table_ID=1000153 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Department.
	  * Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Department.
	  * Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID();

    /** Column name BloodType */
    public static final String COLUMNNAME_BloodType = "BloodType";

	/** Set Blood Type	  */
	public void setBloodType (String BloodType);

	/** Get Blood Type	  */
	public String getBloodType();

    /** Column name BMI */
    public static final String COLUMNNAME_BMI = "BMI";

	/** Set Body Mass Index (BMI)	  */
	public void setBMI (BigDecimal BMI);

	/** Get Body Mass Index (BMI)	  */
	public BigDecimal getBMI();

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

    /** Column name Child */
    public static final String COLUMNNAME_Child = "Child";

	/** Set Child	  */
	public void setChild (boolean Child);

	/** Get Child	  */
	public boolean isChild();

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

    /** Column name Gender */
    public static final String COLUMNNAME_Gender = "Gender";

	/** Set Gender	  */
	public void setGender (String Gender);

	/** Get Gender	  */
	public String getGender();

    /** Column name Height */
    public static final String COLUMNNAME_Height = "Height";

	/** Set Height	  */
	public void setHeight (BigDecimal Height);

	/** Get Height	  */
	public BigDecimal getHeight();

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

	/** Set Is HRD Recruiment	  */
	public void setisHRDRequitment (boolean isHRDRequitment);

	/** Get Is HRD Recruiment	  */
	public boolean isHRDRequitment();

    /** Column name MCUDate */
    public static final String COLUMNNAME_MCUDate = "MCUDate";

	/** Set MCU Date.
	  * Date of MCU
	  */
	public void setMCUDate (Timestamp MCUDate);

	/** Get MCU Date.
	  * Date of MCU
	  */
	public Timestamp getMCUDate();

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

    /** Column name PlaceOfBirth */
    public static final String COLUMNNAME_PlaceOfBirth = "PlaceOfBirth";

	/** Set Place Of Birth	  */
	public void setPlaceOfBirth (String PlaceOfBirth);

	/** Get Place Of Birth	  */
	public String getPlaceOfBirth();

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

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (String Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public String getResult();

    /** Column name ResultNotes */
    public static final String COLUMNNAME_ResultNotes = "ResultNotes";

	/** Set Result Notes.
	  * Notes to the result.
	  */
	public void setResultNotes (String ResultNotes);

	/** Get Result Notes.
	  * Notes to the result.
	  */
	public String getResultNotes();

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

    /** Column name UNS_MCU_Info_ID */
    public static final String COLUMNNAME_UNS_MCU_Info_ID = "UNS_MCU_Info_ID";

	/** Set MCU Info.
	  * UNS_MCU_Info_ID
	  */
	public void setUNS_MCU_Info_ID (int UNS_MCU_Info_ID);

	/** Get MCU Info.
	  * UNS_MCU_Info_ID
	  */
	public int getUNS_MCU_Info_ID();

    /** Column name UNS_MCU_Info_UU */
    public static final String COLUMNNAME_UNS_MCU_Info_UU = "UNS_MCU_Info_UU";

	/** Set UNS_MCU_Info_UU	  */
	public void setUNS_MCU_Info_UU (String UNS_MCU_Info_UU);

	/** Get UNS_MCU_Info_UU	  */
	public String getUNS_MCU_Info_UU();

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
