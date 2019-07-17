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

/** Generated Interface for UNS_Machine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Machine 
{

    /** TableName=UNS_Machine */
    public static final String Table_Name = "UNS_Machine";

    /** AD_Table_ID=1000028 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException;

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
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

    /** Column name IsTimeSlot */
    public static final String COLUMNNAME_IsTimeSlot = "IsTimeSlot";

	/** Set Time Slot.
	  * Resource has time slot availability
	  */
	public void setIsTimeSlot (boolean IsTimeSlot);

	/** Get Time Slot.
	  * Resource has time slot availability
	  */
	public boolean isTimeSlot();

    /** Column name MaxCaps */
    public static final String COLUMNNAME_MaxCaps = "MaxCaps";

	/** Set Max. Capaciy	  */
	public void setMaxCaps (BigDecimal MaxCaps);

	/** Get Max. Capaciy	  */
	public BigDecimal getMaxCaps();

    /** Column name MinCaps */
    public static final String COLUMNNAME_MinCaps = "MinCaps";

	/** Set Min. Capaciy	  */
	public void setMinCaps (BigDecimal MinCaps);

	/** Get Min. Capaciy	  */
	public BigDecimal getMinCaps();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name TimeSlotEnd */
    public static final String COLUMNNAME_TimeSlotEnd = "TimeSlotEnd";

	/** Set Slot End.
	  * Time when timeslot ends
	  */
	public void setTimeSlotEnd (Timestamp TimeSlotEnd);

	/** Get Slot End.
	  * Time when timeslot ends
	  */
	public Timestamp getTimeSlotEnd();

    /** Column name TimeSlotStart */
    public static final String COLUMNNAME_TimeSlotStart = "TimeSlotStart";

	/** Set Slot Start.
	  * Time when timeslot starts
	  */
	public void setTimeSlotStart (Timestamp TimeSlotStart);

	/** Get Slot Start.
	  * Time when timeslot starts
	  */
	public Timestamp getTimeSlotStart();

    /** Column name UNS_Machine_ID */
    public static final String COLUMNNAME_UNS_Machine_ID = "UNS_Machine_ID";

	/** Set Machine	  */
	public void setUNS_Machine_ID (int UNS_Machine_ID);

	/** Get Machine	  */
	public int getUNS_Machine_ID();

    /** Column name UNS_Machine_UU */
    public static final String COLUMNNAME_UNS_Machine_UU = "UNS_Machine_UU";

	/** Set UNS_Machine_UU	  */
	public void setUNS_Machine_UU (String UNS_Machine_UU);

	/** Get UNS_Machine_UU	  */
	public String getUNS_Machine_UU();

    /** Column name UOMCaps */
    public static final String COLUMNNAME_UOMCaps = "UOMCaps";

	/** Set UOM Capaciy	  */
	public void setUOMCaps (int UOMCaps);

	/** Get UOM Capaciy	  */
	public int getUOMCaps();

	public org.compiere.model.I_C_UOM getUOMC() throws RuntimeException;

    /** Column name UOMTime */
    public static final String COLUMNNAME_UOMTime = "UOMTime";

	/** Set UOM Time	  */
	public void setUOMTime (int UOMTime);

	/** Get UOM Time	  */
	public int getUOMTime();

	public org.compiere.model.I_C_UOM getUOMT() throws RuntimeException;

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
