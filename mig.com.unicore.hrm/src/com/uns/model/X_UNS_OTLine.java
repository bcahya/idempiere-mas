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

/** Generated Model for UNS_OTLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_OTLine extends PO implements I_UNS_OTLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180321L;

    /** Standard Constructor */
    public X_UNS_OTLine (Properties ctx, int UNS_OTLine_ID, String trxName)
    {
      super (ctx, UNS_OTLine_ID, trxName);
      /** if (UNS_OTLine_ID == 0)
        {
			setIsSummary (true);
// Y
			setLine (0);
			setUNS_OTLine_ID (0);
			setUNS_OTRequest_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OTLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OTLine[")
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

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (I_UNS_Employee)MTable.get(getCtx(), I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_OTConfirmation getUNS_OTConfirmation() throws RuntimeException
    {
		return (I_UNS_OTConfirmation)MTable.get(getCtx(), I_UNS_OTConfirmation.Table_Name)
			.getPO(getUNS_OTConfirmation_ID(), get_TrxName());	}

	/** Set UNS_OTConfirmation_ID.
		@param UNS_OTConfirmation_ID UNS_OTConfirmation_ID	  */
	public void setUNS_OTConfirmation_ID (int UNS_OTConfirmation_ID)
	{
		if (UNS_OTConfirmation_ID < 1) 
			set_Value (COLUMNNAME_UNS_OTConfirmation_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_OTConfirmation_ID, Integer.valueOf(UNS_OTConfirmation_ID));
	}

	/** Get UNS_OTConfirmation_ID.
		@return UNS_OTConfirmation_ID	  */
	public int getUNS_OTConfirmation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OTConfirmation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Over Time Line.
		@param UNS_OTLine_ID Over Time Line	  */
	public void setUNS_OTLine_ID (int UNS_OTLine_ID)
	{
		if (UNS_OTLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OTLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OTLine_ID, Integer.valueOf(UNS_OTLine_ID));
	}

	/** Get Over Time Line.
		@return Over Time Line	  */
	public int getUNS_OTLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OTLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OTLine_UU.
		@param UNS_OTLine_UU UNS_OTLine_UU	  */
	public void setUNS_OTLine_UU (String UNS_OTLine_UU)
	{
		set_Value (COLUMNNAME_UNS_OTLine_UU, UNS_OTLine_UU);
	}

	/** Get UNS_OTLine_UU.
		@return UNS_OTLine_UU	  */
	public String getUNS_OTLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_OTLine_UU);
	}

	public I_UNS_OTRequest getUNS_OTRequest() throws RuntimeException
    {
		return (I_UNS_OTRequest)MTable.get(getCtx(), I_UNS_OTRequest.Table_Name)
			.getPO(getUNS_OTRequest_ID(), get_TrxName());	}

	/** Set UNS_OTRequest_ID.
		@param UNS_OTRequest_ID UNS_OTRequest_ID	  */
	public void setUNS_OTRequest_ID (int UNS_OTRequest_ID)
	{
		if (UNS_OTRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OTRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OTRequest_ID, Integer.valueOf(UNS_OTRequest_ID));
	}

	/** Get UNS_OTRequest_ID.
		@return UNS_OTRequest_ID	  */
	public int getUNS_OTRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OTRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}