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

/** Generated Interface for UNS_LC_Balance
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_LC_Balance 
{

    /** TableName=UNS_LC_Balance */
    public static final String Table_Name = "UNS_LC_Balance";

    /** AD_Table_ID=1000327 */
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

    /** Column name BankPriority */
    public static final String COLUMNNAME_BankPriority = "BankPriority";

	/** Set Bank Priority	  */
	public void setBankPriority (String BankPriority);

	/** Get Bank Priority	  */
	public String getBankPriority();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

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

    /** Column name FormatCode */
    public static final String COLUMNNAME_FormatCode = "FormatCode";

	/** Set Format Code	  */
	public void setFormatCode (String FormatCode);

	/** Get Format Code	  */
	public String getFormatCode();

    /** Column name FromCode */
    public static final String COLUMNNAME_FromCode = "FromCode";

	/** Set From	  */
	public void setFromCode (String FromCode);

	/** Get From	  */
	public String getFromCode();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name IssuingBank */
    public static final String COLUMNNAME_IssuingBank = "IssuingBank";

	/** Set Issuing Bank	  */
	public void setIssuingBank (String IssuingBank);

	/** Get Issuing Bank	  */
	public String getIssuingBank();

    /** Column name LC_No */
    public static final String COLUMNNAME_LC_No = "LC_No";

	/** Set LC No	  */
	public void setLC_No (String LC_No);

	/** Get LC No	  */
	public String getLC_No();

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

    /** Column name OBS_Period */
    public static final String COLUMNNAME_OBS_Period = "OBS_Period";

	/** Set OBS_Period	  */
	public void setOBS_Period (int OBS_Period);

	/** Get OBS_Period	  */
	public int getOBS_Period();

    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/** Set Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority);

	/** Get Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Reciver */
    public static final String COLUMNNAME_Reciver = "Reciver";

	/** Set Reciver	  */
	public void setReciver (String Reciver);

	/** Get Reciver	  */
	public String getReciver();

    /** Column name SwiftCode */
    public static final String COLUMNNAME_SwiftCode = "SwiftCode";

	/** Set Swift code.
	  * Swift Code or BIC
	  */
	public void setSwiftCode (String SwiftCode);

	/** Get Swift code.
	  * Swift Code or BIC
	  */
	public String getSwiftCode();

    /** Column name ToleranceAmt */
    public static final String COLUMNNAME_ToleranceAmt = "ToleranceAmt";

	/** Set Tolerance (%).
	  * Tolerance amount in percent values (i.e. 10 for 10%)
	  */
	public void setToleranceAmt (BigDecimal ToleranceAmt);

	/** Get Tolerance (%).
	  * Tolerance amount in percent values (i.e. 10 for 10%)
	  */
	public BigDecimal getToleranceAmt();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name TotalAmtLeft */
    public static final String COLUMNNAME_TotalAmtLeft = "TotalAmtLeft";

	/** Set Total Amt Left	  */
	public void setTotalAmtLeft (BigDecimal TotalAmtLeft);

	/** Get Total Amt Left	  */
	public BigDecimal getTotalAmtLeft();

    /** Column name TotalAmtUsed */
    public static final String COLUMNNAME_TotalAmtUsed = "TotalAmtUsed";

	/** Set Total Amt Used	  */
	public void setTotalAmtUsed (BigDecimal TotalAmtUsed);

	/** Get Total Amt Used	  */
	public BigDecimal getTotalAmtUsed();

    /** Column name TrxNo */
    public static final String COLUMNNAME_TrxNo = "TrxNo";

	/** Set Trx No	  */
	public void setTrxNo (int TrxNo);

	/** Get Trx No	  */
	public int getTrxNo();

    /** Column name UNS_LC_Balance_ID */
    public static final String COLUMNNAME_UNS_LC_Balance_ID = "UNS_LC_Balance_ID";

	/** Set LC Balance	  */
	public void setUNS_LC_Balance_ID (int UNS_LC_Balance_ID);

	/** Get LC Balance	  */
	public int getUNS_LC_Balance_ID();

    /** Column name UNS_LC_Balance_UU */
    public static final String COLUMNNAME_UNS_LC_Balance_UU = "UNS_LC_Balance_UU";

	/** Set UNS_LC_Balance_UU	  */
	public void setUNS_LC_Balance_UU (String UNS_LC_Balance_UU);

	/** Get UNS_LC_Balance_UU	  */
	public String getUNS_LC_Balance_UU();

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

    /** Column name UserRef */
    public static final String COLUMNNAME_UserRef = "UserRef";

	/** Set User Ref	  */
	public void setUserRef (String UserRef);

	/** Get User Ref	  */
	public String getUserRef();

    /** Column name VerNo */
    public static final String COLUMNNAME_VerNo = "VerNo";

	/** Set Ver No	  */
	public void setVerNo (int VerNo);

	/** Get Ver No	  */
	public int getVerNo();
}
