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

/** Generated Model for UNS_UnloadingCostLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_UnloadingCostLine extends PO implements I_UNS_UnloadingCostLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170904L;

    /** Standard Constructor */
    public X_UNS_UnloadingCostLine (Properties ctx, int UNS_UnloadingCostLine_ID, String trxName)
    {
      super (ctx, UNS_UnloadingCostLine_ID, trxName);
      /** if (UNS_UnloadingCostLine_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setC_Order_ID (0);
			setDriver (null);
			setM_InOut_ID (0);
			setPayAmt (Env.ZERO);
// 0
			setUNS_UnloadingCostLine_ID (0);
			setVehicleNo (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_UnloadingCostLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_UnloadingCostLine[")
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

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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

	/** Set Driver.
		@param Driver Driver	  */
	public void setDriver (String Driver)
	{
		set_Value (COLUMNNAME_Driver, Driver);
	}

	/** Get Driver.
		@return Driver	  */
	public String getDriver () 
	{
		return (String)get_Value(COLUMNNAME_Driver);
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UNS_UnloadingCostLine getReference() throws RuntimeException
    {
		return (I_UNS_UnloadingCostLine)MTable.get(getCtx(), I_UNS_UnloadingCostLine.Table_Name)
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

	public I_UNS_UnloadingCost getUNS_UnloadingCost() throws RuntimeException
    {
		return (I_UNS_UnloadingCost)MTable.get(getCtx(), I_UNS_UnloadingCost.Table_Name)
			.getPO(getUNS_UnloadingCost_ID(), get_TrxName());	}

	/** Set Unloading Cost.
		@param UNS_UnloadingCost_ID Unloading Cost	  */
	public void setUNS_UnloadingCost_ID (int UNS_UnloadingCost_ID)
	{
		if (UNS_UnloadingCost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_UnloadingCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_UnloadingCost_ID, Integer.valueOf(UNS_UnloadingCost_ID));
	}

	/** Get Unloading Cost.
		@return Unloading Cost	  */
	public int getUNS_UnloadingCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_UnloadingCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unloading Cost Line.
		@param UNS_UnloadingCostLine_ID Unloading Cost Line	  */
	public void setUNS_UnloadingCostLine_ID (int UNS_UnloadingCostLine_ID)
	{
		if (UNS_UnloadingCostLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_UnloadingCostLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_UnloadingCostLine_ID, Integer.valueOf(UNS_UnloadingCostLine_ID));
	}

	/** Get Unloading Cost Line.
		@return Unloading Cost Line	  */
	public int getUNS_UnloadingCostLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_UnloadingCostLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_UnloadingCostLine_UU.
		@param UNS_UnloadingCostLine_UU UNS_UnloadingCostLine_UU	  */
	public void setUNS_UnloadingCostLine_UU (String UNS_UnloadingCostLine_UU)
	{
		set_Value (COLUMNNAME_UNS_UnloadingCostLine_UU, UNS_UnloadingCostLine_UU);
	}

	/** Get UNS_UnloadingCostLine_UU.
		@return UNS_UnloadingCostLine_UU	  */
	public String getUNS_UnloadingCostLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_UnloadingCostLine_UU);
	}

	/** Set Vehicle No.
		@param VehicleNo Vehicle No	  */
	public void setVehicleNo (String VehicleNo)
	{
		set_Value (COLUMNNAME_VehicleNo, VehicleNo);
	}

	/** Get Vehicle No.
		@return Vehicle No	  */
	public String getVehicleNo () 
	{
		return (String)get_Value(COLUMNNAME_VehicleNo);
	}
}