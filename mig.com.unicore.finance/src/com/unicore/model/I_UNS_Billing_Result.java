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

/** Generated Interface for UNS_Billing_Result
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Billing_Result 
{

    /** TableName=UNS_Billing_Result */
    public static final String Table_Name = "UNS_Billing_Result";

    /** AD_Table_ID=1000197 */
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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

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

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount	  */
	public BigDecimal getPaidAmt();

    /** Column name PaidAmtGiro */
    public static final String COLUMNNAME_PaidAmtGiro = "PaidAmtGiro";

	/** Set Paid Amt By Giro.
	  * Paid Amount by Giro
	  */
	public void setPaidAmtGiro (BigDecimal PaidAmtGiro);

	/** Get Paid Amt By Giro.
	  * Paid Amount by Giro
	  */
	public BigDecimal getPaidAmtGiro();

    /** Column name ReceiptAmt */
    public static final String COLUMNNAME_ReceiptAmt = "ReceiptAmt";

	/** Set Receipt Amount	  */
	public void setReceiptAmt (BigDecimal ReceiptAmt);

	/** Get Receipt Amount	  */
	public BigDecimal getReceiptAmt();

    /** Column name ReceiptAmtGiro */
    public static final String COLUMNNAME_ReceiptAmtGiro = "ReceiptAmtGiro";

	/** Set Receipt Amt By Giro.
	  * Receipt Amount By Giro
	  */
	public void setReceiptAmtGiro (BigDecimal ReceiptAmtGiro);

	/** Get Receipt Amt By Giro.
	  * Receipt Amount By Giro
	  */
	public BigDecimal getReceiptAmtGiro();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name UNS_BillingGroup_Result_ID */
    public static final String COLUMNNAME_UNS_BillingGroup_Result_ID = "UNS_BillingGroup_Result_ID";

	/** Set Grouping Billing Result	  */
	public void setUNS_BillingGroup_Result_ID (int UNS_BillingGroup_Result_ID);

	/** Get Grouping Billing Result	  */
	public int getUNS_BillingGroup_Result_ID();

	public com.unicore.model.I_UNS_BillingGroup_Result getUNS_BillingGroup_Result() throws RuntimeException;

    /** Column name UNS_Billing_ID */
    public static final String COLUMNNAME_UNS_Billing_ID = "UNS_Billing_ID";

	/** Set Billing	  */
	public void setUNS_Billing_ID (int UNS_Billing_ID);

	/** Get Billing	  */
	public int getUNS_Billing_ID();

	public com.uns.model.I_UNS_Billing getUNS_Billing() throws RuntimeException;

    /** Column name UNS_Billing_Result_ID */
    public static final String COLUMNNAME_UNS_Billing_Result_ID = "UNS_Billing_Result_ID";

	/** Set Customer Billing	  */
	public void setUNS_Billing_Result_ID (int UNS_Billing_Result_ID);

	/** Get Customer Billing	  */
	public int getUNS_Billing_Result_ID();

    /** Column name UNS_Billing_Result_UU */
    public static final String COLUMNNAME_UNS_Billing_Result_UU = "UNS_Billing_Result_UU";

	/** Set UNS_Billing_Result_UU	  */
	public void setUNS_Billing_Result_UU (String UNS_Billing_Result_UU);

	/** Get UNS_Billing_Result_UU	  */
	public String getUNS_Billing_Result_UU();

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