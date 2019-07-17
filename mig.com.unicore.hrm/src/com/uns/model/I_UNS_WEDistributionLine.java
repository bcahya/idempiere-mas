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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_WEDistributionLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_WEDistributionLine 
{

    /** TableName=UNS_WEDistributionLine */
    public static final String Table_Name = "UNS_WEDistributionLine";

    /** AD_Table_ID=1000372 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name M_LocatorTo_ID */
    public static final String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";

	/** Set Locator To.
	  * Location inventory is moved to
	  */
	public void setM_LocatorTo_ID (int M_LocatorTo_ID);

	/** Get Locator To.
	  * Location inventory is moved to
	  */
	public int getM_LocatorTo_ID();

	public org.compiere.model.I_M_Locator getM_LocatorTo() throws RuntimeException;

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

    /** Column name QtyInternalUse */
    public static final String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

	/** Set Internal Use Qty.
	  * Internal Use Quantity removed from Inventory
	  */
	public void setQtyInternalUse (BigDecimal QtyInternalUse);

	/** Get Internal Use Qty.
	  * Internal Use Quantity removed from Inventory
	  */
	public BigDecimal getQtyInternalUse();

    /** Column name QtyPInternalUse */
    public static final String COLUMNNAME_QtyPInternalUse = "QtyPInternalUse";

	/** Set Previous Used Qty.
	  * Previous Internal Used Quantity removed from Inventory
	  */
	public void setQtyPInternalUse (BigDecimal QtyPInternalUse);

	/** Get Previous Used Qty.
	  * Previous Internal Used Quantity removed from Inventory
	  */
	public BigDecimal getQtyPInternalUse();

    /** Column name UNS_Mess_BuildingBlock_ID */
    public static final String COLUMNNAME_UNS_Mess_BuildingBlock_ID = "UNS_Mess_BuildingBlock_ID";

	/** Set Mess Building Block	  */
	public void setUNS_Mess_BuildingBlock_ID (int UNS_Mess_BuildingBlock_ID);

	/** Get Mess Building Block	  */
	public int getUNS_Mess_BuildingBlock_ID();

	public com.uns.model.I_UNS_Mess_BuildingBlock getUNS_Mess_BuildingBlock() throws RuntimeException;

    /** Column name UNS_Mess_Partition_ID */
    public static final String COLUMNNAME_UNS_Mess_Partition_ID = "UNS_Mess_Partition_ID";

	/** Set Mess	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID);

	/** Get Mess	  */
	public int getUNS_Mess_Partition_ID();

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException;

    /** Column name UNS_WEDistribution_ID */
    public static final String COLUMNNAME_UNS_WEDistribution_ID = "UNS_WEDistribution_ID";

	/** Set Water Electricity Distribution	  */
	public void setUNS_WEDistribution_ID (int UNS_WEDistribution_ID);

	/** Get Water Electricity Distribution	  */
	public int getUNS_WEDistribution_ID();

	public com.uns.model.I_UNS_WEDistribution getUNS_WEDistribution() throws RuntimeException;

    /** Column name UNS_WEDistributionLine_ID */
    public static final String COLUMNNAME_UNS_WEDistributionLine_ID = "UNS_WEDistributionLine_ID";

	/** Set Water Electricity Distribution Detail	  */
	public void setUNS_WEDistributionLine_ID (int UNS_WEDistributionLine_ID);

	/** Get Water Electricity Distribution Detail	  */
	public int getUNS_WEDistributionLine_ID();

    /** Column name UNS_WEDistributionLine_UU */
    public static final String COLUMNNAME_UNS_WEDistributionLine_UU = "UNS_WEDistributionLine_UU";

	/** Set UNS_WEDistributionLine_UU	  */
	public void setUNS_WEDistributionLine_UU (String UNS_WEDistributionLine_UU);

	/** Get UNS_WEDistributionLine_UU	  */
	public String getUNS_WEDistributionLine_UU();

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
