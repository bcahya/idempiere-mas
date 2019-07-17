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

/** Generated Model for UNS_ContainerManagement
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ContainerManagement extends PO implements I_UNS_ContainerManagement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_ContainerManagement (Properties ctx, int UNS_ContainerManagement_ID, String trxName)
    {
      super (ctx, UNS_ContainerManagement_ID, trxName);
      /** if (UNS_ContainerManagement_ID == 0)
        {
			setContainerNo (null);
			setContainerType (null);
// GP
			setContainerVolume (null);
// TEU
			setCurrentStatus (null);
// NRC
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
			setShippingLine_ID (0);
			setUNS_ContainerManagement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ContainerManagement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ContainerManagement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getArrivedBy() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getArrivedBy_ID(), get_TrxName());	}

	/** Set Arrived By.
		@param ArrivedBy_ID Arrived By	  */
	public void setArrivedBy_ID (int ArrivedBy_ID)
	{
		if (ArrivedBy_ID < 1) 
			set_Value (COLUMNNAME_ArrivedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ArrivedBy_ID, Integer.valueOf(ArrivedBy_ID));
	}

	/** Get Arrived By.
		@return Arrived By	  */
	public int getArrivedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ArrivedBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container No.
		@param ContainerNo Container No	  */
	public void setContainerNo (String ContainerNo)
	{
		set_Value (COLUMNNAME_ContainerNo, ContainerNo);
	}

	/** Get Container No.
		@return Container No	  */
	public String getContainerNo () 
	{
		return (String)get_Value(COLUMNNAME_ContainerNo);
	}

	/** ContainerType AD_Reference_ID=1000040 */
	public static final int CONTAINERTYPE_AD_Reference_ID=1000040;
	/** General Purpose = GP */
	public static final String CONTAINERTYPE_GeneralPurpose = "GP";
	/** High Cube = HC */
	public static final String CONTAINERTYPE_HighCube = "HC";
	/** High Cube Refrigerator = HCRF */
	public static final String CONTAINERTYPE_HighCubeRefrigerator = "HCRF";
	/** Set Web Container Type.
		@param ContainerType 
		Web Container Type
	  */
	public void setContainerType (String ContainerType)
	{

		set_Value (COLUMNNAME_ContainerType, ContainerType);
	}

	/** Get Web Container Type.
		@return Web Container Type
	  */
	public String getContainerType () 
	{
		return (String)get_Value(COLUMNNAME_ContainerType);
	}

	/** ContainerVolume AD_Reference_ID=1000041 */
	public static final int CONTAINERVOLUME_AD_Reference_ID=1000041;
	/** TEU = TEU */
	public static final String CONTAINERVOLUME_TEU = "TEU";
	/** FEU = FEU */
	public static final String CONTAINERVOLUME_FEU = "FEU";
	/** FVEU = FVEU */
	public static final String CONTAINERVOLUME_FVEU = "FVEU";
	/** Set Container Volume.
		@param ContainerVolume 
		Container Volume (20', 40', 45')
	  */
	public void setContainerVolume (String ContainerVolume)
	{

		set_Value (COLUMNNAME_ContainerVolume, ContainerVolume);
	}

	/** Get Container Volume.
		@return Container Volume (20', 40', 45')
	  */
	public String getContainerVolume () 
	{
		return (String)get_Value(COLUMNNAME_ContainerVolume);
	}

	/** CurrentStatus AD_Reference_ID=1000027 */
	public static final int CURRENTSTATUS_AD_Reference_ID=1000027;
	/** On The Way = OTW */
	public static final String CURRENTSTATUS_OnTheWay = "OTW";
	/** Arrived + Waiting For Shipment = AWS */
	public static final String CURRENTSTATUS_ArrivedPlusWaitingForShipment = "AWS";
	/** Shipped To Customer = STC */
	public static final String CURRENTSTATUS_ShippedToCustomer = "STC";
	/** Returned to Supplier = RTV */
	public static final String CURRENTSTATUS_ReturnedToSupplier = "RTV";
	/** (Newly Registered Container) = NRC */
	public static final String CURRENTSTATUS_NewlyRegisteredContainer = "NRC";
	/** Set Current Status.
		@param CurrentStatus Current Status	  */
	public void setCurrentStatus (String CurrentStatus)
	{

		set_Value (COLUMNNAME_CurrentStatus, CurrentStatus);
	}

	/** Get Current Status.
		@return Current Status	  */
	public String getCurrentStatus () 
	{
		return (String)get_Value(COLUMNNAME_CurrentStatus);
	}

	public org.compiere.model.I_AD_User getDepartedBy() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getDepartedBy_ID(), get_TrxName());	}

	/** Set Departed By.
		@param DepartedBy_ID Departed By	  */
	public void setDepartedBy_ID (int DepartedBy_ID)
	{
		if (DepartedBy_ID < 1) 
			set_Value (COLUMNNAME_DepartedBy_ID, null);
		else 
			set_Value (COLUMNNAME_DepartedBy_ID, Integer.valueOf(DepartedBy_ID));
	}

	/** Get Departed By.
		@return Departed By	  */
	public int getDepartedBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DepartedBy_ID);
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

	/** Set Last Arrival Date.
		@param LastArrivalDate Last Arrival Date	  */
	public void setLastArrivalDate (Timestamp LastArrivalDate)
	{
		set_Value (COLUMNNAME_LastArrivalDate, LastArrivalDate);
	}

	/** Get Last Arrival Date.
		@return Last Arrival Date	  */
	public Timestamp getLastArrivalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastArrivalDate);
	}

	/** Set Last Departure Date.
		@param LastDepartureDate Last Departure Date	  */
	public void setLastDepartureDate (Timestamp LastDepartureDate)
	{
		set_Value (COLUMNNAME_LastDepartureDate, LastDepartureDate);
	}

	/** Get Last Departure Date.
		@return Last Departure Date	  */
	public Timestamp getLastDepartureDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastDepartureDate);
	}

	/** Set Last QA Date.
		@param LastQADate Last QA Date	  */
	public void setLastQADate (Timestamp LastQADate)
	{
		set_Value (COLUMNNAME_LastQADate, LastQADate);
	}

	/** Get Last QA Date.
		@return Last QA Date	  */
	public Timestamp getLastQADate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastQADate);
	}

	/** LastQAStatus AD_Reference_ID=1000128 */
	public static final int LASTQASTATUS_AD_Reference_ID=1000128;
	/** On Hold = OH */
	public static final String LASTQASTATUS_OnHold = "OH";
	/** Release = RE */
	public static final String LASTQASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String LASTQASTATUS_PrematureReleased = "PR";
	/** Pending Inspection/Lab Test = PL */
	public static final String LASTQASTATUS_PendingInspectionLabTest = "PL";
	/** Reject = RJ */
	public static final String LASTQASTATUS_Reject = "RJ";
	/** Reprocess = RP */
	public static final String LASTQASTATUS_Reprocess = "RP";
	/** Blending = BL */
	public static final String LASTQASTATUS_Blending = "BL";
	/** Resorting = RS */
	public static final String LASTQASTATUS_Resorting = "RS";
	/** Regrade = RG */
	public static final String LASTQASTATUS_Regrade = "RG";
	/** Repacking = RA */
	public static final String LASTQASTATUS_Repacking = "RA";
	/** Non Conformance = NC */
	public static final String LASTQASTATUS_NonConformance = "NC";
	/** Incubation = IC */
	public static final String LASTQASTATUS_Incubation = "IC";
	/** Set Last QA Status.
		@param LastQAStatus Last QA Status	  */
	public void setLastQAStatus (String LastQAStatus)
	{

		set_Value (COLUMNNAME_LastQAStatus, LastQAStatus);
	}

	/** Get Last QA Status.
		@return Last QA Status	  */
	public String getLastQAStatus () 
	{
		return (String)get_Value(COLUMNNAME_LastQAStatus);
	}

	/** Set Max Standby Time (Day).
		@param Max_StandbyTime Max Standby Time (Day)	  */
	public void setMax_StandbyTime (BigDecimal Max_StandbyTime)
	{
		set_Value (COLUMNNAME_Max_StandbyTime, Max_StandbyTime);
	}

	/** Get Max Standby Time (Day).
		@return Max Standby Time (Day)	  */
	public BigDecimal getMax_StandbyTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Max_StandbyTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Container Management.
		@param UNS_ContainerManagement_ID Container Management	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID)
	{
		if (UNS_ContainerManagement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerManagement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerManagement_ID, Integer.valueOf(UNS_ContainerManagement_ID));
	}

	/** Get Container Management.
		@return Container Management	  */
	public int getUNS_ContainerManagement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerManagement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Management UU.
		@param UNS_ContainerManagement_UU Container Management UU	  */
	public void setUNS_ContainerManagement_UU (String UNS_ContainerManagement_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ContainerManagement_UU, UNS_ContainerManagement_UU);
	}

	/** Get Container Management UU.
		@return Container Management UU	  */
	public String getUNS_ContainerManagement_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ContainerManagement_UU);
	}
}