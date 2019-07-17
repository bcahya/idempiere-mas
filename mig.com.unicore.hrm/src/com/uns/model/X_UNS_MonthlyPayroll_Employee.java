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

/** Generated Model for UNS_MonthlyPayroll_Employee
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_MonthlyPayroll_Employee extends PO implements I_UNS_MonthlyPayroll_Employee, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180226L;

    /** Standard Constructor */
    public X_UNS_MonthlyPayroll_Employee (Properties ctx, int UNS_MonthlyPayroll_Employee_ID, String trxName)
    {
      super (ctx, UNS_MonthlyPayroll_Employee_ID, trxName);
      /** if (UNS_MonthlyPayroll_Employee_ID == 0)
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
			setC_DocType_ID (0);
			setC_Period_ID (0);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setG_T_Jabatan (Env.ZERO);
// 0
			setG_T_Kesejahteraan (Env.ZERO);
// 0
			setG_T_Khusus (Env.ZERO);
// 0
			setG_T_Lembur (Env.ZERO);
// 0
			setGenerateList (null);
// GBP
			setGPokok (Env.ZERO);
			setIsApproved (false);
// N
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
			setPeriodType (null);
// 01
			setPPH21 (Env.ZERO);
// 0
			setProcessed (false);
// N
			setProcessing (false);
// N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setTakeHomePay (Env.ZERO);
			setUNS_MonthlyPayroll_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MonthlyPayroll_Employee (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MonthlyPayroll_Employee[")
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
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

	/** Generate By Presence = GBP */
	public static final String GENERATELIST_GenerateByPresence = "GBP";
	/** Generate By Contract = GBC */
	public static final String GENERATELIST_GenerateByContract = "GBC";
	/** Set Generate List.
		@param GenerateList 
		Generate List
	  */
	public void setGenerateList (String GenerateList)
	{

		set_Value (COLUMNNAME_GenerateList, GenerateList);
	}

	/** Get Generate List.
		@return Generate List
	  */
	public String getGenerateList () 
	{
		return (String)get_Value(COLUMNNAME_GenerateList);
	}

	/** Set Generate Pay.
		@param GeneratePay Generate Pay	  */
	public void setGeneratePay (String GeneratePay)
	{
		set_Value (COLUMNNAME_GeneratePay, GeneratePay);
	}

	/** Get Generate Pay.
		@return Generate Pay	  */
	public String getGeneratePay () 
	{
		return (String)get_Value(COLUMNNAME_GeneratePay);
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

	/** 1 Month = 01 */
	public static final String PERIODTYPE_1Month = "01";
	/** 1st Week = 02 */
	public static final String PERIODTYPE_1stWeek = "02";
	/** 2nd Week = 03 */
	public static final String PERIODTYPE_2ndWeek = "03";
	/** 3rd Week = 04 */
	public static final String PERIODTYPE_3rdWeek = "04";
	/** 4th Week = 05 */
	public static final String PERIODTYPE_4thWeek = "05";
	/** 1st 2Weeks = 06 */
	public static final String PERIODTYPE_1st2Weeks = "06";
	/** 2nd 2Weeks = 07 */
	public static final String PERIODTYPE_2nd2Weeks = "07";
	/** Set Period Type.
		@param PeriodType 
		Period Type
	  */
	public void setPeriodType (String PeriodType)
	{

		set_ValueNoCheck (COLUMNNAME_PeriodType, PeriodType);
	}

	/** Get Period Type.
		@return Period Type
	  */
	public String getPeriodType () 
	{
		return (String)get_Value(COLUMNNAME_PeriodType);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getPeriodType()));
    }

	/** Set Potongan Makan.
		@param PMakan Potongan Makan	  */
	public void setPMakan (BigDecimal PMakan)
	{
		set_Value (COLUMNNAME_PMakan, PMakan);
	}

	/** Get Potongan Makan.
		@return Potongan Makan	  */
	public BigDecimal getPMakan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PMakan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PPH 21.
		@param PPH21 PPH 21	  */
	public void setPPH21 (BigDecimal PPH21)
	{
		set_Value (COLUMNNAME_PPH21, PPH21);
	}

	/** Get PPH 21.
		@return PPH 21	  */
	public BigDecimal getPPH21 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PPH21);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Take Home Pay.
		@param TakeHomePay 
		Take Home Pay
	  */
	public void setTakeHomePay (BigDecimal TakeHomePay)
	{
		set_Value (COLUMNNAME_TakeHomePay, TakeHomePay);
	}

	/** Get Take Home Pay.
		@return Take Home Pay
	  */
	public BigDecimal getTakeHomePay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TakeHomePay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Monthly Payroll Employee.
		@param UNS_MonthlyPayroll_Employee_ID Monthly Payroll Employee	  */
	public void setUNS_MonthlyPayroll_Employee_ID (int UNS_MonthlyPayroll_Employee_ID)
	{
		if (UNS_MonthlyPayroll_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPayroll_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MonthlyPayroll_Employee_ID, Integer.valueOf(UNS_MonthlyPayroll_Employee_ID));
	}

	/** Get Monthly Payroll Employee.
		@return Monthly Payroll Employee	  */
	public int getUNS_MonthlyPayroll_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MonthlyPayroll_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MonthlyPayroll_Employee_UU.
		@param UNS_MonthlyPayroll_Employee_UU UNS_MonthlyPayroll_Employee_UU	  */
	public void setUNS_MonthlyPayroll_Employee_UU (String UNS_MonthlyPayroll_Employee_UU)
	{
		set_Value (COLUMNNAME_UNS_MonthlyPayroll_Employee_UU, UNS_MonthlyPayroll_Employee_UU);
	}

	/** Get UNS_MonthlyPayroll_Employee_UU.
		@return UNS_MonthlyPayroll_Employee_UU	  */
	public String getUNS_MonthlyPayroll_Employee_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MonthlyPayroll_Employee_UU);
	}
}