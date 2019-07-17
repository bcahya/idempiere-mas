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

/** Generated Model for UNS_VoucherFuel
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VoucherFuel extends PO implements I_UNS_VoucherFuel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150608L;

    /** Standard Constructor */
    public X_UNS_VoucherFuel (Properties ctx, int UNS_VoucherFuel_ID, String trxName)
    {
      super (ctx, UNS_VoucherFuel_ID, trxName);
      /** if (UNS_VoucherFuel_ID == 0)
        {
			setIsDefault (false);
// N
			setProcessed (false);
			setUNS_Fuel_ID (0);
			setUNS_VoucherFuel_ID (0);
			setUNS_Voucher_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VoucherFuel (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VoucherFuel[")
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

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public com.uns.model.I_UNS_Fuel getUNS_Fuel() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Fuel)MTable.get(getCtx(), com.uns.model.I_UNS_Fuel.Table_Name)
			.getPO(getUNS_Fuel_ID(), get_TrxName());	}

	/** Set Fuel.
		@param UNS_Fuel_ID Fuel	  */
	public void setUNS_Fuel_ID (int UNS_Fuel_ID)
	{
		if (UNS_Fuel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Fuel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Fuel_ID, Integer.valueOf(UNS_Fuel_ID));
	}

	/** Get Fuel.
		@return Fuel	  */
	public int getUNS_Fuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Fuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Voucher Fuel Type.
		@param UNS_VoucherFuel_ID Voucher Fuel Type	  */
	public void setUNS_VoucherFuel_ID (int UNS_VoucherFuel_ID)
	{
		if (UNS_VoucherFuel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherFuel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherFuel_ID, Integer.valueOf(UNS_VoucherFuel_ID));
	}

	/** Get Voucher Fuel Type.
		@return Voucher Fuel Type	  */
	public int getUNS_VoucherFuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VoucherFuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VoucherFuel_UU.
		@param UNS_VoucherFuel_UU UNS_VoucherFuel_UU	  */
	public void setUNS_VoucherFuel_UU (String UNS_VoucherFuel_UU)
	{
		set_Value (COLUMNNAME_UNS_VoucherFuel_UU, UNS_VoucherFuel_UU);
	}

	/** Get UNS_VoucherFuel_UU.
		@return UNS_VoucherFuel_UU	  */
	public String getUNS_VoucherFuel_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VoucherFuel_UU);
	}

	public com.uns.model.I_UNS_Voucher getUNS_Voucher() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Voucher)MTable.get(getCtx(), com.uns.model.I_UNS_Voucher.Table_Name)
			.getPO(getUNS_Voucher_ID(), get_TrxName());	}

	/** Set Voucher.
		@param UNS_Voucher_ID Voucher	  */
	public void setUNS_Voucher_ID (int UNS_Voucher_ID)
	{
		if (UNS_Voucher_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Voucher_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Voucher_ID, Integer.valueOf(UNS_Voucher_ID));
	}

	/** Get Voucher.
		@return Voucher	  */
	public int getUNS_Voucher_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Voucher_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}