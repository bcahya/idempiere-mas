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

import com.uns.model.I_UNS_VATLine;

/** Generated Interface for UNS_VATDocNo
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_VATDocNo 
{

    /** TableName=UNS_VATDocNo */
    public static final String Table_Name = "UNS_VATDocNo";

    /** AD_Table_ID=1000215 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
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

    /** Column name SequenceUsedNo */
    public static final String COLUMNNAME_SequenceUsedNo = "SequenceUsedNo";

	/** Set SequenceUsedNo	  */
	public void setSequenceUsedNo (int SequenceUsedNo);

	/** Get SequenceUsedNo	  */
	public int getSequenceUsedNo();

    /** Column name TaxInvoiceNo */
    public static final String COLUMNNAME_TaxInvoiceNo = "TaxInvoiceNo";

	/** Set Tax Invoice No	  */
	public void setTaxInvoiceNo (String TaxInvoiceNo);

	/** Get Tax Invoice No	  */
	public String getTaxInvoiceNo();

    /** Column name UNS_VATDocNo_ID */
    public static final String COLUMNNAME_UNS_VATDocNo_ID = "UNS_VATDocNo_ID";

	/** Set UNS_VATDocNo	  */
	public void setUNS_VATDocNo_ID (int UNS_VATDocNo_ID);

	/** Get UNS_VATDocNo	  */
	public int getUNS_VATDocNo_ID();

    /** Column name UNS_VATDocNo_UU */
    public static final String COLUMNNAME_UNS_VATDocNo_UU = "UNS_VATDocNo_UU";

	/** Set UNS_VATDocNo_UU	  */
	public void setUNS_VATDocNo_UU (String UNS_VATDocNo_UU);

	/** Get UNS_VATDocNo_UU	  */
	public String getUNS_VATDocNo_UU();

    /** Column name UNS_VATLine_ID */
    public static final String COLUMNNAME_UNS_VATLine_ID = "UNS_VATLine_ID";

	/** Set VAT Lines	  */
	public void setUNS_VATLine_ID (int UNS_VATLine_ID);

	/** Get VAT Lines	  */
	public int getUNS_VATLine_ID();

	public I_UNS_VATLine getUNS_VATLine() throws RuntimeException;

    /** Column name UNS_VATRegisteredSequences_ID */
    public static final String COLUMNNAME_UNS_VATRegisteredSequences_ID = "UNS_VATRegisteredSequences_ID";

	/** Set VAT Registered Sequences	  */
	public void setUNS_VATRegisteredSequences_ID (int UNS_VATRegisteredSequences_ID);

	/** Get VAT Registered Sequences	  */
	public int getUNS_VATRegisteredSequences_ID();

	public com.unicore.model.I_UNS_VATRegisteredSequences getUNS_VATRegisteredSequences() throws RuntimeException;

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

    /** Column name UsageStatus */
    public static final String COLUMNNAME_UsageStatus = "UsageStatus";

	/** Set UsageStatus	  */
	public void setUsageStatus (String UsageStatus);

	/** Get UsageStatus	  */
	public String getUsageStatus();
}
