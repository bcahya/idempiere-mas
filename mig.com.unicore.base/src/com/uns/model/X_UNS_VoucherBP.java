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

/** Generated Model for UNS_VoucherBP
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_VoucherBP extends PO implements I_UNS_VoucherBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150530L;

    /** Standard Constructor */
    public X_UNS_VoucherBP (Properties ctx, int UNS_VoucherBP_ID, String trxName)
    {
      super (ctx, UNS_VoucherBP_ID, trxName);
      /** if (UNS_VoucherBP_ID == 0)
        {
			setC_BPartner_ID (0);
			setUNS_VoucherBP_ID (0);
			setUNS_Voucher_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_VoucherBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_VoucherBP[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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

	/** Set Voucher Partner.
		@param UNS_VoucherBP_ID Voucher Partner	  */
	public void setUNS_VoucherBP_ID (int UNS_VoucherBP_ID)
	{
		if (UNS_VoucherBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_VoucherBP_ID, Integer.valueOf(UNS_VoucherBP_ID));
	}

	/** Get Voucher Partner.
		@return Voucher Partner	  */
	public int getUNS_VoucherBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_VoucherBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_VoucherBP_UU.
		@param UNS_VoucherBP_UU UNS_VoucherBP_UU	  */
	public void setUNS_VoucherBP_UU (String UNS_VoucherBP_UU)
	{
		set_Value (COLUMNNAME_UNS_VoucherBP_UU, UNS_VoucherBP_UU);
	}

	/** Get UNS_VoucherBP_UU.
		@return UNS_VoucherBP_UU	  */
	public String getUNS_VoucherBP_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_VoucherBP_UU);
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