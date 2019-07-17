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

/** Generated Interface for UNS_LC_AttrItems
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_LC_AttrItems 
{

    /** TableName=UNS_LC_AttrItems */
    public static final String Table_Name = "UNS_LC_AttrItems";

    /** AD_Table_ID=1000278 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name ItemNo */
    public static final String COLUMNNAME_ItemNo = "ItemNo";

	/** Set Item No	  */
	public void setItemNo (int ItemNo);

	/** Get Item No	  */
	public int getItemNo();

    /** Column name UNS_LC_Attributes_ID */
    public static final String COLUMNNAME_UNS_LC_Attributes_ID = "UNS_LC_Attributes_ID";

	/** Set LC Attributes	  */
	public void setUNS_LC_Attributes_ID (int UNS_LC_Attributes_ID);

	/** Get LC Attributes	  */
	public int getUNS_LC_Attributes_ID();

	public com.uns.model.I_UNS_LC_Attributes getUNS_LC_Attributes() throws RuntimeException;

    /** Column name UNS_LC_AttrItems_ID */
    public static final String COLUMNNAME_UNS_LC_AttrItems_ID = "UNS_LC_AttrItems_ID";

	/** Set LC Attribute Items	  */
	public void setUNS_LC_AttrItems_ID (int UNS_LC_AttrItems_ID);

	/** Get LC Attribute Items	  */
	public int getUNS_LC_AttrItems_ID();

    /** Column name UNS_LC_AttrItems_UU */
    public static final String COLUMNNAME_UNS_LC_AttrItems_UU = "UNS_LC_AttrItems_UU";

	/** Set UNS_LC_AttrItems_UU	  */
	public void setUNS_LC_AttrItems_UU (String UNS_LC_AttrItems_UU);

	/** Get UNS_LC_AttrItems_UU	  */
	public String getUNS_LC_AttrItems_UU();

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
