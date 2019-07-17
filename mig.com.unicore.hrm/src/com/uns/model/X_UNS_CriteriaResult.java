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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_CriteriaResult
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_CriteriaResult extends PO implements I_UNS_CriteriaResult, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130604L;

    /** Standard Constructor */
    public X_UNS_CriteriaResult (Properties ctx, int UNS_CriteriaResult_ID, String trxName)
    {
      super (ctx, UNS_CriteriaResult_ID, trxName);
      /** if (UNS_CriteriaResult_ID == 0)
        {
			setDescription (null);
			setName (null);
			setResult (Env.ZERO);
			setResultAmt (Env.ZERO);
// 0
			setUNS_CriteriaResult_ID (0);
			setUNS_HalfPeriod_Presence_ID (0);
			setUNS_PayRollLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CriteriaResult (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CriteriaResult[")
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

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (BigDecimal Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public BigDecimal getResult () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Result);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Result Amount.
		@param ResultAmt Result Amount	  */
	public void setResultAmt (BigDecimal ResultAmt)
	{
		set_Value (COLUMNNAME_ResultAmt, ResultAmt);
	}

	/** Get Result Amount.
		@return Result Amount	  */
	public BigDecimal getResultAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResultAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Criteria Result.
		@param UNS_CriteriaResult_ID Criteria Result	  */
	public void setUNS_CriteriaResult_ID (int UNS_CriteriaResult_ID)
	{
		if (UNS_CriteriaResult_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CriteriaResult_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CriteriaResult_ID, Integer.valueOf(UNS_CriteriaResult_ID));
	}

	/** Get Criteria Result.
		@return Criteria Result	  */
	public int getUNS_CriteriaResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CriteriaResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CriteriaResult_UU.
		@param UNS_CriteriaResult_UU UNS_CriteriaResult_UU	  */
	public void setUNS_CriteriaResult_UU (String UNS_CriteriaResult_UU)
	{
		set_Value (COLUMNNAME_UNS_CriteriaResult_UU, UNS_CriteriaResult_UU);
	}

	/** Get UNS_CriteriaResult_UU.
		@return UNS_CriteriaResult_UU	  */
	public String getUNS_CriteriaResult_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CriteriaResult_UU);
	}

	public com.uns.model.I_UNS_HalfPeriod_Presence getUNS_HalfPeriod_Presence() throws RuntimeException
    {
		return (com.uns.model.I_UNS_HalfPeriod_Presence)MTable.get(getCtx(), com.uns.model.I_UNS_HalfPeriod_Presence.Table_Name)
			.getPO(getUNS_HalfPeriod_Presence_ID(), get_TrxName());	}

	/** Set Half Period Presence.
		@param UNS_HalfPeriod_Presence_ID Half Period Presence	  */
	public void setUNS_HalfPeriod_Presence_ID (int UNS_HalfPeriod_Presence_ID)
	{
		if (UNS_HalfPeriod_Presence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, Integer.valueOf(UNS_HalfPeriod_Presence_ID));
	}

	/** Get Half Period Presence.
		@return Half Period Presence	  */
	public int getUNS_HalfPeriod_Presence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HalfPeriod_Presence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_PayRollLine getUNS_PayRollLine() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PayRollLine)MTable.get(getCtx(), com.uns.model.I_UNS_PayRollLine.Table_Name)
			.getPO(getUNS_PayRollLine_ID(), get_TrxName());	}

	/** Set Pay Roll Line.
		@param UNS_PayRollLine_ID Pay Roll Line	  */
	public void setUNS_PayRollLine_ID (int UNS_PayRollLine_ID)
	{
		if (UNS_PayRollLine_ID < 1) 
			set_Value (COLUMNNAME_UNS_PayRollLine_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PayRollLine_ID, Integer.valueOf(UNS_PayRollLine_ID));
	}

	/** Get Pay Roll Line.
		@return Pay Roll Line	  */
	public int getUNS_PayRollLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayRollLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}