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

/** Generated Model for UNS_Mess_Partition
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Mess_Partition extends PO implements I_UNS_Mess_Partition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131003L;

    /** Standard Constructor */
    public X_UNS_Mess_Partition (Properties ctx, int UNS_Mess_Partition_ID, String trxName)
    {
      super (ctx, UNS_Mess_Partition_ID, trxName);
      /** if (UNS_Mess_Partition_ID == 0)
        {
			setAvailableToOccupy (0);
// 0
			setBlok_Room_No (null);
			setM_Locator_ID (0);
			setMaximumToOccupy (0);
// 0
			setOccupiedByOccupants (0);
// 0
			setPayableTo (null);
// EMP
			setUNS_Mess_Partition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Mess_Partition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Mess_Partition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Available To Occupy.
		@param AvailableToOccupy Available To Occupy	  */
	public void setAvailableToOccupy (int AvailableToOccupy)
	{
		set_Value (COLUMNNAME_AvailableToOccupy, Integer.valueOf(AvailableToOccupy));
	}

	/** Get Available To Occupy.
		@return Available To Occupy	  */
	public int getAvailableToOccupy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AvailableToOccupy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Blok Room No.
		@param Blok_Room_No Blok Room No	  */
	public void setBlok_Room_No (String Blok_Room_No)
	{
		set_Value (COLUMNNAME_Blok_Room_No, Blok_Room_No);
	}

	/** Get Blok Room No.
		@return Blok Room No	  */
	public String getBlok_Room_No () 
	{
		return (String)get_Value(COLUMNNAME_Blok_Room_No);
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

	/** Set Last Electricity Used.
		@param LastElectricityUsed 
		Last Electricity Used from Inventory
	  */
	public void setLastElectricityUsed (BigDecimal LastElectricityUsed)
	{
		set_ValueNoCheck (COLUMNNAME_LastElectricityUsed, LastElectricityUsed);
	}

	/** Get Last Electricity Used.
		@return Last Electricity Used from Inventory
	  */
	public BigDecimal getLastElectricityUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastElectricityUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Last Water Used.
		@param LastWaterUsed 
		Last WaterUsed from Inventory
	  */
	public void setLastWaterUsed (BigDecimal LastWaterUsed)
	{
		set_ValueNoCheck (COLUMNNAME_LastWaterUsed, LastWaterUsed);
	}

	/** Get Last Water Used.
		@return Last WaterUsed from Inventory
	  */
	public BigDecimal getLastWaterUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastWaterUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Maximum To Occupy.
		@param MaximumToOccupy Maximum To Occupy	  */
	public void setMaximumToOccupy (int MaximumToOccupy)
	{
		set_Value (COLUMNNAME_MaximumToOccupy, Integer.valueOf(MaximumToOccupy));
	}

	/** Get Maximum To Occupy.
		@return Maximum To Occupy	  */
	public int getMaximumToOccupy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaximumToOccupy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Occuped By Occupants.
		@param OccupiedByOccupants Occuped By Occupants	  */
	public void setOccupiedByOccupants (int OccupiedByOccupants)
	{
		set_Value (COLUMNNAME_OccupiedByOccupants, Integer.valueOf(OccupiedByOccupants));
	}

	/** Get Occuped By Occupants.
		@return Occuped By Occupants	  */
	public int getOccupiedByOccupants () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OccupiedByOccupants);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PayableTo AD_Reference_ID=1000107 */
	public static final int PAYABLETO_AD_Reference_ID=1000107;
	/** Sumatera Timur Indonesia = STI */
	public static final String PAYABLETO_SumateraTimurIndonesia = "STI";
	/** Employee = EMP */
	public static final String PAYABLETO_Employee = "EMP";
	/** Office = OFC */
	public static final String PAYABLETO_Office = "OFC";
	/** Set Payable To.
		@param PayableTo Payable To	  */
	public void setPayableTo (String PayableTo)
	{

		set_Value (COLUMNNAME_PayableTo, PayableTo);
	}

	/** Get Payable To.
		@return Payable To	  */
	public String getPayableTo () 
	{
		return (String)get_Value(COLUMNNAME_PayableTo);
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
			set_Value (COLUMNNAME_UNS_Mess_BuildingBlock_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Mess_BuildingBlock_ID, Integer.valueOf(UNS_Mess_BuildingBlock_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getUNS_Mess_BuildingBlock_ID()));
    }

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

	/** Set UNS Mess Partition UU.
		@param UNS_Mess_Partition_UU UNS Mess Partition UU	  */
	public void setUNS_Mess_Partition_UU (String UNS_Mess_Partition_UU)
	{
		set_Value (COLUMNNAME_UNS_Mess_Partition_UU, UNS_Mess_Partition_UU);
	}

	/** Get UNS Mess Partition UU.
		@return UNS Mess Partition UU	  */
	public String getUNS_Mess_Partition_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Mess_Partition_UU);
	}
}