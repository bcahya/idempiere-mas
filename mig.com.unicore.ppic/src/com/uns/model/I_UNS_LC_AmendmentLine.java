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

/** Generated Interface for UNS_LC_AmendmentLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_LC_AmendmentLine 
{

    /** TableName=UNS_LC_AmendmentLine */
    public static final String Table_Name = "UNS_LC_AmendmentLine";

    /** AD_Table_ID=1000321 */
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

    /** Column name LCQuantity */
    public static final String COLUMNNAME_LCQuantity = "LCQuantity";

	/** Set LC Quantity	  */
	public void setLCQuantity (BigDecimal LCQuantity);

	/** Get LC Quantity	  */
	public BigDecimal getLCQuantity();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

    /** Column name PrevLCQuantity */
    public static final String COLUMNNAME_PrevLCQuantity = "PrevLCQuantity";

	/** Set Prev LC Quantity	  */
	public void setPrevLCQuantity (BigDecimal PrevLCQuantity);

	/** Get Prev LC Quantity	  */
	public BigDecimal getPrevLCQuantity();

    /** Column name UNS_LC_Amendment_ID */
    public static final String COLUMNNAME_UNS_LC_Amendment_ID = "UNS_LC_Amendment_ID";

	/** Set LC Amendment	  */
	public void setUNS_LC_Amendment_ID (int UNS_LC_Amendment_ID);

	/** Get LC Amendment	  */
	public int getUNS_LC_Amendment_ID();

	public com.uns.model.I_UNS_LC_Amendment getUNS_LC_Amendment() throws RuntimeException;

    /** Column name UNS_LC_AmendmentLine_ID */
    public static final String COLUMNNAME_UNS_LC_AmendmentLine_ID = "UNS_LC_AmendmentLine_ID";

	/** Set LC Amendment Line	  */
	public void setUNS_LC_AmendmentLine_ID (int UNS_LC_AmendmentLine_ID);

	/** Get LC Amendment Line	  */
	public int getUNS_LC_AmendmentLine_ID();

    /** Column name UNS_LC_AmendmentLine_UU */
    public static final String COLUMNNAME_UNS_LC_AmendmentLine_UU = "UNS_LC_AmendmentLine_UU";

	/** Set UNS_LC_AmendmentLine_UU	  */
	public void setUNS_LC_AmendmentLine_UU (String UNS_LC_AmendmentLine_UU);

	/** Get UNS_LC_AmendmentLine_UU	  */
	public String getUNS_LC_AmendmentLine_UU();

    /** Column name UNS_LC_Lines_ID */
    public static final String COLUMNNAME_UNS_LC_Lines_ID = "UNS_LC_Lines_ID";

	/** Set LC Lines	  */
	public void setUNS_LC_Lines_ID (int UNS_LC_Lines_ID);

	/** Get LC Lines	  */
	public int getUNS_LC_Lines_ID();

	public com.uns.model.I_UNS_LC_Lines getUNS_LC_Lines() throws RuntimeException;

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