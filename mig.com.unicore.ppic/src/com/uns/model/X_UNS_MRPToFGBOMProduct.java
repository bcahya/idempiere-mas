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

/** Generated Model for UNS_MRPToFGBOMProduct
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MRPToFGBOMProduct extends PO implements I_UNS_MRPToFGBOMProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131013L;

    /** Standard Constructor */
    public X_UNS_MRPToFGBOMProduct (Properties ctx, int UNS_MRPToFGBOMProduct_ID, String trxName)
    {
      super (ctx, UNS_MRPToFGBOMProduct_ID, trxName);
      /** if (UNS_MRPToFGBOMProduct_ID == 0)
        {
			setBOMQty (Env.ZERO);
			setIsMainPart (false);
// N
			setLinkOfRejectMaterial (false);
// N
			setM_ProductBOM_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
			setUNS_MRPToFGBOMProduct_ID (0);
			setUNS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MRPToFGBOMProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MRPToFGBOMProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BOM Quantity.
		@param BOMQty 
		Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty)
	{
		set_Value (COLUMNNAME_BOMQty, BOMQty);
	}

	/** Get BOM Quantity.
		@return Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BOMQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Is Main Part.
		@param IsMainPart 
		To indicate if material is main part of product's BOM
	  */
	public void setIsMainPart (boolean IsMainPart)
	{
		set_Value (COLUMNNAME_IsMainPart, Boolean.valueOf(IsMainPart));
	}

	/** Get Is Main Part.
		@return To indicate if material is main part of product's BOM
	  */
	public boolean isMainPart () 
	{
		Object oo = get_Value(COLUMNNAME_IsMainPart);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set FG is Primary Output.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_Value (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get FG is Primary Output.
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

	/** Set Link Of Reject Material.
		@param LinkOfRejectMaterial 
		The material is linked to the product by the predicted reject material.
	  */
	public void setLinkOfRejectMaterial (boolean LinkOfRejectMaterial)
	{
		set_ValueNoCheck (COLUMNNAME_LinkOfRejectMaterial, Boolean.valueOf(LinkOfRejectMaterial));
	}

	/** Get Link Of Reject Material.
		@return The material is linked to the product by the predicted reject material.
	  */
	public boolean isLinkOfRejectMaterial () 
	{
		Object oo = get_Value(COLUMNNAME_LinkOfRejectMaterial);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product getM_ProductBOM() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_ProductBOM_ID(), get_TrxName());	}

	/** Set BOM Product.
		@param M_ProductBOM_ID 
		Bill of Material Component Product
	  */
	public void setM_ProductBOM_ID (int M_ProductBOM_ID)
	{
		if (M_ProductBOM_ID < 1) 
			set_Value (COLUMNNAME_M_ProductBOM_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductBOM_ID, Integer.valueOf(M_ProductBOM_ID));
	}

	/** Get BOM Product.
		@return Bill of Material Component Product
	  */
	public int getM_ProductBOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductBOM_ID);
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

	/** Set Mapping MRP to FG BOM.
		@param UNS_MRPToFGBOMProduct_ID Mapping MRP to FG BOM	  */
	public void setUNS_MRPToFGBOMProduct_ID (int UNS_MRPToFGBOMProduct_ID)
	{
		if (UNS_MRPToFGBOMProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MRPToFGBOMProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MRPToFGBOMProduct_ID, Integer.valueOf(UNS_MRPToFGBOMProduct_ID));
	}

	/** Get Mapping MRP to FG BOM.
		@return Mapping MRP to FG BOM	  */
	public int getUNS_MRPToFGBOMProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MRPToFGBOMProduct_ID);
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

	/** Set uns_mrptofgbomproduct_uu.
		@param uns_mrptofgbomproduct_uu uns_mrptofgbomproduct_uu	  */
	public void setuns_mrptofgbomproduct_uu (String uns_mrptofgbomproduct_uu)
	{
		set_Value (COLUMNNAME_uns_mrptofgbomproduct_uu, uns_mrptofgbomproduct_uu);
	}

	/** Get uns_mrptofgbomproduct_uu.
		@return uns_mrptofgbomproduct_uu	  */
	public String getuns_mrptofgbomproduct_uu () 
	{
		return (String)get_Value(COLUMNNAME_uns_mrptofgbomproduct_uu);
	}
}