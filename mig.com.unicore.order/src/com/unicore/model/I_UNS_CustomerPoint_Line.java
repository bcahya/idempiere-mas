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

/** Generated Interface for UNS_CustomerPoint_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CustomerPoint_Line 
{

    /** TableName=UNS_CustomerPoint_Line */
    public static final String Table_Name = "UNS_CustomerPoint_Line";

    /** AD_Table_ID=1000234 */
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

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

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

    /** Column name Point */
    public static final String COLUMNNAME_Point = "Point";

	/** Set Point	  */
	public void setPoint (BigDecimal Point);

	/** Get Point	  */
	public BigDecimal getPoint();

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

    /** Column name UNS_CustomerPoint_ID */
    public static final String COLUMNNAME_UNS_CustomerPoint_ID = "UNS_CustomerPoint_ID";

	/** Set Customer Point	  */
	public void setUNS_CustomerPoint_ID (int UNS_CustomerPoint_ID);

	/** Get Customer Point	  */
	public int getUNS_CustomerPoint_ID();

	public com.unicore.model.I_UNS_CustomerPoint getUNS_CustomerPoint() throws RuntimeException;

    /** Column name UNS_CustomerPoint_Line_ID */
    public static final String COLUMNNAME_UNS_CustomerPoint_Line_ID = "UNS_CustomerPoint_Line_ID";

	/** Set Customer Point Line	  */
	public void setUNS_CustomerPoint_Line_ID (int UNS_CustomerPoint_Line_ID);

	/** Get Customer Point Line	  */
	public int getUNS_CustomerPoint_Line_ID();

    /** Column name UNS_CustomerPoint_Line_UU */
    public static final String COLUMNNAME_UNS_CustomerPoint_Line_UU = "UNS_CustomerPoint_Line_UU";

	/** Set UNS_CustomerPoint_Line_UU	  */
	public void setUNS_CustomerPoint_Line_UU (String UNS_CustomerPoint_Line_UU);

	/** Get UNS_CustomerPoint_Line_UU	  */
	public String getUNS_CustomerPoint_Line_UU();

    /** Column name UNS_PointSchema_ID */
    public static final String COLUMNNAME_UNS_PointSchema_ID = "UNS_PointSchema_ID";

	/** Set Point Schema	  */
	public void setUNS_PointSchema_ID (int UNS_PointSchema_ID);

	/** Get Point Schema	  */
	public int getUNS_PointSchema_ID();

	public com.unicore.model.I_UNS_PointSchema getUNS_PointSchema() throws RuntimeException;

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
