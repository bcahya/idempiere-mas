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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Production_OutPlan
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Production_OutPlan extends PO implements I_UNS_Production_OutPlan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150224L;

    /** Standard Constructor */
    public X_UNS_Production_OutPlan (Properties ctx, int UNS_Production_OutPlan_ID, String trxName)
    {
      super (ctx, UNS_Production_OutPlan_ID, trxName);
      /** if (UNS_Production_OutPlan_ID == 0)
        {
			setBOMType (null);
// @0|BOMType@
			setC_UOM_ID (0);
			setLine (0);
// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM UNS_Production_OutPlan WHERE UNS_Production_ID=@UNS_Production_ID@
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setUNS_Production_OutPlan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_OutPlan (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_OutPlan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BOMType AD_Reference_ID=279 */
	public static final int BOMTYPE_AD_Reference_ID=279;
	/** Standard Part = 0.P */
	public static final String BOMTYPE_StandardPart = "0.P";
	/** Optional Part = O */
	public static final String BOMTYPE_OptionalPart = "O";
	/** In alternative Group 1 = 1 */
	public static final String BOMTYPE_InAlternativeGroup1 = "1";
	/** In alternative Group 2 = 2 */
	public static final String BOMTYPE_InAlternativeGroup2 = "2";
	/** In alternaltve Group 3 = 3 */
	public static final String BOMTYPE_InAlternaltveGroup3 = "3";
	/** In alternative Group 4 = 4 */
	public static final String BOMTYPE_InAlternativeGroup4 = "4";
	/** In alternative Group 5 = 5 */
	public static final String BOMTYPE_InAlternativeGroup5 = "5";
	/** In alternative Group 6 = 6 */
	public static final String BOMTYPE_InAlternativeGroup6 = "6";
	/** In alternative Group 7 = 7 */
	public static final String BOMTYPE_InAlternativeGroup7 = "7";
	/** In alternative Group 8 = 8 */
	public static final String BOMTYPE_InAlternativeGroup8 = "8";
	/** In alternative Group 9 = 9 */
	public static final String BOMTYPE_InAlternativeGroup9 = "9";
	/** Predicted Reject Material = R */
	public static final String BOMTYPE_PredictedRejectMaterial = "R";
	/** WIP Bihun - F1 = WPBHN1 */
	public static final String BOMTYPE_WIPBihun_F1 = "WPBHN1";
	/** WIP Bihun - F2 = WPBHN2 */
	public static final String BOMTYPE_WIPBihun_F2 = "WPBHN2";
	/** WIP Bihun - F3 = WPBHN3 */
	public static final String BOMTYPE_WIPBihun_F3 = "WPBHN3";
	/** Set BOM Type.
		@param BOMType 
		Type of BOM
	  */
	public void setBOMType (String BOMType)
	{

		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	/** Get BOM Type.
		@return Type of BOM
	  */
	public String getBOMType () 
	{
		return (String)get_Value(COLUMNNAME_BOMType);
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

	/** Set Primary.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_Value (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Primary.
		@return Indicates if this is the primary budget
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_Product_ID()));
    }

	/** Set Primary Output.
		@param PrimaryOutput_ID 
		The related primary output of this non-primary output.
	  */
	public void setPrimaryOutput_ID (int PrimaryOutput_ID)
	{
		if (PrimaryOutput_ID < 1) 
			set_Value (COLUMNNAME_PrimaryOutput_ID, null);
		else 
			set_Value (COLUMNNAME_PrimaryOutput_ID, Integer.valueOf(PrimaryOutput_ID));
	}

	/** Get Primary Output.
		@return The related primary output of this non-primary output.
	  */
	public int getPrimaryOutput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrimaryOutput_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity Plan.
		@param QtyPlan 
		Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan)
	{
		set_Value (COLUMNNAME_QtyPlan, QtyPlan);
	}

	/** Get Quantity Plan.
		@return Planned Quantity
	  */
	public BigDecimal getQtyPlan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production)MTable.get(getCtx(), com.uns.model.I_UNS_Production.Table_Name)
			.getPO(getUNS_Production_ID(), get_TrxName());	}

	/** Set Production.
		@param UNS_Production_ID Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID)
	{
		if (UNS_Production_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, Integer.valueOf(UNS_Production_ID));
	}

	/** Get Production.
		@return Production	  */
	public int getUNS_Production_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Output Plan.
		@param UNS_Production_OutPlan_ID Production Output Plan	  */
	public void setUNS_Production_OutPlan_ID (int UNS_Production_OutPlan_ID)
	{
		if (UNS_Production_OutPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_OutPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_OutPlan_ID, Integer.valueOf(UNS_Production_OutPlan_ID));
	}

	/** Get Production Output Plan.
		@return Production Output Plan	  */
	public int getUNS_Production_OutPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_OutPlan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Production_OutPlan_UU.
		@param UNS_Production_OutPlan_UU UNS_Production_OutPlan_UU	  */
	public void setUNS_Production_OutPlan_UU (String UNS_Production_OutPlan_UU)
	{
		set_Value (COLUMNNAME_UNS_Production_OutPlan_UU, UNS_Production_OutPlan_UU);
	}

	/** Get UNS_Production_OutPlan_UU.
		@return UNS_Production_OutPlan_UU	  */
	public String getUNS_Production_OutPlan_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_OutPlan_UU);
	}

	public com.uns.model.I_UNS_Resource_InOut getUNS_Resource_InOut() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource_InOut)MTable.get(getCtx(), com.uns.model.I_UNS_Resource_InOut.Table_Name)
			.getPO(getUNS_Resource_InOut_ID(), get_TrxName());	}

	/** Set Manufacture Resource InOut.
		@param UNS_Resource_InOut_ID Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID)
	{
		if (UNS_Resource_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, Integer.valueOf(UNS_Resource_InOut_ID));
	}

	/** Get Manufacture Resource InOut.
		@return Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}