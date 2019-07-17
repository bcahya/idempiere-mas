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

/** Generated Model for UNS_LC_OtherCondition
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_OtherCondition extends PO implements I_UNS_LC_OtherCondition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130506L;

    /** Standard Constructor */
    public X_UNS_LC_OtherCondition (Properties ctx, int UNS_LC_OtherCondition_ID, String trxName)
    {
      super (ctx, UNS_LC_OtherCondition_ID, trxName);
      /** if (UNS_LC_OtherCondition_ID == 0)
        {
			setDescription (null);
			setName (null);
			setUNS_LC_Balance_ID (0);
			setUNS_LC_OtherCondition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_OtherCondition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_OtherCondition[")
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

	public com.uns.model.I_UNS_LC_Balance getUNS_LC_Balance() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Balance)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Balance.Table_Name)
			.getPO(getUNS_LC_Balance_ID(), get_TrxName());	}

	/** Set LC Balance.
		@param UNS_LC_Balance_ID LC Balance	  */
	public void setUNS_LC_Balance_ID (int UNS_LC_Balance_ID)
	{
		if (UNS_LC_Balance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Balance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Balance_ID, Integer.valueOf(UNS_LC_Balance_ID));
	}

	/** Get LC Balance.
		@return LC Balance	  */
	public int getUNS_LC_Balance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Balance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Other Condition.
		@param UNS_LC_OtherCondition_ID Other Condition	  */
	public void setUNS_LC_OtherCondition_ID (int UNS_LC_OtherCondition_ID)
	{
		if (UNS_LC_OtherCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_OtherCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_OtherCondition_ID, Integer.valueOf(UNS_LC_OtherCondition_ID));
	}

	/** Get Other Condition.
		@return Other Condition	  */
	public int getUNS_LC_OtherCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_OtherCondition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_OtherCondition_UU.
		@param UNS_LC_OtherCondition_UU UNS_LC_OtherCondition_UU	  */
	public void setUNS_LC_OtherCondition_UU (String UNS_LC_OtherCondition_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_OtherCondition_UU, UNS_LC_OtherCondition_UU);
	}

	/** Get UNS_LC_OtherCondition_UU.
		@return UNS_LC_OtherCondition_UU	  */
	public String getUNS_LC_OtherCondition_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_OtherCondition_UU);
	}
}