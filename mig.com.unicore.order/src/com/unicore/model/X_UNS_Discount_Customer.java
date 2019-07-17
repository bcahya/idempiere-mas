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

/** Generated Model for UNS_Discount_Customer
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Discount_Customer extends PO implements I_UNS_Discount_Customer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150306L;

    /** Standard Constructor */
    public X_UNS_Discount_Customer (Properties ctx, int UNS_Discount_Customer_ID, String trxName)
    {
      super (ctx, UNS_Discount_Customer_ID, trxName);
      /** if (UNS_Discount_Customer_ID == 0)
        {
			setUNS_Discount_Customer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Discount_Customer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Discount_Customer[")
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

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Discount Schema.
		@return Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Customer.
		@param UNS_Discount_Customer_ID Discount Customer	  */
	public void setUNS_Discount_Customer_ID (int UNS_Discount_Customer_ID)
	{
		if (UNS_Discount_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Customer_ID, Integer.valueOf(UNS_Discount_Customer_ID));
	}

	/** Get Discount Customer.
		@return Discount Customer	  */
	public int getUNS_Discount_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Discount_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Discount_Customer_UU.
		@param UNS_Discount_Customer_UU UNS_Discount_Customer_UU	  */
	public void setUNS_Discount_Customer_UU (String UNS_Discount_Customer_UU)
	{
		set_Value (COLUMNNAME_UNS_Discount_Customer_UU, UNS_Discount_Customer_UU);
	}

	/** Get UNS_Discount_Customer_UU.
		@return UNS_Discount_Customer_UU	  */
	public String getUNS_Discount_Customer_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Discount_Customer_UU);
	}

	/** Set Outlet Grade.
		@param UNS_Outlet_Grade_ID Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID)
	{
		if (UNS_Outlet_Grade_ID < 1) 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Outlet_Grade_ID, Integer.valueOf(UNS_Outlet_Grade_ID));
	}

	/** Get Outlet Grade.
		@return Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Outlet_Grade_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}