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

/** Generated Model for UNS_Production_WRAttributes
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Production_WRAttributes extends PO implements I_UNS_Production_WRAttributes, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150511L;

    /** Standard Constructor */
    public X_UNS_Production_WRAttributes (Properties ctx, int UNS_Production_WRAttributes_ID, String trxName)
    {
      super (ctx, UNS_Production_WRAttributes_ID, trxName);
      /** if (UNS_Production_WRAttributes_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
// N
			setProductionQty (Env.ZERO);
// 1
			setUNS_Production_WRAttributes_ID (0);
			setUNS_Production_WRAttributes_UU (null);
			setUNS_Production_Worker_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_WRAttributes (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_WRAttributes[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Production Worker Result Attributes.
		@param UNS_Production_WRAttributes_ID Production Worker Result Attributes	  */
	public void setUNS_Production_WRAttributes_ID (int UNS_Production_WRAttributes_ID)
	{
		if (UNS_Production_WRAttributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_WRAttributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_WRAttributes_ID, Integer.valueOf(UNS_Production_WRAttributes_ID));
	}

	/** Get Production Worker Result Attributes.
		@return Production Worker Result Attributes	  */
	public int getUNS_Production_WRAttributes_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_WRAttributes_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Production_WRAttributes_UU.
		@param UNS_Production_WRAttributes_UU UNS_Production_WRAttributes_UU	  */
	public void setUNS_Production_WRAttributes_UU (String UNS_Production_WRAttributes_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_Production_WRAttributes_UU, UNS_Production_WRAttributes_UU);
	}

	/** Get UNS_Production_WRAttributes_UU.
		@return UNS_Production_WRAttributes_UU	  */
	public String getUNS_Production_WRAttributes_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_WRAttributes_UU);
	}

	public com.uns.model.I_UNS_Production_Worker getUNS_Production_Worker() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production_Worker)MTable.get(getCtx(), com.uns.model.I_UNS_Production_Worker.Table_Name)
			.getPO(getUNS_Production_Worker_ID(), get_TrxName());	}

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

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}
}