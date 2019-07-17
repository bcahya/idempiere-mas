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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_ActualCostConfigLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ActualCostConfigLine extends PO implements I_UNS_ActualCostConfigLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130629L;

    /** Standard Constructor */
    public X_UNS_ActualCostConfigLine (Properties ctx, int UNS_ActualCostConfigLine_ID, String trxName)
    {
      super (ctx, UNS_ActualCostConfigLine_ID, trxName);
      /** if (UNS_ActualCostConfigLine_ID == 0)
        {
			setC_ElementValue_ID (0);
			setName (null);
			setUNS_ActualCostConfig_ID (0);
			setUNS_ActualCostConfigLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ActualCostConfigLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_ActualCostConfigLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** ProductType AD_Reference_ID=1000114 */
	public static final int PRODUCTTYPE_AD_Reference_ID=1000114;
	/** Service-Man PerHour = SERV */
	public static final String PRODUCTTYPE_Service_ManPerHour = "SERV";
	/** No Specific Consumption Type = NO */
	public static final String PRODUCTTYPE_NoSpecificConsumptionType = "NO";
	/** Work In Process = WIP */
	public static final String PRODUCTTYPE_WorkInProcess = "WIP";
	/** Finished Goods = FG */
	public static final String PRODUCTTYPE_FinishedGoods = "FG";
	/** Raw Material = RM */
	public static final String PRODUCTTYPE_RawMaterial = "RM";
	/** Utilities Consumption = UTL */
	public static final String PRODUCTTYPE_UtilitiesConsumption = "UTL";
	/** Set Product Type.
		@param ProductType 
		Type of product
	  */
	public void setProductType (String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Product Type.
		@return Type of product
	  */
	public String getProductType () 
	{
		return (String)get_Value(COLUMNNAME_ProductType);
	}

	public com.uns.model.I_UNS_ActualCostConfig getUNS_ActualCostConfig() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ActualCostConfig)MTable.get(getCtx(), com.uns.model.I_UNS_ActualCostConfig.Table_Name)
			.getPO(getUNS_ActualCostConfig_ID(), get_TrxName());	}

	/** Set Actual Cost Config.
		@param UNS_ActualCostConfig_ID Actual Cost Config	  */
	public void setUNS_ActualCostConfig_ID (int UNS_ActualCostConfig_ID)
	{
		if (UNS_ActualCostConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfig_ID, Integer.valueOf(UNS_ActualCostConfig_ID));
	}

	/** Get Actual Cost Config.
		@return Actual Cost Config	  */
	public int getUNS_ActualCostConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_ActualCostConfigItem getUNS_ActualCostConfigItem() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ActualCostConfigItem)MTable.get(getCtx(), com.uns.model.I_UNS_ActualCostConfigItem.Table_Name)
			.getPO(getUNS_ActualCostConfigItem_ID(), get_TrxName());	}

	/** Set Actual Cost Item Config.
		@param UNS_ActualCostConfigItem_ID Actual Cost Item Config	  */
	public void setUNS_ActualCostConfigItem_ID (int UNS_ActualCostConfigItem_ID)
	{
		if (UNS_ActualCostConfigItem_ID < 1) 
			set_Value (COLUMNNAME_UNS_ActualCostConfigItem_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ActualCostConfigItem_ID, Integer.valueOf(UNS_ActualCostConfigItem_ID));
	}

	/** Get Actual Cost Item Config.
		@return Actual Cost Item Config	  */
	public int getUNS_ActualCostConfigItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostConfigItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Actual Cost Configuration Line.
		@param UNS_ActualCostConfigLine_ID Actual Cost Configuration Line	  */
	public void setUNS_ActualCostConfigLine_ID (int UNS_ActualCostConfigLine_ID)
	{
		if (UNS_ActualCostConfigLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfigLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ActualCostConfigLine_ID, Integer.valueOf(UNS_ActualCostConfigLine_ID));
	}

	/** Get Actual Cost Configuration Line.
		@return Actual Cost Configuration Line	  */
	public int getUNS_ActualCostConfigLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ActualCostConfigLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ActualCostConfigLine_UU.
		@param UNS_ActualCostConfigLine_UU UNS_ActualCostConfigLine_UU	  */
	public void setUNS_ActualCostConfigLine_UU (String UNS_ActualCostConfigLine_UU)
	{
		set_Value (COLUMNNAME_UNS_ActualCostConfigLine_UU, UNS_ActualCostConfigLine_UU);
	}

	/** Get UNS_ActualCostConfigLine_UU.
		@return UNS_ActualCostConfigLine_UU	  */
	public String getUNS_ActualCostConfigLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ActualCostConfigLine_UU);
	}

	public com.uns.model.I_UNS_JointCostGroup getUNS_JointCostGroup() throws RuntimeException
    {
		return (com.uns.model.I_UNS_JointCostGroup)MTable.get(getCtx(), com.uns.model.I_UNS_JointCostGroup.Table_Name)
			.getPO(getUNS_JointCostGroup_ID(), get_TrxName());	}

	/** Set Joint Cost Group.
		@param UNS_JointCostGroup_ID 
		Joint Cost Group
	  */
	public void setUNS_JointCostGroup_ID (int UNS_JointCostGroup_ID)
	{
		if (UNS_JointCostGroup_ID < 1) 
			set_Value (COLUMNNAME_UNS_JointCostGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_JointCostGroup_ID, Integer.valueOf(UNS_JointCostGroup_ID));
	}

	/** Get Joint Cost Group.
		@return Joint Cost Group
	  */
	public int getUNS_JointCostGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_JointCostGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}