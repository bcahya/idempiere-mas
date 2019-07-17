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

/** Generated Model for UNS_PL_ConfirmProduct
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ConfirmProduct extends PO implements I_UNS_PL_ConfirmProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150304L;

    /** Standard Constructor */
    public X_UNS_PL_ConfirmProduct (Properties ctx, int UNS_PL_ConfirmProduct_ID, String trxName)
    {
      super (ctx, UNS_PL_ConfirmProduct_ID, trxName);
      /** if (UNS_PL_ConfirmProduct_ID == 0)
        {
			setConfirmedQty (Env.ZERO);
			setC_UOM_ID (0);
			setDifferenceQty (Env.ZERO);
			setIsApproved (false);
// N
			setM_Locator_ID (0);
			setMovementQty (Env.ZERO);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setTargetQty (Env.ZERO);
			setUNS_PL_Confirm_ID (0);
			setUNS_PL_ConfirmProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ConfirmProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ConfirmProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Confirmed Quantity.
		@param ConfirmedQty 
		Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	/** Get Confirmed Quantity.
		@return Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Difference.
		@param DifferenceQty 
		Difference Quantity
	  */
	public void setDifferenceQty (BigDecimal DifferenceQty)
	{
		set_Value (COLUMNNAME_DifferenceQty, DifferenceQty);
	}

	/** Get Difference.
		@return Difference Quantity
	  */
	public BigDecimal getDifferenceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Target Quantity.
		@param TargetQty 
		Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	/** Get Target Quantity.
		@return Target Movement Quantity
	  */
	public BigDecimal getTargetQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_PL_Confirm getUNS_PL_Confirm() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PL_Confirm)MTable.get(getCtx(), com.unicore.model.I_UNS_PL_Confirm.Table_Name)
			.getPO(getUNS_PL_Confirm_ID(), get_TrxName());	}

	/** Set Packing List Confirmation.
		@param UNS_PL_Confirm_ID Packing List Confirmation	  */
	public void setUNS_PL_Confirm_ID (int UNS_PL_Confirm_ID)
	{
		if (UNS_PL_Confirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Confirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Confirm_ID, Integer.valueOf(UNS_PL_Confirm_ID));
	}

	/** Get Packing List Confirmation.
		@return Packing List Confirmation	  */
	public int getUNS_PL_Confirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_Confirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Confirmation Product.
		@param UNS_PL_ConfirmProduct_ID Confirmation Product	  */
	public void setUNS_PL_ConfirmProduct_ID (int UNS_PL_ConfirmProduct_ID)
	{
		if (UNS_PL_ConfirmProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, Integer.valueOf(UNS_PL_ConfirmProduct_ID));
	}

	/** Get Confirmation Product.
		@return Confirmation Product	  */
	public int getUNS_PL_ConfirmProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ConfirmProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PL_ConfirmProduct_UU.
		@param UNS_PL_ConfirmProduct_UU UNS_PL_ConfirmProduct_UU	  */
	public void setUNS_PL_ConfirmProduct_UU (String UNS_PL_ConfirmProduct_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_UU, UNS_PL_ConfirmProduct_UU);
	}

	/** Get UNS_PL_ConfirmProduct_UU.
		@return UNS_PL_ConfirmProduct_UU	  */
	public String getUNS_PL_ConfirmProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ConfirmProduct_UU);
	}
}