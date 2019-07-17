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

/** Generated Model for UNS_SER
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SER extends PO implements I_UNS_SER, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130118L;

    /** Standard Constructor */
    public X_UNS_SER (Properties ctx, int UNS_SER_ID, String trxName)
    {
      super (ctx, UNS_SER_ID, trxName);
      /** if (UNS_SER_ID == 0)
        {
			setC_BPartnerSR_ID (0);
			setC_Period_ID (0);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setEnquiryDate (new Timestamp( System.currentTimeMillis() ));
			setIsApproved (false);
// N
			setIsAvailable (true);
// Y
			setIsSOTrx (false);
			setM_PriceList_ID (0);
			setProcessed (false);
// N
			setShippingPeriod_ID (0);
			setUNS_SER_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SER (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SER[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartnerSR() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerSR_ID(), get_TrxName());	}

	/** Set Customer (agent).
		@param C_BPartnerSR_ID 
		Business Partner (Agent or Sales Rep)
	  */
	public void setC_BPartnerSR_ID (int C_BPartnerSR_ID)
	{
		if (C_BPartnerSR_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, Integer.valueOf(C_BPartnerSR_ID));
	}

	/** Get Customer (agent).
		@return Business Partner (Agent or Sales Rep)
	  */
	public int getC_BPartnerSR_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerSR_ID);
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

	/** Set Approval Date.
		@param DateApproval Approval Date	  */
	public void setDateApproval (Timestamp DateApproval)
	{
		set_Value (COLUMNNAME_DateApproval, DateApproval);
	}

	/** Get Approval Date.
		@return Approval Date	  */
	public Timestamp getDateApproval () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateApproval);
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

	/** Set Enquiry Date.
		@param EnquiryDate 
		Sales Enquiry Date
	  */
	public void setEnquiryDate (Timestamp EnquiryDate)
	{
		set_Value (COLUMNNAME_EnquiryDate, EnquiryDate);
	}

	/** Get Enquiry Date.
		@return Sales Enquiry Date
	  */
	public Timestamp getEnquiryDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EnquiryDate);
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

	/** Set Available.
		@param IsAvailable 
		Resource is available
	  */
	public void setIsAvailable (boolean IsAvailable)
	{
		set_Value (COLUMNNAME_IsAvailable, Boolean.valueOf(IsAvailable));
	}

	/** Get Available.
		@return Resource is available
	  */
	public boolean isAvailable () 
	{
		Object oo = get_Value(COLUMNNAME_IsAvailable);
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

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Reference Sales Enquiry.
		@param Ref_UNS_SER_ID Reference Sales Enquiry	  */
	public void setRef_UNS_SER_ID (int Ref_UNS_SER_ID)
	{
		if (Ref_UNS_SER_ID < 1) 
			set_Value (COLUMNNAME_Ref_UNS_SER_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_UNS_SER_ID, Integer.valueOf(Ref_UNS_SER_ID));
	}

	/** Get Reference Sales Enquiry.
		@return Reference Sales Enquiry	  */
	public int getRef_UNS_SER_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_UNS_SER_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reversal ID.
		@param Reversal_ID 
		ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Reversal ID.
		@return ID of document reversal
	  */
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Total.
		@param SalesTotal Sales Total	  */
	public void setSalesTotal (BigDecimal SalesTotal)
	{
		set_Value (COLUMNNAME_SalesTotal, SalesTotal);
	}

	/** Get Sales Total.
		@return Sales Total	  */
	public BigDecimal getSalesTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalesTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Period getShippingPeriod() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getShippingPeriod_ID(), get_TrxName());	}

	/** Set Shipping Period.
		@param ShippingPeriod_ID Shipping Period	  */
	public void setShippingPeriod_ID (int ShippingPeriod_ID)
	{
		if (ShippingPeriod_ID < 1) 
			set_Value (COLUMNNAME_ShippingPeriod_ID, null);
		else 
			set_Value (COLUMNNAME_ShippingPeriod_ID, Integer.valueOf(ShippingPeriod_ID));
	}

	/** Get Shipping Period.
		@return Shipping Period	  */
	public int getShippingPeriod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShippingPeriod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Enquiry.
		@param UNS_SER_ID Sales Enquiry	  */
	public void setUNS_SER_ID (int UNS_SER_ID)
	{
		if (UNS_SER_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SER_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SER_ID, Integer.valueOf(UNS_SER_ID));
	}

	/** Get Sales Enquiry.
		@return Sales Enquiry	  */
	public int getUNS_SER_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SER_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Enquiry UU.
		@param UNS_SER_UU Sales Enquiry UU	  */
	public void setUNS_SER_UU (String UNS_SER_UU)
	{
		set_Value (COLUMNNAME_UNS_SER_UU, UNS_SER_UU);
	}

	/** Get Sales Enquiry UU.
		@return Sales Enquiry UU	  */
	public String getUNS_SER_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SER_UU);
	}
}