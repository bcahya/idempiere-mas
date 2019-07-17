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

/** Generated Interface for UNS_ProductionPayConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ProductionPayConfig 
{

    /** TableName=UNS_ProductionPayConfig */
    public static final String Table_Name = "UNS_ProductionPayConfig";

    /** AD_Table_ID=1000071 */
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

    /** Column name CutOffWeekDay */
    public static final String COLUMNNAME_CutOffWeekDay = "CutOffWeekDay";

	/** Set Cut Off Week Day.
	  * Teh day to cut off production/transaction in every week.
	  */
	public void setCutOffWeekDay (String CutOffWeekDay);

	/** Get Cut Off Week Day.
	  * Teh day to cut off production/transaction in every week.
	  */
	public String getCutOffWeekDay();

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsPayableSK */
    public static final String COLUMNNAME_IsPayableSK = "IsPayableSK";

	/** Set SK Daily Payable	  */
	public void setIsPayableSK (boolean IsPayableSK);

	/** Get SK Daily Payable	  */
	public boolean isPayableSK();

    /** Column name IsPayableSKK */
    public static final String COLUMNNAME_IsPayableSKK = "IsPayableSKK";

	/** Set SKK Daily Payable	  */
	public void setIsPayableSKK (boolean IsPayableSKK);

	/** Get SKK Daily Payable	  */
	public boolean isPayableSKK();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NoWorkDayIncentive */
    public static final String COLUMNNAME_NoWorkDayIncentive = "NoWorkDayIncentive";

	/** Set No-Work Day Incentive.
	  * The default incentive amount for supervised worker if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive);

	/** Get No-Work Day Incentive.
	  * The default incentive amount for supervised worker if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

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

    /** Column name Spv_Period */
    public static final String COLUMNNAME_Spv_Period = "Spv_Period";

	/** Set Supervisor Period	  */
	public void setSpv_Period (String Spv_Period);

	/** Get Supervisor Period	  */
	public String getSpv_Period();

    /** Column name SupervisedInsentifPemborong */
    public static final String COLUMNNAME_SupervisedInsentifPemborong = "SupervisedInsentifPemborong";

	/** Set Supervised Insentif Pemborong	  */
	public void setSupervisedInsentifPemborong (BigDecimal SupervisedInsentifPemborong);

	/** Get Supervised Insentif Pemborong	  */
	public BigDecimal getSupervisedInsentifPemborong();

    /** Column name SupervisedPayFullDay */
    public static final String COLUMNNAME_SupervisedPayFullDay = "SupervisedPayFullDay";

	/** Set Pay Full Day.
	  * Supervised Pay Full Day
	  */
	public void setSupervisedPayFullDay (BigDecimal SupervisedPayFullDay);

	/** Get Pay Full Day.
	  * Supervised Pay Full Day
	  */
	public BigDecimal getSupervisedPayFullDay();

    /** Column name SupervisedPayFullHoliday */
    public static final String COLUMNNAME_SupervisedPayFullHoliday = "SupervisedPayFullHoliday";

	/** Set Pay Full Holiday.
	  * Supervised Pay Full Holiday
	  */
	public void setSupervisedPayFullHoliday (BigDecimal SupervisedPayFullHoliday);

	/** Get Pay Full Holiday.
	  * Supervised Pay Full Holiday
	  */
	public BigDecimal getSupervisedPayFullHoliday();

    /** Column name SupervisedPayFullNHoliday */
    public static final String COLUMNNAME_SupervisedPayFullNHoliday = "SupervisedPayFullNHoliday";

	/** Set Pay Full National Holiday.
	  * Supervised Pay Full National Holiday
	  */
	public void setSupervisedPayFullNHoliday (BigDecimal SupervisedPayFullNHoliday);

	/** Get Pay Full National Holiday.
	  * Supervised Pay Full National Holiday
	  */
	public BigDecimal getSupervisedPayFullNHoliday();

    /** Column name SupervisedPayHalfDay */
    public static final String COLUMNNAME_SupervisedPayHalfDay = "SupervisedPayHalfDay";

	/** Set Pay Half Day.
	  * Supervised Pay Half Day
	  */
	public void setSupervisedPayHalfDay (BigDecimal SupervisedPayHalfDay);

	/** Get Pay Half Day.
	  * Supervised Pay Half Day
	  */
	public BigDecimal getSupervisedPayHalfDay();

    /** Column name SupervisedPayHalfHoliday */
    public static final String COLUMNNAME_SupervisedPayHalfHoliday = "SupervisedPayHalfHoliday";

	/** Set Pay Half Holiday.
	  * Supervised Pay Half Holiday
	  */
	public void setSupervisedPayHalfHoliday (BigDecimal SupervisedPayHalfHoliday);

	/** Get Pay Half Holiday.
	  * Supervised Pay Half Holiday
	  */
	public BigDecimal getSupervisedPayHalfHoliday();

    /** Column name SupervisedPayHalfNHoliday */
    public static final String COLUMNNAME_SupervisedPayHalfNHoliday = "SupervisedPayHalfNHoliday";

	/** Set Pay Half National Holiday.
	  * Supervised Pay Half National Holiday
	  */
	public void setSupervisedPayHalfNHoliday (BigDecimal SupervisedPayHalfNHoliday);

	/** Get Pay Half National Holiday.
	  * Supervised Pay Half National Holiday
	  */
	public BigDecimal getSupervisedPayHalfNHoliday();

    /** Column name SupervisedPayHolidayOTPerHour */
    public static final String COLUMNNAME_SupervisedPayHolidayOTPerHour = "SupervisedPayHolidayOTPerHour";

	/** Set Pay Holiday OT Per Hour.
	  * Supervised Pay Holiday Over Time Per Hour
	  */
	public void setSupervisedPayHolidayOTPerHour (BigDecimal SupervisedPayHolidayOTPerHour);

	/** Get Pay Holiday OT Per Hour.
	  * Supervised Pay Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayHolidayOTPerHour();

    /** Column name SupervisedPayNHolidayOTPerHour */
    public static final String COLUMNNAME_SupervisedPayNHolidayOTPerHour = "SupervisedPayNHolidayOTPerHour";

	/** Set Pay N.Holiday OT Per Hour.
	  * Supervised Pay National Holiday Over Time Per Hour
	  */
	public void setSupervisedPayNHolidayOTPerHour (BigDecimal SupervisedPayNHolidayOTPerHour);

	/** Get Pay N.Holiday OT Per Hour.
	  * Supervised Pay National Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayNHolidayOTPerHour();

    /** Column name SupervisedPayOTPerHour */
    public static final String COLUMNNAME_SupervisedPayOTPerHour = "SupervisedPayOTPerHour";

	/** Set Pay Over Time Per Hour.
	  * Supervised Pay Over Time Per Hour
	  */
	public void setSupervisedPayOTPerHour (BigDecimal SupervisedPayOTPerHour);

	/** Get Pay Over Time Per Hour.
	  * Supervised Pay Over Time Per Hour
	  */
	public BigDecimal getSupervisedPayOTPerHour();

    /** Column name SupervisorInsentifPemborong */
    public static final String COLUMNNAME_SupervisorInsentifPemborong = "SupervisorInsentifPemborong";

	/** Set Supervisor Insentif Pemborong	  */
	public void setSupervisorInsentifPemborong (BigDecimal SupervisorInsentifPemborong);

	/** Get Supervisor Insentif Pemborong	  */
	public BigDecimal getSupervisorInsentifPemborong();

    /** Column name SupervisorPayFullDay */
    public static final String COLUMNNAME_SupervisorPayFullDay = "SupervisorPayFullDay";

	/** Set Pay Full Day.
	  * Supervisor Pay Full Day
	  */
	public void setSupervisorPayFullDay (BigDecimal SupervisorPayFullDay);

	/** Get Pay Full Day.
	  * Supervisor Pay Full Day
	  */
	public BigDecimal getSupervisorPayFullDay();

    /** Column name SupervisorPayFullHoliday */
    public static final String COLUMNNAME_SupervisorPayFullHoliday = "SupervisorPayFullHoliday";

	/** Set Pay Full Holiday.
	  * Supervisor Pay Full Holiday
	  */
	public void setSupervisorPayFullHoliday (BigDecimal SupervisorPayFullHoliday);

	/** Get Pay Full Holiday.
	  * Supervisor Pay Full Holiday
	  */
	public BigDecimal getSupervisorPayFullHoliday();

    /** Column name SupervisorPayFullNHoliday */
    public static final String COLUMNNAME_SupervisorPayFullNHoliday = "SupervisorPayFullNHoliday";

	/** Set Pay Full National Holiday.
	  * Supervisor Pay Full National Holiday
	  */
	public void setSupervisorPayFullNHoliday (BigDecimal SupervisorPayFullNHoliday);

	/** Get Pay Full National Holiday.
	  * Supervisor Pay Full National Holiday
	  */
	public BigDecimal getSupervisorPayFullNHoliday();

    /** Column name SupervisorPayHalfDay */
    public static final String COLUMNNAME_SupervisorPayHalfDay = "SupervisorPayHalfDay";

	/** Set Pay Half Day.
	  * Supervisor Pay Half Day
	  */
	public void setSupervisorPayHalfDay (BigDecimal SupervisorPayHalfDay);

	/** Get Pay Half Day.
	  * Supervisor Pay Half Day
	  */
	public BigDecimal getSupervisorPayHalfDay();

    /** Column name SupervisorPayHalfHoliday */
    public static final String COLUMNNAME_SupervisorPayHalfHoliday = "SupervisorPayHalfHoliday";

	/** Set Pay Half Holiday.
	  * Supervisor Pay Half Holiday
	  */
	public void setSupervisorPayHalfHoliday (BigDecimal SupervisorPayHalfHoliday);

	/** Get Pay Half Holiday.
	  * Supervisor Pay Half Holiday
	  */
	public BigDecimal getSupervisorPayHalfHoliday();

    /** Column name SupervisorPayHalfNHoliday */
    public static final String COLUMNNAME_SupervisorPayHalfNHoliday = "SupervisorPayHalfNHoliday";

	/** Set Pay Half National Holiday.
	  * Supervisor Pay Half National Holiday
	  */
	public void setSupervisorPayHalfNHoliday (BigDecimal SupervisorPayHalfNHoliday);

	/** Get Pay Half National Holiday.
	  * Supervisor Pay Half National Holiday
	  */
	public BigDecimal getSupervisorPayHalfNHoliday();

    /** Column name SupervisorPayHolidayOTPerHours */
    public static final String COLUMNNAME_SupervisorPayHolidayOTPerHours = "SupervisorPayHolidayOTPerHours";

	/** Set Pay Holiday OTPer Hour.
	  * Supervisor Pay Holiday Over Time Per Hour
	  */
	public void setSupervisorPayHolidayOTPerHours (BigDecimal SupervisorPayHolidayOTPerHours);

	/** Get Pay Holiday OTPer Hour.
	  * Supervisor Pay Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisorPayHolidayOTPerHours();

    /** Column name SupervisorPayNHolidayOTPerHour */
    public static final String COLUMNNAME_SupervisorPayNHolidayOTPerHour = "SupervisorPayNHolidayOTPerHour";

	/** Set Pay N. Holiday OT Per Hour.
	  * Supervisor Pay National Holiday Over Time Per Hour
	  */
	public void setSupervisorPayNHolidayOTPerHour (BigDecimal SupervisorPayNHolidayOTPerHour);

	/** Get Pay N. Holiday OT Per Hour.
	  * Supervisor Pay National Holiday Over Time Per Hour
	  */
	public BigDecimal getSupervisorPayNHolidayOTPerHour();

    /** Column name SupervisorPayOTPerhours */
    public static final String COLUMNNAME_SupervisorPayOTPerhours = "SupervisorPayOTPerhours";

	/** Set Pay Over Time Per Hour.
	  * Supervisor Pay Over Time Per Hours
	  */
	public void setSupervisorPayOTPerhours (BigDecimal SupervisorPayOTPerhours);

	/** Get Pay Over Time Per Hour.
	  * Supervisor Pay Over Time Per Hours
	  */
	public BigDecimal getSupervisorPayOTPerhours();

    /** Column name TargetPeriod */
    public static final String COLUMNNAME_TargetPeriod = "TargetPeriod";

	/** Set Supervised Period	  */
	public void setTargetPeriod (String TargetPeriod);

	/** Get Supervised Period	  */
	public String getTargetPeriod();

    /** Column name UNS_ProductionPayConfig_ID */
    public static final String COLUMNNAME_UNS_ProductionPayConfig_ID = "UNS_ProductionPayConfig_ID";

	/** Set Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID);

	/** Get Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID();

    /** Column name UNS_ProductionPayConfig_UU */
    public static final String COLUMNNAME_UNS_ProductionPayConfig_UU = "UNS_ProductionPayConfig_UU";

	/** Set UNS_ProductionPayConfig_UU	  */
	public void setUNS_ProductionPayConfig_UU (String UNS_ProductionPayConfig_UU);

	/** Get UNS_ProductionPayConfig_UU	  */
	public String getUNS_ProductionPayConfig_UU();

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
