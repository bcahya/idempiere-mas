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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Production_WorkerResult
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Production_WorkerResult extends PO implements I_UNS_Production_WorkerResult, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140212L;

    /** Standard Constructor */
    public X_UNS_Production_WorkerResult (Properties ctx, int UNS_Production_WorkerResult_ID, String trxName)
    {
      super (ctx, UNS_Production_WorkerResult_ID, trxName);
      /** if (UNS_Production_WorkerResult_ID == 0)
        {
			setIsConsiderOutput (true);
// Y
			setM_Product_ID (0);
			setProductionQty (Env.ZERO);
// 0
			setUNS_Production_Worker_ID (0);
			setUNS_Production_WorkerResult_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_WorkerResult (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_WorkerResult[")
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

	/** Set Contractor Incentive.
		@param InsentifPemborong 
		The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong)
	{
		set_Value (COLUMNNAME_InsentifPemborong, InsentifPemborong);
	}

	/** Get Contractor Incentive.
		@return The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InsentifPemborong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Consider Output.
		@param IsConsiderOutput Consider Output	  */
	public void setIsConsiderOutput (boolean IsConsiderOutput)
	{
		set_Value (COLUMNNAME_IsConsiderOutput, Boolean.valueOf(IsConsiderOutput));
	}

	/** Get Consider Output.
		@return Consider Output	  */
	public boolean isConsiderOutput () 
	{
		Object oo = get_Value(COLUMNNAME_IsConsiderOutput);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_Product_ID()));
    }

	/** Set Production Quantity.
		@param ProductionQty 
		Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty)
	{
		set_Value (COLUMNNAME_ProductionQty, ProductionQty);
	}

	/** Get Production Quantity.
		@return Quantity of products to produce
	  */
	public BigDecimal getProductionQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Receivable Amt.
		@param ReceivableAmt Receivable Amt	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt)
	{
		set_Value (COLUMNNAME_ReceivableAmt, ReceivableAmt);
	}

	/** Get Receivable Amt.
		@return Receivable Amt	  */
	public BigDecimal getReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Production Worker.
		@param UNS_Production_Worker_ID Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID)
	{
		if (UNS_Production_Worker_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Worker_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Worker_ID, Integer.valueOf(UNS_Production_Worker_ID));
	}

	/** Get Production Worker.
		@return Production Worker	  */
	public int getUNS_Production_Worker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Worker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Worker Result.
		@param UNS_Production_WorkerResult_ID Production Worker Result	  */
	public void setUNS_Production_WorkerResult_ID (int UNS_Production_WorkerResult_ID)
	{
		if (UNS_Production_WorkerResult_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_WorkerResult_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_WorkerResult_ID, Integer.valueOf(UNS_Production_WorkerResult_ID));
	}

	/** Get Production Worker Result.
		@return Production Worker Result	  */
	public int getUNS_Production_WorkerResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_WorkerResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Production_WorkerResult_UU.
		@param UNS_Production_WorkerResult_UU UNS_Production_WorkerResult_UU	  */
	public void setUNS_Production_WorkerResult_UU (String UNS_Production_WorkerResult_UU)
	{
		set_Value (COLUMNNAME_UNS_Production_WorkerResult_UU, UNS_Production_WorkerResult_UU);
	}

	/** Get UNS_Production_WorkerResult_UU.
		@return UNS_Production_WorkerResult_UU	  */
	public String getUNS_Production_WorkerResult_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_WorkerResult_UU);
	}
}