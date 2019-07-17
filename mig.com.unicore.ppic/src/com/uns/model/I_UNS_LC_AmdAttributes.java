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

/** Generated Interface for UNS_LC_AmdAttributes
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_LC_AmdAttributes 
{

    /** TableName=UNS_LC_AmdAttributes */
    public static final String Table_Name = "UNS_LC_AmdAttributes";

    /** AD_Table_ID=1000276 */
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

    /** Column name AttributeNumber */
    public static final String COLUMNNAME_AttributeNumber = "AttributeNumber";

	/** Set Attribute Number.
	  * Number code of the Attribute
	  */
	public void setAttributeNumber (String AttributeNumber);

	/** Get Attribute Number.
	  * Number code of the Attribute
	  */
	public String getAttributeNumber();

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

    /** Column name DataType */
    public static final String COLUMNNAME_DataType = "DataType";

	/** Set Data Type.
	  * Type of data
	  */
	public void setDataType (String DataType);

	/** Get Data Type.
	  * Type of data
	  */
	public String getDataType();

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

    /** Column name HasItems */
    public static final String COLUMNNAME_HasItems = "HasItems";

	/** Set Has Items	  */
	public void setHasItems (boolean HasItems);

	/** Get Has Items	  */
	public boolean isHasItems();

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

    /** Column name NewAttributeValueBD */
    public static final String COLUMNNAME_NewAttributeValueBD = "NewAttributeValueBD";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueBD (BigDecimal NewAttributeValueBD);

	/** Get New Attribute Value	  */
	public BigDecimal getNewAttributeValueBD();

    /** Column name NewAttributeValueDate */
    public static final String COLUMNNAME_NewAttributeValueDate = "NewAttributeValueDate";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueDate (Timestamp NewAttributeValueDate);

	/** Get New Attribute Value	  */
	public Timestamp getNewAttributeValueDate();

    /** Column name NewAttributeValueInt */
    public static final String COLUMNNAME_NewAttributeValueInt = "NewAttributeValueInt";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueInt (int NewAttributeValueInt);

	/** Get New Attribute Value	  */
	public int getNewAttributeValueInt();

    /** Column name NewAttributeValueMemo */
    public static final String COLUMNNAME_NewAttributeValueMemo = "NewAttributeValueMemo";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueMemo (String NewAttributeValueMemo);

	/** Get New Attribute Value	  */
	public String getNewAttributeValueMemo();

    /** Column name NewAttributeValueString */
    public static final String COLUMNNAME_NewAttributeValueString = "NewAttributeValueString";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueString (String NewAttributeValueString);

	/** Get New Attribute Value	  */
	public String getNewAttributeValueString();

    /** Column name NewAttributeValueText */
    public static final String COLUMNNAME_NewAttributeValueText = "NewAttributeValueText";

	/** Set New Attribute Value	  */
	public void setNewAttributeValueText (String NewAttributeValueText);

	/** Get New Attribute Value	  */
	public String getNewAttributeValueText();

    /** Column name NewCurrency_ID */
    public static final String COLUMNNAME_NewCurrency_ID = "NewCurrency_ID";

	/** Set New Currency	  */
	public void setNewCurrency_ID (int NewCurrency_ID);

	/** Get New Currency	  */
	public int getNewCurrency_ID();

	public org.compiere.model.I_C_Currency getNewCurrency() throws RuntimeException;

    /** Column name PrevAttributeValueBD */
    public static final String COLUMNNAME_PrevAttributeValueBD = "PrevAttributeValueBD";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueBD (BigDecimal PrevAttributeValueBD);

	/** Get Prev Attribute Value	  */
	public BigDecimal getPrevAttributeValueBD();

    /** Column name PrevAttributeValueDate */
    public static final String COLUMNNAME_PrevAttributeValueDate = "PrevAttributeValueDate";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueDate (Timestamp PrevAttributeValueDate);

	/** Get Prev Attribute Value	  */
	public Timestamp getPrevAttributeValueDate();

    /** Column name PrevAttributeValueInt */
    public static final String COLUMNNAME_PrevAttributeValueInt = "PrevAttributeValueInt";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueInt (int PrevAttributeValueInt);

	/** Get Prev Attribute Value	  */
	public int getPrevAttributeValueInt();

    /** Column name PrevAttributeValueMemo */
    public static final String COLUMNNAME_PrevAttributeValueMemo = "PrevAttributeValueMemo";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueMemo (String PrevAttributeValueMemo);

	/** Get Prev Attribute Value	  */
	public String getPrevAttributeValueMemo();

    /** Column name PrevAttributeValueString */
    public static final String COLUMNNAME_PrevAttributeValueString = "PrevAttributeValueString";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueString (String PrevAttributeValueString);

	/** Get Prev Attribute Value	  */
	public String getPrevAttributeValueString();

    /** Column name PrevAttributeValueText */
    public static final String COLUMNNAME_PrevAttributeValueText = "PrevAttributeValueText";

	/** Set Prev Attribute Value	  */
	public void setPrevAttributeValueText (String PrevAttributeValueText);

	/** Get Prev Attribute Value	  */
	public String getPrevAttributeValueText();

    /** Column name PrevCurrency_ID */
    public static final String COLUMNNAME_PrevCurrency_ID = "PrevCurrency_ID";

	/** Set Prev Currency	  */
	public void setPrevCurrency_ID (int PrevCurrency_ID);

	/** Get Prev Currency	  */
	public int getPrevCurrency_ID();

	public org.compiere.model.I_C_Currency getPrevCurrency() throws RuntimeException;

    /** Column name UNS_LC_AmdAttributes_ID */
    public static final String COLUMNNAME_UNS_LC_AmdAttributes_ID = "UNS_LC_AmdAttributes_ID";

	/** Set LC Amendment Attributes	  */
	public void setUNS_LC_AmdAttributes_ID (int UNS_LC_AmdAttributes_ID);

	/** Get LC Amendment Attributes	  */
	public int getUNS_LC_AmdAttributes_ID();

    /** Column name UNS_LC_AmdAttributes_UU */
    public static final String COLUMNNAME_UNS_LC_AmdAttributes_UU = "UNS_LC_AmdAttributes_UU";

	/** Set UNS_LC_AmdAttributes_UU	  */
	public void setUNS_LC_AmdAttributes_UU (String UNS_LC_AmdAttributes_UU);

	/** Get UNS_LC_AmdAttributes_UU	  */
	public String getUNS_LC_AmdAttributes_UU();

    /** Column name UNS_LC_Amendment_ID */
    public static final String COLUMNNAME_UNS_LC_Amendment_ID = "UNS_LC_Amendment_ID";

	/** Set LC Amendment	  */
	public void setUNS_LC_Amendment_ID (int UNS_LC_Amendment_ID);

	/** Get LC Amendment	  */
	public int getUNS_LC_Amendment_ID();

	public com.uns.model.I_UNS_LC_Amendment getUNS_LC_Amendment() throws RuntimeException;

    /** Column name UNS_LC_AttributeConf_ID */
    public static final String COLUMNNAME_UNS_LC_AttributeConf_ID = "UNS_LC_AttributeConf_ID";

	/** Set LC Attribute	  */
	public void setUNS_LC_AttributeConf_ID (int UNS_LC_AttributeConf_ID);

	/** Get LC Attribute	  */
	public int getUNS_LC_AttributeConf_ID();

	public com.uns.model.I_UNS_LC_AttributeConf getUNS_LC_AttributeConf() throws RuntimeException;

    /** Column name UNS_LC_Attributes_ID */
    public static final String COLUMNNAME_UNS_LC_Attributes_ID = "UNS_LC_Attributes_ID";

	/** Set LC Attributes	  */
	public void setUNS_LC_Attributes_ID (int UNS_LC_Attributes_ID);

	/** Get LC Attributes	  */
	public int getUNS_LC_Attributes_ID();

	public com.uns.model.I_UNS_LC_Attributes getUNS_LC_Attributes() throws RuntimeException;

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
