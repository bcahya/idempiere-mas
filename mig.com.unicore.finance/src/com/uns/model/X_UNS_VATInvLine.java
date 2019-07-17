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

/** Generated Model for UNS_VATInvLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VATInvLine extends PO implements I_UNS_VATInvLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180313L;

    /** Standard Constructor */
    public X_UNS_VATInvLine (Properties ctx, int UNS_VATInvLine_ID, String trxName)
    {
      super (ctx, UNS_VATInvLine_ID, trxName);
      /** if (UNS_VATInvLine_ID == 0)
        {
			setBeforeTaxAmt (Env.ZERO);
// 0
			setC_InvoiceLine_ID (0);
			setC_Tax_ID (0);
			setC_UOM_ID (0);
			setDifferenceTaxAmt (Env.ZERO);
// 0
			setDiscountAmt (Env.ZERO);
// 0
			setLineNetAmt (Env.ZERO);
// 0
			setLineNo (0);
// 0
			setPriceList (Env.ZERO);
// 0
			setQtyInvoiced (Env.ZERO);
// 0
			setRevisionAmt (Env.ZERO);
// 0
			setRevisionBeforeTaxAmt (Env.ZERO);
// 0
			setRevisionDiscAmt (Env.ZERO);
// 0
			setRevisionPriceList (Env.ZERO);
// 0
			setRevisionTaxAmt (Env.ZERO);
// 0
			setTaxAmt (Env.ZERO);
// 0
			setUNS_VATInvLine_ID (0);
			setUNS_VATLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VATInvLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VATInvLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set Discount Amount.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Discount Amount.
		@return Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Revision Discount Amount.
		@param RevisionDiscAmt Revision Discount Amount	  */
	public void setRevisionDiscAmt (BigDecimal RevisionDiscAmt)
	{
		set_Value (COLUMNNAME_RevisionDiscAmt, RevisionDiscAmt);
	}

	/** Get Revision Discount Amount.
		@return Revision Discount Amount	  */
	public BigDecimal getRevisionDiscAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevisionDiscAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Revision Price List.
		@param RevisionPriceList Revision Price List	  */
	public void setRevisionPriceList (BigDecimal RevisionPriceList)
	{
		set_Value (COLUMNNAME_RevisionPriceList, RevisionPriceList);
	}

	/** Get Revision Price List.
		@return Revision Price List	  */
	public BigDecimal getRevisionPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RevisionPriceList);
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

	/** Set VAT Invoice Lines.
		@param UNS_VATInvLine_ID VAT Invoice Lines	  */
	public void setUNS_VATInvLine_ID (int UNS_VATInvLine_ID)
	{
		if (UNS_VATInvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VATInvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VATInvLine_ID, Integer.valueOf(UNS_VATInvLine_ID));
	}

	/** Get VAT Invoice Lines.
		@return VAT Invoice Lines	  */
	public int getUNS_VATInvLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VATInvLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VATInvLine_UU.
		@param UNS_VATInvLine_UU UNS_VATInvLine_UU	  */
	public void setUNS_VATInvLine_UU (String UNS_VATInvLine_UU)
	{
		set_Value (COLUMNNAME_UNS_VATInvLine_UU, UNS_VATInvLine_UU);
	}

	/** Get UNS_VATInvLine_UU.
		@return UNS_VATInvLine_UU	  */
	public String getUNS_VATInvLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VATInvLine_UU);
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
}