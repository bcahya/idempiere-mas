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

/** Generated Interface for UNS_Production_Worker
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Production_Worker 
{

    /** TableName=UNS_Production_Worker */
    public static final String Table_Name = "UNS_Production_Worker";

    /** AD_Table_ID=1000063 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name HoursOverTime */
    public static final String COLUMNNAME_HoursOverTime = "HoursOverTime";

	/** Set Overtime (Hours).
	  * The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public void setHoursOverTime (BigDecimal HoursOverTime);

	/** Get Overtime (Hours).
	  * The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public BigDecimal getHoursOverTime();

    /** Column name InsentifPemborong */
    public static final String COLUMNNAME_InsentifPemborong = "InsentifPemborong";

	/** Set Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong);

	/** Get Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong();

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

    /** Column name IsAdditional */
    public static final String COLUMNNAME_IsAdditional = "IsAdditional";

	/** Set Additional.
	  * Check this box if this is additional
	  */
	public void setIsAdditional (boolean IsAdditional);

	/** Get Additional.
	  * Check this box if this is additional
	  */
	public boolean isAdditional();

    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/** Set Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/** Get Is Subcontracting	  */
	public boolean isSubcontracting();

    /** Column name Labor_ID */
    public static final String COLUMNNAME_Labor_ID = "Labor_ID";

	/** Set Labor ID.
	  * User ID or account number
	  */
	public void setLabor_ID (int Labor_ID);

	/** Get Labor ID.
	  * User ID or account number
	  */
	public int getLabor_ID();

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

    /** Column name OvertimeReceivable */
    public static final String COLUMNNAME_OvertimeReceivable = "OvertimeReceivable";

	/** Set Overtime Receivable.
	  * Worker's receivable amount based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public void setOvertimeReceivable (BigDecimal OvertimeReceivable);

	/** Get Overtime Receivable.
	  * Worker's receivable amount based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public BigDecimal getOvertimeReceivable();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name PresenceStatus */
    public static final String COLUMNNAME_PresenceStatus = "PresenceStatus";

	/** Set Presence Status	  */
	public void setPresenceStatus (String PresenceStatus);

	/** Get Presence Status	  */
	public String getPresenceStatus();

    /** Column name ReceivableAmt */
    public static final String COLUMNNAME_ReceivableAmt = "ReceivableAmt";

	/** Set Basic Receivable.
	  * Basic receivable amount calculated based on worker's production result/presence
	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt);

	/** Get Basic Receivable.
	  * Basic receivable amount calculated based on worker's production result/presence
	  */
	public BigDecimal getReceivableAmt();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name ReplacementLabor_ID */
    public static final String COLUMNNAME_ReplacementLabor_ID = "ReplacementLabor_ID";

	/** Set Replacement Labor	  */
	public void setReplacementLabor_ID (int ReplacementLabor_ID);

	/** Get Replacement Labor	  */
	public int getReplacementLabor_ID();

    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/** Set Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID);

	/** Get Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID();

    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/** Set Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor_ID (int Supervisor_ID);

	/** Get Supervisor.
	  * Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID();

    /** Column name TotalReceivableAmt */
    public static final String COLUMNNAME_TotalReceivableAmt = "TotalReceivableAmt";

	/** Set Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt);

	/** Get Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt();

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

	public com.uns.model.I_UNS_Job_Role getUNS_Job_Role() throws RuntimeException;

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException;

    /** Column name UNS_Production_Worker_ID */
    public static final String COLUMNNAME_UNS_Production_Worker_ID = "UNS_Production_Worker_ID";

	/** Set Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID);

	/** Get Production Worker	  */
	public int getUNS_Production_Worker_ID();

    /** Column name UNS_Production_Worker_UU */
    public static final String COLUMNNAME_UNS_Production_Worker_UU = "UNS_Production_Worker_UU";

	/** Set UNS_Production_Worker_UU	  */
	public void setUNS_Production_Worker_UU (String UNS_Production_Worker_UU);

	/** Get UNS_Production_Worker_UU	  */
	public String getUNS_Production_Worker_UU();

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

    /** Column name WorkHours */
    public static final String COLUMNNAME_WorkHours = "WorkHours";

	/** Set Normal Work Hours	  */
	public void setWorkHours (BigDecimal WorkHours);

	/** Get Normal Work Hours	  */
	public BigDecimal getWorkHours();
}
