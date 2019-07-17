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

/** Generated Interface for UNS_Payroll_CostBenefit
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Payroll_CostBenefit 
{

    /** TableName=UNS_Payroll_CostBenefit */
    public static final String Table_Name = "UNS_Payroll_CostBenefit";

    /** AD_Table_ID=1000365 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name CostBenefit_Acct */
    public static final String COLUMNNAME_CostBenefit_Acct = "CostBenefit_Acct";

	/** Set Cost/Benefit Acct.
	  * The accounting element of this cost/benefit
	  */
	public void setCostBenefit_Acct (int CostBenefit_Acct);

	/** Get Cost/Benefit Acct.
	  * The accounting element of this cost/benefit
	  */
	public int getCostBenefit_Acct();

	public I_C_ValidCombination getCostBenefit_A() throws RuntimeException;

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

    /** Column name IsBenefit */
    public static final String COLUMNNAME_IsBenefit = "IsBenefit";

	/** Set Is Benefit?.
	  * If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public void setIsBenefit (boolean IsBenefit);

	/** Get Is Benefit?.
	  * If checked it is a benefit and will be added to employee's payroll, otherwise it is a cost and will be deducted.
	  */
	public boolean isBenefit();

    /** Column name IsPPHComp */
    public static final String COLUMNNAME_IsPPHComp = "IsPPHComp";

	/** Set PPH Component ?	  */
	public void setIsPPHComp (boolean IsPPHComp);

	/** Get PPH Component ?	  */
	public boolean isPPHComp();

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

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name UNS_Payroll_Component_Conf_ID */
    public static final String COLUMNNAME_UNS_Payroll_Component_Conf_ID = "UNS_Payroll_Component_Conf_ID";

	/** Set Payroll Component Configuration	  */
	public void setUNS_Payroll_Component_Conf_ID (int UNS_Payroll_Component_Conf_ID);

	/** Get Payroll Component Configuration	  */
	public int getUNS_Payroll_Component_Conf_ID();

	public com.uns.model.I_UNS_Payroll_Component_Conf getUNS_Payroll_Component_Conf() throws RuntimeException;

    /** Column name UNS_Payroll_CostBenefit_ID */
    public static final String COLUMNNAME_UNS_Payroll_CostBenefit_ID = "UNS_Payroll_CostBenefit_ID";

	/** Set Payroll Cost/Benefit	  */
	public void setUNS_Payroll_CostBenefit_ID (int UNS_Payroll_CostBenefit_ID);

	/** Get Payroll Cost/Benefit	  */
	public int getUNS_Payroll_CostBenefit_ID();

    /** Column name UNS_Payroll_CostBenefit_UU */
    public static final String COLUMNNAME_UNS_Payroll_CostBenefit_UU = "UNS_Payroll_CostBenefit_UU";

	/** Set UNS_Payroll_CostBenefit_UU	  */
	public void setUNS_Payroll_CostBenefit_UU (String UNS_Payroll_CostBenefit_UU);

	/** Get UNS_Payroll_CostBenefit_UU	  */
	public String getUNS_Payroll_CostBenefit_UU();

    /** Column name UNS_Payroll_Employee_ID */
    public static final String COLUMNNAME_UNS_Payroll_Employee_ID = "UNS_Payroll_Employee_ID";

	/** Set Payroll Employee	  */
	public void setUNS_Payroll_Employee_ID (int UNS_Payroll_Employee_ID);

	/** Get Payroll Employee	  */
	public int getUNS_Payroll_Employee_ID();

	public com.uns.model.I_UNS_Payroll_Employee getUNS_Payroll_Employee() throws RuntimeException;

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
