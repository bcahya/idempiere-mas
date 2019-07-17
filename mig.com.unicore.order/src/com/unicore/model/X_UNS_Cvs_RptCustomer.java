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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Cvs_RptCustomer
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Cvs_RptCustomer extends PO implements I_UNS_Cvs_RptCustomer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150917L;

    /** Standard Constructor */
    public X_UNS_Cvs_RptCustomer (Properties ctx, int UNS_Cvs_RptCustomer_ID, String trxName)
    {
      super (ctx, UNS_Cvs_RptCustomer_ID, trxName);
      /** if (UNS_Cvs_RptCustomer_ID == 0)
        {
			setProcessed (false);
// N
			setTotalLines (Env.ZERO);
			setUNS_CvsCustomer_ID (0);
			setUNS_CvsCustomer_Location_ID (0);
			setUNS_Cvs_RptCustomer_ID (0);
			setUNS_Cvs_Rpt_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Cvs_RptCustomer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Cvs_RptCustomer[")
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_CvsCustomer getUNS_CvsCustomer() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CvsCustomer)MTable.get(getCtx(), com.unicore.model.I_UNS_CvsCustomer.Table_Name)
			.getPO(getUNS_CvsCustomer_ID(), get_TrxName());	}

	/** Set Canvas Customer.
		@param UNS_CvsCustomer_ID Canvas Customer	  */
	public void setUNS_CvsCustomer_ID (int UNS_CvsCustomer_ID)
	{
		if (UNS_CvsCustomer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CvsCustomer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CvsCustomer_ID, Integer.valueOf(UNS_CvsCustomer_ID));
	}

	/** Get Canvas Customer.
		@return Canvas Customer	  */
	public int getUNS_CvsCustomer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CvsCustomer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_CvsCustomer_Location getUNS_CvsCustomer_Location() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_CvsCustomer_Location)MTable.get(getCtx(), com.unicore.model.I_UNS_CvsCustomer_Location.Table_Name)
			.getPO(getUNS_CvsCustomer_Location_ID(), get_TrxName());	}

	/** Set Customer Location.
		@param UNS_CvsCustomer_Location_ID Customer Location	  */
	public void setUNS_CvsCustomer_Location_ID (int UNS_CvsCustomer_Location_ID)
	{
		if (UNS_CvsCustomer_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CvsCustomer_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CvsCustomer_Location_ID, Integer.valueOf(UNS_CvsCustomer_Location_ID));
	}

	/** Get Customer Location.
		@return Customer Location	  */
	public int getUNS_CvsCustomer_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CvsCustomer_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer.
		@param UNS_Cvs_RptCustomer_ID Customer	  */
	public void setUNS_Cvs_RptCustomer_ID (int UNS_Cvs_RptCustomer_ID)
	{
		if (UNS_Cvs_RptCustomer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptCustomer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptCustomer_ID, Integer.valueOf(UNS_Cvs_RptCustomer_ID));
	}

	/** Get Customer.
		@return Customer	  */
	public int getUNS_Cvs_RptCustomer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_RptCustomer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer UU.
		@param UNS_Cvs_RptCustomer_UU Customer UU	  */
	public void setUNS_Cvs_RptCustomer_UU (String UNS_Cvs_RptCustomer_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Cvs_RptCustomer_UU, UNS_Cvs_RptCustomer_UU);
	}

	/** Get Customer UU.
		@return Customer UU	  */
	public String getUNS_Cvs_RptCustomer_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Cvs_RptCustomer_UU);
	}

	public com.unicore.model.I_UNS_Cvs_Rpt getUNS_Cvs_Rpt() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Cvs_Rpt)MTable.get(getCtx(), com.unicore.model.I_UNS_Cvs_Rpt.Table_Name)
			.getPO(getUNS_Cvs_Rpt_ID(), get_TrxName());	}

	/** Set Canvas Report.
		@param UNS_Cvs_Rpt_ID Canvas Report	  */
	public void setUNS_Cvs_Rpt_ID (int UNS_Cvs_Rpt_ID)
	{
		if (UNS_Cvs_Rpt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Rpt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Cvs_Rpt_ID, Integer.valueOf(UNS_Cvs_Rpt_ID));
	}

	/** Get Canvas Report.
		@return Canvas Report	  */
	public int getUNS_Cvs_Rpt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Cvs_Rpt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}