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

/** Generated Model for UNS_PayrollBase_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PayrollBase_Employee extends PO implements I_UNS_PayrollBase_Employee, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_PayrollBase_Employee (Properties ctx, int UNS_PayrollBase_Employee_ID, String trxName)
    {
      super (ctx, UNS_PayrollBase_Employee_ID, trxName);
      /** if (UNS_PayrollBase_Employee_ID == 0)
        {
			setA_JHT (Env.ZERO);
// 0
			setA_JK (Env.ZERO);
// 0
			setA_JKK (Env.ZERO);
// 0
			setA_JPK (Env.ZERO);
// 0
			setA_L1 (Env.ZERO);
// 0
			setA_L2 (Env.ZERO);
// 0
			setA_L3 (Env.ZERO);
// 0
			setA_LemburJamBerikutnya (Env.ZERO);
// 0
			setA_LemburJamPertama (Env.ZERO);
// 0
			setA_Other (Env.ZERO);
// 0
			setA_Premi (Env.ZERO);
// 0
			setA_Rapel (Env.ZERO);
// 0
			setGPokok (Env.ZERO);
			setG_T_Jabatan (Env.ZERO);
// 0
			setG_T_Kesejahteraan (Env.ZERO);
// 0
			setG_T_Khusus (Env.ZERO);
// 0
			setG_T_Lembur (Env.ZERO);
// 0
			setIsJHTApplyed (true);
// Y
			setIsJKApplyed (true);
// Y
			setIsJKKApplyed (true);
// Y
			setIsJPApplied (true);
// Y
			setIsJPKApplyed (true);
// Y
			setPayrollLevel (null);
			setP_JHT (Env.ZERO);
// 0
			setP_JK (Env.ZERO);
// 0
			setP_JKK (Env.ZERO);
// 0
			setP_JPK (Env.ZERO);
// 0
			setP_Koperasi (Env.ZERO);
// 0
			setP_Label (Env.ZERO);
// 0
			setP_ListrikAir (Env.ZERO);
// 0
			setP_Mangkir (Env.ZERO);
// 0
			setP_Obat (Env.ZERO);
// 0
			setP_Other (Env.ZERO);
// 0
			setP_PinjamanKaryawan (Env.ZERO);
// 0
			setP_SPTP (Env.ZERO);
// 0
			setUNS_Contract_Recommendation_ID (0);
			setUNS_Employee_ID (0);
			setUNS_PayrollBase_Employee_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_PayrollBase_Employee (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayrollBase_Employee[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set A.JHT.
		@param A_JHT A.JHT	  */
	public void setA_JHT (BigDecimal A_JHT)
	{
		set_Value (COLUMNNAME_A_JHT, A_JHT);
	}

	/** Get A.JHT.
		@return A.JHT	  */
	public BigDecimal getA_JHT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_JHT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set A.JK.
		@param A_JK A.JK	  */
	public void setA_JK (BigDecimal A_JK)
	{
		set_Value (COLUMNNAME_A_JK, A_JK);
	}

	/** Get A.JK.
		@return A.JK	  */
	public BigDecimal getA_JK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_JK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set A.JKK.
		@param A_JKK A.JKK	  */
	public void setA_JKK (BigDecimal A_JKK)
	{
		set_Value (COLUMNNAME_A_JKK, A_JKK);
	}

	/** Get A.JKK.
		@return A.JKK	  */
	public BigDecimal getA_JKK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_JKK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set A.JPK.
		@param A_JPK A.JPK	  */
	public void setA_JPK (BigDecimal A_JPK)
	{
		set_Value (COLUMNNAME_A_JPK, A_JPK);
	}

	/** Get A.JPK.
		@return A.JPK	  */
	public BigDecimal getA_JPK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_JPK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Lembur 1.
		@param A_L1 Aditional Lembur 1	  */
	public void setA_L1 (BigDecimal A_L1)
	{
		set_Value (COLUMNNAME_A_L1, A_L1);
	}

	/** Get Aditional Lembur 1.
		@return Aditional Lembur 1	  */
	public BigDecimal getA_L1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_L1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Lembur 2.
		@param A_L2 Aditional Lembur 2	  */
	public void setA_L2 (BigDecimal A_L2)
	{
		set_Value (COLUMNNAME_A_L2, A_L2);
	}

	/** Get Aditional Lembur 2.
		@return Aditional Lembur 2	  */
	public BigDecimal getA_L2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_L2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Lembur 3.
		@param A_L3 Aditional Lembur 3	  */
	public void setA_L3 (BigDecimal A_L3)
	{
		set_Value (COLUMNNAME_A_L3, A_L3);
	}

	/** Get Aditional Lembur 3.
		@return Aditional Lembur 3	  */
	public BigDecimal getA_L3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_L3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set A. Lembur Jam Berikutnya.
		@param A_LemburJamBerikutnya A. Lembur Jam Berikutnya	  */
	public void setA_LemburJamBerikutnya (BigDecimal A_LemburJamBerikutnya)
	{
		set_Value (COLUMNNAME_A_LemburJamBerikutnya, A_LemburJamBerikutnya);
	}

	/** Get A. Lembur Jam Berikutnya.
		@return A. Lembur Jam Berikutnya	  */
	public BigDecimal getA_LemburJamBerikutnya () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_LemburJamBerikutnya);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Lembur Jam Pertama.
		@param A_LemburJamPertama Aditional Lembur Jam Pertama	  */
	public void setA_LemburJamPertama (BigDecimal A_LemburJamPertama)
	{
		set_Value (COLUMNNAME_A_LemburJamPertama, A_LemburJamPertama);
	}

	/** Get Aditional Lembur Jam Pertama.
		@return Aditional Lembur Jam Pertama	  */
	public BigDecimal getA_LemburJamPertama () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_LemburJamPertama);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Other.
		@param A_Other Aditional Other	  */
	public void setA_Other (BigDecimal A_Other)
	{
		set_Value (COLUMNNAME_A_Other, A_Other);
	}

	/** Get Aditional Other.
		@return Aditional Other	  */
	public BigDecimal getA_Other () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Other);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Premi.
		@param A_Premi Aditional Premi	  */
	public void setA_Premi (BigDecimal A_Premi)
	{
		set_Value (COLUMNNAME_A_Premi, A_Premi);
	}

	/** Get Aditional Premi.
		@return Aditional Premi	  */
	public BigDecimal getA_Premi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Premi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Aditional Rapel.
		@param A_Rapel Aditional Rapel	  */
	public void setA_Rapel (BigDecimal A_Rapel)
	{
		set_Value (COLUMNNAME_A_Rapel, A_Rapel);
	}

	/** Get Aditional Rapel.
		@return Aditional Rapel	  */
	public BigDecimal getA_Rapel () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Rapel);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gaji Pokok.
		@param GPokok Gaji Pokok	  */
	public void setGPokok (BigDecimal GPokok)
	{
		set_Value (COLUMNNAME_GPokok, GPokok);
	}

	/** Get Gaji Pokok.
		@return Gaji Pokok	  */
	public BigDecimal getGPokok () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GPokok);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunjangan Jabatan.
		@param G_T_Jabatan Tunjangan Jabatan	  */
	public void setG_T_Jabatan (BigDecimal G_T_Jabatan)
	{
		set_Value (COLUMNNAME_G_T_Jabatan, G_T_Jabatan);
	}

	/** Get Tunjangan Jabatan.
		@return Tunjangan Jabatan	  */
	public BigDecimal getG_T_Jabatan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_G_T_Jabatan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunjangan Kesejahteraan.
		@param G_T_Kesejahteraan Tunjangan Kesejahteraan	  */
	public void setG_T_Kesejahteraan (BigDecimal G_T_Kesejahteraan)
	{
		set_Value (COLUMNNAME_G_T_Kesejahteraan, G_T_Kesejahteraan);
	}

	/** Get Tunjangan Kesejahteraan.
		@return Tunjangan Kesejahteraan	  */
	public BigDecimal getG_T_Kesejahteraan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_G_T_Kesejahteraan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunjangan Khusus.
		@param G_T_Khusus Tunjangan Khusus	  */
	public void setG_T_Khusus (BigDecimal G_T_Khusus)
	{
		set_Value (COLUMNNAME_G_T_Khusus, G_T_Khusus);
	}

	/** Get Tunjangan Khusus.
		@return Tunjangan Khusus	  */
	public BigDecimal getG_T_Khusus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_G_T_Khusus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunjangan Lembur.
		@param G_T_Lembur Tunjangan Lembur	  */
	public void setG_T_Lembur (BigDecimal G_T_Lembur)
	{
		set_Value (COLUMNNAME_G_T_Lembur, G_T_Lembur);
	}

	/** Get Tunjangan Lembur.
		@return Tunjangan Lembur	  */
	public BigDecimal getG_T_Lembur () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_G_T_Lembur);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set P.JHT.
		@param P_JHT P.JHT	  */
	public void setP_JHT (BigDecimal P_JHT)
	{
		set_Value (COLUMNNAME_P_JHT, P_JHT);
	}

	/** Get P.JHT.
		@return P.JHT	  */
	public BigDecimal getP_JHT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_JHT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P.JK.
		@param P_JK P.JK	  */
	public void setP_JK (BigDecimal P_JK)
	{
		set_Value (COLUMNNAME_P_JK, P_JK);
	}

	/** Get P.JK.
		@return P.JK	  */
	public BigDecimal getP_JK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_JK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P.JKK.
		@param P_JKK P.JKK	  */
	public void setP_JKK (BigDecimal P_JKK)
	{
		set_Value (COLUMNNAME_P_JKK, P_JKK);
	}

	/** Get P.JKK.
		@return P.JKK	  */
	public BigDecimal getP_JKK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_JKK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P.JPK.
		@param P_JPK P.JPK	  */
	public void setP_JPK (BigDecimal P_JPK)
	{
		set_Value (COLUMNNAME_P_JPK, P_JPK);
	}

	/** Get P.JPK.
		@return P.JPK	  */
	public BigDecimal getP_JPK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_JPK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P. Koperasi.
		@param P_Koperasi 
		Potongan Pinjaman Koperasi
	  */
	public void setP_Koperasi (BigDecimal P_Koperasi)
	{
		set_Value (COLUMNNAME_P_Koperasi, P_Koperasi);
	}

	/** Get P. Koperasi.
		@return Potongan Pinjaman Koperasi
	  */
	public BigDecimal getP_Koperasi () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Koperasi);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P. Label.
		@param P_Label 
		Potongan Label
	  */
	public void setP_Label (BigDecimal P_Label)
	{
		set_Value (COLUMNNAME_P_Label, P_Label);
	}

	/** Get P. Label.
		@return Potongan Label
	  */
	public BigDecimal getP_Label () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Label);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P. Listrik & Air.
		@param P_ListrikAir 
		Potongan Listrik & Air
	  */
	public void setP_ListrikAir (BigDecimal P_ListrikAir)
	{
		set_Value (COLUMNNAME_P_ListrikAir, P_ListrikAir);
	}

	/** Get P. Listrik & Air.
		@return Potongan Listrik & Air
	  */
	public BigDecimal getP_ListrikAir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_ListrikAir);
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

	/** Set P. Obat.
		@param P_Obat 
		Potongan Obat
	  */
	public void setP_Obat (BigDecimal P_Obat)
	{
		set_Value (COLUMNNAME_P_Obat, P_Obat);
	}

	/** Get P. Obat.
		@return Potongan Obat
	  */
	public BigDecimal getP_Obat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Obat);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Potongan Other.
		@param P_Other 
		Potongan Other
	  */
	public void setP_Other (BigDecimal P_Other)
	{
		set_Value (COLUMNNAME_P_Other, P_Other);
	}

	/** Get Potongan Other.
		@return Potongan Other
	  */
	public BigDecimal getP_Other () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Other);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set P. Pinjaman Karyawan.
		@param P_PinjamanKaryawan 
		Potongan Pinjaman Karyawan
	  */
	public void setP_PinjamanKaryawan (BigDecimal P_PinjamanKaryawan)
	{
		set_Value (COLUMNNAME_P_PinjamanKaryawan, P_PinjamanKaryawan);
	}

	/** Get P. Pinjaman Karyawan.
		@return Potongan Pinjaman Karyawan
	  */
	public BigDecimal getP_PinjamanKaryawan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_PinjamanKaryawan);
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

	/** Set Total Other Allowances.
		@param TotalOtherAllowances 
		Total other allowances listed for employee's payroll
	  */
	public void setTotalOtherAllowances (BigDecimal TotalOtherAllowances)
	{
		set_Value (COLUMNNAME_TotalOtherAllowances, TotalOtherAllowances);
	}

	/** Get Total Other Allowances.
		@return Total other allowances listed for employee's payroll
	  */
	public BigDecimal getTotalOtherAllowances () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOtherAllowances);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Other Deductions.
		@param TotalOtherDeductions 
		Total other deductions listed for employee's payroll
	  */
	public void setTotalOtherDeductions (BigDecimal TotalOtherDeductions)
	{
		set_Value (COLUMNNAME_TotalOtherDeductions, TotalOtherDeductions);
	}

	/** Get Total Other Deductions.
		@return Total other deductions listed for employee's payroll
	  */
	public BigDecimal getTotalOtherDeductions () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOtherDeductions);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Contract_Recommendation)MTable.get(getCtx(), com.uns.model.I_UNS_Contract_Recommendation.Table_Name)
			.getPO(getUNS_Contract_Recommendation_ID(), get_TrxName());	}

	/** Set Contract.
		@param UNS_Contract_Recommendation_ID Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID)
	{
		if (UNS_Contract_Recommendation_ID < 1) 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, Integer.valueOf(UNS_Contract_Recommendation_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getUNS_Contract_Recommendation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Contract_Recommendation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Employee_ID()));
    }

	/** Set Payroll Base Employe.
		@param UNS_PayrollBase_Employee_ID Payroll Base Employe	  */
	public void setUNS_PayrollBase_Employee_ID (int UNS_PayrollBase_Employee_ID)
	{
		if (UNS_PayrollBase_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollBase_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollBase_Employee_ID, Integer.valueOf(UNS_PayrollBase_Employee_ID));
	}

	/** Get Payroll Base Employe.
		@return Payroll Base Employe	  */
	public int getUNS_PayrollBase_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayrollBase_Employee_ID);
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