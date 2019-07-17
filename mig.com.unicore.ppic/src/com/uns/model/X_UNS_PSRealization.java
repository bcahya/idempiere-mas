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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PSRealization
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PSRealization extends PO implements I_UNS_PSRealization, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130213L;

    /** Standard Constructor */
    public X_UNS_PSRealization (Properties ctx, int UNS_PSRealization_ID, String trxName)
    {
      super (ctx, UNS_PSRealization_ID, trxName);
      /** if (UNS_PSRealization_ID == 0)
        {
			setUNS_Production_Detail_ID (0);
			setUNS_ProductionSchedule_ID (0);
			setUNS_PSRealization_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PSRealization (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PSRealization[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Qty MT.
		@param QtyMT Qty MT	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_Value (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Qty MT.
		@return Qty MT	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty UOM.
		@param QtyUom Qty UOM	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_Value (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Qty UOM.
		@return Qty UOM	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Realization Production Date.
		@param Realization_PD Realization Production Date	  */
	public void setRealization_PD (Timestamp Realization_PD)
	{
		set_Value (COLUMNNAME_Realization_PD, Realization_PD);
	}

	/** Get Realization Production Date.
		@return Realization Production Date	  */
	public Timestamp getRealization_PD () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Realization_PD);
	}

	/** Set Targeted Production Date.
		@param Targeted_PD Targeted Production Date	  */
	public void setTargeted_PD (Timestamp Targeted_PD)
	{
		set_Value (COLUMNNAME_Targeted_PD, Targeted_PD);
	}

	/** Get Targeted Production Date.
		@return Targeted Production Date	  */
	public Timestamp getTargeted_PD () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Targeted_PD);
	}

	/** Set UNS Production Detail.
		@param UNS_Production_Detail_ID UNS Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID)
	{
		if (UNS_Production_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, Integer.valueOf(UNS_Production_Detail_ID));
	}

	/** Get UNS Production Detail.
		@return UNS Production Detail	  */
	public int getUNS_Production_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ProductionSchedule)MTable.get(getCtx(), com.uns.model.I_UNS_ProductionSchedule.Table_Name)
			.getPO(getUNS_ProductionSchedule_ID(), get_TrxName());	}

	/** Set Production Schedule.
		@param UNS_ProductionSchedule_ID Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID)
	{
		if (UNS_ProductionSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, Integer.valueOf(UNS_ProductionSchedule_ID));
	}

	/** Get Production Schedule.
		@return Production Schedule	  */
	public int getUNS_ProductionSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Schedule Realization.
		@param UNS_PSRealization_ID Production Schedule Realization	  */
	public void setUNS_PSRealization_ID (int UNS_PSRealization_ID)
	{
		if (UNS_PSRealization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PSRealization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PSRealization_ID, Integer.valueOf(UNS_PSRealization_ID));
	}

	/** Get Production Schedule Realization.
		@return Production Schedule Realization	  */
	public int getUNS_PSRealization_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PSRealization_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PSRealization_UU.
		@param UNS_PSRealization_UU UNS_PSRealization_UU	  */
	public void setUNS_PSRealization_UU (String UNS_PSRealization_UU)
	{
		set_Value (COLUMNNAME_UNS_PSRealization_UU, UNS_PSRealization_UU);
	}

	/** Get UNS_PSRealization_UU.
		@return UNS_PSRealization_UU	  */
	public String getUNS_PSRealization_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PSRealization_UU);
	}
}