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

/** Generated Interface for UNS_Charge_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Charge_Detail 
{

    /** TableName=UNS_Charge_Detail */
    public static final String Table_Name = "UNS_Charge_Detail";

    /** AD_Table_ID=1000266 */
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

    /** Column name AmountConfirmed */
    public static final String COLUMNNAME_AmountConfirmed = "AmountConfirmed";

	/** Set Confirmed Amount	  */
	public void setAmountConfirmed (BigDecimal AmountConfirmed);

	/** Get Confirmed Amount	  */
	public BigDecimal getAmountConfirmed();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/** Set Bank statement line.
	  * Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/** Get Bank statement line.
	  * Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID();

	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine() throws RuntimeException;

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

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name ChargeType */
    public static final String COLUMNNAME_ChargeType = "ChargeType";

	/** Set Charge Type	  */
	public void setChargeType (String ChargeType);

	/** Get Charge Type	  */
	public String getChargeType();

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

    /** Column name IsCancelled */
    public static final String COLUMNNAME_IsCancelled = "IsCancelled";

	/** Set Cancelled.
	  * The transaction was cancelled
	  */
	public void setIsCancelled (boolean IsCancelled);

	/** Get Cancelled.
	  * The transaction was cancelled
	  */
	public boolean isCancelled();

    /** Column name IsFuel */
    public static final String COLUMNNAME_IsFuel = "IsFuel";

	/** Set Fuel	  */
	public void setIsFuel (boolean IsFuel);

	/** Get Fuel	  */
	public boolean isFuel();

    /** Column name Liters */
    public static final String COLUMNNAME_Liters = "Liters";

	/** Set Liters	  */
	public void setLiters (BigDecimal Liters);

	/** Get Liters	  */
	public BigDecimal getLiters();

    /** Column name Reference_ID */
    public static final String COLUMNNAME_Reference_ID = "Reference_ID";

	/** Set Refrerence Record	  */
	public void setReference_ID (int Reference_ID);

	/** Get Refrerence Record	  */
	public int getReference_ID();

	public com.uns.model.I_UNS_Charge_Detail getReference() throws RuntimeException;

    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/** Set Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID);

	/** Get Reversal Line.
	  * Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID();

	public com.uns.model.I_UNS_Charge_Detail getReversalLine() throws RuntimeException;

    /** Column name UNS_Charge_Confirmation_ID */
    public static final String COLUMNNAME_UNS_Charge_Confirmation_ID = "UNS_Charge_Confirmation_ID";

	/** Set Charge Confirmation	  */
	public void setUNS_Charge_Confirmation_ID (int UNS_Charge_Confirmation_ID);

	/** Get Charge Confirmation	  */
	public int getUNS_Charge_Confirmation_ID();

	public com.uns.model.I_UNS_Charge_Confirmation getUNS_Charge_Confirmation() throws RuntimeException;

    /** Column name UNS_Charge_Detail_ID */
    public static final String COLUMNNAME_UNS_Charge_Detail_ID = "UNS_Charge_Detail_ID";

	/** Set Charge Detail	  */
	public void setUNS_Charge_Detail_ID (int UNS_Charge_Detail_ID);

	/** Get Charge Detail	  */
	public int getUNS_Charge_Detail_ID();

    /** Column name UNS_Charge_Detail_UU */
    public static final String COLUMNNAME_UNS_Charge_Detail_UU = "UNS_Charge_Detail_UU";

	/** Set UNS_Charge_Detail_UU	  */
	public void setUNS_Charge_Detail_UU (String UNS_Charge_Detail_UU);

	/** Get UNS_Charge_Detail_UU	  */
	public String getUNS_Charge_Detail_UU();

    /** Column name UNS_Charge_RS_ID */
    public static final String COLUMNNAME_UNS_Charge_RS_ID = "UNS_Charge_RS_ID";

	/** Set Charge Request / Settlement	  */
	public void setUNS_Charge_RS_ID (int UNS_Charge_RS_ID);

	/** Get Charge Request / Settlement	  */
	public int getUNS_Charge_RS_ID();

	public com.uns.model.I_UNS_Charge_RS getUNS_Charge_RS() throws RuntimeException;

    /** Column name UNS_Fuel_ID */
    public static final String COLUMNNAME_UNS_Fuel_ID = "UNS_Fuel_ID";

	/** Set Fuel	  */
	public void setUNS_Fuel_ID (int UNS_Fuel_ID);

	/** Get Fuel	  */
	public int getUNS_Fuel_ID();

    /** Column name UNS_Shipping_ID */
    public static final String COLUMNNAME_UNS_Shipping_ID = "UNS_Shipping_ID";

	/** Set Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID);

	/** Get Shipping Document	  */
	public int getUNS_Shipping_ID();

    /** Column name UNS_Voucher_Code_ID */
    public static final String COLUMNNAME_UNS_Voucher_Code_ID = "UNS_Voucher_Code_ID";

	/** Set Voucher Code	  */
	public void setUNS_Voucher_Code_ID (int UNS_Voucher_Code_ID);

	/** Get Voucher Code	  */
	public int getUNS_Voucher_Code_ID();

    /** Column name UNS_Voucher_ID */
    public static final String COLUMNNAME_UNS_Voucher_ID = "UNS_Voucher_ID";

	/** Set Voucher	  */
	public void setUNS_Voucher_ID (int UNS_Voucher_ID);

	/** Get Voucher	  */
	public int getUNS_Voucher_ID();

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

    /** Column name VoucherCode */
    public static final String COLUMNNAME_VoucherCode = "VoucherCode";

	/** Set Voucher Code.
	  * Voucher Code
	  */
	public void setVoucherCode (String VoucherCode);

	/** Get Voucher Code.
	  * Voucher Code
	  */
	public String getVoucherCode();
}
