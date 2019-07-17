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

/** Generated Model for UNS_DocumentSchema
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DocumentSchema extends PO implements I_UNS_DocumentSchema, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161226L;

    /** Standard Constructor */
    public X_UNS_DocumentSchema (Properties ctx, int UNS_DocumentSchema_ID, String trxName)
    {
      super (ctx, UNS_DocumentSchema_ID, trxName);
      /** if (UNS_DocumentSchema_ID == 0)
        {
			setName (null);
			setUNS_DocumentSchema_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DocumentSchema (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DocumentSchema[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column Date.
		@param AD_Column_ID 
		This column will be parameter date in generate document
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column Date.
		@return This column will be parameter date in generate document
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** Set Document Schema.
		@param UNS_DocumentSchema_ID Document Schema	  */
	public void setUNS_DocumentSchema_ID (int UNS_DocumentSchema_ID)
	{
		if (UNS_DocumentSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DocumentSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DocumentSchema_ID, Integer.valueOf(UNS_DocumentSchema_ID));
	}

	/** Get Document Schema.
		@return Document Schema	  */
	public int getUNS_DocumentSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DocumentSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DocumentSchema_UU.
		@param UNS_DocumentSchema_UU UNS_DocumentSchema_UU	  */
	public void setUNS_DocumentSchema_UU (String UNS_DocumentSchema_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_DocumentSchema_UU, UNS_DocumentSchema_UU);
	}

	/** Get UNS_DocumentSchema_UU.
		@return UNS_DocumentSchema_UU	  */
	public String getUNS_DocumentSchema_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DocumentSchema_UU);
	}
}