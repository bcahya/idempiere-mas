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

/** Generated Interface for UNS_PL_ReturnOrder
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_PL_ReturnOrder 
{

    /** TableName=UNS_PL_ReturnOrder */
    public static final String Table_Name = "UNS_PL_ReturnOrder";

    /** AD_Table_ID=1000187 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (String C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public String getC_BPartner_ID();

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

    /** Column name IsCancelled */
    public static final String COLUMNNAME_IsCancelled = "IsCancelled";

	/** Set Cancelled.
	  * The transaction was cancelled
	  */
	public void setIsCancelled (boolean IsCancelled);

	/** Get Cancelled.
	  * The transaction was cancelled
	  */
	public boolean isCancelled();

    /** Column name isCancelOrder */
    public static final String COLUMNNAME_isCancelOrder = "isCancelOrder";

	/** Set Cancel Order	  */
	public void setisCancelOrder (boolean isCancelOrder);

	/** Get Cancel Order	  */
	public boolean isCancelOrder();

    /** Column name IsPartialCancelation */
    public static final String COLUMNNAME_IsPartialCancelation = "IsPartialCancelation";

	/** Set Partial Cancelation	  */
	public void setIsPartialCancelation (boolean IsPartialCancelation);

	/** Get Partial Cancelation	  */
	public boolean isPartialCancelation();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/** Set Reason	  */
	public void setReason (String Reason);

	/** Get Reason	  */
	public String getReason();

    /** Column name UNS_PackingList_Order_ID */
    public static final String COLUMNNAME_UNS_PackingList_Order_ID = "UNS_PackingList_Order_ID";

	/** Set Packing List Order	  */
	public void setUNS_PackingList_Order_ID (int UNS_PackingList_Order_ID);

	/** Get Packing List Order	  */
	public int getUNS_PackingList_Order_ID();

	public com.unicore.model.I_UNS_PackingList_Order getUNS_PackingList_Order() throws RuntimeException;

    /** Column name UNS_PL_Return_ID */
    public static final String COLUMNNAME_UNS_PL_Return_ID = "UNS_PL_Return_ID";

	/** Set Packing List Return	  */
	public void setUNS_PL_Return_ID (int UNS_PL_Return_ID);

	/** Get Packing List Return	  */
	public int getUNS_PL_Return_ID();

	public com.unicore.model.I_UNS_PL_Return getUNS_PL_Return() throws RuntimeException;

    /** Column name UNS_PL_ReturnOrder_ID */
    public static final String COLUMNNAME_UNS_PL_ReturnOrder_ID = "UNS_PL_ReturnOrder_ID";

	/** Set Canceled Shipment	  */
	public void setUNS_PL_ReturnOrder_ID (int UNS_PL_ReturnOrder_ID);

	/** Get Canceled Shipment	  */
	public int getUNS_PL_ReturnOrder_ID();

    /** Column name UNS_PL_ReturnOrder_UU */
    public static final String COLUMNNAME_UNS_PL_ReturnOrder_UU = "UNS_PL_ReturnOrder_UU";

	/** Set UNS_PL_ReturnOrder_UU	  */
	public void setUNS_PL_ReturnOrder_UU (String UNS_PL_ReturnOrder_UU);

	/** Get UNS_PL_ReturnOrder_UU	  */
	public String getUNS_PL_ReturnOrder_UU();

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

    /** Column name VoidIt */
    public static final String COLUMNNAME_VoidIt = "VoidIt";

	/** Set Void It	  */
	public void setVoidIt (String VoidIt);

	/** Get Void It	  */
	public String getVoidIt();
}
