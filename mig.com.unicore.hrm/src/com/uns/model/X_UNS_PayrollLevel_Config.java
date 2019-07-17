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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PayrollLevel_Config
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PayrollLevel_Config extends PO implements I_UNS_PayrollLevel_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_PayrollLevel_Config (Properties ctx, int UNS_PayrollLevel_Config_ID, String trxName)
    {
      super (ctx, UNS_PayrollLevel_Config_ID, trxName);
      /** if (UNS_PayrollLevel_Config_ID == 0)
        {
			setIsGPBaseOnPresence (false);
// N
			setIsTJabatanBaseOnPresence (false);
// N
			setIsTKesejahteraanBaseOnPresence (false);
// N
			setIsTKhususBaseOnPresence (false);
// N
			setIsTLemburBaseOnPresence (false);
// N
			setIsUMKBase (false);
// N
			setMedicalAllowance (Env.ZERO);
			setPayrollLevel (null);
			setPayrollTerm (null);
			setUMP (Env.ZERO);
// 0
			setUNS_PayrollConfiguration_ID (0);
			setUNS_PayrollLevel_Config_ID (0);
			setUtumDanTelekomunikasi (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_PayrollLevel_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayrollLevel_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Basic Salary Base on Presence.
		@param IsGPBaseOnPresence 
		To indicate if basic salary is deducted for any number of employee's absence
	  */
	public void setIsGPBaseOnPresence (boolean IsGPBaseOnPresence)
	{
		set_Value (COLUMNNAME_IsGPBaseOnPresence, Boolean.valueOf(IsGPBaseOnPresence));
	}

	/** Get Basic Salary Base on Presence.
		@return To indicate if basic salary is deducted for any number of employee's absence
	  */
	public boolean isGPBaseOnPresence () 
	{
		Object oo = get_Value(COLUMNNAME_IsGPBaseOnPresence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set T Jabatan Base On Presence.
		@param IsTJabatanBaseOnPresence 
		Tunjangan Jabatan Base On Presence ?
	  */
	public void setIsTJabatanBaseOnPresence (boolean IsTJabatanBaseOnPresence)
	{
		set_Value (COLUMNNAME_IsTJabatanBaseOnPresence, Boolean.valueOf(IsTJabatanBaseOnPresence));
	}

	/** Get T Jabatan Base On Presence.
		@return Tunjangan Jabatan Base On Presence ?
	  */
	public boolean isTJabatanBaseOnPresence () 
	{
		Object oo = get_Value(COLUMNNAME_IsTJabatanBaseOnPresence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set T Kesejahteraan Base On Presence.
		@param IsTKesejahteraanBaseOnPresence 
		Tunjangan Kesejahteraan Base On Presence ?
	  */
	public void setIsTKesejahteraanBaseOnPresence (boolean IsTKesejahteraanBaseOnPresence)
	{
		set_Value (COLUMNNAME_IsTKesejahteraanBaseOnPresence, Boolean.valueOf(IsTKesejahteraanBaseOnPresence));
	}

	/** Get T Kesejahteraan Base On Presence.
		@return Tunjangan Kesejahteraan Base On Presence ?
	  */
	public boolean isTKesejahteraanBaseOnPresence () 
	{
		Object oo = get_Value(COLUMNNAME_IsTKesejahteraanBaseOnPresence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set T Khusus Base On Presence.
		@param IsTKhususBaseOnPresence 
		Tunjangan Khusus Base On Presence ?
	  */
	public void setIsTKhususBaseOnPresence (boolean IsTKhususBaseOnPresence)
	{
		set_Value (COLUMNNAME_IsTKhususBaseOnPresence, Boolean.valueOf(IsTKhususBaseOnPresence));
	}

	/** Get T Khusus Base On Presence.
		@return Tunjangan Khusus Base On Presence ?
	  */
	public boolean isTKhususBaseOnPresence () 
	{
		Object oo = get_Value(COLUMNNAME_IsTKhususBaseOnPresence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set T Lembur Base On Presence.
		@param IsTLemburBaseOnPresence 
		Tunjangan Khusus Base On Presence ?
	  */
	public void setIsTLemburBaseOnPresence (boolean IsTLemburBaseOnPresence)
	{
		set_Value (COLUMNNAME_IsTLemburBaseOnPresence, Boolean.valueOf(IsTLemburBaseOnPresence));
	}

	/** Get T Lembur Base On Presence.
		@return Tunjangan Khusus Base On Presence ?
	  */
	public boolean isTLemburBaseOnPresence () 
	{
		Object oo = get_Value(COLUMNNAME_IsTLemburBaseOnPresence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Base On UMK.
		@param IsUMKBase Base On UMK	  */
	public void setIsUMKBase (boolean IsUMKBase)
	{
		set_Value (COLUMNNAME_IsUMKBase, Boolean.valueOf(IsUMKBase));
	}

	/** Get Base On UMK.
		@return Base On UMK	  */
	public boolean isUMKBase () 
	{
		Object oo = get_Value(COLUMNNAME_IsUMKBase);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Min A.L1.
		@param Min_A_L1 Min A.L1	  */
	public void setMin_A_L1 (BigDecimal Min_A_L1)
	{
		set_Value (COLUMNNAME_Min_A_L1, Min_A_L1);
	}

	/** Get Min A.L1.
		@return Min A.L1	  */
	public BigDecimal getMin_A_L1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_L1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.L2.
		@param Min_A_L2 Min A.L2	  */
	public void setMin_A_L2 (BigDecimal Min_A_L2)
	{
		set_Value (COLUMNNAME_Min_A_L2, Min_A_L2);
	}

	/** Get Min A.L2.
		@return Min A.L2	  */
	public BigDecimal getMin_A_L2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_L2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.L3.
		@param Min_A_L3 Min A.L3	  */
	public void setMin_A_L3 (BigDecimal Min_A_L3)
	{
		set_Value (COLUMNNAME_Min_A_L3, Min_A_L3);
	}

	/** Get Min A.L3.
		@return Min A.L3	  */
	public BigDecimal getMin_A_L3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_L3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.Lain2.
		@param Min_A_Lain2 Min A.Lain2	  */
	public void setMin_A_Lain2 (BigDecimal Min_A_Lain2)
	{
		set_Value (COLUMNNAME_Min_A_Lain2, Min_A_Lain2);
	}

	/** Get Min A.Lain2.
		@return Min A.Lain2	  */
	public BigDecimal getMin_A_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.Lembur Jam Berikutnya.
		@param Min_A_Lembur Min A.Lembur Jam Berikutnya	  */
	public void setMin_A_Lembur (BigDecimal Min_A_Lembur)
	{
		set_Value (COLUMNNAME_Min_A_Lembur, Min_A_Lembur);
	}

	/** Get Min A.Lembur Jam Berikutnya.
		@return Min A.Lembur Jam Berikutnya	  */
	public BigDecimal getMin_A_Lembur () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_Lembur);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.Lembur Jam Pertama.
		@param Min_A_LemburJamPertama Min A.Lembur Jam Pertama	  */
	public void setMin_A_LemburJamPertama (BigDecimal Min_A_LemburJamPertama)
	{
		set_Value (COLUMNNAME_Min_A_LemburJamPertama, Min_A_LemburJamPertama);
	}

	/** Get Min A.Lembur Jam Pertama.
		@return Min A.Lembur Jam Pertama	  */
	public BigDecimal getMin_A_LemburJamPertama () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_LemburJamPertama);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min A.Premi.
		@param Min_A_Premi Min A.Premi	  */
	public void setMin_A_Premi (BigDecimal Min_A_Premi)
	{
		set_Value (COLUMNNAME_Min_A_Premi, Min_A_Premi);
	}

	/** Get Min A.Premi.
		@return Min A.Premi	  */
	public BigDecimal getMin_A_Premi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_A_Premi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min G.Pokok.
		@param Min_G_Pokok Min G.Pokok	  */
	public void setMin_G_Pokok (BigDecimal Min_G_Pokok)
	{
		set_Value (COLUMNNAME_Min_G_Pokok, Min_G_Pokok);
	}

	/** Get Min G.Pokok.
		@return Min G.Pokok	  */
	public BigDecimal getMin_G_Pokok () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_G_Pokok);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min G.T.Jabatan.
		@param Min_G_T_Jabatan Min G.T.Jabatan	  */
	public void setMin_G_T_Jabatan (BigDecimal Min_G_T_Jabatan)
	{
		set_Value (COLUMNNAME_Min_G_T_Jabatan, Min_G_T_Jabatan);
	}

	/** Get Min G.T.Jabatan.
		@return Min G.T.Jabatan	  */
	public BigDecimal getMin_G_T_Jabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_G_T_Jabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min G.T.Kesejahteraan.
		@param Min_G_T_Kesejahteraan Min G.T.Kesejahteraan	  */
	public void setMin_G_T_Kesejahteraan (BigDecimal Min_G_T_Kesejahteraan)
	{
		set_Value (COLUMNNAME_Min_G_T_Kesejahteraan, Min_G_T_Kesejahteraan);
	}

	/** Get Min G.T.Kesejahteraan.
		@return Min G.T.Kesejahteraan	  */
	public BigDecimal getMin_G_T_Kesejahteraan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_G_T_Kesejahteraan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min G.T.Khusus.
		@param Min_G_T_Khusus Min G.T.Khusus	  */
	public void setMin_G_T_Khusus (BigDecimal Min_G_T_Khusus)
	{
		set_Value (COLUMNNAME_Min_G_T_Khusus, Min_G_T_Khusus);
	}

	/** Get Min G.T.Khusus.
		@return Min G.T.Khusus	  */
	public BigDecimal getMin_G_T_Khusus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_G_T_Khusus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min G.T.Lembur.
		@param Min_G_T_Lembur Min G.T.Lembur	  */
	public void setMin_G_T_Lembur (BigDecimal Min_G_T_Lembur)
	{
		set_Value (COLUMNNAME_Min_G_T_Lembur, Min_G_T_Lembur);
	}

	/** Get Min G.T.Lembur.
		@return Min G.T.Lembur	  */
	public BigDecimal getMin_G_T_Lembur () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Min_G_T_Lembur);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
	/** Set PayrollLevel.
		@param PayrollLevel PayrollLevel	  */
	public void setPayrollLevel (String PayrollLevel)
	{

		set_Value (COLUMNNAME_PayrollLevel, PayrollLevel);
	}

	/** Get PayrollLevel.
		@return PayrollLevel	  */
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

	/** Set P.Lain2.
		@param P_Lain2 P.Lain2	  */
	public void setP_Lain2 (BigDecimal P_Lain2)
	{
		set_Value (COLUMNNAME_P_Lain2, P_Lain2);
	}

	/** Get P.Lain2.
		@return P.Lain2	  */
	public BigDecimal getP_Lain2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Lain2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P.Mangkir.
		@param P_Mangkir P.Mangkir	  */
	public void setP_Mangkir (BigDecimal P_Mangkir)
	{
		set_Value (COLUMNNAME_P_Mangkir, P_Mangkir);
	}

	/** Get P.Mangkir.
		@return P.Mangkir	  */
	public BigDecimal getP_Mangkir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Mangkir);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P. SPTP.
		@param P_SPTP 
		Potongan Biaya SPTP
	  */
	public void setP_SPTP (BigDecimal P_SPTP)
	{
		set_Value (COLUMNNAME_P_SPTP, P_SPTP);
	}

	/** Get P. SPTP.
		@return Potongan Biaya SPTP
	  */
	public BigDecimal getP_SPTP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_SPTP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public com.uns.model.I_UNS_PayrollConfiguration getUNS_PayrollConfiguration() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PayrollConfiguration)MTable.get(getCtx(), com.uns.model.I_UNS_PayrollConfiguration.Table_Name)
			.getPO(getUNS_PayrollConfiguration_ID(), get_TrxName());	}

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

	/** Set PayrolLevel Config.
		@param UNS_PayrollLevel_Config_ID PayrolLevel Config	  */
	public void setUNS_PayrollLevel_Config_ID (int UNS_PayrollLevel_Config_ID)
	{
		if (UNS_PayrollLevel_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollLevel_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollLevel_Config_ID, Integer.valueOf(UNS_PayrollLevel_Config_ID));
	}

	/** Get PayrolLevel Config.
		@return PayrolLevel Config	  */
	public int getUNS_PayrollLevel_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayrollLevel_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PayrolLevel_Config_UU.
		@param UNS_PayrollLevel_Config_UU UNS_PayrolLevel_Config_UU	  */
	public void setUNS_PayrollLevel_Config_UU (String UNS_PayrollLevel_Config_UU)
	{
		set_Value (COLUMNNAME_UNS_PayrollLevel_Config_UU, UNS_PayrollLevel_Config_UU);
	}

	/** Get UNS_PayrolLevel_Config_UU.
		@return UNS_PayrolLevel_Config_UU	  */
	public String getUNS_PayrollLevel_Config_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PayrollLevel_Config_UU);
	}

	/** Set Utum Dan Telekomunikasi.
		@param UtumDanTelekomunikasi Utum Dan Telekomunikasi	  */
	public void setUtumDanTelekomunikasi (BigDecimal UtumDanTelekomunikasi)
	{
		set_Value (COLUMNNAME_UtumDanTelekomunikasi, UtumDanTelekomunikasi);
	}

	/** Get Utum Dan Telekomunikasi.
		@return Utum Dan Telekomunikasi	  */
	public BigDecimal getUtumDanTelekomunikasi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UtumDanTelekomunikasi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}