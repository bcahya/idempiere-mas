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

/** Generated Model for UNS_ShipmentSchedule
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ShipmentSchedule extends PO implements I_UNS_ShipmentSchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140211L;

    /** Standard Constructor */
    public X_UNS_ShipmentSchedule (Properties ctx, int UNS_ShipmentSchedule_ID, String trxName)
    {
      super (ctx, UNS_ShipmentSchedule_ID, trxName);
      /** if (UNS_ShipmentSchedule_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_DocType_ID (0);
			setC_Location_ID (0);
			setCreateInvoice (false);
// N
			setDescription (null);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setDropShip_BPartner_ID (0);
// @C_BPartner_ID@
			setDropShip_Location_ID (0);
			setETA (new Timestamp( System.currentTimeMillis() ));
			setETD (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setInvoicingDept_ID (0);
// @AD_Org_ID@
			setIsApproved (false);
// N
			setM_Warehouse_ID (0);
// 1000051
			setName (null);
			setProcessed (false);
// N
			setUNS_Kapal_ID (0);
			setUNS_ShipmentSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ShipmentSchedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ShipmentSchedule[")
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_BPartner_ID()));
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

	/** Set Change Container.
		@param ChangeType Change Container	  */
	public void setChangeType (String ChangeType)
	{
		set_Value (COLUMNNAME_ChangeType, ChangeType);
	}

	/** Get Change Container.
		@return Change Container	  */
	public String getChangeType () 
	{
		return (String)get_Value(COLUMNNAME_ChangeType);
	}

	/** Set Check Availability.
		@param CheckAvailability 
		Process to check things that must be available for document processing
	  */
	public void setCheckAvailability (String CheckAvailability)
	{
		set_Value (COLUMNNAME_CheckAvailability, CheckAvailability);
	}

	/** Get Check Availability.
		@return Process to check things that must be available for document processing
	  */
	public String getCheckAvailability () 
	{
		return (String)get_Value(COLUMNNAME_CheckAvailability);
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

	/** Set ConfirmationStatus.
		@param ConfirmationStatus 
		Confirmation Status
	  */
	public void setConfirmationStatus (String ConfirmationStatus)
	{
		set_ValueNoCheck (COLUMNNAME_ConfirmationStatus, ConfirmationStatus);
	}

	/** Get ConfirmationStatus.
		@return Confirmation Status
	  */
	public String getConfirmationStatus () 
	{
		return (String)get_Value(COLUMNNAME_ConfirmationStatus);
	}

	/** Set Create Invoice.
		@param CreateInvoice Create Invoice	  */
	public void setCreateInvoice (boolean CreateInvoice)
	{
		set_Value (COLUMNNAME_CreateInvoice, Boolean.valueOf(CreateInvoice));
	}

	/** Get Create Invoice.
		@return Create Invoice	  */
	public boolean isCreateInvoice () 
	{
		Object oo = get_Value(COLUMNNAME_CreateInvoice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getDropShip_BPartner_ID(), get_TrxName());	}

	/** Set Drop Shipment Partner.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Drop Shipment Partner.
		@return Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getDropShip_Location_ID(), get_TrxName());	}

	/** Set Drop Shipment Location.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Drop Shipment Location.
		@return Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ETA.
		@param ETA 
		Estimation Time Arived
	  */
	public void setETA (Timestamp ETA)
	{
		set_Value (COLUMNNAME_ETA, ETA);
	}

	/** Get ETA.
		@return Estimation Time Arived
	  */
	public Timestamp getETA () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ETA);
	}

	/** Set ETD.
		@param ETD 
		Estimation Time Departure
	  */
	public void setETD (Timestamp ETD)
	{
		set_Value (COLUMNNAME_ETD, ETD);
	}

	/** Get ETD.
		@return Estimation Time Departure
	  */
	public Timestamp getETD () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ETD);
	}

	/** Set Invoice Mgt Department.
		@param InvoicingDept_ID Invoice Mgt Department	  */
	public void setInvoicingDept_ID (int InvoicingDept_ID)
	{
		if (InvoicingDept_ID < 1) 
			set_Value (COLUMNNAME_InvoicingDept_ID, null);
		else 
			set_Value (COLUMNNAME_InvoicingDept_ID, Integer.valueOf(InvoicingDept_ID));
	}

	/** Get Invoice Mgt Department.
		@return Invoice Mgt Department	  */
	public int getInvoicingDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoicingDept_ID);
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

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_ValueNoCheck (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Set Ship/Receive Confirm Department.
		@param ShipReceiveConfirmDept_ID 
		Ship/Receive Confirm Department
	  */
	public void setShipReceiveConfirmDept_ID (int ShipReceiveConfirmDept_ID)
	{
		if (ShipReceiveConfirmDept_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ShipReceiveConfirmDept_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ShipReceiveConfirmDept_ID, Integer.valueOf(ShipReceiveConfirmDept_ID));
	}

	/** Get Ship/Receive Confirm Department.
		@return Ship/Receive Confirm Department
	  */
	public int getShipReceiveConfirmDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShipReceiveConfirmDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Billing Reff.
		@param UNS_Billing_ID Billing Reff	  */
	public void setUNS_Billing_ID (int UNS_Billing_ID)
	{
		if (UNS_Billing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Billing_ID, Integer.valueOf(UNS_Billing_ID));
	}

	/** Get Billing Reff.
		@return Billing Reff	  */
	public int getUNS_Billing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Billing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Barge.
		@param UNS_Kapal_ID Barge	  */
	public void setUNS_Kapal_ID (int UNS_Kapal_ID)
	{
		if (UNS_Kapal_ID < 1) 
			set_Value (COLUMNNAME_UNS_Kapal_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Kapal_ID, Integer.valueOf(UNS_Kapal_ID));
	}

	/** Get Barge.
		@return Barge	  */
	public int getUNS_Kapal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Kapal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Inspection.
		@param UNS_QAContainerInspection_ID Container Inspection	  */
	public void setUNS_QAContainerInspection_ID (int UNS_QAContainerInspection_ID)
	{
		if (UNS_QAContainerInspection_ID < 1) 
			set_Value (COLUMNNAME_UNS_QAContainerInspection_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_QAContainerInspection_ID, Integer.valueOf(UNS_QAContainerInspection_ID));
	}

	/** Get Container Inspection.
		@return Container Inspection	  */
	public int getUNS_QAContainerInspection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAContainerInspection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipment Arragement.
		@param UNS_ShipmentSchedule_ID Shipment Arragement	  */
	public void setUNS_ShipmentSchedule_ID (int UNS_ShipmentSchedule_ID)
	{
		if (UNS_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShipmentSchedule_ID, Integer.valueOf(UNS_ShipmentSchedule_ID));
	}

	/** Get Shipment Arragement.
		@return Shipment Arragement	  */
	public int getUNS_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ShipmentSchedule_UU.
		@param UNS_ShipmentSchedule_UU UNS_ShipmentSchedule_UU	  */
	public void setUNS_ShipmentSchedule_UU (String UNS_ShipmentSchedule_UU)
	{
		set_Value (COLUMNNAME_UNS_ShipmentSchedule_UU, UNS_ShipmentSchedule_UU);
	}

	/** Get UNS_ShipmentSchedule_UU.
		@return UNS_ShipmentSchedule_UU	  */
	public String getUNS_ShipmentSchedule_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ShipmentSchedule_UU);
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