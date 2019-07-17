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

/** Generated Interface for UNS_SalesTargetLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_SalesTargetLine 
{

    /** TableName=UNS_SalesTargetLine */
    public static final String Table_Name = "UNS_SalesTargetLine";

    /** AD_Table_ID=1000147 */
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

    /** Column name Deduction */
    public static final String COLUMNNAME_Deduction = "Deduction";

	/** Set Deduction	  */
	public void setDeduction (BigDecimal Deduction);

	/** Get Deduction	  */
	public BigDecimal getDeduction();

    /** Column name GenerateList */
    public static final String COLUMNNAME_GenerateList = "GenerateList";

	/** Set Generate List.
	  * Generate List
	  */
	public void setGenerateList (String GenerateList);

	/** Get Generate List.
	  * Generate List
	  */
	public String getGenerateList();

    /** Column name Incentive */
    public static final String COLUMNNAME_Incentive = "Incentive";

	/** Set Incentive	  */
	public void setIncentive (BigDecimal Incentive);

	/** Get Incentive	  */
	public BigDecimal getIncentive();

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

    /** Column name IsUseSameSchemaForEveryPeriod */
    public static final String COLUMNNAME_IsUseSameSchemaForEveryPeriod = "IsUseSameSchemaForEveryPeriod";

	/** Set Use Same Schema For Every Period	  */
	public void setIsUseSameSchemaForEveryPeriod (boolean IsUseSameSchemaForEveryPeriod);

	/** Get Use Same Schema For Every Period	  */
	public boolean isUseSameSchemaForEveryPeriod();

    /** Column name SalesLevel */
    public static final String COLUMNNAME_SalesLevel = "SalesLevel";

	/** Set Sales Level	  */
	public void setSalesLevel (String SalesLevel);

	/** Get Sales Level	  */
	public String getSalesLevel();

    /** Column name SalesType */
    public static final String COLUMNNAME_SalesType = "SalesType";

	/** Set Sales Type.
	  * Not Defined
	  */
	public void setSalesType (String SalesType);

	/** Get Sales Type.
	  * Not Defined
	  */
	public String getSalesType();

    /** Column name TargetPeriodic */
    public static final String COLUMNNAME_TargetPeriodic = "TargetPeriodic";

	/** Set Target Periodic.
	  * Discont target periodic for target break type periodic
	  */
	public void setTargetPeriodic (String TargetPeriodic);

	/** Get Target Periodic.
	  * Discont target periodic for target break type periodic
	  */
	public String getTargetPeriodic();

    /** Column name TOPTarget */
    public static final String COLUMNNAME_TOPTarget = "TOPTarget";

	/** Set Top Target.
	  * Term of Payment target.
	  */
	public void setTOPTarget (BigDecimal TOPTarget);

	/** Get Top Target.
	  * Term of Payment target.
	  */
	public BigDecimal getTOPTarget();

    /** Column name UNS_SalesTarget_ID */
    public static final String COLUMNNAME_UNS_SalesTarget_ID = "UNS_SalesTarget_ID";

	/** Set Sales Target	  */
	public void setUNS_SalesTarget_ID (int UNS_SalesTarget_ID);

	/** Get Sales Target	  */
	public int getUNS_SalesTarget_ID();

    /** Column name UNS_SalesTargetLine_ID */
    public static final String COLUMNNAME_UNS_SalesTargetLine_ID = "UNS_SalesTargetLine_ID";

	/** Set Sales Target Line	  */
	public void setUNS_SalesTargetLine_ID (int UNS_SalesTargetLine_ID);

	/** Get Sales Target Line	  */
	public int getUNS_SalesTargetLine_ID();

    /** Column name UNS_SalesTargetLine_UU */
    public static final String COLUMNNAME_UNS_SalesTargetLine_UU = "UNS_SalesTargetLine_UU";

	/** Set UNS_SalesTargetLine_UU	  */
	public void setUNS_SalesTargetLine_UU (String UNS_SalesTargetLine_UU);

	/** Get UNS_SalesTargetLine_UU	  */
	public String getUNS_SalesTargetLine_UU();

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

    /** Column name ValueTarget */
    public static final String COLUMNNAME_ValueTarget = "ValueTarget";

	/** Set Value Target	  */
	public void setValueTarget (BigDecimal ValueTarget);

	/** Get Value Target	  */
	public BigDecimal getValueTarget();
}
