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

/** Generated Model for UNS_HalfPeriod_Presence
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_HalfPeriod_Presence extends PO implements I_UNS_HalfPeriod_Presence, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150314L;

    /** Standard Constructor */
    public X_UNS_HalfPeriod_Presence (Properties ctx, int UNS_HalfPeriod_Presence_ID, String trxName)
    {
      super (ctx, UNS_HalfPeriod_Presence_ID, trxName);
      /** if (UNS_HalfPeriod_Presence_ID == 0)
        {
			setA_JHT (Env.ZERO);
// 0
			setA_JK (Env.ZERO);
// 0
			setA_JKK (Env.ZERO);
// 0
			setA_JPK (Env.ZERO);
// 0
			setC_DocType_ID (0);
// 1000226
			setC_Period_ID (0);
			setCreateCriteriaResult (null);
// N
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setHalfPeriodNo (null);
			setIsApproved (false);
// N
			setIsSwitchTodaily (false);
// N
			setPPH21 (Env.ZERO);
// 0
			setP_AlatKerja (Env.ZERO);
// 0
			setP_Koperasi (Env.ZERO);
// 0
			setP_Label (Env.ZERO);
// 0
			setP_ListrikAir (Env.ZERO);
// 0
			setP_Obat (Env.ZERO);
// 0
			setP_PinjamanKaryawan (Env.ZERO);
// 0
			setP_SPTP (Env.ZERO);
// 0
			setPaidDaily (Env.ZERO);
// 0
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setTotalOvertime (Env.ZERO);
// 0
			setUNS_Employee_ID (0);
			setUNS_HalfPeriod_Presence_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_HalfPeriod_Presence (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_HalfPeriod_Presence[")
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Create Criteria Result.
		@param CreateCriteriaResult Create Criteria Result	  */
	public void setCreateCriteriaResult (String CreateCriteriaResult)
	{
		set_Value (COLUMNNAME_CreateCriteriaResult, CreateCriteriaResult);
	}

	/** Get Create Criteria Result.
		@return Create Criteria Result	  */
	public String getCreateCriteriaResult () 
	{
		return (String)get_Value(COLUMNNAME_CreateCriteriaResult);
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

	/** Full Month = FLM */
	public static final String HALFPERIODNO_FullMonth = "FLM";
	/** Half Period 1 = HP1 */
	public static final String HALFPERIODNO_HalfPeriod1 = "HP1";
	/** Half Period 2 = HP2 */
	public static final String HALFPERIODNO_HalfPeriod2 = "HP2";
	/** Week 1 = WP1 */
	public static final String HALFPERIODNO_Week1 = "WP1";
	/** Week 2 = WP2 */
	public static final String HALFPERIODNO_Week2 = "WP2";
	/** Week 3 = WP3 */
	public static final String HALFPERIODNO_Week3 = "WP3";
	/** Week 4 = WP4 */
	public static final String HALFPERIODNO_Week4 = "WP4";
	/** Week 5 = WP5 */
	public static final String HALFPERIODNO_Week5 = "WP5";
	/** Set Half Period No.
		@param HalfPeriodNo Half Period No	  */
	public void setHalfPeriodNo (String HalfPeriodNo)
	{

		set_Value (COLUMNNAME_HalfPeriodNo, HalfPeriodNo);
	}

	/** Get Half Period No.
		@return Half Period No	  */
	public String getHalfPeriodNo () 
	{
		return (String)get_Value(COLUMNNAME_HalfPeriodNo);
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

	/** Set Supervised.
		@param IsSupervised Supervised	  */
	public void setIsSupervised (boolean IsSupervised)
	{
		set_Value (COLUMNNAME_IsSupervised, Boolean.valueOf(IsSupervised));
	}

	/** Get Supervised.
		@return Supervised	  */
	public boolean isSupervised () 
	{
		Object oo = get_Value(COLUMNNAME_IsSupervised);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Switched To Daily.
		@param IsSwitchTodaily 
		If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public void setIsSwitchTodaily (boolean IsSwitchTodaily)
	{
		set_Value (COLUMNNAME_IsSwitchTodaily, Boolean.valueOf(IsSwitchTodaily));
	}

	/** Get Switched To Daily.
		@return If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public boolean isSwitchTodaily () 
	{
		Object oo = get_Value(COLUMNNAME_IsSwitchTodaily);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set P. Alat Kerja.
		@param P_AlatKerja 
		Potongan Alat Kerja
	  */
	public void setP_AlatKerja (BigDecimal P_AlatKerja)
	{
		set_Value (COLUMNNAME_P_AlatKerja, P_AlatKerja);
	}

	/** Get P. Alat Kerja.
		@return Potongan Alat Kerja
	  */
	public BigDecimal getP_AlatKerja () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_AlatKerja);
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

	/** Set Paid Daily.
		@param PaidDaily Paid Daily	  */
	public void setPaidDaily (BigDecimal PaidDaily)
	{
		set_Value (COLUMNNAME_PaidDaily, PaidDaily);
	}

	/** Get Paid Daily.
		@return Paid Daily	  */
	public BigDecimal getPaidDaily () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidDaily);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Premi Kerajinan Receivable.
		@param PremiKerajinanReceivable Premi Kerajinan Receivable	  */
	public void setPremiKerajinanReceivable (BigDecimal PremiKerajinanReceivable)
	{
		set_Value (COLUMNNAME_PremiKerajinanReceivable, PremiKerajinanReceivable);
	}

	/** Get Premi Kerajinan Receivable.
		@return Premi Kerajinan Receivable	  */
	public BigDecimal getPremiKerajinanReceivable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PremiKerajinanReceivable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Premi Target Receivable.
		@param PremiTargetReceivable Premi Target Receivable	  */
	public void setPremiTargetReceivable (BigDecimal PremiTargetReceivable)
	{
		set_Value (COLUMNNAME_PremiTargetReceivable, PremiTargetReceivable);
	}

	/** Get Premi Target Receivable.
		@return Premi Target Receivable	  */
	public BigDecimal getPremiTargetReceivable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PremiTargetReceivable);
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

	/** Set THR.
		@param THR 
		Tunjangan Hari Raya
	  */
	public void setTHR (BigDecimal THR)
	{
		set_Value (COLUMNNAME_THR, THR);
	}

	/** Get THR.
		@return Tunjangan Hari Raya
	  */
	public BigDecimal getTHR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_THR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Mangkir.
		@param TotalMangkir Total Mangkir	  */
	public void setTotalMangkir (BigDecimal TotalMangkir)
	{
		set_Value (COLUMNNAME_TotalMangkir, TotalMangkir);
	}

	/** Get Total Mangkir.
		@return Total Mangkir	  */
	public BigDecimal getTotalMangkir () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalMangkir);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Non Payable Absence.
		@param TotalNonPayableAbsence 
		The number of employee absences with non-payable permission
	  */
	public void setTotalNonPayableAbsence (BigDecimal TotalNonPayableAbsence)
	{
		set_Value (COLUMNNAME_TotalNonPayableAbsence, TotalNonPayableAbsence);
	}

	/** Get Total Non Payable Absence.
		@return The number of employee absences with non-payable permission
	  */
	public BigDecimal getTotalNonPayableAbsence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalNonPayableAbsence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Overtime.
		@param TotalOvertime 
		Total emloyee's overtime in month
	  */
	public void setTotalOvertime (BigDecimal TotalOvertime)
	{
		set_Value (COLUMNNAME_TotalOvertime, TotalOvertime);
	}

	/** Get Total Overtime.
		@return Total emloyee's overtime in month
	  */
	public BigDecimal getTotalOvertime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOvertime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Payable Absence.
		@param TotalPayableAbsence 
		The number of employee absences with payable permission
	  */
	public void setTotalPayableAbsence (BigDecimal TotalPayableAbsence)
	{
		set_Value (COLUMNNAME_TotalPayableAbsence, TotalPayableAbsence);
	}

	/** Get Total Payable Absence.
		@return The number of employee absences with payable permission
	  */
	public BigDecimal getTotalPayableAbsence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalPayableAbsence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Presence.
		@param TotalPresence Total Presence	  */
	public void setTotalPresence (BigDecimal TotalPresence)
	{
		set_Value (COLUMNNAME_TotalPresence, TotalPresence);
	}

	/** Get Total Presence.
		@return Total Presence	  */
	public BigDecimal getTotalPresence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalPresence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Receivable Amt.
		@param TotalReceivableAmt Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt)
	{
		set_Value (COLUMNNAME_TotalReceivableAmt, TotalReceivableAmt);
	}

	/** Get Total Receivable Amt.
		@return Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total SK.
		@param TotalSK Total SK	  */
	public void setTotalSK (BigDecimal TotalSK)
	{
		set_Value (COLUMNNAME_TotalSK, TotalSK);
	}

	/** Get Total SK.
		@return Total SK	  */
	public BigDecimal getTotalSK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalSK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total SKK.
		@param TotalSKK Total SKK	  */
	public void setTotalSKK (BigDecimal TotalSKK)
	{
		set_Value (COLUMNNAME_TotalSKK, TotalSKK);
	}

	/** Get Total SKK.
		@return Total SKK	  */
	public BigDecimal getTotalSKK () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalSKK);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Subsidies Amt.
		@param TotalSubsidies 
		Total subsidies amount
	  */
	public void setTotalSubsidies (BigDecimal TotalSubsidies)
	{
		set_ValueNoCheck (COLUMNNAME_TotalSubsidies, TotalSubsidies);
	}

	/** Get Total Subsidies Amt.
		@return Total subsidies amount
	  */
	public BigDecimal getTotalSubsidies () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalSubsidies);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Half Period Presence.
		@param UNS_HalfPeriod_Presence_ID Half Period Presence	  */
	public void setUNS_HalfPeriod_Presence_ID (int UNS_HalfPeriod_Presence_ID)
	{
		if (UNS_HalfPeriod_Presence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, Integer.valueOf(UNS_HalfPeriod_Presence_ID));
	}

	/** Get Half Period Presence.
		@return Half Period Presence	  */
	public int getUNS_HalfPeriod_Presence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HalfPeriod_Presence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_HalfPeriod_Presence_UU.
		@param UNS_HalfPeriod_Presence_UU UNS_HalfPeriod_Presence_UU	  */
	public void setUNS_HalfPeriod_Presence_UU (String UNS_HalfPeriod_Presence_UU)
	{
		set_Value (COLUMNNAME_UNS_HalfPeriod_Presence_UU, UNS_HalfPeriod_Presence_UU);
	}

	/** Get UNS_HalfPeriod_Presence_UU.
		@return UNS_HalfPeriod_Presence_UU	  */
	public String getUNS_HalfPeriod_Presence_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_HalfPeriod_Presence_UU);
	}

	/** Set Job Role.
		@param UNS_Job_Role_ID Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID)
	{
		if (UNS_Job_Role_ID < 1) 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, Integer.valueOf(UNS_Job_Role_ID));
	}

	/** Get Job Role.
		@return Job Role	  */
	public int getUNS_Job_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Job_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Generate Update.
		@param UpdateHalfPeriodPresence 
		Generate Update Half Period Presence By daily Worker Presence
	  */
	public void setUpdateHalfPeriodPresence (String UpdateHalfPeriodPresence)
	{
		set_Value (COLUMNNAME_UpdateHalfPeriodPresence, UpdateHalfPeriodPresence);
	}

	/** Get Generate Update.
		@return Generate Update Half Period Presence By daily Worker Presence
	  */
	public String getUpdateHalfPeriodPresence () 
	{
		return (String)get_Value(COLUMNNAME_UpdateHalfPeriodPresence);
	}

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getVendor_ID(), get_TrxName());	}

	/** Set Vendor.
		@param Vendor_ID 
		The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID)
	{
		if (Vendor_ID < 1) 
			set_Value (COLUMNNAME_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_Vendor_ID, Integer.valueOf(Vendor_ID));
	}

	/** Get Vendor.
		@return The Vendor of the product/service
	  */
	public int getVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}