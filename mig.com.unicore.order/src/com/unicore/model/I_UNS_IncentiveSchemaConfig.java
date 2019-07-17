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

/** Generated Interface for UNS_IncentiveSchemaConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_IncentiveSchemaConfig 
{

    /** TableName=UNS_IncentiveSchemaConfig */
    public static final String Table_Name = "UNS_IncentiveSchemaConfig";

    /** AD_Table_ID=1000369 */
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

    /** Column name AgeFrom */
    public static final String COLUMNNAME_AgeFrom = "AgeFrom";

	/** Set Age From	  */
	public void setAgeFrom (int AgeFrom);

	/** Get Age From	  */
	public int getAgeFrom();

    /** Column name AgeTo */
    public static final String COLUMNNAME_AgeTo = "AgeTo";

	/** Set Age To	  */
	public void setAgeTo (int AgeTo);

	/** Get Age To	  */
	public int getAgeTo();

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

    /** Column name IsIncentiveAmount */
    public static final String COLUMNNAME_IsIncentiveAmount = "IsIncentiveAmount";

	/** Set Incentive Amount	  */
	public void setIsIncentiveAmount (boolean IsIncentiveAmount);

	/** Get Incentive Amount	  */
	public boolean isIncentiveAmount();

    /** Column name IsSupervisorContinuous */
    public static final String COLUMNNAME_IsSupervisorContinuous = "IsSupervisorContinuous";

	/** Set Supervisor Continuous	  */
	public void setIsSupervisorContinuous (boolean IsSupervisorContinuous);

	/** Get Supervisor Continuous	  */
	public boolean isSupervisorContinuous();

    /** Column name IsSupervisorReward */
    public static final String COLUMNNAME_IsSupervisorReward = "IsSupervisorReward";

	/** Set Supervisor Reward	  */
	public void setIsSupervisorReward (boolean IsSupervisorReward);

	/** Get Supervisor Reward	  */
	public boolean isSupervisorReward();

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

    /** Column name QtyMultiplier */
    public static final String COLUMNNAME_QtyMultiplier = "QtyMultiplier";

	/** Set Multiplier Quantity.
	  * Value to multiply quantities by for generating commissions.
	  */
	public void setQtyMultiplier (BigDecimal QtyMultiplier);

	/** Get Multiplier Quantity.
	  * Value to multiply quantities by for generating commissions.
	  */
	public BigDecimal getQtyMultiplier();

    /** Column name RangeFrom */
    public static final String COLUMNNAME_RangeFrom = "RangeFrom";

	/** Set Range From	  */
	public void setRangeFrom (BigDecimal RangeFrom);

	/** Get Range From	  */
	public BigDecimal getRangeFrom();

    /** Column name RangeTo */
    public static final String COLUMNNAME_RangeTo = "RangeTo";

	/** Set Range To	  */
	public void setRangeTo (BigDecimal RangeTo);

	/** Get Range To	  */
	public BigDecimal getRangeTo();

    /** Column name UNS_Incentive_ID */
    public static final String COLUMNNAME_UNS_Incentive_ID = "UNS_Incentive_ID";

	/** Set Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID);

	/** Get Incentive	  */
	public int getUNS_Incentive_ID();

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException;

    /** Column name UNS_IncentiveSchemaConfig_ID */
    public static final String COLUMNNAME_UNS_IncentiveSchemaConfig_ID = "UNS_IncentiveSchemaConfig_ID";

	/** Set Configuration	  */
	public void setUNS_IncentiveSchemaConfig_ID (int UNS_IncentiveSchemaConfig_ID);

	/** Get Configuration	  */
	public int getUNS_IncentiveSchemaConfig_ID();

    /** Column name UNS_IncentiveSchemaConfig_UU */
    public static final String COLUMNNAME_UNS_IncentiveSchemaConfig_UU = "UNS_IncentiveSchemaConfig_UU";

	/** Set UNS_IncentiveSchemaConfig_UU	  */
	public void setUNS_IncentiveSchemaConfig_UU (String UNS_IncentiveSchemaConfig_UU);

	/** Get UNS_IncentiveSchemaConfig_UU	  */
	public String getUNS_IncentiveSchemaConfig_UU();

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
