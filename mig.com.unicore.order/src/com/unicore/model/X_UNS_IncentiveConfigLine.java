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

/** Generated Model for UNS_IncentiveConfigLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_IncentiveConfigLine extends PO implements I_UNS_IncentiveConfigLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180122L;

    /** Standard Constructor */
    public X_UNS_IncentiveConfigLine (Properties ctx, int UNS_IncentiveConfigLine_ID, String trxName)
    {
      super (ctx, UNS_IncentiveConfigLine_ID, trxName);
      /** if (UNS_IncentiveConfigLine_ID == 0)
        {
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_IncentiveConfigLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_IncentiveConfigLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public com.unicore.model.I_UNS_IncentiveConfig getUNS_IncentiveConfig() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_IncentiveConfig)MTable.get(getCtx(), com.unicore.model.I_UNS_IncentiveConfig.Table_Name)
			.getPO(getUNS_IncentiveConfig_ID(), get_TrxName());	}

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

	/** Set Configuration Line.
		@param UNS_IncentiveConfigLine_ID Configuration Line	  */
	public void setUNS_IncentiveConfigLine_ID (int UNS_IncentiveConfigLine_ID)
	{
		if (UNS_IncentiveConfigLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigLine_ID, Integer.valueOf(UNS_IncentiveConfigLine_ID));
	}

	/** Get Configuration Line.
		@return Configuration Line	  */
	public int getUNS_IncentiveConfigLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveConfigLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_IncentiveConfigLine_UU.
		@param UNS_IncentiveConfigLine_UU UNS_IncentiveConfigLine_UU	  */
	public void setUNS_IncentiveConfigLine_UU (String UNS_IncentiveConfigLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_IncentiveConfigLine_UU, UNS_IncentiveConfigLine_UU);
	}

	/** Get UNS_IncentiveConfigLine_UU.
		@return UNS_IncentiveConfigLine_UU	  */
	public String getUNS_IncentiveConfigLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_IncentiveConfigLine_UU);
	}
}