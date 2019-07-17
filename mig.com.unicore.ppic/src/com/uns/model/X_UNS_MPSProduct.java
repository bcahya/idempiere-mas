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

/** Generated Model for UNS_MPSProduct
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MPSProduct extends PO implements I_UNS_MPSProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130928L;

    /** Standard Constructor */
    public X_UNS_MPSProduct (Properties ctx, int UNS_MPSProduct_ID, String trxName)
    {
      super (ctx, UNS_MPSProduct_ID, trxName);
      /** if (UNS_MPSProduct_ID == 0)
        {
			setActualProduced (Env.ZERO);
// 0
			setActualProducedMT (Env.ZERO);
// 0
			setActualScheduledMT (Env.ZERO);
// 0
			setActualScheduledUOM (Env.ZERO);
// 0
			setActualToOrderAprMT (Env.ZERO);
// 0
			setActualToOrderAprUOM (Env.ZERO);
// 0
			setActualToOrderAugMT (Env.ZERO);
// 0
			setActualToOrderAugUOM (Env.ZERO);
// 0
			setActualToOrderDecMT (Env.ZERO);
// 0
			setActualToOrderDecUOM (Env.ZERO);
// 0
			setActualToOrderFebMT (Env.ZERO);
// 0
			setActualToOrderFebUOM (Env.ZERO);
// 0
			setActualToOrderJanMT (Env.ZERO);
// 0
			setActualToOrderJanUOM (Env.ZERO);
// 0
			setActualToOrderJulMT (Env.ZERO);
// 0
			setActualToOrderJulUOM (Env.ZERO);
// 0
			setActualToOrderJunMT (Env.ZERO);
// 0
			setActualToOrderJunUOM (Env.ZERO);
// 0
			setActualToOrderMarMT (Env.ZERO);
// 0
			setActualToOrderMarUOM (Env.ZERO);
// 0
			setActualToOrderMayMT (Env.ZERO);
// 0
			setActualToOrderMayUOM (Env.ZERO);
// 0
			setActualToOrderNovMT (Env.ZERO);
// 0
			setActualToOrderNovUOM (Env.ZERO);
// 0
			setActualToOrderOctMT (Env.ZERO);
// 0
			setActualToOrderOctUOM (Env.ZERO);
// 0
			setActualToOrderSepMT (Env.ZERO);
// 0
			setActualToOrderSepUOM (Env.ZERO);
// 0
			setC_UOM_ID (0);
			setIncubationDays (0);
// 0
			setInitialProjectedStock_OnHand (Env.ZERO);
			setIsGenerate (false);
// N
			setM_Product_ID (0);
			setQtyMT_Agt (Env.ZERO);
// 0
			setQtyMT_Apr (Env.ZERO);
// 0
			setQtyMT_Dec (Env.ZERO);
// 0
			setQtyMT_Feb (Env.ZERO);
// 0
			setQtyMT_Jan (Env.ZERO);
// 0
			setQtyMT_Jul (Env.ZERO);
// 0
			setQtyMT_Jun (Env.ZERO);
// 0
			setQtyMT_Mar (Env.ZERO);
// 0
			setQtyMT_May (Env.ZERO);
// 0
			setQtyMT_Nov (Env.ZERO);
// 0
			setQtyMT_Oct (Env.ZERO);
// 0
			setQtyMT_Sep (Env.ZERO);
// 0
			setQtyOnHand (Env.ZERO);
// 0
			setQtyUOM_Agt (Env.ZERO);
// 0
			setQtyUOM_Apr (Env.ZERO);
// 0
			setQtyUOM_Dec (Env.ZERO);
// 0
			setQtyUOM_Feb (Env.ZERO);
// 0
			setQtyUOM_Jan (Env.ZERO);
// 0
			setQtyUOM_Jul (Env.ZERO);
// 0
			setQtyUOM_Jun (Env.ZERO);
// 0
			setQtyUOM_Mar (Env.ZERO);
// 0
			setQtyUOM_May (Env.ZERO);
// 0
			setQtyUOM_Nov (Env.ZERO);
// 0
			setQtyUOM_Oct (Env.ZERO);
// 0
			setQtyUOM_Sep (Env.ZERO);
// 0
			setSafetyStock (Env.ZERO);
// 0
			setSOH (Env.ZERO);
// 0
			setTotalActualToOrderMT (Env.ZERO);
// 0
			setTotalActualToOrderUOM (Env.ZERO);
// 0
			setTotalActualToStockMT (Env.ZERO);
// 0
			setTotalActualToStockUOM (Env.ZERO);
			setUNS_MPSHeader_ID (0);
			setUNS_MPSProduct_ID (0);
			setWeekToBeUpdated (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_MPSProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MPSProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Manufactured-Apr MT.
		@param ActualManufacturedAprMT Manufactured-Apr MT	  */
	public void setActualManufacturedAprMT (BigDecimal ActualManufacturedAprMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedAprMT, ActualManufacturedAprMT);
	}

	/** Get Manufactured-Apr MT.
		@return Manufactured-Apr MT	  */
	public BigDecimal getActualManufacturedAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Apr.
		@param ActualManufacturedAprUOM Manufactured-Apr	  */
	public void setActualManufacturedAprUOM (BigDecimal ActualManufacturedAprUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedAprUOM, ActualManufacturedAprUOM);
	}

	/** Get Manufactured-Apr.
		@return Manufactured-Apr	  */
	public BigDecimal getActualManufacturedAprUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedAprUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Aug MT.
		@param ActualManufacturedAugMT Manufactured-Aug MT	  */
	public void setActualManufacturedAugMT (BigDecimal ActualManufacturedAugMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedAugMT, ActualManufacturedAugMT);
	}

	/** Get Manufactured-Aug MT.
		@return Manufactured-Aug MT	  */
	public BigDecimal getActualManufacturedAugMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedAugMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Aug.
		@param ActualManufacturedAugUOM Manufactured-Aug	  */
	public void setActualManufacturedAugUOM (BigDecimal ActualManufacturedAugUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedAugUOM, ActualManufacturedAugUOM);
	}

	/** Get Manufactured-Aug.
		@return Manufactured-Aug	  */
	public BigDecimal getActualManufacturedAugUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedAugUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Dec MT.
		@param ActualManufacturedDecMT Manufactured-Dec MT	  */
	public void setActualManufacturedDecMT (BigDecimal ActualManufacturedDecMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedDecMT, ActualManufacturedDecMT);
	}

	/** Get Manufactured-Dec MT.
		@return Manufactured-Dec MT	  */
	public BigDecimal getActualManufacturedDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Dec.
		@param ActualManufacturedDecUOM Manufactured-Dec	  */
	public void setActualManufacturedDecUOM (BigDecimal ActualManufacturedDecUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedDecUOM, ActualManufacturedDecUOM);
	}

	/** Get Manufactured-Dec.
		@return Manufactured-Dec	  */
	public BigDecimal getActualManufacturedDecUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedDecUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Feb MT.
		@param ActualManufacturedFebMT Manufactured-Feb MT	  */
	public void setActualManufacturedFebMT (BigDecimal ActualManufacturedFebMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedFebMT, ActualManufacturedFebMT);
	}

	/** Get Manufactured-Feb MT.
		@return Manufactured-Feb MT	  */
	public BigDecimal getActualManufacturedFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Feb.
		@param ActualManufacturedFebUOM Manufactured-Feb	  */
	public void setActualManufacturedFebUOM (BigDecimal ActualManufacturedFebUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedFebUOM, ActualManufacturedFebUOM);
	}

	/** Get Manufactured-Feb.
		@return Manufactured-Feb	  */
	public BigDecimal getActualManufacturedFebUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedFebUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jan MT.
		@param ActualManufacturedJanMT Manufactured-Jan MT	  */
	public void setActualManufacturedJanMT (BigDecimal ActualManufacturedJanMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedJanMT, ActualManufacturedJanMT);
	}

	/** Get Manufactured-Jan MT.
		@return Manufactured-Jan MT	  */
	public BigDecimal getActualManufacturedJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jan.
		@param ActualManufacturedJanUOM Manufactured-Jan	  */
	public void setActualManufacturedJanUOM (BigDecimal ActualManufacturedJanUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedJanUOM, ActualManufacturedJanUOM);
	}

	/** Get Manufactured-Jan.
		@return Manufactured-Jan	  */
	public BigDecimal getActualManufacturedJanUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJanUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jul MT.
		@param ActualManufacturedJulMT Manufactured-Jul MT	  */
	public void setActualManufacturedJulMT (BigDecimal ActualManufacturedJulMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedJulMT, ActualManufacturedJulMT);
	}

	/** Get Manufactured-Jul MT.
		@return Manufactured-Jul MT	  */
	public BigDecimal getActualManufacturedJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jul.
		@param ActualManufacturedJulUOM Manufactured-Jul	  */
	public void setActualManufacturedJulUOM (BigDecimal ActualManufacturedJulUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedJulUOM, ActualManufacturedJulUOM);
	}

	/** Get Manufactured-Jul.
		@return Manufactured-Jul	  */
	public BigDecimal getActualManufacturedJulUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJulUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jun MT.
		@param ActualManufacturedJunMT Manufactured-Jun MT	  */
	public void setActualManufacturedJunMT (BigDecimal ActualManufacturedJunMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedJunMT, ActualManufacturedJunMT);
	}

	/** Get Manufactured-Jun MT.
		@return Manufactured-Jun MT	  */
	public BigDecimal getActualManufacturedJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Jun.
		@param ActualManufacturedJunUOM Manufactured-Jun	  */
	public void setActualManufacturedJunUOM (BigDecimal ActualManufacturedJunUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedJunUOM, ActualManufacturedJunUOM);
	}

	/** Get Manufactured-Jun.
		@return Manufactured-Jun	  */
	public BigDecimal getActualManufacturedJunUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedJunUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Mar MT.
		@param ActualManufacturedMarMT Manufactured-Mar MT	  */
	public void setActualManufacturedMarMT (BigDecimal ActualManufacturedMarMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedMarMT, ActualManufacturedMarMT);
	}

	/** Get Manufactured-Mar MT.
		@return Manufactured-Mar MT	  */
	public BigDecimal getActualManufacturedMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Mar.
		@param ActualManufacturedMarUOM Manufactured-Mar	  */
	public void setActualManufacturedMarUOM (BigDecimal ActualManufacturedMarUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedMarUOM, ActualManufacturedMarUOM);
	}

	/** Get Manufactured-Mar.
		@return Manufactured-Mar	  */
	public BigDecimal getActualManufacturedMarUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedMarUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-May MT.
		@param ActualManufacturedMayMT Manufactured-May MT	  */
	public void setActualManufacturedMayMT (BigDecimal ActualManufacturedMayMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedMayMT, ActualManufacturedMayMT);
	}

	/** Get Manufactured-May MT.
		@return Manufactured-May MT	  */
	public BigDecimal getActualManufacturedMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-May.
		@param ActualManufacturedMayUOM Manufactured-May	  */
	public void setActualManufacturedMayUOM (BigDecimal ActualManufacturedMayUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedMayUOM, ActualManufacturedMayUOM);
	}

	/** Get Manufactured-May.
		@return Manufactured-May	  */
	public BigDecimal getActualManufacturedMayUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedMayUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Nov MT.
		@param ActualManufacturedNovMT Manufactured-Nov MT	  */
	public void setActualManufacturedNovMT (BigDecimal ActualManufacturedNovMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedNovMT, ActualManufacturedNovMT);
	}

	/** Get Manufactured-Nov MT.
		@return Manufactured-Nov MT	  */
	public BigDecimal getActualManufacturedNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Nov.
		@param ActualManufacturedNovUOM Manufactured-Nov	  */
	public void setActualManufacturedNovUOM (BigDecimal ActualManufacturedNovUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedNovUOM, ActualManufacturedNovUOM);
	}

	/** Get Manufactured-Nov.
		@return Manufactured-Nov	  */
	public BigDecimal getActualManufacturedNovUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedNovUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Oct MT.
		@param ActualManufacturedOctMT Manufactured-Oct MT	  */
	public void setActualManufacturedOctMT (BigDecimal ActualManufacturedOctMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedOctMT, ActualManufacturedOctMT);
	}

	/** Get Manufactured-Oct MT.
		@return Manufactured-Oct MT	  */
	public BigDecimal getActualManufacturedOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Oct.
		@param ActualManufacturedOctUOM Manufactured-Oct	  */
	public void setActualManufacturedOctUOM (BigDecimal ActualManufacturedOctUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedOctUOM, ActualManufacturedOctUOM);
	}

	/** Get Manufactured-Oct.
		@return Manufactured-Oct	  */
	public BigDecimal getActualManufacturedOctUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedOctUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Sep MT.
		@param ActualManufacturedSepMT Manufactured-Sep MT	  */
	public void setActualManufacturedSepMT (BigDecimal ActualManufacturedSepMT)
	{
		set_Value (COLUMNNAME_ActualManufacturedSepMT, ActualManufacturedSepMT);
	}

	/** Get Manufactured-Sep MT.
		@return Manufactured-Sep MT	  */
	public BigDecimal getActualManufacturedSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured-Sep.
		@param ActualManufacturedSepUOM Manufactured-Sep	  */
	public void setActualManufacturedSepUOM (BigDecimal ActualManufacturedSepUOM)
	{
		set_Value (COLUMNNAME_ActualManufacturedSepUOM, ActualManufacturedSepUOM);
	}

	/** Get Manufactured-Sep.
		@return Manufactured-Sep	  */
	public BigDecimal getActualManufacturedSepUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualManufacturedSepUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Manufactured.
		@param ActualProduced 
		Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced)
	{
		set_ValueNoCheck (COLUMNNAME_ActualProduced, ActualProduced);
	}

	/** Get Total Manufactured.
		@return Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualProduced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set T. Manufactured MT.
		@param ActualProducedMT T. Manufactured MT	  */
	public void setActualProducedMT (BigDecimal ActualProducedMT)
	{
		set_ValueNoCheck (COLUMNNAME_ActualProducedMT, ActualProducedMT);
	}

	/** Get T. Manufactured MT.
		@return T. Manufactured MT	  */
	public BigDecimal getActualProducedMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualProducedMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Apr MT.
		@param ActualScheduledAprMT Scheduled-Apr MT	  */
	public void setActualScheduledAprMT (BigDecimal ActualScheduledAprMT)
	{
		set_Value (COLUMNNAME_ActualScheduledAprMT, ActualScheduledAprMT);
	}

	/** Get Scheduled-Apr MT.
		@return Scheduled-Apr MT	  */
	public BigDecimal getActualScheduledAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Apr.
		@param ActualScheduledAprUOM Scheduled-Apr	  */
	public void setActualScheduledAprUOM (BigDecimal ActualScheduledAprUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledAprUOM, ActualScheduledAprUOM);
	}

	/** Get Scheduled-Apr.
		@return Scheduled-Apr	  */
	public BigDecimal getActualScheduledAprUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledAprUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Aug MT.
		@param ActualScheduledAugMT Scheduled-Aug MT	  */
	public void setActualScheduledAugMT (BigDecimal ActualScheduledAugMT)
	{
		set_Value (COLUMNNAME_ActualScheduledAugMT, ActualScheduledAugMT);
	}

	/** Get Scheduled-Aug MT.
		@return Scheduled-Aug MT	  */
	public BigDecimal getActualScheduledAugMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledAugMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Aug.
		@param ActualScheduledAugUOM Scheduled-Aug	  */
	public void setActualScheduledAugUOM (BigDecimal ActualScheduledAugUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledAugUOM, ActualScheduledAugUOM);
	}

	/** Get Scheduled-Aug.
		@return Scheduled-Aug	  */
	public BigDecimal getActualScheduledAugUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledAugUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Dec MT.
		@param ActualScheduledDecMT Scheduled-Dec MT	  */
	public void setActualScheduledDecMT (BigDecimal ActualScheduledDecMT)
	{
		set_Value (COLUMNNAME_ActualScheduledDecMT, ActualScheduledDecMT);
	}

	/** Get Scheduled-Dec MT.
		@return Scheduled-Dec MT	  */
	public BigDecimal getActualScheduledDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Dec.
		@param ActualScheduledDecUOM Scheduled-Dec	  */
	public void setActualScheduledDecUOM (BigDecimal ActualScheduledDecUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledDecUOM, ActualScheduledDecUOM);
	}

	/** Get Scheduled-Dec.
		@return Scheduled-Dec	  */
	public BigDecimal getActualScheduledDecUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledDecUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Feb MT.
		@param ActualScheduledFebMT Scheduled-Feb MT	  */
	public void setActualScheduledFebMT (BigDecimal ActualScheduledFebMT)
	{
		set_Value (COLUMNNAME_ActualScheduledFebMT, ActualScheduledFebMT);
	}

	/** Get Scheduled-Feb MT.
		@return Scheduled-Feb MT	  */
	public BigDecimal getActualScheduledFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Feb.
		@param ActualScheduledFebUOM Scheduled-Feb	  */
	public void setActualScheduledFebUOM (BigDecimal ActualScheduledFebUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledFebUOM, ActualScheduledFebUOM);
	}

	/** Get Scheduled-Feb.
		@return Scheduled-Feb	  */
	public BigDecimal getActualScheduledFebUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledFebUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jan MT.
		@param ActualScheduledJanMT Scheduled-Jan MT	  */
	public void setActualScheduledJanMT (BigDecimal ActualScheduledJanMT)
	{
		set_Value (COLUMNNAME_ActualScheduledJanMT, ActualScheduledJanMT);
	}

	/** Get Scheduled-Jan MT.
		@return Scheduled-Jan MT	  */
	public BigDecimal getActualScheduledJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jan.
		@param ActualScheduledJanUOM Scheduled-Jan	  */
	public void setActualScheduledJanUOM (BigDecimal ActualScheduledJanUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledJanUOM, ActualScheduledJanUOM);
	}

	/** Get Scheduled-Jan.
		@return Scheduled-Jan	  */
	public BigDecimal getActualScheduledJanUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJanUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jul MT.
		@param ActualScheduledJulMT Scheduled-Jul MT	  */
	public void setActualScheduledJulMT (BigDecimal ActualScheduledJulMT)
	{
		set_Value (COLUMNNAME_ActualScheduledJulMT, ActualScheduledJulMT);
	}

	/** Get Scheduled-Jul MT.
		@return Scheduled-Jul MT	  */
	public BigDecimal getActualScheduledJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jul.
		@param ActualScheduledJulUOM Scheduled-Jul	  */
	public void setActualScheduledJulUOM (BigDecimal ActualScheduledJulUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledJulUOM, ActualScheduledJulUOM);
	}

	/** Get Scheduled-Jul.
		@return Scheduled-Jul	  */
	public BigDecimal getActualScheduledJulUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJulUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jun MT.
		@param ActualScheduledJunMT Scheduled-Jun MT	  */
	public void setActualScheduledJunMT (BigDecimal ActualScheduledJunMT)
	{
		set_Value (COLUMNNAME_ActualScheduledJunMT, ActualScheduledJunMT);
	}

	/** Get Scheduled-Jun MT.
		@return Scheduled-Jun MT	  */
	public BigDecimal getActualScheduledJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Jun.
		@param ActualScheduledJunUOM Scheduled-Jun	  */
	public void setActualScheduledJunUOM (BigDecimal ActualScheduledJunUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledJunUOM, ActualScheduledJunUOM);
	}

	/** Get Scheduled-Jun.
		@return Scheduled-Jun	  */
	public BigDecimal getActualScheduledJunUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledJunUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Mar MT.
		@param ActualScheduledMarMT Scheduled-Mar MT	  */
	public void setActualScheduledMarMT (BigDecimal ActualScheduledMarMT)
	{
		set_Value (COLUMNNAME_ActualScheduledMarMT, ActualScheduledMarMT);
	}

	/** Get Scheduled-Mar MT.
		@return Scheduled-Mar MT	  */
	public BigDecimal getActualScheduledMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Mar.
		@param ActualScheduledMarUOM Scheduled-Mar	  */
	public void setActualScheduledMarUOM (BigDecimal ActualScheduledMarUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledMarUOM, ActualScheduledMarUOM);
	}

	/** Get Scheduled-Mar.
		@return Scheduled-Mar	  */
	public BigDecimal getActualScheduledMarUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMarUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-May MT.
		@param ActualScheduledMayMT Scheduled-May MT	  */
	public void setActualScheduledMayMT (BigDecimal ActualScheduledMayMT)
	{
		set_Value (COLUMNNAME_ActualScheduledMayMT, ActualScheduledMayMT);
	}

	/** Get Scheduled-May MT.
		@return Scheduled-May MT	  */
	public BigDecimal getActualScheduledMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-May.
		@param ActualScheduledMayUOM Scheduled-May	  */
	public void setActualScheduledMayUOM (BigDecimal ActualScheduledMayUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledMayUOM, ActualScheduledMayUOM);
	}

	/** Get Scheduled-May.
		@return Scheduled-May	  */
	public BigDecimal getActualScheduledMayUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMayUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set T. Scheduled (MT).
		@param ActualScheduledMT 
		Total Actual Scheduled (MT)
	  */
	public void setActualScheduledMT (BigDecimal ActualScheduledMT)
	{
		set_ValueNoCheck (COLUMNNAME_ActualScheduledMT, ActualScheduledMT);
	}

	/** Get T. Scheduled (MT).
		@return Total Actual Scheduled (MT)
	  */
	public BigDecimal getActualScheduledMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Nov MT.
		@param ActualScheduledNovMT Scheduled-Nov MT	  */
	public void setActualScheduledNovMT (BigDecimal ActualScheduledNovMT)
	{
		set_Value (COLUMNNAME_ActualScheduledNovMT, ActualScheduledNovMT);
	}

	/** Get Scheduled-Nov MT.
		@return Scheduled-Nov MT	  */
	public BigDecimal getActualScheduledNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Nov.
		@param ActualScheduledNovUOM Scheduled-Nov	  */
	public void setActualScheduledNovUOM (BigDecimal ActualScheduledNovUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledNovUOM, ActualScheduledNovUOM);
	}

	/** Get Scheduled-Nov.
		@return Scheduled-Nov	  */
	public BigDecimal getActualScheduledNovUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledNovUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Oct MT.
		@param ActualScheduledOctMT Scheduled-Oct MT	  */
	public void setActualScheduledOctMT (BigDecimal ActualScheduledOctMT)
	{
		set_Value (COLUMNNAME_ActualScheduledOctMT, ActualScheduledOctMT);
	}

	/** Get Scheduled-Oct MT.
		@return Scheduled-Oct MT	  */
	public BigDecimal getActualScheduledOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Oct.
		@param ActualScheduledOctUOM Scheduled-Oct	  */
	public void setActualScheduledOctUOM (BigDecimal ActualScheduledOctUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledOctUOM, ActualScheduledOctUOM);
	}

	/** Get Scheduled-Oct.
		@return Scheduled-Oct	  */
	public BigDecimal getActualScheduledOctUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledOctUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Sep MT.
		@param ActualScheduledSepMT Scheduled-Sep MT	  */
	public void setActualScheduledSepMT (BigDecimal ActualScheduledSepMT)
	{
		set_Value (COLUMNNAME_ActualScheduledSepMT, ActualScheduledSepMT);
	}

	/** Get Scheduled-Sep MT.
		@return Scheduled-Sep MT	  */
	public BigDecimal getActualScheduledSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled-Sep.
		@param ActualScheduledSepUOM Scheduled-Sep	  */
	public void setActualScheduledSepUOM (BigDecimal ActualScheduledSepUOM)
	{
		set_Value (COLUMNNAME_ActualScheduledSepUOM, ActualScheduledSepUOM);
	}

	/** Get Scheduled-Sep.
		@return Scheduled-Sep	  */
	public BigDecimal getActualScheduledSepUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledSepUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Scheduled.
		@param ActualScheduledUOM 
		Total Actual Scheduled (UOM)
	  */
	public void setActualScheduledUOM (BigDecimal ActualScheduledUOM)
	{
		set_ValueNoCheck (COLUMNNAME_ActualScheduledUOM, ActualScheduledUOM);
	}

	/** Get Total Scheduled.
		@return Total Actual Scheduled (UOM)
	  */
	public BigDecimal getActualScheduledUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualScheduledUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Apr.
		@param ActualToOrderAprMT 
		Actual To Order Apr (MT)
	  */
	public void setActualToOrderAprMT (BigDecimal ActualToOrderAprMT)
	{
		set_Value (COLUMNNAME_ActualToOrderAprMT, ActualToOrderAprMT);
	}

	/** Get Actual Order-Apr.
		@return Actual To Order Apr (MT)
	  */
	public BigDecimal getActualToOrderAprMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderAprMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Apr.
		@param ActualToOrderAprUOM 
		Actual To Order Apr (UOM)
	  */
	public void setActualToOrderAprUOM (BigDecimal ActualToOrderAprUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderAprUOM, ActualToOrderAprUOM);
	}

	/** Get Actual Order-Apr.
		@return Actual To Order Apr (UOM)
	  */
	public BigDecimal getActualToOrderAprUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderAprUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Aug.
		@param ActualToOrderAugMT 
		Actual To Order Aug (MT)
	  */
	public void setActualToOrderAugMT (BigDecimal ActualToOrderAugMT)
	{
		set_Value (COLUMNNAME_ActualToOrderAugMT, ActualToOrderAugMT);
	}

	/** Get Actual Order-Aug.
		@return Actual To Order Aug (MT)
	  */
	public BigDecimal getActualToOrderAugMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderAugMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Aug.
		@param ActualToOrderAugUOM 
		Actual To Order Aug (UOM)
	  */
	public void setActualToOrderAugUOM (BigDecimal ActualToOrderAugUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderAugUOM, ActualToOrderAugUOM);
	}

	/** Get Actual Order-Aug.
		@return Actual To Order Aug (UOM)
	  */
	public BigDecimal getActualToOrderAugUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderAugUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Dec.
		@param ActualToOrderDecMT 
		Actual To Order Dec (MT)
	  */
	public void setActualToOrderDecMT (BigDecimal ActualToOrderDecMT)
	{
		set_Value (COLUMNNAME_ActualToOrderDecMT, ActualToOrderDecMT);
	}

	/** Get Actual Order-Dec.
		@return Actual To Order Dec (MT)
	  */
	public BigDecimal getActualToOrderDecMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderDecMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Dec.
		@param ActualToOrderDecUOM 
		Actual To Order Dec (UOM)
	  */
	public void setActualToOrderDecUOM (BigDecimal ActualToOrderDecUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderDecUOM, ActualToOrderDecUOM);
	}

	/** Get Actual Order-Dec.
		@return Actual To Order Dec (UOM)
	  */
	public BigDecimal getActualToOrderDecUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderDecUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Feb.
		@param ActualToOrderFebMT 
		Actual To Order Feb (MT)
	  */
	public void setActualToOrderFebMT (BigDecimal ActualToOrderFebMT)
	{
		set_Value (COLUMNNAME_ActualToOrderFebMT, ActualToOrderFebMT);
	}

	/** Get Actual Order-Feb.
		@return Actual To Order Feb (MT)
	  */
	public BigDecimal getActualToOrderFebMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderFebMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Feb.
		@param ActualToOrderFebUOM 
		Actual To Order Feb (UOM)
	  */
	public void setActualToOrderFebUOM (BigDecimal ActualToOrderFebUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderFebUOM, ActualToOrderFebUOM);
	}

	/** Get Actual Order-Feb.
		@return Actual To Order Feb (UOM)
	  */
	public BigDecimal getActualToOrderFebUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderFebUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jan.
		@param ActualToOrderJanMT 
		Actual To Order Jan (MT)
	  */
	public void setActualToOrderJanMT (BigDecimal ActualToOrderJanMT)
	{
		set_Value (COLUMNNAME_ActualToOrderJanMT, ActualToOrderJanMT);
	}

	/** Get Actual Order-Jan.
		@return Actual To Order Jan (MT)
	  */
	public BigDecimal getActualToOrderJanMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJanMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jan.
		@param ActualToOrderJanUOM 
		Actual To Order Jan (UOM)
	  */
	public void setActualToOrderJanUOM (BigDecimal ActualToOrderJanUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderJanUOM, ActualToOrderJanUOM);
	}

	/** Get Actual Order-Jan.
		@return Actual To Order Jan (UOM)
	  */
	public BigDecimal getActualToOrderJanUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJanUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jul.
		@param ActualToOrderJulMT 
		Actual To Order Jul (MT)
	  */
	public void setActualToOrderJulMT (BigDecimal ActualToOrderJulMT)
	{
		set_Value (COLUMNNAME_ActualToOrderJulMT, ActualToOrderJulMT);
	}

	/** Get Actual Order-Jul.
		@return Actual To Order Jul (MT)
	  */
	public BigDecimal getActualToOrderJulMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJulMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jul.
		@param ActualToOrderJulUOM 
		Actual To Order Jul (UOM)
	  */
	public void setActualToOrderJulUOM (BigDecimal ActualToOrderJulUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderJulUOM, ActualToOrderJulUOM);
	}

	/** Get Actual Order-Jul.
		@return Actual To Order Jul (UOM)
	  */
	public BigDecimal getActualToOrderJulUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJulUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jun.
		@param ActualToOrderJunMT 
		Actual To Order Jun (MT)
	  */
	public void setActualToOrderJunMT (BigDecimal ActualToOrderJunMT)
	{
		set_Value (COLUMNNAME_ActualToOrderJunMT, ActualToOrderJunMT);
	}

	/** Get Actual Order-Jun.
		@return Actual To Order Jun (MT)
	  */
	public BigDecimal getActualToOrderJunMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJunMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Jun.
		@param ActualToOrderJunUOM 
		Actual To Order Jun (UOM)
	  */
	public void setActualToOrderJunUOM (BigDecimal ActualToOrderJunUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderJunUOM, ActualToOrderJunUOM);
	}

	/** Get Actual Order-Jun.
		@return Actual To Order Jun (UOM)
	  */
	public BigDecimal getActualToOrderJunUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderJunUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Mar.
		@param ActualToOrderMarMT 
		Actual To Order Mar (MT)
	  */
	public void setActualToOrderMarMT (BigDecimal ActualToOrderMarMT)
	{
		set_Value (COLUMNNAME_ActualToOrderMarMT, ActualToOrderMarMT);
	}

	/** Get Actual Order-Mar.
		@return Actual To Order Mar (MT)
	  */
	public BigDecimal getActualToOrderMarMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderMarMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Mar.
		@param ActualToOrderMarUOM 
		Actual To Order Mar (UOM)
	  */
	public void setActualToOrderMarUOM (BigDecimal ActualToOrderMarUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderMarUOM, ActualToOrderMarUOM);
	}

	/** Get Actual Order-Mar.
		@return Actual To Order Mar (UOM)
	  */
	public BigDecimal getActualToOrderMarUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderMarUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-May.
		@param ActualToOrderMayMT 
		Actual To Order May (MT)
	  */
	public void setActualToOrderMayMT (BigDecimal ActualToOrderMayMT)
	{
		set_Value (COLUMNNAME_ActualToOrderMayMT, ActualToOrderMayMT);
	}

	/** Get Actual Order-May.
		@return Actual To Order May (MT)
	  */
	public BigDecimal getActualToOrderMayMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderMayMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-May.
		@param ActualToOrderMayUOM 
		Actual To Order May (UOM)
	  */
	public void setActualToOrderMayUOM (BigDecimal ActualToOrderMayUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderMayUOM, ActualToOrderMayUOM);
	}

	/** Get Actual Order-May.
		@return Actual To Order May (UOM)
	  */
	public BigDecimal getActualToOrderMayUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderMayUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Nov.
		@param ActualToOrderNovMT 
		Actual To Order Nov (MT)
	  */
	public void setActualToOrderNovMT (BigDecimal ActualToOrderNovMT)
	{
		set_Value (COLUMNNAME_ActualToOrderNovMT, ActualToOrderNovMT);
	}

	/** Get Actual Order-Nov.
		@return Actual To Order Nov (MT)
	  */
	public BigDecimal getActualToOrderNovMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderNovMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Nov.
		@param ActualToOrderNovUOM 
		Actual To Order Nov (UOM)
	  */
	public void setActualToOrderNovUOM (BigDecimal ActualToOrderNovUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderNovUOM, ActualToOrderNovUOM);
	}

	/** Get Actual Order-Nov.
		@return Actual To Order Nov (UOM)
	  */
	public BigDecimal getActualToOrderNovUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderNovUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Oct.
		@param ActualToOrderOctMT 
		Actual To Order Oct (MT)
	  */
	public void setActualToOrderOctMT (BigDecimal ActualToOrderOctMT)
	{
		set_Value (COLUMNNAME_ActualToOrderOctMT, ActualToOrderOctMT);
	}

	/** Get Actual Order-Oct.
		@return Actual To Order Oct (MT)
	  */
	public BigDecimal getActualToOrderOctMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderOctMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Oct.
		@param ActualToOrderOctUOM 
		Actual To Order Oct (UOM)
	  */
	public void setActualToOrderOctUOM (BigDecimal ActualToOrderOctUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderOctUOM, ActualToOrderOctUOM);
	}

	/** Get Actual Order-Oct.
		@return Actual To Order Oct (UOM)
	  */
	public BigDecimal getActualToOrderOctUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderOctUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Sep.
		@param ActualToOrderSepMT 
		Actual To Order Sep (MT)
	  */
	public void setActualToOrderSepMT (BigDecimal ActualToOrderSepMT)
	{
		set_Value (COLUMNNAME_ActualToOrderSepMT, ActualToOrderSepMT);
	}

	/** Get Actual Order-Sep.
		@return Actual To Order Sep (MT)
	  */
	public BigDecimal getActualToOrderSepMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderSepMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Order-Sep.
		@param ActualToOrderSepUOM 
		Actual To Order Sep (UOM)
	  */
	public void setActualToOrderSepUOM (BigDecimal ActualToOrderSepUOM)
	{
		set_Value (COLUMNNAME_ActualToOrderSepUOM, ActualToOrderSepUOM);
	}

	/** Get Actual Order-Sep.
		@return Actual To Order Sep (UOM)
	  */
	public BigDecimal getActualToOrderSepUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualToOrderSepUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Incubation Days.
		@param IncubationDays 
		Incubation period of product in days
	  */
	public void setIncubationDays (int IncubationDays)
	{
		set_Value (COLUMNNAME_IncubationDays, Integer.valueOf(IncubationDays));
	}

	/** Get Incubation Days.
		@return Incubation period of product in days
	  */
	public int getIncubationDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncubationDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Initial Stock On Hand.
		@param InitialProjectedStock_OnHand Initial Stock On Hand	  */
	public void setInitialProjectedStock_OnHand (BigDecimal InitialProjectedStock_OnHand)
	{
		set_Value (COLUMNNAME_InitialProjectedStock_OnHand, InitialProjectedStock_OnHand);
	}

	/** Get Initial Stock On Hand.
		@return Initial Stock On Hand	  */
	public BigDecimal getInitialProjectedStock_OnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InitialProjectedStock_OnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Generated.
		@param IsGenerate Generated	  */
	public void setIsGenerate (boolean IsGenerate)
	{
		set_Value (COLUMNNAME_IsGenerate, Boolean.valueOf(IsGenerate));
	}

	/** Get Generated.
		@return Generated	  */
	public boolean isGenerate () 
	{
		Object oo = get_Value(COLUMNNAME_IsGenerate);
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

	/** Set F. Demand-Aug.
		@param QtyMT_Agt 
		Quantity In Matric Ton On August
	  */
	public void setQtyMT_Agt (BigDecimal QtyMT_Agt)
	{
		set_Value (COLUMNNAME_QtyMT_Agt, QtyMT_Agt);
	}

	/** Get F. Demand-Aug.
		@return Quantity In Matric Ton On August
	  */
	public BigDecimal getQtyMT_Agt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Agt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Apr.
		@param QtyMT_Apr 
		Quantity In Matric Ton On April
	  */
	public void setQtyMT_Apr (BigDecimal QtyMT_Apr)
	{
		set_Value (COLUMNNAME_QtyMT_Apr, QtyMT_Apr);
	}

	/** Get F. Demand-Apr.
		@return Quantity In Matric Ton On April
	  */
	public BigDecimal getQtyMT_Apr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Apr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Dec.
		@param QtyMT_Dec 
		Quantity In Matric Ton On December
	  */
	public void setQtyMT_Dec (BigDecimal QtyMT_Dec)
	{
		set_Value (COLUMNNAME_QtyMT_Dec, QtyMT_Dec);
	}

	/** Get F. Demand-Dec.
		@return Quantity In Matric Ton On December
	  */
	public BigDecimal getQtyMT_Dec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Dec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Feb.
		@param QtyMT_Feb 
		Quantity In Matric Ton On February
	  */
	public void setQtyMT_Feb (BigDecimal QtyMT_Feb)
	{
		set_Value (COLUMNNAME_QtyMT_Feb, QtyMT_Feb);
	}

	/** Get F. Demand-Feb.
		@return Quantity In Matric Ton On February
	  */
	public BigDecimal getQtyMT_Feb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Feb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jan.
		@param QtyMT_Jan 
		Quantity In Matric Ton On January
	  */
	public void setQtyMT_Jan (BigDecimal QtyMT_Jan)
	{
		set_Value (COLUMNNAME_QtyMT_Jan, QtyMT_Jan);
	}

	/** Get F. Demand-Jan.
		@return Quantity In Matric Ton On January
	  */
	public BigDecimal getQtyMT_Jan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Jan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jul.
		@param QtyMT_Jul 
		Quantity In Matric Ton On July
	  */
	public void setQtyMT_Jul (BigDecimal QtyMT_Jul)
	{
		set_Value (COLUMNNAME_QtyMT_Jul, QtyMT_Jul);
	}

	/** Get F. Demand-Jul.
		@return Quantity In Matric Ton On July
	  */
	public BigDecimal getQtyMT_Jul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Jul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jun.
		@param QtyMT_Jun 
		Quantity In Matric Ton On Juny
	  */
	public void setQtyMT_Jun (BigDecimal QtyMT_Jun)
	{
		set_Value (COLUMNNAME_QtyMT_Jun, QtyMT_Jun);
	}

	/** Get F. Demand-Jun.
		@return Quantity In Matric Ton On Juny
	  */
	public BigDecimal getQtyMT_Jun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Jun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Mar.
		@param QtyMT_Mar 
		Quantity In Matric Ton On March
	  */
	public void setQtyMT_Mar (BigDecimal QtyMT_Mar)
	{
		set_Value (COLUMNNAME_QtyMT_Mar, QtyMT_Mar);
	}

	/** Get F. Demand-Mar.
		@return Quantity In Matric Ton On March
	  */
	public BigDecimal getQtyMT_Mar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Mar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-May.
		@param QtyMT_May 
		Quantity In Matric Ton On May
	  */
	public void setQtyMT_May (BigDecimal QtyMT_May)
	{
		set_Value (COLUMNNAME_QtyMT_May, QtyMT_May);
	}

	/** Get F. Demand-May.
		@return Quantity In Matric Ton On May
	  */
	public BigDecimal getQtyMT_May () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_May);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Nov.
		@param QtyMT_Nov 
		Quantity In Matric Ton On November
	  */
	public void setQtyMT_Nov (BigDecimal QtyMT_Nov)
	{
		set_Value (COLUMNNAME_QtyMT_Nov, QtyMT_Nov);
	}

	/** Get F. Demand-Nov.
		@return Quantity In Matric Ton On November
	  */
	public BigDecimal getQtyMT_Nov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Nov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Oct.
		@param QtyMT_Oct 
		Quantity In Matric Ton On October
	  */
	public void setQtyMT_Oct (BigDecimal QtyMT_Oct)
	{
		set_Value (COLUMNNAME_QtyMT_Oct, QtyMT_Oct);
	}

	/** Get F. Demand-Oct.
		@return Quantity In Matric Ton On October
	  */
	public BigDecimal getQtyMT_Oct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Oct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Sep.
		@param QtyMT_Sep 
		Quantity In Matric Ton On September
	  */
	public void setQtyMT_Sep (BigDecimal QtyMT_Sep)
	{
		set_Value (COLUMNNAME_QtyMT_Sep, QtyMT_Sep);
	}

	/** Get F. Demand-Sep.
		@return Quantity In Matric Ton On September
	  */
	public BigDecimal getQtyMT_Sep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT_Sep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set On Hand Quantity.
		@param QtyOnHand 
		Actual On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get On Hand Quantity.
		@return Actual On Hand Quantity
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Aug.
		@param QtyUOM_Agt F. Demand-Aug	  */
	public void setQtyUOM_Agt (BigDecimal QtyUOM_Agt)
	{
		set_Value (COLUMNNAME_QtyUOM_Agt, QtyUOM_Agt);
	}

	/** Get F. Demand-Aug.
		@return F. Demand-Aug	  */
	public BigDecimal getQtyUOM_Agt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Agt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Apr.
		@param QtyUOM_Apr F. Demand-Apr	  */
	public void setQtyUOM_Apr (BigDecimal QtyUOM_Apr)
	{
		set_Value (COLUMNNAME_QtyUOM_Apr, QtyUOM_Apr);
	}

	/** Get F. Demand-Apr.
		@return F. Demand-Apr	  */
	public BigDecimal getQtyUOM_Apr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Apr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Dec.
		@param QtyUOM_Dec F. Demand-Dec	  */
	public void setQtyUOM_Dec (BigDecimal QtyUOM_Dec)
	{
		set_Value (COLUMNNAME_QtyUOM_Dec, QtyUOM_Dec);
	}

	/** Get F. Demand-Dec.
		@return F. Demand-Dec	  */
	public BigDecimal getQtyUOM_Dec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Dec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Feb.
		@param QtyUOM_Feb F. Demand-Feb	  */
	public void setQtyUOM_Feb (BigDecimal QtyUOM_Feb)
	{
		set_Value (COLUMNNAME_QtyUOM_Feb, QtyUOM_Feb);
	}

	/** Get F. Demand-Feb.
		@return F. Demand-Feb	  */
	public BigDecimal getQtyUOM_Feb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Feb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jan.
		@param QtyUOM_Jan F. Demand-Jan	  */
	public void setQtyUOM_Jan (BigDecimal QtyUOM_Jan)
	{
		set_Value (COLUMNNAME_QtyUOM_Jan, QtyUOM_Jan);
	}

	/** Get F. Demand-Jan.
		@return F. Demand-Jan	  */
	public BigDecimal getQtyUOM_Jan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Jan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jul.
		@param QtyUOM_Jul F. Demand-Jul	  */
	public void setQtyUOM_Jul (BigDecimal QtyUOM_Jul)
	{
		set_Value (COLUMNNAME_QtyUOM_Jul, QtyUOM_Jul);
	}

	/** Get F. Demand-Jul.
		@return F. Demand-Jul	  */
	public BigDecimal getQtyUOM_Jul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Jul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Jun.
		@param QtyUOM_Jun F. Demand-Jun	  */
	public void setQtyUOM_Jun (BigDecimal QtyUOM_Jun)
	{
		set_Value (COLUMNNAME_QtyUOM_Jun, QtyUOM_Jun);
	}

	/** Get F. Demand-Jun.
		@return F. Demand-Jun	  */
	public BigDecimal getQtyUOM_Jun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Jun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Mar.
		@param QtyUOM_Mar F. Demand-Mar	  */
	public void setQtyUOM_Mar (BigDecimal QtyUOM_Mar)
	{
		set_Value (COLUMNNAME_QtyUOM_Mar, QtyUOM_Mar);
	}

	/** Get F. Demand-Mar.
		@return F. Demand-Mar	  */
	public BigDecimal getQtyUOM_Mar () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Mar);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-May.
		@param QtyUOM_May F. Demand-May	  */
	public void setQtyUOM_May (BigDecimal QtyUOM_May)
	{
		set_Value (COLUMNNAME_QtyUOM_May, QtyUOM_May);
	}

	/** Get F. Demand-May.
		@return F. Demand-May	  */
	public BigDecimal getQtyUOM_May () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_May);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Nov.
		@param QtyUOM_Nov F. Demand-Nov	  */
	public void setQtyUOM_Nov (BigDecimal QtyUOM_Nov)
	{
		set_Value (COLUMNNAME_QtyUOM_Nov, QtyUOM_Nov);
	}

	/** Get F. Demand-Nov.
		@return F. Demand-Nov	  */
	public BigDecimal getQtyUOM_Nov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Nov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Oct.
		@param QtyUOM_Oct F. Demand-Oct	  */
	public void setQtyUOM_Oct (BigDecimal QtyUOM_Oct)
	{
		set_Value (COLUMNNAME_QtyUOM_Oct, QtyUOM_Oct);
	}

	/** Get F. Demand-Oct.
		@return F. Demand-Oct	  */
	public BigDecimal getQtyUOM_Oct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Oct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set F. Demand-Sep.
		@param QtyUOM_Sep F. Demand-Sep	  */
	public void setQtyUOM_Sep (BigDecimal QtyUOM_Sep)
	{
		set_Value (COLUMNNAME_QtyUOM_Sep, QtyUOM_Sep);
	}

	/** Get F. Demand-Sep.
		@return F. Demand-Sep	  */
	public BigDecimal getQtyUOM_Sep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUOM_Sep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Safety Stock Qty.
		@param SafetyStock 
		Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock)
	{
		set_ValueNoCheck (COLUMNNAME_SafetyStock, SafetyStock);
	}

	/** Get Safety Stock Qty.
		@return Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SafetyStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SOH.
		@param SOH 
		Scheduled (Stock) On Hand
	  */
	public void setSOH (BigDecimal SOH)
	{
		set_ValueNoCheck (COLUMNNAME_SOH, SOH);
	}

	/** Get SOH.
		@return Scheduled (Stock) On Hand
	  */
	public BigDecimal getSOH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SOH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set T. Actual Order (MT).
		@param TotalActualToOrderMT 
		Total Actual To Order 
	  */
	public void setTotalActualToOrderMT (BigDecimal TotalActualToOrderMT)
	{
		set_Value (COLUMNNAME_TotalActualToOrderMT, TotalActualToOrderMT);
	}

	/** Get T. Actual Order (MT).
		@return Total Actual To Order 
	  */
	public BigDecimal getTotalActualToOrderMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalActualToOrderMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set T. Actual Order.
		@param TotalActualToOrderUOM 
		Total Actual To Order 
	  */
	public void setTotalActualToOrderUOM (BigDecimal TotalActualToOrderUOM)
	{
		set_Value (COLUMNNAME_TotalActualToOrderUOM, TotalActualToOrderUOM);
	}

	/** Get T. Actual Order.
		@return Total Actual To Order 
	  */
	public BigDecimal getTotalActualToOrderUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalActualToOrderUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total F. Demand (MT).
		@param TotalActualToStockMT Total F. Demand (MT)	  */
	public void setTotalActualToStockMT (BigDecimal TotalActualToStockMT)
	{
		set_Value (COLUMNNAME_TotalActualToStockMT, TotalActualToStockMT);
	}

	/** Get Total F. Demand (MT).
		@return Total F. Demand (MT)	  */
	public BigDecimal getTotalActualToStockMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalActualToStockMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total F. Demand.
		@param TotalActualToStockUOM Total F. Demand	  */
	public void setTotalActualToStockUOM (BigDecimal TotalActualToStockUOM)
	{
		set_Value (COLUMNNAME_TotalActualToStockUOM, TotalActualToStockUOM);
	}

	/** Get Total F. Demand.
		@return Total F. Demand	  */
	public BigDecimal getTotalActualToStockUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalActualToStockUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSHeader)MTable.get(getCtx(), com.uns.model.I_UNS_MPSHeader.Table_Name)
			.getPO(getUNS_MPSHeader_ID(), get_TrxName());	}

	/** Set MPS.
		@param UNS_MPSHeader_ID MPS	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID)
	{
		if (UNS_MPSHeader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSHeader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSHeader_ID, Integer.valueOf(UNS_MPSHeader_ID));
	}

	/** Get MPS.
		@return MPS	  */
	public int getUNS_MPSHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MPS Product.
		@param UNS_MPSProduct_ID MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID)
	{
		if (UNS_MPSProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MPSProduct_ID, Integer.valueOf(UNS_MPSProduct_ID));
	}

	/** Get MPS Product.
		@return MPS Product	  */
	public int getUNS_MPSProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MPSProduct_UU.
		@param UNS_MPSProduct_UU UNS_MPSProduct_UU	  */
	public void setUNS_MPSProduct_UU (String UNS_MPSProduct_UU)
	{
		set_Value (COLUMNNAME_UNS_MPSProduct_UU, UNS_MPSProduct_UU);
	}

	/** Get UNS_MPSProduct_UU.
		@return UNS_MPSProduct_UU	  */
	public String getUNS_MPSProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MPSProduct_UU);
	}

	/** Set Week To Be Updated.
		@param WeekToBeUpdated 
		Week To Be Updated
	  */
	public void setWeekToBeUpdated (int WeekToBeUpdated)
	{
		set_ValueNoCheck (COLUMNNAME_WeekToBeUpdated, Integer.valueOf(WeekToBeUpdated));
	}

	/** Get Week To Be Updated.
		@return Week To Be Updated
	  */
	public int getWeekToBeUpdated () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeekToBeUpdated);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}