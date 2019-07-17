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

/** Generated Interface for UNS_DocumentValidator
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DocumentValidator 
{

    /** TableName=UNS_DocumentValidator */
    public static final String Table_Name = "UNS_DocumentValidator";

    /** AD_Table_ID=1000131 */
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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/** Set Window.
	  * Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID);

	/** Get Window.
	  * Data entry or display window
	  */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException;

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

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

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

    /** Column name IsAlwaysError */
    public static final String COLUMNNAME_IsAlwaysError = "IsAlwaysError";

	/** Set Always Error	  */
	public void setIsAlwaysError (boolean IsAlwaysError);

	/** Get Always Error	  */
	public boolean isAlwaysError();

    /** Column name IsMandatoryAttachment */
    public static final String COLUMNNAME_IsMandatoryAttachment = "IsMandatoryAttachment";

	/** Set Mandatory Attachment	  */
	public void setIsMandatoryAttachment (boolean IsMandatoryAttachment);

	/** Get Mandatory Attachment	  */
	public boolean isMandatoryAttachment();

    /** Column name IsMandatoryDescription */
    public static final String COLUMNNAME_IsMandatoryDescription = "IsMandatoryDescription";

	/** Set Mandatory Description	  */
	public void setIsMandatoryDescription (boolean IsMandatoryDescription);

	/** Get Mandatory Description	  */
	public boolean isMandatoryDescription();

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

    /** Column name OnCondition */
    public static final String COLUMNNAME_OnCondition = "OnCondition";

	/** Set On Condition	  */
	public void setOnCondition (String OnCondition);

	/** Get On Condition	  */
	public String getOnCondition();

    /** Column name Query */
    public static final String COLUMNNAME_Query = "Query";

	/** Set Query.
	  * SQL
	  */
	public void setQuery (String Query);

	/** Get Query.
	  * SQL
	  */
	public String getQuery();

    /** Column name UNS_DocumentValidator_ID */
    public static final String COLUMNNAME_UNS_DocumentValidator_ID = "UNS_DocumentValidator_ID";

	/** Set Document Validator	  */
	public void setUNS_DocumentValidator_ID (int UNS_DocumentValidator_ID);

	/** Get Document Validator	  */
	public int getUNS_DocumentValidator_ID();

    /** Column name UNS_DocumentValidator_UU */
    public static final String COLUMNNAME_UNS_DocumentValidator_UU = "UNS_DocumentValidator_UU";

	/** Set Document Validator UU	  */
	public void setUNS_DocumentValidator_UU (String UNS_DocumentValidator_UU);

	/** Get Document Validator UU	  */
	public String getUNS_DocumentValidator_UU();

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
