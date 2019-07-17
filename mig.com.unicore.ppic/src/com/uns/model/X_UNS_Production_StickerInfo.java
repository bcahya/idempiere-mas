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

/** Generated Model for UNS_Production_StickerInfo
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Production_StickerInfo extends PO implements I_UNS_Production_StickerInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130909L;

    /** Standard Constructor */
    public X_UNS_Production_StickerInfo (Properties ctx, int UNS_Production_StickerInfo_ID, String trxName)
    {
      super (ctx, UNS_Production_StickerInfo_ID, trxName);
      /** if (UNS_Production_StickerInfo_ID == 0)
        {
			setName (null);
			setUNS_Production_StickerInfo_ID (0);
			setUNS_ProductionSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_StickerInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_StickerInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public org.compiere.model.I_M_Product getProductSticker() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getProductSticker_ID(), get_TrxName());	}

	/** Set Sticker.
		@param ProductSticker_ID Sticker	  */
	public void setProductSticker_ID (int ProductSticker_ID)
	{
		if (ProductSticker_ID < 1) 
			set_Value (COLUMNNAME_ProductSticker_ID, null);
		else 
			set_Value (COLUMNNAME_ProductSticker_ID, Integer.valueOf(ProductSticker_ID));
	}

	/** Get Sticker.
		@return Sticker	  */
	public int getProductSticker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductSticker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sticker Info.
		@param StickerInfo Sticker Info	  */
	public void setStickerInfo (String StickerInfo)
	{
		set_Value (COLUMNNAME_StickerInfo, StickerInfo);
	}

	/** Get Sticker Info.
		@return Sticker Info	  */
	public String getStickerInfo () 
	{
		return (String)get_Value(COLUMNNAME_StickerInfo);
	}

	/** Set Sticker Remarks.
		@param StickerRemarks Sticker Remarks	  */
	public void setStickerRemarks (String StickerRemarks)
	{
		set_Value (COLUMNNAME_StickerRemarks, StickerRemarks);
	}

	/** Get Sticker Remarks.
		@return Sticker Remarks	  */
	public String getStickerRemarks () 
	{
		return (String)get_Value(COLUMNNAME_StickerRemarks);
	}

	/** Set Sticker Info.
		@param UNS_Production_StickerInfo_ID Sticker Info	  */
	public void setUNS_Production_StickerInfo_ID (int UNS_Production_StickerInfo_ID)
	{
		if (UNS_Production_StickerInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_StickerInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_StickerInfo_ID, Integer.valueOf(UNS_Production_StickerInfo_ID));
	}

	/** Get Sticker Info.
		@return Sticker Info	  */
	public int getUNS_Production_StickerInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_StickerInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uns_production_stickerinfo_uu.
		@param uns_production_stickerinfo_uu uns_production_stickerinfo_uu	  */
	public void setuns_production_stickerinfo_uu (String uns_production_stickerinfo_uu)
	{
		set_Value (COLUMNNAME_uns_production_stickerinfo_uu, uns_production_stickerinfo_uu);
	}

	/** Get uns_production_stickerinfo_uu.
		@return uns_production_stickerinfo_uu	  */
	public String getuns_production_stickerinfo_uu () 
	{
		return (String)get_Value(COLUMNNAME_uns_production_stickerinfo_uu);
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
}