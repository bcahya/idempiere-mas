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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Shipping
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Shipping extends PO implements I_UNS_Shipping, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171108L;

    /** Standard Constructor */
    public X_UNS_Shipping (Properties ctx, int UNS_Shipping_ID, String trxName)
    {
      super (ctx, UNS_Shipping_ID, trxName);
      /** if (UNS_Shipping_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setGrandTotal (Env.ZERO);
			setIsApproved (false);
// N
			setIsSOTrx (true);
// Y
			setLastOM (Env.ZERO);
			setName (null);
			setPaymentTerm (null);
			setPosted (false);
// N
			setPrevOM (Env.ZERO);
// 0
			setProcessed (false);
// N
			setShippingType (null);
			setStatus (null);
			setTonase (Env.ZERO);
			setTotalAmt (Env.ZERO);
			setUNS_Shipping_ID (0);
			setUseCity (true);
// Y
			setVolume (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_Shipping (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Shipping[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_Location)MTable.get(getCtx(), org.compiere.model.I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	public org.compiere.model.I_C_City getDestination() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getDestination_ID(), get_TrxName());	}

	/** Set Destination.
		@param Destination_ID Destination	  */
	public void setDestination_ID (int Destination_ID)
	{
		if (Destination_ID < 1) 
			set_Value (COLUMNNAME_Destination_ID, null);
		else 
			set_Value (COLUMNNAME_Destination_ID, Integer.valueOf(Destination_ID));
	}

	/** Get Destination.
		@return Destination	  */
	public int getDestination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Destination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set Driver.
		@param Driver Driver	  */
	public void setDriver (String Driver)
	{
		set_Value (COLUMNNAME_Driver, Driver);
	}

	/** Get Driver.
		@return Driver	  */
	public String getDriver () 
	{
		return (String)get_Value(COLUMNNAME_Driver);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Helper 1.
		@param Helper1 Helper 1	  */
	public void setHelper1 (String Helper1)
	{
		set_Value (COLUMNNAME_Helper1, Helper1);
	}

	/** Get Helper 1.
		@return Helper 1	  */
	public String getHelper1 () 
	{
		return (String)get_Value(COLUMNNAME_Helper1);
	}

	/** Set Helper 1.
		@param Helper1_ID Helper 1	  */
	public void setHelper1_ID (int Helper1_ID)
	{
		if (Helper1_ID < 1) 
			set_Value (COLUMNNAME_Helper1_ID, null);
		else 
			set_Value (COLUMNNAME_Helper1_ID, Integer.valueOf(Helper1_ID));
	}

	/** Get Helper 1.
		@return Helper 1	  */
	public int getHelper1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Helper1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Helper 2.
		@param Helper2 Helper 2	  */
	public void setHelper2 (String Helper2)
	{
		set_Value (COLUMNNAME_Helper2, Helper2);
	}

	/** Get Helper 2.
		@return Helper 2	  */
	public String getHelper2 () 
	{
		return (String)get_Value(COLUMNNAME_Helper2);
	}

	/** Set Helper 2.
		@param Helper2_ID Helper 2	  */
	public void setHelper2_ID (int Helper2_ID)
	{
		if (Helper2_ID < 1) 
			set_Value (COLUMNNAME_Helper2_ID, null);
		else 
			set_Value (COLUMNNAME_Helper2_ID, Integer.valueOf(Helper2_ID));
	}

	/** Get Helper 2.
		@return Helper 2	  */
	public int getHelper2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Helper2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Helper 3.
		@param Helper3 Helper 3	  */
	public void setHelper3 (String Helper3)
	{
		set_Value (COLUMNNAME_Helper3, Helper3);
	}

	/** Get Helper 3.
		@return Helper 3	  */
	public String getHelper3 () 
	{
		return (String)get_Value(COLUMNNAME_Helper3);
	}

	/** Set Helper 3.
		@param Helper3_ID Helper 3	  */
	public void setHelper3_ID (int Helper3_ID)
	{
		if (Helper3_ID < 1) 
			set_Value (COLUMNNAME_Helper3_ID, null);
		else 
			set_Value (COLUMNNAME_Helper3_ID, Integer.valueOf(Helper3_ID));
	}

	/** Get Helper 3.
		@return Helper 3	  */
	public int getHelper3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Helper3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Last Odometer.
		@param LastOM 
		Last Odometer
	  */
	public void setLastOM (BigDecimal LastOM)
	{
		set_Value (COLUMNNAME_LastOM, LastOM);
	}

	/** Get Last Odometer.
		@return Last Odometer
	  */
	public BigDecimal getLastOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public org.compiere.model.I_C_City getOrigin() throws RuntimeException
    {
		return (org.compiere.model.I_C_City)MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getOrigin_ID(), get_TrxName());	}

	/** Set Origin.
		@param Origin_ID Origin	  */
	public void setOrigin_ID (int Origin_ID)
	{
		if (Origin_ID < 1) 
			set_Value (COLUMNNAME_Origin_ID, null);
		else 
			set_Value (COLUMNNAME_Origin_ID, Integer.valueOf(Origin_ID));
	}

	/** Get Origin.
		@return Origin	  */
	public int getOrigin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Origin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Borongan (All In) = BO */
	public static final String PAYMENTTERM_BoronganAllIn = "BO";
	/** Detailed Cost = DC */
	public static final String PAYMENTTERM_DetailedCost = "DC";
	/** Set Payment Term.
		@param PaymentTerm 
		Payment Term
	  */
	public void setPaymentTerm (String PaymentTerm)
	{

		set_Value (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	/** Get Payment Term.
		@return Payment Term
	  */
	public String getPaymentTerm () 
	{
		return (String)get_Value(COLUMNNAME_PaymentTerm);
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_ValueNoCheck (COLUMNNAME_Posted, Boolean.valueOf(Posted));
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

	/** Set Previous Odometer.
		@param PrevOM Previous Odometer	  */
	public void setPrevOM (BigDecimal PrevOM)
	{
		set_Value (COLUMNNAME_PrevOM, PrevOM);
	}

	/** Get Previous Odometer.
		@return Previous Odometer	  */
	public BigDecimal getPrevOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Printing Cost.
		@param PrintCost Printing Cost	  */
	public void setPrintCost (String PrintCost)
	{
		set_Value (COLUMNNAME_PrintCost, PrintCost);
	}

	/** Get Printing Cost.
		@return Printing Cost	  */
	public String getPrintCost () 
	{
		return (String)get_Value(COLUMNNAME_PrintCost);
	}

	/** Set Print Delivery Order.
		@param PrintDeliveryOrder Print Delivery Order	  */
	public void setPrintDeliveryOrder (String PrintDeliveryOrder)
	{
		set_Value (COLUMNNAME_PrintDeliveryOrder, PrintDeliveryOrder);
	}

	/** Get Print Delivery Order.
		@return Print Delivery Order	  */
	public String getPrintDeliveryOrder () 
	{
		return (String)get_Value(COLUMNNAME_PrintDeliveryOrder);
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

	/** Set Print Fuel Voucher.
		@param PrintFuelVoucher Print Fuel Voucher	  */
	public void setPrintFuelVoucher (String PrintFuelVoucher)
	{
		set_Value (COLUMNNAME_PrintFuelVoucher, PrintFuelVoucher);
	}

	/** Get Print Fuel Voucher.
		@return Print Fuel Voucher	  */
	public String getPrintFuelVoucher () 
	{
		return (String)get_Value(COLUMNNAME_PrintFuelVoucher);
	}

	/** Set Print SPK.
		@param PrintSPK Print SPK	  */
	public void setPrintSPK (String PrintSPK)
	{
		set_Value (COLUMNNAME_PrintSPK, PrintSPK);
	}

	/** Get Print SPK.
		@return Print SPK	  */
	public String getPrintSPK () 
	{
		return (String)get_Value(COLUMNNAME_PrintSPK);
	}

	/** Set Print Voyage Voucher.
		@param PrintVoyageVoucher Print Voyage Voucher	  */
	public void setPrintVoyageVoucher (String PrintVoyageVoucher)
	{
		set_Value (COLUMNNAME_PrintVoyageVoucher, PrintVoyageVoucher);
	}

	/** Get Print Voyage Voucher.
		@return Print Voyage Voucher	  */
	public String getPrintVoyageVoucher () 
	{
		return (String)get_Value(COLUMNNAME_PrintVoyageVoucher);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	public com.unicore.model.I_UNS_Shipping getReversal() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Shipping)MTable.get(getCtx(), com.unicore.model.I_UNS_Shipping.Table_Name)
			.getPO(getReversal_ID(), get_TrxName());	}

	/** Set Reversal ID.
		@param Reversal_ID 
		ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Reversal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
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

	/** 1 = 1 */
	public static final String RITARMADA_1 = "1";
	/** 2 = 2 */
	public static final String RITARMADA_2 = "2";
	/** 3 = 3 */
	public static final String RITARMADA_3 = "3";
	/** 4 = 4 */
	public static final String RITARMADA_4 = "4";
	/** 5 = 5 */
	public static final String RITARMADA_5 = "5";
	/** 6 = 6 */
	public static final String RITARMADA_6 = "6";
	/** 7 = 7 */
	public static final String RITARMADA_7 = "7";
	/** 8 = 8 */
	public static final String RITARMADA_8 = "8";
	/** 9 = 9 */
	public static final String RITARMADA_9 = "9";
	/** Set Rit Armada.
		@param RitArmada Rit Armada	  */
	public void setRitArmada (String RitArmada)
	{

		set_Value (COLUMNNAME_RitArmada, RitArmada);
	}

	/** Get Rit Armada.
		@return Rit Armada	  */
	public String getRitArmada () 
	{
		return (String)get_Value(COLUMNNAME_RitArmada);
	}

	/** 1 = 1 */
	public static final String RITDRIVER_1 = "1";
	/** 2 = 2 */
	public static final String RITDRIVER_2 = "2";
	/** 3 = 3 */
	public static final String RITDRIVER_3 = "3";
	/** 4 = 4 */
	public static final String RITDRIVER_4 = "4";
	/** 5 = 5 */
	public static final String RITDRIVER_5 = "5";
	/** 6 = 6 */
	public static final String RITDRIVER_6 = "6";
	/** 7 = 7 */
	public static final String RITDRIVER_7 = "7";
	/** 8 = 8 */
	public static final String RITDRIVER_8 = "8";
	/** 9 = 9 */
	public static final String RITDRIVER_9 = "9";
	/** Set Driver Ritase.
		@param RitDriver Driver Ritase	  */
	public void setRitDriver (String RitDriver)
	{

		set_Value (COLUMNNAME_RitDriver, RitDriver);
	}

	/** Get Driver Ritase.
		@return Driver Ritase	  */
	public String getRitDriver () 
	{
		return (String)get_Value(COLUMNNAME_RitDriver);
	}

	/** 1 = 1 */
	public static final String RITHELPER1_1 = "1";
	/** 2 = 2 */
	public static final String RITHELPER1_2 = "2";
	/** 3 = 3 */
	public static final String RITHELPER1_3 = "3";
	/** 4 = 4 */
	public static final String RITHELPER1_4 = "4";
	/** 5 = 5 */
	public static final String RITHELPER1_5 = "5";
	/** 6 = 6 */
	public static final String RITHELPER1_6 = "6";
	/** 7 = 7 */
	public static final String RITHELPER1_7 = "7";
	/** 8 = 8 */
	public static final String RITHELPER1_8 = "8";
	/** 9 = 9 */
	public static final String RITHELPER1_9 = "9";
	/** Set Helper 1 Ritase.
		@param RitHelper1 Helper 1 Ritase	  */
	public void setRitHelper1 (String RitHelper1)
	{

		set_Value (COLUMNNAME_RitHelper1, RitHelper1);
	}

	/** Get Helper 1 Ritase.
		@return Helper 1 Ritase	  */
	public String getRitHelper1 () 
	{
		return (String)get_Value(COLUMNNAME_RitHelper1);
	}

	/** 1 = 1 */
	public static final String RITHELPER2_1 = "1";
	/** 2 = 2 */
	public static final String RITHELPER2_2 = "2";
	/** 3 = 3 */
	public static final String RITHELPER2_3 = "3";
	/** 4 = 4 */
	public static final String RITHELPER2_4 = "4";
	/** 5 = 5 */
	public static final String RITHELPER2_5 = "5";
	/** 6 = 6 */
	public static final String RITHELPER2_6 = "6";
	/** 7 = 7 */
	public static final String RITHELPER2_7 = "7";
	/** 8 = 8 */
	public static final String RITHELPER2_8 = "8";
	/** 9 = 9 */
	public static final String RITHELPER2_9 = "9";
	/** Set Rit Helper 2.
		@param RitHelper2 Rit Helper 2	  */
	public void setRitHelper2 (String RitHelper2)
	{

		set_Value (COLUMNNAME_RitHelper2, RitHelper2);
	}

	/** Get Rit Helper 2.
		@return Rit Helper 2	  */
	public String getRitHelper2 () 
	{
		return (String)get_Value(COLUMNNAME_RitHelper2);
	}

	/** 1 = 1 */
	public static final String RITHELPER3_1 = "1";
	/** 2 = 2 */
	public static final String RITHELPER3_2 = "2";
	/** 3 = 3 */
	public static final String RITHELPER3_3 = "3";
	/** 4 = 4 */
	public static final String RITHELPER3_4 = "4";
	/** 5 = 5 */
	public static final String RITHELPER3_5 = "5";
	/** 6 = 6 */
	public static final String RITHELPER3_6 = "6";
	/** 7 = 7 */
	public static final String RITHELPER3_7 = "7";
	/** 8 = 8 */
	public static final String RITHELPER3_8 = "8";
	/** 9 = 9 */
	public static final String RITHELPER3_9 = "9";
	/** Set Ritase Helper 3.
		@param RitHelper3 Ritase Helper 3	  */
	public void setRitHelper3 (String RitHelper3)
	{

		set_Value (COLUMNNAME_RitHelper3, RitHelper3);
	}

	/** Get Ritase Helper 3.
		@return Ritase Helper 3	  */
	public String getRitHelper3 () 
	{
		return (String)get_Value(COLUMNNAME_RitHelper3);
	}

	/** Distributor Internal = DI */
	public static final String SHIPPINGTYPE_DistributorInternal = "DI";
	/** Expedisi External = EE */
	public static final String SHIPPINGTYPE_ExpedisiExternal = "EE";
	/** Expedisi Internal = EI */
	public static final String SHIPPINGTYPE_ExpedisiInternal = "EI";
	/** Canvas = CV */
	public static final String SHIPPINGTYPE_Canvas = "CV";
	/** PickUp = PU */
	public static final String SHIPPINGTYPE_PickUp = "PU";
	/** Set Shipping Type.
		@param ShippingType Shipping Type	  */
	public void setShippingType (String ShippingType)
	{

		set_Value (COLUMNNAME_ShippingType, ShippingType);
	}

	/** Get Shipping Type.
		@return Shipping Type	  */
	public String getShippingType () 
	{
		return (String)get_Value(COLUMNNAME_ShippingType);
	}

	/** Delivery = DV */
	public static final String STATUS_Delivery = "DV";
	/** Return Shipping = RS */
	public static final String STATUS_ReturnShipping = "RS";
	/** Additional Shipping = AD */
	public static final String STATUS_AdditionalShipping = "AD";
	/** Additional Settlement = AS */
	public static final String STATUS_AdditionalSettlement = "AS";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Tonase.
		@param Tonase 
		Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase)
	{
		set_Value (COLUMNNAME_Tonase, Tonase);
	}

	/** Get Tonase.
		@return Indicate total tonase
	  */
	public BigDecimal getTonase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tonase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Armada.
		@param UNS_Armada_ID Armada	  */
	public void setUNS_Armada_ID (int UNS_Armada_ID)
	{
		if (UNS_Armada_ID < 1) 
			set_Value (COLUMNNAME_UNS_Armada_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Armada_ID, Integer.valueOf(UNS_Armada_ID));
	}

	/** Get Armada.
		@return Armada	  */
	public int getUNS_Armada_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Armada_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Shipping Document.
		@param UNS_Shipping_ID Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID)
	{
		if (UNS_Shipping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Shipping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Shipping_ID, Integer.valueOf(UNS_Shipping_ID));
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

	public com.unicore.model.I_UNS_Shipping getUNS_Shipping_Reff() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Shipping)MTable.get(getCtx(), com.unicore.model.I_UNS_Shipping.Table_Name)
			.getPO(getUNS_Shipping_Reff_ID(), get_TrxName());	}

	/** Set Shipping Reference.
		@param UNS_Shipping_Reff_ID Shipping Reference	  */
	public void setUNS_Shipping_Reff_ID (int UNS_Shipping_Reff_ID)
	{
		if (UNS_Shipping_Reff_ID < 1) 
			set_Value (COLUMNNAME_UNS_Shipping_Reff_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Shipping_Reff_ID, Integer.valueOf(UNS_Shipping_Reff_ID));
	}

	/** Get Shipping Reference.
		@return Shipping Reference	  */
	public int getUNS_Shipping_Reff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Shipping_Reff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Shipping_UU.
		@param UNS_Shipping_UU UNS_Shipping_UU	  */
	public void setUNS_Shipping_UU (String UNS_Shipping_UU)
	{
		set_Value (COLUMNNAME_UNS_Shipping_UU, UNS_Shipping_UU);
	}

	/** Get UNS_Shipping_UU.
		@return UNS_Shipping_UU	  */
	public String getUNS_Shipping_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Shipping_UU);
	}

	/** Set Use City.
		@param UseCity Use City	  */
	public void setUseCity (boolean UseCity)
	{
		set_Value (COLUMNNAME_UseCity, Boolean.valueOf(UseCity));
	}

	/** Get Use City.
		@return Use City	  */
	public boolean isUseCity () 
	{
		Object oo = get_Value(COLUMNNAME_UseCity);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_ValueNoCheck (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}