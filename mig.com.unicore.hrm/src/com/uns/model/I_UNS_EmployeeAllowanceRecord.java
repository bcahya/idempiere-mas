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

/** Generated Interface for UNS_EmployeeAllowanceRecord
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_EmployeeAllowanceRecord 
{

    /** TableName=UNS_EmployeeAllowanceRecord */
    public static final String Table_Name = "UNS_EmployeeAllowanceRecord";

    /** AD_Table_ID=1000396 */
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

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name LeaveClaimReserved */
    public static final String COLUMNNAME_LeaveClaimReserved = "LeaveClaimReserved";

	/** Set Leave Claim Reserved	  */
	public void setLeaveClaimReserved (BigDecimal LeaveClaimReserved);

	/** Get Leave Claim Reserved	  */
	public BigDecimal getLeaveClaimReserved();

    /** Column name LeaveReservedUsed */
    public static final String COLUMNNAME_LeaveReservedUsed = "LeaveReservedUsed";

	/** Set Leave Reserved Used.
	  * The amount of Leave claim reserved used by employee
	  */
	public void setLeaveReservedUsed (BigDecimal LeaveReservedUsed);

	/** Get Leave Reserved Used.
	  * The amount of Leave claim reserved used by employee
	  */
	public BigDecimal getLeaveReservedUsed();

    /** Column name MedicalAllowance */
    public static final String COLUMNNAME_MedicalAllowance = "MedicalAllowance";

	/** Set Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance);

	/** Get Medical Allowance.
	  * The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance();

    /** Column name MedicalAllowanceUsed */
    public static final String COLUMNNAME_MedicalAllowanceUsed = "MedicalAllowanceUsed";

	/** Set Medical Allowance Used	  */
	public void setMedicalAllowanceUsed (BigDecimal MedicalAllowanceUsed);

	/** Get Medical Allowance Used	  */
	public BigDecimal getMedicalAllowanceUsed();

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

    /** Column name PeriodDateEnd */
    public static final String COLUMNNAME_PeriodDateEnd = "PeriodDateEnd";

	/** Set Period Date End	  */
	public void setPeriodDateEnd (Timestamp PeriodDateEnd);

	/** Get Period Date End	  */
	public Timestamp getPeriodDateEnd();

    /** Column name PeriodDateStart */
    public static final String COLUMNNAME_PeriodDateStart = "PeriodDateStart";

	/** Set Period Date Start	  */
	public void setPeriodDateStart (Timestamp PeriodDateStart);

	/** Get Period Date Start	  */
	public Timestamp getPeriodDateStart();

    /** Column name RemainingAmt */
    public static final String COLUMNNAME_RemainingAmt = "RemainingAmt";

	/** Set Remaining Amt.
	  * Remaining Amount
	  */
	public void setRemainingAmt (BigDecimal RemainingAmt);

	/** Get Remaining Amt.
	  * Remaining Amount
	  */
	public BigDecimal getRemainingAmt();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name UNS_Contract_Recommendation_ID */
    public static final String COLUMNNAME_UNS_Contract_Recommendation_ID = "UNS_Contract_Recommendation_ID";

	/** Set Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID);

	/** Get Contract	  */
	public int getUNS_Contract_Recommendation_ID();

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException;

    /** Column name UNS_EmployeeAllowanceRecord_ID */
    public static final String COLUMNNAME_UNS_EmployeeAllowanceRecord_ID = "UNS_EmployeeAllowanceRecord_ID";

	/** Set Employee Allowance Record	  */
	public void setUNS_EmployeeAllowanceRecord_ID (int UNS_EmployeeAllowanceRecord_ID);

	/** Get Employee Allowance Record	  */
	public int getUNS_EmployeeAllowanceRecord_ID();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

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

    /** Column name uns_employeeallowancerecord_uu */
    public static final String COLUMNNAME_uns_employeeallowancerecord_uu = "uns_employeeallowancerecord_uu";

	/** Set uns_employeeallowancerecord_uu	  */
	public void setuns_employeeallowancerecord_uu (String uns_employeeallowancerecord_uu);

	/** Get uns_employeeallowancerecord_uu	  */
	public String getuns_employeeallowancerecord_uu();
}
