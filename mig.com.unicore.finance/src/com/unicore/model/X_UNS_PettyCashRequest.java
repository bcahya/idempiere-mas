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

/** Generated Model for UNS_PettyCashRequest
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_PettyCashRequest extends PO implements I_UNS_PettyCashRequest, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140923L;

    /** Standard Constructor */
    public X_UNS_PettyCashRequest (Properties ctx, int UNS_PettyCashRequest_ID, String trxName)
    {
      super (ctx, UNS_PettyCashRequest_ID, trxName);
      /** if (UNS_PettyCashRequest_ID == 0)
        {
			setAmountRequested (Env.ZERO);
			setC_Charge_ID (0);
			setProcessed (false);
			setUNS_PettyCashRequest_ID (0);
			setUNS_TransferBalance_Request_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PettyCashRequest (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PettyCashRequest[")
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

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
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

	/** Set UNS_PettyCashRequest_UU.
		@param UNS_PettyCashRequest_UU UNS_PettyCashRequest_UU	  */
	public void setUNS_PettyCashRequest_UU (String UNS_PettyCashRequest_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_PettyCashRequest_UU, UNS_PettyCashRequest_UU);
	}

	/** Get UNS_PettyCashRequest_UU.
		@return UNS_PettyCashRequest_UU	  */
	public String getUNS_PettyCashRequest_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PettyCashRequest_UU);
	}

	public com.unicore.model.I_UNS_TransferBalance_Request getUNS_TransferBalance_Request() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_TransferBalance_Request)MTable.get(getCtx(), com.unicore.model.I_UNS_TransferBalance_Request.Table_Name)
			.getPO(getUNS_TransferBalance_Request_ID(), get_TrxName());	}

	/** Set Transfer Balance Request.
		@param UNS_TransferBalance_Request_ID Transfer Balance Request	  */
	public void setUNS_TransferBalance_Request_ID (int UNS_TransferBalance_Request_ID)
	{
		if (UNS_TransferBalance_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TransferBalance_Request_ID, Integer.valueOf(UNS_TransferBalance_Request_ID));
	}

	/** Get Transfer Balance Request.
		@return Transfer Balance Request	  */
	public int getUNS_TransferBalance_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TransferBalance_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}