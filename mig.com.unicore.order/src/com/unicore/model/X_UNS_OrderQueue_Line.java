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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_OrderQueue_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_OrderQueue_Line extends PO implements I_UNS_OrderQueue_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150330L;

    /** Standard Constructor */
    public X_UNS_OrderQueue_Line (Properties ctx, int UNS_OrderQueue_Line_ID, String trxName)
    {
      super (ctx, UNS_OrderQueue_Line_ID, trxName);
      /** if (UNS_OrderQueue_Line_ID == 0)
        {
			setUNS_OrderQueue_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_OrderQueue_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_OrderQueue_Line[")
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

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_ValueNoCheck (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set isReserved.
		@param isReserved isReserved	  */
	public void setisReserved (boolean isReserved)
	{
		set_Value (COLUMNNAME_isReserved, Boolean.valueOf(isReserved));
	}

	/** Get isReserved.
		@return isReserved	  */
	public boolean isReserved () 
	{
		Object oo = get_Value(COLUMNNAME_isReserved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param Sequence Sequence	  */
	public void setSequence (BigDecimal Sequence)
	{
		set_Value (COLUMNNAME_Sequence, Sequence);
	}

	/** Get Sequence.
		@return Sequence	  */
	public BigDecimal getSequence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Sequence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Queueing = Q */
	public static final String STATUSQUEUE_Queueing = "Q";
	/** Reserve Queueing = RQ */
	public static final String STATUSQUEUE_ReserveQueueing = "RQ";
	/** Waiting Break in Approval = WBA */
	public static final String STATUSQUEUE_WaitingBreakInApproval = "WBA";
	/** Break in Approv = BA */
	public static final String STATUSQUEUE_BreakInApprov = "BA";
	/** Prepare = PR */
	public static final String STATUSQUEUE_Prepare = "PR";
	/** Set Status Of Queue.
		@param StatusQueue Status Of Queue	  */
	public void setStatusQueue (String StatusQueue)
	{

		set_Value (COLUMNNAME_StatusQueue, StatusQueue);
	}

	/** Get Status Of Queue.
		@return Status Of Queue	  */
	public String getStatusQueue () 
	{
		return (String)get_Value(COLUMNNAME_StatusQueue);
	}

	public com.unicore.model.I_UNS_OrderQueue getUNS_OrderQueue() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_OrderQueue)MTable.get(getCtx(), com.unicore.model.I_UNS_OrderQueue.Table_Name)
			.getPO(getUNS_OrderQueue_ID(), get_TrxName());	}

	/** Set Order Queue.
		@param UNS_OrderQueue_ID Order Queue	  */
	public void setUNS_OrderQueue_ID (int UNS_OrderQueue_ID)
	{
		if (UNS_OrderQueue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_ID, Integer.valueOf(UNS_OrderQueue_ID));
	}

	/** Get Order Queue.
		@return Order Queue	  */
	public int getUNS_OrderQueue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrderQueue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OrderQueue_Line.
		@param UNS_OrderQueue_Line_ID UNS_OrderQueue_Line	  */
	public void setUNS_OrderQueue_Line_ID (int UNS_OrderQueue_Line_ID)
	{
		if (UNS_OrderQueue_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_Line_ID, Integer.valueOf(UNS_OrderQueue_Line_ID));
	}

	/** Get UNS_OrderQueue_Line.
		@return UNS_OrderQueue_Line	  */
	public int getUNS_OrderQueue_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OrderQueue_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_OrderQueue_Line_UU.
		@param UNS_OrderQueue_Line_UU UNS_OrderQueue_Line_UU	  */
	public void setUNS_OrderQueue_Line_UU (String UNS_OrderQueue_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_OrderQueue_Line_UU, UNS_OrderQueue_Line_UU);
	}

	/** Get UNS_OrderQueue_Line_UU.
		@return UNS_OrderQueue_Line_UU	  */
	public String getUNS_OrderQueue_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_OrderQueue_Line_UU);
	}

	public com.unicore.model.I_UNS_PackingList getUNS_PackingList() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList.Table_Name)
			.getPO(getUNS_PackingList_ID(), get_TrxName());	}

	/** Set Packing List.
		@param UNS_PackingList_ID Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID)
	{
		if (UNS_PackingList_ID < 1) 
			set_Value (COLUMNNAME_UNS_PackingList_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PackingList_ID, Integer.valueOf(UNS_PackingList_ID));
	}

	/** Get Packing List.
		@return Packing List	  */
	public int getUNS_PackingList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}