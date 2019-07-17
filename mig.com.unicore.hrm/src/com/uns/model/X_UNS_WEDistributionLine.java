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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_WEDistributionLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_WEDistributionLine extends PO implements I_UNS_WEDistributionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131003L;

    /** Standard Constructor */
    public X_UNS_WEDistributionLine (Properties ctx, int UNS_WEDistributionLine_ID, String trxName)
    {
      super (ctx, UNS_WEDistributionLine_ID, trxName);
      /** if (UNS_WEDistributionLine_ID == 0)
        {
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_WEDistributionLine WHERE UNS_WEDistribution_ID=@UNS_WEDistribution_ID@
			setM_LocatorTo_ID (0);
// @M_LocatorTo_ID@
			setQtyInternalUse (Env.ZERO);
// 0
			setQtyPInternalUse (Env.ZERO);
// 0
			setUNS_Mess_BuildingBlock_ID (0);
			setUNS_Mess_Partition_ID (0);
			setUNS_WEDistribution_ID (0);
			setUNS_WEDistributionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_WEDistributionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_WEDistributionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
    }

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InventoryLine)MTable.get(getCtx(), org.compiere.model.I_M_InventoryLine.Table_Name)
			.getPO(getM_InventoryLine_ID(), get_TrxName());	}

	/** Set Phys.Inventory Line.
		@param M_InventoryLine_ID 
		Unique line in an Inventory document
	  */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	/** Get Phys.Inventory Line.
		@return Unique line in an Inventory document
	  */
	public int getM_InventoryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Locator getM_LocatorTo() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_LocatorTo_ID(), get_TrxName());	}

	/** Set Locator To.
		@param M_LocatorTo_ID 
		Location inventory is moved to
	  */
	public void setM_LocatorTo_ID (int M_LocatorTo_ID)
	{
		if (M_LocatorTo_ID < 1) 
			set_Value (COLUMNNAME_M_LocatorTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_LocatorTo_ID, Integer.valueOf(M_LocatorTo_ID));
	}

	/** Get Locator To.
		@return Location inventory is moved to
	  */
	public int getM_LocatorTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LocatorTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_MovementLine)MTable.get(getCtx(), org.compiere.model.I_M_MovementLine.Table_Name)
			.getPO(getM_MovementLine_ID(), get_TrxName());	}

	/** Set Move Line.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Move Line.
		@return Inventory Move document Line
	  */
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Internal Use Qty.
		@param QtyInternalUse 
		Internal Use Quantity removed from Inventory
	  */
	public void setQtyInternalUse (BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	/** Get Internal Use Qty.
		@return Internal Use Quantity removed from Inventory
	  */
	public BigDecimal getQtyInternalUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInternalUse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Previous Used Qty.
		@param QtyPInternalUse 
		Previous Internal Used Quantity removed from Inventory
	  */
	public void setQtyPInternalUse (BigDecimal QtyPInternalUse)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPInternalUse, QtyPInternalUse);
	}

	/** Get Previous Used Qty.
		@return Previous Internal Used Quantity removed from Inventory
	  */
	public BigDecimal getQtyPInternalUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPInternalUse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Mess_BuildingBlock getUNS_Mess_BuildingBlock() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Mess_BuildingBlock)MTable.get(getCtx(), com.uns.model.I_UNS_Mess_BuildingBlock.Table_Name)
			.getPO(getUNS_Mess_BuildingBlock_ID(), get_TrxName());	}

	/** Set Mess Building Block.
		@param UNS_Mess_BuildingBlock_ID Mess Building Block	  */
	public void setUNS_Mess_BuildingBlock_ID (int UNS_Mess_BuildingBlock_ID)
	{
		if (UNS_Mess_BuildingBlock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_BuildingBlock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_BuildingBlock_ID, Integer.valueOf(UNS_Mess_BuildingBlock_ID));
	}

	/** Get Mess Building Block.
		@return Mess Building Block	  */
	public int getUNS_Mess_BuildingBlock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_BuildingBlock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Mess_Partition)MTable.get(getCtx(), com.uns.model.I_UNS_Mess_Partition.Table_Name)
			.getPO(getUNS_Mess_Partition_ID(), get_TrxName());	}

	/** Set Mess.
		@param UNS_Mess_Partition_ID Mess	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID)
	{
		if (UNS_Mess_Partition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_Partition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_Partition_ID, Integer.valueOf(UNS_Mess_Partition_ID));
	}

	/** Get Mess.
		@return Mess	  */
	public int getUNS_Mess_Partition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_Partition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_WEDistribution getUNS_WEDistribution() throws RuntimeException
    {
		return (com.uns.model.I_UNS_WEDistribution)MTable.get(getCtx(), com.uns.model.I_UNS_WEDistribution.Table_Name)
			.getPO(getUNS_WEDistribution_ID(), get_TrxName());	}

	/** Set Water Electricity Distribution.
		@param UNS_WEDistribution_ID Water Electricity Distribution	  */
	public void setUNS_WEDistribution_ID (int UNS_WEDistribution_ID)
	{
		if (UNS_WEDistribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_WEDistribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_WEDistribution_ID, Integer.valueOf(UNS_WEDistribution_ID));
	}

	/** Get Water Electricity Distribution.
		@return Water Electricity Distribution	  */
	public int getUNS_WEDistribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_WEDistribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Water Electricity Distribution Detail.
		@param UNS_WEDistributionLine_ID Water Electricity Distribution Detail	  */
	public void setUNS_WEDistributionLine_ID (int UNS_WEDistributionLine_ID)
	{
		if (UNS_WEDistributionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_WEDistributionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_WEDistributionLine_ID, Integer.valueOf(UNS_WEDistributionLine_ID));
	}

	/** Get Water Electricity Distribution Detail.
		@return Water Electricity Distribution Detail	  */
	public int getUNS_WEDistributionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_WEDistributionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_WEDistributionLine_UU.
		@param UNS_WEDistributionLine_UU UNS_WEDistributionLine_UU	  */
	public void setUNS_WEDistributionLine_UU (String UNS_WEDistributionLine_UU)
	{
		set_Value (COLUMNNAME_UNS_WEDistributionLine_UU, UNS_WEDistributionLine_UU);
	}

	/** Get UNS_WEDistributionLine_UU.
		@return UNS_WEDistributionLine_UU	  */
	public String getUNS_WEDistributionLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_WEDistributionLine_UU);
	}
}