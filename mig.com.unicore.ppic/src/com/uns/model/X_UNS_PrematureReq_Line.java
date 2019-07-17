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

/** Generated Model for UNS_PrematureReq_Line
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PrematureReq_Line extends PO implements I_UNS_PrematureReq_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130605L;

    /** Standard Constructor */
    public X_UNS_PrematureReq_Line (Properties ctx, int UNS_PrematureReq_Line_ID, String trxName)
    {
      super (ctx, UNS_PrematureReq_Line_ID, trxName);
      /** if (UNS_PrematureReq_Line_ID == 0)
        {
			setApprovedQty (Env.ZERO);
// 0
			setM_AttributeSetInstance_ID (0);
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setOnHoldQty (Env.ZERO);
			setProcessed (false);
// N
			setReleaseQty (Env.ZERO);
			setRequestedQty (Env.ZERO);
			setUNS_PrematureReq_Line_ID (0);
			setUNS_PrematureRequest_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PrematureReq_Line (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_PrematureReq_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Approved Qty.
		@param ApprovedQty 
		Quantity to be approved for the requested qty by user/department
	  */
	public void setApprovedQty (BigDecimal ApprovedQty)
	{
		set_ValueNoCheck (COLUMNNAME_ApprovedQty, ApprovedQty);
	}

	/** Get Approved Qty.
		@return Quantity to be approved for the requested qty by user/department
	  */
	public BigDecimal getApprovedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ApprovedQty);
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

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
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

	/** Set Quantity On Hold.
		@param OnHoldQty Quantity On Hold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty)
	{
		set_Value (COLUMNNAME_OnHoldQty, OnHoldQty);
	}

	/** Get Quantity On Hold.
		@return Quantity On Hold	  */
	public BigDecimal getOnHoldQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OnHoldQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Packages.
		@param Packages 
		Packages numbers in string sequence format (i.e.: 1-100; 210-250; 254; 256)
	  */
	public void setPackages (String Packages)
	{
		set_Value (COLUMNNAME_Packages, Packages);
	}

	/** Get Packages.
		@return Packages numbers in string sequence format (i.e.: 1-100; 210-250; 254; 256)
	  */
	public String getPackages () 
	{
		return (String)get_Value(COLUMNNAME_Packages);
	}

	/** Set Pallets.
		@param Pallets 
		Pallet numbers in string sequence format (i.e.: 1-10; 12; 14).
	  */
	public void setPallets (String Pallets)
	{
		set_Value (COLUMNNAME_Pallets, Pallets);
	}

	/** Get Pallets.
		@return Pallet numbers in string sequence format (i.e.: 1-10; 12; 14).
	  */
	public String getPallets () 
	{
		return (String)get_Value(COLUMNNAME_Pallets);
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

	/** Set Quantity Release.
		@param ReleaseQty Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty)
	{
		set_Value (COLUMNNAME_ReleaseQty, ReleaseQty);
	}

	/** Get Quantity Release.
		@return Quantity Release	  */
	public BigDecimal getReleaseQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReleaseQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Requested Qty.
		@param RequestedQty 
		Quantity requested by user/department
	  */
	public void setRequestedQty (BigDecimal RequestedQty)
	{
		set_Value (COLUMNNAME_RequestedQty, RequestedQty);
	}

	/** Get Requested Qty.
		@return Quantity requested by user/department
	  */
	public BigDecimal getRequestedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RequestedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Premature Release Line.
		@param UNS_PrematureReq_Line_ID Premature Release Line	  */
	public void setUNS_PrematureReq_Line_ID (int UNS_PrematureReq_Line_ID)
	{
		if (UNS_PrematureReq_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PrematureReq_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PrematureReq_Line_ID, Integer.valueOf(UNS_PrematureReq_Line_ID));
	}

	/** Get Premature Release Line.
		@return Premature Release Line	  */
	public int getUNS_PrematureReq_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PrematureReq_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PrematureReq_Line_UU.
		@param UNS_PrematureReq_Line_UU 
		UNS_PrematureReq_Line_UU
	  */
	public void setUNS_PrematureReq_Line_UU (String UNS_PrematureReq_Line_UU)
	{
		set_Value (COLUMNNAME_UNS_PrematureReq_Line_UU, UNS_PrematureReq_Line_UU);
	}

	/** Get UNS_PrematureReq_Line_UU.
		@return UNS_PrematureReq_Line_UU
	  */
	public String getUNS_PrematureReq_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PrematureReq_Line_UU);
	}

	public com.uns.model.I_UNS_PrematureRequest getUNS_PrematureRequest() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PrematureRequest)MTable.get(getCtx(), com.uns.model.I_UNS_PrematureRequest.Table_Name)
			.getPO(getUNS_PrematureRequest_ID(), get_TrxName());	}

	/** Set Premature Release Request.
		@param UNS_PrematureRequest_ID Premature Release Request	  */
	public void setUNS_PrematureRequest_ID (int UNS_PrematureRequest_ID)
	{
		if (UNS_PrematureRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PrematureRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PrematureRequest_ID, Integer.valueOf(UNS_PrematureRequest_ID));
	}

	/** Get Premature Release Request.
		@return Premature Release Request	  */
	public int getUNS_PrematureRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PrematureRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}