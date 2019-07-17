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

/** Generated Interface for UNS_Handover_Inv
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Handover_Inv 
{

    /** TableName=UNS_Handover_Inv */
    public static final String Table_Name = "UNS_Handover_Inv";

    /** AD_Table_ID=1000205 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AcceptedBy */
    public static final String COLUMNNAME_AcceptedBy = "AcceptedBy";

	/** Set Accepted By.
	  * Person had accepted invoice at customer side
	  */
	public void setAcceptedBy (String AcceptedBy);

	/** Get Accepted By.
	  * Person had accepted invoice at customer side
	  */
	public String getAcceptedBy();

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name InvoiceCollectionType */
    public static final String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

	/** Set Collection Status.
	  * Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType);

	/** Get Collection Status.
	  * Invoice Collection Status
	  */
	public String getInvoiceCollectionType();

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

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

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

    /** Column name ReceiptDate */
    public static final String COLUMNNAME_ReceiptDate = "ReceiptDate";

	/** Set Receipt Date.
	  * Receipt Date
	  */
	public void setReceiptDate (Timestamp ReceiptDate);

	/** Get Receipt Date.
	  * Receipt Date
	  */
	public Timestamp getReceiptDate();

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

    /** Column name UNS_BillingLine_Result_ID */
    public static final String COLUMNNAME_UNS_BillingLine_Result_ID = "UNS_BillingLine_Result_ID";

	/** Set Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID);

	/** Get Invoice Result	  */
	public int getUNS_BillingLine_Result_ID();

	public com.unicore.model.I_UNS_BillingLine_Result getUNS_BillingLine_Result() throws RuntimeException;

    /** Column name UNS_Handover_Inv_ID */
    public static final String COLUMNNAME_UNS_Handover_Inv_ID = "UNS_Handover_Inv_ID";

	/** Set Handover Invoice	  */
	public void setUNS_Handover_Inv_ID (int UNS_Handover_Inv_ID);

	/** Get Handover Invoice	  */
	public int getUNS_Handover_Inv_ID();

    /** Column name UNS_Handover_Inv_UU */
    public static final String COLUMNNAME_UNS_Handover_Inv_UU = "UNS_Handover_Inv_UU";

	/** Set UNS_Handover_Inv_UU	  */
	public void setUNS_Handover_Inv_UU (String UNS_Handover_Inv_UU);

	/** Get UNS_Handover_Inv_UU	  */
	public String getUNS_Handover_Inv_UU();

    /** Column name UNS_PR_Allocation_ID */
    public static final String COLUMNNAME_UNS_PR_Allocation_ID = "UNS_PR_Allocation_ID";

	/** Set Billing Payment Allocation	  */
	public void setUNS_PR_Allocation_ID (int UNS_PR_Allocation_ID);

	/** Get Billing Payment Allocation	  */
	public int getUNS_PR_Allocation_ID();

	public com.unicore.model.I_UNS_PR_Allocation getUNS_PR_Allocation() throws RuntimeException;

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
