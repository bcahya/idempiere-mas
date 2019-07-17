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

/** Generated Model for UNS_AuditDocument
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AuditDocument extends PO implements I_UNS_AuditDocument, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170603L;

    /** Standard Constructor */
    public X_UNS_AuditDocument (Properties ctx, int UNS_AuditDocument_ID, String trxName)
    {
      super (ctx, UNS_AuditDocument_ID, trxName);
      /** if (UNS_AuditDocument_ID == 0)
        {
			setC_DocType_ID (0);
			setC_Invoice_ID (0);
			setDocumentNo (null);
			setGrandTotal (Env.ZERO);
// 0
			setOpenAmt (Env.ZERO);
// 0
			setUNS_AuditDocument_ID (0);
			setUNS_AuditPartner_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_AuditDocument (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_AuditDocument[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Open Amount.
		@param OpenAmt 
		Open item amount
	  */
	public void setOpenAmt (BigDecimal OpenAmt)
	{
		set_ValueNoCheck (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Open Amount.
		@return Open item amount
	  */
	public BigDecimal getOpenAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Documents.
		@param UNS_AuditDocument_ID Documents	  */
	public void setUNS_AuditDocument_ID (int UNS_AuditDocument_ID)
	{
		if (UNS_AuditDocument_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditDocument_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditDocument_ID, Integer.valueOf(UNS_AuditDocument_ID));
	}

	/** Get Documents.
		@return Documents	  */
	public int getUNS_AuditDocument_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AuditDocument_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AuditDocument_UU.
		@param UNS_AuditDocument_UU UNS_AuditDocument_UU	  */
	public void setUNS_AuditDocument_UU (String UNS_AuditDocument_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AuditDocument_UU, UNS_AuditDocument_UU);
	}

	/** Get UNS_AuditDocument_UU.
		@return UNS_AuditDocument_UU	  */
	public String getUNS_AuditDocument_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AuditDocument_UU);
	}

	public I_UNS_AuditPartner getUNS_AuditPartner() throws RuntimeException
    {
		return (I_UNS_AuditPartner)MTable.get(getCtx(), I_UNS_AuditPartner.Table_Name)
			.getPO(getUNS_AuditPartner_ID(), get_TrxName());	}

	/** Set Partner.
		@param UNS_AuditPartner_ID Partner	  */
	public void setUNS_AuditPartner_ID (int UNS_AuditPartner_ID)
	{
		if (UNS_AuditPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditPartner_ID, Integer.valueOf(UNS_AuditPartner_ID));
	}

	/** Get Partner.
		@return Partner	  */
	public int getUNS_AuditPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AuditPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}