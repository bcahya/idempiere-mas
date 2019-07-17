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

/** Generated Model for UNS_BCNumber_Transaction
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_BCNumber_Transaction extends PO implements I_UNS_BCNumber_Transaction, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130831L;

    /** Standard Constructor */
    public X_UNS_BCNumber_Transaction (Properties ctx, int UNS_BCNumber_Transaction_ID, String trxName)
    {
      super (ctx, UNS_BCNumber_Transaction_ID, trxName);
      /** if (UNS_BCNumber_Transaction_ID == 0)
        {
			setUNS_BCNumber_Transaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BCNumber_Transaction (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BCNumber_Transaction[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Coconut RMP.
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

	/** Get Coconut RMP.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_BCNumber_GeneratorLine getUNS_BCNumber_GeneratorLine() throws RuntimeException
    {
		return (com.uns.model.I_UNS_BCNumber_GeneratorLine)MTable.get(getCtx(), com.uns.model.I_UNS_BCNumber_GeneratorLine.Table_Name)
			.getPO(getUNS_BCNumber_GeneratorLine_ID(), get_TrxName());	}

	/** Set BC Number Generator Line.
		@param UNS_BCNumber_GeneratorLine_ID BC Number Generator Line	  */
	public void setUNS_BCNumber_GeneratorLine_ID (int UNS_BCNumber_GeneratorLine_ID)
	{
		if (UNS_BCNumber_GeneratorLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_GeneratorLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_GeneratorLine_ID, Integer.valueOf(UNS_BCNumber_GeneratorLine_ID));
	}

	/** Get BC Number Generator Line.
		@return BC Number Generator Line	  */
	public int getUNS_BCNumber_GeneratorLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCNumber_GeneratorLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bea Cukai Transaction.
		@param UNS_BCNumber_Transaction_ID Bea Cukai Transaction	  */
	public void setUNS_BCNumber_Transaction_ID (int UNS_BCNumber_Transaction_ID)
	{
		if (UNS_BCNumber_Transaction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Transaction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Transaction_ID, Integer.valueOf(UNS_BCNumber_Transaction_ID));
	}

	/** Get Bea Cukai Transaction.
		@return Bea Cukai Transaction	  */
	public int getUNS_BCNumber_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCNumber_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BCNumber_Transaction_UU.
		@param UNS_BCNumber_Transaction_UU UNS_BCNumber_Transaction_UU	  */
	public void setUNS_BCNumber_Transaction_UU (String UNS_BCNumber_Transaction_UU)
	{
		set_Value (COLUMNNAME_UNS_BCNumber_Transaction_UU, UNS_BCNumber_Transaction_UU);
	}

	/** Get UNS_BCNumber_Transaction_UU.
		@return UNS_BCNumber_Transaction_UU	  */
	public String getUNS_BCNumber_Transaction_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BCNumber_Transaction_UU);
	}

	/** Set Packing Slip.
		@param UNS_PackingSlip_ID Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID)
	{
		if (UNS_PackingSlip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, Integer.valueOf(UNS_PackingSlip_ID));
	}

	/** Get Packing Slip.
		@return Packing Slip	  */
	public int getUNS_PackingSlip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}