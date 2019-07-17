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

/** Generated Model for UNS_IncentiveConfigRequire
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_IncentiveConfigRequire extends PO implements I_UNS_IncentiveConfigRequire, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180122L;

    /** Standard Constructor */
    public X_UNS_IncentiveConfigRequire (Properties ctx, int UNS_IncentiveConfigRequire_ID, String trxName)
    {
      super (ctx, UNS_IncentiveConfigRequire_ID, trxName);
      /** if (UNS_IncentiveConfigRequire_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_UNS_IncentiveConfigRequire (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_IncentiveConfigRequire[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
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

	public com.unicore.model.I_UNS_IncentiveConfig getUNS_IncentiveConfig() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_IncentiveConfig)MTable.get(getCtx(), com.unicore.model.I_UNS_IncentiveConfig.Table_Name)
			.getPO(getUNS_IncentiveConfig_ID(), get_TrxName());	}

	/** Set Incentive Configuration.
		@param UNS_IncentiveConfig_ID Incentive Configuration	  */
	public void setUNS_IncentiveConfig_ID (int UNS_IncentiveConfig_ID)
	{
		if (UNS_IncentiveConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfig_ID, Integer.valueOf(UNS_IncentiveConfig_ID));
	}

	/** Get Incentive Configuration.
		@return Incentive Configuration	  */
	public int getUNS_IncentiveConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Incentive Criteria.
		@param UNS_IncentiveConfigRequire_ID Incentive Criteria	  */
	public void setUNS_IncentiveConfigRequire_ID (int UNS_IncentiveConfigRequire_ID)
	{
		if (UNS_IncentiveConfigRequire_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigRequire_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigRequire_ID, Integer.valueOf(UNS_IncentiveConfigRequire_ID));
	}

	/** Get Incentive Criteria.
		@return Incentive Criteria	  */
	public int getUNS_IncentiveConfigRequire_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveConfigRequire_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_IncentiveConfigRequire_UU.
		@param UNS_IncentiveConfigRequire_UU UNS_IncentiveConfigRequire_UU	  */
	public void setUNS_IncentiveConfigRequire_UU (String UNS_IncentiveConfigRequire_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigRequire_UU, UNS_IncentiveConfigRequire_UU);
	}

	/** Get UNS_IncentiveConfigRequire_UU.
		@return UNS_IncentiveConfigRequire_UU	  */
	public String getUNS_IncentiveConfigRequire_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_IncentiveConfigRequire_UU);
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