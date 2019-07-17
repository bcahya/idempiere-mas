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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_ContainerDeparture
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ContainerDeparture 
{

    /** TableName=UNS_ContainerDeparture */
    public static final String Table_Name = "UNS_ContainerDeparture";

    /** AD_Table_ID=1000110 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name BCNo */
    public static final String COLUMNNAME_BCNo = "BCNo";

	/** Set Bea Cukai No	  */
	public void setBCNo (String BCNo);

	/** Get Bea Cukai No	  */
	public String getBCNo();

    /** Column name BLNo */
    public static final String COLUMNNAME_BLNo = "BLNo";

	/** Set Bill of Lading No	  */
	public void setBLNo (String BLNo);

	/** Get Bill of Lading No	  */
	public String getBLNo();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name SealNo */
    public static final String COLUMNNAME_SealNo = "SealNo";

	/** Set Seal No	  */
	public void setSealNo (String SealNo);

	/** Get Seal No	  */
	public String getSealNo();

    /** Column name UNS_ContainerDeparture_ID */
    public static final String COLUMNNAME_UNS_ContainerDeparture_ID = "UNS_ContainerDeparture_ID";

	/** Set Container Departure	  */
	public void setUNS_ContainerDeparture_ID (int UNS_ContainerDeparture_ID);

	/** Get Container Departure	  */
	public int getUNS_ContainerDeparture_ID();

    /** Column name UNS_ContainerDeparture_UU */
    public static final String COLUMNNAME_UNS_ContainerDeparture_UU = "UNS_ContainerDeparture_UU";

	/** Set UNS_ContainerDeparture_UU	  */
	public void setUNS_ContainerDeparture_UU (String UNS_ContainerDeparture_UU);

	/** Get UNS_ContainerDeparture_UU	  */
	public String getUNS_ContainerDeparture_UU();

    /** Column name UNS_ContainerManagement_ID */
    public static final String COLUMNNAME_UNS_ContainerManagement_ID = "UNS_ContainerManagement_ID";

	/** Set Container Management	  */
	public void setUNS_ContainerManagement_ID (int UNS_ContainerManagement_ID);

	/** Get Container Management	  */
	public int getUNS_ContainerManagement_ID();

	public com.uns.model.I_UNS_ContainerManagement getUNS_ContainerManagement() throws RuntimeException;

    /** Column name UNS_ShipmentSchedule_ID */
    public static final String COLUMNNAME_UNS_ShipmentSchedule_ID = "UNS_ShipmentSchedule_ID";

	/** Set Shipment Arragement	  */
	public void setUNS_ShipmentSchedule_ID (int UNS_ShipmentSchedule_ID);

	/** Get Shipment Arragement	  */
	public int getUNS_ShipmentSchedule_ID();

	public com.uns.model.I_UNS_ShipmentSchedule getUNS_ShipmentSchedule() throws RuntimeException;

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
}
