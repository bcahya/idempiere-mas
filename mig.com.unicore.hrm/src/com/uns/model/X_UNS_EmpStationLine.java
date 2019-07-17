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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_EmpStationLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_EmpStationLine extends PO implements I_UNS_EmpStationLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171017L;

    /** Standard Constructor */
    public X_UNS_EmpStationLine (Properties ctx, int UNS_EmpStationLine_ID, String trxName)
    {
      super (ctx, UNS_EmpStationLine_ID, trxName);
      /** if (UNS_EmpStationLine_ID == 0)
        {
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_Product_BOM WHERE M_Product_ID=@M_Product_ID@
			setUNS_EmpStation_ID (0);
			setUNS_EmpStationLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_EmpStationLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_EmpStationLine[")
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

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
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

	public com.uns.model.I_UNS_EmpStation getUNS_EmpStation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_EmpStation)MTable.get(getCtx(), com.uns.model.I_UNS_EmpStation.Table_Name)
			.getPO(getUNS_EmpStation_ID(), get_TrxName());	}

	/** Set Station.
		@param UNS_EmpStation_ID Station	  */
	public void setUNS_EmpStation_ID (int UNS_EmpStation_ID)
	{
		if (UNS_EmpStation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStation_ID, Integer.valueOf(UNS_EmpStation_ID));
	}

	/** Get Station.
		@return Station	  */
	public int getUNS_EmpStation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmpStation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Station Line.
		@param UNS_EmpStationLine_ID Station Line	  */
	public void setUNS_EmpStationLine_ID (int UNS_EmpStationLine_ID)
	{
		if (UNS_EmpStationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmpStationLine_ID, Integer.valueOf(UNS_EmpStationLine_ID));
	}

	/** Get Station Line.
		@return Station Line	  */
	public int getUNS_EmpStationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmpStationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_EmpStationLine_UU.
		@param UNS_EmpStationLine_UU UNS_EmpStationLine_UU	  */
	public void setUNS_EmpStationLine_UU (String UNS_EmpStationLine_UU)
	{
		set_Value (COLUMNNAME_UNS_EmpStationLine_UU, UNS_EmpStationLine_UU);
	}

	/** Get UNS_EmpStationLine_UU.
		@return UNS_EmpStationLine_UU	  */
	public String getUNS_EmpStationLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_EmpStationLine_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}