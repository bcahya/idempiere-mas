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

/** Generated Interface for UNS_Mess_Partition
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Mess_Partition 
{

    /** TableName=UNS_Mess_Partition */
    public static final String Table_Name = "UNS_Mess_Partition";

    /** AD_Table_ID=1000157 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AvailableToOccupy */
    public static final String COLUMNNAME_AvailableToOccupy = "AvailableToOccupy";

	/** Set Available To Occupy	  */
	public void setAvailableToOccupy (int AvailableToOccupy);

	/** Get Available To Occupy	  */
	public int getAvailableToOccupy();

    /** Column name Blok_Room_No */
    public static final String COLUMNNAME_Blok_Room_No = "Blok_Room_No";

	/** Set Blok Room No	  */
	public void setBlok_Room_No (String Blok_Room_No);

	/** Get Blok Room No	  */
	public String getBlok_Room_No();

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

    /** Column name LastElectricityUsed */
    public static final String COLUMNNAME_LastElectricityUsed = "LastElectricityUsed";

	/** Set Last Electricity Used.
	  * Last Electricity Used from Inventory
	  */
	public void setLastElectricityUsed (BigDecimal LastElectricityUsed);

	/** Get Last Electricity Used.
	  * Last Electricity Used from Inventory
	  */
	public BigDecimal getLastElectricityUsed();

    /** Column name LastWaterUsed */
    public static final String COLUMNNAME_LastWaterUsed = "LastWaterUsed";

	/** Set Last Water Used.
	  * Last WaterUsed from Inventory
	  */
	public void setLastWaterUsed (BigDecimal LastWaterUsed);

	/** Get Last Water Used.
	  * Last WaterUsed from Inventory
	  */
	public BigDecimal getLastWaterUsed();

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

    /** Column name MaximumToOccupy */
    public static final String COLUMNNAME_MaximumToOccupy = "MaximumToOccupy";

	/** Set Maximum To Occupy	  */
	public void setMaximumToOccupy (int MaximumToOccupy);

	/** Get Maximum To Occupy	  */
	public int getMaximumToOccupy();

    /** Column name OccupiedByOccupants */
    public static final String COLUMNNAME_OccupiedByOccupants = "OccupiedByOccupants";

	/** Set Occuped By Occupants	  */
	public void setOccupiedByOccupants (int OccupiedByOccupants);

	/** Get Occuped By Occupants	  */
	public int getOccupiedByOccupants();

    /** Column name PayableTo */
    public static final String COLUMNNAME_PayableTo = "PayableTo";

	/** Set Payable To	  */
	public void setPayableTo (String PayableTo);

	/** Get Payable To	  */
	public String getPayableTo();

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

    /** Column name UNS_Mess_Partition_UU */
    public static final String COLUMNNAME_UNS_Mess_Partition_UU = "UNS_Mess_Partition_UU";

	/** Set UNS Mess Partition UU	  */
	public void setUNS_Mess_Partition_UU (String UNS_Mess_Partition_UU);

	/** Get UNS Mess Partition UU	  */
	public String getUNS_Mess_Partition_UU();

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
