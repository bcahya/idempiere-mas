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

/** Generated Interface for UNS_SO_AmendmentLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_SO_AmendmentLine 
{

    /** TableName=UNS_SO_AmendmentLine */
    public static final String Table_Name = "UNS_SO_AmendmentLine";

    /** AD_Table_ID=1000191 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/** Set Line.
	  * Line No
	  */
	public void setLineNo (int LineNo);

	/** Get Line.
	  * Line No
	  */
	public int getLineNo();

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

    /** Column name NewAmt */
    public static final String COLUMNNAME_NewAmt = "NewAmt";

	/** Set New Amount	  */
	public void setNewAmt (BigDecimal NewAmt);

	/** Get New Amount	  */
	public BigDecimal getNewAmt();

    /** Column name NewPrice */
    public static final String COLUMNNAME_NewPrice = "NewPrice";

	/** Set New Price	  */
	public void setNewPrice (BigDecimal NewPrice);

	/** Get New Price	  */
	public BigDecimal getNewPrice();

    /** Column name PrevAmt */
    public static final String COLUMNNAME_PrevAmt = "PrevAmt";

	/** Set Prev Amount	  */
	public void setPrevAmt (BigDecimal PrevAmt);

	/** Get Prev Amount	  */
	public BigDecimal getPrevAmt();

    /** Column name PrevPrice */
    public static final String COLUMNNAME_PrevPrice = "PrevPrice";

	/** Set Prev Price	  */
	public void setPrevPrice (BigDecimal PrevPrice);

	/** Get Prev Price	  */
	public BigDecimal getPrevPrice();

    /** Column name PrevQuantity */
    public static final String COLUMNNAME_PrevQuantity = "PrevQuantity";

	/** Set Prev Quantity	  */
	public void setPrevQuantity (BigDecimal PrevQuantity);

	/** Get Prev Quantity	  */
	public BigDecimal getPrevQuantity();

    /** Column name Quantity */
    public static final String COLUMNNAME_Quantity = "Quantity";

	/** Set Quantity	  */
	public void setQuantity (BigDecimal Quantity);

	/** Get Quantity	  */
	public BigDecimal getQuantity();

    /** Column name UNS_SO_Amendment_ID */
    public static final String COLUMNNAME_UNS_SO_Amendment_ID = "UNS_SO_Amendment_ID";

	/** Set SO Amendment	  */
	public void setUNS_SO_Amendment_ID (int UNS_SO_Amendment_ID);

	/** Get SO Amendment	  */
	public int getUNS_SO_Amendment_ID();

	public com.uns.model.I_UNS_SO_Amendment getUNS_SO_Amendment() throws RuntimeException;

    /** Column name UNS_SO_AmendmentLine_ID */
    public static final String COLUMNNAME_UNS_SO_AmendmentLine_ID = "UNS_SO_AmendmentLine_ID";

	/** Set SO Amendment Line	  */
	public void setUNS_SO_AmendmentLine_ID (int UNS_SO_AmendmentLine_ID);

	/** Get SO Amendment Line	  */
	public int getUNS_SO_AmendmentLine_ID();

    /** Column name UNS_SO_AmendmentLine_UU */
    public static final String COLUMNNAME_UNS_SO_AmendmentLine_UU = "UNS_SO_AmendmentLine_UU";

	/** Set UNS_SO_AmendmentLine_UU	  */
	public void setUNS_SO_AmendmentLine_UU (String UNS_SO_AmendmentLine_UU);

	/** Get UNS_SO_AmendmentLine_UU	  */
	public String getUNS_SO_AmendmentLine_UU();

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
