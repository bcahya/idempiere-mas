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

/** Generated Model for UNS_ActualCostXclLineList
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ActualCostXclLineList extends PO implements I_UNS_ActualCostXclLineList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130510L;

    /** Standard Constructor */
    public X_UNS_ActualCostXclLineList (Properties ctx, int UNS_ActualCostXclLineList_ID, String trxName)
    {
      super (ctx, UNS_ActualCostXclLineList_ID, trxName);
      /** if (UNS_ActualCostXclLineList_ID == 0)
        {
			setC_ElementValue_ID (0);
			setUNS_ActualCostConfigLine_ID (0);
			setUNS_ActualCostXclLineList_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ActualCostXclLineList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ActualCostXclLineList[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
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

	public com.uns.model.I_UNS_ActualCostConfigLine getUNS_ActualCostConfigLine() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ActualCostConfigLine)MTable.get(getCtx(), com.uns.model.I_UNS_ActualCostConfigLine.Table_Name)
			.getPO(getUNS_ActualCostConfigLine_ID(), get_TrxName());	}

	/** Set Actual Cost Configuration Line.
		@param UNS_ActualCostConfigLine_ID Actual Cost Configuration Line	  */
	public void setUNS_ActualCostConfigLine_ID (int UNS_ActualCostConfigLine_ID)
	{
		if (UNS_ActualCostConfigLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfigLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfigLine_ID, Integer.valueOf(UNS_ActualCostConfigLine_ID));
	}

	/** Get Actual Cost Configuration Line.
		@return Actual Cost Configuration Line	  */
	public int getUNS_ActualCostConfigLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostConfigLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ActualCostXclLineList_ID.
		@param UNS_ActualCostXclLineList_ID 
		UNS_ActualCostXclLineList_ID
	  */
	public void setUNS_ActualCostXclLineList_ID (int UNS_ActualCostXclLineList_ID)
	{
		if (UNS_ActualCostXclLineList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostXclLineList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostXclLineList_ID, Integer.valueOf(UNS_ActualCostXclLineList_ID));
	}

	/** Get UNS_ActualCostXclLineList_ID.
		@return UNS_ActualCostXclLineList_ID
	  */
	public int getUNS_ActualCostXclLineList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostXclLineList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ActualCostXclLineList_UU.
		@param UNS_ActualCostXclLineList_UU UNS_ActualCostXclLineList_UU	  */
	public void setUNS_ActualCostXclLineList_UU (String UNS_ActualCostXclLineList_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ActualCostXclLineList_UU, UNS_ActualCostXclLineList_UU);
	}

	/** Get UNS_ActualCostXclLineList_UU.
		@return UNS_ActualCostXclLineList_UU	  */
	public String getUNS_ActualCostXclLineList_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ActualCostXclLineList_UU);
	}
}