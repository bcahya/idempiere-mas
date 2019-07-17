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
/** Generated Model - DO NOT CHANGE */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_Incentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Incentive extends PO implements I_UNS_Incentive, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180402L;

    /** Standard Constructor */
    public X_UNS_Incentive (Properties ctx, int UNS_Incentive_ID, String trxName)
    {
      super (ctx, UNS_Incentive_ID, trxName);
      /** if (UNS_Incentive_ID == 0)
        {
			setBPGroupSelection (null);
// IAG
			setCalculatedRemainingQty (false);
// N
			setCalculationOnPaid (true);
// Y
			setCumulativeLevel (null);
// L
			setCustomerSelection (null);
// IAC
			setIncentiveType (null);
			setIsCreditNoteMinusValue (false);
// N
			setIsMix (false);
// N
			setIsMixRequired (false);
// N
			setIsQuantityBased (true);
// Y
			setIsVendorCashback (false);
// N
			setOrganizationSelection (null);
// IAO
			setOutletGradeSelection (null);
// IAG
			setOutletTypeSelection (null);
// IAT
			setPCategorySelection (null);
// IAC
			setProcessed (false);
// N
			setProductSelection (null);
// IAP
			setRayonSelection (null);
// IAR
			setTenderTypeSelection (null);
// IAT
			setUNS_Incentive_ID (0);
			setUNS_IncentiveSchema_ID (0);
			setUseLastDateTrx (true);
// Y
			setUseLastSalesTrx (true);
// Y
			setUseTotalPayAmt (false);
// N
        } */
    }

    /** Load Constructor */
    public X_UNS_Incentive (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_Incentive[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Include All Group = IAG */
	public static final String BPGROUPSELECTION_IncludeAllGroup = "IAG";
	/** Include Once Group = IOG */
	public static final String BPGROUPSELECTION_IncludeOnceGroup = "IOG";
	/** Exclude Once Group = EOG */
	public static final String BPGROUPSELECTION_ExcludeOnceGroup = "EOG";
	/** Include Selected Group = ISG */
	public static final String BPGROUPSELECTION_IncludeSelectedGroup = "ISG";
	/** Exclude Selected Group = ESG */
	public static final String BPGROUPSELECTION_ExcludeSelectedGroup = "ESG";
	/** Set BP Group Selection.
		@param BPGroupSelection BP Group Selection	  */
	public void setBPGroupSelection (String BPGroupSelection)
	{

		set_Value (COLUMNNAME_BPGroupSelection, BPGroupSelection);
	}

	/** Get BP Group Selection.
		@return BP Group Selection	  */
	public String getBPGroupSelection () 
	{
		return (String)get_Value(COLUMNNAME_BPGroupSelection);
	}

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Promotion Business Partner Group.
		@param C_BP_Group_ID 
		Promotion Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Promotion Business Partner Group.
		@return Promotion Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Calculated Remaining Qty.
		@param CalculatedRemainingQty Calculated Remaining Qty	  */
	public void setCalculatedRemainingQty (boolean CalculatedRemainingQty)
	{
		set_Value (COLUMNNAME_CalculatedRemainingQty, Boolean.valueOf(CalculatedRemainingQty));
	}

	/** Get Calculated Remaining Qty.
		@return Calculated Remaining Qty	  */
	public boolean isCalculatedRemainingQty () 
	{
		Object oo = get_Value(COLUMNNAME_CalculatedRemainingQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculation On Paid.
		@param CalculationOnPaid Calculation On Paid	  */
	public void setCalculationOnPaid (boolean CalculationOnPaid)
	{
		set_Value (COLUMNNAME_CalculationOnPaid, Boolean.valueOf(CalculationOnPaid));
	}

	/** Get Calculation On Paid.
		@return Calculation On Paid	  */
	public boolean isCalculationOnPaid () 
	{
		Object oo = get_Value(COLUMNNAME_CalculationOnPaid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** CumulativeLevel AD_Reference_ID=246 */
	public static final int CUMULATIVELEVEL_AD_Reference_ID=246;
	/** Line = L */
	public static final String CUMULATIVELEVEL_Line = "L";
	/** Document = D */
	public static final String CUMULATIVELEVEL_Document = "D";
	/** Set Accumulation Level.
		@param CumulativeLevel 
		Level for accumulative calculations
	  */
	public void setCumulativeLevel (String CumulativeLevel)
	{

		set_Value (COLUMNNAME_CumulativeLevel, CumulativeLevel);
	}

	/** Get Accumulation Level.
		@return Level for accumulative calculations
	  */
	public String getCumulativeLevel () 
	{
		return (String)get_Value(COLUMNNAME_CumulativeLevel);
	}

	/** Include All Customer = IAC */
	public static final String CUSTOMERSELECTION_IncludeAllCustomer = "IAC";
	/** Include Selected Customer = ISC */
	public static final String CUSTOMERSELECTION_IncludeSelectedCustomer = "ISC";
	/** Exclude Selected Customer = ESC */
	public static final String CUSTOMERSELECTION_ExcludeSelectedCustomer = "ESC";
	/** Include Once Customer = IOC */
	public static final String CUSTOMERSELECTION_IncludeOnceCustomer = "IOC";
	/** Exclude Once Customer = EOC */
	public static final String CUSTOMERSELECTION_ExcludeOnceCustomer = "EOC";
	/** Set Customer Selection.
		@param CustomerSelection Customer Selection	  */
	public void setCustomerSelection (String CustomerSelection)
	{

		set_Value (COLUMNNAME_CustomerSelection, CustomerSelection);
	}

	/** Get Customer Selection.
		@return Customer Selection	  */
	public String getCustomerSelection () 
	{
		return (String)get_Value(COLUMNNAME_CustomerSelection);
	}

	/** Billing = BI */
	public static final String INCENTIVETYPE_Billing = "BI";
	/** Sales Invoice = SI */
	public static final String INCENTIVETYPE_SalesInvoice = "SI";
	/** New Outlet = NI */
	public static final String INCENTIVETYPE_NewOutlet = "NI";
	/** Delivery = DI */
	public static final String INCENTIVETYPE_Delivery = "DI";
	/** Sales Order = OI */
	public static final String INCENTIVETYPE_SalesOrder = "OI";
	/** Deduction Invoice = DS */
	public static final String INCENTIVETYPE_DeductionInvoice = "DS";
	
	/** Set Incentive Base.
		@param IncentiveType 
		Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType)
	{

		set_Value (COLUMNNAME_IncentiveType, IncentiveType);
	}

	/** Get Incentive Base.
		@return Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType () 
	{
		return (String)get_Value(COLUMNNAME_IncentiveType);
	}

	/** Set Credit Note Minus Value.
		@param IsCreditNoteMinusValue Credit Note Minus Value	  */
	public void setIsCreditNoteMinusValue (boolean IsCreditNoteMinusValue)
	{
		set_Value (COLUMNNAME_IsCreditNoteMinusValue, Boolean.valueOf(IsCreditNoteMinusValue));
	}

	/** Get Credit Note Minus Value.
		@return Credit Note Minus Value	  */
	public boolean isCreditNoteMinusValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreditNoteMinusValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mix.
		@param IsMix Mix	  */
	public void setIsMix (boolean IsMix)
	{
		set_Value (COLUMNNAME_IsMix, Boolean.valueOf(IsMix));
	}

	/** Get Mix.
		@return Mix	  */
	public boolean isMix () 
	{
		Object oo = get_Value(COLUMNNAME_IsMix);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mix Required.
		@param IsMixRequired Mix Required	  */
	public void setIsMixRequired (boolean IsMixRequired)
	{
		set_Value (COLUMNNAME_IsMixRequired, Boolean.valueOf(IsMixRequired));
	}

	/** Get Mix Required.
		@return Mix Required	  */
	public boolean isMixRequired () 
	{
		Object oo = get_Value(COLUMNNAME_IsMixRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity based.
		@param IsQuantityBased 
		Trade discount break level based on Quantity (not value)
	  */
	public void setIsQuantityBased (boolean IsQuantityBased)
	{
		set_Value (COLUMNNAME_IsQuantityBased, Boolean.valueOf(IsQuantityBased));
	}

	/** Get Quantity based.
		@return Trade discount break level based on Quantity (not value)
	  */
	public boolean isQuantityBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsQuantityBased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vendor Cashback.
		@param IsVendorCashback Vendor Cashback	  */
	public void setIsVendorCashback (boolean IsVendorCashback)
	{
		set_Value (COLUMNNAME_IsVendorCashback, Boolean.valueOf(IsVendorCashback));
	}

	/** Get Vendor Cashback.
		@return Vendor Cashback	  */
	public boolean isVendorCashback () 
	{
		Object oo = get_Value(COLUMNNAME_IsVendorCashback);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Include Once Organization = IOO */
	public static final String ORGANIZATIONSELECTION_IncludeOnceOrganization = "IOO";
	/** Exclude Once Organization = EOO */
	public static final String ORGANIZATIONSELECTION_ExcludeOnceOrganization = "EOO";
	/** Include All Organization = IAO */
	public static final String ORGANIZATIONSELECTION_IncludeAllOrganization = "IAO";
	/** Include Selected Organization = ISO */
	public static final String ORGANIZATIONSELECTION_IncludeSelectedOrganization = "ISO";
	/** Exclude Selected Organization = ESO */
	public static final String ORGANIZATIONSELECTION_ExcludeSelectedOrganization = "ESO";
	/** Set Organization Selection.
		@param OrganizationSelection Organization Selection	  */
	public void setOrganizationSelection (String OrganizationSelection)
	{

		set_Value (COLUMNNAME_OrganizationSelection, OrganizationSelection);
	}

	/** Get Organization Selection.
		@return Organization Selection	  */
	public String getOrganizationSelection () 
	{
		return (String)get_Value(COLUMNNAME_OrganizationSelection);
	}

	/** Include All Grade = IAG */
	public static final String OUTLETGRADESELECTION_IncludeAllGrade = "IAG";
	/** Include Once Grade = IOG */
	public static final String OUTLETGRADESELECTION_IncludeOnceGrade = "IOG";
	/** Exclude Once Grade = EOG */
	public static final String OUTLETGRADESELECTION_ExcludeOnceGrade = "EOG";
	/** Include Selected Grade = ISG */
	public static final String OUTLETGRADESELECTION_IncludeSelectedGrade = "ISG";
	/** Exclude Selected Grade = ESG */
	public static final String OUTLETGRADESELECTION_ExcludeSelectedGrade = "ESG";
	/** Set Outlet Grade Selection.
		@param OutletGradeSelection Outlet Grade Selection	  */
	public void setOutletGradeSelection (String OutletGradeSelection)
	{

		set_Value (COLUMNNAME_OutletGradeSelection, OutletGradeSelection);
	}

	/** Get Outlet Grade Selection.
		@return Outlet Grade Selection	  */
	public String getOutletGradeSelection () 
	{
		return (String)get_Value(COLUMNNAME_OutletGradeSelection);
	}

	/** Include All Type = IAT */
	public static final String OUTLETTYPESELECTION_IncludeAllType = "IAT";
	/** Include Once Type = IOT */
	public static final String OUTLETTYPESELECTION_IncludeOnceType = "IOT";
	/** Include Selected Type = IST */
	public static final String OUTLETTYPESELECTION_IncludeSelectedType = "IST";
	/** Exclude Once Type = EOT */
	public static final String OUTLETTYPESELECTION_ExcludeOnceType = "EOT";
	/** Exclude Selected Type = EST */
	public static final String OUTLETTYPESELECTION_ExcludeSelectedType = "EST";
	/** Set Outlet Type Selection.
		@param OutletTypeSelection Outlet Type Selection	  */
	public void setOutletTypeSelection (String OutletTypeSelection)
	{

		set_Value (COLUMNNAME_OutletTypeSelection, OutletTypeSelection);
	}

	/** Get Outlet Type Selection.
		@return Outlet Type Selection	  */
	public String getOutletTypeSelection () 
	{
		return (String)get_Value(COLUMNNAME_OutletTypeSelection);
	}

	/** Include All Category = IAC */
	public static final String PCATEGORYSELECTION_IncludeAllCategory = "IAC";
	/** Include Once Category = IOC */
	public static final String PCATEGORYSELECTION_IncludeOnceCategory = "IOC";
	/** Include Selected Category = ISC */
	public static final String PCATEGORYSELECTION_IncludeSelectedCategory = "ISC";
	/** Exclude Once Category = EOC */
	public static final String PCATEGORYSELECTION_ExcludeOnceCategory = "EOC";
	/** Exclude Selected Category = ESC */
	public static final String PCATEGORYSELECTION_ExcludeSelectedCategory = "ESC";
	/** Set Product Category Selection.
		@param PCategorySelection Product Category Selection	  */
	public void setPCategorySelection (String PCategorySelection)
	{

		set_Value (COLUMNNAME_PCategorySelection, PCategorySelection);
	}

	/** Get Product Category Selection.
		@return Product Category Selection	  */
	public String getPCategorySelection () 
	{
		return (String)get_Value(COLUMNNAME_PCategorySelection);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Include All Product = IAP */
	public static final String PRODUCTSELECTION_IncludeAllProduct = "IAP";
	/** Include Selected Product = ISP */
	public static final String PRODUCTSELECTION_IncludeSelectedProduct = "ISP";
	/** Include Once Product = IOP */
	public static final String PRODUCTSELECTION_IncludeOnceProduct = "IOP";
	/** Exclude Selected Product = ESP */
	public static final String PRODUCTSELECTION_ExcludeSelectedProduct = "ESP";
	/** Exclude Once Product = EOP */
	public static final String PRODUCTSELECTION_ExcludeOnceProduct = "EOP";
	/** Set Product Selection.
		@param ProductSelection Product Selection	  */
	public void setProductSelection (String ProductSelection)
	{

		set_Value (COLUMNNAME_ProductSelection, ProductSelection);
	}

	/** Get Product Selection.
		@return Product Selection	  */
	public String getProductSelection () 
	{
		return (String)get_Value(COLUMNNAME_ProductSelection);
	}

	/** Include All Rayon = IAR */
	public static final String RAYONSELECTION_IncludeAllRayon = "IAR";
	/** Include Once Rayon = IOR */
	public static final String RAYONSELECTION_IncludeOnceRayon = "IOR";
	/** Include Selected Rayon = ISR */
	public static final String RAYONSELECTION_IncludeSelectedRayon = "ISR";
	/** Exclude Once Rayon = EOR */
	public static final String RAYONSELECTION_ExcludeOnceRayon = "EOR";
	/** Exclude Selected Rayon = ESR */
	public static final String RAYONSELECTION_ExcludeSelectedRayon = "ESR";
	/** Set Rayon Selection.
		@param RayonSelection Rayon Selection	  */
	public void setRayonSelection (String RayonSelection)
	{

		set_Value (COLUMNNAME_RayonSelection, RayonSelection);
	}

	/** Get Rayon Selection.
		@return Rayon Selection	  */
	public String getRayonSelection () 
	{
		return (String)get_Value(COLUMNNAME_RayonSelection);
	}

	/** TenderType AD_Reference_ID=214 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** Credit Card = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** Direct Deposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** Direct Debit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	public void setTenderType (String TenderType)
	{

		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

	/** Include Selected Tender Type = IST */
	public static final String TENDERTYPESELECTION_IncludeSelectedTenderType = "IST";
	/** Exclude Selected Tender Type = EST */
	public static final String TENDERTYPESELECTION_ExcludeSelectedTenderType = "EST";
	/** Include All Tender Type = IAT */
	public static final String TENDERTYPESELECTION_IncludeAllTenderType = "IAT";
	/** Include Once Tender Type = IOT */
	public static final String TENDERTYPESELECTION_IncludeOnceTenderType = "IOT";
	/** Exclude One Tender Type = EOT */
	public static final String TENDERTYPESELECTION_ExcludeOneTenderType = "EOT";
	/** Set Tender Type Selection.
		@param TenderTypeSelection Tender Type Selection	  */
	public void setTenderTypeSelection (String TenderTypeSelection)
	{

		set_Value (COLUMNNAME_TenderTypeSelection, TenderTypeSelection);
	}

	/** Get Tender Type Selection.
		@return Tender Type Selection	  */
	public String getTenderTypeSelection () 
	{
		return (String)get_Value(COLUMNNAME_TenderTypeSelection);
	}

	/** Set Incentive.
		@param UNS_Incentive_ID Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID)
	{
		if (UNS_Incentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Incentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Incentive_ID, Integer.valueOf(UNS_Incentive_ID));
	}

	/** Get Incentive.
		@return Incentive	  */
	public int getUNS_Incentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Incentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Incentive_UU.
		@param UNS_Incentive_UU UNS_Incentive_UU	  */
	public void setUNS_Incentive_UU (String UNS_Incentive_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Incentive_UU, UNS_Incentive_UU);
	}

	/** Get UNS_Incentive_UU.
		@return UNS_Incentive_UU	  */
	public String getUNS_Incentive_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Incentive_UU);
	}

	public com.unicore.model.I_UNS_IncentiveSchema getUNS_IncentiveSchema() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_IncentiveSchema)MTable.get(getCtx(), com.unicore.model.I_UNS_IncentiveSchema.Table_Name)
			.getPO(getUNS_IncentiveSchema_ID(), get_TrxName());	}

	/** Set Incenive Schema.
		@param UNS_IncentiveSchema_ID Incenive Schema	  */
	public void setUNS_IncentiveSchema_ID (int UNS_IncentiveSchema_ID)
	{
		if (UNS_IncentiveSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveSchema_ID, Integer.valueOf(UNS_IncentiveSchema_ID));
	}

	/** Get Incenive Schema.
		@return Incenive Schema	  */
	public int getUNS_IncentiveSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Outlet Grade.
		@param UNS_Outlet_Grade_ID Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID)
	{
		if (UNS_Outlet_Grade_ID < 1) 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, Integer.valueOf(UNS_Outlet_Grade_ID));
	}

	/** Get Outlet Grade.
		@return Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Outlet_Grade_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Outlet Type.
		@param UNS_Outlet_Type_ID Outlet Type	  */
	public void setUNS_Outlet_Type_ID (int UNS_Outlet_Type_ID)
	{
		if (UNS_Outlet_Type_ID < 1) 
			set_Value (COLUMNNAME_UNS_Outlet_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Outlet_Type_ID, Integer.valueOf(UNS_Outlet_Type_ID));
	}

	/** Get Outlet Type.
		@return Outlet Type	  */
	public int getUNS_Outlet_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Outlet_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rayon.
		@param UNS_Rayon_ID Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID)
	{
		if (UNS_Rayon_ID < 1) 
			set_Value (COLUMNNAME_UNS_Rayon_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Rayon_ID, Integer.valueOf(UNS_Rayon_ID));
	}

	/** Get Rayon.
		@return Rayon	  */
	public int getUNS_Rayon_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Rayon_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Use Last Date Trx.
		@param UseLastDateTrx Use Last Date Trx	  */
	public void setUseLastDateTrx (boolean UseLastDateTrx)
	{
		set_Value (COLUMNNAME_UseLastDateTrx, Boolean.valueOf(UseLastDateTrx));
	}

	/** Get Use Last Date Trx.
		@return Use Last Date Trx	  */
	public boolean isUseLastDateTrx () 
	{
		Object oo = get_Value(COLUMNNAME_UseLastDateTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Last Sales Trx.
		@param UseLastSalesTrx Use Last Sales Trx	  */
	public void setUseLastSalesTrx (boolean UseLastSalesTrx)
	{
		set_Value (COLUMNNAME_UseLastSalesTrx, Boolean.valueOf(UseLastSalesTrx));
	}

	/** Get Use Last Sales Trx.
		@return Use Last Sales Trx	  */
	public boolean isUseLastSalesTrx () 
	{
		Object oo = get_Value(COLUMNNAME_UseLastSalesTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Total Payment Amount.
		@param UseTotalPayAmt Use Total Payment Amount	  */
	public void setUseTotalPayAmt (boolean UseTotalPayAmt)
	{
		set_Value (COLUMNNAME_UseTotalPayAmt, Boolean.valueOf(UseTotalPayAmt));
	}

	/** Get Use Total Payment Amount.
		@return Use Total Payment Amount	  */
	public boolean isUseTotalPayAmt () 
	{
		Object oo = get_Value(COLUMNNAME_UseTotalPayAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getVendor_ID(), get_TrxName());	}

	/** Set Vendor.
		@param Vendor_ID 
		The Vendor of the product/service
	  */
	public void setVendor_ID (int Vendor_ID)
	{
		if (Vendor_ID < 1) 
			set_Value (COLUMNNAME_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_Vendor_ID, Integer.valueOf(Vendor_ID));
	}

	/** Get Vendor.
		@return The Vendor of the product/service
	  */
	public int getVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}