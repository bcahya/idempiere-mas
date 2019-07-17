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

/** Generated Model for UNS_PackingList
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PackingList extends PO implements I_UNS_PackingList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170828L;

    /** Standard Constructor */
    public X_UNS_PackingList (Properties ctx, int UNS_PackingList_ID, String trxName)
    {
      super (ctx, UNS_PackingList_ID, trxName);
      /** if (UNS_PackingList_ID == 0)
        {
			setConsolidateConfirmation (true);
// Y
			setConsolidateDocument (true);
// Y
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setIsCashOrder (false);
// N
			setIsPickup (false);
// N
			setIsSOTrx (true);
// Y
			setName (null);
			setProcessed (false);
// N
			setUNS_PackingList_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PackingList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PackingList[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Consolidate Confirmation.
		@param ConsolidateConfirmation Consolidate Confirmation	  */
	public void setConsolidateConfirmation (boolean ConsolidateConfirmation)
	{
		set_Value (COLUMNNAME_ConsolidateConfirmation, Boolean.valueOf(ConsolidateConfirmation));
	}

	/** Get Consolidate Confirmation.
		@return Consolidate Confirmation	  */
	public boolean isConsolidateConfirmation () 
	{
		Object oo = get_Value(COLUMNNAME_ConsolidateConfirmation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Create From Sales Rep.
		@param CreateFromSalesRep Create From Sales Rep	  */
	public void setCreateFromSalesRep (String CreateFromSalesRep)
	{
		set_Value (COLUMNNAME_CreateFromSalesRep, CreateFromSalesRep);
	}

	/** Get Create From Sales Rep.
		@return Create From Sales Rep	  */
	public String getCreateFromSalesRep () 
	{
		return (String)get_Value(COLUMNNAME_CreateFromSalesRep);
	}

	/** Set Create PL From Rayon.
		@param CreatePLFromRayon 
		Create Packing List based Rayon Customer
	  */
	public void setCreatePLFromRayon (String CreatePLFromRayon)
	{
		set_Value (COLUMNNAME_CreatePLFromRayon, CreatePLFromRayon);
	}

	/** Get Create PL From Rayon.
		@return Create Packing List based Rayon Customer
	  */
	public String getCreatePLFromRayon () 
	{
		return (String)get_Value(COLUMNNAME_CreatePLFromRayon);
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

	/** Set Cash Order.
		@param IsCashOrder Cash Order	  */
	public void setIsCashOrder (boolean IsCashOrder)
	{
		set_Value (COLUMNNAME_IsCashOrder, Boolean.valueOf(IsCashOrder));
	}

	/** Get Cash Order.
		@return Cash Order	  */
	public boolean isCashOrder () 
	{
		Object oo = get_Value(COLUMNNAME_IsCashOrder);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pickup.
		@param IsPickup Pickup	  */
	public void setIsPickup (boolean IsPickup)
	{
		set_Value (COLUMNNAME_IsPickup, Boolean.valueOf(IsPickup));
	}

	/** Get Pickup.
		@return Pickup	  */
	public boolean isPickup () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickup);
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

	/** Set Print Penjualan Per Faktur Per Sales.
		@param PPPFPS Print Penjualan Per Faktur Per Sales	  */
	public void setPPPFPS (String PPPFPS)
	{
		set_Value (COLUMNNAME_PPPFPS, PPPFPS);
	}

	/** Get Print Penjualan Per Faktur Per Sales.
		@return Print Penjualan Per Faktur Per Sales	  */
	public String getPPPFPS () 
	{
		return (String)get_Value(COLUMNNAME_PPPFPS);
	}

	/** Set Print Berita Acara Pengiriman.
		@param PrintBAPengiriman Print Berita Acara Pengiriman	  */
	public void setPrintBAPengiriman (String PrintBAPengiriman)
	{
		set_Value (COLUMNNAME_PrintBAPengiriman, PrintBAPengiriman);
	}

	/** Get Print Berita Acara Pengiriman.
		@return Print Berita Acara Pengiriman	  */
	public String getPrintBAPengiriman () 
	{
		return (String)get_Value(COLUMNNAME_PrintBAPengiriman);
	}

	/** Set Berita Acara Tanda Terima Faktur.
		@param printBATTF Berita Acara Tanda Terima Faktur	  */
	public void setprintBATTF (String printBATTF)
	{
		set_Value (COLUMNNAME_printBATTF, printBATTF);
	}

	/** Get Berita Acara Tanda Terima Faktur.
		@return Berita Acara Tanda Terima Faktur	  */
	public String getprintBATTF () 
	{
		return (String)get_Value(COLUMNNAME_printBATTF);
	}

	/** Set Print Berita Acara Tanda Terima Rekap Barang.
		@param PrintBATTRekapBarang Print Berita Acara Tanda Terima Rekap Barang	  */
	public void setPrintBATTRekapBarang (String PrintBATTRekapBarang)
	{
		set_Value (COLUMNNAME_PrintBATTRekapBarang, PrintBATTRekapBarang);
	}

	/** Get Print Berita Acara Tanda Terima Rekap Barang.
		@return Print Berita Acara Tanda Terima Rekap Barang	  */
	public String getPrintBATTRekapBarang () 
	{
		return (String)get_Value(COLUMNNAME_PrintBATTRekapBarang);
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

	/** Set Print Faktur Penjualan Per Packing List.
		@param PrintFPP Print Faktur Penjualan Per Packing List	  */
	public void setPrintFPP (String PrintFPP)
	{
		set_Value (COLUMNNAME_PrintFPP, PrintFPP);
	}

	/** Get Print Faktur Penjualan Per Packing List.
		@return Print Faktur Penjualan Per Packing List	  */
	public String getPrintFPP () 
	{
		return (String)get_Value(COLUMNNAME_PrintFPP);
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

	public com.unicore.model.I_UNS_PackingList getReference() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList.Table_Name)
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

	/** Set Re-Open Document.
		@param ReOpenDocument Re-Open Document	  */
	public void setReOpenDocument (String ReOpenDocument)
	{
		set_Value (COLUMNNAME_ReOpenDocument, ReOpenDocument);
	}

	/** Get Re-Open Document.
		@return Re-Open Document	  */
	public String getReOpenDocument () 
	{
		return (String)get_Value(COLUMNNAME_ReOpenDocument);
	}

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

	/** Set Packing List.
		@param UNS_PackingList_ID Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID)
	{
		if (UNS_PackingList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, Integer.valueOf(UNS_PackingList_ID));
	}

	/** Get Packing List.
		@return Packing List	  */
	public int getUNS_PackingList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PackingList_UU.
		@param UNS_PackingList_UU UNS_PackingList_UU	  */
	public void setUNS_PackingList_UU (String UNS_PackingList_UU)
	{
		set_Value (COLUMNNAME_UNS_PackingList_UU, UNS_PackingList_UU);
	}

	/** Get UNS_PackingList_UU.
		@return UNS_PackingList_UU	  */
	public String getUNS_PackingList_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingList_UU);
	}

	/** Set Void It.
		@param VoidIt Void It	  */
	public void setVoidIt (String VoidIt)
	{
		set_Value (COLUMNNAME_VoidIt, VoidIt);
	}

	/** Get Void It.
		@return Void It	  */
	public String getVoidIt () 
	{
		return (String)get_Value(COLUMNNAME_VoidIt);
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