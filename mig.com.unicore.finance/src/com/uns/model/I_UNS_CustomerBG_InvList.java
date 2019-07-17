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

import com.unicore.model.I_UNS_BillingLine_Result;

/** Generated Interface for UNS_CustomerBG_InvList
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CustomerBG_InvList 
{

    /** TableName=UNS_CustomerBG_InvList */
    public static final String Table_Name = "UNS_CustomerBG_InvList";

    /** AD_Table_ID=1000203 */
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

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_PaymentAllocate_ID */
    public static final String COLUMNNAME_C_PaymentAllocate_ID = "C_PaymentAllocate_ID";

	/** Set Allocate Payment.
	  * Allocate Payment to Invoices
	  */
	public void setC_PaymentAllocate_ID (int C_PaymentAllocate_ID);

	/** Get Allocate Payment.
	  * Allocate Payment to Invoices
	  */
	public int getC_PaymentAllocate_ID();

	public org.compiere.model.I_C_PaymentAllocate getC_PaymentAllocate() throws RuntimeException;

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

    /** Column name NetAmtToInvoice */
    public static final String COLUMNNAME_NetAmtToInvoice = "NetAmtToInvoice";

	/** Set Invoice net Amount.
	  * Net amount of this Invoice
	  */
	public void setNetAmtToInvoice (BigDecimal NetAmtToInvoice);

	/** Get Invoice net Amount.
	  * Net amount of this Invoice
	  */
	public BigDecimal getNetAmtToInvoice();

    /** Column name PaidAmt */
    public static final String COLUMNNAME_PaidAmt = "PaidAmt";

	/** Set Paid Amount	  */
	public void setPaidAmt (BigDecimal PaidAmt);

	/** Get Paid Amount	  */
	public BigDecimal getPaidAmt();

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

    /** Column name UNS_BillingLine_Result_ID */
    public static final String COLUMNNAME_UNS_BillingLine_Result_ID = "UNS_BillingLine_Result_ID";

	/** Set Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID);

	/** Get Invoice Result	  */
	public int getUNS_BillingLine_Result_ID();

	public com.unicore.model.I_UNS_BillingLine_Result getUNS_BillingLine_Result() throws RuntimeException;

    /** Column name UNS_CustomerBG_ID */
    public static final String COLUMNNAME_UNS_CustomerBG_ID = "UNS_CustomerBG_ID";

	/** Set Customer Billed Giro	  */
	public void setUNS_CustomerBG_ID (int UNS_CustomerBG_ID);

	/** Get Customer Billed Giro	  */
	public int getUNS_CustomerBG_ID();

	public com.uns.model.I_UNS_CustomerBG getUNS_CustomerBG() throws RuntimeException;

    /** Column name UNS_CustomerBG_InvList_ID */
    public static final String COLUMNNAME_UNS_CustomerBG_InvList_ID = "UNS_CustomerBG_InvList_ID";

	/** Set Giro Invoice List	  */
	public void setUNS_CustomerBG_InvList_ID (int UNS_CustomerBG_InvList_ID);

	/** Get Giro Invoice List	  */
	public int getUNS_CustomerBG_InvList_ID();

    /** Column name UNS_CustomerBG_InvList_UU */
    public static final String COLUMNNAME_UNS_CustomerBG_InvList_UU = "UNS_CustomerBG_InvList_UU";

	/** Set UNS_CustomerBG_InvList_UU	  */
	public void setUNS_CustomerBG_InvList_UU (String UNS_CustomerBG_InvList_UU);

	/** Get UNS_CustomerBG_InvList_UU	  */
	public String getUNS_CustomerBG_InvList_UU();

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
