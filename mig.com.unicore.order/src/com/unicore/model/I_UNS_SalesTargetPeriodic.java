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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_SalesTargetPeriodic
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_SalesTargetPeriodic 
{

    /** TableName=UNS_SalesTargetPeriodic */
    public static final String Table_Name = "UNS_SalesTargetPeriodic";

    /** AD_Table_ID=1000151 */
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

    /** Column name Deduction */
    public static final String COLUMNNAME_Deduction = "Deduction";

	/** Set Deduction	  */
	public void setDeduction (BigDecimal Deduction);

	/** Get Deduction	  */
	public BigDecimal getDeduction();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name Incentive */
    public static final String COLUMNNAME_Incentive = "Incentive";

	/** Set Incentive %	  */
	public void setIncentive (BigDecimal Incentive);

	/** Get Incentive %	  */
	public BigDecimal getIncentive();

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

    /** Column name PeriodEnd_ID */
    public static final String COLUMNNAME_PeriodEnd_ID = "PeriodEnd_ID";

	/** Set Period End	  */
	public void setPeriodEnd_ID (int PeriodEnd_ID);

	/** Get Period End	  */
	public int getPeriodEnd_ID();

	public org.compiere.model.I_C_Period getPeriodEnd() throws RuntimeException;

    /** Column name PeriodStart_ID */
    public static final String COLUMNNAME_PeriodStart_ID = "PeriodStart_ID";

	/** Set Period Start	  */
	public void setPeriodStart_ID (int PeriodStart_ID);

	/** Get Period Start	  */
	public int getPeriodStart_ID();

	public org.compiere.model.I_C_Period getPeriodStart() throws RuntimeException;

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name TOPTarget */
    public static final String COLUMNNAME_TOPTarget = "TOPTarget";

	/** Set Top Target.
	  * Term of Payment target.
	  */
	public void setTOPTarget (BigDecimal TOPTarget);

	/** Get Top Target.
	  * Term of Payment target.
	  */
	public BigDecimal getTOPTarget();

    /** Column name UNS_SalesTargetLine_ID */
    public static final String COLUMNNAME_UNS_SalesTargetLine_ID = "UNS_SalesTargetLine_ID";

	/** Set Sales Target Line	  */
	public void setUNS_SalesTargetLine_ID (int UNS_SalesTargetLine_ID);

	/** Get Sales Target Line	  */
	public int getUNS_SalesTargetLine_ID();

	public com.unicore.model.I_UNS_SalesTargetLine getUNS_SalesTargetLine() throws RuntimeException;

    /** Column name UNS_SalesTargetPeriodic_ID */
    public static final String COLUMNNAME_UNS_SalesTargetPeriodic_ID = "UNS_SalesTargetPeriodic_ID";

	/** Set Sales Taget Periodic	  */
	public void setUNS_SalesTargetPeriodic_ID (int UNS_SalesTargetPeriodic_ID);

	/** Get Sales Taget Periodic	  */
	public int getUNS_SalesTargetPeriodic_ID();

    /** Column name UNS_SalesTargetPeriodic_UU */
    public static final String COLUMNNAME_UNS_SalesTargetPeriodic_UU = "UNS_SalesTargetPeriodic_UU";

	/** Set UNS_SalesTargetPeriodic_UU	  */
	public void setUNS_SalesTargetPeriodic_UU (String UNS_SalesTargetPeriodic_UU);

	/** Get UNS_SalesTargetPeriodic_UU	  */
	public String getUNS_SalesTargetPeriodic_UU();

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

    /** Column name ValueTarget */
    public static final String COLUMNNAME_ValueTarget = "ValueTarget";

	/** Set Value Target	  */
	public void setValueTarget (BigDecimal ValueTarget);

	/** Get Value Target	  */
	public BigDecimal getValueTarget();
}
