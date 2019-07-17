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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_ContainerArrival
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ContainerArrival extends PO implements I_UNS_ContainerArrival, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_ContainerArrival (Properties ctx, int UNS_ContainerArrival_ID, String trxName)
    {
      super (ctx, UNS_ContainerArrival_ID, trxName);
      /** if (UNS_ContainerArrival_ID == 0)
        {
			setArriveDate (new Timestamp( System.currentTimeMillis() ));
			setBLRef (null);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setETA (new Timestamp( System.currentTimeMillis() ));
			setETD_Factory (new Timestamp( System.currentTimeMillis() ));
			setIsApproved (false);
// N
			setProcessed (false);
// N
			setQAStatus (null);
// PI
			setSealNo (null);
			setShipped_ID (0);
			setShippingLine_ID (0);
			setUNS_ContainerArrival_ID (0);
			setUNS_ContainerManagement_ID (0);
			setUNS_PackingSlip_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ContainerArrival (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ContainerArrival[")
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

	/** Set BL Ref.
		@param BLRef BL Ref	  */
	public void setBLRef (String BLRef)
	{
		set_Value (COLUMNNAME_BLRef, BLRef);
	}

	/** Get BL Ref.
		@return BL Ref	  */
	public String getBLRef () 
	{
		return (String)get_Value(COLUMNNAME_BLRef);
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

	/** Set ETD Factory.
		@param ETD_Factory ETD Factory	  */
	public void setETD_Factory (Timestamp ETD_Factory)
	{
		set_Value (COLUMNNAME_ETD_Factory, ETD_Factory);
	}

	/** Get ETD Factory.
		@return ETD Factory	  */
	public Timestamp getETD_Factory () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ETD_Factory);
	}

	/** Set Generate List.
		@param GenerateList 
		Generate List
	  */
	public void setGenerateList (String GenerateList)
	{
		set_Value (COLUMNNAME_GenerateList, GenerateList);
	}

	/** Get Generate List.
		@return Generate List
	  */
	public String getGenerateList () 
	{
		return (String)get_Value(COLUMNNAME_GenerateList);
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

	public I_C_ValidCombination getProcesse() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getProcessedOn(), get_TrxName());	}

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (int ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, Integer.valueOf(ProcessedOn));
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public int getProcessedOn () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProcessedOn);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** QAStatus AD_Reference_ID=1000128 */
	public static final int QASTATUS_AD_Reference_ID=1000128;
	/** On Hold = OH */
	public static final String QASTATUS_OnHold = "OH";
	/** Release = RE */
	public static final String QASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String QASTATUS_PrematureReleased = "PR";
	/** Pending Inspection/Lab Test = PL */
	public static final String QASTATUS_PendingInspectionLabTest = "PL";
	/** Reject = RJ */
	public static final String QASTATUS_Reject = "RJ";
	/** Reprocess = RP */
	public static final String QASTATUS_Reprocess = "RP";
	/** Blending = BL */
	public static final String QASTATUS_Blending = "BL";
	/** Resorting = RS */
	public static final String QASTATUS_Resorting = "RS";
	/** Regrade = RG */
	public static final String QASTATUS_Regrade = "RG";
	/** Repacking = RA */
	public static final String QASTATUS_Repacking = "RA";
	/** Non Conformance = NC */
	public static final String QASTATUS_NonConformance = "NC";
	/** Incubation = IC */
	public static final String QASTATUS_Incubation = "IC";
	/** Set QA Status.
		@param QAStatus QA Status	  */
	public void setQAStatus (String QAStatus)
	{

		set_Value (COLUMNNAME_QAStatus, QAStatus);
	}

	/** Get QA Status.
		@return QA Status	  */
	public String getQAStatus () 
	{
		return (String)get_Value(COLUMNNAME_QAStatus);
	}

	/** Set Seal No.
		@param SealNo Seal No	  */
	public void setSealNo (String SealNo)
	{
		set_Value (COLUMNNAME_SealNo, SealNo);
	}

	/** Get Seal No.
		@return Seal No	  */
	public String getSealNo () 
	{
		return (String)get_Value(COLUMNNAME_SealNo);
	}

	public org.compiere.model.I_AD_User getShipped() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getShipped_ID(), get_TrxName());	}

	/** Set Shipped By.
		@param Shipped_ID Shipped By	  */
	public void setShipped_ID (int Shipped_ID)
	{
		if (Shipped_ID < 1) 
			set_Value (COLUMNNAME_Shipped_ID, null);
		else 
			set_Value (COLUMNNAME_Shipped_ID, Integer.valueOf(Shipped_ID));
	}

	/** Get Shipped By.
		@return Shipped By	  */
	public int getShipped_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipped_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Shipper getShippingLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_Shipper)MTable.get(getCtx(), org.compiere.model.I_M_Shipper.Table_Name)
			.getPO(getShippingLine_ID(), get_TrxName());	}

	/** Set Shipping Line.
		@param ShippingLine_ID Shipping Line	  */
	public void setShippingLine_ID (int ShippingLine_ID)
	{
		if (ShippingLine_ID < 1) 
			set_Value (COLUMNNAME_ShippingLine_ID, null);
		else 
			set_Value (COLUMNNAME_ShippingLine_ID, Integer.valueOf(ShippingLine_ID));
	}

	/** Get Shipping Line.
		@return Shipping Line	  */
	public int getShippingLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShippingLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Arrival.
		@param UNS_ContainerArrival_ID Container Arrival	  */
	public void setUNS_ContainerArrival_ID (int UNS_ContainerArrival_ID)
	{
		if (UNS_ContainerArrival_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrival_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrival_ID, Integer.valueOf(UNS_ContainerArrival_ID));
	}

	/** Get Container Arrival.
		@return Container Arrival	  */
	public int getUNS_ContainerArrival_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerArrival_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Arrival UU.
		@param UNS_ContainerArrival_UU Container Arrival UU	  */
	public void setUNS_ContainerArrival_UU (String UNS_ContainerArrival_UU)
	{
		set_Value (COLUMNNAME_UNS_ContainerArrival_UU, UNS_ContainerArrival_UU);
	}

	/** Get Container Arrival UU.
		@return Container Arrival UU	  */
	public String getUNS_ContainerArrival_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ContainerArrival_UU);
	}

	/** Set Container No.
		@param UNS_ContainerManagement_ID Container No	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID)
	{
		if (UNS_ContainerManagement_ID < 1) 
			set_Value (COLUMNNAME_UNS_ContainerManagement_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ContainerManagement_ID, Integer.valueOf(UNS_ContainerManagement_ID));
	}

	/** Get Container No.
		@return Container No	  */
	public int getUNS_ContainerManagement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerManagement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packing Slip.
		@param UNS_PackingSlip_ID Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID)
	{
		if (UNS_PackingSlip_ID < 1) 
			set_Value (COLUMNNAME_UNS_PackingSlip_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PackingSlip_ID, Integer.valueOf(UNS_PackingSlip_ID));
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
}