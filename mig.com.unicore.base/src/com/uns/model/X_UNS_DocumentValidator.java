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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_DocumentValidator
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DocumentValidator extends PO implements I_UNS_DocumentValidator, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160422L;

    /** Standard Constructor */
    public X_UNS_DocumentValidator (Properties ctx, int UNS_DocumentValidator_ID, String trxName)
    {
      super (ctx, UNS_DocumentValidator_ID, trxName);
      /** if (UNS_DocumentValidator_ID == 0)
        {
			setAD_Table_ID (0);
			setIsAlwaysError (false);
// N
			setIsMandatoryAttachment (true);
// Y
			setIsMandatoryDescription (true);
// Y
			setName (null);
			setOnCondition (null);
// BCT
			setUNS_DocumentValidator_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_DocumentValidator (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DocumentValidator[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Window)MTable.get(getCtx(), org.compiere.model.I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
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

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Always Error.
		@param IsAlwaysError Always Error	  */
	public void setIsAlwaysError (boolean IsAlwaysError)
	{
		set_Value (COLUMNNAME_IsAlwaysError, Boolean.valueOf(IsAlwaysError));
	}

	/** Get Always Error.
		@return Always Error	  */
	public boolean isAlwaysError () 
	{
		Object oo = get_Value(COLUMNNAME_IsAlwaysError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Attachment.
		@param IsMandatoryAttachment Mandatory Attachment	  */
	public void setIsMandatoryAttachment (boolean IsMandatoryAttachment)
	{
		set_Value (COLUMNNAME_IsMandatoryAttachment, Boolean.valueOf(IsMandatoryAttachment));
	}

	/** Get Mandatory Attachment.
		@return Mandatory Attachment	  */
	public boolean isMandatoryAttachment () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatoryAttachment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Description.
		@param IsMandatoryDescription Mandatory Description	  */
	public void setIsMandatoryDescription (boolean IsMandatoryDescription)
	{
		set_Value (COLUMNNAME_IsMandatoryDescription, Boolean.valueOf(IsMandatoryDescription));
	}

	/** Get Mandatory Description.
		@return Mandatory Description	  */
	public boolean isMandatoryDescription () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatoryDescription);
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

	/** TYPE_BEFORE_NEW = BFN */
	public static final String ONCONDITION_TYPE_BEFORE_NEW = "BFN";
	/** TYPE_AFTER_NEW = AFN */
	public static final String ONCONDITION_TYPE_AFTER_NEW = "AFN";
	/** TYPE_NEW = NEW */
	public static final String ONCONDITION_TYPE_NEW = "NEW";
	/** TYPE_AFTER_NEW_REPLICATION = ANR */
	public static final String ONCONDITION_TYPE_AFTER_NEW_REPLICATION = "ANR";
	/** TYPE_BEFORE_CHANGE = BFC */
	public static final String ONCONDITION_TYPE_BEFORE_CHANGE = "BFC";
	/** TYPE_CHANGE = CHN */
	public static final String ONCONDITION_TYPE_CHANGE = "CHN";
	/** TYPE_AFTER_CHANGE = 5 */
	public static final String ONCONDITION_TYPE_AFTER_CHANGE = "5";
	/** TYPE_AFTER_CHANGE_REPLICATION = 8 */
	public static final String ONCONDITION_TYPE_AFTER_CHANGE_REPLICATION = "8";
	/** TYPE_BEFORE_DELETE = BFD */
	public static final String ONCONDITION_TYPE_BEFORE_DELETE = "BFD";
	/** TYPE_DELETE = DLT */
	public static final String ONCONDITION_TYPE_DELETE = "DLT";
	/** TYPE_AFTER_DELETE = AFD */
	public static final String ONCONDITION_TYPE_AFTER_DELETE = "AFD";
	/** TYPE_BEFORE_DELETE_REPLICATION = BDR */
	public static final String ONCONDITION_TYPE_BEFORE_DELETE_REPLICATION = "BDR";
	/** TIMING_BEFORE_PREPARE = BPR */
	public static final String ONCONDITION_TIMING_BEFORE_PREPARE = "BPR";
	/** TIMING_BEFORE_VOID = BVD */
	public static final String ONCONDITION_TIMING_BEFORE_VOID = "BVD";
	/** TIMING_BEFORE_CLOSE = BCS */
	public static final String ONCONDITION_TIMING_BEFORE_CLOSE = "BCS";
	/** TIMING_BEFORE_REACTIVATE = BRC */
	public static final String ONCONDITION_TIMING_BEFORE_REACTIVATE = "BRC";
	/** TIMING_BEFORE_REVERSECORRECT =  BRC */
	public static final String ONCONDITION_TIMING_BEFORE_REVERSECORRECT = " BRC";
	/** TIMING_BEFORE_REVERSEACCRUAL = BRL */
	public static final String ONCONDITION_TIMING_BEFORE_REVERSEACCRUAL = "BRL";
	/** TIMING_BEFORE_COMPLETE = BCT */
	public static final String ONCONDITION_TIMING_BEFORE_COMPLETE = "BCT";
	/** TIMING_AFTER_PREPARE = APR */
	public static final String ONCONDITION_TIMING_AFTER_PREPARE = "APR";
	/** TIMING_AFTER_COMPLETE = ACT */
	public static final String ONCONDITION_TIMING_AFTER_COMPLETE = "ACT";
	/** TIMING_AFTER_VOID = ATV */
	public static final String ONCONDITION_TIMING_AFTER_VOID = "ATV";
	/** TIMING_AFTER_CLOSE = AFS */
	public static final String ONCONDITION_TIMING_AFTER_CLOSE = "AFS";
	/** TIMING_AFTER_REACTIVATE = ARV */
	public static final String ONCONDITION_TIMING_AFTER_REACTIVATE = "ARV";
	/** TIMING_AFTER_REVERSECORRECT = ART */
	public static final String ONCONDITION_TIMING_AFTER_REVERSECORRECT = "ART";
	/** TIMING_AFTER_REVERSEACCRUAL = ARS */
	public static final String ONCONDITION_TIMING_AFTER_REVERSEACCRUAL = "ARS";
	/** TIMING_BEFORE_POST = BPS */
	public static final String ONCONDITION_TIMING_BEFORE_POST = "BPS";
	/** TIMING_AFTER_POST = APS */
	public static final String ONCONDITION_TIMING_AFTER_POST = "APS";
	/** All_TypeORTiming = ALL */
	public static final String ONCONDITION_All_TypeORTiming = "ALL";
	/** Set On Condition.
		@param OnCondition On Condition	  */
	public void setOnCondition (String OnCondition)
	{

		set_Value (COLUMNNAME_OnCondition, OnCondition);
	}

	/** Get On Condition.
		@return On Condition	  */
	public String getOnCondition () 
	{
		return (String)get_Value(COLUMNNAME_OnCondition);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getOnCondition()));
    }

	/** Set Query.
		@param Query 
		SQL
	  */
	public void setQuery (String Query)
	{
		set_Value (COLUMNNAME_Query, Query);
	}

	/** Get Query.
		@return SQL
	  */
	public String getQuery () 
	{
		return (String)get_Value(COLUMNNAME_Query);
	}

	/** Set Document Validator.
		@param UNS_DocumentValidator_ID Document Validator	  */
	public void setUNS_DocumentValidator_ID (int UNS_DocumentValidator_ID)
	{
		if (UNS_DocumentValidator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DocumentValidator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DocumentValidator_ID, Integer.valueOf(UNS_DocumentValidator_ID));
	}

	/** Get Document Validator.
		@return Document Validator	  */
	public int getUNS_DocumentValidator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DocumentValidator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Validator UU.
		@param UNS_DocumentValidator_UU Document Validator UU	  */
	public void setUNS_DocumentValidator_UU (String UNS_DocumentValidator_UU)
	{
		set_Value (COLUMNNAME_UNS_DocumentValidator_UU, UNS_DocumentValidator_UU);
	}

	/** Get Document Validator UU.
		@return Document Validator UU	  */
	public String getUNS_DocumentValidator_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DocumentValidator_UU);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}