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

/** Generated Interface for UNS_Resource_Utilities
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Resource_Utilities 
{

    /** TableName=UNS_Resource_Utilities */
    public static final String Table_Name = "UNS_Resource_Utilities";

    /** AD_Table_ID=1000029 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
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

    /** Column name MaxConsumption */
    public static final String COLUMNNAME_MaxConsumption = "MaxConsumption";

	/** Set Max. Consumption	  */
	public void setMaxConsumption (BigDecimal MaxConsumption);

	/** Get Max. Consumption	  */
	public BigDecimal getMaxConsumption();

    /** Column name MinConsumption */
    public static final String COLUMNNAME_MinConsumption = "MinConsumption";

	/** Set Min. Consumption	  */
	public void setMinConsumption (BigDecimal MinConsumption);

	/** Get Min. Consumption	  */
	public BigDecimal getMinConsumption();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Machine	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Machine	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

    /** Column name UNS_Resource_Utilities_ID */
    public static final String COLUMNNAME_UNS_Resource_Utilities_ID = "UNS_Resource_Utilities_ID";

	/** Set Resource Utilities	  */
	public void setUNS_Resource_Utilities_ID (int UNS_Resource_Utilities_ID);

	/** Get Resource Utilities	  */
	public int getUNS_Resource_Utilities_ID();

    /** Column name UNS_Resource_Utilities_UU */
    public static final String COLUMNNAME_UNS_Resource_Utilities_UU = "UNS_Resource_Utilities_UU";

	/** Set Resource Utilities UU	  */
	public void setUNS_Resource_Utilities_UU (String UNS_Resource_Utilities_UU);

	/** Get Resource Utilities UU	  */
	public String getUNS_Resource_Utilities_UU();

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

    /** Column name UtilityType */
    public static final String COLUMNNAME_UtilityType = "UtilityType";

	/** Set Utility Type	  */
	public void setUtilityType (String UtilityType);

	/** Get Utility Type	  */
	public String getUtilityType();
}
