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

/** Generated Model for UNS_VATLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VATLine extends PO implements I_UNS_VATLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180605L;

    /** Standard Constructor */
    public X_UNS_VATLine (Properties ctx, int UNS_VATLine_ID, String trxName)
    {
      super (ctx, UNS_VATLine_ID, trxName);
      /** if (UNS_VATLine_ID == 0)
        {
			setBeforeTaxAmt (Env.ZERO);
// 0
			setDifferenceTaxAmt (Env.ZERO);
// 0
			setGrandTotal (Env.ZERO);
// 0
			setIsCanvas (false);
// N
			setIsManual (false);
// N
			setisReplacement (false);
// N
			setLineNo (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_VATLine WHERE UNS_VAT_ID=@UNS_VAT_ID@
			setRevisionAmt (Env.ZERO);
// 0
			setRevisionBeforeTaxAmt (Env.ZERO);
// 0
			setRevisionTaxAmt (Env.ZERO);
// 0
			setTaxAmt (Env.ZERO);
// 0
			setUNS_VATLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VATLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VATLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Additional Reference No.
		@param AdditionalReferenceNo Additional Reference No	  */
	public void setAdditionalReferenceNo (String AdditionalReferenceNo)
	{
		set_Value (COLUMNNAME_AdditionalReferenceNo, AdditionalReferenceNo);
	}

	/** Get Additional Reference No.
		@return Additional Reference No	  */
	public String getAdditionalReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_AdditionalReferenceNo);
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Canvas.
		@param IsCanvas Canvas	  */
	public void setIsCanvas (boolean IsCanvas)
	{
		set_Value (COLUMNNAME_IsCanvas, Boolean.valueOf(IsCanvas));
	}

	/** Get Canvas.
		@return Canvas	  */
	public boolean isCanvas () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanvas);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
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

	/** Set Line.
		@param LineNo 
		Line No
	  */
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Line.
		@return Line No
	  */
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_VATLine getReference() throws RuntimeException
    {
		return (I_UNS_VATLine)MTable.get(getCtx(), I_UNS_VATLine.Table_Name)
			.getPO(getReference_ID(), get_TrxName());	}

	/** Set Refrerence Record.
		@param Reference_ID Refrerence Record	  */
	public void setReference_ID (int Reference_ID)
	{
		if (Reference_ID < 1) 
			set_Value (COLUMNNAME_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_Reference_ID, Integer.valueOf(Reference_ID));
	}

	/** Get Refrerence Record.
		@return Refrerence Record	  */
	public int getReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
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

	/** Set Tax Address.
		@param TaxAddress 
		The address of Business Partner registered on Tax Office
	  */
	public void setTaxAddress (String TaxAddress)
	{
		set_Value (COLUMNNAME_TaxAddress, TaxAddress);
	}

	/** Get Tax Address.
		@return The address of Business Partner registered on Tax Office
	  */
	public String getTaxAddress () 
	{
		return (String)get_Value(COLUMNNAME_TaxAddress);
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

	/** Set Tax Invoice No.
		@param TaxInvoiceNo Tax Invoice No	  */
	public void setTaxInvoiceNo (String TaxInvoiceNo)
	{
		set_Value (COLUMNNAME_TaxInvoiceNo, TaxInvoiceNo);
	}

	/** Get Tax Invoice No.
		@return Tax Invoice No	  */
	public String getTaxInvoiceNo () 
	{
		return (String)get_Value(COLUMNNAME_TaxInvoiceNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getTaxInvoiceNo());
    }

	/** Set Name for Tax.
		@param TaxName 
		The name identification of Business Partner registered on Tax Office
	  */
	public void setTaxName (String TaxName)
	{
		set_Value (COLUMNNAME_TaxName, TaxName);
	}

	/** Get Name for Tax.
		@return The name identification of Business Partner registered on Tax Office
	  */
	public String getTaxName () 
	{
		return (String)get_Value(COLUMNNAME_TaxName);
	}

	/** Set Tax Serial No.
		@param TaxSerialNo 
		The serial number of Business Partner Tax
	  */
	public void setTaxSerialNo (String TaxSerialNo)
	{
		set_Value (COLUMNNAME_TaxSerialNo, TaxSerialNo);
	}

	/** Get Tax Serial No.
		@return The serial number of Business Partner Tax
	  */
	public String getTaxSerialNo () 
	{
		return (String)get_Value(COLUMNNAME_TaxSerialNo);
	}

	public I_UNS_VAT getUNS_VAT() throws RuntimeException
    {
		return (I_UNS_VAT)MTable.get(getCtx(), I_UNS_VAT.Table_Name)
			.getPO(getUNS_VAT_ID(), get_TrxName());	}

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

	/** Set VAT Lines.
		@param UNS_VATLine_ID VAT Lines	  */
	public void setUNS_VATLine_ID (int UNS_VATLine_ID)
	{
		if (UNS_VATLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATLine_ID, Integer.valueOf(UNS_VATLine_ID));
	}

	/** Get VAT Lines.
		@return VAT Lines	  */
	public int getUNS_VATLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VATLine_UU.
		@param UNS_VATLine_UU UNS_VATLine_UU	  */
	public void setUNS_VATLine_UU (String UNS_VATLine_UU)
	{
		set_Value (COLUMNNAME_UNS_VATLine_UU, UNS_VATLine_UU);
	}

	/** Get UNS_VATLine_UU.
		@return UNS_VATLine_UU	  */
	public String getUNS_VATLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VATLine_UU);
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
}