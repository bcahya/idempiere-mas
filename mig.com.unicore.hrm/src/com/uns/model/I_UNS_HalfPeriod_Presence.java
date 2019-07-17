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

/** Generated Interface for UNS_HalfPeriod_Presence
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_HalfPeriod_Presence 
{

    /** TableName=UNS_HalfPeriod_Presence */
    public static final String Table_Name = "UNS_HalfPeriod_Presence";

    /** AD_Table_ID=1000047 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name CreateCriteriaResult */
    public static final String COLUMNNAME_CreateCriteriaResult = "CreateCriteriaResult";

	/** Set Create Criteria Result	  */
	public void setCreateCriteriaResult (String CreateCriteriaResult);

	/** Get Create Criteria Result	  */
	public String getCreateCriteriaResult();

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

    /** Column name HalfPeriodNo */
    public static final String COLUMNNAME_HalfPeriodNo = "HalfPeriodNo";

	/** Set Half Period No	  */
	public void setHalfPeriodNo (String HalfPeriodNo);

	/** Get Half Period No	  */
	public String getHalfPeriodNo();

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

    /** Column name IsSupervised */
    public static final String COLUMNNAME_IsSupervised = "IsSupervised";

	/** Set Supervised	  */
	public void setIsSupervised (boolean IsSupervised);

	/** Get Supervised	  */
	public boolean isSupervised();

    /** Column name IsSwitchTodaily */
    public static final String COLUMNNAME_IsSwitchTodaily = "IsSwitchTodaily";

	/** Set Switched To Daily.
	  * If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public void setIsSwitchTodaily (boolean IsSwitchTodaily);

	/** Get Switched To Daily.
	  * If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public boolean isSwitchTodaily();

    /** Column name PPH21 */
    public static final String COLUMNNAME_PPH21 = "PPH21";

	/** Set PPH 21	  */
	public void setPPH21 (BigDecimal PPH21);

	/** Get PPH 21	  */
	public BigDecimal getPPH21();

    /** Column name P_AlatKerja */
    public static final String COLUMNNAME_P_AlatKerja = "P_AlatKerja";

	/** Set P. Alat Kerja.
	  * Potongan Alat Kerja
	  */
	public void setP_AlatKerja (BigDecimal P_AlatKerja);

	/** Get P. Alat Kerja.
	  * Potongan Alat Kerja
	  */
	public BigDecimal getP_AlatKerja();

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

    /** Column name PaidDaily */
    public static final String COLUMNNAME_PaidDaily = "PaidDaily";

	/** Set Paid Daily	  */
	public void setPaidDaily (BigDecimal PaidDaily);

	/** Get Paid Daily	  */
	public BigDecimal getPaidDaily();

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

    /** Column name PremiKerajinanReceivable */
    public static final String COLUMNNAME_PremiKerajinanReceivable = "PremiKerajinanReceivable";

	/** Set Premi Kerajinan Receivable	  */
	public void setPremiKerajinanReceivable (BigDecimal PremiKerajinanReceivable);

	/** Get Premi Kerajinan Receivable	  */
	public BigDecimal getPremiKerajinanReceivable();

    /** Column name PremiTargetReceivable */
    public static final String COLUMNNAME_PremiTargetReceivable = "PremiTargetReceivable";

	/** Set Premi Target Receivable	  */
	public void setPremiTargetReceivable (BigDecimal PremiTargetReceivable);

	/** Get Premi Target Receivable	  */
	public BigDecimal getPremiTargetReceivable();

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

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

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

    /** Column name THR */
    public static final String COLUMNNAME_THR = "THR";

	/** Set THR.
	  * Tunjangan Hari Raya
	  */
	public void setTHR (BigDecimal THR);

	/** Get THR.
	  * Tunjangan Hari Raya
	  */
	public BigDecimal getTHR();

    /** Column name TotalMangkir */
    public static final String COLUMNNAME_TotalMangkir = "TotalMangkir";

	/** Set Total Mangkir	  */
	public void setTotalMangkir (BigDecimal TotalMangkir);

	/** Get Total Mangkir	  */
	public BigDecimal getTotalMangkir();

    /** Column name TotalNonPayableAbsence */
    public static final String COLUMNNAME_TotalNonPayableAbsence = "TotalNonPayableAbsence";

	/** Set Total Non Payable Absence.
	  * The number of employee absences with non-payable permission
	  */
	public void setTotalNonPayableAbsence (BigDecimal TotalNonPayableAbsence);

	/** Get Total Non Payable Absence.
	  * The number of employee absences with non-payable permission
	  */
	public BigDecimal getTotalNonPayableAbsence();

    /** Column name TotalOvertime */
    public static final String COLUMNNAME_TotalOvertime = "TotalOvertime";

	/** Set Total Overtime.
	  * Total emloyee's overtime in month
	  */
	public void setTotalOvertime (BigDecimal TotalOvertime);

	/** Get Total Overtime.
	  * Total emloyee's overtime in month
	  */
	public BigDecimal getTotalOvertime();

    /** Column name TotalPayableAbsence */
    public static final String COLUMNNAME_TotalPayableAbsence = "TotalPayableAbsence";

	/** Set Total Payable Absence.
	  * The number of employee absences with payable permission
	  */
	public void setTotalPayableAbsence (BigDecimal TotalPayableAbsence);

	/** Get Total Payable Absence.
	  * The number of employee absences with payable permission
	  */
	public BigDecimal getTotalPayableAbsence();

    /** Column name TotalPresence */
    public static final String COLUMNNAME_TotalPresence = "TotalPresence";

	/** Set Total Presence	  */
	public void setTotalPresence (BigDecimal TotalPresence);

	/** Get Total Presence	  */
	public BigDecimal getTotalPresence();

    /** Column name TotalReceivableAmt */
    public static final String COLUMNNAME_TotalReceivableAmt = "TotalReceivableAmt";

	/** Set Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt);

	/** Get Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt();

    /** Column name TotalSK */
    public static final String COLUMNNAME_TotalSK = "TotalSK";

	/** Set Total SK	  */
	public void setTotalSK (BigDecimal TotalSK);

	/** Get Total SK	  */
	public BigDecimal getTotalSK();

    /** Column name TotalSKK */
    public static final String COLUMNNAME_TotalSKK = "TotalSKK";

	/** Set Total SKK	  */
	public void setTotalSKK (BigDecimal TotalSKK);

	/** Get Total SKK	  */
	public BigDecimal getTotalSKK();

    /** Column name TotalSubsidies */
    public static final String COLUMNNAME_TotalSubsidies = "TotalSubsidies";

	/** Set Total Subsidies Amt.
	  * Total subsidies amount
	  */
	public void setTotalSubsidies (BigDecimal TotalSubsidies);

	/** Get Total Subsidies Amt.
	  * Total subsidies amount
	  */
	public BigDecimal getTotalSubsidies();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_HalfPeriod_Presence_ID */
    public static final String COLUMNNAME_UNS_HalfPeriod_Presence_ID = "UNS_HalfPeriod_Presence_ID";

	/** Set Half Period Presence	  */
	public void setUNS_HalfPeriod_Presence_ID (int UNS_HalfPeriod_Presence_ID);

	/** Get Half Period Presence	  */
	public int getUNS_HalfPeriod_Presence_ID();

    /** Column name UNS_HalfPeriod_Presence_UU */
    public static final String COLUMNNAME_UNS_HalfPeriod_Presence_UU = "UNS_HalfPeriod_Presence_UU";

	/** Set UNS_HalfPeriod_Presence_UU	  */
	public void setUNS_HalfPeriod_Presence_UU (String UNS_HalfPeriod_Presence_UU);

	/** Get UNS_HalfPeriod_Presence_UU	  */
	public String getUNS_HalfPeriod_Presence_UU();

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

    /** Column name UpdateHalfPeriodPresence */
    public static final String COLUMNNAME_UpdateHalfPeriodPresence = "UpdateHalfPeriodPresence";

	/** Set Generate Update.
	  * Generate Update Half Period Presence By daily Worker Presence
	  */
	public void setUpdateHalfPeriodPresence (String UpdateHalfPeriodPresence);

	/** Get Generate Update.
	  * Generate Update Half Period Presence By daily Worker Presence
	  */
	public String getUpdateHalfPeriodPresence();

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
