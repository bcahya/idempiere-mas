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

/** Generated Interface for UNS_AcvIncentiveByPeriod
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_AcvIncentiveByPeriod 
{

    /** TableName=UNS_AcvIncentiveByPeriod */
    public static final String Table_Name = "UNS_AcvIncentiveByPeriod";

    /** AD_Table_ID=1000150 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name InitialIncentive */
    public static final String COLUMNNAME_InitialIncentive = "InitialIncentive";

	/** Set Initial Incentive	  */
	public void setInitialIncentive (String InitialIncentive);

	/** Get Initial Incentive	  */
	public String getInitialIncentive();

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

    /** Column name UNS_AchievedIncentive_ID */
    public static final String COLUMNNAME_UNS_AchievedIncentive_ID = "UNS_AchievedIncentive_ID";

	/** Set Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID);

	/** Get Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID();

	public com.unicore.model.I_UNS_AchievedIncentive getUNS_AchievedIncentive() throws RuntimeException;

    /** Column name UNS_AcvIncentiveByPeriod_ID */
    public static final String COLUMNNAME_UNS_AcvIncentiveByPeriod_ID = "UNS_AcvIncentiveByPeriod_ID";

	/** Set Achieved Incentive By Period	  */
	public void setUNS_AcvIncentiveByPeriod_ID (int UNS_AcvIncentiveByPeriod_ID);

	/** Get Achieved Incentive By Period	  */
	public int getUNS_AcvIncentiveByPeriod_ID();

    /** Column name UNS_AcvIncentiveByPeriod_UU */
    public static final String COLUMNNAME_UNS_AcvIncentiveByPeriod_UU = "UNS_AcvIncentiveByPeriod_UU";

	/** Set UNS_AcvIncentiveByPeriod_UU	  */
	public void setUNS_AcvIncentiveByPeriod_UU (String UNS_AcvIncentiveByPeriod_UU);

	/** Get UNS_AcvIncentiveByPeriod_UU	  */
	public String getUNS_AcvIncentiveByPeriod_UU();

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
