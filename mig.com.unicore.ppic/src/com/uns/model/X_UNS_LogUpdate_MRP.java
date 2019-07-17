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

/** Generated Model for UNS_LogUpdate_MRP
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LogUpdate_MRP extends PO implements I_UNS_LogUpdate_MRP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130312L;

    /** Standard Constructor */
    public X_UNS_LogUpdate_MRP (Properties ctx, int UNS_LogUpdate_MRP_ID, String trxName)
    {
      super (ctx, UNS_LogUpdate_MRP_ID, trxName);
      /** if (UNS_LogUpdate_MRP_ID == 0)
        {
			setRecord_ID (0);
			setTableName (null);
			setUNS_LogUpdate_MRP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LogUpdate_MRP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LogUpdate_MRP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Table Name.
		@param TableName 
		Name of the table in the database
	  */
	public void setTableName (String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	/** Get DB Table Name.
		@return Name of the table in the database
	  */
	public String getTableName () 
	{
		return (String)get_Value(COLUMNNAME_TableName);
	}

	/** Set Log Update MRP.
		@param UNS_LogUpdate_MRP_ID Log Update MRP	  */
	public void setUNS_LogUpdate_MRP_ID (int UNS_LogUpdate_MRP_ID)
	{
		if (UNS_LogUpdate_MRP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LogUpdate_MRP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LogUpdate_MRP_ID, Integer.valueOf(UNS_LogUpdate_MRP_ID));
	}

	/** Get Log Update MRP.
		@return Log Update MRP	  */
	public int getUNS_LogUpdate_MRP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LogUpdate_MRP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LogUpdate_MRP_UU.
		@param UNS_LogUpdate_MRP_UU UNS_LogUpdate_MRP_UU	  */
	public void setUNS_LogUpdate_MRP_UU (String UNS_LogUpdate_MRP_UU)
	{
		set_Value (COLUMNNAME_UNS_LogUpdate_MRP_UU, UNS_LogUpdate_MRP_UU);
	}

	/** Get UNS_LogUpdate_MRP_UU.
		@return UNS_LogUpdate_MRP_UU	  */
	public String getUNS_LogUpdate_MRP_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LogUpdate_MRP_UU);
	}
}