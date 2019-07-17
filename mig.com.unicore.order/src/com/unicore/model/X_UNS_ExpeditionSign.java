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

/** Generated Model for UNS_ExpeditionSign
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ExpeditionSign extends PO implements I_UNS_ExpeditionSign, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170301L;

    /** Standard Constructor */
    public X_UNS_ExpeditionSign (Properties ctx, int UNS_ExpeditionSign_ID, String trxName)
    {
      super (ctx, UNS_ExpeditionSign_ID, trxName);
      /** if (UNS_ExpeditionSign_ID == 0)
        {
			setAddress (null);
			setBPartnerName (null);
			setIsManual (true);
// Y
			setUNS_ExpeditionDetailPO_ID (0);
			setUNS_ExpeditionSign_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ExpeditionSign (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ExpeditionSign[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address.
		@param Address Address	  */
	public void setAddress (String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

	/** Get Address.
		@return Address	  */
	public String getAddress () 
	{
		return (String)get_Value(COLUMNNAME_Address);
	}

	/** Set BPartnerName.
		@param BPartnerName BPartnerName	  */
	public void setBPartnerName (String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	/** Get BPartnerName.
		@return BPartnerName	  */
	public String getBPartnerName () 
	{
		return (String)get_Value(COLUMNNAME_BPartnerName);
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
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

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public com.unicore.model.I_UNS_ExpeditionDetail getUNS_ExpeditionDetailPO() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ExpeditionDetail)MTable.get(getCtx(), com.unicore.model.I_UNS_ExpeditionDetail.Table_Name)
			.getPO(getUNS_ExpeditionDetailPO_ID(), get_TrxName());	}

	/** Set Expedition Detail PO.
		@param UNS_ExpeditionDetailPO_ID Expedition Detail PO	  */
	public void setUNS_ExpeditionDetailPO_ID (int UNS_ExpeditionDetailPO_ID)
	{
		if (UNS_ExpeditionDetailPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetailPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetailPO_ID, Integer.valueOf(UNS_ExpeditionDetailPO_ID));
	}

	/** Get Expedition Detail PO.
		@return Expedition Detail PO	  */
	public int getUNS_ExpeditionDetailPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionDetailPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_ExpeditionDetail getUNS_ExpeditionDetailSO() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_ExpeditionDetail)MTable.get(getCtx(), com.unicore.model.I_UNS_ExpeditionDetail.Table_Name)
			.getPO(getUNS_ExpeditionDetailSO_ID(), get_TrxName());	}

	/** Set Expedition Detail SO.
		@param UNS_ExpeditionDetailSO_ID Expedition Detail SO	  */
	public void setUNS_ExpeditionDetailSO_ID (int UNS_ExpeditionDetailSO_ID)
	{
		if (UNS_ExpeditionDetailSO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetailSO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionDetailSO_ID, Integer.valueOf(UNS_ExpeditionDetailSO_ID));
	}

	/** Get Expedition Detail SO.
		@return Expedition Detail SO	  */
	public int getUNS_ExpeditionDetailSO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionDetailSO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Expedition Sign.
		@param UNS_ExpeditionSign_ID Expedition Sign	  */
	public void setUNS_ExpeditionSign_ID (int UNS_ExpeditionSign_ID)
	{
		if (UNS_ExpeditionSign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionSign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ExpeditionSign_ID, Integer.valueOf(UNS_ExpeditionSign_ID));
	}

	/** Get Expedition Sign.
		@return Expedition Sign	  */
	public int getUNS_ExpeditionSign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ExpeditionSign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ExpeditionSign_UU.
		@param UNS_ExpeditionSign_UU UNS_ExpeditionSign_UU	  */
	public void setUNS_ExpeditionSign_UU (String UNS_ExpeditionSign_UU)
	{
		set_Value (COLUMNNAME_UNS_ExpeditionSign_UU, UNS_ExpeditionSign_UU);
	}

	/** Get UNS_ExpeditionSign_UU.
		@return UNS_ExpeditionSign_UU	  */
	public String getUNS_ExpeditionSign_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ExpeditionSign_UU);
	}
}