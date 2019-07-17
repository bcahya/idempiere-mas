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

/** Generated Model for UNS_DocSchema_MapCol
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DocSchema_MapCol extends PO implements I_UNS_DocSchema_MapCol, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161225L;

    /** Standard Constructor */
    public X_UNS_DocSchema_MapCol (Properties ctx, int UNS_DocSchema_MapCol_ID, String trxName)
    {
      super (ctx, UNS_DocSchema_MapCol_ID, trxName);
      /** if (UNS_DocSchema_MapCol_ID == 0)
        {
			setAD_Column_ID (0);
			setColumnReferer_ID (0);
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_DocumentSchema WHERE UNS_DocumentSchema_ID=@UNS_DocumentSchema_ID@
			setUNS_DocSchema_MapCol_ID (0);
			setUNS_DocumentSchema_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DocSchema_MapCol (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DocSchema_MapCol[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getColumnReferer() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getColumnReferer_ID(), get_TrxName());	}

	/** Set Column Referer.
		@param ColumnReferer_ID Column Referer	  */
	public void setColumnReferer_ID (int ColumnReferer_ID)
	{
		if (ColumnReferer_ID < 1) 
			set_Value (COLUMNNAME_ColumnReferer_ID, null);
		else 
			set_Value (COLUMNNAME_ColumnReferer_ID, Integer.valueOf(ColumnReferer_ID));
	}

	/** Get Column Referer.
		@return Column Referer	  */
	public int getColumnReferer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ColumnReferer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Mapping Column.
		@param UNS_DocSchema_MapCol_ID Mapping Column	  */
	public void setUNS_DocSchema_MapCol_ID (int UNS_DocSchema_MapCol_ID)
	{
		if (UNS_DocSchema_MapCol_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DocSchema_MapCol_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DocSchema_MapCol_ID, Integer.valueOf(UNS_DocSchema_MapCol_ID));
	}

	/** Get Mapping Column.
		@return Mapping Column	  */
	public int getUNS_DocSchema_MapCol_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DocSchema_MapCol_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DocSchema_MapCol_UU.
		@param UNS_DocSchema_MapCol_UU UNS_DocSchema_MapCol_UU	  */
	public void setUNS_DocSchema_MapCol_UU (String UNS_DocSchema_MapCol_UU)
	{
		set_Value (COLUMNNAME_UNS_DocSchema_MapCol_UU, UNS_DocSchema_MapCol_UU);
	}

	/** Get UNS_DocSchema_MapCol_UU.
		@return UNS_DocSchema_MapCol_UU	  */
	public String getUNS_DocSchema_MapCol_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DocSchema_MapCol_UU);
	}

	public com.uns.model.I_UNS_DocumentSchema getUNS_DocumentSchema() throws RuntimeException
    {
		return (com.uns.model.I_UNS_DocumentSchema)MTable.get(getCtx(), com.uns.model.I_UNS_DocumentSchema.Table_Name)
			.getPO(getUNS_DocumentSchema_ID(), get_TrxName());	}

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
}