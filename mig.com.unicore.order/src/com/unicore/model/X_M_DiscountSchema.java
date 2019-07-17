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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_DiscountSchema
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_M_DiscountSchema extends PO implements I_M_DiscountSchema, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150731L;

    /** Standard Constructor */
    public X_M_DiscountSchema (Properties ctx, int M_DiscountSchema_ID, String trxName)
    {
      super (ctx, M_DiscountSchema_ID, trxName);
      /** if (M_DiscountSchema_ID == 0)
        {
			setBudgetType (null);
// NB
			setDiscountType (null);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setGenerateSalesBudget (null);
// N
			setIsApproved (false);
// N
			setIsBirthdayDiscount (false);
// N
			setIsBPartnerFlatDiscount (false);
			setIsCashPayment (false);
// N
			setIsIncludingSubOrdinate (false);
// N
			setIsMustBeContinued (false);
// N
			setIsPickup (false);
// N
			setIsQuantityBased (true);
// Y
			setIsSOTrx (false);
// N
			setIsStandartDiscount (false);
// N
			setIsVendorCashback (false);
// N
			setM_DiscountSchema_ID (0);
			setName (null);
			setProcessed (false);
// N
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchema (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_DiscountSchema[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	public void setCopyFrom (String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	public String getCopyFrom () 
	{
		return (String)get_Value(COLUMNNAME_CopyFrom);
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DiscountType AD_Reference_ID=247 */
	public static final int DISCOUNTTYPE_AD_Reference_ID=247;
	/** Flat = F */
	public static final String DISCOUNTTYPE_Flat = "F";
	/** Formula = S */
	public static final String DISCOUNTTYPE_Formula = "S";
	/** Breaks = B */
	public static final String DISCOUNTTYPE_Breaks = "B";
	/** Pricelist = P */
	public static final String DISCOUNTTYPE_Pricelist = "P";
	/** UniCore Schema = U */
	public static final String DISCOUNTTYPE_UniCoreSchema = "U";
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Confirmed = CF */
	public static final String DOCACTION_Confirmed = "CF";
	/** Finished = FN */
	public static final String DOCACTION_Finished = "FN";
	/** Cancelled = CN */
	public static final String DOCACTION_Cancelled = "CN";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
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

	/** Set Flat Discount %.
		@param FlatDiscount 
		Flat discount percentage
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Flat Discount %.
		@return Flat discount percentage
	  */
	public BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Percent = P */
	public static final String FLATDISCOUNTTYPE_Percent = "P";
	/** Value/Currency = V */
	public static final String FLATDISCOUNTTYPE_ValueCurrency = "V";
	/** Product Bonuses = B */
	public static final String FLATDISCOUNTTYPE_ProductBonuses = "B";
	/** Set Flat Discount Type.
		@param FlatDiscountType Flat Discount Type	  */
	public void setFlatDiscountType (String FlatDiscountType)
	{

		set_Value (COLUMNNAME_FlatDiscountType, FlatDiscountType);
	}

	/** Get Flat Discount Type.
		@return Flat Discount Type	  */
	public String getFlatDiscountType () 
	{
		return (String)get_Value(COLUMNNAME_FlatDiscountType);
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

	/** Set Generate List.
		@param GenerateList 
		Generate List
	  */
	public void setGenerateList (String GenerateList)
	{
		set_Value (COLUMNNAME_GenerateList, GenerateList);
	}

	/** Get Generate List.
		@return Generate List
	  */
	public String getGenerateList () 
	{
		return (String)get_Value(COLUMNNAME_GenerateList);
	}

	/** Set Generate Sales Budget.
		@param GenerateSalesBudget Generate Sales Budget	  */
	public void setGenerateSalesBudget (String GenerateSalesBudget)
	{
		set_Value (COLUMNNAME_GenerateSalesBudget, GenerateSalesBudget);
	}

	/** Get Generate Sales Budget.
		@return Generate Sales Budget	  */
	public String getGenerateSalesBudget () 
	{
		return (String)get_Value(COLUMNNAME_GenerateSalesBudget);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Cash Payment.
		@param IsCashPayment Cash Payment	  */
	public void setIsCashPayment (boolean IsCashPayment)
	{
		set_Value (COLUMNNAME_IsCashPayment, Boolean.valueOf(IsCashPayment));
	}

	/** Get Cash Payment.
		@return Cash Payment	  */
	public boolean isCashPayment () 
	{
		Object oo = get_Value(COLUMNNAME_IsCashPayment);
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

	/** Set Must Be Continued.
		@param IsMustBeContinued Must Be Continued	  */
	public void setIsMustBeContinued (boolean IsMustBeContinued)
	{
		set_Value (COLUMNNAME_IsMustBeContinued, Boolean.valueOf(IsMustBeContinued));
	}

	/** Get Must Be Continued.
		@return Must Be Continued	  */
	public boolean isMustBeContinued () 
	{
		Object oo = get_Value(COLUMNNAME_IsMustBeContinued);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pickup.
		@param IsPickup Pickup	  */
	public void setIsPickup (boolean IsPickup)
	{
		set_Value (COLUMNNAME_IsPickup, Boolean.valueOf(IsPickup));
	}

	/** Get Pickup.
		@return Pickup	  */
	public boolean isPickup () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickup);
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

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standart Discount.
		@param IsStandartDiscount 
		This is a Standart Discount will calculate before other Discount
	  */
	public void setIsStandartDiscount (boolean IsStandartDiscount)
	{
		set_Value (COLUMNNAME_IsStandartDiscount, Boolean.valueOf(IsStandartDiscount));
	}

	/** Get Standart Discount.
		@return This is a Standart Discount will calculate before other Discount
	  */
	public boolean isStandartDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsStandartDiscount);
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

	/** Set M_DiscountSchema_UU.
		@param M_DiscountSchema_UU M_DiscountSchema_UU	  */
	public void setM_DiscountSchema_UU (String M_DiscountSchema_UU)
	{
		set_Value (COLUMNNAME_M_DiscountSchema_UU, M_DiscountSchema_UU);
	}

	/** Get M_DiscountSchema_UU.
		@return M_DiscountSchema_UU	  */
	public String getM_DiscountSchema_UU () 
	{
		return (String)get_Value(COLUMNNAME_M_DiscountSchema_UU);
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

	/** Organization Schema Will be Edite After = WEA */
	public static final String ORGANIZATIONALEFFECTIVENESS_OrganizationSchemaWillBeEditeAfter = "WEA";
	/** Overwritten With Organization Schema. = WOS */
	public static final String ORGANIZATIONALEFFECTIVENESS_OverwrittenWithOrganizationSchema = "WOS";
	/** Exclude Selected Organization = ESO */
	public static final String ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization = "ESO";
	/** Included Selected Organization = ISO */
	public static final String ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization = "ISO";
	/** Organization Schema Will be Edite Before = WEB */
	public static final String ORGANIZATIONALEFFECTIVENESS_OrganizationSchemaWillBeEditeBefore = "WEB";
	/** Set organizational effectiveness.
		@param organizationaleffectiveness organizational effectiveness	  */
	public void setorganizationaleffectiveness (String organizationaleffectiveness)
	{

		set_Value (COLUMNNAME_organizationaleffectiveness, organizationaleffectiveness);
	}

	/** Get organizational effectiveness.
		@return organizational effectiveness	  */
	public String getorganizationaleffectiveness () 
	{
		return (String)get_Value(COLUMNNAME_organizationaleffectiveness);
	}

	public com.unicore.model.I_M_DiscountSchema getPreviousSchema() throws RuntimeException
    {
		return (com.unicore.model.I_M_DiscountSchema)MTable.get(getCtx(), com.unicore.model.I_M_DiscountSchema.Table_Name)
			.getPO(getPreviousSchema_ID(), get_TrxName());	}

	/** Set Previous Schema.
		@param PreviousSchema_ID 
		The previous discount schema to be closed and replaced with this new one.
	  */
	public void setPreviousSchema_ID (int PreviousSchema_ID)
	{
		if (PreviousSchema_ID < 1) 
			set_Value (COLUMNNAME_PreviousSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PreviousSchema_ID, Integer.valueOf(PreviousSchema_ID));
	}

	/** Get Previous Schema.
		@return The previous discount schema to be closed and replaced with this new one.
	  */
	public int getPreviousSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PreviousSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
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

	/** Set Script.
		@param Script 
		Dynamic Java Language Script to calculate result
	  */
	public void setScript (String Script)
	{
		set_Value (COLUMNNAME_Script, Script);
	}

	/** Get Script.
		@return Dynamic Java Language Script to calculate result
	  */
	public String getScript () 
	{
		return (String)get_Value(COLUMNNAME_Script);
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

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
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