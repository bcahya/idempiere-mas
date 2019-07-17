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

/** Generated Interface for UNS_Contract_Evaluation
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Contract_Evaluation 
{

    /** TableName=UNS_Contract_Evaluation */
    public static final String Table_Name = "UNS_Contract_Evaluation";

    /** AD_Table_ID=1000176 */
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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Employee Dept..
	  * Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Employee Dept..
	  * Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID();

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

    /** Column name CreateNewSalary */
    public static final String COLUMNNAME_CreateNewSalary = "CreateNewSalary";

	/** Set Create New Contract	  */
	public void setCreateNewSalary (String CreateNewSalary);

	/** Get Create New Contract	  */
	public String getCreateNewSalary();

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

    /** Column name EvalDetailRefNo */
    public static final String COLUMNNAME_EvalDetailRefNo = "EvalDetailRefNo";

	/** Set Evaluation Detail Ref No	  */
	public void setEvalDetailRefNo (String EvalDetailRefNo);

	/** Get Evaluation Detail Ref No	  */
	public String getEvalDetailRefNo();

    /** Column name Grade */
    public static final String COLUMNNAME_Grade = "Grade";

	/** Set Grade	  */
	public void setGrade (String Grade);

	/** Get Grade	  */
	public String getGrade();

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

    /** Column name LastContractDate */
    public static final String COLUMNNAME_LastContractDate = "LastContractDate";

	/** Set Last Contract Date	  */
	public void setLastContractDate (Timestamp LastContractDate);

	/** Get Last Contract Date	  */
	public Timestamp getLastContractDate();

    /** Column name LastEndContractDate */
    public static final String COLUMNNAME_LastEndContractDate = "LastEndContractDate";

	/** Set Last End Contract Date	  */
	public void setLastEndContractDate (Timestamp LastEndContractDate);

	/** Get Last End Contract Date	  */
	public Timestamp getLastEndContractDate();

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

    /** Column name Recommendation */
    public static final String COLUMNNAME_Recommendation = "Recommendation";

	/** Set Recommendation	  */
	public void setRecommendation (String Recommendation);

	/** Get Recommendation	  */
	public String getRecommendation();

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name TotalGrade */
    public static final String COLUMNNAME_TotalGrade = "TotalGrade";

	/** Set Total Grade	  */
	public void setTotalGrade (BigDecimal TotalGrade);

	/** Get Total Grade	  */
	public BigDecimal getTotalGrade();

    /** Column name UNS_Contract_Evaluation_ID */
    public static final String COLUMNNAME_UNS_Contract_Evaluation_ID = "UNS_Contract_Evaluation_ID";

	/** Set Contract Evaluation	  */
	public void setUNS_Contract_Evaluation_ID (int UNS_Contract_Evaluation_ID);

	/** Get Contract Evaluation	  */
	public int getUNS_Contract_Evaluation_ID();

    /** Column name UNS_Contract_Evaluation_UU */
    public static final String COLUMNNAME_UNS_Contract_Evaluation_UU = "UNS_Contract_Evaluation_UU";

	/** Set UNS_Contract_Evaluation_UU	  */
	public void setUNS_Contract_Evaluation_UU (String UNS_Contract_Evaluation_UU);

	/** Get UNS_Contract_Evaluation_UU	  */
	public String getUNS_Contract_Evaluation_UU();

    /** Column name UNS_Contract_Recommendation_ID */
    public static final String COLUMNNAME_UNS_Contract_Recommendation_ID = "UNS_Contract_Recommendation_ID";

	/** Set Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID);

	/** Get Contract	  */
	public int getUNS_Contract_Recommendation_ID();

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException;

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

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
