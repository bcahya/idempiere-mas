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

/** Generated Interface for UNS_BCNumber_Generator
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_BCNumber_Generator 
{

    /** TableName=UNS_BCNumber_Generator */
    public static final String Table_Name = "UNS_BCNumber_Generator";

    /** AD_Table_ID=1000349 */
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

    /** Column name LatestUsedNumber */
    public static final String COLUMNNAME_LatestUsedNumber = "LatestUsedNumber";

	/** Set Latest Used Number	  */
	public void setLatestUsedNumber (String LatestUsedNumber);

	/** Get Latest Used Number	  */
	public String getLatestUsedNumber();

    /** Column name UNS_BCCode_ID */
    public static final String COLUMNNAME_UNS_BCCode_ID = "UNS_BCCode_ID";

	/** Set Bea Cukai Code	  */
	public void setUNS_BCCode_ID (int UNS_BCCode_ID);

	/** Get Bea Cukai Code	  */
	public int getUNS_BCCode_ID();

    /** Column name UNS_BCNumber_Generator_ID */
    public static final String COLUMNNAME_UNS_BCNumber_Generator_ID = "UNS_BCNumber_Generator_ID";

	/** Set BC Number Generator	  */
	public void setUNS_BCNumber_Generator_ID (int UNS_BCNumber_Generator_ID);

	/** Get BC Number Generator	  */
	public int getUNS_BCNumber_Generator_ID();

    /** Column name UNS_BCNumber_Generator_UU */
    public static final String COLUMNNAME_UNS_BCNumber_Generator_UU = "UNS_BCNumber_Generator_UU";

	/** Set UNS_BCNumber_Generator_UU	  */
	public void setUNS_BCNumber_Generator_UU (String UNS_BCNumber_Generator_UU);

	/** Get UNS_BCNumber_Generator_UU	  */
	public String getUNS_BCNumber_Generator_UU();

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
