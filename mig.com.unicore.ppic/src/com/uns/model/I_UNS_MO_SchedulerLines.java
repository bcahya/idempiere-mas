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

/** Generated Interface for UNS_MO_SchedulerLines
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MO_SchedulerLines 
{

    /** TableName=UNS_MO_SchedulerLines */
    public static final String Table_Name = "UNS_MO_SchedulerLines";

    /** AD_Table_ID=1000333 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name CreateProduction */
    public static final String COLUMNNAME_CreateProduction = "CreateProduction";

	/** Set Create Production	  */
	public void setCreateProduction (String CreateProduction);

	/** Get Create Production	  */
	public String getCreateProduction();

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

    /** Column name ScheduledDate */
    public static final String COLUMNNAME_ScheduledDate = "ScheduledDate";

	/** Set Scheduled Date	  */
	public void setScheduledDate (Timestamp ScheduledDate);

	/** Get Scheduled Date	  */
	public Timestamp getScheduledDate();

    /** Column name UNS_MO_Scheduler_ID */
    public static final String COLUMNNAME_UNS_MO_Scheduler_ID = "UNS_MO_Scheduler_ID";

	/** Set MO Scheduler	  */
	public void setUNS_MO_Scheduler_ID (int UNS_MO_Scheduler_ID);

	/** Get MO Scheduler	  */
	public int getUNS_MO_Scheduler_ID();

	public com.uns.model.I_UNS_MO_Scheduler getUNS_MO_Scheduler() throws RuntimeException;

    /** Column name UNS_MO_SchedulerLines_ID */
    public static final String COLUMNNAME_UNS_MO_SchedulerLines_ID = "UNS_MO_SchedulerLines_ID";

	/** Set MO Scheduler Lines	  */
	public void setUNS_MO_SchedulerLines_ID (int UNS_MO_SchedulerLines_ID);

	/** Get MO Scheduler Lines	  */
	public int getUNS_MO_SchedulerLines_ID();

    /** Column name UNS_MO_SchedulerLines_UU */
    public static final String COLUMNNAME_UNS_MO_SchedulerLines_UU = "UNS_MO_SchedulerLines_UU";

	/** Set UNS_MO_SchedulerLines_UU	  */
	public void setUNS_MO_SchedulerLines_UU (String UNS_MO_SchedulerLines_UU);

	/** Get UNS_MO_SchedulerLines_UU	  */
	public String getUNS_MO_SchedulerLines_UU();

    /** Column name UNS_ProductionSchedule_ID */
    public static final String COLUMNNAME_UNS_ProductionSchedule_ID = "UNS_ProductionSchedule_ID";

	/** Set Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID);

	/** Get Production Schedule	  */
	public int getUNS_ProductionSchedule_ID();

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException;

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
