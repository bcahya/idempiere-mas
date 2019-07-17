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

/** Generated Interface for UNS_BillingLine_Giro
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_BillingLine_Giro 
{

    /** TableName=UNS_BillingLine_Giro */
    public static final String Table_Name = "UNS_BillingLine_Giro";

    /** AD_Table_ID=1000270 */
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

    /** Column name CreateCustomerBG */
    public static final String COLUMNNAME_CreateCustomerBG = "CreateCustomerBG";

	/** Set Create New Customer Giro	  */
	public void setCreateCustomerBG (String CreateCustomerBG);

	/** Get Create New Customer Giro	  */
	public String getCreateCustomerBG();

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

    /** Column name UNS_BillingLine_Giro_ID */
    public static final String COLUMNNAME_UNS_BillingLine_Giro_ID = "UNS_BillingLine_Giro_ID";

	/** Set Giro List	  */
	public void setUNS_BillingLine_Giro_ID (int UNS_BillingLine_Giro_ID);

	/** Get Giro List	  */
	public int getUNS_BillingLine_Giro_ID();

    /** Column name UNS_BillingLine_Giro_UU */
    public static final String COLUMNNAME_UNS_BillingLine_Giro_UU = "UNS_BillingLine_Giro_UU";

	/** Set UNS_BillingLine_Giro_UU	  */
	public void setUNS_BillingLine_Giro_UU (String UNS_BillingLine_Giro_UU);

	/** Get UNS_BillingLine_Giro_UU	  */
	public String getUNS_BillingLine_Giro_UU();

    /** Column name UNS_BillingLine_Result_ID */
    public static final String COLUMNNAME_UNS_BillingLine_Result_ID = "UNS_BillingLine_Result_ID";

	/** Set Invoice Result	  */
	public void setUNS_BillingLine_Result_ID (int UNS_BillingLine_Result_ID);

	/** Get Invoice Result	  */
	public int getUNS_BillingLine_Result_ID();

    /** Column name UNS_CustomerBG_ID */
    public static final String COLUMNNAME_UNS_CustomerBG_ID = "UNS_CustomerBG_ID";

	/** Set Customer Billed Giro	  */
	public void setUNS_CustomerBG_ID (int UNS_CustomerBG_ID);

	/** Get Customer Billed Giro	  */
	public int getUNS_CustomerBG_ID();

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
