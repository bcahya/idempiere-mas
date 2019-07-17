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

/** Generated Model for UNS_Discount_Org
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Discount_Org extends PO implements I_UNS_Discount_Org, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150331L;

    /** Standard Constructor */
    public X_UNS_Discount_Org (Properties ctx, int UNS_Discount_Org_ID, String trxName)
    {
      super (ctx, UNS_Discount_Org_ID, trxName);
      /** if (UNS_Discount_Org_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM UNS_DiscountBonus WHERE  CASE   WHEN UNS_DiscountBonus.M_DiscountSchema_ID IS NOT NULL    THEN UNS_DiscountBonus.M_DiscountSchema_ID=@M_DiscountSchema_ID@  WHEN UNS_DiscountBonus.M_DiscountSchemaBreak_ID IS NOT NULL    THEN UNS_DiscountBonus.M_DiscountSchemaBreak_ID=@M_DiscountSchemaBreak_ID@  WHEN UNS_DiscountBonus.UNS_DSBreakLine_ID IS NOT NULL    THEN UNS_DiscountBonus.UNS_DSBreakLine_ID=@UNS_DSBreakLine_ID@  ELSE 1=1 END
			setUNS_Discount_Org_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Discount_Org (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Discount_Org[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
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

	/** Set Discount Organization.
		@param UNS_Discount_Org_ID Discount Organization	  */
	public void setUNS_Discount_Org_ID (int UNS_Discount_Org_ID)
	{
		if (UNS_Discount_Org_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Org_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Discount_Org_ID, Integer.valueOf(UNS_Discount_Org_ID));
	}

	/** Get Discount Organization.
		@return Discount Organization	  */
	public int getUNS_Discount_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Discount_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Discount_Org_UU.
		@param UNS_Discount_Org_UU UNS_Discount_Org_UU	  */
	public void setUNS_Discount_Org_UU (String UNS_Discount_Org_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Discount_Org_UU, UNS_Discount_Org_UU);
	}

	/** Get UNS_Discount_Org_UU.
		@return UNS_Discount_Org_UU	  */
	public String getUNS_Discount_Org_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Discount_Org_UU);
	}
}