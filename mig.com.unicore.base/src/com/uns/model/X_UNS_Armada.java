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

/** Generated Model for UNS_Armada
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Armada extends PO implements I_UNS_Armada, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170213L;

    /** Standard Constructor */
    public X_UNS_Armada (Properties ctx, int UNS_Armada_ID, String trxName)
    {
      super (ctx, UNS_Armada_ID, trxName);
      /** if (UNS_Armada_ID == 0)
        {
			setIsExternalArmada (false);
// N
			setLastOM (Env.ZERO);
// 0
			setName (null);
			setUNS_Armada_ID (0);
			setUNS_ArmadaType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Armada (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Armada[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
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

	/** Set External Armada.
		@param IsExternalArmada External Armada	  */
	public void setIsExternalArmada (boolean IsExternalArmada)
	{
		set_Value (COLUMNNAME_IsExternalArmada, Boolean.valueOf(IsExternalArmada));
	}

	/** Get External Armada.
		@return External Armada	  */
	public boolean isExternalArmada () 
	{
		Object oo = get_Value(COLUMNNAME_IsExternalArmada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Odometer.
		@param LastOM 
		Last Odometer
	  */
	public void setLastOM (BigDecimal LastOM)
	{
		set_Value (COLUMNNAME_LastOM, LastOM);
	}

	/** Get Last Odometer.
		@return Last Odometer
	  */
	public BigDecimal getLastOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Armada.
		@param UNS_Armada_ID Armada	  */
	public void setUNS_Armada_ID (int UNS_Armada_ID)
	{
		if (UNS_Armada_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Armada_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Armada_ID, Integer.valueOf(UNS_Armada_ID));
	}

	/** Get Armada.
		@return Armada	  */
	public int getUNS_Armada_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Armada_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Armada_UU.
		@param UNS_Armada_UU UNS_Armada_UU	  */
	public void setUNS_Armada_UU (String UNS_Armada_UU)
	{
		set_Value (COLUMNNAME_UNS_Armada_UU, UNS_Armada_UU);
	}

	/** Get UNS_Armada_UU.
		@return UNS_Armada_UU	  */
	public String getUNS_Armada_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Armada_UU);
	}

	public I_UNS_ArmadaType getUNS_ArmadaType() throws RuntimeException
    {
		return (I_UNS_ArmadaType)MTable.get(getCtx(), I_UNS_ArmadaType.Table_Name)
			.getPO(getUNS_ArmadaType_ID(), get_TrxName());	}

	/** Set Armada Type.
		@param UNS_ArmadaType_ID Armada Type	  */
	public void setUNS_ArmadaType_ID (int UNS_ArmadaType_ID)
	{
		if (UNS_ArmadaType_ID < 1) 
			set_Value (COLUMNNAME_UNS_ArmadaType_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ArmadaType_ID, Integer.valueOf(UNS_ArmadaType_ID));
	}

	/** Get Armada Type.
		@return Armada Type	  */
	public int getUNS_ArmadaType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ArmadaType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}