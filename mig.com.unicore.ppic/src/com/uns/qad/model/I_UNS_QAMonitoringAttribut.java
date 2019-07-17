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

/** Generated Interface for UNS_QAMonitoringAttribut
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QAMonitoringAttribut 
{

    /** TableName=UNS_QAMonitoringAttribut */
    public static final String Table_Name = "UNS_QAMonitoringAttribut";

    /** AD_Table_ID=1000237 */
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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name IsCalculated */
    public static final String COLUMNNAME_IsCalculated = "IsCalculated";

	/** Set Calculated.
	  * The value is calculated by the system
	  */
	public void setIsCalculated (boolean IsCalculated);

	/** Get Calculated.
	  * The value is calculated by the system
	  */
	public boolean isCalculated();

    /** Column name needLabResult */
    public static final String COLUMNNAME_needLabResult = "needLabResult";

	/** Set Confirmation with Lab Result	  */
	public void setneedLabResult (boolean needLabResult);

	/** Get Confirmation with Lab Result	  */
	public boolean isneedLabResult();

    /** Column name UNS_QAAttributeTest_ID */
    public static final String COLUMNNAME_UNS_QAAttributeTest_ID = "UNS_QAAttributeTest_ID";

	/** Set Attribute Test	  */
	public void setUNS_QAAttributeTest_ID (int UNS_QAAttributeTest_ID);

	/** Get Attribute Test	  */
	public int getUNS_QAAttributeTest_ID();

	public I_UNS_QAAttributeTest getUNS_QAAttributeTest() throws RuntimeException;

    /** Column name UNS_QAMonitoringAttribut_ID */
    public static final String COLUMNNAME_UNS_QAMonitoringAttribut_ID = "UNS_QAMonitoringAttribut_ID";

	/** Set Monitoring Attribut	  */
	public void setUNS_QAMonitoringAttribut_ID (int UNS_QAMonitoringAttribut_ID);

	/** Get Monitoring Attribut	  */
	public int getUNS_QAMonitoringAttribut_ID();

    /** Column name UNS_QAMonitoringAttribut_UU */
    public static final String COLUMNNAME_UNS_QAMonitoringAttribut_UU = "UNS_QAMonitoringAttribut_UU";

	/** Set UNS_QAMonitoringAttribut_UU	  */
	public void setUNS_QAMonitoringAttribut_UU (String UNS_QAMonitoringAttribut_UU);

	/** Get UNS_QAMonitoringAttribut_UU	  */
	public String getUNS_QAMonitoringAttribut_UU();

    /** Column name UNS_QAMonitoringLab_ID */
    public static final String COLUMNNAME_UNS_QAMonitoringLab_ID = "UNS_QAMonitoringLab_ID";

	/** Set Monitoring Laboratory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID);

	/** Get Monitoring Laboratory	  */
	public int getUNS_QAMonitoringLab_ID();

	public I_UNS_QAMonitoringLab getUNS_QAMonitoringLab() throws RuntimeException;

    /** Column name UNS_QAStatus_Type_ID */
    public static final String COLUMNNAME_UNS_QAStatus_Type_ID = "UNS_QAStatus_Type_ID";

	/** Set Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID);

	/** Get Attribute Type	  */
	public int getUNS_QAStatus_Type_ID();

	public I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException;

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
