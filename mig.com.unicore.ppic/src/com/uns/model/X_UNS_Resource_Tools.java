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

/** Generated Model for UNS_Resource_Tools
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Resource_Tools extends PO implements I_UNS_Resource_Tools, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130111L;

    /** Standard Constructor */
    public X_UNS_Resource_Tools (Properties ctx, int UNS_Resource_Tools_ID, String trxName)
    {
      super (ctx, UNS_Resource_Tools_ID, trxName);
      /** if (UNS_Resource_Tools_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setMinStock (Env.ZERO);
			setUNS_Resource_ID (0);
			setUNS_Resource_Tools_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_Tools (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_Tools[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Min. Stock.
		@param MinStock Min. Stock	  */
	public void setMinStock (BigDecimal MinStock)
	{
		set_Value (COLUMNNAME_MinStock, MinStock);
	}

	/** Get Min. Stock.
		@return Min. Stock	  */
	public BigDecimal getMinStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Manufacture Resource Tools.
		@param UNS_Resource_Tools_ID Manufacture Resource Tools	  */
	public void setUNS_Resource_Tools_ID (int UNS_Resource_Tools_ID)
	{
		if (UNS_Resource_Tools_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Tools_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_Tools_ID, Integer.valueOf(UNS_Resource_Tools_ID));
	}

	/** Get Manufacture Resource Tools.
		@return Manufacture Resource Tools	  */
	public int getUNS_Resource_Tools_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Tools_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Resource_Tools_UU.
		@param UNS_Resource_Tools_UU UNS_Resource_Tools_UU	  */
	public void setUNS_Resource_Tools_UU (String UNS_Resource_Tools_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_Tools_UU, UNS_Resource_Tools_UU);
	}

	/** Get UNS_Resource_Tools_UU.
		@return UNS_Resource_Tools_UU	  */
	public String getUNS_Resource_Tools_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_Tools_UU);
	}
}