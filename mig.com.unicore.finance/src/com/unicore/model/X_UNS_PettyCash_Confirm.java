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

/** Generated Model for UNS_PettyCash_Confirm
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_PettyCash_Confirm extends PO implements I_UNS_PettyCash_Confirm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140923L;

    /** Standard Constructor */
    public X_UNS_PettyCash_Confirm (Properties ctx, int UNS_PettyCash_Confirm_ID, String trxName)
    {
      super (ctx, UNS_PettyCash_Confirm_ID, trxName);
      /** if (UNS_PettyCash_Confirm_ID == 0)
        {
			setAmountConfirmed (Env.ZERO);
// 0
			setAmountRequested (Env.ZERO);
			setProcessed (false);
// N
			setUNS_PettyCash_Confirm_ID (0);
			setUNS_PettyCashRequest_ID (0);
			setUNS_TransferBalance_Confirm_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PettyCash_Confirm (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PettyCash_Confirm[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Confirmed Amount.
		@param AmountConfirmed Confirmed Amount	  */
	public void setAmountConfirmed (BigDecimal AmountConfirmed)
	{
		set_Value (COLUMNNAME_AmountConfirmed, AmountConfirmed);
	}

	/** Get Confirmed Amount.
		@return Confirmed Amount	  */
	public BigDecimal getAmountConfirmed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountConfirmed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Requested Amount.
		@param AmountRequested Requested Amount	  */
	public void setAmountRequested (BigDecimal AmountRequested)
	{
		set_Value (COLUMNNAME_AmountRequested, AmountRequested);
	}

	/** Get Requested Amount.
		@return Requested Amount	  */
	public BigDecimal getAmountRequested () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountRequested);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Petty Cash Request Confirmation.
		@param UNS_PettyCash_Confirm_ID Petty Cash Request Confirmation	  */
	public void setUNS_PettyCash_Confirm_ID (int UNS_PettyCash_Confirm_ID)
	{
		if (UNS_PettyCash_Confirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PettyCash_Confirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PettyCash_Confirm_ID, Integer.valueOf(UNS_PettyCash_Confirm_ID));
	}

	/** Get Petty Cash Request Confirmation.
		@return Petty Cash Request Confirmation	  */
	public int getUNS_PettyCash_Confirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PettyCash_Confirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PettyCash_Confirm_UU.
		@param UNS_PettyCash_Confirm_UU UNS_PettyCash_Confirm_UU	  */
	public void setUNS_PettyCash_Confirm_UU (String UNS_PettyCash_Confirm_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PettyCash_Confirm_UU, UNS_PettyCash_Confirm_UU);
	}

	/** Get UNS_PettyCash_Confirm_UU.
		@return UNS_PettyCash_Confirm_UU	  */
	public String getUNS_PettyCash_Confirm_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PettyCash_Confirm_UU);
	}

	public com.unicore.model.I_UNS_PettyCashRequest getUNS_PettyCashRequest() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PettyCashRequest)MTable.get(getCtx(), com.unicore.model.I_UNS_PettyCashRequest.Table_Name)
			.getPO(getUNS_PettyCashRequest_ID(), get_TrxName());	}

	/** Set Petty Cash Request.
		@param UNS_PettyCashRequest_ID Petty Cash Request	  */
	public void setUNS_PettyCashRequest_ID (int UNS_PettyCashRequest_ID)
	{
		if (UNS_PettyCashRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PettyCashRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PettyCashRequest_ID, Integer.valueOf(UNS_PettyCashRequest_ID));
	}

	/** Get Petty Cash Request.
		@return Petty Cash Request	  */
	public int getUNS_PettyCashRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PettyCashRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_TransferBalance_Confirm getUNS_TransferBalance_Confirm() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_TransferBalance_Confirm)MTable.get(getCtx(), com.unicore.model.I_UNS_TransferBalance_Confirm.Table_Name)
			.getPO(getUNS_TransferBalance_Confirm_ID(), get_TrxName());	}

	/** Set Transfer Balance Confirmation.
		@param UNS_TransferBalance_Confirm_ID Transfer Balance Confirmation	  */
	public void setUNS_TransferBalance_Confirm_ID (int UNS_TransferBalance_Confirm_ID)
	{
		if (UNS_TransferBalance_Confirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Confirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Confirm_ID, Integer.valueOf(UNS_TransferBalance_Confirm_ID));
	}

	/** Get Transfer Balance Confirmation.
		@return Transfer Balance Confirmation	  */
	public int getUNS_TransferBalance_Confirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TransferBalance_Confirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}