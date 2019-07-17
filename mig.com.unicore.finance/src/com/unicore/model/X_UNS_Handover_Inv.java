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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_Handover_Inv
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Handover_Inv extends PO implements I_UNS_Handover_Inv, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150106L;

    /** Standard Constructor */
    public X_UNS_Handover_Inv (Properties ctx, int UNS_Handover_Inv_ID, String trxName)
    {
      super (ctx, UNS_Handover_Inv_ID, trxName);
      /** if (UNS_Handover_Inv_ID == 0)
        {
			setC_Invoice_ID (0);
			setInvoiceCollectionType (null);
			setIsApproved (false);
// N
			setIsReceipt (false);
// N
			setProcessed (false);
// N
			setSalesRep_ID (0);
			setUNS_Handover_Inv_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Handover_Inv (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Handover_Inv[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accepted By.
		@param AcceptedBy 
		Person had accepted invoice at customer side
	  */
	public void setAcceptedBy (String AcceptedBy)
	{
		set_Value (COLUMNNAME_AcceptedBy, AcceptedBy);
	}

	/** Get Accepted By.
		@return Person had accepted invoice at customer side
	  */
	public String getAcceptedBy () 
	{
		return (String)get_Value(COLUMNNAME_AcceptedBy);
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

	/** Handover Finance (Not Paid) = HN */
	public static final String INVOICECOLLECTIONTYPE_HandoverFinanceNotPaid = "HN";
	/** Handover Customer = HO */
	public static final String INVOICECOLLECTIONTYPE_HandoverCustomer = "HO";
	/** Handover Finance (Paid) = HP */
	public static final String INVOICECOLLECTIONTYPE_HandoverFinancePaid = "HP";
	/** Lost = LS */
	public static final String INVOICECOLLECTIONTYPE_Lost = "LS";
	/** Set Collection Status.
		@param InvoiceCollectionType 
		Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType)
	{

		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	/** Get Collection Status.
		@return Invoice Collection Status
	  */
	public String getInvoiceCollectionType () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceCollectionType);
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

	/** Set Receipt.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Receipt.
		@return This is a sales transaction (receipt)
	  */
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public com.unicore.model.I_UNS_BillingLine_Result getUNS_BillingLine_Result() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BillingLine_Result)MTable.get(getCtx(), com.unicore.model.I_UNS_BillingLine_Result.Table_Name)
			.getPO(getUNS_BillingLine_Result_ID(), get_TrxName());	}

	/** Set Invoice Result.
		@param UNS_BillingLine_Result_ID Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID)
	{
		if (UNS_BillingLine_Result_ID < 1) 
			set_Value (COLUMNNAME_UNS_BillingLine_Result_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BillingLine_Result_ID, Integer.valueOf(UNS_BillingLine_Result_ID));
	}

	/** Get Invoice Result.
		@return Invoice Result	  */
	public int getUNS_BillingLine_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BillingLine_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handover Invoice.
		@param UNS_Handover_Inv_ID Handover Invoice	  */
	public void setUNS_Handover_Inv_ID (int UNS_Handover_Inv_ID)
	{
		if (UNS_Handover_Inv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Handover_Inv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Handover_Inv_ID, Integer.valueOf(UNS_Handover_Inv_ID));
	}

	/** Get Handover Invoice.
		@return Handover Invoice	  */
	public int getUNS_Handover_Inv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Handover_Inv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Handover_Inv_UU.
		@param UNS_Handover_Inv_UU UNS_Handover_Inv_UU	  */
	public void setUNS_Handover_Inv_UU (String UNS_Handover_Inv_UU)
	{
		set_Value (COLUMNNAME_UNS_Handover_Inv_UU, UNS_Handover_Inv_UU);
	}

	/** Get UNS_Handover_Inv_UU.
		@return UNS_Handover_Inv_UU	  */
	public String getUNS_Handover_Inv_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Handover_Inv_UU);
	}

	public com.unicore.model.I_UNS_PR_Allocation getUNS_PR_Allocation() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PR_Allocation)MTable.get(getCtx(), com.unicore.model.I_UNS_PR_Allocation.Table_Name)
			.getPO(getUNS_PR_Allocation_ID(), get_TrxName());	}

	/** Set Billing Payment Allocation.
		@param UNS_PR_Allocation_ID Billing Payment Allocation	  */
	public void setUNS_PR_Allocation_ID (int UNS_PR_Allocation_ID)
	{
		if (UNS_PR_Allocation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PR_Allocation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PR_Allocation_ID, Integer.valueOf(UNS_PR_Allocation_ID));
	}

	/** Get Billing Payment Allocation.
		@return Billing Payment Allocation	  */
	public int getUNS_PR_Allocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PR_Allocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}