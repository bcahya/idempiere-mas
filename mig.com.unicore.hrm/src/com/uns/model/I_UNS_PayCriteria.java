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

/** Generated Interface for UNS_PayCriteria
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PayCriteria 
{

    /** TableName=UNS_PayCriteria */
    public static final String Table_Name = "UNS_PayCriteria";

    /** AD_Table_ID=1000051 */
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

    /** Column name Code */
    public static final String COLUMNNAME_Code = "Code";

	/** Set Validation code.
	  * Validation Code
	  */
	public void setCode (String Code);

	/** Get Validation code.
	  * Validation Code
	  */
	public String getCode();

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

    /** Column name UNS_PayCriteria_ID */
    public static final String COLUMNNAME_UNS_PayCriteria_ID = "UNS_PayCriteria_ID";

	/** Set Pay Criteria	  */
	public void setUNS_PayCriteria_ID (int UNS_PayCriteria_ID);

	/** Get Pay Criteria	  */
	public int getUNS_PayCriteria_ID();

    /** Column name UNS_PayCriteria_UU */
    public static final String COLUMNNAME_UNS_PayCriteria_UU = "UNS_PayCriteria_UU";

	/** Set UNS_PayCriteria_UU	  */
	public void setUNS_PayCriteria_UU (String UNS_PayCriteria_UU);

	/** Get UNS_PayCriteria_UU	  */
	public String getUNS_PayCriteria_UU();

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
