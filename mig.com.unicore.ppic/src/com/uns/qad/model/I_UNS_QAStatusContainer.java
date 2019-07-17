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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QAStatusContainer
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_QAStatusContainer 
{

    /** TableName=UNS_QAStatusContainer */
    public static final String Table_Name = "UNS_QAStatusContainer";

    /** AD_Table_ID=1000235 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name ChkByLOG */
    public static final String COLUMNNAME_ChkByLOG = "ChkByLOG";

	/** Set LOG	  */
	public void setChkByLOG (String ChkByLOG);

	/** Get LOG	  */
	public String getChkByLOG();

    /** Column name ChkByQAD */
    public static final String COLUMNNAME_ChkByQAD = "ChkByQAD";

	/** Set QAD	  */
	public void setChkByQAD (String ChkByQAD);

	/** Get QAD	  */
	public String getChkByQAD();

    /** Column name Cleanliness */
    public static final String COLUMNNAME_Cleanliness = "Cleanliness";

	/** Set Cleanliness	  */
	public void setCleanliness (String Cleanliness);

	/** Get Cleanliness	  */
	public String getCleanliness();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Door */
    public static final String COLUMNNAME_Door = "Door";

	/** Set Door	  */
	public void setDoor (String Door);

	/** Get Door	  */
	public String getDoor();

    /** Column name ExteriorCondition */
    public static final String COLUMNNAME_ExteriorCondition = "ExteriorCondition";

	/** Set Exterior Condition	  */
	public void setExteriorCondition (String ExteriorCondition);

	/** Get Exterior Condition	  */
	public String getExteriorCondition();

    /** Column name Floor */
    public static final String COLUMNNAME_Floor = "Floor";

	/** Set Floor	  */
	public void setFloor (String Floor);

	/** Get Floor	  */
	public String getFloor();

    /** Column name Hinges */
    public static final String COLUMNNAME_Hinges = "Hinges";

	/** Set Hinges	  */
	public void setHinges (String Hinges);

	/** Get Hinges	  */
	public String getHinges();

    /** Column name InspectionTime */
    public static final String COLUMNNAME_InspectionTime = "InspectionTime";

	/** Set Inspection Time	  */
	public void setInspectionTime (Timestamp InspectionTime);

	/** Get Inspection Time	  */
	public Timestamp getInspectionTime();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsNC */
    public static final String COLUMNNAME_IsNC = "IsNC";

	/** Set Not Comformance	  */
	public void setIsNC (boolean IsNC);

	/** Get Not Comformance	  */
	public boolean isNC();

    /** Column name OdourSmell */
    public static final String COLUMNNAME_OdourSmell = "OdourSmell";

	/** Set Odour/Smell	  */
	public void setOdourSmell (String OdourSmell);

	/** Get Odour/Smell	  */
	public String getOdourSmell();

    /** Column name PestActivity */
    public static final String COLUMNNAME_PestActivity = "PestActivity";

	/** Set Pest Activity	  */
	public void setPestActivity (String PestActivity);

	/** Get Pest Activity	  */
	public String getPestActivity();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name QAStatus */
    public static final String COLUMNNAME_QAStatus = "QAStatus";

	/** Set QA Status	  */
	public void setQAStatus (String QAStatus);

	/** Get QA Status	  */
	public String getQAStatus();

    /** Column name Remark */
    public static final String COLUMNNAME_Remark = "Remark";

	/** Set Remark	  */
	public void setRemark (String Remark);

	/** Get Remark	  */
	public String getRemark();

    /** Column name Roof */
    public static final String COLUMNNAME_Roof = "Roof";

	/** Set Roof	  */
	public void setRoof (String Roof);

	/** Get Roof	  */
	public String getRoof();

    /** Column name Seal */
    public static final String COLUMNNAME_Seal = "Seal";

	/** Set Seal	  */
	public void setSeal (String Seal);

	/** Get Seal	  */
	public String getSeal();

    /** Column name UNS_ContainerArrival_ID */
    public static final String COLUMNNAME_UNS_ContainerArrival_ID = "UNS_ContainerArrival_ID";

	/** Set Container Arrival	  */
	public void setUNS_ContainerArrival_ID (int UNS_ContainerArrival_ID);

	/** Get Container Arrival	  */
	public int getUNS_ContainerArrival_ID();

    /** Column name UNS_ContainerDeparture_ID */
    public static final String COLUMNNAME_UNS_ContainerDeparture_ID = "UNS_ContainerDeparture_ID";

	/** Set Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID);

	/** Get Container Departure	  */
	public int getUNS_ContainerDeparture_ID();

    /** Column name UNS_ContainerManagement_ID */
    public static final String COLUMNNAME_UNS_ContainerManagement_ID = "UNS_ContainerManagement_ID";

	/** Set Container Management	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID);

	/** Get Container Management	  */
	public int getUNS_ContainerManagement_ID();

    /** Column name UNS_QAContainerInspection_ID */
    public static final String COLUMNNAME_UNS_QAContainerInspection_ID = "UNS_QAContainerInspection_ID";

	/** Set Container Inspection	  */
	public void setUNS_QAContainerInspection_ID (int UNS_QAContainerInspection_ID);

	/** Get Container Inspection	  */
	public int getUNS_QAContainerInspection_ID();

	public com.uns.qad.model.I_UNS_QAContainerInspection getUNS_QAContainerInspection() throws RuntimeException;

    /** Column name UNS_QAStatusContainer_ID */
    public static final String COLUMNNAME_UNS_QAStatusContainer_ID = "UNS_QAStatusContainer_ID";

	/** Set Container Status	  */
	public void setUNS_QAStatusContainer_ID (int UNS_QAStatusContainer_ID);

	/** Get Container Status	  */
	public int getUNS_QAStatusContainer_ID();

    /** Column name UNS_QAStatusContainer_UU */
    public static final String COLUMNNAME_UNS_QAStatusContainer_UU = "UNS_QAStatusContainer_UU";

	/** Set UNS_QAStatusContainer_UU	  */
	public void setUNS_QAStatusContainer_UU (String UNS_QAStatusContainer_UU);

	/** Get UNS_QAStatusContainer_UU	  */
	public String getUNS_QAStatusContainer_UU();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Wall */
    public static final String COLUMNNAME_Wall = "Wall";

	/** Set Wall	  */
	public void setWall (String Wall);

	/** Get Wall	  */
	public String getWall();
}
