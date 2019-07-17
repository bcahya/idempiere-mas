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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_LC_AmdAttributes
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_AmdAttributes extends PO implements I_UNS_LC_AmdAttributes, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130529L;

    /** Standard Constructor */
    public X_UNS_LC_AmdAttributes (Properties ctx, int UNS_LC_AmdAttributes_ID, String trxName)
    {
      super (ctx, UNS_LC_AmdAttributes_ID, trxName);
      /** if (UNS_LC_AmdAttributes_ID == 0)
        {
			setAttributeNumber (null);
			setDataType (null);
			setHasItems (false);
// N
			setName (null);
			setUNS_LC_AmdAttributes_ID (0);
			setUNS_LC_Amendment_ID (0);
			setUNS_LC_AttributeConf_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_AmdAttributes (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_AmdAttributes[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Attribute Number.
		@param AttributeNumber 
		Number code of the Attribute
	  */
	public void setAttributeNumber (String AttributeNumber)
	{
		set_Value (COLUMNNAME_AttributeNumber, AttributeNumber);
	}

	/** Get Attribute Number.
		@return Number code of the Attribute
	  */
	public String getAttributeNumber () 
	{
		return (String)get_Value(COLUMNNAME_AttributeNumber);
	}

	/** DataType AD_Reference_ID=1000141 */
	public static final int DATATYPE_AD_Reference_ID=1000141;
	/** String = STR */
	public static final String DATATYPE_String = "STR";
	/** Integer = INT */
	public static final String DATATYPE_Integer = "INT";
	/** Date = DT */
	public static final String DATATYPE_Date = "DT";
	/** Number = NMR */
	public static final String DATATYPE_Number = "NMR";
	/** Memo = MM */
	public static final String DATATYPE_Memo = "MM";
	/** Text = TXT */
	public static final String DATATYPE_Text = "TXT";
	/** Product = PRD */
	public static final String DATATYPE_Product = "PRD";
	/** Set Data Type.
		@param DataType 
		Type of data
	  */
	public void setDataType (String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Data Type.
		@return Type of data
	  */
	public String getDataType () 
	{
		return (String)get_Value(COLUMNNAME_DataType);
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

	/** Set Has Items.
		@param HasItems Has Items	  */
	public void setHasItems (boolean HasItems)
	{
		set_Value (COLUMNNAME_HasItems, Boolean.valueOf(HasItems));
	}

	/** Get Has Items.
		@return Has Items	  */
	public boolean isHasItems () 
	{
		Object oo = get_Value(COLUMNNAME_HasItems);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set New Attribute Value.
		@param NewAttributeValueBD New Attribute Value	  */
	public void setNewAttributeValueBD (BigDecimal NewAttributeValueBD)
	{
		set_Value (COLUMNNAME_NewAttributeValueBD, NewAttributeValueBD);
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public BigDecimal getNewAttributeValueBD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewAttributeValueBD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set New Attribute Value.
		@param NewAttributeValueDate New Attribute Value	  */
	public void setNewAttributeValueDate (Timestamp NewAttributeValueDate)
	{
		set_Value (COLUMNNAME_NewAttributeValueDate, NewAttributeValueDate);
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public Timestamp getNewAttributeValueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NewAttributeValueDate);
	}

	/** Set New Attribute Value.
		@param NewAttributeValueInt New Attribute Value	  */
	public void setNewAttributeValueInt (int NewAttributeValueInt)
	{
		set_Value (COLUMNNAME_NewAttributeValueInt, Integer.valueOf(NewAttributeValueInt));
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public int getNewAttributeValueInt () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewAttributeValueInt);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set New Attribute Value.
		@param NewAttributeValueMemo New Attribute Value	  */
	public void setNewAttributeValueMemo (String NewAttributeValueMemo)
	{
		set_Value (COLUMNNAME_NewAttributeValueMemo, NewAttributeValueMemo);
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public String getNewAttributeValueMemo () 
	{
		return (String)get_Value(COLUMNNAME_NewAttributeValueMemo);
	}

	/** Set New Attribute Value.
		@param NewAttributeValueString New Attribute Value	  */
	public void setNewAttributeValueString (String NewAttributeValueString)
	{
		set_Value (COLUMNNAME_NewAttributeValueString, NewAttributeValueString);
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public String getNewAttributeValueString () 
	{
		return (String)get_Value(COLUMNNAME_NewAttributeValueString);
	}

	/** Set New Attribute Value.
		@param NewAttributeValueText New Attribute Value	  */
	public void setNewAttributeValueText (String NewAttributeValueText)
	{
		set_Value (COLUMNNAME_NewAttributeValueText, NewAttributeValueText);
	}

	/** Get New Attribute Value.
		@return New Attribute Value	  */
	public String getNewAttributeValueText () 
	{
		return (String)get_Value(COLUMNNAME_NewAttributeValueText);
	}

	public org.compiere.model.I_C_Currency getNewCurrency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getNewCurrency_ID(), get_TrxName());	}

	/** Set New Currency.
		@param NewCurrency_ID New Currency	  */
	public void setNewCurrency_ID (int NewCurrency_ID)
	{
		if (NewCurrency_ID < 1) 
			set_Value (COLUMNNAME_NewCurrency_ID, null);
		else 
			set_Value (COLUMNNAME_NewCurrency_ID, Integer.valueOf(NewCurrency_ID));
	}

	/** Get New Currency.
		@return New Currency	  */
	public int getNewCurrency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NewCurrency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueBD Prev Attribute Value	  */
	public void setPrevAttributeValueBD (BigDecimal PrevAttributeValueBD)
	{
		set_Value (COLUMNNAME_PrevAttributeValueBD, PrevAttributeValueBD);
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public BigDecimal getPrevAttributeValueBD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrevAttributeValueBD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueDate Prev Attribute Value	  */
	public void setPrevAttributeValueDate (Timestamp PrevAttributeValueDate)
	{
		set_Value (COLUMNNAME_PrevAttributeValueDate, PrevAttributeValueDate);
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public Timestamp getPrevAttributeValueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PrevAttributeValueDate);
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueInt Prev Attribute Value	  */
	public void setPrevAttributeValueInt (int PrevAttributeValueInt)
	{
		set_Value (COLUMNNAME_PrevAttributeValueInt, Integer.valueOf(PrevAttributeValueInt));
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public int getPrevAttributeValueInt () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevAttributeValueInt);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueMemo Prev Attribute Value	  */
	public void setPrevAttributeValueMemo (String PrevAttributeValueMemo)
	{
		set_Value (COLUMNNAME_PrevAttributeValueMemo, PrevAttributeValueMemo);
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public String getPrevAttributeValueMemo () 
	{
		return (String)get_Value(COLUMNNAME_PrevAttributeValueMemo);
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueString Prev Attribute Value	  */
	public void setPrevAttributeValueString (String PrevAttributeValueString)
	{
		set_Value (COLUMNNAME_PrevAttributeValueString, PrevAttributeValueString);
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public String getPrevAttributeValueString () 
	{
		return (String)get_Value(COLUMNNAME_PrevAttributeValueString);
	}

	/** Set Prev Attribute Value.
		@param PrevAttributeValueText Prev Attribute Value	  */
	public void setPrevAttributeValueText (String PrevAttributeValueText)
	{
		set_Value (COLUMNNAME_PrevAttributeValueText, PrevAttributeValueText);
	}

	/** Get Prev Attribute Value.
		@return Prev Attribute Value	  */
	public String getPrevAttributeValueText () 
	{
		return (String)get_Value(COLUMNNAME_PrevAttributeValueText);
	}

	public org.compiere.model.I_C_Currency getPrevCurrency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getPrevCurrency_ID(), get_TrxName());	}

	/** Set Prev Currency.
		@param PrevCurrency_ID Prev Currency	  */
	public void setPrevCurrency_ID (int PrevCurrency_ID)
	{
		if (PrevCurrency_ID < 1) 
			set_Value (COLUMNNAME_PrevCurrency_ID, null);
		else 
			set_Value (COLUMNNAME_PrevCurrency_ID, Integer.valueOf(PrevCurrency_ID));
	}

	/** Get Prev Currency.
		@return Prev Currency	  */
	public int getPrevCurrency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrevCurrency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LC Amendment Attributes.
		@param UNS_LC_AmdAttributes_ID LC Amendment Attributes	  */
	public void setUNS_LC_AmdAttributes_ID (int UNS_LC_AmdAttributes_ID)
	{
		if (UNS_LC_AmdAttributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmdAttributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_AmdAttributes_ID, Integer.valueOf(UNS_LC_AmdAttributes_ID));
	}

	/** Get LC Amendment Attributes.
		@return LC Amendment Attributes	  */
	public int getUNS_LC_AmdAttributes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_AmdAttributes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LC_AmdAttributes_UU.
		@param UNS_LC_AmdAttributes_UU UNS_LC_AmdAttributes_UU	  */
	public void setUNS_LC_AmdAttributes_UU (String UNS_LC_AmdAttributes_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_AmdAttributes_UU, UNS_LC_AmdAttributes_UU);
	}

	/** Get UNS_LC_AmdAttributes_UU.
		@return UNS_LC_AmdAttributes_UU	  */
	public String getUNS_LC_AmdAttributes_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_AmdAttributes_UU);
	}

	public com.uns.model.I_UNS_LC_Amendment getUNS_LC_Amendment() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Amendment)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Amendment.Table_Name)
			.getPO(getUNS_LC_Amendment_ID(), get_TrxName());	}

	/** Set LC Amendment.
		@param UNS_LC_Amendment_ID LC Amendment	  */
	public void setUNS_LC_Amendment_ID (int UNS_LC_Amendment_ID)
	{
		if (UNS_LC_Amendment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Amendment_ID, Integer.valueOf(UNS_LC_Amendment_ID));
	}

	/** Get LC Amendment.
		@return LC Amendment	  */
	public int getUNS_LC_Amendment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Amendment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_LC_AttributeConf getUNS_LC_AttributeConf() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_AttributeConf)MTable.get(getCtx(), com.uns.model.I_UNS_LC_AttributeConf.Table_Name)
			.getPO(getUNS_LC_AttributeConf_ID(), get_TrxName());	}

	/** Set LC Attribute.
		@param UNS_LC_AttributeConf_ID LC Attribute	  */
	public void setUNS_LC_AttributeConf_ID (int UNS_LC_AttributeConf_ID)
	{
		if (UNS_LC_AttributeConf_ID < 1) 
			set_Value (COLUMNNAME_UNS_LC_AttributeConf_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_LC_AttributeConf_ID, Integer.valueOf(UNS_LC_AttributeConf_ID));
	}

	/** Get LC Attribute.
		@return LC Attribute	  */
	public int getUNS_LC_AttributeConf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_AttributeConf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_LC_Attributes getUNS_LC_Attributes() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Attributes)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Attributes.Table_Name)
			.getPO(getUNS_LC_Attributes_ID(), get_TrxName());	}

	/** Set LC Attributes.
		@param UNS_LC_Attributes_ID LC Attributes	  */
	public void setUNS_LC_Attributes_ID (int UNS_LC_Attributes_ID)
	{
		if (UNS_LC_Attributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Attributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LC_Attributes_ID, Integer.valueOf(UNS_LC_Attributes_ID));
	}

	/** Get LC Attributes.
		@return LC Attributes	  */
	public int getUNS_LC_Attributes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Attributes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}