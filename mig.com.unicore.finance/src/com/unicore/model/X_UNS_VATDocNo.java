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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.*;

import com.uns.model.I_UNS_VATLine;

/** Generated Model for UNS_VATDocNo
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VATDocNo extends PO implements I_UNS_VATDocNo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171111L;

    /** Standard Constructor */
    public X_UNS_VATDocNo (Properties ctx, int UNS_VATDocNo_ID, String trxName)
    {
      super (ctx, UNS_VATDocNo_ID, trxName);
      /** if (UNS_VATDocNo_ID == 0)
        {
			setTaxInvoiceNo (null);
			setUNS_VATDocNo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VATDocNo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VATDocNo[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	/** Set SequenceUsedNo.
		@param SequenceUsedNo SequenceUsedNo	  */
	public void setSequenceUsedNo (int SequenceUsedNo)
	{
		set_Value (COLUMNNAME_SequenceUsedNo, Integer.valueOf(SequenceUsedNo));
	}

	/** Get SequenceUsedNo.
		@return SequenceUsedNo	  */
	public int getSequenceUsedNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SequenceUsedNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UNS_VATDocNo.
		@param UNS_VATDocNo_ID UNS_VATDocNo	  */
	public void setUNS_VATDocNo_ID (int UNS_VATDocNo_ID)
	{
		if (UNS_VATDocNo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocNo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATDocNo_ID, Integer.valueOf(UNS_VATDocNo_ID));
	}

	/** Get UNS_VATDocNo.
		@return UNS_VATDocNo	  */
	public int getUNS_VATDocNo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATDocNo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VATDocNo_UU.
		@param UNS_VATDocNo_UU UNS_VATDocNo_UU	  */
	public void setUNS_VATDocNo_UU (String UNS_VATDocNo_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_VATDocNo_UU, UNS_VATDocNo_UU);
	}

	/** Get UNS_VATDocNo_UU.
		@return UNS_VATDocNo_UU	  */
	public String getUNS_VATDocNo_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VATDocNo_UU);
	}

	public I_UNS_VATLine getUNS_VATLine() throws RuntimeException
    {
		return (I_UNS_VATLine)MTable.get(getCtx(), I_UNS_VATLine.Table_Name)
			.getPO(getUNS_VATLine_ID(), get_TrxName());	}

	/** Set VAT Lines.
		@param UNS_VATLine_ID VAT Lines	  */
	public void setUNS_VATLine_ID (int UNS_VATLine_ID)
	{
		if (UNS_VATLine_ID < 1) 
			set_Value (COLUMNNAME_UNS_VATLine_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_VATLine_ID, Integer.valueOf(UNS_VATLine_ID));
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

	public com.unicore.model.I_UNS_VATRegisteredSequences getUNS_VATRegisteredSequences() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_VATRegisteredSequences)MTable.get(getCtx(), com.unicore.model.I_UNS_VATRegisteredSequences.Table_Name)
			.getPO(getUNS_VATRegisteredSequences_ID(), get_TrxName());	}

	/** Set VAT Registered Sequences.
		@param UNS_VATRegisteredSequences_ID VAT Registered Sequences	  */
	public void setUNS_VATRegisteredSequences_ID (int UNS_VATRegisteredSequences_ID)
	{
		if (UNS_VATRegisteredSequences_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATRegisteredSequences_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATRegisteredSequences_ID, Integer.valueOf(UNS_VATRegisteredSequences_ID));
	}

	/** Get VAT Registered Sequences.
		@return VAT Registered Sequences	  */
	public int getUNS_VATRegisteredSequences_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATRegisteredSequences_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Cancelled = C */
	public static final String USAGESTATUS_Cancelled = "C";
	/** Not Used = NU */
	public static final String USAGESTATUS_NotUsed = "NU";
	/** Used = U */
	public static final String USAGESTATUS_Used = "U";
	/** Voided = VO */
	public static final String USAGESTATUS_Voided = "VO";
	/** Set UsageStatus.
		@param UsageStatus UsageStatus	  */
	public void setUsageStatus (String UsageStatus)
	{

		set_Value (COLUMNNAME_UsageStatus, UsageStatus);
	}

	/** Get UsageStatus.
		@return UsageStatus	  */
	public String getUsageStatus () 
	{
		return (String)get_Value(COLUMNNAME_UsageStatus);
	}
}