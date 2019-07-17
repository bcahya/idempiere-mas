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

/** Generated Interface for UNS_DocSchema_MapCol
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DocSchema_MapCol 
{

    /** TableName=UNS_DocSchema_MapCol */
    public static final String Table_Name = "UNS_DocSchema_MapCol";

    /** AD_Table_ID=1000306 */
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

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Column.
	  * Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Column.
	  * Column in the table
	  */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException;

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

    /** Column name ColumnReferer_ID */
    public static final String COLUMNNAME_ColumnReferer_ID = "ColumnReferer_ID";

	/** Set Column Referer	  */
	public void setColumnReferer_ID (int ColumnReferer_ID);

	/** Get Column Referer	  */
	public int getColumnReferer_ID();

	public org.compiere.model.I_AD_Column getColumnReferer() throws RuntimeException;

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

    /** Column name UNS_DocSchema_MapCol_ID */
    public static final String COLUMNNAME_UNS_DocSchema_MapCol_ID = "UNS_DocSchema_MapCol_ID";

	/** Set Mapping Column	  */
	public void setUNS_DocSchema_MapCol_ID (int UNS_DocSchema_MapCol_ID);

	/** Get Mapping Column	  */
	public int getUNS_DocSchema_MapCol_ID();

    /** Column name UNS_DocSchema_MapCol_UU */
    public static final String COLUMNNAME_UNS_DocSchema_MapCol_UU = "UNS_DocSchema_MapCol_UU";

	/** Set UNS_DocSchema_MapCol_UU	  */
	public void setUNS_DocSchema_MapCol_UU (String UNS_DocSchema_MapCol_UU);

	/** Get UNS_DocSchema_MapCol_UU	  */
	public String getUNS_DocSchema_MapCol_UU();

    /** Column name UNS_DocumentSchema_ID */
    public static final String COLUMNNAME_UNS_DocumentSchema_ID = "UNS_DocumentSchema_ID";

	/** Set Document Schema	  */
	public void setUNS_DocumentSchema_ID (int UNS_DocumentSchema_ID);

	/** Get Document Schema	  */
	public int getUNS_DocumentSchema_ID();

	public com.uns.model.I_UNS_DocumentSchema getUNS_DocumentSchema() throws RuntimeException;

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
