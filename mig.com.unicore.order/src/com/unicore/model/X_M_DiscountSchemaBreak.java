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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_DiscountSchemaBreak
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_M_DiscountSchemaBreak extends PO implements I_M_DiscountSchemaBreak, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180306L;

    /** Standard Constructor */
    public X_M_DiscountSchemaBreak (Properties ctx, int M_DiscountSchemaBreak_ID, String trxName)
    {
      super (ctx, M_DiscountSchemaBreak_ID, trxName);
      /** if (M_DiscountSchemaBreak_ID == 0)
        {
			setBreakDiscount (Env.ZERO);
			setBreakType (null);
// F
			setBreakValue (Env.ZERO);
			setBudgetType (null);
// NB
			setCalculationType (null);
// P
			setDiscountType (null);
			setIsBirthdayDiscount (false);
// N
			setIsBPartnerFlatDiscount (false);
// N
			setIsIncludingSubOrdinate (false);
// N
			setIsMix (false);
// N
			setIsMixRequired (false);
// N
			setIsSharedDiscount (false);
// N
			setIsStrataBudget (true);
// Y
			setIsStrictStrata (false);
// N
			setIsVendorCashback (false);
// N
			setM_DiscountSchema_ID (0);
			setM_DiscountSchemaBreak_ID (0);
			setName (null);
			setNofMultiples (Env.ZERO);
// 1
			setProcessed (false);
// N
			setProductSelection (null);
// IOP
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM M_DiscountSchemaBreak WHERE M_DiscountSchema_ID=@M_DiscountSchema_ID@
			setTargetBreak (null);
// EP
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchemaBreak (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_DiscountSchemaBreak[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Discount Value.
		@param BreakDiscount 
		Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public void setBreakDiscount (BigDecimal BreakDiscount)
	{
		set_Value (COLUMNNAME_BreakDiscount, BreakDiscount);
	}

	/** Get Discount Value.
		@return Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public BigDecimal getBreakDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Multiple Discount = M */
	public static final String BREAKTYPE_MultipleDiscount = "M";
	/** Flat Discount = F */
	public static final String BREAKTYPE_FlatDiscount = "F";
	/** Set Break Type.
		@param BreakType 
		Break type for discount
	  */
	public void setBreakType (String BreakType)
	{

		set_Value (COLUMNNAME_BreakType, BreakType);
	}

	/** Get Break Type.
		@return Break type for discount
	  */
	public String getBreakType () 
	{
		return (String)get_Value(COLUMNNAME_BreakType);
	}

	/** Set Break Value.
		@param BreakValue 
		Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	/** Get Break Value.
		@return Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Amount = AMT */
	public static final String BUDGETCALCULATION_Amount = "AMT";
	/** Quantity = QTY */
	public static final String BUDGETCALCULATION_Quantity = "QTY";
	/** Amount Of Discount = ADS */
	public static final String BUDGETCALCULATION_AmountOfDiscount = "ADS";
	/** Set Budget Calculation.
		@param BudgetCalculation Budget Calculation	  */
	public void setBudgetCalculation (String BudgetCalculation)
	{

		set_Value (COLUMNNAME_BudgetCalculation, BudgetCalculation);
	}

	/** Get Budget Calculation.
		@return Budget Calculation	  */
	public String getBudgetCalculation () 
	{
		return (String)get_Value(COLUMNNAME_BudgetCalculation);
	}

	/** Customer Budget = CB */
	public static final String BUDGETTYPE_CustomerBudget = "CB";
	/** Customer Group Budget = CG */
	public static final String BUDGETTYPE_CustomerGroupBudget = "CG";
	/** General Budget = GB */
	public static final String BUDGETTYPE_GeneralBudget = "GB";
	/** Non Budgeted = NB */
	public static final String BUDGETTYPE_NonBudgeted = "NB";
	/** Customer Grade Budget = OG */
	public static final String BUDGETTYPE_CustomerGradeBudget = "OG";
	/** Sales Budget = SB */
	public static final String BUDGETTYPE_SalesBudget = "SB";
	/** Organization Budget = OB */
	public static final String BUDGETTYPE_OrganizationBudget = "OB";
	/** Set Budget Type.
		@param BudgetType Budget Type	  */
	public void setBudgetType (String BudgetType)
	{

		set_Value (COLUMNNAME_BudgetType, BudgetType);
	}

	/** Get Budget Type.
		@return Budget Type	  */
	public String getBudgetType () 
	{
		return (String)get_Value(COLUMNNAME_BudgetType);
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

	/** Set Business Partner.
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

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Value = V */
	public static final String CALCULATIONTYPE_Value = "V";
	/** Qty = Q */
	public static final String CALCULATIONTYPE_Qty = "Q";
	/** Set Calculation.
		@param CalculationType Calculation	  */
	public void setCalculationType (String CalculationType)
	{

		set_Value (COLUMNNAME_CalculationType, CalculationType);
	}

	/** Get Calculation.
		@return Calculation	  */
	public String getCalculationType () 
	{
		return (String)get_Value(COLUMNNAME_CalculationType);
	}

	/** Percent Value Discount = PVD */
	public static final String DISCOUNTTYPE_PercentValueDiscount = "PVD";
	/** Flat Value Discount = FVD */
	public static final String DISCOUNTTYPE_FlatValueDiscount = "FVD";
	/** Percent Product Bonuses = PPB */
	public static final String DISCOUNTTYPE_PercentProductBonuses = "PPB";
	/** Flat Product Bonuses = FPB */
	public static final String DISCOUNTTYPE_FlatProductBonuses = "FPB";
	/** Multiple Value Discount = MFD */
	public static final String DISCOUNTTYPE_MultipleValueDiscount = "MFD";
	/** Multiple Flat Product Bonuses = MPB */
	public static final String DISCOUNTTYPE_MultipleFlatProductBonuses = "MPB";
	/** Set Discount Type.
		@param DiscountType 
		Type of trade discount calculation
	  */
	public void setDiscountType (String DiscountType)
	{

		set_Value (COLUMNNAME_DiscountType, DiscountType);
	}

	/** Get Discount Type.
		@return Type of trade discount calculation
	  */
	public String getDiscountType () 
	{
		return (String)get_Value(COLUMNNAME_DiscountType);
	}

	/** Set 5th Discount %.
		@param FifthDiscount 
		Fifth discount percentage
	  */
	public void setFifthDiscount (BigDecimal FifthDiscount)
	{
		set_Value (COLUMNNAME_FifthDiscount, FifthDiscount);
	}

	/** Get 5th Discount %.
		@return Fifth discount percentage
	  */
	public BigDecimal getFifthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FifthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Discount %.
		@param FourthDiscount 
		Fourth discount percentage
	  */
	public void setFourthDiscount (BigDecimal FourthDiscount)
	{
		set_Value (COLUMNNAME_FourthDiscount, FourthDiscount);
	}

	/** Get 4th Discount %.
		@return Fourth discount percentage
	  */
	public BigDecimal getFourthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FourthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Birthday Discount.
		@param IsBirthdayDiscount Birthday Discount	  */
	public void setIsBirthdayDiscount (boolean IsBirthdayDiscount)
	{
		set_Value (COLUMNNAME_IsBirthdayDiscount, Boolean.valueOf(IsBirthdayDiscount));
	}

	/** Get Birthday Discount.
		@return Birthday Discount	  */
	public boolean isBirthdayDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBirthdayDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set B.Partner Flat Discount.
		@param IsBPartnerFlatDiscount 
		Use flat discount defined on Business Partner Level
	  */
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount)
	{
		set_Value (COLUMNNAME_IsBPartnerFlatDiscount, Boolean.valueOf(IsBPartnerFlatDiscount));
	}

	/** Get B.Partner Flat Discount.
		@return Use flat discount defined on Business Partner Level
	  */
	public boolean isBPartnerFlatDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerFlatDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Discounted to Bonus.
		@param isDiscountedBonus 
		Indication the product at line is Discounted to Bonus
	  */
	public void setisDiscountedBonus (boolean isDiscountedBonus)
	{
		set_Value (COLUMNNAME_isDiscountedBonus, Boolean.valueOf(isDiscountedBonus));
	}

	/** Get Discounted to Bonus.
		@return Indication the product at line is Discounted to Bonus
	  */
	public boolean isDiscountedBonus () 
	{
		Object oo = get_Value(COLUMNNAME_isDiscountedBonus);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Including Sub Ordinate.
		@param IsIncludingSubOrdinate Including Sub Ordinate	  */
	public void setIsIncludingSubOrdinate (boolean IsIncludingSubOrdinate)
	{
		set_Value (COLUMNNAME_IsIncludingSubOrdinate, Boolean.valueOf(IsIncludingSubOrdinate));
	}

	/** Get Including Sub Ordinate.
		@return Including Sub Ordinate	  */
	public boolean isIncludingSubOrdinate () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludingSubOrdinate);
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

	/** Set Only Count to Max Range.
		@param isOnlyCountMaxRange 
		Shema for counting multiple discount base range
	  */
	public void setisOnlyCountMaxRange (boolean isOnlyCountMaxRange)
	{
		set_Value (COLUMNNAME_isOnlyCountMaxRange, Boolean.valueOf(isOnlyCountMaxRange));
	}

	/** Get Only Count to Max Range.
		@return Shema for counting multiple discount base range
	  */
	public boolean isOnlyCountMaxRange () 
	{
		Object oo = get_Value(COLUMNNAME_isOnlyCountMaxRange);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Shared Discount.
		@param IsSharedDiscount Shared Discount	  */
	public void setIsSharedDiscount (boolean IsSharedDiscount)
	{
		set_Value (COLUMNNAME_IsSharedDiscount, Boolean.valueOf(IsSharedDiscount));
	}

	/** Get Shared Discount.
		@return Shared Discount	  */
	public boolean isSharedDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsSharedDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Strata Budget.
		@param IsStrataBudget Strata Budget	  */
	public void setIsStrataBudget (boolean IsStrataBudget)
	{
		set_Value (COLUMNNAME_IsStrataBudget, Boolean.valueOf(IsStrataBudget));
	}

	/** Get Strata Budget.
		@return Strata Budget	  */
	public boolean isStrataBudget () 
	{
		Object oo = get_Value(COLUMNNAME_IsStrataBudget);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Strict Strata ?.
		@param IsStrictStrata Strict Strata ?	  */
	public void setIsStrictStrata (boolean IsStrictStrata)
	{
		set_Value (COLUMNNAME_IsStrictStrata, Boolean.valueOf(IsStrictStrata));
	}

	/** Get Strict Strata ?.
		@return Strict Strata ?	  */
	public boolean isStrictStrata () 
	{
		Object oo = get_Value(COLUMNNAME_IsStrictStrata);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Target Before Discount.
		@param isTragetBeforeDiscount 
		Is indicated the target break will calculate with base qty or amount (before discount), or after it.
	  */
	public void setisTragetBeforeDiscount (boolean isTragetBeforeDiscount)
	{
		set_Value (COLUMNNAME_isTragetBeforeDiscount, Boolean.valueOf(isTragetBeforeDiscount));
	}

	/** Get Target Before Discount.
		@return Is indicated the target break will calculate with base qty or amount (before discount), or after it.
	  */
	public boolean isTragetBeforeDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_isTragetBeforeDiscount);
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

	public com.unicore.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
    {
		return (com.unicore.model.I_M_DiscountSchema)MTable.get(getCtx(), com.unicore.model.I_M_DiscountSchema.Table_Name)
			.getPO(getM_DiscountSchema_ID(), get_TrxName());	}

	/** Set Discount Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Discount Schema.
		@return Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_DiscountSchemaBreak_UU.
		@param M_DiscountSchemaBreak_UU M_DiscountSchemaBreak_UU	  */
	public void setM_DiscountSchemaBreak_UU (String M_DiscountSchemaBreak_UU)
	{
		set_Value (COLUMNNAME_M_DiscountSchemaBreak_UU, M_DiscountSchemaBreak_UU);
	}

	/** Get M_DiscountSchemaBreak_UU.
		@return M_DiscountSchemaBreak_UU	  */
	public String getM_DiscountSchemaBreak_UU () 
	{
		return (String)get_Value(COLUMNNAME_M_DiscountSchemaBreak_UU);
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_ValueNoCheck (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Number of Multiples.
		@param NofMultiples Number of Multiples	  */
	public void setNofMultiples (BigDecimal NofMultiples)
	{
		set_Value (COLUMNNAME_NofMultiples, NofMultiples);
	}

	/** Get Number of Multiples.
		@return Number of Multiples	  */
	public BigDecimal getNofMultiples () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NofMultiples);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getPBonus1() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus1_ID(), get_TrxName());	}

	/** Set 1st Product Bonus.
		@param PBonus1_ID 1st Product Bonus	  */
	public void setPBonus1_ID (int PBonus1_ID)
	{
		if (PBonus1_ID < 1) 
			set_Value (COLUMNNAME_PBonus1_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus1_ID, Integer.valueOf(PBonus1_ID));
	}

	/** Get 1st Product Bonus.
		@return 1st Product Bonus	  */
	public int getPBonus1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus2() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus2_ID(), get_TrxName());	}

	/** Set 2nd Product Bonus.
		@param PBonus2_ID 2nd Product Bonus	  */
	public void setPBonus2_ID (int PBonus2_ID)
	{
		if (PBonus2_ID < 1) 
			set_Value (COLUMNNAME_PBonus2_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus2_ID, Integer.valueOf(PBonus2_ID));
	}

	/** Get 2nd Product Bonus.
		@return 2nd Product Bonus	  */
	public int getPBonus2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus3() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus3_ID(), get_TrxName());	}

	/** Set 3rd Product Bonus.
		@param PBonus3_ID 3rd Product Bonus	  */
	public void setPBonus3_ID (int PBonus3_ID)
	{
		if (PBonus3_ID < 1) 
			set_Value (COLUMNNAME_PBonus3_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus3_ID, Integer.valueOf(PBonus3_ID));
	}

	/** Get 3rd Product Bonus.
		@return 3rd Product Bonus	  */
	public int getPBonus3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus4() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus4_ID(), get_TrxName());	}

	/** Set 4th Product Bonus.
		@param PBonus4_ID 4th Product Bonus	  */
	public void setPBonus4_ID (int PBonus4_ID)
	{
		if (PBonus4_ID < 1) 
			set_Value (COLUMNNAME_PBonus4_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus4_ID, Integer.valueOf(PBonus4_ID));
	}

	/** Get 4th Product Bonus.
		@return 4th Product Bonus	  */
	public int getPBonus4_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus4_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getPBonus5() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getPBonus5_ID(), get_TrxName());	}

	/** Set 5th Product Bonus.
		@param PBonus5_ID 5th Product Bonus	  */
	public void setPBonus5_ID (int PBonus5_ID)
	{
		if (PBonus5_ID < 1) 
			set_Value (COLUMNNAME_PBonus5_ID, null);
		else 
			set_Value (COLUMNNAME_PBonus5_ID, Integer.valueOf(PBonus5_ID));
	}

	/** Get 5th Product Bonus.
		@return 5th Product Bonus	  */
	public int getPBonus5_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PBonus5_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 1st Product Qty.
		@param PQty1_ID 1st Product Qty	  */
	public void setPQty1_ID (BigDecimal PQty1_ID)
	{
		set_Value (COLUMNNAME_PQty1_ID, PQty1_ID);
	}

	/** Get 1st Product Qty.
		@return 1st Product Qty	  */
	public BigDecimal getPQty1_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty1_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 2nd Product Qty.
		@param PQty2_ID 2nd Product Qty	  */
	public void setPQty2_ID (BigDecimal PQty2_ID)
	{
		set_Value (COLUMNNAME_PQty2_ID, PQty2_ID);
	}

	/** Get 2nd Product Qty.
		@return 2nd Product Qty	  */
	public BigDecimal getPQty2_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty2_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 3rd Product Qty.
		@param PQty3_ID 3rd Product Qty	  */
	public void setPQty3_ID (BigDecimal PQty3_ID)
	{
		set_Value (COLUMNNAME_PQty3_ID, PQty3_ID);
	}

	/** Get 3rd Product Qty.
		@return 3rd Product Qty	  */
	public BigDecimal getPQty3_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty3_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Product Qty.
		@param PQty4_ID 4th Product Qty	  */
	public void setPQty4_ID (BigDecimal PQty4_ID)
	{
		set_Value (COLUMNNAME_PQty4_ID, PQty4_ID);
	}

	/** Get 4th Product Qty.
		@return 4th Product Qty	  */
	public BigDecimal getPQty4_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty4_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 5th Product Qty.
		@param PQty5_ID 5th Product Qty	  */
	public void setPQty5_ID (BigDecimal PQty5_ID)
	{
		set_Value (COLUMNNAME_PQty5_ID, PQty5_ID);
	}

	/** Get 5th Product Qty.
		@return 5th Product Qty	  */
	public BigDecimal getPQty5_ID () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PQty5_ID);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Included All Product = IAP */
	public static final String PRODUCTSELECTION_IncludedAllProduct = "IAP";
	/** Included Once Product = IOP */
	public static final String PRODUCTSELECTION_IncludedOnceProduct = "IOP";
	/** Included Once Product Category = IOPC */
	public static final String PRODUCTSELECTION_IncludedOnceProductCategory = "IOPC";
	/** Included Selected Product = ISP */
	public static final String PRODUCTSELECTION_IncludedSelectedProduct = "ISP";
	/** Included Selected Product Category = ISPC */
	public static final String PRODUCTSELECTION_IncludedSelectedProductCategory = "ISPC";
	/** Included Selected Product By Vendor = ISV */
	public static final String PRODUCTSELECTION_IncludedSelectedProductByVendor = "ISV";
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

	/** Set Qty Allocated.
		@param QtyAllocated Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated)
	{
		set_Value (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Qty Allocated.
		@return Qty Allocated	  */
	public BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reserved Quantity.
		@param QtyReserved 
		Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reserved Quantity.
		@return Reserved Quantity
	  */
	public BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Can't Bigger Than This Schema = ML */
	public static final String REQUIREMENTTYPE_CanTBiggerThanThisSchema = "ML";
	/** Must Same With This Schema = MS */
	public static final String REQUIREMENTTYPE_MustSameWithThisSchema = "MS";
	/** Set Requirement Type.
		@param RequirementType Requirement Type	  */
	public void setRequirementType (String RequirementType)
	{

		set_Value (COLUMNNAME_RequirementType, RequirementType);
	}

	/** Get Requirement Type.
		@return Requirement Type	  */
	public String getRequirementType () 
	{
		return (String)get_Value(COLUMNNAME_RequirementType);
	}

	/** Level Test1 = L1 */
	public static final String SALESLEVEL_LevelTest1 = "L1";
	/** Level Test2 = L2 */
	public static final String SALESLEVEL_LevelTest2 = "L2";
	/** Level Test3 = L3 */
	public static final String SALESLEVEL_LevelTest3 = "L3";
	/** Level Test4 = L4 */
	public static final String SALESLEVEL_LevelTest4 = "L4";
	/** Set Sales Level.
		@param SalesLevel Sales Level	  */
	public void setSalesLevel (String SalesLevel)
	{

		set_Value (COLUMNNAME_SalesLevel, SalesLevel);
	}

	/** Get Sales Level.
		@return Sales Level	  */
	public String getSalesLevel () 
	{
		return (String)get_Value(COLUMNNAME_SalesLevel);
	}

	/** Sales Type 1 Testing = ST1 */
	public static final String SALESTYPE_SalesType1Testing = "ST1";
	/** Sales Type 2 Testing = ST2 */
	public static final String SALESTYPE_SalesType2Testing = "ST2";
	/** Sales Type 3 Testing = ST3 */
	public static final String SALESTYPE_SalesType3Testing = "ST3";
	/** Sales Type 4 Testing = ST4 */
	public static final String SALESTYPE_SalesType4Testing = "ST4";
	/** Sales Type 5 Testing = ST5 */
	public static final String SALESTYPE_SalesType5Testing = "ST5";
	/** Set Sales Type.
		@param SalesType 
		Not Defined
	  */
	public void setSalesType (String SalesType)
	{

		set_Value (COLUMNNAME_SalesType, SalesType);
	}

	/** Get Sales Type.
		@return Not Defined
	  */
	public String getSalesType () 
	{
		return (String)get_Value(COLUMNNAME_SalesType);
	}

	/** Set 2nd Discount %.
		@param SecondDiscount 
		Second discount percentage
	  */
	public void setSecondDiscount (BigDecimal SecondDiscount)
	{
		set_Value (COLUMNNAME_SecondDiscount, SecondDiscount);
	}

	/** Get 2nd Discount %.
		@return Second discount percentage
	  */
	public BigDecimal getSecondDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SecondDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Exclude Selected Customer = ESC */
	public static final String SELECTIONTYPE_ExcludeSelectedCustomer = "ESC";
	/** Exclude Selected Customer Group = ESCG */
	public static final String SELECTIONTYPE_ExcludeSelectedCustomerGroup = "ESCG";
	/** Included All = IA */
	public static final String SELECTIONTYPE_IncludedAll = "IA";
	/** Included Selected Customer = ISC */
	public static final String SELECTIONTYPE_IncludedSelectedCustomer = "ISC";
	/** Included Selected Customer Group = ISCG */
	public static final String SELECTIONTYPE_IncludedSelectedCustomerGroup = "ISCG";
	/** Exclude Once Customer = EOC */
	public static final String SELECTIONTYPE_ExcludeOnceCustomer = "EOC";
	/** Exclude Once Customer Group = EOCG */
	public static final String SELECTIONTYPE_ExcludeOnceCustomerGroup = "EOCG";
	/** Excluded Once Customer Grade = EOOG */
	public static final String SELECTIONTYPE_ExcludedOnceCustomerGrade = "EOOG";
	/** Exclude Selected Customer Grade = ESOG */
	public static final String SELECTIONTYPE_ExcludeSelectedCustomerGrade = "ESOG";
	/** Included Once Customer = IOC */
	public static final String SELECTIONTYPE_IncludedOnceCustomer = "IOC";
	/** Included Once Customer Group = IOCG */
	public static final String SELECTIONTYPE_IncludedOnceCustomerGroup = "IOCG";
	/** Included Once Customer Grade = IOOG */
	public static final String SELECTIONTYPE_IncludedOnceCustomerGrade = "IOOG";
	/** Included Selected Customer Grade = ISOG */
	public static final String SELECTIONTYPE_IncludedSelectedCustomerGrade = "ISOG";
	/** Vendor = V */
	public static final String SELECTIONTYPE_Vendor = "V";
	/** Vendor Group = VG */
	public static final String SELECTIONTYPE_VendorGroup = "VG";
	/** Set Selection Type.
		@param SelectionType Selection Type	  */
	public void setSelectionType (String SelectionType)
	{

		set_Value (COLUMNNAME_SelectionType, SelectionType);
	}

	/** Get Selection Type.
		@return Selection Type	  */
	public String getSelectionType () 
	{
		return (String)get_Value(COLUMNNAME_SelectionType);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getSeqNo()));
    }

	/** Discount Every PO/SO = EP */
	public static final String TARGETBREAK_DiscountEveryPOSO = "EP";
	/** Discount by Periodic = DP */
	public static final String TARGETBREAK_DiscountByPeriodic = "DP";
	/** Set Target Break.
		@param TargetBreak Target Break	  */
	public void setTargetBreak (String TargetBreak)
	{

		set_Value (COLUMNNAME_TargetBreak, TargetBreak);
	}

	/** Get Target Break.
		@return Target Break	  */
	public String getTargetBreak () 
	{
		return (String)get_Value(COLUMNNAME_TargetBreak);
	}

	/** Monthly = MO */
	public static final String TARGETPERIODIC_Monthly = "MO";
	/** Quarter Year = QY */
	public static final String TARGETPERIODIC_QuarterYear = "QY";
	/** Half Year = HY */
	public static final String TARGETPERIODIC_HalfYear = "HY";
	/** Year = YR */
	public static final String TARGETPERIODIC_Year = "YR";
	/** Set Target Periodic.
		@param TargetPeriodic 
		Discont target periodic for target break type periodic
	  */
	public void setTargetPeriodic (String TargetPeriodic)
	{

		set_Value (COLUMNNAME_TargetPeriodic, TargetPeriodic);
	}

	/** Get Target Periodic.
		@return Discont target periodic for target break type periodic
	  */
	public String getTargetPeriodic () 
	{
		return (String)get_Value(COLUMNNAME_TargetPeriodic);
	}

	/** Set 3rd Discount %.
		@param ThirdDiscount 
		Third discount percentage
	  */
	public void setThirdDiscount (BigDecimal ThirdDiscount)
	{
		set_Value (COLUMNNAME_ThirdDiscount, ThirdDiscount);
	}

	/** Get 3rd Discount %.
		@return Third discount percentage
	  */
	public BigDecimal getThirdDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ThirdDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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