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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Charge_OldCOA_Map
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Charge_OldCOA_Map extends PO implements I_UNS_Charge_OldCOA_Map, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150705L;

    /** Standard Constructor */
    public X_UNS_Charge_OldCOA_Map (Properties ctx, int UNS_Charge_OldCOA_Map_ID, String trxName)
    {
      super (ctx, UNS_Charge_OldCOA_Map_ID, trxName);
      /** if (UNS_Charge_OldCOA_Map_ID == 0)
        {
			setC_Charge_ID (0);
			setOldCOA (null);
			setUNS_Charge_OldCOA_Map_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Charge_OldCOA_Map (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Charge_OldCOA_Map[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Old COA.
		@param OldCOA 
		Previous COA number to be mapped to a charge.
	  */
	public void setOldCOA (String OldCOA)
	{
		set_Value (COLUMNNAME_OldCOA, OldCOA);
	}

	/** Get Old COA.
		@return Previous COA number to be mapped to a charge.
	  */
	public String getOldCOA () 
	{
		return (String)get_Value(COLUMNNAME_OldCOA);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getOldCOA());
    }

	/** Set Charge to Old COA.
		@param UNS_Charge_OldCOA_Map_ID Charge to Old COA	  */
	public void setUNS_Charge_OldCOA_Map_ID (int UNS_Charge_OldCOA_Map_ID)
	{
		if (UNS_Charge_OldCOA_Map_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_OldCOA_Map_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_OldCOA_Map_ID, Integer.valueOf(UNS_Charge_OldCOA_Map_ID));
	}

	/** Get Charge to Old COA.
		@return Charge to Old COA	  */
	public int getUNS_Charge_OldCOA_Map_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Charge_OldCOA_Map_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Charge_OldCOA_Map_UU.
		@param UNS_Charge_OldCOA_Map_UU UNS_Charge_OldCOA_Map_UU	  */
	public void setUNS_Charge_OldCOA_Map_UU (String UNS_Charge_OldCOA_Map_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Charge_OldCOA_Map_UU, UNS_Charge_OldCOA_Map_UU);
	}

	/** Get UNS_Charge_OldCOA_Map_UU.
		@return UNS_Charge_OldCOA_Map_UU	  */
	public String getUNS_Charge_OldCOA_Map_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Charge_OldCOA_Map_UU);
	}
}