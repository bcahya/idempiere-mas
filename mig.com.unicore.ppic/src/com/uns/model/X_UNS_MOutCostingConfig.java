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

/** Generated Model for UNS_MOutCostingConfig
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MOutCostingConfig extends PO implements I_UNS_MOutCostingConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131017L;

    /** Standard Constructor */
    public X_UNS_MOutCostingConfig (Properties ctx, int UNS_MOutCostingConfig_ID, String trxName)
    {
      super (ctx, UNS_MOutCostingConfig_ID, trxName);
      /** if (UNS_MOutCostingConfig_ID == 0)
        {
			setCostingMethod (null);
// 1
			setM_Product_ID (0);
			setName (null);
			setUNS_MOutCostingConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MOutCostingConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MOutCostingConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** ConversionBased AD_Reference_ID=1000043 */
	public static final int CONVERSIONBASED_AD_Reference_ID=1000043;
	/** Price at Trx Date = 1 */
	public static final String CONVERSIONBASED_PriceAtTrxDate = "1";
	/** Current Cost Price of Conversion Product = 2 */
	public static final String CONVERSIONBASED_CurrentCostPriceOfConversionProduct = "2";
	/** Set Conversion Based.
		@param ConversionBased Conversion Based	  */
	public void setConversionBased (String ConversionBased)
	{

		set_Value (COLUMNNAME_ConversionBased, ConversionBased);
	}

	/** Get Conversion Based.
		@return Conversion Based	  */
	public String getConversionBased () 
	{
		return (String)get_Value(COLUMNNAME_ConversionBased);
	}

	/** Set Conversion Percent.
		@param ConversionPercent Conversion Percent	  */
	public void setConversionPercent (BigDecimal ConversionPercent)
	{
		set_Value (COLUMNNAME_ConversionPercent, ConversionPercent);
	}

	/** Get Conversion Percent.
		@return Conversion Percent	  */
	public BigDecimal getConversionPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConversionPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getConversionProduct() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getConversionProduct_ID(), get_TrxName());	}

	/** Set Conversion Product.
		@param ConversionProduct_ID Conversion Product	  */
	public void setConversionProduct_ID (int ConversionProduct_ID)
	{
		if (ConversionProduct_ID < 1) 
			set_Value (COLUMNNAME_ConversionProduct_ID, null);
		else 
			set_Value (COLUMNNAME_ConversionProduct_ID, Integer.valueOf(ConversionProduct_ID));
	}

	/** Get Conversion Product.
		@return Conversion Product	  */
	public int getConversionProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ConversionProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CostingMethod AD_Reference_ID=1000042 */
	public static final int COSTINGMETHOD_AD_Reference_ID=1000042;
	/** Standart Costing = 1 */
	public static final String COSTINGMETHOD_StandartCosting = "1";
	/** Conversion of product = 2 */
	public static final String COSTINGMETHOD_ConversionOfProduct = "2";
	/** Set Costing Method.
		@param CostingMethod 
		Indicates how Costs will be calculated
	  */
	public void setCostingMethod (String CostingMethod)
	{

		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	/** Get Costing Method.
		@return Indicates how Costs will be calculated
	  */
	public String getCostingMethod () 
	{
		return (String)get_Value(COLUMNNAME_CostingMethod);
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
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

	public org.compiere.model.I_M_Product getM_Product_To() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_To_ID(), get_TrxName());	}

	/** Set To Product.
		@param M_Product_To_ID 
		Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public void setM_Product_To_ID (int M_Product_To_ID)
	{
		if (M_Product_To_ID < 1) 
			set_Value (COLUMNNAME_M_Product_To_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_To_ID, Integer.valueOf(M_Product_To_ID));
	}

	/** Get To Product.
		@return Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public int getM_Product_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Standart Costing.
		@param StandartCosting Standart Costing	  */
	public void setStandartCosting (BigDecimal StandartCosting)
	{
		set_Value (COLUMNNAME_StandartCosting, StandartCosting);
	}

	/** Get Standart Costing.
		@return Standart Costing	  */
	public BigDecimal getStandartCosting () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_StandartCosting);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Multi Output Costing.
		@param UNS_MOutCostingConfig_ID Multi Output Costing	  */
	public void setUNS_MOutCostingConfig_ID (int UNS_MOutCostingConfig_ID)
	{
		if (UNS_MOutCostingConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MOutCostingConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MOutCostingConfig_ID, Integer.valueOf(UNS_MOutCostingConfig_ID));
	}

	/** Get Multi Output Costing.
		@return Multi Output Costing	  */
	public int getUNS_MOutCostingConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MOutCostingConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MOutCostingConfig_UU.
		@param UNS_MOutCostingConfig_UU UNS_MOutCostingConfig_UU	  */
	public void setUNS_MOutCostingConfig_UU (String UNS_MOutCostingConfig_UU)
	{
		set_Value (COLUMNNAME_UNS_MOutCostingConfig_UU, UNS_MOutCostingConfig_UU);
	}

	/** Get UNS_MOutCostingConfig_UU.
		@return UNS_MOutCostingConfig_UU	  */
	public String getUNS_MOutCostingConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MOutCostingConfig_UU);
	}
}