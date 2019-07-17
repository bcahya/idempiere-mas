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

/** Generated Model for UNS_DocSchema_Condition
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DocSchema_Condition extends PO implements I_UNS_DocSchema_Condition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161226L;

    /** Standard Constructor */
    public X_UNS_DocSchema_Condition (Properties ctx, int UNS_DocSchema_Condition_ID, String trxName)
    {
      super (ctx, UNS_DocSchema_Condition_ID, trxName);
      /** if (UNS_DocSchema_Condition_ID == 0)
        {
			setAndOr (null);
// A
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_DocSchema_Condition WHERE UNS_DocumentSchema_ID=@UNS_DocumentSchema_ID@
			setOperation (null);
			setUNS_DocSchema_Condition_ID (0);
			setUNS_DocumentSchema_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DocSchema_Condition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DocSchema_Condition[")
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

	/** AndOr AD_Reference_ID=204 */
	public static final int ANDOR_AD_Reference_ID=204;
	/** And = A */
	public static final String ANDOR_And = "A";
	/** Or = O */
	public static final String ANDOR_Or = "O";
	/** Set And/Or.
		@param AndOr 
		Logical operation: AND or OR
	  */
	public void setAndOr (String AndOr)
	{

		set_Value (COLUMNNAME_AndOr, AndOr);
	}

	/** Get And/Or.
		@return Logical operation: AND or OR
	  */
	public String getAndOr () 
	{
		return (String)get_Value(COLUMNNAME_AndOr);
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

	/** Operation AD_Reference_ID=205 */
	public static final int OPERATION_AD_Reference_ID=205;
	/**  = = == */
	public static final String OPERATION_Eq = "==";
	/** >= = >= */
	public static final String OPERATION_GtEq = ">=";
	/** > = >> */
	public static final String OPERATION_Gt = ">>";
	/** < = << */
	public static final String OPERATION_Le = "<<";
	/**  ~ = ~~ */
	public static final String OPERATION_Like = "~~";
	/** <= = <= */
	public static final String OPERATION_LeEq = "<=";
	/** |<x>| = AB */
	public static final String OPERATION_X = "AB";
	/** sql = SQ */
	public static final String OPERATION_Sql = "SQ";
	/** != = != */
	public static final String OPERATION_NotEq = "!=";
	/** Set Operation.
		@param Operation 
		Compare Operation
	  */
	public void setOperation (String Operation)
	{

		set_Value (COLUMNNAME_Operation, Operation);
	}

	/** Get Operation.
		@return Compare Operation
	  */
	public String getOperation () 
	{
		return (String)get_Value(COLUMNNAME_Operation);
	}

	/** Set SQLStatement.
		@param SQLStatement SQLStatement	  */
	public void setSQLStatement (String SQLStatement)
	{
		set_Value (COLUMNNAME_SQLStatement, SQLStatement);
	}

	/** Get SQLStatement.
		@return SQLStatement	  */
	public String getSQLStatement () 
	{
		return (String)get_Value(COLUMNNAME_SQLStatement);
	}

	/** Set Condition.
		@param UNS_DocSchema_Condition_ID Condition	  */
	public void setUNS_DocSchema_Condition_ID (int UNS_DocSchema_Condition_ID)
	{
		if (UNS_DocSchema_Condition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DocSchema_Condition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DocSchema_Condition_ID, Integer.valueOf(UNS_DocSchema_Condition_ID));
	}

	/** Get Condition.
		@return Condition	  */
	public int getUNS_DocSchema_Condition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DocSchema_Condition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DocSchema_Condition_UU.
		@param UNS_DocSchema_Condition_UU UNS_DocSchema_Condition_UU	  */
	public void setUNS_DocSchema_Condition_UU (String UNS_DocSchema_Condition_UU)
	{
		set_Value (COLUMNNAME_UNS_DocSchema_Condition_UU, UNS_DocSchema_Condition_UU);
	}

	/** Get UNS_DocSchema_Condition_UU.
		@return UNS_DocSchema_Condition_UU	  */
	public String getUNS_DocSchema_Condition_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DocSchema_Condition_UU);
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

	/** Set Value From.
		@param ValueFrom Value From	  */
	public void setValueFrom (String ValueFrom)
	{
		set_Value (COLUMNNAME_ValueFrom, ValueFrom);
	}

	/** Get Value From.
		@return Value From	  */
	public String getValueFrom () 
	{
		return (String)get_Value(COLUMNNAME_ValueFrom);
	}

	/** Set Value To.
		@param ValueTo Value To	  */
	public void setValueTo (String ValueTo)
	{
		set_Value (COLUMNNAME_ValueTo, ValueTo);
	}

	/** Get Value To.
		@return Value To	  */
	public String getValueTo () 
	{
		return (String)get_Value(COLUMNNAME_ValueTo);
	}
}