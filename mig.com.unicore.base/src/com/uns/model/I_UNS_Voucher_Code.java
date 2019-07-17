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

/** Generated Interface for UNS_Voucher_Code
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Voucher_Code 
{

    /** TableName=UNS_Voucher_Code */
    public static final String Table_Name = "UNS_Voucher_Code";

    /** AD_Table_ID=1000245 */
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

    /** Column name IsUsed */
    public static final String COLUMNNAME_IsUsed = "IsUsed";

	/** Set Used.
	  * The document has ben used ?
	  */
	public void setIsUsed (boolean IsUsed);

	/** Get Used.
	  * The document has ben used ?
	  */
	public boolean isUsed();

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

    /** Column name UNS_Voucher_Code_ID */
    public static final String COLUMNNAME_UNS_Voucher_Code_ID = "UNS_Voucher_Code_ID";

	/** Set Voucher Code	  */
	public void setUNS_Voucher_Code_ID (int UNS_Voucher_Code_ID);

	/** Get Voucher Code	  */
	public int getUNS_Voucher_Code_ID();

    /** Column name UNS_Voucher_Code_UU */
    public static final String COLUMNNAME_UNS_Voucher_Code_UU = "UNS_Voucher_Code_UU";

	/** Set UNS_Voucher_Code_UU	  */
	public void setUNS_Voucher_Code_UU (String UNS_Voucher_Code_UU);

	/** Get UNS_Voucher_Code_UU	  */
	public String getUNS_Voucher_Code_UU();

    /** Column name UNS_Voucher_ID */
    public static final String COLUMNNAME_UNS_Voucher_ID = "UNS_Voucher_ID";

	/** Set Voucher	  */
	public void setUNS_Voucher_ID (int UNS_Voucher_ID);

	/** Get Voucher	  */
	public int getUNS_Voucher_ID();

	public com.uns.model.I_UNS_Voucher getUNS_Voucher() throws RuntimeException;

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

    /** Column name UseDate */
    public static final String COLUMNNAME_UseDate = "UseDate";

	/** Set UseDate	  */
	public void setUseDate (Timestamp UseDate);

	/** Get UseDate	  */
	public Timestamp getUseDate();
}
