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

/** Generated Model for UNS_ProductAllocation
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ProductAllocation extends PO implements I_UNS_ProductAllocation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130720L;

    /** Standard Constructor */
    public X_UNS_ProductAllocation (Properties ctx, int UNS_ProductAllocation_ID, String trxName)
    {
      super (ctx, UNS_ProductAllocation_ID, trxName);
      /** if (UNS_ProductAllocation_ID == 0)
        {
			setIsPrimary (false);
// N
			setIsSold (false);
// N
			setM_Product_ID (0);
			setProcessed (false);
// N
			setUNS_Forecast_Header_ID (0);
// @0|UNS_Forecast_Header_ID@
			setUNS_ProductAllocation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ProductAllocation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ProductAllocation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Forecast Marketing Agt.
		@param ForecastMarketingAgt Forecast Marketing Agt	  */
	public void setForecastMarketingAgt (BigDecimal ForecastMarketingAgt)
	{
		set_Value (COLUMNNAME_ForecastMarketingAgt, ForecastMarketingAgt);
	}

	/** Get Forecast Marketing Agt.
		@return Forecast Marketing Agt	  */
	public BigDecimal getForecastMarketingAgt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingAgt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Agt (MT).
		@param ForecastMarketingAgtMT Forecast Marketing Agt (MT)	  */
	public void setForecastMarketingAgtMT (BigDecimal ForecastMarketingAgtMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingAgtMT, ForecastMarketingAgtMT);
	}

	/** Get Forecast Marketing Agt (MT).
		@return Forecast Marketing Agt (MT)	  */
	public BigDecimal getForecastMarketingAgtMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingAgtMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Apr.
		@param ForecastMarketingApr Forecast Marketing Apr	  */
	public void setForecastMarketingApr (BigDecimal ForecastMarketingApr)
	{
		set_Value (COLUMNNAME_ForecastMarketingApr, ForecastMarketingApr);
	}

	/** Get Forecast Marketing Apr.
		@return Forecast Marketing Apr	  */
	public BigDecimal getForecastMarketingApr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingApr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Apr (MT).
		@param ForecastMarketingAprMT Forecast Marketing Apr (MT)	  */
	public void setForecastMarketingAprMT (BigDecimal ForecastMarketingAprMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingAprMT, ForecastMarketingAprMT);
	}

	/** Get Forecast Marketing Apr (MT).
		@return Forecast Marketing Apr (MT)	  */
	public BigDecimal getForecastMarketingAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Dec.
		@param ForecastMarketingDec Forecast Marketing Dec	  */
	public void setForecastMarketingDec (BigDecimal ForecastMarketingDec)
	{
		set_Value (COLUMNNAME_ForecastMarketingDec, ForecastMarketingDec);
	}

	/** Get Forecast Marketing Dec.
		@return Forecast Marketing Dec	  */
	public BigDecimal getForecastMarketingDec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingDec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Dec (MT).
		@param ForecastMarketingDecMT Forecast Marketing Dec (MT)	  */
	public void setForecastMarketingDecMT (BigDecimal ForecastMarketingDecMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingDecMT, ForecastMarketingDecMT);
	}

	/** Get Forecast Marketing Dec (MT).
		@return Forecast Marketing Dec (MT)	  */
	public BigDecimal getForecastMarketingDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Feb.
		@param ForecastMarketingFeb Forecast Marketing Feb	  */
	public void setForecastMarketingFeb (BigDecimal ForecastMarketingFeb)
	{
		set_Value (COLUMNNAME_ForecastMarketingFeb, ForecastMarketingFeb);
	}

	/** Get Forecast Marketing Feb.
		@return Forecast Marketing Feb	  */
	public BigDecimal getForecastMarketingFeb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingFeb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Feb (MT).
		@param ForecastMarketingFebMT Forecast Marketing Feb (MT)	  */
	public void setForecastMarketingFebMT (BigDecimal ForecastMarketingFebMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingFebMT, ForecastMarketingFebMT);
	}

	/** Get Forecast Marketing Feb (MT).
		@return Forecast Marketing Feb (MT)	  */
	public BigDecimal getForecastMarketingFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jan.
		@param ForecastMarketingJan Forecast Marketing Jan	  */
	public void setForecastMarketingJan (BigDecimal ForecastMarketingJan)
	{
		set_Value (COLUMNNAME_ForecastMarketingJan, ForecastMarketingJan);
	}

	/** Get Forecast Marketing Jan.
		@return Forecast Marketing Jan	  */
	public BigDecimal getForecastMarketingJan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jan (MT).
		@param ForecastMarketingJanMT Forecast Marketing Jan (MT)	  */
	public void setForecastMarketingJanMT (BigDecimal ForecastMarketingJanMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingJanMT, ForecastMarketingJanMT);
	}

	/** Get Forecast Marketing Jan (MT).
		@return Forecast Marketing Jan (MT)	  */
	public BigDecimal getForecastMarketingJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jul.
		@param ForecastMarketingJul Forecast Marketing Jul	  */
	public void setForecastMarketingJul (BigDecimal ForecastMarketingJul)
	{
		set_Value (COLUMNNAME_ForecastMarketingJul, ForecastMarketingJul);
	}

	/** Get Forecast Marketing Jul.
		@return Forecast Marketing Jul	  */
	public BigDecimal getForecastMarketingJul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jul (MT).
		@param ForecastMarketingJulMT Forecast Marketing Jul (MT)	  */
	public void setForecastMarketingJulMT (BigDecimal ForecastMarketingJulMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingJulMT, ForecastMarketingJulMT);
	}

	/** Get Forecast Marketing Jul (MT).
		@return Forecast Marketing Jul (MT)	  */
	public BigDecimal getForecastMarketingJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jun.
		@param ForecastMarketingJun Forecast Marketing Jun	  */
	public void setForecastMarketingJun (BigDecimal ForecastMarketingJun)
	{
		set_Value (COLUMNNAME_ForecastMarketingJun, ForecastMarketingJun);
	}

	/** Get Forecast Marketing Jun.
		@return Forecast Marketing Jun	  */
	public BigDecimal getForecastMarketingJun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Jun (MT).
		@param ForecastMarketingJunMT Forecast Marketing Jun (MT)	  */
	public void setForecastMarketingJunMT (BigDecimal ForecastMarketingJunMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingJunMT, ForecastMarketingJunMT);
	}

	/** Get Forecast Marketing Jun (MT).
		@return Forecast Marketing Jun (MT)	  */
	public BigDecimal getForecastMarketingJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Mar.
		@param ForecastMarketingMar Forecast Marketing Mar	  */
	public void setForecastMarketingMar (BigDecimal ForecastMarketingMar)
	{
		set_Value (COLUMNNAME_ForecastMarketingMar, ForecastMarketingMar);
	}

	/** Get Forecast Marketing Mar.
		@return Forecast Marketing Mar	  */
	public BigDecimal getForecastMarketingMar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingMar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Mar (MT).
		@param ForecastMarketingMarMT Forecast Marketing Mar (MT)	  */
	public void setForecastMarketingMarMT (BigDecimal ForecastMarketingMarMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingMarMT, ForecastMarketingMarMT);
	}

	/** Get Forecast Marketing Mar (MT).
		@return Forecast Marketing Mar (MT)	  */
	public BigDecimal getForecastMarketingMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing May.
		@param ForecastMarketingMay Forecast Marketing May	  */
	public void setForecastMarketingMay (BigDecimal ForecastMarketingMay)
	{
		set_Value (COLUMNNAME_ForecastMarketingMay, ForecastMarketingMay);
	}

	/** Get Forecast Marketing May.
		@return Forecast Marketing May	  */
	public BigDecimal getForecastMarketingMay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingMay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing May (MT).
		@param ForecastMarketingMayMT Forecast Marketing May (MT)	  */
	public void setForecastMarketingMayMT (BigDecimal ForecastMarketingMayMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingMayMT, ForecastMarketingMayMT);
	}

	/** Get Forecast Marketing May (MT).
		@return Forecast Marketing May (MT)	  */
	public BigDecimal getForecastMarketingMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Nov.
		@param ForecastMarketingNov Forecast Marketing Nov	  */
	public void setForecastMarketingNov (BigDecimal ForecastMarketingNov)
	{
		set_Value (COLUMNNAME_ForecastMarketingNov, ForecastMarketingNov);
	}

	/** Get Forecast Marketing Nov.
		@return Forecast Marketing Nov	  */
	public BigDecimal getForecastMarketingNov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingNov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Nov (MT).
		@param ForecastMarketingNovMT Forecast Marketing Nov (MT)	  */
	public void setForecastMarketingNovMT (BigDecimal ForecastMarketingNovMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingNovMT, ForecastMarketingNovMT);
	}

	/** Get Forecast Marketing Nov (MT).
		@return Forecast Marketing Nov (MT)	  */
	public BigDecimal getForecastMarketingNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Oct.
		@param ForecastMarketingOct Forecast Marketing Oct	  */
	public void setForecastMarketingOct (BigDecimal ForecastMarketingOct)
	{
		set_Value (COLUMNNAME_ForecastMarketingOct, ForecastMarketingOct);
	}

	/** Get Forecast Marketing Oct.
		@return Forecast Marketing Oct	  */
	public BigDecimal getForecastMarketingOct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingOct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Oct (MT).
		@param ForecastMarketingOctMT Forecast Marketing Oct (MT)	  */
	public void setForecastMarketingOctMT (BigDecimal ForecastMarketingOctMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingOctMT, ForecastMarketingOctMT);
	}

	/** Get Forecast Marketing Oct (MT).
		@return Forecast Marketing Oct (MT)	  */
	public BigDecimal getForecastMarketingOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Sep.
		@param ForecastMarketingSep Forecast Marketing Sep	  */
	public void setForecastMarketingSep (BigDecimal ForecastMarketingSep)
	{
		set_Value (COLUMNNAME_ForecastMarketingSep, ForecastMarketingSep);
	}

	/** Get Forecast Marketing Sep.
		@return Forecast Marketing Sep	  */
	public BigDecimal getForecastMarketingSep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingSep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Marketing Sep (MT).
		@param ForecastMarketingSepMT Forecast Marketing Sep (MT)	  */
	public void setForecastMarketingSepMT (BigDecimal ForecastMarketingSepMT)
	{
		set_Value (COLUMNNAME_ForecastMarketingSepMT, ForecastMarketingSepMT);
	}

	/** Get Forecast Marketing Sep (MT).
		@return Forecast Marketing Sep (MT)	  */
	public BigDecimal getForecastMarketingSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastMarketingSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Agt.
		@param ForecastRMPAgt Forecast RMP Agt	  */
	public void setForecastRMPAgt (BigDecimal ForecastRMPAgt)
	{
		set_Value (COLUMNNAME_ForecastRMPAgt, ForecastRMPAgt);
	}

	/** Get Forecast RMP Agt.
		@return Forecast RMP Agt	  */
	public BigDecimal getForecastRMPAgt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPAgt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Agt (MT).
		@param ForecastRMPAgtMT Forecast RMP Agt (MT)	  */
	public void setForecastRMPAgtMT (BigDecimal ForecastRMPAgtMT)
	{
		set_Value (COLUMNNAME_ForecastRMPAgtMT, ForecastRMPAgtMT);
	}

	/** Get Forecast RMP Agt (MT).
		@return Forecast RMP Agt (MT)	  */
	public BigDecimal getForecastRMPAgtMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPAgtMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Apr.
		@param ForecastRMPApr Forecast RMP Apr	  */
	public void setForecastRMPApr (BigDecimal ForecastRMPApr)
	{
		set_Value (COLUMNNAME_ForecastRMPApr, ForecastRMPApr);
	}

	/** Get Forecast RMP Apr.
		@return Forecast RMP Apr	  */
	public BigDecimal getForecastRMPApr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPApr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Apr (MT).
		@param ForecastRMPAprMT Forecast RMP Apr (MT)	  */
	public void setForecastRMPAprMT (BigDecimal ForecastRMPAprMT)
	{
		set_Value (COLUMNNAME_ForecastRMPAprMT, ForecastRMPAprMT);
	}

	/** Get Forecast RMP Apr (MT).
		@return Forecast RMP Apr (MT)	  */
	public BigDecimal getForecastRMPAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Dec.
		@param ForecastRMPDec Forecast RMP Dec	  */
	public void setForecastRMPDec (BigDecimal ForecastRMPDec)
	{
		set_Value (COLUMNNAME_ForecastRMPDec, ForecastRMPDec);
	}

	/** Get Forecast RMP Dec.
		@return Forecast RMP Dec	  */
	public BigDecimal getForecastRMPDec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPDec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Dec (MT).
		@param ForecastRMPDecMT Forecast RMP Dec (MT)	  */
	public void setForecastRMPDecMT (BigDecimal ForecastRMPDecMT)
	{
		set_Value (COLUMNNAME_ForecastRMPDecMT, ForecastRMPDecMT);
	}

	/** Get Forecast RMP Dec (MT).
		@return Forecast RMP Dec (MT)	  */
	public BigDecimal getForecastRMPDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Feb.
		@param ForecastRMPFeb Forecast RMP Feb	  */
	public void setForecastRMPFeb (BigDecimal ForecastRMPFeb)
	{
		set_Value (COLUMNNAME_ForecastRMPFeb, ForecastRMPFeb);
	}

	/** Get Forecast RMP Feb.
		@return Forecast RMP Feb	  */
	public BigDecimal getForecastRMPFeb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPFeb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Feb (MT).
		@param ForecastRMPFebMT Forecast RMP Feb (MT)	  */
	public void setForecastRMPFebMT (BigDecimal ForecastRMPFebMT)
	{
		set_Value (COLUMNNAME_ForecastRMPFebMT, ForecastRMPFebMT);
	}

	/** Get Forecast RMP Feb (MT).
		@return Forecast RMP Feb (MT)	  */
	public BigDecimal getForecastRMPFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jan.
		@param ForecastRMPJan Forecast RMP Jan	  */
	public void setForecastRMPJan (BigDecimal ForecastRMPJan)
	{
		set_Value (COLUMNNAME_ForecastRMPJan, ForecastRMPJan);
	}

	/** Get Forecast RMP Jan.
		@return Forecast RMP Jan	  */
	public BigDecimal getForecastRMPJan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jan (MT).
		@param ForecastRMPJanMT Forecast RMP Jan (MT)	  */
	public void setForecastRMPJanMT (BigDecimal ForecastRMPJanMT)
	{
		set_Value (COLUMNNAME_ForecastRMPJanMT, ForecastRMPJanMT);
	}

	/** Get Forecast RMP Jan (MT).
		@return Forecast RMP Jan (MT)	  */
	public BigDecimal getForecastRMPJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jul.
		@param ForecastRMPJul Forecast RMP Jul	  */
	public void setForecastRMPJul (BigDecimal ForecastRMPJul)
	{
		set_Value (COLUMNNAME_ForecastRMPJul, ForecastRMPJul);
	}

	/** Get Forecast RMP Jul.
		@return Forecast RMP Jul	  */
	public BigDecimal getForecastRMPJul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jul (MT).
		@param ForecastRMPJulMT Forecast RMP Jul (MT)	  */
	public void setForecastRMPJulMT (BigDecimal ForecastRMPJulMT)
	{
		set_Value (COLUMNNAME_ForecastRMPJulMT, ForecastRMPJulMT);
	}

	/** Get Forecast RMP Jul (MT).
		@return Forecast RMP Jul (MT)	  */
	public BigDecimal getForecastRMPJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jun.
		@param ForecastRMPJun Forecast RMP Jun	  */
	public void setForecastRMPJun (BigDecimal ForecastRMPJun)
	{
		set_Value (COLUMNNAME_ForecastRMPJun, ForecastRMPJun);
	}

	/** Get Forecast RMP Jun.
		@return Forecast RMP Jun	  */
	public BigDecimal getForecastRMPJun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Jun (MT).
		@param ForecastRMPJunMT Forecast RMP Jun (MT)	  */
	public void setForecastRMPJunMT (BigDecimal ForecastRMPJunMT)
	{
		set_Value (COLUMNNAME_ForecastRMPJunMT, ForecastRMPJunMT);
	}

	/** Get Forecast RMP Jun (MT).
		@return Forecast RMP Jun (MT)	  */
	public BigDecimal getForecastRMPJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Mar.
		@param ForecastRMPMar Forecast RMP Mar	  */
	public void setForecastRMPMar (BigDecimal ForecastRMPMar)
	{
		set_Value (COLUMNNAME_ForecastRMPMar, ForecastRMPMar);
	}

	/** Get Forecast RMP Mar.
		@return Forecast RMP Mar	  */
	public BigDecimal getForecastRMPMar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPMar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Mar (MT).
		@param ForecastRMPMarMT Forecast RMP Mar (MT)	  */
	public void setForecastRMPMarMT (BigDecimal ForecastRMPMarMT)
	{
		set_Value (COLUMNNAME_ForecastRMPMarMT, ForecastRMPMarMT);
	}

	/** Get Forecast RMP Mar (MT).
		@return Forecast RMP Mar (MT)	  */
	public BigDecimal getForecastRMPMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP May.
		@param ForecastRMPMay Forecast RMP May	  */
	public void setForecastRMPMay (BigDecimal ForecastRMPMay)
	{
		set_Value (COLUMNNAME_ForecastRMPMay, ForecastRMPMay);
	}

	/** Get Forecast RMP May.
		@return Forecast RMP May	  */
	public BigDecimal getForecastRMPMay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPMay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP May (MT).
		@param ForecastRMPMayMT Forecast RMP May (MT)	  */
	public void setForecastRMPMayMT (BigDecimal ForecastRMPMayMT)
	{
		set_Value (COLUMNNAME_ForecastRMPMayMT, ForecastRMPMayMT);
	}

	/** Get Forecast RMP May (MT).
		@return Forecast RMP May (MT)	  */
	public BigDecimal getForecastRMPMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Nov.
		@param ForecastRMPNov Forecast RMP Nov	  */
	public void setForecastRMPNov (BigDecimal ForecastRMPNov)
	{
		set_Value (COLUMNNAME_ForecastRMPNov, ForecastRMPNov);
	}

	/** Get Forecast RMP Nov.
		@return Forecast RMP Nov	  */
	public BigDecimal getForecastRMPNov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPNov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Nov (MT).
		@param ForecastRMPNovMT Forecast RMP Nov (MT)	  */
	public void setForecastRMPNovMT (BigDecimal ForecastRMPNovMT)
	{
		set_Value (COLUMNNAME_ForecastRMPNovMT, ForecastRMPNovMT);
	}

	/** Get Forecast RMP Nov (MT).
		@return Forecast RMP Nov (MT)	  */
	public BigDecimal getForecastRMPNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Oct.
		@param ForecastRMPOct Forecast RMP Oct	  */
	public void setForecastRMPOct (BigDecimal ForecastRMPOct)
	{
		set_Value (COLUMNNAME_ForecastRMPOct, ForecastRMPOct);
	}

	/** Get Forecast RMP Oct.
		@return Forecast RMP Oct	  */
	public BigDecimal getForecastRMPOct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPOct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Oct (MT).
		@param ForecastRMPOctMT Forecast RMP Oct (MT)	  */
	public void setForecastRMPOctMT (BigDecimal ForecastRMPOctMT)
	{
		set_Value (COLUMNNAME_ForecastRMPOctMT, ForecastRMPOctMT);
	}

	/** Get Forecast RMP Oct (MT).
		@return Forecast RMP Oct (MT)	  */
	public BigDecimal getForecastRMPOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Sep.
		@param ForecastRMPSep Forecast RMP Sep	  */
	public void setForecastRMPSep (BigDecimal ForecastRMPSep)
	{
		set_Value (COLUMNNAME_ForecastRMPSep, ForecastRMPSep);
	}

	/** Get Forecast RMP Sep.
		@return Forecast RMP Sep	  */
	public BigDecimal getForecastRMPSep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPSep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast RMP Sep (MT).
		@param ForecastRMPSepMT Forecast RMP Sep (MT)	  */
	public void setForecastRMPSepMT (BigDecimal ForecastRMPSepMT)
	{
		set_Value (COLUMNNAME_ForecastRMPSepMT, ForecastRMPSepMT);
	}

	/** Get Forecast RMP Sep (MT).
		@return Forecast RMP Sep (MT)	  */
	public BigDecimal getForecastRMPSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastRMPSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Agt.
		@param ForecastTargetedAgt 
		Forecast Targeted August
	  */
	public void setForecastTargetedAgt (BigDecimal ForecastTargetedAgt)
	{
		set_Value (COLUMNNAME_ForecastTargetedAgt, ForecastTargetedAgt);
	}

	/** Get Forecast Targeted Agt.
		@return Forecast Targeted August
	  */
	public BigDecimal getForecastTargetedAgt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedAgt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Agt (MT).
		@param ForecastTargetedAgtMT 
		Forecast Targeted August
	  */
	public void setForecastTargetedAgtMT (BigDecimal ForecastTargetedAgtMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedAgtMT, ForecastTargetedAgtMT);
	}

	/** Get Forecast Targeted Agt (MT).
		@return Forecast Targeted August
	  */
	public BigDecimal getForecastTargetedAgtMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedAgtMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Apr.
		@param ForecastTargetedApr 
		Forecast Targeted April
	  */
	public void setForecastTargetedApr (BigDecimal ForecastTargetedApr)
	{
		set_Value (COLUMNNAME_ForecastTargetedApr, ForecastTargetedApr);
	}

	/** Get Forecast Targeted Apr.
		@return Forecast Targeted April
	  */
	public BigDecimal getForecastTargetedApr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedApr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Apr (MT).
		@param ForecastTargetedAprMT 
		Forecast Targeted April
	  */
	public void setForecastTargetedAprMT (BigDecimal ForecastTargetedAprMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedAprMT, ForecastTargetedAprMT);
	}

	/** Get Forecast Targeted Apr (MT).
		@return Forecast Targeted April
	  */
	public BigDecimal getForecastTargetedAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Dec.
		@param ForecastTargetedDec 
		Forecast Targeted December
	  */
	public void setForecastTargetedDec (BigDecimal ForecastTargetedDec)
	{
		set_Value (COLUMNNAME_ForecastTargetedDec, ForecastTargetedDec);
	}

	/** Get Forecast Targeted Dec.
		@return Forecast Targeted December
	  */
	public BigDecimal getForecastTargetedDec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedDec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Dec (MT).
		@param ForecastTargetedDecMT 
		Forecast Targeted December
	  */
	public void setForecastTargetedDecMT (BigDecimal ForecastTargetedDecMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedDecMT, ForecastTargetedDecMT);
	}

	/** Get Forecast Targeted Dec (MT).
		@return Forecast Targeted December
	  */
	public BigDecimal getForecastTargetedDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Feb.
		@param ForecastTargetedFeb 
		Forecast Targeted February
	  */
	public void setForecastTargetedFeb (BigDecimal ForecastTargetedFeb)
	{
		set_Value (COLUMNNAME_ForecastTargetedFeb, ForecastTargetedFeb);
	}

	/** Get Forecast Targeted Feb.
		@return Forecast Targeted February
	  */
	public BigDecimal getForecastTargetedFeb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedFeb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Feb (MT).
		@param ForecastTargetedFebMT 
		Forecast Targeted February
	  */
	public void setForecastTargetedFebMT (BigDecimal ForecastTargetedFebMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedFebMT, ForecastTargetedFebMT);
	}

	/** Get Forecast Targeted Feb (MT).
		@return Forecast Targeted February
	  */
	public BigDecimal getForecastTargetedFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jan.
		@param ForecastTargetedJan 
		Forecast Targeted January
	  */
	public void setForecastTargetedJan (BigDecimal ForecastTargetedJan)
	{
		set_Value (COLUMNNAME_ForecastTargetedJan, ForecastTargetedJan);
	}

	/** Get Forecast Targeted Jan.
		@return Forecast Targeted January
	  */
	public BigDecimal getForecastTargetedJan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jan (MT).
		@param ForecastTargetedJanMT 
		Forecast Targeted January
	  */
	public void setForecastTargetedJanMT (BigDecimal ForecastTargetedJanMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedJanMT, ForecastTargetedJanMT);
	}

	/** Get Forecast Targeted Jan (MT).
		@return Forecast Targeted January
	  */
	public BigDecimal getForecastTargetedJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jul.
		@param ForecastTargetedJul 
		Forecast Targeted July
	  */
	public void setForecastTargetedJul (BigDecimal ForecastTargetedJul)
	{
		set_Value (COLUMNNAME_ForecastTargetedJul, ForecastTargetedJul);
	}

	/** Get Forecast Targeted Jul.
		@return Forecast Targeted July
	  */
	public BigDecimal getForecastTargetedJul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jul (MT).
		@param ForecastTargetedJulMT 
		Forecast Targeted July
	  */
	public void setForecastTargetedJulMT (BigDecimal ForecastTargetedJulMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedJulMT, ForecastTargetedJulMT);
	}

	/** Get Forecast Targeted Jul (MT).
		@return Forecast Targeted July
	  */
	public BigDecimal getForecastTargetedJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jun.
		@param ForecastTargetedJun 
		Forecast Targeted Juny
	  */
	public void setForecastTargetedJun (BigDecimal ForecastTargetedJun)
	{
		set_Value (COLUMNNAME_ForecastTargetedJun, ForecastTargetedJun);
	}

	/** Get Forecast Targeted Jun.
		@return Forecast Targeted Juny
	  */
	public BigDecimal getForecastTargetedJun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Jun (MT).
		@param ForecastTargetedJunMT 
		Forecast Targeted Juny
	  */
	public void setForecastTargetedJunMT (BigDecimal ForecastTargetedJunMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedJunMT, ForecastTargetedJunMT);
	}

	/** Get Forecast Targeted Jun (MT).
		@return Forecast Targeted Juny
	  */
	public BigDecimal getForecastTargetedJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Mar.
		@param ForecastTargetedMar 
		Forecast Targeted March
	  */
	public void setForecastTargetedMar (BigDecimal ForecastTargetedMar)
	{
		set_Value (COLUMNNAME_ForecastTargetedMar, ForecastTargetedMar);
	}

	/** Get Forecast Targeted Mar.
		@return Forecast Targeted March
	  */
	public BigDecimal getForecastTargetedMar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedMar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Mar (MT).
		@param ForecastTargetedMarMT 
		Forecast Targeted March
	  */
	public void setForecastTargetedMarMT (BigDecimal ForecastTargetedMarMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedMarMT, ForecastTargetedMarMT);
	}

	/** Get Forecast Targeted Mar (MT).
		@return Forecast Targeted March
	  */
	public BigDecimal getForecastTargetedMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted May.
		@param ForecastTargetedMay 
		Forecast Targeted May
	  */
	public void setForecastTargetedMay (BigDecimal ForecastTargetedMay)
	{
		set_Value (COLUMNNAME_ForecastTargetedMay, ForecastTargetedMay);
	}

	/** Get Forecast Targeted May.
		@return Forecast Targeted May
	  */
	public BigDecimal getForecastTargetedMay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedMay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted May (MT).
		@param ForecastTargetedMayMT 
		Forecast Targeted May
	  */
	public void setForecastTargetedMayMT (BigDecimal ForecastTargetedMayMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedMayMT, ForecastTargetedMayMT);
	}

	/** Get Forecast Targeted May (MT).
		@return Forecast Targeted May
	  */
	public BigDecimal getForecastTargetedMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Nov.
		@param ForecastTargetedNov 
		Forecast Targeted November
	  */
	public void setForecastTargetedNov (BigDecimal ForecastTargetedNov)
	{
		set_Value (COLUMNNAME_ForecastTargetedNov, ForecastTargetedNov);
	}

	/** Get Forecast Targeted Nov.
		@return Forecast Targeted November
	  */
	public BigDecimal getForecastTargetedNov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedNov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Nov (MT).
		@param ForecastTargetedNovMT 
		Forecast Targeted November
	  */
	public void setForecastTargetedNovMT (BigDecimal ForecastTargetedNovMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedNovMT, ForecastTargetedNovMT);
	}

	/** Get Forecast Targeted Nov (MT).
		@return Forecast Targeted November
	  */
	public BigDecimal getForecastTargetedNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Oct.
		@param ForecastTargetedOct 
		Forecast Targeted October
	  */
	public void setForecastTargetedOct (BigDecimal ForecastTargetedOct)
	{
		set_Value (COLUMNNAME_ForecastTargetedOct, ForecastTargetedOct);
	}

	/** Get Forecast Targeted Oct.
		@return Forecast Targeted October
	  */
	public BigDecimal getForecastTargetedOct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedOct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Oct (MT).
		@param ForecastTargetedOctMT 
		Forecast Targeted October
	  */
	public void setForecastTargetedOctMT (BigDecimal ForecastTargetedOctMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedOctMT, ForecastTargetedOctMT);
	}

	/** Get Forecast Targeted Oct (MT).
		@return Forecast Targeted October
	  */
	public BigDecimal getForecastTargetedOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Sep.
		@param ForecastTargetedSep 
		Forecast Targeted September
	  */
	public void setForecastTargetedSep (BigDecimal ForecastTargetedSep)
	{
		set_Value (COLUMNNAME_ForecastTargetedSep, ForecastTargetedSep);
	}

	/** Get Forecast Targeted Sep.
		@return Forecast Targeted September
	  */
	public BigDecimal getForecastTargetedSep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedSep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecast Targeted Sep (MT).
		@param ForecastTargetedSepMT 
		Forecast Targeted September
	  */
	public void setForecastTargetedSepMT (BigDecimal ForecastTargetedSepMT)
	{
		set_Value (COLUMNNAME_ForecastTargetedSepMT, ForecastTargetedSepMT);
	}

	/** Get Forecast Targeted Sep (MT).
		@return Forecast Targeted September
	  */
	public BigDecimal getForecastTargetedSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ForecastTargetedSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Is Primary Output.
		@param IsPrimary 
		Indicates if this is the primary output of a resource
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_ValueNoCheck (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Is Primary Output.
		@return Indicates if this is the primary output of a resource
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sold.
		@param IsSold 
		Organization sells this product
	  */
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Forecast_Header)MTable.get(getCtx(), com.uns.model.I_UNS_Forecast_Header.Table_Name)
			.getPO(getUNS_Forecast_Header_ID(), get_TrxName());	}

	/** Set Manufacturing Forecast.
		@param UNS_Forecast_Header_ID Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID)
	{
		if (UNS_Forecast_Header_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Forecast_Header_ID, Integer.valueOf(UNS_Forecast_Header_ID));
	}

	/** Get Manufacturing Forecast.
		@return Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Forecast_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Allocation.
		@param UNS_ProductAllocation_ID Product Allocation	  */
	public void setUNS_ProductAllocation_ID (int UNS_ProductAllocation_ID)
	{
		if (UNS_ProductAllocation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductAllocation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductAllocation_ID, Integer.valueOf(UNS_ProductAllocation_ID));
	}

	/** Get Product Allocation.
		@return Product Allocation	  */
	public int getUNS_ProductAllocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductAllocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Allocation UU.
		@param UNS_ProductAllocation_UU Product Allocation UU	  */
	public void setUNS_ProductAllocation_UU (String UNS_ProductAllocation_UU)
	{
		set_Value (COLUMNNAME_UNS_ProductAllocation_UU, UNS_ProductAllocation_UU);
	}

	/** Get Product Allocation UU.
		@return Product Allocation UU	  */
	public String getUNS_ProductAllocation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ProductAllocation_UU);
	}
}