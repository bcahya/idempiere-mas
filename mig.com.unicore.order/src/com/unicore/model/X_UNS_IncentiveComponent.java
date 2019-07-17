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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_IncentiveComponent
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_IncentiveComponent extends PO implements I_UNS_IncentiveComponent, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180130L;

    /** Standard Constructor */
    public X_UNS_IncentiveComponent (Properties ctx, int UNS_IncentiveComponent_ID, String trxName)
    {
      super (ctx, UNS_IncentiveComponent_ID, trxName);
      /** if (UNS_IncentiveComponent_ID == 0)
        {
			setComponentType (null);
			setUNS_IncentiveComponent_ID (0);
			setUNS_Incentive_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_IncentiveComponent (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_IncentiveComponent[")
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

	public org.compiere.model.I_AD_Ref_List getAD_Ref_List() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Ref_List)MTable.get(getCtx(), org.compiere.model.I_AD_Ref_List.Table_Name)
			.getPO(getAD_Ref_List_ID(), get_TrxName());	}

	/** Set Reference List.
		@param AD_Ref_List_ID 
		Reference List based on Table
	  */
	public void setAD_Ref_List_ID (int AD_Ref_List_ID)
	{
		if (AD_Ref_List_ID < 1) 
			set_Value (COLUMNNAME_AD_Ref_List_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Ref_List_ID, Integer.valueOf(AD_Ref_List_ID));
	}

	/** Get Reference List.
		@return Reference List based on Table
	  */
	public int getAD_Ref_List_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Ref_List_ID);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_BP_Group_ID()));
    }

	/** Customer = C */
	public static final String COMPONENTTYPE_Customer = "C";
	/** Customer Group = CG */
	public static final String COMPONENTTYPE_CustomerGroup = "CG";
	/** Customer Type = CT */
	public static final String COMPONENTTYPE_CustomerType = "CT";
	/** Grade Customer = GC */
	public static final String COMPONENTTYPE_GradeCustomer = "GC";
	/** Organization = O */
	public static final String COMPONENTTYPE_Organization = "O";
	/** Product = P */
	public static final String COMPONENTTYPE_Product = "P";
	/** Product Category = PG */
	public static final String COMPONENTTYPE_ProductCategory = "PG";
	/** Rayon = R */
	public static final String COMPONENTTYPE_Rayon = "R";
	/** Tender Type = TT */
	public static final String COMPONENTTYPE_TenderType = "TT";
	/** Set Component Type.
		@param ComponentType 
		Component Type
	  */
	public void setComponentType (String ComponentType)
	{

		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	/** Get Component Type.
		@return Component Type
	  */
	public String getComponentType () 
	{
		return (String)get_Value(COLUMNNAME_ComponentType);
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

	/** Set Incentive Component.
		@param UNS_IncentiveComponent_ID Incentive Component	  */
	public void setUNS_IncentiveComponent_ID (int UNS_IncentiveComponent_ID)
	{
		if (UNS_IncentiveComponent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveComponent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveComponent_ID, Integer.valueOf(UNS_IncentiveComponent_ID));
	}

	/** Get Incentive Component.
		@return Incentive Component	  */
	public int getUNS_IncentiveComponent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveComponent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_IncentiveComponent_UU.
		@param UNS_IncentiveComponent_UU UNS_IncentiveComponent_UU	  */
	public void setUNS_IncentiveComponent_UU (String UNS_IncentiveComponent_UU)
	{
		set_Value (COLUMNNAME_UNS_IncentiveComponent_UU, UNS_IncentiveComponent_UU);
	}

	/** Get UNS_IncentiveComponent_UU.
		@return UNS_IncentiveComponent_UU	  */
	public String getUNS_IncentiveComponent_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_IncentiveComponent_UU);
	}

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Incentive)MTable.get(getCtx(), com.unicore.model.I_UNS_Incentive.Table_Name)
			.getPO(getUNS_Incentive_ID(), get_TrxName());	}

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
}