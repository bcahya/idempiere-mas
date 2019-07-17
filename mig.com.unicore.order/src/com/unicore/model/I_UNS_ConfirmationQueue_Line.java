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

/** Generated Interface for UNS_ConfirmationQueue_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ConfirmationQueue_Line 
{

    /** TableName=UNS_ConfirmationQueue_Line */
    public static final String Table_Name = "UNS_ConfirmationQueue_Line";

    /** AD_Table_ID=1000230 */
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

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException;

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

    /** Column name isReserved */
    public static final String COLUMNNAME_isReserved = "isReserved";

	/** Set isReserved	  */
	public void setisReserved (boolean isReserved);

	/** Get isReserved	  */
	public boolean isReserved();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name QueueBeforeThis */
    public static final String COLUMNNAME_QueueBeforeThis = "QueueBeforeThis";

	/** Set QueueBeforeThis	  */
	public void setQueueBeforeThis (BigDecimal QueueBeforeThis);

	/** Get QueueBeforeThis	  */
	public BigDecimal getQueueBeforeThis();

    /** Column name Sequence */
    public static final String COLUMNNAME_Sequence = "Sequence";

	/** Set Sequence	  */
	public void setSequence (BigDecimal Sequence);

	/** Get Sequence	  */
	public BigDecimal getSequence();

    /** Column name UNS_ConfirmationQueue_ID */
    public static final String COLUMNNAME_UNS_ConfirmationQueue_ID = "UNS_ConfirmationQueue_ID";

	/** Set Confirmation Queue	  */
	public void setUNS_ConfirmationQueue_ID (int UNS_ConfirmationQueue_ID);

	/** Get Confirmation Queue	  */
	public int getUNS_ConfirmationQueue_ID();

	public com.unicore.model.I_UNS_ConfirmationQueue getUNS_ConfirmationQueue() throws RuntimeException;

    /** Column name UNS_ConfirmationQueue_Line_ID */
    public static final String COLUMNNAME_UNS_ConfirmationQueue_Line_ID = "UNS_ConfirmationQueue_Line_ID";

	/** Set Confirmation Queue Line	  */
	public void setUNS_ConfirmationQueue_Line_ID (int UNS_ConfirmationQueue_Line_ID);

	/** Get Confirmation Queue Line	  */
	public int getUNS_ConfirmationQueue_Line_ID();

    /** Column name UNS_ConfirmationQueue_Line_UU */
    public static final String COLUMNNAME_UNS_ConfirmationQueue_Line_UU = "UNS_ConfirmationQueue_Line_UU";

	/** Set UNS_ConfirmationQueue_Line_UU	  */
	public void setUNS_ConfirmationQueue_Line_UU (String UNS_ConfirmationQueue_Line_UU);

	/** Get UNS_ConfirmationQueue_Line_UU	  */
	public String getUNS_ConfirmationQueue_Line_UU();

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
