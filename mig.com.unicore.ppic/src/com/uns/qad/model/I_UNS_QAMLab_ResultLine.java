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

/** Generated Interface for UNS_QAMLab_ResultLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QAMLab_ResultLine 
{

    /** TableName=UNS_QAMLab_ResultLine */
    public static final String Table_Name = "UNS_QAMLab_ResultLine";

    /** AD_Table_ID=1000290 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name Operator */
    public static final String COLUMNNAME_Operator = "Operator";

	/** Set Operator Name	  */
	public void setOperator (String Operator);

	/** Get Operator Name	  */
	public String getOperator();

    /** Column name Remark */
    public static final String COLUMNNAME_Remark = "Remark";

	/** Set Remark	  */
	public void setRemark (String Remark);

	/** Get Remark	  */
	public String getRemark();

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (BigDecimal Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public BigDecimal getResult();

    /** Column name Result2 */
    public static final String COLUMNNAME_Result2 = "Result2";

	/** Set Second Result.
	  * Result of the action taken
	  */
	public void setResult2 (String Result2);

	/** Get Second Result.
	  * Result of the action taken
	  */
	public String getResult2();

    /** Column name UNS_QALabTest_ID */
    public static final String COLUMNNAME_UNS_QALabTest_ID = "UNS_QALabTest_ID";

	/** Set Laboratory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID);

	/** Get Laboratory Test	  */
	public int getUNS_QALabTest_ID();

	public com.uns.qad.model.I_UNS_QALabTest getUNS_QALabTest() throws RuntimeException;

    /** Column name UNS_QAMLab_Result_ID */
    public static final String COLUMNNAME_UNS_QAMLab_Result_ID = "UNS_QAMLab_Result_ID";

	/** Set Laboratory Result	  */
	public void setUNS_QAMLab_Result_ID (int UNS_QAMLab_Result_ID);

	/** Get Laboratory Result	  */
	public int getUNS_QAMLab_Result_ID();

	public com.uns.qad.model.I_UNS_QAMLab_Result getUNS_QAMLab_Result() throws RuntimeException;

    /** Column name UNS_QAMLab_ResultInfo_ID */
    public static final String COLUMNNAME_UNS_QAMLab_ResultInfo_ID = "UNS_QAMLab_ResultInfo_ID";

	/** Set Laboratory Result Info	  */
	public void setUNS_QAMLab_ResultInfo_ID (int UNS_QAMLab_ResultInfo_ID);

	/** Get Laboratory Result Info	  */
	public int getUNS_QAMLab_ResultInfo_ID();

	public com.uns.qad.model.I_UNS_QAMLab_ResultInfo getUNS_QAMLab_ResultInfo() throws RuntimeException;

    /** Column name UNS_QAMLab_ResultLine_ID */
    public static final String COLUMNNAME_UNS_QAMLab_ResultLine_ID = "UNS_QAMLab_ResultLine_ID";

	/** Set Laboratory Result Line	  */
	public void setUNS_QAMLab_ResultLine_ID (int UNS_QAMLab_ResultLine_ID);

	/** Get Laboratory Result Line	  */
	public int getUNS_QAMLab_ResultLine_ID();

    /** Column name UNS_QAMLab_ResultLine_UU */
    public static final String COLUMNNAME_UNS_QAMLab_ResultLine_UU = "UNS_QAMLab_ResultLine_UU";

	/** Set UNS_QAMLab_ResultLine_UU	  */
	public void setUNS_QAMLab_ResultLine_UU (String UNS_QAMLab_ResultLine_UU);

	/** Get UNS_QAMLab_ResultLine_UU	  */
	public String getUNS_QAMLab_ResultLine_UU();

    /** Column name UNS_QAMonitoringLabLine_ID */
    public static final String COLUMNNAME_UNS_QAMonitoringLabLine_ID = "UNS_QAMonitoringLabLine_ID";

	/** Set Monitoring Test	  */
	public void setUNS_QAMonitoringLabLine_ID (int UNS_QAMonitoringLabLine_ID);

	/** Get Monitoring Test	  */
	public int getUNS_QAMonitoringLabLine_ID();

	public com.uns.qad.model.I_UNS_QAMonitoringLabLine getUNS_QAMonitoringLabLine() throws RuntimeException;

    /** Column name UNS_WO_RequestLine_ID */
    public static final String COLUMNNAME_UNS_WO_RequestLine_ID = "UNS_WO_RequestLine_ID";

	/** Set WO Request Line	  */
	public void setUNS_WO_RequestLine_ID (int UNS_WO_RequestLine_ID);

	/** Get WO Request Line	  */
	public int getUNS_WO_RequestLine_ID();

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
