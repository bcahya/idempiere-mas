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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_MemberRegistration
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_MemberRegistration extends PO implements I_UNS_MemberRegistration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150331L;

    /** Standard Constructor */
    public X_UNS_MemberRegistration (Properties ctx, int UNS_MemberRegistration_ID, String trxName)
    {
      super (ctx, UNS_MemberRegistration_ID, trxName);
      /** if (UNS_MemberRegistration_ID == 0)
        {
			setBirthday (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setName (null);
			setNIP (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_MemberRegistration (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MemberRegistration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Bank Account No.
		@param BankAccountNo 
		Bank Account Number
	  */
	public void setBankAccountNo (String BankAccountNo)
	{
		set_Value (COLUMNNAME_BankAccountNo, BankAccountNo);
	}

	/** Get Bank Account No.
		@return Bank Account Number
	  */
	public String getBankAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_BankAccountNo);
	}

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set NIP.
		@param NIP 
		The registered identifier of member, bpartner, or employee.
	  */
	public void setNIP (String NIP)
	{
		set_Value (COLUMNNAME_NIP, NIP);
	}

	/** Get NIP.
		@return The registered identifier of member, bpartner, or employee.
	  */
	public String getNIP () 
	{
		return (String)get_Value(COLUMNNAME_NIP);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
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

	/** Bad Services = BS */
	public static final String REASON_BadServices = "BS";
	/** Resign = RS */
	public static final String REASON_Resign = "RS";
	/** Other Reason = OT */
	public static final String REASON_OtherReason = "OT";
	/** Mutasi = MS */
	public static final String REASON_Mutasi = "MS";
	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{

		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

	/** Set A/n Rekening.
		@param UNS_AvailableAccount A/n Rekening	  */
	public void setUNS_AvailableAccount (boolean UNS_AvailableAccount)
	{
		set_Value (COLUMNNAME_UNS_AvailableAccount, Boolean.valueOf(UNS_AvailableAccount));
	}

	/** Get A/n Rekening.
		@return A/n Rekening	  */
	public boolean isUNS_AvailableAccount () 
	{
		Object oo = get_Value(COLUMNNAME_UNS_AvailableAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UNS_Golongan getUNS_Golongan() throws RuntimeException
    {
		return (I_UNS_Golongan)MTable.get(getCtx(), I_UNS_Golongan.Table_Name)
			.getPO(getUNS_Golongan_ID(), get_TrxName());	}

	/** Set Golongan ID.
		@param UNS_Golongan_ID Golongan ID	  */
	public void setUNS_Golongan_ID (int UNS_Golongan_ID)
	{
		if (UNS_Golongan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, Integer.valueOf(UNS_Golongan_ID));
	}

	/** Get Golongan ID.
		@return Golongan ID	  */
	public int getUNS_Golongan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Golongan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Registered = RGS */
	public static final String UNS_LATESTSTATUS_Registered = "RGS";
	/** Unregistered = UGS */
	public static final String UNS_LATESTSTATUS_Unregistered = "UGS";
	/** Reregistered = RRG */
	public static final String UNS_LATESTSTATUS_Reregistered = "RRG";
	/** None = NN */
	public static final String UNS_LATESTSTATUS_None = "NN";
	/** Set LatestStatus.
		@param UNS_LatestStatus 
		Latest Status of Members
	  */
	public void setUNS_LatestStatus (String UNS_LatestStatus)
	{

		set_Value (COLUMNNAME_UNS_LatestStatus, UNS_LatestStatus);
	}

	/** Get LatestStatus.
		@return Latest Status of Members
	  */
	public String getUNS_LatestStatus () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LatestStatus);
	}

	/** Set Latest Unregistered Date.
		@param UNS_LatestUnregisteredDate 
		Date of Unregistered
	  */
	public void setUNS_LatestUnregisteredDate (Timestamp UNS_LatestUnregisteredDate)
	{
		set_Value (COLUMNNAME_UNS_LatestUnregisteredDate, UNS_LatestUnregisteredDate);
	}

	/** Get Latest Unregistered Date.
		@return Date of Unregistered
	  */
	public Timestamp getUNS_LatestUnregisteredDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UNS_LatestUnregisteredDate);
	}

	/** Set Member Registration ID.
		@param UNS_MemberRegistration_ID Member Registration ID	  */
	public void setUNS_MemberRegistration_ID (int UNS_MemberRegistration_ID)
	{
		if (UNS_MemberRegistration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MemberRegistration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MemberRegistration_ID, Integer.valueOf(UNS_MemberRegistration_ID));
	}

	/** Get Member Registration ID.
		@return Member Registration ID	  */
	public int getUNS_MemberRegistration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MemberRegistration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Member Registration UU.
		@param UNS_memberRegistration_UU Member Registration UU	  */
	public void setUNS_memberRegistration_UU (String UNS_memberRegistration_UU)
	{
		set_Value (COLUMNNAME_UNS_memberRegistration_UU, UNS_memberRegistration_UU);
	}

	/** Get Member Registration UU.
		@return Member Registration UU	  */
	public String getUNS_memberRegistration_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_memberRegistration_UU);
	}

	/** Set Account Numbers.
		@param UNS_NoRekening Account Numbers	  */
	public void setUNS_NoRekening (String UNS_NoRekening)
	{
		set_Value (COLUMNNAME_UNS_NoRekening, UNS_NoRekening);
	}

	/** Get Account Numbers.
		@return Account Numbers	  */
	public String getUNS_NoRekening () 
	{
		return (String)get_Value(COLUMNNAME_UNS_NoRekening);
	}

	/** Set Primary Saving Required.
		@param UNS_PSavingRequired 
		Primary Saving Requierd when Members Registration
	  */
	public void setUNS_PSavingRequired (boolean UNS_PSavingRequired)
	{
		set_Value (COLUMNNAME_UNS_PSavingRequired, Boolean.valueOf(UNS_PSavingRequired));
	}

	/** Get Primary Saving Required.
		@return Primary Saving Requierd when Members Registration
	  */
	public boolean isUNS_PSavingRequired () 
	{
		Object oo = get_Value(COLUMNNAME_UNS_PSavingRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Simpanan Pokok Is Amusti.
		@param UNS_SimPokokIsAmust Simpanan Pokok Is Amusti	  */
	public void setUNS_SimPokokIsAmust (boolean UNS_SimPokokIsAmust)
	{
		set_Value (COLUMNNAME_UNS_SimPokokIsAmust, Boolean.valueOf(UNS_SimPokokIsAmust));
	}

	/** Get Simpanan Pokok Is Amusti.
		@return Simpanan Pokok Is Amusti	  */
	public boolean isUNS_SimPokokIsAmust () 
	{
		Object oo = get_Value(COLUMNNAME_UNS_SimPokokIsAmust);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Setoran Simpanan Pokok.
		@param UNS_StoranSimPokok Setoran Simpanan Pokok	  */
	public void setUNS_StoranSimPokok (int UNS_StoranSimPokok)
	{
		set_Value (COLUMNNAME_UNS_StoranSimPokok, Integer.valueOf(UNS_StoranSimPokok));
	}

	/** Get Setoran Simpanan Pokok.
		@return Setoran Simpanan Pokok	  */
	public int getUNS_StoranSimPokok () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_StoranSimPokok);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TMT.
		@param UNS_TMT 
		Date of Registered or Unregistred Members
	  */
	public void setUNS_TMT (Timestamp UNS_TMT)
	{
		set_Value (COLUMNNAME_UNS_TMT, UNS_TMT);
	}

	/** Get TMT.
		@return Date of Registered or Unregistred Members
	  */
	public Timestamp getUNS_TMT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UNS_TMT);
	}

	/** Set Unit.
		@param UNS_Unit Unit	  */
	public void setUNS_Unit (String UNS_Unit)
	{
		set_Value (COLUMNNAME_UNS_Unit, UNS_Unit);
	}

	/** Get Unit.
		@return Unit	  */
	public String getUNS_Unit () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Unit);
	}
}