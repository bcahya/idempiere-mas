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

/** Generated Model for UNS_PL_ReturnOrder
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ReturnOrder extends PO implements I_UNS_PL_ReturnOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180130L;

    /** Standard Constructor */
    public X_UNS_PL_ReturnOrder (Properties ctx, int UNS_PL_ReturnOrder_ID, String trxName)
    {
      super (ctx, UNS_PL_ReturnOrder_ID, trxName);
      /** if (UNS_PL_ReturnOrder_ID == 0)
        {
			setIsCancelled (false);
			setisCancelOrder (false);
// N
			setIsPartialCancelation (false);
// N
			setProcessed (false);
			setUNS_PL_Return_ID (0);
			setUNS_PL_ReturnOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ReturnOrder (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ReturnOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (String C_BPartner_ID)
	{
		throw new IllegalArgumentException ("C_BPartner_ID is virtual column");	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public String getC_BPartner_ID () 
	{
		return (String)get_Value(COLUMNNAME_C_BPartner_ID);
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
		set_Value (COLUMNNAME_IsCancelled, Boolean.valueOf(IsCancelled));
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

	/** Set Cancel Order.
		@param isCancelOrder Cancel Order	  */
	public void setisCancelOrder (boolean isCancelOrder)
	{
		set_Value (COLUMNNAME_isCancelOrder, Boolean.valueOf(isCancelOrder));
	}

	/** Get Cancel Order.
		@return Cancel Order	  */
	public boolean isCancelOrder () 
	{
		Object oo = get_Value(COLUMNNAME_isCancelOrder);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Partial Cancelation.
		@param IsPartialCancelation Partial Cancelation	  */
	public void setIsPartialCancelation (boolean IsPartialCancelation)
	{
		set_Value (COLUMNNAME_IsPartialCancelation, Boolean.valueOf(IsPartialCancelation));
	}

	/** Get Partial Cancelation.
		@return Partial Cancelation	  */
	public boolean isPartialCancelation () 
	{
		Object oo = get_Value(COLUMNNAME_IsPartialCancelation);
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

	/** Other reason = NA */
	public static final String REASON_OtherReason = "NA";
	/** Toko tidak ditemukan = TK */
	public static final String REASON_TokoTidakDitemukan = "TK";
	/** Tutup = TP */
	public static final String REASON_Tutup = "TP";
	/** Terlambat = TR */
	public static final String REASON_Terlambat = "TR";
	/** Set Reason.
		@param Reason Reason	  */
	public void setReason (String Reason)
	{

		set_Value (COLUMNNAME_Reason, Reason);
	}

	/** Get Reason.
		@return Reason	  */
	public String getReason () 
	{
		return (String)get_Value(COLUMNNAME_Reason);
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

	public com.unicore.model.I_UNS_PL_Return getUNS_PL_Return() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PL_Return)MTable.get(getCtx(), com.unicore.model.I_UNS_PL_Return.Table_Name)
			.getPO(getUNS_PL_Return_ID(), get_TrxName());	}

	/** Set Packing List Return.
		@param UNS_PL_Return_ID Packing List Return	  */
	public void setUNS_PL_Return_ID (int UNS_PL_Return_ID)
	{
		if (UNS_PL_Return_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Return_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_Return_ID, Integer.valueOf(UNS_PL_Return_ID));
	}

	/** Get Packing List Return.
		@return Packing List Return	  */
	public int getUNS_PL_Return_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_Return_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Canceled Shipment.
		@param UNS_PL_ReturnOrder_ID Canceled Shipment	  */
	public void setUNS_PL_ReturnOrder_ID (int UNS_PL_ReturnOrder_ID)
	{
		if (UNS_PL_ReturnOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ReturnOrder_ID, Integer.valueOf(UNS_PL_ReturnOrder_ID));
	}

	/** Get Canceled Shipment.
		@return Canceled Shipment	  */
	public int getUNS_PL_ReturnOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ReturnOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PL_ReturnOrder_UU.
		@param UNS_PL_ReturnOrder_UU UNS_PL_ReturnOrder_UU	  */
	public void setUNS_PL_ReturnOrder_UU (String UNS_PL_ReturnOrder_UU)
	{
		set_Value (COLUMNNAME_UNS_PL_ReturnOrder_UU, UNS_PL_ReturnOrder_UU);
	}

	/** Get UNS_PL_ReturnOrder_UU.
		@return UNS_PL_ReturnOrder_UU	  */
	public String getUNS_PL_ReturnOrder_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ReturnOrder_UU);
	}

	/** Set Void It.
		@param VoidIt Void It	  */
	public void setVoidIt (String VoidIt)
	{
		set_Value (COLUMNNAME_VoidIt, VoidIt);
	}

	/** Get Void It.
		@return Void It	  */
	public String getVoidIt () 
	{
		return (String)get_Value(COLUMNNAME_VoidIt);
	}
}