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

/** Generated Interface for UNS_Incentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Incentive 
{

    /** TableName=UNS_Incentive */
    public static final String Table_Name = "UNS_Incentive";

    /** AD_Table_ID=1000148 */
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

    /** Column name BPGroupSelection */
    public static final String COLUMNNAME_BPGroupSelection = "BPGroupSelection";

	/** Set BP Group Selection	  */
	public void setBPGroupSelection (String BPGroupSelection);

	/** Get BP Group Selection	  */
	public String getBPGroupSelection();

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

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name CalculatedRemainingQty */
    public static final String COLUMNNAME_CalculatedRemainingQty = "CalculatedRemainingQty";

	/** Set Calculated Remaining Qty	  */
	public void setCalculatedRemainingQty (boolean CalculatedRemainingQty);

	/** Get Calculated Remaining Qty	  */
	public boolean isCalculatedRemainingQty();

    /** Column name CalculationOnPaid */
    public static final String COLUMNNAME_CalculationOnPaid = "CalculationOnPaid";

	/** Set Calculation On Paid	  */
	public void setCalculationOnPaid (boolean CalculationOnPaid);

	/** Get Calculation On Paid	  */
	public boolean isCalculationOnPaid();

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

    /** Column name CustomerSelection */
    public static final String COLUMNNAME_CustomerSelection = "CustomerSelection";

	/** Set Customer Selection	  */
	public void setCustomerSelection (String CustomerSelection);

	/** Get Customer Selection	  */
	public String getCustomerSelection();

    /** Column name IncentiveType */
    public static final String COLUMNNAME_IncentiveType = "IncentiveType";

	/** Set Incentive Base.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType);

	/** Get Incentive Base.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType();

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

    /** Column name IsCreditNoteMinusValue */
    public static final String COLUMNNAME_IsCreditNoteMinusValue = "IsCreditNoteMinusValue";

	/** Set Credit Note Minus Value	  */
	public void setIsCreditNoteMinusValue (boolean IsCreditNoteMinusValue);

	/** Get Credit Note Minus Value	  */
	public boolean isCreditNoteMinusValue();

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

    /** Column name IsVendorCashback */
    public static final String COLUMNNAME_IsVendorCashback = "IsVendorCashback";

	/** Set Vendor Cashback	  */
	public void setIsVendorCashback (boolean IsVendorCashback);

	/** Get Vendor Cashback	  */
	public boolean isVendorCashback();

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

    /** Column name OrganizationSelection */
    public static final String COLUMNNAME_OrganizationSelection = "OrganizationSelection";

	/** Set Organization Selection	  */
	public void setOrganizationSelection (String OrganizationSelection);

	/** Get Organization Selection	  */
	public String getOrganizationSelection();

    /** Column name OutletGradeSelection */
    public static final String COLUMNNAME_OutletGradeSelection = "OutletGradeSelection";

	/** Set Outlet Grade Selection	  */
	public void setOutletGradeSelection (String OutletGradeSelection);

	/** Get Outlet Grade Selection	  */
	public String getOutletGradeSelection();

    /** Column name OutletTypeSelection */
    public static final String COLUMNNAME_OutletTypeSelection = "OutletTypeSelection";

	/** Set Outlet Type Selection	  */
	public void setOutletTypeSelection (String OutletTypeSelection);

	/** Get Outlet Type Selection	  */
	public String getOutletTypeSelection();

    /** Column name PCategorySelection */
    public static final String COLUMNNAME_PCategorySelection = "PCategorySelection";

	/** Set Product Category Selection	  */
	public void setPCategorySelection (String PCategorySelection);

	/** Get Product Category Selection	  */
	public String getPCategorySelection();

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

    /** Column name RayonSelection */
    public static final String COLUMNNAME_RayonSelection = "RayonSelection";

	/** Set Rayon Selection	  */
	public void setRayonSelection (String RayonSelection);

	/** Get Rayon Selection	  */
	public String getRayonSelection();

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

    /** Column name TenderTypeSelection */
    public static final String COLUMNNAME_TenderTypeSelection = "TenderTypeSelection";

	/** Set Tender Type Selection	  */
	public void setTenderTypeSelection (String TenderTypeSelection);

	/** Get Tender Type Selection	  */
	public String getTenderTypeSelection();

    /** Column name UNS_Incentive_ID */
    public static final String COLUMNNAME_UNS_Incentive_ID = "UNS_Incentive_ID";

	/** Set Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID);

	/** Get Incentive	  */
	public int getUNS_Incentive_ID();

    /** Column name UNS_Incentive_UU */
    public static final String COLUMNNAME_UNS_Incentive_UU = "UNS_Incentive_UU";

	/** Set UNS_Incentive_UU	  */
	public void setUNS_Incentive_UU (String UNS_Incentive_UU);

	/** Get UNS_Incentive_UU	  */
	public String getUNS_Incentive_UU();

    /** Column name UNS_IncentiveSchema_ID */
    public static final String COLUMNNAME_UNS_IncentiveSchema_ID = "UNS_IncentiveSchema_ID";

	/** Set Incenive Schema	  */
	public void setUNS_IncentiveSchema_ID (int UNS_IncentiveSchema_ID);

	/** Get Incenive Schema	  */
	public int getUNS_IncentiveSchema_ID();

	public com.unicore.model.I_UNS_IncentiveSchema getUNS_IncentiveSchema() throws RuntimeException;

    /** Column name UNS_Outlet_Grade_ID */
    public static final String COLUMNNAME_UNS_Outlet_Grade_ID = "UNS_Outlet_Grade_ID";

	/** Set Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID);

	/** Get Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID();

    /** Column name UNS_Outlet_Type_ID */
    public static final String COLUMNNAME_UNS_Outlet_Type_ID = "UNS_Outlet_Type_ID";

	/** Set Outlet Type	  */
	public void setUNS_Outlet_Type_ID (int UNS_Outlet_Type_ID);

	/** Get Outlet Type	  */
	public int getUNS_Outlet_Type_ID();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

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

    /** Column name UseLastDateTrx */
    public static final String COLUMNNAME_UseLastDateTrx = "UseLastDateTrx";

	/** Set Use Last Date Trx	  */
	public void setUseLastDateTrx (boolean UseLastDateTrx);

	/** Get Use Last Date Trx	  */
	public boolean isUseLastDateTrx();

    /** Column name UseLastSalesTrx */
    public static final String COLUMNNAME_UseLastSalesTrx = "UseLastSalesTrx";

	/** Set Use Last Sales Trx	  */
	public void setUseLastSalesTrx (boolean UseLastSalesTrx);

	/** Get Use Last Sales Trx	  */
	public boolean isUseLastSalesTrx();

    /** Column name UseTotalPayAmt */
    public static final String COLUMNNAME_UseTotalPayAmt = "UseTotalPayAmt";

	/** Set Use Total Payment Amount	  */
	public void setUseTotalPayAmt (boolean UseTotalPayAmt);

	/** Get Use Total Payment Amount	  */
	public boolean isUseTotalPayAmt();

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
