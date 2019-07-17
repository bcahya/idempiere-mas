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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PL_ConfirmMA
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PL_ConfirmMA extends PO implements I_UNS_PL_ConfirmMA, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150514L;

    /** Standard Constructor */
    public X_UNS_PL_ConfirmMA (Properties ctx, int UNS_PL_ConfirmMA_ID, String trxName)
    {
      super (ctx, UNS_PL_ConfirmMA_ID, trxName);
      /** if (UNS_PL_ConfirmMA_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setMovementQty (Env.ZERO);
			setUNS_PL_ConfirmProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PL_ConfirmMA (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PL_ConfirmMA[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date  Material Policy.
		@param DateMaterialPolicy 
		Time used for LIFO and FIFO Material Policy
	  */
	public void setDateMaterialPolicy (Timestamp DateMaterialPolicy)
	{
		set_ValueNoCheck (COLUMNNAME_DateMaterialPolicy, DateMaterialPolicy);
	}

	/** Get Date  Material Policy.
		@return Time used for LIFO and FIFO Material Policy
	  */
	public Timestamp getDateMaterialPolicy () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateMaterialPolicy);
	}

	/** Set Auto Generated.
		@param IsAutoGenerated Auto Generated	  */
	public void setIsAutoGenerated (boolean IsAutoGenerated)
	{
		set_ValueNoCheck (COLUMNNAME_IsAutoGenerated, Boolean.valueOf(IsAutoGenerated));
	}

	/** Get Auto Generated.
		@return Auto Generated	  */
	public boolean isAutoGenerated () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoGenerated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UNS_PL_ConfirmMA_UU.
		@param UNS_PL_ConfirmMA_UU UNS_PL_ConfirmMA_UU	  */
	public void setUNS_PL_ConfirmMA_UU (String UNS_PL_ConfirmMA_UU)
	{
		set_Value (COLUMNNAME_UNS_PL_ConfirmMA_UU, UNS_PL_ConfirmMA_UU);
	}

	/** Get UNS_PL_ConfirmMA_UU.
		@return UNS_PL_ConfirmMA_UU	  */
	public String getUNS_PL_ConfirmMA_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PL_ConfirmMA_UU);
	}

	public com.unicore.model.I_UNS_PL_ConfirmProduct getUNS_PL_ConfirmProduct() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_PL_ConfirmProduct)MTable.get(getCtx(), com.unicore.model.I_UNS_PL_ConfirmProduct.Table_Name)
			.getPO(getUNS_PL_ConfirmProduct_ID(), get_TrxName());	}

	/** Set Confirmation Product.
		@param UNS_PL_ConfirmProduct_ID Confirmation Product	  */
	public void setUNS_PL_ConfirmProduct_ID (int UNS_PL_ConfirmProduct_ID)
	{
		if (UNS_PL_ConfirmProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PL_ConfirmProduct_ID, Integer.valueOf(UNS_PL_ConfirmProduct_ID));
	}

	/** Get Confirmation Product.
		@return Confirmation Product	  */
	public int getUNS_PL_ConfirmProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PL_ConfirmProduct_ID);
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