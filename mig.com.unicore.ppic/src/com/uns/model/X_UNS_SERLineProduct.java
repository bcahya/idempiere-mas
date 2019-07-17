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

/** Generated Model for UNS_SERLineProduct
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SERLineProduct extends PO implements I_UNS_SERLineProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130123L;

    /** Standard Constructor */
    public X_UNS_SERLineProduct (Properties ctx, int UNS_SERLineProduct_ID, String trxName)
    {
      super (ctx, UNS_SERLineProduct_ID, trxName);
      /** if (UNS_SERLineProduct_ID == 0)
        {
			setC_UOM_ID (0);
			setFOBGtnToM3 (Env.ZERO);
			setFOBGtnToMT (Env.ZERO);
			setIsVolumeOrWeight (false);
// N
			setLine (0);
			setLineNetAmt (Env.ZERO);
			setM_Product_ID (0);
			setPrice (Env.ZERO);
			setProcessed (false);
// N
			setQtyAvailable (Env.ZERO);
// 1
			setUNS_SERLineBuyer_ID (0);
			setUNS_SERLineProduct_ID (0);
			setVolume (Env.ZERO);
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_SERLineProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SERLineProduct[")
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

	/** Set FOB (GTN/M3).
		@param FOBGtnToM3 
		hasil conversi dari UoM ke M3
	  */
	public void setFOBGtnToM3 (BigDecimal FOBGtnToM3)
	{
		set_Value (COLUMNNAME_FOBGtnToM3, FOBGtnToM3);
	}

	/** Get FOB (GTN/M3).
		@return hasil conversi dari UoM ke M3
	  */
	public BigDecimal getFOBGtnToM3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FOBGtnToM3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FOB (GTN/MT).
		@param FOBGtnToMT 
		hasil conversi dari UoM ke MT
	  */
	public void setFOBGtnToMT (BigDecimal FOBGtnToMT)
	{
		set_Value (COLUMNNAME_FOBGtnToMT, FOBGtnToMT);
	}

	/** Get FOB (GTN/MT).
		@return hasil conversi dari UoM ke MT
	  */
	public BigDecimal getFOBGtnToMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FOBGtnToMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Volume or weight.
		@param IsVolumeOrWeight Volume or weight	  */
	public void setIsVolumeOrWeight (boolean IsVolumeOrWeight)
	{
		set_Value (COLUMNNAME_IsVolumeOrWeight, Boolean.valueOf(IsVolumeOrWeight));
	}

	/** Get Volume or weight.
		@return Volume or weight	  */
	public boolean isVolumeOrWeight () 
	{
		Object oo = get_Value(COLUMNNAME_IsVolumeOrWeight);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set FOB (GTN).
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get FOB (GTN).
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered Quantity.
		@return Ordered Quantity
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reversal Line.
		@param ReversalLine_ID 
		Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Reversal Line.
		@return Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Total.
		@param SalesTotal Sales Total	  */
	public void setSalesTotal (BigDecimal SalesTotal)
	{
		set_Value (COLUMNNAME_SalesTotal, SalesTotal);
	}

	/** Get Sales Total.
		@return Sales Total	  */
	public BigDecimal getSalesTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalesTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_SERLineBuyer getUNS_SERLineBuyer() throws RuntimeException
    {
		return (com.uns.model.I_UNS_SERLineBuyer)MTable.get(getCtx(), com.uns.model.I_UNS_SERLineBuyer.Table_Name)
			.getPO(getUNS_SERLineBuyer_ID(), get_TrxName());	}

	/** Set Line Buyer.
		@param UNS_SERLineBuyer_ID Line Buyer	  */
	public void setUNS_SERLineBuyer_ID (int UNS_SERLineBuyer_ID)
	{
		if (UNS_SERLineBuyer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SERLineBuyer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SERLineBuyer_ID, Integer.valueOf(UNS_SERLineBuyer_ID));
	}

	/** Get Line Buyer.
		@return Line Buyer	  */
	public int getUNS_SERLineBuyer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SERLineBuyer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Product.
		@param UNS_SERLineProduct_ID Line Product	  */
	public void setUNS_SERLineProduct_ID (int UNS_SERLineProduct_ID)
	{
		if (UNS_SERLineProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SERLineProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SERLineProduct_ID, Integer.valueOf(UNS_SERLineProduct_ID));
	}

	/** Get Line Product.
		@return Line Product	  */
	public int getUNS_SERLineProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SERLineProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Product UU.
		@param UNS_SERLineProduct_UU Line Product UU	  */
	public void setUNS_SERLineProduct_UU (String UNS_SERLineProduct_UU)
	{
		set_Value (COLUMNNAME_UNS_SERLineProduct_UU, UNS_SERLineProduct_UU);
	}

	/** Get Line Product UU.
		@return Line Product UU	  */
	public String getUNS_SERLineProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SERLineProduct_UU);
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}