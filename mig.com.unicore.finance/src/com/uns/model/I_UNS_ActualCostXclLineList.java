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

/** Generated Interface for UNS_ActualCostXclLineList
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ActualCostXclLineList 
{

    /** TableName=UNS_ActualCostXclLineList */
    public static final String Table_Name = "UNS_ActualCostXclLineList";

    /** AD_Table_ID=1000224 */
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

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException;

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

    /** Column name UNS_ActualCostConfigLine_ID */
    public static final String COLUMNNAME_UNS_ActualCostConfigLine_ID = "UNS_ActualCostConfigLine_ID";

	/** Set Actual Cost Configuration Line	  */
	public void setUNS_ActualCostConfigLine_ID (int UNS_ActualCostConfigLine_ID);

	/** Get Actual Cost Configuration Line	  */
	public int getUNS_ActualCostConfigLine_ID();

	public com.uns.model.I_UNS_ActualCostConfigLine getUNS_ActualCostConfigLine() throws RuntimeException;

    /** Column name UNS_ActualCostXclLineList_ID */
    public static final String COLUMNNAME_UNS_ActualCostXclLineList_ID = "UNS_ActualCostXclLineList_ID";

	/** Set UNS_ActualCostXclLineList_ID.
	  * UNS_ActualCostXclLineList_ID
	  */
	public void setUNS_ActualCostXclLineList_ID (int UNS_ActualCostXclLineList_ID);

	/** Get UNS_ActualCostXclLineList_ID.
	  * UNS_ActualCostXclLineList_ID
	  */
	public int getUNS_ActualCostXclLineList_ID();

    /** Column name UNS_ActualCostXclLineList_UU */
    public static final String COLUMNNAME_UNS_ActualCostXclLineList_UU = "UNS_ActualCostXclLineList_UU";

	/** Set UNS_ActualCostXclLineList_UU	  */
	public void setUNS_ActualCostXclLineList_UU (String UNS_ActualCostXclLineList_UU);

	/** Get UNS_ActualCostXclLineList_UU	  */
	public String getUNS_ActualCostXclLineList_UU();

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
