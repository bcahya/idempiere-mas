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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_AdditionalPoint
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AdditionalPoint extends PO implements I_UNS_AdditionalPoint, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150520L;

    /** Standard Constructor */
    public X_UNS_AdditionalPoint (Properties ctx, int UNS_AdditionalPoint_ID, String trxName)
    {
      super (ctx, UNS_AdditionalPoint_ID, trxName);
      /** if (UNS_AdditionalPoint_ID == 0)
        {
			setUNS_AdditionalPoint_ID (0);
			setUNS_AdditionalPoint_UU (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_AdditionalPoint (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AdditionalPoint[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Balance Point.
		@param BalancePoint 
		Balance of Point
	  */
	public void setBalancePoint (BigDecimal BalancePoint)
	{
		throw new IllegalArgumentException ("BalancePoint is virtual column");	}

	/** Get Balance Point.
		@return Balance of Point
	  */
	public BigDecimal getBalancePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BalancePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CurrentPoint.
		@param CurrentPoint CurrentPoint	  */
	public void setCurrentPoint (BigDecimal CurrentPoint)
	{
		set_Value (COLUMNNAME_CurrentPoint, CurrentPoint);
	}

	/** Get CurrentPoint.
		@return CurrentPoint	  */
	public BigDecimal getCurrentPoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentPoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Additional Point.
		@param UNS_AdditionalPoint_ID Additional Point	  */
	public void setUNS_AdditionalPoint_ID (int UNS_AdditionalPoint_ID)
	{
		if (UNS_AdditionalPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AdditionalPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AdditionalPoint_ID, Integer.valueOf(UNS_AdditionalPoint_ID));
	}

	/** Get Additional Point.
		@return Additional Point	  */
	public int getUNS_AdditionalPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AdditionalPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AdditionalPoint_UU.
		@param UNS_AdditionalPoint_UU UNS_AdditionalPoint_UU	  */
	public void setUNS_AdditionalPoint_UU (String UNS_AdditionalPoint_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AdditionalPoint_UU, UNS_AdditionalPoint_UU);
	}

	/** Get UNS_AdditionalPoint_UU.
		@return UNS_AdditionalPoint_UU	  */
	public String getUNS_AdditionalPoint_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AdditionalPoint_UU);
	}

	public com.unicore.model.I_UNS_CustomerRedeem getUNS_CustomerRedeem() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CustomerRedeem)MTable.get(getCtx(), com.unicore.model.I_UNS_CustomerRedeem.Table_Name)
			.getPO(getUNS_CustomerRedeem_ID(), get_TrxName());	}

	/** Set Customer Redeem Point.
		@param UNS_CustomerRedeem_ID Customer Redeem Point	  */
	public void setUNS_CustomerRedeem_ID (int UNS_CustomerRedeem_ID)
	{
		if (UNS_CustomerRedeem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerRedeem_ID, Integer.valueOf(UNS_CustomerRedeem_ID));
	}

	/** Get Customer Redeem Point.
		@return Customer Redeem Point	  */
	public int getUNS_CustomerRedeem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerRedeem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ValuePoint.
		@param ValuePoint ValuePoint	  */
	public void setValuePoint (BigDecimal ValuePoint)
	{
		set_Value (COLUMNNAME_ValuePoint, ValuePoint);
	}

	/** Get ValuePoint.
		@return ValuePoint	  */
	public BigDecimal getValuePoint () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValuePoint);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}