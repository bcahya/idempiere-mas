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

/** Generated Interface for UNS_PayRollPremiTarget
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_PayRollPremiTarget 
{

    /** TableName=UNS_PayRollPremiTarget */
    public static final String Table_Name = "UNS_PayRollPremiTarget";

    /** AD_Table_ID=1000304 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AbsenType */
    public static final String COLUMNNAME_AbsenType = "AbsenType";

	/** Set Absen Type	  */
	public void setAbsenType (String AbsenType);

	/** Get Absen Type	  */
	public String getAbsenType();

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

    /** Column name Pay */
    public static final String COLUMNNAME_Pay = "Pay";

	/** Set Pay	  */
	public void setPay (BigDecimal Pay);

	/** Get Pay	  */
	public BigDecimal getPay();

    /** Column name PayableAmt */
    public static final String COLUMNNAME_PayableAmt = "PayableAmt";

	/** Set Payable Amount	  */
	public void setPayableAmt (BigDecimal PayableAmt);

	/** Get Payable Amount	  */
	public BigDecimal getPayableAmt();

    /** Column name PayableToTarget_ID */
    public static final String COLUMNNAME_PayableToTarget_ID = "PayableToTarget_ID";

	/** Set Payable To Target	  */
	public void setPayableToTarget_ID (int PayableToTarget_ID);

	/** Get Payable To Target	  */
	public int getPayableToTarget_ID();

    /** Column name TargetMax */
    public static final String COLUMNNAME_TargetMax = "TargetMax";

	/** Set Target Max	  */
	public void setTargetMax (BigDecimal TargetMax);

	/** Get Target Max	  */
	public BigDecimal getTargetMax();

    /** Column name TargetMin */
    public static final String COLUMNNAME_TargetMin = "TargetMin";

	/** Set Target Min.
	  * Target Minimum
	  */
	public void setTargetMin (BigDecimal TargetMin);

	/** Get Target Min.
	  * Target Minimum
	  */
	public BigDecimal getTargetMin();

    /** Column name UNS_PayRollLine_ID */
    public static final String COLUMNNAME_UNS_PayRollLine_ID = "UNS_PayRollLine_ID";

	/** Set Pay Roll Line	  */
	public void setUNS_PayRollLine_ID (int UNS_PayRollLine_ID);

	/** Get Pay Roll Line	  */
	public int getUNS_PayRollLine_ID();

	public com.uns.model.I_UNS_PayRollLine getUNS_PayRollLine() throws RuntimeException;

    /** Column name UNS_PayRollPremiTarget_ID */
    public static final String COLUMNNAME_UNS_PayRollPremiTarget_ID = "UNS_PayRollPremiTarget_ID";

	/** Set Pay Roll Premi Target	  */
	public void setUNS_PayRollPremiTarget_ID (int UNS_PayRollPremiTarget_ID);

	/** Get Pay Roll Premi Target	  */
	public int getUNS_PayRollPremiTarget_ID();

    /** Column name UNS_PayRollPremiTarget_UU */
    public static final String COLUMNNAME_UNS_PayRollPremiTarget_UU = "UNS_PayRollPremiTarget_UU";

	/** Set UNS_PayRollPremiTarget_UU	  */
	public void setUNS_PayRollPremiTarget_UU (String UNS_PayRollPremiTarget_UU);

	/** Get UNS_PayRollPremiTarget_UU	  */
	public String getUNS_PayRollPremiTarget_UU();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
