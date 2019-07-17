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

/** Generated Model for UNS_LoadingCost_Line
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LoadingCost_Line extends PO implements I_UNS_LoadingCost_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130303L;

    /** Standard Constructor */
    public X_UNS_LoadingCost_Line (Properties ctx, int UNS_LoadingCost_Line_ID, String trxName)
    {
      super (ctx, UNS_LoadingCost_Line_ID, trxName);
      /** if (UNS_LoadingCost_Line_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Order_ID (0);
			setC_OrderLine_ID (0);
			setC_UOM_ID (0);
			setIsDescription (false);
// N
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_BongkarMuatLine WHERE UNS_BongkarMuat_ID=@UNS_BongkarMuat_ID@
			setLineNetAmt (Env.ZERO);
			setM_Product_ID (0);
			setPriceCost (Env.ZERO);
			setProcessed (false);
// N
			setQty (Env.ZERO);
			setUNS_LoadingCost_ID (0);
			setUNS_LoadingCost_Line_ID (0);
			setUNS_SOShipment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LoadingCost_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LoadingCost_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Description Only.
		@param IsDescription 
		if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, Boolean.valueOf(IsDescription));
	}

	/** Get Description Only.
		@return if true, the line is just description and no transaction
	  */
	public boolean isDescription () 
	{
		Object oo = get_Value(COLUMNNAME_IsDescription);
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
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
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

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
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

	/** Set Cost Price.
		@param PriceCost 
		Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public void setPriceCost (BigDecimal PriceCost)
	{
		set_Value (COLUMNNAME_PriceCost, PriceCost);
	}

	/** Get Cost Price.
		@return Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public BigDecimal getPriceCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** TypeBongkar AD_Reference_ID=1000025 */
	public static final int TYPEBONGKAR_AD_Reference_ID=1000025;
	/** Bongkar Tanpa Alat = BTA */
	public static final String TYPEBONGKAR_HandlingManual = "BTA";
	/** Bongkar Dengan Forklift = BDF */
	public static final String TYPEBONGKAR_HandlingUsingForklift = "BDF";
	/** Bongkar Dengan Crane = BDC */
	public static final String TYPEBONGKAR_HandlingUsingCrane = "BDC";
	/** Disusun / Dibongkar = DSB */
	public static final String TYPEBONGKAR_DisusunDibongkar = "DSB";
	/** Set Type Bongkar.
		@param TypeBongkar Type Bongkar	  */
	public void setTypeBongkar (String TypeBongkar)
	{

		set_Value (COLUMNNAME_TypeBongkar, TypeBongkar);
	}

	/** Get Type Bongkar.
		@return Type Bongkar	  */
	public String getTypeBongkar () 
	{
		return (String)get_Value(COLUMNNAME_TypeBongkar);
	}

	/** Set uns_bongkarmuatline_uu.
		@param uns_bongkarmuatline_uu uns_bongkarmuatline_uu	  */
	public void setuns_bongkarmuatline_uu (String uns_bongkarmuatline_uu)
	{
		set_Value (COLUMNNAME_uns_bongkarmuatline_uu, uns_bongkarmuatline_uu);
	}

	/** Get uns_bongkarmuatline_uu.
		@return uns_bongkarmuatline_uu	  */
	public String getuns_bongkarmuatline_uu () 
	{
		return (String)get_Value(COLUMNNAME_uns_bongkarmuatline_uu);
	}

	public com.uns.model.I_UNS_LoadingCost getUNS_LoadingCost() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LoadingCost)MTable.get(getCtx(), com.uns.model.I_UNS_LoadingCost.Table_Name)
			.getPO(getUNS_LoadingCost_ID(), get_TrxName());	}

	/** Set Loading Cost.
		@param UNS_LoadingCost_ID Loading Cost	  */
	public void setUNS_LoadingCost_ID (int UNS_LoadingCost_ID)
	{
		if (UNS_LoadingCost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LoadingCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LoadingCost_ID, Integer.valueOf(UNS_LoadingCost_ID));
	}

	/** Get Loading Cost.
		@return Loading Cost	  */
	public int getUNS_LoadingCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LoadingCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Loading Cost Line.
		@param UNS_LoadingCost_Line_ID Loading Cost Line	  */
	public void setUNS_LoadingCost_Line_ID (int UNS_LoadingCost_Line_ID)
	{
		if (UNS_LoadingCost_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LoadingCost_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LoadingCost_Line_ID, Integer.valueOf(UNS_LoadingCost_Line_ID));
	}

	/** Get Loading Cost Line.
		@return Loading Cost Line	  */
	public int getUNS_LoadingCost_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LoadingCost_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Loading Cost Line UU.
		@param UNS_LoadingCost_Line_UU Loading Cost Line UU	  */
	public void setUNS_LoadingCost_Line_UU (String UNS_LoadingCost_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_LoadingCost_Line_UU, UNS_LoadingCost_Line_UU);
	}

	/** Get Loading Cost Line UU.
		@return Loading Cost Line UU	  */
	public String getUNS_LoadingCost_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LoadingCost_Line_UU);
	}

	/** Set Sales Order Shipment.
		@param UNS_SOShipment_ID Sales Order Shipment	  */
	public void setUNS_SOShipment_ID (int UNS_SOShipment_ID)
	{
		if (UNS_SOShipment_ID < 1) 
			set_Value (COLUMNNAME_UNS_SOShipment_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SOShipment_ID, Integer.valueOf(UNS_SOShipment_ID));
	}

	/** Get Sales Order Shipment.
		@return Sales Order Shipment	  */
	public int getUNS_SOShipment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SOShipment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}