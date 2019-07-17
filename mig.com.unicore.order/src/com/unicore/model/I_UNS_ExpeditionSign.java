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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_ExpeditionSign
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ExpeditionSign 
{

    /** TableName=UNS_ExpeditionSign */
    public static final String Table_Name = "UNS_ExpeditionSign";

    /** AD_Table_ID=1000320 */
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

    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

	/** Set Address	  */
	public void setAddress (String Address);

	/** Get Address	  */
	public String getAddress();

    /** Column name BPartnerName */
    public static final String COLUMNNAME_BPartnerName = "BPartnerName";

	/** Set BPartnerName	  */
	public void setBPartnerName (String BPartnerName);

	/** Get BPartnerName	  */
	public String getBPartnerName();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

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

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name UNS_ExpeditionDetailPO_ID */
    public static final String COLUMNNAME_UNS_ExpeditionDetailPO_ID = "UNS_ExpeditionDetailPO_ID";

	/** Set Expedition Detail PO	  */
	public void setUNS_ExpeditionDetailPO_ID (int UNS_ExpeditionDetailPO_ID);

	/** Get Expedition Detail PO	  */
	public int getUNS_ExpeditionDetailPO_ID();

	public com.unicore.model.I_UNS_ExpeditionDetail getUNS_ExpeditionDetailPO() throws RuntimeException;

    /** Column name UNS_ExpeditionDetailSO_ID */
    public static final String COLUMNNAME_UNS_ExpeditionDetailSO_ID = "UNS_ExpeditionDetailSO_ID";

	/** Set Expedition Detail SO	  */
	public void setUNS_ExpeditionDetailSO_ID (int UNS_ExpeditionDetailSO_ID);

	/** Get Expedition Detail SO	  */
	public int getUNS_ExpeditionDetailSO_ID();

	public com.unicore.model.I_UNS_ExpeditionDetail getUNS_ExpeditionDetailSO() throws RuntimeException;

    /** Column name UNS_ExpeditionSign_ID */
    public static final String COLUMNNAME_UNS_ExpeditionSign_ID = "UNS_ExpeditionSign_ID";

	/** Set Expedition Sign	  */
	public void setUNS_ExpeditionSign_ID (int UNS_ExpeditionSign_ID);

	/** Get Expedition Sign	  */
	public int getUNS_ExpeditionSign_ID();

    /** Column name UNS_ExpeditionSign_UU */
    public static final String COLUMNNAME_UNS_ExpeditionSign_UU = "UNS_ExpeditionSign_UU";

	/** Set UNS_ExpeditionSign_UU	  */
	public void setUNS_ExpeditionSign_UU (String UNS_ExpeditionSign_UU);

	/** Get UNS_ExpeditionSign_UU	  */
	public String getUNS_ExpeditionSign_UU();

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
