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

/** Generated Model for UNS_BillingGroup
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BillingGroup extends PO implements I_UNS_BillingGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180521L;

    /** Standard Constructor */
    public X_UNS_BillingGroup (Properties ctx, int UNS_BillingGroup_ID, String trxName)
    {
      super (ctx, UNS_BillingGroup_ID, trxName);
      /** if (UNS_BillingGroup_ID == 0)
        {
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setGenerateBillingLine (null);
			setGrandTotal (Env.ZERO);
			setIsApproved (false);
// N
			setPaidAmt (Env.ZERO);
// 0
			setProcessed (false);
// N
			setTotalInvoice (0);
// 0
			setUNS_BillingGroup_ID (0);
			setUNS_Rayon_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BillingGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BillingGroup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Consolidate to one Document.
		@param ConsolidateDocument 
		Consolidate Lines into one Document
	  */
	public void setConsolidateDocument (boolean ConsolidateDocument)
	{
		set_Value (COLUMNNAME_ConsolidateDocument, Boolean.valueOf(ConsolidateDocument));
	}

	/** Get Consolidate to one Document.
		@return Consolidate Lines into one Document
	  */
	public boolean isConsolidateDocument () 
	{
		Object oo = get_Value(COLUMNNAME_ConsolidateDocument);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Create Invoice Billing.
		@param GenerateBillingLine Create Invoice Billing	  */
	public void setGenerateBillingLine (String GenerateBillingLine)
	{
		set_Value (COLUMNNAME_GenerateBillingLine, GenerateBillingLine);
	}

	/** Get Create Invoice Billing.
		@return Create Invoice Billing	  */
	public String getGenerateBillingLine () 
	{
		return (String)get_Value(COLUMNNAME_GenerateBillingLine);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
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

	/** Set Paid Amount.
		@param PaidAmt 
		Total Paid Amount of Document
	  */
	public void setPaidAmt (BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	/** Get Paid Amount.
		@return Total Paid Amount of Document
	  */
	public BigDecimal getPaidAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaidAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Print Daftar Faktur Potong Retur/Diskon.
		@param PrintDaftarFakturPotongReDis Print Daftar Faktur Potong Retur/Diskon	  */
	public void setPrintDaftarFakturPotongReDis (String PrintDaftarFakturPotongReDis)
	{
		set_Value (COLUMNNAME_PrintDaftarFakturPotongReDis, PrintDaftarFakturPotongReDis);
	}

	/** Get Print Daftar Faktur Potong Retur/Diskon.
		@return Print Daftar Faktur Potong Retur/Diskon	  */
	public String getPrintDaftarFakturPotongReDis () 
	{
		return (String)get_Value(COLUMNNAME_PrintDaftarFakturPotongReDis);
	}

	/** Set Print Daftar Tagihan Tukar Guling.
		@param PrintDaftarTagihTukarGuling Print Daftar Tagihan Tukar Guling	  */
	public void setPrintDaftarTagihTukarGuling (String PrintDaftarTagihTukarGuling)
	{
		set_Value (COLUMNNAME_PrintDaftarTagihTukarGuling, PrintDaftarTagihTukarGuling);
	}

	/** Get Print Daftar Tagihan Tukar Guling.
		@return Print Daftar Tagihan Tukar Guling	  */
	public String getPrintDaftarTagihTukarGuling () 
	{
		return (String)get_Value(COLUMNNAME_PrintDaftarTagihTukarGuling);
	}

	/** Set Print Faktur Pajak.
		@param PrintFakturPajak Print Faktur Pajak	  */
	public void setPrintFakturPajak (String PrintFakturPajak)
	{
		set_Value (COLUMNNAME_PrintFakturPajak, PrintFakturPajak);
	}

	/** Get Print Faktur Pajak.
		@return Print Faktur Pajak	  */
	public String getPrintFakturPajak () 
	{
		return (String)get_Value(COLUMNNAME_PrintFakturPajak);
	}

	/** Set Print Surat Jalan Penagihan.
		@param PrintSJPenagihan Print Surat Jalan Penagihan	  */
	public void setPrintSJPenagihan (String PrintSJPenagihan)
	{
		set_Value (COLUMNNAME_PrintSJPenagihan, PrintSJPenagihan);
	}

	/** Get Print Surat Jalan Penagihan.
		@return Print Surat Jalan Penagihan	  */
	public String getPrintSJPenagihan () 
	{
		return (String)get_Value(COLUMNNAME_PrintSJPenagihan);
	}

	/** Set Print Tanda Titip Faktur.
		@param PrintTTFaktur Print Tanda Titip Faktur	  */
	public void setPrintTTFaktur (String PrintTTFaktur)
	{
		set_Value (COLUMNNAME_PrintTTFaktur, PrintTTFaktur);
	}

	/** Get Print Tanda Titip Faktur.
		@return Print Tanda Titip Faktur	  */
	public String getPrintTTFaktur () 
	{
		return (String)get_Value(COLUMNNAME_PrintTTFaktur);
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Invoice.
		@param TotalInvoice Total Invoice	  */
	public void setTotalInvoice (int TotalInvoice)
	{
		set_Value (COLUMNNAME_TotalInvoice, Integer.valueOf(TotalInvoice));
	}

	/** Get Total Invoice.
		@return Total Invoice	  */
	public int getTotalInvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalInvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Grouping Billing.
		@param UNS_BillingGroup_ID 
		Grouping Billing by parameter
	  */
	public void setUNS_BillingGroup_ID (int UNS_BillingGroup_ID)
	{
		if (UNS_BillingGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BillingGroup_ID, Integer.valueOf(UNS_BillingGroup_ID));
	}

	/** Get Grouping Billing.
		@return Grouping Billing by parameter
	  */
	public int getUNS_BillingGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BillingGroup_UU.
		@param UNS_BillingGroup_UU UNS_BillingGroup_UU	  */
	public void setUNS_BillingGroup_UU (String UNS_BillingGroup_UU)
	{
		set_Value (COLUMNNAME_UNS_BillingGroup_UU, UNS_BillingGroup_UU);
	}

	/** Get UNS_BillingGroup_UU.
		@return UNS_BillingGroup_UU	  */
	public String getUNS_BillingGroup_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BillingGroup_UU);
	}

	/** Set Rayon.
		@param UNS_Rayon_ID Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID)
	{
		if (UNS_Rayon_ID < 1) 
			set_Value (COLUMNNAME_UNS_Rayon_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Rayon_ID, Integer.valueOf(UNS_Rayon_ID));
	}

	/** Get Rayon.
		@return Rayon	  */
	public int getUNS_Rayon_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Rayon_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}