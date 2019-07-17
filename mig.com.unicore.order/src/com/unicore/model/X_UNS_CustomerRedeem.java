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

/** Generated Model for UNS_CustomerRedeem
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerRedeem extends PO implements I_UNS_CustomerRedeem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150520L;

    /** Standard Constructor */
    public X_UNS_CustomerRedeem (Properties ctx, int UNS_CustomerRedeem_ID, String trxName)
    {
      super (ctx, UNS_CustomerRedeem_ID, trxName);
      /** if (UNS_CustomerRedeem_ID == 0)
        {
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setProcessed (false);
			setUNS_CustomerRedeem_ID (0);
			setUNS_CustomerRedeem_UU (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerRedeem (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerRedeem[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	public void setFax (String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	public String getFax () 
	{
		return (String)get_Value(COLUMNNAME_Fax);
	}

	public I_C_Location getOwnerAddress() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getOwnerAddress_ID(), get_TrxName());	}

	/** Set Owner Address_ID.
		@param OwnerAddress_ID 
		Address Owner from Business Partner
	  */
	public void setOwnerAddress_ID (int OwnerAddress_ID)
	{
		if (OwnerAddress_ID < 1) 
			set_Value (COLUMNNAME_OwnerAddress_ID, null);
		else 
			set_Value (COLUMNNAME_OwnerAddress_ID, Integer.valueOf(OwnerAddress_ID));
	}

	/** Get Owner Address_ID.
		@return Address Owner from Business Partner
	  */
	public int getOwnerAddress_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OwnerAddress_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Owner Email.
		@param OwnerEmail Owner Email	  */
	public void setOwnerEmail (String OwnerEmail)
	{
		set_Value (COLUMNNAME_OwnerEmail, OwnerEmail);
	}

	/** Get Owner Email.
		@return Owner Email	  */
	public String getOwnerEmail () 
	{
		return (String)get_Value(COLUMNNAME_OwnerEmail);
	}

	/** Set Owner Fax.
		@param OwnerFax Owner Fax	  */
	public void setOwnerFax (String OwnerFax)
	{
		set_Value (COLUMNNAME_OwnerFax, OwnerFax);
	}

	/** Get Owner Fax.
		@return Owner Fax	  */
	public String getOwnerFax () 
	{
		return (String)get_Value(COLUMNNAME_OwnerFax);
	}

	/** Set Owner Name.
		@param OwnerName 
		The name of the owner of the business partner
	  */
	public void setOwnerName (String OwnerName)
	{
		set_Value (COLUMNNAME_OwnerName, OwnerName);
	}

	/** Get Owner Name.
		@return The name of the owner of the business partner
	  */
	public String getOwnerName () 
	{
		return (String)get_Value(COLUMNNAME_OwnerName);
	}

	/** Set Owner Phone.
		@param OwnerPhone Owner Phone	  */
	public void setOwnerPhone (String OwnerPhone)
	{
		set_Value (COLUMNNAME_OwnerPhone, OwnerPhone);
	}

	/** Get Owner Phone.
		@return Owner Phone	  */
	public String getOwnerPhone () 
	{
		return (String)get_Value(COLUMNNAME_OwnerPhone);
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

	public com.unicore.model.I_UNS_CustomerPoint getUNS_CustomerPoint() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CustomerPoint)MTable.get(getCtx(), com.unicore.model.I_UNS_CustomerPoint.Table_Name)
			.getPO(getUNS_CustomerPoint_ID(), get_TrxName());	}

	/** Set Customer Point.
		@param UNS_CustomerPoint_ID Customer Point	  */
	public void setUNS_CustomerPoint_ID (int UNS_CustomerPoint_ID)
	{
		if (UNS_CustomerPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, Integer.valueOf(UNS_CustomerPoint_ID));
	}

	/** Get Customer Point.
		@return Customer Point	  */
	public int getUNS_CustomerPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Redeem Point.
		@param UNS_CustomerRedeem_ID Customer Redeem Point	  */
	public void setUNS_CustomerRedeem_ID (int UNS_CustomerRedeem_ID)
	{
		if (UNS_CustomerRedeem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, Integer.valueOf(UNS_CustomerRedeem_ID));
	}

	/** Get Customer Redeem Point.
		@return Customer Redeem Point	  */
	public int getUNS_CustomerRedeem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerRedeem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CustomerRedeem_UU.
		@param UNS_CustomerRedeem_UU UNS_CustomerRedeem_UU	  */
	public void setUNS_CustomerRedeem_UU (String UNS_CustomerRedeem_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_UU, UNS_CustomerRedeem_UU);
	}

	/** Get UNS_CustomerRedeem_UU.
		@return UNS_CustomerRedeem_UU	  */
	public String getUNS_CustomerRedeem_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerRedeem_UU);
	}
}