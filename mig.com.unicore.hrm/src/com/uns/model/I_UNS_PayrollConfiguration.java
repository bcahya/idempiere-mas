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

/** Generated Interface for UNS_PayrollConfiguration
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PayrollConfiguration 
{

    /** TableName=UNS_PayrollConfiguration */
    public static final String Table_Name = "UNS_PayrollConfiguration";

    /** AD_Table_ID=1000101 */
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

    /** Column name A_L1_Acct_ID */
    public static final String COLUMNNAME_A_L1_Acct_ID = "A_L1_Acct_ID";

	/** Set Additional L1 Acct.
	  * Aditional Lembur1 Acct
	  */
	public void setA_L1_Acct_ID (int A_L1_Acct_ID);

	/** Get Additional L1 Acct.
	  * Aditional Lembur1 Acct
	  */
	public int getA_L1_Acct_ID();

	public I_C_ValidCombination getA_L1_Acct() throws RuntimeException;

    /** Column name A_L2_Acct_ID */
    public static final String COLUMNNAME_A_L2_Acct_ID = "A_L2_Acct_ID";

	/** Set Additional L2 Acct.
	  * Aditional Lembur2 Acct
	  */
	public void setA_L2_Acct_ID (int A_L2_Acct_ID);

	/** Get Additional L2 Acct.
	  * Aditional Lembur2 Acct
	  */
	public int getA_L2_Acct_ID();

	public I_C_ValidCombination getA_L2_Acct() throws RuntimeException;

    /** Column name A_L3_Acct_ID */
    public static final String COLUMNNAME_A_L3_Acct_ID = "A_L3_Acct_ID";

	/** Set Additional L3 Acct.
	  * Aditional Lembur3 Acct
	  */
	public void setA_L3_Acct_ID (int A_L3_Acct_ID);

	/** Get Additional L3 Acct.
	  * Aditional Lembur3 Acct
	  */
	public int getA_L3_Acct_ID();

	public I_C_ValidCombination getA_L3_Acct() throws RuntimeException;

    /** Column name A_Lembur_Acct_ID */
    public static final String COLUMNNAME_A_Lembur_Acct_ID = "A_Lembur_Acct_ID";

	/** Set Additional Lembur Acct	  */
	public void setA_Lembur_Acct_ID (int A_Lembur_Acct_ID);

	/** Get Additional Lembur Acct	  */
	public int getA_Lembur_Acct_ID();

	public I_C_ValidCombination getA_Lembur_Acct() throws RuntimeException;

    /** Column name A_OtherAcct_ID */
    public static final String COLUMNNAME_A_OtherAcct_ID = "A_OtherAcct_ID";

	/** Set Additional Other Acct	  */
	public void setA_OtherAcct_ID (int A_OtherAcct_ID);

	/** Get Additional Other Acct	  */
	public int getA_OtherAcct_ID();

	public I_C_ValidCombination getA_OtherAcct() throws RuntimeException;

    /** Column name A_PremiAcct_ID */
    public static final String COLUMNNAME_A_PremiAcct_ID = "A_PremiAcct_ID";

	/** Set Additional Premi Acct	  */
	public void setA_PremiAcct_ID (int A_PremiAcct_ID);

	/** Get Additional Premi Acct	  */
	public int getA_PremiAcct_ID();

	public I_C_ValidCombination getA_PremiAcct() throws RuntimeException;

    /** Column name A_RapelAcct_ID */
    public static final String COLUMNNAME_A_RapelAcct_ID = "A_RapelAcct_ID";

	/** Set Additional Rapel Acct	  */
	public void setA_RapelAcct_ID (int A_RapelAcct_ID);

	/** Get Additional Rapel Acct	  */
	public int getA_RapelAcct_ID();

	public I_C_ValidCombination getA_RapelAcct() throws RuntimeException;

    /** Column name BiayaAir */
    public static final String COLUMNNAME_BiayaAir = "BiayaAir";

	/** Set Biaya Air / Liter	  */
	public void setBiayaAir (BigDecimal BiayaAir);

	/** Get Biaya Air / Liter	  */
	public BigDecimal getBiayaAir();

    /** Column name BiayaGajiBulananAcct_ID */
    public static final String COLUMNNAME_BiayaGajiBulananAcct_ID = "BiayaGajiBulananAcct_ID";

	/** Set Biaya Gaji Bulanan Acct	  */
	public void setBiayaGajiBulananAcct_ID (int BiayaGajiBulananAcct_ID);

	/** Get Biaya Gaji Bulanan Acct	  */
	public int getBiayaGajiBulananAcct_ID();

	public I_C_ValidCombination getBiayaGajiBulananAcct() throws RuntimeException;

    /** Column name BiayaGajiHarianAcct_ID */
    public static final String COLUMNNAME_BiayaGajiHarianAcct_ID = "BiayaGajiHarianAcct_ID";

	/** Set Biaya Gaji Harian Acct	  */
	public void setBiayaGajiHarianAcct_ID (int BiayaGajiHarianAcct_ID);

	/** Get Biaya Gaji Harian Acct	  */
	public int getBiayaGajiHarianAcct_ID();

	public I_C_ValidCombination getBiayaGajiHarianAcct() throws RuntimeException;

    /** Column name BiayaJabatan */
    public static final String COLUMNNAME_BiayaJabatan = "BiayaJabatan";

	/** Set Biaya Jabatan (%).
	  * Biaya Jabatan In Percent
	  */
	public void setBiayaJabatan (BigDecimal BiayaJabatan);

	/** Get Biaya Jabatan (%).
	  * Biaya Jabatan In Percent
	  */
	public BigDecimal getBiayaJabatan();

    /** Column name BiayaListrik */
    public static final String COLUMNNAME_BiayaListrik = "BiayaListrik";

	/** Set Biaya Listrik / KWH	  */
	public void setBiayaListrik (BigDecimal BiayaListrik);

	/** Get Biaya Listrik / KWH	  */
	public BigDecimal getBiayaListrik();

    /** Column name B_JHTAcct_ID */
    public static final String COLUMNNAME_B_JHTAcct_ID = "B_JHTAcct_ID";

	/** Set Biaya JHT Acct	  */
	public void setB_JHTAcct_ID (int B_JHTAcct_ID);

	/** Get Biaya JHT Acct	  */
	public int getB_JHTAcct_ID();

	public I_C_ValidCombination getB_JHTAcct() throws RuntimeException;

    /** Column name B_JKAcct_ID */
    public static final String COLUMNNAME_B_JKAcct_ID = "B_JKAcct_ID";

	/** Set Biaya JK Acct	  */
	public void setB_JKAcct_ID (int B_JKAcct_ID);

	/** Get Biaya JK Acct	  */
	public int getB_JKAcct_ID();

	public I_C_ValidCombination getB_JKAcct() throws RuntimeException;

    /** Column name B_JKKAcct_ID */
    public static final String COLUMNNAME_B_JKKAcct_ID = "B_JKKAcct_ID";

	/** Set Biaya JKK Acct	  */
	public void setB_JKKAcct_ID (int B_JKKAcct_ID);

	/** Get Biaya JKK Acct	  */
	public int getB_JKKAcct_ID();

	public I_C_ValidCombination getB_JKKAcct() throws RuntimeException;

    /** Column name B_JPAcct_ID */
    public static final String COLUMNNAME_B_JPAcct_ID = "B_JPAcct_ID";

	/** Set Biaya JP Acct	  */
	public void setB_JPAcct_ID (int B_JPAcct_ID);

	/** Get Biaya JP Acct	  */
	public int getB_JPAcct_ID();

	public I_C_ValidCombination getB_JPAcct() throws RuntimeException;

    /** Column name B_JPKAcct_ID */
    public static final String COLUMNNAME_B_JPKAcct_ID = "B_JPKAcct_ID";

	/** Set Biaya JPK Acct	  */
	public void setB_JPKAcct_ID (int B_JPKAcct_ID);

	/** Get Biaya JPK Acct	  */
	public int getB_JPKAcct_ID();

	public I_C_ValidCombination getB_JPKAcct() throws RuntimeException;

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

    /** Column name E_Medical_Expense_Acct */
    public static final String COLUMNNAME_E_Medical_Expense_Acct = "E_Medical_Expense_Acct";

	/** Set Medical Expense Acct.
	  * The account to post medical expense.
	  */
	public void setE_Medical_Expense_Acct (int E_Medical_Expense_Acct);

	/** Get Medical Expense Acct.
	  * The account to post medical expense.
	  */
	public int getE_Medical_Expense_Acct();

	public I_C_ValidCombination getE_Medical_Expense_A() throws RuntimeException;

    /** Column name H_JHT_Acct_ID */
    public static final String COLUMNNAME_H_JHT_Acct_ID = "H_JHT_Acct_ID";

	/** Set Hutang JHT Acct.
	  * Jaminan Hari Tua Account
	  */
	public void setH_JHT_Acct_ID (int H_JHT_Acct_ID);

	/** Get Hutang JHT Acct.
	  * Jaminan Hari Tua Account
	  */
	public int getH_JHT_Acct_ID();

	public I_C_ValidCombination getH_JHT_Acct() throws RuntimeException;

    /** Column name H_JK_Acct_ID */
    public static final String COLUMNNAME_H_JK_Acct_ID = "H_JK_Acct_ID";

	/** Set Hutang JK Acct.
	  * Jaminan Kematian Account
	  */
	public void setH_JK_Acct_ID (int H_JK_Acct_ID);

	/** Get Hutang JK Acct.
	  * Jaminan Kematian Account
	  */
	public int getH_JK_Acct_ID();

	public I_C_ValidCombination getH_JK_Acct() throws RuntimeException;

    /** Column name H_JKK_Acct_ID */
    public static final String COLUMNNAME_H_JKK_Acct_ID = "H_JKK_Acct_ID";

	/** Set Hutang JKK Acct.
	  * Jaminan Kecelakaan Kerha Account
	  */
	public void setH_JKK_Acct_ID (int H_JKK_Acct_ID);

	/** Get Hutang JKK Acct.
	  * Jaminan Kecelakaan Kerha Account
	  */
	public int getH_JKK_Acct_ID();

	public I_C_ValidCombination getH_JKK_Acct() throws RuntimeException;

    /** Column name H_JPAcct_ID */
    public static final String COLUMNNAME_H_JPAcct_ID = "H_JPAcct_ID";

	/** Set Hutang JP Acct	  */
	public void setH_JPAcct_ID (int H_JPAcct_ID);

	/** Get Hutang JP Acct	  */
	public int getH_JPAcct_ID();

	public I_C_ValidCombination getH_JPAcct() throws RuntimeException;

    /** Column name H_JPK_Acct_ID */
    public static final String COLUMNNAME_H_JPK_Acct_ID = "H_JPK_Acct_ID";

	/** Set Hutang JPK Acct.
	  * Jaminan Pemeliharaan Kesehatan Account
	  */
	public void setH_JPK_Acct_ID (int H_JPK_Acct_ID);

	/** Get Hutang JPK Acct.
	  * Jaminan Pemeliharaan Kesehatan Account
	  */
	public int getH_JPK_Acct_ID();

	public I_C_ValidCombination getH_JPK_Acct() throws RuntimeException;

    /** Column name HolidayWorkHours */
    public static final String COLUMNNAME_HolidayWorkHours = "HolidayWorkHours";

	/** Set Holiday Work Hours	  */
	public void setHolidayWorkHours (BigDecimal HolidayWorkHours);

	/** Get Holiday Work Hours	  */
	public BigDecimal getHolidayWorkHours();

    /** Column name HolidayWorkHoursShift */
    public static final String COLUMNNAME_HolidayWorkHoursShift = "HolidayWorkHoursShift";

	/** Set Holiday Work Hours (S)	  */
	public void setHolidayWorkHoursShift (BigDecimal HolidayWorkHoursShift);

	/** Get Holiday Work Hours (S)	  */
	public BigDecimal getHolidayWorkHoursShift();

    /** Column name HutangGajiBulananAcct_ID */
    public static final String COLUMNNAME_HutangGajiBulananAcct_ID = "HutangGajiBulananAcct_ID";

	/** Set Hutang Gaji Bulanan Acct	  */
	public void setHutangGajiBulananAcct_ID (int HutangGajiBulananAcct_ID);

	/** Get Hutang Gaji Bulanan Acct	  */
	public int getHutangGajiBulananAcct_ID();

	public I_C_ValidCombination getHutangGajiBulananAcct() throws RuntimeException;

    /** Column name HutangGajiHarianAcct_ID */
    public static final String COLUMNNAME_HutangGajiHarianAcct_ID = "HutangGajiHarianAcct_ID";

	/** Set Hutang Gaji Harian Acct	  */
	public void setHutangGajiHarianAcct_ID (int HutangGajiHarianAcct_ID);

	/** Get Hutang Gaji Harian Acct	  */
	public int getHutangGajiHarianAcct_ID();

	public I_C_ValidCombination getHutangGajiHarianAcct() throws RuntimeException;

    /** Column name HutangUpahBuruhDirectAcct_ID */
    public static final String COLUMNNAME_HutangUpahBuruhDirectAcct_ID = "HutangUpahBuruhDirectAcct_ID";

	/** Set Hutang Upah Buruh Direct Acct	  */
	public void setHutangUpahBuruhDirectAcct_ID (int HutangUpahBuruhDirectAcct_ID);

	/** Get Hutang Upah Buruh Direct Acct	  */
	public int getHutangUpahBuruhDirectAcct_ID();

	public I_C_ValidCombination getHutangUpahBuruhDirectAcct() throws RuntimeException;

    /** Column name HutangUpahBuruhInDirectAcct_ID */
    public static final String COLUMNNAME_HutangUpahBuruhInDirectAcct_ID = "HutangUpahBuruhInDirectAcct_ID";

	/** Set Hutang Upah Buruh Indirect Acct	  */
	public void setHutangUpahBuruhInDirectAcct_ID (int HutangUpahBuruhInDirectAcct_ID);

	/** Get Hutang Upah Buruh Indirect Acct	  */
	public int getHutangUpahBuruhInDirectAcct_ID();

	public I_C_ValidCombination getHutangUpahBuruhInDirectAcct() throws RuntimeException;

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

    /** Column name IsCalculatePPH21Buruh */
    public static final String COLUMNNAME_IsCalculatePPH21Buruh = "IsCalculatePPH21Buruh";

	/** Set Calculate PPH21 Buruh	  */
	public void setIsCalculatePPH21Buruh (boolean IsCalculatePPH21Buruh);

	/** Get Calculate PPH21 Buruh	  */
	public boolean isCalculatePPH21Buruh();

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

    /** Column name JHTBasicCalculation */
    public static final String COLUMNNAME_JHTBasicCalculation = "JHTBasicCalculation";

	/** Set JHT Basic Calculation	  */
	public void setJHTBasicCalculation (String JHTBasicCalculation);

	/** Get JHT Basic Calculation	  */
	public String getJHTBasicCalculation();

    /** Column name JHTBorongan */
    public static final String COLUMNNAME_JHTBorongan = "JHTBorongan";

	/** Set JHT Borongan.
	  * Jaminan Hari Tua Borongan
	  */
	public void setJHTBorongan (BigDecimal JHTBorongan);

	/** Get JHT Borongan.
	  * Jaminan Hari Tua Borongan
	  */
	public BigDecimal getJHTBorongan();

    /** Column name JHTPaidByCompany */
    public static final String COLUMNNAME_JHTPaidByCompany = "JHTPaidByCompany";

	/** Set JHT Paid By Comp(%).
	  * JHT Paid By Company in percent
	  */
	public void setJHTPaidByCompany (BigDecimal JHTPaidByCompany);

	/** Get JHT Paid By Comp(%).
	  * JHT Paid By Company in percent
	  */
	public BigDecimal getJHTPaidByCompany();

    /** Column name JHTPaidByEmployee */
    public static final String COLUMNNAME_JHTPaidByEmployee = "JHTPaidByEmployee";

	/** Set JHT Paid By Employee (%).
	  * JHT Paid By Employee In Percent
	  */
	public void setJHTPaidByEmployee (BigDecimal JHTPaidByEmployee);

	/** Get JHT Paid By Employee (%).
	  * JHT Paid By Employee In Percent
	  */
	public BigDecimal getJHTPaidByEmployee();

    /** Column name JKBasicCalculation */
    public static final String COLUMNNAME_JKBasicCalculation = "JKBasicCalculation";

	/** Set JK Basic Calculation	  */
	public void setJKBasicCalculation (String JKBasicCalculation);

	/** Get JK Basic Calculation	  */
	public String getJKBasicCalculation();

    /** Column name JKBorongan */
    public static final String COLUMNNAME_JKBorongan = "JKBorongan";

	/** Set JK Borongan.
	  * Jaminan Kematian Borongan
	  */
	public void setJKBorongan (BigDecimal JKBorongan);

	/** Get JK Borongan.
	  * Jaminan Kematian Borongan
	  */
	public BigDecimal getJKBorongan();

    /** Column name JKKBasicCalculation */
    public static final String COLUMNNAME_JKKBasicCalculation = "JKKBasicCalculation";

	/** Set JKK Basic Calculation	  */
	public void setJKKBasicCalculation (String JKKBasicCalculation);

	/** Get JKK Basic Calculation	  */
	public String getJKKBasicCalculation();

    /** Column name JKKBorongan */
    public static final String COLUMNNAME_JKKBorongan = "JKKBorongan";

	/** Set JKK Borongan.
	  * Jaminan Kecelakaan Kerja Borongan
	  */
	public void setJKKBorongan (BigDecimal JKKBorongan);

	/** Get JKK Borongan.
	  * Jaminan Kecelakaan Kerja Borongan
	  */
	public BigDecimal getJKKBorongan();

    /** Column name JKKPaidByCompany */
    public static final String COLUMNNAME_JKKPaidByCompany = "JKKPaidByCompany";

	/** Set JKK Paid By Comp(%).
	  * JKK Paid By Company in percent
	  */
	public void setJKKPaidByCompany (BigDecimal JKKPaidByCompany);

	/** Get JKK Paid By Comp(%).
	  * JKK Paid By Company in percent
	  */
	public BigDecimal getJKKPaidByCompany();

    /** Column name JKKPaidByEmployee */
    public static final String COLUMNNAME_JKKPaidByEmployee = "JKKPaidByEmployee";

	/** Set JKK Paid By Employee (%).
	  * JKK Paid By Employee In Percent
	  */
	public void setJKKPaidByEmployee (BigDecimal JKKPaidByEmployee);

	/** Get JKK Paid By Employee (%).
	  * JKK Paid By Employee In Percent
	  */
	public BigDecimal getJKKPaidByEmployee();

    /** Column name JKPaidByCompany */
    public static final String COLUMNNAME_JKPaidByCompany = "JKPaidByCompany";

	/** Set JK Paid By Comp(%).
	  * JK Paid By Company in percent
	  */
	public void setJKPaidByCompany (BigDecimal JKPaidByCompany);

	/** Get JK Paid By Comp(%).
	  * JK Paid By Company in percent
	  */
	public BigDecimal getJKPaidByCompany();

    /** Column name JKPaidByEmployee */
    public static final String COLUMNNAME_JKPaidByEmployee = "JKPaidByEmployee";

	/** Set JK Paid By Employee (%).
	  * JK Paid By Employee In Percent
	  */
	public void setJKPaidByEmployee (BigDecimal JKPaidByEmployee);

	/** Get JK Paid By Employee (%).
	  * JK Paid By Employee In Percent
	  */
	public BigDecimal getJKPaidByEmployee();

    /** Column name JPBasicCalculation */
    public static final String COLUMNNAME_JPBasicCalculation = "JPBasicCalculation";

	/** Set JP Basic Calculation	  */
	public void setJPBasicCalculation (String JPBasicCalculation);

	/** Get JP Basic Calculation	  */
	public String getJPBasicCalculation();

    /** Column name JPKBasicCalculation */
    public static final String COLUMNNAME_JPKBasicCalculation = "JPKBasicCalculation";

	/** Set JPK Basic Calculation	  */
	public void setJPKBasicCalculation (String JPKBasicCalculation);

	/** Get JPK Basic Calculation	  */
	public String getJPKBasicCalculation();

    /** Column name JPKBorongan */
    public static final String COLUMNNAME_JPKBorongan = "JPKBorongan";

	/** Set JPK Borongan.
	  * Jaminan Pemeliharaan Kesehatan Borongan
	  */
	public void setJPKBorongan (BigDecimal JPKBorongan);

	/** Get JPK Borongan.
	  * Jaminan Pemeliharaan Kesehatan Borongan
	  */
	public BigDecimal getJPKBorongan();

    /** Column name JPKKawinPaidByCompany */
    public static final String COLUMNNAME_JPKKawinPaidByCompany = "JPKKawinPaidByCompany";

	/** Set JPK Kawin Paid By Comp(%).
	  * JPK Kawin Paid By Conpany in percent
	  */
	public void setJPKKawinPaidByCompany (BigDecimal JPKKawinPaidByCompany);

	/** Get JPK Kawin Paid By Comp(%).
	  * JPK Kawin Paid By Conpany in percent
	  */
	public BigDecimal getJPKKawinPaidByCompany();

    /** Column name JPKKawinPaidByEmployee */
    public static final String COLUMNNAME_JPKKawinPaidByEmployee = "JPKKawinPaidByEmployee";

	/** Set JPK Kawin Paid By Emp(%).
	  * JPK Kawin Paid By Employee in percent
	  */
	public void setJPKKawinPaidByEmployee (BigDecimal JPKKawinPaidByEmployee);

	/** Get JPK Kawin Paid By Emp(%).
	  * JPK Kawin Paid By Employee in percent
	  */
	public BigDecimal getJPKKawinPaidByEmployee();

    /** Column name JPKLajangPaidByCompany */
    public static final String COLUMNNAME_JPKLajangPaidByCompany = "JPKLajangPaidByCompany";

	/** Set JPK Lajang Paid By Comp(%).
	  * JPK Lajang Paid By Company in percent
	  */
	public void setJPKLajangPaidByCompany (BigDecimal JPKLajangPaidByCompany);

	/** Get JPK Lajang Paid By Comp(%).
	  * JPK Lajang Paid By Company in percent
	  */
	public BigDecimal getJPKLajangPaidByCompany();

    /** Column name JPKLajangPaidByEmployee */
    public static final String COLUMNNAME_JPKLajangPaidByEmployee = "JPKLajangPaidByEmployee";

	/** Set JPK Lajang Paid By Emp(%).
	  * JPK Lajang Paid By Company in percent
	  */
	public void setJPKLajangPaidByEmployee (BigDecimal JPKLajangPaidByEmployee);

	/** Get JPK Lajang Paid By Emp(%).
	  * JPK Lajang Paid By Company in percent
	  */
	public BigDecimal getJPKLajangPaidByEmployee();

    /** Column name JPPaidByCompany */
    public static final String COLUMNNAME_JPPaidByCompany = "JPPaidByCompany";

	/** Set JP Paid By Company (%)	  */
	public void setJPPaidByCompany (BigDecimal JPPaidByCompany);

	/** Get JP Paid By Company (%)	  */
	public BigDecimal getJPPaidByCompany();

    /** Column name JPPaidByEmployee */
    public static final String COLUMNNAME_JPPaidByEmployee = "JPPaidByEmployee";

	/** Set JP Paid By Employee (%)	  */
	public void setJPPaidByEmployee (BigDecimal JPPaidByEmployee);

	/** Get JP Paid By Employee (%)	  */
	public BigDecimal getJPPaidByEmployee();

    /** Column name K0 */
    public static final String COLUMNNAME_K0 = "K0";

	/** Set K0.
	  * Kawind dan 0 tanggungan
	  */
	public void setK0 (BigDecimal K0);

	/** Get K0.
	  * Kawind dan 0 tanggungan
	  */
	public BigDecimal getK0();

    /** Column name K1 */
    public static final String COLUMNNAME_K1 = "K1";

	/** Set K1.
	  * Kawind dengan 1 tanggungan
	  */
	public void setK1 (BigDecimal K1);

	/** Get K1.
	  * Kawind dengan 1 tanggungan
	  */
	public BigDecimal getK1();

    /** Column name K2 */
    public static final String COLUMNNAME_K2 = "K2";

	/** Set K2.
	  * Kawind dengan 2 tanggungan
	  */
	public void setK2 (BigDecimal K2);

	/** Get K2.
	  * Kawind dengan 2 tanggungan
	  */
	public BigDecimal getK2();

    /** Column name K3 */
    public static final String COLUMNNAME_K3 = "K3";

	/** Set K3.
	  * Kawind dengan 3 tanggungan
	  */
	public void setK3 (BigDecimal K3);

	/** Get K3.
	  * Kawind dengan 3 tanggungan
	  */
	public BigDecimal getK3();

    /** Column name Level1 */
    public static final String COLUMNNAME_Level1 = "Level1";

	/** Set Sampai Dengan	  */
	public void setLevel1 (BigDecimal Level1);

	/** Get Sampai Dengan	  */
	public BigDecimal getLevel1();

    /** Column name Level2 */
    public static final String COLUMNNAME_Level2 = "Level2";

	/** Set Sampai Dengan	  */
	public void setLevel2 (BigDecimal Level2);

	/** Get Sampai Dengan	  */
	public BigDecimal getLevel2();

    /** Column name Level3 */
    public static final String COLUMNNAME_Level3 = "Level3";

	/** Set Sampai Dengan	  */
	public void setLevel3 (BigDecimal Level3);

	/** Get Sampai Dengan	  */
	public BigDecimal getLevel3();

    /** Column name Level4 */
    public static final String COLUMNNAME_Level4 = "Level4";

	/** Set Sampai Dengan	  */
	public void setLevel4 (BigDecimal Level4);

	/** Get Sampai Dengan	  */
	public BigDecimal getLevel4();

    /** Column name Level5 */
    public static final String COLUMNNAME_Level5 = "Level5";

	/** Set Di Atas	  */
	public void setLevel5 (BigDecimal Level5);

	/** Get Di Atas	  */
	public BigDecimal getLevel5();

    /** Column name MaxBiayaJabatan */
    public static final String COLUMNNAME_MaxBiayaJabatan = "MaxBiayaJabatan";

	/** Set Maximum	  */
	public void setMaxBiayaJabatan (BigDecimal MaxBiayaJabatan);

	/** Get Maximum	  */
	public BigDecimal getMaxBiayaJabatan();

    /** Column name MaxGajiToCalcJPK */
    public static final String COLUMNNAME_MaxGajiToCalcJPK = "MaxGajiToCalcJPK";

	/** Set Max Gaji To Calc JPK	  */
	public void setMaxGajiToCalcJPK (BigDecimal MaxGajiToCalcJPK);

	/** Get Max Gaji To Calc JPK	  */
	public BigDecimal getMaxGajiToCalcJPK();

    /** Column name MaxLD1 */
    public static final String COLUMNNAME_MaxLD1 = "MaxLD1";

	/** Set Max LD 1	  */
	public void setMaxLD1 (int MaxLD1);

	/** Get Max LD 1	  */
	public int getMaxLD1();

    /** Column name MaxLD2 */
    public static final String COLUMNNAME_MaxLD2 = "MaxLD2";

	/** Set Max LD 2	  */
	public void setMaxLD2 (int MaxLD2);

	/** Get Max LD 2	  */
	public int getMaxLD2();

    /** Column name MedicalKaryawan */
    public static final String COLUMNNAME_MedicalKaryawan = "MedicalKaryawan";

	/** Set Biaya Medis Karyawan	  */
	public void setMedicalKaryawan (BigDecimal MedicalKaryawan);

	/** Get Biaya Medis Karyawan	  */
	public BigDecimal getMedicalKaryawan();

    /** Column name MedicalNonKaryawan */
    public static final String COLUMNNAME_MedicalNonKaryawan = "MedicalNonKaryawan";

	/** Set Biaya Medis Non Karyawan	  */
	public void setMedicalNonKaryawan (BigDecimal MedicalNonKaryawan);

	/** Get Biaya Medis Non Karyawan	  */
	public BigDecimal getMedicalNonKaryawan();

    /** Column name MinElectricityUsage */
    public static final String COLUMNNAME_MinElectricityUsage = "MinElectricityUsage";

	/** Set Min. Electricity Usage (KWH).
	  * The minimum usage amount of electricity
	  */
	public void setMinElectricityUsage (BigDecimal MinElectricityUsage);

	/** Get Min. Electricity Usage (KWH).
	  * The minimum usage amount of electricity
	  */
	public BigDecimal getMinElectricityUsage();

    /** Column name MinWaterUsage */
    public static final String COLUMNNAME_MinWaterUsage = "MinWaterUsage";

	/** Set Min. Water Usage (Liter)	  */
	public void setMinWaterUsage (BigDecimal MinWaterUsage);

	/** Get Min. Water Usage (Liter)	  */
	public BigDecimal getMinWaterUsage();

    /** Column name NonShiftAllWorkDayIsOT */
    public static final String COLUMNNAME_NonShiftAllWorkDayIsOT = "NonShiftAllWorkDayIsOT";

	/** Set All Work Days are OT.
	  * To indicate that overtime allowance for non-shift-based employee is calculated to all work days
	  */
	public void setNonShiftAllWorkDayIsOT (boolean NonShiftAllWorkDayIsOT);

	/** Get All Work Days are OT.
	  * To indicate that overtime allowance for non-shift-based employee is calculated to all work days
	  */
	public boolean isNonShiftAllWorkDayIsOT();

    /** Column name NonShiftOTAllowanceHours */
    public static final String COLUMNNAME_NonShiftOTAllowanceHours = "NonShiftOTAllowanceHours";

	/** Set OT Allowance Hours.
	  * Number of hours in month used as overtime calculation basis for non-shift-based employee
	  */
	public void setNonShiftOTAllowanceHours (BigDecimal NonShiftOTAllowanceHours);

	/** Get OT Allowance Hours.
	  * Number of hours in month used as overtime calculation basis for non-shift-based employee
	  */
	public BigDecimal getNonShiftOTAllowanceHours();

    /** Column name NonShiftOTDay */
    public static final String COLUMNNAME_NonShiftOTDay = "NonShiftOTDay";

	/** Set OT Work Day.
	  * The work day assumes as OT base-day of non-shift-based employee
	  */
	public void setNonShiftOTDay (String NonShiftOTDay);

	/** Get OT Work Day.
	  * The work day assumes as OT base-day of non-shift-based employee
	  */
	public String getNonShiftOTDay();

    /** Column name NonShiftOTDeductionMultiplier */
    public static final String COLUMNNAME_NonShiftOTDeductionMultiplier = "NonShiftOTDeductionMultiplier";

	/** Set OT DeductionMultiplier.
	  * Number of multiplier to non-shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public void setNonShiftOTDeductionMultiplier (BigDecimal NonShiftOTDeductionMultiplier);

	/** Get OT DeductionMultiplier.
	  * Number of multiplier to non-shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public BigDecimal getNonShiftOTDeductionMultiplier();

    /** Column name NormalDayWorkHours */
    public static final String COLUMNNAME_NormalDayWorkHours = "NormalDayWorkHours";

	/** Set Normal Day Work Hours	  */
	public void setNormalDayWorkHours (BigDecimal NormalDayWorkHours);

	/** Get Normal Day Work Hours	  */
	public BigDecimal getNormalDayWorkHours();

    /** Column name NormalDayWorkHoursShift */
    public static final String COLUMNNAME_NormalDayWorkHoursShift = "NormalDayWorkHoursShift";

	/** Set Normal Day Work Hours (S)	  */
	public void setNormalDayWorkHoursShift (BigDecimal NormalDayWorkHoursShift);

	/** Get Normal Day Work Hours (S)	  */
	public BigDecimal getNormalDayWorkHoursShift();

    /** Column name PajakLevel1 */
    public static final String COLUMNNAME_PajakLevel1 = "PajakLevel1";

	/** Set Pajak (%).
	  * Pajak In Percent
	  */
	public void setPajakLevel1 (BigDecimal PajakLevel1);

	/** Get Pajak (%).
	  * Pajak In Percent
	  */
	public BigDecimal getPajakLevel1();

    /** Column name PajakLevel2 */
    public static final String COLUMNNAME_PajakLevel2 = "PajakLevel2";

	/** Set Pajak (%).
	  * Pajak In Percent
	  */
	public void setPajakLevel2 (BigDecimal PajakLevel2);

	/** Get Pajak (%).
	  * Pajak In Percent
	  */
	public BigDecimal getPajakLevel2();

    /** Column name PajakLevel3 */
    public static final String COLUMNNAME_PajakLevel3 = "PajakLevel3";

	/** Set Pajak (%).
	  * Pajak In Percent
	  */
	public void setPajakLevel3 (BigDecimal PajakLevel3);

	/** Get Pajak (%).
	  * Pajak In Percent
	  */
	public BigDecimal getPajakLevel3();

    /** Column name PajakLevel4 */
    public static final String COLUMNNAME_PajakLevel4 = "PajakLevel4";

	/** Set Pajak (%).
	  * Pajak In Percent
	  */
	public void setPajakLevel4 (BigDecimal PajakLevel4);

	/** Get Pajak (%).
	  * Pajak In Percent
	  */
	public BigDecimal getPajakLevel4();

    /** Column name PajakLevel5 */
    public static final String COLUMNNAME_PajakLevel5 = "PajakLevel5";

	/** Set Pajak (%).
	  * Pajak In Percent
	  */
	public void setPajakLevel5 (BigDecimal PajakLevel5);

	/** Get Pajak (%).
	  * Pajak In Percent
	  */
	public BigDecimal getPajakLevel5();

    /** Column name PajakNonNPWP */
    public static final String COLUMNNAME_PajakNonNPWP = "PajakNonNPWP";

	/** Set Pajak Non NPWP (%)	  */
	public void setPajakNonNPWP (BigDecimal PajakNonNPWP);

	/** Get Pajak Non NPWP (%)	  */
	public BigDecimal getPajakNonNPWP();

    /** Column name PayrollDateEnd */
    public static final String COLUMNNAME_PayrollDateEnd = "PayrollDateEnd";

	/** Set Payroll Date End	  */
	public void setPayrollDateEnd (int PayrollDateEnd);

	/** Get Payroll Date End	  */
	public int getPayrollDateEnd();

    /** Column name PayrollDateStart */
    public static final String COLUMNNAME_PayrollDateStart = "PayrollDateStart";

	/** Set Payroll Date Start	  */
	public void setPayrollDateStart (int PayrollDateStart);

	/** Get Payroll Date Start	  */
	public int getPayrollDateStart();

    /** Column name P_Employee_Loan_Acct */
    public static final String COLUMNNAME_P_Employee_Loan_Acct = "P_Employee_Loan_Acct";

	/** Set Employee Loan Acct.
	  * The accounting element for posting employee's loan to company
	  */
	public void setP_Employee_Loan_Acct (int P_Employee_Loan_Acct);

	/** Get Employee Loan Acct.
	  * The accounting element for posting employee's loan to company
	  */
	public int getP_Employee_Loan_Acct();

	public I_C_ValidCombination getP_Employee_Loan_A() throws RuntimeException;

    /** Column name P_LabelAcct_ID */
    public static final String COLUMNNAME_P_LabelAcct_ID = "P_LabelAcct_ID";

	/** Set P. Label Acct	  */
	public void setP_LabelAcct_ID (int P_LabelAcct_ID);

	/** Get P. Label Acct	  */
	public int getP_LabelAcct_ID();

	public I_C_ValidCombination getP_LabelAcct() throws RuntimeException;

    /** Column name P_ListrikAirAcct_ID */
    public static final String COLUMNNAME_P_ListrikAirAcct_ID = "P_ListrikAirAcct_ID";

	/** Set P. Listrik Air Acct	  */
	public void setP_ListrikAirAcct_ID (int P_ListrikAirAcct_ID);

	/** Get P. Listrik Air Acct	  */
	public int getP_ListrikAirAcct_ID();

	public I_C_ValidCombination getP_ListrikAirAcct() throws RuntimeException;

    /** Column name P_MakanAcct_ID */
    public static final String COLUMNNAME_P_MakanAcct_ID = "P_MakanAcct_ID";

	/** Set P Makan Account	  */
	public void setP_MakanAcct_ID (int P_MakanAcct_ID);

	/** Get P Makan Account	  */
	public int getP_MakanAcct_ID();

	public I_C_ValidCombination getP_MakanAcct() throws RuntimeException;

    /** Column name P_MangkirAcct_ID */
    public static final String COLUMNNAME_P_MangkirAcct_ID = "P_MangkirAcct_ID";

	/** Set P. Mangkir Acct.
	  * Potongan Mangkir Account
	  */
	public void setP_MangkirAcct_ID (int P_MangkirAcct_ID);

	/** Get P. Mangkir Acct.
	  * Potongan Mangkir Account
	  */
	public int getP_MangkirAcct_ID();

	public I_C_ValidCombination getP_MangkirAcct() throws RuntimeException;

    /** Column name P_Obat_Acct */
    public static final String COLUMNNAME_P_Obat_Acct = "P_Obat_Acct";

	/** Set Clinic Revenue Acct.
	  * Accounting element for potongan / pendapatan / revenue of clinic dept. from medical transaction
	  */
	public void setP_Obat_Acct (int P_Obat_Acct);

	/** Get Clinic Revenue Acct.
	  * Accounting element for potongan / pendapatan / revenue of clinic dept. from medical transaction
	  */
	public int getP_Obat_Acct();

	public I_C_ValidCombination getP_Obat_A() throws RuntimeException;

    /** Column name P_OtherAcct_ID */
    public static final String COLUMNNAME_P_OtherAcct_ID = "P_OtherAcct_ID";

	/** Set P. Other Acct	  */
	public void setP_OtherAcct_ID (int P_OtherAcct_ID);

	/** Get P. Other Acct	  */
	public int getP_OtherAcct_ID();

	public I_C_ValidCombination getP_OtherAcct() throws RuntimeException;

    /** Column name PPH21_Acct_ID */
    public static final String COLUMNNAME_PPH21_Acct_ID = "PPH21_Acct_ID";

	/** Set PPH21 Acct	  */
	public void setPPH21_Acct_ID (int PPH21_Acct_ID);

	/** Get PPH21 Acct	  */
	public int getPPH21_Acct_ID();

	public I_C_ValidCombination getPPH21_Acct() throws RuntimeException;

    /** Column name PPH21PaidByCompany */
    public static final String COLUMNNAME_PPH21PaidByCompany = "PPH21PaidByCompany";

	/** Set PPH21PaidByCompany	  */
	public void setPPH21PaidByCompany (boolean PPH21PaidByCompany);

	/** Get PPH21PaidByCompany	  */
	public boolean isPPH21PaidByCompany();

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

    /** Column name P_SPTPAcct_ID */
    public static final String COLUMNNAME_P_SPTPAcct_ID = "P_SPTPAcct_ID";

	/** Set Potongan SPTP Acct	  */
	public void setP_SPTPAcct_ID (int P_SPTPAcct_ID);

	/** Get Potongan SPTP Acct	  */
	public int getP_SPTPAcct_ID();

	public I_C_ValidCombination getP_SPTPAcct() throws RuntimeException;

    /** Column name ShiftAllWorkDayIsOT */
    public static final String COLUMNNAME_ShiftAllWorkDayIsOT = "ShiftAllWorkDayIsOT";

	/** Set All Work Days are OT (S).
	  * To indicate that overtime allowance for shift-based employee is calculated to all work days
	  */
	public void setShiftAllWorkDayIsOT (boolean ShiftAllWorkDayIsOT);

	/** Get All Work Days are OT (S).
	  * To indicate that overtime allowance for shift-based employee is calculated to all work days
	  */
	public boolean isShiftAllWorkDayIsOT();

    /** Column name ShiftOTAllowanceHours */
    public static final String COLUMNNAME_ShiftOTAllowanceHours = "ShiftOTAllowanceHours";

	/** Set OT Allowance Hours (S).
	  * Number of hours in month used as overtime calculation basis for shift-based employee
	  */
	public void setShiftOTAllowanceHours (BigDecimal ShiftOTAllowanceHours);

	/** Get OT Allowance Hours (S).
	  * Number of hours in month used as overtime calculation basis for shift-based employee
	  */
	public BigDecimal getShiftOTAllowanceHours();

    /** Column name ShiftOTDay */
    public static final String COLUMNNAME_ShiftOTDay = "ShiftOTDay";

	/** Set OT Work Day (S).
	  * The work day assumes as OT base-day of shift-based employee
	  */
	public void setShiftOTDay (String ShiftOTDay);

	/** Get OT Work Day (S).
	  * The work day assumes as OT base-day of shift-based employee
	  */
	public String getShiftOTDay();

    /** Column name ShiftOTDeductionMultiplier */
    public static final String COLUMNNAME_ShiftOTDeductionMultiplier = "ShiftOTDeductionMultiplier";

	/** Set OT Deduction Multiplier (S).
	  * Number of multiplier to shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public void setShiftOTDeductionMultiplier (BigDecimal ShiftOTDeductionMultiplier);

	/** Get OT Deduction Multiplier (S).
	  * Number of multiplier to shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public BigDecimal getShiftOTDeductionMultiplier();

    /** Column name ShortDay */
    public static final String COLUMNNAME_ShortDay = "ShortDay";

	/** Set Short Day	  */
	public void setShortDay (String ShortDay);

	/** Get Short Day	  */
	public String getShortDay();

    /** Column name ShortDayShift */
    public static final String COLUMNNAME_ShortDayShift = "ShortDayShift";

	/** Set Short Day (S)	  */
	public void setShortDayShift (String ShortDayShift);

	/** Get Short Day (S)	  */
	public String getShortDayShift();

    /** Column name ShortDayWorkHours */
    public static final String COLUMNNAME_ShortDayWorkHours = "ShortDayWorkHours";

	/** Set Short Day Work Hours	  */
	public void setShortDayWorkHours (BigDecimal ShortDayWorkHours);

	/** Get Short Day Work Hours	  */
	public BigDecimal getShortDayWorkHours();

    /** Column name ShortDayWorkHoursShift */
    public static final String COLUMNNAME_ShortDayWorkHoursShift = "ShortDayWorkHoursShift";

	/** Set Short Day Work Hours (S)	  */
	public void setShortDayWorkHoursShift (BigDecimal ShortDayWorkHoursShift);

	/** Get Short Day Work Hours (S)	  */
	public BigDecimal getShortDayWorkHoursShift();

    /** Column name SP_Period */
    public static final String COLUMNNAME_SP_Period = "SP_Period";

	/** Set SP Period	  */
	public void setSP_Period (int SP_Period);

	/** Get SP Period	  */
	public int getSP_Period();

    /** Column name TK0 */
    public static final String COLUMNNAME_TK0 = "TK0";

	/** Set TK0.
	  * Tidak kawin dengan 0 tanggunan
	  */
	public void setTK0 (BigDecimal TK0);

	/** Get TK0.
	  * Tidak kawin dengan 0 tanggunan
	  */
	public BigDecimal getTK0();

    /** Column name TK1 */
    public static final String COLUMNNAME_TK1 = "TK1";

	/** Set TK1.
	  * Tidak kawin dengan 1 tanggunan
	  */
	public void setTK1 (BigDecimal TK1);

	/** Get TK1.
	  * Tidak kawin dengan 1 tanggunan
	  */
	public BigDecimal getTK1();

    /** Column name TK2 */
    public static final String COLUMNNAME_TK2 = "TK2";

	/** Set TK2.
	  * Tidak kawin dengan 2 tanggunan
	  */
	public void setTK2 (BigDecimal TK2);

	/** Get TK2.
	  * Tidak kawin dengan 2 tanggunan
	  */
	public BigDecimal getTK2();

    /** Column name TK3 */
    public static final String COLUMNNAME_TK3 = "TK3";

	/** Set TK3.
	  * Tidak kawin dengan 3 tanggunan
	  */
	public void setTK3 (BigDecimal TK3);

	/** Get TK3.
	  * Tidak kawin dengan 3 tanggunan
	  */
	public BigDecimal getTK3();

    /** Column name TotalWorkDayBasis */
    public static final String COLUMNNAME_TotalWorkDayBasis = "TotalWorkDayBasis";

	/** Set Total Work Day Basis.
	  * Number of work days in month used as absence deduction basis to employee's alowances
	  */
	public void setTotalWorkDayBasis (BigDecimal TotalWorkDayBasis);

	/** Get Total Work Day Basis.
	  * Number of work days in month used as absence deduction basis to employee's alowances
	  */
	public BigDecimal getTotalWorkDayBasis();

    /** Column name TunjanganJabatanAcct_ID */
    public static final String COLUMNNAME_TunjanganJabatanAcct_ID = "TunjanganJabatanAcct_ID";

	/** Set Tunjangan Jabatan Acct	  */
	public void setTunjanganJabatanAcct_ID (int TunjanganJabatanAcct_ID);

	/** Get Tunjangan Jabatan Acct	  */
	public int getTunjanganJabatanAcct_ID();

	public I_C_ValidCombination getTunjanganJabatanAcct() throws RuntimeException;

    /** Column name TunjanganKesejahteraanAcct_ID */
    public static final String COLUMNNAME_TunjanganKesejahteraanAcct_ID = "TunjanganKesejahteraanAcct_ID";

	/** Set Tunjangan Kesejahteraan Acct	  */
	public void setTunjanganKesejahteraanAcct_ID (int TunjanganKesejahteraanAcct_ID);

	/** Get Tunjangan Kesejahteraan Acct	  */
	public int getTunjanganKesejahteraanAcct_ID();

	public I_C_ValidCombination getTunjanganKesejahteraanAcct() throws RuntimeException;

    /** Column name TunjanganKhususAcct_ID */
    public static final String COLUMNNAME_TunjanganKhususAcct_ID = "TunjanganKhususAcct_ID";

	/** Set Tunjangan Khusus Acct	  */
	public void setTunjanganKhususAcct_ID (int TunjanganKhususAcct_ID);

	/** Get Tunjangan Khusus Acct	  */
	public int getTunjanganKhususAcct_ID();

	public I_C_ValidCombination getTunjanganKhususAcct() throws RuntimeException;

    /** Column name TunjanganLemburAcct_ID */
    public static final String COLUMNNAME_TunjanganLemburAcct_ID = "TunjanganLemburAcct_ID";

	/** Set Tunjangan Lembur Acct	  */
	public void setTunjanganLemburAcct_ID (int TunjanganLemburAcct_ID);

	/** Get Tunjangan Lembur Acct	  */
	public int getTunjanganLemburAcct_ID();

	public I_C_ValidCombination getTunjanganLemburAcct() throws RuntimeException;

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

    /** Column name UNS_PayrollConfiguration_UU */
    public static final String COLUMNNAME_UNS_PayrollConfiguration_UU = "UNS_PayrollConfiguration_UU";

	/** Set UNS_PayrollConfiguration_UU	  */
	public void setUNS_PayrollConfiguration_UU (String UNS_PayrollConfiguration_UU);

	/** Get UNS_PayrollConfiguration_UU	  */
	public String getUNS_PayrollConfiguration_UU();

    /** Column name UpahBuruhDirectAcct_ID */
    public static final String COLUMNNAME_UpahBuruhDirectAcct_ID = "UpahBuruhDirectAcct_ID";

	/** Set Upah Buruh Direct Acct	  */
	public void setUpahBuruhDirectAcct_ID (int UpahBuruhDirectAcct_ID);

	/** Get Upah Buruh Direct Acct	  */
	public int getUpahBuruhDirectAcct_ID();

	public I_C_ValidCombination getUpahBuruhDirectAcct() throws RuntimeException;

    /** Column name UpahBuruhInDirectAcct_ID */
    public static final String COLUMNNAME_UpahBuruhInDirectAcct_ID = "UpahBuruhInDirectAcct_ID";

	/** Set Upah Buruh InDirect Acct	  */
	public void setUpahBuruhInDirectAcct_ID (int UpahBuruhInDirectAcct_ID);

	/** Get Upah Buruh InDirect Acct	  */
	public int getUpahBuruhInDirectAcct_ID();

	public I_C_ValidCombination getUpahBuruhInDirectAcct() throws RuntimeException;

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
