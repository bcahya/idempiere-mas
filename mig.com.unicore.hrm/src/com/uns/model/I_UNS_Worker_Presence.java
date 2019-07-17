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

/** Generated Interface for UNS_Worker_Presence
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Worker_Presence 
{

    /** TableName=UNS_Worker_Presence */
    public static final String Table_Name = "UNS_Worker_Presence";

    /** AD_Table_ID=1000049 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
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

    /** Column name DayType */
    public static final String COLUMNNAME_DayType = "DayType";

	/** Set Day Type	  */
	public void setDayType (String DayType);

	/** Get Day Type	  */
	public String getDayType();

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

    /** Column name NoWorkDayIncentive */
    public static final String COLUMNNAME_NoWorkDayIncentive = "NoWorkDayIncentive";

	/** Set No-Work Day Incentive.
	  * The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive);

	/** Get No-Work Day Incentive.
	  * The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive();

    /** Column name OT_Loading_ID */
    public static final String COLUMNNAME_OT_Loading_ID = "OT_Loading_ID";

	/** Set OT Loading.
	  * Over Time Loading
	  */
	public void setOT_Loading_ID (int OT_Loading_ID);

	/** Get OT Loading.
	  * Over Time Loading
	  */
	public int getOT_Loading_ID();

    /** Column name OT_ProductionWOrker_ID */
    public static final String COLUMNNAME_OT_ProductionWOrker_ID = "OT_ProductionWOrker_ID";

	/** Set OT Production Worker.
	  * Over Time Production Worker
	  */
	public void setOT_ProductionWOrker_ID (int OT_ProductionWOrker_ID);

	/** Get OT Production Worker.
	  * Over Time Production Worker
	  */
	public int getOT_ProductionWOrker_ID();

    /** Column name OT_Unloading_ID */
    public static final String COLUMNNAME_OT_Unloading_ID = "OT_Unloading_ID";

	/** Set OT Unloading.
	  * Over Time Unloading
	  */
	public void setOT_Unloading_ID (int OT_Unloading_ID);

	/** Get OT Unloading.
	  * Over Time Unloading
	  */
	public int getOT_Unloading_ID();

    /** Column name Overtime */
    public static final String COLUMNNAME_Overtime = "Overtime";

	/** Set Overtime	  */
	public void setOvertime (BigDecimal Overtime);

	/** Get Overtime	  */
	public BigDecimal getOvertime();

    /** Column name OvertimeReceivable */
    public static final String COLUMNNAME_OvertimeReceivable = "OvertimeReceivable";

	/** Set Overtime Receivable.
	  * Worker's receivable based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public void setOvertimeReceivable (BigDecimal OvertimeReceivable);

	/** Get Overtime Receivable.
	  * Worker's receivable based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public BigDecimal getOvertimeReceivable();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name PermissionType */
    public static final String COLUMNNAME_PermissionType = "PermissionType";

	/** Set PermissionType	  */
	public void setPermissionType (String PermissionType);

	/** Get PermissionType	  */
	public String getPermissionType();

    /** Column name PresenceStatus */
    public static final String COLUMNNAME_PresenceStatus = "PresenceStatus";

	/** Set Presence Status	  */
	public void setPresenceStatus (String PresenceStatus);

	/** Get Presence Status	  */
	public String getPresenceStatus();

    /** Column name ProductionQty */
    public static final String COLUMNNAME_ProductionQty = "ProductionQty";

	/** Set Production Quantity.
	  * Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty);

	/** Get Production Quantity.
	  * Quantity of products to produce
	  */
	public BigDecimal getProductionQty();

    /** Column name ReceivableAmt */
    public static final String COLUMNNAME_ReceivableAmt = "ReceivableAmt";

	/** Set Basic Receivable	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt);

	/** Get Basic Receivable	  */
	public BigDecimal getReceivableAmt();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name SubsidyAmt */
    public static final String COLUMNNAME_SubsidyAmt = "SubsidyAmt";

	/** Set Subsidy Amt.
	  * The amount of subsidy to be reserved for this worker
	  */
	public void setSubsidyAmt (BigDecimal SubsidyAmt);

	/** Get Subsidy Amt.
	  * The amount of subsidy to be reserved for this worker
	  */
	public BigDecimal getSubsidyAmt();

    /** Column name SubsidyReserved */
    public static final String COLUMNNAME_SubsidyReserved = "SubsidyReserved";

	/** Set Reserved a Subsidy.
	  * Worker is reserved for a subsidy based on company's rules
	  */
	public void setSubsidyReserved (boolean SubsidyReserved);

	/** Get Reserved a Subsidy.
	  * Worker is reserved for a subsidy based on company's rules
	  */
	public boolean isSubsidyReserved();

    /** Column name TotalReceivableAmt */
    public static final String COLUMNNAME_TotalReceivableAmt = "TotalReceivableAmt";

	/** Set Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt);

	/** Get Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt();

    /** Column name UNS_BongkarMuat_ID */
    public static final String COLUMNNAME_UNS_BongkarMuat_ID = "UNS_BongkarMuat_ID";

	/** Set Bongkar Muat	  */
	public void setUNS_BongkarMuat_ID (int UNS_BongkarMuat_ID);

	/** Get Bongkar Muat	  */
	public int getUNS_BongkarMuat_ID();

    /** Column name UNS_HalfPeriod_Presence_ID */
    public static final String COLUMNNAME_UNS_HalfPeriod_Presence_ID = "UNS_HalfPeriod_Presence_ID";

	/** Set Half Period Presence	  */
	public void setUNS_HalfPeriod_Presence_ID (int UNS_HalfPeriod_Presence_ID);

	/** Get Half Period Presence	  */
	public int getUNS_HalfPeriod_Presence_ID();

	public com.uns.model.I_UNS_HalfPeriod_Presence getUNS_HalfPeriod_Presence() throws RuntimeException;

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

    /** Column name UNS_LoadingCost_ID */
    public static final String COLUMNNAME_UNS_LoadingCost_ID = "UNS_LoadingCost_ID";

	/** Set Loading Cost	  */
	public void setUNS_LoadingCost_ID (int UNS_LoadingCost_ID);

	/** Get Loading Cost	  */
	public int getUNS_LoadingCost_ID();

    /** Column name UNS_Production_Worker_ID */
    public static final String COLUMNNAME_UNS_Production_Worker_ID = "UNS_Production_Worker_ID";

	/** Set Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID);

	/** Get Production Worker	  */
	public int getUNS_Production_Worker_ID();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

    /** Column name UNS_Worker_Presence_ID */
    public static final String COLUMNNAME_UNS_Worker_Presence_ID = "UNS_Worker_Presence_ID";

	/** Set Worker Presence	  */
	public void setUNS_Worker_Presence_ID (int UNS_Worker_Presence_ID);

	/** Get Worker Presence	  */
	public int getUNS_Worker_Presence_ID();

    /** Column name UNS_Worker_Presence_UU */
    public static final String COLUMNNAME_UNS_Worker_Presence_UU = "UNS_Worker_Presence_UU";

	/** Set UNS_Worker_Presence_UU	  */
	public void setUNS_Worker_Presence_UU (String UNS_Worker_Presence_UU);

	/** Get UNS_Worker_Presence_UU	  */
	public String getUNS_Worker_Presence_UU();

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

    /** Column name WorkDate */
    public static final String COLUMNNAME_WorkDate = "WorkDate";

	/** Set Work Date	  */
	public void setWorkDate (Timestamp WorkDate);

	/** Get Work Date	  */
	public Timestamp getWorkDate();
}
