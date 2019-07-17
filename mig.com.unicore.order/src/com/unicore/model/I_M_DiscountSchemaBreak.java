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

/** Generated Interface for M_DiscountSchemaBreak
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_M_DiscountSchemaBreak 
{

    /** TableName=M_DiscountSchemaBreak */
    public static final String Table_Name = "M_DiscountSchemaBreak";

    /** AD_Table_ID=476 */
    public static final int Table_ID = 476;

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

    /** Column name BreakDiscount */
    public static final String COLUMNNAME_BreakDiscount = "BreakDiscount";

	/** Set Discount Value.
	  * Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public void setBreakDiscount (BigDecimal BreakDiscount);

	/** Get Discount Value.
	  * Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public BigDecimal getBreakDiscount();

    /** Column name BreakType */
    public static final String COLUMNNAME_BreakType = "BreakType";

	/** Set Break Type.
	  * Break type for discount
	  */
	public void setBreakType (String BreakType);

	/** Get Break Type.
	  * Break type for discount
	  */
	public String getBreakType();

    /** Column name BreakValue */
    public static final String COLUMNNAME_BreakValue = "BreakValue";

	/** Set Break Value.
	  * Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue);

	/** Get Break Value.
	  * Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue();

    /** Column name BudgetCalculation */
    public static final String COLUMNNAME_BudgetCalculation = "BudgetCalculation";

	/** Set Budget Calculation	  */
	public void setBudgetCalculation (String BudgetCalculation);

	/** Get Budget Calculation	  */
	public String getBudgetCalculation();

    /** Column name BudgetType */
    public static final String COLUMNNAME_BudgetType = "BudgetType";

	/** Set Budget Type	  */
	public void setBudgetType (String BudgetType);

	/** Get Budget Type	  */
	public String getBudgetType();

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Promotion Business Partner Group.
	  * Promotion Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Promotion Business Partner Group.
	  * Promotion Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

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

    /** Column name CalculationType */
    public static final String COLUMNNAME_CalculationType = "CalculationType";

	/** Set Calculation	  */
	public void setCalculationType (String CalculationType);

	/** Get Calculation	  */
	public String getCalculationType();

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

    /** Column name isDiscountedBonus */
    public static final String COLUMNNAME_isDiscountedBonus = "isDiscountedBonus";

	/** Set Discounted to Bonus.
	  * Indication the product at line is Discounted to Bonus
	  */
	public void setisDiscountedBonus (boolean isDiscountedBonus);

	/** Get Discounted to Bonus.
	  * Indication the product at line is Discounted to Bonus
	  */
	public boolean isDiscountedBonus();

    /** Column name IsIncludingSubOrdinate */
    public static final String COLUMNNAME_IsIncludingSubOrdinate = "IsIncludingSubOrdinate";

	/** Set Including Sub Ordinate	  */
	public void setIsIncludingSubOrdinate (boolean IsIncludingSubOrdinate);

	/** Get Including Sub Ordinate	  */
	public boolean isIncludingSubOrdinate();

    /** Column name IsMix */
    public static final String COLUMNNAME_IsMix = "IsMix";

	/** Set Mix	  */
	public void setIsMix (boolean IsMix);

	/** Get Mix	  */
	public boolean isMix();

    /** Column name IsMixRequired */
    public static final String COLUMNNAME_IsMixRequired = "IsMixRequired";

	/** Set Mix Required	  */
	public void setIsMixRequired (boolean IsMixRequired);

	/** Get Mix Required	  */
	public boolean isMixRequired();

    /** Column name isOnlyCountMaxRange */
    public static final String COLUMNNAME_isOnlyCountMaxRange = "isOnlyCountMaxRange";

	/** Set Only Count to Max Range.
	  * Shema for counting multiple discount base range
	  */
	public void setisOnlyCountMaxRange (boolean isOnlyCountMaxRange);

	/** Get Only Count to Max Range.
	  * Shema for counting multiple discount base range
	  */
	public boolean isOnlyCountMaxRange();

    /** Column name IsSharedDiscount */
    public static final String COLUMNNAME_IsSharedDiscount = "IsSharedDiscount";

	/** Set Shared Discount	  */
	public void setIsSharedDiscount (boolean IsSharedDiscount);

	/** Get Shared Discount	  */
	public boolean isSharedDiscount();

    /** Column name IsStrataBudget */
    public static final String COLUMNNAME_IsStrataBudget = "IsStrataBudget";

	/** Set Strata Budget	  */
	public void setIsStrataBudget (boolean IsStrataBudget);

	/** Get Strata Budget	  */
	public boolean isStrataBudget();

    /** Column name IsStrictStrata */
    public static final String COLUMNNAME_IsStrictStrata = "IsStrictStrata";

	/** Set Strict Strata ?	  */
	public void setIsStrictStrata (boolean IsStrictStrata);

	/** Get Strict Strata ?	  */
	public boolean isStrictStrata();

    /** Column name isTragetBeforeDiscount */
    public static final String COLUMNNAME_isTragetBeforeDiscount = "isTragetBeforeDiscount";

	/** Set Target Before Discount.
	  * Is indicated the target break will calculate with base qty or amount (before discount), or after it.
	  */
	public void setisTragetBeforeDiscount (boolean isTragetBeforeDiscount);

	/** Get Target Before Discount.
	  * Is indicated the target break will calculate with base qty or amount (before discount), or after it.
	  */
	public boolean isTragetBeforeDiscount();

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

	public com.unicore.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException;

    /** Column name M_DiscountSchemaBreak_ID */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

	/** Set Discount Schema Break.
	  * Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/** Get Discount Schema Break.
	  * Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID();

    /** Column name M_DiscountSchemaBreak_UU */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_UU = "M_DiscountSchemaBreak_UU";

	/** Set M_DiscountSchemaBreak_UU	  */
	public void setM_DiscountSchemaBreak_UU (String M_DiscountSchemaBreak_UU);

	/** Get M_DiscountSchemaBreak_UU	  */
	public String getM_DiscountSchemaBreak_UU();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

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

    /** Column name NofMultiples */
    public static final String COLUMNNAME_NofMultiples = "NofMultiples";

	/** Set Number of Multiples	  */
	public void setNofMultiples (BigDecimal NofMultiples);

	/** Get Number of Multiples	  */
	public BigDecimal getNofMultiples();

    /** Column name PBonus1_ID */
    public static final String COLUMNNAME_PBonus1_ID = "PBonus1_ID";

	/** Set 1st Product Bonus	  */
	public void setPBonus1_ID (int PBonus1_ID);

	/** Get 1st Product Bonus	  */
	public int getPBonus1_ID();

	public org.compiere.model.I_M_Product getPBonus1() throws RuntimeException;

    /** Column name PBonus2_ID */
    public static final String COLUMNNAME_PBonus2_ID = "PBonus2_ID";

	/** Set 2nd Product Bonus	  */
	public void setPBonus2_ID (int PBonus2_ID);

	/** Get 2nd Product Bonus	  */
	public int getPBonus2_ID();

	public org.compiere.model.I_M_Product getPBonus2() throws RuntimeException;

    /** Column name PBonus3_ID */
    public static final String COLUMNNAME_PBonus3_ID = "PBonus3_ID";

	/** Set 3rd Product Bonus	  */
	public void setPBonus3_ID (int PBonus3_ID);

	/** Get 3rd Product Bonus	  */
	public int getPBonus3_ID();

	public org.compiere.model.I_M_Product getPBonus3() throws RuntimeException;

    /** Column name PBonus4_ID */
    public static final String COLUMNNAME_PBonus4_ID = "PBonus4_ID";

	/** Set 4th Product Bonus	  */
	public void setPBonus4_ID (int PBonus4_ID);

	/** Get 4th Product Bonus	  */
	public int getPBonus4_ID();

	public org.compiere.model.I_M_Product getPBonus4() throws RuntimeException;

    /** Column name PBonus5_ID */
    public static final String COLUMNNAME_PBonus5_ID = "PBonus5_ID";

	/** Set 5th Product Bonus	  */
	public void setPBonus5_ID (int PBonus5_ID);

	/** Get 5th Product Bonus	  */
	public int getPBonus5_ID();

	public org.compiere.model.I_M_Product getPBonus5() throws RuntimeException;

    /** Column name PQty1_ID */
    public static final String COLUMNNAME_PQty1_ID = "PQty1_ID";

	/** Set 1st Product Qty	  */
	public void setPQty1_ID (BigDecimal PQty1_ID);

	/** Get 1st Product Qty	  */
	public BigDecimal getPQty1_ID();

    /** Column name PQty2_ID */
    public static final String COLUMNNAME_PQty2_ID = "PQty2_ID";

	/** Set 2nd Product Qty	  */
	public void setPQty2_ID (BigDecimal PQty2_ID);

	/** Get 2nd Product Qty	  */
	public BigDecimal getPQty2_ID();

    /** Column name PQty3_ID */
    public static final String COLUMNNAME_PQty3_ID = "PQty3_ID";

	/** Set 3rd Product Qty	  */
	public void setPQty3_ID (BigDecimal PQty3_ID);

	/** Get 3rd Product Qty	  */
	public BigDecimal getPQty3_ID();

    /** Column name PQty4_ID */
    public static final String COLUMNNAME_PQty4_ID = "PQty4_ID";

	/** Set 4th Product Qty	  */
	public void setPQty4_ID (BigDecimal PQty4_ID);

	/** Get 4th Product Qty	  */
	public BigDecimal getPQty4_ID();

    /** Column name PQty5_ID */
    public static final String COLUMNNAME_PQty5_ID = "PQty5_ID";

	/** Set 5th Product Qty	  */
	public void setPQty5_ID (BigDecimal PQty5_ID);

	/** Get 5th Product Qty	  */
	public BigDecimal getPQty5_ID();

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

    /** Column name ProductSelection */
    public static final String COLUMNNAME_ProductSelection = "ProductSelection";

	/** Set Product Selection	  */
	public void setProductSelection (String ProductSelection);

	/** Get Product Selection	  */
	public String getProductSelection();

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

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name TargetBreak */
    public static final String COLUMNNAME_TargetBreak = "TargetBreak";

	/** Set Target Break	  */
	public void setTargetBreak (String TargetBreak);

	/** Get Target Break	  */
	public String getTargetBreak();

    /** Column name TargetPeriodic */
    public static final String COLUMNNAME_TargetPeriodic = "TargetPeriodic";

	/** Set Target Periodic.
	  * Discont target periodic for target break type periodic
	  */
	public void setTargetPeriodic (String TargetPeriodic);

	/** Get Target Periodic.
	  * Discont target periodic for target break type periodic
	  */
	public String getTargetPeriodic();

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
