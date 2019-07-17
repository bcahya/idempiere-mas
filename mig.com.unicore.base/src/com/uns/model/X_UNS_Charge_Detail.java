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

/** Generated Model for UNS_Charge_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Charge_Detail extends PO implements I_UNS_Charge_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150613L;

    /** Standard Constructor */
    public X_UNS_Charge_Detail (Properties ctx, int UNS_Charge_Detail_ID, String trxName)
    {
      super (ctx, UNS_Charge_Detail_ID, trxName);
      /** if (UNS_Charge_Detail_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setC_Charge_ID (0);
			setChargeType (null);
			setDescription (null);
			setIsCancelled (false);
// N
			setIsFuel (false);
// N
			setUNS_Charge_Detail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Charge_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Charge_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Confirmed Amount.
		@param AmountConfirmed Confirmed Amount	  */
	public void setAmountConfirmed (BigDecimal AmountConfirmed)
	{
		set_Value (COLUMNNAME_AmountConfirmed, AmountConfirmed);
	}

	/** Get Confirmed Amount.
		@return Confirmed Amount	  */
	public BigDecimal getAmountConfirmed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountConfirmed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankStatementLine)MTable.get(getCtx(), org.compiere.model.I_C_BankStatementLine.Table_Name)
			.getPO(getC_BankStatementLine_ID(), get_TrxName());	}

	/** Set Bank statement line.
		@param C_BankStatementLine_ID 
		Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Bank statement line.
		@return Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Voucher = VC */
	public static final String CHARGETYPE_Voucher = "VC";
	/** Cost = CS */
	public static final String CHARGETYPE_Cost = "CS";
	/** Set Charge Type.
		@param ChargeType Charge Type	  */
	public void setChargeType (String ChargeType)
	{

		set_Value (COLUMNNAME_ChargeType, ChargeType);
	}

	/** Get Charge Type.
		@return Charge Type	  */
	public String getChargeType () 
	{
		return (String)get_Value(COLUMNNAME_ChargeType);
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

	/** Set Cancelled.
		@param IsCancelled 
		The transaction was cancelled
	  */
	public void setIsCancelled (boolean IsCancelled)
	{
		set_Value (COLUMNNAME_IsCancelled, Boolean.valueOf(IsCancelled));
	}

	/** Get Cancelled.
		@return The transaction was cancelled
	  */
	public boolean isCancelled () 
	{
		Object oo = get_Value(COLUMNNAME_IsCancelled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fuel.
		@param IsFuel Fuel	  */
	public void setIsFuel (boolean IsFuel)
	{
		set_Value (COLUMNNAME_IsFuel, Boolean.valueOf(IsFuel));
	}

	/** Get Fuel.
		@return Fuel	  */
	public boolean isFuel () 
	{
		Object oo = get_Value(COLUMNNAME_IsFuel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liters.
		@param Liters Liters	  */
	public void setLiters (BigDecimal Liters)
	{
		set_Value (COLUMNNAME_Liters, Liters);
	}

	/** Get Liters.
		@return Liters	  */
	public BigDecimal getLiters () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Liters);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Charge_Detail getReference() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Charge_Detail)MTable.get(getCtx(), com.uns.model.I_UNS_Charge_Detail.Table_Name)
			.getPO(getReference_ID(), get_TrxName());	}

	/** Set Refrerence Record.
		@param Reference_ID Refrerence Record	  */
	public void setReference_ID (int Reference_ID)
	{
		if (Reference_ID < 1) 
			set_Value (COLUMNNAME_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_Reference_ID, Integer.valueOf(Reference_ID));
	}

	/** Get Refrerence Record.
		@return Refrerence Record	  */
	public int getReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Charge_Detail getReversalLine() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Charge_Detail)MTable.get(getCtx(), com.uns.model.I_UNS_Charge_Detail.Table_Name)
			.getPO(getReversalLine_ID(), get_TrxName());	}

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

	public com.uns.model.I_UNS_Charge_Confirmation getUNS_Charge_Confirmation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Charge_Confirmation)MTable.get(getCtx(), com.uns.model.I_UNS_Charge_Confirmation.Table_Name)
			.getPO(getUNS_Charge_Confirmation_ID(), get_TrxName());	}

	/** Set Charge Confirmation.
		@param UNS_Charge_Confirmation_ID Charge Confirmation	  */
	public void setUNS_Charge_Confirmation_ID (int UNS_Charge_Confirmation_ID)
	{
		if (UNS_Charge_Confirmation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_Confirmation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_Confirmation_ID, Integer.valueOf(UNS_Charge_Confirmation_ID));
	}

	/** Get Charge Confirmation.
		@return Charge Confirmation	  */
	public int getUNS_Charge_Confirmation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Charge_Confirmation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Charge Detail.
		@param UNS_Charge_Detail_ID Charge Detail	  */
	public void setUNS_Charge_Detail_ID (int UNS_Charge_Detail_ID)
	{
		if (UNS_Charge_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_Detail_ID, Integer.valueOf(UNS_Charge_Detail_ID));
	}

	/** Get Charge Detail.
		@return Charge Detail	  */
	public int getUNS_Charge_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Charge_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Charge_Detail_UU.
		@param UNS_Charge_Detail_UU UNS_Charge_Detail_UU	  */
	public void setUNS_Charge_Detail_UU (String UNS_Charge_Detail_UU)
	{
		set_Value (COLUMNNAME_UNS_Charge_Detail_UU, UNS_Charge_Detail_UU);
	}

	/** Get UNS_Charge_Detail_UU.
		@return UNS_Charge_Detail_UU	  */
	public String getUNS_Charge_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Charge_Detail_UU);
	}

	public com.uns.model.I_UNS_Charge_RS getUNS_Charge_RS() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Charge_RS)MTable.get(getCtx(), com.uns.model.I_UNS_Charge_RS.Table_Name)
			.getPO(getUNS_Charge_RS_ID(), get_TrxName());	}

	/** Set Charge Request / Settlement.
		@param UNS_Charge_RS_ID Charge Request / Settlement	  */
	public void setUNS_Charge_RS_ID (int UNS_Charge_RS_ID)
	{
		if (UNS_Charge_RS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_RS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Charge_RS_ID, Integer.valueOf(UNS_Charge_RS_ID));
	}

	/** Get Charge Request / Settlement.
		@return Charge Request / Settlement	  */
	public int getUNS_Charge_RS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Charge_RS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fuel.
		@param UNS_Fuel_ID Fuel	  */
	public void setUNS_Fuel_ID (int UNS_Fuel_ID)
	{
		if (UNS_Fuel_ID < 1) 
			set_Value (COLUMNNAME_UNS_Fuel_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Fuel_ID, Integer.valueOf(UNS_Fuel_ID));
	}

	/** Get Fuel.
		@return Fuel	  */
	public int getUNS_Fuel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Fuel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Document.
		@param UNS_Shipping_ID Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID)
	{
		if (UNS_Shipping_ID < 1) 
			set_Value (COLUMNNAME_UNS_Shipping_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Shipping_ID, Integer.valueOf(UNS_Shipping_ID));
	}

	/** Get Shipping Document.
		@return Shipping Document	  */
	public int getUNS_Shipping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Shipping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Voucher Code.
		@param UNS_Voucher_Code_ID Voucher Code	  */
	public void setUNS_Voucher_Code_ID (int UNS_Voucher_Code_ID)
	{
		if (UNS_Voucher_Code_ID < 1) 
			set_Value (COLUMNNAME_UNS_Voucher_Code_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Voucher_Code_ID, Integer.valueOf(UNS_Voucher_Code_ID));
	}

	/** Get Voucher Code.
		@return Voucher Code	  */
	public int getUNS_Voucher_Code_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Voucher_Code_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Voucher.
		@param UNS_Voucher_ID Voucher	  */
	public void setUNS_Voucher_ID (int UNS_Voucher_ID)
	{
		if (UNS_Voucher_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Voucher_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Voucher_ID, Integer.valueOf(UNS_Voucher_ID));
	}

	/** Get Voucher.
		@return Voucher	  */
	public int getUNS_Voucher_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Voucher_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Voucher Code.
		@param VoucherCode 
		Voucher Code
	  */
	public void setVoucherCode (String VoucherCode)
	{
		set_Value (COLUMNNAME_VoucherCode, VoucherCode);
	}

	/** Get Voucher Code.
		@return Voucher Code
	  */
	public String getVoucherCode () 
	{
		return (String)get_Value(COLUMNNAME_VoucherCode);
	}
}