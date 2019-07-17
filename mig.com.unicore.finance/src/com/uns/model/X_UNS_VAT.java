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

/** Generated Model for UNS_VAT
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VAT extends PO implements I_UNS_VAT, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180313L;

    /** Standard Constructor */
    public X_UNS_VAT (Properties ctx, int UNS_VAT_ID, String trxName)
    {
      super (ctx, UNS_VAT_ID, trxName);
      /** if (UNS_VAT_ID == 0)
        {
			setAssignedBy_ID (0);
			setBeforeTaxAmt (Env.ZERO);
// 0
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateReport (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setDifferenceTaxAmt (Env.ZERO);
// 0
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setExpFormatExcel (null);
// N
			setExpFormatImporter (null);
// N
			setIsApproved (false);
// N
			setisCreditMemo (false);
// N
			setIsSOTrx (true);
// Y
			setProcessed (false);
// N
			setRevisionAmt (Env.ZERO);
// 0
			setRevisionBeforeTaxAmt (Env.ZERO);
// 0
			setRevisionTaxAmt (Env.ZERO);
// 0
			setTaxAmt (Env.ZERO);
// 0
			setUpdateAmount (null);
// N
			setUpdateRecord (null);
// N
        } */
    }

    /** Load Constructor */
    public X_UNS_VAT (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VAT[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAssignedBy() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAssignedBy_ID(), get_TrxName());	}

	/** Set Assigned By.
		@param AssignedBy_ID Assigned By	  */
	public void setAssignedBy_ID (int AssignedBy_ID)
	{
		if (AssignedBy_ID < 1) 
			set_Value (COLUMNNAME_AssignedBy_ID, null);
		else 
			set_Value (COLUMNNAME_AssignedBy_ID, Integer.valueOf(AssignedBy_ID));
	}

	/** Get Assigned By.
		@return Assigned By	  */
	public int getAssignedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AssignedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Before Tax Amount.
		@param BeforeTaxAmt Before Tax Amount	  */
	public void setBeforeTaxAmt (BigDecimal BeforeTaxAmt)
	{
		set_Value (COLUMNNAME_BeforeTaxAmt, BeforeTaxAmt);
	}

	/** Get Before Tax Amount.
		@return Before Tax Amount	  */
	public BigDecimal getBeforeTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BeforeTaxAmt);
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

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create lines from.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create lines from.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Report Date.
		@param DateReport 
		Expense/Time Report Date
	  */
	public void setDateReport (Timestamp DateReport)
	{
		set_Value (COLUMNNAME_DateReport, DateReport);
	}

	/** Get Report Date.
		@return Expense/Time Report Date
	  */
	public Timestamp getDateReport () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReport);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getDateReport()));
    }

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	/** Set Difference Tax Amt.
		@param DifferenceTaxAmt Difference Tax Amt	  */
	public void setDifferenceTaxAmt (BigDecimal DifferenceTaxAmt)
	{
		set_Value (COLUMNNAME_DifferenceTaxAmt, DifferenceTaxAmt);
	}

	/** Get Difference Tax Amt.
		@return Difference Tax Amt	  */
	public BigDecimal getDifferenceTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Export Format Excel.
		@param ExpFormatExcel Export Format Excel	  */
	public void setExpFormatExcel (String ExpFormatExcel)
	{
		set_Value (COLUMNNAME_ExpFormatExcel, ExpFormatExcel);
	}

	/** Get Export Format Excel.
		@return Export Format Excel	  */
	public String getExpFormatExcel () 
	{
		return (String)get_Value(COLUMNNAME_ExpFormatExcel);
	}

	/** Set Export Format Importer.
		@param ExpFormatImporter Export Format Importer	  */
	public void setExpFormatImporter (String ExpFormatImporter)
	{
		set_Value (COLUMNNAME_ExpFormatImporter, ExpFormatImporter);
	}

	/** Get Export Format Importer.
		@return Export Format Importer	  */
	public String getExpFormatImporter () 
	{
		return (String)get_Value(COLUMNNAME_ExpFormatImporter);
	}

	/** Set Generate Tax Invoice No.
		@param GenerateTaxInvoiceNo Generate Tax Invoice No	  */
	public void setGenerateTaxInvoiceNo (String GenerateTaxInvoiceNo)
	{
		set_Value (COLUMNNAME_GenerateTaxInvoiceNo, GenerateTaxInvoiceNo);
	}

	/** Get Generate Tax Invoice No.
		@return Generate Tax Invoice No	  */
	public String getGenerateTaxInvoiceNo () 
	{
		return (String)get_Value(COLUMNNAME_GenerateTaxInvoiceNo);
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

	/** Set Credit Memo.
		@param isCreditMemo 
		identification return transaction
	  */
	public void setisCreditMemo (boolean isCreditMemo)
	{
		set_Value (COLUMNNAME_isCreditMemo, Boolean.valueOf(isCreditMemo));
	}

	/** Get Credit Memo.
		@return identification return transaction
	  */
	public boolean isCreditMemo () 
	{
		Object oo = get_Value(COLUMNNAME_isCreditMemo);
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
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
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

	/** Set Print Document.
		@param PrintDocument Print Document	  */
	public void setPrintDocument (String PrintDocument)
	{
		set_Value (COLUMNNAME_PrintDocument, PrintDocument);
	}

	/** Get Print Document.
		@return Print Document	  */
	public String getPrintDocument () 
	{
		return (String)get_Value(COLUMNNAME_PrintDocument);
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

	/** Set Revision Amount.
		@param RevisionAmt Revision Amount	  */
	public void setRevisionAmt (BigDecimal RevisionAmt)
	{
		set_Value (COLUMNNAME_RevisionAmt, RevisionAmt);
	}

	/** Get Revision Amount.
		@return Revision Amount	  */
	public BigDecimal getRevisionAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevisionAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Revision Before Tax Amt.
		@param RevisionBeforeTaxAmt Revision Before Tax Amt	  */
	public void setRevisionBeforeTaxAmt (BigDecimal RevisionBeforeTaxAmt)
	{
		set_Value (COLUMNNAME_RevisionBeforeTaxAmt, RevisionBeforeTaxAmt);
	}

	/** Get Revision Before Tax Amt.
		@return Revision Before Tax Amt	  */
	public BigDecimal getRevisionBeforeTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevisionBeforeTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Revision Tax Amount.
		@param RevisionTaxAmt Revision Tax Amount	  */
	public void setRevisionTaxAmt (BigDecimal RevisionTaxAmt)
	{
		set_Value (COLUMNNAME_RevisionTaxAmt, RevisionTaxAmt);
	}

	/** Get Revision Tax Amount.
		@return Revision Tax Amount	  */
	public BigDecimal getRevisionTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevisionTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Invoice.
		@param TotalInvoice Total Invoice	  */
	public void setTotalInvoice (int TotalInvoice)
	{
		throw new IllegalArgumentException ("TotalInvoice is virtual column");	}

	/** Get Total Invoice.
		@return Total Invoice	  */
	public int getTotalInvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalInvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VAT_ID.
		@param UNS_VAT_ID UNS_VAT_ID	  */
	public void setUNS_VAT_ID (int UNS_VAT_ID)
	{
		if (UNS_VAT_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VAT_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VAT_ID, Integer.valueOf(UNS_VAT_ID));
	}

	/** Get UNS_VAT_ID.
		@return UNS_VAT_ID	  */
	public int getUNS_VAT_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VAT_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VAT_UU.
		@param UNS_VAT_UU UNS_VAT_UU	  */
	public void setUNS_VAT_UU (String UNS_VAT_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_VAT_UU, UNS_VAT_UU);
	}

	/** Get UNS_VAT_UU.
		@return UNS_VAT_UU	  */
	public String getUNS_VAT_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VAT_UU);
	}

	public I_UNS_VATPayment getUNS_VATPayment() throws RuntimeException
    {
		return (I_UNS_VATPayment)MTable.get(getCtx(), I_UNS_VATPayment.Table_Name)
			.getPO(getUNS_VATPayment_ID(), get_TrxName());	}

	/** Set VAT Payment.
		@param UNS_VATPayment_ID VAT Payment	  */
	public void setUNS_VATPayment_ID (int UNS_VATPayment_ID)
	{
		if (UNS_VATPayment_ID < 1) 
			set_Value (COLUMNNAME_UNS_VATPayment_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_VATPayment_ID, Integer.valueOf(UNS_VATPayment_ID));
	}

	/** Get VAT Payment.
		@return VAT Payment	  */
	public int getUNS_VATPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Update Amount.
		@param UpdateAmount Update Amount	  */
	public void setUpdateAmount (String UpdateAmount)
	{
		set_Value (COLUMNNAME_UpdateAmount, UpdateAmount);
	}

	/** Get Update Amount.
		@return Update Amount	  */
	public String getUpdateAmount () 
	{
		return (String)get_Value(COLUMNNAME_UpdateAmount);
	}

	/** Set Update Record.
		@param UpdateRecord Update Record	  */
	public void setUpdateRecord (String UpdateRecord)
	{
		set_Value (COLUMNNAME_UpdateRecord, UpdateRecord);
	}

	/** Get Update Record.
		@return Update Record	  */
	public String getUpdateRecord () 
	{
		return (String)get_Value(COLUMNNAME_UpdateRecord);
	}
}