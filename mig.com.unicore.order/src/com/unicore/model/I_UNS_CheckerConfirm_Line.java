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

/** Generated Interface for UNS_CheckerConfirm_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CheckerConfirm_Line 
{

    /** TableName=UNS_CheckerConfirm_Line */
    public static final String Table_Name = "UNS_CheckerConfirm_Line";

    /** AD_Table_ID=1000305 */
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

    /** Column name Notes */
    public static final String COLUMNNAME_Notes = "Notes";

	/** Set Notes	  */
	public void setNotes (String Notes);

	/** Get Notes	  */
	public String getNotes();

    /** Column name UNS_CheckerConfirm_Line_ID */
    public static final String COLUMNNAME_UNS_CheckerConfirm_Line_ID = "UNS_CheckerConfirm_Line_ID";

	/** Set Checker Confirmation Line	  */
	public void setUNS_CheckerConfirm_Line_ID (int UNS_CheckerConfirm_Line_ID);

	/** Get Checker Confirmation Line	  */
	public int getUNS_CheckerConfirm_Line_ID();

    /** Column name UNS_CheckerConfirm_Line_UU */
    public static final String COLUMNNAME_UNS_CheckerConfirm_Line_UU = "UNS_CheckerConfirm_Line_UU";

	/** Set UNS_CheckerConfirm_Line_UU	  */
	public void setUNS_CheckerConfirm_Line_UU (String UNS_CheckerConfirm_Line_UU);

	/** Get UNS_CheckerConfirm_Line_UU	  */
	public String getUNS_CheckerConfirm_Line_UU();

    /** Column name UNS_CheckerConfirmation_ID */
    public static final String COLUMNNAME_UNS_CheckerConfirmation_ID = "UNS_CheckerConfirmation_ID";

	/** Set Checker Confirmation	  */
	public void setUNS_CheckerConfirmation_ID (int UNS_CheckerConfirmation_ID);

	/** Get Checker Confirmation	  */
	public int getUNS_CheckerConfirmation_ID();

	public com.unicore.model.I_UNS_CheckerConfirmation getUNS_CheckerConfirmation() throws RuntimeException;

    /** Column name UNS_CheckerLine_ID */
    public static final String COLUMNNAME_UNS_CheckerLine_ID = "UNS_CheckerLine_ID";

	/** Set Detail Of Checker	  */
	public void setUNS_CheckerLine_ID (int UNS_CheckerLine_ID);

	/** Get Detail Of Checker	  */
	public int getUNS_CheckerLine_ID();

	public com.unicore.model.I_UNS_CheckerLine getUNS_CheckerLine() throws RuntimeException;

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
