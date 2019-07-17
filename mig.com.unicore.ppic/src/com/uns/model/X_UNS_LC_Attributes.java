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

/** Generated Model for UNS_LC_Attributes
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LC_Attributes extends PO implements I_UNS_LC_Attributes, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130529L;

    /** Standard Constructor */
    public X_UNS_LC_Attributes (Properties ctx, int UNS_LC_Attributes_ID, String trxName)
    {
      super (ctx, UNS_LC_Attributes_ID, trxName);
      /** if (UNS_LC_Attributes_ID == 0)
        {
			setAttributeNumber (null);
			setDataType (null);
			setHasItems (false);
// N
			setName (null);
			setUNS_LC_AttributeConf_ID (0);
			setUNS_LC_Attributes_ID (0);
			setUNS_LC_Balance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LC_Attributes (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LC_Attributes[")
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

	/** Set Attribute Value.
		@param AttributeValueBigDecimal Attribute Value	  */
	public void setAttributeValueBigDecimal (BigDecimal AttributeValueBigDecimal)
	{
		set_Value (COLUMNNAME_AttributeValueBigDecimal, AttributeValueBigDecimal);
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public BigDecimal getAttributeValueBigDecimal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AttributeValueBigDecimal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Attribute Value.
		@param AttributeValueDate Attribute Value	  */
	public void setAttributeValueDate (Timestamp AttributeValueDate)
	{
		set_Value (COLUMNNAME_AttributeValueDate, AttributeValueDate);
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public Timestamp getAttributeValueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AttributeValueDate);
	}

	/** Set Attribute Value.
		@param AttributeValueInteger Attribute Value	  */
	public void setAttributeValueInteger (int AttributeValueInteger)
	{
		set_Value (COLUMNNAME_AttributeValueInteger, Integer.valueOf(AttributeValueInteger));
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public int getAttributeValueInteger () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AttributeValueInteger);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute Value.
		@param AttributeValueMemo Attribute Value	  */
	public void setAttributeValueMemo (String AttributeValueMemo)
	{
		set_Value (COLUMNNAME_AttributeValueMemo, AttributeValueMemo);
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public String getAttributeValueMemo () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValueMemo);
	}

	/** Set Attribute Value.
		@param AttributeValueString Attribute Value	  */
	public void setAttributeValueString (String AttributeValueString)
	{
		set_Value (COLUMNNAME_AttributeValueString, AttributeValueString);
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public String getAttributeValueString () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValueString);
	}

	/** Set Attribute Value.
		@param AttributeValueText Attribute Value	  */
	public void setAttributeValueText (String AttributeValueText)
	{
		set_Value (COLUMNNAME_AttributeValueText, AttributeValueText);
	}

	/** Get Attribute Value.
		@return Attribute Value	  */
	public String getAttributeValueText () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValueText);
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UNS_LC_Attributes_UU.
		@param UNS_LC_Attributes_UU UNS_LC_Attributes_UU	  */
	public void setUNS_LC_Attributes_UU (String UNS_LC_Attributes_UU)
	{
		set_Value (COLUMNNAME_UNS_LC_Attributes_UU, UNS_LC_Attributes_UU);
	}

	/** Get UNS_LC_Attributes_UU.
		@return UNS_LC_Attributes_UU	  */
	public String getUNS_LC_Attributes_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LC_Attributes_UU);
	}

	public com.uns.model.I_UNS_LC_Balance getUNS_LC_Balance() throws RuntimeException
    {
		return (com.uns.model.I_UNS_LC_Balance)MTable.get(getCtx(), com.uns.model.I_UNS_LC_Balance.Table_Name)
			.getPO(getUNS_LC_Balance_ID(), get_TrxName());	}

	/** Set LC Balance.
		@param UNS_LC_Balance_ID LC Balance	  */
	public void setUNS_LC_Balance_ID (int UNS_LC_Balance_ID)
	{
		if (UNS_LC_Balance_ID < 1) 
			set_Value (COLUMNNAME_UNS_LC_Balance_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_LC_Balance_ID, Integer.valueOf(UNS_LC_Balance_ID));
	}

	/** Get LC Balance.
		@return LC Balance	  */
	public int getUNS_LC_Balance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LC_Balance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}