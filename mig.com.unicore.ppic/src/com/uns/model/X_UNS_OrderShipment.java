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

/** Generated Model for UNS_OrderShipment
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_OrderShipment extends PO implements I_UNS_OrderShipment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130122L;

    /** Standard Constructor */
    public X_UNS_OrderShipment (Properties ctx, int UNS_OrderShipment_ID, String trxName)
    {
      super (ctx, UNS_OrderShipment_ID, trxName);
      /** if (UNS_OrderShipment_ID == 0)
        {
			setC_Order_ID (0);
// @C_Order_ID@
			setLegalRequirement (false);
			setSOStatus (null);
// OUTSTANDING
			setUNS_OrderShipment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OrderShipment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OrderShipment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Legal Requirement.
		@param LegalRequirement Legal Requirement	  */
	public void setLegalRequirement (boolean LegalRequirement)
	{
		set_Value (COLUMNNAME_LegalRequirement, Boolean.valueOf(LegalRequirement));
	}

	/** Get Legal Requirement.
		@return Legal Requirement	  */
	public boolean isLegalRequirement () 
	{
		Object oo = get_Value(COLUMNNAME_LegalRequirement);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{
		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
	}

	/** Set SO Status.
		@param SOStatus SO Status	  */
	public void setSOStatus (String SOStatus)
	{
		set_Value (COLUMNNAME_SOStatus, SOStatus);
	}

	/** Get SO Status.
		@return SO Status	  */
	public String getSOStatus () 
	{
		return (String)get_Value(COLUMNNAME_SOStatus);
	}

	/** Set Trading T Factory.
		@param TradingTermFactory Trading T Factory	  */
	public void setTradingTermFactory (String TradingTermFactory)
	{
		set_Value (COLUMNNAME_TradingTermFactory, TradingTermFactory);
	}

	/** Get Trading T Factory.
		@return Trading T Factory	  */
	public String getTradingTermFactory () 
	{
		return (String)get_Value(COLUMNNAME_TradingTermFactory);
	}

	/** Set Order Shipment.
		@param UNS_OrderShipment_ID Order Shipment	  */
	public void setUNS_OrderShipment_ID (int UNS_OrderShipment_ID)
	{
		if (UNS_OrderShipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderShipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderShipment_ID, Integer.valueOf(UNS_OrderShipment_ID));
	}

	/** Get Order Shipment.
		@return Order Shipment	  */
	public int getUNS_OrderShipment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrderShipment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}