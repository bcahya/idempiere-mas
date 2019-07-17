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

/** Generated Interface for UNS_MedicalDisease_Record
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_MedicalDisease_Record 
{

    /** TableName=UNS_MedicalDisease_Record */
    public static final String Table_Name = "UNS_MedicalDisease_Record";

    /** AD_Table_ID=1000146 */
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

    /** Column name pass_to_departement */
    public static final String COLUMNNAME_pass_to_departement = "pass_to_departement";

	/** Set pass_to_departement	  */
	public void setpass_to_departement (String pass_to_departement);

	/** Get pass_to_departement	  */
	public String getpass_to_departement();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name UNS_DiseaseList_ID */
    public static final String COLUMNNAME_UNS_DiseaseList_ID = "UNS_DiseaseList_ID";

	/** Set Disease List	  */
	public void setUNS_DiseaseList_ID (int UNS_DiseaseList_ID);

	/** Get Disease List	  */
	public int getUNS_DiseaseList_ID();

	public com.uns.model.I_UNS_DiseaseList getUNS_DiseaseList() throws RuntimeException;

    /** Column name UNS_MCU_Info_ID */
    public static final String COLUMNNAME_UNS_MCU_Info_ID = "UNS_MCU_Info_ID";

	/** Set MCU Info.
	  * UNS_MCU_Info_ID
	  */
	public void setUNS_MCU_Info_ID (int UNS_MCU_Info_ID);

	/** Get MCU Info.
	  * UNS_MCU_Info_ID
	  */
	public int getUNS_MCU_Info_ID();

	public com.uns.model.I_UNS_MCU_Info getUNS_MCU_Info() throws RuntimeException;

    /** Column name UNS_MedicalDisease_Record_ID */
    public static final String COLUMNNAME_UNS_MedicalDisease_Record_ID = "UNS_MedicalDisease_Record_ID";

	/** Set Medical Disease Record	  */
	public void setUNS_MedicalDisease_Record_ID (int UNS_MedicalDisease_Record_ID);

	/** Get Medical Disease Record	  */
	public int getUNS_MedicalDisease_Record_ID();

    /** Column name UNS_MedicalDisease_Record_UU */
    public static final String COLUMNNAME_UNS_MedicalDisease_Record_UU = "UNS_MedicalDisease_Record_UU";

	/** Set UNS_MedicalDisease_Record_UU	  */
	public void setUNS_MedicalDisease_Record_UU (String UNS_MedicalDisease_Record_UU);

	/** Get UNS_MedicalDisease_Record_UU	  */
	public String getUNS_MedicalDisease_Record_UU();

    /** Column name UNS_MedicalRecord_ID */
    public static final String COLUMNNAME_UNS_MedicalRecord_ID = "UNS_MedicalRecord_ID";

	/** Set Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID);

	/** Get Medical Record	  */
	public int getUNS_MedicalRecord_ID();

	public com.uns.model.I_UNS_MedicalRecord getUNS_MedicalRecord() throws RuntimeException;

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
