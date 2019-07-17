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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_BonusClaimLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_BonusClaimLine extends PO implements I_UNS_BonusClaimLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141201L;

    /** Standard Constructor */
    public X_UNS_BonusClaimLine (Properties ctx, int UNS_BonusClaimLine_ID, String trxName)
    {
      super (ctx, UNS_BonusClaimLine_ID, trxName);
      /** if (UNS_BonusClaimLine_ID == 0)
        {
			setAmtAchieved (Env.ZERO);
			setAmtClaimed (Env.ZERO);
			setC_Tax_ID (0);
			setLine (0);
			setLineNetAmt (Env.ZERO);
			setProcessed (false);
			setQtyAchieved (Env.ZERO);
			setQtyClaimed (Env.ZERO);
			setUNS_BonusClaim_ID (0);
			setUNS_BonusClaimLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BonusClaimLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BonusClaimLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amt Achieved.
		@param AmtAchieved Amt Achieved	  */
	public void setAmtAchieved (BigDecimal AmtAchieved)
	{
		set_Value (COLUMNNAME_AmtAchieved, AmtAchieved);
	}

	/** Get Amt Achieved.
		@return Amt Achieved	  */
	public BigDecimal getAmtAchieved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAchieved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amt Claimed.
		@param AmtClaimed Amt Claimed	  */
	public void setAmtClaimed (BigDecimal AmtClaimed)
	{
		set_Value (COLUMNNAME_AmtClaimed, AmtClaimed);
	}

	/** Get Amt Claimed.
		@return Amt Claimed	  */
	public BigDecimal getAmtClaimed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtClaimed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
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

	/** Apply = APL */
	public static final String DECISIONCONFIRM_Apply = "APL";
	/** Discard = DSR */
	public static final String DECISIONCONFIRM_Discard = "DSR";
	/** Set Decision Confirm.
		@param DecisionConfirm Decision Confirm	  */
	public void setDecisionConfirm (String DecisionConfirm)
	{

		set_Value (COLUMNNAME_DecisionConfirm, DecisionConfirm);
	}

	/** Get Decision Confirm.
		@return Decision Confirm	  */
	public String getDecisionConfirm () 
	{
		return (String)get_Value(COLUMNNAME_DecisionConfirm);
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
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

	public org.compiere.model.I_M_Product getProductBonus() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getProductBonus_ID(), get_TrxName());	}

	/** Set Product Bonus.
		@param ProductBonus_ID Product Bonus	  */
	public void setProductBonus_ID (int ProductBonus_ID)
	{
		if (ProductBonus_ID < 1) 
			set_Value (COLUMNNAME_ProductBonus_ID, null);
		else 
			set_Value (COLUMNNAME_ProductBonus_ID, Integer.valueOf(ProductBonus_ID));
	}

	/** Get Product Bonus.
		@return Product Bonus	  */
	public int getProductBonus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductBonus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty Achieved.
		@param QtyAchieved Qty Achieved	  */
	public void setQtyAchieved (BigDecimal QtyAchieved)
	{
		set_Value (COLUMNNAME_QtyAchieved, QtyAchieved);
	}

	/** Get Qty Achieved.
		@return Qty Achieved	  */
	public BigDecimal getQtyAchieved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAchieved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Claimed.
		@param QtyClaimed Qty Claimed	  */
	public void setQtyClaimed (BigDecimal QtyClaimed)
	{
		set_Value (COLUMNNAME_QtyClaimed, QtyClaimed);
	}

	/** Get Qty Claimed.
		@return Qty Claimed	  */
	public BigDecimal getQtyClaimed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyClaimed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Invoice getSourceInvoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getSourceInvoice_ID(), get_TrxName());	}

	/** Set Source Invoice.
		@param SourceInvoice_ID 
		Source Invoice
	  */
	public void setSourceInvoice_ID (int SourceInvoice_ID)
	{
		if (SourceInvoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SourceInvoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SourceInvoice_ID, Integer.valueOf(SourceInvoice_ID));
	}

	/** Get Source Invoice.
		@return Source Invoice
	  */
	public int getSourceInvoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SourceInvoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_InvoiceLine getSourceInvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getSourceInvoiceLine_ID(), get_TrxName());	}

	/** Set Source Invoice Line.
		@param SourceInvoiceLine_ID 
		Source Invoice Detail Line
	  */
	public void setSourceInvoiceLine_ID (int SourceInvoiceLine_ID)
	{
		if (SourceInvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SourceInvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SourceInvoiceLine_ID, Integer.valueOf(SourceInvoiceLine_ID));
	}

	/** Get Source Invoice Line.
		@return Source Invoice Detail Line
	  */
	public int getSourceInvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SourceInvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_BonusClaim getUNS_BonusClaim() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_BonusClaim)MTable.get(getCtx(), com.unicore.model.I_UNS_BonusClaim.Table_Name)
			.getPO(getUNS_BonusClaim_ID(), get_TrxName());	}

	/** Set Bonus Claim.
		@param UNS_BonusClaim_ID Bonus Claim	  */
	public void setUNS_BonusClaim_ID (int UNS_BonusClaim_ID)
	{
		if (UNS_BonusClaim_ID < 1) 
			set_Value (COLUMNNAME_UNS_BonusClaim_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BonusClaim_ID, Integer.valueOf(UNS_BonusClaim_ID));
	}

	/** Get Bonus Claim.
		@return Bonus Claim	  */
	public int getUNS_BonusClaim_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BonusClaim_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bonus Claim Line.
		@param UNS_BonusClaimLine_ID Bonus Claim Line	  */
	public void setUNS_BonusClaimLine_ID (int UNS_BonusClaimLine_ID)
	{
		if (UNS_BonusClaimLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BonusClaimLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BonusClaimLine_ID, Integer.valueOf(UNS_BonusClaimLine_ID));
	}

	/** Get Bonus Claim Line.
		@return Bonus Claim Line	  */
	public int getUNS_BonusClaimLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BonusClaimLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BonusClaimLine_UU.
		@param UNS_BonusClaimLine_UU UNS_BonusClaimLine_UU	  */
	public void setUNS_BonusClaimLine_UU (String UNS_BonusClaimLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_BonusClaimLine_UU, UNS_BonusClaimLine_UU);
	}

	/** Get UNS_BonusClaimLine_UU.
		@return UNS_BonusClaimLine_UU	  */
	public String getUNS_BonusClaimLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BonusClaimLine_UU);
	}

	public org.compiere.model.I_C_UOM getUOMBonus() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMBonus_ID(), get_TrxName());	}

	/** Set UOM Bonus.
		@param UOMBonus_ID UOM Bonus	  */
	public void setUOMBonus_ID (int UOMBonus_ID)
	{
		if (UOMBonus_ID < 1) 
			set_Value (COLUMNNAME_UOMBonus_ID, null);
		else 
			set_Value (COLUMNNAME_UOMBonus_ID, Integer.valueOf(UOMBonus_ID));
	}

	/** Get UOM Bonus.
		@return UOM Bonus	  */
	public int getUOMBonus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMBonus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}