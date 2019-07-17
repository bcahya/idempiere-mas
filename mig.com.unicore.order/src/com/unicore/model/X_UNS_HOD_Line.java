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

/** Generated Model for UNS_HOD_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_HOD_Line extends PO implements I_UNS_HOD_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150616L;

    /** Standard Constructor */
    public X_UNS_HOD_Line (Properties ctx, int UNS_HOD_Line_ID, String trxName)
    {
      super (ctx, UNS_HOD_Line_ID, trxName);
      /** if (UNS_HOD_Line_ID == 0)
        {
			setUNS_HOD_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_HOD_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_HOD_Line[")
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

	/** Set Cancelled.
		@param IsCancelled 
		The transaction was cancelled
	  */
	public void setIsCancelled (boolean IsCancelled)
	{
		set_ValueNoCheck (COLUMNNAME_IsCancelled, Boolean.valueOf(IsCancelled));
	}

	/** Get Cancelled.
		@return The transaction was cancelled
	  */
	public boolean isCancelled () 
	{
		Object oo = get_Value(COLUMNNAME_IsCancelled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Delivered.
		@param IsDelivered Delivered	  */
	public void setIsDelivered (boolean IsDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_IsDelivered, Boolean.valueOf(IsDelivered));
	}

	/** Get Delivered.
		@return Delivered	  */
	public boolean isDelivered () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Receipt.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Receipt.
		@return This is a sales transaction (receipt)
	  */
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Handover_Document getUNS_Handover_Document() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Handover_Document)MTable.get(getCtx(), com.unicore.model.I_UNS_Handover_Document.Table_Name)
			.getPO(getUNS_Handover_Document_ID(), get_TrxName());	}

	/** Set Handover Document.
		@param UNS_Handover_Document_ID Handover Document	  */
	public void setUNS_Handover_Document_ID (int UNS_Handover_Document_ID)
	{
		if (UNS_Handover_Document_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Handover_Document_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Handover_Document_ID, Integer.valueOf(UNS_Handover_Document_ID));
	}

	/** Get Handover Document.
		@return Handover Document	  */
	public int getUNS_Handover_Document_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Handover_Document_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handover Document Line.
		@param UNS_HOD_Line_ID Handover Document Line	  */
	public void setUNS_HOD_Line_ID (int UNS_HOD_Line_ID)
	{
		if (UNS_HOD_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HOD_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HOD_Line_ID, Integer.valueOf(UNS_HOD_Line_ID));
	}

	/** Get Handover Document Line.
		@return Handover Document Line	  */
	public int getUNS_HOD_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HOD_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_HOD_Line_UU.
		@param UNS_HOD_Line_UU UNS_HOD_Line_UU	  */
	public void setUNS_HOD_Line_UU (String UNS_HOD_Line_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_HOD_Line_UU, UNS_HOD_Line_UU);
	}

	/** Get UNS_HOD_Line_UU.
		@return UNS_HOD_Line_UU	  */
	public String getUNS_HOD_Line_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_HOD_Line_UU);
	}

	public com.unicore.model.I_UNS_PackingList_Order getUNS_PackingList_Order() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PackingList_Order)MTable.get(getCtx(), com.unicore.model.I_UNS_PackingList_Order.Table_Name)
			.getPO(getUNS_PackingList_Order_ID(), get_TrxName());	}

	/** Set Packing List Order.
		@param UNS_PackingList_Order_ID Packing List Order	  */
	public void setUNS_PackingList_Order_ID (int UNS_PackingList_Order_ID)
	{
		if (UNS_PackingList_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_Order_ID, Integer.valueOf(UNS_PackingList_Order_ID));
	}

	/** Get Packing List Order.
		@return Packing List Order	  */
	public int getUNS_PackingList_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}