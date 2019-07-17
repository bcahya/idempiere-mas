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

/** Generated Interface for UNS_Mess_BuildingBlock
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Mess_BuildingBlock 
{

    /** TableName=UNS_Mess_BuildingBlock */
    public static final String Table_Name = "UNS_Mess_BuildingBlock";

    /** AD_Table_ID=1000155 */
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

    /** Column name Blok */
    public static final String COLUMNNAME_Blok = "Blok";

	/** Set Blok	  */
	public void setBlok (String Blok);

	/** Get Blok	  */
	public String getBlok();

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

    /** Column name MaximumToOccupy */
    public static final String COLUMNNAME_MaximumToOccupy = "MaximumToOccupy";

	/** Set Maximum To Occupy	  */
	public void setMaximumToOccupy (int MaximumToOccupy);

	/** Get Maximum To Occupy	  */
	public int getMaximumToOccupy();

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

    /** Column name Nomer */
    public static final String COLUMNNAME_Nomer = "Nomer";

	/** Set Number	  */
	public void setNomer (String Nomer);

	/** Get Number	  */
	public String getNomer();

    /** Column name OccupiedByOccupants */
    public static final String COLUMNNAME_OccupiedByOccupants = "OccupiedByOccupants";

	/** Set Occuped By Occupants	  */
	public void setOccupiedByOccupants (int OccupiedByOccupants);

	/** Get Occuped By Occupants	  */
	public int getOccupiedByOccupants();

    /** Column name UNS_Mess_BuildingBlock_ID */
    public static final String COLUMNNAME_UNS_Mess_BuildingBlock_ID = "UNS_Mess_BuildingBlock_ID";

	/** Set Mess Building Block	  */
	public void setUNS_Mess_BuildingBlock_ID (int UNS_Mess_BuildingBlock_ID);

	/** Get Mess Building Block	  */
	public int getUNS_Mess_BuildingBlock_ID();

    /** Column name UNS_Mess_BuildingBlock_UU */
    public static final String COLUMNNAME_UNS_Mess_BuildingBlock_UU = "UNS_Mess_BuildingBlock_UU";

	/** Set UNS Mess BuildingBlock UU	  */
	public void setUNS_Mess_BuildingBlock_UU (String UNS_Mess_BuildingBlock_UU);

	/** Get UNS Mess BuildingBlock UU	  */
	public String getUNS_Mess_BuildingBlock_UU();

    /** Column name UNS_RecidenceGroup_ID */
    public static final String COLUMNNAME_UNS_RecidenceGroup_ID = "UNS_RecidenceGroup_ID";

	/** Set Recidence Group	  */
	public void setUNS_RecidenceGroup_ID (int UNS_RecidenceGroup_ID);

	/** Get Recidence Group	  */
	public int getUNS_RecidenceGroup_ID();

	public com.uns.model.I_UNS_RecidenceGroup getUNS_RecidenceGroup() throws RuntimeException;

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

	/** Set Code.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Code.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
