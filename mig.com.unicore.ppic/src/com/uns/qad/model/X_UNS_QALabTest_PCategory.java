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
package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_QALabTest_PCategory
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QALabTest_PCategory extends PO implements I_UNS_QALabTest_PCategory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130523L;

    /** Standard Constructor */
    public X_UNS_QALabTest_PCategory (Properties ctx, int UNS_QALabTest_PCategory_ID, String trxName)
    {
      super (ctx, UNS_QALabTest_PCategory_ID, trxName);
      /** if (UNS_QALabTest_PCategory_ID == 0)
        {
			setM_Product_Category_ID (0);
			setUNS_QALabTest_ID (0);
			setUNS_QALabTest_PCategory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QALabTest_PCategory (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_QALabTest_PCategory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QALabTest getUNS_QALabTest() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QALabTest)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QALabTest.Table_Name)
			.getPO(getUNS_QALabTest_ID(), get_TrxName());	}

	/** Set Labolatory Test.
		@param UNS_QALabTest_ID Labolatory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID)
	{
		if (UNS_QALabTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_ID, Integer.valueOf(UNS_QALabTest_ID));
	}

	/** Get Labolatory Test.
		@return Labolatory Test	  */
	public int getUNS_QALabTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QALabTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Category List.
		@param UNS_QALabTest_PCategory_ID Product Category List	  */
	public void setUNS_QALabTest_PCategory_ID (int UNS_QALabTest_PCategory_ID)
	{
		if (UNS_QALabTest_PCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_PCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_PCategory_ID, Integer.valueOf(UNS_QALabTest_PCategory_ID));
	}

	/** Get Product Category List.
		@return Product Category List	  */
	public int getUNS_QALabTest_PCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QALabTest_PCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QALabTest_PCategory_UU.
		@param UNS_QALabTest_PCategory_UU UNS_QALabTest_PCategory_UU	  */
	public void setUNS_QALabTest_PCategory_UU (String UNS_QALabTest_PCategory_UU)
	{
		set_Value (COLUMNNAME_UNS_QALabTest_PCategory_UU, UNS_QALabTest_PCategory_UU);
	}

	/** Get UNS_QALabTest_PCategory_UU.
		@return UNS_QALabTest_PCategory_UU	  */
	public String getUNS_QALabTest_PCategory_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QALabTest_PCategory_UU);
	}
}