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

/** Generated Model for UNS_PackingSlip
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PackingSlip extends PO implements I_UNS_PackingSlip, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140131L;

    /** Standard Constructor */
    public X_UNS_PackingSlip (Properties ctx, int UNS_PackingSlip_ID, String trxName)
    {
      super (ctx, UNS_PackingSlip_ID, trxName);
      /** if (UNS_PackingSlip_ID == 0)
        {
			setArriveDate (new Timestamp( System.currentTimeMillis() ));
			setDeptToInvoice_ID (0);
// @AD_Org_ID@
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setM_Warehouse_ID (0);
			setProcessed (false);
// N
			setShipDate (new Timestamp( System.currentTimeMillis() ));
			setShipReceiveDept_ID (0);
// 0
			setUNS_Kapal_ID (0);
			setUNS_PackingSlip_ID (0);
			setVoyage (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_PackingSlip (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PackingSlip[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Arrival Date.
		@param ArriveDate Arrival Date	  */
	public void setArriveDate (Timestamp ArriveDate)
	{
		set_Value (COLUMNNAME_ArriveDate, ArriveDate);
	}

	/** Get Arrival Date.
		@return Arrival Date	  */
	public Timestamp getArriveDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ArriveDate);
	}

	/** Set BC Document Date.
		@param BCDocDate BC Document Date	  */
	public void setBCDocDate (Timestamp BCDocDate)
	{
		set_Value (COLUMNNAME_BCDocDate, BCDocDate);
	}

	/** Get BC Document Date.
		@return BC Document Date	  */
	public Timestamp getBCDocDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BCDocDate);
	}

	/** Set Create Lines.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create Lines.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Dept. To Invoice.
		@param DeptToInvoice_ID 
		Departemen to handle and assigned to the invoice
	  */
	public void setDeptToInvoice_ID (int DeptToInvoice_ID)
	{
		if (DeptToInvoice_ID < 1) 
			set_Value (COLUMNNAME_DeptToInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_DeptToInvoice_ID, Integer.valueOf(DeptToInvoice_ID));
	}

	/** Get Dept. To Invoice.
		@return Departemen to handle and assigned to the invoice
	  */
	public int getDeptToInvoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeptToInvoice_ID);
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

	/** Set Generate Receipt.
		@param GenerateTo 
		Generate Receipt
	  */
	public void setGenerateTo (String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	/** Get Generate Receipt.
		@return Generate Receipt
	  */
	public String getGenerateTo () 
	{
		return (String)get_Value(COLUMNNAME_GenerateTo);
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set SO/PO Type.
		@param SOPOType 
		Sales Tax applies to sales situations, Purchase Tax to purchase situations
	  */
	public void setSOPOType (String SOPOType)
	{
		set_Value (COLUMNNAME_SOPOType, SOPOType);
	}

	/** Get SO/PO Type.
		@return Sales Tax applies to sales situations, Purchase Tax to purchase situations
	  */
	public String getSOPOType () 
	{
		return (String)get_Value(COLUMNNAME_SOPOType);
	}

	/** Set Departure Date.
		@param ShipDate 
		Shipment Date/Time
	  */
	public void setShipDate (Timestamp ShipDate)
	{
		set_Value (COLUMNNAME_ShipDate, ShipDate);
	}

	/** Get Departure Date.
		@return Shipment Date/Time
	  */
	public Timestamp getShipDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ShipDate);
	}

	/** Set Ship/Receive Department.
		@param ShipReceiveDept_ID 
		Ship/Receive Department
	  */
	public void setShipReceiveDept_ID (int ShipReceiveDept_ID)
	{
		if (ShipReceiveDept_ID < 1) 
			set_Value (COLUMNNAME_ShipReceiveDept_ID, null);
		else 
			set_Value (COLUMNNAME_ShipReceiveDept_ID, Integer.valueOf(ShipReceiveDept_ID));
	}

	/** Get Ship/Receive Department.
		@return Ship/Receive Department
	  */
	public int getShipReceiveDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShipReceiveDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Terms.
		@param Terms Terms	  */
	public void setTerms (String Terms)
	{
		set_Value (COLUMNNAME_Terms, Terms);
	}

	/** Get Terms.
		@return Terms	  */
	public String getTerms () 
	{
		return (String)get_Value(COLUMNNAME_Terms);
	}

	public com.uns.model.I_UNS_BCCode getUNS_BCCode() throws RuntimeException
    {
		return (com.uns.model.I_UNS_BCCode)MTable.get(getCtx(), com.uns.model.I_UNS_BCCode.Table_Name)
			.getPO(getUNS_BCCode_ID(), get_TrxName());	}

	/** Set Bea Cukai Code.
		@param UNS_BCCode_ID Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID)
	{
		if (UNS_BCCode_ID < 1) 
			set_Value (COLUMNNAME_UNS_BCCode_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BCCode_ID, Integer.valueOf(UNS_BCCode_ID));
	}

	/** Get Bea Cukai Code.
		@return Bea Cukai Code	  */
	public int getUNS_BCCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kapal.
		@param UNS_Kapal_ID Kapal	  */
	public void setUNS_Kapal_ID (int UNS_Kapal_ID)
	{
		if (UNS_Kapal_ID < 1) 
			set_Value (COLUMNNAME_UNS_Kapal_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Kapal_ID, Integer.valueOf(UNS_Kapal_ID));
	}

	/** Get Kapal.
		@return Kapal	  */
	public int getUNS_Kapal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Kapal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packing Slip.
		@param UNS_PackingSlip_ID Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID)
	{
		if (UNS_PackingSlip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, Integer.valueOf(UNS_PackingSlip_ID));
	}

	/** Get Packing Slip.
		@return Packing Slip	  */
	public int getUNS_PackingSlip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PackingSlip UU.
		@param UNS_PackingSlip_UU PackingSlip UU	  */
	public void setUNS_PackingSlip_UU (String UNS_PackingSlip_UU)
	{
		set_Value (COLUMNNAME_UNS_PackingSlip_UU, UNS_PackingSlip_UU);
	}

	/** Get PackingSlip UU.
		@return PackingSlip UU	  */
	public String getUNS_PackingSlip_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingSlip_UU);
	}

	/** Set Voyage.
		@param Voyage Voyage	  */
	public void setVoyage (String Voyage)
	{
		set_Value (COLUMNNAME_Voyage, Voyage);
	}

	/** Get Voyage.
		@return Voyage	  */
	public String getVoyage () 
	{
		return (String)get_Value(COLUMNNAME_Voyage);
	}
}