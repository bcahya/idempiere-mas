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

/** Generated Interface for UNS_IncentiveConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_IncentiveConfig 
{

    /** TableName=UNS_IncentiveConfig */
    public static final String Table_Name = "UNS_IncentiveConfig";

    /** AD_Table_ID=1000366 */
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

    /** Column name IncentiveAmt */
    public static final String COLUMNNAME_IncentiveAmt = "IncentiveAmt";

	/** Set Incentive Amount	  */
	public void setIncentiveAmt (BigDecimal IncentiveAmt);

	/** Get Incentive Amount	  */
	public BigDecimal getIncentiveAmt();

    /** Column name IncentiveType */
    public static final String COLUMNNAME_IncentiveType = "IncentiveType";

	/** Set Incentive Type.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType);

	/** Get Incentive Type.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType();

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

	/** Set Incentive Amount.
	  * uncheck this check box if you will use percent
	  */
	public void setIsIncentiveAmount (boolean IsIncentiveAmount);

	/** Get Incentive Amount.
	  * uncheck this check box if you will use percent
	  */
	public boolean isIncentiveAmount();

    /** Column name IsUseBaseAmt */
    public static final String COLUMNNAME_IsUseBaseAmt = "IsUseBaseAmt";

	/** Set Use Base Amount	  */
	public void setIsUseBaseAmt (boolean IsUseBaseAmt);

	/** Get Use Base Amount	  */
	public boolean isUseBaseAmt();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name UNS_IncentiveConfig_ID */
    public static final String COLUMNNAME_UNS_IncentiveConfig_ID = "UNS_IncentiveConfig_ID";

	/** Set Incentive Configuration	  */
	public void setUNS_IncentiveConfig_ID (int UNS_IncentiveConfig_ID);

	/** Get Incentive Configuration	  */
	public int getUNS_IncentiveConfig_ID();

    /** Column name UNS_IncentiveConfig_UU */
    public static final String COLUMNNAME_UNS_IncentiveConfig_UU = "UNS_IncentiveConfig_UU";

	/** Set UNS_IncentiveConfig_UU	  */
	public void setUNS_IncentiveConfig_UU (String UNS_IncentiveConfig_UU);

	/** Get UNS_IncentiveConfig_UU	  */
	public String getUNS_IncentiveConfig_UU();

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
