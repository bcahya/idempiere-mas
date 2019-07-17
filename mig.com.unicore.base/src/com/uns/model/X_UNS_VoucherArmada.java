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

/** Generated Model for UNS_VoucherArmada
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VoucherArmada extends PO implements I_UNS_VoucherArmada, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150606L;

    /** Standard Constructor */
    public X_UNS_VoucherArmada (Properties ctx, int UNS_VoucherArmada_ID, String trxName)
    {
      super (ctx, UNS_VoucherArmada_ID, trxName);
      /** if (UNS_VoucherArmada_ID == 0)
        {
			setProcessed (false);
// N
			setUNS_Armada_ID (0);
			setUNS_VoucherArmada_ID (0);
			setUNS_Voucher_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VoucherArmada (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VoucherArmada[")
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

	public com.uns.model.I_UNS_Armada getUNS_Armada() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Armada)MTable.get(getCtx(), com.uns.model.I_UNS_Armada.Table_Name)
			.getPO(getUNS_Armada_ID(), get_TrxName());	}

	/** Set Armada.
		@param UNS_Armada_ID Armada	  */
	public void setUNS_Armada_ID (int UNS_Armada_ID)
	{
		if (UNS_Armada_ID < 1) 
			set_Value (COLUMNNAME_UNS_Armada_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Armada_ID, Integer.valueOf(UNS_Armada_ID));
	}

	/** Get Armada.
		@return Armada	  */
	public int getUNS_Armada_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Armada_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Voucher Armada.
		@param UNS_VoucherArmada_ID Voucher Armada	  */
	public void setUNS_VoucherArmada_ID (int UNS_VoucherArmada_ID)
	{
		if (UNS_VoucherArmada_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherArmada_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherArmada_ID, Integer.valueOf(UNS_VoucherArmada_ID));
	}

	/** Get Voucher Armada.
		@return Voucher Armada	  */
	public int getUNS_VoucherArmada_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VoucherArmada_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VoucherArmada_UU.
		@param UNS_VoucherArmada_UU UNS_VoucherArmada_UU	  */
	public void setUNS_VoucherArmada_UU (String UNS_VoucherArmada_UU)
	{
		set_Value (COLUMNNAME_UNS_VoucherArmada_UU, UNS_VoucherArmada_UU);
	}

	/** Get UNS_VoucherArmada_UU.
		@return UNS_VoucherArmada_UU	  */
	public String getUNS_VoucherArmada_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VoucherArmada_UU);
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