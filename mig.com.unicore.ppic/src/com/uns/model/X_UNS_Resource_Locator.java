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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_Resource_Locator
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Resource_Locator extends PO implements I_UNS_Resource_Locator, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130616L;

    /** Standard Constructor */
    public X_UNS_Resource_Locator (Properties ctx, int UNS_Resource_Locator_ID, String trxName)
    {
      super (ctx, UNS_Resource_Locator_ID, trxName);
      /** if (UNS_Resource_Locator_ID == 0)
        {
			setM_Locator_ID (0);
			setUNS_Resource_ID (0);
			setUNS_Resource_Locator_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_Locator (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_Locator[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Default Locator.
		@param IsDefault 
		Locator to be used as default input location.
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default Locator.
		@return Locator to be used as default input location.
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Input Locator.
		@param M_Locator_ID 
		Warehouse Locator of resource's materials input
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Input Locator.
		@return Warehouse Locator of resource's materials input
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

	/** Set Input Material.
		@param M_Product_ID 
		Production's raw, packaging, or ingredient material
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Input Material.
		@return Production's raw, packaging, or ingredient material
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacture Resource Locator.
		@param UNS_Resource_Locator_ID Manufacture Resource Locator	  */
	public void setUNS_Resource_Locator_ID (int UNS_Resource_Locator_ID)
	{
		if (UNS_Resource_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Locator_ID, Integer.valueOf(UNS_Resource_Locator_ID));
	}

	/** Get Manufacture Resource Locator.
		@return Manufacture Resource Locator	  */
	public int getUNS_Resource_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Resource_Locator_UU.
		@param UNS_Resource_Locator_UU UNS_Resource_Locator_UU	  */
	public void setUNS_Resource_Locator_UU (String UNS_Resource_Locator_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_Locator_UU, UNS_Resource_Locator_UU);
	}

	/** Get UNS_Resource_Locator_UU.
		@return UNS_Resource_Locator_UU	  */
	public String getUNS_Resource_Locator_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_Locator_UU);
	}
}