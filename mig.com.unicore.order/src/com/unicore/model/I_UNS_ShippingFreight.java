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

/** Generated Interface for UNS_ShippingFreight
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ShippingFreight 
{

    /** TableName=UNS_ShippingFreight */
    public static final String Table_Name = "UNS_ShippingFreight";

    /** AD_Table_ID=1000283 */
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

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name FreightType */
    public static final String COLUMNNAME_FreightType = "FreightType";

	/** Set Freight Type	  */
	public void setFreightType (String FreightType);

	/** Get Freight Type	  */
	public String getFreightType();

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

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Tonase */
    public static final String COLUMNNAME_Tonase = "Tonase";

	/** Set Tonase.
	  * Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase);

	/** Get Tonase.
	  * Indicate total tonase
	  */
	public BigDecimal getTonase();

    /** Column name UNS_Expedition_ID */
    public static final String COLUMNNAME_UNS_Expedition_ID = "UNS_Expedition_ID";

	/** Set Expedition	  */
	public void setUNS_Expedition_ID (int UNS_Expedition_ID);

	/** Get Expedition	  */
	public int getUNS_Expedition_ID();

    /** Column name UNS_PackingList_ID */
    public static final String COLUMNNAME_UNS_PackingList_ID = "UNS_PackingList_ID";

	/** Set Packing List	  */
	public void setUNS_PackingList_ID (int UNS_PackingList_ID);

	/** Get Packing List	  */
	public int getUNS_PackingList_ID();

    /** Column name UNS_ShippingFreight_ID */
    public static final String COLUMNNAME_UNS_ShippingFreight_ID = "UNS_ShippingFreight_ID";

	/** Set Freight	  */
	public void setUNS_ShippingFreight_ID (int UNS_ShippingFreight_ID);

	/** Get Freight	  */
	public int getUNS_ShippingFreight_ID();

    /** Column name UNS_ShippingFreight_UU */
    public static final String COLUMNNAME_UNS_ShippingFreight_UU = "UNS_ShippingFreight_UU";

	/** Set UNS_ShippingFreight_UU	  */
	public void setUNS_ShippingFreight_UU (String UNS_ShippingFreight_UU);

	/** Get UNS_ShippingFreight_UU	  */
	public String getUNS_ShippingFreight_UU();

    /** Column name UNS_Shipping_ID */
    public static final String COLUMNNAME_UNS_Shipping_ID = "UNS_Shipping_ID";

	/** Set Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID);

	/** Get Shipping Document	  */
	public int getUNS_Shipping_ID();

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

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();
}
