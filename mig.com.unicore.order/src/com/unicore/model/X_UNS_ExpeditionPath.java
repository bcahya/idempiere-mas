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

/** Generated Model for UNS_ExpeditionPath
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ExpeditionPath extends PO implements I_UNS_ExpeditionPath, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160328L;

    /** Standard Constructor */
    public X_UNS_ExpeditionPath (Properties ctx, int UNS_ExpeditionPath_ID, String trxName)
    {
      super (ctx, UNS_ExpeditionPath_ID, trxName);
      /** if (UNS_ExpeditionPath_ID == 0)
        {
			setDestination_ID (0);
			setOrigin_ID (0);
			setPriceActual (Env.ZERO);
// 0
			setPriceLimit (Env.ZERO);
// 0
			setPriceList (Env.ZERO);
// 0
			setProcessed (false);
			setTonase (Env.ZERO);
// 0
			setTotalAmt (Env.ZERO);
// 0
			setUNS_ExpeditionPath_ID (0);
			setVolume (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_ExpeditionPath (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ExpeditionPath[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
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

	public org.compiere.model.I_C_Location getDestination() throws RuntimeException
    {
		return (org.compiere.model.I_C_Location)MTable.get(getCtx(), org.compiere.model.I_C_Location.Table_Name)
			.getPO(getDestination_ID(), get_TrxName());	}

	/** Set Destination.
		@param Destination_ID Destination	  */
	public void setDestination_ID (int Destination_ID)
	{
		if (Destination_ID < 1) 
			set_Value (COLUMNNAME_Destination_ID, null);
		else 
			set_Value (COLUMNNAME_Destination_ID, Integer.valueOf(Destination_ID));
	}

	/** Get Destination.
		@return Destination	  */
	public int getDestination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Destination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Distance.
		@param Distance 
		The number of distance in Kilo Meter from an area to another.
	  */
	public void setDistance (BigDecimal Distance)
	{
		set_Value (COLUMNNAME_Distance, Distance);
	}

	/** Get Distance.
		@return The number of distance in Kilo Meter from an area to another.
	  */
	public BigDecimal getDistance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Distance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JobSOPath_ID.
		@param JobSOPath_ID JobSOPath_ID	  */
	public void setJobSOPath_ID (int JobSOPath_ID)
	{
		if (JobSOPath_ID < 1) 
			set_Value (COLUMNNAME_JobSOPath_ID, null);
		else 
			set_Value (COLUMNNAME_JobSOPath_ID, Integer.valueOf(JobSOPath_ID));
	}

	/** Get JobSOPath_ID.
		@return JobSOPath_ID	  */
	public int getJobSOPath_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JobSOPath_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Location getOrigin() throws RuntimeException
    {
		return (org.compiere.model.I_C_Location)MTable.get(getCtx(), org.compiere.model.I_C_Location.Table_Name)
			.getPO(getOrigin_ID(), get_TrxName());	}

	/** Set Origin.
		@param Origin_ID Origin	  */
	public void setOrigin_ID (int Origin_ID)
	{
		if (Origin_ID < 1) 
			set_Value (COLUMNNAME_Origin_ID, null);
		else 
			set_Value (COLUMNNAME_Origin_ID, Integer.valueOf(Origin_ID));
	}

	/** Get Origin.
		@return Origin	  */
	public int getOrigin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Origin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit Price.
		@param PriceLimit 
		Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Limit Price.
		@return Lowest price for a product
	  */
	public BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
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

	/** Set Tonase.
		@param Tonase 
		Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase)
	{
		set_Value (COLUMNNAME_Tonase, Tonase);
	}

	/** Get Tonase.
		@return Indicate total tonase
	  */
	public BigDecimal getTonase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tonase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
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

	public com.unicore.model.I_UNS_Expedition getUNS_Expedition() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Expedition)MTable.get(getCtx(), com.unicore.model.I_UNS_Expedition.Table_Name)
			.getPO(getUNS_Expedition_ID(), get_TrxName());	}

	/** Set Expedition.
		@param UNS_Expedition_ID Expedition	  */
	public void setUNS_Expedition_ID (int UNS_Expedition_ID)
	{
		if (UNS_Expedition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Expedition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Expedition_ID, Integer.valueOf(UNS_Expedition_ID));
	}

	/** Get Expedition.
		@return Expedition	  */
	public int getUNS_Expedition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Expedition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Expedetion Path.
		@param UNS_ExpeditionPath_ID Expedetion Path	  */
	public void setUNS_ExpeditionPath_ID (int UNS_ExpeditionPath_ID)
	{
		if (UNS_ExpeditionPath_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionPath_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionPath_ID, Integer.valueOf(UNS_ExpeditionPath_ID));
	}

	/** Get Expedetion Path.
		@return Expedetion Path	  */
	public int getUNS_ExpeditionPath_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionPath_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ExpeditionPath_UU.
		@param UNS_ExpeditionPath_UU UNS_ExpeditionPath_UU	  */
	public void setUNS_ExpeditionPath_UU (String UNS_ExpeditionPath_UU)
	{
		set_Value (COLUMNNAME_UNS_ExpeditionPath_UU, UNS_ExpeditionPath_UU);
	}

	/** Get UNS_ExpeditionPath_UU.
		@return UNS_ExpeditionPath_UU	  */
	public String getUNS_ExpeditionPath_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ExpeditionPath_UU);
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}