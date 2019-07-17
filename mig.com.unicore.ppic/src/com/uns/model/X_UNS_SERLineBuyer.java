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

/** Generated Model for UNS_SERLineBuyer
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_SERLineBuyer extends PO implements I_UNS_SERLineBuyer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130123L;

    /** Standard Constructor */
    public X_UNS_SERLineBuyer (Properties ctx, int UNS_SERLineBuyer_ID, String trxName)
    {
      super (ctx, UNS_SERLineBuyer_ID, trxName);
      /** if (UNS_SERLineBuyer_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Country_ID (0);
			setIsAvailable (true);
// Y
			setLine (0);
			setM_Product_Category_ID (0);
			setProcessed (false);
// N
			setQtyAvailable (Env.ZERO);
			setUNS_SER_ID (0);
			setUNS_SERLineBuyer_ID (0);
			setVolume (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_SERLineBuyer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SERLineBuyer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Freight Terms.
		@param FOB Freight Terms	  */
	public void setFOB (BigDecimal FOB)
	{
		set_Value (COLUMNNAME_FOB, FOB);
	}

	/** Get Freight Terms.
		@return Freight Terms	  */
	public BigDecimal getFOB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FOB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Available.
		@param IsAvailable 
		Resource is available
	  */
	public void setIsAvailable (boolean IsAvailable)
	{
		set_Value (COLUMNNAME_IsAvailable, Boolean.valueOf(IsAvailable));
	}

	/** Get Available.
		@return Resource is available
	  */
	public boolean isAvailable () 
	{
		Object oo = get_Value(COLUMNNAME_IsAvailable);
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

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_Category)MTable.get(getCtx(), org.compiere.model.I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
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

	/** Set Reference SER Buyer Product.
		@param Ref_UNS_SERBuyerProduct_ID 
		Reference SER Buyer Product (reversal)
	  */
	public void setRef_UNS_SERBuyerProduct_ID (int Ref_UNS_SERBuyerProduct_ID)
	{
		if (Ref_UNS_SERBuyerProduct_ID < 1) 
			set_Value (COLUMNNAME_Ref_UNS_SERBuyerProduct_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_UNS_SERBuyerProduct_ID, Integer.valueOf(Ref_UNS_SERBuyerProduct_ID));
	}

	/** Get Reference SER Buyer Product.
		@return Reference SER Buyer Product (reversal)
	  */
	public int getRef_UNS_SERBuyerProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_UNS_SERBuyerProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set TEUs (20 ft).
		@param TEU 
		Twenty Equivalent Unit
	  */
	public void setTEU (BigDecimal TEU)
	{
		set_Value (COLUMNNAME_TEU, TEU);
	}

	/** Get TEUs (20 ft).
		@return Twenty Equivalent Unit
	  */
	public BigDecimal getTEU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TEU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_SER getUNS_SER() throws RuntimeException
    {
		return (com.uns.model.I_UNS_SER)MTable.get(getCtx(), com.uns.model.I_UNS_SER.Table_Name)
			.getPO(getUNS_SER_ID(), get_TrxName());	}

	/** Set Sales Enquiry.
		@param UNS_SER_ID Sales Enquiry	  */
	public void setUNS_SER_ID (int UNS_SER_ID)
	{
		if (UNS_SER_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SER_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SER_ID, Integer.valueOf(UNS_SER_ID));
	}

	/** Get Sales Enquiry.
		@return Sales Enquiry	  */
	public int getUNS_SER_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SER_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set Line Buyer UU.
		@param UNS_SERLineBuyer_UU Line Buyer UU	  */
	public void setUNS_SERLineBuyer_UU (String UNS_SERLineBuyer_UU)
	{
		set_Value (COLUMNNAME_UNS_SERLineBuyer_UU, UNS_SERLineBuyer_UU);
	}

	/** Get Line Buyer UU.
		@return Line Buyer UU	  */
	public String getUNS_SERLineBuyer_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SERLineBuyer_UU);
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