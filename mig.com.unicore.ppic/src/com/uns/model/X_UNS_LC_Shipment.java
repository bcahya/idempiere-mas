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

/** Generated Model for UNS_LC_Shipment
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_Shipment extends PO implements I_UNS_LC_Shipment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130506L;

    /** Standard Constructor */
    public X_UNS_LC_Shipment (Properties ctx, int UNS_LC_Shipment_ID, String trxName)
    {
      super (ctx, UNS_LC_Shipment_ID, trxName);
      /** if (UNS_LC_Shipment_ID == 0)
        {
			setC_UOM_ID (0);
// @#C_UOM_ID@
			setM_AttributeSetInstance_ID (0);
			setM_InOut_ID (0);
			setUNS_LC_Lines_ID (0);
			setUNS_LC_Shipment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_Shipment (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_Shipment[")
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_LC_Lines getUNS_LC_Lines() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Lines)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Lines.Table_Name)
			.getPO(getUNS_LC_Lines_ID(), get_TrxName());	}

	/** Set LC Lines.
		@param UNS_LC_Lines_ID LC Lines	  */
	public void setUNS_LC_Lines_ID (int UNS_LC_Lines_ID)
	{
		if (UNS_LC_Lines_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Lines_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Lines_ID, Integer.valueOf(UNS_LC_Lines_ID));
	}

	/** Get LC Lines.
		@return LC Lines	  */
	public int getUNS_LC_Lines_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Lines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LC Shipment.
		@param UNS_LC_Shipment_ID LC Shipment	  */
	public void setUNS_LC_Shipment_ID (int UNS_LC_Shipment_ID)
	{
		if (UNS_LC_Shipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Shipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Shipment_ID, Integer.valueOf(UNS_LC_Shipment_ID));
	}

	/** Get LC Shipment.
		@return LC Shipment	  */
	public int getUNS_LC_Shipment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Shipment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_Shipment_UU.
		@param UNS_LC_Shipment_UU UNS_LC_Shipment_UU	  */
	public void setUNS_LC_Shipment_UU (String UNS_LC_Shipment_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_Shipment_UU, UNS_LC_Shipment_UU);
	}

	/** Get UNS_LC_Shipment_UU.
		@return UNS_LC_Shipment_UU	  */
	public String getUNS_LC_Shipment_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_Shipment_UU);
	}
}