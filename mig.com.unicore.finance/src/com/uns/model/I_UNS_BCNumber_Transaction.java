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

/** Generated Interface for UNS_BCNumber_Transaction
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_BCNumber_Transaction 
{

    /** TableName=UNS_BCNumber_Transaction */
    public static final String Table_Name = "UNS_BCNumber_Transaction";

    /** AD_Table_ID=1000367 */
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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Coconut RMP.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Coconut RMP.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name UNS_BCNumber_GeneratorLine_ID */
    public static final String COLUMNNAME_UNS_BCNumber_GeneratorLine_ID = "UNS_BCNumber_GeneratorLine_ID";

	/** Set BC Number Generator Line	  */
	public void setUNS_BCNumber_GeneratorLine_ID (int UNS_BCNumber_GeneratorLine_ID);

	/** Get BC Number Generator Line	  */
	public int getUNS_BCNumber_GeneratorLine_ID();

	public com.uns.model.I_UNS_BCNumber_GeneratorLine getUNS_BCNumber_GeneratorLine() throws RuntimeException;

    /** Column name UNS_BCNumber_Transaction_ID */
    public static final String COLUMNNAME_UNS_BCNumber_Transaction_ID = "UNS_BCNumber_Transaction_ID";

	/** Set Bea Cukai Transaction	  */
	public void setUNS_BCNumber_Transaction_ID (int UNS_BCNumber_Transaction_ID);

	/** Get Bea Cukai Transaction	  */
	public int getUNS_BCNumber_Transaction_ID();

    /** Column name UNS_BCNumber_Transaction_UU */
    public static final String COLUMNNAME_UNS_BCNumber_Transaction_UU = "UNS_BCNumber_Transaction_UU";

	/** Set UNS_BCNumber_Transaction_UU	  */
	public void setUNS_BCNumber_Transaction_UU (String UNS_BCNumber_Transaction_UU);

	/** Get UNS_BCNumber_Transaction_UU	  */
	public String getUNS_BCNumber_Transaction_UU();

    /** Column name UNS_PackingSlip_ID */
    public static final String COLUMNNAME_UNS_PackingSlip_ID = "UNS_PackingSlip_ID";

	/** Set Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID);

	/** Get Packing Slip	  */
	public int getUNS_PackingSlip_ID();

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