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

/** Generated Model for UNS_OrgWarehouse_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_OrgWarehouse_Line extends PO implements I_UNS_OrgWarehouse_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150325L;

    /** Standard Constructor */
    public X_UNS_OrgWarehouse_Line (Properties ctx, int UNS_OrgWarehouse_Line_ID, String trxName)
    {
      super (ctx, UNS_OrgWarehouse_Line_ID, trxName);
      /** if (UNS_OrgWarehouse_Line_ID == 0)
        {
			setUNS_OrgWarehouse_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OrgWarehouse_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OrgWarehouse_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_OrgWarehouse getUNS_OrgWarehouse() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_OrgWarehouse)MTable.get(getCtx(), com.unicore.model.I_UNS_OrgWarehouse.Table_Name)
			.getPO(getUNS_OrgWarehouse_ID(), get_TrxName());	}

	/** Set Organization Warehouse.
		@param UNS_OrgWarehouse_ID Organization Warehouse	  */
	public void setUNS_OrgWarehouse_ID (int UNS_OrgWarehouse_ID)
	{
		if (UNS_OrgWarehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrgWarehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrgWarehouse_ID, Integer.valueOf(UNS_OrgWarehouse_ID));
	}

	/** Get Organization Warehouse.
		@return Organization Warehouse	  */
	public int getUNS_OrgWarehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrgWarehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Organization Warehouse Line.
		@param UNS_OrgWarehouse_Line_ID Organization Warehouse Line	  */
	public void setUNS_OrgWarehouse_Line_ID (int UNS_OrgWarehouse_Line_ID)
	{
		if (UNS_OrgWarehouse_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrgWarehouse_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrgWarehouse_Line_ID, Integer.valueOf(UNS_OrgWarehouse_Line_ID));
	}

	/** Get Organization Warehouse Line.
		@return Organization Warehouse Line	  */
	public int getUNS_OrgWarehouse_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrgWarehouse_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OrgWarehouse_Line_UU.
		@param UNS_OrgWarehouse_Line_UU UNS_OrgWarehouse_Line_UU	  */
	public void setUNS_OrgWarehouse_Line_UU (String UNS_OrgWarehouse_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_OrgWarehouse_Line_UU, UNS_OrgWarehouse_Line_UU);
	}

	/** Get UNS_OrgWarehouse_Line_UU.
		@return UNS_OrgWarehouse_Line_UU	  */
	public String getUNS_OrgWarehouse_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_OrgWarehouse_Line_UU);
	}
}