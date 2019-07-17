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

/** Generated Model for UNS_VATDocInOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VATDocInOut extends PO implements I_UNS_VATDocInOut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150227L;

    /** Standard Constructor */
    public X_UNS_VATDocInOut (Properties ctx, int UNS_VATDocInOut_ID, String trxName)
    {
      super (ctx, UNS_VATDocInOut_ID, trxName);
      /** if (UNS_VATDocInOut_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_UNS_VATDocInOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VATDocInOut[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner.
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

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set DeliveredBy.
		@param DeliveredBy 
		DeliveredBy
	  */
	public void setDeliveredBy (String DeliveredBy)
	{
		set_Value (COLUMNNAME_DeliveredBy, DeliveredBy);
	}

	/** Get DeliveredBy.
		@return DeliveredBy
	  */
	public String getDeliveredBy () 
	{
		return (String)get_Value(COLUMNNAME_DeliveredBy);
	}

	/** Set Delivered By.
		@param DeliveredBy_ID Delivered By	  */
	public void setDeliveredBy_ID (int DeliveredBy_ID)
	{
		if (DeliveredBy_ID < 1) 
			set_Value (COLUMNNAME_DeliveredBy_ID, null);
		else 
			set_Value (COLUMNNAME_DeliveredBy_ID, Integer.valueOf(DeliveredBy_ID));
	}

	/** Get Delivered By.
		@return Delivered By	  */
	public int getDeliveredBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveredBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DeliveredDate.
		@param DeliveredDate 
		Date Of Delivered
	  */
	public void setDeliveredDate (Timestamp DeliveredDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveredDate, DeliveredDate);
	}

	/** Get DeliveredDate.
		@return Date Of Delivered
	  */
	public Timestamp getDeliveredDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DeliveredDate);
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

	/** Set GenerateInvoicePaid.
		@param GenerateInvoicePaid GenerateInvoicePaid	  */
	public void setGenerateInvoicePaid (String GenerateInvoicePaid)
	{
		set_Value (COLUMNNAME_GenerateInvoicePaid, GenerateInvoicePaid);
	}

	/** Get GenerateInvoicePaid.
		@return GenerateInvoicePaid	  */
	public String getGenerateInvoicePaid () 
	{
		return (String)get_Value(COLUMNNAME_GenerateInvoicePaid);
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

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_ValueNoCheck (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isReplacement.
		@param isReplacement isReplacement	  */
	public void setisReplacement (boolean isReplacement)
	{
		set_Value (COLUMNNAME_isReplacement, Boolean.valueOf(isReplacement));
	}

	/** Get isReplacement.
		@return isReplacement	  */
	public boolean isReplacement () 
	{
		Object oo = get_Value(COLUMNNAME_isReplacement);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Notes.
		@param Notes Notes	  */
	public void setNotes (String Notes)
	{
		set_Value (COLUMNNAME_Notes, Notes);
	}

	/** Get Notes.
		@return Notes	  */
	public String getNotes () 
	{
		return (String)get_Value(COLUMNNAME_Notes);
	}

	/** Set PrintedBy.
		@param PrintedBy PrintedBy	  */
	public void setPrintedBy (int PrintedBy)
	{
		set_Value (COLUMNNAME_PrintedBy, Integer.valueOf(PrintedBy));
	}

	/** Get PrintedBy.
		@return PrintedBy	  */
	public int getPrintedBy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrintedBy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PrintedDate.
		@param PrintedDate PrintedDate	  */
	public void setPrintedDate (Timestamp PrintedDate)
	{
		set_Value (COLUMNNAME_PrintedDate, PrintedDate);
	}

	/** Get PrintedDate.
		@return PrintedDate	  */
	public Timestamp getPrintedDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PrintedDate);
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

	/** Set Receipt Date.
		@param ReceiptDate 
		Receipt Date
	  */
	public void setReceiptDate (Timestamp ReceiptDate)
	{
		set_Value (COLUMNNAME_ReceiptDate, ReceiptDate);
	}

	/** Get Receipt Date.
		@return Receipt Date
	  */
	public Timestamp getReceiptDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReceiptDate);
	}

	/** Set ReceivedBy.
		@param ReceivedBy ReceivedBy	  */
	public void setReceivedBy (String ReceivedBy)
	{
		set_Value (COLUMNNAME_ReceivedBy, ReceivedBy);
	}

	/** Get ReceivedBy.
		@return ReceivedBy	  */
	public String getReceivedBy () 
	{
		return (String)get_Value(COLUMNNAME_ReceivedBy);
	}

	/** Set Received By.
		@param ReceivedBy_ID Received By	  */
	public void setReceivedBy_ID (int ReceivedBy_ID)
	{
		if (ReceivedBy_ID < 1) 
			set_Value (COLUMNNAME_ReceivedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ReceivedBy_ID, Integer.valueOf(ReceivedBy_ID));
	}

	/** Get Received By.
		@return Received By	  */
	public int getReceivedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReceivedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set StdVATDocNo.
		@param StdVATDocNo StdVATDocNo	  */
	public void setStdVATDocNo (String StdVATDocNo)
	{
		set_Value (COLUMNNAME_StdVATDocNo, StdVATDocNo);
	}

	/** Get StdVATDocNo.
		@return StdVATDocNo	  */
	public String getStdVATDocNo () 
	{
		return (String)get_Value(COLUMNNAME_StdVATDocNo);
	}

	/** Set UNS_VATDocInOut.
		@param UNS_VATDocInOut_ID UNS_VATDocInOut	  */
	public void setUNS_VATDocInOut_ID (int UNS_VATDocInOut_ID)
	{
		if (UNS_VATDocInOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocInOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocInOut_ID, Integer.valueOf(UNS_VATDocInOut_ID));
	}

	/** Get UNS_VATDocInOut.
		@return UNS_VATDocInOut	  */
	public int getUNS_VATDocInOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATDocInOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VATDocInOut_UU.
		@param UNS_VATDocInOut_UU UNS_VATDocInOut_UU	  */
	public void setUNS_VATDocInOut_UU (String UNS_VATDocInOut_UU)
	{
		set_Value (COLUMNNAME_UNS_VATDocInOut_UU, UNS_VATDocInOut_UU);
	}

	/** Get UNS_VATDocInOut_UU.
		@return UNS_VATDocInOut_UU	  */
	public String getUNS_VATDocInOut_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VATDocInOut_UU);
	}

	public com.unicore.model.I_UNS_VATDocNo getUNS_VATDocNo() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_VATDocNo)MTable.get(getCtx(), com.unicore.model.I_UNS_VATDocNo.Table_Name)
			.getPO(getUNS_VATDocNo_ID(), get_TrxName());	}

	/** Set UNS_VATDocNo_ID.
		@param UNS_VATDocNo_ID UNS_VATDocNo_ID	  */
	public void setUNS_VATDocNo_ID (int UNS_VATDocNo_ID)
	{
		if (UNS_VATDocNo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocNo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocNo_ID, Integer.valueOf(UNS_VATDocNo_ID));
	}

	/** Get UNS_VATDocNo_ID.
		@return UNS_VATDocNo_ID	  */
	public int getUNS_VATDocNo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATDocNo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}