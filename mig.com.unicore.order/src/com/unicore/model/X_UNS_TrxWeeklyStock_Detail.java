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

/** Generated Model for UNS_TrxWeeklyStock_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_TrxWeeklyStock_Detail extends PO implements I_UNS_TrxWeeklyStock_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170409L;

    /** Standard Constructor */
    public X_UNS_TrxWeeklyStock_Detail (Properties ctx, int UNS_TrxWeeklyStock_Detail_ID, String trxName)
    {
      super (ctx, UNS_TrxWeeklyStock_Detail_ID, trxName);
      /** if (UNS_TrxWeeklyStock_Detail_ID == 0)
        {
			setM_Transaction_ID (0);
			setMovementQty (Env.ZERO);
// 0
			setMovementType (null);
			setUNS_TrxWeeklyStock_Detail_ID (0);
			setUNS_TrxWeeklyStock_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_TrxWeeklyStock_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_TrxWeeklyStock_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
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

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException
    {
		return (org.compiere.model.I_M_Inventory)MTable.get(getCtx(), org.compiere.model.I_M_Inventory.Table_Name)
			.getPO(getM_Inventory_ID(), get_TrxName());	}

	/** Set Phys.Inventory.
		@param M_Inventory_ID 
		Parameters for a Physical Inventory
	  */
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	/** Get Phys.Inventory.
		@return Parameters for a Physical Inventory
	  */
	public int getM_Inventory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Inventory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InventoryLine)MTable.get(getCtx(), org.compiere.model.I_M_InventoryLine.Table_Name)
			.getPO(getM_InventoryLine_ID(), get_TrxName());	}

	/** Set Phys.Inventory Line.
		@param M_InventoryLine_ID 
		Unique line in an Inventory document
	  */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	/** Get Phys.Inventory Line.
		@return Unique line in an Inventory document
	  */
	public int getM_InventoryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
    {
		return (org.compiere.model.I_M_Movement)MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
			.getPO(getM_Movement_ID(), get_TrxName());	}

	/** Set Inventory Move.
		@param M_Movement_ID 
		Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Inventory Move.
		@return Movement of Inventory
	  */
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_MovementLine)MTable.get(getCtx(), org.compiere.model.I_M_MovementLine.Table_Name)
			.getPO(getM_MovementLine_ID(), get_TrxName());	}

	/** Set Move Line.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Move Line.
		@return Inventory Move document Line
	  */
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Transaction getM_Transaction() throws RuntimeException
    {
		return (org.compiere.model.I_M_Transaction)MTable.get(getCtx(), org.compiere.model.I_M_Transaction.Table_Name)
			.getPO(getM_Transaction_ID(), get_TrxName());	}

	/** Set Inventory Transaction.
		@param M_Transaction_ID Inventory Transaction	  */
	public void setM_Transaction_ID (int M_Transaction_ID)
	{
		if (M_Transaction_ID < 1) 
			set_Value (COLUMNNAME_M_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_M_Transaction_ID, Integer.valueOf(M_Transaction_ID));
	}

	/** Get Inventory Transaction.
		@return Inventory Transaction	  */
	public int getM_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Date.
		@param MovementDate 
		Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate)
	{
		set_ValueNoCheck (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Movement Date.
		@return Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MovementDate);
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

	/** MovementType AD_Reference_ID=189 */
	public static final int MOVEMENTTYPE_AD_Reference_ID=189;
	/** Customer Shipment = C- */
	public static final String MOVEMENTTYPE_CustomerShipment = "C-";
	/** Customer Returns = C+ */
	public static final String MOVEMENTTYPE_CustomerReturns = "C+";
	/** Vendor Receipts = V+ */
	public static final String MOVEMENTTYPE_VendorReceipts = "V+";
	/** Vendor Returns = V- */
	public static final String MOVEMENTTYPE_VendorReturns = "V-";
	/** Inventory Out = I- */
	public static final String MOVEMENTTYPE_InventoryOut = "I-";
	/** Inventory In = I+ */
	public static final String MOVEMENTTYPE_InventoryIn = "I+";
	/** Movement From = M- */
	public static final String MOVEMENTTYPE_MovementFrom = "M-";
	/** Movement To = M+ */
	public static final String MOVEMENTTYPE_MovementTo = "M+";
	/** Production + = P+ */
	public static final String MOVEMENTTYPE_ProductionPlus = "P+";
	/** Production - = P- */
	public static final String MOVEMENTTYPE_Production_ = "P-";
	/** Work Order + = W+ */
	public static final String MOVEMENTTYPE_WorkOrderPlus = "W+";
	/** Work Order - = W- */
	public static final String MOVEMENTTYPE_WorkOrder_ = "W-";
	/** Service_ = S- */
	public static final String MOVEMENTTYPE_Service_ = "S-";
	/** Packed Out = L- */
	public static final String MOVEMENTTYPE_PackedOut = "L-";
	/** Packed In = L+ */
	public static final String MOVEMENTTYPE_PackedIn = "L+";
	/** Set Movement Type.
		@param MovementType 
		Method of moving the inventory
	  */
	public void setMovementType (String MovementType)
	{

		set_Value (COLUMNNAME_MovementType, MovementType);
	}

	/** Get Movement Type.
		@return Method of moving the inventory
	  */
	public String getMovementType () 
	{
		return (String)get_Value(COLUMNNAME_MovementType);
	}

	/** Set Packing List.
		@param UNS_PackingList_ID Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID)
	{
		if (UNS_PackingList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingList_ID, Integer.valueOf(UNS_PackingList_ID));
	}

	/** Get Packing List.
		@return Packing List	  */
	public int getUNS_PackingList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set Production Detail.
		@param UNS_Production_Detail_ID Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID)
	{
		if (UNS_Production_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, Integer.valueOf(UNS_Production_Detail_ID));
	}

	/** Get Production Detail.
		@return Production Detail	  */
	public int getUNS_Production_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production.
		@param UNS_Production_ID Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID)
	{
		if (UNS_Production_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, Integer.valueOf(UNS_Production_ID));
	}

	/** Get Production.
		@return Production	  */
	public int getUNS_Production_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Weekly Stock Detail.
		@param UNS_TrxWeeklyStock_Detail_ID Transaction Weekly Stock Detail	  */
	public void setUNS_TrxWeeklyStock_Detail_ID (int UNS_TrxWeeklyStock_Detail_ID)
	{
		if (UNS_TrxWeeklyStock_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Detail_ID, Integer.valueOf(UNS_TrxWeeklyStock_Detail_ID));
	}

	/** Get Transaction Weekly Stock Detail.
		@return Transaction Weekly Stock Detail	  */
	public int getUNS_TrxWeeklyStock_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_TrxWeeklyStock_Detail_UU.
		@param UNS_TrxWeeklyStock_Detail_UU UNS_TrxWeeklyStock_Detail_UU	  */
	public void setUNS_TrxWeeklyStock_Detail_UU (String UNS_TrxWeeklyStock_Detail_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Detail_UU, UNS_TrxWeeklyStock_Detail_UU);
	}

	/** Get UNS_TrxWeeklyStock_Detail_UU.
		@return UNS_TrxWeeklyStock_Detail_UU	  */
	public String getUNS_TrxWeeklyStock_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_Detail_UU);
	}

	public com.unicore.model.I_UNS_TrxWeeklyStock_Line getUNS_TrxWeeklyStock_Line() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_TrxWeeklyStock_Line)MTable.get(getCtx(), com.unicore.model.I_UNS_TrxWeeklyStock_Line.Table_Name)
			.getPO(getUNS_TrxWeeklyStock_Line_ID(), get_TrxName());	}

	/** Set Transaction Weekly Line.
		@param UNS_TrxWeeklyStock_Line_ID Transaction Weekly Line	  */
	public void setUNS_TrxWeeklyStock_Line_ID (int UNS_TrxWeeklyStock_Line_ID)
	{
		if (UNS_TrxWeeklyStock_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_TrxWeeklyStock_Line_ID, Integer.valueOf(UNS_TrxWeeklyStock_Line_ID));
	}

	/** Get Transaction Weekly Line.
		@return Transaction Weekly Line	  */
	public int getUNS_TrxWeeklyStock_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_TrxWeeklyStock_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}