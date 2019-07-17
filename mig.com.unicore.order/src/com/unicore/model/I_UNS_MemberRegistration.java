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

/** Generated Interface for UNS_MemberRegistration
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_MemberRegistration 
{

    /** TableName=UNS_MemberRegistration */
    public static final String Table_Name = "UNS_MemberRegistration";

    /** AD_Table_ID=1000224 */
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

    /** Column name BankAccountNo */
    public static final String COLUMNNAME_BankAccountNo = "BankAccountNo";

	/** Set Bank Account No.
	  * Bank Account Number
	  */
	public void setBankAccountNo (String BankAccountNo);

	/** Get Bank Account No.
	  * Bank Account Number
	  */
	public String getBankAccountNo();

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

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

    /** Column name NIP */
    public static final String COLUMNNAME_NIP = "NIP";

	/** Set NIP.
	  * The registered identifier of member, bpartner, or employee.
	  */
	public void setNIP (String NIP);

	/** Get NIP.
	  * The registered identifier of member, bpartner, or employee.
	  */
	public String getNIP();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

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

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/** Set Reason	  */
	public void setReason (String Reason);

	/** Get Reason	  */
	public String getReason();

    /** Column name UNS_AvailableAccount */
    public static final String COLUMNNAME_UNS_AvailableAccount = "UNS_AvailableAccount";

	/** Set A/n Rekening	  */
	public void setUNS_AvailableAccount (boolean UNS_AvailableAccount);

	/** Get A/n Rekening	  */
	public boolean isUNS_AvailableAccount();

    /** Column name UNS_Golongan_ID */
    public static final String COLUMNNAME_UNS_Golongan_ID = "UNS_Golongan_ID";

	/** Set Golongan ID	  */
	public void setUNS_Golongan_ID (int UNS_Golongan_ID);

	/** Get Golongan ID	  */
	public int getUNS_Golongan_ID();

	public I_UNS_Golongan getUNS_Golongan() throws RuntimeException;

    /** Column name UNS_LatestStatus */
    public static final String COLUMNNAME_UNS_LatestStatus = "UNS_LatestStatus";

	/** Set LatestStatus.
	  * Latest Status of Members
	  */
	public void setUNS_LatestStatus (String UNS_LatestStatus);

	/** Get LatestStatus.
	  * Latest Status of Members
	  */
	public String getUNS_LatestStatus();

    /** Column name UNS_LatestUnregisteredDate */
    public static final String COLUMNNAME_UNS_LatestUnregisteredDate = "UNS_LatestUnregisteredDate";

	/** Set Latest Unregistered Date.
	  * Date of Unregistered
	  */
	public void setUNS_LatestUnregisteredDate (Timestamp UNS_LatestUnregisteredDate);

	/** Get Latest Unregistered Date.
	  * Date of Unregistered
	  */
	public Timestamp getUNS_LatestUnregisteredDate();

    /** Column name UNS_MemberRegistration_ID */
    public static final String COLUMNNAME_UNS_MemberRegistration_ID = "UNS_MemberRegistration_ID";

	/** Set Member Registration ID	  */
	public void setUNS_MemberRegistration_ID (int UNS_MemberRegistration_ID);

	/** Get Member Registration ID	  */
	public int getUNS_MemberRegistration_ID();

    /** Column name UNS_memberRegistration_UU */
    public static final String COLUMNNAME_UNS_memberRegistration_UU = "UNS_memberRegistration_UU";

	/** Set Member Registration UU	  */
	public void setUNS_memberRegistration_UU (String UNS_memberRegistration_UU);

	/** Get Member Registration UU	  */
	public String getUNS_memberRegistration_UU();

    /** Column name UNS_NoRekening */
    public static final String COLUMNNAME_UNS_NoRekening = "UNS_NoRekening";

	/** Set Account Numbers	  */
	public void setUNS_NoRekening (String UNS_NoRekening);

	/** Get Account Numbers	  */
	public String getUNS_NoRekening();

    /** Column name UNS_PSavingRequired */
    public static final String COLUMNNAME_UNS_PSavingRequired = "UNS_PSavingRequired";

	/** Set Primary Saving Required.
	  * Primary Saving Requierd when Members Registration
	  */
	public void setUNS_PSavingRequired (boolean UNS_PSavingRequired);

	/** Get Primary Saving Required.
	  * Primary Saving Requierd when Members Registration
	  */
	public boolean isUNS_PSavingRequired();

    /** Column name UNS_SimPokokIsAmust */
    public static final String COLUMNNAME_UNS_SimPokokIsAmust = "UNS_SimPokokIsAmust";

	/** Set Simpanan Pokok Is Amusti	  */
	public void setUNS_SimPokokIsAmust (boolean UNS_SimPokokIsAmust);

	/** Get Simpanan Pokok Is Amusti	  */
	public boolean isUNS_SimPokokIsAmust();

    /** Column name UNS_StoranSimPokok */
    public static final String COLUMNNAME_UNS_StoranSimPokok = "UNS_StoranSimPokok";

	/** Set Setoran Simpanan Pokok	  */
	public void setUNS_StoranSimPokok (int UNS_StoranSimPokok);

	/** Get Setoran Simpanan Pokok	  */
	public int getUNS_StoranSimPokok();

    /** Column name UNS_TMT */
    public static final String COLUMNNAME_UNS_TMT = "UNS_TMT";

	/** Set TMT.
	  * Date of Registered or Unregistred Members
	  */
	public void setUNS_TMT (Timestamp UNS_TMT);

	/** Get TMT.
	  * Date of Registered or Unregistred Members
	  */
	public Timestamp getUNS_TMT();

    /** Column name UNS_Unit */
    public static final String COLUMNNAME_UNS_Unit = "UNS_Unit";

	/** Set Unit	  */
	public void setUNS_Unit (String UNS_Unit);

	/** Get Unit	  */
	public String getUNS_Unit();

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
