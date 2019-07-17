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

/** Generated Model for UNS_ProdAllocTrans
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ProdAllocTrans extends PO implements I_UNS_ProdAllocTrans, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130318L;

    /** Standard Constructor */
    public X_UNS_ProdAllocTrans (Properties ctx, int UNS_ProdAllocTrans_ID, String trxName)
    {
      super (ctx, UNS_ProdAllocTrans_ID, trxName);
      /** if (UNS_ProdAllocTrans_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
// N
			setUNS_Forecast_Header_ID (0);
			setUNS_ProdAllocTrans_ID (0);
			setUNS_Resource_ID (0);
			setUNS_Resource_Transition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ProdAllocTrans (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ProdAllocTrans[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set August.
		@param Agt August	  */
	public void setAgt (BigDecimal Agt)
	{
		set_Value (COLUMNNAME_Agt, Agt);
	}

	/** Get August.
		@return August	  */
	public BigDecimal getAgt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Agt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set April.
		@param Apr April	  */
	public void setApr (BigDecimal Apr)
	{
		set_Value (COLUMNNAME_Apr, Apr);
	}

	/** Get April.
		@return April	  */
	public BigDecimal getApr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Apr);
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

	/** Set December.
		@param Dec December	  */
	public void setDec (BigDecimal Dec)
	{
		set_Value (COLUMNNAME_Dec, Dec);
	}

	/** Get December.
		@return December	  */
	public BigDecimal getDec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Dec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set February.
		@param Feb February	  */
	public void setFeb (BigDecimal Feb)
	{
		set_Value (COLUMNNAME_Feb, Feb);
	}

	/** Get February.
		@return February	  */
	public BigDecimal getFeb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Feb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Input Allocation.
		@param InputAllocation Input Allocation	  */
	public void setInputAllocation (BigDecimal InputAllocation)
	{
		set_Value (COLUMNNAME_InputAllocation, InputAllocation);
	}

	/** Get Input Allocation.
		@return Input Allocation	  */
	public BigDecimal getInputAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InputAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Input Requirement.
		@param InputRequirement Input Requirement	  */
	public void setInputRequirement (BigDecimal InputRequirement)
	{
		set_Value (COLUMNNAME_InputRequirement, InputRequirement);
	}

	/** Get Input Requirement.
		@return Input Requirement	  */
	public BigDecimal getInputRequirement () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InputRequirement);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set January.
		@param Jan January	  */
	public void setJan (BigDecimal Jan)
	{
		set_Value (COLUMNNAME_Jan, Jan);
	}

	/** Get January.
		@return January	  */
	public BigDecimal getJan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Jan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set July.
		@param Jul July	  */
	public void setJul (BigDecimal Jul)
	{
		set_Value (COLUMNNAME_Jul, Jul);
	}

	/** Get July.
		@return July	  */
	public BigDecimal getJul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Jul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set June.
		@param Jun June	  */
	public void setJun (BigDecimal Jun)
	{
		set_Value (COLUMNNAME_Jun, Jun);
	}

	/** Get June.
		@return June	  */
	public BigDecimal getJun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Jun);
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

	/** Set March.
		@param Mar March	  */
	public void setMar (BigDecimal Mar)
	{
		set_Value (COLUMNNAME_Mar, Mar);
	}

	/** Get March.
		@return March	  */
	public BigDecimal getMar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Mar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set May.
		@param May May	  */
	public void setMay (BigDecimal May)
	{
		set_Value (COLUMNNAME_May, May);
	}

	/** Get May.
		@return May	  */
	public BigDecimal getMay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_May);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Resource getNextResource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getNextResource_ID(), get_TrxName());	}

	/** Set Next Resource.
		@param NextResource_ID Next Resource	  */
	public void setNextResource_ID (int NextResource_ID)
	{
		if (NextResource_ID < 1) 
			set_Value (COLUMNNAME_NextResource_ID, null);
		else 
			set_Value (COLUMNNAME_NextResource_ID, Integer.valueOf(NextResource_ID));
	}

	/** Get Next Resource.
		@return Next Resource	  */
	public int getNextResource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NextResource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set November.
		@param Nov November	  */
	public void setNov (BigDecimal Nov)
	{
		set_Value (COLUMNNAME_Nov, Nov);
	}

	/** Get November.
		@return November	  */
	public BigDecimal getNov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Nov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set October.
		@param Oct October	  */
	public void setOct (BigDecimal Oct)
	{
		set_Value (COLUMNNAME_Oct, Oct);
	}

	/** Get October.
		@return October	  */
	public BigDecimal getOct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Oct);
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

	/** Set September.
		@param Sep September	  */
	public void setSep (BigDecimal Sep)
	{
		set_Value (COLUMNNAME_Sep, Sep);
	}

	/** Get September.
		@return September	  */
	public BigDecimal getSep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Sep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Header)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Header.Table_Name)
			.getPO(getUNS_Forecast_Header_ID(), get_TrxName());	}

	/** Set Manufacturing Forecast.
		@param UNS_Forecast_Header_ID Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID)
	{
		if (UNS_Forecast_Header_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, Integer.valueOf(UNS_Forecast_Header_ID));
	}

	/** Get Manufacturing Forecast.
		@return Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Forecast_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Allocation Transition.
		@param UNS_ProdAllocTrans_ID Product Allocation Transition	  */
	public void setUNS_ProdAllocTrans_ID (int UNS_ProdAllocTrans_ID)
	{
		if (UNS_ProdAllocTrans_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProdAllocTrans_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProdAllocTrans_ID, Integer.valueOf(UNS_ProdAllocTrans_ID));
	}

	/** Get Product Allocation Transition.
		@return Product Allocation Transition	  */
	public int getUNS_ProdAllocTrans_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProdAllocTrans_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Allocation Transition UU.
		@param UNS_ProdAllocTrans_UU Product Allocation Transition UU	  */
	public void setUNS_ProdAllocTrans_UU (String UNS_ProdAllocTrans_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ProdAllocTrans_UU, UNS_ProdAllocTrans_UU);
	}

	/** Get Product Allocation Transition UU.
		@return Product Allocation Transition UU	  */
	public String getUNS_ProdAllocTrans_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ProdAllocTrans_UU);
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
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
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

	public com.uns.model.I_UNS_Resource_Transition getUNS_Resource_Transition() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource_Transition)MTable.get(getCtx(), com.uns.model.I_UNS_Resource_Transition.Table_Name)
			.getPO(getUNS_Resource_Transition_ID(), get_TrxName());	}

	/** Set Manufacturing Resource Transition.
		@param UNS_Resource_Transition_ID Manufacturing Resource Transition	  */
	public void setUNS_Resource_Transition_ID (int UNS_Resource_Transition_ID)
	{
		if (UNS_Resource_Transition_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_Transition_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_Transition_ID, Integer.valueOf(UNS_Resource_Transition_ID));
	}

	/** Get Manufacturing Resource Transition.
		@return Manufacturing Resource Transition	  */
	public int getUNS_Resource_Transition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Transition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}