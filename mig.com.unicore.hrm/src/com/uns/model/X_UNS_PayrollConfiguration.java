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

/** Generated Model for UNS_PayrollConfiguration
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PayrollConfiguration extends PO implements I_UNS_PayrollConfiguration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_PayrollConfiguration (Properties ctx, int UNS_PayrollConfiguration_ID, String trxName)
    {
      super (ctx, UNS_PayrollConfiguration_ID, trxName);
      /** if (UNS_PayrollConfiguration_ID == 0)
        {
			setA_L1_Acct_ID (0);
			setA_L2_Acct_ID (0);
			setA_L3_Acct_ID (0);
			setA_Lembur_Acct_ID (0);
			setA_OtherAcct_ID (0);
			setA_PremiAcct_ID (0);
			setA_RapelAcct_ID (0);
			setBiayaAir (Env.ZERO);
// 50
			setBiayaGajiBulananAcct_ID (0);
			setBiayaGajiHarianAcct_ID (0);
			setBiayaListrik (Env.ZERO);
// 50
			setB_JHTAcct_ID (0);
			setB_JKAcct_ID (0);
			setB_JKKAcct_ID (0);
			setB_JPKAcct_ID (0);
			setDocAction (null);
// PR
			setE_Medical_Expense_Acct (0);
			setH_JHT_Acct_ID (0);
			setH_JK_Acct_ID (0);
			setH_JKK_Acct_ID (0);
			setH_JPK_Acct_ID (0);
			setHolidayWorkHours (Env.ZERO);
// 8
			setHolidayWorkHoursShift (Env.ZERO);
// 8
			setHutangGajiBulananAcct_ID (0);
			setHutangGajiHarianAcct_ID (0);
			setHutangUpahBuruhDirectAcct_ID (0);
			setHutangUpahBuruhInDirectAcct_ID (0);
			setIsCalculatePPH21Buruh (false);
// N
			setIsJHTApplyed (false);
			setIsJKApplyed (false);
			setIsJKKApplyed (false);
			setIsJPApplied (false);
// N
			setIsJPKApplyed (false);
			setJHTBasicCalculation (null);
// BGP
			setJKBasicCalculation (null);
// BGP
			setJKKBasicCalculation (null);
// BGP
			setJPKBasicCalculation (null);
// BGP
			setJPPaidByCompany (Env.ZERO);
// 0
			setJPPaidByEmployee (Env.ZERO);
// 0
			setK0 (Env.ZERO);
// 0
			setK1 (Env.ZERO);
// 0
			setK2 (Env.ZERO);
// 0
			setK3 (Env.ZERO);
// 0
			setMaxGajiToCalcJPK (Env.ZERO);
// 3880000
			setMaxLD1 (0);
			setMaxLD2 (0);
			setMedicalKaryawan (Env.ZERO);
// 3000
			setMedicalNonKaryawan (Env.ZERO);
// 6000
			setMinElectricityUsage (Env.ZERO);
// 15
			setMinWaterUsage (Env.ZERO);
// 4000
			setNonShiftAllWorkDayIsOT (false);
// N
			setNonShiftOTAllowanceHours (Env.ZERO);
// 39
			setNonShiftOTDeductionMultiplier (Env.ZERO);
// 1
			setNormalDayWorkHours (Env.ZERO);
// 8
			setNormalDayWorkHoursShift (Env.ZERO);
// 8
			setPajakNonNPWP (Env.ZERO);
// 120
			setPayrollDateEnd (0);
			setPayrollDateStart (0);
			setP_Employee_Loan_Acct (0);
			setP_LabelAcct_ID (0);
			setP_ListrikAirAcct_ID (0);
			setP_MangkirAcct_ID (0);
			setP_Obat_Acct (0);
			setP_OtherAcct_ID (0);
			setPPH21_Acct_ID (0);
			setPPH21PaidByCompany (false);
// N
			setProcessed (false);
// N
			setP_SPTPAcct_ID (0);
			setShiftAllWorkDayIsOT (false);
// N
			setShiftOTAllowanceHours (Env.ZERO);
// 39
			setShiftOTDeductionMultiplier (Env.ZERO);
// 1
			setShortDay (null);
// 6
			setShortDayShift (null);
// 6
			setShortDayWorkHours (Env.ZERO);
// 6
			setShortDayWorkHoursShift (Env.ZERO);
// 6
			setSP_Period (0);
// 6
			setTK0 (Env.ZERO);
// 0
			setTK1 (Env.ZERO);
// 0
			setTK2 (Env.ZERO);
// 0
			setTK3 (Env.ZERO);
// 0
			setTotalWorkDayBasis (Env.ZERO);
// 26
			setTunjanganJabatanAcct_ID (0);
			setTunjanganKesejahteraanAcct_ID (0);
			setTunjanganKhususAcct_ID (0);
			setTunjanganLemburAcct_ID (0);
			setUMK (Env.ZERO);
// 0
			setUMP (Env.ZERO);
// 0
			setUNS_PayrollConfiguration_ID (0);
			setUpahBuruhDirectAcct_ID (0);
			setUpahBuruhInDirectAcct_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_PayrollConfiguration (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayrollConfiguration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getA_L1_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_L1_Acct_ID(), get_TrxName());	}

	/** Set Additional L1 Acct.
		@param A_L1_Acct_ID 
		Aditional Lembur1 Acct
	  */
	public void setA_L1_Acct_ID (int A_L1_Acct_ID)
	{
		if (A_L1_Acct_ID < 1) 
			set_Value (COLUMNNAME_A_L1_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_A_L1_Acct_ID, Integer.valueOf(A_L1_Acct_ID));
	}

	/** Get Additional L1 Acct.
		@return Aditional Lembur1 Acct
	  */
	public int getA_L1_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_L1_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_L2_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_L2_Acct_ID(), get_TrxName());	}

	/** Set Additional L2 Acct.
		@param A_L2_Acct_ID 
		Aditional Lembur2 Acct
	  */
	public void setA_L2_Acct_ID (int A_L2_Acct_ID)
	{
		if (A_L2_Acct_ID < 1) 
			set_Value (COLUMNNAME_A_L2_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_A_L2_Acct_ID, Integer.valueOf(A_L2_Acct_ID));
	}

	/** Get Additional L2 Acct.
		@return Aditional Lembur2 Acct
	  */
	public int getA_L2_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_L2_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_L3_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_L3_Acct_ID(), get_TrxName());	}

	/** Set Additional L3 Acct.
		@param A_L3_Acct_ID 
		Aditional Lembur3 Acct
	  */
	public void setA_L3_Acct_ID (int A_L3_Acct_ID)
	{
		if (A_L3_Acct_ID < 1) 
			set_Value (COLUMNNAME_A_L3_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_A_L3_Acct_ID, Integer.valueOf(A_L3_Acct_ID));
	}

	/** Get Additional L3 Acct.
		@return Aditional Lembur3 Acct
	  */
	public int getA_L3_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_L3_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Lembur_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Lembur_Acct_ID(), get_TrxName());	}

	/** Set Additional Lembur Acct.
		@param A_Lembur_Acct_ID Additional Lembur Acct	  */
	public void setA_Lembur_Acct_ID (int A_Lembur_Acct_ID)
	{
		if (A_Lembur_Acct_ID < 1) 
			set_Value (COLUMNNAME_A_Lembur_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_A_Lembur_Acct_ID, Integer.valueOf(A_Lembur_Acct_ID));
	}

	/** Get Additional Lembur Acct.
		@return Additional Lembur Acct	  */
	public int getA_Lembur_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Lembur_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_OtherAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_OtherAcct_ID(), get_TrxName());	}

	/** Set Additional Other Acct.
		@param A_OtherAcct_ID Additional Other Acct	  */
	public void setA_OtherAcct_ID (int A_OtherAcct_ID)
	{
		if (A_OtherAcct_ID < 1) 
			set_Value (COLUMNNAME_A_OtherAcct_ID, null);
		else 
			set_Value (COLUMNNAME_A_OtherAcct_ID, Integer.valueOf(A_OtherAcct_ID));
	}

	/** Get Additional Other Acct.
		@return Additional Other Acct	  */
	public int getA_OtherAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_OtherAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_PremiAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_PremiAcct_ID(), get_TrxName());	}

	/** Set Additional Premi Acct.
		@param A_PremiAcct_ID Additional Premi Acct	  */
	public void setA_PremiAcct_ID (int A_PremiAcct_ID)
	{
		if (A_PremiAcct_ID < 1) 
			set_Value (COLUMNNAME_A_PremiAcct_ID, null);
		else 
			set_Value (COLUMNNAME_A_PremiAcct_ID, Integer.valueOf(A_PremiAcct_ID));
	}

	/** Get Additional Premi Acct.
		@return Additional Premi Acct	  */
	public int getA_PremiAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_PremiAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_RapelAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_RapelAcct_ID(), get_TrxName());	}

	/** Set Additional Rapel Acct.
		@param A_RapelAcct_ID Additional Rapel Acct	  */
	public void setA_RapelAcct_ID (int A_RapelAcct_ID)
	{
		if (A_RapelAcct_ID < 1) 
			set_Value (COLUMNNAME_A_RapelAcct_ID, null);
		else 
			set_Value (COLUMNNAME_A_RapelAcct_ID, Integer.valueOf(A_RapelAcct_ID));
	}

	/** Get Additional Rapel Acct.
		@return Additional Rapel Acct	  */
	public int getA_RapelAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_RapelAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Biaya Air / Liter.
		@param BiayaAir Biaya Air / Liter	  */
	public void setBiayaAir (BigDecimal BiayaAir)
	{
		set_Value (COLUMNNAME_BiayaAir, BiayaAir);
	}

	/** Get Biaya Air / Liter.
		@return Biaya Air / Liter	  */
	public BigDecimal getBiayaAir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BiayaAir);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getBiayaGajiBulananAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getBiayaGajiBulananAcct_ID(), get_TrxName());	}

	/** Set Biaya Gaji Bulanan Acct.
		@param BiayaGajiBulananAcct_ID Biaya Gaji Bulanan Acct	  */
	public void setBiayaGajiBulananAcct_ID (int BiayaGajiBulananAcct_ID)
	{
		if (BiayaGajiBulananAcct_ID < 1) 
			set_Value (COLUMNNAME_BiayaGajiBulananAcct_ID, null);
		else 
			set_Value (COLUMNNAME_BiayaGajiBulananAcct_ID, Integer.valueOf(BiayaGajiBulananAcct_ID));
	}

	/** Get Biaya Gaji Bulanan Acct.
		@return Biaya Gaji Bulanan Acct	  */
	public int getBiayaGajiBulananAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BiayaGajiBulananAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getBiayaGajiHarianAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getBiayaGajiHarianAcct_ID(), get_TrxName());	}

	/** Set Biaya Gaji Harian Acct.
		@param BiayaGajiHarianAcct_ID Biaya Gaji Harian Acct	  */
	public void setBiayaGajiHarianAcct_ID (int BiayaGajiHarianAcct_ID)
	{
		if (BiayaGajiHarianAcct_ID < 1) 
			set_Value (COLUMNNAME_BiayaGajiHarianAcct_ID, null);
		else 
			set_Value (COLUMNNAME_BiayaGajiHarianAcct_ID, Integer.valueOf(BiayaGajiHarianAcct_ID));
	}

	/** Get Biaya Gaji Harian Acct.
		@return Biaya Gaji Harian Acct	  */
	public int getBiayaGajiHarianAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BiayaGajiHarianAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Biaya Jabatan (%).
		@param BiayaJabatan 
		Biaya Jabatan In Percent
	  */
	public void setBiayaJabatan (BigDecimal BiayaJabatan)
	{
		set_Value (COLUMNNAME_BiayaJabatan, BiayaJabatan);
	}

	/** Get Biaya Jabatan (%).
		@return Biaya Jabatan In Percent
	  */
	public BigDecimal getBiayaJabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BiayaJabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Biaya Listrik / KWH.
		@param BiayaListrik Biaya Listrik / KWH	  */
	public void setBiayaListrik (BigDecimal BiayaListrik)
	{
		set_Value (COLUMNNAME_BiayaListrik, BiayaListrik);
	}

	/** Get Biaya Listrik / KWH.
		@return Biaya Listrik / KWH	  */
	public BigDecimal getBiayaListrik () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BiayaListrik);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getB_JHTAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_JHTAcct_ID(), get_TrxName());	}

	/** Set Biaya JHT Acct.
		@param B_JHTAcct_ID Biaya JHT Acct	  */
	public void setB_JHTAcct_ID (int B_JHTAcct_ID)
	{
		if (B_JHTAcct_ID < 1) 
			set_Value (COLUMNNAME_B_JHTAcct_ID, null);
		else 
			set_Value (COLUMNNAME_B_JHTAcct_ID, Integer.valueOf(B_JHTAcct_ID));
	}

	/** Get Biaya JHT Acct.
		@return Biaya JHT Acct	  */
	public int getB_JHTAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_JHTAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_JKAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_JKAcct_ID(), get_TrxName());	}

	/** Set Biaya JK Acct.
		@param B_JKAcct_ID Biaya JK Acct	  */
	public void setB_JKAcct_ID (int B_JKAcct_ID)
	{
		if (B_JKAcct_ID < 1) 
			set_Value (COLUMNNAME_B_JKAcct_ID, null);
		else 
			set_Value (COLUMNNAME_B_JKAcct_ID, Integer.valueOf(B_JKAcct_ID));
	}

	/** Get Biaya JK Acct.
		@return Biaya JK Acct	  */
	public int getB_JKAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_JKAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_JKKAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_JKKAcct_ID(), get_TrxName());	}

	/** Set Biaya JKK Acct.
		@param B_JKKAcct_ID Biaya JKK Acct	  */
	public void setB_JKKAcct_ID (int B_JKKAcct_ID)
	{
		if (B_JKKAcct_ID < 1) 
			set_Value (COLUMNNAME_B_JKKAcct_ID, null);
		else 
			set_Value (COLUMNNAME_B_JKKAcct_ID, Integer.valueOf(B_JKKAcct_ID));
	}

	/** Get Biaya JKK Acct.
		@return Biaya JKK Acct	  */
	public int getB_JKKAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_JKKAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_JPAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_JPAcct_ID(), get_TrxName());	}

	/** Set Biaya JP Acct.
		@param B_JPAcct_ID Biaya JP Acct	  */
	public void setB_JPAcct_ID (int B_JPAcct_ID)
	{
		if (B_JPAcct_ID < 1) 
			set_Value (COLUMNNAME_B_JPAcct_ID, null);
		else 
			set_Value (COLUMNNAME_B_JPAcct_ID, Integer.valueOf(B_JPAcct_ID));
	}

	/** Get Biaya JP Acct.
		@return Biaya JP Acct	  */
	public int getB_JPAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_JPAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_JPKAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_JPKAcct_ID(), get_TrxName());	}

	/** Set Biaya JPK Acct.
		@param B_JPKAcct_ID Biaya JPK Acct	  */
	public void setB_JPKAcct_ID (int B_JPKAcct_ID)
	{
		if (B_JPKAcct_ID < 1) 
			set_Value (COLUMNNAME_B_JPKAcct_ID, null);
		else 
			set_Value (COLUMNNAME_B_JPKAcct_ID, Integer.valueOf(B_JPKAcct_ID));
	}

	/** Get Biaya JPK Acct.
		@return Biaya JPK Acct	  */
	public int getB_JPKAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_JPKAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	public I_C_ValidCombination getE_Medical_Expense_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getE_Medical_Expense_Acct(), get_TrxName());	}

	/** Set Medical Expense Acct.
		@param E_Medical_Expense_Acct 
		The account to post medical expense.
	  */
	public void setE_Medical_Expense_Acct (int E_Medical_Expense_Acct)
	{
		set_Value (COLUMNNAME_E_Medical_Expense_Acct, Integer.valueOf(E_Medical_Expense_Acct));
	}

	/** Get Medical Expense Acct.
		@return The account to post medical expense.
	  */
	public int getE_Medical_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_E_Medical_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getH_JHT_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getH_JHT_Acct_ID(), get_TrxName());	}

	/** Set Hutang JHT Acct.
		@param H_JHT_Acct_ID 
		Jaminan Hari Tua Account
	  */
	public void setH_JHT_Acct_ID (int H_JHT_Acct_ID)
	{
		if (H_JHT_Acct_ID < 1) 
			set_Value (COLUMNNAME_H_JHT_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_H_JHT_Acct_ID, Integer.valueOf(H_JHT_Acct_ID));
	}

	/** Get Hutang JHT Acct.
		@return Jaminan Hari Tua Account
	  */
	public int getH_JHT_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_H_JHT_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getH_JK_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getH_JK_Acct_ID(), get_TrxName());	}

	/** Set Hutang JK Acct.
		@param H_JK_Acct_ID 
		Jaminan Kematian Account
	  */
	public void setH_JK_Acct_ID (int H_JK_Acct_ID)
	{
		if (H_JK_Acct_ID < 1) 
			set_Value (COLUMNNAME_H_JK_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_H_JK_Acct_ID, Integer.valueOf(H_JK_Acct_ID));
	}

	/** Get Hutang JK Acct.
		@return Jaminan Kematian Account
	  */
	public int getH_JK_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_H_JK_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getH_JKK_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getH_JKK_Acct_ID(), get_TrxName());	}

	/** Set Hutang JKK Acct.
		@param H_JKK_Acct_ID 
		Jaminan Kecelakaan Kerha Account
	  */
	public void setH_JKK_Acct_ID (int H_JKK_Acct_ID)
	{
		if (H_JKK_Acct_ID < 1) 
			set_Value (COLUMNNAME_H_JKK_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_H_JKK_Acct_ID, Integer.valueOf(H_JKK_Acct_ID));
	}

	/** Get Hutang JKK Acct.
		@return Jaminan Kecelakaan Kerha Account
	  */
	public int getH_JKK_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_H_JKK_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getH_JPAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getH_JPAcct_ID(), get_TrxName());	}

	/** Set Hutang JP Acct.
		@param H_JPAcct_ID Hutang JP Acct	  */
	public void setH_JPAcct_ID (int H_JPAcct_ID)
	{
		if (H_JPAcct_ID < 1) 
			set_Value (COLUMNNAME_H_JPAcct_ID, null);
		else 
			set_Value (COLUMNNAME_H_JPAcct_ID, Integer.valueOf(H_JPAcct_ID));
	}

	/** Get Hutang JP Acct.
		@return Hutang JP Acct	  */
	public int getH_JPAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_H_JPAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getH_JPK_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getH_JPK_Acct_ID(), get_TrxName());	}

	/** Set Hutang JPK Acct.
		@param H_JPK_Acct_ID 
		Jaminan Pemeliharaan Kesehatan Account
	  */
	public void setH_JPK_Acct_ID (int H_JPK_Acct_ID)
	{
		if (H_JPK_Acct_ID < 1) 
			set_Value (COLUMNNAME_H_JPK_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_H_JPK_Acct_ID, Integer.valueOf(H_JPK_Acct_ID));
	}

	/** Get Hutang JPK Acct.
		@return Jaminan Pemeliharaan Kesehatan Account
	  */
	public int getH_JPK_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_H_JPK_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Holiday Work Hours.
		@param HolidayWorkHours Holiday Work Hours	  */
	public void setHolidayWorkHours (BigDecimal HolidayWorkHours)
	{
		set_Value (COLUMNNAME_HolidayWorkHours, HolidayWorkHours);
	}

	/** Get Holiday Work Hours.
		@return Holiday Work Hours	  */
	public BigDecimal getHolidayWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HolidayWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Holiday Work Hours (S).
		@param HolidayWorkHoursShift Holiday Work Hours (S)	  */
	public void setHolidayWorkHoursShift (BigDecimal HolidayWorkHoursShift)
	{
		set_Value (COLUMNNAME_HolidayWorkHoursShift, HolidayWorkHoursShift);
	}

	/** Get Holiday Work Hours (S).
		@return Holiday Work Hours (S)	  */
	public BigDecimal getHolidayWorkHoursShift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HolidayWorkHoursShift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getHutangGajiBulananAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getHutangGajiBulananAcct_ID(), get_TrxName());	}

	/** Set Hutang Gaji Bulanan Acct.
		@param HutangGajiBulananAcct_ID Hutang Gaji Bulanan Acct	  */
	public void setHutangGajiBulananAcct_ID (int HutangGajiBulananAcct_ID)
	{
		if (HutangGajiBulananAcct_ID < 1) 
			set_Value (COLUMNNAME_HutangGajiBulananAcct_ID, null);
		else 
			set_Value (COLUMNNAME_HutangGajiBulananAcct_ID, Integer.valueOf(HutangGajiBulananAcct_ID));
	}

	/** Get Hutang Gaji Bulanan Acct.
		@return Hutang Gaji Bulanan Acct	  */
	public int getHutangGajiBulananAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HutangGajiBulananAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getHutangGajiHarianAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getHutangGajiHarianAcct_ID(), get_TrxName());	}

	/** Set Hutang Gaji Harian Acct.
		@param HutangGajiHarianAcct_ID Hutang Gaji Harian Acct	  */
	public void setHutangGajiHarianAcct_ID (int HutangGajiHarianAcct_ID)
	{
		if (HutangGajiHarianAcct_ID < 1) 
			set_Value (COLUMNNAME_HutangGajiHarianAcct_ID, null);
		else 
			set_Value (COLUMNNAME_HutangGajiHarianAcct_ID, Integer.valueOf(HutangGajiHarianAcct_ID));
	}

	/** Get Hutang Gaji Harian Acct.
		@return Hutang Gaji Harian Acct	  */
	public int getHutangGajiHarianAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HutangGajiHarianAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getHutangUpahBuruhDirectAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getHutangUpahBuruhDirectAcct_ID(), get_TrxName());	}

	/** Set Hutang Upah Buruh Direct Acct.
		@param HutangUpahBuruhDirectAcct_ID Hutang Upah Buruh Direct Acct	  */
	public void setHutangUpahBuruhDirectAcct_ID (int HutangUpahBuruhDirectAcct_ID)
	{
		if (HutangUpahBuruhDirectAcct_ID < 1) 
			set_Value (COLUMNNAME_HutangUpahBuruhDirectAcct_ID, null);
		else 
			set_Value (COLUMNNAME_HutangUpahBuruhDirectAcct_ID, Integer.valueOf(HutangUpahBuruhDirectAcct_ID));
	}

	/** Get Hutang Upah Buruh Direct Acct.
		@return Hutang Upah Buruh Direct Acct	  */
	public int getHutangUpahBuruhDirectAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HutangUpahBuruhDirectAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getHutangUpahBuruhInDirectAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getHutangUpahBuruhInDirectAcct_ID(), get_TrxName());	}

	/** Set Hutang Upah Buruh Indirect Acct.
		@param HutangUpahBuruhInDirectAcct_ID Hutang Upah Buruh Indirect Acct	  */
	public void setHutangUpahBuruhInDirectAcct_ID (int HutangUpahBuruhInDirectAcct_ID)
	{
		if (HutangUpahBuruhInDirectAcct_ID < 1) 
			set_Value (COLUMNNAME_HutangUpahBuruhInDirectAcct_ID, null);
		else 
			set_Value (COLUMNNAME_HutangUpahBuruhInDirectAcct_ID, Integer.valueOf(HutangUpahBuruhInDirectAcct_ID));
	}

	/** Get Hutang Upah Buruh Indirect Acct.
		@return Hutang Upah Buruh Indirect Acct	  */
	public int getHutangUpahBuruhInDirectAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HutangUpahBuruhInDirectAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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

	/** Set Calculate PPH21 Buruh.
		@param IsCalculatePPH21Buruh Calculate PPH21 Buruh	  */
	public void setIsCalculatePPH21Buruh (boolean IsCalculatePPH21Buruh)
	{
		set_Value (COLUMNNAME_IsCalculatePPH21Buruh, Boolean.valueOf(IsCalculatePPH21Buruh));
	}

	/** Get Calculate PPH21 Buruh.
		@return Calculate PPH21 Buruh	  */
	public boolean isCalculatePPH21Buruh () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculatePPH21Buruh);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JHT Applied.
		@param IsJHTApplyed 
		centang jika memiliki jaminan hari tua
	  */
	public void setIsJHTApplyed (boolean IsJHTApplyed)
	{
		set_Value (COLUMNNAME_IsJHTApplyed, Boolean.valueOf(IsJHTApplyed));
	}

	/** Get JHT Applied.
		@return centang jika memiliki jaminan hari tua
	  */
	public boolean isJHTApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJHTApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JK Applied.
		@param IsJKApplyed 
		centang jika memiliki jaminan kematian
	  */
	public void setIsJKApplyed (boolean IsJKApplyed)
	{
		set_Value (COLUMNNAME_IsJKApplyed, Boolean.valueOf(IsJKApplyed));
	}

	/** Get JK Applied.
		@return centang jika memiliki jaminan kematian
	  */
	public boolean isJKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JKK Applied.
		@param IsJKKApplyed 
		centang jika memiliki jaminan kecelakaan kerja
	  */
	public void setIsJKKApplyed (boolean IsJKKApplyed)
	{
		set_Value (COLUMNNAME_IsJKKApplyed, Boolean.valueOf(IsJKKApplyed));
	}

	/** Get JKK Applied.
		@return centang jika memiliki jaminan kecelakaan kerja
	  */
	public boolean isJKKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJKKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JP Applied.
		@param IsJPApplied JP Applied	  */
	public void setIsJPApplied (boolean IsJPApplied)
	{
		set_Value (COLUMNNAME_IsJPApplied, Boolean.valueOf(IsJPApplied));
	}

	/** Get JP Applied.
		@return JP Applied	  */
	public boolean isJPApplied () 
	{
		Object oo = get_Value(COLUMNNAME_IsJPApplied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JPK Applied.
		@param IsJPKApplyed 
		centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public void setIsJPKApplyed (boolean IsJPKApplyed)
	{
		set_Value (COLUMNNAME_IsJPKApplyed, Boolean.valueOf(IsJPKApplyed));
	}

	/** Get JPK Applied.
		@return centang jika memiliki jaminan pemeliharan kesehatan
	  */
	public boolean isJPKApplyed () 
	{
		Object oo = get_Value(COLUMNNAME_IsJPKApplyed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Base On Brutto Salary = BGB */
	public static final String JHTBASICCALCULATION_BaseOnBruttoSalary = "BGB";
	/** Base On Netto Salary = BGN */
	public static final String JHTBASICCALCULATION_BaseOnNettoSalary = "BGN";
	/** Base On Basic Salary = BGP */
	public static final String JHTBASICCALCULATION_BaseOnBasicSalary = "BGP";
	/** Base On UMK = BOU */
	public static final String JHTBASICCALCULATION_BaseOnUMK = "BOU";
	/** Base On UMP = BOP */
	public static final String JHTBASICCALCULATION_BaseOnUMP = "BOP";
	/** Set JHT Basic Calculation.
		@param JHTBasicCalculation JHT Basic Calculation	  */
	public void setJHTBasicCalculation (String JHTBasicCalculation)
	{

		set_Value (COLUMNNAME_JHTBasicCalculation, JHTBasicCalculation);
	}

	/** Get JHT Basic Calculation.
		@return JHT Basic Calculation	  */
	public String getJHTBasicCalculation () 
	{
		return (String)get_Value(COLUMNNAME_JHTBasicCalculation);
	}

	/** Set JHT Borongan.
		@param JHTBorongan 
		Jaminan Hari Tua Borongan
	  */
	public void setJHTBorongan (BigDecimal JHTBorongan)
	{
		set_Value (COLUMNNAME_JHTBorongan, JHTBorongan);
	}

	/** Get JHT Borongan.
		@return Jaminan Hari Tua Borongan
	  */
	public BigDecimal getJHTBorongan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JHTBorongan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JHT Paid By Comp(%).
		@param JHTPaidByCompany 
		JHT Paid By Company in percent
	  */
	public void setJHTPaidByCompany (BigDecimal JHTPaidByCompany)
	{
		set_Value (COLUMNNAME_JHTPaidByCompany, JHTPaidByCompany);
	}

	/** Get JHT Paid By Comp(%).
		@return JHT Paid By Company in percent
	  */
	public BigDecimal getJHTPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JHTPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JHT Paid By Employee (%).
		@param JHTPaidByEmployee 
		JHT Paid By Employee In Percent
	  */
	public void setJHTPaidByEmployee (BigDecimal JHTPaidByEmployee)
	{
		set_Value (COLUMNNAME_JHTPaidByEmployee, JHTPaidByEmployee);
	}

	/** Get JHT Paid By Employee (%).
		@return JHT Paid By Employee In Percent
	  */
	public BigDecimal getJHTPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JHTPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Base On Brutto Salary = BGB */
	public static final String JKBASICCALCULATION_BaseOnBruttoSalary = "BGB";
	/** Base On Netto Salary = BGN */
	public static final String JKBASICCALCULATION_BaseOnNettoSalary = "BGN";
	/** Base On Basic Salary = BGP */
	public static final String JKBASICCALCULATION_BaseOnBasicSalary = "BGP";
	/** Base On UMK = BOU */
	public static final String JKBASICCALCULATION_BaseOnUMK = "BOU";
	/** Base On UMP = BOP */
	public static final String JKBASICCALCULATION_BaseOnUMP = "BOP";
	/** Set JK Basic Calculation.
		@param JKBasicCalculation JK Basic Calculation	  */
	public void setJKBasicCalculation (String JKBasicCalculation)
	{

		set_Value (COLUMNNAME_JKBasicCalculation, JKBasicCalculation);
	}

	/** Get JK Basic Calculation.
		@return JK Basic Calculation	  */
	public String getJKBasicCalculation () 
	{
		return (String)get_Value(COLUMNNAME_JKBasicCalculation);
	}

	/** Set JK Borongan.
		@param JKBorongan 
		Jaminan Kematian Borongan
	  */
	public void setJKBorongan (BigDecimal JKBorongan)
	{
		set_Value (COLUMNNAME_JKBorongan, JKBorongan);
	}

	/** Get JK Borongan.
		@return Jaminan Kematian Borongan
	  */
	public BigDecimal getJKBorongan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKBorongan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Base On Brutto Salary = BGB */
	public static final String JKKBASICCALCULATION_BaseOnBruttoSalary = "BGB";
	/** Base On Netto Salary = BGN */
	public static final String JKKBASICCALCULATION_BaseOnNettoSalary = "BGN";
	/** Base On Basic Salary = BGP */
	public static final String JKKBASICCALCULATION_BaseOnBasicSalary = "BGP";
	/** Base On UMK = BOU */
	public static final String JKKBASICCALCULATION_BaseOnUMK = "BOU";
	/** Base On UMP = BOP */
	public static final String JKKBASICCALCULATION_BaseOnUMP = "BOP";
	/** Set JKK Basic Calculation.
		@param JKKBasicCalculation JKK Basic Calculation	  */
	public void setJKKBasicCalculation (String JKKBasicCalculation)
	{

		set_Value (COLUMNNAME_JKKBasicCalculation, JKKBasicCalculation);
	}

	/** Get JKK Basic Calculation.
		@return JKK Basic Calculation	  */
	public String getJKKBasicCalculation () 
	{
		return (String)get_Value(COLUMNNAME_JKKBasicCalculation);
	}

	/** Set JKK Borongan.
		@param JKKBorongan 
		Jaminan Kecelakaan Kerja Borongan
	  */
	public void setJKKBorongan (BigDecimal JKKBorongan)
	{
		set_Value (COLUMNNAME_JKKBorongan, JKKBorongan);
	}

	/** Get JKK Borongan.
		@return Jaminan Kecelakaan Kerja Borongan
	  */
	public BigDecimal getJKKBorongan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKKBorongan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JKK Paid By Comp(%).
		@param JKKPaidByCompany 
		JKK Paid By Company in percent
	  */
	public void setJKKPaidByCompany (BigDecimal JKKPaidByCompany)
	{
		set_Value (COLUMNNAME_JKKPaidByCompany, JKKPaidByCompany);
	}

	/** Get JKK Paid By Comp(%).
		@return JKK Paid By Company in percent
	  */
	public BigDecimal getJKKPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKKPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JKK Paid By Employee (%).
		@param JKKPaidByEmployee 
		JKK Paid By Employee In Percent
	  */
	public void setJKKPaidByEmployee (BigDecimal JKKPaidByEmployee)
	{
		set_Value (COLUMNNAME_JKKPaidByEmployee, JKKPaidByEmployee);
	}

	/** Get JKK Paid By Employee (%).
		@return JKK Paid By Employee In Percent
	  */
	public BigDecimal getJKKPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKKPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JK Paid By Comp(%).
		@param JKPaidByCompany 
		JK Paid By Company in percent
	  */
	public void setJKPaidByCompany (BigDecimal JKPaidByCompany)
	{
		set_Value (COLUMNNAME_JKPaidByCompany, JKPaidByCompany);
	}

	/** Get JK Paid By Comp(%).
		@return JK Paid By Company in percent
	  */
	public BigDecimal getJKPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JK Paid By Employee (%).
		@param JKPaidByEmployee 
		JK Paid By Employee In Percent
	  */
	public void setJKPaidByEmployee (BigDecimal JKPaidByEmployee)
	{
		set_Value (COLUMNNAME_JKPaidByEmployee, JKPaidByEmployee);
	}

	/** Get JK Paid By Employee (%).
		@return JK Paid By Employee In Percent
	  */
	public BigDecimal getJKPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JKPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Base On Brutto Salary = BGB */
	public static final String JPBASICCALCULATION_BaseOnBruttoSalary = "BGB";
	/** Base On Netto Salary = BGN */
	public static final String JPBASICCALCULATION_BaseOnNettoSalary = "BGN";
	/** Base On Basic Salary = BGP */
	public static final String JPBASICCALCULATION_BaseOnBasicSalary = "BGP";
	/** Base On UMK = BOU */
	public static final String JPBASICCALCULATION_BaseOnUMK = "BOU";
	/** Base On UMP = BOP */
	public static final String JPBASICCALCULATION_BaseOnUMP = "BOP";
	/** Set JP Basic Calculation.
		@param JPBasicCalculation JP Basic Calculation	  */
	public void setJPBasicCalculation (String JPBasicCalculation)
	{

		set_Value (COLUMNNAME_JPBasicCalculation, JPBasicCalculation);
	}

	/** Get JP Basic Calculation.
		@return JP Basic Calculation	  */
	public String getJPBasicCalculation () 
	{
		return (String)get_Value(COLUMNNAME_JPBasicCalculation);
	}

	/** Base On Brutto Salary = BGB */
	public static final String JPKBASICCALCULATION_BaseOnBruttoSalary = "BGB";
	/** Base On Netto Salary = BGN */
	public static final String JPKBASICCALCULATION_BaseOnNettoSalary = "BGN";
	/** Base On Basic Salary = BGP */
	public static final String JPKBASICCALCULATION_BaseOnBasicSalary = "BGP";
	/** Base On UMK = BOU */
	public static final String JPKBASICCALCULATION_BaseOnUMK = "BOU";
	/** Base On UMP = BOP */
	public static final String JPKBASICCALCULATION_BaseOnUMP = "BOP";
	/** Set JPK Basic Calculation.
		@param JPKBasicCalculation JPK Basic Calculation	  */
	public void setJPKBasicCalculation (String JPKBasicCalculation)
	{

		set_Value (COLUMNNAME_JPKBasicCalculation, JPKBasicCalculation);
	}

	/** Get JPK Basic Calculation.
		@return JPK Basic Calculation	  */
	public String getJPKBasicCalculation () 
	{
		return (String)get_Value(COLUMNNAME_JPKBasicCalculation);
	}

	/** Set JPK Borongan.
		@param JPKBorongan 
		Jaminan Pemeliharaan Kesehatan Borongan
	  */
	public void setJPKBorongan (BigDecimal JPKBorongan)
	{
		set_Value (COLUMNNAME_JPKBorongan, JPKBorongan);
	}

	/** Get JPK Borongan.
		@return Jaminan Pemeliharaan Kesehatan Borongan
	  */
	public BigDecimal getJPKBorongan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPKBorongan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JPK Kawin Paid By Comp(%).
		@param JPKKawinPaidByCompany 
		JPK Kawin Paid By Conpany in percent
	  */
	public void setJPKKawinPaidByCompany (BigDecimal JPKKawinPaidByCompany)
	{
		set_Value (COLUMNNAME_JPKKawinPaidByCompany, JPKKawinPaidByCompany);
	}

	/** Get JPK Kawin Paid By Comp(%).
		@return JPK Kawin Paid By Conpany in percent
	  */
	public BigDecimal getJPKKawinPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPKKawinPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JPK Kawin Paid By Emp(%).
		@param JPKKawinPaidByEmployee 
		JPK Kawin Paid By Employee in percent
	  */
	public void setJPKKawinPaidByEmployee (BigDecimal JPKKawinPaidByEmployee)
	{
		set_Value (COLUMNNAME_JPKKawinPaidByEmployee, JPKKawinPaidByEmployee);
	}

	/** Get JPK Kawin Paid By Emp(%).
		@return JPK Kawin Paid By Employee in percent
	  */
	public BigDecimal getJPKKawinPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPKKawinPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JPK Lajang Paid By Comp(%).
		@param JPKLajangPaidByCompany 
		JPK Lajang Paid By Company in percent
	  */
	public void setJPKLajangPaidByCompany (BigDecimal JPKLajangPaidByCompany)
	{
		set_Value (COLUMNNAME_JPKLajangPaidByCompany, JPKLajangPaidByCompany);
	}

	/** Get JPK Lajang Paid By Comp(%).
		@return JPK Lajang Paid By Company in percent
	  */
	public BigDecimal getJPKLajangPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPKLajangPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JPK Lajang Paid By Emp(%).
		@param JPKLajangPaidByEmployee 
		JPK Lajang Paid By Company in percent
	  */
	public void setJPKLajangPaidByEmployee (BigDecimal JPKLajangPaidByEmployee)
	{
		set_Value (COLUMNNAME_JPKLajangPaidByEmployee, JPKLajangPaidByEmployee);
	}

	/** Get JPK Lajang Paid By Emp(%).
		@return JPK Lajang Paid By Company in percent
	  */
	public BigDecimal getJPKLajangPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPKLajangPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JP Paid By Company (%).
		@param JPPaidByCompany JP Paid By Company (%)	  */
	public void setJPPaidByCompany (BigDecimal JPPaidByCompany)
	{
		set_Value (COLUMNNAME_JPPaidByCompany, JPPaidByCompany);
	}

	/** Get JP Paid By Company (%).
		@return JP Paid By Company (%)	  */
	public BigDecimal getJPPaidByCompany () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPPaidByCompany);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JP Paid By Employee (%).
		@param JPPaidByEmployee JP Paid By Employee (%)	  */
	public void setJPPaidByEmployee (BigDecimal JPPaidByEmployee)
	{
		set_Value (COLUMNNAME_JPPaidByEmployee, JPPaidByEmployee);
	}

	/** Get JP Paid By Employee (%).
		@return JP Paid By Employee (%)	  */
	public BigDecimal getJPPaidByEmployee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JPPaidByEmployee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set K0.
		@param K0 
		Kawind dan 0 tanggungan
	  */
	public void setK0 (BigDecimal K0)
	{
		set_Value (COLUMNNAME_K0, K0);
	}

	/** Get K0.
		@return Kawind dan 0 tanggungan
	  */
	public BigDecimal getK0 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_K0);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set K1.
		@param K1 
		Kawind dengan 1 tanggungan
	  */
	public void setK1 (BigDecimal K1)
	{
		set_Value (COLUMNNAME_K1, K1);
	}

	/** Get K1.
		@return Kawind dengan 1 tanggungan
	  */
	public BigDecimal getK1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_K1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set K2.
		@param K2 
		Kawind dengan 2 tanggungan
	  */
	public void setK2 (BigDecimal K2)
	{
		set_Value (COLUMNNAME_K2, K2);
	}

	/** Get K2.
		@return Kawind dengan 2 tanggungan
	  */
	public BigDecimal getK2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_K2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set K3.
		@param K3 
		Kawind dengan 3 tanggungan
	  */
	public void setK3 (BigDecimal K3)
	{
		set_Value (COLUMNNAME_K3, K3);
	}

	/** Get K3.
		@return Kawind dengan 3 tanggungan
	  */
	public BigDecimal getK3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_K3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sampai Dengan.
		@param Level1 Sampai Dengan	  */
	public void setLevel1 (BigDecimal Level1)
	{
		set_Value (COLUMNNAME_Level1, Level1);
	}

	/** Get Sampai Dengan.
		@return Sampai Dengan	  */
	public BigDecimal getLevel1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sampai Dengan.
		@param Level2 Sampai Dengan	  */
	public void setLevel2 (BigDecimal Level2)
	{
		set_Value (COLUMNNAME_Level2, Level2);
	}

	/** Get Sampai Dengan.
		@return Sampai Dengan	  */
	public BigDecimal getLevel2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sampai Dengan.
		@param Level3 Sampai Dengan	  */
	public void setLevel3 (BigDecimal Level3)
	{
		set_Value (COLUMNNAME_Level3, Level3);
	}

	/** Get Sampai Dengan.
		@return Sampai Dengan	  */
	public BigDecimal getLevel3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sampai Dengan.
		@param Level4 Sampai Dengan	  */
	public void setLevel4 (BigDecimal Level4)
	{
		set_Value (COLUMNNAME_Level4, Level4);
	}

	/** Get Sampai Dengan.
		@return Sampai Dengan	  */
	public BigDecimal getLevel4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Di Atas.
		@param Level5 Di Atas	  */
	public void setLevel5 (BigDecimal Level5)
	{
		set_Value (COLUMNNAME_Level5, Level5);
	}

	/** Get Di Atas.
		@return Di Atas	  */
	public BigDecimal getLevel5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Maximum.
		@param MaxBiayaJabatan Maximum	  */
	public void setMaxBiayaJabatan (BigDecimal MaxBiayaJabatan)
	{
		set_Value (COLUMNNAME_MaxBiayaJabatan, MaxBiayaJabatan);
	}

	/** Get Maximum.
		@return Maximum	  */
	public BigDecimal getMaxBiayaJabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxBiayaJabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max Gaji To Calc JPK.
		@param MaxGajiToCalcJPK Max Gaji To Calc JPK	  */
	public void setMaxGajiToCalcJPK (BigDecimal MaxGajiToCalcJPK)
	{
		set_Value (COLUMNNAME_MaxGajiToCalcJPK, MaxGajiToCalcJPK);
	}

	/** Get Max Gaji To Calc JPK.
		@return Max Gaji To Calc JPK	  */
	public BigDecimal getMaxGajiToCalcJPK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxGajiToCalcJPK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max LD 1.
		@param MaxLD1 Max LD 1	  */
	public void setMaxLD1 (int MaxLD1)
	{
		set_Value (COLUMNNAME_MaxLD1, Integer.valueOf(MaxLD1));
	}

	/** Get Max LD 1.
		@return Max LD 1	  */
	public int getMaxLD1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxLD1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max LD 2.
		@param MaxLD2 Max LD 2	  */
	public void setMaxLD2 (int MaxLD2)
	{
		set_Value (COLUMNNAME_MaxLD2, Integer.valueOf(MaxLD2));
	}

	/** Get Max LD 2.
		@return Max LD 2	  */
	public int getMaxLD2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxLD2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Biaya Medis Karyawan.
		@param MedicalKaryawan Biaya Medis Karyawan	  */
	public void setMedicalKaryawan (BigDecimal MedicalKaryawan)
	{
		set_Value (COLUMNNAME_MedicalKaryawan, MedicalKaryawan);
	}

	/** Get Biaya Medis Karyawan.
		@return Biaya Medis Karyawan	  */
	public BigDecimal getMedicalKaryawan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalKaryawan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Biaya Medis Non Karyawan.
		@param MedicalNonKaryawan Biaya Medis Non Karyawan	  */
	public void setMedicalNonKaryawan (BigDecimal MedicalNonKaryawan)
	{
		set_Value (COLUMNNAME_MedicalNonKaryawan, MedicalNonKaryawan);
	}

	/** Get Biaya Medis Non Karyawan.
		@return Biaya Medis Non Karyawan	  */
	public BigDecimal getMedicalNonKaryawan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalNonKaryawan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min. Electricity Usage (KWH).
		@param MinElectricityUsage 
		The minimum usage amount of electricity
	  */
	public void setMinElectricityUsage (BigDecimal MinElectricityUsage)
	{
		set_Value (COLUMNNAME_MinElectricityUsage, MinElectricityUsage);
	}

	/** Get Min. Electricity Usage (KWH).
		@return The minimum usage amount of electricity
	  */
	public BigDecimal getMinElectricityUsage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinElectricityUsage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min. Water Usage (Liter).
		@param MinWaterUsage Min. Water Usage (Liter)	  */
	public void setMinWaterUsage (BigDecimal MinWaterUsage)
	{
		set_Value (COLUMNNAME_MinWaterUsage, MinWaterUsage);
	}

	/** Get Min. Water Usage (Liter).
		@return Min. Water Usage (Liter)	  */
	public BigDecimal getMinWaterUsage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinWaterUsage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set All Work Days are OT.
		@param NonShiftAllWorkDayIsOT 
		To indicate that overtime allowance for non-shift-based employee is calculated to all work days
	  */
	public void setNonShiftAllWorkDayIsOT (boolean NonShiftAllWorkDayIsOT)
	{
		set_Value (COLUMNNAME_NonShiftAllWorkDayIsOT, Boolean.valueOf(NonShiftAllWorkDayIsOT));
	}

	/** Get All Work Days are OT.
		@return To indicate that overtime allowance for non-shift-based employee is calculated to all work days
	  */
	public boolean isNonShiftAllWorkDayIsOT () 
	{
		Object oo = get_Value(COLUMNNAME_NonShiftAllWorkDayIsOT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OT Allowance Hours.
		@param NonShiftOTAllowanceHours 
		Number of hours in month used as overtime calculation basis for non-shift-based employee
	  */
	public void setNonShiftOTAllowanceHours (BigDecimal NonShiftOTAllowanceHours)
	{
		set_Value (COLUMNNAME_NonShiftOTAllowanceHours, NonShiftOTAllowanceHours);
	}

	/** Get OT Allowance Hours.
		@return Number of hours in month used as overtime calculation basis for non-shift-based employee
	  */
	public BigDecimal getNonShiftOTAllowanceHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NonShiftOTAllowanceHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Saturday = 7 */
	public static final String NONSHIFTOTDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String NONSHIFTOTDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String NONSHIFTOTDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String NONSHIFTOTDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String NONSHIFTOTDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String NONSHIFTOTDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String NONSHIFTOTDAY_Sunday = "1";
	/** Set OT Work Day.
		@param NonShiftOTDay 
		The work day assumes as OT base-day of non-shift-based employee
	  */
	public void setNonShiftOTDay (String NonShiftOTDay)
	{

		set_Value (COLUMNNAME_NonShiftOTDay, NonShiftOTDay);
	}

	/** Get OT Work Day.
		@return The work day assumes as OT base-day of non-shift-based employee
	  */
	public String getNonShiftOTDay () 
	{
		return (String)get_Value(COLUMNNAME_NonShiftOTDay);
	}

	/** Set OT DeductionMultiplier.
		@param NonShiftOTDeductionMultiplier 
		Number of multiplier to non-shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public void setNonShiftOTDeductionMultiplier (BigDecimal NonShiftOTDeductionMultiplier)
	{
		set_Value (COLUMNNAME_NonShiftOTDeductionMultiplier, NonShiftOTDeductionMultiplier);
	}

	/** Get OT DeductionMultiplier.
		@return Number of multiplier to non-shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public BigDecimal getNonShiftOTDeductionMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NonShiftOTDeductionMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Normal Day Work Hours.
		@param NormalDayWorkHours Normal Day Work Hours	  */
	public void setNormalDayWorkHours (BigDecimal NormalDayWorkHours)
	{
		set_Value (COLUMNNAME_NormalDayWorkHours, NormalDayWorkHours);
	}

	/** Get Normal Day Work Hours.
		@return Normal Day Work Hours	  */
	public BigDecimal getNormalDayWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NormalDayWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Normal Day Work Hours (S).
		@param NormalDayWorkHoursShift Normal Day Work Hours (S)	  */
	public void setNormalDayWorkHoursShift (BigDecimal NormalDayWorkHoursShift)
	{
		set_Value (COLUMNNAME_NormalDayWorkHoursShift, NormalDayWorkHoursShift);
	}

	/** Get Normal Day Work Hours (S).
		@return Normal Day Work Hours (S)	  */
	public BigDecimal getNormalDayWorkHoursShift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NormalDayWorkHoursShift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak (%).
		@param PajakLevel1 
		Pajak In Percent
	  */
	public void setPajakLevel1 (BigDecimal PajakLevel1)
	{
		set_Value (COLUMNNAME_PajakLevel1, PajakLevel1);
	}

	/** Get Pajak (%).
		@return Pajak In Percent
	  */
	public BigDecimal getPajakLevel1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakLevel1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak (%).
		@param PajakLevel2 
		Pajak In Percent
	  */
	public void setPajakLevel2 (BigDecimal PajakLevel2)
	{
		set_Value (COLUMNNAME_PajakLevel2, PajakLevel2);
	}

	/** Get Pajak (%).
		@return Pajak In Percent
	  */
	public BigDecimal getPajakLevel2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakLevel2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak (%).
		@param PajakLevel3 
		Pajak In Percent
	  */
	public void setPajakLevel3 (BigDecimal PajakLevel3)
	{
		set_Value (COLUMNNAME_PajakLevel3, PajakLevel3);
	}

	/** Get Pajak (%).
		@return Pajak In Percent
	  */
	public BigDecimal getPajakLevel3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakLevel3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak (%).
		@param PajakLevel4 
		Pajak In Percent
	  */
	public void setPajakLevel4 (BigDecimal PajakLevel4)
	{
		set_Value (COLUMNNAME_PajakLevel4, PajakLevel4);
	}

	/** Get Pajak (%).
		@return Pajak In Percent
	  */
	public BigDecimal getPajakLevel4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakLevel4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak (%).
		@param PajakLevel5 
		Pajak In Percent
	  */
	public void setPajakLevel5 (BigDecimal PajakLevel5)
	{
		set_Value (COLUMNNAME_PajakLevel5, PajakLevel5);
	}

	/** Get Pajak (%).
		@return Pajak In Percent
	  */
	public BigDecimal getPajakLevel5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakLevel5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pajak Non NPWP (%).
		@param PajakNonNPWP Pajak Non NPWP (%)	  */
	public void setPajakNonNPWP (BigDecimal PajakNonNPWP)
	{
		set_Value (COLUMNNAME_PajakNonNPWP, PajakNonNPWP);
	}

	/** Get Pajak Non NPWP (%).
		@return Pajak Non NPWP (%)	  */
	public BigDecimal getPajakNonNPWP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PajakNonNPWP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payroll Date End.
		@param PayrollDateEnd Payroll Date End	  */
	public void setPayrollDateEnd (int PayrollDateEnd)
	{
		set_Value (COLUMNNAME_PayrollDateEnd, Integer.valueOf(PayrollDateEnd));
	}

	/** Get Payroll Date End.
		@return Payroll Date End	  */
	public int getPayrollDateEnd () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayrollDateEnd);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Date Start.
		@param PayrollDateStart Payroll Date Start	  */
	public void setPayrollDateStart (int PayrollDateStart)
	{
		set_Value (COLUMNNAME_PayrollDateStart, Integer.valueOf(PayrollDateStart));
	}

	/** Get Payroll Date Start.
		@return Payroll Date Start	  */
	public int getPayrollDateStart () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayrollDateStart);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Employee_Loan_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Employee_Loan_Acct(), get_TrxName());	}

	/** Set Employee Loan Acct.
		@param P_Employee_Loan_Acct 
		The accounting element for posting employee's loan to company
	  */
	public void setP_Employee_Loan_Acct (int P_Employee_Loan_Acct)
	{
		set_Value (COLUMNNAME_P_Employee_Loan_Acct, Integer.valueOf(P_Employee_Loan_Acct));
	}

	/** Get Employee Loan Acct.
		@return The accounting element for posting employee's loan to company
	  */
	public int getP_Employee_Loan_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Employee_Loan_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_LabelAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_LabelAcct_ID(), get_TrxName());	}

	/** Set P. Label Acct.
		@param P_LabelAcct_ID P. Label Acct	  */
	public void setP_LabelAcct_ID (int P_LabelAcct_ID)
	{
		if (P_LabelAcct_ID < 1) 
			set_Value (COLUMNNAME_P_LabelAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_LabelAcct_ID, Integer.valueOf(P_LabelAcct_ID));
	}

	/** Get P. Label Acct.
		@return P. Label Acct	  */
	public int getP_LabelAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_LabelAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_ListrikAirAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_ListrikAirAcct_ID(), get_TrxName());	}

	/** Set P. Listrik Air Acct.
		@param P_ListrikAirAcct_ID P. Listrik Air Acct	  */
	public void setP_ListrikAirAcct_ID (int P_ListrikAirAcct_ID)
	{
		if (P_ListrikAirAcct_ID < 1) 
			set_Value (COLUMNNAME_P_ListrikAirAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_ListrikAirAcct_ID, Integer.valueOf(P_ListrikAirAcct_ID));
	}

	/** Get P. Listrik Air Acct.
		@return P. Listrik Air Acct	  */
	public int getP_ListrikAirAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_ListrikAirAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_MakanAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_MakanAcct_ID(), get_TrxName());	}

	/** Set P Makan Account.
		@param P_MakanAcct_ID P Makan Account	  */
	public void setP_MakanAcct_ID (int P_MakanAcct_ID)
	{
		if (P_MakanAcct_ID < 1) 
			set_Value (COLUMNNAME_P_MakanAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_MakanAcct_ID, Integer.valueOf(P_MakanAcct_ID));
	}

	/** Get P Makan Account.
		@return P Makan Account	  */
	public int getP_MakanAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MakanAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_MangkirAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_MangkirAcct_ID(), get_TrxName());	}

	/** Set P. Mangkir Acct.
		@param P_MangkirAcct_ID 
		Potongan Mangkir Account
	  */
	public void setP_MangkirAcct_ID (int P_MangkirAcct_ID)
	{
		if (P_MangkirAcct_ID < 1) 
			set_Value (COLUMNNAME_P_MangkirAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_MangkirAcct_ID, Integer.valueOf(P_MangkirAcct_ID));
	}

	/** Get P. Mangkir Acct.
		@return Potongan Mangkir Account
	  */
	public int getP_MangkirAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MangkirAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Obat_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Obat_Acct(), get_TrxName());	}

	/** Set Clinic Revenue Acct.
		@param P_Obat_Acct 
		Accounting element for potongan / pendapatan / revenue of clinic dept. from medical transaction
	  */
	public void setP_Obat_Acct (int P_Obat_Acct)
	{
		set_Value (COLUMNNAME_P_Obat_Acct, Integer.valueOf(P_Obat_Acct));
	}

	/** Get Clinic Revenue Acct.
		@return Accounting element for potongan / pendapatan / revenue of clinic dept. from medical transaction
	  */
	public int getP_Obat_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Obat_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_OtherAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_OtherAcct_ID(), get_TrxName());	}

	/** Set P. Other Acct.
		@param P_OtherAcct_ID P. Other Acct	  */
	public void setP_OtherAcct_ID (int P_OtherAcct_ID)
	{
		if (P_OtherAcct_ID < 1) 
			set_Value (COLUMNNAME_P_OtherAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_OtherAcct_ID, Integer.valueOf(P_OtherAcct_ID));
	}

	/** Get P. Other Acct.
		@return P. Other Acct	  */
	public int getP_OtherAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_OtherAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getPPH21_Acct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getPPH21_Acct_ID(), get_TrxName());	}

	/** Set PPH21 Acct.
		@param PPH21_Acct_ID PPH21 Acct	  */
	public void setPPH21_Acct_ID (int PPH21_Acct_ID)
	{
		if (PPH21_Acct_ID < 1) 
			set_Value (COLUMNNAME_PPH21_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_PPH21_Acct_ID, Integer.valueOf(PPH21_Acct_ID));
	}

	/** Get PPH21 Acct.
		@return PPH21 Acct	  */
	public int getPPH21_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PPH21_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PPH21PaidByCompany.
		@param PPH21PaidByCompany PPH21PaidByCompany	  */
	public void setPPH21PaidByCompany (boolean PPH21PaidByCompany)
	{
		set_Value (COLUMNNAME_PPH21PaidByCompany, Boolean.valueOf(PPH21PaidByCompany));
	}

	/** Get PPH21PaidByCompany.
		@return PPH21PaidByCompany	  */
	public boolean isPPH21PaidByCompany () 
	{
		Object oo = get_Value(COLUMNNAME_PPH21PaidByCompany);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_C_ValidCombination getP_SPTPAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_SPTPAcct_ID(), get_TrxName());	}

	/** Set Potongan SPTP Acct.
		@param P_SPTPAcct_ID Potongan SPTP Acct	  */
	public void setP_SPTPAcct_ID (int P_SPTPAcct_ID)
	{
		if (P_SPTPAcct_ID < 1) 
			set_Value (COLUMNNAME_P_SPTPAcct_ID, null);
		else 
			set_Value (COLUMNNAME_P_SPTPAcct_ID, Integer.valueOf(P_SPTPAcct_ID));
	}

	/** Get Potongan SPTP Acct.
		@return Potongan SPTP Acct	  */
	public int getP_SPTPAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_SPTPAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set All Work Days are OT (S).
		@param ShiftAllWorkDayIsOT 
		To indicate that overtime allowance for shift-based employee is calculated to all work days
	  */
	public void setShiftAllWorkDayIsOT (boolean ShiftAllWorkDayIsOT)
	{
		set_Value (COLUMNNAME_ShiftAllWorkDayIsOT, Boolean.valueOf(ShiftAllWorkDayIsOT));
	}

	/** Get All Work Days are OT (S).
		@return To indicate that overtime allowance for shift-based employee is calculated to all work days
	  */
	public boolean isShiftAllWorkDayIsOT () 
	{
		Object oo = get_Value(COLUMNNAME_ShiftAllWorkDayIsOT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OT Allowance Hours (S).
		@param ShiftOTAllowanceHours 
		Number of hours in month used as overtime calculation basis for shift-based employee
	  */
	public void setShiftOTAllowanceHours (BigDecimal ShiftOTAllowanceHours)
	{
		set_Value (COLUMNNAME_ShiftOTAllowanceHours, ShiftOTAllowanceHours);
	}

	/** Get OT Allowance Hours (S).
		@return Number of hours in month used as overtime calculation basis for shift-based employee
	  */
	public BigDecimal getShiftOTAllowanceHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShiftOTAllowanceHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Saturday = 7 */
	public static final String SHIFTOTDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String SHIFTOTDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String SHIFTOTDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String SHIFTOTDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String SHIFTOTDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String SHIFTOTDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String SHIFTOTDAY_Sunday = "1";
	/** Set OT Work Day (S).
		@param ShiftOTDay 
		The work day assumes as OT base-day of shift-based employee
	  */
	public void setShiftOTDay (String ShiftOTDay)
	{

		set_Value (COLUMNNAME_ShiftOTDay, ShiftOTDay);
	}

	/** Get OT Work Day (S).
		@return The work day assumes as OT base-day of shift-based employee
	  */
	public String getShiftOTDay () 
	{
		return (String)get_Value(COLUMNNAME_ShiftOTDay);
	}

	/** Set OT Deduction Multiplier (S).
		@param ShiftOTDeductionMultiplier 
		Number of multiplier to shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public void setShiftOTDeductionMultiplier (BigDecimal ShiftOTDeductionMultiplier)
	{
		set_Value (COLUMNNAME_ShiftOTDeductionMultiplier, ShiftOTDeductionMultiplier);
	}

	/** Get OT Deduction Multiplier (S).
		@return Number of multiplier to shift-based employee's absences / presences that will deduct employee's OT allowance
	  */
	public BigDecimal getShiftOTDeductionMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShiftOTDeductionMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Saturday = 7 */
	public static final String SHORTDAY_Saturday = "7";
	/** Friday = 6 */
	public static final String SHORTDAY_Friday = "6";
	/** Thursday = 5 */
	public static final String SHORTDAY_Thursday = "5";
	/** Wednesday = 4 */
	public static final String SHORTDAY_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String SHORTDAY_Tuesday = "3";
	/** Monday = 2 */
	public static final String SHORTDAY_Monday = "2";
	/** Sunday = 1 */
	public static final String SHORTDAY_Sunday = "1";
	/** Set Short Day.
		@param ShortDay Short Day	  */
	public void setShortDay (String ShortDay)
	{

		set_Value (COLUMNNAME_ShortDay, ShortDay);
	}

	/** Get Short Day.
		@return Short Day	  */
	public String getShortDay () 
	{
		return (String)get_Value(COLUMNNAME_ShortDay);
	}

	/** Saturday = 7 */
	public static final String SHORTDAYSHIFT_Saturday = "7";
	/** Friday = 6 */
	public static final String SHORTDAYSHIFT_Friday = "6";
	/** Thursday = 5 */
	public static final String SHORTDAYSHIFT_Thursday = "5";
	/** Wednesday = 4 */
	public static final String SHORTDAYSHIFT_Wednesday = "4";
	/** Tuesday = 3 */
	public static final String SHORTDAYSHIFT_Tuesday = "3";
	/** Monday = 2 */
	public static final String SHORTDAYSHIFT_Monday = "2";
	/** Sunday = 1 */
	public static final String SHORTDAYSHIFT_Sunday = "1";
	/** Set Short Day (S).
		@param ShortDayShift Short Day (S)	  */
	public void setShortDayShift (String ShortDayShift)
	{

		set_Value (COLUMNNAME_ShortDayShift, ShortDayShift);
	}

	/** Get Short Day (S).
		@return Short Day (S)	  */
	public String getShortDayShift () 
	{
		return (String)get_Value(COLUMNNAME_ShortDayShift);
	}

	/** Set Short Day Work Hours.
		@param ShortDayWorkHours Short Day Work Hours	  */
	public void setShortDayWorkHours (BigDecimal ShortDayWorkHours)
	{
		set_Value (COLUMNNAME_ShortDayWorkHours, ShortDayWorkHours);
	}

	/** Get Short Day Work Hours.
		@return Short Day Work Hours	  */
	public BigDecimal getShortDayWorkHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShortDayWorkHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Short Day Work Hours (S).
		@param ShortDayWorkHoursShift Short Day Work Hours (S)	  */
	public void setShortDayWorkHoursShift (BigDecimal ShortDayWorkHoursShift)
	{
		set_Value (COLUMNNAME_ShortDayWorkHoursShift, ShortDayWorkHoursShift);
	}

	/** Get Short Day Work Hours (S).
		@return Short Day Work Hours (S)	  */
	public BigDecimal getShortDayWorkHoursShift () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShortDayWorkHoursShift);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SP Period.
		@param SP_Period SP Period	  */
	public void setSP_Period (int SP_Period)
	{
		set_Value (COLUMNNAME_SP_Period, Integer.valueOf(SP_Period));
	}

	/** Get SP Period.
		@return SP Period	  */
	public int getSP_Period () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SP_Period);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TK0.
		@param TK0 
		Tidak kawin dengan 0 tanggunan
	  */
	public void setTK0 (BigDecimal TK0)
	{
		set_Value (COLUMNNAME_TK0, TK0);
	}

	/** Get TK0.
		@return Tidak kawin dengan 0 tanggunan
	  */
	public BigDecimal getTK0 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TK0);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TK1.
		@param TK1 
		Tidak kawin dengan 1 tanggunan
	  */
	public void setTK1 (BigDecimal TK1)
	{
		set_Value (COLUMNNAME_TK1, TK1);
	}

	/** Get TK1.
		@return Tidak kawin dengan 1 tanggunan
	  */
	public BigDecimal getTK1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TK1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TK2.
		@param TK2 
		Tidak kawin dengan 2 tanggunan
	  */
	public void setTK2 (BigDecimal TK2)
	{
		set_Value (COLUMNNAME_TK2, TK2);
	}

	/** Get TK2.
		@return Tidak kawin dengan 2 tanggunan
	  */
	public BigDecimal getTK2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TK2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TK3.
		@param TK3 
		Tidak kawin dengan 3 tanggunan
	  */
	public void setTK3 (BigDecimal TK3)
	{
		set_Value (COLUMNNAME_TK3, TK3);
	}

	/** Get TK3.
		@return Tidak kawin dengan 3 tanggunan
	  */
	public BigDecimal getTK3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TK3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Work Day Basis.
		@param TotalWorkDayBasis 
		Number of work days in month used as absence deduction basis to employee's alowances
	  */
	public void setTotalWorkDayBasis (BigDecimal TotalWorkDayBasis)
	{
		set_Value (COLUMNNAME_TotalWorkDayBasis, TotalWorkDayBasis);
	}

	/** Get Total Work Day Basis.
		@return Number of work days in month used as absence deduction basis to employee's alowances
	  */
	public BigDecimal getTotalWorkDayBasis () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalWorkDayBasis);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getTunjanganJabatanAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getTunjanganJabatanAcct_ID(), get_TrxName());	}

	/** Set Tunjangan Jabatan Acct.
		@param TunjanganJabatanAcct_ID Tunjangan Jabatan Acct	  */
	public void setTunjanganJabatanAcct_ID (int TunjanganJabatanAcct_ID)
	{
		if (TunjanganJabatanAcct_ID < 1) 
			set_Value (COLUMNNAME_TunjanganJabatanAcct_ID, null);
		else 
			set_Value (COLUMNNAME_TunjanganJabatanAcct_ID, Integer.valueOf(TunjanganJabatanAcct_ID));
	}

	/** Get Tunjangan Jabatan Acct.
		@return Tunjangan Jabatan Acct	  */
	public int getTunjanganJabatanAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TunjanganJabatanAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getTunjanganKesejahteraanAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getTunjanganKesejahteraanAcct_ID(), get_TrxName());	}

	/** Set Tunjangan Kesejahteraan Acct.
		@param TunjanganKesejahteraanAcct_ID Tunjangan Kesejahteraan Acct	  */
	public void setTunjanganKesejahteraanAcct_ID (int TunjanganKesejahteraanAcct_ID)
	{
		if (TunjanganKesejahteraanAcct_ID < 1) 
			set_Value (COLUMNNAME_TunjanganKesejahteraanAcct_ID, null);
		else 
			set_Value (COLUMNNAME_TunjanganKesejahteraanAcct_ID, Integer.valueOf(TunjanganKesejahteraanAcct_ID));
	}

	/** Get Tunjangan Kesejahteraan Acct.
		@return Tunjangan Kesejahteraan Acct	  */
	public int getTunjanganKesejahteraanAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TunjanganKesejahteraanAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getTunjanganKhususAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getTunjanganKhususAcct_ID(), get_TrxName());	}

	/** Set Tunjangan Khusus Acct.
		@param TunjanganKhususAcct_ID Tunjangan Khusus Acct	  */
	public void setTunjanganKhususAcct_ID (int TunjanganKhususAcct_ID)
	{
		if (TunjanganKhususAcct_ID < 1) 
			set_Value (COLUMNNAME_TunjanganKhususAcct_ID, null);
		else 
			set_Value (COLUMNNAME_TunjanganKhususAcct_ID, Integer.valueOf(TunjanganKhususAcct_ID));
	}

	/** Get Tunjangan Khusus Acct.
		@return Tunjangan Khusus Acct	  */
	public int getTunjanganKhususAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TunjanganKhususAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getTunjanganLemburAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getTunjanganLemburAcct_ID(), get_TrxName());	}

	/** Set Tunjangan Lembur Acct.
		@param TunjanganLemburAcct_ID Tunjangan Lembur Acct	  */
	public void setTunjanganLemburAcct_ID (int TunjanganLemburAcct_ID)
	{
		if (TunjanganLemburAcct_ID < 1) 
			set_Value (COLUMNNAME_TunjanganLemburAcct_ID, null);
		else 
			set_Value (COLUMNNAME_TunjanganLemburAcct_ID, Integer.valueOf(TunjanganLemburAcct_ID));
	}

	/** Get Tunjangan Lembur Acct.
		@return Tunjangan Lembur Acct	  */
	public int getTunjanganLemburAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TunjanganLemburAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UMK.
		@param UMK 
		Upah Minimum Kota
	  */
	public void setUMK (BigDecimal UMK)
	{
		set_Value (COLUMNNAME_UMK, UMK);
	}

	/** Get UMK.
		@return Upah Minimum Kota
	  */
	public BigDecimal getUMK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UMK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UMP.
		@param UMP UMP	  */
	public void setUMP (BigDecimal UMP)
	{
		set_Value (COLUMNNAME_UMP, UMP);
	}

	/** Get UMP.
		@return UMP	  */
	public BigDecimal getUMP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UMP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payroll Configuration.
		@param UNS_PayrollConfiguration_ID Payroll Configuration	  */
	public void setUNS_PayrollConfiguration_ID (int UNS_PayrollConfiguration_ID)
	{
		if (UNS_PayrollConfiguration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollConfiguration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollConfiguration_ID, Integer.valueOf(UNS_PayrollConfiguration_ID));
	}

	/** Get Payroll Configuration.
		@return Payroll Configuration	  */
	public int getUNS_PayrollConfiguration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayrollConfiguration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PayrollConfiguration_UU.
		@param UNS_PayrollConfiguration_UU UNS_PayrollConfiguration_UU	  */
	public void setUNS_PayrollConfiguration_UU (String UNS_PayrollConfiguration_UU)
	{
		set_Value (COLUMNNAME_UNS_PayrollConfiguration_UU, UNS_PayrollConfiguration_UU);
	}

	/** Get UNS_PayrollConfiguration_UU.
		@return UNS_PayrollConfiguration_UU	  */
	public String getUNS_PayrollConfiguration_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PayrollConfiguration_UU);
	}

	public I_C_ValidCombination getUpahBuruhDirectAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getUpahBuruhDirectAcct_ID(), get_TrxName());	}

	/** Set Upah Buruh Direct Acct.
		@param UpahBuruhDirectAcct_ID Upah Buruh Direct Acct	  */
	public void setUpahBuruhDirectAcct_ID (int UpahBuruhDirectAcct_ID)
	{
		if (UpahBuruhDirectAcct_ID < 1) 
			set_Value (COLUMNNAME_UpahBuruhDirectAcct_ID, null);
		else 
			set_Value (COLUMNNAME_UpahBuruhDirectAcct_ID, Integer.valueOf(UpahBuruhDirectAcct_ID));
	}

	/** Get Upah Buruh Direct Acct.
		@return Upah Buruh Direct Acct	  */
	public int getUpahBuruhDirectAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UpahBuruhDirectAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getUpahBuruhInDirectAcct() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getUpahBuruhInDirectAcct_ID(), get_TrxName());	}

	/** Set Upah Buruh InDirect Acct.
		@param UpahBuruhInDirectAcct_ID Upah Buruh InDirect Acct	  */
	public void setUpahBuruhInDirectAcct_ID (int UpahBuruhInDirectAcct_ID)
	{
		if (UpahBuruhInDirectAcct_ID < 1) 
			set_Value (COLUMNNAME_UpahBuruhInDirectAcct_ID, null);
		else 
			set_Value (COLUMNNAME_UpahBuruhInDirectAcct_ID, Integer.valueOf(UpahBuruhInDirectAcct_ID));
	}

	/** Get Upah Buruh InDirect Acct.
		@return Upah Buruh InDirect Acct	  */
	public int getUpahBuruhInDirectAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UpahBuruhInDirectAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}