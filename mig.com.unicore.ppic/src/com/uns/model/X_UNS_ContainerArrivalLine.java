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

/** Generated Model for UNS_ContainerArrivalLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ContainerArrivalLine extends PO implements I_UNS_ContainerArrivalLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130614L;

    /** Standard Constructor */
    public X_UNS_ContainerArrivalLine (Properties ctx, int UNS_ContainerArrivalLine_ID, String trxName)
    {
      super (ctx, UNS_ContainerArrivalLine_ID, trxName);
      /** if (UNS_ContainerArrivalLine_ID == 0)
        {
			setC_OrderLine_ID (0);
			setC_UOM_ID (0);
			setM_InOutLine_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
// N
			setQty (Env.ZERO);
			setUNS_ContainerArrival_ID (0);
			setUNS_ContainerArrivalLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ContainerArrivalLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ContainerArrivalLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Purchase Order Line.
		@param C_OrderLine_ID 
		Purchase Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Purchase Order Line.
		@return Purchase Order Line
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

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
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

	/** Set Unloading Line.
		@param UNS_BongkarMuatLine_ID Unloading Line	  */
	public void setUNS_BongkarMuatLine_ID (int UNS_BongkarMuatLine_ID)
	{
		if (UNS_BongkarMuatLine_ID < 1) 
			set_Value (COLUMNNAME_UNS_BongkarMuatLine_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BongkarMuatLine_ID, Integer.valueOf(UNS_BongkarMuatLine_ID));
	}

	/** Get Unloading Line.
		@return Unloading Line	  */
	public int getUNS_BongkarMuatLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BongkarMuatLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_ContainerArrival getUNS_ContainerArrival() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ContainerArrival)MTable.get(getCtx(), com.uns.model.I_UNS_ContainerArrival.Table_Name)
			.getPO(getUNS_ContainerArrival_ID(), get_TrxName());	}

	/** Set Container Arrival.
		@param UNS_ContainerArrival_ID Container Arrival	  */
	public void setUNS_ContainerArrival_ID (int UNS_ContainerArrival_ID)
	{
		if (UNS_ContainerArrival_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrival_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrival_ID, Integer.valueOf(UNS_ContainerArrival_ID));
	}

	/** Get Container Arrival.
		@return Container Arrival	  */
	public int getUNS_ContainerArrival_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerArrival_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Arrival Line.
		@param UNS_ContainerArrivalLine_ID Container Arrival Line	  */
	public void setUNS_ContainerArrivalLine_ID (int UNS_ContainerArrivalLine_ID)
	{
		if (UNS_ContainerArrivalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrivalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrivalLine_ID, Integer.valueOf(UNS_ContainerArrivalLine_ID));
	}

	/** Get Container Arrival Line.
		@return Container Arrival Line	  */
	public int getUNS_ContainerArrivalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ContainerArrivalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container Arrival Line UU.
		@param UNS_ContainerArrivalLine_UU Container Arrival Line UU	  */
	public void setUNS_ContainerArrivalLine_UU (String UNS_ContainerArrivalLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ContainerArrivalLine_UU, UNS_ContainerArrivalLine_UU);
	}

	/** Get Container Arrival Line UU.
		@return Container Arrival Line UU	  */
	public String getUNS_ContainerArrivalLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ContainerArrivalLine_UU);
	}
}