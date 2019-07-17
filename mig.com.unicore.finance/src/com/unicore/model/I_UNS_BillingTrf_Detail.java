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

/** Generated Interface for UNS_BillingTrf_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_BillingTrf_Detail 
{

    /** TableName=UNS_BillingTrf_Detail */
    public static final String Table_Name = "UNS_BillingTrf_Detail";

    /** AD_Table_ID=1000285 */
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

    /** Column name AmountTrf */
    public static final String COLUMNNAME_AmountTrf = "AmountTrf";

	/** Set Amount Transferred	  */
	public void setAmountTrf (BigDecimal AmountTrf);

	/** Get Amount Transferred	  */
	public BigDecimal getAmountTrf();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

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

    /** Column name UNS_BillingGroup_ID */
    public static final String COLUMNNAME_UNS_BillingGroup_ID = "UNS_BillingGroup_ID";

	/** Set Grouping Billing.
	  * Grouping Billing by parameter
	  */
	public void setUNS_BillingGroup_ID (int UNS_BillingGroup_ID);

	/** Get Grouping Billing.
	  * Grouping Billing by parameter
	  */
	public int getUNS_BillingGroup_ID();

    /** Column name UNS_BillingTrf_Detail_ID */
    public static final String COLUMNNAME_UNS_BillingTrf_Detail_ID = "UNS_BillingTrf_Detail_ID";

	/** Set Billing Transfer Detail	  */
	public void setUNS_BillingTrf_Detail_ID (int UNS_BillingTrf_Detail_ID);

	/** Get Billing Transfer Detail	  */
	public int getUNS_BillingTrf_Detail_ID();

    /** Column name UNS_BillingTrf_Detail_UU */
    public static final String COLUMNNAME_UNS_BillingTrf_Detail_UU = "UNS_BillingTrf_Detail_UU";

	/** Set UNS_BillingTrf_Detail_UU	  */
	public void setUNS_BillingTrf_Detail_UU (String UNS_BillingTrf_Detail_UU);

	/** Get UNS_BillingTrf_Detail_UU	  */
	public String getUNS_BillingTrf_Detail_UU();

    /** Column name UNS_BillingTrfValidation_ID */
    public static final String COLUMNNAME_UNS_BillingTrfValidation_ID = "UNS_BillingTrfValidation_ID";

	/** Set Billing Transfer Validation	  */
	public void setUNS_BillingTrfValidation_ID (int UNS_BillingTrfValidation_ID);

	/** Get Billing Transfer Validation	  */
	public int getUNS_BillingTrfValidation_ID();

	public com.unicore.model.I_UNS_BillingTrfValidation getUNS_BillingTrfValidation() throws RuntimeException;

    /** Column name UNS_PaymentReceipt_ID */
    public static final String COLUMNNAME_UNS_PaymentReceipt_ID = "UNS_PaymentReceipt_ID";

	/** Set Billing Payment Receipt	  */
	public void setUNS_PaymentReceipt_ID (int UNS_PaymentReceipt_ID);

	/** Get Billing Payment Receipt	  */
	public int getUNS_PaymentReceipt_ID();

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
