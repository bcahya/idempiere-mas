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

/** Generated Interface for UNS_BonusClaimLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_BonusClaimLine 
{

    /** TableName=UNS_BonusClaimLine */
    public static final String Table_Name = "UNS_BonusClaimLine";

    /** AD_Table_ID=1000023 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Department.
	  * Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Department.
	  * Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID();

    /** Column name AmtAchieved */
    public static final String COLUMNNAME_AmtAchieved = "AmtAchieved";

	/** Set Amt Achieved	  */
	public void setAmtAchieved (BigDecimal AmtAchieved);

	/** Get Amt Achieved	  */
	public BigDecimal getAmtAchieved();

    /** Column name AmtClaimed */
    public static final String COLUMNNAME_AmtClaimed = "AmtClaimed";

	/** Set Amt Claimed	  */
	public void setAmtClaimed (BigDecimal AmtClaimed);

	/** Get Amt Claimed	  */
	public BigDecimal getAmtClaimed();

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name DecisionConfirm */
    public static final String COLUMNNAME_DecisionConfirm = "DecisionConfirm";

	/** Set Decision Confirm	  */
	public void setDecisionConfirm (String DecisionConfirm);

	/** Get Decision Confirm	  */
	public String getDecisionConfirm();

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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Line Amount.
	  * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name ProductBonus_ID */
    public static final String COLUMNNAME_ProductBonus_ID = "ProductBonus_ID";

	/** Set Product Bonus	  */
	public void setProductBonus_ID (int ProductBonus_ID);

	/** Get Product Bonus	  */
	public int getProductBonus_ID();

	public org.compiere.model.I_M_Product getProductBonus() throws RuntimeException;

    /** Column name QtyAchieved */
    public static final String COLUMNNAME_QtyAchieved = "QtyAchieved";

	/** Set Qty Achieved	  */
	public void setQtyAchieved (BigDecimal QtyAchieved);

	/** Get Qty Achieved	  */
	public BigDecimal getQtyAchieved();

    /** Column name QtyClaimed */
    public static final String COLUMNNAME_QtyClaimed = "QtyClaimed";

	/** Set Qty Claimed	  */
	public void setQtyClaimed (BigDecimal QtyClaimed);

	/** Get Qty Claimed	  */
	public BigDecimal getQtyClaimed();

    /** Column name SourceInvoice_ID */
    public static final String COLUMNNAME_SourceInvoice_ID = "SourceInvoice_ID";

	/** Set Source Invoice.
	  * Source Invoice
	  */
	public void setSourceInvoice_ID (int SourceInvoice_ID);

	/** Get Source Invoice.
	  * Source Invoice
	  */
	public int getSourceInvoice_ID();

	public org.compiere.model.I_C_Invoice getSourceInvoice() throws RuntimeException;

    /** Column name SourceInvoiceLine_ID */
    public static final String COLUMNNAME_SourceInvoiceLine_ID = "SourceInvoiceLine_ID";

	/** Set Source Invoice Line.
	  * Source Invoice Detail Line
	  */
	public void setSourceInvoiceLine_ID (int SourceInvoiceLine_ID);

	/** Get Source Invoice Line.
	  * Source Invoice Detail Line
	  */
	public int getSourceInvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getSourceInvoiceLine() throws RuntimeException;

    /** Column name UNS_BonusClaim_ID */
    public static final String COLUMNNAME_UNS_BonusClaim_ID = "UNS_BonusClaim_ID";

	/** Set Bonus Claim	  */
	public void setUNS_BonusClaim_ID (int UNS_BonusClaim_ID);

	/** Get Bonus Claim	  */
	public int getUNS_BonusClaim_ID();

	public com.unicore.model.I_UNS_BonusClaim getUNS_BonusClaim() throws RuntimeException;

    /** Column name UNS_BonusClaimLine_ID */
    public static final String COLUMNNAME_UNS_BonusClaimLine_ID = "UNS_BonusClaimLine_ID";

	/** Set Bonus Claim Line	  */
	public void setUNS_BonusClaimLine_ID (int UNS_BonusClaimLine_ID);

	/** Get Bonus Claim Line	  */
	public int getUNS_BonusClaimLine_ID();

    /** Column name UNS_BonusClaimLine_UU */
    public static final String COLUMNNAME_UNS_BonusClaimLine_UU = "UNS_BonusClaimLine_UU";

	/** Set UNS_BonusClaimLine_UU	  */
	public void setUNS_BonusClaimLine_UU (String UNS_BonusClaimLine_UU);

	/** Get UNS_BonusClaimLine_UU	  */
	public String getUNS_BonusClaimLine_UU();

    /** Column name UOMBonus_ID */
    public static final String COLUMNNAME_UOMBonus_ID = "UOMBonus_ID";

	/** Set UOM Bonus	  */
	public void setUOMBonus_ID (int UOMBonus_ID);

	/** Get UOM Bonus	  */
	public int getUOMBonus_ID();

	public org.compiere.model.I_C_UOM getUOMBonus() throws RuntimeException;

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
