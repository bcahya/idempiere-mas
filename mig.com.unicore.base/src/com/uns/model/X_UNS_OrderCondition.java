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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_OrderCondition
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_OrderCondition extends PO implements I_UNS_OrderCondition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130824L;

    /** Standard Constructor */
    public X_UNS_OrderCondition (Properties ctx, int UNS_OrderCondition_ID, String trxName)
    {
      super (ctx, UNS_OrderCondition_ID, trxName);
      /** if (UNS_OrderCondition_ID == 0)
        {
			setDescription (null);
			setName (null);
			setUNS_OrderCondition_ID (0);
			setUNS_OtherCondition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OrderCondition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OrderCondition[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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

	/** Set Order Line.
		@param C_OrderLine_ID 
		Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Order Line.
		@return Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
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

	/** Set Order Condition.
		@param UNS_OrderCondition_ID Order Condition	  */
	public void setUNS_OrderCondition_ID (int UNS_OrderCondition_ID)
	{
		if (UNS_OrderCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderCondition_ID, Integer.valueOf(UNS_OrderCondition_ID));
	}

	/** Get Order Condition.
		@return Order Condition	  */
	public int getUNS_OrderCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrderCondition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Other Condition.
		@param UNS_OtherCondition_ID Other Condition	  */
	public void setUNS_OtherCondition_ID (int UNS_OtherCondition_ID)
	{
		if (UNS_OtherCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OtherCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OtherCondition_ID, Integer.valueOf(UNS_OtherCondition_ID));
	}

	/** Get Other Condition.
		@return Other Condition	  */
	public int getUNS_OtherCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OtherCondition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}