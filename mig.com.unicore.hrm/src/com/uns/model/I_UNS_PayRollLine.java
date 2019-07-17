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

/** Generated Interface for UNS_PayRollLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PayRollLine 
{

    /** TableName=UNS_PayRollLine */
    public static final String Table_Name = "UNS_PayRollLine";

    /** AD_Table_ID=1000072 */
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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name CriteriaType */
    public static final String COLUMNNAME_CriteriaType = "CriteriaType";

	/** Set Criteria Type	  */
	public void setCriteriaType (String CriteriaType);

	/** Get Criteria Type	  */
	public String getCriteriaType();

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

    /** Column name IsGradualCalculation */
    public static final String COLUMNNAME_IsGradualCalculation = "IsGradualCalculation";

	/** Set Is Gradual Calculation.
	  * Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public void setIsGradualCalculation (boolean IsGradualCalculation);

	/** Get Is Gradual Calculation.
	  * Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public boolean isGradualCalculation();

    /** Column name IsSwitchTodaily */
    public static final String COLUMNNAME_IsSwitchTodaily = "IsSwitchTodaily";

	/** Set Switched To Daily.
	  * If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public void setIsSwitchTodaily (boolean IsSwitchTodaily);

	/** Get Switched To Daily.
	  * If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public boolean isSwitchTodaily();

    /** Column name IsTargetBased */
    public static final String COLUMNNAME_IsTargetBased = "IsTargetBased";

	/** Set Target Based	  */
	public void setIsTargetBased (boolean IsTargetBased);

	/** Get Target Based	  */
	public boolean isTargetBased();

    /** Column name IsTotalResultTarget */
    public static final String COLUMNNAME_IsTotalResultTarget = "IsTotalResultTarget";

	/** Set Is Total Result Target.
	  * To indicate that the target defined is based on the product total result of the production.
	  */
	public void setIsTotalResultTarget (boolean IsTotalResultTarget);

	/** Get Is Total Result Target.
	  * To indicate that the target defined is based on the product total result of the production.
	  */
	public boolean isTotalResultTarget();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

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
	  * The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive);

	/** Get No-Work Day Incentive.
	  * The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive();

    /** Column name Pay */
    public static final String COLUMNNAME_Pay = "Pay";

	/** Set Pay	  */
	public void setPay (BigDecimal Pay);

	/** Get Pay	  */
	public BigDecimal getPay();

    /** Column name PayFullDay */
    public static final String COLUMNNAME_PayFullDay = "PayFullDay";

	/** Set Pay Full Day.
	  * Normal daily work-day payment for full day presence
	  */
	public void setPayFullDay (BigDecimal PayFullDay);

	/** Get Pay Full Day.
	  * Normal daily work-day payment for full day presence
	  */
	public BigDecimal getPayFullDay();

    /** Column name PayHalfDay */
    public static final String COLUMNNAME_PayHalfDay = "PayHalfDay";

	/** Set Pay Half Day.
	  * Normal daily work-day payment for half day presence
	  */
	public void setPayHalfDay (BigDecimal PayHalfDay);

	/** Get Pay Half Day.
	  * Normal daily work-day payment for half day presence
	  */
	public BigDecimal getPayHalfDay();

    /** Column name PayHolidayFullDay */
    public static final String COLUMNNAME_PayHolidayFullDay = "PayHolidayFullDay";

	/** Set Pay Holiday Full Day.
	  * Holiday payment for full day presence
	  */
	public void setPayHolidayFullDay (BigDecimal PayHolidayFullDay);

	/** Get Pay Holiday Full Day.
	  * Holiday payment for full day presence
	  */
	public BigDecimal getPayHolidayFullDay();

    /** Column name PayHolidayHalfDay */
    public static final String COLUMNNAME_PayHolidayHalfDay = "PayHolidayHalfDay";

	/** Set Pay Holiday Half Day.
	  * Holiday payment for half day presence
	  */
	public void setPayHolidayHalfDay (BigDecimal PayHolidayHalfDay);

	/** Get Pay Holiday Half Day.
	  * Holiday payment for half day presence
	  */
	public BigDecimal getPayHolidayHalfDay();

    /** Column name PayHolidayOTPerHour */
    public static final String COLUMNNAME_PayHolidayOTPerHour = "PayHolidayOTPerHour";

	/** Set Pay  Holiday OT PerHour.
	  * Holiday payment per over time hours presence
	  */
	public void setPayHolidayOTPerHour (BigDecimal PayHolidayOTPerHour);

	/** Get Pay  Holiday OT PerHour.
	  * Holiday payment per over time hours presence
	  */
	public BigDecimal getPayHolidayOTPerHour();

    /** Column name PayNHolidayFullDay */
    public static final String COLUMNNAME_PayNHolidayFullDay = "PayNHolidayFullDay";

	/** Set Pay N.Holiday Full Day.
	  * National holiday payment for full day presence
	  */
	public void setPayNHolidayFullDay (BigDecimal PayNHolidayFullDay);

	/** Get Pay N.Holiday Full Day.
	  * National holiday payment for full day presence
	  */
	public BigDecimal getPayNHolidayFullDay();

    /** Column name PayNHolidayHalfDay */
    public static final String COLUMNNAME_PayNHolidayHalfDay = "PayNHolidayHalfDay";

	/** Set Pay N.Holiday Half Day.
	  * National Holiday payment for half day presence
	  */
	public void setPayNHolidayHalfDay (BigDecimal PayNHolidayHalfDay);

	/** Get Pay N.Holiday Half Day.
	  * National Holiday payment for half day presence
	  */
	public BigDecimal getPayNHolidayHalfDay();

    /** Column name PayNHolidayOTPerHour */
    public static final String COLUMNNAME_PayNHolidayOTPerHour = "PayNHolidayOTPerHour";

	/** Set Pay  N.Holiday OT PerHour.
	  * National payment per overtime hours presence
	  */
	public void setPayNHolidayOTPerHour (BigDecimal PayNHolidayOTPerHour);

	/** Get Pay  N.Holiday OT PerHour.
	  * National payment per overtime hours presence
	  */
	public BigDecimal getPayNHolidayOTPerHour();

    /** Column name PayNationalHoliday */
    public static final String COLUMNNAME_PayNationalHoliday = "PayNationalHoliday";

	/** Set Pay National Holiday.
	  * Payment amount on national holiday work-result
	  */
	public void setPayNationalHoliday (BigDecimal PayNationalHoliday);

	/** Get Pay National Holiday.
	  * Payment amount on national holiday work-result
	  */
	public BigDecimal getPayNationalHoliday();

    /** Column name PayOverTime */
    public static final String COLUMNNAME_PayOverTime = "PayOverTime";

	/** Set Pay Over Time.
	  * Overtime Payment amount on normal day
	  */
	public void setPayOverTime (BigDecimal PayOverTime);

	/** Get Pay Over Time.
	  * Overtime Payment amount on normal day
	  */
	public BigDecimal getPayOverTime();

    /** Column name PaySunday */
    public static final String COLUMNNAME_PaySunday = "PaySunday";

	/** Set Pay Holiday.
	  * Payment amount on holiday work-result
	  */
	public void setPaySunday (BigDecimal PaySunday);

	/** Get Pay Holiday.
	  * Payment amount on holiday work-result
	  */
	public BigDecimal getPaySunday();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name PremiAchivementRecognized */
    public static final String COLUMNNAME_PremiAchivementRecognized = "PremiAchivementRecognized";

	/** Set Premi Achivement Recognized	  */
	public void setPremiAchivementRecognized (BigDecimal PremiAchivementRecognized);

	/** Get Premi Achivement Recognized	  */
	public BigDecimal getPremiAchivementRecognized();

    /** Column name PremiCalculateBased */
    public static final String COLUMNNAME_PremiCalculateBased = "PremiCalculateBased";

	/** Set Premi Calculate Based	  */
	public void setPremiCalculateBased (String PremiCalculateBased);

	/** Get Premi Calculate Based	  */
	public String getPremiCalculateBased();

    /** Column name StdLaborNumber */
    public static final String COLUMNNAME_StdLaborNumber = "StdLaborNumber";

	/** Set Std. Number of Labor.
	  * The standard expected number of labor involved during production.
	  */
	public void setStdLaborNumber (int StdLaborNumber);

	/** Get Std. Number of Labor.
	  * The standard expected number of labor involved during production.
	  */
	public int getStdLaborNumber();

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

    /** Column name UNS_PayCriteria_ID */
    public static final String COLUMNNAME_UNS_PayCriteria_ID = "UNS_PayCriteria_ID";

	/** Set Pay Criteria	  */
	public void setUNS_PayCriteria_ID (int UNS_PayCriteria_ID);

	/** Get Pay Criteria	  */
	public int getUNS_PayCriteria_ID();

	public com.uns.model.I_UNS_PayCriteria getUNS_PayCriteria() throws RuntimeException;

    /** Column name UNS_PayRollLine_ID */
    public static final String COLUMNNAME_UNS_PayRollLine_ID = "UNS_PayRollLine_ID";

	/** Set Pay Roll Line	  */
	public void setUNS_PayRollLine_ID (int UNS_PayRollLine_ID);

	/** Get Pay Roll Line	  */
	public int getUNS_PayRollLine_ID();

    /** Column name UNS_PayRollLine_UU */
    public static final String COLUMNNAME_UNS_PayRollLine_UU = "UNS_PayRollLine_UU";

	/** Set UNS_PayRollLine_UU	  */
	public void setUNS_PayRollLine_UU (String UNS_PayRollLine_UU);

	/** Get UNS_PayRollLine_UU	  */
	public String getUNS_PayRollLine_UU();

    /** Column name UNS_ProductionPayConfig_ID */
    public static final String COLUMNNAME_UNS_ProductionPayConfig_ID = "UNS_ProductionPayConfig_ID";

	/** Set Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID);

	/** Get Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID();

	public com.uns.model.I_UNS_ProductionPayConfig getUNS_ProductionPayConfig() throws RuntimeException;

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
