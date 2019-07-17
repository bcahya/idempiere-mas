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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QAStatus_NC
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QAStatus_NC 
{

    /** TableName=UNS_QAStatus_NC */
    public static final String Table_Name = "UNS_QAStatus_NC";

    /** AD_Table_ID=1000184 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AccByDT */
    public static final String COLUMNNAME_AccByDT = "AccByDT";

	/** Set Date Time	  */
	public void setAccByDT (Timestamp AccByDT);

	/** Get Date Time	  */
	public Timestamp getAccByDT();

    /** Column name AccByName */
    public static final String COLUMNNAME_AccByName = "AccByName";

	/** Set Name	  */
	public void setAccByName (String AccByName);

	/** Get Name	  */
	public String getAccByName();

    /** Column name ActByDT */
    public static final String COLUMNNAME_ActByDT = "ActByDT";

	/** Set Date Time	  */
	public void setActByDT (Timestamp ActByDT);

	/** Get Date Time	  */
	public Timestamp getActByDT();

    /** Column name ActByName */
    public static final String COLUMNNAME_ActByName = "ActByName";

	/** Set Name	  */
	public void setActByName (String ActByName);

	/** Get Name	  */
	public String getActByName();

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

    /** Column name IsComplete */
    public static final String COLUMNNAME_IsComplete = "IsComplete";

	/** Set Complete.
	  * It is complete
	  */
	public void setIsComplete (String IsComplete);

	/** Get Complete.
	  * It is complete
	  */
	public String getIsComplete();

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

    /** Column name ProposedCorectiveAction */
    public static final String COLUMNNAME_ProposedCorectiveAction = "ProposedCorectiveAction";

	/** Set Proposed Corective Action	  */
	public void setProposedCorectiveAction (String ProposedCorectiveAction);

	/** Get Proposed Corective Action	  */
	public String getProposedCorectiveAction();

    /** Column name QADisposition */
    public static final String COLUMNNAME_QADisposition = "QADisposition";

	/** Set QA Disposition.
	  * QA Disposition and/or Proposed Corrective. Action for Non-conformances
	  */
	public void setQADisposition (String QADisposition);

	/** Get QA Disposition.
	  * QA Disposition and/or Proposed Corrective. Action for Non-conformances
	  */
	public String getQADisposition();

    /** Column name UNS_QAStatus_NC_ID */
    public static final String COLUMNNAME_UNS_QAStatus_NC_ID = "UNS_QAStatus_NC_ID";

	/** Set QA Status Non-Conformance	  */
	public void setUNS_QAStatus_NC_ID (int UNS_QAStatus_NC_ID);

	/** Get QA Status Non-Conformance	  */
	public int getUNS_QAStatus_NC_ID();

    /** Column name UNS_QAStatus_NC_UU */
    public static final String COLUMNNAME_UNS_QAStatus_NC_UU = "UNS_QAStatus_NC_UU";

	/** Set UNS_QAStatus_NC_UU	  */
	public void setUNS_QAStatus_NC_UU (String UNS_QAStatus_NC_UU);

	/** Get UNS_QAStatus_NC_UU	  */
	public String getUNS_QAStatus_NC_UU();

    /** Column name UNS_QAStatusContainer_ID */
    public static final String COLUMNNAME_UNS_QAStatusContainer_ID = "UNS_QAStatusContainer_ID";

	/** Set QA Status Container	  */
	public void setUNS_QAStatusContainer_ID (int UNS_QAStatusContainer_ID);

	/** Get QA Status Container	  */
	public int getUNS_QAStatusContainer_ID();

	public com.uns.qad.model.I_UNS_QAStatusContainer getUNS_QAStatusContainer() throws RuntimeException;

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

    /** Column name VrfByDT */
    public static final String COLUMNNAME_VrfByDT = "VrfByDT";

	/** Set Date Time	  */
	public void setVrfByDT (Timestamp VrfByDT);

	/** Get Date Time	  */
	public Timestamp getVrfByDT();

    /** Column name VrfByName */
    public static final String COLUMNNAME_VrfByName = "VrfByName";

	/** Set Name	  */
	public void setVrfByName (String VrfByName);

	/** Get Name	  */
	public String getVrfByName();
}
