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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_ContainerDeparture
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ContainerDeparture extends PO implements I_UNS_ContainerDeparture, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_ContainerDeparture (Properties ctx, int UNS_ContainerDeparture_ID, String trxName)
    {
      super (ctx, UNS_ContainerDeparture_ID, trxName);
      /** if (UNS_ContainerDeparture_ID == 0)
        {
			setUNS_ContainerDeparture_ID (0);
			setUNS_ContainerManagement_ID (0);
			setUNS_ShipmentSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ContainerDeparture (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ContainerDeparture[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Bea Cukai No.
		@param BCNo Bea Cukai No	  */
	public void setBCNo (String BCNo)
	{
		set_Value (COLUMNNAME_BCNo, BCNo);
	}

	/** Get Bea Cukai No.
		@return Bea Cukai No	  */
	public String getBCNo () 
	{
		return (String)get_Value(COLUMNNAME_BCNo);
	}

	/** Set Bill of Lading No.
		@param BLNo Bill of Lading No	  */
	public void setBLNo (String BLNo)
	{
		set_Value (COLUMNNAME_BLNo, BLNo);
	}

	/** Get Bill of Lading No.
		@return Bill of Lading No	  */
	public String getBLNo () 
	{
		return (String)get_Value(COLUMNNAME_BLNo);
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

	/** Set Remark.
		@param Remark Remark	  */
	public void setRemark (String Remark)
	{
		set_Value (COLUMNNAME_Remark, Remark);
	}

	/** Get Remark.
		@return Remark	  */
	public String getRemark () 
	{
		return (String)get_Value(COLUMNNAME_Remark);
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

	/** Set Container Departure.
		@param UNS_ContainerDeparture_ID Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID)
	{
		if (UNS_ContainerDeparture_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerDeparture_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerDeparture_ID, Integer.valueOf(UNS_ContainerDeparture_ID));
	}

	/** Get Container Departure.
		@return Container Departure	  */
	public int getUNS_ContainerDeparture_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerDeparture_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ContainerDeparture_UU.
		@param UNS_ContainerDeparture_UU UNS_ContainerDeparture_UU	  */
	public void setUNS_ContainerDeparture_UU (String UNS_ContainerDeparture_UU)
	{
		set_Value (COLUMNNAME_UNS_ContainerDeparture_UU, UNS_ContainerDeparture_UU);
	}

	/** Get UNS_ContainerDeparture_UU.
		@return UNS_ContainerDeparture_UU	  */
	public String getUNS_ContainerDeparture_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ContainerDeparture_UU);
	}

	public com.uns.model.I_UNS_ContainerManagement getUNS_ContainerManagement() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ContainerManagement)MTable.get(getCtx(), com.uns.model.I_UNS_ContainerManagement.Table_Name)
			.getPO(getUNS_ContainerManagement_ID(), get_TrxName());	}

	/** Set Container Management.
		@param UNS_ContainerManagement_ID Container Management	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID)
	{
		if (UNS_ContainerManagement_ID < 1) 
			set_Value (COLUMNNAME_UNS_ContainerManagement_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ContainerManagement_ID, Integer.valueOf(UNS_ContainerManagement_ID));
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

	public com.uns.model.I_UNS_ShipmentSchedule getUNS_ShipmentSchedule() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ShipmentSchedule)MTable.get(getCtx(), com.uns.model.I_UNS_ShipmentSchedule.Table_Name)
			.getPO(getUNS_ShipmentSchedule_ID(), get_TrxName());	}

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
}