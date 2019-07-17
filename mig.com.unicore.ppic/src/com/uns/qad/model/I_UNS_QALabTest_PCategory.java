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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QALabTest_PCategory
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QALabTest_PCategory 
{

    /** TableName=UNS_QALabTest_PCategory */
    public static final String Table_Name = "UNS_QALabTest_PCategory";

    /** AD_Table_ID=1000239 */
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

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
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

    /** Column name UNS_QALabTest_ID */
    public static final String COLUMNNAME_UNS_QALabTest_ID = "UNS_QALabTest_ID";

	/** Set Labolatory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID);

	/** Get Labolatory Test	  */
	public int getUNS_QALabTest_ID();

	public com.uns.qad.model.I_UNS_QALabTest getUNS_QALabTest() throws RuntimeException;

    /** Column name UNS_QALabTest_PCategory_ID */
    public static final String COLUMNNAME_UNS_QALabTest_PCategory_ID = "UNS_QALabTest_PCategory_ID";

	/** Set Product Category List	  */
	public void setUNS_QALabTest_PCategory_ID (int UNS_QALabTest_PCategory_ID);

	/** Get Product Category List	  */
	public int getUNS_QALabTest_PCategory_ID();

    /** Column name UNS_QALabTest_PCategory_UU */
    public static final String COLUMNNAME_UNS_QALabTest_PCategory_UU = "UNS_QALabTest_PCategory_UU";

	/** Set UNS_QALabTest_PCategory_UU	  */
	public void setUNS_QALabTest_PCategory_UU (String UNS_QALabTest_PCategory_UU);

	/** Get UNS_QALabTest_PCategory_UU	  */
	public String getUNS_QALabTest_PCategory_UU();

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
