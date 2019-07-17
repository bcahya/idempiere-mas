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

/** Generated Interface for UNS_PayrollBase_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PayrollBase_Employee 
{

    /** TableName=UNS_PayrollBase_Employee */
    public static final String Table_Name = "UNS_PayrollBase_Employee";

    /** AD_Table_ID=1000106 */
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

    /** Column name GPokok */
    public static final String COLUMNNAME_GPokok = "GPokok";

	/** Set Gaji Pokok	  */
	public void setGPokok (BigDecimal GPokok);

	/** Get Gaji Pokok	  */
	public BigDecimal getGPokok();

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

    /** Column name PayrollLevel */
    public static final String COLUMNNAME_PayrollLevel = "PayrollLevel";

	/** Set PayrollLevel	  */
	public void setPayrollLevel (String PayrollLevel);

	/** Get PayrollLevel	  */
	public String getPayrollLevel();

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

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

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

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_PayrollBase_Employee_ID */
    public static final String COLUMNNAME_UNS_PayrollBase_Employee_ID = "UNS_PayrollBase_Employee_ID";

	/** Set Payroll Base Employe	  */
	public void setUNS_PayrollBase_Employee_ID (int UNS_PayrollBase_Employee_ID);

	/** Get Payroll Base Employe	  */
	public int getUNS_PayrollBase_Employee_ID();

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
}
