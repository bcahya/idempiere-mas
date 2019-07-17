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

/** Generated Model for UNS_QAStatus_AttributeTest
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_AttributeTest extends PO implements I_UNS_QAStatus_AttributeTest, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130615L;

    /** Standard Constructor */
    public X_UNS_QAStatus_AttributeTest (Properties ctx, int UNS_QAStatus_AttributeTest_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_AttributeTest_ID, trxName);
      /** if (UNS_QAStatus_AttributeTest_ID == 0)
        {
			setName (null);
			setUNS_QAAttributeTest_ID (0);
			setUNS_QAStatus_AttributeTest_ID (0);
			setUNS_QAStatus_InOutLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_AttributeTest (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_AttributeTest[")
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public I_UNS_QAAttributeTest getUNS_QAAttributeTest() throws RuntimeException
    {
		return (I_UNS_QAAttributeTest)MTable.get(getCtx(), I_UNS_QAAttributeTest.Table_Name)
			.getPO(getUNS_QAAttributeTest_ID(), get_TrxName());	}

	/** Set Attribute Test.
		@param UNS_QAAttributeTest_ID Attribute Test	  */
	public void setUNS_QAAttributeTest_ID (int UNS_QAAttributeTest_ID)
	{
		if (UNS_QAAttributeTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, Integer.valueOf(UNS_QAAttributeTest_ID));
	}

	/** Get Attribute Test.
		@return Attribute Test	  */
	public int getUNS_QAAttributeTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAAttributeTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_QAMLab_Result getUNS_QAMLab_Result() throws RuntimeException
    {
		return (I_UNS_QAMLab_Result)MTable.get(getCtx(), I_UNS_QAMLab_Result.Table_Name)
			.getPO(getUNS_QAMLab_Result_ID(), get_TrxName());	}

	/** Set Laboratory Result.
		@param UNS_QAMLab_Result_ID Laboratory Result	  */
	public void setUNS_QAMLab_Result_ID (int UNS_QAMLab_Result_ID)
	{
		if (UNS_QAMLab_Result_ID < 1) 
			set_Value (COLUMNNAME_UNS_QAMLab_Result_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_QAMLab_Result_ID, Integer.valueOf(UNS_QAMLab_Result_ID));
	}

	/** Get Laboratory Result.
		@return Laboratory Result	  */
	public int getUNS_QAMLab_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute Test.
		@param UNS_QAStatus_AttributeTest_ID Attribute Test	  */
	public void setUNS_QAStatus_AttributeTest_ID (int UNS_QAStatus_AttributeTest_ID)
	{
		if (UNS_QAStatus_AttributeTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_AttributeTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_AttributeTest_ID, Integer.valueOf(UNS_QAStatus_AttributeTest_ID));
	}

	/** Get Attribute Test.
		@return Attribute Test	  */
	public int getUNS_QAStatus_AttributeTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_AttributeTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_AttributeTest_UU.
		@param UNS_QAStatus_AttributeTest_UU UNS_QAStatus_AttributeTest_UU	  */
	public void setUNS_QAStatus_AttributeTest_UU (String UNS_QAStatus_AttributeTest_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_AttributeTest_UU, UNS_QAStatus_AttributeTest_UU);
	}

	/** Get UNS_QAStatus_AttributeTest_UU.
		@return UNS_QAStatus_AttributeTest_UU	  */
	public String getUNS_QAStatus_AttributeTest_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_AttributeTest_UU);
	}

	public I_UNS_QAStatus_InOutLine getUNS_QAStatus_InOutLine() throws RuntimeException
    {
		return (I_UNS_QAStatus_InOutLine)MTable.get(getCtx(), I_UNS_QAStatus_InOutLine.Table_Name)
			.getPO(getUNS_QAStatus_InOutLine_ID(), get_TrxName());	}

	/** Set Ship/Receipt Inspection Line.
		@param UNS_QAStatus_InOutLine_ID Ship/Receipt Inspection Line	  */
	public void setUNS_QAStatus_InOutLine_ID (int UNS_QAStatus_InOutLine_ID)
	{
		if (UNS_QAStatus_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, Integer.valueOf(UNS_QAStatus_InOutLine_ID));
	}

	/** Get Ship/Receipt Inspection Line.
		@return Ship/Receipt Inspection Line	  */
	public int getUNS_QAStatus_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}