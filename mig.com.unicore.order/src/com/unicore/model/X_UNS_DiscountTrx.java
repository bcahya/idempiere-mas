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

/** Generated Model for UNS_DiscountTrx
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_DiscountTrx extends PO implements I_UNS_DiscountTrx, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180201L;

    /** Standard Constructor */
    public X_UNS_DiscountTrx (Properties ctx, int UNS_DiscountTrx_ID, String trxName)
    {
      super (ctx, UNS_DiscountTrx_ID, trxName);
      /** if (UNS_DiscountTrx_ID == 0)
        {
			setDiscountValue1st (Env.ZERO);
// 0
			setDiscountValue2nd (Env.ZERO);
// 0
			setDiscountValue3rd (Env.ZERO);
// 0
			setDiscountValue4th (Env.ZERO);
// 0
			setDiscountValue5th (Env.ZERO);
// 0
			setFirstDiscount (Env.ZERO);
// 0
			setFlatPercentDiscount (Env.ZERO);
// 0
			setFlatValueDiscount (Env.ZERO);
// 0
			setIsChangedByUser (false);
// N
			setIsNeedRecalculate (false);
// N
			setisReCheck (false);
// N
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM UNS_DiscountTrx WHERE C_Order_ID=@C_Order_ID@
			setUNS_DiscountTrx_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DiscountTrx (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DiscountTrx[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BonusesPrice.
		@param BonusesPrice BonusesPrice	  */
	public void setBonusesPrice (BigDecimal BonusesPrice)
	{
		set_Value (COLUMNNAME_BonusesPrice, BonusesPrice);
	}

	/** Get BonusesPrice.
		@return BonusesPrice	  */
	public BigDecimal getBonusesPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BonusesPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discounted Amt.
		@param DiscountedAmt Discounted Amt	  */
	public void setDiscountedAmt (BigDecimal DiscountedAmt)
	{
		set_Value (COLUMNNAME_DiscountedAmt, DiscountedAmt);
	}

	/** Get Discounted Amt.
		@return Discounted Amt	  */
	public BigDecimal getDiscountedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Bonus = B */
	public static final String DISCOUNTTYPE_Bonus = "B";
	/** Percent = P */
	public static final String DISCOUNTTYPE_Percent = "P";
	/** Value = V */
	public static final String DISCOUNTTYPE_Value = "V";
	/** Set Discount Type.
		@param DiscountType 
		Type of trade discount calculation
	  */
	public void setDiscountType (String DiscountType)
	{

		set_Value (COLUMNNAME_DiscountType, DiscountType);
	}

	/** Get Discount Type.
		@return Type of trade discount calculation
	  */
	public String getDiscountType () 
	{
		return (String)get_Value(COLUMNNAME_DiscountType);
	}

	/** Set DiscountValue1st.
		@param DiscountValue1st DiscountValue1st	  */
	public void setDiscountValue1st (BigDecimal DiscountValue1st)
	{
		set_Value (COLUMNNAME_DiscountValue1st, DiscountValue1st);
	}

	/** Get DiscountValue1st.
		@return DiscountValue1st	  */
	public BigDecimal getDiscountValue1st () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountValue1st);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiscountValue2nd.
		@param DiscountValue2nd DiscountValue2nd	  */
	public void setDiscountValue2nd (BigDecimal DiscountValue2nd)
	{
		set_Value (COLUMNNAME_DiscountValue2nd, DiscountValue2nd);
	}

	/** Get DiscountValue2nd.
		@return DiscountValue2nd	  */
	public BigDecimal getDiscountValue2nd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountValue2nd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiscountValue3rd.
		@param DiscountValue3rd DiscountValue3rd	  */
	public void setDiscountValue3rd (BigDecimal DiscountValue3rd)
	{
		set_Value (COLUMNNAME_DiscountValue3rd, DiscountValue3rd);
	}

	/** Get DiscountValue3rd.
		@return DiscountValue3rd	  */
	public BigDecimal getDiscountValue3rd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountValue3rd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiscountValue4th.
		@param DiscountValue4th DiscountValue4th	  */
	public void setDiscountValue4th (BigDecimal DiscountValue4th)
	{
		set_Value (COLUMNNAME_DiscountValue4th, DiscountValue4th);
	}

	/** Get DiscountValue4th.
		@return DiscountValue4th	  */
	public BigDecimal getDiscountValue4th () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountValue4th);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DiscountValue5th.
		@param DiscountValue5th DiscountValue5th	  */
	public void setDiscountValue5th (BigDecimal DiscountValue5th)
	{
		set_Value (COLUMNNAME_DiscountValue5th, DiscountValue5th);
	}

	/** Get DiscountValue5th.
		@return DiscountValue5th	  */
	public BigDecimal getDiscountValue5th () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountValue5th);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 5th Discount %.
		@param FifthDiscount 
		Fifth discount percentage
	  */
	public void setFifthDiscount (BigDecimal FifthDiscount)
	{
		set_Value (COLUMNNAME_FifthDiscount, FifthDiscount);
	}

	/** Get 5th Discount %.
		@return Fifth discount percentage
	  */
	public BigDecimal getFifthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FifthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 1st Discount %.
		@param FirstDiscount 1st Discount %	  */
	public void setFirstDiscount (BigDecimal FirstDiscount)
	{
		set_Value (COLUMNNAME_FirstDiscount, FirstDiscount);
	}

	/** Get 1st Discount %.
		@return 1st Discount %	  */
	public BigDecimal getFirstDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FirstDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Flat Discount %.
		@param FlatPercentDiscount Flat Discount %	  */
	public void setFlatPercentDiscount (BigDecimal FlatPercentDiscount)
	{
		set_Value (COLUMNNAME_FlatPercentDiscount, FlatPercentDiscount);
	}

	/** Get Flat Discount %.
		@return Flat Discount %	  */
	public BigDecimal getFlatPercentDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatPercentDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Flat Value Discount.
		@param FlatValueDiscount Flat Value Discount	  */
	public void setFlatValueDiscount (BigDecimal FlatValueDiscount)
	{
		set_Value (COLUMNNAME_FlatValueDiscount, FlatValueDiscount);
	}

	/** Get Flat Value Discount.
		@return Flat Value Discount	  */
	public BigDecimal getFlatValueDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatValueDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Discount %.
		@param FourthDiscount 
		Fourth discount percentage
	  */
	public void setFourthDiscount (BigDecimal FourthDiscount)
	{
		set_Value (COLUMNNAME_FourthDiscount, FourthDiscount);
	}

	/** Get 4th Discount %.
		@return Fourth discount percentage
	  */
	public BigDecimal getFourthDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FourthDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Changed By User.
		@param IsChangedByUser Changed By User	  */
	public void setIsChangedByUser (boolean IsChangedByUser)
	{
		set_Value (COLUMNNAME_IsChangedByUser, Boolean.valueOf(IsChangedByUser));
	}

	/** Get Changed By User.
		@return Changed By User	  */
	public boolean isChangedByUser () 
	{
		Object oo = get_Value(COLUMNNAME_IsChangedByUser);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Need Recalculate.
		@param IsNeedRecalculate Need Recalculate	  */
	public void setIsNeedRecalculate (boolean IsNeedRecalculate)
	{
		set_Value (COLUMNNAME_IsNeedRecalculate, Boolean.valueOf(IsNeedRecalculate));
	}

	/** Get Need Recalculate.
		@return Need Recalculate	  */
	public boolean isNeedRecalculate () 
	{
		Object oo = get_Value(COLUMNNAME_IsNeedRecalculate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Re Check.
		@param isReCheck Re Check	  */
	public void setisReCheck (boolean isReCheck)
	{
		set_Value (COLUMNNAME_isReCheck, Boolean.valueOf(isReCheck));
	}

	/** Get Re Check.
		@return Re Check	  */
	public boolean isReCheck () 
	{
		Object oo = get_Value(COLUMNNAME_isReCheck);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Discount Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Discount Schema.
		@return Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Schema Break.
		@param M_DiscountSchemaBreak_ID 
		Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_ID, Integer.valueOf(M_DiscountSchemaBreak_ID));
	}

	/** Get Discount Schema Break.
		@return Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaBreak_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Bonuses Qty.
		@param QtyBonuses 
		Bonuses Qty
	  */
	public void setQtyBonuses (BigDecimal QtyBonuses)
	{
		set_Value (COLUMNNAME_QtyBonuses, QtyBonuses);
	}

	/** Get Bonuses Qty.
		@return Bonuses Qty
	  */
	public BigDecimal getQtyBonuses () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBonuses);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Or Value Discounted.
		@param QtyValDiscounted Qty Or Value Discounted	  */
	public void setQtyValDiscounted (BigDecimal QtyValDiscounted)
	{
		set_Value (COLUMNNAME_QtyValDiscounted, QtyValDiscounted);
	}

	/** Get Qty Or Value Discounted.
		@return Qty Or Value Discounted	  */
	public BigDecimal getQtyValDiscounted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyValDiscounted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 2nd Discount %.
		@param SecondDiscount 
		Second discount percentage
	  */
	public void setSecondDiscount (BigDecimal SecondDiscount)
	{
		set_Value (COLUMNNAME_SecondDiscount, SecondDiscount);
	}

	/** Get 2nd Discount %.
		@return Second discount percentage
	  */
	public BigDecimal getSecondDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SecondDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 3rd Discount %.
		@param ThirdDiscount 
		Third discount percentage
	  */
	public void setThirdDiscount (BigDecimal ThirdDiscount)
	{
		set_Value (COLUMNNAME_ThirdDiscount, ThirdDiscount);
	}

	/** Get 3rd Discount %.
		@return Third discount percentage
	  */
	public BigDecimal getThirdDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ThirdDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Discount Bonus.
		@param UNS_DiscountBonus_ID Discount Bonus	  */
	public void setUNS_DiscountBonus_ID (int UNS_DiscountBonus_ID)
	{
		if (UNS_DiscountBonus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DiscountBonus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DiscountBonus_ID, Integer.valueOf(UNS_DiscountBonus_ID));
	}

	/** Get Discount Bonus.
		@return Discount Bonus	  */
	public int getUNS_DiscountBonus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DiscountBonus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Bonus Transcation.
		@param UNS_DiscountTrx_ID Discount Bonus Transcation	  */
	public void setUNS_DiscountTrx_ID (int UNS_DiscountTrx_ID)
	{
		if (UNS_DiscountTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DiscountTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DiscountTrx_ID, Integer.valueOf(UNS_DiscountTrx_ID));
	}

	/** Get Discount Bonus Transcation.
		@return Discount Bonus Transcation	  */
	public int getUNS_DiscountTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DiscountTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DiscountTrx_UU.
		@param UNS_DiscountTrx_UU UNS_DiscountTrx_UU	  */
	public void setUNS_DiscountTrx_UU (String UNS_DiscountTrx_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_DiscountTrx_UU, UNS_DiscountTrx_UU);
	}

	/** Get UNS_DiscountTrx_UU.
		@return UNS_DiscountTrx_UU	  */
	public String getUNS_DiscountTrx_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DiscountTrx_UU);
	}

	/** Set Discount Break Line.
		@param UNS_DSBreakLine_ID Discount Break Line	  */
	public void setUNS_DSBreakLine_ID (int UNS_DSBreakLine_ID)
	{
		if (UNS_DSBreakLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DSBreakLine_ID, Integer.valueOf(UNS_DSBreakLine_ID));
	}

	/** Get Discount Break Line.
		@return Discount Break Line	  */
	public int getUNS_DSBreakLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DSBreakLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}