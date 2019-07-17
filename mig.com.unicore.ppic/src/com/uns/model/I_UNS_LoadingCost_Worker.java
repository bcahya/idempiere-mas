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

/** Generated Interface for UNS_LoadingCost_Worker
 *  @author iDempiere (generated) 
 *  @version Release 2.0
 */
@SuppressWarnings("all")
public interface I_UNS_LoadingCost_Worker 
{

    /** TableName=UNS_LoadingCost_Worker */
    public static final String Table_Name = "UNS_LoadingCost_Worker";

    /** AD_Table_ID=1000119 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name InsentifPemborong */
    public static final String COLUMNNAME_InsentifPemborong = "InsentifPemborong";

	/** Set Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong);

	/** Get Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong();

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

    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/** Set Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/** Get Is Subcontracting	  */
	public boolean isSubcontracting();

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

    /** Column name ReceivableAmt */
    public static final String COLUMNNAME_ReceivableAmt = "ReceivableAmt";

	/** Set Receivable Amt	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt);

	/** Get Receivable Amt	  */
	public BigDecimal getReceivableAmt();

    /** Column name ReplacementLabor_ID */
    public static final String COLUMNNAME_ReplacementLabor_ID = "ReplacementLabor_ID";

	/** Set Replacement Labor	  */
	public void setReplacementLabor_ID (int ReplacementLabor_ID);

	/** Get Replacement Labor	  */
	public int getReplacementLabor_ID();

    /** Column name UNS_Job_Role_ID */
    public static final String COLUMNNAME_UNS_Job_Role_ID = "UNS_Job_Role_ID";

	/** Set Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID);

	/** Get Job Role	  */
	public int getUNS_Job_Role_ID();

    /** Column name UNS_LoadingCost_ID */
    public static final String COLUMNNAME_UNS_LoadingCost_ID = "UNS_LoadingCost_ID";

	/** Set Loading Cost	  */
	public void setUNS_LoadingCost_ID (int UNS_LoadingCost_ID);

	/** Get Loading Cost	  */
	public int getUNS_LoadingCost_ID();

	public com.uns.model.I_UNS_LoadingCost getUNS_LoadingCost() throws RuntimeException;

    /** Column name UNS_LoadingCost_Worker_ID */
    public static final String COLUMNNAME_UNS_LoadingCost_Worker_ID = "UNS_LoadingCost_Worker_ID";

	/** Set Loading Cost Worker	  */
	public void setUNS_LoadingCost_Worker_ID (int UNS_LoadingCost_Worker_ID);

	/** Get Loading Cost Worker	  */
	public int getUNS_LoadingCost_Worker_ID();

    /** Column name UNS_LoadingCost_Worker_UU */
    public static final String COLUMNNAME_UNS_LoadingCost_Worker_UU = "UNS_LoadingCost_Worker_UU";

	/** Set UNS_LoadingCost_Worker_UU	  */
	public void setUNS_LoadingCost_Worker_UU (String UNS_LoadingCost_Worker_UU);

	/** Get UNS_LoadingCost_Worker_UU	  */
	public String getUNS_LoadingCost_Worker_UU();

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
