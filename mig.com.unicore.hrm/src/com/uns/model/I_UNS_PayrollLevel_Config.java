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

/** Generated Interface for UNS_PayrollLevel_Config
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PayrollLevel_Config 
{

    /** TableName=UNS_PayrollLevel_Config */
    public static final String Table_Name = "UNS_PayrollLevel_Config";

    /** AD_Table_ID=1000102 */
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

    /** Column name IsGPBaseOnPresence */
    public static final String COLUMNNAME_IsGPBaseOnPresence = "IsGPBaseOnPresence";

	/** Set Basic Salary Base on Presence.
	  * To indicate if basic salary is deducted for any number of employee's absence
	  */
	public void setIsGPBaseOnPresence (boolean IsGPBaseOnPresence);

	/** Get Basic Salary Base on Presence.
	  * To indicate if basic salary is deducted for any number of employee's absence
	  */
	public boolean isGPBaseOnPresence();

    /** Column name IsTJabatanBaseOnPresence */
    public static final String COLUMNNAME_IsTJabatanBaseOnPresence = "IsTJabatanBaseOnPresence";

	/** Set T Jabatan Base On Presence.
	  * Tunjangan Jabatan Base On Presence ?
	  */
	public void setIsTJabatanBaseOnPresence (boolean IsTJabatanBaseOnPresence);

	/** Get T Jabatan Base On Presence.
	  * Tunjangan Jabatan Base On Presence ?
	  */
	public boolean isTJabatanBaseOnPresence();

    /** Column name IsTKesejahteraanBaseOnPresence */
    public static final String COLUMNNAME_IsTKesejahteraanBaseOnPresence = "IsTKesejahteraanBaseOnPresence";

	/** Set T Kesejahteraan Base On Presence.
	  * Tunjangan Kesejahteraan Base On Presence ?
	  */
	public void setIsTKesejahteraanBaseOnPresence (boolean IsTKesejahteraanBaseOnPresence);

	/** Get T Kesejahteraan Base On Presence.
	  * Tunjangan Kesejahteraan Base On Presence ?
	  */
	public boolean isTKesejahteraanBaseOnPresence();

    /** Column name IsTKhususBaseOnPresence */
    public static final String COLUMNNAME_IsTKhususBaseOnPresence = "IsTKhususBaseOnPresence";

	/** Set T Khusus Base On Presence.
	  * Tunjangan Khusus Base On Presence ?
	  */
	public void setIsTKhususBaseOnPresence (boolean IsTKhususBaseOnPresence);

	/** Get T Khusus Base On Presence.
	  * Tunjangan Khusus Base On Presence ?
	  */
	public boolean isTKhususBaseOnPresence();

    /** Column name IsTLemburBaseOnPresence */
    public static final String COLUMNNAME_IsTLemburBaseOnPresence = "IsTLemburBaseOnPresence";

	/** Set T Lembur Base On Presence.
	  * Tunjangan Khusus Base On Presence ?
	  */
	public void setIsTLemburBaseOnPresence (boolean IsTLemburBaseOnPresence);

	/** Get T Lembur Base On Presence.
	  * Tunjangan Khusus Base On Presence ?
	  */
	public boolean isTLemburBaseOnPresence();

    /** Column name IsUMKBase */
    public static final String COLUMNNAME_IsUMKBase = "IsUMKBase";

	/** Set Base On UMK	  */
	public void setIsUMKBase (boolean IsUMKBase);

	/** Get Base On UMK	  */
	public boolean isUMKBase();

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

    /** Column name Min_A_L1 */
    public static final String COLUMNNAME_Min_A_L1 = "Min_A_L1";

	/** Set Min A.L1	  */
	public void setMin_A_L1 (BigDecimal Min_A_L1);

	/** Get Min A.L1	  */
	public BigDecimal getMin_A_L1();

    /** Column name Min_A_L2 */
    public static final String COLUMNNAME_Min_A_L2 = "Min_A_L2";

	/** Set Min A.L2	  */
	public void setMin_A_L2 (BigDecimal Min_A_L2);

	/** Get Min A.L2	  */
	public BigDecimal getMin_A_L2();

    /** Column name Min_A_L3 */
    public static final String COLUMNNAME_Min_A_L3 = "Min_A_L3";

	/** Set Min A.L3	  */
	public void setMin_A_L3 (BigDecimal Min_A_L3);

	/** Get Min A.L3	  */
	public BigDecimal getMin_A_L3();

    /** Column name Min_A_Lain2 */
    public static final String COLUMNNAME_Min_A_Lain2 = "Min_A_Lain2";

	/** Set Min A.Lain2	  */
	public void setMin_A_Lain2 (BigDecimal Min_A_Lain2);

	/** Get Min A.Lain2	  */
	public BigDecimal getMin_A_Lain2();

    /** Column name Min_A_Lembur */
    public static final String COLUMNNAME_Min_A_Lembur = "Min_A_Lembur";

	/** Set Min A.Lembur Jam Berikutnya	  */
	public void setMin_A_Lembur (BigDecimal Min_A_Lembur);

	/** Get Min A.Lembur Jam Berikutnya	  */
	public BigDecimal getMin_A_Lembur();

    /** Column name Min_A_LemburJamPertama */
    public static final String COLUMNNAME_Min_A_LemburJamPertama = "Min_A_LemburJamPertama";

	/** Set Min A.Lembur Jam Pertama	  */
	public void setMin_A_LemburJamPertama (BigDecimal Min_A_LemburJamPertama);

	/** Get Min A.Lembur Jam Pertama	  */
	public BigDecimal getMin_A_LemburJamPertama();

    /** Column name Min_A_Premi */
    public static final String COLUMNNAME_Min_A_Premi = "Min_A_Premi";

	/** Set Min A.Premi	  */
	public void setMin_A_Premi (BigDecimal Min_A_Premi);

	/** Get Min A.Premi	  */
	public BigDecimal getMin_A_Premi();

    /** Column name Min_G_Pokok */
    public static final String COLUMNNAME_Min_G_Pokok = "Min_G_Pokok";

	/** Set Min G.Pokok	  */
	public void setMin_G_Pokok (BigDecimal Min_G_Pokok);

	/** Get Min G.Pokok	  */
	public BigDecimal getMin_G_Pokok();

    /** Column name Min_G_T_Jabatan */
    public static final String COLUMNNAME_Min_G_T_Jabatan = "Min_G_T_Jabatan";

	/** Set Min G.T.Jabatan	  */
	public void setMin_G_T_Jabatan (BigDecimal Min_G_T_Jabatan);

	/** Get Min G.T.Jabatan	  */
	public BigDecimal getMin_G_T_Jabatan();

    /** Column name Min_G_T_Kesejahteraan */
    public static final String COLUMNNAME_Min_G_T_Kesejahteraan = "Min_G_T_Kesejahteraan";

	/** Set Min G.T.Kesejahteraan	  */
	public void setMin_G_T_Kesejahteraan (BigDecimal Min_G_T_Kesejahteraan);

	/** Get Min G.T.Kesejahteraan	  */
	public BigDecimal getMin_G_T_Kesejahteraan();

    /** Column name Min_G_T_Khusus */
    public static final String COLUMNNAME_Min_G_T_Khusus = "Min_G_T_Khusus";

	/** Set Min G.T.Khusus	  */
	public void setMin_G_T_Khusus (BigDecimal Min_G_T_Khusus);

	/** Get Min G.T.Khusus	  */
	public BigDecimal getMin_G_T_Khusus();

    /** Column name Min_G_T_Lembur */
    public static final String COLUMNNAME_Min_G_T_Lembur = "Min_G_T_Lembur";

	/** Set Min G.T.Lembur	  */
	public void setMin_G_T_Lembur (BigDecimal Min_G_T_Lembur);

	/** Get Min G.T.Lembur	  */
	public BigDecimal getMin_G_T_Lembur();

    /** Column name PayrollLevel */
    public static final String COLUMNNAME_PayrollLevel = "PayrollLevel";

	/** Set PayrollLevel	  */
	public void setPayrollLevel (String PayrollLevel);

	/** Get PayrollLevel	  */
	public String getPayrollLevel();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name P_Lain2 */
    public static final String COLUMNNAME_P_Lain2 = "P_Lain2";

	/** Set P.Lain2	  */
	public void setP_Lain2 (BigDecimal P_Lain2);

	/** Get P.Lain2	  */
	public BigDecimal getP_Lain2();

    /** Column name P_Mangkir */
    public static final String COLUMNNAME_P_Mangkir = "P_Mangkir";

	/** Set P.Mangkir	  */
	public void setP_Mangkir (BigDecimal P_Mangkir);

	/** Get P.Mangkir	  */
	public BigDecimal getP_Mangkir();

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

    /** Column name UMK */
    public static final String COLUMNNAME_UMK = "UMK";

	/** Set UMK.
	  * Upah Minimum Kota
	  */
	public void setUMK (BigDecimal UMK);

	/** Get UMK.
	  * Upah Minimum Kota
	  */
	public BigDecimal getUMK();

    /** Column name UMP */
    public static final String COLUMNNAME_UMP = "UMP";

	/** Set UMP	  */
	public void setUMP (BigDecimal UMP);

	/** Get UMP	  */
	public BigDecimal getUMP();

    /** Column name UNS_PayrollConfiguration_ID */
    public static final String COLUMNNAME_UNS_PayrollConfiguration_ID = "UNS_PayrollConfiguration_ID";

	/** Set Payroll Configuration	  */
	public void setUNS_PayrollConfiguration_ID (int UNS_PayrollConfiguration_ID);

	/** Get Payroll Configuration	  */
	public int getUNS_PayrollConfiguration_ID();

	public com.uns.model.I_UNS_PayrollConfiguration getUNS_PayrollConfiguration() throws RuntimeException;

    /** Column name UNS_PayrollLevel_Config_ID */
    public static final String COLUMNNAME_UNS_PayrollLevel_Config_ID = "UNS_PayrollLevel_Config_ID";

	/** Set PayrolLevel Config	  */
	public void setUNS_PayrollLevel_Config_ID (int UNS_PayrollLevel_Config_ID);

	/** Get PayrolLevel Config	  */
	public int getUNS_PayrollLevel_Config_ID();

    /** Column name UNS_PayrollLevel_Config_UU */
    public static final String COLUMNNAME_UNS_PayrollLevel_Config_UU = "UNS_PayrollLevel_Config_UU";

	/** Set UNS_PayrolLevel_Config_UU	  */
	public void setUNS_PayrollLevel_Config_UU (String UNS_PayrollLevel_Config_UU);

	/** Get UNS_PayrolLevel_Config_UU	  */
	public String getUNS_PayrollLevel_Config_UU();

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

    /** Column name UtumDanTelekomunikasi */
    public static final String COLUMNNAME_UtumDanTelekomunikasi = "UtumDanTelekomunikasi";

	/** Set Utum Dan Telekomunikasi	  */
	public void setUtumDanTelekomunikasi (BigDecimal UtumDanTelekomunikasi);

	/** Get Utum Dan Telekomunikasi	  */
	public BigDecimal getUtumDanTelekomunikasi();
}
