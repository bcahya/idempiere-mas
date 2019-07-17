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

/** Generated Interface for UNS_TrxWeeklyStock_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_TrxWeeklyStock_Detail 
{

    /** TableName=UNS_TrxWeeklyStock_Detail */
    public static final String Table_Name = "UNS_TrxWeeklyStock_Detail";

    /** AD_Table_ID=1000329 */
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

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/** Set Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/** Get Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException;

    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/** Set Phys.Inventory.
	  * Parameters for a Physical Inventory
	  */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/** Get Phys.Inventory.
	  * Parameters for a Physical Inventory
	  */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException;

    /** Column name M_InventoryLine_ID */
    public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/** Set Phys.Inventory Line.
	  * Unique line in an Inventory document
	  */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/** Get Phys.Inventory Line.
	  * Unique line in an Inventory document
	  */
	public int getM_InventoryLine_ID();

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine() throws RuntimeException;

    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/** Set Inventory Move.
	  * Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID);

	/** Get Inventory Move.
	  * Movement of Inventory
	  */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException;

    /** Column name M_MovementLine_ID */
    public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	/** Set Move Line.
	  * Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID);

	/** Get Move Line.
	  * Inventory Move document Line
	  */
	public int getM_MovementLine_ID();

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException;

    /** Column name M_Transaction_ID */
    public static final String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";

	/** Set Inventory Transaction	  */
	public void setM_Transaction_ID (int M_Transaction_ID);

	/** Get Inventory Transaction	  */
	public int getM_Transaction_ID();

	public org.compiere.model.I_M_Transaction getM_Transaction() throws RuntimeException;

    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/** Set Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate);

	/** Get Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate();

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

    /** Column name MovementType */
    public static final String COLUMNNAME_MovementType = "MovementType";

	/** Set Movement Type.
	  * Method of moving the inventory
	  */
	public void setMovementType (String MovementType);

	/** Get Movement Type.
	  * Method of moving the inventory
	  */
	public String getMovementType();

    /** Column name UNS_PackingList_ID */
    public static final String COLUMNNAME_UNS_PackingList_ID = "UNS_PackingList_ID";

	/** Set Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID);

	/** Get Packing List	  */
	public int getUNS_PackingList_ID();

    /** Column name UNS_PL_ConfirmProduct_ID */
    public static final String COLUMNNAME_UNS_PL_ConfirmProduct_ID = "UNS_PL_ConfirmProduct_ID";

	/** Set Confirmation Product	  */
	public void setUNS_PL_ConfirmProduct_ID (int UNS_PL_ConfirmProduct_ID);

	/** Get Confirmation Product	  */
	public int getUNS_PL_ConfirmProduct_ID();

    /** Column name UNS_Production_Detail_ID */
    public static final String COLUMNNAME_UNS_Production_Detail_ID = "UNS_Production_Detail_ID";

	/** Set Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID);

	/** Get Production Detail	  */
	public int getUNS_Production_Detail_ID();

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

    /** Column name UNS_TrxWeeklyStock_Detail_ID */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_Detail_ID = "UNS_TrxWeeklyStock_Detail_ID";

	/** Set Transaction Weekly Stock Detail	  */
	public void setUNS_TrxWeeklyStock_Detail_ID (int UNS_TrxWeeklyStock_Detail_ID);

	/** Get Transaction Weekly Stock Detail	  */
	public int getUNS_TrxWeeklyStock_Detail_ID();

    /** Column name UNS_TrxWeeklyStock_Detail_UU */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_Detail_UU = "UNS_TrxWeeklyStock_Detail_UU";

	/** Set UNS_TrxWeeklyStock_Detail_UU	  */
	public void setUNS_TrxWeeklyStock_Detail_UU (String UNS_TrxWeeklyStock_Detail_UU);

	/** Get UNS_TrxWeeklyStock_Detail_UU	  */
	public String getUNS_TrxWeeklyStock_Detail_UU();

    /** Column name UNS_TrxWeeklyStock_Line_ID */
    public static final String COLUMNNAME_UNS_TrxWeeklyStock_Line_ID = "UNS_TrxWeeklyStock_Line_ID";

	/** Set Transaction Weekly Line	  */
	public void setUNS_TrxWeeklyStock_Line_ID (int UNS_TrxWeeklyStock_Line_ID);

	/** Get Transaction Weekly Line	  */
	public int getUNS_TrxWeeklyStock_Line_ID();

	public com.unicore.model.I_UNS_TrxWeeklyStock_Line getUNS_TrxWeeklyStock_Line() throws RuntimeException;

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
