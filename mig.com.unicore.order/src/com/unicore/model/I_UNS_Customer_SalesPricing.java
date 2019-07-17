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

/** Generated Interface for UNS_Customer_SalesPricing
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Customer_SalesPricing 
{

    /** TableName=UNS_Customer_SalesPricing */
    public static final String Table_Name = "UNS_Customer_SalesPricing";

    /** AD_Table_ID=1000146 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set Salesman.
	  * The salesman to whom the customer's linked to.
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get Salesman.
	  * The salesman to whom the customer's linked to.
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

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

    /** Column name CreditLimit */
    public static final String COLUMNNAME_CreditLimit = "CreditLimit";

	/** Set Credit limit.
	  * Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit);

	/** Get Credit limit.
	  * Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit();

    /** Column name InvoiceCountLimit */
    public static final String COLUMNNAME_InvoiceCountLimit = "InvoiceCountLimit";

	/** Set Invoice Count Limit.
	  * The limitation to the business partner's number of invoice(s) of a product/category
	  */
	public void setInvoiceCountLimit (int InvoiceCountLimit);

	/** Get Invoice Count Limit.
	  * The limitation to the business partner's number of invoice(s) of a product/category
	  */
	public int getInvoiceCountLimit();

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

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name PaymentTerm */
    public static final String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/** Set Payment Term.
	  * Payment Term
	  */
	public void setPaymentTerm (int PaymentTerm);

	/** Get Payment Term.
	  * Payment Term
	  */
	public int getPaymentTerm();

    /** Column name UNS_Customer_SalesPricing_ID */
    public static final String COLUMNNAME_UNS_Customer_SalesPricing_ID = "UNS_Customer_SalesPricing_ID";

	/** Set Customer Sales Pricing	  */
	public void setUNS_Customer_SalesPricing_ID (int UNS_Customer_SalesPricing_ID);

	/** Get Customer Sales Pricing	  */
	public int getUNS_Customer_SalesPricing_ID();

    /** Column name UNS_Customer_SalesPricing_UU */
    public static final String COLUMNNAME_UNS_Customer_SalesPricing_UU = "UNS_Customer_SalesPricing_UU";

	/** Set UNS_Customer_SalesPricing_UU	  */
	public void setUNS_Customer_SalesPricing_UU (String UNS_Customer_SalesPricing_UU);

	/** Get UNS_Customer_SalesPricing_UU	  */
	public String getUNS_Customer_SalesPricing_UU();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

	public com.unicore.model.I_UNS_Rayon getUNS_Rayon() throws RuntimeException;

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
