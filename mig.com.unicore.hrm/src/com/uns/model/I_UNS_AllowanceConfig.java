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

/** Generated Interface for UNS_AllowanceConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.0
 */
@SuppressWarnings("all")
public interface I_UNS_AllowanceConfig 
{

    /** TableName=UNS_AllowanceConfig */
    public static final String Table_Name = "UNS_AllowanceConfig";

    /** AD_Table_ID=1000103 */
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

    /** Column name AllowanceBaseOnSallary */
    public static final String COLUMNNAME_AllowanceBaseOnSallary = "AllowanceBaseOnSallary";

	/** Set Allowance Base On Sallary	  */
	public void setAllowanceBaseOnSallary (boolean AllowanceBaseOnSallary);

	/** Get Allowance Base On Sallary	  */
	public boolean isAllowanceBaseOnSallary();

    /** Column name AllowanceMultiplier */
    public static final String COLUMNNAME_AllowanceMultiplier = "AllowanceMultiplier";

	/** Set Allowance Multiplier	  */
	public void setAllowanceMultiplier (BigDecimal AllowanceMultiplier);

	/** Get Allowance Multiplier	  */
	public BigDecimal getAllowanceMultiplier();

    /** Column name C_JobCategory_ID */
    public static final String COLUMNNAME_C_JobCategory_ID = "C_JobCategory_ID";

	/** Set Position Category.
	  * Job Position Category
	  */
	public void setC_JobCategory_ID (int C_JobCategory_ID);

	/** Get Position Category.
	  * Job Position Category
	  */
	public int getC_JobCategory_ID();

	public org.compiere.model.I_C_JobCategory getC_JobCategory() throws RuntimeException;

    /** Column name ContractType */
    public static final String COLUMNNAME_ContractType = "ContractType";

	/** Set Contract Type.
	  * Contract Type
	  */
	public void setContractType (String ContractType);

	/** Get Contract Type.
	  * Contract Type
	  */
	public String getContractType();

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

    /** Column name UNS_AllowanceConfig_ID */
    public static final String COLUMNNAME_UNS_AllowanceConfig_ID = "UNS_AllowanceConfig_ID";

	/** Set Allowance Configuration	  */
	public void setUNS_AllowanceConfig_ID (int UNS_AllowanceConfig_ID);

	/** Get Allowance Configuration	  */
	public int getUNS_AllowanceConfig_ID();

    /** Column name uns_allowanceconfig_uu */
    public static final String COLUMNNAME_uns_allowanceconfig_uu = "uns_allowanceconfig_uu";

	/** Set uns_allowanceconfig_uu	  */
	public void setuns_allowanceconfig_uu (String uns_allowanceconfig_uu);

	/** Get uns_allowanceconfig_uu	  */
	public String getuns_allowanceconfig_uu();

    /** Column name UNS_PayrollConfiguration_ID */
    public static final String COLUMNNAME_UNS_PayrollConfiguration_ID = "UNS_PayrollConfiguration_ID";

	/** Set Payroll Configuration	  */
	public void setUNS_PayrollConfiguration_ID (int UNS_PayrollConfiguration_ID);

	/** Get Payroll Configuration	  */
	public int getUNS_PayrollConfiguration_ID();

	public com.uns.model.I_UNS_PayrollConfiguration getUNS_PayrollConfiguration() throws RuntimeException;

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
