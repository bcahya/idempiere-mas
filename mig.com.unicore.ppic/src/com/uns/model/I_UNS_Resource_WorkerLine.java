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

/** Generated Interface for UNS_Resource_WorkerLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Resource_WorkerLine 
{

    /** TableName=UNS_Resource_WorkerLine */
    public static final String Table_Name = "UNS_Resource_WorkerLine";

    /** AD_Table_ID=1000082 */
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

    /** Column name IsAdditional */
    public static final String COLUMNNAME_IsAdditional = "IsAdditional";

	/** Set Additional.
	  * Check this box if this is additional
	  */
	public void setIsAdditional (boolean IsAdditional);

	/** Get Additional.
	  * Check this box if this is additional
	  */
	public boolean isAdditional();

    /** Column name IsPrimePortion */
    public static final String COLUMNNAME_IsPrimePortion = "IsPrimePortion";

	/** Set Prime Portion	  */
	public void setIsPrimePortion (boolean IsPrimePortion);

	/** Get Prime Portion	  */
	public boolean isPrimePortion();

    /** Column name Labor_ID */
    public static final String COLUMNNAME_Labor_ID = "Labor_ID";

	/** Set Labor ID.
	  * User ID or account number
	  */
	public void setLabor_ID (int Labor_ID);

	/** Get Labor ID.
	  * User ID or account number
	  */
	public int getLabor_ID();

    /** Column name PayrollTerm */
    public static final String COLUMNNAME_PayrollTerm = "PayrollTerm";

	/** Set Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm);

	/** Get Payroll Term	  */
	public String getPayrollTerm();

    /** Column name ResultProportion */
    public static final String COLUMNNAME_ResultProportion = "ResultProportion";

	/** Set Result Proportion (%)	  */
	public void setResultProportion (BigDecimal ResultProportion);

	/** Get Result Proportion (%)	  */
	public BigDecimal getResultProportion();

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

	public com.uns.model.I_UNS_Job_Role getUNS_Job_Role() throws RuntimeException;

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

    /** Column name UNS_Resource_WorkerLine_ID */
    public static final String COLUMNNAME_UNS_Resource_WorkerLine_ID = "UNS_Resource_WorkerLine_ID";

	/** Set Manufacturing Resource Worker Line	  */
	public void setUNS_Resource_WorkerLine_ID (int UNS_Resource_WorkerLine_ID);

	/** Get Manufacturing Resource Worker Line	  */
	public int getUNS_Resource_WorkerLine_ID();

    /** Column name UNS_Resource_WorkerLine_UU */
    public static final String COLUMNNAME_UNS_Resource_WorkerLine_UU = "UNS_Resource_WorkerLine_UU";

	/** Set UNS_Resource_WorkerLine_UU	  */
	public void setUNS_Resource_WorkerLine_UU (String UNS_Resource_WorkerLine_UU);

	/** Get UNS_Resource_WorkerLine_UU	  */
	public String getUNS_Resource_WorkerLine_UU();

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
