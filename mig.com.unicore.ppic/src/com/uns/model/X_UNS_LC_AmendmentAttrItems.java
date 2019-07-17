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

/** Generated Model for UNS_LC_AmendmentAttrItems
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_AmendmentAttrItems extends PO implements I_UNS_LC_AmendmentAttrItems, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130528L;

    /** Standard Constructor */
    public X_UNS_LC_AmendmentAttrItems (Properties ctx, int UNS_LC_AmendmentAttrItems_ID, String trxName)
    {
      super (ctx, UNS_LC_AmendmentAttrItems_ID, trxName);
      /** if (UNS_LC_AmendmentAttrItems_ID == 0)
        {
			setDescription (null);
			setItemNo (0);
// 0
			setUNS_LC_AmdAttributes_ID (0);
			setUNS_LC_AmendmentAttrItems_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_AmendmentAttrItems (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_AmendmentAttrItems[")
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

	/** Set Item No.
		@param ItemNo Item No	  */
	public void setItemNo (int ItemNo)
	{
		set_Value (COLUMNNAME_ItemNo, Integer.valueOf(ItemNo));
	}

	/** Get Item No.
		@return Item No	  */
	public int getItemNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ItemNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_LC_AmdAttributes getUNS_LC_AmdAttributes() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_AmdAttributes)MTable.get(getCtx(), com.uns.model.I_UNS_LC_AmdAttributes.Table_Name)
			.getPO(getUNS_LC_AmdAttributes_ID(), get_TrxName());	}

	/** Set LC Amendment Attributes.
		@param UNS_LC_AmdAttributes_ID LC Amendment Attributes	  */
	public void setUNS_LC_AmdAttributes_ID (int UNS_LC_AmdAttributes_ID)
	{
		if (UNS_LC_AmdAttributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmdAttributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmdAttributes_ID, Integer.valueOf(UNS_LC_AmdAttributes_ID));
	}

	/** Get LC Amendment Attributes.
		@return LC Amendment Attributes	  */
	public int getUNS_LC_AmdAttributes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_AmdAttributes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amendment LC Attribute Items.
		@param UNS_LC_AmendmentAttrItems_ID Amendment LC Attribute Items	  */
	public void setUNS_LC_AmendmentAttrItems_ID (int UNS_LC_AmendmentAttrItems_ID)
	{
		if (UNS_LC_AmendmentAttrItems_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmendmentAttrItems_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmendmentAttrItems_ID, Integer.valueOf(UNS_LC_AmendmentAttrItems_ID));
	}

	/** Get Amendment LC Attribute Items.
		@return Amendment LC Attribute Items	  */
	public int getUNS_LC_AmendmentAttrItems_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_AmendmentAttrItems_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_AmendmentAttrItems_UU.
		@param UNS_LC_AmendmentAttrItems_UU UNS_LC_AmendmentAttrItems_UU	  */
	public void setUNS_LC_AmendmentAttrItems_UU (String UNS_LC_AmendmentAttrItems_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_AmendmentAttrItems_UU, UNS_LC_AmendmentAttrItems_UU);
	}

	/** Get UNS_LC_AmendmentAttrItems_UU.
		@return UNS_LC_AmendmentAttrItems_UU	  */
	public String getUNS_LC_AmendmentAttrItems_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_AmendmentAttrItems_UU);
	}
}