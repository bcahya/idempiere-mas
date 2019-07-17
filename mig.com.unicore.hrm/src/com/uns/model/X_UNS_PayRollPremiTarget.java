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

/** Generated Model for UNS_PayRollPremiTarget
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PayRollPremiTarget extends PO implements I_UNS_PayRollPremiTarget, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130611L;

    /** Standard Constructor */
    public X_UNS_PayRollPremiTarget (Properties ctx, int UNS_PayRollPremiTarget_ID, String trxName)
    {
      super (ctx, UNS_PayRollPremiTarget_ID, trxName);
      /** if (UNS_PayRollPremiTarget_ID == 0)
        {
			setPay (Env.ZERO);
			setTargetMax (Env.ZERO);
// 0
			setTargetMin (Env.ZERO);
// 0
			setUNS_PayRollLine_ID (0);
			setUNS_PayRollPremiTarget_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PayRollPremiTarget (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayRollPremiTarget[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AbsenType AD_Reference_ID=1000150 */
	public static final int ABSENTYPE_AD_Reference_ID=1000150;
	/** Payable Dispensation = PDP */
	public static final String ABSENTYPE_PayableDispensation = "PDP";
	/** All Absence Type = AAT */
	public static final String ABSENTYPE_AllAbsenceType = "AAT";
	/** Sakit = SKT */
	public static final String ABSENTYPE_Sakit = "SKT";
	/** Non Payable Dispensation = NPD */
	public static final String ABSENTYPE_NonPayableDispensation = "NPD";
	/** Mangkir = MKR */
	public static final String ABSENTYPE_Mangkir = "MKR";
	/** Set Absen Type.
		@param AbsenType Absen Type	  */
	public void setAbsenType (String AbsenType)
	{

		set_Value (COLUMNNAME_AbsenType, AbsenType);
	}

	/** Get Absen Type.
		@return Absen Type	  */
	public String getAbsenType () 
	{
		return (String)get_Value(COLUMNNAME_AbsenType);
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

	/** Set Pay.
		@param Pay Pay	  */
	public void setPay (BigDecimal Pay)
	{
		set_Value (COLUMNNAME_Pay, Pay);
	}

	/** Get Pay.
		@return Pay	  */
	public BigDecimal getPay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Pay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payable Amount.
		@param PayableAmt Payable Amount	  */
	public void setPayableAmt (BigDecimal PayableAmt)
	{
		set_Value (COLUMNNAME_PayableAmt, PayableAmt);
	}

	/** Get Payable Amount.
		@return Payable Amount	  */
	public BigDecimal getPayableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payable To Target.
		@param PayableToTarget_ID Payable To Target	  */
	public void setPayableToTarget_ID (int PayableToTarget_ID)
	{
		if (PayableToTarget_ID < 1) 
			set_Value (COLUMNNAME_PayableToTarget_ID, null);
		else 
			set_Value (COLUMNNAME_PayableToTarget_ID, Integer.valueOf(PayableToTarget_ID));
	}

	/** Get Payable To Target.
		@return Payable To Target	  */
	public int getPayableToTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayableToTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Target Max.
		@param TargetMax Target Max	  */
	public void setTargetMax (BigDecimal TargetMax)
	{
		set_Value (COLUMNNAME_TargetMax, TargetMax);
	}

	/** Get Target Max.
		@return Target Max	  */
	public BigDecimal getTargetMax () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetMax);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Min.
		@param TargetMin 
		Target Minimum
	  */
	public void setTargetMin (BigDecimal TargetMin)
	{
		set_Value (COLUMNNAME_TargetMin, TargetMin);
	}

	/** Get Target Min.
		@return Target Minimum
	  */
	public BigDecimal getTargetMin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetMin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollLine_ID, Integer.valueOf(UNS_PayRollLine_ID));
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

	/** Set Pay Roll Premi Target.
		@param UNS_PayRollPremiTarget_ID Pay Roll Premi Target	  */
	public void setUNS_PayRollPremiTarget_ID (int UNS_PayRollPremiTarget_ID)
	{
		if (UNS_PayRollPremiTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollPremiTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollPremiTarget_ID, Integer.valueOf(UNS_PayRollPremiTarget_ID));
	}

	/** Get Pay Roll Premi Target.
		@return Pay Roll Premi Target	  */
	public int getUNS_PayRollPremiTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayRollPremiTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PayRollPremiTarget_UU.
		@param UNS_PayRollPremiTarget_UU UNS_PayRollPremiTarget_UU	  */
	public void setUNS_PayRollPremiTarget_UU (String UNS_PayRollPremiTarget_UU)
	{
		set_Value (COLUMNNAME_UNS_PayRollPremiTarget_UU, UNS_PayRollPremiTarget_UU);
	}

	/** Get UNS_PayRollPremiTarget_UU.
		@return UNS_PayRollPremiTarget_UU	  */
	public String getUNS_PayRollPremiTarget_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PayRollPremiTarget_UU);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}