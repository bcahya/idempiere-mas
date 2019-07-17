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

/** Generated Interface for UNS_Resource_Transition
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Resource_Transition 
{

    /** TableName=UNS_Resource_Transition */
    public static final String Table_Name = "UNS_Resource_Transition";

    /** AD_Table_ID=1000047 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name IsDirectBOM */
    public static final String COLUMNNAME_IsDirectBOM = "IsDirectBOM";

	/** Set Is Direct BOM.
	  * To indicates if output of previous resource are direct BOM to next resource's uoutput
	  */
	public void setIsDirectBOM (boolean IsDirectBOM);

	/** Get Is Direct BOM.
	  * To indicates if output of previous resource are direct BOM to next resource's uoutput
	  */
	public boolean isDirectBOM();

    /** Column name IsEndNode */
    public static final String COLUMNNAME_IsEndNode = "IsEndNode";

	/** Set End Node	  */
	public void setIsEndNode (boolean IsEndNode);

	/** Get End Node	  */
	public boolean isEndNode();

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

    /** Column name NextResource_ID */
    public static final String COLUMNNAME_NextResource_ID = "NextResource_ID";

	/** Set Next Resource	  */
	public void setNextResource_ID (int NextResource_ID);

	/** Get Next Resource	  */
	public int getNextResource_ID();

	public com.uns.model.I_UNS_Resource getNextResource() throws RuntimeException;

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

    /** Column name UNS_Resource_Transition_ID */
    public static final String COLUMNNAME_UNS_Resource_Transition_ID = "UNS_Resource_Transition_ID";

	/** Set Manufacturing Resource Transition	  */
	public void setUNS_Resource_Transition_ID (int UNS_Resource_Transition_ID);

	/** Get Manufacturing Resource Transition	  */
	public int getUNS_Resource_Transition_ID();

    /** Column name UNS_Resource_Transition_UU */
    public static final String COLUMNNAME_UNS_Resource_Transition_UU = "UNS_Resource_Transition_UU";

	/** Set UNS_Resource_Transition_UU	  */
	public void setUNS_Resource_Transition_UU (String UNS_Resource_Transition_UU);

	/** Get UNS_Resource_Transition_UU	  */
	public String getUNS_Resource_Transition_UU();

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
