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

/** Generated Model for UNS_Queue_WS
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Queue_WS extends PO implements I_UNS_Queue_WS, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170718L;

    /** Standard Constructor */
    public X_UNS_Queue_WS (Properties ctx, int UNS_Queue_WS_ID, String trxName)
    {
      super (ctx, UNS_Queue_WS_ID, trxName);
      /** if (UNS_Queue_WS_ID == 0)
        {
			setStatus (null);
// Q
        } */
    }

    /** Load Constructor */
    public X_UNS_Queue_WS (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Queue_WS[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_ValueNoCheck (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set DeliveredDate.
		@param DeliveredDate 
		Date Of Delivered
	  */
	public void setDeliveredDate (Timestamp DeliveredDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveredDate, DeliveredDate);
	}

	/** Get DeliveredDate.
		@return Date Of Delivered
	  */
	public Timestamp getDeliveredDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DeliveredDate);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	public org.compiere.model.I_AD_Table getReferenced_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getReferenced_Table_ID(), get_TrxName());	}

	/** Set Referenced Table.
		@param Referenced_Table_ID Referenced Table	  */
	public void setReferenced_Table_ID (int Referenced_Table_ID)
	{
		if (Referenced_Table_ID < 1) 
			set_Value (COLUMNNAME_Referenced_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_Table_ID, Integer.valueOf(Referenced_Table_ID));
	}

	/** Get Referenced Table.
		@return Referenced Table	  */
	public int getReferenced_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Referenced_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Queue = Q */
	public static final String STATUS_Queue = "Q";
	/** Success = S */
	public static final String STATUS_Success = "S";
	/** Failed = F */
	public static final String STATUS_Failed = "F";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Queue Web Service.
		@param UNS_Queue_WS_ID Queue Web Service	  */
	public void setUNS_Queue_WS_ID (int UNS_Queue_WS_ID)
	{
		if (UNS_Queue_WS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Queue_WS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Queue_WS_ID, Integer.valueOf(UNS_Queue_WS_ID));
	}

	/** Get Queue Web Service.
		@return Queue Web Service	  */
	public int getUNS_Queue_WS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Queue_WS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Queue_WS_UU.
		@param UNS_Queue_WS_UU UNS_Queue_WS_UU	  */
	public void setUNS_Queue_WS_UU (String UNS_Queue_WS_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Queue_WS_UU, UNS_Queue_WS_UU);
	}

	/** Get UNS_Queue_WS_UU.
		@return UNS_Queue_WS_UU	  */
	public String getUNS_Queue_WS_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Queue_WS_UU);
	}
}