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

/** Generated Model for UNS_HandoverDoc_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_HandoverDoc_Line extends PO implements I_UNS_HandoverDoc_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151226L;

    /** Standard Constructor */
    public X_UNS_HandoverDoc_Line (Properties ctx, int UNS_HandoverDoc_Line_ID, String trxName)
    {
      super (ctx, UNS_HandoverDoc_Line_ID, trxName);
      /** if (UNS_HandoverDoc_Line_ID == 0)
        {
			setC_Invoice_ID (0);
			setIsApproved (false);
// N
			setIsConfirmed (false);
// N
			setProcessed (false);
// N
			setUNS_HandoverDoc_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_HandoverDoc_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_HandoverDoc_Line[")
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_ValueNoCheck (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public com.unicore.model.I_UNS_HandoverDoc_Confirm getUNS_HandoverDoc_Confirm() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_HandoverDoc_Confirm)MTable.get(getCtx(), com.unicore.model.I_UNS_HandoverDoc_Confirm.Table_Name)
			.getPO(getUNS_HandoverDoc_Confirm_ID(), get_TrxName());	}

	/** Set Handover Document Confirm.
		@param UNS_HandoverDoc_Confirm_ID Handover Document Confirm	  */
	public void setUNS_HandoverDoc_Confirm_ID (int UNS_HandoverDoc_Confirm_ID)
	{
		if (UNS_HandoverDoc_Confirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_Confirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_Confirm_ID, Integer.valueOf(UNS_HandoverDoc_Confirm_ID));
	}

	/** Get Handover Document Confirm.
		@return Handover Document Confirm	  */
	public int getUNS_HandoverDoc_Confirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HandoverDoc_Confirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_HandoverDoc getUNS_HandoverDoc() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_HandoverDoc)MTable.get(getCtx(), com.unicore.model.I_UNS_HandoverDoc.Table_Name)
			.getPO(getUNS_HandoverDoc_ID(), get_TrxName());	}

	/** Set Handover Document.
		@param UNS_HandoverDoc_ID Handover Document	  */
	public void setUNS_HandoverDoc_ID (int UNS_HandoverDoc_ID)
	{
		if (UNS_HandoverDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_ID, Integer.valueOf(UNS_HandoverDoc_ID));
	}

	/** Get Handover Document.
		@return Handover Document	  */
	public int getUNS_HandoverDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HandoverDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handover Document Line.
		@param UNS_HandoverDoc_Line_ID Handover Document Line	  */
	public void setUNS_HandoverDoc_Line_ID (int UNS_HandoverDoc_Line_ID)
	{
		if (UNS_HandoverDoc_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HandoverDoc_Line_ID, Integer.valueOf(UNS_HandoverDoc_Line_ID));
	}

	/** Get Handover Document Line.
		@return Handover Document Line	  */
	public int getUNS_HandoverDoc_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HandoverDoc_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_HandoverDoc_Line_UU.
		@param UNS_HandoverDoc_Line_UU UNS_HandoverDoc_Line_UU	  */
	public void setUNS_HandoverDoc_Line_UU (String UNS_HandoverDoc_Line_UU)
	{
		set_Value (COLUMNNAME_UNS_HandoverDoc_Line_UU, UNS_HandoverDoc_Line_UU);
	}

	/** Get UNS_HandoverDoc_Line_UU.
		@return UNS_HandoverDoc_Line_UU	  */
	public String getUNS_HandoverDoc_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_HandoverDoc_Line_UU);
	}
}