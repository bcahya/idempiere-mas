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

/** Generated Interface for UNS_MonthlyPayroll_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_MonthlyPayroll_Employee 
{

    /** TableName=UNS_MonthlyPayroll_Employee */
    public static final String Table_Name = "UNS_MonthlyPayroll_Employee";

    /** AD_Table_ID=1000123 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_JHT */
    public static final String COLUMNNAME_A_JHT = "A_JHT";

	/** Set A.JHT	  */
	public void setA_JHT (BigDecimal A_JHT);

	/** Get A.JHT	  */
	public BigDecimal getA_JHT();

    /** Column name A_JK */
    public static final String COLUMNNAME_A_JK = "A_JK";

	/** Set A.JK	  */
	public void setA_JK (BigDecimal A_JK);

	/** Get A.JK	  */
	public BigDecimal getA_JK();

    /** Column name A_JKK */
    public static final String COLUMNNAME_A_JKK = "A_JKK";

	/** Set A.JKK	  */
	public void setA_JKK (BigDecimal A_JKK);

	/** Get A.JKK	  */
	public BigDecimal getA_JKK();

    /** Column name A_JPK */
    public static final String COLUMNNAME_A_JPK = "A_JPK";

	/** Set A.JPK	  */
	public void setA_JPK (BigDecimal A_JPK);

	/** Get A.JPK	  */
	public BigDecimal getA_JPK();

    /** Column name A_L1 */
    public static final String COLUMNNAME_A_L1 = "A_L1";

	/** Set Aditional Lembur 1	  */
	public void setA_L1 (BigDecimal A_L1);

	/** Get Aditional Lembur 1	  */
	public BigDecimal getA_L1();

    /** Column name A_L2 */
    public static final String COLUMNNAME_A_L2 = "A_L2";

	/** Set Aditional Lembur 2	  */
	public void setA_L2 (BigDecimal A_L2);

	/** Get Aditional Lembur 2	  */
	public BigDecimal getA_L2();

    /** Column name A_L3 */
    public static final String COLUMNNAME_A_L3 = "A_L3";

	/** Set Aditional Lembur 3	  */
	public void setA_L3 (BigDecimal A_L3);

	/** Get Aditional Lembur 3	  */
	public BigDecimal getA_L3();

    /** Column name A_LemburJamBerikutnya */
    public static final String COLUMNNAME_A_LemburJamBerikutnya = "A_LemburJamBerikutnya";

	/** Set A. Lembur Jam Berikutnya	  */
	public void setA_LemburJamBerikutnya (BigDecimal A_LemburJamBerikutnya);

	/** Get A. Lembur Jam Berikutnya	  */
	public BigDecimal getA_LemburJamBerikutnya();

    /** Column name A_LemburJamPertama */
    public static final String COLUMNNAME_A_LemburJamPertama = "A_LemburJamPertama";

	/** Set Aditional Lembur Jam Pertama	  */
	public void setA_LemburJamPertama (BigDecimal A_LemburJamPertama);

	/** Get Aditional Lembur Jam Pertama	  */
	public BigDecimal getA_LemburJamPertama();

    /** Column name A_Other */
    public static final String COLUMNNAME_A_Other = "A_Other";

	/** Set Aditional Other	  */
	public void setA_Other (BigDecimal A_Other);

	/** Get Aditional Other	  */
	public BigDecimal getA_Other();

    /** Column name A_Premi */
    public static final String COLUMNNAME_A_Premi = "A_Premi";

	/** Set Aditional Premi	  */
	public void setA_Premi (BigDecimal A_Premi);

	/** Get Aditional Premi	  */
	public BigDecimal getA_Premi();

    /** Column name A_Rapel */
    public static final String COLUMNNAME_A_Rapel = "A_Rapel";

	/** Set Aditional Rapel	  */
	public void setA_Rapel (BigDecimal A_Rapel);

	/** Get Aditional Rapel	  */
	public BigDecimal getA_Rapel();

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name G_T_Jabatan */
    public static final String COLUMNNAME_G_T_Jabatan = "G_T_Jabatan";

	/** Set Tunjangan Jabatan	  */
	public void setG_T_Jabatan (BigDecimal G_T_Jabatan);

	/** Get Tunjangan Jabatan	  */
	public BigDecimal getG_T_Jabatan();

    /** Column name G_T_Kesejahteraan */
    public static final String COLUMNNAME_G_T_Kesejahteraan = "G_T_Kesejahteraan";

	/** Set Tunjangan Kesejahteraan	  */
	public void setG_T_Kesejahteraan (BigDecimal G_T_Kesejahteraan);

	/** Get Tunjangan Kesejahteraan	  */
	public BigDecimal getG_T_Kesejahteraan();

    /** Column name G_T_Khusus */
    public static final String COLUMNNAME_G_T_Khusus = "G_T_Khusus";

	/** Set Tunjangan Khusus	  */
	public void setG_T_Khusus (BigDecimal G_T_Khusus);

	/** Get Tunjangan Khusus	  */
	public BigDecimal getG_T_Khusus();

    /** Column name G_T_Lembur */
    public static final String COLUMNNAME_G_T_Lembur = "G_T_Lembur";

	/** Set Tunjangan Lembur	  */
	public void setG_T_Lembur (BigDecimal G_T_Lembur);

	/** Get Tunjangan Lembur	  */
	public BigDecimal getG_T_Lembur();

    /** Column name GenerateList */
    public static final String COLUMNNAME_GenerateList = "GenerateList";

	/** Set Generate List.
	  * Generate List
	  */
	public void setGenerateList (String GenerateList);

	/** Get Generate List.
	  * Generate List
	  */
	public String getGenerateList();

    /** Column name GeneratePay */
    public static final String COLUMNNAME_GeneratePay = "GeneratePay";

	/** Set Generate Pay	  */
	public void setGeneratePay (String GeneratePay);

	/** Get Generate Pay	  */
	public String getGeneratePay();

    /** Column name GPokok */
    public static final String COLUMNNAME_GPokok = "GPokok";

	/** Set Gaji Pokok	  */
	public void setGPokok (BigDecimal GPokok);

	/** Get Gaji Pokok	  */
	public BigDecimal getGPokok();

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

    /** Column name P_JHT */
    public static final String COLUMNNAME_P_JHT = "P_JHT";

	/** Set P.JHT	  */
	public void setP_JHT (BigDecimal P_JHT);

	/** Get P.JHT	  */
	public BigDecimal getP_JHT();

    /** Column name P_JK */
    public static final String COLUMNNAME_P_JK = "P_JK";

	/** Set P.JK	  */
	public void setP_JK (BigDecimal P_JK);

	/** Get P.JK	  */
	public BigDecimal getP_JK();

    /** Column name P_JKK */
    public static final String COLUMNNAME_P_JKK = "P_JKK";

	/** Set P.JKK	  */
	public void setP_JKK (BigDecimal P_JKK);

	/** Get P.JKK	  */
	public BigDecimal getP_JKK();

    /** Column name P_JPK */
    public static final String COLUMNNAME_P_JPK = "P_JPK";

	/** Set P.JPK	  */
	public void setP_JPK (BigDecimal P_JPK);

	/** Get P.JPK	  */
	public BigDecimal getP_JPK();

    /** Column name P_Koperasi */
    public static final String COLUMNNAME_P_Koperasi = "P_Koperasi";

	/** Set P. Koperasi.
	  * Potongan Pinjaman Koperasi
	  */
	public void setP_Koperasi (BigDecimal P_Koperasi);

	/** Get P. Koperasi.
	  * Potongan Pinjaman Koperasi
	  */
	public BigDecimal getP_Koperasi();

    /** Column name P_Label */
    public static final String COLUMNNAME_P_Label = "P_Label";

	/** Set P. Label.
	  * Potongan Label
	  */
	public void setP_Label (BigDecimal P_Label);

	/** Get P. Label.
	  * Potongan Label
	  */
	public BigDecimal getP_Label();

    /** Column name P_ListrikAir */
    public static final String COLUMNNAME_P_ListrikAir = "P_ListrikAir";

	/** Set P. Listrik & Air.
	  * Potongan Listrik & Air
	  */
	public void setP_ListrikAir (BigDecimal P_ListrikAir);

	/** Get P. Listrik & Air.
	  * Potongan Listrik & Air
	  */
	public BigDecimal getP_ListrikAir();

    /** Column name P_Mangkir */
    public static final String COLUMNNAME_P_Mangkir = "P_Mangkir";

	/** Set P.Mangkir	  */
	public void setP_Mangkir (BigDecimal P_Mangkir);

	/** Get P.Mangkir	  */
	public BigDecimal getP_Mangkir();

    /** Column name P_Obat */
    public static final String COLUMNNAME_P_Obat = "P_Obat";

	/** Set P. Obat.
	  * Potongan Obat
	  */
	public void setP_Obat (BigDecimal P_Obat);

	/** Get P. Obat.
	  * Potongan Obat
	  */
	public BigDecimal getP_Obat();

    /** Column name P_Other */
    public static final String COLUMNNAME_P_Other = "P_Other";

	/** Set Potongan Other.
	  * Potongan Other
	  */
	public void setP_Other (BigDecimal P_Other);

	/** Get Potongan Other.
	  * Potongan Other
	  */
	public BigDecimal getP_Other();

    /** Column name P_PinjamanKaryawan */
    public static final String COLUMNNAME_P_PinjamanKaryawan = "P_PinjamanKaryawan";

	/** Set P. Pinjaman Karyawan.
	  * Potongan Pinjaman Karyawan
	  */
	public void setP_PinjamanKaryawan (BigDecimal P_PinjamanKaryawan);

	/** Get P. Pinjaman Karyawan.
	  * Potongan Pinjaman Karyawan
	  */
	public BigDecimal getP_PinjamanKaryawan();

    /** Column name P_SPTP */
    public static final String COLUMNNAME_P_SPTP = "P_SPTP";

	/** Set P. SPTP.
	  * Potongan Biaya SPTP
	  */
	public void setP_SPTP (BigDecimal P_SPTP);

	/** Get P. SPTP.
	  * Potongan Biaya SPTP
	  */
	public BigDecimal getP_SPTP();

    /** Column name PeriodType */
    public static final String COLUMNNAME_PeriodType = "PeriodType";

	/** Set Period Type.
	  * Period Type
	  */
	public void setPeriodType (String PeriodType);

	/** Get Period Type.
	  * Period Type
	  */
	public String getPeriodType();

    /** Column name PMakan */
    public static final String COLUMNNAME_PMakan = "PMakan";

	/** Set Potongan Makan	  */
	public void setPMakan (BigDecimal PMakan);

	/** Get Potongan Makan	  */
	public BigDecimal getPMakan();

    /** Column name PPH21 */
    public static final String COLUMNNAME_PPH21 = "PPH21";

	/** Set PPH 21	  */
	public void setPPH21 (BigDecimal PPH21);

	/** Get PPH 21	  */
	public BigDecimal getPPH21();

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

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name TakeHomePay */
    public static final String COLUMNNAME_TakeHomePay = "TakeHomePay";

	/** Set Take Home Pay.
	  * Take Home Pay
	  */
	public void setTakeHomePay (BigDecimal TakeHomePay);

	/** Get Take Home Pay.
	  * Take Home Pay
	  */
	public BigDecimal getTakeHomePay();

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

    /** Column name UNS_MonthlyPayroll_Employee_ID */
    public static final String COLUMNNAME_UNS_MonthlyPayroll_Employee_ID = "UNS_MonthlyPayroll_Employee_ID";

	/** Set Monthly Payroll Employee	  */
	public void setUNS_MonthlyPayroll_Employee_ID (int UNS_MonthlyPayroll_Employee_ID);

	/** Get Monthly Payroll Employee	  */
	public int getUNS_MonthlyPayroll_Employee_ID();

    /** Column name UNS_MonthlyPayroll_Employee_UU */
    public static final String COLUMNNAME_UNS_MonthlyPayroll_Employee_UU = "UNS_MonthlyPayroll_Employee_UU";

	/** Set UNS_MonthlyPayroll_Employee_UU	  */
	public void setUNS_MonthlyPayroll_Employee_UU (String UNS_MonthlyPayroll_Employee_UU);

	/** Get UNS_MonthlyPayroll_Employee_UU	  */
	public String getUNS_MonthlyPayroll_Employee_UU();

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
