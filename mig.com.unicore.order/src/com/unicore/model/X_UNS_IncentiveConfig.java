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

/** Generated Model for UNS_IncentiveConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_IncentiveConfig extends PO implements I_UNS_IncentiveConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180126L;

    /** Standard Constructor */
    public X_UNS_IncentiveConfig (Properties ctx, int UNS_IncentiveConfig_ID, String trxName)
    {
      super (ctx, UNS_IncentiveConfig_ID, trxName);
      /** if (UNS_IncentiveConfig_ID == 0)
        {
			setIsIncentiveAmount (false);
// N
			setIsUseBaseAmt (false);
// N
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_IncentiveConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_IncentiveConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Incentive Amount.
		@param IncentiveAmt Incentive Amount	  */
	public void setIncentiveAmt (BigDecimal IncentiveAmt)
	{
		set_Value (COLUMNNAME_IncentiveAmt, IncentiveAmt);
	}

	/** Get Incentive Amount.
		@return Incentive Amount	  */
	public BigDecimal getIncentiveAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IncentiveAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Billing Incentive = BI */
	public static final String INCENTIVETYPE_BillingIncentive = "BI";
	/** Sales Incentive = SI */
	public static final String INCENTIVETYPE_SalesIncentive = "SI";
	/** New Outlet Incentive = NI */
	public static final String INCENTIVETYPE_NewOutletIncentive = "NI";
	/** Delivery Incentive = DI */
	public static final String INCENTIVETYPE_DeliveryIncentive = "DI";
	/** Order Incentive = OI */
	public static final String INCENTIVETYPE_OrderIncentive = "OI";
	/** Set Incentive Type.
		@param IncentiveType 
		Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType)
	{

		set_Value (COLUMNNAME_IncentiveType, IncentiveType);
	}

	/** Get Incentive Type.
		@return Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType () 
	{
		return (String)get_Value(COLUMNNAME_IncentiveType);
	}

	/** Set Incentive Amount.
		@param IsIncentiveAmount 
		uncheck this check box if you will use percent
	  */
	public void setIsIncentiveAmount (boolean IsIncentiveAmount)
	{
		set_Value (COLUMNNAME_IsIncentiveAmount, Boolean.valueOf(IsIncentiveAmount));
	}

	/** Get Incentive Amount.
		@return uncheck this check box if you will use percent
	  */
	public boolean isIncentiveAmount () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncentiveAmount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Base Amount.
		@param IsUseBaseAmt Use Base Amount	  */
	public void setIsUseBaseAmt (boolean IsUseBaseAmt)
	{
		set_Value (COLUMNNAME_IsUseBaseAmt, Boolean.valueOf(IsUseBaseAmt));
	}

	/** Get Use Base Amount.
		@return Use Base Amount	  */
	public boolean isUseBaseAmt () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBaseAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Incentive Configuration.
		@param UNS_IncentiveConfig_ID Incentive Configuration	  */
	public void setUNS_IncentiveConfig_ID (int UNS_IncentiveConfig_ID)
	{
		if (UNS_IncentiveConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfig_ID, Integer.valueOf(UNS_IncentiveConfig_ID));
	}

	/** Get Incentive Configuration.
		@return Incentive Configuration	  */
	public int getUNS_IncentiveConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_IncentiveConfig_UU.
		@param UNS_IncentiveConfig_UU UNS_IncentiveConfig_UU	  */
	public void setUNS_IncentiveConfig_UU (String UNS_IncentiveConfig_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfig_UU, UNS_IncentiveConfig_UU);
	}

	/** Get UNS_IncentiveConfig_UU.
		@return UNS_IncentiveConfig_UU	  */
	public String getUNS_IncentiveConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_IncentiveConfig_UU);
	}
}