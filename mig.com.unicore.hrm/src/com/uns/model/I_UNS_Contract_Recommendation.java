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

/** Generated Interface for UNS_Contract_Recommendation
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Contract_Recommendation 
{

    /** TableName=UNS_Contract_Recommendation */
    public static final String Table_Name = "UNS_Contract_Recommendation";

    /** AD_Table_ID=1000069 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

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

    /** Column name DateContractEnd */
    public static final String COLUMNNAME_DateContractEnd = "DateContractEnd";

	/** Set Date Contract End	  */
	public void setDateContractEnd (Timestamp DateContractEnd);

	/** Get Date Contract End	  */
	public Timestamp getDateContractEnd();

    /** Column name DateContractStart */
    public static final String COLUMNNAME_DateContractStart = "DateContractStart";

	/** Set Date Contract Start	  */
	public void setDateContractStart (Timestamp DateContractStart);

	/** Get Date Contract Start	  */
	public Timestamp getDateContractStart();

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

    /** Column name EmploymentType */
    public static final String COLUMNNAME_EmploymentType = "EmploymentType";

	/** Set Employment Type	  */
	public void setEmploymentType (String EmploymentType);

	/** Get Employment Type	  */
	public String getEmploymentType();

    /** Column name GenerateNIK */
    public static final String COLUMNNAME_GenerateNIK = "GenerateNIK";

	/** Set Generate NIK	  */
	public void setGenerateNIK (String GenerateNIK);

	/** Get Generate NIK	  */
	public String getGenerateNIK();

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

    /** Column name IsJHTApplyed */
    public static final String COLUMNNAME_IsJHTApplyed = "IsJHTApplyed";

	/** Set JHT Applied.
	  * centang jika memiliki jaminan hari tua
	  */
	public void setIsJHTApplyed (boolean IsJHTApplyed);

	/** Get JHT Applied.
	  * centang jika memiliki jaminan hari tua
	  */
	public boolean isJHTApplyed();

    /** Column name IsJKApplyed */
    public static final String COLUMNNAME_IsJKApplyed = "IsJKApplyed";

	/** Set JK Applied.
	  * centang jika memiliki jaminan kematian
	  */
	public void setIsJKApplyed (boolean IsJKApplyed);

	/** Get JK Applied.
	  * centang jika memiliki jaminan kematian
	  */
	public boolean isJKApplyed();

    /** Column name IsJKKApplyed */
    public static final String COLUMNNAME_IsJKKApplyed = "IsJKKApplyed";

	/** Set JKK Applied.
	  * centang jika memiliki jaminan kecelakaan kerja
	  */
	public void setIsJKKApplyed (boolean IsJKKApplyed);

	/** Get JKK Applied.
	  * centang jika memiliki jaminan kecelakaan kerja
	  */
	public boolean isJKKApplyed();

    /** Column name IsJPApplied */
    public static final String COLUMNNAME_IsJPApplied = "IsJPApplied";

	/** Set JP Applied	  */
	public void setIsJPApplied (boolean IsJPApplied);

	/** Get JP Applied	  */
	public boolean isJPApplied();

    /** Column name IsJPKApplyed */
    public static final String COLUMNNAME_IsJPKApplyed = "IsJPKApplyed";

	/** Set JPK Applied.
	  * centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public void setIsJPKApplyed (boolean IsJPKApplyed);

	/** Get JPK Applied.
	  * centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public boolean isJPKApplyed();

    /** Column name IsMoveTo */
    public static final String COLUMNNAME_IsMoveTo = "IsMoveTo";

	/** Set Move To	  */
	public void setIsMoveTo (boolean IsMoveTo);

	/** Get Move To	  */
	public boolean isMoveTo();

    /** Column name IsUseGeneralPayroll */
    public static final String COLUMNNAME_IsUseGeneralPayroll = "IsUseGeneralPayroll";

	/** Set Use General Payroll	  */
	public void setIsUseGeneralPayroll (boolean IsUseGeneralPayroll);

	/** Get Use General Payroll	  */
	public boolean isUseGeneralPayroll();

    /** Column name LoadBasicPayroll */
    public static final String COLUMNNAME_LoadBasicPayroll = "LoadBasicPayroll";

	/** Set Load Basic Payroll	  */
	public void setLoadBasicPayroll (String LoadBasicPayroll);

	/** Get Load Basic Payroll	  */
	public String getLoadBasicPayroll();

    /** Column name NewAgent_ID */
    public static final String COLUMNNAME_NewAgent_ID = "NewAgent_ID";

	/** Set New Agent	  */
	public void setNewAgent_ID (int NewAgent_ID);

	/** Get New Agent	  */
	public int getNewAgent_ID();

	public org.compiere.model.I_C_BPartner getNewAgent() throws RuntimeException;

    /** Column name New_A_L1 */
    public static final String COLUMNNAME_New_A_L1 = "New_A_L1";

	/** Set New A L1	  */
	public void setNew_A_L1 (BigDecimal New_A_L1);

	/** Get New A L1	  */
	public BigDecimal getNew_A_L1();

    /** Column name New_A_L2 */
    public static final String COLUMNNAME_New_A_L2 = "New_A_L2";

	/** Set New A L2	  */
	public void setNew_A_L2 (BigDecimal New_A_L2);

	/** Get New A L2	  */
	public BigDecimal getNew_A_L2();

    /** Column name New_A_L3 */
    public static final String COLUMNNAME_New_A_L3 = "New_A_L3";

	/** Set New A L3	  */
	public void setNew_A_L3 (BigDecimal New_A_L3);

	/** Get New A L3	  */
	public BigDecimal getNew_A_L3();

    /** Column name New_A_Lain2 */
    public static final String COLUMNNAME_New_A_Lain2 = "New_A_Lain2";

	/** Set New A Lain2	  */
	public void setNew_A_Lain2 (BigDecimal New_A_Lain2);

	/** Get New A Lain2	  */
	public BigDecimal getNew_A_Lain2();

    /** Column name New_A_Premi */
    public static final String COLUMNNAME_New_A_Premi = "New_A_Premi";

	/** Set New A Premi	  */
	public void setNew_A_Premi (BigDecimal New_A_Premi);

	/** Get New A Premi	  */
	public BigDecimal getNew_A_Premi();

    /** Column name NewContractRef */
    public static final String COLUMNNAME_NewContractRef = "NewContractRef";

	/** Set New Contract Ref	  */
	public void setNewContractRef (String NewContractRef);

	/** Get New Contract Ref	  */
	public String getNewContractRef();

    /** Column name NewDept_ID */
    public static final String COLUMNNAME_NewDept_ID = "NewDept_ID";

	/** Set New Department	  */
	public void setNewDept_ID (int NewDept_ID);

	/** Get New Department	  */
	public int getNewDept_ID();

    /** Column name NewGender */
    public static final String COLUMNNAME_NewGender = "NewGender";

	/** Set New Gender	  */
	public void setNewGender (String NewGender);

	/** Get New Gender	  */
	public String getNewGender();

    /** Column name New_G_Pokok */
    public static final String COLUMNNAME_New_G_Pokok = "New_G_Pokok";

	/** Set New G Pokok	  */
	public void setNew_G_Pokok (BigDecimal New_G_Pokok);

	/** Get New G Pokok	  */
	public BigDecimal getNew_G_Pokok();

    /** Column name NewJob_ID */
    public static final String COLUMNNAME_NewJob_ID = "NewJob_ID";

	/** Set New Position	  */
	public void setNewJob_ID (int NewJob_ID);

	/** Get New Position	  */
	public int getNewJob_ID();

	public org.compiere.model.I_C_Job getNewJob() throws RuntimeException;

    /** Column name NewLeburJamBerikutnya */
    public static final String COLUMNNAME_NewLeburJamBerikutnya = "NewLeburJamBerikutnya";

	/** Set New Lembur Jam Berikutnya	  */
	public void setNewLeburJamBerikutnya (BigDecimal NewLeburJamBerikutnya);

	/** Get New Lembur Jam Berikutnya	  */
	public BigDecimal getNewLeburJamBerikutnya();

    /** Column name NewLeburJamPertama */
    public static final String COLUMNNAME_NewLeburJamPertama = "NewLeburJamPertama";

	/** Set New Lembur Jam Pertama	  */
	public void setNewLeburJamPertama (BigDecimal NewLeburJamPertama);

	/** Get New Lembur Jam Pertama	  */
	public BigDecimal getNewLeburJamPertama();

    /** Column name NewNIK */
    public static final String COLUMNNAME_NewNIK = "NewNIK";

	/** Set New NIK	  */
	public void setNewNIK (String NewNIK);

	/** Get New NIK	  */
	public String getNewNIK();

    /** Column name NewPayrollLevel */
    public static final String COLUMNNAME_NewPayrollLevel = "NewPayrollLevel";

	/** Set New Payroll Level	  */
	public void setNewPayrollLevel (String NewPayrollLevel);

	/** Get New Payroll Level	  */
	public String getNewPayrollLevel();

    /** Column name New_P_Label */
    public static final String COLUMNNAME_New_P_Label = "New_P_Label";

	/** Set New P Label	  */
	public void setNew_P_Label (BigDecimal New_P_Label);

	/** Get New P Label	  */
	public BigDecimal getNew_P_Label();

    /** Column name New_P_Lain2 */
    public static final String COLUMNNAME_New_P_Lain2 = "New_P_Lain2";

	/** Set New P Lain2	  */
	public void setNew_P_Lain2 (BigDecimal New_P_Lain2);

	/** Get New P Lain2	  */
	public BigDecimal getNew_P_Lain2();

    /** Column name New_P_Mangkir */
    public static final String COLUMNNAME_New_P_Mangkir = "New_P_Mangkir";

	/** Set New P Mangkir	  */
	public void setNew_P_Mangkir (BigDecimal New_P_Mangkir);

	/** Get New P Mangkir	  */
	public BigDecimal getNew_P_Mangkir();

    /** Column name New_P_SPTP */
    public static final String COLUMNNAME_New_P_SPTP = "New_P_SPTP";

	/** Set New P SPTP	  */
	public void setNew_P_SPTP (BigDecimal New_P_SPTP);

	/** Get New P SPTP	  */
	public BigDecimal getNew_P_SPTP();

    /** Column name NewSectionOfDept_ID */
    public static final String COLUMNNAME_NewSectionOfDept_ID = "NewSectionOfDept_ID";

	/** Set New Section Of Dept ID	  */
	public void setNewSectionOfDept_ID (int NewSectionOfDept_ID);

	/** Get New Section Of Dept ID	  */
	public int getNewSectionOfDept_ID();

	public org.compiere.model.I_C_BPartner getNewSectionOfDept() throws RuntimeException;

    /** Column name NewShift */
    public static final String COLUMNNAME_NewShift = "NewShift";

	/** Set New Shift	  */
	public void setNewShift (String NewShift);

	/** Get New Shift	  */
	public String getNewShift();

    /** Column name NewSlotType_ID */
    public static final String COLUMNNAME_NewSlotType_ID = "NewSlotType_ID";

	/** Set New Slot Type	  */
	public void setNewSlotType_ID (int NewSlotType_ID);

	/** Get New Slot Type	  */
	public int getNewSlotType_ID();

    /** Column name New_T_Jabatan */
    public static final String COLUMNNAME_New_T_Jabatan = "New_T_Jabatan";

	/** Set New T Jabatan	  */
	public void setNew_T_Jabatan (BigDecimal New_T_Jabatan);

	/** Get New T Jabatan	  */
	public BigDecimal getNew_T_Jabatan();

    /** Column name New_T_Kesejahteraan */
    public static final String COLUMNNAME_New_T_Kesejahteraan = "New_T_Kesejahteraan";

	/** Set New T Kesejahteraan	  */
	public void setNew_T_Kesejahteraan (BigDecimal New_T_Kesejahteraan);

	/** Get New T Kesejahteraan	  */
	public BigDecimal getNew_T_Kesejahteraan();

    /** Column name New_T_Lembur */
    public static final String COLUMNNAME_New_T_Lembur = "New_T_Lembur";

	/** Set New T Lembur	  */
	public void setNew_T_Lembur (BigDecimal New_T_Lembur);

	/** Get New T Lembur	  */
	public BigDecimal getNew_T_Lembur();

    /** Column name NextContractNumber */
    public static final String COLUMNNAME_NextContractNumber = "NextContractNumber";

	/** Set New Contract Number	  */
	public void setNextContractNumber (int NextContractNumber);

	/** Get New Contract Number	  */
	public int getNextContractNumber();

    /** Column name NextContractType */
    public static final String COLUMNNAME_NextContractType = "NextContractType";

	/** Set New Contract	  */
	public void setNextContractType (String NextContractType);

	/** Get New Contract	  */
	public String getNextContractType();

    /** Column name NextPayrollTerm */
    public static final String COLUMNNAME_NextPayrollTerm = "NextPayrollTerm";

	/** Set New Payroll Term	  */
	public void setNextPayrollTerm (String NextPayrollTerm);

	/** Get New Payroll Term	  */
	public String getNextPayrollTerm();

    /** Column name Notes */
    public static final String COLUMNNAME_Notes = "Notes";

	/** Set Notes	  */
	public void setNotes (String Notes);

	/** Get Notes	  */
	public String getNotes();

    /** Column name PPH21PaidByCompany */
    public static final String COLUMNNAME_PPH21PaidByCompany = "PPH21PaidByCompany";

	/** Set PPH21PaidByCompany	  */
	public void setPPH21PaidByCompany (boolean PPH21PaidByCompany);

	/** Get PPH21PaidByCompany	  */
	public boolean isPPH21PaidByCompany();

    /** Column name PrevAgent_ID */
    public static final String COLUMNNAME_PrevAgent_ID = "PrevAgent_ID";

	/** Set Prev Agent	  */
	public void setPrevAgent_ID (int PrevAgent_ID);

	/** Get Prev Agent	  */
	public int getPrevAgent_ID();

	public org.compiere.model.I_C_BPartner getPrevAgent() throws RuntimeException;

    /** Column name Prev_A_L1 */
    public static final String COLUMNNAME_Prev_A_L1 = "Prev_A_L1";

	/** Set Prev A L1	  */
	public void setPrev_A_L1 (BigDecimal Prev_A_L1);

	/** Get Prev A L1	  */
	public BigDecimal getPrev_A_L1();

    /** Column name Prev_A_L2 */
    public static final String COLUMNNAME_Prev_A_L2 = "Prev_A_L2";

	/** Set Prev A L2	  */
	public void setPrev_A_L2 (BigDecimal Prev_A_L2);

	/** Get Prev A L2	  */
	public BigDecimal getPrev_A_L2();

    /** Column name Prev_A_L3 */
    public static final String COLUMNNAME_Prev_A_L3 = "Prev_A_L3";

	/** Set Prev A L3	  */
	public void setPrev_A_L3 (BigDecimal Prev_A_L3);

	/** Get Prev A L3	  */
	public BigDecimal getPrev_A_L3();

    /** Column name Prev_A_Lain2 */
    public static final String COLUMNNAME_Prev_A_Lain2 = "Prev_A_Lain2";

	/** Set Prev A Lain2	  */
	public void setPrev_A_Lain2 (BigDecimal Prev_A_Lain2);

	/** Get Prev A Lain2	  */
	public BigDecimal getPrev_A_Lain2();

    /** Column name Prev_A_Premi */
    public static final String COLUMNNAME_Prev_A_Premi = "Prev_A_Premi";

	/** Set Prev A Premi	  */
	public void setPrev_A_Premi (BigDecimal Prev_A_Premi);

	/** Get Prev A Premi	  */
	public BigDecimal getPrev_A_Premi();

    /** Column name PrevContractNumber */
    public static final String COLUMNNAME_PrevContractNumber = "PrevContractNumber";

	/** Set Prev Contract Number	  */
	public void setPrevContractNumber (int PrevContractNumber);

	/** Get Prev Contract Number	  */
	public int getPrevContractNumber();

    /** Column name PrevContractRef */
    public static final String COLUMNNAME_PrevContractRef = "PrevContractRef";

	/** Set Prev Contract Ref	  */
	public void setPrevContractRef (String PrevContractRef);

	/** Get Prev Contract Ref	  */
	public String getPrevContractRef();

    /** Column name PrevContractType */
    public static final String COLUMNNAME_PrevContractType = "PrevContractType";

	/** Set Prev Contract	  */
	public void setPrevContractType (String PrevContractType);

	/** Get Prev Contract	  */
	public String getPrevContractType();

    /** Column name PrevDept_ID */
    public static final String COLUMNNAME_PrevDept_ID = "PrevDept_ID";

	/** Set Prev Department	  */
	public void setPrevDept_ID (int PrevDept_ID);

	/** Get Prev Department	  */
	public int getPrevDept_ID();

    /** Column name PrevGender */
    public static final String COLUMNNAME_PrevGender = "PrevGender";

	/** Set Prev Gender	  */
	public void setPrevGender (String PrevGender);

	/** Get Prev Gender	  */
	public String getPrevGender();

    /** Column name Prev_G_Pokok */
    public static final String COLUMNNAME_Prev_G_Pokok = "Prev_G_Pokok";

	/** Set Prev G Pokok	  */
	public void setPrev_G_Pokok (BigDecimal Prev_G_Pokok);

	/** Get Prev G Pokok	  */
	public BigDecimal getPrev_G_Pokok();

    /** Column name PrevJob_ID */
    public static final String COLUMNNAME_PrevJob_ID = "PrevJob_ID";

	/** Set Prev Position	  */
	public void setPrevJob_ID (int PrevJob_ID);

	/** Get Prev Position	  */
	public int getPrevJob_ID();

	public org.compiere.model.I_C_Job getPrevJob() throws RuntimeException;

    /** Column name PrevLeburJamBerikutnya */
    public static final String COLUMNNAME_PrevLeburJamBerikutnya = "PrevLeburJamBerikutnya";

	/** Set Prev Lembur Jam Berikutnya	  */
	public void setPrevLeburJamBerikutnya (BigDecimal PrevLeburJamBerikutnya);

	/** Get Prev Lembur Jam Berikutnya	  */
	public BigDecimal getPrevLeburJamBerikutnya();

    /** Column name PrevLeburJamPertama */
    public static final String COLUMNNAME_PrevLeburJamPertama = "PrevLeburJamPertama";

	/** Set Prev Lembur Jam Pertama	  */
	public void setPrevLeburJamPertama (BigDecimal PrevLeburJamPertama);

	/** Get Prev Lembur Jam Pertama	  */
	public BigDecimal getPrevLeburJamPertama();

    /** Column name PrevNIK */
    public static final String COLUMNNAME_PrevNIK = "PrevNIK";

	/** Set Prev NIK	  */
	public void setPrevNIK (String PrevNIK);

	/** Get Prev NIK	  */
	public String getPrevNIK();

    /** Column name PrevPayrollLevel */
    public static final String COLUMNNAME_PrevPayrollLevel = "PrevPayrollLevel";

	/** Set Prev Payroll Level	  */
	public void setPrevPayrollLevel (String PrevPayrollLevel);

	/** Get Prev Payroll Level	  */
	public String getPrevPayrollLevel();

    /** Column name PrevPayrollTerm */
    public static final String COLUMNNAME_PrevPayrollTerm = "PrevPayrollTerm";

	/** Set Prev Payroll Term	  */
	public void setPrevPayrollTerm (String PrevPayrollTerm);

	/** Get Prev Payroll Term	  */
	public String getPrevPayrollTerm();

    /** Column name Prev_P_Label */
    public static final String COLUMNNAME_Prev_P_Label = "Prev_P_Label";

	/** Set Prev P Label	  */
	public void setPrev_P_Label (BigDecimal Prev_P_Label);

	/** Get Prev P Label	  */
	public BigDecimal getPrev_P_Label();

    /** Column name Prev_P_Lain2 */
    public static final String COLUMNNAME_Prev_P_Lain2 = "Prev_P_Lain2";

	/** Set Prev P Lain2	  */
	public void setPrev_P_Lain2 (BigDecimal Prev_P_Lain2);

	/** Get Prev P Lain2	  */
	public BigDecimal getPrev_P_Lain2();

    /** Column name Prev_P_Mangkir */
    public static final String COLUMNNAME_Prev_P_Mangkir = "Prev_P_Mangkir";

	/** Set Prev P Mangkir	  */
	public void setPrev_P_Mangkir (BigDecimal Prev_P_Mangkir);

	/** Get Prev P Mangkir	  */
	public BigDecimal getPrev_P_Mangkir();

    /** Column name Prev_P_SPTP */
    public static final String COLUMNNAME_Prev_P_SPTP = "Prev_P_SPTP";

	/** Set Prev P SPTP	  */
	public void setPrev_P_SPTP (BigDecimal Prev_P_SPTP);

	/** Get Prev P SPTP	  */
	public BigDecimal getPrev_P_SPTP();

    /** Column name PrevSectionOfDept_ID */
    public static final String COLUMNNAME_PrevSectionOfDept_ID = "PrevSectionOfDept_ID";

	/** Set Prev Section Of Dept_ID	  */
	public void setPrevSectionOfDept_ID (int PrevSectionOfDept_ID);

	/** Get Prev Section Of Dept_ID	  */
	public int getPrevSectionOfDept_ID();

	public org.compiere.model.I_C_BPartner getPrevSectionOfDept() throws RuntimeException;

    /** Column name PrevShift */
    public static final String COLUMNNAME_PrevShift = "PrevShift";

	/** Set Prev Shift	  */
	public void setPrevShift (String PrevShift);

	/** Get Prev Shift	  */
	public String getPrevShift();

    /** Column name PrevSlotType_ID */
    public static final String COLUMNNAME_PrevSlotType_ID = "PrevSlotType_ID";

	/** Set Prev Slot Type	  */
	public void setPrevSlotType_ID (int PrevSlotType_ID);

	/** Get Prev Slot Type	  */
	public int getPrevSlotType_ID();

    /** Column name Prev_T_Jabatan */
    public static final String COLUMNNAME_Prev_T_Jabatan = "Prev_T_Jabatan";

	/** Set Prev T Jabatan	  */
	public void setPrev_T_Jabatan (BigDecimal Prev_T_Jabatan);

	/** Get Prev T Jabatan	  */
	public BigDecimal getPrev_T_Jabatan();

    /** Column name Prev_T_Kesejahteraan */
    public static final String COLUMNNAME_Prev_T_Kesejahteraan = "Prev_T_Kesejahteraan";

	/** Set Prev T Kesejahteraan	  */
	public void setPrev_T_Kesejahteraan (BigDecimal Prev_T_Kesejahteraan);

	/** Get Prev T Kesejahteraan	  */
	public BigDecimal getPrev_T_Kesejahteraan();

    /** Column name Prev_T_Lembur */
    public static final String COLUMNNAME_Prev_T_Lembur = "Prev_T_Lembur";

	/** Set Prev T Lembur	  */
	public void setPrev_T_Lembur (BigDecimal Prev_T_Lembur);

	/** Get Prev T Lembur	  */
	public BigDecimal getPrev_T_Lembur();

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

    /** Column name TotalOtherAllowances */
    public static final String COLUMNNAME_TotalOtherAllowances = "TotalOtherAllowances";

	/** Set Total Other Allowances.
	  * Total other allowances listed for employee's payroll
	  */
	public void setTotalOtherAllowances (BigDecimal TotalOtherAllowances);

	/** Get Total Other Allowances.
	  * Total other allowances listed for employee's payroll
	  */
	public BigDecimal getTotalOtherAllowances();

    /** Column name TotalOtherDeductions */
    public static final String COLUMNNAME_TotalOtherDeductions = "TotalOtherDeductions";

	/** Set Total Other Deductions.
	  * Total other deductions listed for employee's payroll
	  */
	public void setTotalOtherDeductions (BigDecimal TotalOtherDeductions);

	/** Get Total Other Deductions.
	  * Total other deductions listed for employee's payroll
	  */
	public BigDecimal getTotalOtherDeductions();

    /** Column name UNS_Contract_Evaluation_ID */
    public static final String COLUMNNAME_UNS_Contract_Evaluation_ID = "UNS_Contract_Evaluation_ID";

	/** Set Contract Evaluation	  */
	public void setUNS_Contract_Evaluation_ID (int UNS_Contract_Evaluation_ID);

	/** Get Contract Evaluation	  */
	public int getUNS_Contract_Evaluation_ID();

	public com.uns.model.I_UNS_Contract_Evaluation getUNS_Contract_Evaluation() throws RuntimeException;

    /** Column name UNS_Contract_Recommendation_ID */
    public static final String COLUMNNAME_UNS_Contract_Recommendation_ID = "UNS_Contract_Recommendation_ID";

	/** Set Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID);

	/** Get Contract	  */
	public int getUNS_Contract_Recommendation_ID();

    /** Column name UNS_Contract_Recommendation_UU */
    public static final String COLUMNNAME_UNS_Contract_Recommendation_UU = "UNS_Contract_Recommendation_UU";

	/** Set UNS_Contract_Recommendation_UU	  */
	public void setUNS_Contract_Recommendation_UU (String UNS_Contract_Recommendation_UU);

	/** Get UNS_Contract_Recommendation_UU	  */
	public String getUNS_Contract_Recommendation_UU();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

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
}
