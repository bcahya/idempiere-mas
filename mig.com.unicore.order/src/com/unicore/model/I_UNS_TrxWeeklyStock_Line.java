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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_TrxWeeklyStock_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_TrxWeeklyStock_Line 
{

    /** TableName=UNS_TrxWeeklyStock_Line */
    public static final String Table_Name = "UNS_TrxWeeklyStock_Line";

    /** AD_Table_ID=1000328 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsInTransit */
    public static final String COLUMNNAME_IsInTransit = "IsInTransit";

	/** Set In Transit.
	  * Movement is in transit
	  */
	public void setIsInTransit (boolean IsInTransit);

	/** Get In Transit.
	  * Movement is in transit
	  */
	public boolean isInTransit();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name MovementQtyIn */
    public static final String COLUMNNAME_MovementQtyIn = "MovementQtyIn";

	/** Set Movement Quantity In.
	  * Quantity of a product moved.
	  */
	public void setMovementQtyIn (BigDecimal MovementQtyIn);

	/** Get Movement Quantity In.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQtyIn();

    /** Column name MovementQtyOut */
    public static final String COLUMNNAME_MovementQtyOut = "MovementQtyOut";

	/** Set Movement Quantity Out.
	  * Quantity of a product moved.
	  */
	public void setMovementQtyOut (BigDecimal MovementQtyOut);

	/** Get Movement Quantity Out.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQtyOut();

    /** Column name OtherQtyIn */
    public static final String COLUMNNAME_OtherQtyIn = "OtherQtyIn";

	/** Set Other Qty In	  */
	public void setOtherQtyIn (BigDecimal OtherQtyIn);

	/** Get Other Qty In	  */
	public BigDecimal getOtherQtyIn();

    /** Column name OtherQtyOut */
    public static final String COLUMNNAME_OtherQtyOut = "OtherQtyOut";

	/** Set Other Qty Out	  */
	public void setOtherQtyOut (BigDecimal OtherQtyOut);

	/** Get Other Qty Out	  */
	public BigDecimal getOtherQtyOut();

    /** Column name ProductionQtyIn */
    public static final String COLUMNNAME_ProductionQtyIn = "ProductionQtyIn";

	/** Set Production Qty In	  */
	public void setProductionQtyIn (BigDecimal ProductionQtyIn);

	/** Get Production Qty In	  */
	public BigDecimal getProductionQtyIn();

    /** Column name ProductionQtyOut */
    public static final String COLUMNNAME_ProductionQtyOut = "ProductionQtyOut";

	/** Set Production Qty Out	  */
	public void setProductionQtyOut (BigDecimal ProductionQtyOut);

	/** Get Production Qty Out	  */
	public BigDecimal getProductionQtyOut();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Delivered Quantity.
	  * Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Delivered Quantity.
	  * Delivered Quantity
	  */
	public BigDecimal getQtyDelivered();

    /** Column name QtyOnDate */
    public static final String COLUMNNAME_QtyOnDate = "QtyOnDate";

	/** Set Qty On Date	  */
	public void setQtyOnDate (BigDecimal QtyOnDate);

	/** Get Qty On Date	  */
	public BigDecimal getQtyOnDate();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name QtyPacked */
    public static final String COLUMNNAME_QtyPacked = "QtyPacked";

	/** Set Quantity Packed	  */
	public void setQtyPacked (BigDecimal QtyPacked);

	/** Get Quantity Packed	  */
	public BigDecimal getQtyPacked();

    /** Column name QtyReceipt */
    public static final String COLUMNNAME_QtyReceipt = "QtyReceipt";

	/** Set Qty Receipt	  */
	public void setQtyReceipt (BigDecimal QtyReceipt);

	/** Get Qty Receipt	  */
	public BigDecimal getQtyReceipt();

    /** Column name QtyReturnC */
    public static final String COLUMNNAME_QtyReturnC = "QtyReturnC";

	/** Set Return Customer	  */
	public void setQtyReturnC (BigDecimal QtyReturnC);

	/** Get Return Customer	  */
	public BigDecimal getQtyReturnC();

    /** Column name QtyReturnV */
    public static final String COLUMNNAME_QtyReturnV = "QtyReturnV";

	/** Set Return Vendor	  */
	public void setQtyReturnV (BigDecimal QtyReturnV);

	/** Get Return Vendor	  */
	public BigDecimal getQtyReturnV();

    /** Column name QtySold */
    public static final String COLUMNNAME_QtySold = "QtySold";

	/** Set Quantity Sold	  */
	public void setQtySold (BigDecimal QtySold);

	/** Get Quantity Sold	  */
	public BigDecimal getQtySold();

    /** Column name Reference_ID */
    public static final String COLUMNNAME_Reference_ID = "Reference_ID";

	/** Set Refrerence Record	  */
	public void setReference_ID (int Reference_ID);

	/** Get Refrerence Record	  */
	public int getReference_ID();

	public com.unicore.model.I_UNS_TrxWeeklyStock_Line getReference() throws RuntimeException;

    /** Column name TransactionQty */
    public static final String COLUMNNAME_TransactionQty = "TransactionQty";

	/** Set Transaction Qty	  */
	public void setTransactionQty (BigDecimal TransactionQty);

	/** Get Transaction Qty	  */
	public BigDecimal getTransactionQty();

    /** Column name UNS_TrxWeeklyStock_ID */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_ID = "UNS_TrxWeeklyStock_ID";

	/** Set Transaction Weekly Stock	  */
	public void setUNS_TrxWeeklyStock_ID (int UNS_TrxWeeklyStock_ID);

	/** Get Transaction Weekly Stock	  */
	public int getUNS_TrxWeeklyStock_ID();

	public com.unicore.model.I_UNS_TrxWeeklyStock getUNS_TrxWeeklyStock() throws RuntimeException;

    /** Column name UNS_TrxWeeklyStock_Line_ID */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_Line_ID = "UNS_TrxWeeklyStock_Line_ID";

	/** Set Transaction Weekly Line	  */
	public void setUNS_TrxWeeklyStock_Line_ID (int UNS_TrxWeeklyStock_Line_ID);

	/** Get Transaction Weekly Line	  */
	public int getUNS_TrxWeeklyStock_Line_ID();

    /** Column name UNS_TrxWeeklyStock_Line_UU */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_Line_UU = "UNS_TrxWeeklyStock_Line_UU";

	/** Set UNS_TrxWeeklyStock_Line_UU	  */
	public void setUNS_TrxWeeklyStock_Line_UU (String UNS_TrxWeeklyStock_Line_UU);

	/** Get UNS_TrxWeeklyStock_Line_UU	  */
	public String getUNS_TrxWeeklyStock_Line_UU();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
