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

/** Generated Model for UNS_AchievedIncentive_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AchievedIncentive_Line extends PO implements I_UNS_AchievedIncentive_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180402L;

    /** Standard Constructor */
    public X_UNS_AchievedIncentive_Line (Properties ctx, int UNS_AchievedIncentive_Line_ID, String trxName)
    {
      super (ctx, UNS_AchievedIncentive_Line_ID, trxName);
      /** if (UNS_AchievedIncentive_Line_ID == 0)
        {
			setAmountBase (Env.ZERO);
// 0
			setSchemaType (null);
			setSourceType (null);
			setUNS_AchievedIncentive_ID (0);
			setUNS_AchievedIncentive_Line_ID (0);
			setUNS_AcvIncentiveByPeriod_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_AchievedIncentive_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AchievedIncentive_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount Base.
		@param AmountBase Amount Base	  */
	public void setAmountBase (BigDecimal AmountBase)
	{
		set_Value (COLUMNNAME_AmountBase, AmountBase);
	}

	/** Get Amount Base.
		@return Amount Base	  */
	public BigDecimal getAmountBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountBase);
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
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
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

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentAllocate getC_PaymentAllocate() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentAllocate)MTable.get(getCtx(), org.compiere.model.I_C_PaymentAllocate.Table_Name)
			.getPO(getC_PaymentAllocate_ID(), get_TrxName());	}

	/** Set Allocate Payment.
		@param C_PaymentAllocate_ID 
		Allocate Payment to Invoices
	  */
	public void setC_PaymentAllocate_ID (int C_PaymentAllocate_ID)
	{
		if (C_PaymentAllocate_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentAllocate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentAllocate_ID, Integer.valueOf(C_PaymentAllocate_ID));
	}

	/** Get Allocate Payment.
		@return Allocate Payment to Invoices
	  */
	public int getC_PaymentAllocate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentAllocate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Sales Incentive = SI */
	public static final String SCHEMATYPE_SalesIncentive = "SI";
	/** Sales Target = ST */
	public static final String SCHEMATYPE_SalesTarget = "ST";
	/** Customer Target = CT */
	public static final String SCHEMATYPE_CustomerTarget = "CT";
	/** Set Schema Type.
		@param SchemaType Schema Type	  */
	public void setSchemaType (String SchemaType)
	{

		set_Value (COLUMNNAME_SchemaType, SchemaType);
	}

	/** Get Schema Type.
		@return Schema Type	  */
	public String getSchemaType () 
	{
		return (String)get_Value(COLUMNNAME_SchemaType);
	}

	/** Sales Order Line = SOL */
	public static final String SOURCETYPE_SalesOrderLine = "SOL";
	/** Invoice Line = INL */
	public static final String SOURCETYPE_InvoiceLine = "INL";
	/** Invoice New Outlet = INO */
	public static final String SOURCETYPE_InvoiceNewOutlet = "INO";
	/** Invoice Over Due = IOD */
	public static final String SOURCETYPE_InvoiceOverDue = "IOD";
	/** Payment = PAY */
	public static final String SOURCETYPE_Payment = "PAY";
	/** Set Source Type.
		@param SourceType Source Type	  */
	public void setSourceType (String SourceType)
	{

		set_Value (COLUMNNAME_SourceType, SourceType);
	}

	/** Get Source Type.
		@return Source Type	  */
	public String getSourceType () 
	{
		return (String)get_Value(COLUMNNAME_SourceType);
	}

	public com.unicore.model.I_UNS_AchievedIncentive getUNS_AchievedIncentive() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_AchievedIncentive)MTable.get(getCtx(), com.unicore.model.I_UNS_AchievedIncentive.Table_Name)
			.getPO(getUNS_AchievedIncentive_ID(), get_TrxName());	}

	/** Set Achieved Incentive.
		@param UNS_AchievedIncentive_ID Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID)
	{
		if (UNS_AchievedIncentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, Integer.valueOf(UNS_AchievedIncentive_ID));
	}

	/** Get Achieved Incentive.
		@return Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AchievedIncentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Achieved Incentive Line.
		@param UNS_AchievedIncentive_Line_ID Achieved Incentive Line	  */
	public void setUNS_AchievedIncentive_Line_ID (int UNS_AchievedIncentive_Line_ID)
	{
		if (UNS_AchievedIncentive_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_Line_ID, Integer.valueOf(UNS_AchievedIncentive_Line_ID));
	}

	/** Get Achieved Incentive Line.
		@return Achieved Incentive Line	  */
	public int getUNS_AchievedIncentive_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AchievedIncentive_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AchievedIncentive_Line_UU.
		@param UNS_AchievedIncentive_Line_UU UNS_AchievedIncentive_Line_UU	  */
	public void setUNS_AchievedIncentive_Line_UU (String UNS_AchievedIncentive_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_Line_UU, UNS_AchievedIncentive_Line_UU);
	}

	/** Get UNS_AchievedIncentive_Line_UU.
		@return UNS_AchievedIncentive_Line_UU	  */
	public String getUNS_AchievedIncentive_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AchievedIncentive_Line_UU);
	}

	/** Set Achieved Incentive By Period.
		@param UNS_AcvIncentiveByPeriod_ID Achieved Incentive By Period	  */
	public void setUNS_AcvIncentiveByPeriod_ID (int UNS_AcvIncentiveByPeriod_ID)
	{
		if (UNS_AcvIncentiveByPeriod_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AcvIncentiveByPeriod_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AcvIncentiveByPeriod_ID, Integer.valueOf(UNS_AcvIncentiveByPeriod_ID));
	}

	/** Get Achieved Incentive By Period.
		@return Achieved Incentive By Period	  */
	public int getUNS_AcvIncentiveByPeriod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AcvIncentiveByPeriod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Incentive)MTable.get(getCtx(), com.unicore.model.I_UNS_Incentive.Table_Name)
			.getPO(getUNS_Incentive_ID(), get_TrxName());	}

	/** Set Incentive.
		@param UNS_Incentive_ID Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID)
	{
		if (UNS_Incentive_ID < 1) 
			set_Value (COLUMNNAME_UNS_Incentive_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Incentive_ID, Integer.valueOf(UNS_Incentive_ID));
	}

	/** Get Incentive.
		@return Incentive	  */
	public int getUNS_Incentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Incentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Shipping getUNS_Shipping() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Shipping)MTable.get(getCtx(), com.unicore.model.I_UNS_Shipping.Table_Name)
			.getPO(getUNS_Shipping_ID(), get_TrxName());	}

	/** Set Shipping Document.
		@param UNS_Shipping_ID Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID)
	{
		if (UNS_Shipping_ID < 1) 
			set_Value (COLUMNNAME_UNS_Shipping_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Shipping_ID, Integer.valueOf(UNS_Shipping_ID));
	}

	/** Get Shipping Document.
		@return Shipping Document	  */
	public int getUNS_Shipping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Shipping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}