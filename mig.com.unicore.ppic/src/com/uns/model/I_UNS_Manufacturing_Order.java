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

/** Generated Interface for UNS_Manufacturing_Order
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Manufacturing_Order 
{

    /** TableName=UNS_Manufacturing_Order */
    public static final String Table_Name = "UNS_Manufacturing_Order";

    /** AD_Table_ID=1000346 */
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

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name CreateProduction */
    public static final String COLUMNNAME_CreateProduction = "CreateProduction";

	/** Set Create Production	  */
	public void setCreateProduction (String CreateProduction);

	/** Get Create Production	  */
	public String getCreateProduction();

    /** Column name CreatePSSOAllocation */
    public static final String COLUMNNAME_CreatePSSOAllocation = "CreatePSSOAllocation";

	/** Set Create SO Allocation	  */
	public void setCreatePSSOAllocation (String CreatePSSOAllocation);

	/** Get Create SO Allocation	  */
	public String getCreatePSSOAllocation();

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name Notes */
    public static final String COLUMNNAME_Notes = "Notes";

	/** Set Notes.
	  * Notes to the schedule from Planner Department
	  */
	public void setNotes (String Notes);

	/** Get Notes.
	  * Notes to the schedule from Planner Department
	  */
	public String getNotes();

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

    /** Column name ProductionDepartment_ID */
    public static final String COLUMNNAME_ProductionDepartment_ID = "ProductionDepartment_ID";

	/** Set Production Department.
	  * Department to do the production
	  */
	public void setProductionDepartment_ID (int ProductionDepartment_ID);

	/** Get Production Department.
	  * Department to do the production
	  */
	public int getProductionDepartment_ID();

    /** Column name Target_PD_End */
    public static final String COLUMNNAME_Target_PD_End = "Target_PD_End";

	/** Set Target Production Date End	  */
	public void setTarget_PD_End (Timestamp Target_PD_End);

	/** Get Target Production Date End	  */
	public Timestamp getTarget_PD_End();

    /** Column name Target_PD_Start */
    public static final String COLUMNNAME_Target_PD_Start = "Target_PD_Start";

	/** Set Target Production Date Start	  */
	public void setTarget_PD_Start (Timestamp Target_PD_Start);

	/** Get Target Production Date Start	  */
	public Timestamp getTarget_PD_Start();

    /** Column name UNS_Manufacturing_Order_ID */
    public static final String COLUMNNAME_UNS_Manufacturing_Order_ID = "UNS_Manufacturing_Order_ID";

	/** Set Manufacturing Order	  */
	public void setUNS_Manufacturing_Order_ID (int UNS_Manufacturing_Order_ID);

	/** Get Manufacturing Order	  */
	public int getUNS_Manufacturing_Order_ID();

    /** Column name UNS_Manufacturing_Order_UU */
    public static final String COLUMNNAME_UNS_Manufacturing_Order_UU = "UNS_Manufacturing_Order_UU";

	/** Set UNS_Manufacturing_Order_UU	  */
	public void setUNS_Manufacturing_Order_UU (String UNS_Manufacturing_Order_UU);

	/** Get UNS_Manufacturing_Order_UU	  */
	public String getUNS_Manufacturing_Order_UU();

    /** Column name UNS_MPSHeader_ID */
    public static final String COLUMNNAME_UNS_MPSHeader_ID = "UNS_MPSHeader_ID";

	/** Set Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID);

	/** Get Master Production Schedule	  */
	public int getUNS_MPSHeader_ID();

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException;

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

    /** Column name WeekNo */
    public static final String COLUMNNAME_WeekNo = "WeekNo";

	/** Set Week No	  */
	public void setWeekNo (String WeekNo);

	/** Get Week No	  */
	public String getWeekNo();
}
