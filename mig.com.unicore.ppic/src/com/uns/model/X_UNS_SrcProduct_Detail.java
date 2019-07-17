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

/** Generated Model for UNS_SrcProduct_Detail
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SrcProduct_Detail extends PO implements I_UNS_SrcProduct_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131102L;

    /** Standard Constructor */
    public X_UNS_SrcProduct_Detail (Properties ctx, int UNS_SrcProduct_Detail_ID, String trxName)
    {
      super (ctx, UNS_SrcProduct_Detail_ID, trxName);
      /** if (UNS_SrcProduct_Detail_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setQtyMT (Env.ZERO);
// 0
			setQtyUom (Env.ZERO);
// 0
			setUNS_ProductionSchedule_ID (0);
			setUNS_SrcProduct_Detail_ID (0);
			setUsedQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_SrcProduct_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SrcProduct_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Available Qty.
		@param AvailableQty 
		The quantity of the source product (OnHand - OnHold - NC)
	  */
	public void setAvailableQty (BigDecimal AvailableQty)
	{
		set_ValueNoCheck (COLUMNNAME_AvailableQty, AvailableQty);
	}

	/** Get Available Qty.
		@return The quantity of the source product (OnHand - OnHold - NC)
	  */
	public BigDecimal getAvailableQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AvailableQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (org.compiere.model.I_M_AttributeSetInstance)MTable.get(getCtx(), org.compiere.model.I_M_AttributeSetInstance.Table_Name)
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

	/** Set Raw Material-F Qty (MT).
		@param QtyMT 
		Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_Value (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Raw Material-F Qty (MT).
		@return Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Raw Material-F Qty (UOM).
		@param QtyUom 
		Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_Value (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Raw Material-F Qty (UOM).
		@return Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ProductionSchedule)MTable.get(getCtx(), com.uns.model.I_UNS_ProductionSchedule.Table_Name)
			.getPO(getUNS_ProductionSchedule_ID(), get_TrxName());	}

	/** Set Production Schedule.
		@param UNS_ProductionSchedule_ID Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID)
	{
		if (UNS_ProductionSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, Integer.valueOf(UNS_ProductionSchedule_ID));
	}

	/** Get Production Schedule.
		@return Production Schedule	  */
	public int getUNS_ProductionSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Product Detail.
		@param UNS_SrcProduct_Detail_ID Source Product Detail	  */
	public void setUNS_SrcProduct_Detail_ID (int UNS_SrcProduct_Detail_ID)
	{
		if (UNS_SrcProduct_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SrcProduct_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SrcProduct_Detail_ID, Integer.valueOf(UNS_SrcProduct_Detail_ID));
	}

	/** Get Source Product Detail.
		@return Source Product Detail	  */
	public int getUNS_SrcProduct_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SrcProduct_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SrcProduct_Detail_UU.
		@param UNS_SrcProduct_Detail_UU UNS_SrcProduct_Detail_UU	  */
	public void setUNS_SrcProduct_Detail_UU (String UNS_SrcProduct_Detail_UU)
	{
		set_Value (COLUMNNAME_UNS_SrcProduct_Detail_UU, UNS_SrcProduct_Detail_UU);
	}

	/** Get UNS_SrcProduct_Detail_UU.
		@return UNS_SrcProduct_Detail_UU	  */
	public String getUNS_SrcProduct_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SrcProduct_Detail_UU);
	}

	/** Set Qty To Use.
		@param UsedQty 
		Quantity to be used as the source product of the target production (order) for selected ASI
	  */
	public void setUsedQty (BigDecimal UsedQty)
	{
		set_Value (COLUMNNAME_UsedQty, UsedQty);
	}

	/** Get Qty To Use.
		@return Quantity to be used as the source product of the target production (order) for selected ASI
	  */
	public BigDecimal getUsedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UsedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}