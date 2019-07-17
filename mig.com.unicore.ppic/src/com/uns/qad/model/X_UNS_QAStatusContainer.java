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
package com.uns.qad.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_QAStatusContainer
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatusContainer extends PO implements I_UNS_QAStatusContainer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_QAStatusContainer (Properties ctx, int UNS_QAStatusContainer_ID, String trxName)
    {
      super (ctx, UNS_QAStatusContainer_ID, trxName);
      /** if (UNS_QAStatusContainer_ID == 0)
        {
			setChkByLOG (null);
			setChkByQAD (null);
			setCleanliness (null);
// N
			setDoor (null);
// P
			setExteriorCondition (null);
// P
			setFloor (null);
// P
			setHinges (null);
// P
			setInspectionTime (new Timestamp( System.currentTimeMillis() ));
// @Time@
			setIsNC (true);
// Y
			setOdourSmell (null);
// A
			setPestActivity (null);
// N
			setProcessed (false);
// N
			setQAStatus (null);
// PI
			setRoof (null);
// P
			setSeal (null);
// P
			setUNS_QAContainerInspection_ID (0);
			setUNS_QAStatusContainer_ID (0);
			setWall (null);
// P
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatusContainer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatusContainer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set LOG.
		@param ChkByLOG LOG	  */
	public void setChkByLOG (String ChkByLOG)
	{
		set_Value (COLUMNNAME_ChkByLOG, ChkByLOG);
	}

	/** Get LOG.
		@return LOG	  */
	public String getChkByLOG () 
	{
		return (String)get_Value(COLUMNNAME_ChkByLOG);
	}

	/** Set QAD.
		@param ChkByQAD QAD	  */
	public void setChkByQAD (String ChkByQAD)
	{
		set_Value (COLUMNNAME_ChkByQAD, ChkByQAD);
	}

	/** Get QAD.
		@return QAD	  */
	public String getChkByQAD () 
	{
		return (String)get_Value(COLUMNNAME_ChkByQAD);
	}

	/** Cleanliness AD_Reference_ID=1000133 */
	public static final int CLEANLINESS_AD_Reference_ID=1000133;
	/** OK = O */
	public static final String CLEANLINESS_OK = "O";
	/** Not-OK = N */
	public static final String CLEANLINESS_Not_OK = "N";
	/** Set Cleanliness.
		@param Cleanliness Cleanliness	  */
	public void setCleanliness (String Cleanliness)
	{

		set_Value (COLUMNNAME_Cleanliness, Cleanliness);
	}

	/** Get Cleanliness.
		@return Cleanliness	  */
	public String getCleanliness () 
	{
		return (String)get_Value(COLUMNNAME_Cleanliness);
	}

	/** Door AD_Reference_ID=1000130 */
	public static final int DOOR_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String DOOR_Good = "G";
	/** Poor = P */
	public static final String DOOR_Poor = "P";
	/** Set Door.
		@param Door Door	  */
	public void setDoor (String Door)
	{

		set_Value (COLUMNNAME_Door, Door);
	}

	/** Get Door.
		@return Door	  */
	public String getDoor () 
	{
		return (String)get_Value(COLUMNNAME_Door);
	}

	/** ExteriorCondition AD_Reference_ID=1000130 */
	public static final int EXTERIORCONDITION_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String EXTERIORCONDITION_Good = "G";
	/** Poor = P */
	public static final String EXTERIORCONDITION_Poor = "P";
	/** Set Exterior Condition.
		@param ExteriorCondition Exterior Condition	  */
	public void setExteriorCondition (String ExteriorCondition)
	{

		set_Value (COLUMNNAME_ExteriorCondition, ExteriorCondition);
	}

	/** Get Exterior Condition.
		@return Exterior Condition	  */
	public String getExteriorCondition () 
	{
		return (String)get_Value(COLUMNNAME_ExteriorCondition);
	}

	/** Floor AD_Reference_ID=1000130 */
	public static final int FLOOR_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String FLOOR_Good = "G";
	/** Poor = P */
	public static final String FLOOR_Poor = "P";
	/** Set Floor.
		@param Floor Floor	  */
	public void setFloor (String Floor)
	{

		set_Value (COLUMNNAME_Floor, Floor);
	}

	/** Get Floor.
		@return Floor	  */
	public String getFloor () 
	{
		return (String)get_Value(COLUMNNAME_Floor);
	}

	/** Hinges AD_Reference_ID=1000130 */
	public static final int HINGES_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String HINGES_Good = "G";
	/** Poor = P */
	public static final String HINGES_Poor = "P";
	/** Set Hinges.
		@param Hinges Hinges	  */
	public void setHinges (String Hinges)
	{

		set_Value (COLUMNNAME_Hinges, Hinges);
	}

	/** Get Hinges.
		@return Hinges	  */
	public String getHinges () 
	{
		return (String)get_Value(COLUMNNAME_Hinges);
	}

	/** Set Inspection Time.
		@param InspectionTime Inspection Time	  */
	public void setInspectionTime (Timestamp InspectionTime)
	{
		set_Value (COLUMNNAME_InspectionTime, InspectionTime);
	}

	/** Get Inspection Time.
		@return Inspection Time	  */
	public Timestamp getInspectionTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_InspectionTime);
	}

	/** Set Not Comformance.
		@param IsNC Not Comformance	  */
	public void setIsNC (boolean IsNC)
	{
		set_Value (COLUMNNAME_IsNC, Boolean.valueOf(IsNC));
	}

	/** Get Not Comformance.
		@return Not Comformance	  */
	public boolean isNC () 
	{
		Object oo = get_Value(COLUMNNAME_IsNC);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** OdourSmell AD_Reference_ID=1000132 */
	public static final int ODOURSMELL_AD_Reference_ID=1000132;
	/** Normal = N */
	public static final String ODOURSMELL_Normal = "N";
	/** Abnormal = A */
	public static final String ODOURSMELL_Abnormal = "A";
	/** Set Odour/Smell.
		@param OdourSmell Odour/Smell	  */
	public void setOdourSmell (String OdourSmell)
	{

		set_Value (COLUMNNAME_OdourSmell, OdourSmell);
	}

	/** Get Odour/Smell.
		@return Odour/Smell	  */
	public String getOdourSmell () 
	{
		return (String)get_Value(COLUMNNAME_OdourSmell);
	}

	/** PestActivity AD_Reference_ID=1000131 */
	public static final int PESTACTIVITY_AD_Reference_ID=1000131;
	/** Yes = Y */
	public static final String PESTACTIVITY_Yes = "Y";
	/** No = N */
	public static final String PESTACTIVITY_No = "N";
	/** Set Pest Activity.
		@param PestActivity Pest Activity	  */
	public void setPestActivity (String PestActivity)
	{

		set_Value (COLUMNNAME_PestActivity, PestActivity);
	}

	/** Get Pest Activity.
		@return Pest Activity	  */
	public String getPestActivity () 
	{
		return (String)get_Value(COLUMNNAME_PestActivity);
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

	/** Roof AD_Reference_ID=1000130 */
	public static final int ROOF_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String ROOF_Good = "G";
	/** Poor = P */
	public static final String ROOF_Poor = "P";
	/** Set Roof.
		@param Roof Roof	  */
	public void setRoof (String Roof)
	{

		set_Value (COLUMNNAME_Roof, Roof);
	}

	/** Get Roof.
		@return Roof	  */
	public String getRoof () 
	{
		return (String)get_Value(COLUMNNAME_Roof);
	}

	/** Seal AD_Reference_ID=1000130 */
	public static final int SEAL_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String SEAL_Good = "G";
	/** Poor = P */
	public static final String SEAL_Poor = "P";
	/** Set Seal.
		@param Seal Seal	  */
	public void setSeal (String Seal)
	{

		set_Value (COLUMNNAME_Seal, Seal);
	}

	/** Get Seal.
		@return Seal	  */
	public String getSeal () 
	{
		return (String)get_Value(COLUMNNAME_Seal);
	}

	/** Set Container Arrival.
		@param UNS_ContainerArrival_ID Container Arrival	  */
	public void setUNS_ContainerArrival_ID (int UNS_ContainerArrival_ID)
	{
		if (UNS_ContainerArrival_ID < 1) 
			set_Value (COLUMNNAME_UNS_ContainerArrival_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ContainerArrival_ID, Integer.valueOf(UNS_ContainerArrival_ID));
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

	/** Set Container Departure.
		@param UNS_ContainerDeparture_ID Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID)
	{
		if (UNS_ContainerDeparture_ID < 1) 
			set_Value (COLUMNNAME_UNS_ContainerDeparture_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ContainerDeparture_ID, Integer.valueOf(UNS_ContainerDeparture_ID));
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

	public com.uns.qad.model.I_UNS_QAContainerInspection getUNS_QAContainerInspection() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAContainerInspection)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAContainerInspection.Table_Name)
			.getPO(getUNS_QAContainerInspection_ID(), get_TrxName());	}

	/** Set Container Inspection.
		@param UNS_QAContainerInspection_ID Container Inspection	  */
	public void setUNS_QAContainerInspection_ID (int UNS_QAContainerInspection_ID)
	{
		if (UNS_QAContainerInspection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAContainerInspection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAContainerInspection_ID, Integer.valueOf(UNS_QAContainerInspection_ID));
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

	/** Set Container Status.
		@param UNS_QAStatusContainer_ID Container Status	  */
	public void setUNS_QAStatusContainer_ID (int UNS_QAStatusContainer_ID)
	{
		if (UNS_QAStatusContainer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatusContainer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatusContainer_ID, Integer.valueOf(UNS_QAStatusContainer_ID));
	}

	/** Get Container Status.
		@return Container Status	  */
	public int getUNS_QAStatusContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatusContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatusContainer_UU.
		@param UNS_QAStatusContainer_UU UNS_QAStatusContainer_UU	  */
	public void setUNS_QAStatusContainer_UU (String UNS_QAStatusContainer_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatusContainer_UU, UNS_QAStatusContainer_UU);
	}

	/** Get UNS_QAStatusContainer_UU.
		@return UNS_QAStatusContainer_UU	  */
	public String getUNS_QAStatusContainer_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatusContainer_UU);
	}

	/** Wall AD_Reference_ID=1000130 */
	public static final int WALL_AD_Reference_ID=1000130;
	/** Good = G */
	public static final String WALL_Good = "G";
	/** Poor = P */
	public static final String WALL_Poor = "P";
	/** Set Wall.
		@param Wall Wall	  */
	public void setWall (String Wall)
	{

		set_Value (COLUMNNAME_Wall, Wall);
	}

	/** Get Wall.
		@return Wall	  */
	public String getWall () 
	{
		return (String)get_Value(COLUMNNAME_Wall);
	}
}