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

/** Generated Interface for M_DiscountSchema
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_M_DiscountSchema 
{

    /** TableName=M_DiscountSchema */
    public static final String Table_Name = "M_DiscountSchema";

    /** AD_Table_ID=475 */
    public static final int Table_ID = 475;

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

    /** Column name BudgetType */
    public static final String COLUMNNAME_BudgetType = "BudgetType";

	/** Set Budget Type	  */
	public void setBudgetType (String BudgetType);

	/** Get Budget Type	  */
	public String getBudgetType();

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

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Business Partner Group.
	  * Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Business Partner Group.
	  * Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (String CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public String getCopyFrom();

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

    /** Column name CumulativeLevel */
    public static final String COLUMNNAME_CumulativeLevel = "CumulativeLevel";

	/** Set Accumulation Level.
	  * Level for accumulative calculations
	  */
	public void setCumulativeLevel (String CumulativeLevel);

	/** Get Accumulation Level.
	  * Level for accumulative calculations
	  */
	public String getCumulativeLevel();

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

    /** Column name DiscountType */
    public static final String COLUMNNAME_DiscountType = "DiscountType";

	/** Set Discount Type.
	  * Type of trade discount calculation
	  */
	public void setDiscountType (String DiscountType);

	/** Get Discount Type.
	  * Type of trade discount calculation
	  */
	public String getDiscountType();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name FifthDiscount */
    public static final String COLUMNNAME_FifthDiscount = "FifthDiscount";

	/** Set 5th Discount %.
	  * Fifth discount percentage
	  */
	public void setFifthDiscount (BigDecimal FifthDiscount);

	/** Get 5th Discount %.
	  * Fifth discount percentage
	  */
	public BigDecimal getFifthDiscount();

    /** Column name FlatDiscount */
    public static final String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/** Set Flat Discount %.
	  * Flat discount percentage
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount);

	/** Get Flat Discount %.
	  * Flat discount percentage
	  */
	public BigDecimal getFlatDiscount();

    /** Column name FlatDiscountType */
    public static final String COLUMNNAME_FlatDiscountType = "FlatDiscountType";

	/** Set Flat Discount Type	  */
	public void setFlatDiscountType (String FlatDiscountType);

	/** Get Flat Discount Type	  */
	public String getFlatDiscountType();

    /** Column name FourthDiscount */
    public static final String COLUMNNAME_FourthDiscount = "FourthDiscount";

	/** Set 4th Discount %.
	  * Fourth discount percentage
	  */
	public void setFourthDiscount (BigDecimal FourthDiscount);

	/** Get 4th Discount %.
	  * Fourth discount percentage
	  */
	public BigDecimal getFourthDiscount();

    /** Column name GenerateList */
    public static final String COLUMNNAME_GenerateList = "GenerateList";

	/** Set Generate List.
	  * Generate List
	  */
	public void setGenerateList (String GenerateList);

	/** Get Generate List.
	  * Generate List
	  */
	public String getGenerateList();

    /** Column name GenerateSalesBudget */
    public static final String COLUMNNAME_GenerateSalesBudget = "GenerateSalesBudget";

	/** Set Generate Sales Budget	  */
	public void setGenerateSalesBudget (String GenerateSalesBudget);

	/** Get Generate Sales Budget	  */
	public String getGenerateSalesBudget();

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

    /** Column name IsBirthdayDiscount */
    public static final String COLUMNNAME_IsBirthdayDiscount = "IsBirthdayDiscount";

	/** Set Birthday Discount	  */
	public void setIsBirthdayDiscount (boolean IsBirthdayDiscount);

	/** Get Birthday Discount	  */
	public boolean isBirthdayDiscount();

    /** Column name IsBPartnerFlatDiscount */
    public static final String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/** Set B.Partner Flat Discount.
	  * Use flat discount defined on Business Partner Level
	  */
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/** Get B.Partner Flat Discount.
	  * Use flat discount defined on Business Partner Level
	  */
	public boolean isBPartnerFlatDiscount();

    /** Column name IsCashPayment */
    public static final String COLUMNNAME_IsCashPayment = "IsCashPayment";

	/** Set Cash Payment	  */
	public void setIsCashPayment (boolean IsCashPayment);

	/** Get Cash Payment	  */
	public boolean isCashPayment();

    /** Column name IsIncludingSubOrdinate */
    public static final String COLUMNNAME_IsIncludingSubOrdinate = "IsIncludingSubOrdinate";

	/** Set Including Sub Ordinate	  */
	public void setIsIncludingSubOrdinate (boolean IsIncludingSubOrdinate);

	/** Get Including Sub Ordinate	  */
	public boolean isIncludingSubOrdinate();

    /** Column name IsMustBeContinued */
    public static final String COLUMNNAME_IsMustBeContinued = "IsMustBeContinued";

	/** Set Must Be Continued	  */
	public void setIsMustBeContinued (boolean IsMustBeContinued);

	/** Get Must Be Continued	  */
	public boolean isMustBeContinued();

    /** Column name IsPickup */
    public static final String COLUMNNAME_IsPickup = "IsPickup";

	/** Set Pickup	  */
	public void setIsPickup (boolean IsPickup);

	/** Get Pickup	  */
	public boolean isPickup();

    /** Column name IsQuantityBased */
    public static final String COLUMNNAME_IsQuantityBased = "IsQuantityBased";

	/** Set Quantity based.
	  * Trade discount break level based on Quantity (not value)
	  */
	public void setIsQuantityBased (boolean IsQuantityBased);

	/** Get Quantity based.
	  * Trade discount break level based on Quantity (not value)
	  */
	public boolean isQuantityBased();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name IsStandartDiscount */
    public static final String COLUMNNAME_IsStandartDiscount = "IsStandartDiscount";

	/** Set Standart Discount.
	  * This is a Standart Discount will calculate before other Discount
	  */
	public void setIsStandartDiscount (boolean IsStandartDiscount);

	/** Get Standart Discount.
	  * This is a Standart Discount will calculate before other Discount
	  */
	public boolean isStandartDiscount();

    /** Column name IsVendorCashback */
    public static final String COLUMNNAME_IsVendorCashback = "IsVendorCashback";

	/** Set Vendor Cashback	  */
	public void setIsVendorCashback (boolean IsVendorCashback);

	/** Get Vendor Cashback	  */
	public boolean isVendorCashback();

    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/** Set Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/** Get Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID();

    /** Column name M_DiscountSchema_UU */
    public static final String COLUMNNAME_M_DiscountSchema_UU = "M_DiscountSchema_UU";

	/** Set M_DiscountSchema_UU	  */
	public void setM_DiscountSchema_UU (String M_DiscountSchema_UU);

	/** Get M_DiscountSchema_UU	  */
	public String getM_DiscountSchema_UU();

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name organizationaleffectiveness */
    public static final String COLUMNNAME_organizationaleffectiveness = "organizationaleffectiveness";

	/** Set organizational effectiveness	  */
	public void setorganizationaleffectiveness (String organizationaleffectiveness);

	/** Get organizational effectiveness	  */
	public String getorganizationaleffectiveness();

    /** Column name PreviousSchema_ID */
    public static final String COLUMNNAME_PreviousSchema_ID = "PreviousSchema_ID";

	/** Set Previous Schema.
	  * The previous discount schema to be closed and replaced with this new one.
	  */
	public void setPreviousSchema_ID (int PreviousSchema_ID);

	/** Get Previous Schema.
	  * The previous discount schema to be closed and replaced with this new one.
	  */
	public int getPreviousSchema_ID();

	public com.unicore.model.I_M_DiscountSchema getPreviousSchema() throws RuntimeException;

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QtyAllocated */
    public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";

	/** Set Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated);

	/** Get Qty Allocated	  */
	public BigDecimal getQtyAllocated();

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/** Set Reserved Quantity.
	  * Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved);

	/** Get Reserved Quantity.
	  * Reserved Quantity
	  */
	public BigDecimal getQtyReserved();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name RequirementType */
    public static final String COLUMNNAME_RequirementType = "RequirementType";

	/** Set Requirement Type	  */
	public void setRequirementType (String RequirementType);

	/** Get Requirement Type	  */
	public String getRequirementType();

    /** Column name SalesLevel */
    public static final String COLUMNNAME_SalesLevel = "SalesLevel";

	/** Set Sales Level	  */
	public void setSalesLevel (String SalesLevel);

	/** Get Sales Level	  */
	public String getSalesLevel();

    /** Column name SalesType */
    public static final String COLUMNNAME_SalesType = "SalesType";

	/** Set Sales Type.
	  * Not Defined
	  */
	public void setSalesType (String SalesType);

	/** Get Sales Type.
	  * Not Defined
	  */
	public String getSalesType();

    /** Column name Script */
    public static final String COLUMNNAME_Script = "Script";

	/** Set Script.
	  * Dynamic Java Language Script to calculate result
	  */
	public void setScript (String Script);

	/** Get Script.
	  * Dynamic Java Language Script to calculate result
	  */
	public String getScript();

    /** Column name SecondDiscount */
    public static final String COLUMNNAME_SecondDiscount = "SecondDiscount";

	/** Set 2nd Discount %.
	  * Second discount percentage
	  */
	public void setSecondDiscount (BigDecimal SecondDiscount);

	/** Get 2nd Discount %.
	  * Second discount percentage
	  */
	public BigDecimal getSecondDiscount();

    /** Column name SelectionType */
    public static final String COLUMNNAME_SelectionType = "SelectionType";

	/** Set Selection Type	  */
	public void setSelectionType (String SelectionType);

	/** Get Selection Type	  */
	public String getSelectionType();

    /** Column name ThirdDiscount */
    public static final String COLUMNNAME_ThirdDiscount = "ThirdDiscount";

	/** Set 3rd Discount %.
	  * Third discount percentage
	  */
	public void setThirdDiscount (BigDecimal ThirdDiscount);

	/** Get 3rd Discount %.
	  * Third discount percentage
	  */
	public BigDecimal getThirdDiscount();

    /** Column name UNS_Outlet_Grade_ID */
    public static final String COLUMNNAME_UNS_Outlet_Grade_ID = "UNS_Outlet_Grade_ID";

	/** Set Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID);

	/** Get Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID();

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

    /** Column name Vendor_ID */
    public static final String COLUMNNAME_Vendor_ID = "Vendor_ID";

	/** Set Vendor.
	  * The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID);

	/** Get Vendor.
	  * The Vendor of the product/service
	  */
	public int getVendor_ID();

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException;
}
