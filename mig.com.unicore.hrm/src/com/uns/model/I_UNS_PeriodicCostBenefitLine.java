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

/** Generated Interface for UNS_PeriodicCostBenefitLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PeriodicCostBenefitLine 
{

    /** TableName=UNS_PeriodicCostBenefitLine */
    public static final String Table_Name = "UNS_PeriodicCostBenefitLine";

    /** AD_Table_ID=1000339 */
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

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount	  */
	public BigDecimal getPaidAmt();

    /** Column name PrevAmount */
    public static final String COLUMNNAME_PrevAmount = "PrevAmount";

	/** Set Previous Amount	  */
	public void setPrevAmount (BigDecimal PrevAmount);

	/** Get Previous Amount	  */
	public BigDecimal getPrevAmount();

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

    /** Column name RemainingAmount */
    public static final String COLUMNNAME_RemainingAmount = "RemainingAmount";

	/** Set Remaining Amount	  */
	public void setRemainingAmount (BigDecimal RemainingAmount);

	/** Get Remaining Amount	  */
	public BigDecimal getRemainingAmount();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_PeriodicCostBenefit_ID */
    public static final String COLUMNNAME_UNS_PeriodicCostBenefit_ID = "UNS_PeriodicCostBenefit_ID";

	/** Set Periodic Cost And Benefit	  */
	public void setUNS_PeriodicCostBenefit_ID (int UNS_PeriodicCostBenefit_ID);

	/** Get Periodic Cost And Benefit	  */
	public int getUNS_PeriodicCostBenefit_ID();

	public com.uns.model.I_UNS_PeriodicCostBenefit getUNS_PeriodicCostBenefit() throws RuntimeException;

    /** Column name UNS_PeriodicCostBenefitLine_ID */
    public static final String COLUMNNAME_UNS_PeriodicCostBenefitLine_ID = "UNS_PeriodicCostBenefitLine_ID";

	/** Set Detail	  */
	public void setUNS_PeriodicCostBenefitLine_ID (int UNS_PeriodicCostBenefitLine_ID);

	/** Get Detail	  */
	public int getUNS_PeriodicCostBenefitLine_ID();

    /** Column name UNS_PeriodicCostBenefitLine_UU */
    public static final String COLUMNNAME_UNS_PeriodicCostBenefitLine_UU = "UNS_PeriodicCostBenefitLine_UU";

	/** Set UNS_PeriodicCostBenefitLine_UU	  */
	public void setUNS_PeriodicCostBenefitLine_UU (String UNS_PeriodicCostBenefitLine_UU);

	/** Get UNS_PeriodicCostBenefitLine_UU	  */
	public String getUNS_PeriodicCostBenefitLine_UU();

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
